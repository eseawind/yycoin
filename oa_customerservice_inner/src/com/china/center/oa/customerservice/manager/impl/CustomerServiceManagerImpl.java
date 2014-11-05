package com.china.center.oa.customerservice.manager.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.Workbook;
import jxl.format.PageOrientation;
import jxl.format.PaperSize;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.center.china.osgi.config.ConfigLoader;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.client.dao.StafferVSCustomerDAO;
import com.china.center.oa.client.vs.StafferVSCustomerBean;
import com.china.center.oa.customerservice.bean.FeedBackBean;
import com.china.center.oa.customerservice.bean.FeedBackCheckBean;
import com.china.center.oa.customerservice.bean.FeedBackDetailBean;
import com.china.center.oa.customerservice.bean.FeedBackVSOutBean;
import com.china.center.oa.customerservice.bean.FeedBackVisitBean;
import com.china.center.oa.customerservice.bean.FeedBackVisitItemBean;
import com.china.center.oa.customerservice.constant.CustomerServiceConstant;
import com.china.center.oa.customerservice.dao.FeedBackCheckDAO;
import com.china.center.oa.customerservice.dao.FeedBackDAO;
import com.china.center.oa.customerservice.dao.FeedBackDetailDAO;
import com.china.center.oa.customerservice.dao.FeedBackVSOutDAO;
import com.china.center.oa.customerservice.dao.FeedBackVisitDAO;
import com.china.center.oa.customerservice.dao.FeedBackVisitItemDAO;
import com.china.center.oa.customerservice.helper.CustomerServiceHelper;
import com.china.center.oa.customerservice.manager.CustomerServiceManager;
import com.china.center.oa.customerservice.vo.FeedBackVO;
import com.china.center.oa.finance.constant.FinanceConstant;
import com.china.center.oa.finance.dao.InBillDAO;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.manager.CommonMailManager;
import com.china.center.oa.sail.bean.BaseBalanceBean;
import com.china.center.oa.sail.bean.BaseBean;
import com.china.center.oa.sail.bean.DistributionBean;
import com.china.center.oa.sail.bean.OutBalanceBean;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.bean.OutRepaireBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.dao.BaseBalanceDAO;
import com.china.center.oa.sail.dao.BaseDAO;
import com.china.center.oa.sail.dao.DistributionDAO;
import com.china.center.oa.sail.dao.OutBalanceDAO;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.dao.OutRepaireDAO;
import com.china.center.oa.sail.helper.OutHelper;
import com.china.center.oa.sail.helper.YYTools;
import com.china.center.oa.sail.vo.OutVO;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;

public class CustomerServiceManagerImpl implements CustomerServiceManager
{
	private final Log _logger = LogFactory.getLog(getClass());

	private final Log triggerLog = LogFactory.getLog("trigger");

	private PlatformTransactionManager transactionManager = null;

	private OutDAO outDAO = null;

	private BaseDAO baseDAO = null;

	private OutBalanceDAO outBalanceDAO = null;

	private BaseBalanceDAO baseBalanceDAO = null;

	private FeedBackDAO feedBackDAO = null;

	private FeedBackCheckDAO feedBackCheckDAO = null;

	private FeedBackDetailDAO feedBackDetailDAO = null;

	private FeedBackVisitDAO feedBackVisitDAO = null;

	private FeedBackVisitItemDAO feedBackVisitItemDAO = null;

	private FeedBackVSOutDAO feedBackVSOutDAO = null;

	private CommonDAO commonDAO = null;

	private InBillDAO inBillDAO = null;

	private StafferDAO stafferDAO = null;

	private CommonMailManager commonMailManager = null;

	private DistributionDAO distributionDAO = null;

	private StafferVSCustomerDAO stafferVSCustomerDAO = null;

	private OutRepaireDAO outRepaireDAO = null;
	
	/**
	 * 
	 */
	public CustomerServiceManagerImpl()
	{
	}

	/**
	 * 每天统计前3天的数据 {@inheritDoc}
	 */
	public void statFeedBackVisit()
	{
		triggerLog.info("statFeedBackVisit 开始统计...");

		long statsStar = System.currentTimeMillis();

		TransactionTemplate tran = new TransactionTemplate(transactionManager);

		try
		{
			tran.execute(new TransactionCallback()
			{
				public Object doInTransaction(TransactionStatus arg0)
				{
					// 统计近5天至近3天的数据，然后过滤
					String statBeginDate = TimeTools.now_short(-5);

					String statEndDate = TimeTools.now_short(-3);

					triggerLog.info("统计时间段为：" + statBeginDate + " 至 "	+ statEndDate);

					processStatFeedBack(null, statBeginDate, statEndDate, CustomerServiceConstant.FEEDBACK_TYPE_VISIT, true, null);

					// 回访合并：系统统计回访数据后，发现同一客户还有待回访的任务，将两个任务合并，删除前一个回访任务
					postProcessStatFeedBack(CustomerServiceConstant.FEEDBACK_TYPE_VISIT);

					return Boolean.TRUE;
				}

			});
		}
		catch (Exception e)
		{
			triggerLog.error(e, e);
		}

		triggerLog.info("statFeedBackVisit 统计结束... ,共耗时："
				+ (System.currentTimeMillis() - statsStar));

		return;
	}

	/**
	 * 统计 回访数据
	 * 
	 * @user 
	 * 		单个客户回访生成时，要用到
	 * @param 
	 * 		beginDate
	 * @param 
	 * 		endDate
	 * @type 
	 * 		0 :表示回访 1 表示对账
	 * @all 
	 * 		是否全部客户统计
	 * @customerId 
	 * 			单个客户回访生成时指定的客户
	 */
	private void processStatFeedBack(User user, String beginDate, String endDate, int type, boolean all, String customerId)
	{
		ConditionParse con = new ConditionParse();

		con.addWhereStr();

		con.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_OUTBILL);

		con.addCondition(" and OutBean.outType in (0, 3)");

		con.addCondition(" and OutBean.status in (3, 4)");

		if (all){
			con.addCondition("OutBean.changeTime", ">=", beginDate + " 00:00:01");

			con.addCondition("OutBean.changeTime", "<=", endDate + " 23:59:59");
		}else{
			con.addCondition("OutBean.changeTime", ">=", TimeTools.now_short(-7) + " 00:00:01");

			con.addCondition("OutBean.changeTime", "<=", TimeTools.now_short() + " 23:59:59");
			
			con.addCondition("OutBean.customerId", "=", customerId);
		}

		// 回访
		if (type == 1)
		{
			// 只统计纪念品与邮政， 终端只统计自提方式
			con.addCondition(" and OutBean.industryId in ('5111656', '5111657', '5111658')");

			con.addIntCondition("OutBean.feedBackVisit", "=", OutConstant.OUT_FEEDBACK_VISIT_NO);
		}
		else
		{
			// 对账 只统计纪念品与邮政
			con.addCondition(" and OutBean.industryId in ('5111656', '5111657')");

			con.addIntCondition("OutBean.pay", "=", OutConstant.PAY_NOT);

			con.addIntCondition("OutBean.feedBackCheck", "=", OutConstant.OUT_FEEDBACK_CHECK_NO);
		}

		List<OutVO> outList = outDAO.queryEntityVOsByCondition(con);

		// 以客户与业务为单位，统计 单据数量， 产品数量（减退货数量），销售金额（减退货金额），未付款金额（减退货 - 已付款 - 坏账 - 折扣）
		Map<String, FeedBackBean> map = new HashMap<String, FeedBackBean>();

		List<FeedBackVSOutBean> vsList = new ArrayList<FeedBackVSOutBean>();

		List<FeedBackDetailBean> detailList = new ArrayList<FeedBackDetailBean>();

		// 约定：统计近5天的，自提过滤掉；货运; 近5天; 快递，近3天; 公司, 近4天; 货运+快递：近5天
		for (OutVO each : outList)
		{
			if (type == 1 && all)
			{
				// A1201310151011526376[空退空开库（仅限商务部操作）的默认仓区] 过滤掉空退库的销售 - A1201301221008971864[空退空开库] - 不回访
				if (each.getLocation().equals("A1201301221008971864") || each.getLocation().equals("A1201310151011526376"))
					continue;
				
				List<DistributionBean> distBeanList = distributionDAO.queryEntityBeansByFK(each.getFullId());

				if (!ListTools.isEmptyOrNull(distBeanList))
				{
					if (!CustomerServiceHelper.checkStatsOutTime(distBeanList.get(0), each, beginDate))
					{
						continue;
					}
				}
				
				// 空开空退产生的新销售单:
				// 做过空开空退原单子不回访，只访问新单
				// 如果是空开空退产生的新单，判断原单是否已回访过，回访了产生的新单则不再回访
				if (checkIfOldOutRepaire(each))
				{
					continue;
				}
				
				if (checkIfNewOutRepaire(each))
				{
					continue;
				}
			}

			List<FeedBackDetailBean> dList = new ArrayList<FeedBackDetailBean>();

			// 根据客户 生成任务单
			String key = each.getCustomerId();

			int amount = 0;

			double moneys = 0.0d;

			double hadpay = 0.0d;

			double noPayMoneys = 0.0d;

			// 销售出库
			if (each.getOutType() == OutConstant.OUTTYPE_OUT_COMMON)
			{
				amount = processDetail(each, dList);

				double backTotal = outDAO.sumOutBackValue(each.getFullId());

				moneys = each.getTotal() - backTotal;

				hadpay = each.getHadPay() + each.getBadDebts()
						+ each.getPromValue();

				noPayMoneys = moneys - hadpay;
			}
			else
			// 委托代销
			{
				amount = processDetail(each, dList);

				double balanceBackValue = 0.0d;
				
				hadpay = each.getHadPay() + each.getBadDebts()
				+ each.getPromValue();

//				List<OutBalanceBean> balanceList = outBalanceDAO.queryExcludeSettleBack(each.getFullId(), 0);
//
//				for (OutBalanceBean eachBalance : balanceList)
//				{
//					balanceBackValue += eachBalance.getTotal();
//				}
				
				for (FeedBackDetailBean eachdetail: dList){
					noPayMoneys += eachdetail.getNoPayMoneys();
					
					balanceBackValue += eachdetail.getBackMoney();
				}

				moneys = each.getTotal() - balanceBackValue;

//				noPayMoneys = moneys - hadpay;
			}

			if (!map.containsKey(key))
			{
				FeedBackBean fbb = new FeedBackBean();

				// 任务ID
				fbb.setId(commonDAO.getSquenceString20());
				fbb.setCustomerId(each.getCustomerId());
				fbb.setStafferId(each.getStafferId());
				fbb.setOutCount(1);
				fbb.setProductCount(amount);
				fbb.setMoneys(moneys);
				fbb.setHadpay(hadpay);
				fbb.setNoPayMoneys(noPayMoneys);
				fbb.setType(type);
				if (all){
					fbb.setStatus(CustomerServiceConstant.FEEDBACK_STATUS_DISTRIBUTE);
				}else{
					fbb.setStatus(CustomerServiceConstant.FEEDBACK_STATUS_PROCESSING);
					fbb.setBear(user.getStafferId());
					fbb.setBearName(user.getStafferName());
				}
				
				fbb.setLogTime(TimeTools.now());
				fbb.setStatsStar(beginDate);
				fbb.setStatsEnd(endDate);
				fbb.setIndustryIdName(each.getIndustryName());
				fbb.setPstatus(CustomerServiceConstant.FEEDBACK_STATUS_INIT);

				map.put(key, fbb);

				FeedBackVSOutBean vs = new FeedBackVSOutBean();

				vs.setOutId(each.getFullId());
				vs.setTaskId(fbb.getId());
				vs.setChangeTime(each.getChangeTime());

				vsList.add(vs);

				for (FeedBackDetailBean eachd : dList)
				{
					eachd.setTaskId(fbb.getId());
				}

				detailList.addAll(dList);
			}
			else
			{
				FeedBackBean fbb = map.get(key);

				fbb.setOutCount(fbb.getOutCount() + 1);
				fbb.setProductCount(fbb.getProductCount() + amount);
				fbb.setMoneys(fbb.getMoneys() + moneys);
				fbb.setHadpay(fbb.getHadpay() + hadpay);
				fbb.setNoPayMoneys(fbb.getNoPayMoneys() + noPayMoneys);

				FeedBackVSOutBean vs = new FeedBackVSOutBean();

				vs.setOutId(each.getFullId());
				vs.setTaskId(fbb.getId());
				vs.setChangeTime(each.getChangeTime());

				vsList.add(vs);

				for (FeedBackDetailBean eachd : dList)
				{
					eachd.setTaskId(fbb.getId());
				}

				detailList.addAll(dList);
			}

			// 置状态
			if (type == 1)
			{
				outDAO.updateFeedBackVisit(each.getFullId(),
					OutConstant.OUT_FEEDBACK_VISIT_INWAY);
			}
			else{
				outDAO.updateFeedBackCheck(each.getFullId(),
						OutConstant.OUT_FEEDBACK_CHECK_INWAY);
			}
		}

		// 将map 中的统计数据持久化
		Collection<FeedBackBean> list = map.values();

		// 赋上客户当前挂靠的业务员
		for (FeedBackBean each : list)
		{
			StafferVSCustomerBean vscust = stafferVSCustomerDAO
					.findByUnique(each.getCustomerId());

			if (vscust != null)
			{
				each.setStafferId(vscust.getStafferId());
			}
		}

		feedBackDAO.saveAllEntityBeans(list);

		feedBackVSOutDAO.saveAllEntityBeans(vsList);

		if (!ListTools.isEmptyOrNull(detailList))
		{
			feedBackDetailDAO.saveAllEntityBeans(detailList);
		}
	}

	private boolean checkIfOldOutRepaire(OutVO out)
	{
		ConditionParse con = new ConditionParse();
		
		// 是否有未审批结束的空开空退申请
		con.clear();
		
		con.addWhereStr();
		
		con.addCondition("OutRepaireBean.outId", "=", out.getFullId());
		
		int count = outRepaireDAO.countByCondition(con.toString());
		
		return (count > 0);
	}
	
	private boolean checkIfNewOutRepaire(OutVO out)
	{
		ConditionParse con = new ConditionParse();
		
		// 是否有未审批结束的空开空退申请
		con.clear();
		
		con.addWhereStr();
		
		con.addCondition("OutRepaireBean.newOutId", "=", out.getFullId());
		
		List<OutRepaireBean> list = outRepaireDAO.queryEntityBeansByCondition(con);
		
		for (OutRepaireBean each : list)
		{
			String oldOutId = each.getOutId();
			
			OutBean  oldOut = outDAO.find(oldOutId);
			
			if (oldOut != null)
			{
				if (oldOut.getFeedBackVisit() != 0)
					return true;
			}
		}
		
		return false;
	}

	/**
	 * 返回一个销售单产品数据，减去退货量，同时返回产品 明细
	 * 
	 * @param out
	 *            含 销售 出库与委托代销
	 * @param dList
	 * @return
	 */
	private int processDetail(OutBean out, List<FeedBackDetailBean> dList)
	{
		int amount = 0;

		if (out.getOutType() == OutConstant.OUTTYPE_OUT_COMMON)
		{
			List<BaseBean> baseList = OutHelper.trimBaseList2(baseDAO
					.queryEntityBeansByFK(out.getFullId()));

			List<OutBean> refBuyList = queryRefOut(out.getFullId());

			for (BaseBean baseBean : baseList)
			{
				int hasBack = 0;

				// 退库
				for (OutBean ref : refBuyList)
				{
					List<BaseBean> refBaseList = OutHelper.trimBaseList2(ref
							.getBaseList());

					for (BaseBean refBase : refBaseList)
					{
						if (refBase.equals2(baseBean))
						{
							hasBack += refBase.getAmount();

							break;
						}
					}
				}

				baseBean.setAmount(baseBean.getAmount() - hasBack);

				amount += baseBean.getAmount();

				// fill dList
				FeedBackDetailBean fbdb = new FeedBackDetailBean();

				fbdb.setOutId(baseBean.getOutId());
				fbdb.setProductId(baseBean.getProductId());
				fbdb.setProductName(baseBean.getProductName());
				fbdb.setPrice(baseBean.getPrice());
				fbdb.setAmount(baseBean.getAmount());
				fbdb.setHasBack(hasBack);
				fbdb.setMoney(fbdb.getPrice() * fbdb.getAmount());
				fbdb.setBackMoney(fbdb.getPrice() * fbdb.getHasBack());
				fbdb.setPay(out.getPay());
				if (out.getPay() == OutConstant.PAY_NOT)
				{
					fbdb.setNoPayAmount(fbdb.getAmount());
					fbdb.setNoPayMoneys(fbdb.getMoney());
				}
				else
				{
					fbdb.setNoPayAmount(0);
					fbdb.setNoPayMoneys(0);
				}

				dList.add(fbdb);
			}
		}
		else
		// 委托代销
		{
			List<BaseBean> baseList = OutHelper.trimBaseList2(baseDAO
					.queryEntityBeansByFK(out.getFullId()));

			List<BaseBalanceBean> bbbList = new ArrayList<BaseBalanceBean>();
			
			// 所有委托代销退货
			List<OutBalanceBean> balanceList = outBalanceDAO
					.queryExcludeSettleBack(out.getFullId(), 0);

			for (OutBalanceBean eachBalance : balanceList)
			{
				if (eachBalance.getStatus() != OutConstant.OUTBALANCE_STATUS_END)
					continue;
				
				bbbList.addAll(baseBalanceDAO.queryEntityBeansByFK(eachBalance
						.getId()));
			}
			
			// 所有结算单，且已勾款的 - 结算单退货单
			List<OutBalanceBean> payedBalanceList = outBalanceDAO.queryHasPayByOutId(out.getFullId());
			
			List<BaseBalanceBean> pbbbList = new ArrayList<BaseBalanceBean>();
			
			List<OutBalanceBean> refpayedBalanceList = new ArrayList<OutBalanceBean>();
			
			for (OutBalanceBean eachBalance : payedBalanceList)
			{
				pbbbList.addAll(baseBalanceDAO.queryEntityBeansByFK(eachBalance.getId()));
				
				refpayedBalanceList.addAll(outBalanceDAO.queryEntityBeansByFK(eachBalance.getId(), AnoConstant.FK_FIRST));
			}
			
			List<BaseBalanceBean> rpbbbList = new ArrayList<BaseBalanceBean>();
			
			for (OutBalanceBean eachBalance : refpayedBalanceList)
			{
				if (eachBalance.getStatus() != OutConstant.OUTBALANCE_STATUS_END)
					continue;
				
				rpbbbList.addAll(baseBalanceDAO.queryEntityBeansByFK(eachBalance
						.getId()));
			}
			
			for (BaseBalanceBean each : pbbbList)
			{
				int back = 0;
				for (BaseBalanceBean eachb : rpbbbList)
				{
					if (each.getBaseId().equals(eachb.getBaseId()))
					{
						// 要求eachb.parentId 的outbalance中的refOutbalanceid= each.parentid
						OutBalanceBean obb = outBalanceDAO.find(eachb.getParentId());
						
						if (each.getParentId().equals(obb.getRefOutBalanceId()))
							back += eachb.getAmount();
					}
				}
				
				each.setAmount(each.getAmount() - back);
			}
			
			// 所有结算单，且未勾款的 - 结算退货单
			List<OutBalanceBean> nopayedBalanceList = outBalanceDAO.queryNoPayByOutId(out.getFullId());
			
			List<OutBalanceBean> refnopayedBalanceList = new ArrayList<OutBalanceBean>();
			
			List<BaseBalanceBean> pnobbbList = new ArrayList<BaseBalanceBean>();
			
			for (OutBalanceBean eachBalance : nopayedBalanceList)
			{
				pnobbbList.addAll(baseBalanceDAO.queryEntityBeansByFK(eachBalance
						.getId()));
				
				refnopayedBalanceList.addAll(outBalanceDAO.queryEntityBeansByFK(eachBalance.getId(), AnoConstant.FK_FIRST));
			}
			
			List<BaseBalanceBean> rpnobbbList = new ArrayList<BaseBalanceBean>();
			
			for (OutBalanceBean eachBalance : refnopayedBalanceList)
			{
				if (eachBalance.getStatus() != OutConstant.OUTBALANCE_STATUS_END)
					continue;
				
				rpnobbbList.addAll(baseBalanceDAO.queryEntityBeansByFK(eachBalance
						.getId()));
			}
			
			for (BaseBalanceBean each : pnobbbList)
			{
				int back = 0;
				for (BaseBalanceBean eachb : rpnobbbList)
				{
					if (each.getBaseId().equals(eachb.getBaseId()))
					{
						// 要求eachb.parentId 的outbalance中的refOutbalanceid= each.parentid
						OutBalanceBean obb = outBalanceDAO.find(eachb.getParentId());
						
						if (each.getParentId().equals(obb.getRefOutBalanceId()))
							back += eachb.getAmount();
					}
				}
				
				each.setAmount(each.getAmount() - back);
			}
			// end
			
			
			for (BaseBean each : baseList)
			{
				int hasBack = 0;
				double hasBackMoney = 0.0d;
				
				int hasPay = 0;
				double hasPayMoney = 0.0d;
				
				int settleNoPayAmount = 0;
				double settleNoPayMoney = 0.0d;
				

				for (BaseBalanceBean eachb : bbbList)
				{
					if (each.getId().equals(eachb.getBaseId()))
					{
						hasBack += eachb.getAmount();
						hasBackMoney += eachb.getAmount() * eachb.getSailPrice();
					}
				}

				for (BaseBalanceBean eachb : pbbbList)
				{
					if (each.getId().equals(eachb.getBaseId()))
					{
						hasPay += eachb.getAmount();
						hasPayMoney += eachb.getAmount() * eachb.getSailPrice();
					}
				}
				
				for (BaseBalanceBean eachb : pnobbbList)
				{
					if (each.getId().equals(eachb.getBaseId()))
					{
						settleNoPayAmount += eachb.getAmount();
						settleNoPayMoney += eachb.getAmount() * eachb.getSailPrice();
					}
				}
				
				each.setAmount(each.getAmount() - hasBack);

				amount += each.getAmount();

				// fill dList
				FeedBackDetailBean fbdb = new FeedBackDetailBean();

				fbdb.setOutId(each.getOutId());
				fbdb.setProductId(each.getProductId());
				fbdb.setProductName(each.getProductName());
				fbdb.setPrice(each.getPrice());
				fbdb.setAmount(each.getAmount());
				fbdb.setHasBack(hasBack);
				// 结算单 价格 可能会变化 = 未结算数量 * 委托单价 + 结算单 未退部分
				fbdb.setMoney(fbdb.getPrice() * (fbdb.getAmount() - hasPay - settleNoPayAmount) + hasPayMoney + settleNoPayMoney);
				fbdb.setBackMoney(hasBackMoney);
				fbdb.setPay(out.getPay());
				if (out.getPay() == OutConstant.PAY_NOT)
				{
					fbdb.setNoPayAmount(fbdb.getAmount() - hasPay);
					fbdb.setNoPayMoneys(fbdb.getMoney() - hasPayMoney);
				}
				else
				{
					fbdb.setNoPayAmount(0);
					fbdb.setNoPayMoneys(0);
				}

				fbdb.setSettleNoPayAmount(settleNoPayAmount);
				fbdb.setSettleNoPayMoney(settleNoPayMoney);
				
				dList.add(fbdb);
			}
		}

		return amount;
	}

	/**
	 * 查询REF的入库单(已经通过的,退库的)
	 * 
	 * @param request
	 * @param outId
	 * @return
	 */
	private List<OutBean> queryRefOut(String outId)
	{
		// 查询当前已经有多少个人领样
		ConditionParse con = new ConditionParse();

		con.addWhereStr();

		con.addCondition("OutBean.refOutFullId", "=", outId);

		con.addCondition(" and OutBean.status in (3, 4)");

		con.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_INBILL);

		// 排除其他入库(对冲单据)
		// con.addIntCondition("OutBean.outType", "<>",
		// OutConstant.OUTTYPE_IN_OTHER);

		con.addCondition("OutBean.reserve8", "<>", "1");

		List<OutBean> refBuyList = outDAO.queryEntityBeansByCondition(con);

		for (OutBean outBean : refBuyList)
		{
			List<BaseBean> list = baseDAO.queryEntityBeansByFK(outBean
					.getFullId());

			outBean.setBaseList(list);
		}

		return refBuyList;
	}

	/**
	 * 每月3号出数据 {@inheritDoc}
	 */
	public void statFeedBackCheck()
	{
		triggerLog.info("statFeedBackCheck 开始统计...");

		long statsStar = System.currentTimeMillis();

		TransactionTemplate tran = new TransactionTemplate(transactionManager);

		try
		{
			tran.execute(new TransactionCallback()
			{
				public Object doInTransaction(TransactionStatus arg0)
				{
					String statsBegin = YYTools.getFinanceBeginDate();

					String statsBegin1 = TimeTools.getLastestMonthBegin();

					String statsEnd = TimeTools.getMonthEnd(statsBegin1);

					statsBegin = "2013-03-01";
					triggerLog.info("统计时间段为：" + statsBegin + " 至 " + statsEnd);

					processStatFeedBack(null, statsBegin, statsEnd, CustomerServiceConstant.FEEDBACK_TYPE_CHECK, true, null);

					// 对帐回访合并：系统统计对帐数据后，发现同一客户还有待回访的任务，删除前面的回访任务
					//postProcessStatFeedBack(CustomerServiceConstant.FEEDBACK_TYPE_CHECK);

					return Boolean.TRUE;
				}
			});
		}
		catch (Exception e)
		{
			triggerLog.error(e, e);
		}

		triggerLog.info("statFeedBackCheck 统计结束... ,共耗时："
				+ (System.currentTimeMillis() - statsStar));

		return;
	}

	/**
	 * 
	 * @param type
	 */
	private void postProcessStatFeedBack(int type)
	{
		// 找出未处理过的回访 or 对账 待分配，待接收，待处理（但未发生过操作记录）
		List<FeedBackBean> list = feedBackDAO.queryByTypeAndPstatus(type, CustomerServiceConstant.FEEDBACK_STATUS_INIT);

		if (type == CustomerServiceConstant.FEEDBACK_TYPE_VISIT)
		{
			Map<String, List<FeedBackBean>> map = new HashMap<String, List<FeedBackBean>>();

			for (FeedBackBean each : list)
			{
				if (!map.containsKey(each.getCustomerId()))
				{

					List<FeedBackBean> mList = new ArrayList<FeedBackBean>();

					mList.add(each);
					
					map.put(each.getCustomerId(), mList);
				}
				else
				{
					List<FeedBackBean> mList = map.get(each.getCustomerId());

					mList.add(each);
				}
			}

			for (Map.Entry<String, List<FeedBackBean>> each : map.entrySet())
			{
				String customerId = each.getKey();
				
				List<FeedBackBean> mList = each.getValue();

				// if need merge， 合并到最近的一个任务上
				if (mList.size() > 1)
				{
					FeedBackBean fbb = mList.get(0);

					int amount = 0;

					int outCount = 0;

					double moneys = 0.0d;

					double hadpay = 0.0d;

					double noPayMoneys = 0.0d;

					Set<String> taskIds = new HashSet<String>();

					for (FeedBackBean each1 : mList)
					{
						amount += each1.getProductCount();
						outCount += each1.getOutCount();
						moneys += each1.getMoneys();
						hadpay += each1.getHadpay();
						noPayMoneys += each1.getNoPayMoneys();

						// 为要被并掉的任务做准备
						if (!each1.getId().equals(fbb.getId())) 
							taskIds
								.add(each1.getId());
					}

					fbb.setOutCount(outCount);
					fbb.setProductCount(amount);
					fbb.setMoneys(moneys);
					fbb.setHadpay(hadpay);
					fbb.setNoPayMoneys(noPayMoneys);
					fbb.setLogTime(TimeTools.now());
					fbb.setStatus(CustomerServiceConstant.FEEDBACK_STATUS_DISTRIBUTE);
					fbb.setBear("");
					fbb.setBearName("");
					fbb.setPstatus(CustomerServiceConstant.FEEDBACK_STATUS_INIT);

					feedBackDAO.updateEntityBean(fbb);

					for (String eachTask : taskIds)
					{
						feedBackDAO.deleteEntityBean(eachTask);

						List<FeedBackDetailBean> dList = feedBackDetailDAO
								.queryEntityBeansByFK(eachTask);

						for (FeedBackDetailBean eachd : dList)
						{
							eachd.setTaskId(fbb.getId());

							feedBackDetailDAO.updateEntityBean(eachd);
						}

						List<FeedBackVSOutBean> vsList = feedBackVSOutDAO
								.queryEntityBeansByFK(eachTask);

						for (FeedBackVSOutBean eachvs : vsList)
						{
							eachvs.setTaskId(fbb.getId());

							feedBackVSOutDAO.updateEntityBean(eachvs);
						}
					}
					
					triggerLog.info("postProcessStatFeedBack 合并客户："+ customerId + " 的回访单" + taskIds);
				}
			}

		}

		// 对帐回访合并：系统统计对帐数据后，发现同一客户还有待回访的任务，删除前面的回访任务
		if (type == CustomerServiceConstant.FEEDBACK_TYPE_CHECK)
		{
			for (FeedBackBean each : list)
			{
				String customerId = each.getCustomerId();

				// 查询客户对应的回访数据
				List<FeedBackBean> visitList = feedBackDAO
						.queryByTypeAndCustomerIdAndPstatus(CustomerServiceConstant.FEEDBACK_TYPE_VISIT,
								customerId, CustomerServiceConstant.FEEDBACK_STATUS_INIT);
				
				for(FeedBackBean eachv : visitList)
				{
					feedBackDAO.deleteEntityBean(eachv.getId());
					
					feedBackDetailDAO.deleteEntityBeansByFK(eachv.getId());
					
					feedBackVSOutDAO.deleteEntityBeansByFK(eachv.getId());
					
					triggerLog.info("postProcessStatFeedBack 生成对账时，删除客户："+ customerId + " 的未回访单" + eachv.getId());
				}
			}
		}
	}

	@Transactional
	public boolean allocationTasks(User user, String ids, String destStafferId)
			throws MYException
	{
		JudgeTools.judgeParameterIsNull(ids, destStafferId);

		_logger.info("allocationTasks:" + ids);

		StafferBean stafferBean = stafferDAO.find(destStafferId);

		String taskIds[] = ids.split("~");

		for (String id : taskIds)
		{
			// 确定任务类型为1回访2对帐4异常回访; 状态为 1 待分配
			FeedBackBean bean = feedBackDAO.find(id);

			if (bean.getType() != CustomerServiceConstant.FEEDBACK_TYPE_EXCEPTION_PROCESS
					&& bean.getStatus() == CustomerServiceConstant.FEEDBACK_STATUS_DISTRIBUTE)
			{
				bean.setBear(destStafferId);

				bean.setBearName(stafferBean.getName());

				bean.setStatus(CustomerServiceConstant.FEEDBACK_STATUS_ACCEPT);

				feedBackDAO.updateEntityBean(bean);
			}
		}

		return true;
	}

	@Transactional(rollbackFor = MYException.class)
	public boolean acceptTasks(User user, String ids) throws MYException
	{
		JudgeTools.judgeParameterIsNull(ids);

		_logger.info("allocationTasks:" + ids);

		String taskIds[] = ids.split("~");

		for (String id : taskIds)
		{
			// 确定任务类型为1回访2对帐4异常回访; 状态为 1 待分配
			FeedBackBean bean = feedBackDAO.find(id);

			if (bean.getType() != CustomerServiceConstant.FEEDBACK_TYPE_EXCEPTION_PROCESS
					&& bean.getStatus() == CustomerServiceConstant.FEEDBACK_STATUS_ACCEPT)
			{
				bean.setStatus(CustomerServiceConstant.FEEDBACK_STATUS_PROCESSING);

				feedBackDAO.updateEntityBean(bean);
			}
		}

		return true;
	}

	@Transactional(rollbackFor = MYException.class)
	public boolean rejectTasks(User user, String ids) throws MYException
	{
		JudgeTools.judgeParameterIsNull(ids);

		_logger.info("allocationTasks:" + ids);

		String taskIds[] = ids.split("~");

		for (String id : taskIds)
		{
			// 确定任务类型为1回访2对帐4异常回访; 状态为 1 待分配
			FeedBackBean bean = feedBackDAO.find(id);

			if (bean.getType() != CustomerServiceConstant.FEEDBACK_TYPE_EXCEPTION_PROCESS
					&& bean.getStatus() == CustomerServiceConstant.FEEDBACK_STATUS_ACCEPT)
			{
				bean.setStatus(CustomerServiceConstant.FEEDBACK_STATUS_DISTRIBUTE);

				bean.setBear("");

				bean.setBearName("");

				feedBackDAO.updateEntityBean(bean);
			}
		}

		return true;
	}

	@Transactional(rollbackFor = MYException.class)
	public boolean addSingleCustomerVisit(User user, String customerId)
	throws MYException
	{
		JudgeTools.judgeParameterIsNull(user, customerId);
		
		processStatFeedBack(user, null, null, CustomerServiceConstant.FEEDBACK_TYPE_VISIT, false, customerId);
		
		return true;
	}
	
	@Transactional(rollbackFor = MYException.class)
	public boolean addFeedBackVisit(User user, FeedBackVisitBean visitBean)
			throws MYException
	{
		JudgeTools.judgeParameterIsNull(visitBean);

		FeedBackBean bean = feedBackDAO.find(visitBean.getTaskId());

		if (null == bean)
		{
			throw new MYException("数据错误");
		}

		List<FeedBackVisitItemBean> itemList = visitBean.getItemList();

		boolean exception = false;

		String id = "";

		id = commonDAO.getSquenceString20();

		visitBean.setId(id);

		for (FeedBackVisitItemBean each : itemList)
		{
			each.setRefId(id);

			if (each.getIfHasException() == CustomerServiceConstant.FEEDBACK_RECEIVE_EXCEPTION_YES)
			{
				exception = true;
			}
		}

		bean.setPstatus(CustomerServiceConstant.FEEDBACK_STATUS_SAVE);
		
		// 提交时执行
		if (visitBean.getStatus() == 1)
		{
			if (exception)
			{
				// 异常处理人要必须
				if (StringTools.isNullOrNone(visitBean.getExceptionProcesser()))
				{
					throw new MYException("有异常时，异常处理人必须指定.");
				}

				visitBean
						.setExceptionStatus(CustomerServiceConstant.FEEDBACK_EXCEPTION_STATUS_INWAY);

				bean.setType(CustomerServiceConstant.FEEDBACK_TYPE_EXCEPTION_VISIT);

				bean.setStatus(CustomerServiceConstant.FEEDBACK_STATUS_EXCEPTION_PROCESSING);

				bean.setRefVisitId(id);

				bean.setPstatus(CustomerServiceConstant.FEEDBACK_STATUS_SAVE);
			}
			else
			{
				visitBean.setExceptionProcesser("");
				
				visitBean.setExceptionProcesserName("");
				
				bean.setStatus(CustomerServiceConstant.FEEDBACK_STATUS_OK);

				bean.setPstatus(CustomerServiceConstant.FEEDBACK_STATUS_SUBMIT);

				List<FeedBackVSOutBean> outList = feedBackVSOutDAO
						.queryEntityBeansByFK(visitBean.getTaskId());

				for (FeedBackVSOutBean each : outList)
				{
					outDAO.updateFeedBackVisit(each.getOutId(),
							OutConstant.OUT_FEEDBACK_VISIT_YES);
				}
			}
		}
		
		feedBackDAO.updateEntityBean(bean);
		
		feedBackVisitDAO.saveEntityBean(visitBean);

		feedBackVisitItemDAO.saveAllEntityBeans(itemList);

		return true;
	}

	@Transactional(rollbackFor = MYException.class)
	public boolean updateFeedBackVisit(User user, FeedBackVisitBean visitBean)
			throws MYException
	{
		JudgeTools.judgeParameterIsNull(visitBean);

		FeedBackVisitBean old = feedBackVisitDAO.find(visitBean.getId());

		if (old == null)
		{
			throw new MYException("数据错误");
		}

		if (old.getExceptionStatus() != CustomerServiceConstant.FEEDBACK_EXCEPTION_STATUS_INWAY)
		{
			throw new MYException("状态错误");
		}

		if (visitBean.getStatus() == 1)
		{
			visitBean
			.setExceptionStatus(CustomerServiceConstant.FEEDBACK_EXCEPTION_STATUS_OK);

			// 异常处理完成后，同步修改
			FeedBackBean bean = feedBackDAO.find(visitBean.getTaskId());
		
			if (null == bean)
			{
				throw new MYException("数据错误");
			}
		
			// 处理结束后，重新由原处理人继续处理,但要求时间为3天后
			bean.setStatus(CustomerServiceConstant.FEEDBACK_STATUS_PROCESSING);
		
			bean.setForecastDate(TimeTools.now_short(3));
			
			feedBackDAO.updateEntityBean(bean);
		}
		
		feedBackVisitDAO.updateEntityBean(visitBean);

		return true;
	}

	@Transactional(rollbackFor = MYException.class)
	public boolean addFeedBackCheck(User user, FeedBackCheckBean checkBean)
			throws MYException
	{
		JudgeTools.judgeParameterIsNull(checkBean);

		FeedBackBean bean = feedBackDAO.find(checkBean.getTaskId());

		if (null == bean)
		{
			throw new MYException("数据错误");
		}

		String id = commonDAO.getSquenceString20();

		checkBean.setId(id);

		bean.setPstatus(CustomerServiceConstant.FEEDBACK_STATUS_SAVE);
		
		boolean exception = false;
		
		if (!StringTools.isNullOrNone(checkBean.getExceptionProcesser()))
		{
			exception = true;
		}

		// 提交时
		if (checkBean.getStatus() == 1)
		{
			if (exception)
			{
				checkBean
						.setExceptionStatus(CustomerServiceConstant.FEEDBACK_EXCEPTION_STATUS_INWAY);

				bean.setStatus(CustomerServiceConstant.FEEDBACK_STATUS_EXCEPTION_PROCESSING);

				bean.setPstatus(CustomerServiceConstant.FEEDBACK_STATUS_SAVE);
			}
			else
			{
				bean.setStatus(CustomerServiceConstant.FEEDBACK_STATUS_OK);

				bean.setPstatus(CustomerServiceConstant.FEEDBACK_STATUS_SUBMIT);
			}
			
			List<FeedBackVSOutBean> outList = feedBackVSOutDAO.queryEntityBeansByFK(checkBean.getTaskId());

			for (FeedBackVSOutBean each : outList)
			{
				// 对账结束后，重置。下次重新统计
				outDAO.updateFeedBackCheck(each.getOutId(),	OutConstant.OUT_FEEDBACK_CHECK_NO);
			}
		}

		if (checkBean.getIfSendConfirmFax() == CustomerServiceConstant.FEEDBACK_CONFIRMFAX_YES)
		{
			bean.setPstatus(CustomerServiceConstant.FEEDBACK_STATUS_SENDFAX);
		}
		
		if (checkBean.getIfReceiveConfirmFax() == CustomerServiceConstant.FEEDBACK_CONFIRMFAX_YES)
		{
			bean.setPstatus(CustomerServiceConstant.FEEDBACK_STATUS_RECEIVEFAX);
		}
		
		feedBackCheckDAO.saveEntityBean(checkBean);
		
		feedBackDAO.updateEntityBean(bean);

		return true;
	}

	@Transactional(rollbackFor = MYException.class)
	public boolean updateFeedBackCheck(User user, FeedBackCheckBean checkBean)
			throws MYException
	{
		JudgeTools.judgeParameterIsNull(checkBean);

		FeedBackCheckBean old = feedBackCheckDAO.find(checkBean.getId());

		if (old == null)
		{
			throw new MYException("数据错误");
		}

		if (old.getExceptionStatus() != CustomerServiceConstant.FEEDBACK_EXCEPTION_STATUS_INWAY)
		{
			throw new MYException("状态错误");
		}

		if (checkBean.getStatus() == 1)
		{
			checkBean.setExceptionStatus(CustomerServiceConstant.FEEDBACK_EXCEPTION_STATUS_OK);

			// 异常处理完成后，同步修改
			FeedBackBean bean = feedBackDAO.find(checkBean.getTaskId());
		
			if (null == bean)
			{
				throw new MYException("数据错误");
			}
		
			bean.setType(CustomerServiceConstant.FEEDBACK_TYPE_EXCEPTION_CHECK);
			// 处理结束后，重新由原处理人继续处理
			bean.setStatus(CustomerServiceConstant.FEEDBACK_STATUS_PROCESSING);
		
			feedBackDAO.updateEntityBean(bean);
		}
		
		feedBackCheckDAO.updateEntityBean(checkBean);

		return true;
	}
	
	public String downAttachment(User user, FeedBackVO bean) throws MYException
	{
		String fileName = getFeedBackCheckPath() + "/" + bean.getCustomerName()
				+ "_" + TimeTools.now("yyyyMMddHHmmss") + ".xls";

		createMailAttachment(bean, fileName);

		// check file either exists
		File file = new File(fileName);

		if (!file.exists())
		{
			throw new MYException("邮件附件未成功生成");
		}

		return fileName;
	}

	/**
	 * 先生成附件,再发邮件 {@inheritDoc}
	 */
	@Transactional(rollbackFor = MYException.class)
	public boolean mailAttachment(User user, FeedBackVO bean)
			throws MYException
	{
		String fileName = getFeedBackCheckPath() + "/" + bean.getCustomerName()
				+ "_" + TimeTools.now("yyyyMMddHHmmss") + ".xls";

		createMailAttachment(bean, fileName);

		// check file either exists
		File file = new File(fileName);

		if (!file.exists())
		{
			throw new MYException("邮件附件未成功生成");
		}

		feedBackDAO.updateEntityBean(bean);

		// send mail contain attachment
		commonMailManager.sendMail(bean.getMailAddress(), "对账函",
				"永银文化创意产业发展有限责任公司对账函，请查看附件，谢谢。", fileName);

		return true;
	}

	@SuppressWarnings("static-access")
	private void createMailAttachment(FeedBackVO bean, String fileName)
	{
		WritableWorkbook wwb = null;

		WritableSheet ws = null;

		OutputStream out = null;

		String content = "永银公司为正确反映双方的往来，特与贵公司核实往来账项等事项。下列信息出自本公司系统记录，如与贵公司记录相符，请在本函下端“信息证明无误”处签章证明；如有不符，请在“信息不符”处列明不符项目。如存在与本公司有关的未列入本函的其他项目，也请在“信息不符”处列出的这些项目的金额及其他详细资料。";

		String content2 = "回函地址：南京市秦淮区应天大街388号1865创意园c2栋   邮编：210006";

		String content3 = "电话：  4006518859           传真：  025-51885907      联系人：永银商务部";

		String content4 = "1．本公司至今与贵公司未结款商品的往来账项列示如下：";

		String content5 = "备注，以上均为已发货未付款，请贵公司核实，谢谢！";

		String content6 = "本函仅为复核账目之用，并非催款结算。若款项在上述日期之后已经付清，仍请及时函复为盼。";

		List<FeedBackDetailBean> detailList = bean.getDetailList();

		List<FeedBackDetailBean> mergeList = merge(detailList);

		try
		{
			out = new FileOutputStream(fileName);

			// create a excel
			wwb = Workbook.createWorkbook(out);

			ws = wwb.createSheet("对账", 0);

			// 横向
			ws.setPageSetup(PageOrientation.LANDSCAPE.LANDSCAPE,PaperSize.A4,0.5d,0.5d);
			
			// 标题字体
			WritableFont font = new WritableFont(WritableFont.ARIAL, 11,
					WritableFont.BOLD, false,
					jxl.format.UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK);

			WritableFont font2 = new WritableFont(WritableFont.ARIAL, 9,
					WritableFont.BOLD, false,
					jxl.format.UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK);

			WritableFont font3 = new WritableFont(WritableFont.ARIAL, 9,
					WritableFont.NO_BOLD, false,
					jxl.format.UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK);

			WritableFont font4 = new WritableFont(WritableFont.ARIAL, 9,
					WritableFont.BOLD, false,
					jxl.format.UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLUE);

			WritableCellFormat format = new WritableCellFormat(font);

			format.setAlignment(jxl.format.Alignment.CENTRE);
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

			WritableCellFormat format2 = new WritableCellFormat(font2);

			format2.setAlignment(jxl.format.Alignment.LEFT);
			format2.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			format2.setWrap(true);

			WritableCellFormat format21 = new WritableCellFormat(font2);
			format21.setAlignment(jxl.format.Alignment.RIGHT);

			WritableCellFormat format3 = new WritableCellFormat(font3);
			format3.setBorder(jxl.format.Border.ALL,
					jxl.format.BorderLineStyle.THIN);

			WritableCellFormat format31 = new WritableCellFormat(font3);
			format31.setBorder(jxl.format.Border.ALL,
					jxl.format.BorderLineStyle.THIN);
			format31.setAlignment(jxl.format.Alignment.RIGHT);

			WritableCellFormat format4 = new WritableCellFormat(font4);
			format4.setBorder(jxl.format.Border.ALL,
					jxl.format.BorderLineStyle.THIN);

			WritableCellFormat format41 = new WritableCellFormat(font4);
			format41.setBorder(jxl.format.Border.ALL,
					jxl.format.BorderLineStyle.THIN);
			format41.setAlignment(jxl.format.Alignment.CENTRE);
			format41.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

			int i = 0, j = 0, i1 = 1;

			// 完成标题
			ws.addCell(new Label(0, i, "企业往来询证函", format));

			setWS(ws, i, 800, true);

			ws.setColumnView(0, 5);
			ws.setColumnView(1, 40);
			ws.setColumnView(2, 10);
			ws.setColumnView(3, 10);
			ws.setColumnView(4, 10);
			ws.setColumnView(5, 10);
			ws.setColumnView(6, 10);
			ws.setColumnView(7, 10);
			ws.setColumnView(8, 10);
			ws.setColumnView(9, 10);

			// 第三行
			i++;
			ws.addCell(new Label(0, i, bean.getCustomerName() + " 公司", format2));
			setWS(ws, i, 300, true);

			i++;
			// 第4行
			ws.addCell(new Label(0, i, content, format2));
			setWS(ws, i, 1000, true);

			// 第5行
			i++;
			ws.addCell(new Label(0, i, content2, format2));
			setWS(ws, i, 300, true);
			// 第6行
			i++;
			ws.addCell(new Label(0, i, content3, format2));
			setWS(ws, i, 300, true);
			// 第7行
			i++;
			ws.addCell(new Label(0, i, content4, format2));
			setWS(ws, i, 300, true);
			// 第8行
			i++;
			ws.addCell(new Label(0, i, "单位：元", format21));
			setWS(ws, i, 300, true);

			i++;
			// 正文表格
			ws.addCell(new Label(0, i, "序号", format3));

			setWS(ws, i, 300, false);

			ws.addCell(new Label(1, i, "品名", format3));

			ws.addCell(new Label(2, i, "发货数量", format3));

			ws.addCell(new Label(3, i, "发货金额", format3));

			ws.addCell(new Label(4, i, "回款数量", format3));

			ws.addCell(new Label(5, i, "回款金额", format3));
			
			ws.addCell(new Label(6, i, "退货数量", format3));
			ws.addCell(new Label(7, i, "退货金额", format3));

			ws.addCell(new Label(8, i, "应收数量", format3));
			ws.addCell(new Label(9, i, "应收金额", format3));

			int allAmount = 0, amount = 0, hasBack = 0, noPayAmount = 0, hadAmount = 0;

			double allMoney = 0.0d, money = 0.0d, backMoney = 0.0d, noPayMoney = 0.0d, hadMoney = 0.0d;

			i++;

			for (FeedBackDetailBean each : mergeList)
			{
				ws.addCell(new Label(j++, i, String.valueOf(i1++), format3));
				setWS(ws, i, 300, false);
				ws.addCell(new Label(j++, i, each.getProductName(), format3));
				ws.addCell(new Label(j++, i, String.valueOf(each.getAmount()
						+ each.getHasBack()), format31));
				ws.addCell(new Label(j++, i, String.valueOf(MathTools.formatNum2(each.getMoney()
						+ each.getBackMoney())), format31));

				ws.addCell(new Label(j++, i, String.valueOf(each.getAmount() - each.getNoPayAmount()),
						format31));
				ws.addCell(new Label(j++, i,
						String.valueOf(MathTools.formatNum2(each.getMoney() - each.getNoPayMoneys())), format31));
				
				ws.addCell(new Label(j++, i, String.valueOf(each.getHasBack()),
						format31));
				ws.addCell(new Label(j++, i,
						String.valueOf(MathTools.formatNum2(each.getBackMoney())), format31));
				ws.addCell(new Label(j++, i, String.valueOf(each.getNoPayAmount()),
						format31));
				ws.addCell(new Label(j++, i, String.valueOf(MathTools.formatNum2(each.getNoPayMoneys())),
						format31));

				allAmount += each.getAmount() + each.getHasBack();
				amount += each.getAmount();
				hasBack += each.getHasBack();
				noPayAmount += each.getNoPayAmount();
				hadAmount += each.getAmount() - each.getNoPayAmount();

				allMoney += each.getMoney() + each.getBackMoney();
				money += each.getMoney();
				backMoney += each.getBackMoney();
				noPayMoney += each.getNoPayMoneys();
				hadMoney += each.getMoney() - each.getNoPayMoneys();
				
				j = 0;
				i++;
			}

			// 第i + 1 行
			ws.addCell(new Label(0, i, "合计:", format31));
			setWS(ws, i, 300, false);

			ws.mergeCells(0, i, 1, i);

			ws.addCell(new Label(2, i, String.valueOf(allAmount), format31));
			ws.addCell(new Label(3, i, String.valueOf(MathTools.formatNum2(allMoney)), format31));
			ws.addCell(new Label(4, i, String.valueOf(hadAmount), format31));
			ws.addCell(new Label(5, i, String.valueOf(MathTools.formatNum2(hadMoney)), format31));
			ws.addCell(new Label(6, i, String.valueOf(hasBack), format31));
			ws.addCell(new Label(7, i, String.valueOf(MathTools.formatNum2(backMoney)), format31));
			ws.addCell(new Label(8, i, String.valueOf(noPayAmount), format31));	
			ws.addCell(new Label(9, i, String.valueOf(MathTools.formatNum2(noPayMoney)), format31));

			i++;

			ws.addCell(new Label(0, i, TimeTools.changeFormat(
					TimeTools.changeTimeToDate(bean.getStatsStar()),
					"yyyy-MM-dd", "yyyy年MM月dd日") + "至"
					+ TimeTools.changeFormat(
							TimeTools.changeTimeToDate(bean.getStatsEnd()),
							"yyyy-MM-dd", "yyyy年MM月dd日") + " 应收合计：", format3));

			ws.mergeCells(0, i, 2, i);
			setWS(ws, i, 300, false);

			ws.addCell(new Label(3, i, String.valueOf(MathTools.formatNum2(noPayMoney)), format31));

			ws.mergeCells(3, i, 9, i);

			i++;

			ws.addCell(new Label(0, i, "预收款余额：", format3));

			ws.mergeCells(0, i, 2, i);
			setWS(ws, i, 300, false);

			ws.addCell(new Label(3, i, String.valueOf(MathTools.formatNum2(getPreMoney(bean
					.getCustomerId()))), format31));

			ws.mergeCells(3, i, 9, i);

			i++;
			ws.addCell(new Label(0, i, content5, format2));
			setWS(ws, i, 300, true);

			i++;
			ws.addCell(new Label(0, i, content6, format2));
			setWS(ws, i, 300, true);
			i++;

			ws.addCell(new Label(0, i, "永银文化创意产业发展有限责任公司", format21));
			setWS(ws, i, 300, true);

			i++;
			ws.addCell(new Label(0, i, TimeTools.changeFormat(
					TimeTools.changeTimeToDate(TimeTools.now()), "yyyy-MM-dd",
					"yyyy年MM月dd日"), format21));
			setWS(ws, i, 300, true);

			i++;
			ws.addCell(new Label(0, i, "结论:", format4));
			setWS(ws, i, 300, true);

			i++;
			ws.addCell(new Label(0, i, "1. 信息证明无误。", format4));
			setWS(ws, i, 300, false);
			ws.mergeCells(0, i, 2, i);
			ws.addCell(new Label(3, i, "2．信息不符，请列明不符项目及具体内容。", format4));

			ws.mergeCells(3, i, 9, i);

			i++;
			ws.addCell(new Label(0, i, "", format4));
			setWS(ws, i, 1600, false);
			ws.mergeCells(0, i, 2, i);

			ws.addCell(new Label(3, i, "", format4));

			ws.mergeCells(3, i, 9, i);

			i++;
			ws.addCell(new Label(0, i, "（ 公司盖章）", format41));

			ws.mergeCells(0, i, 2, i);
			setWS(ws, i, 800, false);

			ws.addCell(new Label(3, i, "（ 公司盖章）", format41));

			ws.mergeCells(3, i, 9, i);

			i++;
			// 空行

			i++;
			ws.addCell(new Label(0, i, "年   月  日 ", format41));

			ws.mergeCells(0, i, 2, i);
			setWS(ws, i, 500, false);

			ws.addCell(new Label(3, i, "年   月  日 ", format41));

			ws.mergeCells(3, i, 9, i);

			i++;
			// 空行
			i++;
			ws.addCell(new Label(0, i, "经办人 ", format41));

			ws.mergeCells(0, i, 2, i);
			setWS(ws, i, 500, false);

			ws.addCell(new Label(3, i, "经办人 ", format41));

			ws.mergeCells(3, i, 9, i);
		}
		catch (Throwable e)
		{
			_logger.error(e, e);
		}
		finally
		{
			if (wwb != null)
			{
				try
				{
					wwb.write();
					wwb.close();
				}
				catch (Exception e1)
				{
				}
			}
			if (out != null)
			{
				try
				{
					out.close();
				}
				catch (IOException e1)
				{
				}
			}
		}
	}

	private void setWS(WritableSheet ws, int i, int rowHeight, boolean mergeCell)
			throws WriteException, RowsExceededException
	{
		if (mergeCell) ws.mergeCells(0, i, 9, i);

		ws.setRowView(i, rowHeight);
	}

	private List<FeedBackDetailBean> merge(List<FeedBackDetailBean> list)
	{
		List<FeedBackDetailBean> mergeList = new ArrayList<FeedBackDetailBean>();

		Map<String, FeedBackDetailBean> map = new HashMap<String, FeedBackDetailBean>();

		for (FeedBackDetailBean each : list)
		{
			if (!map.containsKey(each.getProductId()))
			{
				map.put(each.getProductId(), each);
			}
			else
			{
				FeedBackDetailBean detailBean = map.get(each.getProductId());

				detailBean.setAmount(detailBean.getAmount() + each.getAmount());
				
				detailBean.setHasBack(detailBean.getHasBack()+ each.getHasBack());
				
				detailBean.setMoney(detailBean.getMoney() + each.getMoney());
				
				detailBean.setBackMoney(detailBean.getBackMoney() + each.getBackMoney());
				
				detailBean.setNoPayAmount(detailBean.getNoPayAmount() + each.getNoPayAmount());
				
				detailBean.setNoPayMoneys(detailBean.getNoPayMoneys() + each.getNoPayMoneys());
			}
		}

		Collection<FeedBackDetailBean> lastList = map.values();

		for (FeedBackDetailBean each : lastList)
		{
			mergeList.add(each);
		}

		return mergeList;
	}

	private double getPreMoney(String customerId)
	{
		ConditionParse con = new ConditionParse();

		con.addWhereStr();

		con.addCondition("InBillBean.customerId", "=", customerId);

		con.addIntCondition("InBillBean.status", "=",
				FinanceConstant.INBILL_STATUS_NOREF);

		return inBillDAO.sumByCondition(con);

	}

	/**
	 * @return the mailAttchmentPath
	 */
	public String getFeedBackCheckPath()
	{
		return ConfigLoader.getProperty("feedbackCheck");
	}

	public PlatformTransactionManager getTransactionManager()
	{
		return transactionManager;
	}

	public void setTransactionManager(
			PlatformTransactionManager transactionManager)
	{
		this.transactionManager = transactionManager;
	}

	public OutDAO getOutDAO()
	{
		return outDAO;
	}

	public void setOutDAO(OutDAO outDAO)
	{
		this.outDAO = outDAO;
	}

	public BaseDAO getBaseDAO()
	{
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO)
	{
		this.baseDAO = baseDAO;
	}

	public FeedBackDAO getFeedBackDAO()
	{
		return feedBackDAO;
	}

	public void setFeedBackDAO(FeedBackDAO feedBackDAO)
	{
		this.feedBackDAO = feedBackDAO;
	}

	public FeedBackCheckDAO getFeedBackCheckDAO()
	{
		return feedBackCheckDAO;
	}

	public void setFeedBackCheckDAO(FeedBackCheckDAO feedBackCheckDAO)
	{
		this.feedBackCheckDAO = feedBackCheckDAO;
	}

	public FeedBackDetailDAO getFeedBackDetailDAO()
	{
		return feedBackDetailDAO;
	}

	public void setFeedBackDetailDAO(FeedBackDetailDAO feedBackDetailDAO)
	{
		this.feedBackDetailDAO = feedBackDetailDAO;
	}

	public FeedBackVisitDAO getFeedBackVisitDAO()
	{
		return feedBackVisitDAO;
	}

	public void setFeedBackVisitDAO(FeedBackVisitDAO feedBackVisitDAO)
	{
		this.feedBackVisitDAO = feedBackVisitDAO;
	}

	public FeedBackVSOutDAO getFeedBackVSOutDAO()
	{
		return feedBackVSOutDAO;
	}

	public void setFeedBackVSOutDAO(FeedBackVSOutDAO feedBackVSOutDAO)
	{
		this.feedBackVSOutDAO = feedBackVSOutDAO;
	}

	public OutBalanceDAO getOutBalanceDAO()
	{
		return outBalanceDAO;
	}

	public void setOutBalanceDAO(OutBalanceDAO outBalanceDAO)
	{
		this.outBalanceDAO = outBalanceDAO;
	}

	public BaseBalanceDAO getBaseBalanceDAO()
	{
		return baseBalanceDAO;
	}

	public void setBaseBalanceDAO(BaseBalanceDAO baseBalanceDAO)
	{
		this.baseBalanceDAO = baseBalanceDAO;
	}

	public CommonDAO getCommonDAO()
	{
		return commonDAO;
	}

	public void setCommonDAO(CommonDAO commonDAO)
	{
		this.commonDAO = commonDAO;
	}

	public StafferDAO getStafferDAO()
	{
		return stafferDAO;
	}

	public void setStafferDAO(StafferDAO stafferDAO)
	{
		this.stafferDAO = stafferDAO;
	}

	public FeedBackVisitItemDAO getFeedBackVisitItemDAO()
	{
		return feedBackVisitItemDAO;
	}

	public void setFeedBackVisitItemDAO(
			FeedBackVisitItemDAO feedBackVisitItemDAO)
	{
		this.feedBackVisitItemDAO = feedBackVisitItemDAO;
	}

	public InBillDAO getInBillDAO()
	{
		return inBillDAO;
	}

	public void setInBillDAO(InBillDAO inBillDAO)
	{
		this.inBillDAO = inBillDAO;
	}

	public CommonMailManager getCommonMailManager()
	{
		return commonMailManager;
	}

	public void setCommonMailManager(CommonMailManager commonMailManager)
	{
		this.commonMailManager = commonMailManager;
	}

	public DistributionDAO getDistributionDAO()
	{
		return distributionDAO;
	}

	public void setDistributionDAO(DistributionDAO distributionDAO)
	{
		this.distributionDAO = distributionDAO;
	}

	public StafferVSCustomerDAO getStafferVSCustomerDAO()
	{
		return stafferVSCustomerDAO;
	}

	public void setStafferVSCustomerDAO(
			StafferVSCustomerDAO stafferVSCustomerDAO)
	{
		this.stafferVSCustomerDAO = stafferVSCustomerDAO;
	}

	public OutRepaireDAO getOutRepaireDAO()
	{
		return outRepaireDAO;
	}

	public void setOutRepaireDAO(OutRepaireDAO outRepaireDAO)
	{
		this.outRepaireDAO = outRepaireDAO;
	}
}
