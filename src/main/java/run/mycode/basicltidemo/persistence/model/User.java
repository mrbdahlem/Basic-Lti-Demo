package run.mycode.basicltidemo.persistence.model;

import run.mycode.basiclti.authentication.LtiKey;

/**
 * A sample user - this would be an instructor/admin that adds this tool to
 * an LMS
 * 
 * @author dahlem.brian
 */
public class User extends LtiKey {
    private String name;
    private String organization;

    public User(String name, String organization, 
            String consumerKey, String sharedSecret) {
        
        super(consumerKey, sharedSecret);
        this.name = name;
        this.organization = organization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
}
