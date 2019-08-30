package com.glen.terminnal.controller;


import com.alibaba.fastjson.JSONObject;
import com.glen.terminnal.dao.StoreTerminnalDetailDao;
import com.glen.terminnal.entity.StoreTerminnalDetailEntity;
import com.glen.terminnal.service.StoreTerminnalDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/terminnal")
@RestController
@Slf4j
public class StoreTerminnalDetailController {
	@Autowired
	StoreTerminnalDetailService storeTerminnalDetailService;
	@Autowired
	StoreTerminnalDetailDao storeTerminnalDetailDao;
	/** 获取登陆用户所有产品 */
	@GetMapping("/changeProduct/getStoreTerminalByIccid/{iccid}")
	public StoreTerminnalDetailEntity getStoreTerminalByIccid(@PathVariable String iccid) {
	    log.info("进入这里:"+iccid);
		log.info("getStoreTerminalByIccid:"+storeTerminnalDetailService.getStoreTerminalByIccid(iccid));
		storeTerminnalDetailService.getStoreTerminalByIccid(iccid);
		return storeTerminnalDetailService.getStoreTerminalByIccid(iccid);
	}

	@RequestMapping("/changeProduct/updateAllColumnById")
	public Integer updateAllColumnById(@RequestBody JSONObject data) {
		String newProductId = data.getString("newProductId");
		StoreTerminnalDetailEntity storeTerminnalDetailInfo = new StoreTerminnalDetailEntity();
		storeTerminnalDetailInfo.setProductId(newProductId);
		log.info("进入这里:"+newProductId+"---"+storeTerminnalDetailDao.updateAllColumnById(storeTerminnalDetailInfo));
		return storeTerminnalDetailDao.updateAllColumnById(storeTerminnalDetailInfo);
	}
}