<%@ taglib uri="WEB-INF/struts-html" prefix="html"%>
<%@ taglib uri="WEB-INF/struts-logic" prefix="logic"%>
<%@ taglib uri="WEB-INF/struts-bean" prefix="bean"%>
<html>
<head>
   <title>  VERCO - Venta de Articulos Deportivos </title>
<meta http-equiv="" content="text/html; charset=iso-8859-1">



<link rel="stylesheet" type="text/css" href="<html:rewrite page='/pages/css/estilos.css'/>">


<script language="javascript">
function login()
{		
		var usuario,password;
		var metodo = 'inicio';
        usuario=document.getElementsByName("usuarioBean.usuario")[0].value;
        password=document.getElementsByName("usuarioBean.password")[0].value;
        if(usuario=="" || password==""){
            document.getElementsByName("usuarioBean.usuario")[0].focus();
            alert('<bean:message key="login.incorrecto" />');
              
        }
        else {
        	//document.forms[0].method.value="login";       	
        	llamarMetodo('login');   
        }    
        /*
        else if(password==""){        
            alert('<bean:message key="login.incorrecto" />');
            return false;
        }
        
        document.forms[0].submit();
		*/
	}


	function validaLogin()
	{
	        if(event.keyCode == 13)
	        {
	        login();
	        }
	}
	
	function inicio()
	{
	document.getElementsByName("usuarioBean.usuario")[0].focus();
	}
	
	function llamarMetodo(metodo)
	{   
	
	doPostMethod('<%=request.getContextPath()%>/Login.do',metodo,0);
	}
	
	function doPostMethod(accion, method, index){
	    document.forms[index].method.value=method;    
	    document.forms[index].action=accion;
	    document.forms[index].submit();
	}
		
</script>
</head>


<body onload="inicio()">
<html:form action="/Login.do">
<input type="hidden" name="method" value=""/>


<table width="800px" height="550px" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="80"  bgcolor="#F9F9F9">
    <div align="center">
         <img src="<%=request.getContextPath()%>/pages/images/cabecera.jpg" width="800" height="80" />
	</div>
    </td>
  </tr>

  <tr>
    <td  height="390">
    <table width="100%" height="40%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td align="center" class="errorLogin" width="410">
        &nbsp;
        <br>
 		<logic:present name="LoginForm" property="strMensaje">
		      <bean:write name="LoginForm" property="strMensaje"/>
		</logic:present>       
        </td>
        <td>&nbsp;</td>
        <td>

		<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="2">&nbsp;</td>
  </tr>
  
  
  <tr>
    <td width="90%" height="171">
	
		<form id="form1" name="form1" method="post" action="">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td colspan="2"><b><font color="#1A4553">
                <bean:message key="login.cabecera" /></font></b></td>
              </tr>
			  <tr>
                <td colspan="2">&nbsp;</td>
              </tr>
              <tr>
                <td><b><font color="#1A4553">
                <bean:message key="login.usuario" />
                :</font></b></td>
                <td><label>
                <html:text property="usuarioBean.usuario" size="15" onkeypress="validaLogin()"/>
                </label></td>
              </tr>
              <tr>
                <td><b><font color="#1A4553">
                <bean:message key="login.password" />:</font></b></td>
                <td><html:password property="usuarioBean.password" size="15" onkeypress="validaLogin()"/></td>
              </tr>
              <tr>
                <td colspan="2">&nbsp;</td>
              </tr>
              <tr>
                <td colspan="2" align="center"><label>
				<a href="javascript:login()">
				<img src="<%=request.getContextPath()%>/pages/images/btnenviar.png" width="74" height="24" border="0"/>
				</a>
				&nbsp;&nbsp;
				<a href="#">
				<img src="<%=request.getContextPath()%>/pages/images/btncancelar.png" width="74" height="24" border="0"/>
				</a>
                </label></td>
              </tr>
			  <tr>
                <td colspan="2">&nbsp;
				</td>
              </tr>
			 </table>
              
              </form>	
	
	</td>
  </tr>

</table>

		</td>
      </tr>
    </table>	</td>
  </tr>
  
  

</table>


</html:form>
</html>