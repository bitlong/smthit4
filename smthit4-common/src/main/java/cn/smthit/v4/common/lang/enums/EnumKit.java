package cn.smthit.v4.common.lang.enums;

/**
 * 枚举工具
 * @author dinghq
 * @date 2023/8/28
 */
public class EnumKit {

    /**
     * 获得枚举类
     * @param enumModule 枚举类模块
     * @param enumName 枚举类名称
     * @return 枚举类
     */
    public static Class<?> getEnum(String packageName, String enumModule, String enumName) {
        try {
            if(!packageName.endsWith(".")) {
                packageName += ".";
            }
            return Class.forName(packageName + enumModule + "." + enumName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
