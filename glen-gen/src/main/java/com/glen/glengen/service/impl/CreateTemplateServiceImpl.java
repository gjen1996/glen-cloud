package com.glen.glengen.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.glen.glencommonsystem.util.R;
import com.glen.glengen.dao.CreateTemplateDao;
import com.glen.glengen.service.CreateTemplateService;
import com.glen.glengen.templates.EntityTemplate;
import com.glen.glengen.util.CopyDirOpeUtil;
import com.glen.glengen.util.FileOperationUtil;
import com.glen.glengen.util.MkdirDirOpeUtil;
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

    @Override
    public R createTable(JSONObject param) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        String packageName = FileOperationUtil.packageNmae(param.getString("packageName"));
        log.info("packageName:"+packageName);
        StringBuffer fileUrl = new StringBuffer("C:/Users/80559/Desktop/");
        fileUrl.append(packageName);
        log.info("fileUrl:"+fileUrl);
        //创建文件
        String classFileName = FileOperationUtil.className(param.getString("className"),false);
        MkdirDirOpeUtil.createFile(fileUrl.toString(),classFileName);
        param.put("fileUrl",fileUrl.toString());
        param.put("classNameStand",FileOperationUtil.className(param.getString("className"),true));
        param.put("classFileName",classFileName);
        EntityTemplate.EntityTemplateWriteFile(param);
        CopyDirOpeUtil.moveFile(fileUrl.toString(),param.getString("endPath"),classFileName);
        createTemplateDao.createTables(param);
        return R.ok();
    }
}
