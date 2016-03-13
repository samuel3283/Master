
<%@ taglib uri="WEB-INF/struts-logic" prefix="logic"%>
<%@ taglib uri="WEB-INF/struts-bean" prefix="bean"%>

<html:form action="/Login.do" method="post">
  <table style="border: thin outset" width="100%" height="100%" border="1"
       cellspacing="0" cellpadding="0" bordercolor="#666666" bgcolor="#336699"
       align="center">
  <tr>
    <td valign="top">
      <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr>
          <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0"
                   align="center">
              <tr>
                <td height="19">
                  <div align="center">
                    <font color="#00CCFF">
                      <b><br>
                      <font color="#99CCFF">MENU</font></b>
                      <br><br><br>
                      
                    </font>
                    </div>
                </td>
              </tr>    
                     
               <tr>
                <td height="14" onmouseover="bgColor='#A3C1E0'"
                    onmouseout="bgColor=''"
                    onclick="doPost('<%=request.getContextPath()%>/consultaAction.do?method=regcli',0)"
                    style="cursor:hand" bgcolor="#336699">
                  <font color="#FFFFFF">&nbsp;&raquo;&nbsp;Registrar Cliente</font>
                </td>
              </tr>         
                    
              <tr>
                <td height="14" onmouseover="bgColor='#A3C1E0'"
                    onmouseout="bgColor=''"
                    onclick="doPost('<%=request.getContextPath()%>/consultaAction.do?method=conpro',0)"
                    style="cursor:hand" bgcolor="#336699">
                  <font color="#FFFFFF">&nbsp;&raquo;&nbsp;Mostrar Productos</font>
                </td>
              </tr>       
                  
              <tr>
                <td height="14" onmouseover="bgColor='#A3C1E0'"
                    onmouseout="bgColor=''"
                    onclick="doPost('<%=request.getContextPath()%>/consultaAction.do?method=disponibilidad',0)"
                    style="cursor:hand" bgcolor="#336699">
                <!--   <font color="#FFFFFF">&nbsp;&raquo;&nbsp;Disponiblidad de Productos</font>
                 -->
                </td>
              </tr>       


              <tr>
                <td height="14" onmouseover="bgColor='#A3C1E0'"
                    onmouseout="bgColor=''"
                    onclick="doPost('<%=request.getContextPath()%>/consultaAction.do?method=pedidos',0)"
                    style="cursor:hand" bgcolor="#336699">
                  <font color="#FFFFFF">&nbsp;&raquo;&nbsp;Generar Pedido</font>
                </td>
              </tr>       

                <tr>
                <td height="14" onmouseover="bgColor='#A3C1E0'"
                    onmouseout="bgColor=''"
                    onclick="doPost('<%=request.getContextPath()%>/consultaAction.do?method=iniciar',0)"
                    style="cursor:hand" bgcolor="#336699">
                  <!-- 
                  <font color="#FFFFFF">&nbsp;&raquo;&nbsp;Consulta General</font>
                   -->
                </td>
              </tr>                          


             
 
 
 



             	
 
 
 
             <!--   
             <tr>
                <td height="14" onmouseover="bgColor='#A3C1E0'"
                    onmouseout="bgColor=''"
                    onclick="doPost('<%=request.getContextPath()%>/consultaAction.do?method=initMensajePep',0)"
                    style="cursor:hand" bgcolor="#336699">
                  <font color="#FFFFFF">&nbsp;&raquo;&nbsp;Consultar Msje Pep</font>
                </td>
              </tr>                        

 			 	
             <tr>
                <td height="14" onmouseover="bgColor='#A3C1E0'"
                    onmouseout="bgColor=''"
                    onclick="doPost('<%=request.getContextPath()%>/consultaAction.do?method=facturacion',0)"
                    style="cursor:hand" bgcolor="#336699">
                  <font color="#FFFFFF">&nbsp;&raquo;&nbsp;Consulta Facturación</font>
                </td>
              </tr>    
              -->
            <tr>
              <td height="14" bgcolor="#336699">
                	&nbsp;
              </td>
            </tr>     
                                     
            <tr>
                <td height="14" onmouseover="bgColor='#A3C1E0'"
                    onmouseout="bgColor=''"
                    onclick="doPost('<%=request.getContextPath()%>/Login.do?method=inicio',0)"
                    style="cursor:hand" bgcolor="#336699">
                  <font color="#FFFFFF">&nbsp;&raquo;&nbsp;Salir</font>
                </td>
              </tr>       
                                 
              <tr>
                <td>&nbsp;</td>
              </tr>    
                                 
            </table>
          </td>
        </tr>
      </table>
   </td>
  </tr>
</table>
</html:form>
 
 			 

