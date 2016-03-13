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
					<font color="#336699">Consulta Generales</font>
					</td>
				</tr>
				
				<tr>
					<td width="190" class="textBold">
					<font color="#336699">Tipo de Reporte</font>
					</td>
					<td  width="125" class="textBold">
					<font color="#336699">
					<html:select property="reporteVO.tipoReporte" styleId="reporteVO.idrepor">
					<html:option value="-">[--Tipo Reporte--]</html:option>
					<html:optionsCollection property="reporteVO.listaTipoReporte"	label="nombrereporte" value="idrepor" />
					</html:select>
					</font>
					</td>
				
					<td width="180" class="textBold">Fecha Inicio :</td>
					<td width="315"><span class="textBold"> <html:text
						styleId="reporteVO.fechaInicio" property="reporteVO.fechaInicio"
						size="12" maxlength="10" styleClass="formInputBoxText"
						onkeyup="this.value=formateafecha(this.value);" /> <a>
					<img alt="" border="0"
						src="<%=request.getContextPath()%>/pages/images/cal.gif"
						id="cal-button-1P" class="botonCalendario" /> <script
						type="text/javascript">
					Calendar.setup({
					inputField    : "reporteVO.fechaInicio",
					button        : "cal-button-1P"
					 });
					</script> </a> </span></td>
					<td width="125" class="textBold">Fecha Fin :</td>
					<td width="224"><span class="textBold"> <html:text
						styleId="reporteVO.fechaFin" property="reporteVO.fechaFin" size="12"
						maxlength="10" styleClass="formInputBoxText"
						onkeyup="this.value=formateafecha(this.value);" /> <a> <img
						alt="" border="0"
						src="<%=request.getContextPath()%>/pages/images/cal.gif"
						id="cal-button-2P" class="botonCalendario" /> <script
						type="text/javascript">
					Calendar.setup({
					inputField    : "reporteVO.fechaFin",
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
					
				<a href="javascript:calcula('reporteVO.fechaInicio','reporteVO.fechaFin')">
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
    <% 
    	String mensaje=(String)request.getAttribute("mensajeJobReporte");
    	if (mensaje==null){mensaje="";}
    	if (mensaje.length()>0){
    %>
    <center>
    <div id="divcontenedor">
    	<b><h5><%=mensaje %></h5></b>
    	</div>    	
    </center>
    <%} 
    	request.setAttribute("mensajeJobReporte","");
    %>
  </body>
</html:html>
<script type="text/javascript">
function consultar() {
   	document.forms['idConsultaForm'].method.value = 'ConsultaProcesoJobReporte';
    document.forms['idConsultaForm'].submit();
}
function limpiar(){	
	document.getElementById("reporteVO.fechaInicio").value="";
	document.getElementById("reporteVO.fechaFin").value="";
	//document.getElementById('div_listaConsulta').style.display = "none";
	document.getElementById('divcontenedor').innerHTML='';	
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
<script language="JavaScript">
   function cerosIzq(sVal, nPos){
    var sRes = sVal;
    for (var i = sVal.length; i < nPos; i++)
     sRes = "0" + sRes;
    return sRes;
   }

   function armaFecha(nDia, nMes, nAno){
    var sRes = cerosIzq(String(nDia), 2);
    sRes = sRes + "/" + cerosIzq(String(nMes), 2);
    sRes = sRes + "/" + cerosIzq(String(nAno), 4);
    return sRes;
   }

   function sumaMes(nDia, nMes, nAno, nSum){
    if (nSum >= 0){
     for (var i = 0; i < Math.abs(nSum); i++){
      if (nMes == 12){
       nMes = 1;
       nAno += 1;
      } else nMes += 1;
     }
    } else {
     for (var i = 0; i < Math.abs(nSum); i++){
      if (nMes == 1){
       nMes = 12;
       nAno -= 1;
      } else nMes -= 1;
     }
    }
    return armaFecha(nDia, nMes, nAno);
   }

   function esBisiesto(nAno){
    var bRes = true;
    res = bRes && (nAno % 4 == 0);
    res = bRes && (nAno % 100 != 0);
    res = bRes || (nAno % 400 == 0);
    return bRes;
   }

   function finMes(nMes, nAno){
    var nRes = 0;
    switch (nMes){
     case 1: nRes = 31; break;
     case 2: nRes = 28; break;
     case 3: nRes = 31; break;
     case 4: nRes = 30; break;
     case 5: nRes = 31; break;
     case 6: nRes = 30; break;
     case 7: nRes = 31; break;
     case 8: nRes = 31; break;
     case 9: nRes = 30; break;
     case 10: nRes = 31; break;
     case 11: nRes = 30; break;
     case 12: nRes = 31; break;
    }
    return nRes + (((nMes == 2) && esBisiesto(nAno))? 1: 0);
   }

   function diasDelAno(nAno){
    var nRes = 365;
    if (esBisiesto(nAno)) nRes++;
    return nRes;
   }

   function anosEntre(nDi0, nMe0, nAn0, nDi1, nMe1, nAn1)
   {
	    var nRes = Math.max(0, nAn1 - nAn0 - 1);
	    if (nAn1 != nAn0)
	     if ((nMe1 > nMe0) || ((nMe1 == nMe0) && (nDi1 >= nDi0)))
	      nRes++;
	    return nRes;
   }

   function mesesEntre(nDi0, nMe0, nAn0, nDi1, nMe1, nAn1)
   {
	    var nRes;
	    if ((nMe1 < nMe0) || ((nMe1 == nMe0) && (nDi1 < nDi0))) nMe1 += 12;
	    nRes = Math.max(0, nMe1 - nMe0 - 1);
	    if ((nDi1 > nDi0) && (nMe1 != nMe0)) nRes++;
	    return nRes;
   }

   function diasEntre(nDi0, nMe0, nAn0, nDi1, nMe1, nAn1)
   {
	    var nRes;
	    if (nDi1 < nDi0) nDi1 += finMes(nMe0, nAn0);
	    nRes = Math.max(0, nDi1 - nDi0);
	    return nRes;
   }

   function mayorOIgual(nDi0, nMe0, nAn0, nDi1, nMe1, nAn1)
   {
	    var bRes = false;
	    bRes = bRes || (nAn1 > nAn0);
	    bRes = bRes || ((nAn1 == nAn0) && (nMe1 > nMe0));
	    bRes = bRes || ((nAn1 == nAn0) && (nMe1 == nMe0) && (nDi1 >= nDi0));
	    return bRes;
   }

   function calcula(fechaIni,fechaFin)
   {
	    var tipRpt=document.getElementById("reporteVO.idrepor");
	    var sFc0 = document.getElementById(fechaIni).value; // Se asume válida
	    var sFc1 = document.getElementById(fechaFin).value; // Se asume válida
	    if (tipRpt.options[tipRpt.selectedIndex].value=="-"){alert("Seleccione un tipo de Reporte");return;}
	    if (parseInt(sFc0.length)<=0){alert("Ingrese la fecha Inicio");return;}  
	    if (parseInt(sFc1.length)<=0){alert("Ingrese la fecha Fin");return;}	 	    
	    var nDi0 = parseInt(sFc0.substr(0, 2), 10);
	    var nMe0 = parseInt(sFc0.substr(3, 2), 10);
	    var nAn0 = parseInt(sFc0.substr(6, 4), 10);
	    var nDi1 = parseInt(sFc1.substr(0, 2), 10);
	    var nMe1 = parseInt(sFc1.substr(3, 2), 10);
	    var nAn1 = parseInt(sFc1.substr(6, 4), 10);
	    if (mayorOIgual(nDi0, nMe0, nAn0, nDi1, nMe1, nAn1))
		{
		     var nAno = anosEntre(nDi0, nMe0, nAn0, nDi1, nMe1, nAn1);
		     var nMes = mesesEntre(nDi0, nMe0, nAn0, nDi1, nMe1, nAn1);
		     var nDia = diasEntre(nDi0, nMe0, nAn0, nDi1, nMe1, nAn1);
		     var nTtM = nAno * 12 + nMes;
		     var nTtD = nDia;
		     for (var i = nAn0; i < nAn0 + nAno; i++) nTtD += diasDelAno(nAno);
		     for (var j = nMe0; j < nMe0 + nMes; j++) nTtD += finMes(j, nAn1);
		     var nTSS = Math.floor(nTtD / 7);
		     var nTSD = nTtD % 7;
		     // OBTIENE LOS AÑOS
		     // ALERT(String(nAno) + " años, " + String(nMes) + " meses, " + String(nDia) + " días");
		     // OBTIENE LOS MESES
		     // ALERT(String(nTtM) + " meses, " + String(nDia) + " días");
		     // OBTIENE LOS DIAS
		     	totDias=String(nTtD); 	
		     	if (parseInt(totDias)<=31){
		     		consultar();
			     	}else{
				     alert('Error:\r\nLa fecha fin no debe ser superior de 15 dias \r\nA la fecha de Inicio ');
				     }
		     // OBTIENE LAS SEMANAS
		     // ALERT(String(nTSS) + " semanas, " + String(nTSD) + " días");
	    } else alert("Error en rango de Fechas...");
   }
  </script>
