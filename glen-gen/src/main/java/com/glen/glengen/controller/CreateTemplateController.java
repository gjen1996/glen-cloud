package com.glen.glengen.controller;

import com.alibaba.fastjson.JSONObject;
import com.glen.glencommonsystem.util.R;
import com.glen.glengen.dao.CreateTemplateDao;
import com.glen.glengen.entity.SysUserEntity;
import com.glen.glengen.service.CreateTemplateService;
import com.glen.glengen.service.ISysUserService;
import com.glen.glengen.service.impl.SysUserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import com.glen.glengen.dao.CreateTemplateDao;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CreateTemplateController {
    @Autowired
    private CreateTemplateService createTemplateServicee;
    @Autowired
    private  CreateTemplateDao createTemplateDao;

    @ResponseBody
    @RequestMapping(value = "/createTable", method = RequestMethod.POST)
    public R createDir(@RequestBody JSONObject params) throws IOException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        return createTemplateServicee.createTable(params);
    }
    @ResponseBody
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public void  findAndDeleteUser() {
        ISysUserService ser = new SysUserServiceImpl();
        String id = "1";
        SysUserEntity user = ser.findById(id);
        log.info("user:"+user);
    }
    @ResponseBody
    @RequestMapping(value = "/createTables", method = RequestMethod.POST)
    public R createTables(@RequestBody JSONObject params) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        return createTemplateDao.createTables(params);
    }
}
