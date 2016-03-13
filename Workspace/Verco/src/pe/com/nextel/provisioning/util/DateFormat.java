package pe.com.nextel.provisioning.util;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class DateFormat {

	private static final Log log = LogFactory.getLog(DateFormat.class);  
	
	public static void main(String args[]) {
		
	log.info("====>"+DateFormat.stringToTimestamp());
		
	}


	public static java.sql.Date stringToDate() {
		java.sql.Date dateSQL = null;
		java.util.Date dateUtil = null;
		try {
			
			Date fecha = new Date();
			SimpleDateFormat simpDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			simpDate.format(fecha);
			simpDate.setTimeZone(TimeZone.getTimeZone("GMT-05"));
			String string_fecha = simpDate.format(fecha);
			log.info(":::====>"+string_fecha);
			SimpleDateFormat sdfInput = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			 dateUtil = sdfInput.parse(string_fecha);
			log.info(":::====>"+dateUtil);
			dateSQL = new java.sql.Date(dateUtil.getTime());
		} catch (ParseException e) {
			log.error(e.getMessage());
			dateSQL = null;
		}
		return dateSQL;
	}
	

	public static Timestamp stringToTimestamp() {
		java.util.Date dateUtil = null;
		Timestamp ts = null;
		
		try {
			
			Date fecha = new Date();
			SimpleDateFormat simpDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			simpDate.format(fecha);
			simpDate.setTimeZone(TimeZone.getTimeZone("GMT-05"));
			String string_fecha = simpDate.format(fecha);
			log.info(":::====>"+string_fecha);
			SimpleDateFormat sdfInput = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			dateUtil = sdfInput.parse(string_fecha);
			ts = new Timestamp(dateUtil.getTime());
			log.info(":::====>"+ts);
			
		} catch (Exception e) {
			log.error(e.getMessage());
			ts = null;
		}
		return ts;
	}
	
	public static String dateToStringComp() {
		
		try{
						
			Date fecha = new Date();
			SimpleDateFormat simpDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			simpDate.format(fecha);
			simpDate.setTimeZone(TimeZone.getTimeZone("GMT-05"));
			String string_fecha = simpDate.format(fecha);
			
			SimpleDateFormat sdfInput = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date dateUtil = sdfInput.parse(string_fecha);
			String fechas = sdfInput.format(dateUtil);

			return fechas;
		}catch(Exception e){
			log.error(e.getMessage());
			return null;
		}	
			
	}
	

	public static String dateToStringReg() {
		
		try{
						
			Date fecha = new Date();
			SimpleDateFormat simpDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			simpDate.format(fecha);
			simpDate.setTimeZone(TimeZone.getTimeZone("GMT-05"));
			String string_fecha = simpDate.format(fecha);

			SimpleDateFormat sdfInput = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date dateUtil = sdfInput.parse(string_fecha);
			String fechas = sdfInput.format(dateUtil);

			return fechas;
		}catch(Exception e){
			log.error(e.getMessage());
			return null;
		}	
			
	}

	public static String dateToStringFull() {
		
		try{
				
			Date fecha = new Date();
			SimpleDateFormat simpDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			simpDate.format(fecha);
			simpDate.setTimeZone(TimeZone.getTimeZone("GMT-05"));
			String string_fecha = simpDate.format(fecha);

			SimpleDateFormat sdfInput = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			java.util.Date dateUtil = sdfInput.parse(string_fecha);
			String fechas = sdfInput.format(dateUtil);

			return fechas;
		}catch(Exception e){
			log.error(e.getMessage());
			return null;
		}	
			
	}
	
	
	
}