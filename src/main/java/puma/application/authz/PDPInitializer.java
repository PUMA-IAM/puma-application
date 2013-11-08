package puma.application.authz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import puma.applicationpdp.ApplicationPEP;

/**
 * This class is used to initialize the Application PDP from the web
 * application.
 * 
 * @author Maarten Decat
 * 
 */
public class PDPInitializer implements ServletContextListener {

	private static final Logger logger = Logger.getLogger(PDPInitializer.class
			.getName());

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// nothing to do
	}

	@Override
	public void contextInitialized(ServletContextEvent e) {
		File dir = new File(
				"/home/maartend/PhD/code/workspace-jee/puma-application-pdp/src/main/resources/policies/"); // TODO use a system property or something for this
		File[] policyFiles = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String filename) {
				return filename.endsWith(".xml");
			}
		});
		
		Collection<InputStream> policies = new LinkedList<InputStream>();
		for(File policyFile: Arrays.asList(policyFiles)) {
			try {
				policies.add(new FileInputStream(policyFile));
			} catch (FileNotFoundException e1) {
				logger.log(Level.WARNING, "File not found when reading policy files?", e1);
			}
		}

//		De vorige manier van werken (werkte niet :P)
//		// MDC: wat een kutmanier om dit te doen zeg
//		Set<String> fileNames = e.getServletContext().getResourcePaths(
//				"/policies/");
//		Collection<InputStream> policies = new LinkedList<InputStream>();
//		if (fileNames == null) {
//			logger.warning("no policies found!");
//			return;
//		} else {
//			for (String fileName : fileNames) {
//				// only add the correct filenames to the result
//				if (fileName.endsWith(".xml")) {
//					policies.add(e.getServletContext().getResourceAsStream(
//							"/policies/" + fileName));
//				}
//			}
//		}
		
		ApplicationPEP.getInstance().initializePDP(policies);
		logger.info("initialized application PDP");
	}

}
