package pe.com.nextel.provisioning.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.com.nextel.provisioning.framework.conexion.JdbcBpmPorta;
import pe.com.nextel.provisioning.framework.conexion.JdbcUtilitario;
import pe.com.nextel.provisioning.framework.exception.DAOException;
import pe.com.nextel.provisioning.model.vo.AlarmaVO;
import pe.com.nextel.provisioning.model.vo.Consulta1VO;
import pe.com.nextel.provisioning.model.vo.EstadoProcesoVO;
import pe.com.nextel.provisioning.model.vo.SolicitudVO;

public class SolicitudDAO {
	
	private static final Log log = LogFactory.getLog(SolicitudDAO.class);    

	public SolicitudDAO(){}

	


	public static List consultarSolicitudes(String tipo, String idProceso, String numeroSecuencial) throws Exception{

		List rList = new ArrayList();
		List item = new ArrayList();
		SolicitudVO bean = new SolicitudVO();
		Connection con = null;
		String sql = "";
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		
		sql = " SELECT C.ID_INTERNO AS ID_INT, C.TRACK_ID, C.ID_MENSAJE AS ID_MJE,  " +
			" C.TIPO_MENSAJE AS MENSAJE,  " + 
			" C.OPERADOR_ORIGEN AS OPE_ORI,C.OPERADOR_DESTINO AS OPE_DES, "+ 
			" NVL(C.NUMERO_SECUENCIAL,' ') AS NUMERO_SECUENCIAL, NVL(C.ID_PROCESO,' ') AS ID_PROCESO,  " +
			" to_char(C.FECHA_REGISTRO,'dd/mm/yyyy HH24:mi:ss') AS FECHA_REGISTRO, " +
			" (CASE  " +
			" WHEN C.TIPO_MENSAJE = 'SR' then " + 
			" (SELECT 'Móvil: '||NUMERO_TELEFONO||'  Ced Ini: '||DONANTE_INICIAL||'  Motivo: '||MOTIVO_RETORNO " +
			" FROM MENSAJE_SR WHERE ID_INTERNO=C.ID_INTERNO) " +
			" WHEN C.TIPO_MENSAJE = 'AR' then  " +
			" (SELECT 'Fec Ejecución: '||TO_CHAR(FECHA_EJECUCION,'dd/mm/yyyy HH24:mi:ss') " +
			" FROM MENSAJE_AR WHERE ID_INTERNO=C.ID_INTERNO) " +
			" WHEN C.TIPO_MENSAJE = 'DR' then  " +
			" (SELECT 'Rechazo: '||CAUSA_RECHAZO " +
			" FROM MENSAJE_DR WHERE ID_INTERNO=C.ID_INTERNO) " +
			" WHEN C.TIPO_MENSAJE = 'SP' then  " +
			" (SELECT  " +
			" 'Receptor: '||OPERADOR_RECEPTOR||chr(10)|| " +
			" '  Cedente: '||OPERADOR_DONANTE||chr(10)|| " +
			" '  Tipo Doc: '||TIPO_IDENTIFICACION||chr(10)|| " +
			" '  Nro Doc: '||NUMERO_DOCUMENTO||chr(10)|| " +
			" '  Cant Númeraciones: '||CANTIDAD_NUMERACIONES " +
			" AS DESCRIPCION FROM MENSAJE_SP WHERE ID_INTERNO=C.ID_INTERNO) " +
			" WHEN C.TIPO_MENSAJE = 'ANS' then " + 
			" ((CASE " + 
			" WHEN (SELECT 1 FROM PROCESO_ABIERTO_NUMERACION WHERE ID_SOLICITUD=C.ID_PROCESO) = '1' then  " + 
			" (SELECT 'Móvil:  '||NUMERO_TELEFONO||'  Proceso Abierto' FROM MENSAJE_ANS WHERE ID_INTERNO=C.ID_INTERNO) "+   
		    	  
			" WHEN (SELECT 1 FROM PROCESO_CERRADO_NUMERACION WHERE ID_SOLICITUD=C.ID_PROCESO) = '1' then  " + 
			" (SELECT 'Móvil:  '||NUMERO_TELEFONO||'  Proceso Cerrado' FROM MENSAJE_ANS WHERE ID_INTERNO=C.ID_INTERNO) "+   
			" ELSE  " +    
			" (select 'NA' from dual) " + 
			" end)) " +
			
			" WHEN C.TIPO_MENSAJE = 'RABDCP' then " + 
			" (SELECT M.CAUSA_RECHAZO||' - '||R.DESCRIPCION " +
			" FROM MENSAJE_RABDCP M, CAUSAS_RECHAZO R " +
			" WHERE M.CAUSA_RECHAZO=R.CODIGO AND " +
			" M.ID_INTERNO=C.ID_INTERNO)"	+

			" WHEN C.TIPO_MENSAJE = 'VABDCP' then  " +
			" (SELECT 'Estado de Numeración:  '||N.ESTADO||'  '||DESCRIPCION FROM " + 
			" NUMERACION_PORTADA N, ESTADO_NUMERACION E " +
			" WHERE N.ESTADO=E.CODIGO AND " +
			" N.ULTIMO_PROCESO=C.ID_PROCESO ) " +
			" WHEN C.TIPO_MENSAJE = 'EABDCP' then  " +
			" (SELECT CODIGO_ERROR FROM MENSAJE_EABDCP WHERE ID_INTERNO=C.ID_INTERNO) " +
			" WHEN C.TIPO_MENSAJE = 'SPR' then  " +
			" (SELECT 'Fec Lim Prog: '||to_char(FECHA_LIMITE_PROGRAMACION,'dd/mm/yyyy HH24:mi:ss') " +
			" ||'  Fec Lim Ejec: '||to_char(FECHA_LIMITE_EJECUCION,'dd/mm/yyyy HH24:mi:ss') " +   
			" FROM MENSAJE_SPR WHERE ID_INTERNO=C.ID_INTERNO) " +
			" WHEN C.TIPO_MENSAJE = 'PP' then " + 
			" (SELECT 'Fec Prog: '||to_char(FECHA_PROGRAMACION,'dd/mm/yyyy HH24:mi:ss') " +
			" FROM MENSAJE_PP WHERE ID_INTERNO=C.ID_INTERNO) " +
			" WHEN C.TIPO_MENSAJE = 'PEP' then  " +
			" (SELECT 'Fec Eje Portabilidad: '||to_char(FECHA_EJECUCION_PORTABILIDAD,'dd/mm/yyyy HH24:mi:ss') " +
			" FROM MENSAJE_PEP WHERE ID_INTERNO=C.ID_INTERNO) " +
			" WHEN C.TIPO_MENSAJE = 'CNPF' then  " +
			" (SELECT 'Fec Lim Prog: '||to_char(FECHA_LIMITE_PROGRAMACION,'dd/mm/yyyy HH24:mi:ss') " +
			" FROM MENSAJE_CNPF WHERE ID_INTERNO=C.ID_INTERNO) " +
			" WHEN C.TIPO_MENSAJE = 'OCC' then  " +
			" (SELECT 'Objeción: '||M.CAUSA_OBJECION||' - '||R.DESCRIPCION " +
			" FROM MENSAJE_OCC M, CAUSAS_RECHAZO R " +
			" WHERE M.CAUSA_OBJECION=R.CODIGO AND  M.ID_INTERNO=C.ID_INTERNO) " +
			" WHEN C.TIPO_MENSAJE = 'OPC' then  " +
			" (SELECT 'Objeción: '||CAUSA_OBJECION " +
			" FROM MENSAJE_OPC WHERE ID_INTERNO=C.ID_INTERNO) " +
			" WHEN C.TIPO_MENSAJE = 'SVP' then " + 
			" (SELECT 'Tip Doc: '||TIPO_IDENTIFICACION||'  Nro Doc: '||NUMERO_DOCUMENTO " +
			" FROM MENSAJE_SVP WHERE ID_INTERNO=C.ID_INTERNO) " +
			" WHEN C.TIPO_MENSAJE = 'NI' then  " +
			" (SELECT 'Causa: '||CAUSA_NO_INTEGRIDAD " +
			" FROM MENSAJE_NI WHERE ID_INTERNO=C.ID_INTERNO) " +
			" WHEN C.TIPO_MENSAJE = 'FLEP' then  " +
			" (SELECT 'Fec Lim Prog: '||to_char(FECHA_LIMITE_PROGRAMACION,'dd/mm/yyyy HH24:mi:ss') " +
			" ||'  Fec Lim Ejec: '||to_char(FECHA_LIMITE_EJECUCION,'dd/mm/yyyy HH24:mi:ss') " +   
			" FROM MENSAJE_FLEP WHERE ID_INTERNO=C.ID_INTERNO) " +
			" else " + 
			" (select ' ' from dual) " + 
			" end) AS DETALLE, " +
			" (SELECT RESP_SOAP FROM XCEWSMULTI.TRK WHERE TRK=C.TRACK_ID) AS RESP_SOAP, " +
			" (SELECT to_char(FECHA_SOAP,'dd/mm/yyyy HH24:mi:ss')  FROM XCEWSMULTI.TRK WHERE TRK=C.TRACK_ID) AS FECHA_SOAP, " +
			" 'Mensaje ' AS MJE_SOAP, C.ID_MENSAJE, C.ID_INTERNO " +
			" FROM CABECERA_MENSAJE C " +
			" WHERE ";
		
			//" (SELECT TO_CHAR(UTL_RAW.CAST_TO_VARCHAR2(MENSAJE)) FROM XCEWSMULTI.TRK WHERE TRK=C.TRACK_ID) AS MJE_SOAP " +
		
		
			if("S".equals(tipo)) {
			
			if(numeroSecuencial.length()>0)	{
			
				sql = sql+" C.NUMERO_SECUENCIAL='"+numeroSecuencial+"' " +
				" ORDER BY C.ID_INTERNO ";
			} else if (idProceso.length()>0)	{
				sql = sql+" C.NUMERO_SECUENCIAL = " +
				" (SELECT DISTINCT NUMERO_SECUENCIAL FROM CABECERA_MENSAJE WHERE " +
				"ID_PROCESO='"+idProceso+"') " +
				" ORDER BY C.ID_INTERNO ";
			}
				
			} else {
				sql = sql+" C.ID_PROCESO='"+idProceso+"' " +
				" ORDER BY C.ID_INTERNO ";
			}
			
		
			
			log.info(" sql " + sql ) ;

		try {
			con = JdbcUtilitario.getInstance().getConnection();
			stmt = con.prepareStatement(sql);
			resultSet = stmt.executeQuery();
			
			while (resultSet.next()) {
				bean = new SolicitudVO();
				
				/*ID_INTERNO,TRACK_ID,ID_MENSAJE,TIPO_MENSAJE, 
				OPERADOR_ORIGEN, OPERADOR_DESTINO NUMERO_SECUENCIAL,ID_PROCESO,FEC_REGISTRO,
				*/
				bean.setTrackId(resultSet.getString(2));
				bean.setTipoMensaje(resultSet.getString(4));
				bean.setOperadorOrigen(resultSet.getString(5));
				bean.setOperadorDestino(resultSet.getString(6));
				bean.setNumeroSecuencial(resultSet.getString(7));
				bean.setIdentificadorProceso(resultSet.getString(8));
				bean.setFechaRegistro(resultSet.getString(9));
				bean.setDetalle(resultSet.getString(10));
				bean.setRespuestaSoap(resultSet.getString(11));
				bean.setFechaSoap(resultSet.getString(12));
				bean.setMensaje(resultSet.getString(13));
				bean.setIdMensaje(resultSet.getString(14));
				bean.setIdInterno(resultSet.getString(15));
				if(bean.getTipoMensaje().equals("NI")) {
				item = TRKDAO.consultarTRK(bean);
				bean.setListadoMensajes(item);
				}			
				rList.add(bean);
					
			}
			
		} catch (Exception e) { 
			throw new DAOException(e.getMessage(), e);
		}
		finally
		{
			try
			{
				con.close();
				stmt.close();
				resultSet.close();
			}catch(SQLException e)
			{
				e.printStackTrace();
				log.error(e);
			}
		}
		return rList ;
	}
	
	


	public static String obtenerProcessInstance(String tipo, String idProceso, String numeroSecuencial) throws Exception{
		
		List rList = new ArrayList() ;
		AlarmaVO bean = new AlarmaVO();
		bean.setProcessinstance("");
		Connection con = null;
		String sql = "";
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		sql = "SELECT PROCESSINSTANCE FROM RELACION_PROCESO WHERE " ;
		
		
			if("S".equals(tipo)) {
			
			if(numeroSecuencial.length()>0)	{			
				sql = sql+" NUMERO_SECUENCIAL='"+numeroSecuencial+"' AND ID_PROCESO!='NO_ID' " ;
			} else if (idProceso.length()>0)	{
				sql = sql+" ID_PROCESO='"+idProceso+"' ";
			}
				
			} else {
				sql = sql+" ID_PROCESO='"+idProceso+"' ";
			}
			log.info(" sql " + sql ) ;

		try {
			con = JdbcUtilitario.getInstance().getConnection();
			stmt = con.prepareStatement(sql);
			resultSet = stmt.executeQuery();
			
			while (resultSet.next()) {
				bean = new AlarmaVO();
				bean.setProcessinstance(resultSet.getString(1));
				rList.add(bean);
					
			}
			
		} catch (Exception e) { 
			throw new DAOException(e.getMessage(), e);
		}
		finally
		{
			try
			{
				con.close();
				stmt.close();
				resultSet.close();
			}catch(SQLException e)
			{
				e.printStackTrace();
				log.error(e);
			}
		}
		return bean.getProcessinstance();
	}
	
	

	
	public static List consultarAlarmas(String processInstance) throws Exception{

		List rList = new ArrayList() ;
		AlarmaVO bean = new AlarmaVO();
		Connection con = null;
		String sql = "";
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		sql = " SELECT PROCESSINSTANCE_, to_char(DUEDATE_,'dd/mm/yyyy hh24:mi:ss') AS TEMPORIZADOR, " + 
			  " SUBSTR (NAME_,1,5) AS ALARMA, " + 
			  " DECODE(SUBSTR (NAME_,1,5), " +
			  " 'A0101','Verif de rspta al mje SP', " +
			  " 'A0102','Verif de envio RABDCP/VABDCP', " +
			  " 'A0103','Verif de plazo de subsanación', " +
			  " 'A0104','Verif de envio ANS', " +
			  " 'A0105','Verif de rspta al mje SVP', " +
			  " 'A0106','Verif aceptacion/denegacion del cedente', " +
			  " 'A0107','Verif de envio ESC', " +
			  " 'A0108','Verif de rspta al mje SAC', " +
			  " 'A0109','Verif de envio SPR', " +
			  " 'A0110','Verif de plazo de prog de portabilidad', " +
			  " 'A0111','Verif de rspta al mje OCC', " +
			  " 'A0112','Verif de envio OPC', " +
			  " 'A0113','Verif de rspta al mje PP', " +
			  " 'A0114','Verif de envio PEP', " +
			  " 'A0115','Fin de ventana de cambio', " +
			  " 'A0116','Verif de envio FLEP', " +
			  " 'A0117','Verif de envio CNPF', " +
			  " 'A0118','Notif del fin de plazo de rspta al OCC', " +
			  " 'A0119','Verif de envio RABDCP no subsanable', " +
			  " 'A0201','Verif de rspta al mje SR', " +
			  " 'A0202','Verif de envio DR', " +
			  " 'A0203','Verif de envio AR', " +
			  " 'A0204','Fin de ventana de Retorno', " +
			  " 'A0205','Verif de envio SR al cedente', " +
			  " 'A0301','Verif de envio NI', " +
			  " 'A0302','Verif de rspta al mje NI', " +
			  " 'A0401','Verif de envio EABDCP',  " +
			  " 'NA') AS DESC_ALARMA " +
			  " FROM JBPM_JOB " +
			  " WHERE processinstance_='"+processInstance+"' ";
		
			log.info(" sql " + sql ) ;

		try {
			con = JdbcBpmPorta.getInstance().getConnection();
			stmt = con.prepareStatement(sql);
			resultSet = stmt.executeQuery();
			
			while (resultSet.next()) {
				bean = new AlarmaVO();
				bean.setProcessinstance(resultSet.getString(1));
				bean.setFechaExpiracion(resultSet.getString(2));
				bean.setAlarma(resultSet.getString(3));
				bean.setDescripcionAlarma(resultSet.getString(4));
				rList.add(bean);
					
			}
			
		} catch (Exception e) { 
			throw new DAOException(e.getMessage(), e);
		}
		finally
		{
			try
			{
				con.close();
				stmt.close();
				resultSet.close();
			}catch(SQLException e)
			{
				e.printStackTrace();
				log.error(e);
			}
		}
		return rList ;
	}
	
	
	public static List consultarEstados() throws Exception{

		List rList = new ArrayList() ;
		EstadoProcesoVO bean = new EstadoProcesoVO();
		Connection con = null;
		String sql = "";
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		sql = " SELECT (case " +
			" when CODIGO = '01' OR CODIGO ='02' then " + 
			" (SELECT 'MOTIVO RETORNO' FROM DUAL) " +
			" when SUBSTR(CODIGO,1,5)='ERROR' then  " +
			" (SELECT 'ERROR EABDCP' FROM DUAL) " +
			" when SUBSTR(CODIGO,1,3)='REC' then  " +
			" (SELECT 'CAUSA RECHAZO' FROM DUAL) " +
			" when SUBSTR(CODIGO,1,3)='NIN' then  " +
			" (SELECT 'CAUSA NO INTEGRIDAD' FROM DUAL) " +
			" else  " +
			" (SELECT ' ' FROM DUAL) " +
			" end) AS VALOR, " +
			" CODIGO, DESCRIPCION FROM CAUSAS_RECHAZO " + 
			//" WHERE SUBSTR(CODIGO,1,3)!='NIN' " +
			" UNION " +
			" SELECT  " +
			" 'ESTADO NUMERO' AS VALOR,CODIGO, DESCRIPCION FROM ESTADO_NUMERACION " +
			" ORDER BY 1,2 " ; 
			
			log.info(" sql " + sql ) ;

		try {
			con = JdbcUtilitario.getInstance().getConnection();
			stmt = con.prepareStatement(sql);
			resultSet = stmt.executeQuery();
			
			while (resultSet.next()) {
				bean = new EstadoProcesoVO();
				//VALOR codigo desc
				bean.setProceso(resultSet.getString(1));
				bean.setCodigo(resultSet.getString(2));
				bean.setDescripcion(resultSet.getString(3));
				rList.add(bean);					
			}
			
		} catch (Exception e) { 
			throw new DAOException(e.getMessage(), e);
		}
		finally
		{
			try
			{
				con.close();
				stmt.close();
				resultSet.close();
			}catch(SQLException e)
			{
				e.printStackTrace();
				log.error(e);
			}
		}
		return rList ;
	}
	
	
	

	public static List consultarListaSolicitudes(String tipo, String numero, String trk) throws Exception{

		List rList = new ArrayList();
		List item = new ArrayList();
		SolicitudVO bean = new SolicitudVO();
		Connection con = null;
		String sql = "";
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		

			if("S".equals(tipo)) {
		
				sql =
					" select m.id_interno, c.track_id as track_id, m.inicio_rango as numero, c.tipo_mensaje as tipo_mensaje, c.operador_origen as operador, " + 
					" (select id_proceso from cabecera_mensaje ca, mensaje_ans ma where ca.id_interno=ma.id_interno and ca.tipo_mensaje='ANS' and " + 
					" ca.numero_Secuencial=c.numero_secuencial and ma.numero_telefono=m.inicio_rango) as id_proceso, " +
					" c.numero_secuencial as numero_secuencial,  " +
					" to_char(c.fecha_registro,'dd/mm/yyyy HH24:mi:ss') as fecha_registro " +
					" from mensaje_sp_numeracion m, cabecera_mensaje c " +
					" where m.id_interno=c.id_interno and m.inicio_rango='"+numero+"' " +
					" UNION " +
					" select s.id_interno as id_interno, c.track_id as track_id, s.numero_telefono as numero, c.tipo_mensaje as tipo_mensaje, c.operador_origen as operador, " +
					" c.id_proceso as id_proceso, nvl(c.numero_secuencial,' ') as numero_secuencial, " + 
					" to_char(c.fecha_registro,'dd/mm/yyyy HH24:mi:ss') as fecha_registro " +
					" from mensaje_sr s, cabecera_mensaje c " +
					" where  " +
					" s.id_interno=c.id_interno and c.operador_origen !='00' and s.numero_telefono='"+numero+"' " +
					" order by 1 ";

			} else {

				sql =
					" select m.id_interno, c.track_id as track_id, m.inicio_rango as numero, c.tipo_mensaje as tipo_mensaje, c.operador_origen as operador, " + 
					" (select id_proceso from cabecera_mensaje ca, mensaje_ans ma where ca.id_interno=ma.id_interno and ca.tipo_mensaje='ANS' and " + 
					" ca.numero_Secuencial=c.numero_secuencial and ma.numero_telefono=m.inicio_rango) as id_proceso, " +
					" c.numero_secuencial as numero_secuencial,  " +
					" to_char(c.fecha_registro,'dd/mm/yyyy HH24:mi:ss') as fecha_registro " +
					" from mensaje_sp_numeracion m, cabecera_mensaje c " +
					" where m.id_interno=c.id_interno and m.inicio_rango='"+numero+"' " +
					" UNION " +
					" select s.id_interno as id_interno, c.track_id as track_id, s.numero_telefono as numero, c.tipo_mensaje as tipo_mensaje, c.operador_origen as operador, " +
					" c.id_proceso as id_proceso, nvl(c.numero_secuencial,' ') as numero_secuencial, " + 
					" to_char(c.fecha_registro,'dd/mm/yyyy HH24:mi:ss') as fecha_registro " +
					" from mensaje_sr s, cabecera_mensaje c " +
					" where  " +
					" s.id_interno=c.id_interno and c.operador_origen !='00' and s.numero_telefono='"+numero+"' " +
					" order by 1 ";
				
			}
						
			log.info(" sql " + sql ) ;

		try {
			con = JdbcUtilitario.getInstance().getConnection();
			stmt = con.prepareStatement(sql);
			resultSet = stmt.executeQuery();
			
			while (resultSet.next()) {
				bean = new SolicitudVO();
				
				//ID_INTERNO,TRACK_ID,NUMERO,TIPO_MJE,OPE_ORIGEN,ID_PROCESO,NRO_SECUENCIAL,FEC_REGISTRO
				
				bean.setIdInterno(resultSet.getString(1));
				bean.setTrackId(resultSet.getString(2));
				bean.setNumero(resultSet.getString(3));
				bean.setTipoMensaje(resultSet.getString(4));
				bean.setOperadorOrigen(resultSet.getString(5));

				bean.setIdentificadorProceso(resultSet.getString(6));
				bean.setNumeroSecuencial(resultSet.getString(7));
				bean.setFechaRegistro(resultSet.getString(8));

				rList.add(bean);
					
			}
			
		} catch (Exception e) { 
			throw new DAOException(e.getMessage(), e);
		}
		finally
		{
			try
			{
				con.close();
				stmt.close();
				resultSet.close();
			}catch(SQLException e)
			{
				e.printStackTrace();
				log.error(e);
			}
		}
		return rList ;
	}
	
	

}
