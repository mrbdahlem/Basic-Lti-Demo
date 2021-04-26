package run.mycode.basicltidemo.controller;

import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import run.mycode.basiclti.model.LtiLaunchData;

/**
 *  A demo controller for other pages
 *  @author dahlem.brian
 */
@Controller
public class AnotherController {
    private static final Logger LOG = LogManager.getLogger(AnotherController.class);
        
    @GetMapping(value = "/lti/p2")
    public ModelAndView anotherPage(HttpSession session) {
        ModelAndView mv = new ModelAndView("p2");
        
        LtiLaunchData data = (LtiLaunchData) session.getAttribute(LtiLaunchData.NAME);
        mv.addObject("data", data);
        mv.addObject("note", "LTI");
        
        return mv;
    }
    
}
