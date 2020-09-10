package com.glen.glengen.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.glen.glencommonsystem.util.R;
import com.glen.glengen.dao.CreateTemplateDao;
import com.glen.glengen.service.CreateTemplateService;
import com.glen.glengen.service.MkdirDirOperService;
import com.glen.glengen.templates.EntityTemplate;
import com.glen.glengen.util.FileOperationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author 盖玉成
 * @date 2020/9/8 16:29
 * @description 该类的作用
 */
@Slf4j
@Service
@Transactional
public class CreateTemplateServiceImpl implements CreateTemplateService {
    @Autowired
    private CreateTemplateDao createTemplateDao;
    @Autowired
    private MkdirDirOperService mkdirDirOperService;

    @Override
    public R createTable(JSONObject param) {
        String packageName = FileOperationUtil.packageNmae(param.getString("packageName"));
        log.info("packageName:"+packageName);
        StringBuffer fileUrl = new StringBuffer("C:/Users/80559/Desktop/");
        fileUrl.append(packageName);
        log.info("fileUrl:"+fileUrl);
        //创建文件
        String className = FileOperationUtil.className(param.getString("className"));
        mkdirDirOperService.CreateFile(fileUrl.toString(),className);
        param.put("fileUrl",fileUrl);
        param.put("className",className);
        EntityTemplate.EntityTemplateWriteFile(param);
       // createTemplateDao.createTable(param);
        return R.ok();
    }
}
