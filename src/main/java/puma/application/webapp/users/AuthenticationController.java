package puma.application.webapp.users;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import puma.application.webapp.msgs.MessageManager;
import puma.peputils.Subject;
import puma.peputils.attributes.SubjectAttributeValue;

@Controller
public class AuthenticationController {

    @RequestMapping(value = "/user/login", method = RequestMethod.GET)
    public String login(ModelMap model, HttpSession session) {
    	// set the application attributes
    	session.setAttribute("user", "maarten");
    	session.setAttribute("user-email", "maarten.decat@cs.kuleuven.be");
    	// store the authorization subject
		Subject subject = new Subject("maarten"); // TODO use the real authentication data here
		SubjectAttributeValue roles = new SubjectAttributeValue("roles");
		roles.addValue("phd");
		roles.addValue("iminds-pr");
		roles.addValue("boss-of-Jasper");
		subject.addAttributeValue(roles);
		subject.addAttributeValue(new SubjectAttributeValue("departement", "computer-science"));
		subject.addAttributeValue(new SubjectAttributeValue("fired", false));
		subject.addAttributeValue(new SubjectAttributeValue("tenant", "KUL"));
		subject.addAttributeValue(new SubjectAttributeValue("email", "maarten.decat@cs.kuleuven.be"));
		session.setAttribute("subject", subject);
		
    	MessageManager.getInstance().addMessage(session, "success", "Welcome back, commander");
    	return "redirect:/docs";
    }

    @RequestMapping(value = "/user/logout", method = RequestMethod.GET)
    public String logout(ModelMap model, HttpSession session) {
    	session.removeAttribute("user");
    	session.removeAttribute("user-email");
    	session.removeAttribute("subject");
    	return "redirect:/";
    }
    
    
}