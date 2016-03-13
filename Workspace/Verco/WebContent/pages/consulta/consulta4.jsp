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
        
        <div id="div_listaPortado"
				style="display: ${not empty sessionScope.listaPortado ? 'block' : 'none'}">
			<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td> <font color="#336699"> :::. Consulta Portados .::: </font> </td>
				</tr>
			</table>
			<display:table name="sessionScope.listaPortado" 
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
				
				<display:column title="Portados"
				                property="portados"
					            style="width:30px;VERTICAL-ALIGN:top;TEXT-ALIGN:right"
					            class="filaTabla" 
					            headerClass="newsTituloCabecera" />
				
				<display:setProperty name="paging.banner.group_size" value="2" />
				<display:setProperty name="paging.banner.placement" value="bottom" />
				<display:setProperty name="paging.banner.page.separator" value=".." />
				<display:setProperty name="paging.banner.some_items_found" value="" />
	
		  </display:table>
		</div>
		<br>
		<bean:write name="listaPortadosAnterior"/>
       </td>
      </tr>
   </table>
 
</body>

