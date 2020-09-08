package com.glen.glengen.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.glen.glencommonsystem.util.R;
import com.glen.glengen.dao.MappingDao;
import com.glen.glengen.dao.impl.MappingDaoImpl;
import com.glen.glengen.service.MappingService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 盖玉成
 * @date 2020/9/8 16:29
 * @description 该类的作用
 */
@Slf4j
public class MappingServiceImpl implements MappingService {

    @Override
    public R createTable(JSONObject param){
        log.info("MappingServiceImpl");
        MappingDao mappingDao =new MappingDaoImpl();
        mappingDao.createTable(param);
        return R.ok();
    }
}
