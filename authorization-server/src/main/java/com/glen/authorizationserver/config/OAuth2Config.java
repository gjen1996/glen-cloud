package com.glen.authorizationserver.config;
/**
 * @author Glen
 * @create 2019- 06-2019/6/28-17:33
 * @Description
 */


import com.glen.authorizationserver.service.UserServiceDetail;
import org.springframework.http.HttpMethod;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

/**
 * @author Glen
 * @create 2019/6/28 17:33
 * @Description
 */
@Slf4j
@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {
//
//    @Value("${config.oauth2.privateKey}")
//    private String privateKey ;
//    @Value("${config.oauth2.publicKey}")
//    private String publicKey;
//
//    @Autowired
//    @Qualifier("authenticationManagerBean")
//    AuthenticationManager authenticationManager;
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Autowired
//    private TokenStore tokenStore;
//    @Autowired
//    private UserServiceDetail userServiceDetail;
//    @Autowired
//    private ClientDetailsService clientDetailsService;
//
//
//    @Bean
//    public TokenStore tokenStore() {
//        return new JwtTokenStore(jwtTokenEnhancer());
//    }
//
//    @Bean
//    public JwtAccessTokenConverter jwtTokenEnhancer() {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        //converter.setSigningKey("micosrv_signing_key");
//        log.info("jwtAccessTokenConverter privateKey ：" + privateKey);
//        converter.setSigningKey(privateKey);
//        converter.setVerifierKey(publicKey);
//        return converter;
//    }
//
//    @Bean // 声明 ClientDetails实现
//    public ClientDetailsService clientDetailsService() {
//        return new JdbcClientDetailsService(dataSource);
//    }
//
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
////       String finalSecret =new BCryptPasswordEncoder().encode("123456");
////        clients.inMemory()
////                // 配置一个客户端
////                .withClient("user-service")
////                .secret(finalSecret)
////                // 配置客户端的域
////                .scopes("all","read", "write")
////                // 配置验证类型为refresh_token和password
////                .authorizedGrantTypes("refresh_token","password")
////                // 配置token的过期时间为1h
////                .accessTokenValiditySeconds(3600 * 1000)
////                .authorizedGrantTypes("client_credentials")
////                .and()
////                .withClient("client_2")
////                .authorizedGrantTypes("password", "refresh_token")
////                .scopes("all","read", "write")
////                .accessTokenValiditySeconds(7200)
////                .refreshTokenValiditySeconds(10000)
////                .authorities("password")
////                .secret(finalSecret);
//
//        clients.withClientDetails(clientDetailsService);
//
//    }
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints.tokenStore(tokenStore).authenticationManager(authenticationManager)
//                .userDetailsService(userServiceDetail);
//
//        // 配置tokenServices参数
//        DefaultTokenServices tokenServices = new DefaultTokenServices();
//        tokenServices.setTokenStore(endpoints.getTokenStore());
//        tokenServices.setSupportRefreshToken(false);
//        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
//        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
//        tokenServices.setAccessTokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(30)); // 30天
//        endpoints.tokenServices(tokenServices);
//    }
//
//    @Override
//    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        // 允许表单认证
//        security.allowFormAuthenticationForClients().tokenKeyAccess("permitAll()")
//                .checkTokenAccess("isAuthenticated()");
//    }
@Autowired
AuthenticationManager authenticationManager;

    @Autowired
    RedisConnectionFactory redisConnectionFactory;
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        String finalSecret = "{bcrypt}" + new BCryptPasswordEncoder().encode("123456");

        // 配置两个客户端，一个用于password认证一个用于client认证
        clients.inMemory().withClient("client_1")
//                .resourceIds(Utils.RESOURCEIDS.ORDER)
                .authorizedGrantTypes("client_credentials", "refresh_token")
                .scopes("select")
                .authorities("oauth2")
                .secret(finalSecret)
                .and().withClient("client_2")
//                .resourceIds(Utils.RESOURCEIDS.ORDER)
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("server")
                .authorities("oauth2")
                .secret(finalSecret);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(new MyRedisTokenStore(redisConnectionFactory))
                .authenticationManager(authenticationManager)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 允许表单认证
        security.allowFormAuthenticationForClients().tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }
}

