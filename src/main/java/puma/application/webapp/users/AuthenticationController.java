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
package puma.application.webapp.users;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;
import puma.application.webapp.msgs.MessageManager;
import puma.peputils.Subject;
import puma.peputils.attributes.SubjectAttributeValue;

@Controller
public class AuthenticationController {

	private static final String PUMA_AUTHENTICATION_ENDPOINT = "/authn/ServiceAccessServlet";
	private static final String LOGOUT_URL = "/authn/LogoutServlet";

	@RequestMapping(value = "/user/login", method = RequestMethod.GET)
	public RedirectView login(ModelMap model,
			@RequestParam(value = "RelayState", defaultValue = "") String relayState,
			@RequestParam(value = "Tenant", defaultValue = "") String tenant, HttpSession session,
			UriComponentsBuilder builder) {
		String targetURI = PUMA_AUTHENTICATION_ENDPOINT;

		// add the RelayState. If none given, use the default.
		if (relayState.isEmpty()) {
			relayState = builder.path("/user/login-callback").build()
					.toString();
		}
		try {
			relayState = URLEncoder.encode(relayState, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		targetURI += "?RelayState=" + relayState;

		// add the Tenant if given
		if (!(tenant == null || tenant.isEmpty())) {
			// TODO get the tenant from the domain if a tenant has a sub-domain?
			targetURI += "&Tenant=" + tenant; 
		}
		
//		model.addAttribute("output", targetURI);
//		return "test";
		
		return new RedirectView(targetURI); // "redirect:..." would always be relative to the current context path, we do not want that...
	}

	@RequestMapping(value = "/user/login-callback")
	public String loginCallback(ModelMap model,
			@RequestParam MultiValueMap<String, String> params, HttpSession session) {
		// set the application attributes
		if (!params.containsKey("UserId"))
			throw new RuntimeException("No user id was given");
		session.setAttribute("user_id", params.get("UserId").get(0));
		if (!params.containsKey("Name"))
			throw new RuntimeException("No user name given");
		session.setAttribute("user_name", params.get("Name").get(0)); 
		if (params.containsKey("Email"))
			session.setAttribute("user_email", params.get("Email").get(0));
		else
			session.setAttribute("user_email", params.get("UserId").get(0));
		
		Subject subject = new Subject(params.get("UserId").get(0));
		if (!params.containsKey("PrimaryTenant"))
			throw new RuntimeException("No tenant given for user " + session.getAttribute("Name"));
		session.setAttribute("user_tenant", params.get("PrimaryTenant").get(0));
		if (params.containsKey("Tenant") && params.get("Tenant").size() > 0) {
			SubjectAttributeValue tenantAttr = new SubjectAttributeValue("tenant");
			for (String t: params.get("Tenant"))
				tenantAttr.addValue(t);
			subject.addAttributeValue(tenantAttr);
		}
		if (params.containsKey("Token"))
			session.setAttribute("user_token", params.get("Token").get(0));
		
		// store the authorization subject
		if (params.containsKey("Role") && params.get("Role").size() > 0) {
			SubjectAttributeValue rolesAttr = new SubjectAttributeValue("roles");
			for (String r : params.get("Role")) {
				rolesAttr.addValue(r);
			}
			subject.addAttributeValue(rolesAttr);
		}
		if (params.containsKey("Manages") && params.get("Manages").size() > 0) {
			SubjectAttributeValue assignedAttr = new SubjectAttributeValue("assigned");
			for (String n: params.get("Manages"))
				assignedAttr.addValue(n);
			subject.addAttributeValue(assignedAttr);
		}
		subject.addAttributeValue(new SubjectAttributeValue("email", (String) session.getAttribute("user_email")));
		session.setAttribute("subject", subject);

		MessageManager.getInstance().addMessage(session, "success",
				"Welcome back, " + (String) session.getAttribute("user_name"));
		return "redirect:/docs";
	}

	@RequestMapping(value = "/user/logout", method = RequestMethod.GET)
	public RedirectView logout(ModelMap model, HttpSession session, HttpServletRequest request, UriComponentsBuilder builder) {
		session.invalidate();
		String relayState = builder.path("/").build().toString();
    	try {
			relayState = URLEncoder.encode(relayState, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new RedirectView(LOGOUT_URL + "?RelayState=" + relayState);
	}

}
