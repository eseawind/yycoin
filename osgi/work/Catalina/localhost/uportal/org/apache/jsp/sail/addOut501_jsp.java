package org.apache.jsp.sail;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class addOut501_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(5);
    _jspx_dependants.add("/sail/../common/common.jsp");
    _jspx_dependants.add("/WEB-INF/tld/pageTld.tld");
    _jspx_dependants.add("/WEB-INF/tld/common.tld");
    _jspx_dependants.add("/sail/../sail_js/out501.jsp");
    _jspx_dependants.add("/sail/../sail_js/../common/common.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fp_005flink_005ftitle_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_005fvar_005fitems;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_005ftest;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fp_005fnavigation_005fheight;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fp_005foption_005ftype_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fp_005foption_005ftype_005fempty_005fnobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fp_005flink_005ftitle_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fforEach_005fvar_005fitems = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fif_005ftest = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fp_005fnavigation_005fheight = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fp_005foption_005ftype_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fp_005foption_005ftype_005fempty_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fp_005flink_005ftitle_005fnobody.release();
    _005fjspx_005ftagPool_005fc_005fforEach_005fvar_005fitems.release();
    _005fjspx_005ftagPool_005fc_005fif_005ftest.release();
    _005fjspx_005ftagPool_005fp_005fnavigation_005fheight.release();
    _005fjspx_005ftagPool_005fp_005foption_005ftype_005fnobody.release();
    _005fjspx_005ftagPool_005fp_005foption_005ftype_005fempty_005fnobody.release();
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    JspFactory _jspxFactory = null;
    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      _jspxFactory = JspFactory.getDefaultFactory();
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			"../common/error.jsp", true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      if (_jspx_meth_p_005flink_005f0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("<script language=\"JavaScript\" src=\"../js/common.js\"></script>\r\n");
      out.write("<script language=\"JavaScript\" src=\"../js/math.js\"></script>\r\n");
      out.write("<script language=\"JavaScript\" src=\"../js/public.js\"></script>\r\n");
      out.write("<script language=\"JavaScript\" src=\"../js/cnchina.js\"></script>\r\n");
      out.write("<script language=\"JavaScript\" src=\"../js/JCheck.js\"></script>\r\n");
      out.write("<script language=\"JavaScript\" src=\"../js/compatible.js\"></script>\r\n");
      out.write("<script language=\"JavaScript\" src=\"../js/jquery/jquery.js\"></script>\r\n");
      out.write("<script language=\"JavaScript\" src=\"../js/json.js\"></script>\r\n");
      out.write("<script language=\"JavaScript\" src=\"../sail_js/addOut50.js\"></script>\r\n");
      out.write("<script language=\"javascript\">\r\n");
      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("//当前的焦点对象\r\n");
      out.write("var oo;\r\n");
      out.write("var ids = '';\r\n");
      out.write("var amous = '';\r\n");
      out.write("var tsts;\r\n");
      out.write("var messk = '';\r\n");
      out.write("var locationId = '");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${currentLocationId}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("';\r\n");
      out.write("var currentLocationId = '");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${currentLocationId}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("';\r\n");
      out.write("var obj;\r\n");
      out.write("\r\n");
      out.write("var showJSON = JSON.parse('");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${showJSON}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("');\r\n");
      out.write("\r\n");
      out.write("var invoicesJSON = JSON.parse('");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${invoicesJSON}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("');\r\n");
      out.write("\r\n");
      out.write("var vsJSON = JSON.parse('");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${vsJSON}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("');\r\n");
      out.write("\r\n");
      out.write("function selectCustomer()\r\n");
      out.write("{\r\n");
      out.write("    window.common.modal(\"../client/client.do?method=rptQuerySelfClient&stafferId=");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${user.stafferId}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("&load=1\");\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("var invMap = {};\r\n");
      out.write("var invFullMap = {};\r\n");
      if (_jspx_meth_c_005fforEach_005f0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\r\n");
      if (_jspx_meth_c_005fforEach_005f1(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\r\n");
      out.write("//默认黑名单\r\n");
      out.write("var BLACK_LEVEL = '90000000000000000000';\r\n");
      out.write("\r\n");
      out.write("function getCustomer(oos)\r\n");
      out.write("{\r\n");
      out.write("    obj = oos;\r\n");
      out.write("    \r\n");
      out.write("    if ($$('outType') == 3 && obj.pcreditlevelid == BLACK_LEVEL)\r\n");
      out.write("    {\r\n");
      out.write("        alert('委托代销的时候不能选择黑名单用户');\r\n");
      out.write("        \r\n");
      out.write("        return;\r\n");
      out.write("    }\r\n");
      out.write("    \r\n");
      out.write("    if ($$('outType') == 4 && obj.pcreditlevelid == BLACK_LEVEL)\r\n");
      out.write("    {\r\n");
      out.write("        alert('赠送的时候不能选择黑名单用户');\r\n");
      out.write("        \r\n");
      out.write("        return;\r\n");
      out.write("    }\r\n");
      out.write("    \r\n");
      out.write("    $O(\"connector\").value = obj.pconnector;\r\n");
      out.write("    $O(\"phone\").value = obj.phandphone;\r\n");
      out.write("    $O(\"customerName\").value = obj.pname;\r\n");
      out.write("    $O(\"customerId\").value = obj.value;\r\n");
      out.write("    $O(\"customercreditlevel\").value = obj.pcreditlevelid;\r\n");
      out.write("    \r\n");
      out.write("    if (obj.pcreditlevelid == BLACK_LEVEL || $$('outType') == 2 || ");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${staffer.black}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write(" == 1)\r\n");
      out.write("    {\r\n");
      out.write("        removeAllItem($O('reserve3'));\r\n");
      out.write("        \r\n");
      out.write("        setOption($O('reserve3'), '1', '款到发货(黑名单客户)'); \r\n");
      out.write("        \r\n");
      out.write("        return;\r\n");
      out.write("    }\r\n");
      out.write("    \r\n");
      out.write("    if ($$('outType') == 4)\r\n");
      out.write("    {\r\n");
      out.write("        resetReserve3_ZS(); \r\n");
      out.write("    }\r\n");
      out.write("    else\r\n");
      out.write("    {\r\n");
      out.write("        resetReserve3();\r\n");
      out.write("    }\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function resetReserve3()\r\n");
      out.write("{\r\n");
      out.write("    removeAllItem($O('reserve3'));\r\n");
      out.write("        \r\n");
      out.write("    setOption($O('reserve3'), '2', '客户信用和业务员信用额度担保');  \r\n");
      out.write("    setOption($O('reserve3'), '3', '信用担保');  \r\n");
      out.write("    setOption($O('reserve3'), '1', '款到发货(黑名单客户/零售)');  \r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function resetReserve3_ZS()\r\n");
      out.write("{\r\n");
      out.write("    removeAllItem($O('reserve3'));\r\n");
      out.write("        \r\n");
      out.write("    setOption($O('reserve3'), '2', '客户信用和业务员信用额度担保');  \r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function total()\r\n");
      out.write("{\r\n");
      out.write("    var obj = document.getElementsByName(\"value\");\r\n");
      out.write("\r\n");
      out.write("    var total = 0;\r\n");
      out.write("    for (var i = 1; i < obj.length; i++)\r\n");
      out.write("    {\r\n");
      out.write("        if (obj[i].value != '')\r\n");
      out.write("        {\r\n");
      out.write("            total = add(total, parseFloat(obj[i].value));\r\n");
      out.write("        }\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    var ss =  document.getElementById(\"total\");\r\n");
      out.write("    tsts = formatNum(total, 2);\r\n");
      out.write("    ss.innerHTML = '(总计:' + tsts + ')';\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function titleChange()\r\n");
      out.write("{\r\n");
      out.write("    \r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("function check(isAdd)\r\n");
      out.write("{\r\n");
      out.write("    var amountList = document.getElementsByName('amount');\r\n");
      out.write("    \r\n");
      out.write("    if (isAdd)\r\n");
      out.write("    {\r\n");
      out.write("        for (var i = 0; i < amountList.length; i++)\r\n");
      out.write("        {\r\n");
      out.write("            amountList[i].ignorecheck = 'true';\r\n");
      out.write("        }\r\n");
      out.write("    }\r\n");
      out.write("    else\r\n");
      out.write("    {\r\n");
      out.write("         for (var i = 0; i < amountList.length; i++)\r\n");
      out.write("        {\r\n");
      out.write("            amountList[i].ignorecheck = 'false';\r\n");
      out.write("        }\r\n");
      out.write("    }\r\n");
      out.write("    \r\n");
      out.write("    if (!formCheck())\r\n");
      out.write("    {\r\n");
      out.write("        return false;\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    if ($$('outType') != 0)\r\n");
      out.write("    {\r\n");
      out.write("\t    var lock_sw = false;\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tlock_sw = '");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${lock_sw}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("';\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif (lock_sw)\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\t//alert('领样转销售,只能是销售出库.');\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t//return;\r\n");
      out.write("\t\t}\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    ids = '';\r\n");
      out.write("    amous = '';\r\n");
      out.write("    \r\n");
      out.write("    $O('priceList').value = '';\r\n");
      out.write("    $O('inputPriceList').value = '';\r\n");
      out.write("    $O('totalList').value = '';\r\n");
      out.write("    $O('nameList').value = '';\r\n");
      out.write("    \r\n");
      out.write("    $O('otherList').value = '';\r\n");
      out.write("    \r\n");
      out.write("    $O('desList').value = '';\r\n");
      out.write("    $O('showCostList').value = '';\r\n");
      out.write("    \r\n");
      out.write("    $O('depotList').value = '';\r\n");
      out.write("    $O('mtypeList').value = '';\r\n");
      out.write("    $O('oldGoodsList').value = '';\r\n");
      out.write("    $O('taxrateList').value = '';\r\n");
      out.write("    $O('taxList').value = '';\r\n");
      out.write("    $O('inputRateList').value = '';\r\n");
      out.write("    \r\n");
      out.write("    if (trim($O('outTime').value) == '')\r\n");
      out.write("    {\r\n");
      out.write("        alert('请选择销售日期');\r\n");
      out.write("        return false;\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    if ($$('outType') == '')\r\n");
      out.write("    {\r\n");
      out.write("        alert('请选择库单类型');\r\n");
      out.write("        return false;\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("\tif ($$('outType') == '4')\r\n");
      out.write("    {\r\n");
      out.write("    \tif ($$('presentFlag') == ''){\r\n");
      out.write("    \t   alert('赠送单据须选择赠送类型');\r\n");
      out.write("           return false;\r\n");
      out.write("    \t}\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    if ($O('customerId').value == '')\r\n");
      out.write("    {\r\n");
      out.write("        alert('请选择客户');\r\n");
      out.write("        return false;\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    if ($$('department') == '')\r\n");
      out.write("    {\r\n");
      out.write("        alert('请选择销售部门');\r\n");
      out.write("        return false;\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    if (!eCheck([$O('reday')]))\r\n");
      out.write("    {\r\n");
      out.write("        alert('请填入1到180之内的数字');\r\n");
      out.write("        $O('reday').focus();\r\n");
      out.write("        return false;\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    var proNames = document.getElementsByName('productName');\r\n");
      out.write("\r\n");
      out.write("    var amounts = document.getElementsByName('amount');\r\n");
      out.write("\r\n");
      out.write("    var prices = document.getElementsByName('price');\r\n");
      out.write("    \r\n");
      out.write("    var taxrates = document.getElementsByName('taxrate');\r\n");
      out.write("    \r\n");
      out.write("    var taxs = document.getElementsByName('tax');\r\n");
      out.write("    \r\n");
      out.write("    var values = document.getElementsByName('value');\r\n");
      out.write("\r\n");
      out.write("    //成本\r\n");
      out.write("    var desList = document.getElementsByName('costPrice');\r\n");
      out.write("    \r\n");
      out.write("    var desciprtList = document.getElementsByName('desciprt');\r\n");
      out.write("    \r\n");
      out.write("    var depotList = document.getElementsByName('locationIds');\r\n");
      out.write("\r\n");
      out.write("    var tmpMap = {};\r\n");
      out.write("    \r\n");
      out.write("    var proName = '';\r\n");
      out.write("    \r\n");
      out.write("    for (var i = 1; i < depotList.length; i++)\r\n");
      out.write("    {\r\n");
      out.write("    \tif (depotList[i].value == '')\r\n");
      out.write("    \t{\r\n");
      out.write("    \t\talert('数据不完整,请选择产品仓库!');\r\n");
      out.write("            \r\n");
      out.write("            return false;\r\n");
      out.write("    \t}\r\n");
      out.write("    \t\r\n");
      out.write("        $O('depotList').value = $O('depotList').value + depotList[i].value + '~';\r\n");
      out.write("    }\r\n");
      out.write("    \r\n");
      out.write("    //isNumbers\r\n");
      out.write("    for (var i = 1; i < proNames.length; i++)\r\n");
      out.write("    {\r\n");
      out.write("    \t\r\n");
      out.write("        if (proNames[i].value == '' || proNames[i].productid == '')\r\n");
      out.write("        {\r\n");
      out.write("            alert('数据不完整,请选择产品名称!');\r\n");
      out.write("            \r\n");
      out.write("            return false;\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("    \tif (proNames[i].productid == '9775852' || proNames[i].productid == '9865735')\r\n");
      out.write("    \t{\r\n");
      out.write("    \t\tproNames[i].price = desciprtList[i].value;\r\n");
      out.write("    \t}\r\n");
      out.write("\r\n");
      out.write("        ids = ids + proNames[i].productid + '~';\r\n");
      out.write("\r\n");
      out.write("        $O('nameList').value = $O('nameList').value +  proNames[i].value + '~';\r\n");
      out.write("        \r\n");
      out.write("        $O('mtypeList').value = $O('mtypeList').value +  proNames[i].mtype + '~';\r\n");
      out.write("        $O('oldGoodsList').value = $O('oldGoodsList').value +  proNames[i].oldgoods + '~';\r\n");
      out.write("        \r\n");
      out.write("        $O('inputRateList').value = $O('inputRateList').value +  proNames[i].inputrate + '~';\r\n");
      out.write("        \r\n");
      out.write("        var ikey = toUnqueStr2(proNames[i]);\r\n");
      out.write("        \r\n");
      out.write("        if (tmpMap[ikey])\r\n");
      out.write("        {\r\n");
      out.write("            alert('选择的产品重复[仓区+产品+职员+价格],请核实!');\r\n");
      out.write("            \r\n");
      out.write("            return false;\r\n");
      out.write("        }\r\n");
      out.write("        \r\n");
      out.write("        tmpMap[ikey] = ikey;\r\n");
      out.write("        \r\n");
      out.write("        //库存重要的标识\r\n");
      out.write("        $O('otherList').value = $O('otherList').value + ikey + '~';\r\n");
      out.write("\r\n");
      out.write("        $O('idsList').value = ids;\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    for (var i = 1; i < amounts.length; i++)\r\n");
      out.write("    {\r\n");
      out.write("        if (trim(amounts[i].value) == '')\r\n");
      out.write("        {\r\n");
      out.write("            alert('数据不完整,请填写产品数量!');\r\n");
      out.write("            amounts[i].focus();\r\n");
      out.write("            return false;\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        if (!isNumbers(amounts[i].value))\r\n");
      out.write("        {\r\n");
      out.write("            alert('数据错误,产品数量 只能是整数!');\r\n");
      out.write("            amounts[i].focus();\r\n");
      out.write("            return false;\r\n");
      out.write("        }\r\n");
      out.write("        \r\n");
      out.write("        if (parseInt(amounts[i].value, 10) < 0)\r\n");
      out.write("        {\r\n");
      out.write("            alert('数据错误,产品数量不能为负数!');\r\n");
      out.write("            amounts[i].focus();\r\n");
      out.write("            return false;\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        amous = amous + amounts[i].value + '~';\r\n");
      out.write("\r\n");
      out.write("        $O('amontList').value = amous;\r\n");
      out.write("    }\r\n");
      out.write("    \r\n");
      out.write("\r\n");
      out.write("    for (var i = 1; i < prices.length; i++)\r\n");
      out.write("    {            \r\n");
      out.write("        if (!isFloat(prices[i].value))\r\n");
      out.write("        {\r\n");
      out.write("            alert('数据错误,产品输入价格只能是浮点数!');\r\n");
      out.write("            prices[i].focus();\r\n");
      out.write("            return false;\r\n");
      out.write("        }\r\n");
      out.write("        \r\n");
      out.write("        if (parseFloat(trim(prices[i].value)) == 0)\r\n");
      out.write("        {\r\n");
      out.write("            if (!window.confirm('除非赠品单价不要填0,否则到总裁审批,你确定?'))\r\n");
      out.write("            {\r\n");
      out.write("                 prices[i].focus();\r\n");
      out.write("                 return false;\r\n");
      out.write("            }\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        $O('priceList').value = $O('priceList').value + prices[i].value + '~';\r\n");
      out.write("                \r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    for (var i = 1; i < taxrates.length; i++)\r\n");
      out.write("    {\r\n");
      out.write("        if (taxrates[i].value == '')\r\n");
      out.write("        {\r\n");
      out.write("            alert('请选择税率!');\r\n");
      out.write("            taxrates[i].focus();\r\n");
      out.write("            return false;\r\n");
      out.write("        }\r\n");
      out.write("        \r\n");
      out.write("        $O('taxrateList').value = $O('taxrateList').value +  taxrates[i].value + '~';\r\n");
      out.write("    }\r\n");
      out.write("    \r\n");
      out.write("    for (var i = 1; i < taxs.length; i++)\r\n");
      out.write("    {            \r\n");
      out.write("        $O('taxList').value = $O('taxList').value +  taxs[i].value + '~';\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("\t// 成本 \r\n");
      out.write("    for (var i = 1; i < desList.length; i++)\r\n");
      out.write("    {\r\n");
      out.write("    \tif (proNames[i].productid == '9775852' || proNames[i].productid == '9865735')\r\n");
      out.write("    \t{\r\n");
      out.write("    \t\tdesList[i].value = desciprtList[i].value;\r\n");
      out.write("    \t}\r\n");
      out.write("    \r\n");
      out.write("        if (trim(desList[i].value) == '')\r\n");
      out.write("        {\r\n");
      out.write("            alert('成本是必填!');\r\n");
      out.write("            desList[i].focus();\r\n");
      out.write("            return false;\r\n");
      out.write("        }\r\n");
      out.write("        \r\n");
      out.write("        if (!isFloat(desList[i].value))\r\n");
      out.write("        {\r\n");
      out.write("            alert('格式错误,成本只能是浮点数!');\r\n");
      out.write("            desList[i].focus();\r\n");
      out.write("            return false;\r\n");
      out.write("        }\r\n");
      out.write("        \r\n");
      out.write("        if (parseFloat(trim(desciprtList[i].value)) == 0)\r\n");
      out.write("        {\r\n");
      out.write("        \talert('成本不能为0!');\r\n");
      out.write("        \tprices[i].focus();\r\n");
      out.write("            return false;\r\n");
      out.write("        }\r\n");
      out.write("        \r\n");
      out.write("        if (parseFloat(trim(prices[i].value)) != 0 \r\n");
      out.write("            && (parseFloat(trim(prices[i].value)) < parseFloat(trim(desciprtList[i].value))))\r\n");
      out.write("        {\r\n");
      out.write("            if (!window.confirm('产品销售价['+prices[i].value+']低于成本价['+desciprtList[i].value+'],你确定?'))\r\n");
      out.write("            {\r\n");
      out.write("                 prices[i].focus();\r\n");
      out.write("                 return false;\r\n");
      out.write("            }\r\n");
      out.write("        }\r\n");
      out.write("    }\r\n");
      out.write("    \r\n");
      out.write("    for (var i = 1; i < values.length; i++)\r\n");
      out.write("    {\r\n");
      out.write("        $O('totalList').value = $O('totalList').value + values[i].value + '~';\r\n");
      out.write("        \r\n");
      out.write("        //真正的成本\r\n");
      out.write("        $O('desList').value = $O('desList').value + desList[i].value + '~';\r\n");
      out.write("        \r\n");
      out.write("        //显示成本\r\n");
      out.write("        $O('showCostList').value = $O('showCostList').value + desciprtList[i].value + '~';\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    $O('totalss').value = tsts;\r\n");
      out.write("\r\n");
      out.write("    return true;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function checkTotal()\r\n");
      out.write("{\r\n");
      out.write("\tvar ret = checkCurrentUser();\r\n");
      out.write("\r\n");
      out.write("\tif (!ret)\r\n");
      out.write("\t{\r\n");
      out.write("\t\twindow.parent.location.reload();\r\n");
      out.write("\r\n");
      out.write("\t\treturn false\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("    messk = '';\r\n");
      out.write("    \r\n");
      out.write("    ");
      if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("    \r\n");
      out.write("    var gh = $O('nameList').value.split('~');\r\n");
      out.write("    var ghk = $O('amontList').value.split('~');\r\n");
      out.write("\r\n");
      out.write("    messk += '\\r\\n';\r\n");
      out.write("    for(var i = 0 ; i < gh.length - 1; i++)\r\n");
      out.write("    {\r\n");
      out.write("        messk += '\\r\\n' + '产品【' + gh[i] + '】   数量:' + ghk[i];\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("\tif (gh.length == 1){\r\n");
      out.write("\t\talert('销售单明细不能为空');\r\n");
      out.write("\t\twindow.history.go(-1);\r\n");
      out.write("\t\treturn false;\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("    if ($O('saves').value == 'save')\r\n");
      out.write("    {\r\n");
      out.write("         if (window.confirm(\"确定保存库单?\" + messk))\r\n");
      out.write("         {\r\n");
      out.write("            disableAllButton();\r\n");
      out.write("            outForm.submit();\r\n");
      out.write("         }else\r\n");
      out.write("         {\r\n");
      out.write("         \t$Dbuttons(false);\r\n");
      out.write("         }\r\n");
      out.write("\r\n");
      out.write("         return;\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    //判断method\r\n");
      out.write("    if ($$('method') != 'addOut')\r\n");
      out.write("    {\r\n");
      out.write("        alert('提示：提交没有方法，请重新登录操作');\r\n");
      out.write("        return false;\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    if (window.confirm(\"确定提交库单?\" + messk))\r\n");
      out.write("    {\r\n");
      out.write("        disableAllButton();\r\n");
      out.write("        outForm.submit();\r\n");
      out.write("    }else\r\n");
      out.write("    {\r\n");
      out.write("    \t$Dbuttons(false);\r\n");
      out.write("    }\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function save()\r\n");
      out.write("{\r\n");
      out.write("    $O('saves').value = 'save';\r\n");
      out.write("    \r\n");
      out.write("    if (changePrice)\r\n");
      out.write("    {\r\n");
      out.write("        changePrice();\r\n");
      out.write("    }\r\n");
      out.write("    \r\n");
      out.write("    if (check(true))\r\n");
      out.write("    { \t \r\n");
      out.write("    \tcheckTotal();\r\n");
      out.write("\t\t//processProm();\r\n");
      out.write("    }\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function sub()\r\n");
      out.write("{\r\n");
      out.write("    $O('saves').value = 'submit';\r\n");
      out.write("    if (check(false))\r\n");
      out.write("    {\r\n");
      out.write("       \tprocessProm();\r\n");
      out.write("    }\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function processProm()\r\n");
      out.write("{\r\n");
      out.write("   \tif ($$('outType')==0)\r\n");
      out.write("   \t{\r\n");
      out.write("   \t\tif ($$('hasProm')== '0')\r\n");
      out.write("   \t\t{\r\n");
      out.write("   \t\t   \tif (window.confirm(\"是否使用促销活动?\"))\r\n");
      out.write("\t   \t\t{\r\n");
      out.write("\t   \t\t\t$Dbuttons(true);\r\n");
      out.write("\t   \t\t\t\r\n");
      out.write("\t   \t\t\tselectProm();\r\n");
      out.write("\t   \t\t\t\r\n");
      out.write("\t   \t\t\tvar sh;\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\t\tsh=setInterval(function() {\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t\tif ($$('isAddUp')=='Y')\r\n");
      out.write("\t\t\t\t\t{\r\n");
      out.write("\t\t\t\t\t\tclearInterval(sh);\r\n");
      out.write("\t\t\t\t\t\tcheckTotal();\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t}\t\t\r\n");
      out.write("\t\t\t\t}, 100);\r\n");
      out.write("\t   \t\t\t\r\n");
      out.write("\t   \t\t}else\r\n");
      out.write("\t   \t\t{\r\n");
      out.write("\t   \t\t\tclearProm();\r\n");
      out.write("\t   \t\t\tcheckTotal();\r\n");
      out.write("\t   \t\t}\r\n");
      out.write("   \t\t}\r\n");
      out.write("   \t\telse\r\n");
      out.write("   \t\t{\r\n");
      out.write("   \t\t\tcheckTotal();\r\n");
      out.write("   \t\t}\r\n");
      out.write("   \t}\r\n");
      out.write("   \telse\r\n");
      out.write("   \t{\r\n");
      out.write("   \t\tcheckTotal();\r\n");
      out.write("   \t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function managerChange()\r\n");
      out.write("{\r\n");
      out.write("     $O('connector').readOnly = true;\r\n");
      out.write("     $O('phone').readOnly = true;\r\n");
      out.write("     \r\n");
      out.write("     $O('connector').oncheck = '';\r\n");
      out.write("     $O('phone').oncheck = '';\r\n");
      out.write("     \r\n");
      out.write("     //价格为0\r\n");
      out.write("     var showArr = $(\"input[name='inputPrice']\") ;\r\n");
      out.write("     \r\n");
      out.write("     for (var i = 0; i < showArr.length; i++)\r\n");
      out.write("     {\r\n");
      out.write("         var each = showArr[i];\r\n");
      out.write("         each.readOnly = false;\r\n");
      out.write("     }\r\n");
      out.write("        \r\n");
      out.write("    //普通销售/委托代销 - 巡展\r\n");
      out.write("    if ($$('outType') == 0 || $$('outType') == 3 || $$('outType') == 5)\r\n");
      out.write("    {\r\n");
      out.write("        $O('customerName').value = '';\r\n");
      out.write("        $O('customerId').value = '';\r\n");
      out.write("        $O('customerName').disabled  = false;\r\n");
      out.write("        $O('reday').value = '';\r\n");
      out.write("        $O('reday').readOnly = false;\r\n");
      out.write("        \r\n");
      out.write("        resetReserve3();\r\n");
      out.write("    }\r\n");
      out.write("    \r\n");
      out.write("    //个人领样\r\n");
      out.write("    if ($$('outType') == 1 || $$('outType') == 6)\r\n");
      out.write("    {\r\n");
      out.write("        $O('customerName').value = '个人领样';\r\n");
      out.write("        $O('customerId').value = '99';\r\n");
      out.write("        $O('customerName').disabled  = true;\r\n");
      out.write("        $O('reday').value = '");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${goDays}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("';\r\n");
      out.write("        $O('reday').readOnly = true;\r\n");
      out.write("        \r\n");
      out.write("        resetReserve3();\r\n");
      out.write("    }\r\n");
      out.write("    \r\n");
      out.write("    //零售 是给公共客户的\r\n");
      out.write("    if ($$('outType') == 2)\r\n");
      out.write("    {\r\n");
      out.write("        $O('customerName').value = '公共客户';\r\n");
      out.write("        $O('customerId').value = '99';\r\n");
      out.write("        $O('customerName').disabled  = true;\r\n");
      out.write("        $O('reday').value = '';\r\n");
      out.write("        $O('reday').readOnly = false;\r\n");
      out.write("        \r\n");
      out.write("        //联系人和电话必填\r\n");
      out.write("        $O('connector').readOnly = false;\r\n");
      out.write("        $O('phone').readOnly = false;\r\n");
      out.write("        \r\n");
      out.write("        $O('connector').oncheck = 'notNone';\r\n");
      out.write("        $O('phone').oncheck = 'notNone';\r\n");
      out.write("        \r\n");
      out.write("        removeAllItem($O('reserve3'));\r\n");
      out.write("        \r\n");
      out.write("        setOption($O('reserve3'), '1', '款到发货(黑名单客户/零售)');   \r\n");
      out.write("    }\r\n");
      out.write("    \r\n");
      out.write("     //赠送\r\n");
      out.write("    if ($$('outType') == 4)\r\n");
      out.write("    {\r\n");
      out.write("        $O('customerName').value = '';\r\n");
      out.write("        $O('customerId').value = '';\r\n");
      out.write("        $O('customerName').disabled  = false;\r\n");
      out.write("        $O('reday').value = '1';\r\n");
      out.write("        $O('reday').readOnly = true;\r\n");
      out.write("        \r\n");
      out.write("        resetReserve3_ZS();\r\n");
      out.write("        \r\n");
      out.write("        //价格为0\r\n");
      out.write("        var showArr = $(\"input[name='inputPrice']\") ;\r\n");
      out.write("        \r\n");
      out.write("        for (var i = 0; i < showArr.length; i++)\r\n");
      out.write("\t    {\r\n");
      out.write("\t        var each = showArr[i];\r\n");
      out.write("\t        each.readOnly = true;\r\n");
      out.write("\t        each.value = 0.0;\r\n");
      out.write("\t    }\r\n");
      out.write("\t    \r\n");
      out.write("\t    $v('presentTR', true);\r\n");
      out.write("    } else {\r\n");
      out.write("    \t$v('presentTR', false);\r\n");
      out.write("    \t$O('presentFlag').value = '';\r\n");
      out.write("    }\r\n");
      out.write("    \r\n");
      out.write("    //处理个人黑名单\r\n");
      out.write("    if ($$('outType') != 4 && ");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${staffer.black}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write(" == 1)\r\n");
      out.write("    {\r\n");
      out.write("        removeAllItem($O('reserve3'));\r\n");
      out.write("        \r\n");
      out.write("        setOption($O('reserve3'), '1', '款到发货(黑名单客户/零售)');   \r\n");
      out.write("    }\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("// 选择促销活动\r\n");
      out.write("function selectProm()\r\n");
      out.write("{\r\n");
      out.write("    //获取付款方式 - 款到发货,信用担保\r\n");
      out.write("    var reserve3 = $O('reserve3').value;\r\n");
      out.write("    \r\n");
      out.write("    window.common.modal('../sail/promotion.do?method=rptQueryPromotion&payType='+reserve3+ '&load=1&selectMode=1');\r\n");
      out.write("\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function getProm(oos)\r\n");
      out.write("{\r\n");
      out.write("\r\n");
      out.write("\tvar oo = oos[0];\r\n");
      out.write("\r\n");
      out.write("\t// 获取活动ID, 表单中eventId赋值，同时根据活动ID，及当前单据的商品、数量，通过ajax的方式校验活动规则是否满足\r\n");
      out.write("\tvar eventId = oo.value;\r\n");
      out.write("    $O('eventName').value = oo.pname;  \r\n");
      out.write("    $O('eventId').value = eventId;\r\n");
      out.write("    $O('isAddUp').value = oo.paddup;\r\n");
      out.write("\t$O('eventDate').value = oo.pdate1 + '~' + oo.pdate2;\r\n");
      out.write("\t\r\n");
      out.write("\tvar products = getProducts();\r\n");
      out.write("\t\r\n");
      out.write("\tvar hisOrders = '';\t\r\n");
      out.write("\r\n");
      out.write("\t// flag=0表示不包括历史订单\r\n");
      out.write("\t$ajax('../sail/out.do?method=checkPromotionAndRetPromValue&thisOrder='\r\n");
      out.write("\t\t\t+ products + '&hisOrders='+ hisOrders + '&eventId='+ eventId + '&flag=0', callBackProm);\r\n");
      out.write("\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function callBackProm(data)\r\n");
      out.write("{\r\n");
      out.write("\t\r\n");
      out.write("\tvar prom = data.msg;\r\n");
      out.write("\t\r\n");
      out.write("\t// ret = 1 表示出错\r\n");
      out.write("\tif (data.ret == 1)\r\n");
      out.write("\t{\r\n");
      out.write("\t\tclearProm();\r\n");
      out.write("\t\r\n");
      out.write("\t\talert('促销活动执行出错：' + prom);\r\n");
      out.write("\t\t\r\n");
      out.write("\t\treturn false;\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tif (!isFloat(prom.promValue))\r\n");
      out.write("\t{\r\n");
      out.write("\t\tclearProm();\r\n");
      out.write("\t\t\r\n");
      out.write("\t\talert('促销活动执行出错：折扣金额不为数据型')\r\n");
      out.write("\t\t\r\n");
      out.write("\t\treturn false;\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t// 金额不足\r\n");
      out.write("\tif (prom.ret == 1 || prom.ret == 4)\r\n");
      out.write("\t{\r\n");
      out.write("\t\tvar mess0 = '';\r\n");
      out.write("\t\tvar mess = '';\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif (prom.ret == 1)\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\tmess0 = '参与活动的商品的金额小于活动的最小金额，是否绑定历史单据?';\t\t\t\r\n");
      out.write("\t\t\tmess = '促销活动执行出错：不满足活动金额要求';\t\r\n");
      out.write("\t\t}else\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\tmess0 = '活动中的商品不都在销售单中，是否绑定历史单据?';\t\t\t\r\n");
      out.write("\t\t\tmess = '促销活动执行出错：当前是礼包活动，要求活动中的商品在销售单都包含';\t\t\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\r\n");
      out.write("\t\t$O('promValue').value = '';\r\n");
      out.write("\t\t$O('refBindOutId').value = '';\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif ($$('isAddUp')== 0 )\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\tif (window.confirm(mess0))\r\n");
      out.write("\t         {\r\n");
      out.write("\t\t\t\tselectOrder();\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\treturn true;\r\n");
      out.write("\t         }\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\talert(mess);\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t$O('eventId').value = '';\r\n");
      out.write("\t\t$O('eventName').value = '';\r\n");
      out.write("\t\t$O('isAddUp').value = 'Y';\r\n");
      out.write("\t\t$O('eventDate').value = '';\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\treturn \tfalse;\r\n");
      out.write("\t}\t\r\n");
      out.write("\t\r\n");
      out.write("\t// 数量不足\r\n");
      out.write("\tif (prom.ret == 2)\r\n");
      out.write("\t{\r\n");
      out.write("\t\tclearProm();\r\n");
      out.write("\t\t\r\n");
      out.write("\t\talert('促销活动执行出错：不满足活动数量要求');\r\n");
      out.write("\t\t\r\n");
      out.write("\t\treturn false;\t\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t// 折扣金额大于参加活动商品的总金额\r\n");
      out.write("\tif (prom.ret == 3)\r\n");
      out.write("\t{\r\n");
      out.write("\t\tclearProm();\r\n");
      out.write("\t\t\r\n");
      out.write("\t\talert('促销活动执行出错：折扣金额大于参加活动商品的总金额');\r\n");
      out.write("\t\t\r\n");
      out.write("\t\treturn false;\t\r\n");
      out.write("\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t$O('promValue').value = prom.promValue;\r\n");
      out.write("\t$O('isAddUp').value = 'Y';\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("// 选择历史订单\r\n");
      out.write("function selectOrder()\r\n");
      out.write("{\r\n");
      out.write("\t\r\n");
      out.write("\tvar customerId = $O('customerId').value;\r\n");
      out.write("\t\r\n");
      out.write("\tvar eventDate = $O('eventDate').value;\t\r\n");
      out.write("\t\r\n");
      out.write("\twindow.common.modal('../sail/out.do?method=rptQueryHisOut&customerId='+customerId+ '&eventDate='+eventDate +'&load=1&selectMode=0');\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function getOrder(oos)\r\n");
      out.write("{\r\n");
      out.write("\t\r\n");
      out.write("\tvar hisOrders = '';\t\r\n");
      out.write("\t\r\n");
      out.write("\tvar eventId = $O('eventId').value;\r\n");
      out.write("\t\r\n");
      out.write("\tfor (var i=0; i< oos.length; i++)\r\n");
      out.write("\t{\r\n");
      out.write("\t\thisOrders = hisOrders + oos[i].value + '~'; \r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t$O('refBindOutId').value = hisOrders;\r\n");
      out.write("\t\r\n");
      out.write("\tvar products = getProducts();\r\n");
      out.write("\t\r\n");
      out.write("\t$ajax('../sail/out.do?method=checkPromotionAndRetPromValue&thisOrder='\r\n");
      out.write("\t\t\t+ products + '&hisOrders='+ hisOrders + '&eventId='+ eventId + '&flag=1', callBackProm);\t\t\t\t\t\t\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("function getProducts()\r\n");
      out.write("{\r\n");
      out.write("\tvar products = '';\r\n");
      out.write("\t\r\n");
      out.write("\tvar proNames = document.getElementsByName('productName');\r\n");
      out.write("    \r\n");
      out.write("    var amounts = document.getElementsByName('amount');\r\n");
      out.write("\r\n");
      out.write("\tvar values = document.getElementsByName('value');\r\n");
      out.write("\t\r\n");
      out.write("\t//i 从1开始 ??\r\n");
      out.write(" \tfor (var i = 1; i < proNames.length; i++)\r\n");
      out.write("    {\r\n");
      out.write("        var productid = proNames[i].productid;\r\n");
      out.write("        \r\n");
      out.write("        var amount = amounts[i].value;\r\n");
      out.write("        \r\n");
      out.write("        var outValue = values[i].value;\r\n");
      out.write("        \r\n");
      out.write("        products = products + productid + '-' + amount + '-' + outValue + '~';\r\n");
      out.write("\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\treturn products;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function clearProm()\r\n");
      out.write("{\r\n");
      out.write("\t$O('eventId').value = '';\r\n");
      out.write("\t$O('promValue').value = '0.0';\r\n");
      out.write("\t$O('refBindOutId').value = '';\t\r\n");
      out.write("\t$O('isAddUp').value = 'Y';\r\n");
      out.write("\t$O('eventDate').value = '';\r\n");
      out.write("\t$O('eventName').value = '';\t\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function checkCurrentUser()\r\n");
      out.write("{\t\r\n");
      out.write("     // check\r\n");
      out.write("     var elogin = \"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${g_elogin}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\";\r\n");
      out.write("\r\n");
      out.write(" \t //  商务登陆\r\n");
      out.write("     var top = window.top.topFrame.document;\r\n");
      out.write("     //var allDef = window.top.topFrame.allDef;\r\n");
      out.write("     var srcStafferId = top.getElementById('srcStafferId').value;\r\n");
      out.write("    \r\n");
      out.write("     var currentStafferId = \"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${g_stafferBean.id}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\";\r\n");
      out.write("\r\n");
      out.write("     var currentStafferName = \"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${g_stafferBean.name}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\";\r\n");
      out.write("\r\n");
      out.write("     if (srcStafferId && srcStafferId != currentStafferId)\r\n");
      out.write("     {\r\n");
      out.write("\r\n");
      out.write("          alert('请不要同时打开多个OA连接，且当前登陆者不同，当前登陆者应为：' + currentStafferName);\r\n");
      out.write("          \r\n");
      out.write("          return false;\r\n");
      out.write("     }\r\n");
      out.write("\r\n");
      out.write("\treturn true;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("var duesMap = {};\r\n");
      out.write("var duesTypeMap = {};\r\n");
      if (_jspx_meth_c_005fforEach_005f2(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("/**\r\n");
      out.write(" * 查询库存\r\n");
      out.write(" */\r\n");
      out.write("function opens(obj)\r\n");
      out.write("{\r\n");
      out.write("\tvar ret = checkCurrentUser();\r\n");
      out.write("\r\n");
      out.write("\tif (!ret)\r\n");
      out.write("\t{\r\n");
      out.write("\t\twindow.parent.location.reload();\r\n");
      out.write("\r\n");
      out.write("\t\treturn false;\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tvar os = obj.parentNode.parentNode;\r\n");
      out.write("\r\n");
      out.write("\tvar location = os.cells[0].childNodes[0].value;\r\n");
      out.write("\r\n");
      out.write("\tif (location == '')\r\n");
      out.write("    {\r\n");
      out.write("        alert('请选择仓库');\r\n");
      out.write("        \r\n");
      out.write("        return false;\r\n");
      out.write("    }\r\n");
      out.write("    \r\n");
      out.write("    //if ($$('dutyId') == '')\r\n");
      out.write("    //{\r\n");
      out.write("        //alert('请选择纳税实体');\r\n");
      out.write("        \r\n");
      out.write("        //return false;\r\n");
      out.write("    //}\r\n");
      out.write("    \r\n");
      out.write("    var mtype = \"\";\r\n");
      out.write("    oo = obj;\r\n");
      out.write("    // 配件\r\n");
      out.write("    window.common.modal('../depot/storage.do?method=rptQueryStorageRelationInDepot&sailLocation=");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${g_staffer.industryId}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("&load=1&depotId='\r\n");
      out.write("\t                    + location + '&code=' + obj.productcode + '&mtype=' + mtype \r\n");
      out.write("\t                    + '&init=1');\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function load()\r\n");
      out.write("{\t\r\n");
      out.write("    blackForbid();\r\n");
      out.write("    \r\n");
      out.write("    titleChange();\r\n");
      out.write("    \r\n");
      out.write("    loadForm();\r\n");
      out.write("    \r\n");
      out.write("     //load show\r\n");
      out.write("    //loadShow();\r\n");
      out.write("    \r\n");
      out.write("    //loadForm();\r\n");
      out.write("    \r\n");
      out.write("    managerChange();\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function changePrice()\r\n");
      out.write("{\r\n");
      out.write("    var ssList = document.getElementsByName('price');\r\n");
      out.write("    \r\n");
      out.write("    for (var i = 0; i < ssList.length; i++)\r\n");
      out.write("    {\r\n");
      out.write("        if (ssList[i].value != '')\r\n");
      out.write("        {\r\n");
      out.write("           ccs(ssList[i]);\r\n");
      out.write("           total();\r\n");
      out.write("        }\r\n");
      out.write("    }\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function blackForbid()\r\n");
      out.write("{\r\n");
      out.write("\t\r\n");
      out.write("\tvar black = \"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${black}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\";\r\n");
      out.write("\r\n");
      out.write("\tif (black > '')\r\n");
      out.write("\t{\r\n");
      out.write("\t\talert(black);\r\n");
      out.write("\r\n");
      out.write("\t\tdocument.location.href = '../admin/welcome.jsp';\r\n");
      out.write("\t\treturn false;\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\treturn true;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body class=\"body_class\" onload=\"load()\">\r\n");
      out.write("<form name=\"outForm\" method=\"post\" action=\"../sail/out.do?method=addOut\">\r\n");
      out.write("<input type=hidden name=\"update\" value=\"0\" />\r\n");
      out.write("<input type=hidden name=\"nameList\" /> \r\n");
      out.write("<input type=hidden name=\"idsList\" /> \r\n");
      out.write("<input type=hidden name=\"amontList\" />\r\n");
      out.write("\r\n");
      out.write("<input type=hidden name=\"totalList\" /> \r\n");
      out.write("<input type=hidden name=\"totalss\" /> \r\n");
      out.write("<input type=hidden name=\"customerId\" /> \r\n");
      out.write("<input type=hidden name=\"type\" value='0' /> \r\n");
      out.write("<input type=hidden name=\"saves\" value=\"\" />\r\n");
      out.write("<input type=hidden name=\"desList\" value=\"\" />\r\n");
      out.write("<input type=hidden name=\"showCostList\" value=\"\" />\r\n");
      out.write("<input type=hidden name=\"otherList\" value=\"\" />\r\n");
      out.write("<input type=hidden name=\"depotList\" value=\"\" />\r\n");
      out.write("<input type=hidden name=\"mtypeList\" value=\"\" />\r\n");
      out.write("<input type=hidden name=\"oldGoodsList\" value=\"\" />\r\n");
      out.write("<input type=hidden name=\"taxList\" value=\"\" />\r\n");
      out.write("<input type=hidden name=\"taxrateList\" value=\"\" />\r\n");
      out.write("<input type=hidden name=\"inputRateList\" value=\"\" />\r\n");
      out.write("\r\n");
      out.write("<input type=hidden name=\"customercreditlevel\" value=\"\" />\r\n");
      out.write("<input type=hidden name=\"id\" value=\"\" />\r\n");
      out.write("<input type=hidden name=\"priceList\"> \r\n");
      out.write("<input type=hidden name=\"inputPriceList\">\r\n");
      out.write("<input type=hidden name=\"mtype\" value=\"\" />\r\n");
      out.write("<input type=hidden name=\"hasProm\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${hasProm}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\" />\r\n");
      out.write("<input type=hidden name=\"step\" value=\"1\" />\r\n");
      out.write("\r\n");
      out.write("<input type=hidden name=\"locationShadow\" value=\"\">\r\n");
      out.write("<!-- 批量开单 -->\r\n");
      out.write("<input type=hidden name=\"oprType\" value=\"0\">\r\n");
      out.write("\r\n");
      if (_jspx_meth_p_005fnavigation_005f0(_jspx_page_context))
        return;
      out.write(" <br>\r\n");
      out.write("\r\n");
      out.write("<table width=\"95%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\r\n");
      out.write("\talign=\"center\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td valign=\"top\" colspan='2'>\r\n");
      out.write("\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t<!--DWLayoutTable-->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td width=\"784\" height=\"6\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td align=\"center\" valign=\"top\">\r\n");
      out.write("\t\t\t\t<div align=\"left\">\r\n");
      out.write("\t\t\t\t<table width=\"90%\" border=\"0\" cellspacing=\"2\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"10\">\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td width=\"5\">&nbsp;</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td width=\"6\"><img src=\"../images/dot_r.gif\" width=\"6\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\theight=\"6\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"caption\"><strong><font color=red>");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${hasOver}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("</font> 您信用还剩:");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${credit}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("</strong>\r\n");
      out.write("\t\t\t\t\t\t\t\t信用担保：\r\n");
      out.write("\t\t\t\t\t\t\t\t<select name=\"guarantor\" style=\"width: 300px\">\r\n");
      out.write("                                    ");
      if (_jspx_meth_c_005fforEach_005f3(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("                                </select>\r\n");
      out.write("                                &nbsp;&nbsp;<strong>");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${swatchMoney}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("</strong>\r\n");
      out.write("\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td background=\"../images/dot_line.gif\" colspan='2'></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td height=\"10\" colspan='2'></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td colspan='2' align='center'>\r\n");
      out.write("\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\r\n");
      out.write("\t\t\tclass=\"border\">\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t<table width=\"100%\" border=\"0\" cellspacing='1'>\r\n");
      out.write("\t\t\t\t\t<tr class=\"content2\">\r\n");
      out.write("\t\t\t\t\t\t<td width=\"15%\" align=\"right\">销售日期：</td>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<td width=\"35%\"><input type=\"text\" name=\"outTime\"\r\n");
      out.write("\t\t\t\t\t\t\tvalue=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${current}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\" maxlength=\"20\" size=\"20\"\r\n");
      out.write("\t\t\t\t\t\t\treadonly=\"readonly\"><font color=\"#FF0000\">*</font></td>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t<td width=\"15%\" align=\"right\">销售类型：</td>\r\n");
      out.write("\t\t\t\t\t\t\t<td width=\"35%\"><select name=\"outType\" class=\"select_class\" onchange=\"managerChange()\">\r\n");
      out.write("\t\t\t\t\t\t\t\t");
      if (_jspx_meth_p_005foption_005f0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t</select><font color=\"#FF0000\">*</font></td>\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t<tr id=\"presentTR\" class=\"content1\" style=\"display: none;\">\r\n");
      out.write("\t\t\t\t\t\t<td align=\"right\" id=\"outd\">赠送类型：</td>\r\n");
      out.write("\t\t\t\t\t\t<td colspan=\"3\"><select name=\"presentFlag\" class=\"select_class\" >\r\n");
      out.write("\t\t\t\t\t\t\t\t");
      if (_jspx_meth_p_005foption_005f1(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t</select></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t<tr class=\"content1\">\r\n");
      out.write("\t\t\t\t\t\t<td align=\"right\" id=\"outd\">客户：</td>\r\n");
      out.write("\t\t\t\t\t\t<td colspan=\"3\"><input type=\"text\" name=\"customerName\" maxlength=\"14\" value=\"\"\r\n");
      out.write("\t\t\t\t\t\t\tonclick=\"selectCustomer()\" style=\"cursor: pointer;\"\r\n");
      out.write("\t\t\t\t\t\t\treadonly=\"readonly\"><font color=\"#FF0000\">*</font></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr class=\"content2\">\r\n");
      out.write("\t\t\t\t\t\t<td align=\"right\">联系人：</td>\r\n");
      out.write("\t\t\t\t\t\t<td><input type=\"text\" name=\"connector\" maxlength=\"14\"\r\n");
      out.write("\t\t\t\t\t\t\treadonly=\"readonly\"></td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"right\">联系电话：</td>\r\n");
      out.write("\t\t\t\t\t\t<td><input type=\"text\" name=\"phone\" maxlength=\"20\" readonly></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr class=\"content1\">\r\n");
      out.write("\t\t\t\t\t\t<td align=\"right\">经手人：</td>\r\n");
      out.write("\t\t\t\t\t\t<td><input type=\"text\" name=\"stafferName\" maxlength=\"14\"\r\n");
      out.write("\t\t\t\t\t\t\tvalue=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${user.stafferName}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\" readonly=\"readonly\"></td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"right\">单据号码：</td>\r\n");
      out.write("\t\t\t\t\t\t<td><input type=\"text\" name=\"idName\" maxlength=\"20\"\r\n");
      out.write("\t\t\t\t\t\t\tvalue=\"系统自动生成\" readonly></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t<tr class=\"content2\">\r\n");
      out.write("\t\t\t\t\t\t<td align=\"right\">回款天数：</td>\r\n");
      out.write("\t\t\t\t\t\t<td colspan=\"1\"><input type=\"text\" name=\"reday\" maxlength=\"4\" oncheck=\"notNone;isInt;range(1, 180)\"\r\n");
      out.write("\t\t\t\t\t\t\tvalue=\"\" title=\"请填入1到180之内的数字\"><font color=\"#FF0000\">*</font></td>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<td align=\"right\">付款方式：</td>\r\n");
      out.write("                        <td colspan=\"1\">\r\n");
      out.write("                        <select name=\"reserve3\" class=\"select_class\" oncheck=\"notNone;\" head=\"付款方式\" style=\"width: 240px\">\r\n");
      out.write("                            <option value='2'>客户信用和业务员信用额度担保</option>\r\n");
      out.write("                            <option value='3'>信用担保</option>\r\n");
      out.write("                            <option value='1'>款到发货(黑名单客户/零售)</option>\r\n");
      out.write("                        </select>\r\n");
      out.write("                        <font color=\"#FF0000\">*</font></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t<tr class=\"content2\">\r\n");
      out.write("\t\t\t\t\t\t<td align=\"right\">销售单备注：</td>\r\n");
      out.write("\t\t\t\t\t\t<td colspan=\"3\"><textarea rows=\"3\" cols=\"55\"\r\n");
      out.write("\t\t\t\t\t\t\tname=\"description\"></textarea>\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td height=\"10\" colspan='2'></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td background=\"../images/dot_line.gif\" colspan='2'></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td height=\"10\" colspan='2'></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td colspan='2' align='center'>\r\n");
      out.write("\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\r\n");
      out.write("\t\t\tclass=\"border\">\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t<table width=\"100%\" border=\"0\" cellspacing='1' id=\"tables\">\r\n");
      out.write("\t\t\t\t\t<tr align=\"center\" class=\"content0\">\r\n");
      out.write("\t\t\t\t\t\t<td width=\"13%\" align=\"center\">仓库</td>\r\n");
      out.write("\t\t\t\t\t\t<td width=\"27%\" align=\"center\">品名</td>\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t<td width=\"5%\" align=\"center\">数量</td>\r\n");
      out.write("\t\t\t\t\t\t<td width=\"8%\" align=\"center\">单价</td>\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t<td width=\"8%\" align=\"left\">金额<span id=\"total\"></span></td>\r\n");
      out.write("\t\t\t\t\t\t<td width=\"8%\" align=\"center\">成本</td>\r\n");
      out.write("\t\t\t\t\t\t<td width=\"5%\" align=\"center\">税率</td>\r\n");
      out.write("\t\t\t\t\t\t<td width=\"5%\" align=\"center\">税额</td>\r\n");
      out.write("\t\t\t\t\t\t<td width=\"8%\" align=\"center\">毛利</td>\r\n");
      out.write("\t\t\t\t\t\t<td width=\"8%\" align=\"center\">毛利率</td>\r\n");
      out.write("\t\t\t\t\t\t<td width=\"5%\" align=\"center\"><input type=\"button\" accesskey=\"A\"\r\n");
      out.write("\t\t\t\t\t\t\tvalue=\"增加\" class=\"button_class\" onclick=\"addTr()\"></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t<tr class=\"content1\" id=\"trCopy\" style=\"display: none;\">\r\n");
      out.write("\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t<select name=\"locationIds\" class=\"select_class\" onchange=\"clearsItem(this)\" style=\"width: 100%\">\r\n");
      out.write("\t\t\t\t\t\t\t\t    <option value=\"\">--</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t");
      if (_jspx_meth_c_005fforEach_005f4(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t</select>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t<input type=\"text\" name=\"productName\"\r\n");
      out.write("\t\t\t\t\t\t\tonclick=\"opens(this)\"\r\n");
      out.write("\t\t\t\t\t\t\tproductid=\"\" \r\n");
      out.write("\t\t\t\t\t\t\tproductcode=\"\" \r\n");
      out.write("\t\t\t\t\t\t\tprice=\"\"\r\n");
      out.write("\t\t\t\t\t\t\taddprice=\"\"\r\n");
      out.write("\t\t\t\t\t\t\tstafferid=\"\"\r\n");
      out.write("\t\t\t\t\t\t\tdepotpartid=\"\"\r\n");
      out.write("\t\t\t\t\t\t\tproducttype=\"\"\r\n");
      out.write("\t\t\t\t\t\t\toldgoods=\"\"\r\n");
      out.write("\t\t\t\t\t\t\tmtype=\"\"\r\n");
      out.write("\t\t\t\t\t\t\tdepotid=\"\"\r\n");
      out.write("\t\t\t\t\t\t\tinputrate=\"\"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\treadonly=\"readonly\"\r\n");
      out.write("\t\t\t\t\t\t\tstyle=\"width: 100%; cursor: hand\">\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<td align=\"center\"><input type=\"text\"\r\n");
      out.write("\t\t\t\t\t\t\tstyle=\"width: 100%\" maxlength=\"6\" onkeyup=\"cc(this)\"\r\n");
      out.write("\t\t\t\t\t\t\tname=\"amount\"></td>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<td align=\"center\"><input type=\"text\"\r\n");
      out.write("\t\t\t\t\t\t\tstyle=\"width: 100%\" maxlength=\"13\" onkeyup=\"cc(this)\" cost=\"\"\r\n");
      out.write("\t\t\t\t\t\t\tonblur=\"blu(this)\" name=\"price\"></td>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<td align=\"center\"><input type=\"text\"\r\n");
      out.write("\t\t\t\t\t\t\tvalue=\"0.00\" readonly=\"readonly\" style=\"width: 100%\" name=\"value\"></td>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<td align=\"center\"><input type=\"text\" readonly=\"readonly\" onkeyup=\"cc(this)\"\r\n");
      out.write("\t\t\t\t\t\t\tstyle=\"width: 100%\" name=\"desciprt\"><input type=\"hidden\" name=\"costPrice\" value=\"\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t<td align=\"center\">\r\n");
      out.write("\t\t\t\t\t\t\t<input type=\"text\"\r\n");
      out.write("\t\t\t\t\t\t\t\tvalue=\"0.00\" readonly=\"readonly\" style=\"width: 100%\" name=\"taxrate\">\t\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t<td align=\"center\"><input type=\"text\" readonly=\"readonly\" value=\"0.00\"\r\n");
      out.write("\t\t\t\t\t\t\tstyle=\"width: 100%\" name=\"tax\"></td>\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t<td align=\"center\"><input type=\"text\" readonly=\"readonly\" value=\"0.00\"\r\n");
      out.write("\t\t\t\t\t\t\tstyle=\"width: 100%\" name=\"profit\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t<td align=\"center\"><input type=\"text\" readonly=\"readonly\" value=\"0.00%\"\r\n");
      out.write("\t\t\t\t\t\t\tstyle=\"width: 100%\" name=\"profitRatio\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t<td align=\"center\"></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t<tr class=\"content2\">\r\n");
      out.write("\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t\t<select id=\"unLocationId\" name=\"locationIds\" class=\"select_class\" onchange=\"clearsItem(this)\" style=\"width: 100%\">\r\n");
      out.write("\t\t\t\t\t\t\t\t    <option value=\"\">--</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t");
      if (_jspx_meth_c_005fforEach_005f5(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t</select>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t<td><input type=\"text\" name=\"productName\" id=\"unProductName\"\r\n");
      out.write("\t\t\t\t\t\t\tonclick=\"opens(this)\" \r\n");
      out.write("\t\t\t\t\t\t\tproductid=\"\" \r\n");
      out.write("                            productcode=\"\" \r\n");
      out.write("                            price=\"\"\r\n");
      out.write("                            addprice=\"\"\r\n");
      out.write("                            stafferid=\"\"\r\n");
      out.write("                            depotpartid=\"\"\r\n");
      out.write("                            producttype=\"\"\r\n");
      out.write("                            productsailtype=\"\"\r\n");
      out.write("                            oldgoods=\"\"\r\n");
      out.write("                            mtype=\"\"\r\n");
      out.write("                            depotid=\"\"\r\n");
      out.write("                            inputrate=\"\"\r\n");
      out.write("\t\t\t\t\t\t\treadonly=\"readonly\"\r\n");
      out.write("\t\t\t\t\t\t\tstyle=\"width: 100%; cursor: pointer\"></td>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<td align=\"center\"><input type=\"text\" style=\"width: 100%\" id=\"unAmount\"\r\n");
      out.write("\t\t\t\t\t\t\tmaxlength=\"8\" onkeyup=\"cc(this)\" name=\"amount\"></td>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<td align=\"center\"><input type=\"text\" style=\"width: 100%\" id=\"unPrice\" cost=\"\"\r\n");
      out.write("\t\t\t\t\t\t\tmaxlength=\"13\" onkeyup=\"cc(this)\" onblur=\"blu(this)\" name=\"price\"></td>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<td align=\"center\"><input type=\"text\"\r\n");
      out.write("\t\t\t\t\t\t\tvalue=\"0.00\" readonly=\"readonly\" style=\"width: 100%\" name=\"value\"></td>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<td align=\"center\"><input type=\"text\" id=\"unDesciprt\" readonly=\"readonly\" onkeyup=\"cc(this)\"\r\n");
      out.write("\t\t\t\t\t\t\tstyle=\"width: 100%\" name=\"desciprt\"><input type=\"hidden\" id=\"unCostPrice\" name=\"costPrice\" value=\"\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t<td align=\"center\">\r\n");
      out.write("\t\t\t\t\t\t\t<input type=\"text\" id=\"unTaxrate\"\r\n");
      out.write("\t\t\t\t\t\t\t\tvalue=\"0.00\" readonly=\"readonly\" style=\"width: 100%\" name=\"taxrate\">\t\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t<td align=\"center\"><input type=\"text\" readonly=\"readonly\" id=\"unTax\" value=\"0.00\"\r\n");
      out.write("\t\t\t\t\t\t\tstyle=\"width: 100%\" name=\"tax\"></td>\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t<td align=\"center\"><input type=\"text\" readonly=\"readonly\" id=\"unProfit\" value=\"0.00\"\r\n");
      out.write("\t\t\t\t\t\t\tstyle=\"width: 100%\" name=\"profit\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t<td align=\"center\"><input type=\"text\" readonly=\"readonly\" id=\"unProfitRatio\" value=\"0.00%\"\r\n");
      out.write("\t\t\t\t\t\t\tstyle=\"width: 100%\" name=\"profitRatio\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t<td><input type=button value=\"清空\"  class=\"button_class\" onclick=\"clears()\"></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td height=\"10\" colspan='2'></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td background=\"../images/dot_line.gif\" colspan='2'></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td height=\"10\" colspan='2'></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td width=\"100%\">\r\n");
      out.write("\t\t<div align=\"right\">\r\n");
      out.write("\t\t\t<input type=\"button\" class=\"button_class\"\r\n");
      out.write("\t\t\tvalue=\"去完成配送信息\" onClick=\"save()\" />&nbsp;&nbsp;\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t\t<td width=\"0%\"></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\r\n");
      out.write("</table>\r\n");
      out.write("</form>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
      out.write("\r\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      if (_jspxFactory != null) _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_p_005flink_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  p:link
    com.china.center.common.taglib.PageLink _jspx_th_p_005flink_005f0 = (com.china.center.common.taglib.PageLink) _005fjspx_005ftagPool_005fp_005flink_005ftitle_005fnobody.get(com.china.center.common.taglib.PageLink.class);
    _jspx_th_p_005flink_005f0.setPageContext(_jspx_page_context);
    _jspx_th_p_005flink_005f0.setParent(null);
    _jspx_th_p_005flink_005f0.setTitle("填写销售单(now)");
    int _jspx_eval_p_005flink_005f0 = _jspx_th_p_005flink_005f0.doStartTag();
    if (_jspx_th_p_005flink_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fp_005flink_005ftitle_005fnobody.reuse(_jspx_th_p_005flink_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fp_005flink_005ftitle_005fnobody.reuse(_jspx_th_p_005flink_005f0);
    return false;
  }

  private boolean _jspx_meth_c_005fforEach_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_005fforEach_005f0 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _005fjspx_005ftagPool_005fc_005fforEach_005fvar_005fitems.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fforEach_005f0.setParent(null);
    _jspx_th_c_005fforEach_005f0.setItems((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${invoiceList}", java.lang.Object.class, (PageContext)_jspx_page_context, null, false));
    _jspx_th_c_005fforEach_005f0.setVar("item");
    int[] _jspx_push_body_count_c_005fforEach_005f0 = new int[] { 0 };
    try {
      int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
      if (_jspx_eval_c_005fforEach_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("  invFullMap['");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.id}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("'] = '");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.fullName}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("';\r\n");
          int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fforEach_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_c_005fforEach_005f0[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
    } finally {
      _jspx_th_c_005fforEach_005f0.doFinally();
      _005fjspx_005ftagPool_005fc_005fforEach_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
    }
    return false;
  }

  private boolean _jspx_meth_c_005fforEach_005f1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_005fforEach_005f1 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _005fjspx_005ftagPool_005fc_005fforEach_005fvar_005fitems.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
    _jspx_th_c_005fforEach_005f1.setParent(null);
    _jspx_th_c_005fforEach_005f1.setItems((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${dutyList}", java.lang.Object.class, (PageContext)_jspx_page_context, null, false));
    _jspx_th_c_005fforEach_005f1.setVar("item");
    int[] _jspx_push_body_count_c_005fforEach_005f1 = new int[] { 0 };
    try {
      int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
      if (_jspx_eval_c_005fforEach_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("  invMap['");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.id}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("'] = '");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.type}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("';\r\n");
          int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fforEach_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_c_005fforEach_005f1[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
    } finally {
      _jspx_th_c_005fforEach_005f1.doFinally();
      _005fjspx_005ftagPool_005fc_005fforEach_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
    }
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f0 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f0.setParent(null);
    _jspx_th_c_005fif_005f0.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${lock_sw}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
    if (_jspx_eval_c_005fif_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("    \r\n");
        out.write("     messk += '\\r\\n';\r\n");
        out.write("     \r\n");
        out.write("     messk += '由于是领样转销售保存库存后需要提交才能进入销售流程';\r\n");
        out.write("    ");
        int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_005ftest.reuse(_jspx_th_c_005fif_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_005ftest.reuse(_jspx_th_c_005fif_005f0);
    return false;
  }

  private boolean _jspx_meth_c_005fforEach_005f2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_005fforEach_005f2 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _005fjspx_005ftagPool_005fc_005fforEach_005fvar_005fitems.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    _jspx_th_c_005fforEach_005f2.setPageContext(_jspx_page_context);
    _jspx_th_c_005fforEach_005f2.setParent(null);
    _jspx_th_c_005fforEach_005f2.setItems((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${dutyList}", java.lang.Object.class, (PageContext)_jspx_page_context, null, false));
    _jspx_th_c_005fforEach_005f2.setVar("item");
    int[] _jspx_push_body_count_c_005fforEach_005f2 = new int[] { 0 };
    try {
      int _jspx_eval_c_005fforEach_005f2 = _jspx_th_c_005fforEach_005f2.doStartTag();
      if (_jspx_eval_c_005fforEach_005f2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("duesMap['");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.id}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("'] = '");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.dues}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("';\r\n");
          out.write("duesTypeMap['");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.id}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("'] = '");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.mtype}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("';\r\n");
          int evalDoAfterBody = _jspx_th_c_005fforEach_005f2.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fforEach_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_c_005fforEach_005f2[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_c_005fforEach_005f2.doCatch(_jspx_exception);
    } finally {
      _jspx_th_c_005fforEach_005f2.doFinally();
      _005fjspx_005ftagPool_005fc_005fforEach_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2);
    }
    return false;
  }

  private boolean _jspx_meth_p_005fnavigation_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  p:navigation
    com.china.center.common.taglib.PageStart _jspx_th_p_005fnavigation_005f0 = (com.china.center.common.taglib.PageStart) _005fjspx_005ftagPool_005fp_005fnavigation_005fheight.get(com.china.center.common.taglib.PageStart.class);
    _jspx_th_p_005fnavigation_005f0.setPageContext(_jspx_page_context);
    _jspx_th_p_005fnavigation_005f0.setParent(null);
    _jspx_th_p_005fnavigation_005f0.setHeight("22");
    int _jspx_eval_p_005fnavigation_005f0 = _jspx_th_p_005fnavigation_005f0.doStartTag();
    if (_jspx_eval_p_005fnavigation_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_p_005fnavigation_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_p_005fnavigation_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_p_005fnavigation_005f0.doInitBody();
      }
      do {
        out.write("\r\n");
        out.write("\t<td width=\"550\" class=\"navigation\">库单管理 &gt;&gt; 填写销售单1</td>\r\n");
        out.write("\t\t\t\t<td width=\"85\"></td>\r\n");
        int evalDoAfterBody = _jspx_th_p_005fnavigation_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_p_005fnavigation_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_p_005fnavigation_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fp_005fnavigation_005fheight.reuse(_jspx_th_p_005fnavigation_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fp_005fnavigation_005fheight.reuse(_jspx_th_p_005fnavigation_005f0);
    return false;
  }

  private boolean _jspx_meth_c_005fforEach_005f3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_005fforEach_005f3 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _005fjspx_005ftagPool_005fc_005fforEach_005fvar_005fitems.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    _jspx_th_c_005fforEach_005f3.setPageContext(_jspx_page_context);
    _jspx_th_c_005fforEach_005f3.setParent(null);
    _jspx_th_c_005fforEach_005f3.setItems((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${mList}", java.lang.Object.class, (PageContext)_jspx_page_context, null, false));
    _jspx_th_c_005fforEach_005f3.setVar("item");
    int[] _jspx_push_body_count_c_005fforEach_005f3 = new int[] { 0 };
    try {
      int _jspx_eval_c_005fforEach_005f3 = _jspx_th_c_005fforEach_005f3.doStartTag();
      if (_jspx_eval_c_005fforEach_005f3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("                                        <option value=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.id}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write('"');
          out.write('>');
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.description}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("</option>\r\n");
          out.write("                                    ");
          int evalDoAfterBody = _jspx_th_c_005fforEach_005f3.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fforEach_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_c_005fforEach_005f3[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_c_005fforEach_005f3.doCatch(_jspx_exception);
    } finally {
      _jspx_th_c_005fforEach_005f3.doFinally();
      _005fjspx_005ftagPool_005fc_005fforEach_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f3);
    }
    return false;
  }

  private boolean _jspx_meth_p_005foption_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  p:option
    com.china.center.common.taglib.PageSelectOption _jspx_th_p_005foption_005f0 = (com.china.center.common.taglib.PageSelectOption) _005fjspx_005ftagPool_005fp_005foption_005ftype_005fnobody.get(com.china.center.common.taglib.PageSelectOption.class);
    _jspx_th_p_005foption_005f0.setPageContext(_jspx_page_context);
    _jspx_th_p_005foption_005f0.setParent(null);
    _jspx_th_p_005foption_005f0.setType("outType_out");
    int _jspx_eval_p_005foption_005f0 = _jspx_th_p_005foption_005f0.doStartTag();
    if (_jspx_th_p_005foption_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fp_005foption_005ftype_005fnobody.reuse(_jspx_th_p_005foption_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fp_005foption_005ftype_005fnobody.reuse(_jspx_th_p_005foption_005f0);
    return false;
  }

  private boolean _jspx_meth_p_005foption_005f1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  p:option
    com.china.center.common.taglib.PageSelectOption _jspx_th_p_005foption_005f1 = (com.china.center.common.taglib.PageSelectOption) _005fjspx_005ftagPool_005fp_005foption_005ftype_005fempty_005fnobody.get(com.china.center.common.taglib.PageSelectOption.class);
    _jspx_th_p_005foption_005f1.setPageContext(_jspx_page_context);
    _jspx_th_p_005foption_005f1.setParent(null);
    _jspx_th_p_005foption_005f1.setType("presentFlag");
    _jspx_th_p_005foption_005f1.setEmpty(true);
    int _jspx_eval_p_005foption_005f1 = _jspx_th_p_005foption_005f1.doStartTag();
    if (_jspx_th_p_005foption_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fp_005foption_005ftype_005fempty_005fnobody.reuse(_jspx_th_p_005foption_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fp_005foption_005ftype_005fempty_005fnobody.reuse(_jspx_th_p_005foption_005f1);
    return false;
  }

  private boolean _jspx_meth_c_005fforEach_005f4(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_005fforEach_005f4 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _005fjspx_005ftagPool_005fc_005fforEach_005fvar_005fitems.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    _jspx_th_c_005fforEach_005f4.setPageContext(_jspx_page_context);
    _jspx_th_c_005fforEach_005f4.setParent(null);
    _jspx_th_c_005fforEach_005f4.setItems((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${locationList}", java.lang.Object.class, (PageContext)_jspx_page_context, null, false));
    _jspx_th_c_005fforEach_005f4.setVar("item");
    int[] _jspx_push_body_count_c_005fforEach_005f4 = new int[] { 0 };
    try {
      int _jspx_eval_c_005fforEach_005f4 = _jspx_th_c_005fforEach_005f4.doStartTag();
      if (_jspx_eval_c_005fforEach_005f4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t<option value=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.id}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write('"');
          out.write('>');
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.name}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("</option>\r\n");
          out.write("\t\t\t\t\t\t\t\t\t");
          int evalDoAfterBody = _jspx_th_c_005fforEach_005f4.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fforEach_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_c_005fforEach_005f4[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_c_005fforEach_005f4.doCatch(_jspx_exception);
    } finally {
      _jspx_th_c_005fforEach_005f4.doFinally();
      _005fjspx_005ftagPool_005fc_005fforEach_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f4);
    }
    return false;
  }

  private boolean _jspx_meth_c_005fforEach_005f5(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_005fforEach_005f5 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _005fjspx_005ftagPool_005fc_005fforEach_005fvar_005fitems.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    _jspx_th_c_005fforEach_005f5.setPageContext(_jspx_page_context);
    _jspx_th_c_005fforEach_005f5.setParent(null);
    _jspx_th_c_005fforEach_005f5.setItems((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${locationList}", java.lang.Object.class, (PageContext)_jspx_page_context, null, false));
    _jspx_th_c_005fforEach_005f5.setVar("item");
    int[] _jspx_push_body_count_c_005fforEach_005f5 = new int[] { 0 };
    try {
      int _jspx_eval_c_005fforEach_005f5 = _jspx_th_c_005fforEach_005f5.doStartTag();
      if (_jspx_eval_c_005fforEach_005f5 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("\t\t\t\t\t\t\t\t\t\t<option value=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.id}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write('"');
          out.write('>');
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.name}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("</option>\r\n");
          out.write("\t\t\t\t\t\t\t\t\t");
          int evalDoAfterBody = _jspx_th_c_005fforEach_005f5.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fforEach_005f5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_c_005fforEach_005f5[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_c_005fforEach_005f5.doCatch(_jspx_exception);
    } finally {
      _jspx_th_c_005fforEach_005f5.doFinally();
      _005fjspx_005ftagPool_005fc_005fforEach_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f5);
    }
    return false;
  }
}
