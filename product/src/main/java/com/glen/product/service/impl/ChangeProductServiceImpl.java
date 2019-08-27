package com.glen.product.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.glen.product.entity.ChangeProductEntity;
import com.glen.product.dao.ChangeProductDao;
import com.glen.product.service.ChangeProductService;
import com.glen.product.service.StoreTerminnalDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class ChangeProductServiceImpl extends ServiceImpl<ChangeProductDao, ChangeProductEntity>
		implements ChangeProductService {

	@Autowired
	private StoreTerminnalDetailService storeTerminnalDetailService;
	//预付到预付
	@Override
	public List<String> changePrePayToPrePay(String newProductId,String[] iccids) {
		List<String> errorList = new ArrayList<>();
		for(int i =0;i<iccids.length;i++){
			String iccid= iccids[i];
		//2.获取卡
        log.info("iccic"+iccid);
		Map<String,Object> storeTerminnalDetailInfo = storeTerminnalDetailService.getStoreTerminalByIccid(iccid);
		log.info("storeTerminnalDetailInfo:"+storeTerminnalDetailInfo);
		boolean flag = this.updateChangeProduct(storeTerminnalDetailInfo,storeTerminnalDetailInfo.get("productId").toString(),newProductId);
		if(flag==false){
			errorList.add(iccid);
		}
		}
		return errorList;
	}
	/**
	 * 插入或更新，一条次月生效日志
	 * @param productId
	 * */
	public boolean updateChangeProduct(Map<String,Object>storeTerminnalDetailInfo,String productId ,String newProductId){
		String cycleMonth = this.getYearAndMonth();
		String iccid =storeTerminnalDetailInfo.get("iccid").toString();
		String username = storeTerminnalDetailInfo.get("username").toString();
		String userId = storeTerminnalDetailInfo.get("userId").toString();
		String accountId= storeTerminnalDetailInfo.get("accountId").toString();
		//获取ChangeProductEntity
		EntityWrapper<ChangeProductEntity> ew  = new EntityWrapper<>();
		ew.andNew().eq("iccid", iccid).eq("cycle_month",cycleMonth).eq("is_update", 0);
		ChangeProductEntity changeProductEntity = this.selectOne(ew);
		if(changeProductEntity==null){
			changeProductEntity = new ChangeProductEntity();
		}
		changeProductEntity.setIccid(iccid);
		changeProductEntity.setNewProductId(newProductId);
		changeProductEntity.setAccountId(accountId);
		changeProductEntity.setUserId(userId);
		changeProductEntity.setUsername(username);
		changeProductEntity.setProductId(productId);
		changeProductEntity.setIsUpdate(0);
		changeProductEntity.setCycleMonth(cycleMonth);;
		changeProductEntity.setCreateDate(new Date());
		changeProductEntity.setEffectiveDate(this.getYearMonthAndDay());
		this.insertOrUpdate(changeProductEntity);
		return true;
	}
	public String getYearAndMonth(){
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);//获取年份
		int month=cal.get(Calendar.MONTH);//获取月份
		month+=1;
		int day=cal.get(Calendar.DATE);//获取日
		String stringMonth ="";
		if(day>26){
			month+=1;
		}
		if(month>12){
			year+=1;
		}
		if(month<10){
			stringMonth="0"+month;
		}
		return year+"-"+stringMonth;
	}

	public Date getYearMonthAndDay(){
		Date cycle = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String billingCycle = sdf.format(cycle) + "-27";
		Date result = null;
		try {
			result = simpleDateFormat.parse(billingCycle);
		} catch (Exception e) {
			System.out.println("时间获取失败");
		}
		return result;
	}

}
