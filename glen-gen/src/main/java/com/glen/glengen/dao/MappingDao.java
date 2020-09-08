package com.glen.glengen.dao;

import com.alibaba.fastjson.JSONObject;
import com.glen.glencommonsystem.util.R;


public interface MappingDao{
    R createTable(JSONObject r);

}