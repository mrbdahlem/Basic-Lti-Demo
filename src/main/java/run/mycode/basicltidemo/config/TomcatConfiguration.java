package run.mycode.basicltidemo.config;

import javax.servlet.ServletContext;
import org.apache.tomcat.util.http.Rfc6265CookieProcessor;
import org.apache.catalina.Context;
import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * A configuration to add the SameSite = NONE, Secure to cookies in order to save
 * session for Chrome browser
 * 
 * @author dahlem.brian
 */
@Configuration
public class TomcatConfiguration {

    @Bean
    WebServerFactoryCustomizer<TomcatServletWebServerFactory> cookieProcessorCustomizer() {
        return (TomcatServletWebServerFactory tomcatServletWebServerFactory) -> {
            tomcatServletWebServerFactory.addContextCustomizers((TomcatContextCustomizer) (Context context) -> {
                Rfc6265CookieProcessor processor=new Rfc6265CookieProcessor();
                processor.setSameSiteCookies(SameSiteCookies.NONE.toString());
                context.setCookieProcessor(processor);
            });
        };
    }
    
    @Bean
    public ServletContextInitializer servletContextInitializer(@Value("${secure.cookie:true}") boolean secure) {
        return (ServletContext servletContext) -> {
            servletContext.getSessionCookieConfig().setSecure(secure);
        };
    }
}