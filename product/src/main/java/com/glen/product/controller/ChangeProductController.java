package com.glen.product.controller;

import com.alibaba.fastjson.JSONObject;
import com.glen.product.dao.ChangeProductDao;
import com.glen.product.service.ChangeProductService;
import com.glen.product.utils.PageUtils;
import com.glen.product.utils.R;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
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
	@Autowired
	ChangeProductDao changeProductDao;
	/** 获取登陆用户所有产品 */
	@RequestMapping("/changeProduct")
	@ResponseBody
	public Map<String,Object> changeProduct(@RequestBody JSONObject data) {
		String sharingMode = data.getString("sharingMode");
		String newProductId = data.getString("newProductId");
		String iccids = data.getString("iccid");
		log.info("进入这里:"+sharingMode+"---"+newProductId+"---"+iccids);
		String result;
		String[] iccidList = iccids.split(",");
		boolean code = true;
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> list = new ArrayList<>();
		switch (sharingMode) {
			case "prePay":
				list = changeProductService.changePrePayToPrePay(newProductId,iccidList);
				break;
			case "monthPay":
				list = changeProductService.changeMonthPayToMonthPay(newProductId,iccidList);
				break;
			case "monthPayShare":
				list = changeProductService.changeMonthPayShareToMonthPayShare(newProductId,iccidList);
				break;
			case "nextMonthPrePay":
				list = changeProductService.changeNextMonthPrePayToNextMonthPrePay(newProductId,iccidList);
				break;
			default:
				break;
		}
		if (list==null||list.isEmpty()||list.size()==0) {
			map.put("code", code);
			map.put("errorList", list);
		} else {
			map.put("code", false);
			map.put("errorList", list);
		}
		return map;
	}
	/** 获取登陆用户可变更的产品 */
	@RequestMapping("/selectProductList")
	@ResponseBody
	public JSONArray selectProductList(@RequestBody JSONObject data) {
		String sharingMode = data.getString("sharingMode");
		String username = data.getString("username");
		List<Map<String,Object>> selectProductList = changeProductDao.getSelectProductType(sharingMode,username);
		JSONArray result = JSONArray.fromObject(selectProductList);
		return result;
	}
	//页面展示
//	@RequestMapping("/EditProductQueryPage")
//	@ResponseBody
//	public R queryPendingOrder(@RequestParam Map<String, Object> params) throws Exception {
//		PageUtils page = changeProductService.queryPage(params);
//		return R.ok().put("page", page);
//	}
}