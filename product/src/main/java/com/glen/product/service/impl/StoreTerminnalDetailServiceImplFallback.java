package com.glen.product.service.impl;

import com.glen.product.service.StoreTerminnalDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Glen
 * @create 2019/6/26 10:25 
 * @Description
 */
@Component
@Slf4j
public class StoreTerminnalDetailServiceImplFallback implements StoreTerminnalDetailService {
    @Override
    public Map<String,Object> getStoreTerminalByIccid(String iccid) {
       log.info("程序猿小哥哥，你的服务挂掉了，赶快去修bug");
       Map<String,Object> map= new HashMap<String,Object>();
       map.put("message","程序猿小哥哥，你的服务挂掉了，赶快去修bug。");
       return map;
    }
}
