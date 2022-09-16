package cn.smthit.v4.feign.kits;

import java.io.*;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/9/16  12:14
 */
public class SerializalbeKit {

    /**
     * 异常序列化为字节码
     * @param exception
     * @return
     * @throws IOException
     */
    public static byte[] serialize(Exception exception) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream oo = new ObjectOutputStream(byteArrayOutputStream);) {
            oo.writeObject(exception);
            oo.flush();
            return byteArrayOutputStream.toByteArray();
        }
    }

    /**
     * 直接码反序列化为异常对象
     * @param bytes
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Exception deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new ObjectInputStream(byteArrayInputStream);) {
            return (Exception) ois.readObject();
        }
    }
}