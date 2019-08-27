package com.glen.terminnal.controller;


import com.glen.terminnal.service.StoreTerminnalDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RequestMapping("/terminnal")
@RestController
@Slf4j
public class StoreTerminnalDetailController {
	@Autowired
	StoreTerminnalDetailService storeTerminnalDetailService;
	/** 获取登陆用户所有产品 */
	@GetMapping("/changeProduct/getStoreTerminalByIccid/{iccid}")
	public Map<String, Object> getStoreTerminalByIccid(@PathVariable String iccid) {
	    log.info("进入这里:"+iccid);
		log.info("getStoreTerminalByIccid:"+storeTerminnalDetailService.getStoreTerminalByIccid(iccid));
		storeTerminnalDetailService.getStoreTerminalByIccid(iccid);
		return storeTerminnalDetailService.getStoreTerminalByIccid(iccid);
	}
}