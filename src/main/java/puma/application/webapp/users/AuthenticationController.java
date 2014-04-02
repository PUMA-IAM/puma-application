package puma.application.webapp.users;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
			@RequestParam("UserId") String id,
			@RequestParam(value = "Name", defaultValue = "") String name,
			@RequestParam(value = "Email", defaultValue = "") String email,
			@RequestParam(value = "Tenant", defaultValue = "") String tenant,
			@RequestParam(value = "Role", defaultValue = "") String[] roles, HttpSession session) {
		// set the application attributes
		session.setAttribute("user_name", name); 
		session.setAttribute("user_id", id);
		session.setAttribute("user_email", email);
		session.setAttribute("user_tenant", tenant);
		// store the authorization subject
		Subject subject = new Subject(id);
		if (roles != null && roles.length > 0) {
			SubjectAttributeValue rolesAttr = new SubjectAttributeValue("roles");
			for (String r : Arrays.asList(roles)) {
				rolesAttr.addValue(r);
			}
			subject.addAttributeValue(rolesAttr);
		}
		subject.addAttributeValue(new SubjectAttributeValue("tenant", tenant));
		subject.addAttributeValue(new SubjectAttributeValue("email", email));
		session.setAttribute("subject", subject);
		
//		String output = "UserId=" + id + ", Name=" + name + ", Email=" + email + ", Tenant=" + tenant + ", Role=[";
//		for(String r : Arrays.asList(roles)) {
//			output += r + ", ";
//		}
//		output += "]";
//		model.addAttribute("output", output);
//		return "test";

		MessageManager.getInstance().addMessage(session, "success",
				"Welcome back, " + name);
		return "redirect:/docs";
	}

	@RequestMapping(value = "/user/logout", method = RequestMethod.GET)
	public String logout(ModelMap model, HttpSession session, HttpServletRequest request, UriComponentsBuilder builder) {
		session.invalidate();
		String relayState = builder.path("/").build().toString();
    	try {
			relayState = URLEncoder.encode(relayState, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "redirect:" + LOGOUT_URL + "?RelayState=" + relayState;
	}

}