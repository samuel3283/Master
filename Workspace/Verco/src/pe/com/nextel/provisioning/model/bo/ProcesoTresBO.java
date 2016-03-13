package pe.com.nextel.provisioning.model.bo;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.com.nextel.provisioning.framework.exception.DAOException;
import pe.com.nextel.provisioning.framework.exception.FileUtilException;
import pe.com.nextel.provisioning.framework.util.files.FileUtil;
import pe.com.nextel.provisioning.model.dao.CabeceraArchivoDAO;
import pe.com.nextel.provisioning.model.vo.CabeceraArchivoVO;
import pe.com.nextel.provisioning.view.ValueConstants;

/**
 * <p>Title: ProcesoTresBO</p>
 * <p>Description: Clase controladora encargada del flujo del aplicativo.</p>
 * <p>Proyecto    : provisioningNextel</p>
 * <p>Clase       : ProcesoTresBO</p>
 * <p>Fecha       : 04 Diciembre 2009</p>
 * <p>Copyright   : Copyright (c) 2000-2009</p>
 * <p>Company     : NEXTEL</p>
 * @author  FRANK PICOY ROSAS (COMSA)
 * @version 1.0
 */

public class ProcesoTresBO {
	private static final Log log = LogFactory.getLog(ProcesoTresBO.class);    
  /**
   * Metodo que se encarga de ejecutar el Proceso Tres
   * @param String fechaEjecucion
   * @return String
   */
  public String ejecutar(String fechaEjecucion, Map mapParametros){
    String resultado="OK";
    String resultadoFP="OK";
    String resultadoFR="OK";
    
    resultadoFP = this.ejecutar(ValueConstants.ARCHIVO_FP, fechaEjecucion, mapParametros);
    log.info("ejecutar ARCHIVO_FP "+resultadoFP);
    resultadoFR = this.ejecutar(ValueConstants.ARCHIVO_FR, fechaEjecucion, mapParametros);
    log.info("ejecutar ARCHIVO_FR "+resultadoFR);

    return resultado;
  }
  
  public String ejecutar(String tipoArchivo,String fechaProceso, Map mapParametros){
    String resultado="OK";
    String nombreArchivoOrigen="";
    String rutaDestino="";
    String nombreArchivoDestino="";
    List<CabeceraArchivoVO> listCabeceraArchivo = null;
    CabeceraArchivoVO cabeceraArchivoVO = new CabeceraArchivoVO();
    DynaBean dyna = null;
    try {
      String destino="";
      if (ValueConstants.ARCHIVO_FP.equals(tipoArchivo)) {
        destino= ValueConstants.DESTINO_ARCHIVO_FP;
      } else {
        destino= ValueConstants.DESTINO_ARCHIVO_FR;
      }
      nombreArchivoOrigen=String.valueOf(mapParametros.get(ValueConstants.CODIGO_RUTA_ORIGEN_PROCESO_TRES)).trim()
      .concat(destino).concat(fechaProceso);
      rutaDestino=String.valueOf(mapParametros.get(ValueConstants.CODIGO_RUTA_DESTINO_PROCESO_TRES)).trim();
      nombreArchivoDestino=destino.concat(fechaProceso);
      log.info("La Ruta del Archivo Proceso 3 es-" + nombreArchivoOrigen + "-");
      File archivo = new File(nombreArchivoOrigen);
      if (archivo.exists()){
        List<DynaBean> listaArchivo = verificarExisteArchivoEnBD(tipoArchivo, fechaProceso);
        if (listaArchivo!=null && listaArchivo.size()>0) {
          dyna = listaArchivo.get(0);
          cabeceraArchivoVO.setIdcabecera(Integer.valueOf(String.valueOf(dyna.get("IDCABECERA"))));
        } else {
          log.info("Ocurrio un error al verificar los datos en la BD para el archivo " + tipoArchivo );
          resultado="ERRTEC0000";
        }
        FileUtil.copyFile(nombreArchivoOrigen,rutaDestino,nombreArchivoDestino);
        //Actualizamos Estado Proceso Cabecera
        cabeceraArchivoVO.setEstado(ValueConstants.CODIGO_PROCESO3);
        resultado = CabeceraArchivoDAO.actualizar(cabeceraArchivoVO);
      } else {
        log.info("No existe el archivo " + tipoArchivo + " en la ruta Origen del Proceso 3");
        resultado="ERRP030001"+"-No existe el archivo " + tipoArchivo + " en la ruta Origen del Proceso 03";
      }
    } catch(FileUtilException e) {
      resultado="";
      log.info("Hubo un error al copiar el archivo " + tipoArchivo);
    } catch (Exception e) {
      resultado="";
      log.info("Hubo un error al obtener los Parametros en el Proceso Tres");
    }
    
    return resultado;
  }
  
  public List<DynaBean> verificarExisteArchivoEnBD(String tipoArchivo, String fechaProceso) {
    List<DynaBean> listaArchivoBD = null;
    try {
      listaArchivoBD = CabeceraArchivoDAO.ObtenerCabeceraArchivo(tipoArchivo, fechaProceso);
      if (listaArchivoBD==null || listaArchivoBD.size()==0) {
        
      } 
    } catch (DAOException e) {
      
    }
    return listaArchivoBD;
  }
}
