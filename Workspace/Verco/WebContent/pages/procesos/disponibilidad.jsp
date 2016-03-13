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
    <html:hidden property="codigo" />
    
    <table border="0" width="100%" >
     <tr>
        <td align="center"> 
           <table width="90%" border="0" cellpadding="0" cellspacing="3"
				class="gNarrowContentSection">
				<tr>
					<td colspan="2" align="center">
					<font color="#336699" size="3"><br>DISPONIBILIDAD DE PRODUCTO </font>
					</td>
				</tr>
				
				<tr>
					<td colspan="2" >&nbsp;</td>
				</tr>	

				<!--  				
				<tr>
					<td width="35%" class="textBold">Código Cliente:</td>
					<td width="65%"><span class="textBold"> 
					<html:text styleId="codcliente" property="clienteBean.codcliente" size="40" maxlength="100" styleClass="formInputBoxText"/>
					</span></td>
				</tr>
				-->
										
				<tr>
					<td width="35%" class="textBold">Nombre Producto:</td>
					<td width="65%"><span class="textBold"> 
					<html:text styleId="nombre" property="producto.nombre" size="50" disabled="true" styleClass="formInputBoxText"/>
					</span></td>
				</tr>
				
				<tr>
					<td width="35%" class="textBold">Descripción:</td>
					<td width="65%"><span class="textBold"> 
					<html:text styleId="descripcion" property="producto.descripcion" size="50"  disabled="true" styleClass="formInputBoxText"/>
					</span></td>
				</tr>

				<tr>
					<td class="textBold">Tipo Producto:</td>
					<td><span class="textBold"> 
					<html:text styleId="tipoproducto" property="producto.tipoproducto" size="50" disabled="true" styleClass="formInputBoxText"/>
					</span></td>
				</tr>
				
				<tr>
					<td class="textBold">Stock:</td>
					<td><span class="textBold"> 
					<html:text styleId="stock" property="producto.stock" size="20"  disabled="true" styleClass="formInputBoxText"/>
					</span></td>
				</tr>						
				
				<tr>
					<td class="textBold">Precio:</td>
					<td><span class="textBold"> 
					<html:text styleId="precio" property="producto.precio" size="20"  disabled="true" styleClass="formInputBoxText"/>
					</span></td>
				</tr>	
							

				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td colspan="4" align="right">
					
				<a href="javascript:consultar()">
				<img src="<%=request.getContextPath()%>/pages/images/btnvolver.png" width="74" height="24" border="0"/>
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

