<%@ include file="/includes/taglibs.jsp"%>
<meta http-equiv=refresh content=120;URL=<%=request.getContextPath()%>/consultaAction.do?method=consultar>
<link href="../calendario/calendar-win2k-cold-1.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../calendario/calendar.js"></script>
<script type="text/javascript" src="../calendario/calendar-setup.js"></script>
<script type="text/javascript" src="../calendario/lang/calendar-en.js"></script>
<style type="text/css">@import url("../calendario/calendar-win2k-cold-1.css");
</style>
  <body>
    <html:form action="/consultaAction.do" styleId="idConsultaForm">
    <html:hidden property="method" />
	<html:hidden property="strMensaje" />
    <table border="0" width="100%" >
     
     <tr align="right"> 
        <td width="100%" valign="top"> 
        <% String rFecha = (String)session.getAttribute("rFecha") ; %>
         Consulta por :  <%= rFecha %> 
         <a href="../consulta/consultaXLS.jsp" target="_BLANK" >  <IMG SRC="../images/excel.jpg"  BORDER=0 ALT="Generar Reporte Excel"> </a>
         <a href="javascript:window.print()"> Imprimir </a>
        </td> 
      </tr>
     <tr> 
        <td width="100%" valign="top"> 
          <table border="1">
             <tr> <td > <%@ include file="/pages/consulta/consulta2.jsp"%></td> </tr>
            </table>
        </td> 
      </tr>
      <tr> 
        <td width="100%" valign="top"> 
          <table border="1">
             <tr> <td > <%@ include file="/pages/consulta/consulta3.jsp"%></td> </tr>
            </table>
        </td> 
      </tr>
      
       <tr> 
        <td width="100%" valign="top"> 
          <table border="1">
             <tr> <td > <%@ include file="/pages/consulta/consulta4.jsp"%></td> </tr>
            </table>
        </td> 
      </tr>
      
       <tr> 
        <td width="100%" valign="top"> 
          <table border="1">
             <tr> <td > <%@ include file="/pages/consulta/consulta5.jsp"%></td> </tr>
            </table>
        </td> 
      </tr>
      
    </table>
    </html:form>
  </body>




