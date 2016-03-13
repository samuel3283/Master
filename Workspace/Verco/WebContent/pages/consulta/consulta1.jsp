<%@ include file="/includes/taglibs.jsp"%>
<!-- <meta http-equiv=refresh content=30;URL=<%=request.getContextPath()%>/consultaAction.do?method=consultar1_ref>
 -->
 <link href="../calendario/calendar-win2k-cold-1.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../calendario/calendar.js"></script>
<script type="text/javascript" src="../calendario/calendar-setup.js"></script>
<script type="text/javascript" src="../calendario/lang/calendar-en.js"></script>
<style type="text/css">@import url("../calendario/calendar-win2k-cold-1.css");
</style>
<body>
  
  
  <table width="660" border="0" cellpadding="0" cellspacing="0">
  
  <tr align="right">
        <td> 
         <% String rFecha = (String)session.getAttribute("rFecha") ; %>
           * Consulta por :  <%= rFecha %>
          <a href="../consulta/consulta1XLS.jsp" target="_BLANK">  <IMG SRC="../images/excel.jpg"  BORDER=0 ALT="Generar Rpte Excel"> </a> 
          <a href="javascript:window.print()"> Imprimir </a>
         </td> 
        </tr>
  
  <tr> <td>
        
        <div id="div_listaConsulta1"
				style="display: ${not empty sessionScope.listaConsulta1 ? 'block' : 'none'}">
			<table width="660" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td> <font color="#336699"> :::. Consulta Mensajes .::: </font> </td>
				</tr>
			</table>
			   <display:table name="sessionScope.listaConsulta1" 
			               id="consulta1" 
			               list="true" 
			               class="tablaBordeGrisNormal"
				           requestURI="/consultaAction.do?method=consultar"
				           style="width:660;border:1px; padding:0px;">
								
				<display:column title="Origen"
				                property="operador_origen"
					            style="width:50px;VERTICAL-ALIGN:top;TEXT-ALIGN:LEFT"
					            class="filaTabla" 
					            headerClass="newsTituloCabecera" />
				
				<display:column title="Destino"
				                property="operador_destino"
					            style="width:50px;VERTICAL-ALIGN:top;TEXT-ALIGN:LEFT"
					            class="filaTabla" 
					            headerClass="newsTituloCabecera" />
				
				<display:column title="F.Inicio"
				                property="fecha_inicio"
					            style="width:125px;VERTICAL-ALIGN:top;TEXT-ALIGN:CENTER"
					            class="filaTabla" 
					            headerClass="newsTituloCabecera" />
				
				<display:column title="F.Fin"
				 				property="fecha_fin" 
				                style="width:125px;VERTICAL-ALIGN:top;TEXT-ALIGN:CENTER"
					            class="filaTabla" 
					            headerClass="newsTituloCabecera" />
				
				<display:column title="Mensaje"
				                property="tipo_mensaje" 
				                style="width:20px;TEXT-ALIGN:LEFT" 
								class="filaTabla" 
					            headerClass="newsTituloCabecera"/>
					            
			    <display:column title="Total"
			                    property="total" 
			                    style="width:20px;VERTICAL-ALIGN:top;TEXT-ALIGN:RIGHT"
								class="filaTabla" 
					            headerClass="newsTituloCabecera"/>		            
	
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
 