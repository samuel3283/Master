<%@ include file="/includes/taglibs.jsp"%>
<%@ page import="pe.com.nextel.provisioning.controller.form.ConsultaForm"%>
<%@ page import="pe.com.nextel.provisioning.model.vo.Consulta1VO"%>
<link href="../calendario/calendar-win2k-cold-1.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../calendario/calendar.js"></script>
<script type="text/javascript" src="../calendario/calendar-setup.js"></script>
<script type="text/javascript" src="../calendario/lang/calendar-en.js"></script>
<style type="text/css">
@import url("../calendario/calendar-win2k-cold-1.css");
</style>
<%
String bandera  = (request.getAttribute("bandera")==null)?"0":request.getAttribute("bandera").toString();
%>
<html:html>
<script language="javascript" type="text/javascript">
function goToPoint(existe)
{
	 if(existe=="1") {
		alert("Se procede a Procesar Archivos para la fecha selecionada en segundo plano.");
		document.getElementsByName("cabeceraArchivo.fecha")[0].value="";
	 } else if(existe=="2") {   
		alert("No se puede Procesar estos archivos. Verifiquelo en el módulo de Reproceso");			 
		document.getElementsByName("cabeceraArchivo.fecha")[0].value="";
	 } else if(existe=="3") {   
			alert("Fecha no puede ser mayor que la actual...");			 
			document.getElementsByName("cabeceraArchivo.fecha")[0].value="";
	 } 
		 
}

function consultar() {
   	document.forms['idConsultaForm'].method.value = 'buscarMensajeRec';
    document.forms['idConsultaForm'].submit();
}
function limpiar(){
   	document.forms['idConsultaForm'].method.value = 'initMensajeRec';
    document.forms['idConsultaForm'].submit();
}
function provisioning_mostrarMensajeError(miform){
	if(document.forms[miform].strMensaje != null){
		if(document.forms[miform].strMensaje.value != ''){
			var msg = document.forms[miform].strMensaje.value;
			document.forms[miform].strMensaje.value = null;
			alert(msg);
		}
	}
}
function generarExcel() {
    window.open('<%=request.getContextPath()%>/consultaAction.do?method=mensajeRecXls','window','height=300,width=500,status=yes,toolbar=yes,resizable=yes,scrollbars=yes');
}
provisioning_mostrarMensajeError('idConsultaForm');   
</script>

<body
 <%if(!bandera.equals("0"))
  { %> onload="goToPoint('<%=(String)request.getAttribute("bandera")%>');"
  <%System.out.println("********* Entro bandera");
    } %>
>

    <html:form action="/consultaAction.do" styleId="idConsultaForm">
    <html:hidden property="method" />
	<html:hidden property="strMensaje" />
    
    <table border="0" width="100%" >
     <tr>
        <td> 
           <table width="680" border="0" cellpadding="0" cellspacing="3"
				class="gNarrowContentSection">
				<tr>
					<td colspan="6" >
					<font color="#336699">Consulta Retornos por Fecha</font>
					</td>
				</tr>
				
				<tr>
					<td width="125" class="textBold">Fecha Proceso :</td>
					<td width="315"><span class="textBold"> <html:text
						styleId="nombre" property="consulta1VO.fecha_inicio"
						size="12" maxlength="10" styleClass="formInputBoxText"
						onkeyup="this.value=formateafecha(this.value);" /> <a>
					<img alt="" border="0"
						src="<%=request.getContextPath()%>/pages/images/cal.gif"
						id="cal-button-1P" class="botonCalendario" /> <script
						type="text/javascript">
					Calendar.setup({
					inputField    : "consulta1VO.fecha_inicio",
					button        : "cal-button-1P"
					 });
					</script> </a> </span></td>
					
					
					<td width="125" class="textBold">Fecha Fin :</td>
					<td width="224"><span class="textBold"> <html:text
						styleId="nombre" property="consulta1VO.fecha_fin" size="12"
						maxlength="10" styleClass="formInputBoxText"
						onkeyup="this.value=formateafecha(this.value);" /> <a> <img
						alt="" border="0"
						src="<%=request.getContextPath()%>/pages/images/cal.gif"
						id="cal-button-2P" class="botonCalendario" /> <script
						type="text/javascript">
					Calendar.setup({
					inputField    : "consulta1VO.fecha_fin",
					button        : "cal-button-2P"
					 });
					</script> </a> </span></td>
					
					<td width="7" class="textBold">&nbsp;</td>
					<td width="9">&nbsp;</td>
				</tr>

				<tr>
					<td class="textBold">&nbsp;</td>
					<td>&nbsp;</td>
					<td class="textBold">&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				
				<tr>
					<td colspan="6" align="right">
					
				<a href="javascript:consultar()">
				<img src="<%=request.getContextPath()%>/pages/images/btnconsultar.png" width="74" height="24" border="0"/>
				</a>
				
				<a href="javascript:limpiar()">
				<img src="<%=request.getContextPath()%>/pages/images/btnlimpiar.png" width="74" height="24" border="0"/>
				</a>
				
				<br></br>
				<div align="left">
				<b>Se recomienda no ejecutar esta consulta, las horas que PortaFlow 
				ejecuta transacciones pesadas. (17:00:00, 00:00:00-01:00:00).
				</b></div>
				
		  </td>
	     </tr>
	     
	     
		</table>
       </td>
     </tr>
     
     <tr>
     <td>
	 <br>
     <logic:present name="consultaForm" property="listaMensajes">
     
     <a href="javascript:generarExcel()">  
     <IMG SRC="../images/excel.jpg"  BORDER=0 ALT="Generar Reporte Excel">
     </a> 
         
     <table width="90%" border="1">
     <tr bgcolor="#336699">
     <th align="center">Nro</th>
     <th align="center">Numero</th>
     <th align="center">Receptor</th>
     <th align="center">Cedente</th>
     <th align="center">Id Proceso</th>
     <th align="center">Fecha Registro</th>
     <th align="center">Motivo Retorno</th>
     <th align="center">Respuesta Retorno</th>
	</tr>
	<% int contador=0;%>
     <logic:iterate name="consultaForm" property="listaMensajes" 
     id="lista" type="pe.com.nextel.provisioning.model.vo.Consulta1VO">
      <% contador++;%>
     <tr>
     <td align="left">&nbsp;<%=contador%></td>
     <td align="left"><bean:write name="lista" property="telefono"/></td>
     <td align="left"><bean:write name="lista" property="receptor"/></td>
     <td align="left"><bean:write name="lista" property="cedente"/></td>
     <td align="left"><bean:write name="lista" property="identificadorProceso"/></td>
     <td align="left"><bean:write name="lista" property="fechaRegistro"/></td>
     <td align="left"><bean:write name="lista" property="motivoRetorno"/></td>
     <td align="left"><bean:write name="lista" property="respuestaRetorno"/></td>
     </tr>
	 
     </logic:iterate>
     </table>
     </logic:present>
     
     <logic:notPresent name="consultaForm" property="listaMensajes">
     Listado Vacio
     </logic:notPresent>
     
     </td>
     </tr>
     
     </table>
    </html:form>
  </body>
</html:html>


