<%@ include file="/includes/taglibs.jsp"%>
<%@ page import="pe.com.nextel.provisioning.controller.form.ConsultaForm"%>
<%@ page import="pe.com.nextel.provisioning.model.vo.Consulta1VO"%>
<%@ page import="pe.com.nextel.provisioning.model.vo.AlarmaVO"%>
<%@ page import="pe.com.nextel.provisioning.model.vo.EstadoProcesoVO"%>
<%@ page import="java.util.List"%>
<link href="../calendario/calendar-win2k-cold-1.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../calendario/calendar.js"></script>
<script type="text/javascript" src="../calendario/calendar-setup.js"></script>
<script type="text/javascript" src="../calendario/lang/calendar-en.js"></script>
<style type="text/css">
@import url("../calendario/calendar-win2k-cold-1.css");
</style>
<%
String bandera  = (request.getAttribute("bandera")==null)?"0":request.getAttribute("bandera").toString();
List listaEstados = (List)session.getAttribute("listaEstados") ;

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
   	document.forms['idConsultaForm'].method.value = 'buscarSolicitudes';
    document.forms['idConsultaForm'].submit();
}
function limpiar(){
   	document.forms['idConsultaForm'].method.value = 'initConsultaSolicitudes';
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
    <html:hidden property="consulta1VO.tipoReporte" value="S" styleId="tipBus"/>	
    
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
					<input type="radio" name="busqueda" checked onclick="document.getElementById('tipBus').value='S';" ></>Por Numero Secuencial:
					</font>
					</td>
				</tr>
				<tr>				
					<td colspan="4">
					<table>
					<tr>
					<td>
					Numero Secuencial:
					</td>
					<td>
					<html:text
						styleId="secuencial" property="consulta1VO.numeroSecuencial"
						size="22" maxlength="17" styleClass="formInputBoxText"
						onkeyup="document.getElementById('tipBus').value='S';" 
						/> 	
					</td>
					</tr>
					<tr>
					<td>
					Identificador de Proceso:
					</td>
					<td>
					<html:text
						styleId="proceso" property="consulta1VO.identificadorProceso"
						size="22" maxlength="17" styleClass="formInputBoxText"
						/> 	
					</td>
					</tr>
					</table>			
					</td>				
					
									
				</tr>
				<tr>
					<td colspan="6" >
					<font color="">
					<input type="radio" name="busqueda" onclick="document.getElementById('tipBus').value='P';"></>Por Identificador de Proceso:
					</font>
					</td>
				</tr>

				<tr>
				<td>
				<b>Identificador de Proceso:</b>
				
					<html:text
						styleId="proceso" property="consulta1VO.busqueda"
						size="22" maxlength="17" styleClass="formInputBoxText"
						/> 	
				</td>
				</tr>


				<!--  
				<tr>
					<td colspan="6" >
					<font color="">
					<input type="radio" name="busqueda" onclick="document.getElementById('tipBus').value='O';"></>Por Otros Criterios:
					</font>
					</td>
				</tr>


				<tr>
				<td>
				<b>Trk:</b>
				
					<html:text
						styleId="proceso" property="consulta1VO.trk"
						size="22" maxlength="8" styleClass="formInputBoxText"
						/> 	
				</td>
				</tr>
				-->
				
				

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
     
     
     <%
     String detalles="<table width='100%' border='1'>";
	 detalles +="<tr>";
	 detalles +="<td>TRK</td>";
	 detalles +="<td>Mensaje</td>";
	 detalles +="<td>Detalles</td>";
	 detalles +="</tr>";
	 
	 System.out.println("ALARMAS:: "+listaEstados.size());
	 String detalle="";

	 detalle ="<table width='100%' border='1'>";
	 detalle +="<tr bgcolor='#D5EBF9'> ";
	 //detalle +="<td>VALOR</td>";
	 detalle +="<td>CÓDIGO</td>";
	 detalle +="<td>DESCRIPCIÓN</td>";
	 detalle +="</tr> ";
	 String detalle_rechazo=detalle;
	 String detalle_integridad=detalle;
	 String detalle_estados=detalle;
	 
	 for(int i=0;i<listaEstados.size();i++)
	 {
		 EstadoProcesoVO estadoProcesoVO = new EstadoProcesoVO();
		 estadoProcesoVO = (EstadoProcesoVO) listaEstados.get(i);
		 
		 System.out.println("ALARMA:: "+estadoProcesoVO.getCodigo());
		 if("CAUSA RECHAZO".equals(estadoProcesoVO.getProceso())) {
			 detalle_rechazo +="<tr> ";
		 //detalle_rechazo +="<td>"+estadoProcesoVO.getProceso()+"</td>";
		 detalle_rechazo +="<td>"+estadoProcesoVO.getCodigo()+"</td>";
		 detalle_rechazo +="<td>"+estadoProcesoVO.getDescripcion()+"</td>";
		 detalle_rechazo +="</tr> ";
		 } else if("CAUSA NO INTEGRIDAD".equals(estadoProcesoVO.getProceso())) {
		 detalle_integridad +="<tr> ";
		 //detalle_integridad +="<td>"+estadoProcesoVO.getProceso()+"</td>";
		 detalle_integridad +="<td>"+estadoProcesoVO.getCodigo()+"</td>";
		 detalle_integridad +="<td>"+estadoProcesoVO.getDescripcion()+"</td>";
		 detalle_integridad +="</tr> ";
		 } else {
		 detalle_estados +="<tr> ";
		 //detalle_estados +="<td>"+estadoProcesoVO.getProceso()+"</td>";
		 detalle_estados +="<td>"+estadoProcesoVO.getCodigo()+"</td>";
		 detalle_estados +="<td>"+estadoProcesoVO.getDescripcion()+"</td>";
		 detalle_estados +="</tr> ";
		 }

	 }
	 
	 detalle = "</table>";	 
	 detalle_rechazo +=detalle;
	 detalle_integridad +=detalle;
	 detalle_estados +=detalle;
	 //String detalle='title="header=[MATRICULADOS] body=['CUERPO']" ';
	 System.out.println("\n\n DETALLE:: "+detalle);
	 %>


     <tr>
     <td>
     <table width="100%" border="1">
     <tr>
     <th align="center" colspan="4">Leyendas</th>
     </tr>
     
     <tr bgcolor="#336699">
     <th align="center" title="header=[RECHAZOS] body=[<%=detalle_rechazo%>]">Rechazos</th>
     <th align="center" title="header=[CAUSA NO INTEGRIDAD] body=[<%=detalle_integridad%>]">Causas No Integridad</th>
     <th align="center" title="header=[ESTADOS] body=[<%=detalle_estados%>]">Estados</th>
     </tr>
     </table>
     </td>
     </tr>

     
     <tr>
     <td>
	 <br>

     <logic:present name="consultaForm" property="listaMensajes">

     <table width="95%" border="1">
     <tr bgcolor="#336699">
     <th align="center">N</th>
     <th align="center">Mensaje</th>
     <th align="center">Trk</th>
     <th align="center">Ori</th>
     <th align="center">Des</th>
     <th align="center">Nro Secuencial</th>
     <th align="center">Id Proceso</th>
     <th align="center">Fec Registro</th>
     <th align="center">Rspta Soap</th>
     <th align="center">Fec Soap</th>
    </tr>
    
	<% int contador=0;%>
     <logic:iterate name="consultaForm" property="listaMensajes" id="lista" type="pe.com.nextel.provisioning.model.vo.SolicitudVO">
      <% contador++;%>
     <tr>
     <td align="left">&nbsp;<%=contador%></td>
     <td align="left" title="header=[Detalles] body=[<bean:write name="lista" property="detalle"/>]">
     <bean:write name="lista" property="tipoMensaje"/>
     
     
 
     <logic:equal name="lista" property="tipoMensaje" value="NI">
     <logic:iterate name="lista" property="listadoMensajes" id="item" type="pe.com.nextel.provisioning.model.vo.TRKVO">
     
     <bean:define id="trk">
	 <bean:write name="item" property="trk" />
	 </bean:define>
     <bean:define id="tipo_mensaje">
	 <bean:write name="item" property="tipo_mensaje" />
	 </bean:define>
     <bean:define id="mensaje">
	 <bean:write name="item" property="mensaje" />
	 </bean:define>
     <% 
	 detalles +="<tr>";
	 detalles +="<td>"+trk+"</td>";
	 detalles +="<td>"+tipo_mensaje+"</td>";
	 detalles +="<td>"+mensaje+"</td>";
	 detalles +="</tr>";
     %>
     </logic:iterate>
     
     <% 
	 detalles +="</table>";     
	 System.out.println("DETALLES:"+detalles);
     %>   
     </logic:equal>


  	<logic:notEqual name="lista" property="tipoMensaje" value="NI">
     <% 
	 detalles +="</table>";     
     %>  	
	</logic:notEqual> 
 
 

     </td>




     
     <td align="left"
     title="header=[Detalles] body=[<%=detalles%>]"
     ><bean:write name="lista" property="trackId"/></td>
     <td align="left"><bean:write name="lista" property="operadorOrigen"/></td>
     <td align="left"><bean:write name="lista" property="operadorDestino"/></td>
     <td align="left"><bean:write name="lista" property="numeroSecuencial"/>&nbsp;</td>
     <td align="left"><bean:write name="lista" property="identificadorProceso"/>&nbsp;</td>
     <td align="center"><bean:write name="lista" property="fechaRegistro"/> </td>
     <td align="left"><bean:write name="lista" property="respuestaSoap"/> </td>
     <td align="center"><bean:write name="lista" property="fechaSoap"/> </td>
     </tr>
	 
     </logic:iterate>
     </table>
     


     <logic:present name="consultaForm" property="listaAlarmas">
     <br></br>
     <table width="95%" border="1">
     <tr>
     <th align="center" colspan="4">Alarmas Asociadas
     </th>
     </tr>
     <tr bgcolor="#336699">
     <th align="center">Process Instance</th>
     <th align="center">Fecha Expiracion</th>
     <th align="center">Alarma</th>
     <th align="center">Desc Alarma</th>
     </tr>
     <logic:iterate name="consultaForm" property="listaAlarmas" 
     id="listado" type="pe.com.nextel.provisioning.model.vo.AlarmaVO">
     <tr>
     <td align="left">&nbsp;<bean:write name="listado" property="processinstance"/></td>
     <td align="left">&nbsp;<bean:write name="listado" property="fechaExpiracion"/></td>
     <td align="left">&nbsp;<bean:write name="listado" property="alarma"/></td>
	 <td align="left">&nbsp;<bean:write name="listado" property="descripcionAlarma"/>
     </td>
     </tr>
     </logic:iterate>
     
     </table>
     </logic:present>
     
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


