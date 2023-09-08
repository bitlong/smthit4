package cn.smthit.v4.common.lang.optional;

import org.junit.Test;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * TODO
 *
 * @author bean
 * @date 2023/9/5
 */
public class TestOptional {

    @Test
    public void testOptional() {
        String test = "b";
        String input = ret();

        // 为空则返回默认值
        String result = Optional.ofNullable(input).orElse("a");
        System.out.println(result);

        System.out.println(Optional.ofNullable(input).isPresent());

        // 优雅的编程，不为空则消费
        Optional.ofNullable(input).ifPresent(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println("消费掉：" + s);
            }
        });


        result = Optional.ofNullable(input).orElseGet(new Supplier<String>() {
            @Override
            public String get() {
                return "生成默认值";
            }
        });

        System.out.println(result);


        result = Optional.of(input).map((k)-> {
            return k + "1";
        }).orElse("");
        System.out.println(result);

        // result = Optional.of(input).orElse("a");
        // System.out.println(result);

        User userId = Optional.of(retUser()).flatMap((u)->{
            return Optional.of(u);
        }).orElse(retUser());


        System.out.println(userId);
    }

    private String ret() {
        return "aa";
    }

    private User retUser() {
        return new User(1L, "Bean");
    }
}
