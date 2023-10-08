package cn.smthit.v4.common.lang.kits;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * TODO
 *
 * @author bean
 * @date 2023/10/8
 */
public class DesensitizedKit {
    /**
     * 功能描述：姓名脱敏
     * 脱敏规则：只显示第一个汉字,比如李某某置换为李**, 李某置换为李*
     * @param fullName 完整的姓名
     * @return
     */
    public static String desensitizedName(String fullName) {
        if (StringUtils.isNotBlank(fullName)) {
            String name = StringUtils.left(fullName, 1);
            return StringUtils.rightPad(name, StringUtils.length(fullName), "*");
        }
        return fullName;
    }

    /**
     * 功能描述：手机号脱敏
     * 脱敏规则：保留前三后三, 比如18368158794置换为183*****794
     * @param phoneNumber 手机号
     * @return
     */
    public static String desensitizedPhoneNumber(String phoneNumber) {
        if (StringUtils.isNotBlank(phoneNumber)) {
            phoneNumber = phoneNumber.replaceAll("(\\w{3})\\w*(\\w{3})", "$1*****$2");
        }
        return phoneNumber;
    }

    /**
     * 功能描述：身份证号脱敏
     * 脱敏规则：保留前六后三, 适用于15位和18位身份证号
     * @param idNumber 身份证号
     * @return
     */
    public static String desensitizedIdNumber(String idNumber) {
        if (StringUtils.isNotBlank(idNumber) && idNumber.length() >= 15) {
            return StringUtils.left(idNumber, 6).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(idNumber, 3), StringUtils.length(idNumber), "*"), "******"));
        }
        return idNumber;
    }

    /**
     * 功能描述：地址脱敏
     * 脱敏规则：从第4位开始隐藏,隐藏8位
     *         因地址位数是不确定的,所以结尾长度为总长度减去 前面保留长度和隐藏长度之和 address.length()-11
     * @param address 具体地址
     * @return
     */
    public static String desensitizedAddress(String address) {
        if (StringUtils.isNotBlank(address)) {
            return StringUtils.left(address, 3).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(address, address.length() - 11), StringUtils.length(address), "*"), "***"));
        }
        return address;
    }

    /**
     * 加密邮箱
     * @param email
     * @return
     */
    public static String desensitizedEmail(String email) {

        if (StringUtils.isNotBlank(email) && email.contains("@")) {
            String name  = email.substring(0, email.indexOf('@'));
            String address = email.substring(email.indexOf('@'));
            name = desensitizedName(name);
            return name + address;
        }

        return email;
    }

    public static <T> List<T> desensitizedList(List<T> objs) {
        if(objs == null || objs.size() == 0) {
            return objs;
        }

        for (T obj : objs) {
            desensitizedObject(obj);
        }

        return objs;
    }

    public static <T> T desensitizedObject(T obj) {
        if(obj == null) {
            return null;
        }

        try {
            return internelEncrpytObject(obj);
        } catch (Exception e) {
            return obj;
        }
    }

    private static <T> T internelEncrpytObject(T object) throws Exception {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            // 判断字段上是否存在@DecryptField注解
            boolean hasSecureField = field.isAnnotationPresent(EncryptField.class);
            // 存在
            if (hasSecureField) {
                // 暴力破解 不然操作不了权限为private的字段
                field.setAccessible(true);
                // 如果当前字段在userDo中不为空 即name,address字段有值
                if (field.get(object) != null) {
                    // 获取字段上注解的value值
                    DesensitizationEnum desensitizationEnum = field.getAnnotation(EncryptField.class).value();
                    // 根据不同的value值 我们可以对字段进行不同逻辑的脱敏 比如姓名脱敏-魏*,手机号脱敏-187****2275
                    Object obj = internalEncrypt(desensitizationEnum, field.get(object));
                    // 字段内容加密过后 通过反射重新赋给该字段
                    field.set(object, obj);
                }
            }
        }

        return object;
    }

    private static Object internalEncrypt(DesensitizationEnum desensitizationEnum, Object obj){
        if(desensitizationEnum == DesensitizationEnum.ADDRESS) {
            return DesensitizedKit.desensitizedAddress(obj.toString());
        }

        if(desensitizationEnum == DesensitizationEnum.EMAIL) {
            return DesensitizedKit.desensitizedEmail(obj.toString());
        }

        if(desensitizationEnum == DesensitizationEnum.NAME) {
            return DesensitizedKit.desensitizedName(obj.toString());
        }

        if(desensitizationEnum == DesensitizationEnum.PHONE) {
            return DesensitizedKit.desensitizedPhoneNumber(obj.toString());
        }

        return obj;
    }
}
