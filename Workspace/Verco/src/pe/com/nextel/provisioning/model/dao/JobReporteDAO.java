package pe.com.nextel.provisioning.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pe.com.nextel.provisioning.framework.conexion.ConexionAPI;
import pe.com.nextel.provisioning.framework.conexion.JdbcUtilitario;
import pe.com.nextel.provisioning.framework.conexion.JdbcUtilitarioTest;
import pe.com.nextel.provisioning.framework.conexion.ParameterCollection;
import pe.com.nextel.provisioning.framework.conexion.QueryUtil;
import pe.com.nextel.provisioning.framework.exception.DAOException;
import pe.com.nextel.provisioning.framework.util.transaction.TransactionContext;
import pe.com.nextel.provisioning.model.vo.JobReporteVO;
import pe.com.nextel.provisioning.model.vo.ReporteVO;
import pe.com.nextel.provisioning.view.ValueConstants;

public class JobReporteDAO {
	private static final Log log = LogFactory.getLog(JobReporteDAO.class);
	private static JobReporteDAO single = null;
	public JobReporteDAO(){}
	public static JobReporteDAO getInstance() {
	    if (single == null)
	      single = new JobReporteDAO();
	    return single;
	  }
	 
	
	  public void editarJob(String idJob) throws Exception{       
			 	log.info("[limpiarTabla] ");
				Statement stmt = null;
				Connection con = null;
	  	try {
	  		 	con = JdbcUtilitario.getInstance().getConnection();
	  		 	stmt = con.createStatement();
		        stmt.execute(" update JOB_REPORTE where idjob='"+idJob+"' ");
		        } catch (Exception e) { 	        	
		        	log.error(e);
		        }
		        finally
		         {
		             try
		             {
		            	 stmt.close();
		            	 con.close();
		             }catch(SQLException e)
		             {
		                 log.error(e);
		             }
		         }	  
	  }
	
	  
	public List<JobReporteVO> consultaJobReporte() throws DAOException {
		List <JobReporteVO>reporte=new ArrayList<JobReporteVO>(); 
		JobReporteVO beanJob =null; 
		Connection con = null;		
		PreparedStatement stmt = null;
		ResultSet resultSet=null;
		StringBuffer sql2 = new StringBuffer(" SELECT TRIM(TO_CHAR(A.IDJOB)), " +
				 " TO_CHAR(A.FECHAINICIO,'DD/MM/YYYY'), " +
				 " TO_CHAR(A.FECHAFIN,'DD/MM/YYYY') , " +
				 " (SELECT NOMBREREPORTE FROM TIPO_REPORTE B WHERE B.IDREPORTE=A.IDREPORTE  )tiporeporte ," +
				 " IND_FECHAS,A.IDREPORTE"+
				 " FROM (SELECT * FROM JOB_REPORTE "+  
				 " WHERE INDICADORPROCESO=0   "+
				 " AND ROWNUM=1 " +
				 " HAVING  (SELECT COUNT(INDICADORPROCESO) FROM JOB_REPORTE WHERE INDICADORPROCESO=1)=0 " +				 
				 " ORDER BY FECHAREGISTRO)A  ");
		
		StringBuffer sql = new StringBuffer("SELECT TRIM(TO_CHAR(A.IDJOB)),  TO_CHAR(A.FECHAINICIO,'dd/mm/yyyy'), " +
				" TO_CHAR(A.FECHAFIN,'dd/mm/yyyy'), " +
				" (SELECT NOMBREREPORTE FROM TIPO_REPORTE B WHERE B.IDREPORTE=A.IDREPORTE)tiporeporte , " +
				" IND_FECHAS,A.IDREPORTE " + 
				" FROM  JOB_REPORTE A WHERE INDICADORPROCESO=0 AND ROWNUM=1 "); 
			
			System.out.println("sql="+sql.toString());
			try {
				con = ConexionAPI.getInstance().getConnection();
				stmt = con.prepareStatement(sql.toString());
			    resultSet = stmt.executeQuery();
			    log.info("Se ejecuto correctamente el query");
				while (resultSet.next()) {
					beanJob = new JobReporteVO();
					beanJob.setIdjob(resultSet.getString(1));
					beanJob.setFechaInicio(resultSet.getString(2));
					beanJob.setFechaFin(resultSet.getString(3));
					beanJob.setTipoReporte(resultSet.getString(4));
					beanJob.setInd_fechas(resultSet.getString(5));
					beanJob.setIdrepor(resultSet.getString(6));
					reporte.add(beanJob);
					}
				
			} catch (Exception e) { 
			      log.info("Hubo un error " + e.getMessage());
			    }
			    finally
			    {
			      try
			      {
			        resultSet.close();
			        stmt.close();
			        con.close();
			      }catch(SQLException e)
			      {
			        e.printStackTrace();
			        log.error(e);
			      }
			    
			}
			return reporte ;
	  } 

	 
	 
	 public  List<ReporteVO> listaJobReporte(JobReporteVO consulta) throws Exception {
		 List<ReporteVO> lista = new ArrayList<ReporteVO>(); 
		Connection con = null;
		 String sql = "";
		 String val1="0",val2="0";
		 int tipoReporte=Integer.valueOf(consulta.getIdrepor());
		 System.out.println("tipo_reporte="+tipoReporte);
		 int parRec=0;
		 switch (tipoReporte) {
		 case 1:
			 try {
				 //Reporte de Osiptel
				 sql =" SELECT " +
					" PROCESO, OPERADOR_RECEPTOR,OPERADOR_CEDENTE," +
					"TIPO_DOCUMENTO,NUMERO_DOCUMENTO,INICIO_RANGO,FINAL_RANGO," +
					" TIPO_PORTABILIDAD,FECHA_MENSAJE_SP, SECUENCIAL, ULT_MJE," +
					" (case when ULT_MJE = 'RABDCP' or ULT_MJE = 'EABDCP' then" +
					" (SELECT CAUSA_RECHAZO FROM MENSAJE_RABDCP WHERE ID_INTERNO=" +
					"(SELECT MAX(ID_INTERNO) FROM CABECERA_MENSAJE WHERE ID_PROCESO=PROCESO AND TIPO_MENSAJE='RABDCP'))" +
					" else " +
					" (SELECT ' ' FROM DUAL) " +
					" end) AS CAUSA " +
					"	FROM ( " +		
					 " SELECT PROCESO, OPERADOR_RECEPTOR,OPERADOR_CEDENTE, "+
					 " TIPO_DOCUMENTO,NUMERO_DOCUMENTO,INICIO_RANGO,FINAL_RANGO, " +
					 " TIPO_PORTABILIDAD,FECHA_MENSAJE_SP, SECUENCIAL, " +
					 " (case when OPERADOR_RECEPTOR = '22' then " +
					 " (select tipo_mensaje from CABECERA_MENSAJE where id_interno= " +
					 " NVL((select max(ID_INTERNO) from MENSAJE_EABDCP where ID_PROCESO_ERROR=(select max(ID_PROCESO) from CABECERA_MENSAJE where ID_PROCESO=PROCESO)), (select ID_INTERNO from CABECERA_MENSAJE where id_interno=(select max(id_interno) from CABECERA_MENSAJE where ID_PROCESO=PROCESO)))) " +
					 " else  " +
					 " (select tipo_mensaje from CABECERA_MENSAJE where id_interno=(select max(id_interno) from CABECERA_MENSAJE where NUMERO_SECUENCIAL=SECUENCIAL)) " + 
					 " end) AS ULT_MJE " +
					 "  FROM ( "+
					 "  SELECT  "+
					 "  (SELECT MIN(ID_PROCESO) FROM CABECERA_MENSAJE CM, MENSAJE_ANS MA "+ 
					 "	WHERE CM.ID_INTERNO=MA.ID_INTERNO AND CM.TIPO_MENSAJE='ANS' AND  "+
					 "	CM.NUMERO_SECUENCIAL=C.NUMERO_SECUENCIAL AND MA.NUMERO_TELEFONO=M.INICIO_RANGO) AS PROCESO, "+
					 "	(SELECT CODIGO||' - '||DESCRIPCION FROM OPERADOR WHERE CODIGO=S.OPERADOR_RECEPTOR) AS OPERADOR_RECEPTOR, "+
					 "	(SELECT CODIGO||' - '||DESCRIPCION FROM OPERADOR WHERE CODIGO=S.OPERADOR_DONANTE) AS OPERADOR_CEDENTE, "+
					 "	DECODE(S.TIPO_IDENTIFICACION,'01','DNI','02','CARNET EXTRANJERIA','03','RUC','04','PASAPORTE','05','CIP','NA') AS TIPO_DOCUMENTO, "+ 
					 "	S.NUMERO_DOCUMENTO,M.INICIO_RANGO, M.FINAL_RANGO,  "+
					 "	DECODE(M.TIPO_PORTABILIDAD,'01','01 - Móvil prepago','02','02 - Móvil postpago','NA') AS TIPO_PORTABILIDAD, "+
					 "	to_char(C.FECHA_REGISTRO,'dd/mm/yyyy HH24:mi:ss') AS FECHA_MENSAJE_SP, C.NUMERO_SECUENCIAL AS SECUENCIAL " +
					 "	FROM CABECERA_MENSAJE C, MENSAJE_SP S,MENSAJE_SP_NUMERACION M "+
					 "	WHERE C.TIPO_MENSAJE='SP' "+
					 "	AND C.ID_INTERNO=M.ID_INTERNO "+
					 "	AND C.ID_INTERNO=S.ID_INTERNO "+
					 "	AND C.FECHA_REGISTRO BETWEEN  "+
					 "	to_date('"+consulta.getFechaInicio()+" 00:00:00','dd/mm/yyyy HH24:mi:ss') AND "+
					 "	to_date('"+consulta.getFechaFin()+" 23:59:59','dd/mm/yyyy HH24:mi:ss') "+
					 "	ORDER BY C.ID_INTERNO,C.FECHA_REGISTRO)TEMP )TABLA";
				 
						 System.out.println("SQL1="+sql);						
						 con = JdbcUtilitario.getInstance().getConnection();
						 PreparedStatement stmt = con.prepareStatement(sql);
						 ResultSet resultSet = stmt.executeQuery();
						
						 while (resultSet.next()) {
								ReporteVO  report = new ReporteVO();
								report.setIdProceso(resultSet.getString(1));
								report.setOperadorReceptor(resultSet.getString(2));
								report.setOperadorCedente(resultSet.getString(3));
								report.setTipoDocumento(resultSet.getString(4));
								report.setNumeroDocumento(resultSet.getString(5));
								report.setRangoInicial(resultSet.getString(6));
								report.setRangoFinal(resultSet.getString(7));
								report.setTipoPortabilidad(resultSet.getString(8));
								report.setFechaHoraMensajeSp(resultSet.getString(9));
								report.setUltimoMensaje(resultSet.getString(11));
								report.setCausaRechazo(resultSet.getString(12));
								lista.add(report);
								}					
						
					} catch (Exception e) { 
						throw new DAOException(e.getMessage(), e);
					}
					finally
					{
						try
						{con.close();}catch(SQLException e)
						{e.printStackTrace();log.error(e);}
					}
			break;
		 case 2:
			 try {
				 sql =" SELECT NUMERO, "+
						 " DONANTE,  "+
						 " RECEPTOR, "+ 
						 " DONANTE_INICIAL, "+
						 " ULTIMO_PROCESO,  "+
						 " NUMERO_SECUENCIAL, "+
						 " to_char(INICIO_PROCESO,'dd/mm/yyyy HH24:mi:ss') INICIO_PROCESO, "+
						 " to_char(INICIO_VENTANA,'dd/mm/yyyy HH24:mi:ss') INICIO_VENTANA, "+
						 " DURACION_VENTANA,  "+
						 " ESTADO,  "+
						 " TIMESTAMP_MODIFICACION, "+ 
						 " DONANTE_PREVIO,  "+
						 " TIPO_PORTABILIDAD "+
						 " FROM NUMERACION_PORTADA "+ 
						 " ORDER BY INICIO_PROCESO";			
						System.out.println("SQL2="+sql);
						con = JdbcUtilitario.getInstance().getConnection();
						PreparedStatement stmt = con.prepareStatement(sql);
						ResultSet resultSet = stmt.executeQuery();
					   
						while (resultSet.next()) {						
							ReporteVO  report = new ReporteVO();
							report.setNumero(resultSet.getString(1));
							report.setDonante(resultSet.getString(2));							
							report.setReceptor(resultSet.getString(3));
							report.setDonante_inicial(resultSet.getString(4));
							report.setUltimo_proceso(resultSet.getString(5));
							report.setNumero_secuencial(resultSet.getString(6));
							report.setInicio_proceso(resultSet.getString(7));
							report.setInicio_ventana(resultSet.getString(8));
							report.setDuracion_ventana(resultSet.getString(9));
							report.setEstado(resultSet.getString(10));
							report.setTimestamp_modificacion(resultSet.getString(11));
							report.setDonante_previo(resultSet.getString(12));
							report.setTipoPortabilidad(resultSet.getString(13));
							lista.add(report);							
							}
						
					} catch (Exception e) { 
						throw new DAOException(e.getMessage(), e);
					}
					finally
					{
						try
						{con.close();}catch(SQLException e)
						{e.printStackTrace();log.error(e);}
					}
			break;
		 case 3:
			 try {
				 log.info("COnsulta tipo 3 INI");
		sql = "  SELECT CODIGO AS OPERADOR, PORTADOS-RECHAZOS AS PORTADOS, RECHAZOS, RETORNOS, OBJECIONES, " +
				 " ((PORTADOS-RECHAZOS)*6.95+(RECHAZOS*6.95)+(RETORNOS+OBJECIONES)*6.95) AS MONTO " +
				 " FROM( SELECT O.CODIGO,  " +
				 " (SELECT COUNT(*) FROM FACTURA_OPERADORES_DETALLE  " + 
				 " WHERE FECHA BETWEEN " +
				 " TO_DATE('"+consulta.getFechaInicio()+" 000000','dd/mm/yyyy HH24miss') AND " + 
				 " TO_DATE('"+consulta.getFechaFin()+" 235959','dd/mm/yyyy HH24miss') " +
				 " AND OPERADOR=O.CODIGO AND CONCEPTO_FACTURA='01') AS PORTADOS, " +
				 " (SELECT COUNT(MR.CAUSA_RECHAZO) " +
				 " FROM CABECERA_MENSAJE CM, MENSAJE_RABDCP MR " +
				 " WHERE CM.ID_INTERNO= MR.ID_INTERNO " +
				 " AND CM.OPERADOR_DESTINO=O.CODIGO " +
				 " AND MR.CAUSA_RECHAZO='REC01ABD08' " +
				 " AND CM.ID_INTERNO IN " +
				 " (SELECT ID_INTERNO  " +
				 " FROM(SELECT CA.ID_PROCESO, MAX(CA.ID_INTERNO) AS ID_INTERNO " +
				 " FROM CABECERA_MENSAJE CA " +
				 " WHERE CA.TIPO_MENSAJE='RABDCP' " +
				 " AND CA.ID_PROCESO IN " +
				 " (SELECT ID_PROCESO FROM FACTURA_OPERADORES_DETALLE  " +
				 " WHERE OPERADOR=CA.OPERADOR_DESTINO AND CONCEPTO_FACTURA='01' AND FECHA BETWEEN " + 
				 " TO_DATE('"+consulta.getFechaInicio()+" 000000','dd/mm/yyyy HH24miss') AND  " +
				 " TO_DATE('"+consulta.getFechaFin()+" 235959','dd/mm/yyyy HH24miss')) " +
				 " AND CA.ID_PROCESO NOT IN " +
				 " (SELECT DISTINCT(ID_PROCESO) FROM CABECERA_MENSAJE " +
				 " WHERE TIPO_MENSAJE='VABDCP' AND " +
				 " FECHA_REGISTRO BETWEEN  " +
				 " TO_DATE('"+consulta.getFechaInicio()+" 000000','dd/mm/yyyy HH24miss') AND  " +
				 " TO_DATE('"+consulta.getFechaFin()+" 235959','dd/mm/yyyy HH24miss')) " +
				 " GROUP BY CA.ID_PROCESO " +
				 " ORDER BY CA.ID_PROCESO)TEMP)) AS RECHAZOS, " +
				 " (SELECT COUNT(*) FROM FACTURA_OPERADORES_DETALLE  " +
				 " WHERE FECHA BETWEEN  " +
				 " TO_DATE('"+consulta.getFechaInicio()+" 000000','dd/mm/yyyy HH24miss') AND  " +
				 " TO_DATE('"+consulta.getFechaFin()+" 235959','dd/mm/yyyy HH24miss') " +
				 " AND OPERADOR=O.CODIGO AND CONCEPTO_FACTURA='03') AS RETORNOS, " +
				 " (SELECT COUNT(*) FROM FACTURA_OPERADORES_DETALLE " + 
				 " WHERE FECHA BETWEEN  " +
				 " TO_DATE('"+consulta.getFechaInicio()+" 000000','dd/mm/yyyy HH24miss') AND " + 
				 " TO_DATE('"+consulta.getFechaFin()+" 235959','dd/mm/yyyy HH24miss') " +
				 " AND OPERADOR=O.CODIGO AND CONCEPTO_FACTURA='02') AS OBJECIONES " +
				 " FROM OPERADOR O " +
				 " WHERE O.CODIGO IN('20','21','22'))TEMP " +
				 " UNION " +
				 " SELECT OPERADOR, PORTADOS-RECHAZOS AS PORTADOS, RECHAZOS, RETORNOS, OBJECIONES, " +
				 " ((PORTADOS-RECHAZOS)*6.95+(RECHAZOS*6.95)+(RETORNOS+OBJECIONES)*6.95) AS MONTO " +
				 " FROM( " +
				 " SELECT  " +
				 " 'TOTAL' AS OPERADOR, " +
				 " (SELECT COUNT(*) FROM FACTURA_OPERADORES_DETALLE  " +
				 " WHERE FECHA BETWEEN  " +
				 " TO_DATE('"+consulta.getFechaInicio()+" 000000','dd/mm/yyyy HH24miss') AND  " +
				 " TO_DATE('"+consulta.getFechaFin()+" 235959','dd/mm/yyyy HH24miss') " +
				 " AND CONCEPTO_FACTURA='01') AS PORTADOS, " +
				 " (SELECT COUNT(MR.CAUSA_RECHAZO) " +
				 " FROM CABECERA_MENSAJE CM, MENSAJE_RABDCP MR " +
				 " WHERE CM.ID_INTERNO= MR.ID_INTERNO " +
				 " AND MR.CAUSA_RECHAZO='REC01ABD08' " +
				 " AND CM.ID_INTERNO IN " +
				 " (SELECT ID_INTERNO  " +
				 " FROM(SELECT CA.ID_PROCESO, MAX(CA.ID_INTERNO) AS ID_INTERNO " +
				 " FROM CABECERA_MENSAJE CA " +
				 " WHERE CA.TIPO_MENSAJE='RABDCP' " +
				 " AND CA.ID_PROCESO IN " +
				 " (SELECT ID_PROCESO FROM FACTURA_OPERADORES_DETALLE  " +
				 " WHERE OPERADOR=CA.OPERADOR_DESTINO AND CONCEPTO_FACTURA='01' AND FECHA BETWEEN " + 
				 " TO_DATE('"+consulta.getFechaInicio()+" 000000','dd/mm/yyyy HH24miss') AND  " +
				 " TO_DATE('"+consulta.getFechaFin()+" 235959','dd/mm/yyyy HH24miss')) " +
				 " AND CA.ID_PROCESO NOT IN " +
				 " (SELECT DISTINCT(ID_PROCESO) FROM CABECERA_MENSAJE " +
				 " WHERE TIPO_MENSAJE='VABDCP' AND " +
				 " FECHA_REGISTRO BETWEEN  " +
				 " TO_DATE('"+consulta.getFechaInicio()+" 000000','dd/mm/yyyy HH24miss') AND  " + 
				 " TO_DATE('"+consulta.getFechaFin()+" 235959','dd/mm/yyyy HH24miss')) " +
				 " GROUP BY CA.ID_PROCESO " +
				 " ORDER BY CA.ID_PROCESO)TEMP)) AS RECHAZOS, " +
				 " (SELECT COUNT(*) FROM FACTURA_OPERADORES_DETALLE " + 
				 " WHERE FECHA BETWEEN  " +
				 " TO_DATE('"+consulta.getFechaInicio()+" 000000','dd/mm/yyyy HH24miss') AND  " +
				 " TO_DATE('"+consulta.getFechaFin()+" 235959','dd/mm/yyyy HH24miss') " +
				 " AND CONCEPTO_FACTURA='03') AS RETORNOS, " +
				 " (SELECT COUNT(*) FROM FACTURA_OPERADORES_DETALLE " + 
				 " WHERE FECHA BETWEEN  " +
				 " TO_DATE('"+consulta.getFechaInicio()+" 000000','dd/mm/yyyy HH24miss') AND  " +
				 " TO_DATE('"+consulta.getFechaFin()+" 235959','dd/mm/yyyy HH24miss') " +
				 " AND CONCEPTO_FACTURA='02') AS OBJECIONES, " +
				 " ' ' AS MONTO " +
				 " FROM DUAL) TEMP ";
		
		
		
		sql = "  SELECT CODIGO AS OPERADOR, PORTADOS-RECHAZOS AS PORTADOS, RECHAZOS, RETORNOS, OBJECIONES, " +
		 " ((PORTADOS-RECHAZOS)*6.95+(RECHAZOS*6.95)+(RETORNOS+OBJECIONES)*6.95) AS MONTO " +
		 " FROM( SELECT O.CODIGO,  " +

		 " (SELECT COUNT(*) FROM FACTURA_OPERADORES_DETALLE FO, FACTURA_OPERADORES_DETALLE_NUM FON " +
		 " WHERE FO.ID_INTERNO=FON.ID_INTERNO AND " +
		 " FO.FECHA BETWEEN " +
		 " TO_DATE('"+consulta.getFechaInicio()+" 000000','dd/mm/yyyy HH24miss') AND " + 
		 " TO_DATE('"+consulta.getFechaFin()+" 235959','dd/mm/yyyy HH24miss') " +
		 " AND FO.OPERADOR=O.CODIGO AND FO.CONCEPTO_FACTURA='01') AS PORTADOS, "+
			 
		 " (SELECT COUNT(MR.CAUSA_RECHAZO) " +
		 " FROM CABECERA_MENSAJE CM, MENSAJE_RABDCP MR " +
		 " WHERE CM.ID_INTERNO= MR.ID_INTERNO " +
		 " AND CM.OPERADOR_DESTINO=O.CODIGO " +
		 " AND MR.CAUSA_RECHAZO='REC01ABD08' " +
		 " AND CM.ID_INTERNO IN " +
		 " (SELECT ID_INTERNO  " +
		 " FROM(SELECT CA.ID_PROCESO, MAX(CA.ID_INTERNO) AS ID_INTERNO " +
		 " FROM CABECERA_MENSAJE CA " +
		 " WHERE CA.TIPO_MENSAJE='RABDCP' " +
		 " AND CA.ID_PROCESO IN " +
		 " (SELECT ID_PROCESO FROM FACTURA_OPERADORES_DETALLE  " +
		 " WHERE OPERADOR=CA.OPERADOR_DESTINO AND CONCEPTO_FACTURA='01' AND FECHA BETWEEN " + 
		 " TO_DATE('"+consulta.getFechaInicio()+" 000000','dd/mm/yyyy HH24miss') AND  " +
		 " TO_DATE('"+consulta.getFechaFin()+" 235959','dd/mm/yyyy HH24miss')) " +
		 " AND CA.ID_PROCESO NOT IN " +
		 " (SELECT DISTINCT(ID_PROCESO) FROM CABECERA_MENSAJE " +
		 " WHERE TIPO_MENSAJE='VABDCP' AND " +
		 " FECHA_REGISTRO BETWEEN  " +
		 " TO_DATE('"+consulta.getFechaInicio()+" 000000','dd/mm/yyyy HH24miss') AND  " +
		 " TO_DATE('"+consulta.getFechaFin()+" 235959','dd/mm/yyyy HH24miss')) " +
		 " GROUP BY CA.ID_PROCESO " +
		 " ORDER BY CA.ID_PROCESO)TEMP)) AS RECHAZOS, " +

		 " (SELECT COUNT(*) FROM FACTURA_OPERADORES_DETALLE FO, FACTURA_OPERADORES_DETALLE_NUM FON " +
		 " WHERE FO.ID_INTERNO=FON.ID_INTERNO AND " +
		 " FO.FECHA BETWEEN " +
		 " TO_DATE('"+consulta.getFechaInicio()+" 000000','dd/mm/yyyy HH24miss') AND " + 
		 " TO_DATE('"+consulta.getFechaFin()+" 235959','dd/mm/yyyy HH24miss') " +
		 " AND FO.OPERADOR=O.CODIGO AND FO.CONCEPTO_FACTURA='03') AS RETORNOS, "+

		 " (SELECT COUNT(*) FROM FACTURA_OPERADORES_DETALLE FO, FACTURA_OPERADORES_DETALLE_NUM FON " +
		 " WHERE FO.ID_INTERNO=FON.ID_INTERNO AND " +
		 " FO.FECHA BETWEEN " +
		 " TO_DATE('"+consulta.getFechaInicio()+" 000000','dd/mm/yyyy HH24miss') AND " + 
		 " TO_DATE('"+consulta.getFechaFin()+" 235959','dd/mm/yyyy HH24miss') " +
		 " AND FO.OPERADOR=O.CODIGO AND FO.CONCEPTO_FACTURA='02') AS OBJECIONES "+

		 " FROM OPERADOR O " +
		 " WHERE O.CODIGO IN('20','21','22'))TEMP " +
		 " UNION " +
		 " SELECT OPERADOR, PORTADOS-RECHAZOS AS PORTADOS, RECHAZOS, RETORNOS, OBJECIONES, " +
		 " ((PORTADOS-RECHAZOS)*6.95+(RECHAZOS*6.95)+(RETORNOS+OBJECIONES)*6.95) AS MONTO " +
		 " FROM( " +
		 " SELECT  " +
		 " 'TOTAL' AS OPERADOR, " +
		 
		 " (SELECT COUNT(*) FROM FACTURA_OPERADORES_DETALLE FO, FACTURA_OPERADORES_DETALLE_NUM FON " +
		 " WHERE FO.ID_INTERNO=FON.ID_INTERNO AND " +
		 " FO.FECHA BETWEEN " +
		 " TO_DATE('"+consulta.getFechaInicio()+" 000000','dd/mm/yyyy HH24miss') AND " + 
		 " TO_DATE('"+consulta.getFechaFin()+" 235959','dd/mm/yyyy HH24miss') " +
		 " AND FO.CONCEPTO_FACTURA='01') AS PORTADOS, "+
		 
		 " (SELECT COUNT(MR.CAUSA_RECHAZO) " +
		 " FROM CABECERA_MENSAJE CM, MENSAJE_RABDCP MR " +
		 " WHERE CM.ID_INTERNO= MR.ID_INTERNO " +
		 " AND MR.CAUSA_RECHAZO='REC01ABD08' " +
		 " AND CM.ID_INTERNO IN " +
		 " (SELECT ID_INTERNO  " +
		 " FROM(SELECT CA.ID_PROCESO, MAX(CA.ID_INTERNO) AS ID_INTERNO " +
		 " FROM CABECERA_MENSAJE CA " +
		 " WHERE CA.TIPO_MENSAJE='RABDCP' " +
		 " AND CA.ID_PROCESO IN " +
		 " (SELECT ID_PROCESO FROM FACTURA_OPERADORES_DETALLE  " +
		 " WHERE OPERADOR=CA.OPERADOR_DESTINO AND CONCEPTO_FACTURA='01' AND FECHA BETWEEN " + 
		 " TO_DATE('"+consulta.getFechaInicio()+" 000000','dd/mm/yyyy HH24miss') AND  " +
		 " TO_DATE('"+consulta.getFechaFin()+" 235959','dd/mm/yyyy HH24miss')) " +
		 " AND CA.ID_PROCESO NOT IN " +
		 " (SELECT DISTINCT(ID_PROCESO) FROM CABECERA_MENSAJE " +
		 " WHERE TIPO_MENSAJE='VABDCP' AND " +
		 " FECHA_REGISTRO BETWEEN  " +
		 " TO_DATE('"+consulta.getFechaInicio()+" 000000','dd/mm/yyyy HH24miss') AND  " + 
		 " TO_DATE('"+consulta.getFechaFin()+" 235959','dd/mm/yyyy HH24miss')) " +
		 " GROUP BY CA.ID_PROCESO " +
		 " ORDER BY CA.ID_PROCESO)TEMP)) AS RECHAZOS, " +
		 
		 " (SELECT COUNT(*) FROM FACTURA_OPERADORES_DETALLE FO, FACTURA_OPERADORES_DETALLE_NUM FON " +
		 " WHERE FO.ID_INTERNO=FON.ID_INTERNO AND " +
		 " FO.FECHA BETWEEN " +
		 " TO_DATE('"+consulta.getFechaInicio()+" 000000','dd/mm/yyyy HH24miss') AND " + 
		 " TO_DATE('"+consulta.getFechaFin()+" 235959','dd/mm/yyyy HH24miss') " +
		 " AND FO.CONCEPTO_FACTURA='03') AS RETORNOS, "+
		 
		 " (SELECT COUNT(*) FROM FACTURA_OPERADORES_DETALLE FO, FACTURA_OPERADORES_DETALLE_NUM FON " +
		 " WHERE FO.ID_INTERNO=FON.ID_INTERNO AND " +
		 " FO.FECHA BETWEEN " +
		 " TO_DATE('"+consulta.getFechaInicio()+" 000000','dd/mm/yyyy HH24miss') AND " + 
		 " TO_DATE('"+consulta.getFechaFin()+" 235959','dd/mm/yyyy HH24miss') " +
		 " AND FO.CONCEPTO_FACTURA='02') AS OBJECIONES, "+
		 
		 " ' ' AS MONTO " +
		 " FROM DUAL) TEMP ";
		
		   log.info("SQL3="+sql);
						//OPERADOR, PORTADOS, RECHAZOS, RETORNOS, OBJECIONES, MONTO
						 System.out.println("SQL3="+sql);						
						 con = JdbcUtilitario.getInstance().getConnection();
						 PreparedStatement stmt = con.prepareStatement(sql);
						 ResultSet resultSet = stmt.executeQuery();						
						while (resultSet.next()) {							
							ReporteVO  report = new ReporteVO();
							report.setOperador(resultSet.getString(1));
							//report.setEmisionFactura(resultSet.getString(2));						
							report.setPortador(resultSet.getString(2));
							report.setRetornos(resultSet.getString(4));
							report.setObjeciones(resultSet.getString(5));
							report.setRecAbd(resultSet.getString(3));
							report.setMonto(resultSet.getString(6));
							lista.add(report);
							}
						
					} catch (Exception e) { 
						//throw new DAOException(e.getMessage(), e);
						 log.info("ERROR:"+e.getMessage());
					}
					finally
					{
						try
						{con.close();}catch(SQLException e)
						{e.printStackTrace();log.error(e);}
					}
					 log.info("COnsulta tipo 3 FIN");
			break;
		 case 4:
			 try { 
				 
				 sql = "SELECT M.NUMERO_TELEFONO, " +
					" (select operador_receptor from mensaje_sp where id_interno=(SELECT max(ID_INTERNO) FROM CABECERA_MENSAJE WHERE NUMERO_SECUENCIAL=C.NUMERO_SECUENCIAL AND TIPO_MENSAJE='SP')) RECEPTOR, " +
					" (select operador_donante from mensaje_sp where id_interno=(SELECT max(ID_INTERNO) FROM CABECERA_MENSAJE WHERE NUMERO_SECUENCIAL=C.NUMERO_SECUENCIAL AND TIPO_MENSAJE='SP')) CEDENTE, " +
					" C.NUMERO_SECUENCIAL, C.ID_PROCESO, to_char(C.FECHA_REGISTRO,'dd/mm/yyyy HH24:mi:ss') AS FECHA_REGISTRO, " +
					" (SELECT CAUSA_RECHAZO FROM MENSAJE_RABDCP WHERE ID_INTERNO=(SELECT MAX(CM.ID_INTERNO) FROM CABECERA_MENSAJE CM WHERE CM.TIPO_MENSAJE='RABDCP' AND CM.ID_PROCESO=C.ID_PROCESO)) AS CAUSA, " +
					" (case " +
					" when C.operador_destino = '22' then  " +
					" (select to_char(fecha_registro,'dd/mm/yyyy HH24:mi:ss') from CABECERA_MENSAJE where id_interno= " +
					" NVL((select max(ID_INTERNO) from MENSAJE_EABDCP where CODIGO_ERROR='ERROR00003' AND ID_PROCESO_ERROR=(select max(ID_PROCESO) from CABECERA_MENSAJE where ID_PROCESO=C.ID_PROCESO)), (select ID_INTERNO from CABECERA_MENSAJE where id_interno=(select max(id_interno) from CABECERA_MENSAJE where ID_PROCESO=C.ID_PROCESO)))) " +
					" else  " +
					" (select to_char(fecha_registro,'dd/mm/yyyy HH24:mi:ss') from CABECERA_MENSAJE where id_interno=(select max(id_interno) from CABECERA_MENSAJE where NUMERO_SECUENCIAL=C.NUMERO_SECUENCIAL)) " + 
					" end) AS FECHA_EABDCP " +
					" FROM CABECERA_MENSAJE C, MENSAJE_ANS M " + 
					" WHERE  C.TIPO_MENSAJE='ANS' " +
					" AND C.ID_INTERNO=M.ID_INTERNO " +
					" AND C.ID_PROCESO IN " +
					" (SELECT distinct(ID_PROCESO)  " +
					" FROM CABECERA_MENSAJE C, MENSAJE_RABDCP M " + 
					" WHERE C.TIPO_MENSAJE='RABDCP' " +
					" AND C.ID_INTERNO=M.ID_INTERNO " +
					" AND M.CAUSA_RECHAZO NOT IN ('REC01ABD01','REC01ABD03','REC01ABD05')) " +
					" AND (case when C.operador_destino = '22' then " +
					" (select fecha_registro from CABECERA_MENSAJE where id_interno= " +
					" NVL((select max(ID_INTERNO) from MENSAJE_EABDCP where CODIGO_ERROR='ERROR00003' AND ID_PROCESO_ERROR=(select max(ID_PROCESO) from CABECERA_MENSAJE where ID_PROCESO=C.ID_PROCESO)), (select ID_INTERNO from CABECERA_MENSAJE where id_interno=(select max(id_interno) from CABECERA_MENSAJE where ID_PROCESO=C.ID_PROCESO)))) " +
					" else  " +
					" (select fecha_registro from CABECERA_MENSAJE where id_interno=(select max(id_interno) from CABECERA_MENSAJE where NUMERO_SECUENCIAL=C.NUMERO_SECUENCIAL)) " + 
					" end)  BETWEEN   " +
					" TO_DATE('"+consulta.getFechaInicio()+" 000000','dd/mm/yyyy HH24miss') AND " + 
					" TO_DATE('"+consulta.getFechaFin()+" 235959','dd/mm/yyyy HH24miss') " +
					" AND (case " +
					" when C.operador_destino = '22' then  " +
					" (select tipo_mensaje from CABECERA_MENSAJE where id_interno= " +
					" NVL((select max(ID_INTERNO) from MENSAJE_EABDCP where CODIGO_ERROR='ERROR00003' AND ID_PROCESO_ERROR=(select max(ID_PROCESO) from CABECERA_MENSAJE where ID_PROCESO=C.ID_PROCESO)), (select ID_INTERNO from CABECERA_MENSAJE where id_interno=(select max(id_interno) from CABECERA_MENSAJE where ID_PROCESO=C.ID_PROCESO)))) " +
					" else  " +
					" (select tipo_mensaje from CABECERA_MENSAJE where id_interno=(select max(id_interno) from CABECERA_MENSAJE where NUMERO_SECUENCIAL=C.NUMERO_SECUENCIAL)) " + 
					" end) ='EABDCP' " + 
					" ORDER BY C.ID_PROCESO ";

				 		 System.out.println("SQL3="+sql);						
						 con = JdbcUtilitario.getInstance().getConnection();
						 PreparedStatement stmt = con.prepareStatement(sql);
						 ResultSet resultSet = stmt.executeQuery();						
						while (resultSet.next()) {							
							ReporteVO  report = new ReporteVO();
							report.setNumero(resultSet.getString(1));
							report.setReceptor(resultSet.getString(2));
							report.setDonante(resultSet.getString(3));
							report.setNumero_secuencial(resultSet.getString(4));
							report.setUltimo_proceso(resultSet.getString(5));
							report.setFecha(resultSet.getString(6));
							report.setCausaRechazo(resultSet.getString(7));
							report.setFechaEabdcp(resultSet.getString(8));
							lista.add(report);
							}
						
					} catch (Exception e) { 
						throw new DAOException(e.getMessage(), e);
					}
					finally
					{
						try
						{con.close();}catch(SQLException e)
						{e.printStackTrace();log.error(e);}
					}
			break;
		 case 5:
			 try { 
				 sql =" SELECT M.NUMERO_TELEFONO, " +
					 " (select operador_receptor from mensaje_sp where id_interno=(SELECT max(ID_INTERNO) FROM CABECERA_MENSAJE WHERE NUMERO_SECUENCIAL=C.NUMERO_SECUENCIAL AND TIPO_MENSAJE='SP')) RECEPTOR, " +
					 " (select operador_donante from mensaje_sp where id_interno=(SELECT max(ID_INTERNO) FROM CABECERA_MENSAJE WHERE NUMERO_SECUENCIAL=C.NUMERO_SECUENCIAL AND TIPO_MENSAJE='SP')) CEDENTE, " +
					 " C.NUMERO_SECUENCIAL, C.ID_PROCESO, to_char(C.FECHA_REGISTRO,'dd/mm/yyyy HH24:mi:ss') AS FECHA_REGISTRO, " +
					 " C.tipo_mensaje, " +
					 " (case when C.operador_destino = '22' then  " +
					 " (select tipo_mensaje from CABECERA_MENSAJE where id_interno= " +
					 " NVL((select max(ID_INTERNO) from MENSAJE_EABDCP where ID_PROCESO_ERROR=(select max(ID_PROCESO) from CABECERA_MENSAJE where ID_PROCESO=C.ID_PROCESO)), (select ID_INTERNO from CABECERA_MENSAJE where id_interno=(select max(id_interno) from CABECERA_MENSAJE where ID_PROCESO=C.ID_PROCESO)))) " +
					 " else  " +
					 " (select tipo_mensaje from CABECERA_MENSAJE where id_interno=(select max(id_interno) from CABECERA_MENSAJE where NUMERO_SECUENCIAL=C.NUMERO_SECUENCIAL)) " + 
					 " end) AS ULT_MJE " +
					 " FROM CABECERA_MENSAJE C, MENSAJE_VABDCP M WHERE " +
					 " C.FECHA_REGISTRO BETWEEN  " +
					 " TO_DATE('"+consulta.getFechaInicio()+" 000000','dd/mm/yyyy HH24miss') AND " + 
					 " TO_DATE('"+consulta.getFechaFin()+" 235959','dd/mm/yyyy HH24miss') " +
					 " AND TIPO_MENSAJE='VABDCP' AND C.ID_INTERNO=M.ID_INTERNO " +
					 " AND (case when C.operador_destino = '22' then  " +
					 " (select tipo_mensaje from CABECERA_MENSAJE where id_interno= " +
					 " NVL((select max(ID_INTERNO) from MENSAJE_EABDCP where ID_PROCESO_ERROR=(select max(ID_PROCESO) from CABECERA_MENSAJE where ID_PROCESO=C.ID_PROCESO)), (select ID_INTERNO from CABECERA_MENSAJE where id_interno=(select max(id_interno) from CABECERA_MENSAJE where ID_PROCESO=C.ID_PROCESO)))) " +
					 " else  " +
					 " (select tipo_mensaje from CABECERA_MENSAJE where id_interno=(select max(id_interno) from CABECERA_MENSAJE where NUMERO_SECUENCIAL=C.NUMERO_SECUENCIAL)) " + 
					 " end)!='EABDCP' " + 
					 " order by c.id_interno ";	
				 
				 		 System.out.println("SQL3="+sql);						
						 con = JdbcUtilitario.getInstance().getConnection();
						 PreparedStatement stmt = con.prepareStatement(sql);
						 ResultSet resultSet = stmt.executeQuery();						
						while (resultSet.next()) {							
							ReporteVO  report = new ReporteVO();
							//Numero,Rec,Ced,Secuencial,Id_Proceso,Fec_Reg,Tipo_Mje,Ultimo_Mensaje
							report.setNumero(resultSet.getString(1));
							report.setReceptor(resultSet.getString(2));
							report.setDonante(resultSet.getString(3));
							report.setNumero_secuencial(resultSet.getString(4));
							report.setUltimo_proceso(resultSet.getString(5));
							report.setFecha(resultSet.getString(6));
							report.setMensaje(resultSet.getString(7));
							report.setUltimoMensaje(resultSet.getString(8));
							lista.add(report);
							}
						
					} catch (Exception e) { 
						throw new DAOException(e.getMessage(), e);
					}
					finally
					{
						try
						{con.close();}catch(SQLException e)
						{e.printStackTrace();log.error(e);}
					}
			break;
		default:
			break;
		}
		
		 return lista;
	 }

	 
	 public String ActualizarJobReporte(JobReporteVO consulta,String indicadorProc) throws Exception {				
			Object[] objectL = new Object[1];
			String codigo = "";
			Connection con = null;
			
			log.info("[UPD Proceso JOB REPORTE] ==> " +
			"==>IDJOB "+ consulta.getIdjob());	
			
			ParameterCollection inputCollection = new ParameterCollection();
			inputCollection.addInput(consulta.getIdjob(),OracleTypes.NUMBER);
			inputCollection.addInput(indicadorProc,OracleTypes.VARCHAR);						
			
			ParameterCollection outputCollection = new ParameterCollection();
			outputCollection.addOutput(OracleTypes.VARCHAR);  	
	        con =ConexionAPI.getInstance().getConnection();	
			String resultado="";
			try {
				objectL = QueryUtil.executeProcedure(con, ValueConstants.PK_PORTABILIDAD.concat(".SP_UPD_JOB_REPORTE"), inputCollection, outputCollection);
				codigo = String.valueOf(objectL[0]);
				con.commit();
				resultado="OK";
			} catch (Exception e) {
				log.info("Mensaje al UPD JobReporte:"+e.getMessage());
				con.rollback();
				resultado="Error en la operación";
			}
			log.info("Resultado de la Actualizacion ="+resultado);
	    return resultado;
	}	
	 
	 public static List<ReporteVO> consultaDetalleFacturacion(JobReporteVO consulta,String tipo) throws Exception {
		 List<ReporteVO> lista = new ArrayList<ReporteVO>(); 
		Connection con = null;		
		 String sql = "";
		 
			sql ="SELECT OD.OPERADOR, "+
				"	OD.CONCEPTO_FACTURA ||' - '||DECODE(OD.CONCEPTO_FACTURA,'01','Portado','02','Objecciones','03','Retorno','NA') AS CONCEPTO_FACTURA, "+
				"	OD.ID_PROCESO,to_char(OD.FECHA,'dd/mm/yyyy') AS FECHA, "+
				"	ODN.INICIO_RANGO, ODN.FINAL_RANGO, ODN.TOTAL_RANGO, "+
				"	NVL((SELECT CAUSA_RECHAZO FROM MENSAJE_RABDCP "+
				"	WHERE CAUSA_RECHAZO='REC01ABD08' AND ID_INTERNO= "+
				"	(SELECT MAX(ID_INTERNO) FROM CABECERA_MENSAJE CA WHERE "+
				"	CA.TIPO_MENSAJE='RABDCP' AND  "+
				"	CA.ID_PROCESO=OD.ID_PROCESO AND "+
				"	'VABDCP' NOT IN  "+
				"	(SELECT TIPO_MENSAJE FROM CABECERA_MENSAJE WHERE ID_PROCESO=CA.ID_PROCESO))),' ') "+
				"	AS MENSAJE "+
				"	FROM FACTURA_OPERADORES_DETALLE OD, FACTURA_OPERADORES_DETALLE_NUM ODN "+
				"	WHERE "+
				"	OD.ID_INTERNO=ODN.ID_INTERNO AND "+
				"	OD.OPERADOR='"+tipo+"' AND "+
				"	OD.FECHA BETWEEN "+
				"	TO_DATE('"+consulta.getFechaInicio()+" 00:00:00','dd/mm/yyyy HH24:mi:ss') AND "+
				"	TO_DATE('"+consulta.getFechaFin()+" 23:59:59','dd/mm/yyyy HH24:mi:ss') "+
				"	ORDER BY OD.CONCEPTO_FACTURA,OD.ID_PROCESO";		
			
			System.out.println("SQL="+sql);
			
			try {
				con = JdbcUtilitario.getInstance().getConnection();
				PreparedStatement stmt = con.prepareStatement(sql);
				ResultSet resultSet = stmt.executeQuery();

				while (resultSet.next()) {					
					ReporteVO  report = new ReporteVO();
					report.setOperador(resultSet.getString(1));		
					report.setConceptoFactura(resultSet.getString(2));
					report.setIdProceso(resultSet.getString(3));
					report.setFecha(resultSet.getString(4));
					report.setRangoInicial(resultSet.getString(5));
					report.setRangoFinal(resultSet.getString(6));
					report.setTotalRango(resultSet.getString(7));
					report.setMensaje(resultSet.getString(8));
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

	 
}
