package com.itheima.test;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * @author zjq
 * @date 2023/10/19
 */
public class BCryptTest {


    @Test
    public void createPassword(){

        String password = BCrypt.hashpw("112233", BCrypt.gensalt());
        System.out.println(password);
        //$2a$10$yi6zIXQ4EW28DrupAUiznOZWl34VeMdaq2u5bnyyEVjf6EQsfIzHy
        //$2a$10$omlvbRtbl2tysaQ4aXDOletVmw2gjaQYu/RniWCrxfENLZfzCtty.

    }

    @Test
    public void checkPassword(){
        boolean checkpw = BCrypt.checkpw("123456", "$2a$10$CNIfESVYBZ.uwKyeD7ZfDOQ.aqJE0Y4M7EF8yPb6ZzpRQOlioXqS6");
        System.out.println(checkpw);
    }
}
