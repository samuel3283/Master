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
					<td colspan="6" >
					<font color="#336699">Consulta Estadisticas</font>
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
     <logic:present name="consultaForm" property="listaMensajes">
     
     <table width="60%" border="1">
     <tr bgcolor="#336699">
     <th align="left">Operador</th>
     <th align="center">Exito</th>
     <th align="center">Sin Exito</th>
     <th align="center">Pendientes</th>
     <th align="center">Total</th>
     </tr>
     
     <logic:iterate name="consultaForm" property="listaMensajes" 
     id="lista" type="pe.com.nextel.provisioning.model.vo.EstadisticaVO">
     <tr>
     <td align="left"><bean:write name="lista" property="operador"/></td>
     <td align="right"><bean:write name="lista" property="exito"/></td>
     <td align="right"><bean:write name="lista" property="sinExito"/></td>
     <td align="right"><bean:write name="lista" property="pendiente"/></td>
     <td align="right"><bean:write name="lista" property="totales"/></td>
     </tr>     
     
     </logic:iterate>
     
     </table>
     </logic:present>
     </td>
     </tr>
     
     
     </table>
    </html:form>
  </body>
</html:html>
<script type="text/javascript">
function consultar() {
   	document.forms['idConsultaForm'].method.value = 'buscarEstadistica';
    document.forms['idConsultaForm'].submit();
}
function limpiar(){
   	document.forms['idConsultaForm'].method.value = 'initConsultaEstadsitica';
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

