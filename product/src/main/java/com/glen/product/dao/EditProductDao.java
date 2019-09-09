package com.glen.product.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.glen.product.entity.EditProductEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface EditProductDao extends BaseMapper<EditProductEntity>{

	List<HashMap> getAllProduct(String userName);

	List<HashMap> getAllIccid();
	
	void insetOrUpdate(List<EditProductEntity> list);

	Map<String, Object> getIccid(String iccid);

}
