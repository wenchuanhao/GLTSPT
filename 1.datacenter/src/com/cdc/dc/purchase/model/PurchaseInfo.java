package com.cdc.dc.purchase.model;
// default package

import java.util.Date;

import org.trustel.service.form.PageQueryForm;

import com.cdc.dc.purchase.service.IPurchaseService;
import com.cdc.system.core.util.SpringHelper;


/**
 * Purchase entity. @author MyEclipse Persistence Tools
 */

public class PurchaseInfo  extends PageQueryForm  implements java.io.Serializable {


    // Fields    

     private String id;
     private String units = "NJ";
 	private Date createTime;
 	private Date updateTime;
     private String operator;
     private String columnA;
     private String columnB;
     private String columnC;
     private String columnD;
     private String columnE;
     private String columnF;
     private String columnG;
     private String columnH;
     private String columnI;
     private String columnJ;
     private String columnK;
     private String columnL;
     private String columnM;
     private String columnN;
     private String columnO;
     private String columnP;
     private String columnQ;
     private String columnR;
     private String columnS;
     private String columnT;
     private String columnU;
     private String columnV;
     private String columnW;
     private String columnX;
     private String columnY;
     private String columnZ;
     private String columnAa;
     private String columnAb;
     private String columnAc;
     private String columnAd;
     private String columnAe;
     private String columnAf;
     private String columnAg;
     private String columnAh;
     private String columnAi;
     private String columnAj;
     private String columnAk;
     private String columnAl;
     private String columnAm;
     private String columnAn;
     private String columnAo;
     private String columnAp;
     private String columnAq;
     private String columnAr;
     private String columnAs;
     private String columnAt;
     private String columnAu;
     private String columnAv;
     private String columnAw;
     private String columnAx;
     private String columnAy;
     private String columnAz;
     private String columnBa;
     private String columnBb;
     private String columnBc;
     private String columnBd;
     private String columnBe;
     private String columnBf;
     private String columnBg;
     private String columnBh;
     private String columnBi;
     private String columnBj;
     private String columnBk;
     private String columnBl;//是否取消
     private String columnBm;//取消原因
     private String columnBn;
     private String columnBo;
    

     private Date beginCreateTime;
     private Date endCreateTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getColumnA() {
		return columnA;
	}
	public void setColumnA(String columnA) {
		this.columnA = columnA;
	}
	public String getColumnB() {
		return columnB;
	}
	public void setColumnB(String columnB) {
		this.columnB = columnB;
	}
	public String getColumnC() {
		return columnC;
	}
	public void setColumnC(String columnC) {
		this.columnC = columnC;
	}
	public String getColumnD() {
		return columnD;
	}
	public void setColumnD(String columnD) {
		this.columnD = columnD;
	}
	public String getColumnE() {
		return columnE;
	}
	//是否计划内项目
	public void setColumnE(String columnE) {
		if("请选择".equals(columnE)) {
			columnE="";
		}
		this.columnE = columnE;
	}
	public String getColumnF() {
		return columnF;
	}
	public void setColumnF(String columnF) {
		if("请选择".equals(columnF)) {
			columnF="";
		}
		//去掉excel下拉框中的“1.‘
		if(columnF != null){
			columnF=columnF.substring(columnF.indexOf(".")+1);
			columnF = columnF.replace("+", "");
		}
		this.columnF = columnF;
	}
	public String getColumnG() {
		return columnG;
	}
	public void setColumnG(String columnG) {
		if("请选择".equals(columnG)) {
			columnG="";
		}
		if(columnG != null){
			columnG=columnG.substring(columnG.indexOf(".")+1);
		}
		this.columnG = columnG;
	}
	public String getColumnH() {
		return columnH;
	}
	public void setColumnH(String columnH) {
		this.columnH = columnH;
	}
	public String getColumnI() {
		return columnI;
	}
	public void setColumnI(String columnI) {
		if("请选择".equals(columnI)) {
			columnI="";
		}
		if(columnI != null){
			columnI=columnI.substring(columnI.indexOf(".")+1);
		}
		this.columnI = columnI;
	}
	public String getColumnJ() {
		return columnJ;
	}
	public void setColumnJ(String columnJ) {
		if("请选择".equals(columnJ)) {
			columnJ="";
		}
		if(columnJ != null){
			columnJ=columnJ.substring(columnJ.indexOf(".")+1).trim();
		}
		this.columnJ = columnJ;
	}
	public String getColumnK() {
		return columnK;
	}
	public void setColumnK(String columnK) {
		if("请选择".equals(columnK)) {
			columnK="";
		}
		
		this.columnK = columnK;
	}
	public String getColumnL() {
		if("请选择".equals(columnL)) {
			columnL="";
		}
		return columnL;
	}
	public void setColumnL(String columnL) {
		if(columnL != null){
		    columnL = columnL.trim();
		}
		this.columnL = columnL;
	}
	public String getColumnM() {
		return columnM;
	}
	public void setColumnM(String columnM) {
		this.columnM = columnM;
	}
	public String getColumnN() {
		return columnN;
	}
	public void setColumnN(String columnN) {
		this.columnN = columnN;
	}
	public String getColumnO() {
		return columnO;
	}
	public void setColumnO(String columnO) {
		this.columnO = columnO;
	}
	public String getColumnP() {
		return columnP;
	}
	public void setColumnP(String columnP) {
		this.columnP = columnP;
	}
	public String getColumnQ() {
		return columnQ;
	}
	public void setColumnQ(String columnQ) {
		this.columnQ = columnQ;
	}
	public String getColumnR() {
		return columnR;
	}
	public void setColumnR(String columnR) {
		this.columnR = columnR;
	}
	public String getColumnS() {
		return columnS;
	}
	public void setColumnS(String columnS) {
		this.columnS = columnS;
	}
	public String getColumnT() {
		return columnT;
	}
	public void setColumnT(String columnT) {
		this.columnT = columnT;
	}
	public String getColumnU() {
		return columnU;
	}
	public void setColumnU(String columnU) {
		this.columnU = columnU;
	}
	public String getColumnV() {
		return columnV;
	}
	public void setColumnV(String columnV) {
		this.columnV = columnV;
	}
	public String getColumnW() {
		return columnW;
	}
	public void setColumnW(String columnW) {
		this.columnW = columnW;
	}
	public String getColumnX() {
		return columnX;
	}
	public void setColumnX(String columnX) {
		this.columnX = columnX;
	}
	public String getColumnY() {
		return columnY;
	}
	public void setColumnY(String columnY) {
		this.columnY = columnY;
	}
	public String getColumnZ() {
		return columnZ;
	}
	public void setColumnZ(String columnZ) {
		this.columnZ = columnZ;
	}
	public String getColumnAa() {
		return columnAa;
	}
	public void setColumnAa(String columnAa) {
		this.columnAa = columnAa;
	}
	public String getColumnAb() {
		return columnAb;
	}
	public void setColumnAb(String columnAb) {
		this.columnAb = columnAb;
	}
	public String getColumnAc() {
		return columnAc;
	}
	public void setColumnAc(String columnAc) {
		if("请选择".equals(columnAc)) {
			columnAc="";
		}
		if(columnAc != null){
			columnAc=columnAc.substring(columnAc.indexOf("、")+1).trim();
		}
		this.columnAc = columnAc;
	}
	public String getColumnAd() {
		return columnAd;
	}
	public void setColumnAd(String columnAd) {
		this.columnAd = columnAd;
	}
	public String getColumnAe() {
		return columnAe;
	}
	public void setColumnAe(String columnAe) {
		if("请选择".equals(columnAe)) {
			columnAe="";
		}
		if(columnAe != null){
			columnAe=columnAe.substring(columnAe.indexOf(".")+1);
		}
		this.columnAe = columnAe;
	}
	public String getColumnAf() {
		return columnAf;
	}
	public void setColumnAf(String columnAf) {
		if("请选择".equals(columnAf)) {
			columnAf="";
		}
		if(columnAf != null){
			columnAf=columnAf.substring(columnAf.indexOf(".")+1);
		}
		this.columnAf = columnAf;
	}
	public String getColumnAg() {
		return columnAg;
	}
	public void setColumnAg(String columnAg) {
		this.columnAg = columnAg;
	}
	public String getColumnAh() {
		return columnAh;
	}
	public void setColumnAh(String columnAh) {
		if("请选择".equals(columnAh)) {
			columnAh = "";
		}
		if(columnAh != null){
			columnAh = columnAh.substring(columnAh.indexOf(".")+1);
		}
		this.columnAh = columnAh;
	}
	public String getColumnAi() {
		return columnAi;
	}
	public void setColumnAi(String columnAi) {
		if("请选择".equals(columnAi)) {
			columnAi="";
		}
		if(columnAi != null){
			columnAi = columnAi.substring(columnAi.indexOf(".")+1);
		}
		this.columnAi = columnAi;
	}
	public String getColumnAj() {
		return columnAj;
	}
	public void setColumnAj(String columnAj) {
		this.columnAj = columnAj;
	}
	public String getColumnAk() {
		return columnAk;
	}
	public void setColumnAk(String columnAk) {
		this.columnAk = columnAk;
	}
	public String getColumnAl() {
		return columnAl;
	}
	public void setColumnAl(String columnAl) {
		if("请选择".equals(columnAl)) {
			columnAl="";
		}
		if(columnAl != null){
			columnAl = columnAl.substring(columnAl.indexOf(".")+1);
			columnAl = columnAl.replace("(", "（");
			columnAl = columnAl.replace(")", "）");
			columnAl = columnAl.trim();
		}
		this.columnAl = columnAl;
	}
	public String getColumnAm() {
		return columnAm;
	}
	public void setColumnAm(String columnAm) {
		if("请选择".equals(columnAm)) {
			columnAm="";
		}
		this.columnAm = columnAm;
	}
	public String getColumnAn() {
		
		return columnAn;
	}
	public void setColumnAn(String columnAn) {
		if("请选择".equals(columnAn)) {
			columnAn="";
		}
		if(columnAn != null){
			columnAn = columnAn.substring(columnAn.indexOf(".")+1);
			columnAn = columnAn.replace("(", "（");
			columnAn = columnAn.replace(")", "）");
			columnAn = columnAn.replace(" ", "");
		}
		this.columnAn = columnAn;
	}
	public String getColumnAo() {
		return columnAo;
	}
	public void setColumnAo(String columnAo) {
		this.columnAo = columnAo;
	}
	public String getColumnAp() {
		return columnAp;
	}
	public void setColumnAp(String columnAp) {
		this.columnAp = columnAp;
	}
	public String getColumnAq() {
		return columnAq;
	}
	public void setColumnAq(String columnAq) {
		this.columnAq = columnAq;
	}
	public String getColumnAr() {
		return columnAr;
	}
	public void setColumnAr(String columnAr) {
		this.columnAr = columnAr;
	}
	public String getColumnAs() {
		return columnAs;
	}
	public void setColumnAs(String columnAs) {
		this.columnAs = columnAs;
	}
	public String getColumnAt() {
		return columnAt;
	}
	public void setColumnAt(String columnAt) {
		this.columnAt = columnAt;
	}
	public String getColumnAu() {
		return columnAu;
	}
	public void setColumnAu(String columnAu) {
		this.columnAu = columnAu;
	}
	public String getColumnAv() {
		return columnAv;
	}
	public void setColumnAv(String columnAv) {
		this.columnAv = columnAv;
	}
	public String getColumnAw() {
		return columnAw;
	}
	public void setColumnAw(String columnAw) {
		this.columnAw = columnAw;
	}
	public String getColumnAx() {
		return columnAx;
	}
	public void setColumnAx(String columnAx) {
		this.columnAx = columnAx;
	}
	public String getColumnAy() {
		return columnAy;
	}
	public void setColumnAy(String columnAy) {
		this.columnAy = columnAy;
	}
	public String getColumnAz() {
		return columnAz;
	}
	public void setColumnAz(String columnAz) {
		this.columnAz = columnAz;
	}
	public String getColumnBa() {
		return columnBa;
	}
	public void setColumnBa(String columnBa) {
		this.columnBa = columnBa;
	}
	public String getColumnBb() {
		return columnBb;
	}
	public void setColumnBb(String columnBb) {
		if("请选择".equals(columnBb)) {
			columnBb="";
		}
		if(columnBb != null){
			columnBb = columnBb.substring(columnBb.indexOf(".")+1);
		}
		this.columnBb = columnBb;
	}
	public String getColumnBc() {
		
		return columnBc;
	}
	public void setColumnBc(String columnBc) {
		if("请选择".equals(columnBc)) {
			columnBc="";
		}
		this.columnBc = columnBc;
	}
	public String getColumnBd() {
		return columnBd;
	}
	public void setColumnBd(String columnBd) {
		this.columnBd = columnBd;
	}
	public String getColumnBe() {
		return columnBe;
	}
	public void setColumnBe(String columnBe) {
		if("请选择".equals(columnBe)) {
			columnBe="";
		}
		if(columnBe !=null){
			columnBe = columnBe.trim();
		}
		this.columnBe = columnBe;
	}
	public String getColumnBf() {
		return columnBf;
	}
	public void setColumnBf(String columnBf) {
		if("请选择".equals(columnBf)) {
			columnBf="";
		}
		this.columnBf = columnBf;
	}
	public String getColumnBg() {
		return columnBg;
	}
	public void setColumnBg(String columnBg) {
		if("请选择".equals(columnBg)) {
			columnBg="";
		}
		this.columnBg = columnBg;
	}
	public String getColumnBh() {
		return columnBh;
	}
	public void setColumnBh(String columnBh) {
		this.columnBh = columnBh;
	}
	public String getColumnBi() {
		return columnBi;
	}
	public void setColumnBi(String columnBi) {
		if("请选择".equals(columnBi)) {
			columnBi="";
		}
		this.columnBi = columnBi;
	}
	public String getColumnBj() {
		return columnBj;
	}
	public void setColumnBj(String columnBj) {
		if("请选择".equals(columnBj)) {
			columnBj="";
		}
		this.columnBj = columnBj;
	}
	public String getColumnBk() {
		return columnBk;
	}
	public void setColumnBk(String columnBk) {
		this.columnBk = columnBk;
	}
	public String getColumnBl() {
		return columnBl;
	}
	public void setColumnBl(String columnBl) {
		this.columnBl = columnBl;
	}
	public String getColumnBm() {
		return columnBm;
	}
	public void setColumnBm(String columnBm) {
		this.columnBm = columnBm;
	}
	public String getColumnBn() {
		return columnBn;
	}
	public void setColumnBn(String columnBn) {
		if("请选择".equals(columnBn)) {
			columnBn="";
		}
		this.columnBn = columnBn;
	}
	public String getColumnBo() {
		return columnBo;
	}
	public void setColumnBo(String columnBo) {
		this.columnBo = columnBo;
	}
	public Date getBeginCreateTime() {
		return beginCreateTime;
	}
	public void setBeginCreateTime(Date beginCreateTime) {
		this.beginCreateTime = beginCreateTime;
	}
	public Date getEndCreateTime() {
		return endCreateTime;
	}
	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

	/**
	 * 获取采购时长
	 * （合同签署时间）减（50万以上方案汇报时间/50万以下需求部门提交方案呈批时间）
	 */
	public Long getPurTime(){
		IPurchaseService purchaseService = (IPurchaseService) SpringHelper.getBean("iPurchaseService");
		return purchaseService.queryTimeBetweenDate(this.columnQ,this.columnAs);
	}
    /**
     * 采购工作投入天数
     * （合同签署时间）减（项目启动时间）
     */
	public Long getPurDays(){
		IPurchaseService purchaseService = (IPurchaseService) SpringHelper.getBean("iPurchaseService");
		return purchaseService.queryTimeBetweenDate(this.columnO,this.columnAs);
	}
	/**
	 * 需求确认时长
	 */
	public Long getConfirmTime(){
		IPurchaseService purchaseService = (IPurchaseService) SpringHelper.getBean("iPurchaseService");
		return purchaseService.queryTimeBetweenDate(this.columnO,this.columnQ);
	}
	
	/**
	 * 需求确认完毕-评审时间（工作日）
	 */
	public Long getReviewDays(){
		IPurchaseService purchaseService = (IPurchaseService) SpringHelper.getBean("iPurchaseService");
		return purchaseService.queryTimeBetweenDate(this.columnQ,this.columnAb);
	}
}