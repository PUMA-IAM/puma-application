package puma.application.evaluation.test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TestController {

    @RequestMapping(value = "/test")
    public String index(ModelMap model, @RequestParam("name") String name, @RequestParam("to") String to) {
    	model.addAttribute("output", "received request params: name = " + name + ", to = " + to);
        return "test";
    }
}