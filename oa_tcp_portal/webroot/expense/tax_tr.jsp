<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

<tr class="content1" id="taxCopy" style="display: none;">
         <td width="50%" align="center">
         <input name="taxName" type="text" readonly="readonly" style="width: 100%;cursor: pointer;" oncheck="notNone" onclick="selectTax(this)">
         <input type="hidden" name="taxId" value=""> 
         </td>
         <td width="20%" align="center"><input type="text" style="width: 100%"
                    name="t_money" value="" oncheck="notNone;isFloat">
         </td>
          <td width="50%" align="center">
              <input name="taxStafferName" type="text" readonly="readonly"
              value=""
               style="width: 100%;cursor: pointer;" oncheck="notNone" onclick="selectTaxStaffer(this)">
              <input type="hidden" name="taxStafferId" value=""> 
          </td>
         <td width="5%" align="center"><input type=button name="tax_del_bu"
            value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>
    </tr>
    