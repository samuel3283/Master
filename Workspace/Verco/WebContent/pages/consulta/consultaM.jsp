<%@ include file="/includes/taglibs.jsp"%>
<link href="../calendario/calendar-win2k-cold-1.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../calendario/calendar.js"></script>
<script type="text/javascript" src="../calendario/calendar-setup.js"></script>
<script type="text/javascript" src="../calendario/lang/calendar-en.js"></script>
<style type="text/css">
@import url("../calendario/calendar-win2k-cold-1.css");
</style>
<html:html>
 <body>
    <html:form action="/consultaAction.do" styleId="idConsultaForm">
    <html:hidden property="method" />
	<html:hidden property="strMensaje" />
    
    <table border="0" width="100%" >
     <tr>
        <td> 
           <table width="680" border="0" cellpadding="0" cellspacing="3"
				class="gNarrowContentSection">
				<tr>
					<td colspan="6" class="subtitulo">Consulta Generales</td>
				</tr>
				
				<tr>
					<td width="125" class="textBold">Fecha :</td>
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
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
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
     </table>
    </html:form>
  </body>
</html:html>
<script type="text/javascript">
function consultar() {
   	document.forms['idConsultaForm'].method.value = 'consultarMensajes';
    document.forms['idConsultaForm'].submit();
}
function limpiar(){
	document.forms['idConsultaForm'].nombre.value="";
	document.getElementById('div_listaConsulta').style.display = "none";
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

