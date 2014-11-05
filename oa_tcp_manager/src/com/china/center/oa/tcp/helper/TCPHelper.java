/**
 * File Name: TCPHlper.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.helper;


import java.text.DecimalFormat;
import java.util.List;

import com.china.center.common.taglib.DefinedCommon;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.vo.FlowLogVO;
import com.china.center.oa.tcp.bean.AbstractTcpBean;
import com.china.center.oa.tcp.bean.ExpenseApplyBean;
import com.china.center.oa.tcp.bean.TravelApplyBean;
import com.china.center.oa.tcp.constanst.TcpConstanst;
import com.china.center.oa.tcp.constanst.TcpFlowConstant;
import com.china.center.oa.tcp.vo.ExpenseApplyVO;
import com.china.center.oa.tcp.vo.RebateApplyVO;
import com.china.center.oa.tcp.vo.TcpApproveVO;
import com.china.center.oa.tcp.vo.TcpHandleHisVO;
import com.china.center.oa.tcp.vo.TcpShareVO;
import com.china.center.oa.tcp.vo.TravelApplyVO;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.MathTools;
import com.china.center.tools.StringTools;


/**
 * TCPHlper
 * 
 * @author ZHUZHU
 * @version 2011-7-17
 * @see TCPHelper
 * @since 3.0
 */
public abstract class TCPHelper
{
    /**
     * 设置报销的key(CORE)
     * 
     * @param bean
     */
    public static void setFlowKey(AbstractTcpBean bean)
    {
    	if (bean.getType() == TcpConstanst.TCP_APPLYTYPE_MID) {
    		bean.setFlowKey(TcpFlowConstant.TRAVELAPPLY_0_5000);
    		
    		return;
    	}
    	
    	if (bean.getType() == TcpConstanst.TCP_EXPENSETYPE_MID) {
    		bean.setFlowKey(TcpFlowConstant.TRAVELAPPLY_MID);
    		
    		return;
    	}
    	
        if (bean.getType() != TcpConstanst.TCP_APPLYTYPE_STOCK)
        {
            if (bean.getStype() == TcpConstanst.TCP_STYPE_SAIL)
            {
                if (bean.getTotal() <= 500000)
                {
                    bean.setFlowKey(TcpFlowConstant.TRAVELAPPLY_0_5000);

                    return;
                }

                if (bean.getTotal() > 500000 && bean.getTotal() <= 1000000)
                {
                    bean.setFlowKey(TcpFlowConstant.TRAVELAPPLY_5000_10000);

                    return;
                }

                if (bean.getTotal() > 1000000 && bean.getTotal() <= 5000000)
                {
                    bean.setFlowKey(TcpFlowConstant.TRAVELAPPLY_10000_50000);

                    return;
                }

                if (bean.getTotal() > 5000000)
                {
                    bean.setFlowKey(TcpFlowConstant.TRAVELAPPLY_50000_MAX);

                    return;
                }
            }

            if (bean.getStype() == TcpConstanst.TCP_STYPE_WORK)
            {
                if (bean.getTotal() <= 5000000)
                {
                    bean.setFlowKey(TcpFlowConstant.WORK_APPLY_0_50000);

                    return;
                }
                else
                {
                    bean.setFlowKey(TcpFlowConstant.WORK_APPLY_50000_MAX);

                    return;
                }
            }

            if (bean.getStype() == TcpConstanst.TCP_STYPE_MANAGER)
            {
                if (bean.getTotal() <= 5000000)
                {
                    bean.setFlowKey(TcpFlowConstant.MANAGER_APPLY_0_50000);

                    return;
                }
                else
                {
                    bean.setFlowKey(TcpFlowConstant.MANAGER_APPLY_50000_MAX);

                    return;
                }
            }
        }
        else
        {
            if (bean.getStype() == TcpConstanst.TCP_STYPE_SAIL)
            {
                if (bean.getTotal() <= 500000)
                {
                    bean.setFlowKey(TcpFlowConstant.STOCK_APPLY_0_5000);

                    return;
                }

                if (bean.getTotal() > 500000 && bean.getTotal() <= 1000000)
                {
                    bean.setFlowKey(TcpFlowConstant.STOCK_APPLY_5000_10000);

                    return;
                }

                if (bean.getTotal() > 1000000 && bean.getTotal() <= 5000000)
                {
                    bean.setFlowKey(TcpFlowConstant.STOCK_APPLY_10000_50000);

                    return;
                }

                if (bean.getTotal() > 5000000)
                {
                    bean.setFlowKey(TcpFlowConstant.STOCK_APPLY_50000_MAX);

                    return;
                }
            }

            if (bean.getStype() == TcpConstanst.TCP_STYPE_WORK)
            {
                if (bean.getTotal() <= 5000000)
                {
                    bean.setFlowKey(TcpFlowConstant.WORK_STOCK_APPLY_0_50000);

                    return;
                }
                else
                {
                    bean.setFlowKey(TcpFlowConstant.WORK_STOCK_APPLY_50000_MAX);

                    return;
                }
            }

            if (bean.getStype() == TcpConstanst.TCP_STYPE_MANAGER)
            {
                if (bean.getTotal() <= 5000000)
                {
                    bean.setFlowKey(TcpFlowConstant.MANAGER_STOCK_APPLY_0_50000);

                    return;
                }
                else
                {
                    bean.setFlowKey(TcpFlowConstant.MANAGER_STOCK_APPLY_50000_MAX);

                    return;
                }
            }
        }
    }

    /**
     * doubleToLong(到分)
     * 
     * @param value
     * @return
     */
    public static long doubleToLong2(String value)
    {
        if (StringTools.isNullOrNone(value))
        {
            return 0L;
        }

        // 先格式转成double
        double parseDouble = MathTools.parseDouble(value);

        return Math.round(MathTools.parseDouble(formatNum2(parseDouble)) * 100);
    }

    public static long doubleToLong2(double value)
    {
        return Math.round(MathTools.parseDouble(formatNum2(value)) * 100);
    }

    public static String longToDouble2(long value)
    {
        return TCPHelper.formatNum2(value / 100.0d);
    }

    public static void chageVO(TravelApplyVO vo)
    {
        vo.setShowTotal(formatNum2(vo.getTotal() / 100.0d));
        vo.setShowBorrowTotal(formatNum2(vo.getBorrowTotal() / 100.0d));

        vo.setShowAirplaneCharges(formatNum2(vo.getAirplaneCharges() / 100.0d));
        vo.setShowTrainCharges(formatNum2(vo.getTrainCharges() / 100.0d));

        vo.setShowBusCharges(formatNum2(vo.getBusCharges() / 100.0d));
        vo.setShowHotelCharges(formatNum2(vo.getHotelCharges() / 100.0d));

        vo.setShowEntertainCharges(formatNum2(vo.getEntertainCharges() / 100.0d));
        vo.setShowAllowanceCharges(formatNum2(vo.getAllowanceCharges() / 100.0d));

        vo.setShowOther1Charges(formatNum2(vo.getOther1Charges() / 100.0d));
        vo.setShowOther2Charges(formatNum2(vo.getOther2Charges() / 100.0d));
    }

    public static void chageVO(ExpenseApplyVO vo)
    {
        vo.setShowTotal(formatNum2(vo.getTotal() / 100.0d));
        vo.setShowBorrowTotal(formatNum2(vo.getBorrowTotal() / 100.0d));

        vo.setShowAirplaneCharges(formatNum2(vo.getAirplaneCharges() / 100.0d));
        vo.setShowTrainCharges(formatNum2(vo.getTrainCharges() / 100.0d));

        vo.setShowBusCharges(formatNum2(vo.getBusCharges() / 100.0d));
        vo.setShowHotelCharges(formatNum2(vo.getHotelCharges() / 100.0d));

        vo.setShowEntertainCharges(formatNum2(vo.getEntertainCharges() / 100.0d));
        vo.setShowAllowanceCharges(formatNum2(vo.getAllowanceCharges() / 100.0d));

        vo.setShowOther1Charges(formatNum2(vo.getOther1Charges() / 100.0d));
        vo.setShowOther2Charges(formatNum2(vo.getOther2Charges() / 100.0d));
    }

    /**
     * 是否可以删除
     * 
     * @param bean
     * @return
     */
    public static boolean canTravelApplyDelete(AbstractTcpBean bean)
    {
        if (bean.getStatus() == TcpConstanst.TCP_STATUS_INIT
            || bean.getStatus() == TcpConstanst.TCP_STATUS_REJECT)
        {
            return true;
        }

        return false;
    }


    /**
     * canTravelApplyUpdate
     * 
     * @param bean
     * @return
     */
    public static boolean canTravelApplyUpdate(AbstractTcpBean bean)
    {
        return canTravelApplyDelete(bean);
    }

    /**
     * 是否是初始状态
     * 
     * @param bean
     * @return
     */
    public static boolean isTravelApplyInit(TravelApplyBean bean)
    {
        if (bean.getStatus() == TcpConstanst.TCP_STATUS_INIT
            || bean.getStatus() == TcpConstanst.TCP_STATUS_REJECT)
        {
            return true;
        }

        return false;
    }

    /**
     * 是否是初始状态
     * 
     * @param status
     * @return
     */
    public static boolean isTravelApplyInit(int status)
    {
        if (status == TcpConstanst.TCP_STATUS_INIT || status == TcpConstanst.TCP_STATUS_REJECT)
        {
            return true;
        }

        return false;
    }

    public static String formatNum2(double d)
    {
        DecimalFormat df = new DecimalFormat("####0.00");

        String result = df.format(d);

        if (".00".equals(result))
        {
            result = "0" + result;
        }

        return result;
    }

    /**
     * getTcpApproveVO
     * 
     * @param vo
     */
    public static void getTcpApproveVO(TcpApproveVO vo)
    {
        vo.setShowTotal(MathTools.formatNum(MathTools.longToDouble2(vo.getTotal())));
        vo.setShowCheckTotal(MathTools.formatNum(MathTools.longToDouble2(vo.getCheckTotal())));

        if (vo.getType() <= 10)
        {
            vo.setUrl(TcpConstanst.TCP_TRAVELAPPLY_PROCESS_URL + vo.getApplyId());
        }
        else if (vo.getType() > 10 && vo.getType() <= 20)
        {
            vo.setUrl(TcpConstanst.TCP_EXPENSE_PROCESS_URL + vo.getApplyId());
        }else if (vo.getType() == 21)
        {
        	vo.setUrl(TcpConstanst.REBATE_PROCESS_URL + vo.getApplyId());
        } else if (vo.getType() == 22) {
        	vo.setUrl(TcpConstanst.PREINVOICE_PROCESS_URL + vo.getApplyId());
        }
        else{
        	vo.setUrl(TcpConstanst.BACKPREPAY_PROCESS_URL + vo.getApplyId());
        }
    }
    

    /**
     * getTcpApproveVO
     * 
     * @param vo
     */
    public static void getTcpHandleHisVO(TcpHandleHisVO vo)
    {
        if (vo.getType() <= 10)
        {
            vo.setUrl(TcpConstanst.TCP_TRAVELAPPLY_DETAIL_URL + vo.getRefId());
        }
        else if (vo.getType() > 10 && vo.getType() <= 20)
        {
            vo.setUrl(TcpConstanst.TCP_EXPENSE_DETAIL_URL + vo.getRefId());
        }else if (vo.getType() == 21)
        {
        	vo.setUrl(TcpConstanst.REBATE_DETAIL_URL + vo.getRefId());
        }else if (vo.getType() == 22){
        	vo.setUrl(TcpConstanst.PREINVOICE_DETAIL_URL + vo.getRefId());
        } else {
        	vo.setUrl(TcpConstanst.BACKPREPAY_DETAIL_URL + vo.getRefId());
        }
    }

    public static FlowLogVO getTCPFlowLogVO(FlowLogBean bean)
    {
        FlowLogVO vo = new FlowLogVO();

        if (bean == null)
        {
            return vo;
        }

        BeanUtil.copyProperties(vo, bean);

        vo.setOprModeName(DefinedCommon.getValue("oprMode", vo.getOprMode()));

        vo.setPreStatusName(DefinedCommon.getValue("tcpStatus", vo.getPreStatus()));

        vo.setAfterStatusName(DefinedCommon.getValue("tcpStatus", vo.getAfterStatus()));

        return vo;
    }

    public static boolean isTemplateExpense(ExpenseApplyBean bean)
    {
        if (bean.getType() == TcpConstanst.TCP_EXPENSETYPE_COMMON
            && bean.getSpecialType() == TcpConstanst.SPECIALTYPE_TEMPLATE_YES)
        {
            return true;
        }

        return false;
    }

    /**
     * getShareRatio
     * 
     * @param shareVOList
     * @param vo
     * @return
     */
    public static double getShareRatio(List<TcpShareVO> shareVOList, TcpShareVO vo)
    {
        long total = 0L;

        for (TcpShareVO tcpShareVO : shareVOList)
        {
            total += tcpShareVO.getRealMonery();
        }

        return ((double)vo.getRealMonery() * 100.0d) / (double)total;
    }

    /**
     * ratioShare
     * 
     * @param shareVOList
     */
    public static void ratioShare(List<TcpShareVO> shareVOList)
    {
        if (shareVOList.get(0).getRatio() != 0)
        {
            return;
        }

        long total = 0L;

        for (TcpShareVO tcpShareVO : shareVOList)
        {
            total += tcpShareVO.getRealMonery();
        }

        long totalRatio = 100;

        for (TcpShareVO tcpShareVO : shareVOList)
        {
            long ratio = (tcpShareVO.getRealMonery() * 100) / total;

            if (totalRatio < ratio)
            {
            	totalRatio = 0;
            }

            if (totalRatio >= ratio)
            {
                tcpShareVO.setRatio((int)ratio);
                
                totalRatio -= ratio;
            }
            else
            {
                tcpShareVO.setRatio((int)totalRatio);
                break;
            }
        }
    }

    public static long ratioValue(long value, int ratio)
    {
        return value * ratio / 100;
    }
    
    public static void chageVO(RebateApplyVO vo)
    {
        vo.setShowTotal(formatNum2(vo.getTotal() / 100.0d));
        vo.setShowLastMoney(formatNum2(vo.getLastMoney() / 100.0d));
    }
}
