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
        <td align="center"> 
           <table width="90%" border="0" cellpadding="0" cellspacing="3"
				class="gNarrowContentSection">
				<tr>
					<td colspan="2" align="center">
					<font color="#336699" size="3"><br>REGISTRO DE CLIENTE </font>
					</td>
				</tr>
				
				<tr>
					<td colspan="2" >&nbsp;</td>
				</tr>	
				
				<tr>
					<td width="125" class="textBold">Código Cliente:</td>
					<td width="315"><span class="textBold"> 
					<html:text styleId="codcliente" property="clienteBean.codcliente" size="40" maxlength="100" styleClass="formInputBoxText"/>
					</span></td>
				</tr>						
				<tr>
					<td width="125" class="textBold">Nombre:</td>
					<td width="315"><span class="textBold"> 
					<html:text styleId="nombre" property="clienteBean.nombre" size="40" maxlength="100" styleClass="formInputBoxText"/>
					</span></td>
				</tr>
				
				<tr>
					<td width="125" class="textBold">Apellidos:</td>
					<td width="315"><span class="textBold"> 
					<html:text styleId="apellidos" property="clienteBean.apellidos" size="40" maxlength="100" styleClass="formInputBoxText"/>
					</span></td>
				</tr>
				
				<tr>
					<td width="125" class="textBold">Tipo Documento:</td>
					<td width="315"><span class="textBold"> 
					<html:select styleId="tipodocumento" property="clienteBean.tipodocumento" styleClass="formInputBoxText">
					<html:option value="01">DNI</html:option>
					<html:option value="03">RUC</html:option>
					<html:option value="05">CARNET EXTRANJERIA</html:option>
					</html:select>
					</span></td>
				</tr>					
				<tr>
					<td width="125" class="textBold">Nro Documento:</td>
					<td width="315"><span class="textBold"> 
					<html:text styleId="dni" property="clienteBean.dni" size="20" maxlength="8" styleClass="formInputBoxText"/>
					</span></td>
				</tr>						
				
				<tr>
					<td width="125" class="textBold">Dirección:</td>
					<td width="315"><span class="textBold"> 
					<html:text styleId="direccion" property="clienteBean.direccion" size="20" maxlength="8" styleClass="formInputBoxText"/>
					</span></td>
				</tr>	
							
				<tr>
					<td width="125" class="textBold">Email:</td>
					<td width="315"><span class="textBold"> 
					<html:text styleId="email" property="clienteBean.email" size="20" maxlength="8" styleClass="formInputBoxText"/>
					</span></td>
				</tr>	
				
				<tr>
					<td width="125" class="textBold">Telefono:</td>
					<td width="315"><span class="textBold"> 
					<html:text styleId="telefono" property="clienteBean.telefono" size="20" maxlength="8" styleClass="formInputBoxText"/>
					</span></td>
				</tr>										
				<tr>
					<td width="125" class="textBold">Fecha Nacimiento:
					
					
					</td>
					<td width="315"><span class="textBold"> <html:text
						styleId="fechanacimiento" property="clienteBean.fechanacimiento"
						size="12" maxlength="10" styleClass="formInputBoxText"
						onkeyup="this.value=formateafecha(this.value);" /> <a>
					<img alt="" border="0"
						src="<%=request.getContextPath()%>/pages/images/cal.gif"
						id="cal-button-1P" class="botonCalendario" /> <script
						type="text/javascript">
					Calendar.setup({
					inputField    : "clienteBean.fechanacimiento",
					button        : "cal-button-1P"
					 });
					</script> </a> </span></td>
					

				</tr>

				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td colspan="4" align="right">
					
				<a href="javascript:consultar()">
				<img src="<%=request.getContextPath()%>/pages/images/btnguardar.png" width="74" height="24" border="0"/>
				</a>
				
				<a href="javascript:limpiar()">
				<img src="<%=request.getContextPath()%>/pages/images/btnlimpiar.png" width="74" height="24" border="0"/>
				</a>
				
		  </td>
	     </tr>
		</table>
       </td>
     </tr>
     
     <tr><td>
     &nbsp;&nbsp;&nbsp;
     <br><br>
     <logic:present name="ConsultaForm" property="strMensaje">
     	<bean:write name="ConsultaForm" property="strMensaje"/>
     </logic:present>
     </td>
     </tr>
     </table>
    </html:form>
  </body>
</html:html>
<script type="text/javascript">
function consultar() {
   	document.forms['idConsultaForm'].method.value = 'registrocli';
    document.forms['idConsultaForm'].submit();
    
}
function limpiar(){
	document.getElementById("nombre").value='';
	document.getElementById("apellidos").value='';
	document.getElementById("codcliente").value='';

	document.getElementById("direccion").value='';
	document.getElementById("email").value='';
	document.getElementById("telefono").value='';
	document.getElementById("dni").value='';
	document.getElementById("fechanacimiento").value='';

	
	//document.getElementById('div_listaConsulta').style.display = "none";
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

