package run.mycode.basicltidemo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import run.mycode.basiclti.model.LtiLaunchData;

/**
 *  A demo controller for LTI tool launches
 *  @author dahlem.brian
 */
@Controller
public class LtiLaunchController {
    private static final Logger LOG = LogManager.getLogger(LtiLaunchController.class);
        
    @PostMapping(value = "/lti/launch")
    public String ltiEntry(HttpServletRequest request, HttpServletResponse response,
            Authentication auth, LtiLaunchData data) {
        
        // A new launch means any session we had should be invalidated
        HttpSession session = request.getSession();
        session.invalidate();
        
        // Create a new session and store the launch data in it
        session = request.getSession();
        session.setAttribute(LtiLaunchData.NAME, data);
        
        SecurityContext sc = SecurityContextHolder.getContext();
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);
        sc.setAuthentication(auth);
        
        // Log the launch
        LOG.info("Launch for Context id: " + data.getContext_id() +
                " User: " + auth.getName() + 
                " Roles: " + 
                auth.getAuthorities().stream()
                        .map(a -> a.getAuthority())
                        .reduce("", String::concat) + " ");
        
        return "success";
    }    
}
