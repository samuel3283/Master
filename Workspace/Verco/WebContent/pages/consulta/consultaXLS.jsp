<% 
 response.setContentType("application/vnd.ms-excel") ; 
 response.setHeader("Content-disposition","attachmen t;filename=ConsultaPortabilidad.xls"); 
 response.setHeader( "Pragma", "no-cache" );
%>
<%@ page import="java.util.List" %>
<%@ page import="pe.com.nextel.provisioning.model.vo.Consulta2VO" %>
<%@ page import="pe.com.nextel.provisioning.model.vo.RechazoVO" %>
<%@ page import="pe.com.nextel.provisioning.model.vo.PortadosVO" %>
<%@ page import="pe.com.nextel.provisioning.model.vo.NumeroSolicitadoVO" %>


<% 
     List list1 = (List)session.getAttribute("listaConsulta2") ;
     List list2 = (List)session.getAttribute("listaRechazo") ;
     List list3 = (List)session.getAttribute("listaPortado") ;
     List list4 = (List)session.getAttribute("listaNumeroPortado");
     String rechazosTotal = String.valueOf(session.getAttribute("rechazosTotal"));
     String rechazoSubsanable = String.valueOf(session.getAttribute("rechazoSubsanable"));
     String rechazoNoSubsanable = String.valueOf(session.getAttribute("rechazoNoSubsanable"));
     String listaPortadosAnterior = String.valueOf(session.getAttribute("listaPortadosAnterior"));
     
%>
<html>
<body>
<table border="1">
 <% String fecha = (String)session.getAttribute("rFecha") ;  %>
   <tr bgcolor="#cccccc"> <td colspan="7" align="center">  Consulta por :<%=fecha%> </td> </tr>
   <tr bgcolor="#cccccc"> <td colspan="7" align="center">  Consulta General </td> </tr>
   <tr bgcolor="#cccccc"> <td>  Código </td> <td>  Operador </td> <td>  SP </td> <td>  ANS </td> <td>  VABDCP </td> <td>  RABDCP </td> <td>  SVP </td> </tr>
   <% for (int ii = 0 ; ii < list1.size() ; ii++ ) { 
         Consulta2VO bean1 = (Consulta2VO) list1.get(ii);
   %>
   <tr> <td>  <%= bean1.getCodigo() %> </td> <td>  <%= bean1.getOperador() %> </td> <td>  <%= bean1.getCANT_DE_SP() %> </td> <td>  <%= bean1.getCANT_DE_ANS() %> </td> <td>  <%= bean1.getCANT_DE_VABDCP() %> </td> <td>  <%= bean1.getCANT_DE_RABDCP() %> </td><td>  <%= bean1.getCANT_DE_SVP() %> </td></tr>
   <% } %>
   
   <tr> 
   <td colspan="7">
   Rechazos por Solicitud <%= rechazosTotal %> <br>
   Rechazos Subsanables <%= rechazoSubsanable %> <br>
   Rechazos No Subsanables <%= rechazoNoSubsanable %>
   </td>  
   
   <tr>
   <td colspan="7"> &nbsp; </td> </tr>
   
  </table>
 
  <table border="1">
   <tr bgcolor="#cccccc"> <td colspan="3" align="center"> Rechazos </td> </tr>
   <tr bgcolor="#cccccc"> <td>  Rechazo </td> <td>  Descripcion </td> <td>  Cantidad </td> </tr>
   <% for (int ii = 0 ; ii < list2.size() ; ii++ ) { 
         RechazoVO bean1 = (RechazoVO) list2.get(ii);
         %>
   <tr> <td>  <%= bean1.getCodigo() %> </td> <td>  <%= bean1.getDescripcion() %> </td> <td>  <%= bean1.getCantidad() %> </td> </tr>
   <% } %>
   <tr> <td colspan="3"> &nbsp; </td> </tr>
  </table>
  
   <table border="1">
   <tr bgcolor="#cccccc"> <td colspan="3" align="center"> Consulta Portados </td> </tr>
   <tr bgcolor="#cccccc"> <td>  Código </td> <td>  Operador </td> <td>  Portados </td> </tr>
   <% for (int ii = 0 ; ii < list3.size() ; ii++ ) { 
         PortadosVO bean1 = (PortadosVO) list3.get(ii);
         %>
   <tr> <td>  <%= bean1.getCodigo() %> </td> <td>  <%= bean1.getOperador() %> </td> <td>  <%= bean1.getPortados() %> </td> </tr>
   <% } %>
   
   <tr>
   <td colspan="3" align="left">
   <br>
   <%= listaPortadosAnterior %>
   </td>
   </tr>
   <tr> 
   <td colspan="3"> &nbsp; </td> </tr>
  </table>
  
    <table border="1">
   <tr bgcolor="#cccccc"> <td colspan="3" align="center"> Consulta Numeraciones </td> </tr>
   <tr bgcolor="#cccccc"> <td>  Operador Origen </td> <td>  Tipo Mensajes </td> <td>  Total </td> </tr>
   <% for (int ii = 0 ; ii < list4.size() ; ii++ ) { 
         NumeroSolicitadoVO bean1 = (NumeroSolicitadoVO) list4.get(ii);
         %>
   <tr> <td>  <%= bean1.getOperador_origen() %> </td> <td>  <%= bean1.getTipo_mensaje() %> </td> <td>  <%= bean1.getTotal() %> </td> </tr>
   <% } %>
   <tr> <td colspan="3"> &nbsp; </td> </tr>
  </table>
  
</body>
</html>