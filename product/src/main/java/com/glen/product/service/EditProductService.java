package com.glen.product.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.IService;
import com.glen.product.entity.EditProductEntity;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface EditProductService extends IService<EditProductEntity>{

	List<HashMap> getAllProduct();

	Map<String,String> editProduct(Map<String, Object> params) throws ParseException;
	
	boolean update();

	Map<String, String> editProductMulti(String[] iccid, String productId, String time, String submitNumber)  throws ParseException ;
	
}
