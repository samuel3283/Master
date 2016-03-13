package pe.com.nextel.provisioning.framework.scheduler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import pe.com.nextel.provisioning.model.bo.ReporteBO;

public class ReporteJob implements Job {
	private static final Log log = LogFactory.getLog(ReporteJob.class);
	
	public void execute(JobExecutionContext context)
    throws JobExecutionException {
		log.info("Revision de Job Reportes...");			
		try {
			ReporteBO.getInstance().ejecutarJobReporte();
		} catch (Exception e) {
			e.printStackTrace();
		}			
		
	}
}
