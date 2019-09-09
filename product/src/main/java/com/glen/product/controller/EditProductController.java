package com.glen.product.controller;

import com.alibaba.fastjson.JSONObject;
import com.glen.product.methodservice.AppcustomerLoginService;
import com.glen.product.service.EditProductService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/product")
public class EditProductController {
	@Autowired
	EditProductService editProductService;

	/** 获取登陆用户所有产品 */
	@RequestMapping("/allProduct")
	@ResponseBody
	public JSONArray getAllProduct() {
		List<HashMap> allProduct = editProductService.getAllProduct();
		JSONArray result = JSONArray.fromObject(allProduct);
		return result;		
	}
	
	/** 单卡更换产品 */
	@RequestMapping("/EditProduct")
	@ResponseBody
	public JSONArray editProduct(@RequestParam Map<String, Object> params) throws ParseException{
		Map<String,String> res = editProductService.editProduct(params);
		//JSONArray result = JSONArray.fromObject(res);
		JSONArray result = JSONArray.fromObject(res); 
		return result;		
	}
	
	
	/** 多张卡批量更换产品 */
	@RequestMapping("/EditProductMulti")
	@ResponseBody
	public JSONArray editProductMulti(String[] iccid,String productId,String time,String submitNumber) throws ParseException{
		Map<String,String> res = editProductService.editProductMulti(iccid,productId,time,submitNumber);
		JSONArray result = JSONArray.fromObject(res);
		return result;		
	}


}
