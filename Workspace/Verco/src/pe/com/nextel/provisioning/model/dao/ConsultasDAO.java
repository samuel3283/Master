package pe.com.nextel.provisioning.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleTypes;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.com.nextel.provisioning.framework.conexion.ConexionAPI;
import pe.com.nextel.provisioning.framework.conexion.JdbcUtilitario;
import pe.com.nextel.provisioning.framework.conexion.ParameterCollection;
import pe.com.nextel.provisioning.framework.conexion.QueryUtil;
import pe.com.nextel.provisioning.framework.exception.DAOException;
import pe.com.nextel.provisioning.model.vo.CabeceraArchivoVO;
import pe.com.nextel.provisioning.model.vo.Consulta1VO;
import pe.com.nextel.provisioning.model.vo.Consulta2VO;
import pe.com.nextel.provisioning.model.vo.EstadisticaVO;
import pe.com.nextel.provisioning.model.vo.JobReporteVO;
import pe.com.nextel.provisioning.model.vo.NumeroSolicitadoVO;
import pe.com.nextel.provisioning.model.vo.PortabilidadVO;
import pe.com.nextel.provisioning.model.vo.PortadosVO;
import pe.com.nextel.provisioning.model.vo.PreFacturacionVO;
import pe.com.nextel.provisioning.model.vo.RechazoVO;
import pe.com.nextel.provisioning.util.BundleGeneric;
import pe.com.nextel.provisioning.view.ValueConstants;

public class ConsultasDAO {

	private static final Log log = LogFactory.getLog(ConsultasDAO.class);    

	public ConsultasDAO(){}

	public static List consulta1(String fechaInicio, String fechaFin, String cadena) throws Exception{

		List rList = new ArrayList () ;
		Consulta1VO bean = new Consulta1VO();
		Connection con = null;
		String sql = "";
		if(cadena.equals("0")) {
		sql =
			" SELECT COUNT(*) as TOTAL ,      " +      
			"        DECODE(oo.codigo,20,'Nextel',21,'Claro',22,'Movistar','ABDCP')  operador_origen , " +             
			"        DECODE(od.codigo,20,'Nextel',21,'Claro',22,'Movistar','ABDCP')  operador_destino , " +             
			"        MIN (to_char(FECHA_REGISTRO,'dd/mm/yyyy HH24:mi:ss')) AS FEC_INICIO , " +
			"        MAX (to_char(FECHA_REGISTRO,'dd/mm/yyyy HH24:mi:ss')) AS FEC_FIN , " +
			"        TIPO_MENSAJE " +
			" FROM   cabecera_mensaje cm , " +
			"        operador oo         , " +
			"        operador od          " +
			" WHERE  cm.operador_origen  = oo.codigo and  " +
			"        cm.operador_destino = od.codigo and " +
			"        FECHA_REGISTRO between " + 
			"        TO_DATE('"+fechaInicio+"', 'dd/mm/yyyy HH24:mi:ss') AND " +  
			"        TO_DATE('"+fechaFin+"','dd/mm/yyyy HH24:mi:ss')     AND " +
			"                               ( operador_destino='20'  OR  " +    
			"                                 operador_destino='21'  OR  " +    
			"                                 operador_destino='22'  OR  " +   
			"                                 operador_destino='00'  )   " +
			" GROUP BY TIPO_MENSAJE ,od.codigo, oo.codigo     " +
			" ORDER BY oo.codigo , MIN( to_char(FECHA_REGISTRO,'dd/MM/yyyy HH24:mi:ss') ) , tipo_mensaje  	";
	
		} else {
			
		sql = 
			" SELECT COUNT(*) as TOTAL ,      " +       
			"        DECODE(oo.codigo,20,'Nextel',21,'Claro',22,'Movistar','ABDCP')  operador_origen , " +             
			"        DECODE(od.codigo,20,'Nextel',21,'Claro',22,'Movistar','ABDCP')  operador_destino , " +             
			"        MIN (to_char(FECHA_REGISTRO,'dd/mm/yyyy HH24:mi:ss')) AS FEC_INICIO , " +
			"        MAX (to_char(FECHA_REGISTRO,'dd/mm/yyyy HH24:mi:ss')) AS FEC_FIN , " +
			"        TIPO_MENSAJE " +
			" FROM   cabecera_mensaje cm , " +
			"        operador oo         , " +
			"        operador od          " +
			" WHERE  cm.operador_origen  = oo.codigo and  " +
			"        cm.operador_destino = od.codigo and " +
			"        FECHA_REGISTRO between " + 
			"        TO_DATE('"+fechaInicio+"', 'dd/mm/yyyy HH24:mi:ss') AND " +  
			"        TO_DATE('"+fechaFin+"','dd/mm/yyyy HH24:mi:ss')     AND " +
			"                               ( operador_destino='20'  OR  " +    
			"                                 operador_destino='21'  OR  " +    
			"                                 operador_destino='22'  OR  " +   
			"                                 operador_destino='00'  )   " +
			" 		 AND cm.TIPO_MENSAJE IN "+ cadena + 
			" GROUP BY TIPO_MENSAJE ,od.codigo, oo.codigo     " +
			" ORDER BY oo.codigo , MIN( to_char(FECHA_REGISTRO,'dd/MM/yyyy HH24:mi:ss') ) , tipo_mensaje  	";
		}

		log.info(" sql " + sql ) ;

		try {
			con = JdbcUtilitario.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {

				bean = new Consulta1VO();
				bean.setTotal(resultSet.getString(1)) ;
				bean.setOperador_origen(resultSet.getString(2)) ;
				bean.setOperador_destino(resultSet.getString(3)) ;
				bean.setFecha_inicio(resultSet.getString(4)) ;
				bean.setFecha_fin(resultSet.getString(5)) ;
				bean.setTipo_mensaje(resultSet.getString(6)) ;
				rList.add(bean) ;

			}

			int total = 0 ;
			for ( int ii = 0 ; ii < rList.size() ; ii++ ){
				Consulta1VO	beanTemp = (Consulta1VO)rList.get(ii) ;
				total += Integer.parseInt(beanTemp.getTotal()) ;

			}

			Consulta1VO	beanNew  = new Consulta1VO() ;

			beanNew.setTotal(Integer.toString(total)) ;
			beanNew.setOperador_origen("TOTAL") ;
			beanNew.setOperador_destino("") ;
			beanNew.setFecha_inicio("") ;
			beanNew.setFecha_fin("") ;
			beanNew.setTipo_mensaje("");
			rList.add(beanNew) ;


		} catch (Exception e) { 
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
		return rList ;
	}

	public static List consulta2(String fechaInicio, String fechaFin) throws Exception{

		List rList = new ArrayList () ;
		Consulta2VO bean = new Consulta2VO();
		Connection con = null;
		String sql = 
			" SELECT codigo, descripcion, " +
			" (SELECT count(*) " + 
			" FROM cabecera_mensaje " +
			" WHERE tipo_mensaje='SP' " +
			"  AND fecha_registro between to_date('"+fechaInicio+"','dd/mm/yyyy HH24:mi:ss') " +
			"  AND to_date('"+fechaFin+"','dd/mm/yyyy HH24:mi:ss') " +
			"  AND operador_origen=codigo) as CANT_DE_SP, " +
			" (SELECT count(*) " +
			" FROM cabecera_mensaje " +
			" WHERE tipo_mensaje='ANS' "  +

			" AND fecha_registro between to_date('"+fechaInicio+"','dd/mm/yyyy HH24:mi:ss') " +
			" AND to_date('"+fechaFin+"','dd/mm/yyyy HH24:mi:ss') " +

			" AND operador_destino=codigo) as CANT_DE_ANS, " +
			" (SELECT count(*) " +
			"    FROM cabecera_mensaje " +
			"    WHERE tipo_mensaje='VABDCP' " +
			"    AND fecha_registro between to_date('"+fechaInicio+"','dd/mm/yyyy HH24:mi:ss') " +
			"    AND to_date('"+fechaFin+"','dd/mm/yyyy HH24:mi:ss') " +
			"  AND operador_destino=codigo) as CANT_DE_VABDCP, " +
			//"       (SELECT count(distinct(id_proceso)) " +
			"       (SELECT count(*) " +
			"          FROM cabecera_mensaje " + 
			"	WHERE tipo_mensaje='RABDCP' " +
			"	AND fecha_registro between to_date('"+fechaInicio+"','dd/mm/yyyy HH24:mi:ss') " +
			"   AND to_date('"+fechaFin+"','dd/mm/yyyy HH24:mi:ss') " +
			"   AND operador_destino=codigo) as CANT_DE_RABDCP, " +
			"   (SELECT count(*) " +
			"      FROM cabecera_mensaje "  +
			"      WHERE tipo_mensaje='SVP' " +
			"      AND fecha_registro between to_date('"+fechaInicio+"','dd/mm/yyyy HH24:mi:ss') " +
			"      AND to_date('"+fechaFin+"','dd/mm/yyyy HH24:mi:ss') " +
			"      AND operador_origen=codigo) as CANT_DE_SVP " +
			"  FROM operador " +
			"  WHERE CODIGO NOT IN ('00')  " ;

		log.info(" sql " + sql ) ;

		try {
			con = JdbcUtilitario.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {

				bean = new Consulta2VO();
				bean.setCodigo(resultSet.getString(1)) ;
				bean.setOperador(resultSet.getString(2)) ;
				bean.setCANT_DE_SP(resultSet.getString(3)) ;
				bean.setCANT_DE_ANS(resultSet.getString(4)) ;
				bean.setCANT_DE_VABDCP(resultSet.getString(5)) ;
				bean.setCANT_DE_RABDCP(resultSet.getString(6)) ;
				bean.setCANT_DE_SVP(resultSet.getString(7)) ;
				rList.add(bean) ;

			}
			int sp     = 0 ;
			int ans    = 0 ;
			int vabdcp = 0 ;
			int rabdcp = 0 ;
			int svp    = 0 ;

			for (  int ii = 0 ; ii < rList.size() ; ii++ ){ 

				Consulta2VO beanTemp = (Consulta2VO)rList.get(ii) ;
				sp+= Integer.parseInt(beanTemp.getCANT_DE_SP());
				ans+= Integer.parseInt(beanTemp.getCANT_DE_ANS());
				vabdcp+= Integer.parseInt(beanTemp.getCANT_DE_VABDCP());
				rabdcp+= Integer.parseInt(beanTemp.getCANT_DE_RABDCP());
				svp+= Integer.parseInt(beanTemp.getCANT_DE_SVP());

			}

			Consulta2VO beanNew = new Consulta2VO() ;

			beanNew.setCodigo("TOTAL") ;
			beanNew.setOperador("") ;
			beanNew.setCANT_DE_SP(Integer.toString(sp)) ;
			beanNew.setCANT_DE_ANS(Integer.toString(ans)) ;
			beanNew.setCANT_DE_VABDCP(Integer.toString(vabdcp)) ;
			beanNew.setCANT_DE_RABDCP(Integer.toString(rabdcp)) ;
			beanNew.setCANT_DE_SVP(Integer.toString(svp));

			rList.add(beanNew) ;


		} catch (Exception e) { 
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
		return rList ;
	}

	
	public static List consultaMensajes() throws Exception {

		List rList = new ArrayList () ;
		List<Consulta2VO> lista = new ArrayList<Consulta2VO>();
		Consulta2VO bean = new Consulta2VO();
		Connection con = null;
		String sql = " SELECT CODIGO, DESCRIPCION FROM TIPO_MENSAJE " ;
		log.info(" sql " + sql ) ;

		try {
			con = JdbcUtilitario.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {

				bean = new Consulta2VO();
				bean.setCodigo(resultSet.getString(1)) ;
				bean.setOperador(resultSet.getString(2)) ;
				rList.add(bean) ;

			}

			for (  int ii = 0 ; ii < rList.size() ; ii++ ){ 

				Consulta2VO beanTemp = (Consulta2VO)rList.get(ii) ;
				lista.add(beanTemp);
			}


		} catch (Exception e) { 
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
		return rList ;
	}
	
	
	public static List consulta3(String fecha ) throws Exception{

		List rList = new ArrayList () ;
		List rListTemp = new ArrayList () ;
		PortabilidadVO bean = new PortabilidadVO();
		Connection con = null;
		String sql = 

			" select oo.codigo , oo.descripcion, cm.tipo_mensaje , count(*) " +
			" from cabecera_mensaje cm, mensaje_sp sp , operador oo  " +
			" where cm.operador_origen = oo.codigo and " +
			" cm.id_interno = sp.id_interno " + 
			" and cm.fecha_registro between to_date('"+ fecha +"00:00:00','dd/mm/yyyy HH24:mi:ss') and " + 
			" to_date('"+ fecha +"23:59:59','dd/mm/yyyy HH24:mi:ss') " +
			" group by tipo_mensaje, oo.descripcion , oo.codigo " +
			" order by oo.codigo "  ;


		String sql1 = 

			" select oo.codigo , oo.descripcion , cm.tipo_mensaje,  count(*) " +
			" from cabecera_mensaje cm, mensaje_sp_numeracion sp ,  operador oo " +
			" where  cm.operador_origen = oo.codigo and " +
			"        cm.id_interno = sp.id_interno " +
			"        and cm.fecha_registro between to_date('"+ fecha +"00:00:00','dd/mm/yyyy HH24:mi:ss') and" + 
			" to_date('"+ fecha +"23:59:59','dd/mm/yyyy HH24:mi:ss') " +
			" group by tipo_mensaje, oo.descripcion , oo.codigo "+
			" order by oo.codigo " ;

		log.info(" sql  " + sql ) ;
		log.info(" sql1 " + sql1 ) ;

		try {
			con = JdbcUtilitario.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet resultSet = stmt.executeQuery();
			while (resultSet.next()) {

				bean = new PortabilidadVO();
				bean.setCodigo(resultSet.getString(1)) ;
				bean.setDescripcion(resultSet.getString(2)) ;
				bean.setTipo_mensaje(resultSet.getString(3)) ;
				bean.setTotalMensajes(resultSet.getString(4)) ;
				rList.add(bean) ;

			}
			stmt = con.prepareStatement(sql1);
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				PortabilidadVO beanT = new PortabilidadVO () ;
				beanT.setTotalLineas(resultSet.getString(4)) ;
				rListTemp.add(beanT) ;

			}
			int  suma1 = 0;
			int  suma2 = 0;
			for(int ii = 0 ; ii < rList.size() ; ii++ ){
				bean = (PortabilidadVO ) rList.get(ii);
				for  ( int jj = 0 ; jj < rListTemp.size() ; jj++ ){
					if (ii==jj){
						PortabilidadVO beanTemp = (PortabilidadVO)rListTemp.get(ii) ;
						bean.setTotalLineas(beanTemp.getTotalLineas()) ;
						rList.set(ii, bean ) ;
						break;
					}
				}
				suma2+=Integer.parseInt(bean.getTotalLineas());
				suma1+=Integer.parseInt(bean.getTotalMensajes());
			}

			bean = new PortabilidadVO();
			bean.setDescripcion("TOTAL") ;
			bean.setCodigo("");
			bean.setTipo_mensaje("");
			bean.setTotalMensajes(Integer.toString(suma1));
			bean.setTotalLineas(Integer.toString(suma2));
			rList.add(bean);

		} catch (Exception e) { 
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
		return rList ;
	}


	public static List consulta4(String fecha) throws Exception{

		List rList = new ArrayList () ;
		List rListTemp = new ArrayList () ;
		PortabilidadVO bean = new PortabilidadVO();
		Connection con = null;
		String sql = 

			"	select oo.codigo , oo.descripcion ,  cm.tipo_mensaje , count(*) " +
			"	from cabecera_mensaje cm, mensaje_sp sp , operador oo " +
			"	where  cm.operador_origen = oo.codigo and " +
			"	       cm.id_interno = sp.id_interno " +
			"   and cm.fecha_registro <= to_date('"+ fecha +"23:59:59','dd/mm/yyyy HH24:mi:ss') " +
			"	group by tipo_mensaje, oo.descripcion ,oo.codigo " +
			"	order by oo.codigo "  ;

		String sql1 = 

			" select  oo.codigo , oo.descripcion , cm.tipo_mensaje, count(*) " +
			" from cabecera_mensaje cm, mensaje_sp_numeracion sp , operador oo " +
			" where cm.operador_origen = oo.codigo and " +
			"      cm.id_interno = sp.id_interno " +
			"   and cm.fecha_registro <= to_date('"+ fecha +"23:59:59','dd/mm/yyyy HH24:mi:ss') " +
			" group by tipo_mensaje, oo.descripcion ,oo.codigo " + 
			" order by oo.codigo " ;

		log.info(" sql  " + sql ) ;
		log.info(" sql1 " + sql1 ) ;

		try {
			con = JdbcUtilitario.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet resultSet = stmt.executeQuery();
			while (resultSet.next()) {

				bean = new PortabilidadVO();
				bean.setCodigo(resultSet.getString(1)) ;
				bean.setDescripcion(resultSet.getString(2)) ;
				bean.setTipo_mensaje(resultSet.getString(3)) ;
				bean.setTotalMensajes(resultSet.getString(4)) ;
				rList.add(bean) ;

			}
			stmt = con.prepareStatement(sql1);
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				PortabilidadVO beanT = new PortabilidadVO () ;
				beanT.setTotalLineas(resultSet.getString(4)) ;
				rListTemp.add(beanT) ;

			}
			int  suma1 = 0;
			int  suma2 = 0;
			for(int ii = 0 ; ii < rList.size() ; ii++ ){
				bean = (PortabilidadVO ) rList.get(ii);
				for  ( int jj = 0 ; jj < rListTemp.size() ; jj++ ){
					if (ii==jj){
						PortabilidadVO beanTemp = (PortabilidadVO)rListTemp.get(ii) ;
						bean.setTotalLineas(beanTemp.getTotalLineas()) ;
						rList.set(ii, bean ) ;
						break;
					}
				}
				suma2+=Integer.parseInt(bean.getTotalLineas());
				suma1+=Integer.parseInt(bean.getTotalMensajes());
			}

			bean = new PortabilidadVO();
			bean.setDescripcion("TOTAL") ;
			bean.setCodigo("");
			bean.setTipo_mensaje("");
			bean.setTotalMensajes(Integer.toString(suma1));
			bean.setTotalLineas(Integer.toString(suma2));
			rList.add(bean);

		} catch (Exception e) { 
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
		return rList ;
	}


	public static Map rechazosUnicos(String fechaI, String fechaF) throws Exception{

		List rList = new ArrayList () ;
		Map parametros = new HashMap();
		PortadosVO bean = new PortadosVO();
		Connection con = null;
		log.info("DAO rechazosUnicos:::==>INI:"+fechaI+"==>FIN:"+fechaF);
		
		String sql = 
		" SELECT (SELECT count(distinct(ID_PROCESO)) AS RECHAZOS" +
		" FROM CABECERA_MENSAJE C, MENSAJE_RABDCP M " + 
		" WHERE C.TIPO_MENSAJE='RABDCP' " +
		" AND C.ID_INTERNO=M.ID_INTERNO " +
		" AND C.FECHA_REGISTRO BETWEEN  " +
		" TO_DATE('"+fechaI+"','dd/mm/yyyy HH24:mi:ss') AND " +
		" TO_DATE('"+fechaF+"','dd/mm/yyyy HH24:mi:ss')), " +

		" (SELECT count(distinct(ID_PROCESO)) AS SUBSANBLES" +
		" FROM CABECERA_MENSAJE C, MENSAJE_RABDCP M " + 
		" WHERE C.TIPO_MENSAJE='RABDCP' " +
		" AND C.ID_INTERNO=M.ID_INTERNO " +
		" AND M.CAUSA_RECHAZO NOT IN ('REC01ABD01','REC01ABD03','REC01ABD05') " +
		" AND C.FECHA_REGISTRO BETWEEN  " +
		" TO_DATE('"+fechaI+"','dd/mm/yyyy HH24:mi:ss') AND " +
		" TO_DATE('"+fechaF+"','dd/mm/yyyy HH24:mi:ss')), " +		
		
		" (SELECT count(distinct(ID_PROCESO)) AS NOSUBSANBLES" +
		" FROM CABECERA_MENSAJE C, MENSAJE_RABDCP M " + 
		" WHERE C.TIPO_MENSAJE='RABDCP' " +
		" AND C.ID_INTERNO=M.ID_INTERNO " +
		" AND M.CAUSA_RECHAZO IN ('REC01ABD01','REC01ABD03','REC01ABD05') " +
		" AND C.FECHA_REGISTRO BETWEEN  " +
		" TO_DATE('"+fechaI+"','dd/mm/yyyy HH24:mi:ss') AND " +
		" TO_DATE('"+fechaF+"','dd/mm/yyyy HH24:mi:ss')) " +
		" FROM DUAL ";

		log.info(" sql rechazosUnicos " + sql ) ;

		try {
			con = JdbcUtilitario.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {

				bean = new PortadosVO();
				parametros.put("rechazoTotal", resultSet.getString(1));
				parametros.put("rechazoSubsanable", resultSet.getString(2));
				parametros.put("rechazoNoSubsanable", resultSet.getString(3));
				rList.add(bean) ;
				log.info(" .Rechazo  Total: " + parametros.get("rechazoTotal")) ;
				log.info(" .Rechazo    Sub: " + parametros.get("rechazoSubsanable")) ;
				log.info(" .Rechazo No Sub: " + parametros.get("rechazoNoSubsanable")) ;

			}
		} catch (Exception e) { 
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
		return parametros ;
	}
	
	
	

	public static List rechazo(String fechaInicio, String fechaFin) throws Exception{

		List rList = new ArrayList () ;
		RechazoVO bean = new RechazoVO();
		Connection con = null;
		String sql = 

			" SELECT DISTINCT(causa_rechazo) rechazo, cantidad, " +
			" (select descripcion from causas_rechazo where codigo=causa_rechazo) descripcion "+ 
			" FROM " +
			"     ( SELECT r.causa_rechazo, count(*) AS cantidad " +
			"       FROM CABECERA_MENSAJE m,mensaje_rabdcp r " +
			"       where m.FECHA_REGISTRO between to_date('"+fechaInicio+"','dd/mm/yyyy HH24:mi:ss') " +
			"             AND to_date('"+fechaFin+"','dd/mm/yyyy HH24:mi:ss') " +
			"             AND m.tipo_mensaje='RABDCP' " +
			"             AND r.id_interno=m.id_interno " +

			" group by r.causa_rechazo " +
			" 	) temp "  +
			"	order by causa_rechazo " 

			;

		
		String sql2 = 
		" SELECT " +
		" causa_rechazo, receptor, cedente, count(*) as cantidad, " +
		" (select descripcion from causas_rechazo where codigo=causa_rechazo) descripcion " +  
			" FROM " +
			" (SELECT r.causa_rechazo, m.id_proceso, m.operador_destino as receptor, " +  
			" (select OPERADOR_DONANTE from mensaje_sp where id_interno = " +
			" (select min(id_interno) from cabecera_mensaje where numero_secuencial=m.numero_secuencial)) " +
			" AS cedente  " +
			" FROM CABECERA_MENSAJE m,mensaje_rabdcp r " +        
			" where m.FECHA_REGISTRO between " + 
			" to_date('"+fechaInicio+"','dd/mm/yyyy HH24:mi:ss') AND " + 
			" to_date('"+fechaFin+"','dd/mm/yyyy HH24:mi:ss') " +         
			" AND m.tipo_mensaje='RABDCP' " +
			" AND r.id_interno=m.id_interno) " +
			" group by causa_rechazo,receptor,cedente " +
			" order by causa_rechazo,receptor,cedente";
		
		log.info(" sql rechazo " + sql ) ;

		try {
			con = JdbcUtilitario.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {

				bean = new RechazoVO();
				/*
				bean.setCodigo(resultSet.getString(1)) ;
				bean.setReceptor(resultSet.getString(2));
				bean.setCedente(resultSet.getString(3));
				bean.setCantidad(resultSet.getString(4)) ;
				bean.setDescripcion(resultSet.getString(5)) ;*/
				bean.setCodigo(resultSet.getString(1)) ;
				bean.setCantidad(resultSet.getString(2));
				bean.setDescripcion(resultSet.getString(3));
				rList.add(bean) ;

			}

			int cantidad = 0 ;
			for ( int ii = 0 ; ii < rList.size() ; ii++){

				RechazoVO	beanTemp = (RechazoVO)rList.get(ii) ;

				cantidad += Integer.parseInt(beanTemp.getCantidad()) ;


			}

			RechazoVO	beanNew = new  RechazoVO() ;

			beanNew.setCantidad(Integer.toString(cantidad)) ;
			beanNew.setCodigo("TOTAL") ;
			beanNew.setDescripcion("") ;

			rList.add(beanNew) ;


		} catch (Exception e) { 
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
		return rList ;
	}


	public static Map portadosMesAnterior(String fechaI, String fechaF) throws Exception{

		List rList = new ArrayList () ;
		Map parametros = new HashMap();
		PortadosVO bean = new PortadosVO();
		Connection con = null;
		log.info("DAO portadosMesAnterior:::==>INI:"+fechaI+"==>FIN:"+fechaF);
		
		String sql = 
		" SELECT CANTIDAD, " +
		" (select to_char(add_months(to_date('"+fechaI+"','dd/mm/yyyy HH24:mi:ss') ,-1),'Month yyyy') from dual) " +
		" FROM (SELECT count(*) AS CANTIDAD " +
		" FROM NUMERACION_PORTADA " +
		" WHERE ESTADO='01' " +
		" AND INICIO_VENTANA " +
		" BETWEEN TO_DATE('"+fechaI+"','dd/mm/yyyy HH24:mi:ss') " +
		" AND TO_DATE('"+fechaF+"','dd/mm/yyyy HH24:mi:ss') " +
		" AND to_char(INICIO_PROCESO,'mmyyyy') = " +
		" (select to_char(add_months(to_date('"+fechaI+"','dd/mm/yyyy HH24:mi:ss') ,-1),'mmyyyy') from dual))temp" +
		" "; 

		log.info(" sql portadosMesAnterior " + sql ) ;

		try {
			con = JdbcUtilitario.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {

				bean = new PortadosVO();
				parametros.put("cantidadPortados", resultSet.getString(1));
				parametros.put("valorPortados", resultSet.getString(2));
				rList.add(bean) ;

			}
		} catch (Exception e) { 
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
		return parametros ;
	}
	
	public static List portados(String fechaI, String fechaF) throws Exception{

		List rList = new ArrayList () ;
		PortadosVO bean = new PortadosVO();
		Connection con = null;
		log.info("DAO portados:::==>INI:"+fechaI+"==>FIN:"+fechaF);
		
		String sql = 

			" select O.CODIGO, O.DESCRIPCION AS OPERADOR, " +
			" (select count(*) from numeracion_portada where estado='01' " + 
			" and receptor=O.CODIGO " +
			" and INICIO_VENTANA between to_date('"+fechaI+"','dd/mm/yyyy HH24:mi:ss') " + 
			" and to_date('"+fechaF+"','dd/mm/yyyy HH24:mi:ss')) AS PORTADOS " +
			" from OPERADOR O " + 
			" where O.CODIGO in ('20','21','22') " +
			" UNION " +
			" select 'TOTAL' AS CODIGO, ' ' AS OPERADOR, " + 
			" (select count(*) from numeracion_portada where estado='01' " +
			" and INICIO_VENTANA between to_date('"+fechaI+"','dd/mm/yyyy HH24:mi:ss')  " +
			" and to_date('"+fechaF+"','dd/mm/yyyy HH24:mi:ss')) AS PORTADOS " +
			" from dual " ; 


		log.info(" sql portados " + sql ) ;

		try {
			con = JdbcUtilitario.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {

				bean = new PortadosVO();
				bean.setCodigo(resultSet.getString(1)) ;
				bean.setOperador(resultSet.getString(2)) ;
				bean.setPortados(resultSet.getString(3)) ;
				rList.add(bean) ;

			}
		} catch (Exception e) { 
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
		return rList ;
	}

	public static List numeroSolicitados(String fechaI, String fechaF) throws Exception{

		List rList = new ArrayList () ;
		NumeroSolicitadoVO bean = new NumeroSolicitadoVO();
		Connection con = null;
		String sql1= 
		" select cm.operador_origen, " +
		" DECODE(sp.tipo_portabilidad,'01','PRE-PAGO','02','POST-PAGO','N/A'), " +
		" count(tipo_portabilidad) " +
		" from cabecera_mensaje cm, mensaje_sp_numeracion sp " + 
		" where cm.id_interno = sp.id_interno  " +
		" and cm.fecha_registro >= to_date('"+fechaI+"','dd/mm/yyyy HH24:mi:ss') " +
		" and cm.fecha_registro <= to_date('"+fechaF+"','dd/mm/yyyy HH24:mi:ss') " +
		" group by tipo_portabilidad, operador_origen  " +
		" order by operador_origen ";
			
		String sql = 
			" select cm.operador_origen, " +
			" DECODE(sp.tipo_portabilidad,'01','PRE-PAGO','02','POST-PAGO','N/A'), " +
			" count(sp.tipo_portabilidad) " +
			" from cabecera_mensaje cm, mensaje_sp_numeracion sp, cabecera_mensaje ca, mensaje_ans ma " + 
			" where cm.id_interno = sp.id_interno  " +
			" and cm.fecha_registro >= to_date('"+fechaI+"','dd/mm/yyyy HH24:mi:ss') " +
			" and cm.fecha_registro <= to_date('"+fechaF+"','dd/mm/yyyy HH24:mi:ss') " +
			" and ca.id_interno=ma.id_interno " +
			" and ca.numero_secuencial=cm.numero_secuencial " +
			" and ca.tipo_mensaje='ANS' " +
			" and ma.numero_telefono=sp.inicio_rango " +
			" group by sp.tipo_portabilidad, cm.operador_origen  " +
			" order by cm.operador_origen ";
				
			
		
		
			/*
			"	select  cm.operador_origen , cm.tipo_mensaje,  count(distinct sp.inicio_rango) " +
			"	from cabecera_mensaje cm, mensaje_sp_numeracion sp " +
			"	where cm.id_interno = sp.id_interno " +
			"	group by tipo_mensaje, operador_origen " ; 
			 */

		log.info(" sql numeroSolicitados " + sql ) ;

		try {
			con = JdbcUtilitario.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {

				bean = new NumeroSolicitadoVO();
				bean.setOperador_origen(resultSet.getString(1)) ;
				bean.setTipo_mensaje(resultSet.getString(2)) ;
				bean.setTotal(resultSet.getString(3)) ;
				rList.add(bean) ;

			}
		} catch (Exception e) { 
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
		
		int cantidad = 0 ;
		for ( int ii = 0 ; ii < rList.size() ; ii++){
			NumeroSolicitadoVO	beanTemp = (NumeroSolicitadoVO)rList.get(ii) ;
			cantidad += Integer.parseInt(beanTemp.getTotal()) ;
		}

		NumeroSolicitadoVO	beanNew = new  NumeroSolicitadoVO() ;

		beanNew.setTotal(Integer.toString(cantidad)) ;
		beanNew.setOperador_origen("TOTAL") ;
		beanNew.setTipo_mensaje("") ;

		rList.add(beanNew) ;
			
		return rList ;
	} 

	
	
	
	
	public static List consultaFacturacion(String fechaInicio, String fechaFin) throws Exception{

		List rList = new ArrayList() ;
		Consulta1VO bean = new Consulta1VO();
		Connection con = null;
		String sql = "";

		sql =
			" SELECT M.NUMERO_TELEFONO, " +
			" (select operador_receptor from mensaje_sp where id_interno=(SELECT max(ID_INTERNO) FROM CABECERA_MENSAJE WHERE NUMERO_SECUENCIAL=C.NUMERO_SECUENCIAL AND TIPO_MENSAJE='SP')) RECEPTOR, " +
			" (select operador_donante from mensaje_sp where id_interno=(SELECT max(ID_INTERNO) FROM CABECERA_MENSAJE WHERE NUMERO_SECUENCIAL=C.NUMERO_SECUENCIAL AND TIPO_MENSAJE='SP')) CEDENTE, " +
			" C.NUMERO_SECUENCIAL, C.ID_PROCESO, to_char(C.FECHA_REGISTRO,'dd/mm/yyyy HH24:mi:ss') AS FECHA_REGISTRO, " +
			" C.tipo_mensaje, " +
			" (case when C.operador_destino = '22' then " +
			"       (select tipo_mensaje from CABECERA_MENSAJE where id_interno= " +
			"       NVL((select max(ID_INTERNO) from MENSAJE_EABDCP where ID_PROCESO_ERROR=(select max(ID_PROCESO) from CABECERA_MENSAJE where ID_PROCESO=C.ID_PROCESO)), (select ID_INTERNO from CABECERA_MENSAJE where id_interno=(select max(id_interno) from CABECERA_MENSAJE where ID_PROCESO=C.ID_PROCESO)))) " +
			"    else  " +
			"    (select tipo_mensaje from CABECERA_MENSAJE where id_interno=(select max(id_interno) from CABECERA_MENSAJE where NUMERO_SECUENCIAL=C.NUMERO_SECUENCIAL)) " + 
			" end) AS ULT_MJE " +
			" FROM CABECERA_MENSAJE C, MENSAJE_VABDCP M WHERE " +
			" C.FECHA_REGISTRO BETWEEN TO_DATE('"+fechaInicio+"','ddmmyyyy HH24miss') " +
			" AND TO_DATE('"+fechaFin+"','ddmmyyyy HH24miss') " +
			" AND TIPO_MENSAJE='VABDCP' AND C.ID_INTERNO=M.ID_INTERNO " +
			" order by c.id_interno ";
			
			log.info(" sql " + sql ) ;

		try {
			con = JdbcUtilitario.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {

				bean = new Consulta1VO();
				bean.setTelefono(resultSet.getString(1));
				bean.setReceptor(resultSet.getString(2));
				bean.setCedente(resultSet.getString(3));
				bean.setNumeroSecuencial(resultSet.getString(4));
				bean.setIdentificadorProceso(resultSet.getString(5));
				bean.setFechaRegistro(resultSet.getString(6));
				bean.setTipo_mensaje(resultSet.getString(7));
				bean.setUltimoMensaje(resultSet.getString(8));
				
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
			}catch(SQLException e)
			{
				e.printStackTrace();
				log.error(e);
			}
		}
		return rList ;
	}
	
	
	
	public static List consultaOCC(String fechaInicio, String fechaFin) throws Exception{

		List rList = new ArrayList() ;
		Consulta1VO bean = new Consulta1VO();
		Connection con = null;
		String sql = "";

		sql =
			" SELECT M.NUMERO_TELEFONO, " +
			" (select operador_receptor from mensaje_sp where id_interno=(SELECT max(ID_INTERNO) FROM CABECERA_MENSAJE WHERE NUMERO_SECUENCIAL=C.NUMERO_SECUENCIAL AND TIPO_MENSAJE='SP')) RECEPTOR, " +
			" (select operador_donante from mensaje_sp where id_interno=(SELECT max(ID_INTERNO) FROM CABECERA_MENSAJE WHERE NUMERO_SECUENCIAL=C.NUMERO_SECUENCIAL AND TIPO_MENSAJE='SP')) CEDENTE, " +
			" C.NUMERO_SECUENCIAL, C.ID_PROCESO, to_char(C.FECHA_REGISTRO,'dd/mm/yyyy HH24:mi:ss') AS FECHA_REGISTRO, " +
			" C.tipo_mensaje,  " +
			" (select tipo_mensaje from CABECERA_MENSAJE where id_interno=(select max(id_interno) from CABECERA_MENSAJE where ID_PROCESO=C.ID_PROCESO AND TIPO_MENSAJE in ('OPC','SPR'))) " +
			" AS RESPUESTA, " +
			" (case when   " +
			"  (select operador_receptor from mensaje_sp where id_interno=(SELECT max(ID_INTERNO) FROM CABECERA_MENSAJE WHERE NUMERO_SECUENCIAL=C.NUMERO_SECUENCIAL AND TIPO_MENSAJE='SP')) " +
			"    = '22' then  " +
			"   (select tipo_mensaje from CABECERA_MENSAJE where id_interno= " +
			"   NVL((select max(ID_INTERNO) from MENSAJE_EABDCP where ID_PROCESO_ERROR=(select max(ID_PROCESO) from CABECERA_MENSAJE where ID_PROCESO=C.ID_PROCESO)), (select ID_INTERNO from CABECERA_MENSAJE where id_interno=(select max(id_interno) from CABECERA_MENSAJE where ID_PROCESO=C.ID_PROCESO)))) " +
			"   else  " +
			"   (select tipo_mensaje from CABECERA_MENSAJE where id_interno=(select max(id_interno) from CABECERA_MENSAJE where NUMERO_SECUENCIAL=C.NUMERO_SECUENCIAL)) " + 
			" end) AS ULT_MJE " +
			" FROM CABECERA_MENSAJE C, MENSAJE_OCC M WHERE " +
			" C.FECHA_REGISTRO BETWEEN TO_DATE('"+fechaInicio+"','ddmmyyyy HH24miss') " +
			" AND TO_DATE('"+fechaFin+"','ddmmyyyy HH24miss') " +
			" AND TIPO_MENSAJE='OCC' AND C.ID_INTERNO=M.ID_INTERNO " +
			" order by c.id_interno ";
			
			log.info(" sql " + sql ) ;

		try {
			con = JdbcUtilitario.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {

				bean = new Consulta1VO();
				bean.setTelefono(resultSet.getString(1));
				bean.setReceptor(resultSet.getString(2));
				bean.setCedente(resultSet.getString(3));
				bean.setNumeroSecuencial(resultSet.getString(4));
				bean.setIdentificadorProceso(resultSet.getString(5));
				bean.setFechaRegistro(resultSet.getString(6));
				bean.setTipo_mensaje(resultSet.getString(7));
				bean.setRespuesta(resultSet.getString(8));
				bean.setUltimoMensaje(resultSet.getString(9));
				
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
			}catch(SQLException e)
			{
				e.printStackTrace();
				log.error(e);
			}
		}
		return rList ;
	}
		
	
	public static List consultaRechazo(String fechaInicio, String fechaFin) throws Exception{

		List rList = new ArrayList() ;
		Consulta1VO bean = new Consulta1VO();
		Connection con = null;
		String sql = "";

		sql =
			" SELECT M.NUMERO_TELEFONO, " +
			" (select operador_receptor from mensaje_sp where id_interno=(SELECT max(ID_INTERNO) FROM CABECERA_MENSAJE WHERE NUMERO_SECUENCIAL=C.NUMERO_SECUENCIAL AND TIPO_MENSAJE='SP')) RECEPTOR, " +
			" (select operador_donante from mensaje_sp where id_interno=(SELECT max(ID_INTERNO) FROM CABECERA_MENSAJE WHERE NUMERO_SECUENCIAL=C.NUMERO_SECUENCIAL AND TIPO_MENSAJE='SP')) CEDENTE, " +
			" C.NUMERO_SECUENCIAL, C.ID_PROCESO, to_char(C.FECHA_REGISTRO,'dd/mm/yyyy HH24:mi:ss') AS FECHA_REGISTRO, " +
			" (SELECT CAUSA_RECHAZO FROM MENSAJE_RABDCP WHERE ID_INTERNO=(SELECT MAX(CM.ID_INTERNO) FROM CABECERA_MENSAJE CM WHERE CM.TIPO_MENSAJE='RABDCP' AND CM.ID_PROCESO=C.ID_PROCESO)) AS CAUSA, " +
			" 'RABDCP' AS TIPO_MENSAJE, " +
			" (case " +
			"   when C.operador_destino = '22' then " + 
			"  (select tipo_mensaje from CABECERA_MENSAJE where id_interno= " +
			"   NVL((select max(ID_INTERNO) from MENSAJE_EABDCP where ID_PROCESO_ERROR=(select max(ID_PROCESO) from CABECERA_MENSAJE where ID_PROCESO=C.ID_PROCESO)), (select ID_INTERNO from CABECERA_MENSAJE where id_interno=(select max(id_interno) from CABECERA_MENSAJE where ID_PROCESO=C.ID_PROCESO)))) " +
			"   else  " +
			"  (select tipo_mensaje from CABECERA_MENSAJE where id_interno=(select max(id_interno) from CABECERA_MENSAJE where NUMERO_SECUENCIAL=C.NUMERO_SECUENCIAL)) " + 
			" end) AS ULT_MJE " +
			" FROM CABECERA_MENSAJE C, MENSAJE_ANS M " + 
			" WHERE  " +
			" C.TIPO_MENSAJE='ANS' " +
			" AND C.ID_INTERNO=M.ID_INTERNO " +
			" AND C.ID_PROCESO IN " +
			" (SELECT distinct(ID_PROCESO)  " +
			" FROM CABECERA_MENSAJE C, MENSAJE_RABDCP M " + 
			" WHERE C.TIPO_MENSAJE='RABDCP' " +
			" AND C.ID_INTERNO=M.ID_INTERNO " +
			" AND M.CAUSA_RECHAZO NOT IN ('REC01ABD01','REC01ABD03','REC01ABD05') " +
			" AND C.FECHA_REGISTRO BETWEEN " +
			" to_date('"+fechaInicio+"','ddmmyyyy HH24miss') AND " +
			" to_date('"+fechaFin+"','ddmmyyyy HH24miss')) " +
			" ORDER BY C.ID_PROCESO ";
			
			log.info(" sql " + sql ) ;

		try {
			con = JdbcUtilitario.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {

				bean = new Consulta1VO();
				bean.setTelefono(resultSet.getString(1));
				bean.setReceptor(resultSet.getString(2));
				bean.setCedente(resultSet.getString(3));
				bean.setNumeroSecuencial(resultSet.getString(4));
				bean.setIdentificadorProceso(resultSet.getString(5));
				bean.setFechaRegistro(resultSet.getString(6));
				bean.setCausaRechazo(resultSet.getString(7));
				bean.setTipo_mensaje(resultSet.getString(8));
				bean.setUltimoMensaje(resultSet.getString(9));
				
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
			}catch(SQLException e)
			{
				e.printStackTrace();
				log.error(e);
			}
		}
		return rList ;
	}
		
	
	
	
	public static List consultaPortados(String fechaInicio, String fechaFin) throws Exception{

		List rList = new ArrayList() ;
		Consulta1VO bean = new Consulta1VO();
		Connection con = null;
		String sql = "";
		
		sql =
			" SELECT NUMERO, DONANTE, RECEPTOR, DONANTE_INICIAL, ULTIMO_PROCESO, " +
			" NUMERO_SECUENCIAL, INICIO_PROCESO, to_char(INICIO_VENTANA,'dd/mm/yyyy HH24:mi:ss') AS INICIO_VENTANA," +
			" ESTADO, TIPO_PORTABILIDAD " +
			" FROM NUMERACION_PORTADA " +
			" WHERE ESTADO='01' " +
			" AND INICIO_VENTANA " +
			" BETWEEN TO_DATE('"+fechaInicio+"','ddmmyyyy HH24miss') " + 
			" AND TO_DATE('"+fechaFin+"','ddmmyyyy HH24miss') " +
			" ORDER BY NUMERO_SECUENCIAL, INICIO_PROCESO ";
			
			log.info(" sql " + sql ) ;

		try {
			con = JdbcUtilitario.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {

				bean = new Consulta1VO();
				bean.setTelefono(resultSet.getString(1));
				bean.setCedente(resultSet.getString(2));
				bean.setReceptor(resultSet.getString(3));
				bean.setCedenteInicial(resultSet.getString(4));
				bean.setIdentificadorProceso(resultSet.getString(5));
				bean.setNumeroSecuencial(resultSet.getString(6));
				
				bean.setFechaProceso(resultSet.getString(7));
				bean.setFechaVentana(resultSet.getString(8));
				
				bean.setEstado(resultSet.getString(9));
				bean.setTipoPortabilidad(resultSet.getString(10));
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
			}catch(SQLException e)
			{
				e.printStackTrace();
				log.error(e);
			}
		}
		return rList ;
	}
		
	
	
	
	
	
	

	public static List consultaMensajesPep(String fechaInicio, String fechaFin) throws Exception{

		List rList = new ArrayList() ;
		Consulta1VO bean = new Consulta1VO();
		Connection con = null;
		String sql = "";
		PreparedStatement stmt = null;
		ResultSet resultSet = null;

		sql =" SELECT " +
			" (select operador_receptor from mensaje_sp where id_interno=(SELECT max(ID_INTERNO) FROM CABECERA_MENSAJE WHERE NUMERO_SECUENCIAL=C.NUMERO_SECUENCIAL AND TIPO_MENSAJE='SP')) RECEPTOR, " +
			" (select operador_donante from mensaje_sp where id_interno=(SELECT max(ID_INTERNO) FROM CABECERA_MENSAJE WHERE NUMERO_SECUENCIAL=C.NUMERO_SECUENCIAL AND TIPO_MENSAJE='SP')) CEDENTE, " +
			" (SELECT NUMERO_TELEFONO FROM MENSAJE_ANS WHERE ID_INTERNO=C.ID_INTERNO) AS NUMERO, " +
			" C.ID_PROCESO, C.NUMERO_SECUENCIAL, " +
			" to_char(C.FECHA_REGISTRO,'dd/mm/yyyy HH24:mi:ss') AS FECHA_REGISTRO, " +
			" (case " +
			"     when C.operador_destino = '22' then " + 
			"     (select tipo_mensaje from CABECERA_MENSAJE where id_interno= " +
			"     NVL((select max(ID_INTERNO) from MENSAJE_EABDCP where CODIGO_ERROR='ERROR00003' AND ID_PROCESO_ERROR=(select max(ID_PROCESO) from CABECERA_MENSAJE where ID_PROCESO=C.ID_PROCESO)), (select ID_INTERNO from CABECERA_MENSAJE where id_interno=(select max(id_interno) from CABECERA_MENSAJE where ID_PROCESO=C.ID_PROCESO)))) " +
			"      else  " +
			"     (select tipo_mensaje from CABECERA_MENSAJE where id_interno=(select max(id_interno) from CABECERA_MENSAJE where NUMERO_SECUENCIAL=C.NUMERO_SECUENCIAL)) " + 
			" end) AS ULT_MJE " +
			" FROM CABECERA_MENSAJE C WHERE  " +
			" (case  " +
			"     when C.operador_destino = '22' then " + 
			"    (select tipo_mensaje from CABECERA_MENSAJE where id_interno= " +
			"    NVL((select max(ID_INTERNO) from MENSAJE_EABDCP where CODIGO_ERROR='ERROR00003' AND ID_PROCESO_ERROR=(select max(ID_PROCESO) from CABECERA_MENSAJE where ID_PROCESO=C.ID_PROCESO)), (select ID_INTERNO from CABECERA_MENSAJE where id_interno=(select max(id_interno) from CABECERA_MENSAJE where ID_PROCESO=C.ID_PROCESO)))) " +
			"      else " + 
			"     (select tipo_mensaje from CABECERA_MENSAJE where id_interno=(select max(id_interno) from CABECERA_MENSAJE where NUMERO_SECUENCIAL=C.NUMERO_SECUENCIAL)) " + 
			" end)='EABDCP' AND " +
			" C.TIPO_MENSAJE='ANS' AND " +
			" C.ID_PROCESO IN " +
			" (SELECT DISTINCT(ID_PROCESO) FROM CABECERA_MENSAJE " +
			" WHERE TIPO_MENSAJE='PEP' AND FECHA_REGISTRO BETWEEN " +  
			" to_date('"+fechaInicio+"','ddmmyyyy HH24miss') AND " +
			" to_date('"+fechaFin+"','ddmmyyyy HH24miss') " +
			" GROUP BY ID_PROCESO) "; 
			
			log.info(" sql " + sql ) ;

		try {
			con = JdbcUtilitario.getInstance().getConnection();
			stmt = con.prepareStatement(sql);
			resultSet = stmt.executeQuery();

			while (resultSet.next()) {
				//RECEPTOR,CEDENTE,NUMERO,ID_PROCESO,NRO_SECUENCIAL,FEC_REG,ULT_MJE 
				bean = new Consulta1VO();
				bean.setReceptor(resultSet.getString(1));
				bean.setCedente(resultSet.getString(2));
				bean.setTelefono(resultSet.getString(3));
				bean.setIdentificadorProceso(resultSet.getString(4));
				bean.setNumeroSecuencial(resultSet.getString(5));
				bean.setFechaRegistro(resultSet.getString(6));
				bean.setUltimoMensaje(resultSet.getString(7));
				
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
	
	
	


	public static List consultaMensajesOcc(String fechaInicio, String fechaFin) throws Exception{

		List rList = new ArrayList() ;
		Consulta1VO bean = new Consulta1VO();
		Connection con = null;
		String sql = "";
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		sql =" SELECT M.NUMERO_TELEFONO, " +      
			" (select operador_receptor from mensaje_sp where id_interno=(SELECT max(ID_INTERNO) FROM CABECERA_MENSAJE WHERE NUMERO_SECUENCIAL=C.NUMERO_SECUENCIAL AND TIPO_MENSAJE='SP')) RECEPTOR, " +
			" (select operador_donante from mensaje_sp where id_interno=(SELECT max(ID_INTERNO) FROM CABECERA_MENSAJE WHERE NUMERO_SECUENCIAL=C.NUMERO_SECUENCIAL AND TIPO_MENSAJE='SP')) CEDENTE, " +
			" C.NUMERO_SECUENCIAL, C.ID_PROCESO, to_char(C.FECHA_REGISTRO,'dd/mm/yyyy HH24:mi:ss') AS FECHA_REGISTRO, " +
			" C.tipo_mensaje, " +
			" (select tipo_mensaje from CABECERA_MENSAJE where id_interno=(select max(id_interno) from CABECERA_MENSAJE where ID_PROCESO=C.ID_PROCESO AND TIPO_MENSAJE in ('OPC','SPR'))) " +
			" AS RESPUESTA, " +
			" (case when   " +
			" (select operador_receptor from mensaje_sp where id_interno=(SELECT max(ID_INTERNO) FROM CABECERA_MENSAJE WHERE NUMERO_SECUENCIAL=C.NUMERO_SECUENCIAL AND TIPO_MENSAJE='SP')) " +
			" = '22' then  " +
			" (select tipo_mensaje from CABECERA_MENSAJE where id_interno= " +
			" NVL((select max(ID_INTERNO) from MENSAJE_EABDCP where ID_PROCESO_ERROR=(select max(ID_PROCESO) from CABECERA_MENSAJE where ID_PROCESO=C.ID_PROCESO)), (select ID_INTERNO from CABECERA_MENSAJE where id_interno=(select max(id_interno) from CABECERA_MENSAJE where ID_PROCESO=C.ID_PROCESO)))) " +
			"  else  " +
			" (select tipo_mensaje from CABECERA_MENSAJE where id_interno=(select max(id_interno) from CABECERA_MENSAJE where NUMERO_SECUENCIAL=C.NUMERO_SECUENCIAL)) " + 
			" end) AS ULT_MJE, " +
			
			" to_char((select FECHA_REGISTRO from CABECERA_MENSAJE where id_interno=(select max(id_interno) from CABECERA_MENSAJE where ID_PROCESO=C.ID_PROCESO AND TIPO_MENSAJE in ('OPC','SPR'))),'dd/mm/yyyy HH24:mi:ss') AS FECHA_RESPUESTA " +
			" FROM CABECERA_MENSAJE C, MENSAJE_OCC M WHERE " +
			" (select FECHA_REGISTRO from CABECERA_MENSAJE where id_interno=(select max(id_interno) from CABECERA_MENSAJE where ID_PROCESO=C.ID_PROCESO AND TIPO_MENSAJE in ('OPC','SPR'))) " +
			" BETWEEN " + 
			" to_date('"+fechaInicio+"','ddmmyyyy HH24miss') " +
			" AND to_date('"+fechaFin+"','ddmmyyyy HH24miss') " +
			" AND TIPO_MENSAJE='OCC' AND C.ID_INTERNO=M.ID_INTERNO " +
			" order by c.id_interno ";
		

			log.info(" sql " + sql ) ;

		try {
			con = JdbcUtilitario.getInstance().getConnection();
			stmt = con.prepareStatement(sql);
			resultSet = stmt.executeQuery();
			
			while (resultSet.next()) {
				bean = new Consulta1VO();
				
				//TELEFONO, RECEPTOR,CEDENTE,NRO_SECUENCIAL, ID_PROCESO,
				//FEC_REGISTRO, tipo_mensaje,RESPUESTA ULT_MJE ,
				bean.setTelefono(resultSet.getString(1));
				bean.setReceptor(resultSet.getString(2));
				bean.setCedente(resultSet.getString(3));
				
				bean.setNumeroSecuencial(resultSet.getString(4));
				bean.setIdentificadorProceso(resultSet.getString(5));
				
				bean.setFechaRegistro(resultSet.getString(6));
				bean.setTipo_mensaje(resultSet.getString(7));
				bean.setRespuesta(resultSet.getString(8));
				bean.setUltimoMensaje(resultSet.getString(9));
				bean.setFechaRespuesta(resultSet.getString(10));
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
	
	

	public static List consultaMensajesRec(String fechaInicio, String fechaFin) throws Exception{

		List rList = new ArrayList() ;
		Consulta1VO bean = new Consulta1VO();
		Connection con = null;
		String sql = "";
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		sql =" SELECT M.NUMERO_TELEFONO,M.OPERADOR_RECEPTOR,M.DONANTE_INICIAL,C.ID_PROCESO, " + 
			" to_char(C.FECHA_REGISTRO,'dd/mm/yyyy HH24:mi:ss') AS FECHA_REGISTRO, " +
			" M.MOTIVO_RETORNO, " +
			" (SELECT TIPO_MENSAJE FROM CABECERA_MENSAJE WHERE ID_PROCESO=C.ID_PROCESO AND TIPO_MENSAJE IN ('AR','DR')) RSPTA_RETORNO " +
			" FROM CABECERA_MENSAJE C, MENSAJE_SR M WHERE " +
			" C.ID_INTERNO=M.ID_INTERNO AND " + 
			" C.TIPO_MENSAJE='SR' AND  " +
			" C.FECHA_REGISTRO BETWEEN  " +
			" to_date('"+fechaInicio+"','ddmmyyyy HH24miss') " +
			" AND to_date('"+fechaFin+"','ddmmyyyy HH24miss') " +
			" AND OPERADOR_ORIGEN!='00' " +
			" AND " +
			" (SELECT TIPO_MENSAJE FROM CABECERA_MENSAJE WHERE ID_PROCESO=C.ID_PROCESO AND TIPO_MENSAJE IN ('AR','DR')) IN ('AR','DR') ";
		
			log.info(" sql " + sql ) ;

		try {
			con = JdbcUtilitario.getInstance().getConnection();
			stmt = con.prepareStatement(sql);
			resultSet = stmt.executeQuery();
			
			while (resultSet.next()) {
				bean = new Consulta1VO();
				//NUMERO,rec,ced,id_proceso,fec_reg,motivo,respuesta
				bean.setTelefono(resultSet.getString(1));
				bean.setReceptor(resultSet.getString(2));
				bean.setCedente(resultSet.getString(3));
				bean.setIdentificadorProceso(resultSet.getString(4));
				bean.setFechaRegistro(resultSet.getString(5));
				bean.setMotivoRetorno(resultSet.getString(6));
				bean.setRespuestaRetorno(resultSet.getString(7));
				
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
	
	

	public static List consultaNumerosPortados(String fechaInicio, String fechaFin, String receptor) throws Exception{
		
		List rList = new ArrayList();
		Consulta1VO bean = new Consulta1VO();
		Connection con = null;
		String sql = "";
		PreparedStatement stmt = null;
		ResultSet resultSet = null;

		sql =
			" SELECT N.NUMERO, N.DONANTE, N.RECEPTOR, N.DONANTE_INICIAL, N.ULTIMO_PROCESO, " +  
			" N.NUMERO_SECUENCIAL, to_char(N.INICIO_PROCESO,'dd/mm/yyyy HH24:mi:ss') AS INICIO_PROCESO, to_char(N.INICIO_VENTANA,'dd/mm/yyyy HH24:mi:ss') AS INICIO_VENTANA, " +
			" N.ESTADO, " + 
			" DECODE(N.TIPO_PORTABILIDAD,'01','Pre-Pago','02','Post-Pago','NA') as  TIPO_PORTABILIDAD" +  
			" FROM NUMERACION_PORTADA N, FACTURA_OPERADORES_DETALLE F " + 
			" WHERE N.ESTADO='01' " + 
			" AND N.ULTIMO_PROCESO=F.ID_PROCESO" +
			" AND N.INICIO_VENTANA BETWEEN " +  
			" TO_DATE('"+fechaInicio+"','ddmmyyyy HH24miss') AND " +  
			" TO_DATE('"+fechaFin+"','ddmmyyyy HH24miss') " + 
			" AND N.RECEPTOR ='"+receptor+"' " +
			" ORDER BY N.NUMERO_SECUENCIAL, N.INICIO_PROCESO "; 
		
		/*
		sql = "SELECT NUMERO, DONANTE, RECEPTOR, DONANTE_INICIAL,ULTIMO_PROCESO, NUMERO_SECUENCIAL, " +
			" to_char(INICIO_PROCESO,'dd/mm/yyyy HH24:mi:ss') INICIO_PROCESO,to_char(INICIO_VENTANA,'dd/mm/yyyy HH24:mi:ss') INICIO_VENTANA, " +
			" DURACION_VENTANA, ESTADO, TIMESTAMP_MODIFICACION, DONANTE_PREVIO, TIPO_PORTABILIDAD " +
			" FROM NUMERACION_PORTADA " +
			" ORDER BY INICIO_PROCESO ";
			*/
			log.info(" sql " + sql ) ;

		try {
			con = JdbcUtilitario.getInstance().getConnection();
			stmt = con.prepareStatement(sql);
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				bean = new Consulta1VO();
				// NUMERO, DONANTE, RECEPTOR, DONANTE_INICIAL, ULTIMO_PROCESO, 
				// NUMERO_SECUENCIAL, INICIO_PROCESO, INICIO_VENTANA,ESTADO, TIPO_PORTABILIDAD 
				bean.setTelefono(resultSet.getString(1));
				bean.setCedente(resultSet.getString(2));
				bean.setReceptor(resultSet.getString(3));
				bean.setCedenteInicial(resultSet.getString(4));
				bean.setIdentificadorProceso(resultSet.getString(5));
				bean.setNumeroSecuencial(resultSet.getString(6));
				bean.setFechaProceso(resultSet.getString(7));
				bean.setFechaVentana(resultSet.getString(8));
				bean.setEstado(resultSet.getString(9));
				bean.setTipoPortabilidad(resultSet.getString(10));
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
	


	public static List consultaTipoProceso() throws Exception{

		List rList = new ArrayList() ;		
		JobReporteVO bean =new JobReporteVO(); 
		Connection con = null;
		String sql = "";
		
		sql =" SELECT TO_CHAR(IDREPORTE)IDREPORTE, " +
			 " NOMBREREPORTE FROM TIPO_REPORTE " +
			 " ORDER BY NOMBREREPORTE ";
			
			log.info(" sql " + sql ) ;

		try {
			con = ConexionAPI.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {
				bean = new JobReporteVO();
				bean.setIdrepor(resultSet.getString(1));
				bean.setNombrereporte(resultSet.getString(2));			
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
			}catch(SQLException e)
			{
				e.printStackTrace();
				log.error(e);
			}
		}
		return rList ;
	}
	
	public static String insertProcesoJobReporte(JobReporteVO consulta) throws Exception{
		
		Object[] objectL = new Object[1];
		String codigo = "";
		Connection con = null;
		
		log.info("[Insertar Proceso JOB REPORTE] ==> " +
		"==>Tipo reporte "+ consulta.getTipoReporte()+
		"==>fecha inicio "+ consulta.getFechaInicio()+
		"==>fecha fin "+ consulta.getFechaFin());	
		
		ParameterCollection inputCollection = new ParameterCollection();
		inputCollection.addInput(consulta.getTipoReporte(),OracleTypes.NUMBER);
		inputCollection.addInput(consulta.getFechaInicio(),OracleTypes.VARCHAR);
		inputCollection.addInput(consulta.getFechaFin(),OracleTypes.VARCHAR);		
		
		ParameterCollection outputCollection = new ParameterCollection();
		outputCollection.addOutput(OracleTypes.VARCHAR);  	
        con =ConexionAPI.getInstance().getConnection();       		
		String resultado="";
		try {
			objectL = QueryUtil.executeProcedure(con, ValueConstants.PK_PORTABILIDAD.concat(".SP_PROCESO_XLS"), inputCollection, outputCollection);
			codigo = String.valueOf(objectL[0]);
			con.commit();
			resultado="OK";
		} catch (Exception e) {
			log.info("Mensaje error Insertar JobReporte:"+e.getMessage());
			con.rollback();
			resultado="Error en la operación";
		}
		log.info("Resultado de la Insercion ="+resultado);
    return resultado;
}			
	
	
	public static List<PreFacturacionVO> consultaPreFacturacion(PreFacturacionVO consulta) throws Exception {
		 List<PreFacturacionVO> lista = new ArrayList<PreFacturacionVO>(); 
		Connection con = null;
		DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
		simbolos.setDecimalSeparator('.');
		//DecimalFormat myFormatter = new DecimalFormat(pattern);
		DecimalFormat formateador = new DecimalFormat("###.00",simbolos);
		int portados=0,retorno=0,objecion=0;
		double monto=0;
		 String sql = "";
		 System.out.println("TIPO CONSULTA="+consulta.getTipoCons());
			if ("P".equals(consulta.getTipoCons())){
			sql ="  SELECT COD_OPERADOR OPERADOR, SUM(NVL(PORTADOS,0))PORTADOS,SUM(NVL(RETORNOS,0))RETORNOS,SUM(NVL(OBJECIONES,0)) OBJECIONES, "+
				 " 	TO_CHAR(ROUND(SUM(((NVL(PORTADOS,0)+NVL(RETORNOS,0)+ NVL(OBJECIONES,0) )* "+BundleGeneric.getBundle("porcentaje_repor")+")),2),'999,999,999.00')MONTO "+
				 " 	FROM FACTURA_OPERADORES "+
				 " 	WHERE EMISION_FACTURA  BETWEEN "+
				 "  TO_DATE('"+consulta.getPeriodoInicio()+"','mmyyyy') AND "+
				 "  TO_DATE('"+consulta.getPeriodoFin()+"','mmyyyy')"+
				 " 	GROUP BY  COD_OPERADOR"+
				 " 	UNION ALL "+
				 " 	SELECT 'TOTALES' OPERADOR,SUM(NVL(PORTADOS,0))PORTADOS,SUM(NVL(RETORNOS,0))RETORNOS,SUM(NVL(OBJECIONES,0)) OBJECIONES, "+
				 " 	TO_CHAR(ROUND(SUM(((NVL(PORTADOS,0)+NVL(RETORNOS,0)+ NVL(OBJECIONES,0) )* 6.95)),2),'999,999,999.00')MONTO "+
				 " 	FROM FACTURA_OPERADORES "+
				 " 	WHERE EMISION_FACTURA  BETWEEN "+
				 "  TO_DATE('"+consulta.getPeriodoInicio()+"','mmyyyy') AND "+
				 "  TO_DATE('"+consulta.getPeriodoFin()+"','mmyyyy') "+
			     " 	ORDER BY 1";
			}
			if ("F".equals(consulta.getTipoCons())){
				sql =" SELECT OPERADOR,SP,RETORNO,OBJECION, "+
				    " TO_CHAR(ROUND(((SP+RETORNO+OBJECION)* 6.95),2),'999,999,999.00') TOTALES" +
				    " FROM (SELECT DISTINCT OPERADOR, "+
					" NVL((SELECT COUNT(concepto_factura) "+
					" 		FROM FACTURA_OPERADORES_DETALLE  "+
					" 			 WHERE concepto_factura='01' AND OPERADOR=A.OPERADOR "+ 
					" 			 AND FECHA BETWEEN "+
					"  TO_DATE('"+consulta.getFechaInicio()+" 000000','dd/mm/yyyy HH24miss') AND "+
					"  TO_DATE('"+consulta.getFechaFin()+" 235959','dd/mm/yyyy HH24miss') "+
					" 			 GROUP BY concepto_factura),0) SP, "+				
					" NVL((SELECT COUNT(concepto_factura) "+
					" 		FROM FACTURA_OPERADORES_DETALLE  "+
					" 			 WHERE concepto_factura='03' AND OPERADOR=A.OPERADOR "+ 
					" 			 AND FECHA BETWEEN "+
					"  TO_DATE('"+consulta.getFechaInicio()+" 000000','dd/mm/yyyy HH24miss') AND "+
					"  TO_DATE('"+consulta.getFechaFin()+" 235959','dd/mm/yyyy HH24miss') "+
					" 			 GROUP BY concepto_factura),0) Retorno, "+
					" NVL((SELECT COUNT(concepto_factura) "+
					" 		FROM FACTURA_OPERADORES_DETALLE  "+
					" 			 WHERE concepto_factura='02' AND OPERADOR=A.OPERADOR "+
					" 			 AND FECHA BETWEEN "+
					"  TO_DATE('"+consulta.getFechaInicio()+" 000000','dd/mm/yyyy HH24miss') AND "+
					"  TO_DATE('"+consulta.getFechaFin()+" 235959','dd/mm/yyyy HH24miss') "+
					" 			 GROUP BY concepto_factura),0) Objecion "+
					" FROM FACTURA_OPERADORES_DETALLE A "+
					" WHERE OPERADOR IS NOT NULL )A " +
					" UNION ALL "+
					" SELECT 'TOTALES',SUM(NVL(SP,0)),SUM(NVL(RETORNO,0)),SUM(NVL(OBJECION,0)), "+
				    " TO_CHAR(SUM(ROUND(((NVL(SP,0)+NVL(RETORNO,0)+NVL(OBJECION,0))* 6.95),2)),'999,999,999.00') TOTALES " +
				    " FROM (SELECT DISTINCT OPERADOR, "+
					" NVL((SELECT COUNT(concepto_factura) "+
					" 		FROM FACTURA_OPERADORES_DETALLE  "+
					" 			 WHERE concepto_factura='01' AND OPERADOR=A.OPERADOR "+ 
					" 			 AND FECHA BETWEEN "+
					"  TO_DATE('"+consulta.getFechaInicio()+" 000000','dd/mm/yyyy HH24miss') AND "+
					"  TO_DATE('"+consulta.getFechaFin()+" 235959','dd/mm/yyyy HH24miss') "+
					" 			 GROUP BY concepto_factura),0) SP, "+				
					" NVL((SELECT COUNT(concepto_factura) "+
					" 		FROM FACTURA_OPERADORES_DETALLE  "+
					" 			 WHERE concepto_factura='03' AND OPERADOR=A.OPERADOR "+ 
					" 			 AND FECHA BETWEEN "+
					"  TO_DATE('"+consulta.getFechaInicio()+" 000000','dd/mm/yyyy HH24miss') AND "+
					"  TO_DATE('"+consulta.getFechaFin()+" 235959','dd/mm/yyyy HH24miss') "+
					" 			 GROUP BY concepto_factura),0) Retorno, "+
					" NVL((SELECT COUNT(concepto_factura) "+
					" 		FROM FACTURA_OPERADORES_DETALLE  "+
					" 			 WHERE concepto_factura='02' AND OPERADOR=A.OPERADOR "+
					" 			 AND FECHA BETWEEN "+
					"  TO_DATE('"+consulta.getFechaInicio()+" 000000','dd/mm/yyyy HH24miss') AND "+
					"  TO_DATE('"+consulta.getFechaFin()+" 235959','dd/mm/yyyy HH24miss') "+
					" 			 GROUP BY concepto_factura),0) Objecion "+
					" FROM FACTURA_OPERADORES_DETALLE A "+
					" WHERE OPERADOR IS NOT NULL )A " +
					" ORDER BY 1";
			}
			System.out.println("SQL="+sql);
			
			try {				
				con = JdbcUtilitario.getInstance().getConnection();
				PreparedStatement stmt = con.prepareStatement(sql);
				ResultSet resultSet = stmt.executeQuery();

				while (resultSet.next()) {
					PreFacturacionVO  report = new PreFacturacionVO();
					report.setOperador((resultSet.getString(1)));
					report.setPortados((resultSet.getString(2)));
					report.setRetornos((resultSet.getString(3)));
					report.setObjeciones((resultSet.getString(4)));					
					report.setMonto(resultSet.getString(5));				
					lista.add(report);					
					}				
				    //report.setMonto(String.valueOf(formateador.format(monto)));			
			} catch (Exception e) { 
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
		 return lista;
	 }

	public static List<PreFacturacionVO> consultaDetalleOperadores(PreFacturacionVO consulta,String tipo) throws Exception {
		 List<PreFacturacionVO> lista = new ArrayList<PreFacturacionVO>(); 
		Connection con = null;
		int portados=0,retorno=0,objecion=0;
		String fechaInicio="",fechaFin="";		
		Calendar cal = new GregorianCalendar();		
		 String sql = "";
		 System.out.println("TIPO CONSULTA="+consulta.getTipoCons());		 
		 if ("F".equals(consulta.getTipoCons())){
			 fechaInicio=consulta.getFechaInicio();
			 fechaFin   =consulta.getFechaFin();
		 }
		 if ("P".equals(consulta.getTipoCons())){
			 fechaInicio="01/"+consulta.getPeriodoInicio().substring(0,2)+"/"+consulta.getPeriodoInicio().substring(2,6);			 
			 cal.setTime(new SimpleDateFormat("dd/MM/yyyy").parse("01/"+consulta.getPeriodoFin().substring(0,2)+"/"+consulta.getPeriodoFin().substring(2,6)));
			 fechaFin=String.valueOf(cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH))+"/"+consulta.getPeriodoFin().substring(0,2)+"/"+consulta.getPeriodoFin().substring(2,6);
		 }
		 
		 System.out.println("FECHA INICIO="+fechaInicio);
		 System.out.println("FECHA FIN="+fechaFin);
		 
			sql ="  SELECT D.OPERADOR, D.CONCEPTO_FACTURA, "+
				"  	D.ID_PROCESO, TO_CHAR(D.FECHA,'DD/MM/YYYY')fecha,  "+
				"  	DN.INICIO_RANGO, DN.FINAL_RANGO, DN.TOTAL_RANGO "+
				"  	FROM FACTURA_OPERADORES_DETALLE D,  "+
				"  	FACTURA_OPERADORES_DETALLE_NUM DN "+
				"  	WHERE D.ID_INTERNO=DN.ID_INTERNO "+
				"  	AND D.FECHA BETWEEN "+
				"   TO_DATE('"+fechaInicio+" 000000','dd/mm/yyyy HH24miss') AND "+
				"   TO_DATE('"+fechaFin+" 235959','dd/mm/yyyy HH24miss') "+
				"  	AND D.OPERADOR='"+tipo+"' "+
				"  	ORDER BY D.FECHA,D.CONCEPTO_FACTURA, D.ID_PROCESO ";			
			
			System.out.println("SQL="+sql);
			
			try {
				con = JdbcUtilitario.getInstance().getConnection();
				PreparedStatement stmt = con.prepareStatement(sql);
				ResultSet resultSet = stmt.executeQuery();

				while (resultSet.next()) {
					PreFacturacionVO  report = new PreFacturacionVO();
					report.setOperador((resultSet.getString(1)));
					report.setConceptoFactura((resultSet.getString(2)));
					report.setIdProceso((resultSet.getString(3)));					
					report.setFecha((resultSet.getString(4)));
					report.setInicioRango((resultSet.getString(5)));
					report.setFinalRango((resultSet.getString(6)));
					report.setTotalRango((resultSet.getString(7)));					
					lista.add(report);
					}
			} catch (Exception e) { 
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
		 return lista;
	 }
	

	

	
	public static List consultaValidaPortados(String fechaInicio) throws Exception{

		List rList = new ArrayList() ;
		CabeceraArchivoVO bean = new CabeceraArchivoVO();
		Connection con = null;
		String sql = "";
		
		sql =
		" SELECT tipoarchivo, cantidadinconsistencias, TO_CHAR(fecharegistro,'dd/mm/yyyy') as fecharegistro " +
		" FROM cabecera_archivo " +
		" WHERE TO_CHAR(fecharegistro,'dd/mm/yyyy') = '"+fechaInicio+"' ";
			
		log.info(" sql " + sql ) ;

		try {
			con = ConexionAPI.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				bean = new CabeceraArchivoVO();
				bean.setTipoArchivo(resultSet.getString(1));
				bean.setCantidadRegistros(Integer.valueOf(resultSet.getString(2)));
				bean.setFecha(resultSet.getString(3));
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
			}catch(SQLException e)
			{
				e.printStackTrace();
				log.error(e);
			}
		}
		return rList ;
	}
		
	
	
	
	
	

	public static List ConsultaEstadisticaPortabilidades(String fechaI, String fechaF) throws Exception{

		List rList = new ArrayList () ;
		Map parametros = new HashMap();
		EstadisticaVO bean = new EstadisticaVO();
		Connection con = null;
		log.info("DAO ConsultaEstadisticaPortabilidades:::==>INI:"+fechaI+"==>FIN:"+fechaF);
		
		String sql = 
			" SELECT CAB.OPERADOR_DESTINO,  " + 
			" (SELECT COUNT(*) AS EXITO FROM CABECERA_MENSAJE C, PROCESO_CERRADO P WHERE  " + 
			" C.ID_PROCESO=P.ID_SOLICITUD AND C.TIPO_MENSAJE = 'ANS' AND P.STATUS = '00A02' AND " + 
			" substr(P.ID_SOLICITUD,0,2) <> '00' AND  " + 
			" substr(P.ID_SOLICITUD,11,2) = '01' AND  " + 
			" C.FECHA_REGISTRO BETWEEN  " + 
			" to_date('"+fechaI+"','dd/mm/yyyy HH24:mi:ss') AND " + 
			" to_date('"+fechaF+"','dd/mm/yyyy HH24:mi:ss') AND " + 
			" C.ID_PROCESO NOT IN " + 
			" (select id_proceso from cabecera_mensaje where id_proceso = P.ID_SOLICITUD and tipo_mensaje = 'OPC') " +  
			" AND C.OPERADOR_DESTINO=CAB.OPERADOR_DESTINO) AS EXITO, " + 

			" (SELECT SUM(CANTIDAD) " + 
			" FROM( " + 
			
			/*//REEMPLAZAMOS POR EL SCRIPT DE ABAJO PARA SACAR ID PROCESO DISTINCT
			" (SELECT C.OPERADOR_DESTINO AS OPERADOR, COUNT(*) AS CANTIDAD FROM CABECERA_MENSAJE C, PROCESO_CERRADO P WHERE " +  
			" C.ID_PROCESO=P.ID_SOLICITUD AND C.TIPO_MENSAJE = 'ANS' AND P.STATUS <> '00A02' AND " + 
			" substr(P.ID_SOLICITUD,0,2) <> '00' AND  " + 
			" substr(P.ID_SOLICITUD,11,2) = '01' AND  " + 
			" C.FECHA_REGISTRO BETWEEN  " + 
			" to_date('"+fechaI+"','dd/mm/yyyy HH24:mi:ss') AND " + 
			" to_date('"+fechaF+"','dd/mm/yyyy HH24:mi:ss') " +  
			" GROUP BY C.OPERADOR_DESTINO) " + */
			
			" SELECT OPERADOR, COUNT(*) AS CANTIDAD FROM" +
			" (SELECT distinct C.OPERADOR_DESTINO AS OPERADOR,  C.ID_PROCESO AS ID_PROCESO" +
			" FROM CABECERA_MENSAJE C, PROCESO_CERRADO P WHERE  " +
			" C.ID_PROCESO=P.ID_SOLICITUD AND C.TIPO_MENSAJE = 'ANS' AND P.STATUS <> '00A02' AND  " +
			" substr(P.ID_SOLICITUD,0,2) <> '00' AND   substr(P.ID_SOLICITUD,11,2) = '01' AND   " +
			" C.FECHA_REGISTRO BETWEEN   " +
			" to_date('"+fechaI+"','dd/mm/yyyy HH24:mi:ss') AND " + 
			" to_date('"+fechaF+"','dd/mm/yyyy HH24:mi:ss') " +  
			" )B GROUP BY OPERADOR " +
			
			" UNION ALL " + 

			" SELECT C.OPERADOR_DESTINO AS OPERADOR, COUNT(*) AS CANTIDAD FROM CABECERA_MENSAJE C, PROCESO_CERRADO P WHERE " +  
			" C.ID_PROCESO=P.ID_SOLICITUD AND " + 
			" C.TIPO_MENSAJE = 'ANS' AND " + 
			" P.STATUS = '00A02' AND " + 
			" substr(P.ID_SOLICITUD,0,2) <> '00' AND  " + 
			" substr(P.ID_SOLICITUD,11,2) = '01' AND  " + 
			" C.FECHA_REGISTRO BETWEEN  " + 
			" to_date('"+fechaI+"','dd/mm/yyyy HH24:mi:ss') AND " + 
			" to_date('"+fechaF+"','dd/mm/yyyy HH24:mi:ss') AND " + 
			" C.ID_PROCESO IN " + 
			" (select id_proceso from cabecera_mensaje where id_proceso = P.ID_SOLICITUD and tipo_mensaje = 'OPC') " +  
			" group by C.OPERADOR_DESTINO " + 
			" )TEMP   " + 
			" WHERE OPERADOR=CAB.OPERADOR_DESTINO " + 
			" GROUP BY OPERADOR) AS SIN_EXITO, " + 

			" (SELECT COUNT(*) FROM CABECERA_MENSAJE C, PROCESO_ABIERTO P WHERE " +  
			" C.ID_PROCESO=P.ID_SOLICITUD AND C.TIPO_MENSAJE = 'ANS' AND " + 
			" C.FECHA_REGISTRO BETWEEN  " + 
			" to_date('"+fechaI+"','dd/mm/yyyy HH24:mi:ss') AND " + 
			" to_date('"+fechaF+"','dd/mm/yyyy HH24:mi:ss') " + 
			" AND C.OPERADOR_DESTINO=CAB.OPERADOR_DESTINO) AS PENDIENTES, " + 
			
			" COUNT(DISTINCT CAB.ID_PROCESO) AS TOTALES FROM CABECERA_MENSAJE CAB WHERE CAB.TIPO_MENSAJE = 'ANS' AND " + 
			" CAB.FECHA_REGISTRO BETWEEN  " + 
			" to_date('"+fechaI+"','dd/mm/yyyy HH24:mi:ss') AND " + 
			" to_date('"+fechaF+"','dd/mm/yyyy HH24:mi:ss') " + 
			" group by CAB.OPERADOR_DESTINO	";
		
		String sql3 = 
			" SELECT CAB.OPERADOR_DESTINO,  " + 
			" (SELECT COUNT(*) AS EXITO FROM CABECERA_MENSAJE C, PROCESO_CERRADO P WHERE  " + 
			" C.ID_PROCESO=P.ID_SOLICITUD AND C.TIPO_MENSAJE = 'ANS' AND P.STATUS = '00A02' AND " + 
			" substr(P.ID_SOLICITUD,0,2) <> '00' AND  " + 
			" substr(P.ID_SOLICITUD,11,2) = '01' AND  " + 
			" C.FECHA_REGISTRO BETWEEN  " + 
			" to_date('"+fechaI+"','dd/mm/yyyy HH24:mi:ss') AND " + 
			" to_date('"+fechaF+"','dd/mm/yyyy HH24:mi:ss') AND " + 
			" C.ID_PROCESO NOT IN " + 
			" (select id_proceso from cabecera_mensaje where id_proceso = P.ID_SOLICITUD and tipo_mensaje = 'OPC') " +  
			" AND C.OPERADOR_DESTINO=CAB.OPERADOR_DESTINO) AS EXITO, " + 

			" (SELECT SUM(CANTIDAD) " + 
			" FROM( " + 
			" (SELECT C.OPERADOR_DESTINO AS OPERADOR, COUNT(*) AS CANTIDAD FROM CABECERA_MENSAJE C, PROCESO_CERRADO P WHERE " +  
			" C.ID_PROCESO=P.ID_SOLICITUD AND C.TIPO_MENSAJE = 'ANS' AND P.STATUS <> '00A02' AND " + 
			" substr(P.ID_SOLICITUD,0,2) <> '00' AND  " + 
			" substr(P.ID_SOLICITUD,11,2) = '01' AND  " + 
			" C.FECHA_REGISTRO BETWEEN  " + 
			" to_date('"+fechaI+"','dd/mm/yyyy HH24:mi:ss') AND " + 
			" to_date('"+fechaF+"','dd/mm/yyyy HH24:mi:ss') " +  
			" GROUP BY C.OPERADOR_DESTINO) " + 
			" UNION ALL " + 

			" SELECT C.OPERADOR_DESTINO AS OPERADOR, COUNT(*) AS CANTIDAD FROM CABECERA_MENSAJE C, PROCESO_CERRADO P WHERE " +  
			" C.ID_PROCESO=P.ID_SOLICITUD AND " + 
			" C.TIPO_MENSAJE = 'ANS' AND " + 
			" P.STATUS = '00A02' AND " + 
			" substr(P.ID_SOLICITUD,0,2) <> '00' AND  " + 
			" substr(P.ID_SOLICITUD,11,2) = '01' AND  " + 
			" C.FECHA_REGISTRO BETWEEN  " + 
			" to_date('"+fechaI+"','dd/mm/yyyy HH24:mi:ss') AND " + 
			" to_date('"+fechaF+"','dd/mm/yyyy HH24:mi:ss') AND " + 
			" C.ID_PROCESO IN " + 
			" (select id_proceso from cabecera_mensaje where id_proceso = P.ID_SOLICITUD and tipo_mensaje = 'OPC') " +  
			" group by C.OPERADOR_DESTINO " + 
			" )TEMP   " + 
			" WHERE OPERADOR=CAB.OPERADOR_DESTINO " + 
			" GROUP BY OPERADOR) AS SIN_EXITO, " + 

			" (SELECT COUNT(*) FROM CABECERA_MENSAJE C, PROCESO_ABIERTO P WHERE " +  
			" C.ID_PROCESO=P.ID_SOLICITUD AND C.TIPO_MENSAJE = 'ANS' AND " + 
			" C.FECHA_REGISTRO BETWEEN  " + 
			" to_date('"+fechaI+"','dd/mm/yyyy HH24:mi:ss') AND " + 
			" to_date('"+fechaF+"','dd/mm/yyyy HH24:mi:ss') " + 
			" AND C.OPERADOR_DESTINO=CAB.OPERADOR_DESTINO) AS PENDIENTES, " + 
			
			" COUNT(*) AS TOTALES FROM CABECERA_MENSAJE CAB WHERE CAB.TIPO_MENSAJE = 'ANS' AND " + 
			" CAB.FECHA_REGISTRO BETWEEN  " + 
			" to_date('"+fechaI+"','dd/mm/yyyy HH24:mi:ss') AND " + 
			" to_date('"+fechaF+"','dd/mm/yyyy HH24:mi:ss') " + 
			" group by CAB.OPERADOR_DESTINO	";

		
		String sql2 = 
			" SELECT CAB.OPERADOR_DESTINO,  " + 
			" (SELECT COUNT(*) AS EXITO FROM CABECERA_MENSAJE C, PROCESO_CERRADO P WHERE  " + 
			" C.ID_PROCESO=P.ID_SOLICITUD AND C.TIPO_MENSAJE = 'ANS' AND P.STATUS = '00A02' AND " + 
			" substr(P.ID_SOLICITUD,0,2) <> '00' AND  " + 
			" substr(P.ID_SOLICITUD,11,2) = '01' AND  " + 
			" C.FECHA_REGISTRO BETWEEN  " + 
			" to_date('"+fechaI+"','dd/mm/yyyy HH24:mi:ss') AND " + 
			" to_date('"+fechaF+"','dd/mm/yyyy HH24:mi:ss') AND " + 
			" C.ID_PROCESO NOT IN " + 
			" (select id_proceso from cabecera_mensaje where id_proceso = P.ID_SOLICITUD and tipo_mensaje = 'OPC') " +  
			" AND C.OPERADOR_DESTINO=CAB.OPERADOR_DESTINO) AS EXITO, " + 

			" (SELECT SUM(CANTIDAD) " + 
			" FROM( " + 
			" (SELECT C.OPERADOR_DESTINO AS OPERADOR, COUNT(*) AS CANTIDAD FROM CABECERA_MENSAJE C, PROCESO_CERRADO P WHERE " +  
			" C.ID_PROCESO=P.ID_SOLICITUD AND C.TIPO_MENSAJE = 'ANS' AND P.STATUS <> '00A02' AND " + 
			" substr(P.ID_SOLICITUD,0,2) <> '00' AND  " + 
			" substr(P.ID_SOLICITUD,11,2) = '01' AND  " + 
			" C.FECHA_REGISTRO BETWEEN  " + 
			" to_date('"+fechaI+"','dd/mm/yyyy HH24:mi:ss') AND " + 
			" to_date('"+fechaF+"','dd/mm/yyyy HH24:mi:ss') " +  
			" GROUP BY C.OPERADOR_DESTINO) " + 
			" UNION ALL " + 
			" SELECT N.OPERADOR_ORIGEN AS OPERADOR, COUNT(*) AS CANTIDAD FROM NI_CABECERA_MENSAJE N " + 
			" WHERE  " + 
			" N.TIPO_MENSAJE = 'SP' AND " + 
			" N.FECHA_REGISTRO BETWEEN  " + 
			" to_date('"+fechaI+"','dd/mm/yyyy HH24:mi:ss') AND " + 
			" to_date('"+fechaF+"','dd/mm/yyyy HH24:mi:ss') " + 
			" group by N.OPERADOR_ORIGEN " + 
			" UNION ALL " + 
				
			" SELECT C.OPERADOR_DESTINO AS OPERADOR, COUNT(*) AS CANTIDAD FROM CABECERA_MENSAJE C, PROCESO_CERRADO P WHERE " +  
			" C.ID_PROCESO=P.ID_SOLICITUD AND " + 
			" C.TIPO_MENSAJE = 'ANS' AND " + 
			" P.STATUS = '00A02' AND " + 
			" substr(P.ID_SOLICITUD,0,2) <> '00' AND  " + 
			" substr(P.ID_SOLICITUD,11,2) = '01' AND  " + 
			" C.FECHA_REGISTRO BETWEEN  " + 
			" to_date('"+fechaI+"','dd/mm/yyyy HH24:mi:ss') AND " + 
			" to_date('"+fechaF+"','dd/mm/yyyy HH24:mi:ss') AND " + 
			" C.ID_PROCESO IN " + 
			" (select id_proceso from cabecera_mensaje where id_proceso = P.ID_SOLICITUD and tipo_mensaje = 'OPC') " +  
			" group by C.OPERADOR_DESTINO " + 
			" )TEMP   " + 
			" WHERE OPERADOR=CAB.OPERADOR_DESTINO " + 
			" GROUP BY OPERADOR) AS SIN_EXITO, " + 

			" (SELECT COUNT(*) FROM CABECERA_MENSAJE C, PROCESO_ABIERTO P WHERE " +  
			" C.ID_PROCESO=P.ID_SOLICITUD AND C.TIPO_MENSAJE = 'ANS' AND " + 
			" C.FECHA_REGISTRO BETWEEN  " + 
			" to_date('"+fechaI+"','dd/mm/yyyy HH24:mi:ss') AND " + 
			" to_date('"+fechaF+"','dd/mm/yyyy HH24:mi:ss') " + 
			" AND C.OPERADOR_DESTINO=CAB.OPERADOR_DESTINO) AS PENDIENTES, " + 

			" COUNT(*) + " +
			" (SELECT COUNT(*) FROM NI_CABECERA_MENSAJE NI " +
			" WHERE NI.TIPO_MENSAJE = 'SP' AND " +
			" NI.FECHA_REGISTRO BETWEEN " +
			" to_date('"+fechaI+"','dd/mm/yyyy HH24:mi:ss') AND " +
			" to_date('"+fechaF+"','dd/mm/yyyy HH24:mi:ss') " +
			" AND NI.OPERADOR_ORIGEN=CAB.OPERADOR_DESTINO) " +
			
			"AS TOTALES FROM CABECERA_MENSAJE CAB WHERE CAB.TIPO_MENSAJE = 'ANS' AND " + 
			" CAB.FECHA_REGISTRO BETWEEN  " + 
			" to_date('"+fechaI+"','dd/mm/yyyy HH24:mi:ss') AND " + 
			" to_date('"+fechaF+"','dd/mm/yyyy HH24:mi:ss') " + 
			" group by CAB.OPERADOR_DESTINO	";
		
		log.info(" sql estadsiticas " + sql ) ;

		try {
			con = JdbcUtilitario.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {

				bean = new EstadisticaVO();
				bean.setOperador(resultSet.getString(1));
				bean.setExito(resultSet.getInt(2));
				bean.setSinExito(resultSet.getInt(3));
				bean.setPendiente(resultSet.getInt(4));
				bean.setTotales(resultSet.getInt(5));
				
				rList.add(bean) ;
			}
		} catch (Exception e) { 
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
		return rList;
	}
	
	
	


	public static List ConsultaEstadisticaRetornos(String fechaI, String fechaF) throws Exception{

		List rList = new ArrayList () ;
		Map parametros = new HashMap();
		EstadisticaVO bean = new EstadisticaVO();
		Connection con = null;
		log.info("DAO ConsultaEstadisticaPortabilidades:::==>INI:"+fechaI+"==>FIN:"+fechaF);
		
		String sql = 
			" SELECT CAB.OPERADOR_DESTINO,  " + 
			" (SELECT COUNT(*) AS EXITO FROM CABECERA_MENSAJE C, PROCESO_CERRADO P WHERE  " + 
			" C.ID_PROCESO=P.ID_SOLICITUD AND C.TIPO_MENSAJE = 'ANS' AND P.STATUS = '00A02' AND " + 
			" substr(P.ID_SOLICITUD,0,2) <> '00' AND  " + 
			" substr(P.ID_SOLICITUD,11,2) = '01' AND  " + 
			" C.FECHA_REGISTRO BETWEEN  " + 
			" to_date('"+fechaI+"','dd/mm/yyyy HH24:mi:ss') AND " + 
			" to_date('"+fechaF+"','dd/mm/yyyy HH24:mi:ss') AND " + 
			" C.ID_PROCESO NOT IN " + 
			" (select id_proceso from cabecera_mensaje where id_proceso = P.ID_SOLICITUD and tipo_mensaje = 'OPC') " +  
			" AND C.OPERADOR_DESTINO=CAB.OPERADOR_DESTINO) AS EXITO, " + 

			" (SELECT SUM(CANTIDAD) " + 
			" FROM( " + 

			" SELECT OPERADOR, COUNT(*) AS CANTIDAD FROM" +
			" (SELECT distinct C.OPERADOR_DESTINO AS OPERADOR,  C.ID_PROCESO AS ID_PROCESO" +
			" FROM CABECERA_MENSAJE C, PROCESO_CERRADO P WHERE  " +
			" C.ID_PROCESO=P.ID_SOLICITUD AND C.TIPO_MENSAJE = 'ANS' AND P.STATUS <> '00A02' AND  " +
			" substr(P.ID_SOLICITUD,0,2) <> '00' AND   substr(P.ID_SOLICITUD,11,2) = '01' AND   " +
			" C.FECHA_REGISTRO BETWEEN   " +
			" to_date('"+fechaI+"','dd/mm/yyyy HH24:mi:ss') AND " + 
			" to_date('"+fechaF+"','dd/mm/yyyy HH24:mi:ss') " +  
			" )B GROUP BY OPERADOR " +
			
			" UNION ALL " + 

			" SELECT C.OPERADOR_DESTINO AS OPERADOR, COUNT(*) AS CANTIDAD FROM CABECERA_MENSAJE C, PROCESO_CERRADO P WHERE " +  
			" C.ID_PROCESO=P.ID_SOLICITUD AND " + 
			" C.TIPO_MENSAJE = 'ANS' AND " + 
			" P.STATUS = '00A02' AND " + 
			" substr(P.ID_SOLICITUD,0,2) <> '00' AND  " + 
			" substr(P.ID_SOLICITUD,11,2) = '01' AND  " + 
			" C.FECHA_REGISTRO BETWEEN  " + 
			" to_date('"+fechaI+"','dd/mm/yyyy HH24:mi:ss') AND " + 
			" to_date('"+fechaF+"','dd/mm/yyyy HH24:mi:ss') AND " + 
			" C.ID_PROCESO IN " + 
			" (select id_proceso from cabecera_mensaje where id_proceso = P.ID_SOLICITUD and tipo_mensaje = 'OPC') " +  
			" group by C.OPERADOR_DESTINO " + 
			" )TEMP   " + 
			" WHERE OPERADOR=CAB.OPERADOR_DESTINO " + 
			" GROUP BY OPERADOR) AS SIN_EXITO, " + 

			" (SELECT COUNT(*) FROM CABECERA_MENSAJE C, PROCESO_ABIERTO P WHERE " +  
			" C.ID_PROCESO=P.ID_SOLICITUD AND C.TIPO_MENSAJE = 'ANS' AND " + 
			" C.FECHA_REGISTRO BETWEEN  " + 
			" to_date('"+fechaI+"','dd/mm/yyyy HH24:mi:ss') AND " + 
			" to_date('"+fechaF+"','dd/mm/yyyy HH24:mi:ss') " + 
			" AND C.OPERADOR_DESTINO=CAB.OPERADOR_DESTINO) AS PENDIENTES, " + 
			
			" COUNT(DISTINCT CAB.ID_PROCESO) AS TOTALES FROM CABECERA_MENSAJE CAB WHERE CAB.TIPO_MENSAJE = 'ANS' AND " + 
			" CAB.FECHA_REGISTRO BETWEEN  " + 
			" to_date('"+fechaI+"','dd/mm/yyyy HH24:mi:ss') AND " + 
			" to_date('"+fechaF+"','dd/mm/yyyy HH24:mi:ss') " + 
			" group by CAB.OPERADOR_DESTINO	";
		
		log.info(" sql estadsiticas " + sql ) ;

		try {
			con = JdbcUtilitario.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {

				bean = new EstadisticaVO();
				bean.setOperador(resultSet.getString(1));
				bean.setExito(resultSet.getInt(2));
				bean.setSinExito(resultSet.getInt(3));
				bean.setPendiente(resultSet.getInt(4));
				bean.setTotales(resultSet.getInt(5));
				
				rList.add(bean) ;
			}
		} catch (Exception e) { 
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
		return rList;
	}
	
	
}
