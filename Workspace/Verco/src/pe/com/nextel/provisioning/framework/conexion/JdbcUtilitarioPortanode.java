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
public class JdbcUtilitarioPortanode implements Parametros {

	private static Connection cn = null;
    private static JdbcUtilitarioPortanode singleton = null;
    private static Logger logger = Logger.getLogger(JdbcUtilitarioPortanode.class.getName()); 
    private static String environmentContext= "java:comp/env/";
    
    public static JdbcUtilitarioPortanode getInstance() 
    {/*
        if (singleton == null)
        { 
            singleton = new JdbcUtilitario();
        }*/
      singleton = new JdbcUtilitarioPortanode();
        return singleton;   
    }
    
    public Connection getConnection()
    throws Exception
{
    
    String modo = "0";//MODO_CONECCION;
    
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
            Class.forName(DRIVER_DB);
            String url = "jdbc:oracle:thin:@" + "172.23.1.201" + ":" + "1521" + ":" + "PNPORTAB";
            cn =  DriverManager.getConnection(url, "GATEWAY", "t3mp0r4l");
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
