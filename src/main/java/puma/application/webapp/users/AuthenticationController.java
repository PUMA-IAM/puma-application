package puma.application.webapp.users;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import puma.application.webapp.msgs.MessageManager;

@Controller
public class AuthenticationController {

    @RequestMapping(value = "/user/login", method = RequestMethod.GET)
    public String login(ModelMap model, HttpSession session) {
    	session.setAttribute("user", "maarten");
    	session.setAttribute("user-email", "maarten.decat@cs.kuleuven.be");
    	MessageManager.getInstance().addMessage(session, "success", "Welcome back, commander");
    	return "redirect:/docs";
    }

    @RequestMapping(value = "/user/logout", method = RequestMethod.GET)
    public String logout(ModelMap model, HttpSession session) {
    	session.removeAttribute("user");
    	session.removeAttribute("user-email");
    	return "redirect:/";
    }
    
    
}