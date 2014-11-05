<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

<tr class="content1" id="trCopy_share" style="display: none;">
         <td align="left"><input type="text" style="width: 100%;cursor: pointer;"
                    name="partsDetail" value=""  readonly="readonly" onclick="selectBudget(this)">
         </td>
         
         <td align="left">
         <input type="text" style="width: 100%"
                    name="partsCount" value=""  readonly="readonly">
         </td>
         
         <td width="5%" align="center"><input type=button
            value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>
    </tr>