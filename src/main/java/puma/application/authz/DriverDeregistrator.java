package puma.application.authz;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;

/**
 * This class is used to initialize the Application PDP from the web application.
 * 
 * @author Maarten Decat
 *
 */
public class DriverDeregistrator implements ServletContextListener {
	
	private static final Logger logger = Logger
			.getLogger(DriverDeregistrator.class.getName());

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// MDC: against the permgen error: deregister all drivers at context destroy moment.
		// Source: http://stackoverflow.com/a/19027873
		try {
			AbandonedConnectionCleanupThread.shutdown();
			logger.info("Deregistered drivers at contextDestroyed");
		} catch (InterruptedException e) {
			logger.log(Level.WARNING, "error when cleaning up abandoned connection threads", e);
		}
		
		// Other option:
		// Source: http://stackoverflow.com/questions/3320400/to-prevent-a-memory-leak-the-jdbc-driver-has-been-forcibly-unregistered
//		Enumeration<Driver> drivers = DriverManager.getDrivers();
//        while (drivers.hasMoreElements()) {
//            Driver driver = drivers.nextElement();
//            try {
//                DriverManager.deregisterDriver(driver);
//                logger.log(Level.INFO, String.format("deregistering jdbc driver: %s", driver));
//            } catch (SQLException e) {
//                logger.log(Level.SEVERE, String.format("Error deregistering driver %s", driver), e);
//            }
//
//        }
	}

	@Override
	public void contextInitialized(ServletContextEvent e) {
		// nothing to do
	}

}
