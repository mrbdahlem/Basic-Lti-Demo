package run.mycode.basicltidemo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import run.mycode.basiclti.model.LtiLaunchData;

/**
 *  A demo controller for other (non-lti launch) pages
 *  @author dahlem.brian
 */
@Controller
public class AnotherController {
    private static final Logger LOG = LogManager.getLogger(AnotherController.class);
        
    @GetMapping(value = "/lti/p2")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView anotherPage(
            HttpSession session, Authentication auth, 
            HttpServletRequest request )
    {
        ModelAndView mv = new ModelAndView("p2");
        
        if (auth == null) {
            LOG.info("NOT authenticated: " + request.getServletPath());
        }
        
        if (session.isNew()) {
            LOG.info("New Session! " + request.getServletPath());
        }
        
        LtiLaunchData data = (LtiLaunchData) session.getAttribute(LtiLaunchData.NAME);
        mv.addObject("data", data);
        mv.addObject("note", "AnotherController");
       
        return mv;
    }
    
    @RequestMapping(value = "/loginsecured/p2")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView notLti(
            HttpSession session, Authentication auth, HttpServletRequest req )
    {
        return anotherPage(session, auth, req);
    }
}
