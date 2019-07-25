package com.glen.authorizationserver.config;/**
 * @author Glen
 * @create 2019- 06-2019/6/28-11:06
 * @Description
 */

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Glen
 * @create 2019/6/28 11:06 
 * @Description
 */
public class BPwdEncoderUtil implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals(charSequence.toString());
    }
}

