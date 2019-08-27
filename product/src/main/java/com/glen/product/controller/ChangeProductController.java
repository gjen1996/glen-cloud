package com.glen.product.controller;

import com.glen.product.service.ChangeProductService;
import com.glen.product.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequestMapping("/product")
@RestController
@Slf4j
public class ChangeProductController {
	@Autowired
	ChangeProductService changeProductService;
	/** 获取登陆用户所有产品 */
	@RequestMapping("/changeProduct/{iccid}")
	public String changeProduct(@PathVariable String iccid) {
		log.info("进入这里");
//		String sharingMode = (String) params.get("sharingMode");
//		String newProductId = (String) params.get("newProductId");
//		String  iccids = (String) params.get("iccids");
		String sharingMode = "pro";
		String newProductId = "19960111";
		String  iccids = iccid;
		String[] iccidList =  iccids.split(",");
		boolean code  =true;
		Map<String,Object> map = new HashMap<String, Object>();
		List<String> list = new ArrayList<>();
		list = changeProductService.changePrePayToPrePay(newProductId,iccidList);
		map.put("code", code);
		map.put("errorList", list);
		return "success";
	}

}