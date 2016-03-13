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
        
        <div id="div_listaNumeroPortado"
				style="display: ${not empty sessionScope.listaNumeroPortado ? 'block' : 'none'}">
			<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td> <font color="#336699"> :::. Consulta Numeraciones .::: </font> </td>
				</tr>
			</table>
			<display:table name="sessionScope.listaNumeroPortado" 
			               id="consulta2" 
			               list="true" 
			               class="tablaBordeGrisNormal"
				           requestURI="/consultaAction.do?method=consultar"
				           style="width:660;border:1px; padding:0px;">
								
				<display:column title="Operador Origen"
				                property="operador_origen"
					            style="width:40px;VERTICAL-ALIGN:top;TEXT-ALIGN:LEFT"
					            class="filaTabla" 
					            headerClass="newsTituloCabecera" />
				
				<display:column title="Tipo Portabilidad"
				                property="tipo_mensaje"
					            style="width:200px;VERTICAL-ALIGN:top;TEXT-ALIGN:LEFT"
					            class="filaTabla" 
					            headerClass="newsTituloCabecera" />
				
				<display:column title="Total"
				                property="total"
					            style="width:30px;VERTICAL-ALIGN:top;TEXT-ALIGN:right"
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

