package pe.com.nextel.provisioning.view;

public class ValueConstants {

	/*Parametros del Scheduler*/
	public static String GRUPO_TRIGGER="JobCargaGrupoTrigger";
	public static String GRUPO_JOB="GrupoCargar";
	
	public static String PK_PORTABILIDAD="PK_PORTABILIDAD";
	public static String JOB_INICIAR_JOB="IniciarAprovisionamientoJob";
	public static String JOB_CARGAR_JOB="JobCargar";
	
	public static String TRIGGER_INICIAR_JOB="TriggerIniciarAprovisionamiento";
	public static String TRIGGER_CARGAR_JOB="JobCargaTrigger";
	
	public static String PK_PROVISIONING_LOGIN="PK_PROVISIONING_LOGIN";
	/*
	public static String PK_PROVISIONING_PROCESO="PK_PROVISIONING_PROCESO";
	public static String PK_PROVISIONING_RF="PK_PROVISIONING_RF";
	public static String PK_PROVISIONING_CNC="PK_PROVISIONING_CNC";
	*/
	public static String PK_PROVISIONING_PROCESO="PK_PROVISIONING";
	public static String PK_PROVISIONING_RF="PK_PROVISIONING";
	public static String PK_PROVISIONING_CNC="PK_PROVISIONING";
	public static String PK_MONITOREO="PK_NEXTEL";
	public static String PK_MONITOREO_PORTABILIDAD="PK_MONITOREO_PORTABILIDAD";
	
	public static String ORIGEN_ARCHIVO_FP="SolicitudesProgramadas_";
	public static String ORIGEN_ARCHIVO_FR="RetornosProgramados_";
	
	public static String DESTINO_ARCHIVO_FP="FP_";
	public static String DESTINO_ARCHIVO_FR="FR_";
	
	public static String ARCHIVO_FP="FP";
	public static String ARCHIVO_FR="FR";	
	public static String ARCHIVO_FPWS="FPWS_";
	public static String ARCHIVO_FRWS="FRWS_";
	

	public static String USUARIO_WS="USUARIO_WS";
	public static String PASSWORD_WS="PASSWORD_WS";
	
	public static final String VALOR_CERO="0";
	public static final String VALOR_UNO="1";
	public static final String VALOR_DOS="2";
	public static final String VALOR_TRES="3";
	
	public static String COMANDO_ADD_USR="ADD_USR";
	public static String COMANDO_RMV_USR="RMV_USR";

	public static String LABEL_WS_USERNAME="USERNAME";
	public static String LABEL_WS_PASSWORD="PASSWORD";
	public static String LABEL_WS_NUMBER="Number";
	public static String LABEL_WS_HLRADDRESS="HLRAddress";
	public static String LABEL_WS_NUMBERTYPE="NumberType";
	public static String LABEL_WS_NEWROUTE="NewRoute";
	
	public static String PROCESO_CUATRO_RUTA_DESTINO="DESTINO_DELAY";

	/**************************************************************
	/** CONSTANTES Forward **/
	public static final String INICIO 		      = "inicio";
	public static final String FW_LISTAR 		  = "listar";	
	public static final String FW_LISTAR_REENVIO  = "listarReenvio";	
	public static final String FW_LISTAR_CONSULTA1  = "listarConsulta1";
	public static final String FW_LISTAR_CONSULTA2  = "listarConsulta2";
	public static final String FW_LISTAR_CONSULTAM  = "listarConsultaM";
	public static final String FW_LISTAR_CONSULTA3  = "listarConsulta3";	

	public static final String FW_DETALLE_REENVIO = "detalleReenvio";	
	public static final String FW_INGRESAR_EDITAR = "ingresarEditar";
	public static final String FW_REPROCESO_INDIVIDUAL 	= "reprocesoIndividual";
	public static final String FW_REPROCESO_INCREMENTAL 	= "reprocesoIncremental";
	
	public static final String FRAMEWORK_PROPERTIES 	= "pe.com.nextel.provisioning.util.provisioning";
	public static final String PROCESO_UNO_DIRECTORIO_DESTINO	= "PROCESO.UNO.DIRECTORIO.DESTINO";
	
	/** CONSTANTES Posicion  **/
	public static final String INICIO_INDEX_FECHA_GENERACION	= "0";
	public static final String INDEX_CANTIDAD_REGISTROS	= "8";
	public static final String INICIO_INDEX_NUMERO_SOLICITUD_FP	= "0";
	public static final String FIN_INDEX_NUMERO_SOLICITUD_FP	= "16";
	public static final String INICIO_INDEX_NUMERO_TELEFONO_FP	= "17";
	public static final String FIN_INDEX_NUMERO_TELEFONO_FP	= "28";
	public static final String INICIO_INDEX_FECHA_EJECUCION_FP	= "29";
	public static final String FIN_INDEX_FECHA_EJECUCION_FP	= "42";
	public static final String INICIO_INDEX_RECEPTOR_FP	= "43";
	public static final String FIN_INDEX_RECEPTOR_FP	= "44";
	public static final String INICIO_INDEX_CEDENTE_FP	= "45";
	public static final String FIN_INDEX_CEDENTE_FP	= "46";
	
	public static final String INICIO_INDEX_NUMERO_TELEFONO_FR	= "0";
	public static final String FIN_INDEX_NUMERO_TELEFONO_FR	= "11";
	public static final String INICIO_INDEX_FECHA_EJECUCION_FR	= "12";
	public static final String FIN_INDEX_FECHA_EJECUCION_FR	= "25";
	public static final String INICIO_INDEX_FECHA_COMUNICACION_FR	= "26";
	public static final String FIN_INDEX_FECHA_COMUNICACION_FR	= "39";
	public static final String INICIO_INDEX_RECEPTOR_FR	= "40";
	public static final String FIN_INDEX_RECEPTOR_FR	= "41";
	public static final String INICIO_INDEX_CEDENTE_INICIAL_FR	= "42";	
	public static final String FIN_INDEX_CEDENTE_INICIAL_FR	= "43";
	public static final String INICIO_INDEX_MOTIVO_RETORNO_FR	= "44";
	public static final String FIN_INDEX_MOTIVO_RETORNO_FR	= "45";
	
	public static String PK_NEXTEL="PK_NEXTEL";
	
	public static final String WORLD_NUMBER	= "worldNumber";
	public static final String TIPO_TECNOLOGIA	= "tipoTecnologia";
	public static final String ID_CABECERA	= "idCabecera";	
	public static final String ARCHIVO	= "archivo";
	
	public static final String PUNTO = ".";
	public static final String HORA_PM = "PM";
	public static final String HORA_AM = "AM";	
	public static final String CODIGO_HOST_PORTANODE	= "HPN";
	public static final String CODIGO_USERNAME_PORTANODE	= "UPN";
	public static final String CODIGO_PASSWORD_PORTANODE	= "PPN";	
	public static final String CODIGO_RUTA_ORIGEN_PROCESO_UNO	= "RO01";
	public static final String CODIGO_RUTA_DESTINO_PROCESO_UNO	= "RUTA01";
	public static final String CODIGO_RUTA_ORIGEN_PROCESO_TRES  = "RUTA01";
	public static final String CODIGO_RUTA_DESTINO_PROCESO_TRES  = "RUTA02";
  	public static final String CODIGO_RUTA_ORIGEN_PROCESO_SEIS  = "RUTA04";
		
	/* CNC */
	public static String ESTADO_BANDEJA_SALIDA="1";
	public static String RESULT="OK";
	public static String CODIGO_ERROR="000" ;

	public static final String ACCION_REGISTRAR = "R";
	public static final String ACCION_MODIFICAR = "M";
	public static final String ACCION_DETALLE = "D";
	public static final String ACCION_CANCELAR 	= "C";
	public static final String ACCION_ELIMINAR = "E";
	public static final String CASO_UNO = "1";
	public static final String CASO_DOS = "2";
	public static final String CASO_TRES = "3";
	public static final String CASO_CUATRO = "4";
	public static final String TIPO_REPROCESO = "tipoReproceso";
	public static final String ESTADO_PROCESO = "estadoProceso";	
	public static final String CODIGO_REPROCESO_INCREMENTAL = "REPINC";
	public static final String CODIGO_REPROCESO_INDIVIDUAL = "REPIND";
	public static final String CADENA_ESTADOS = "P001P002P003P004P005P006";
	public static final String CODIGO_PROCESO1 = "P001";
	public static final String CODIGO_PROCESO2 = "P002";
	public static final String CODIGO_PROCESO3 = "P003";
	public static final String CODIGO_PROCESO4 = "P004";
	public static final String CODIGO_PROCESO5 = "P005";
	public static final String CODIGO_PROCESO6 = "P006";
	public static final String CARACTER_PROCESO = "P";
	public static final String PROCESO_PORTABILIDAD = "P";
	public static final String PROCESO_RETORNO_FRAUDE = "RF";
	public static final String PROCESO_RETORNO_TERMINACION_CONTRATO = "RT";
	public static final String CODIGO_RETORNO_FRAUDE = "01";
	public static final String CODIGO_RETORNO_TERMINACION_CONTRATO = "02";	
	public static final String FIN_FICHERO = "EOF";
	public static final String FORMATO_CANTIDAD_REGISTROS = "000000";
	public static final String PRUEBA_TIPO_ARCHIVO = "fr";
	public static final String PRUEBA_FECHA = "PRUEBA.FECHA";
	public static final String CARACTER_DOBLE_SLASH = "//";
	public static final String VALOR_OK="OK";
	public static final String CON_MENSAJE=" CON EL SIGUIENTE MENSAJE DE ERROR: ";
	
	
	/** CONSTANTES PARA SESSION SCOPE **/
	public static final String LISTA_TIPO_CONTACTO	 		= "listaTipoContacto";
	public static final String LISTA_CONTACTOS	 		    = "listaContactos";	
	public static final String LISTA_PARAMETROS	 		    = "listaParametros";	
	public static final String LISTA_REPROCESO	 		    = "listaReproceso";
	public static final String LISTA_PROCESO	 		    = "listaProceso";
	public static final String LISTA_REENVIO	 = "listaReenvio";
	public static final String LISTA_ESTADO_SOAP = "listaEstadoSoap";
	
	public static final String LISTA_CONSULTA1 = "listaConsulta1";
	public static final String LISTA_CONSULTA2 = "listaConsulta2";
	public static final String LISTA_RECHAZO   = "listaRechazo";
	public static final String LISTA_PORTADO   = "listaPortado";
	public static final String LISTA_NUMERO_PORTADO   = "listaNumeroPortado";
	public static final String LISTA_CONSULTAM = "listaConsultam" ;
	public static final String LISTA_CONSULTAM1 = "listaConsultam1"	;
	public static final String FECHA_PROCESO = "fechaProceso"	;
	
	/* ERRORES TIPIFICADOS */
	public static final String PROCESO_SATISFACTORIO = "OK";
	public static final String ERROR_CONTROLADO = "ERRP000000";
	public static final String ERROR_CONEXION_FTP = "ERRP010001";
	public static final String ERROR_ARCHIVOS_NOEXISTE_FTP = "ERRP010002";
	public static final String ERROR_DESCARGA_INCORRECTA_ARCHIVO_FTP = "ERRP010003";
	public static final String ERROR_INVOCAR_API_TECNOLOGIA = "ERRP020002";
	public static final String ERROR_INVOCAR_API_WORLDNUMBER = "ERRP020001";
	public static final String ERROR_INVOCAR_API_SERIEORIGEN = "ERRP020003";
	public static final String ERROR_CAUSAS_TECNICAS = "ERRTEC0000";
	public static final String ERROR_NOEXISTE_ARCHIVO_RUTAORIGEN = "ERRP030001";
	public static final String ERROR_NOEXISTE_ARCHIVO_RUTADESTINO = "ERRP030002";
	
	public static final String ERROR_ARCHIVO_INCORRECTA_COPIA = "ERRP030003";

	public static final String ERROR_SERVICIO_NO_DISPONIBLE = "9000";
	public static final String ERROR_ENVIADO_SIN_RESPUESTA = "9001";

	public static final String ERROR_OPERATION_SUCCESS = "0";
	public static final String ERROR_SYSTEM_ERROR = "5000";
	public static final String ERROR_PARAMETRO_OBLIGATORIO_NULO = "5004";
	public static final String ERROR_PARAMETRO_ENTRADA_INVALIDO_NUMBER = "5112";
	public static final String ERROR_PARAMETRO_ENTRADA_INVALIDO_TODOS = "5010";
	public static final String ERROR_PARAMETRO_ROUTING_OBLIGATORIO_NULO = "5103";
	public static final String ERROR_TIPO_USUARIO_RED_EXTERNA = "5013";
	public static final String ERROR_TIPO_USUARIO_RED_LOCAL = "5012";


	public static final String ERROR_ARCHIVO_COPIA_INCORRECTA = "ERRP040002";
	public static final String ERROR_ARCHIVO_CREACION_INCORRECTA = "ERRP040003";
	
	
	public static final String ESTADO_INACTIVO = "0";
	public static final String ESTADO_ACTIVO = "1";
	public static final String ESTADO_FINALIZADO = "2";
	public static String PROCESO_CINCO_RUTA_ORIGEN="RUTA03";
	public static String PROCESO_CINCO_RUTA_DESTINO="RUTA04";
	public static final String STATUS = "status";
	public static final String MESSAGE = "message";
	public static String CODIGO_USUARIO_WS ="UWS";
	public static String CODIGO_PASSWORD_WS ="PWS";
	public static String CODIGO_RUTA_ORIGEN_PROCESO_CUATRO="RUTA01";
	public static String CODIGO_RUTA_DESTINO_PROCESO_CUATRO="RUTA03";
	public static final String ERROR = "ERROR";
	public static final String ERR = "ERR";
	
	
	public static final String ERROR_DIRECTORIO_NOEXISTE = "ERRP000001";
	public static final String ERROR_DIRECTORIO_SINPERMISO_READ = "ERRP000002";
	public static final String ERROR_DIRECTORIO_SINPERMISO_WRITE = "ERRP000003";
	public static final String ERROR_DIRECTORIO_SINPERMISO_EXECUTE = "ERRP000004";
	public static final String ERROR_CONEXION_API = "ERRP000005";
	public static final String ERROR_CONEXION_PROVISIONING = "ERRP000006";
	public static final String ERROR_CONEXION_SMARTKEY = "ERRP000007";
	public static final String ERROR_ACCESO_FTP = "ERRP000008";
	public static final String ERROR_PARAMETROS_NULOS = "ERRP000009";
	
	public static final String REPROCESO_PROCESAR_FECHA_ANTERIOR = "1";
	public static final String REPROCESO_INCREMENTAL = "2";
	public static final String REPROCESO_MANUAL = "3";
	public static final String ERROR_ARCHIVO_INCONSISTENTE = "ERRP010004";	
	public static final String ERROR_FORMATO_ARCHIVO = "ERRP010005";
	public static final String ERROR_COMPRESION_ARCHIVO = "ERRP010006";
	

	public static String RUTA_DESTINO_PROCESO_UNO="RUTA01";
	public static String RUTA_DESTINO_PROCESO_TRES="RUTA02";
	public static String RUTA_DESTINO_PROCESO_CUATRO="RUTA03";
	public static String RUTA_DESTINO_PROCESO_CINCO="RUTA04";
	

	public static String DESCRIPCION_ERROR_ARCHIVO_FORMATO_FECHA="Fecha de cabecera de archivo no concuerda con nombre de archivo. ";
	public static String DESCRIPCION_ERROR_ARCHIVO_FORMATO_TELEFONO="Número de Celular contiene valores no válidos. ";
	public static String DESCRIPCION_ERROR_ARCHIVO_FORMATO_RECEPTOR="Código de Operador Receptor contiene valores no válidos. ";
	public static String DESCRIPCION_ERROR_ARCHIVO_FORMATO_CEDENTE="Código de Operador Cedente contiene valores no válidos. ";
	public static String DESCRIPCION_ERROR_ARCHIVO_RECEPTOR_CEDENTE="Código de Operador Receptor y Cedente son iguales. ";
	

	public static String DESCRIPCION_ERROR_ARCHIVO_INCONSISTENTE_VACIO="Archivo vacio. ";
	public static String DESCRIPCION_ERROR_ARCHIVO_INCONSISTENTE_CANTIDAD="Cantidad de Registros no concuerda con el descrito en la cabecera del archivo. ";
	public static String DESCRIPCION_ERROR_ARCHIVO_INCONSISTENTE_FECHA="Fecha de Cabecera no concuerda con la fecha del nombre de archivo. ";

	public static String MENSAJE_EMAIL_ERROR="Error en el Aplicativo de Aprovisionamiento";

	public static String DESCRIPCION_ERROR_ARCHIVO_INCONSISTENTE="Archivo no contiene el formato establecido. ";
	public static int LONGITUD_ARCHIVO_FP=47;
	public static int LONGITUD_ARCHIVO_FR=47;
}
