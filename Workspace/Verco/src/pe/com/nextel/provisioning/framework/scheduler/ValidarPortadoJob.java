package pe.com.nextel.provisioning.framework.scheduler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import pe.com.nextel.provisioning.model.bo.ProcesoBO;

public class ValidarPortadoJob implements Job {

	private static final Log log = LogFactory.getLog(ValidarPortadoJob.class);    
	
	public ValidarPortadoJob() {

	}
	
	public void execute(JobExecutionContext context) {
    	/*
		log.info("Init Daemon ==>Proceso Validar Tabla Numeración Portada... .:. ");
       	ProcesoBO.getInstance().procesoNumeracionPortada();*/
		
	}
	
	
	
	
}
