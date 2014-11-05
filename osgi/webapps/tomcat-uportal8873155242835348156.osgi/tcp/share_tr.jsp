<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

<tr class="content1" id="trCopy_share" style="display: none;">
         <td align="left"><input type="text" style="width: 100%;cursor: pointer;"
                    name="s_budgetName" value="" oncheck="notNone;" readonly="readonly" onclick="selectBudget(this)">
         <input type="hidden" name="s_budgetId" value=""> 
         </td>
         
         <td align="left">
         <input type="text" style="width: 100%"
                    name="s_departmentName" value="" oncheck="notNone;" readonly="readonly">
         <input type="hidden" name="s_departmentId" value=""> 
         </td>
         
         <td align="left">
         <input type="text" style="width: 100%"
                    name="s_approverName" value="" oncheck="notNone;" readonly="readonly">
         <input type="hidden" name="s_approverId" value=""> 
         </td>
         
         <td align="left">
         <input type="text" style="width: 100%;cursor: pointer;" title="不选择默认为提交人"
                    name="s_bearName" value="" readonly="readonly" onclick="selectStaffer2(this)">
         <input type="hidden" name="s_bearId" value=""> 
         </td>
         
         <td align="left">
         <input type="text" style="width: 100%"
                    name="s_ratio" value="" oncheck="notNone;isFloat2">
         </td>
         
        <td width="5%" align="center"><input type=button
            value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>
    </tr>