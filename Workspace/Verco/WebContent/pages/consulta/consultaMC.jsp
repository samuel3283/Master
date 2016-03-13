<%@ include file="/includes/taglibs.jsp"%>
<link href="../calendario/calendar-win2k-cold-1.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../calendario/calendar.js"></script>
<script type="text/javascript" src="../calendario/calendar-setup.js"></script>
<script type="text/javascript" src="../calendario/lang/calendar-en.js"></script>
<style type="text/css">@import url("../calendario/calendar-win2k-cold-1.css");
</style>
 <body>
 
<table width="660" border="0" cellpadding="0" cellspacing="0">
      <tr align="right"> <td>  <% String rFecha = (String)session.getAttribute("rFecha") ; %>
         Consulta por :  <%= rFecha %> <a href="javascript:window.print()"> Imprimir </a>
          </td> </tr>  
  <tr> <td>
        
        <div id="div_listaConsultam"
				style="display: ${not empty sessionScope.listaConsultam ? 'block' : 'none'}">
			<table border="0" cellpadding="0" cellspacing="0">
				<% String fecha = (String)session.getAttribute("fechaProceso") ;  %>
				<tr align="center">
					<td  color="#336699" > INFORME PORTABILIDAD <%= fecha%>
					 <a href="../consulta/consultaMCXLS.jsp" target="_self" >  <IMG SRC="../images/excel.jpg"  BORDER=0 ALT="Generar Reporte Excel"> </a>
					</td>
				</tr>
			</table>
			
			<display:table name="sessionScope.listaConsultam" 
			               id="consulta2" 
			               list="true" 
			               class="tablaBordeGrisNormal"
				           requestURI="/consultaAction.do?method=consultarMensajes"
				           style="width:100%;border:1px; padding:0px;">
				
				
				<display:column title="OPERADOR"
				                property="descripcion"
					            style="width:130px;VERTICAL-ALIGN:top;TEXT-ALIGN:LEFT"
					            class="filaTabla" 
					            headerClass="newsTituloCabecera" />
				
				<display:column title="MENSAJES DE SOLICITUD DE PORTABILIDAD REGISTRADOS EN EL ABDCP"
				                property="totalMensajes"
					            style="width:180px;VERTICAL-ALIGN:top;TEXT-ALIGN:right"
					            class="filaTabla" 
					            headerClass="newsTituloCabecera" />
				
				<display:column title="LÍNEAS DE TELÉFONO REGISTRADAS EN EL ABDCP"
				                property="totalLineas"
					            style="width:180px;VERTICAL-ALIGN:top;TEXT-ALIGN:right"
					            class="filaTabla" 
					            headerClass="newsTituloCabecera" />
				 
	
				<display:setProperty name="paging.banner.group_size" value="2" />
				<display:setProperty name="paging.banner.placement" value="bottom" />
				<display:setProperty name="paging.banner.page.separator" value=".." />
				<display:setProperty name="paging.banner.some_items_found" value="" />
	
		  </display:table>
		</div>
		<br>
		<div id="div_listaConsultam1"
				style="display: ${not empty sessionScope.listaConsultam1 ? 'block' : 'none'}">
			<table border="0" cellpadding="0" cellspacing="0">
				<tr align="center">
				
					<td  color="#336699" > INFORME PORTABILIDAD ACUMULATIVO 
						
					  </td>
				</tr>
			</table>
			
			<display:table name="sessionScope.listaConsultam1" 
			               id="consulta2" 
			               list="true" 
			               class="tablaBordeGrisNormal"
				           requestURI="/consultaAction.do?method=consultarMensajes"
				           style="width:100%;border:1px; padding:0px;">
						
				<display:column title="OPERADOR"
				                property="descripcion"
					            style="width:130px;VERTICAL-ALIGN:top;TEXT-ALIGN:LEFT"
					            class="filaTabla" 
					            headerClass="newsTituloCabecera" />
				
				<display:column title="MENSAJES DE SOLICITUD DE PORTABILIDAD REGISTRADOS EN EL ABDCP"
				                property="totalMensajes"
					            style="width:180px;VERTICAL-ALIGN:top;TEXT-ALIGN:right"
					            class="filaTabla" 
					            headerClass="newsTituloCabecera" />
				
				<display:column title="LÍNEAS DE TELÉFONO REGISTRADAS EN EL ABDCP"
				                property="totalLineas"
					            style="width:180px;VERTICAL-ALIGN:top;TEXT-ALIGN:right"
					            class="filaTabla" 
					            headerClass="newsTituloCabecera" />
		
				<display:setProperty name="paging.banner.group_size" value="2" />
				<display:setProperty name="paging.banner.placement" value="bottom" />
				<display:setProperty name="paging.banner.page.separator" value=".." />
				<display:setProperty name="paging.banner.some_items_found" value="" />
	
		  </display:table>
		</div>
       </td>
      </tr>
   </table>
</body>
