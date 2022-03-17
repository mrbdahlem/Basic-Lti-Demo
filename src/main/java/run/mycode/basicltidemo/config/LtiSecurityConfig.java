package run.mycode.basicltidemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import run.mycode.basiclti.security.BasicLtiAuthenticationManager;
import run.mycode.basiclti.security.LtiAuthenticationProcessingFilter;
import run.mycode.basiclti.service.LtiKeyService;
import run.mycode.basiclti.service.NonceService;
import run.mycode.basiclti.service.SimpleNonceServiceImpl;

@Configuration
@EnableWebSecurity( debug = true )
public class LtiSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private LtiKeyService keyService;
    
    @Autowired
    private NonceService nonceService;
    
    private LtiAuthenticationProcessingFilter ltiAuthFilter;
        
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new PreAuthenticatedAuthenticationProvider());
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        this.ltiAuthFilter = createLtiFilter();
        
        http
            .authorizeRequests()
                .anyRequest()
                //.antMatchers("/lti/**") // Launches will be made on any of the resources below lti                
                .authenticated()        // and must be authenticated (by the filter)
            .and()
                .anonymous().disable()
            .cors().disable()
                
                ;
        
        http
            //.cors()
            //.and()
            .csrf()
                .ignoringAntMatchers("/lti/**") // LTI launches are made using HTTP POST, but won't have csrf tokens so must be ignored -- nonce should eliminate csrf
            .and()
            .antMatcher("/lti/**")
                .headers().frameOptions().disable() // Allow launches in iframes     
            .and()
            .addFilterBefore(ltiAuthFilter,
                    BasicAuthenticationFilter.class) // Authenticate LTI launches
                ;
        
        
    }
    
    @Bean(name = "nonceService")
    public NonceService createNonceService() {
        return new SimpleNonceServiceImpl(600);
    }
    
    public LtiAuthenticationProcessingFilter createLtiFilter() {
        LtiAuthenticationProcessingFilter filter = 
                new LtiAuthenticationProcessingFilter(keyService, nonceService);
        filter.setAuthenticationManager(new BasicLtiAuthenticationManager());
        
        return filter;
    }
}