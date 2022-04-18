package com.spirebase.daemons;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@WebListener
public class MainDaemon implements ServletContextListener {
	
	private final static Logger LOG = LoggerFactory.getLogger(MainDaemon.class);
	
    @Autowired
    private MonitorFileDameon monitorFileProcessor;
	private Thread monitorFileProcessorThread;
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		LOG.info("Starting Daemons");
		
		monitorFileProcessorThread = new Thread(monitorFileProcessor);
		monitorFileProcessorThread.start();
		
		LOG.info("Started Daemons");
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
		
		
	}

}
