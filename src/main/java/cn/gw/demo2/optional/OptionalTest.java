package cn.gw.demo2.optional;

import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Optional;

public class OptionalTest {

    public static void main(String[] args) throws Exception {
        test();
    }
    public static void test() throws Exception {
        User user = create("dd");
        System.out.println("不空的方法使用");
        User user2 = Optional.of(user).orElseGet(User::new);
        User user1 = Optional.ofNullable(user).orElse(create(null));


        User user3 = create(null);
        User user4 = Optional.ofNullable(user3).orElseGet(User::new);

        user2.setName("");
        user2.setAge(0);


    }

    private static User create(String type) {
        System.out.println("new one");
        return StringUtils.isEmpty(type) ? null : new User();
    }

    @Data
    static
    class User {
        String name;
        int age;
    }
}
