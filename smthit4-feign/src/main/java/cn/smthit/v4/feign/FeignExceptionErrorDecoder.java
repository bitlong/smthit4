package cn.smthit.v4.feign;

import cn.smthit.v4.common.lang.exception.ErrorBuilder;
import cn.smthit.v4.common.lang.exception.ServiceException;
import cn.smthit.v4.common.lang.kits.CollectionKit;
import cn.smthit.v4.feign.exception.ErrorCode;
import cn.smthit.v4.feign.kits.SerializalbeKit;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

/**
 * @description: FeignClient异常解码器
 * @author: Bean
 * @date: 2022/9/16  12:23
 */
@Slf4j
public class FeignExceptionErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.body() != null) {

            Collection<String> errorDecodes = response.headers().get(FeignConstants.FEIGN_REQUEST_HEADER);
            if(errorDecodes.isEmpty()) {
                return errorStatus(methodKey, response);
            }

            String decodeType = errorDecodes.toArray()[0].toString();

            if (FeignConstants.FEIGN_REQUEST_HEADER_VALUE.equals(decodeType) &&
                    HttpStatus.INTERNAL_SERVER_ERROR.value() == response.status()) {
                try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                     InputStream inputStream = response.body().asInputStream();) {
                    IOUtils.copy(inputStream, byteArrayOutputStream);
                    try {
                        return SerializalbeKit.deserialize(byteArrayOutputStream.toByteArray());
                    } catch (ClassNotFoundException e) {
                        return ErrorBuilder.builder()
                                .setDetailMessage(byteArrayOutputStream.toString())
                                .build(cn.smthit.v4.common.lang.exception.ClassNotFoundException.class);
                    }
                } catch (IOException e) {
                    return e;
                }
            }
        }

        return null;
    }

    private Exception errorStatus(String key, Response response) {
        if(response.status() >= 400 && response.status() < 499) {
            log.error("客户端错误", response.body());
            return ErrorBuilder.builder()
                    .setCode(ErrorCode.HTTP_40X)
                    .setDetailMessage("HttpStatus : %s %s", response.status(), response.body())
                    .build(ServiceException.class);
        }

        if(response.status() >= 500 && response.status() < 599) {
            log.error("服务器内部异常", response.body());
            return ErrorBuilder.builder()
                    .setCode(ErrorCode.HTTP_50X)
                    .setDetailMessage("HttpStatus : %s %s", response.status(), response.body())
                    .build(ServiceException.class);
        }

        return new ErrorDecoder.Default().decode(key, response);
    }
}