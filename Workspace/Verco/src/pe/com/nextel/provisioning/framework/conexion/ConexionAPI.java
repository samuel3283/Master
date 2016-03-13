package pe.com.nextel.provisioning.framework.conexion;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class ConexionAPI implements Parametros {

	private static Connection cn = null;
    private static ConexionAPI singleton = null;
    private static Logger logger = Logger.getLogger(ConexionAPI.class.getName()); 
    private static String environmentContext= "java:comp/env/";
    
    public static ConexionAPI getInstance()  
    {
        if (singleton == null)
        { 
            singleton = new ConexionAPI();
        }
        return singleton;   
    }
    
    public Connection getConnection()
    throws Exception
{
    String modo = MODO_CONECCION_API;

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
	    String dataSource = DATASOURCE_API;
	    DataSource ds = (DataSource)ctx.lookup(dataSource);
	    cn = ds.getConnection();
	    return cn;
	}
    
    public Connection getConnectionJDBC() throws Exception
    {
            Class.forName(DRIVER_API);
            String url = "jdbc:oracle:thin:@" + SERVER_API + ":" + PUERTO_API + ":" + SID_API;
            cn =  DriverManager.getConnection(url, USUARIO_API, PASSWORD_API);
            return cn;
    }  

    
}
