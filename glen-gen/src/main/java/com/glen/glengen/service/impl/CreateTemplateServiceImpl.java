package com.glen.glengen.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.glen.glencommonsystem.util.R;
import com.glen.glengen.compiler.JdkCompiler;
import com.glen.glengen.dao.CreateTemplateDao;
import com.glen.glengen.hibernate.MysqlConnectionManager;
import com.glen.glengen.hibernate.ResultHandler;
import com.glen.glengen.hibernate.SqlExecutor;
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
 * @author ???
 * @date 2020/9/8 16:29
 * @description ?????
 */
@Slf4j
@Service
@Transactional
public class CreateTemplateServiceImpl implements CreateTemplateService {
    @Autowired
    private CreateTemplateDao createTemplateDao;
    String SOURCE_CODE =null;
    @Override
    public R createTables(JSONObject param) throws Exception {
        String packageName = FileOperationUtil.packageNmae(param.getString("packageName"));
        log.info("packageName:"+packageName);
        StringBuffer fileUrl = new StringBuffer(param.getString("startFileUrl"));
        fileUrl.append(packageName);
        log.info("fileUrl:"+fileUrl);
        //格式化文件
        String classFileName = FileOperationUtil.className(param.getString("className"),false);
        //创建文件
        MkdirDirOpeUtil.createFile(fileUrl.toString(),classFileName);
        param.put("fileUrl",fileUrl.toString());
        param.put("classNameStand",FileOperationUtil.className(param.getString("className"),true));
        param.put("classFileName",classFileName);
        SOURCE_CODE = EntityTemplate.EntityTemplateWriteFile(param).getString("SOURCE_CODE");
        log.info("SOURCE_CODE:"+SOURCE_CODE);
        log.info("z这个是最终数据："+param);
        //移动文件
        CopyDirOpeUtil.moveFile(fileUrl.toString(),param.getString("endPath"),classFileName);
        //创建文件
        MkdirDirOpeUtil.createFile(fileUrl.toString(),classFileName);
        //开始动态编译
        JdkCompiler.compile(
                param.getString("packageName"),
                FileOperationUtil.className(param.getString("className"),true),
                SOURCE_CODE,
                null,
                null);
        //这是一个测试例子
//        JdkCompiler.compile(
//                param.getString("packageName"),
//                FileOperationUtil.className(param.getString("className"),true),
//                SOURCE_CODE,
//                new Class[]{MysqlConnectionManager.class, SqlExecutor.class, ResultHandler.class, String.class},
//                new Object[]{MysqlConnectionManager.X, SqlExecutor.X, ResultHandler.X, null});
       // CompilerUtil.compilerSecondTpye(param.getString("endPath")+classFileName);
        //进行数据存取
        createTemplateDao.createTables(param);

        return R.ok();
    }
}
