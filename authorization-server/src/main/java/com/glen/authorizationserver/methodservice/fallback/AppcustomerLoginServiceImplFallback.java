package com.glen.authorizationserver.methodservice.fallback;

import com.glen.authorizationserver.entity.SysUserEntity;
import com.glen.authorizationserver.methodservice.AppcustomerLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * @author Glen
 * @create 2019/6/26 10:25 
 * @Description
 */
@Component
@Slf4j
public class AppcustomerLoginServiceImplFallback implements AppcustomerLoginService {
/**
  * @author Glen
  * @date 2019/8/29 16:13
  * @Description
  */
    @Override
    public SysUserEntity findByUsername(@PathVariable String username){
        log.info("findByUsername方法异常");
        return null;
    }
}
