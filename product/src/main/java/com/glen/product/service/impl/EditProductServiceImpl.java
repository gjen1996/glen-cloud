package com.glen.product.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.glen.product.dao.EditProductDao;
import com.glen.product.entity.EditProductEntity;
import com.glen.product.entity.ProductEntity;
import com.glen.product.methodservice.AppcustomerLoginService;
import com.glen.product.methodservice.StoreTerminnalDetailService;
import com.glen.product.service.EditProductService;
import com.glen.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;


@Service
@Slf4j
public class EditProductServiceImpl extends ServiceImpl<EditProductDao, EditProductEntity>
		implements EditProductService {
	@Autowired
    HttpServletRequest request;
	@Autowired
	ProductService productService;
	@Autowired
	EditProductDao editProductDao;
	@Autowired
	StoreTerminnalDetailService storeTerminnalDetailService;
	@Autowired
	AppcustomerLoginService appcustomerLoginService;
	/** 获取登陆用户所有产品 */
	@Override
	public List<HashMap> getAllProduct() {
		Map<String,Object> getToken = appcustomerLoginService.getToken();
		log.info("us:"+getToken.get("username").toString());
		List<HashMap> product = editProductDao.getAllProduct(getToken.get("username").toString());
		return product;
	}

	/**
	 * 但张卡更换产品
	 * 
	 * @throws ParseException
	 */
	@Override
	public Map<String, String> editProduct(Map<String, Object> params) throws ParseException {
		String productId = (String) params.get("productId");
		String iccid = (String) params.get("iccid");

		String time = (String) params.get("time") + "-27" ;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-HH");
		Date eff =java.sql.Date.valueOf(time); 
		Calendar cal = Calendar.getInstance();
		cal.setTime(eff);
		cal.add(Calendar.MONTH, -1);
		Date effDate = cal.getTime();
		
		String submitNumber = (String) params.get("submitNumber");

		List<EditProductEntity> list = new ArrayList<>();
		Map<String, String> resultMap = new HashMap<>();
		
		EditProductEntity edit = judge(iccid,productId,effDate,submitNumber);
		if("exits".equals(edit.getUserId())) {
			//用户之前已变更为该产品
			resultMap.put("res", "exits");
			resultMap.put("nextProductName", edit.getNextProductId());
			String date = formatter.format(edit.getEffectiveDate());
			resultMap.put("effectiveDate", date.substring(0, 7));
			return resultMap;
		}else if("conflict".equals(edit.getUserId())) {
			//用户之前更改其他产品
			resultMap.put("res", "conflict");
			resultMap.put("nextProductName", edit.getNextProductId());
			String date = formatter.format(edit.getEffectiveDate());
			resultMap.put("effectiveDate", date.substring(0, 7));
			return resultMap;
		}else {
			list.add(edit);
			editProductDao.insetOrUpdate(list);
			resultMap.put("res", "success");
			return resultMap;
		}
	}
	
	//判断卡能否变更产品的公用方法
	public EditProductEntity judge(String iccid,String productId,Date effDate,String submitNumber) {
		EditProductEntity editProductEntity = new EditProductEntity();
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-HH");

		Map<String, Object> iccidMap = editProductDao.getIccid(iccid);
		String userId = (String) iccidMap.get("iccid");
		String userName = (String) iccidMap.get("user_name");
		String productID = (String) iccidMap.get("product_id");
		Date transferDate = (Date) iccidMap.get("transfer_date");
		
		Date cycleBegin = (Date) iccidMap.get("cycle_begin");
		if(cycleBegin == null) {
			cycleBegin = transferDate;
		}

		Map<String, String> resultMap = new HashMap<>();

		String nextProductId = (String) iccidMap.get("next_product_id");
		Date effective = (Date) iccidMap.get("effective_date");		
		if (nextProductId == null || effective == null) {
				editProductEntity.setUserId(userId);
				editProductEntity.setUserName(userName);
				editProductEntity.setIccid(iccid);
				editProductEntity.setProductId(productID);
				editProductEntity.setNextProductId(productId);
				editProductEntity.setCycleBegin(cycleBegin);
				editProductEntity.setEffectiveDate(effDate);
				return editProductEntity;
		} else {
			//由于计费周期从上月27,数据库存储为上月27号,月份加一
			Calendar ct=Calendar.getInstance();
			ct.setTime(effective);
			ct.add(Calendar.MONTH, +1);
			Date effectiveDate = ct.getTime();
			
			if (productId.equals(nextProductId)) {
				if ("1".equals(submitNumber)) {
					//用户之前已变更为该产品
					resultMap.put("res", "exits");

					EntityWrapper<ProductEntity> productEw = new EntityWrapper<>();
					productEw.eq("product_id", nextProductId);
					String productName = productService.selectOne(productEw).getProductName();
					
					editProductEntity.setUserId("exits");					
					editProductEntity.setNextProductId(productName);
					editProductEntity.setEffectiveDate(effectiveDate);
					return editProductEntity;
				} else {
					//前端提示后，用户选择更改变更时间，不用判断通信计划，直接存入
					editProductEntity.setUserId(userId);
					editProductEntity.setUserName(userName);
					editProductEntity.setIccid(iccid);
					editProductEntity.setProductId(productID);
					editProductEntity.setNextProductId(productId);
					editProductEntity.setCycleBegin(cycleBegin);
					editProductEntity.setEffectiveDate(effDate);
					return editProductEntity;
				}
			} else {
				if ("1".equals(submitNumber)) {
					//用户之前已变更为其他的产品
					resultMap.put("res", "conflict");

					EntityWrapper<ProductEntity> productEw = new EntityWrapper<>();
					productEw.eq("product_id", nextProductId);
					String productName = productService.selectOne(productEw).getProductName();
					
					editProductEntity.setUserId("conflict");
					editProductEntity.setNextProductId(productName);
					editProductEntity.setEffectiveDate(effectiveDate);
					return editProductEntity;
				} else {
					//用户选择变更为当前选择的产品
						editProductEntity.setUserId(userId);
						editProductEntity.setUserName(userName);
						editProductEntity.setIccid(iccid);
						editProductEntity.setProductId(productID);
						editProductEntity.setNextProductId(productId);
						editProductEntity.setCycleBegin(cycleBegin);
						editProductEntity.setEffectiveDate(effDate);
						return editProductEntity;
				}
			}

		}
		
	}
	
	
	
	

	/** 根据用户选择每月更新 */
	@Override
	public boolean update() {
		List<EditProductEntity> list = new ArrayList<>();

//		YearMonth  month =  YearMonth.now();
//		String changeDay = month.toString() + "-27";

		LocalDate today = LocalDate.now();

		List<HashMap> iccidList = editProductDao.getAllIccid();
		System.out.println("共有" + iccidList.size() + "个产品记录");
		for (int i = 0; i < iccidList.size(); i++) {
			EditProductEntity editProductEntity = new EditProductEntity();

			String iccid = (String) iccidList.get(i).get("iccid");
			String productId = (String) iccidList.get(i).get("product_id");
			String userId = (String) iccidList.get(i).get("user_id");
			String userName = (String) iccidList.get(i).get("user_name");
			Date transferDate = (Date) iccidList.get(i).get("transfer_date");
			String nextProductId = (String) iccidList.get(i).get("next_product_id");
			Date effectiveDate = (Date) iccidList.get(i).get("effective_date");
			JSONObject data = null;
			data.put("iccid",iccid);
			data.put("productId",productId);
			data.put("userId",userId);
			data.put("userName",userName);
			data.put("transferDate",transferDate);
			data.put("nextProductId",nextProductId);
			data.put("effectiveDate",effectiveDate);
//			String communicationPlan = (String)iccidList.get(i).get("communication_plan");

			Date cycleBegin = (Date) iccidList.get(i).get("cycle_begin");
			if (cycleBegin == null) {
				cycleBegin = transferDate;
			}
			if (nextProductId == null || effectiveDate == null) {
				// 卡为新划拨或未发送产品变更的卡
				editProductEntity.setCycleBegin(cycleBegin);
				editProductEntity.setProductId(productId);
				editProductEntity.setNextProductId(null);
				editProductEntity.setEffectiveDate(null);
			} else {

				if (effectiveDate.toString().equals(today.toString())) {
					//本月变更产品,判断通信计划
//					EntityWrapper<ProductEntity> proEw = new EntityWrapper<>();
//					proEw.eq("product_id", productId);
//					String comPlan = productService.selectOne(proEw).getCommunicationPlan();
//					if (comPlan.equals(communicationPlan)) {
					//通信计划相同
					editProductEntity.setProductId(nextProductId);
					editProductEntity.setCycleBegin(new Date());
					editProductEntity.setNextProductId(null);
					editProductEntity.setEffectiveDate(null);

					//更换设备表中的产品ID
					storeTerminnalDetailService.updateById(data);
//					}else {
//						//通信计划不同,更改通信计划
//					}
				} else {
					editProductEntity.setCycleBegin(cycleBegin);
					editProductEntity.setProductId(productId);
					editProductEntity.setNextProductId(nextProductId);
					editProductEntity.setEffectiveDate(effectiveDate);
				}
			}

			editProductEntity.setIccid(iccid);
			editProductEntity.setUserId(userId);
			editProductEntity.setUserName(userName);
			list.add(editProductEntity);
		}

		try {
			List<List<EditProductEntity>> insertList = splitList(list, 1000);
			for (List<EditProductEntity> iccid : insertList) {
				editProductDao.insetOrUpdate(iccid);
			}
		} catch (Exception e) {
			System.out.println("无产品变更记录\"数据插入product_edit_record表");
		}
		return true;
	}

	// list分片，批量插入分批提交
	public List<List<EditProductEntity>> splitList(List<EditProductEntity> list, int len) {
		if (list == null || list.size() == 0 || len < 1) {
			return null;
		}
		List<List<EditProductEntity>> result = new ArrayList<List<EditProductEntity>>();
		int size = list.size();
		int count = (size + len - 1) / len;
		for (int i = 0; i < count; i++) {
			List<EditProductEntity> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
			result.add(subList);
		}
		return result;
	}

	
	/**
	 * 多张卡批量更换产品
	 * 
	 * @throws ParseException
	 */
	@Override
	public Map<String, String> editProductMulti(String[] iccid,String productId,String time,String submitNumber)  throws ParseException {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-HH");
		
		String t = time + "-27";
		Date eff =//formatter.parse(t);
			java.sql.Date.valueOf(t); 
		Calendar cal = Calendar.getInstance();
		cal.setTime(eff);
		cal.add(Calendar.MONTH, -1);
		Date effDate = cal.getTime();		
		
		List<EditProductEntity> list = new ArrayList<>();
		Map<String, String> resultMap = new HashMap<>();
		
		int exitsLength = 0;
		String exitsIccidString = "";
		
		int conflictLength = 0;
		String conflictIccidString = "";
		for(int i =0;i<iccid.length;i++) {			
			
			EditProductEntity edit = judge(iccid[i],productId,effDate,submitNumber);
			if("exits".equals(edit.getUserId())) {
				//该卡用户之前已变更为该产品
				exitsLength++;
				
				Date date = edit.getEffectiveDate();
				String d = formatter.format(date);
				exitsIccidString = exitsIccidString+iccid[i] + ",生效时间为："+d.substring(0, 7)+",";
			}else if("conflict".equals(edit.getUserId())) {
				//该卡用户之前更改其他产品
				conflictLength++;
				
				conflictIccidString = conflictIccidString+iccid[i] + ",变更产品为：" + edit.getNextProductId() + ",";
				
			}else {
				list.add(edit);
			}
			
		}
		
		if(exitsLength>0 && conflictLength == 0) {
			resultMap.put("res", "exits");
			resultMap.put("iccidList", exitsIccidString);
			return resultMap;
			
		}else if(exitsLength == 0 && conflictLength > 0) {
			resultMap.put("res", "conflict");
			resultMap.put("iccidList", conflictIccidString);
			return resultMap;
			
		} if(exitsLength>0 && conflictLength>0) {
			resultMap.put("res", "exitsOrconflict");
			resultMap.put("exitsIccidString",exitsIccidString);
			resultMap.put("conflictIccidString",conflictIccidString);
			return resultMap;
		}else {
			editProductDao.insetOrUpdate(list);
			resultMap.put("res", "success");
			return resultMap;
		}		
	}

	

}
