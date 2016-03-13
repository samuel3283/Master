package pe.com.nextel.provisioning.model.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.com.nextel.provisioning.framework.exception.FileUtilException;
import pe.com.nextel.provisioning.framework.util.files.FileUtil;
import pe.com.nextel.provisioning.model.dao.CabeceraArchivoDAO;
import pe.com.nextel.provisioning.model.vo.CabeceraArchivoVO;
import pe.com.nextel.provisioning.view.ValueConstants;

public class ProcesoCincoBO {
  
	 private static final Log log = LogFactory.getLog(ProcesoCincoBO.class);    
	 private static ProcesoCincoBO single = null;
	 
	 public static ProcesoCincoBO getInstance() {
	    if (single == null)
	        single = new ProcesoCincoBO();
	    return single;
	 }	
	 
	 
	 public String ejecutar(String fechaProceso)
	 {
		 
		 List<CabeceraArchivoVO> lista = ListarCabeceraArchivoDelay(fechaProceso);
		 String resultado = ValueConstants.PROCESO_SATISFACTORIO;
		 String valor="";
		 if(lista.size()>0)
		 {
			 Map<String,String> mapParametros = new HashMap<String, String>();
			 CabeceraArchivoVO cabeceraArchivoVO = null;
			 String name_file = "";
			 try {
				 
				mapParametros =ParametrosBO.getInstance().ObtenerParametros();
				
				for(int i=0;i<lista.size();i++) {
					cabeceraArchivoVO = new CabeceraArchivoVO();
					cabeceraArchivoVO = lista.get(i);
					
					 try {
						 name_file = cabeceraArchivoVO.getTipoArchivo().toUpperCase().concat("WS_").concat(cabeceraArchivoVO.getFechaProceso());

						 if(FileUtil.existsFile(String.valueOf(mapParametros.get(ValueConstants.PROCESO_CINCO_RUTA_ORIGEN))+name_file )) {	 

						 FileUtil.copyFile(String.valueOf(mapParametros.get(ValueConstants.PROCESO_CINCO_RUTA_ORIGEN))+name_file, String.valueOf(mapParametros.get(ValueConstants.PROCESO_CINCO_RUTA_DESTINO)),name_file);
						 
						 FileUtil.deleteFile(String.valueOf(mapParametros.get(ValueConstants.PROCESO_CINCO_RUTA_ORIGEN))+name_file);
						 
						 //Si fue correcto matamos flag delay
						 ProcesoCuatroBO.getInstance().activarFlag(cabeceraArchivoVO,ValueConstants.ESTADO_FINALIZADO,ValueConstants.VALOR_UNO);

						 //Si fue correcto activamos flag ejecucion
						 ProcesoCuatroBO.getInstance().activarFlag(cabeceraArchivoVO,ValueConstants.ESTADO_ACTIVO,ValueConstants.VALOR_DOS);

						 //Actualizamos Estado Proceso Cabecera
						 cabeceraArchivoVO.setEstado(ValueConstants.CODIGO_PROCESO5);
						 valor = CabeceraArchivoDAO.actualizar(cabeceraArchivoVO);

						 }

					 } 
					 catch(FileUtilException e)
					 { 
					   ProcesoCuatroBO.getInstance().asignarProcesoCambio(cabeceraArchivoVO.getTipoArchivo(), cabeceraArchivoVO.getFechaProceso(), ValueConstants.VALOR_CERO);
					   log.error(e.getMessage()); 
					   resultado = ValueConstants.ERROR_ARCHIVO_COPIA_INCORRECTA;
					  } catch (Exception e) {
 					    ProcesoCuatroBO.getInstance().asignarProcesoCambio(cabeceraArchivoVO.getTipoArchivo(), cabeceraArchivoVO.getFechaProceso(), ValueConstants.VALOR_CERO);
						log.info(e.getMessage());
						resultado = ValueConstants.ERROR_CAUSAS_TECNICAS;
					  }				 
						 
				}					
			} catch (Exception e) {
				log.info(e.getMessage());
				 resultado = ValueConstants.ERROR_CAUSAS_TECNICAS;
			}
					
		 } else {
			 log.info("==> moverEjecucion: Lista de Vacia.");	 
		 }
		 
		 return resultado;
	 }
	 

	 public List<CabeceraArchivoVO> ListarCabeceraArchivoDelay(String fechaProceso) {       

		 	CabeceraArchivoVO cabeceraArchivoVO = null;
		 	List<CabeceraArchivoVO> lista = new ArrayList();   
		 	List<DynaBean> listaArchivo = new ArrayList();
		 	
		 try
         {
              listaArchivo = CabeceraArchivoDAO.ListarCabeceraArchivoDelay(fechaProceso);
              if(log.isInfoEnabled())log.info(" ListarCabeceraArchivoDelay ::: size ::: "+(listaArchivo == null?null:listaArchivo.size()));
              Iterator it = listaArchivo.iterator();
              for(;it.hasNext();)
              {
             	 	  cabeceraArchivoVO = new CabeceraArchivoVO();
                      DynaBean dyna = (DynaBean)it.next();                    
                      cabeceraArchivoVO.setIdcabecera(Integer.valueOf(String.valueOf(dyna.get("IDCABECERA"))));
                      cabeceraArchivoVO.setArchivo(String.valueOf(dyna.get("ARCHIVO")));
                      cabeceraArchivoVO.setFechaProceso(String.valueOf(dyna.get("FECHAPROCESO")));
                      cabeceraArchivoVO.setTipoArchivo(String.valueOf(dyna.get("TIPOARCHIVO")));
                      cabeceraArchivoVO.setProcesoInicio(String.valueOf(dyna.get("FECHAINICIOEJECUCION")));
                      lista.add(cabeceraArchivoVO);
              }
        
          }
          catch(Exception e)
          {
                log.info(e.getMessage());
          }
          return lista;   
	 }	 	
  
	 
  
	 
  
}
