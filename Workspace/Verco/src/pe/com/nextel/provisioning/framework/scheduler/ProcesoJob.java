package pe.com.nextel.provisioning.framework.scheduler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import pe.com.nextel.provisioning.model.bo.ProcesoBO;

public class ProcesoJob implements Job {

	private static final Log log = LogFactory.getLog(ProcesoJob.class);    
	
	public ProcesoJob() {

	}
	
	public void execute(JobExecutionContext context) {
    	/*
		log.info("Init Daemon ==>Proceso Obtención Numeración Portada e Historico... .:. ");
       	ProcesoBO.getInstance().iniciarProceso();*/
		
	}
	
	
	
	
}
