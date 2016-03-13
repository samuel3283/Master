<% 
 response.setContentType("application/vnd.ms-excel") ; 
 response.setHeader("Content-disposition","attachmen t;filename=ConsultaPortados.xls"); 
 response.setHeader( "Pragma", "no-cache" );
%>
<%@ taglib uri="WEB-INF/struts-html" prefix="html"%>
<%@ taglib uri="WEB-INF/struts-logic" prefix="logic"%>
<%@ taglib uri="WEB-INF/struts-bean" prefix="bean"%>
<%@ taglib uri="WEB-INF/displaytag" prefix="display"%>
<%@ page import="pe.com.nextel.provisioning.controller.form.ConsultaForm"%>
<%@ page import="pe.com.nextel.provisioning.model.vo.Consulta1VO"%>

    <html:form action="/consultaAction.do" styleId="idConsultaForm">
	 <table width="100%" border="1">
	 <tr>
     <td colspan="9" align="center">&nbsp;
     Numeros Portados del <bean:write name="fechaInicio"/> al <bean:write name="fechaFin"/>
     </td>
     </tr>
     <tr>
     <td bgcolor="#336699" align="center">Nro</td>
     <td bgcolor="#336699" align="center">Numero</td>
     <td bgcolor="#336699" align="center">Cedente</td>
     <td bgcolor="#336699" align="center">Receptor</td>
     <td bgcolor="#336699" align="center">Cedente Inicial</td>
     <td bgcolor="#336699" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Id Proceso&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
     <td bgcolor="#336699" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Nro Secuencial&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
     <td bgcolor="#336699" align="center">Inicio Proceso</td>
     <td bgcolor="#336699" align="center">Inicio Ventana</td>
     <td bgcolor="#336699" align="center">Tipo Portabilidad</td>
	</tr>
	 <% int contador=0;%>
	 <logic:iterate name="listaMensajes" 
     id="lista" type="pe.com.nextel.provisioning.model.vo.Consulta1VO">

	<bean:define id="numeroSecuencial">
	<bean:write name="lista" property="numeroSecuencial"/>
	</bean:define>
	 <% contador++;%>
     <tr>
    <td align="left">&nbsp;<%=contador%></td>
     <td align="left"><bean:write name="lista" property="telefono"/></td>
     <td align="left"><bean:write name="lista" property="cedente"/></td>
     <td align="left"><bean:write name="lista" property="receptor"/></td>
     <td align="left"><bean:write name="lista" property="cedenteInicial"/></td>
     <td align="left">&nbsp;<bean:write name="lista" property="identificadorProceso"/></td>
     <td align="left">&nbsp;<%=Long.parseLong(numeroSecuencial)%></td>
     
     <td align="left"><bean:write name="lista" property="fechaProceso"/></td>
     <td align="left"><bean:write name="lista" property="fechaVentana"/></td>
     <td align="left"><bean:write name="lista" property="tipoPortabilidad"/></td>
	 </tr>
     </logic:iterate>

	</table>

    </html:form>
