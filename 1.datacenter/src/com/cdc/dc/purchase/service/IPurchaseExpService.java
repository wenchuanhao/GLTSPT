package com.cdc.dc.purchase.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cdc.dc.purchase.form.PurchaseForm;
import com.trustel.common.ItemPage;

public interface IPurchaseExpService {

	public List<Map<String, Object>> queryRecPurchase(PurchaseForm purchaseForm) throws Exception;

	public List<Map<String, Object>> queryMonPurchase(String month,PurchaseForm purchaseForm) throws Exception;

	public List<Map<String, Object>> queryPerPurchase(PurchaseForm purchaseForm);

	public List<Map<String, Object>> queryTqcPurchase(PurchaseForm purchaseForm);
	
	public List<Map<String, Object>> queryConTimePurchase(PurchaseForm purchaseForm);
	
	public List<Map<String, Object>> queryRevTimePurchase(PurchaseForm purchaseForm);
	
	public List<Map<String, Object>> queryApprTimePurchase(PurchaseForm purchaseForm);
	
	public List<Map<String, Object>> queryTemTimePurchase(PurchaseForm purchaseForm);
	
	public List<Map<String, Object>> queryBidTimePurchase(PurchaseForm purchaseForm);
	
	public List<Map<String, Object>> queryOnlTimePurchase(PurchaseForm purchaseForm);
	
	public List<Map<String, Object>> queryCosTimePurchase(PurchaseForm purchaseForm);

	public List<Map<String, Object>> queryOnlChartPurchase(PurchaseForm purchaseForm);

	public void purchaseAll(HttpServletRequest request, PurchaseForm purchaseForm) throws ParseException;
 
	public List<Object[]> getChartData(String type,PurchaseForm purchaseForm);

	public List<Object[]> getColumnData(String type, PurchaseForm purchaseForm);


}
