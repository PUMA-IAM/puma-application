/*******************************************************************************
 * Copyright 2014 KU Leuven Research and Developement - iMinds - Distrinet 
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 * 
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *    
 *    Administrative Contact: dnet-project-office@cs.kuleuven.be
 *    Technical Contact: maarten.decat@cs.kuleuven.be
 *    Author: maarten.decat@cs.kuleuven.be
 ******************************************************************************/
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
