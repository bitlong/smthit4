package cn.smthit.v4.common.lang.experimental;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/10/13  21:56
 */
public class If {
    /**
     * if执行器
     * @param flag
     * @param fun
     */
    public static void execute(boolean flag, NoArgFunction fun) {
        if(flag) {
            fun.excute();
        }
    }

    /**
     * if...else...
     * @param flag
     * @param trueFun
     * @param falseFun
     */
    public static void execute(boolean flag, NoArgFunction trueFun, NoArgFunction falseFun) {
        if(flag) {
            trueFun.excute();
        } else {
            falseFun.excute();
        }
    }
}