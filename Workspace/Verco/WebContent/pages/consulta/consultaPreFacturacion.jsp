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
	<html:hidden property="prefacturacionVO.tipoCons" value="F" styleId="tipBus"/>	
    <html:hidden property="prefacturacionVO.periodoInicio" styleId="prefacturacionVO.periodoInicio"/>
    <html:hidden property="prefacturacionVO.periodoFin"    styleId="prefacturacionVO.periodoFin"/>
    <table border="0" width="100%" >
     <tr>
        <td>
           <table width="680" border="0" cellpadding="0" cellspacing="3" class="gNarrowContentSection">
           
				<tr>
					<td colspan="6" >
					<font color="#336699">Seleccione tipo de búsqueda</font>
					</td>
				</tr>				
				<tr>
					<td colspan="6" >
					<font color="">
					<input type="radio" name="busqueda" checked onclick="document.getElementById('tipBus').value='F';" ></>Por Fecha:
					</font>
					</td>
				</tr>
				<tr>				
					<td>
					Fecha Inicio :<html:text
						styleId="nombre" property="prefacturacionVO.fechaInicio"
						size="12" maxlength="10" styleClass="formInputBoxText"
						onkeyup="this.value=formateafecha(this.value);document.getElementById('tipBus').value='F';" 
						/> <a>
					<img alt="" border="0"
						src="<%=request.getContextPath()%>/pages/images/cal.gif"
						id="cal-button-1P" class="botonCalendario" /> <script
						type="text/javascript">
					Calendar.setup({
					inputField    : "prefacturacionVO.fechaInicio",
					button        : "cal-button-1P"
					 });
					</script> </a>					
					</td>				
					<td>Fecha Fin
					<html:text
						styleId="nombre" property="prefacturacionVO.fechaFin" size="12"
						maxlength="10" styleClass="formInputBoxText"
						onkeyup="this.value=formateafecha(this.value);document.getElementById('tipBus').value='F';" /> <a> <img
						alt="" border="0"
						src="<%=request.getContextPath()%>/pages/images/cal.gif"
						id="cal-button-2P" class="botonCalendario" /> <script
						type="text/javascript">
					Calendar.setup({
					inputField    : "prefacturacionVO.fechaFin",
					button        : "cal-button-2P"
					 });
					</script> </a>					
				</tr>
				<tr>
					<td colspan="6" >
					<font color="">
					<input type="radio" name="busqueda" onclick="document.getElementById('tipBus').value='P';"></>Por periodo:
					</font>
					</td>
				</tr>
				<tr>
				<td class="azlc"><span class="txt">Desde:</span>
				<br/>&nbsp;&nbsp;&nbsp;&nbsp;												
				<html:select property="prefacturacionVO.mesInicio" styleId="prefacturacionVO.mesInicio">												
				<html:option value="00"> Mes</html:option>													
				<html:option value="01">Enero</html:option>													
				<html:option value="02">Febrero</html:option>												
				<html:option value="03">Marzo</html:option>													
				<html:option value="04">Abril</html:option>													
				<html:option value="05">Mayo</html:option>													
				<html:option value="06">Junio</html:option>													
				<html:option value="07">Julio</html:option>													
				<html:option value="08">Agosto</html:option>												
				<html:option value="09">Septiembre</html:option>													
				<html:option value="10">Octubre</html:option>													
				<html:option value="11">Noviembre</html:option>													
				<html:option value="12">Diciembre</html:option>													
				</html:select>
				<html:select property="prefacturacionVO.anioInicio" styleId="prefacturacionVO.anioInicio">	
				<html:option value="00" >A&ntilde;o</html:option>													
				<html:option value="2009">2009</html:option>													
				<html:option value="2010">2010</html:option>													
				<html:option value="2011">2011</html:option>
				<html:option value="2012">2012</html:option>
				</html:select>
				<br/>
				<span class="txt">hasta:</span>
				<br/>&nbsp;&nbsp;&nbsp;&nbsp;
				<html:select property="prefacturacionVO.mesfin" styleId="prefacturacionVO.mesfin">	
				<html:option value="00"> Mes</html:option>													
				<html:option value="01">Enero</html:option>													
				<html:option value="02">Febrero</html:option>												
				<html:option value="03">Marzo</html:option>													
				<html:option value="04">Abril</html:option>													
				<html:option value="05">Mayo</html:option>													
				<html:option value="06">Junio</html:option>													
				<html:option value="07">Julio</html:option>													
				<html:option value="08">Agosto</html:option>												
				<html:option value="09">Septiembre</html:option>													
				<html:option value="10">Octubre</html:option>													
				<html:option value="11">Noviembre</html:option>													
				<html:option value="12">Diciembre</html:option>														
				</html:select>
				<html:select property="prefacturacionVO.aniofin" styleId="prefacturacionVO.aniofin">
				<html:option  value="00">A&ntilde;o</html:option>	
				<html:option  value="2009">2009</html:option>	
				<html:option  value="2010">2010</html:option>
				<html:option  value="2011">2011</html:option>
				<html:option  value="2012">2012</html:option>
				</html:select>
				</td>
				</tr>
				<tr>
					<td colspan="6" align="right">
					
				<a href="javascript:consultar(document.getElementById('tipBus').value)">
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
function consultar(tipoCons) {	
	if (tipoCons=="P"){
	mesInicio=document.getElementById("prefacturacionVO.mesInicio");
	anioInicio=document.getElementById("prefacturacionVO.anioInicio");
	mesfin=document.getElementById("prefacturacionVO.mesfin");
	aniofin=document.getElementById("prefacturacionVO.aniofin");
	mesInicioV=mesInicio.options[mesInicio.selectedIndex].value;
	anioInicioV=anioInicio.options[anioInicio.selectedIndex].value;
	mesfinV=mesfin.options[mesfin.selectedIndex].value;
	aniofinV=aniofin.options[aniofin.selectedIndex].value;
	if((mesInicioV+anioInicioV)=='0000'){alert('Inicio de Periodo Inválido');return;}
	if((mesfinV+aniofinV)=='0000'){alert('Fin de Periodo Inválido');return;}
	document.getElementById("prefacturacionVO.periodoInicio").value=mesInicioV+anioInicioV;
	document.getElementById("prefacturacionVO.periodoFin").value=mesfinV+aniofinV;
	incioVfi='01-'+mesInicioV+'-'+anioInicioV;
	incioVff='01-'+mesfinV+'-'+aniofinV;	
	if (Date.parse(incioVff)>=Date.parse(incioVfi)){
		document.forms['idConsultaForm'].method.value = 'consultaPreFacturacion';
	    document.forms['idConsultaForm'].submit();
       }else{
       	alert("Ingrese parámetros de Periodos Válidas");
       }
	}else{
		 date_inicial=document.getElementById("prefacturacionVO.fechaInicio").value;
		 date_final=document.getElementById("prefacturacionVO.fechaFin").value;
		    if (parseInt(date_inicial.length)>0  && parseInt(date_final.length)>0){
		          date_inicial=date_inicial.replace('/','-');
		          date_final=date_final.replace('/','-');
		          date_inicial=date_inicial.replace('/','-');
		          date_final=date_final.replace('/','-');		          
		            if (Date.parse(date_final)>=Date.parse(date_inicial)){
		            	document.forms['idConsultaForm'].method.value = 'consultaPreFacturacion';
		        	    document.forms['idConsultaForm'].submit();
		                }else{
		                  alert("Ingrese parámetros de fechas Válidas");
		                }
		          }else{alert("Debe seleccionar el rango de fechas");}

				
	}   	
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
function obtenerMesActual(combo){
	for (var i=0;i<combo.length;i++){
		//alert(combo.options[i].value);		 	
		}
}
//provisioning_mostrarMensajeError('idConsultaForm');
//window.onload=obtenerMesActual(document.getElementById("prefacturacionVO.mesInicio"));
</script>

