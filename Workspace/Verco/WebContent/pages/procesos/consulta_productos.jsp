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
    <html:hidden property="codigo" />
	<html:hidden property="strMensaje" />
    
    <table border="0" width="100%" >
     <tr>
        <td> 
           <table width="680" border="0" cellpadding="0" cellspacing="3"
				class="gNarrowContentSection">
				<tr>
					<td colspan="4" align="center">
					<font color="#336699" size="3"><br>MOSTRAR PRODUCTOS</font>
					</td>
				</tr>
				<tr>
					<td colspan="4" align="center">&nbsp;</td>
				</tr>
				
				<tr>
					<td width="25%" class="textBold">Tipo de Producto:</td>
					<td width="25%"><span class="textBold"> 
					<html:select styleId="tipoproducto" property="productoBean.tipoproducto" styleClass="formInputBoxText">
					<html:option value="0">TODOS</html:option>
					<html:option value="P002">ZAPATILLAS</html:option>
					</html:select>
					</span></td>
					
					
					<td width="25%" class="textBold">Nombre de Producto:</td>
					<td width="25%"><span class="textBold"> 
					<html:text styleId="nombre" property="productoBean.nombre" size="12"
						maxlength="10" styleClass="formInputBoxText"
						onkeyup="this.value=formateafecha(this.value);" /> 
					</span></td>
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
     
     <tr> <td>&nbsp;</td></tr>
     
     
     <tr>
     <td>
      <table width="680" border="0" cellpadding="0" cellspacing="3"	class="gNarrowContentSection">
		<tr>	
		<th bgcolor="#336699"><font color="#FFFFFF">Producto</font></th>
		<th bgcolor="#336699"><font color="#FFFFFF">Tipo Producto</font></th>
		<!-- 
		<th bgcolor="#336699"><font color="#FFFFFF">Stock</font></th>
		 -->
		<th bgcolor="#336699"><font color="#FFFFFF">Precio S/.</font></th>
		<th bgcolor="#336699"><font color="#FFFFFF">Detalle</font></th>
		<th bgcolor="#336699"><font color="#FFFFFF">Acciones</font></th>
		</tr>
		

	<logic:present name="consultaForm" property="listaMensajes">
	
	<logic:iterate name="consultaForm" property="listaMensajes" id="lista" type="pe.com.nextel.provisioning.model.vo.Producto">
	 <tr>
     <td>&nbsp; <bean:write name="lista" property="nombre"/></td>
     <td>&nbsp; <bean:write name="lista" property="tipoproducto"/></td>
     <!--
     <td align="right">&nbsp; <bean:write name="lista" property="stock"/></td>
     -->
     <td align="right">&nbsp; <bean:write name="lista" property="precio"/></td>
     <td>&nbsp; <bean:write name="lista" property="descripcion"/></td>
     <td>&nbsp; 
     <a href="javascript:disponibilidad(<bean:write name='lista' property='codigo'/>)">
     <img src="<%=request.getContextPath()%>/images/lupa_popup.gif" border="0" >
     </a>
	&nbsp;&nbsp; 
     <a href="<%=request.getContextPath()%>/consultaAction.do?method=dispro" title="Disponibilidad de Producto" target=":BLANK">
     <img src="<%=request.getContextPath()%>/images/calendar.gif" border="0" >
     </a>
     </td>
     </tr>


	</logic:iterate>
	</logic:present>


	<logic:notPresent name="consultaForm" property="listaMensajes">
    			
	 <tr>
     <td>&nbsp; No se encontraron registros.</td>
     </tr>
	</logic:notPresent>
	     
	 </table>
				
     </td>
     </tr>
     
     </table>
    </html:form>
  </body>
</html:html>
<script type="text/javascript">


function disponibilidad(codproducto) {
   	document.forms['idConsultaForm'].method.value = 'dispro';
   	document.forms['idConsultaForm'].codigo.value = codproducto;
    document.forms['idConsultaForm'].submit();
//window.open('<%=request.getContextPath()%>/consultaAction.do?method=dispro','_BLANK');
//window.open('<%=request.getContextPath()%>/consultaAction.do?method=dispro','_BLANK','_BLANK','height=60,width=60,top=60,left=80');
}
function consultar() {
   	document.forms['idConsultaForm'].method.value = 'buscapro';
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

