<%@ taglib uri="WEB-INF/struts-html" prefix="html"%>
<%@ taglib uri="WEB-INF/struts-logic" prefix="logic"%>
<%@ taglib uri="WEB-INF/struts-bean" prefix="bean"%>
<%@ taglib uri="WEB-INF/struts-tiles" prefix="tiles"%>

<HTML>
<HEAD>
	<!--  
	<meta http-equiv="refresh" content="600" />
	-->
   <title>  VERCO - Venta de Articulos deportivos </title>
    <html:base/>
    
    
    <link rel="stylesheet" type="text/css" href="<html:rewrite page='/pages/css/estilos.css'/>">
	<script language='javascript' type="text/javascript" src="<%=request.getContextPath()%>/pages/js/script.js"></script>
	<script language='javascript' type="text/javascript" src="<%=request.getContextPath()%>/pages/js/fecha.js"></script>
	<script language='javascript' type="text/javascript" src="<%=request.getContextPath()%>/pages/js/popcalendar.js"></script>
	<script src="<%=request.getContextPath()%>/pages/js/boxover.js"></script>
</HEAD>	   


<body bgcolor="#DFE6EE" text="#000000" link="#023264" alink="#023264" vlink="#023264">
    <table align="center" border="0" width="800" height="100%" cellpadding="0" cellspacing="0">
        <tr>
          <td colspan="2" height="80" width="800" valign="top">
          <tiles:insert attribute="header" />
          </td>
        </tr>
        <tr>
          <td width="18%" valign="top" class="bttntext">
            <tiles:insert attribute="menu"/>
            
          </td>
          <td width="82%" valign="top" align="left">
            <tiles:insert attribute="body" />
            
          </td>
        </tr>
        <tr>
          <td colspan="2">
            <tiles:insert attribute="footer" />
            
          </td>
        </tr>
    </table>
</body>
</html>