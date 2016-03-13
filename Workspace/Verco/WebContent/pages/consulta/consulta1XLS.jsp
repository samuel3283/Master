<% 
 response.setContentType("application/vnd.ms-excel") ; 
 response.setHeader("Content-disposition","attachmen t;filename=ConsultaPortabilidad.xls"); 
 response.setHeader( "Pragma", "no-cache" );
%>
<%@ page import="java.util.List" %>
<%@ page import="pe.com.nextel.provisioning.model.vo.Consulta1VO" %>
<% 
     List list1 = (List)session.getAttribute("listaConsulta1") ;
    
%>
<html>
<body>
<table border="1">
 <% String fecha = (String)session.getAttribute("rFecha") ;  %>
   <tr bgcolor="#cccccc"> <td colspan="6" align="center">  Consulta por :<%=fecha%> </td> </tr>
   <tr bgcolor="#cccccc"> <td colspan="6" align="center">  Consulta Parciales  </td> </tr>
   <tr bgcolor="#cccccc"> <td>  Operador Origen </td> <td>  Operador Destino </td> <td>  Fecha Inicio  </td> <td>  Fecha Fin </td> <td>  Tipo Mensaje </td><td>  Total </td></tr>
   <% for (int ii = 0 ; ii < list1.size() ; ii++ ) { 
         Consulta1VO bean1 = (Consulta1VO) list1.get(ii);
   %>
   <tr> <td>  <%= bean1.getOperador_origen() %> </td> <td>  <%= bean1.getOperador_destino() %> </td> <td>  <%= bean1.getFecha_inicio() %> </td> <td>  <%= bean1.getFecha_fin() %> </td> <td>  <%= bean1.getTipo_mensaje() %> </td> <td>  <%= bean1.getTotal() %> </td></tr>
   <% } %>
   
  </table>
</body>
</html>