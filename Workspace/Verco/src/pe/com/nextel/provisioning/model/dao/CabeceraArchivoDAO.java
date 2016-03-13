package pe.com.nextel.provisioning.model.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.OracleTypes;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.com.nextel.provisioning.framework.conexion.JdbcUtilitario;
import pe.com.nextel.provisioning.framework.conexion.ParameterCollection;
import pe.com.nextel.provisioning.framework.conexion.QueryUtil;
import pe.com.nextel.provisioning.framework.exception.DAOException;
import pe.com.nextel.provisioning.model.vo.CabeceraArchivoVO;
import pe.com.nextel.provisioning.model.vo.FiltroVO;
import pe.com.nextel.provisioning.view.ValueConstants;

public class CabeceraArchivoDAO {

	 private static final Log log = LogFactory.getLog(CabeceraArchivoDAO.class);    
		
	 public CabeceraArchivoDAO() 
	 {
	 } 
	 
	 public static String insertar(CabeceraArchivoVO cabeceraArchivo) {
		 
			Object[] objectL = new Object[1];
			String codigo = "";
			Connection con = null;
			
			log.info("[CabeceraArchivoDAO insertar] ==> " +
			"==>Archivo "+ cabeceraArchivo.getArchivo() +
			"==>TipoArchivo "+ cabeceraArchivo.getTipoArchivoBean().getCodigo().toUpperCase() +
			"==>CantRegistros "+ cabeceraArchivo.getCantidadRegistros() +
			"==>ProcesoIni "+ cabeceraArchivo.getProcesoInicio() +
			"==>Estado "+ cabeceraArchivo.getEstadoProcesoBean().getCodigo() +			
			"==>FecIniEjecucion "+ cabeceraArchivo.getFechaInicioEjecucion() +
			"==>FecProceso "+ cabeceraArchivo.getFechaProceso());
   		 	
			ParameterCollection inputCollection = new ParameterCollection();
			inputCollection.addInput(cabeceraArchivo.getArchivo(),OracleTypes.VARCHAR);
			inputCollection.addInput(cabeceraArchivo.getTipoArchivoBean().getCodigo().toUpperCase(),OracleTypes.VARCHAR);
			inputCollection.addInput(cabeceraArchivo.getCantidadRegistros(),OracleTypes.NUMBER);
			inputCollection.addInput((cabeceraArchivo.getProcesoInicio()!=null) ? cabeceraArchivo.getProcesoInicio().trim():"P001",OracleTypes.VARCHAR);
			inputCollection.addInput((cabeceraArchivo.getEstadoProcesoBean().getCodigo()!=null) ? cabeceraArchivo.getEstadoProcesoBean().getCodigo():"P001",OracleTypes.VARCHAR);
			
			ParameterCollection outputCollection = new ParameterCollection();
			outputCollection.addOutput(OracleTypes.VARCHAR);
			
	        try {
				inputCollection.addInput(cabeceraArchivo.getFechaInicioEjecucion(),OracleTypes.VARCHAR);
			inputCollection.addInput(cabeceraArchivo.getFechaProceso(),OracleTypes.VARCHAR);
			inputCollection.addInput(Integer.parseInt(ValueConstants.VALOR_CERO),OracleTypes.NUMBER);
			inputCollection.addInput(Integer.parseInt(ValueConstants.VALOR_CERO),OracleTypes.NUMBER);
        	con = JdbcUtilitario.getInstance().getConnection();
	        	objectL = QueryUtil.executeProcedure(con, ValueConstants.PK_PROVISIONING_RF.concat(".SP_GRABAR_CABECERA"), inputCollection, outputCollection);
	            if (objectL != null) {
	                codigo = String.valueOf(objectL[0]);
	            }
	
	        } catch (DAOException de) { 
	        	codigo = ValueConstants.ERROR_CAUSAS_TECNICAS;
	        	log.error(de);
	            
	        } catch (Exception e) { 
	        	codigo = ValueConstants.ERROR_CAUSAS_TECNICAS;
	        	log.error(e);            
	        }
	         finally
	         {
	             try
	             {
	                 con.close();
	             }catch(SQLException e)
	             {
	                 e.printStackTrace();
	                 log.error(e);
	             }
	         }		        
        
        return codigo;
    }
	 
	 public static String consultarIdCabecera(CabeceraArchivoVO cabecera) throws Exception{		 
			Object[] objectL = new Object[1];
			String codigo = "";
			Connection con = null;
			
			log.info("[CabeceraArchivoDAO consultarIdCabecera] ==> Archivo "+ cabecera.getArchivo());
			
			ParameterCollection inputCollection = new ParameterCollection();
			inputCollection.addInput(cabecera.getArchivo().trim(),OracleTypes.VARCHAR);           		
			
			ParameterCollection outputCollection = new ParameterCollection();
			outputCollection.addOutput(OracleTypes.VARCHAR);
			
	        try {
        		con = JdbcUtilitario.getInstance().getConnection();
        		objectL = QueryUtil.executeProcedure(con, ValueConstants.PK_PROVISIONING_RF.concat(".SP_CONSULTAR_ID_CABECERA"), inputCollection, outputCollection);
	        	
	            if (objectL != null) {
	                codigo = String.valueOf(objectL[0]);
	            }
	
	        } catch (Exception e) { 
	        	log.error(e);
	            throw new DAOException(e.getMessage(), e);
	        }
	         finally
	         {
	             try
	             {
	                 con.close();
	             }catch(SQLException e)
	             {
	                 e.printStackTrace();
	                 log.error(e);
	             }
	         }		        
        
        return codigo;
    }
	 
	 public static String consultarTipoTecnologia(CabeceraArchivoVO cabecera) throws Exception{
		 
			List lista = new ArrayList();
			String codigo = "";
			String tipoArchivo = "";
			String worldNumber = "";
			String proceso = "";
			String numeroSolicitud = "";
			int routingNumber = 0;
			String motivoRetorno = "";
			
			tipoArchivo=cabecera.getTipoArchivoBean().getCodigo();
			if (tipoArchivo.equalsIgnoreCase(ValueConstants.ARCHIVO_FP)){
				proceso=ValueConstants.PROCESO_PORTABILIDAD;
				worldNumber=cabecera.getListaArchivoFPVO().get(0).getWorldNumber();
				numeroSolicitud=cabecera.getListaArchivoFPVO().get(0).getNumeroSolicitud();
				routingNumber=Integer.parseInt(cabecera.getListaArchivoFPVO().get(0).getReceptor());
			}else if (tipoArchivo.equalsIgnoreCase(ValueConstants.ARCHIVO_FR)){
				motivoRetorno=cabecera.getListaArchivoFRVO().get(0).getMotivoRetorno().trim();
				if (motivoRetorno.equals(ValueConstants.CODIGO_RETORNO_FRAUDE)) {	//01
					proceso=ValueConstants.PROCESO_RETORNO_FRAUDE;
				}else if (motivoRetorno.equals(ValueConstants.CODIGO_RETORNO_TERMINACION_CONTRATO)) {	//02
					proceso=ValueConstants.PROCESO_RETORNO_TERMINACION_CONTRATO;
				}
				worldNumber=cabecera.getListaArchivoFRVO().get(0).getNumeroTelefono();
				routingNumber=Integer.parseInt(cabecera.getListaArchivoFRVO().get(0).getCedenteInicial());
			}
			
			log.info("[CabeceraArchivoDAO  consultarTipoTecnologia] ==> TipoArchivo "+ tipoArchivo +
			" ==> WorldNumber "+ worldNumber +
			" ==> MotivoRetorno "+ motivoRetorno +
			" ==> Proceso "+ proceso +
			" ==> NumeroSolicitud "+ numeroSolicitud +
			" ==> RoutingNumber "+ routingNumber);
			
	        try {
	        	codigo = ApiDAO.obtenerTipoTecnologia(worldNumber, proceso, numeroSolicitud, routingNumber);
	        	
	            if (codigo != null) {
	                log.info("[consultarTipoTecnologia]codigo " +codigo);
	            }
	
	        } catch (Exception e) { 
	        	log.error(e);
	            throw new DAOException(e.getMessage(), e);
	        }

        return codigo;
    }
	 
	 public static String consultarWorldNumber(CabeceraArchivoVO cabecera) throws Exception{

			List lista = new ArrayList();
			String codigo = "";
			String tipoArchivo = "";
			String numeroTelefono = "";
			
			tipoArchivo=cabecera.getTipoArchivoBean().getCodigo();
			if (tipoArchivo.equalsIgnoreCase(ValueConstants.ARCHIVO_FP)){
				numeroTelefono=cabecera.getListaArchivoFPVO().get(0).getNumeroTelefono();
			}else if (tipoArchivo.equalsIgnoreCase(ValueConstants.ARCHIVO_FR)){
				numeroTelefono=cabecera.getListaArchivoFRVO().get(0).getNumeroTelefono();
			}
			
			log.info("[consultarWorldNumber] ==> TipoArchivo "+ tipoArchivo+" ==> NumeroTelefono "+ numeroTelefono.trim());
			
	        try {
	        	codigo=ApiDAO.obtenerWorldNumber(numeroTelefono.trim());
	        	
	            if (codigo != null) {
	                log.info("[consultarWorldNumber]codigo " +codigo);
	            }
	
	        } catch (Exception e) { 
	        	log.error(e);
	            throw new DAOException(e.getMessage(), e);
	        }
     
     return codigo;
    }
	 
	 public static String validarCantidadRegistros(CabeceraArchivoVO cabeceraArchivo) throws Exception{
			Object[] objectL = new Object[1];
			String codigo = "";
			Connection con = null;
			
			log.info("[CabeceraArchivoDAO validarCantidadRegistros] ==> Archivo "+ cabeceraArchivo.getArchivo() +
			" ==> TipoArchivo "+ cabeceraArchivo.getTipoArchivoBean().getCodigo().toUpperCase());
			
			ParameterCollection inputCollection = new ParameterCollection();
			inputCollection.addInput(cabeceraArchivo.getArchivo(),OracleTypes.VARCHAR);
			inputCollection.addInput(cabeceraArchivo.getTipoArchivoBean().getCodigo().toUpperCase(),OracleTypes.VARCHAR);
			
			ParameterCollection outputCollection = new ParameterCollection();
			outputCollection.addOutput(OracleTypes.VARCHAR);
			
	        try {
	        	con = JdbcUtilitario.getInstance().getConnection();
	        	objectL = QueryUtil.executeProcedure(con, ValueConstants.PK_PROVISIONING_RF.concat(".SP_VALIDAR_CANTIDAD_ARCHIVOS"), inputCollection, outputCollection);
	            if (objectL != null) {
	                codigo = String.valueOf(objectL[0]);
	            }
	
	        } catch (Exception e) { 
	        	log.error(e);
	            throw new DAOException(e.getMessage(), e);
	        }
	        finally
	        {
	            try
	            {
	                con.close();
	            }catch(SQLException e)
	            {
	                e.printStackTrace();
	                log.error(e);
	            }
	        }	        
        
        return codigo;
    }
	 
	 @SuppressWarnings("unchecked")
	public static List<DynaBean> consultar(FiltroVO filtro) throws Exception
	 {    
		List<DynaBean> lista = null;
		Connection con = null;
		
		ParameterCollection inputCollection = new ParameterCollection();
		inputCollection.addInput(filtro.getCaso(),OracleTypes.VARCHAR);	//caso
		inputCollection.addInput(filtro.getNombre(),OracleTypes.VARCHAR);	//valor
		
        try {
        	con = JdbcUtilitario.getInstance().getConnection();
        	lista = QueryUtil.executeProcedure(con,ValueConstants.PK_PROVISIONING_RF.concat(".SP_CONSULTAR_CABECERA"), inputCollection);

        }catch(Exception e)
		{
		     e.printStackTrace();
		     log.error(e);
		     throw new DAOException(e.getMessage(),e);
		}
        finally
        {
            try
            {
                con.close();
            }catch(SQLException e)
            {
                e.printStackTrace();
                log.error(e);
            }
        }        
		
		return lista;
	 }	 
	 
	 public static List<DynaBean> ObtenerCabeceraArchivo(String tipo, String fechaProceso) throws DAOException {     
	     List<DynaBean> lista = null;
		 Connection con = null;
		 ParameterCollection inputCollection = new ParameterCollection();
         inputCollection.addInput(fechaProceso,OracleTypes.VARCHAR);
         inputCollection.addInput(tipo,OracleTypes.VARCHAR);
         
         try
         {
             con= JdbcUtilitario.getInstance().getConnection();
             lista = QueryUtil.executeProcedure(con,ValueConstants.PK_PROVISIONING_PROCESO.concat(".SP_OBTENER_CABECERA"), inputCollection);     
         }catch(Exception e)
         {
             e.printStackTrace();
             log.error(e);
             throw new DAOException(e.getMessage(),e);
         }
         finally
         {
             try
             {
                 con.close();
             }catch(SQLException e)
             {
                 e.printStackTrace();
                 log.error(e);
             }
         }
         return lista;
	 }
	 
	 
	 public static void activarFlag(CabeceraArchivoVO cabeceraArchivo, String estado, String opcion) {
		 
			Connection con = null;
			log.info(" ActivarFlag Id Cabecera "+ cabeceraArchivo.getIdcabecera()+" estado ="+estado+"  OPCION ="+opcion);
   		    
			ParameterCollection inputCollection = new ParameterCollection();
			inputCollection.addInput(cabeceraArchivo.getIdcabecera(),OracleTypes.NUMBER);
			inputCollection.addInput(estado,OracleTypes.VARCHAR);
			inputCollection.addInput(opcion,OracleTypes.VARCHAR);
			
	        try {
	        	con = JdbcUtilitario.getInstance().getConnection();
	        	QueryUtil.execute(con, ValueConstants.PK_PROVISIONING_PROCESO.concat(".SP_ACTIVAR_FLAG"), inputCollection);
	        } catch (Exception e) { 
	        	log.error(e);
	        }
	         finally
	         {
	             try
	             {
	                 con.close();
	             }catch(SQLException e)
	             {
	                 e.printStackTrace();
	                 log.error(e);
	             }
	         }
    }
	 
	public static List<DynaBean> obtenerFlag(CabeceraArchivoVO cabeceraArchivo, int estado) {
		    List<DynaBean> lista = new ArrayList<DynaBean>();
			Connection con = null;
			log.info(" obtenerFlag Id Cabecera "+ cabeceraArchivo.getIdcabecera());
			ParameterCollection inputCollection = new ParameterCollection();
			inputCollection.addInput(cabeceraArchivo.getIdcabecera(),OracleTypes.NUMBER);
			inputCollection.addInput(estado,OracleTypes.NUMBER);
			
	        try {
	        	con = JdbcUtilitario.getInstance().getConnection();
          	    lista = QueryUtil.executeProcedure(con,ValueConstants.PK_PROVISIONING_PROCESO.concat(".SP_OBTENER_FLAG"), inputCollection);     
	               
	        } catch (Exception e) { 
	        	log.error(e);
	        }
	         finally
	         {
	             try
	             {
	                 con.close();
	             }catch(SQLException e)
	             {
	                 e.printStackTrace();
	                 log.error(e);
	             }
	         }
	        return lista;
 }

	@SuppressWarnings("unchecked")
	public static List<DynaBean> ListarCabeceraArchivoDelay(String fechaProceso) {     
	     List<DynaBean> lista = new ArrayList<DynaBean>();
		 Connection con = null;
         
		 ParameterCollection inputCollection = new ParameterCollection();
         inputCollection.addInput(fechaProceso,OracleTypes.VARCHAR);
         
         try
         {
             con= JdbcUtilitario.getInstance().getConnection();
             lista = QueryUtil.executeProcedure(con, ValueConstants.PK_PROVISIONING_PROCESO.concat(".SP_LISTAR_CABECERA_DELAY"), inputCollection);
         }
         catch(DAOException de)
         {
             log.error(de);
         }
         catch(Exception e)
         {
             log.error(e);
         }
         finally
         {
             try
             {
                 con.close();
             }catch(SQLException e)
             {
                 e.printStackTrace();
                 log.error(e);
             }
         }
         return lista;
	 }
	 

	public static List<DynaBean> ListarCabeceraArchivoEjecucion(String flag) {     
	    List<DynaBean> lista = new ArrayList<DynaBean>();
		Connection con = null;
		ParameterCollection inputCollection = new ParameterCollection();
        inputCollection.addInput(flag,OracleTypes.VARCHAR);
        log.info("CabeceraArchivoDAO ListarCabeceraArchivoEjecucion ==> FLAG ::"+flag);
        try
        {
          con= JdbcUtilitario.getInstance().getConnection();
        	lista = QueryUtil.executeProcedure(con,ValueConstants.PK_PROVISIONING_RF.concat(".SP_LISTAR_CABECERA_EJECUCION"), inputCollection);
        }
        catch(DAOException de)
        {
            log.error(de);
        }
        catch(Exception e)
        {
            log.error(e);
        }
        finally
        {
            try
            {
                con.close();
            }catch(SQLException e)
            {
                e.printStackTrace();
                log.error(e);
            }
        }
        return lista;
	 }
	 
	
	
	public static List<DynaBean> ObtenerCabeceraArchivoReproceso(String flag) {     
	     List<DynaBean> lista = new ArrayList<DynaBean>();
		 Connection con = null;
		 ParameterCollection inputCollection = new ParameterCollection();
       inputCollection.addInput(flag,OracleTypes.VARCHAR);
       log.info("CabeceraArchivoDAO ObtenerCabeceraArchivoReproceso ==> FLAG (1)Proceso Anterior (2)Repro Incre (3)Repro Manual :::"+flag);
       try
       {
           con= JdbcUtilitario.getInstance().getConnection();
           lista = QueryUtil.executeProcedure(con,ValueConstants.PK_PROVISIONING_PROCESO.concat(".SP_OBTENER_CABECERA_REPROCESO"), inputCollection);     
       }
       catch(DAOException de)
       {
           log.error(de);
       }
       catch(Exception e)
       {
           log.error(e);
       }
       finally
       {
           try
           {
               con.close();
           }catch(SQLException e)
           {
               e.printStackTrace();
               log.error(e);
           }
       }
       return lista;
	 }
	
/**
	 * Metodo que obtiene la lista del Archivo
	 * @param String fechaEjecucion
	 * @param String tipoArchivo
	 * @return List
	 */ 
  public static List obtenerListaDetalleArchivodeBD(String fechaEjecucion, String tipoArchivo) throws Exception{
	
	  Object[] objectL = new Object[1];
	  String codigo = "";
	  List<DynaBean> lista = new ArrayList<DynaBean>();
	  Connection con = null;
	  log.info("[CabeceraArchivoDAO obtenerListaDetalleArchivodeBD ==> fechaEjecucion :"+ fechaEjecucion+" ==> Tipo Archivo: "+tipoArchivo);
	  ParameterCollection inputCollection = new ParameterCollection();
	  inputCollection.addInput(fechaEjecucion,OracleTypes.VARCHAR);               
	  inputCollection.addInput(tipoArchivo,OracleTypes.VARCHAR);
    try {
	    con = JdbcUtilitario.getInstance().getConnection();
	    lista = QueryUtil.executeProcedure(con,ValueConstants.PK_PROVISIONING_PROCESO.concat(".SP_LISTAR_DETALLE_ARCHIVO"), inputCollection);
    } catch(Exception e) {
	    e.printStackTrace();
	    log.error(e);
	    throw new DAOException(e.getMessage(),e);
	  }
    finally
    {
        try
        {
            con.close();
        }catch(SQLException e)
        {
            e.printStackTrace();
            log.error(e);
        }
    }
	  return lista;
  }
	 	 
  /**
   * Metodo que elimina un registro en la tabla JOB
   * @param JobVO job
   * @return String
   * @throws Exception
   */
  public static String actualizar(CabeceraArchivoVO cabecera) throws Exception{       
    
    Object[] objectL = new Object[1];
    String codigo = "";
    Connection con = null;
    log.info("[CabeceraArchivoDAO actualizar] ==> IdCabecera "+ cabecera.getIdcabecera());
    ParameterCollection inputCollection = new ParameterCollection();
    inputCollection.addInput(cabecera.getIdcabecera(),OracleTypes.NUMBER);
    inputCollection.addInput(cabecera.getEstado(),OracleTypes.VARCHAR);
    if (!"".equals(cabecera.getEstado())) {
      inputCollection.addInput("ESTADO",OracleTypes.VARCHAR);
    } else {
      inputCollection.addInput("CONTADOREXITOS",OracleTypes.VARCHAR);
    }
    ParameterCollection outputCollection = new ParameterCollection();
    outputCollection.addOutput(OracleTypes.VARCHAR);
    try {
      con = JdbcUtilitario.getInstance().getConnection();
      objectL = QueryUtil.executeProcedure(con, ValueConstants.PK_PROVISIONING_PROCESO.concat(".SP_ACTUALIZAR_CABECERA"), inputCollection, outputCollection);
      if (objectL != null) {
        codigo = String.valueOf(objectL[0]);
      }
    } catch (Exception e) { 
      log.error(e);
      throw new DAOException(e.getMessage(), e);
    }
    finally
    {
        try
        {
            con.close();
        }catch(SQLException e)
        {
            e.printStackTrace();
            log.error(e);
        }
    }
    return codigo;
  }
	 
  

	 public static String obtenerCantidadReproceso(String flag) throws Exception{		 
			Object[] objectL = new Object[1];
			String codigo = "0";
			Connection con = null;
			
			log.info("[CabeceraArchivoDAO obtenerCantidadReproceso] ==> flag "+ flag);
			
			ParameterCollection inputCollection = new ParameterCollection();
			inputCollection.addInput(flag,OracleTypes.VARCHAR);           		
			
			ParameterCollection outputCollection = new ParameterCollection();
			outputCollection.addOutput(OracleTypes.NUMBER);
			
	        try {
     		con = JdbcUtilitario.getInstance().getConnection();
     		objectL = QueryUtil.executeProcedure(con, ValueConstants.PK_PROVISIONING_PROCESO.concat(".SP_OBTENER_CANTIDAD_FLAG"), inputCollection, outputCollection);
	        	
	            if (objectL != null) {
	                codigo = String.valueOf(objectL[0]);
	            }
	
	        } catch (Exception e) { 
	        	codigo="0";
	        	log.error(e);
	            throw new DAOException(e.getMessage(), e);
	        }
	         finally
	         {
	             try
	             {
	                 con.close();
	             }catch(SQLException e)
	             {
	                 e.printStackTrace();
	                 log.error(e);
	             }
	         }		        
     
	         return codigo;
	 }
	 
	 
	  public static String asignarCabeceraReproceso(CabeceraArchivoVO cabecera, String opcion) throws Exception{       
		    
		    Object[] objectL = new Object[1];
		    String codigo = "";
		    Connection con = null;
		    log.info("[CabeceraArchivoDAO asignarCabeceraReproceso] ==> Fecha Proceso "+ cabecera.getFechaProceso()+" ==> Tipo Arc :"+cabecera.getTipoArchivo()+" ==> opcion ::"+opcion);
		    ParameterCollection inputCollection = new ParameterCollection();
		    inputCollection.addInput(opcion,OracleTypes.VARCHAR);
		    inputCollection.addInput(cabecera.getFechaProceso(),OracleTypes.VARCHAR);
		    inputCollection.addInput(cabecera.getTipoArchivo(),OracleTypes.VARCHAR);
		    
		    ParameterCollection outputCollection = new ParameterCollection();
		    outputCollection.addOutput(OracleTypes.VARCHAR);
		    try {
		      con = JdbcUtilitario.getInstance().getConnection();
		      objectL = QueryUtil.executeProcedure(con, ValueConstants.PK_PROVISIONING_PROCESO.concat(".SP_ASIGNAR_CABECERA_REPROCESO"), inputCollection, outputCollection);
		      if (objectL != null) {
		        codigo = String.valueOf(objectL[0]);
		      }
		    } catch (Exception e) { 
		      log.error(e);
		      throw new DAOException(e.getMessage(), e);
		    }
	         finally
	         {
	             try
	             {
	                 con.close();
	             }catch(SQLException e)
	             {
	                 e.printStackTrace();
	                 log.error(e);
	             }
	         }
		    log.error("DAO obtenido :::"+codigo);
		    return codigo;
		  }
			 
	  
	  public static String cerrarProcesos() throws Exception{       
		    
		    Object[] objectL = new Object[1];
		    String codigo = "0";
		    Connection con = null;
		    ParameterCollection inputCollection = new ParameterCollection();
		    inputCollection.addInput(codigo, OracleTypes.VARCHAR); 
		    ParameterCollection outputCollection = new ParameterCollection();
		    outputCollection.addOutput(OracleTypes.VARCHAR);
		    try {
		      con = JdbcUtilitario.getInstance().getConnection();
		      objectL = QueryUtil.executeProcedure(con, ValueConstants.PK_PROVISIONING_PROCESO.concat(".SP_CERRAR_PROCESO"), inputCollection, outputCollection);
		      if (objectL != null) {
		        codigo = String.valueOf(objectL[0]);
		      }
		    } catch (Exception e) { 
		      log.error(e);
		      throw new DAOException(e.getMessage(), e);
		    }
	         finally
	         {
	             try
	             {
	                 con.close();
	             }catch(SQLException e)
	             {
	                 e.printStackTrace();
	                 log.error(e);
	             }
	         }
		    log.error("DAO cerrarProcesos obtenido :::"+codigo);
		    return codigo;
		  }
			 
	  public static boolean existeArchivoEnCabecera(String nombreArchivo) throws Exception{    
      Object[] objectL = new Object[1];
      boolean resultado=false;
      int cantidad = 0;
      Connection con = null;
      
      log.info("[CabeceraArchivoDAO existeArchivoEnCabecera] ==> nombreArchivo "+ nombreArchivo);
      
      ParameterCollection inputCollection = new ParameterCollection();
      inputCollection.addInput(nombreArchivo,OracleTypes.VARCHAR);               
      
      ParameterCollection outputCollection = new ParameterCollection();
      outputCollection.addOutput(OracleTypes.NUMBER);
      
          try {
        con = JdbcUtilitario.getInstance().getConnection();
        objectL = QueryUtil.executeProcedure(con, ValueConstants.PK_PROVISIONING_PROCESO.concat(".SP_EXISTE_ARCHIVO_EN_CABECERA"), inputCollection, outputCollection);
            
              if (objectL != null) {
                cantidad = Integer.parseInt(String.valueOf(objectL[0]));
                if (cantidad>0) {
                  resultado=true;
                }
              }
  
          } catch (Exception e) { 
            cantidad=0;
            log.error(e);
              throw new DAOException(e.getMessage(), e);
          }
           finally
           {
               try
               {
                   con.close();
               }catch(SQLException e)
               {
                   e.printStackTrace();
                   log.error(e);
               }
           }            
     
           return resultado;
   }
	  
	  
	  
		 public static void asignarProcesoCambio(String tipoArchivo, String fechaProceso, String opcion) {
			 	
				Connection con = null;
				log.info(" asignarProcesoCambio ==> fechaProceso:"+ fechaProceso+"==>tipoArchivo:"+tipoArchivo+"==>opcion:"+opcion);
	   		    
				ParameterCollection inputCollection = new ParameterCollection();
				inputCollection.addInput(fechaProceso,OracleTypes.VARCHAR);
				inputCollection.addInput(tipoArchivo,OracleTypes.VARCHAR);
				inputCollection.addInput(opcion,OracleTypes.VARCHAR);
				
		        try {
		        	con = JdbcUtilitario.getInstance().getConnection();
		        	QueryUtil.execute(con, ValueConstants.PK_PROVISIONING_PROCESO.concat(".SP_ASIGNAR_PROCESO_CAMBIO"), inputCollection);
		        } catch (Exception e) { 
		        	log.error(e);
		        }
		         finally
		         {
		             try
		             {
		                 con.close();
		             }catch(SQLException e)
		             {
		                 e.printStackTrace();
		                 log.error(e);
		             }
		         }
	    }
		 
		 
}
