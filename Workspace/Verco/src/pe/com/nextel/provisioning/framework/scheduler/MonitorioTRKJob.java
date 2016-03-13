package pe.com.nextel.provisioning.framework.scheduler;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jcraft.jsch.JSchException;

import pe.com.nextel.provisioning.model.bo.MonitoreoBO;
import pe.com.nextel.provisioning.model.bo.ParametrosBO;



public class MonitorioTRKJob implements Job {
private static final Log log = LogFactory.getLog(MonitorioTRKJob.class);
	

	public void execute(JobExecutionContext context)
            throws JobExecutionException {
		
		/*
       log.info("Init Daemon ==> " + context.getJobDetail().getJobDataMap().getString("name")+" ==> ");
    	
       MonitoreoBO.getInstance().ejecutarJobMonitoreoTRK();
       MonitoreoBO.getInstance().ejecutarJobMonitoreoTRKNoProceso();     	*/
        
	}


}