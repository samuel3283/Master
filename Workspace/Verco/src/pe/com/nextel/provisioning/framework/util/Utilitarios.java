package pe.com.nextel.provisioning.framework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.com.nextel.provisioning.view.ValueConstants;

public class Utilitarios {
	private static final Log log = LogFactory.getLog(Utilitarios.class);
	
	public static String mensaje = "";
	
	public static String getMessage(String propertieName,String msgId){
		
		ResourceBundle bundle = ResourceBundle.getBundle(propertieName);
		
		mensaje = bundle.getString(msgId);		
		if(mensaje == null || mensaje.length() <= 0)
		{
			mensaje = "";
		}
				
		return mensaje;
		
	}
	
	public static Date stringToDate(String cadena,String format){
		SimpleDateFormat sdf= new SimpleDateFormat(format);//"dd/MM/yyyy","dd/MM/yyyy hh:mmAM"
		Date date = null;
		
		if(cadena != null){
			cadena = cadena.trim();
			cadena = cadena.replaceAll("-", "/");
			
			String fecha[];
			
			if(cadena.length()>10){
				fecha = cadena.split(" ");
				
				String evaluar[] = fecha[0].split("/");
				
				if(evaluar[0].length()>2){
					fecha[0] = evaluar[2]+"/"+evaluar[1]+"/"+evaluar[0];
					cadena = fecha[0] + " " + fecha[1];
				}
				
			}

			if(!"".equals(cadena)){
				int posicionPunto = cadena.indexOf(ValueConstants.PUNTO);
				
				if(posicionPunto > 0){
					cadena = cadena.substring(0, posicionPunto);
				}
				
				int posicionPM = cadena.indexOf(ValueConstants.HORA_PM);
				int posicionAM = cadena.indexOf(ValueConstants.HORA_AM);
					
				if(posicionPM > 0){
					cadena = cadena.substring(0, posicionPM);
				}
				if(posicionAM > 0){
					cadena = cadena.substring(0, posicionAM);
				
				}
				try{
					date = sdf.parse(cadena);
				} catch (ParseException ex) {
					ex.printStackTrace();
					return new Date();
				}
			}
		}
		return date;
	}
		
	public static String dateToString(Date fecha,String format){
		SimpleDateFormat formato = new SimpleDateFormat(format);//"dd/MM/yyyy"
		String cadenaFecha = formato.format(fecha);
		return cadenaFecha;
	}
	
	public static boolean esNumerico(String cadena){	
		
		boolean resultado=true;
		try {	
		//Integer.parseInt(cadena);	
		Long.parseLong(cadena);
		} 
		catch (NumberFormatException nfe){		
		resultado=false;	}
		
		return resultado;
	
	}		
	
	/*
	public static void main(String[] args){
		
		System.out.println("VAL:::"+esNumerico("A21"));
	}*/
		
		
	
}
