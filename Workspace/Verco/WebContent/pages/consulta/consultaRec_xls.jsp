<%@ page contentType="application/vnd.ms-excel" %>
<%@ taglib uri="WEB-INF/struts-html" prefix="html"%>
<%@ taglib uri="WEB-INF/struts-logic" prefix="logic"%>
<%@ taglib uri="WEB-INF/struts-bean" prefix="bean"%>
<%@ taglib uri="WEB-INF/displaytag" prefix="display"%>
<%@ page import="pe.com.nextel.provisioning.controller.form.ConsultaForm"%>
<%@ page import="pe.com.nextel.provisioning.model.vo.Consulta1VO"%>

<html:html>

<body>

    <html:form action="/consultaAction.do" styleId="idConsultaForm">
    <html:hidden property="method" />
	<html:hidden property="strMensaje" />
    
	 <logic:present name="listaMensajes">
	 <table width="90%" border="1">
	 <tr>
     <td colspan="10" align="center">&nbsp;
     Solicitudes de Retorno del <bean:write name="fechaInicio"/> al <bean:write name="fechaFin"/>
     </td>
     </tr>
     
     <tr>
     <th bgcolor="#336699" align="center">Nro</th>
     <th bgcolor="#336699" align="center">Numero&nbsp;</th>
     <th bgcolor="#336699" align="center">Receptor</th>
     <th bgcolor="#336699" align="center">Cedente</th>
     <th bgcolor="#336699" align="center">Id Proceso &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
     <th bgcolor="#336699" align="center">&nbsp;&nbsp;&nbsp;Fecha Registro&nbsp;&nbsp;&nbsp;</th>
     <th bgcolor="#336699" align="center">Motivo Retorno</th>
     <th bgcolor="#336699" align="center">Respuesta Retorno</th>
	 </tr>
	 <% int contador=0;%>
	 <logic:iterate name="listaMensajes" 
     id="lista" type="pe.com.nextel.provisioning.model.vo.Consulta1VO">
	 <% contador++;%>

     <tr>
     <td align="left">&nbsp;<%=contador%></td>
     <td align="left"><bean:write name="lista" property="telefono"/></td>
     <td align="left"><bean:write name="lista" property="receptor"/></td>
     <td align="left"><bean:write name="lista" property="cedente"/></td>
     <td align="left"><bean:write name="lista" property="identificadorProceso"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
     <td align="left"><bean:write name="lista" property="fechaRegistro"/></td>
     <td align="left"><bean:write name="lista" property="motivoRetorno"/></td>
     <td align="left"><bean:write name="lista" property="respuestaRetorno"/></td>
    </tr>
     </logic:iterate>

	</table>
	</logic:present>


    </html:form>
  </body>
</html:html>


