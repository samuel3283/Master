<%@ taglib uri="WEB-INF/struts-html" prefix="html"%>
<%@ taglib uri="WEB-INF/struts-logic" prefix="logic"%>
<%@ taglib uri="WEB-INF/struts-bean" prefix="bean"%>
<%@ taglib uri="WEB-INF/displaytag" prefix="display"%>
<% 
 response.setContentType("application/vnd.ms-excel") ; 
 response.setHeader("Content-disposition","attachmen t;filename=ConsultaPortabilidad.xls"); 
 response.setHeader( "Pragma", "no-cache" );
%>
<% 	
	 String nombreMes = (String)session.getAttribute("nombreMes") ;	
	 String consolInfo = (String)session.getAttribute("consolInfo") ;
%>
<html>
<body>
<table border="1">
   <tr bgcolor="#cccccc"> <td colspan="7" align="center">  <%=nombreMes%> </td> </tr>
   <tr > <td colspan="7" align="center">  <%=consolInfo%> </td> </tr>   
  </table> 			
<table  border="1">
<thead>
<tr style="width:660;border:1px; padding:0px;">
<th bgcolor="#cccccc" style="border:1">OPERADOR</th>
<th bgcolor="#cccccc" style="border:1">PORTADOS</th>
<th bgcolor="#cccccc" style="border:1">RETORNOS</th>
<th bgcolor="#cccccc" style="border:1">OBJECIONES</th>
<th bgcolor="#cccccc" style="border:1">MONTO $</th>
</tr></thead>
<tbody>
<logic:iterate name="consultaForm" property="prefacturacionVO.listaPreFacturacion" id="lista" type="pe.com.nextel.provisioning.model.vo.PreFacturacionVO">
<tr class="odd">
<td style="width:40px;VERTICAL-ALIGN:top;TEXT-ALIGN:RIGHT" class="filaTabla">
 <bean:write name="lista" property="operador"/>
</td>
<td style="width:40px;VERTICAL-ALIGN:top;TEXT-ALIGN:RIGHT" class="filaTabla">
 <bean:write name="lista" property="portados"/>
</td>
<td style="width:40px;VERTICAL-ALIGN:top;TEXT-ALIGN:RIGHT" class="filaTabla">
<bean:write name="lista" property="retornos"/>
</td>
<td style="width:40px;VERTICAL-ALIGN:top;TEXT-ALIGN:RIGHT" class="filaTabla">
<bean:write name="lista" property="objeciones"/>
</td>
<td style="width:40px;VERTICAL-ALIGN:top;TEXT-ALIGN:RIGHT" class="filaTabla">
<bean:write name="lista" property="monto"/>
</td>
</tr></tbody>
</logic:iterate>
</table>
</body>
</html>