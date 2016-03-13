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
		alert("No se puede Procesar estos archivos. Verifiquelo en el m�dulo de Reproceso");			 
		document.getElementsByName("cabeceraArchivo.fecha")[0].value="";
	 } else if(existe=="3") {   
			alert("Fecha no puede ser mayor que la actual...");			 
			document.getElementsByName("cabeceraArchivo.fecha")[0].value="";
	 } 
		 
}
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
					<font color="#336699">Consulta Facturacion</font>
					</td>
				</tr>
				
				<tr>
					<td width="125" class="textBold">Fecha Inicio :</td>
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
				
		  </td>
	     </tr>
	     
	     
		</table>
       </td>
     </tr>
     
     <tr>
     <td>

     <logic:present name="consultaForm" property="listaFacturacion">
     <logic:iterate name="consultaForm" property="listaFacturacion" 
     id="lista" type="pe.com.nextel.provisioning.model.vo.Consulta1VO">
     
     <bean:write name="lista" property="telefono"/><br>
     </logic:iterate>
     </logic:present>
     
     <logic:notPresent name="consultaForm" property="listaFacturacion">
     Listado Vacio
     </logic:notPresent>
     
     </td>
     </tr>
     
     </table>
    </html:form>
  </body>
</html:html>
<script type="text/javascript">
function consultar() {
   	document.forms['idConsultaForm'].method.value = 'buscarFacturacion';
    document.forms['idConsultaForm'].submit();
}
function limpiar(){
	document.forms['idConsultaForm'].nombre.value="";
	document.getElementById('div_listaConsulta').style.display = "none";
   	document.forms['idConsultaForm'].method.value = 'limpiarFacturacion';
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
provisioning_mostrarMensajeError('idConsultaForm');    
</script>

