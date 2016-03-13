package pe.com.nextel.provisioning.framework.conexion;

import java.util.ResourceBundle;
public interface Parametros 
{
  /* Parametros para la conexion con Oracle */
  //ResourceBundle ParmGene = ResourceBundle.getBundle("pe.com.nextel.provisioning.util.provisioning");
  ResourceBundle ParmGene = ResourceBundle.getBundle("pe.com.nextel.provisioning.view.framework");

  String DRIVER_DB=ParmGene.getString("DRIVER_DB");
  String SERVER=ParmGene.getString("SERVER");
  String PUERTO=ParmGene.getString("PUERTO_DB");
  String SID=ParmGene.getString("SID");
  String USUARIO_DB=ParmGene.getString("USUARIO_DB");
  String PASSWORD_DB=ParmGene.getString("PASSWORD_DB");
  String MODO_CONECCION=ParmGene.getString("MODO_CONECCION");
  String URL_JDBC=ParmGene.getString("URL_JDBC");
  String DATASOURCE=ParmGene.getString("DATASOURCE");
  
  String USUARIO_BPM=ParmGene.getString("USUARIO_BPM");
  String PASSWORD_BPM=ParmGene.getString("PASSWORD_BPM");
  //PARA CONECCION DE API
  String DRIVER_API=ParmGene.getString("DRIVER_API");
  String SERVER_API=ParmGene.getString("SERVER_API");
  String PUERTO_API=ParmGene.getString("PUERTO_API");
  String SID_API=ParmGene.getString("SID_API");
  String USUARIO_API=ParmGene.getString("USUARIO_API");
  String PASSWORD_API=ParmGene.getString("PASSWORD_API");
  String MODO_CONECCION_API=ParmGene.getString("MODO_CONECCION_API");
  String DATASOURCE_API=ParmGene.getString("DATASOURCE_API");

  
  String SERVER_TEST=ParmGene.getString("SERVER_TEST");
  String USUARIO_DB_XCE=ParmGene.getString("USUARIO_DB_XCE");
  String PUERTO_TEST=ParmGene.getString("PUERTO_TEST");
  String SID_TEST=ParmGene.getString("SID_TEST");

  
}
