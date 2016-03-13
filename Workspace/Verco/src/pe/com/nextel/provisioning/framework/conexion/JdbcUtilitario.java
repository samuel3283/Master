package pe.com.nextel.provisioning.framework.conexion;


import java.sql.Connection;
import java.sql.DriverManager;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

/**
 * @author Snavarro
 */
public class JdbcUtilitario implements Parametros {

	private static Connection cn = null;
    private static JdbcUtilitario singleton = null;
    private static Logger logger = Logger.getLogger(JdbcUtilitario.class.getName()); 
    private static String environmentContext= "java:comp/env/";
    
    public static JdbcUtilitario getInstance()  
    {/*
        if (singleton == null)
        { 
            singleton = new JdbcUtilitario();
        }*/
      singleton = new JdbcUtilitario();
        return singleton;   
    }
    
    public Connection getConnection()
    throws Exception
{
    
    String modo = MODO_CONECCION;
    
    if(modo.equals("1"))  {
    	cn = getConnectionDS();
    } else  {
        cn = getConnectionJDBC();
    }
    return cn;
	}
    
    public Connection getConnection(String dataSourceName) throws Exception
    {

            String jndiNameResource = environmentContext.concat(dataSourceName);
            Context ctx = new InitialContext();
             if (ctx!=null)
             {
                try
                {
                    cn = ((DataSource)ctx.lookup(jndiNameResource)).getConnection();
                    
                    if (cn!=null) 
                            logger.debug("Se obtiene conexion del DataSource::"+dataSourceName);
                    else  
                            logger.error("No es posible obtener conexion alguna del recurso::"+dataSourceName);
                  
                }
                catch(Exception e)
                {
                    logger.error("No es posible obtener conexion alguna del recurso::"+jndiNameResource);
                    logger.error(e.getMessage());
                    throw new  Exception(e); 
                }                
            }              
        return cn;
    }
    
    
    public Connection getConnectionDS()
    throws Exception
	{
	    Context ctx = new InitialContext();
	    String dataSource = DATASOURCE;
	    DataSource ds = (DataSource)ctx.lookup(dataSource);
	    cn = ds.getConnection();
	    return cn;
	}
    
    public Connection getConnectionJDBC() throws Exception
    {
      try {
           //Class.forName(DRIVER_DB);
          //String url = "jdbc:oracle:thin:@" + SERVER + ":" + PUERTO + ":" + SID;

    	  Class.forName("com.mysql.jdbc.Driver");
          String url="jdbc:mysql://"+SERVER+":"+PUERTO+"/"+SID;
            cn =  DriverManager.getConnection(url, USUARIO_DB, PASSWORD_DB);
            //Connection conn =  DriverManager.getConnection(URL_JDBC, USUARIO_DB, PASSWORD_DB);
      } catch (Exception e) {
        logger.error("No es posible obtener conexion alguna del recurso::");
        logger.error(e.getMessage());
        throw new  Exception(e); 
      } finally {
        
      }
            return cn;
    }  

    
}
