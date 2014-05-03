package puma.application.authz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import puma.applicationpdp.ApplicationPEP;
import puma.rmi.pdp.mgmt.PDPRegistryRemote;

/**
 * This class is used to initialize the Application PDP from the web
 * application.
 * 
 * @author Maarten Decat
 * 
 */
public class PDPInitializer implements ServletContextListener {

	private static final String PUMA_PDP_MGMT_HOST = "puma-pdp-mgmt";

	private static final String APPLICATION_PDP_REGISTRY_RMI_NAME = "application-pdp-registry";

	private static final int APPLICATION_PDP_REGISTRY_RMI_PORT = 2050;

	//private static final String POLICY_DIR = "/home/maartend/PhD/code/workspace-jee/puma-application-pdp/src/main/resources/policies/";

	private static final Logger logger = Logger.getLogger(PDPInitializer.class
			.getName());

	private static final String POLICY_PROPERTY = "puma.application.policydir";
	private static final String SILENT_MODE_PROPERTY = "puma.application.silent"; 

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// deregister the application PDP
		if (isApplicationPDPRegistryConnectionOK()) {
			try {
				applicationPDPRegistry.deregister(ApplicationPEP.getInstance());
			} catch (RemoteException exc) {
				logger.log(
						Level.SEVERE,
						"RemoteException when deregistering the Application PDP",
						exc);
			}
			logger.info("Deregistered the Application PDP");
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent e) {
		// initialize the PDP with all policies in the policy directory
		try {
			initializeProperties(e);
			ApplicationPEP.getInstance().initializePDP(getPolicyDir());
			logger.info("initialized application PDP");
		} catch(Exception exc) {
			logger.log(Level.SEVERE, "Error when setting up PEP", exc);
			return;
		}
		// register this application PDP/PEP with the central management server
		if (setupApplicationPDPRegistryConnection()) {
			registerWithPDPRegistry();
		} else {
			logger.info("Retrying to reach the Registry periodically");
			// retry periodically
			Thread thread = new Thread(new Runnable() {				
				@Override
				public void run() {
					while(true) {
						if(setupApplicationPDPRegistryConnection()) {
							registerWithPDPRegistry();
							return; // end the thread here
						} else {
							logger.info("Failed again, trying again in 5 sec");
							try {
								Thread.sleep(5000);
							} catch (InterruptedException e) {
								logger.log(Level.WARNING, "Sleep interrupted, is this important?", e);
							}
						}
					}
				}
			});
			thread.start();
		}
		Boolean silent = false;
		if (System.getProperty(SILENT_MODE_PROPERTY) != null) {
			silent = Boolean.parseBoolean(System.getProperty(SILENT_MODE_PROPERTY));
		}
		if (silent) {
			logger.log(Level.INFO, "Now switching to silent mode");
			LogManager.getLogManager().reset();			
		}
	}

	private void initializeProperties(ServletContextEvent e) {
		String subPath = "WEB-INF/.properties";
		File propertiesFile = new File(e.getServletContext().getRealPath(subPath));
		try {
			Properties p = new Properties();
			if (propertiesFile.exists()) {
				logger.info("Fetching properties from " + propertiesFile);
				p.load(new FileInputStream(e.getServletContext().getRealPath(subPath)));
				System.setProperty(POLICY_PROPERTY, p.getProperty(POLICY_PROPERTY));
			} else {
				logger.warning("Properties file not found at " + propertiesFile.getAbsolutePath());
			}
		} catch (FileNotFoundException e1) {
			logger.warning("File not found at " + propertiesFile.getAbsolutePath());
		} catch (IOException e1) {
			logger.warning("Could not read properties file");
		}
	}

	private String getPolicyDir() {
		//return POLICY_DIR;
		String dir = System.getProperty(POLICY_PROPERTY);
		if(dir == null) {
			logger.severe("System property \"puma.application.policydir\" not found, no policies will be loaded.");
		}
		return dir;
	}

	private PDPRegistryRemote applicationPDPRegistry;

	/**
	 * Idempotent helper function to set up the RMI connection to the central
	 * Application PDP Registry.
	 */
	private boolean setupApplicationPDPRegistryConnection() {
		if (!isApplicationPDPRegistryConnectionOK()) { //
			try {
				Registry registry = LocateRegistry.getRegistry(
						PUMA_PDP_MGMT_HOST, APPLICATION_PDP_REGISTRY_RMI_PORT);
				applicationPDPRegistry = (PDPRegistryRemote) registry
						.lookup(APPLICATION_PDP_REGISTRY_RMI_NAME);
				logger.info("Set up the Application PDP Registry");
				return true;
			} catch (Exception e) {
				logger.log(Level.WARNING,
						"FAILED to reach the Application PDP Registry", e);
				applicationPDPRegistry = null; // just to be sure
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Helper function to register this Application PDP with the Application PDP Registry.
	 */
	private void registerWithPDPRegistry() {
		try {
			UnicastRemoteObject.exportObject(
					ApplicationPEP.getInstance(), 0);
			applicationPDPRegistry.register(ApplicationPEP.getInstance());
			logger.info("Registered the Application PDP");
		} catch (RemoteException exc) {
			logger.log(Level.SEVERE,
					"RemoteException when registering the Application PDP",
					exc);
		}
	}

	/**
	 * Helper function that returns whether the RMI connection to the
	 * Application PDP Registry is set up or not.
	 */
	private boolean isApplicationPDPRegistryConnectionOK() {
		return applicationPDPRegistry != null;
	}

}
