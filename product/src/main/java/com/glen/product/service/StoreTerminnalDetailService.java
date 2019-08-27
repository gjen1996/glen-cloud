package com.glen.product.service;/**
 * @author Glen
 * @create 2019- 06-2019/6/24-17:04
 * @Description
 */

import com.glen.product.service.impl.StoreTerminnalDetailServiceImplFallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author Glen
 * @create 2019/6/24 17:04 
 * @Description
 */
@FeignClient(value="terminnal", fallback = StoreTerminnalDetailServiceImplFallback.class)
public interface StoreTerminnalDetailService {
    @RequestMapping("/terminnal/changeProduct/getStoreTerminalByIccid/{iccid}")
    public Map<String,Object> getStoreTerminalByIccid(@PathVariable String iccid);
}