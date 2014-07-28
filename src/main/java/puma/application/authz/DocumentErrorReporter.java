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
 *    Technical Contact: jasper.bogaerts@cs.kuleuven.be
 *    Author: jasper.bogaerts@cs.kuleuven.be
 ******************************************************************************/

/**
 * 
 * @author Jasper Bogaerts
 * @since Jul 16, 2014 3:26:28 PM
 */
package puma.application.authz;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import peglio.modules.instrumentation.util.ErrorReporter;
import puma.application.webapp.msgs.MessageManager;

/**
 * @author Jasper Bogaerts
 * @since Jul 16, 2014
 *
 */
public class DocumentErrorReporter extends ErrorReporter {
	private HttpSession session;

	/**
	 * Initialize an instance of the {@link DocumentErrorReporter}
	 * @author Jasper Bogaerts
	 * @since Jul 16, 2014
	 *
	 * @param message
	 * @param redirectionUrl
	 */
	public DocumentErrorReporter(HttpSession session) {
		super();
		this.session = session;
	}

	/** 
	 * {@inheritDoc}
	 * @author Jasper Bogaerts
	 * @since Jul 16, 2014
	 * @see peglio.modules.instrumentation.util.ErrorReporter#sendMessage(javax.servlet.http.HttpServletResponse, java.lang.String, java.lang.String)
	 **/
	@Override
	public void sendMessage(HttpServletRequest request, HttpServletResponse response, String message,
			String redirectionUrl) throws IOException {
		MessageManager.getInstance().addMessage(this.session, "failure", message);
		response.sendRedirect(this.constructRedirection(request, redirectionUrl) + redirectionUrl);		
	}

	/**
	 * @author Jasper Bogaerts
	 * @since Jul 18, 2014
	 *
	 * @param request
	 * @param redirectionUrl
	 * @return
	 */
	private String constructRedirection(HttpServletRequest request,
			String redirectionUrl) {
		// This is used to match e.g. http://localhost:8080/app/docs with redirection app/docs/2 to http://localhost:8080/
		// Note that this is case specific and is not always the best way to find out the current url (read: its a hack)
		Integer index = redirectionUrl.indexOf("/");
		String currentRequestUrl = request.getRequestURL().toString();
		index = currentRequestUrl.indexOf(redirectionUrl.substring(0, index), 0);		
		return currentRequestUrl.substring(0, index);
	}

}
