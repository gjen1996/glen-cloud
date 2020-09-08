package com.glen.glengen.controller;

import com.alibaba.fastjson.JSONObject;
import com.glen.glencommonsystem.util.R;
import com.glen.glengen.dao.MappingDao;
import com.glen.glengen.dao.impl.MappingDaoImpl;
import com.glen.glengen.entity.SysUserEntity;
import com.glen.glengen.service.ISysUserService;
import com.glen.glengen.service.MappingService;
import com.glen.glengen.service.impl.MappingServiceImpl;
import com.glen.glengen.service.impl.SysUserServiceImpl;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author 盖玉成
 * @date 2020/9/8 10:08
 * @description 实体类映射数据库表
 */

@RestController
@RequestMapping("/api")
@Slf4j
public class MappingController {
    @ResponseBody
    @RequestMapping(value = "/createTable", method = RequestMethod.POST)
    public R createTable(@RequestBody JSONObject params) throws IOException {
        MappingService mappingService = new MappingServiceImpl();
        return mappingService.createTable(params);
    }
    @ResponseBody
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public void  findAndDeleteUser() {
        ISysUserService ser = new SysUserServiceImpl();
        String id = "1";
        SysUserEntity user = ser.findById(id);
        log.info("user:"+user);
    }
}
