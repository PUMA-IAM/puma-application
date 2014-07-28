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
 * @since Jul 24, 2014 1:38:28 PM
 */
package puma.application.authz;

import peglio.modules.instrumentation.prototypes.BasicAuthorizationFactory;
import puma.applicationpdp.ApplicationPEP;
import puma.peputils.PEP;

/**
 * @author Jasper Bogaerts
 * @since Jul 24, 2014
 *
 */
public class DocumentAuthzFactoryImpl extends BasicAuthorizationFactory {

	/** 
	 * {@inheritDoc}
	 * @author Jasper Bogaerts
	 * @since Jul 24, 2014
	 * @see peglio.modules.instrumentation.util.AuthorizationFactory#buildPEP(java.lang.String)
	 **/
	@Override
	public PEP buildPEP(String pdpContactURL) {
		return ApplicationPEP.getInstance();
	}

}
