package cn.smthit.v4.feign;

import cn.smthit.v4.common.lang.exception.ErrorBuilder;
import cn.smthit.v4.common.lang.exception.ServiceException;
import cn.smthit.v4.feign.exception.ErrorCode;
import cn.smthit.v4.feign.exception.FeignClientException;
import cn.smthit.v4.feign.exception.FeignServerException;
import cn.smthit.v4.feign.kits.SerializalbeKit;
import com.netflix.client.ClientException;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

/**
 * @description: FeignClient异常解码器
 * 微服务的调用（Consumer）调用，对Response的返回值进行解码。
 * @author: Bean
 * @date: 2022/9/16  12:23
 */
@Slf4j
public class FeignExceptionErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.body() != null) {

            Collection<String> errorDecodes = response.headers().get(FeignConstants.FEIGN_REQUEST_HEADER);

            //非Feign请求
            if (errorDecodes == null || errorDecodes.isEmpty()) {
                return errorStatus(methodKey, response);
            }

            String decodeType = errorDecodes.toArray()[0].toString();

            //是Feign请求
            if (FeignConstants.FEIGN_REQUEST_HEADER_VALUE.equals(decodeType) &&
                    HttpStatus.INTERNAL_SERVER_ERROR.value() == response.status()) {
                try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                     InputStream inputStream = response.body().asInputStream();) {

                    IOUtils.copy(inputStream, byteArrayOutputStream);

                    try {
                        return SerializalbeKit.deserialize(byteArrayOutputStream.toByteArray());
                    } catch (ClassNotFoundException e) {
                        log.error(e.getMessage(), e);
                        return ErrorBuilder.builder()
                                .setCode(ErrorCode.HTTP_50X)
                                .setMessage("接口反序列化失败, 无法找到类，请联系管理员")
                                .build(cn.smthit.v4.common.lang.exception.ClassNotFoundException.class);
                    }
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                    return ErrorBuilder.builder()
                            .setCode(ErrorCode.HTTP_50X)
                            .setMessage("IO异常")
                            .setDetailMessage(e.getMessage())
                            .build(FeignClientException.class);
                }
            }
        }


        //默认处理方法
        return ErrorBuilder.builder()
                .setCode(ErrorCode.HTTP_50X)
                .setMessage("请求失败或无响应内容")
                .setDetailMessage("方法名: %s", methodKey)
                .build(FeignClientException.class);
    }

    private Exception errorStatus(String key, Response response) {
        String ret = "";

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             InputStream inputStream = response.body().asInputStream();
        ){
            IOUtils.copy(inputStream, byteArrayOutputStream);
            ret = byteArrayOutputStream.toString(StandardCharsets.UTF_8.name());
        } catch (IOException exp) {
            log.error(exp.getMessage(), exp);
        }

        if(response.status() >= 400 && response.status() < 499) {
            log.error("40X错误 status={} ret={}", response.status(), ret);
            return ErrorBuilder.builder()
                    .setCode(ErrorCode.HTTP_40X)
                    .setMessage(response.reason())
                    .setDetailMessage("HttpStatus : %s\n", response.status())
                    .appendDetailMessage("Request: %s\n", response.request().toString())
                    .appendDetailMessage("Response: %s\n", response.toString())
                    .build(FeignClientException.class);
        }

        if(response.status() >= 500 && response.status() < 599) {
            log.error("服务器内部异常, status={} ret={}", response.status(), ret);
            return ErrorBuilder.builder()
                    .setCode(ErrorCode.HTTP_50X)
                    .setMessage(response.reason())
                    .setDetailMessage("HttpStatus : %s\n", response.status())
                    .appendDetailMessage("Request: %s", response.request().toString())
                    .appendDetailMessage("Response: %s", response.toString())
                    .build(FeignServerException.class);
        }

        try {
            return new ErrorDecoder.Default().decode(key, response);
        } catch (FeignException exp) {
            log.error(exp.getMessage(), exp);
            return ErrorBuilder.builder()
                    .setCode(ErrorCode.HTTP_40X)
                    .setMessage("Feign解码失败,请联系管理员")
                    .setDetailMessage(exp.getMessage())
                    .setParentException(exp)
                    .build(FeignClientException.class);
        }
    }
}