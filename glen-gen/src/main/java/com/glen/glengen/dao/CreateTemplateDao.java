package com.glen.glengen.dao;

import com.alibaba.fastjson.JSONObject;
import com.glen.glencommonsystem.util.R;


public interface CreateTemplateDao {
    R createTable(JSONObject r);
    <T> R createTables(JSONObject r) throws ClassNotFoundException, IllegalAccessException, InstantiationException;
}