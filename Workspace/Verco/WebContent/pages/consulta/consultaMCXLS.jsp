<% 
 response.setContentType("application/vnd.ms-excel") ; 
 response.setHeader("Content-disposition","attachmen t;filename=ConsultaPortabilidad.xls"); 
 response.setHeader( "Pragma", "no-cache" );
%>
<%@ page import="java.util.List" %>
<%@ page import="pe.com.nextel.provisioning.model.vo.PortabilidadVO" %>
<% 
     List list1 = (List)session.getAttribute("listaConsultam") ;
     List list2 = (List)session.getAttribute("listaConsultam1") ;
%>
<html>
<body>
<table border="1">
 <% String fecha = (String)session.getAttribute("fechaProceso") ;  %>
   <tr bgcolor="#cccccc"> <td colspan="3" align="center">  INFORME PORTABILIDAD <%=fecha%> </td> </tr>
   <tr bgcolor="#cccccc"> <td>  OPERADOR </td> <td>  MENSAJES DE SOLICITUD DE PORTABILIDAD REGISTRADOS EN EL ABDCP </td> <td>  LÍNEAS DE TELÉFONO REGISTRADAS EN EL ABDCP </td> </tr>
   <% for (int ii = 0 ; ii < list1.size() ; ii++ ) { 
         PortabilidadVO bean1 = (PortabilidadVO) list1.get(ii);
   %>
   <tr> <td>  <%= bean1.getDescripcion() %> </td> <td>  <%= bean1.getTotalMensajes() %> </td> <td>  <%= bean1.getTotalLineas() %> </td> </tr>
   <% } %>
   <tr> <td colspan="3"> &nbsp; </td> </tr>
   <tr> <td colspan="3"> &nbsp; </td> </tr>
   <tr> <td colspan="3"> &nbsp; </td> </tr>
   <tr bgcolor="#cccccc"> <td colspan="3" align="center">  INFORME PORTABILIDAD ACUMULATIVO </td> </tr>
   <tr bgcolor="#cccccc"> <td>  OPERADOR </td> <td>  MENSAJES DE SOLICITUD DE PORTABILIDAD REGISTRADOS EN EL ABDCP </td> <td>  LÍNEAS DE TELÉFONO REGISTRADAS EN EL ABDCP </td> </tr>
   <% for (int ii = 0 ; ii < list2.size() ; ii++ ) { 
         PortabilidadVO bean1 = (PortabilidadVO) list2.get(ii);
         %>
   <tr> <td>  <%= bean1.getDescripcion() %> </td> <td>  <%= bean1.getTotalMensajes() %> </td> <td>  <%= bean1.getTotalLineas() %> </td> </tr>
   <% } %>
  </table>
</body>
</html>