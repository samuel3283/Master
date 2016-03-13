<%@ include file="/includes/taglibs.jsp"%>
<link href="../calendario/calendar-win2k-cold-1.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../calendario/calendar.js"></script>
<script type="text/javascript" src="../calendario/calendar-setup.js"></script>
<script type="text/javascript" src="../calendario/lang/calendar-en.js"></script>
<style type="text/css">@import url("../calendario/calendar-win2k-cold-1.css");
</style>
<body>
  
  <table width="660" border="0" cellpadding="0" cellspacing="0">
  
  <tr> <td>
        
        <div id="div_listaConsulta2"
				style="display: ${not empty sessionScope.listaConsulta2 ? 'block' : 'none'}">
			<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td> <font color="#336699"> :::. Consulta General .::: </font> </td>
				</tr>
			</table>
			<display:table name="sessionScope.listaConsulta2" 
			               id="consulta2" 
			               list="true" 
			               class="tablaBordeGrisNormal"
				           requestURI="/consultaAction.do?method=consultar"
				           style="width:660;border:1px; padding:0px;">
								
				<display:column title="Código"
				                property="codigo"
					            style="width:40px;VERTICAL-ALIGN:top;TEXT-ALIGN:LEFT"
					            class="filaTabla" 
					            headerClass="newsTituloCabecera" />
				
				<display:column title="Operador"
				                property="operador"
					            style="width:200px;VERTICAL-ALIGN:top;TEXT-ALIGN:LEFT"
					            class="filaTabla" 
					            headerClass="newsTituloCabecera" />
				
				<display:column title="SP"
				                property="CANT_DE_SP"
					            style="width:30px;VERTICAL-ALIGN:top;TEXT-ALIGN:right"
					            class="filaTabla" 
					            headerClass="newsTituloCabecera" />
				
				<display:column title="ANS"
				 				property="CANT_DE_ANS" 
				                style="width:30px;VERTICAL-ALIGN:top;TEXT-ALIGN:right"
					            class="filaTabla" 
					            headerClass="newsTituloCabecera" />
				
				<display:column title="VABDCP"
				                property="CANT_DE_VABDCP" 
				                style="width:30px;VERTICAL-ALIGN:top;TEXT-ALIGN:right" 
								class="filaTabla" 
					            headerClass="newsTituloCabecera"/>
					            
			    <display:column title="RABDCP"
			                    property="CANT_DE_RABDCP" 
			                    style="width:30px;VERTICAL-ALIGN:top;TEXT-ALIGN:right" 
								class="filaTabla" 
					            headerClass="newsTituloCabecera"/>	
					            
		        <display:column title="SVP"
			                    property="CANT_DE_SVP" 
			                    style="width:30px;VERTICAL-ALIGN:top;TEXT-ALIGN:right"
								class="filaTabla" 
					            headerClass="newsTituloCabecera"/>			            	            
	
				<display:setProperty name="paging.banner.group_size" value="2" />
				<display:setProperty name="paging.banner.placement" value="bottom" />
				<display:setProperty name="paging.banner.page.separator" value=".." />
				<display:setProperty name="paging.banner.some_items_found" value="" />
	
		  </display:table>
		</div><br>
		Rechazos por Solicitud <bean:write name="rechazosTotal"/> <br>
		Rechazos Subsanables <bean:write name="rechazoSubsanable"/> <br>
		Rechazos No Subsanables <bean:write name="rechazoNoSubsanable"/>
       </td>
      </tr>
   </table>
 
</body>
