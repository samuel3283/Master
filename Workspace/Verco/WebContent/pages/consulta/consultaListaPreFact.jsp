<%@ include file="/includes/taglibs.jsp"%>
<%@ taglib uri="WEB-INF/struts-html" prefix="html"%>
<%@ taglib uri="WEB-INF/struts-logic" prefix="logic"%>
<%@ taglib uri="WEB-INF/struts-bean" prefix="bean"%>
<%@ taglib uri="WEB-INF/displaytag" prefix="display"%>
<meta http-equiv=refresh content=120;URL=<%=request.getContextPath()%>/consultaAction.do?method=consultaPreFacturacion>
<link href="../calendario/calendar-win2k-cold-1.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../calendario/calendar.js"></script>
<script type="text/javascript" src="../calendario/calendar-setup.js"></script>
<script type="text/javascript" src="../calendario/lang/calendar-en.js"></script>
<style type="text/css">@import url("../calendario/calendar-win2k-cold-1.css");
</style>
<script>
function abrirXls(){
	document.getElementById("idConsultaForm").action="<%=request.getContextPath()%>/consultaAction.do?method=consultaPreFacturacion&tipocon=3";
	document.getElementById("idConsultaForm").submit();
}
</script>
  <body>
    <html:form action="/consultaAction.do" styleId="idConsultaForm">
    <html:hidden property="method" />
	<html:hidden property="strMensaje" />
	<html:hidden property="prefacturacionVO.periodoInicio" styleId="prefacturacionVO.periodoInicio"/>
    <html:hidden property="prefacturacionVO.periodoFin"    styleId="prefacturacionVO.periodoFin"/>	
    <table border="0" width="100%" >     
     <tr align="right"> 
        <td width="100%" valign="top"> 
        <% String tipoCons = (String)session.getAttribute("tipoCons") ; %>
        <% String rFecha = (String)session.getAttribute("rFechas") ; %>
        <% 	
		String fechainicio = (String)session.getAttribute("fInicios") ;
		String fechaFin = (String)session.getAttribute("fFins") ;
		%>
         Consulta por :  <%= rFecha %>         
        </td> 
      </tr>      
     <tr> 
        <td width="100%" valign="top"> 
          <table border="1">
             <tr> <td >
              
<div id="div_listaConsulta2" style="display: block">
			<table border="0" cellpadding="0" cellspacing="0">
			<%if ("P".equals(tipoCons)){ %> 
				<tr>
					<td>
					<font color="#336699"> :::. PRE FACTURACION .::: </font> 
					</td>
					<td>
					<a href="<%=request.getContextPath()%>/consultaAction.do?method=consultaPreFacturacion&tipocon=2&fInicios=<%=fechainicio%>&fFins=<%=fechaFin%>>" target="_self" >  
					<IMG SRC="../images/excel.jpg"  BORDER=0 ALT="Generar Reporte Excel">   
					</a>
					</td>
				</tr>
				<%}else{ %>
				<tr>
					<td>				
					<font color="#336699"> :::. OPERADORES DETALLES .::: </font> 
					</td>
					<td>					
					<a href="<%=request.getContextPath()%>/consultaAction.do?method=consultaPreFacturacion&tipocon=3&fInicios=<%=fechainicio%>&fFins=<%=fechaFin%>>" target="_self" >  
					<IMG SRC="../images/excel.jpg"  BORDER=0 ALT="Generar Reporte Excel">   
					</a>
					</td>
				</tr>
				<%}%>
			</table>			
<table style="width:660;border:1px; padding:0px;" class="tablaBordeGrisNormal">
<thead>
<tr>
<th class="newsTituloCabecera">OPERADOR</th>
<th class="newsTituloCabecera">PORTADOS</th>
<th class="newsTituloCabecera">RETORNOS</th>
<th class="newsTituloCabecera">OBJECIONES</th>
<th class="newsTituloCabecera">MONTO $</th>
</tr></thead>
<tbody>
<logic:iterate name="consultaForm" property="prefacturacionVO.listaPreFacturacion" id="lista" type="pe.com.nextel.provisioning.model.vo.PreFacturacionVO">
<tr class="odd">
<td style="width:40px;VERTICAL-ALIGN:top;TEXT-ALIGN:RIGHT" class="filaTabla">
 <bean:write name="lista" property="operador"/>
</td>
<td style="width:40px;VERTICAL-ALIGN:top;TEXT-ALIGN:RIGHT" class="filaTabla">
 <bean:write name="lista" property="portados"/>
</td>
<td style="width:40px;VERTICAL-ALIGN:top;TEXT-ALIGN:RIGHT" class="filaTabla">
<bean:write name="lista" property="retornos"/>
</td>
<td style="width:40px;VERTICAL-ALIGN:top;TEXT-ALIGN:RIGHT" class="filaTabla">
<bean:write name="lista" property="objeciones"/>
</td>
<td style="width:40px;VERTICAL-ALIGN:top;TEXT-ALIGN:RIGHT" class="filaTabla">
<bean:write name="lista" property="monto"/>
</td>
</tr></tbody>
</logic:iterate>
</table>
</div>             
      </td> </tr>
      <tr style="border:0">
      <td style="border:0"><br></td>
      </tr>
      <tr style="border:0">
      <td style="border:0"><br></td>
      </tr>
      <tr style="border:0">
      <td style="border:0"><br></td>
      </tr>
    </table>
    </td> 
      </tr>
      <tr> 
     </table>
    </html:form>
  </body>




