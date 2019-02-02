package org.anirudh.marketplace.util;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncrypter {

    @Test
    public void encrypt(){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String s = passwordEncoder.encode("intuitApp");
        System.out.println(s);

    }
}
