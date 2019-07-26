package com.glen.appcustomerlogin;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.ws.rs.core.MediaType;
import java.util.Base64;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppCustomerLoginApplication.class)
@AutoConfigureMockMvc
public class TokenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * 密码授权模式获取令牌
     *
     * @throws Exception
     */
   // @Test
    public void getToken() throws Exception {

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("username", Collections.singletonList("glen"));
        map.put("password", Collections.singletonList("123456"));
        map.put("grant_type", Collections.singletonList("password"));
        map.put("scope", Collections.singletonList("service"));
        int status = this.mockMvc.perform(
                post("http://localhost:8085/oauth/token").header("Authorization", "Basic " + Base64.getEncoder().encodeToString("user-service:123456".getBytes()))
                        .params(map)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print()).andReturn().getResponse().getStatus();
        switch (status) {
            case HttpStatus.SC_OK:
                log.info("密码授权模式获取令牌---------------->成功（200）");
                break;
            case HttpStatus.SC_UNAUTHORIZED:
                log.info("密码授权模式获取令牌---------------->失败（401---没有权限，请检查验证信息，账号是否存在、客户端信息）");
                break;
            case HttpStatus.SC_BAD_REQUEST:
                log.info("密码授权模式获取令牌---------------->失败（400---请求失败，请检查密码是否正确）");
                break;
            default:
                log.info("密码授权模式获取令牌---------------->失败（{}---未知结果）", status);
                break;
        }
        Assert.assertEquals(status, HttpStatus.SC_OK);
    }

}