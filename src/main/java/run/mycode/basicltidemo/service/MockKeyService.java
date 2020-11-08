package run.mycode.basicltidemo.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import run.mycode.basiclti.service.LtiKeyService;
import run.mycode.basiclti.authentication.LtiKey;
import run.mycode.basicltidemo.persistence.model.User;

/**
 * A demo key service that generates 10 key/secret pairs and associated user
 * organizations
 * 
 * @author dahlem.brian
 */
@Service
public class MockKeyService implements LtiKeyService {
    private static final Logger LOG = LogManager.getLogger(MockKeyService.class);
    
    private final List<LtiKey> keys;
    
    public MockKeyService() {
        keys = new ArrayList<>();
        
        // Build 10 fake users with key/secret pairs
        for(int i = 0; i < 10; i++) {
            User u = new User(String.format("USER%03d", i),
                        String.format("ORG%03d", i),
                        String.format("KEY%05d", i),
                        "secretkey");
            
            keys.add(u);
            LOG.debug(u);
        }
        
        LOG.info("Mock Key Service Initialized");
    }
    
    @Override
    public LtiKey getKey(String key) {
        LOG.info("Info Request for Key: " + key);
        LtiKey found = keys.stream()
                .filter(k -> k.getKey().equals(key))
                .findFirst()
                .orElse(null);
        
        if (found == null) {
            LOG.info(key + " NOT Found.");      
            return null;
        }        
        
        return found;
    }

    @Override
    public String getSecretForKey(String key) {
        LtiKey found = getKey(key);
        
        if (found == null) {
            return null;
        }
        
        return found.getSecret();
    }

}