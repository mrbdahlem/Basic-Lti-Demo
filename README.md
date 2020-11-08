# Basic-Lti-Demo

## What is it?
A demo of basic LTI authentication in Spring Web MVC, not requiring the deprecated Spring-Security-Oauth

## How to use
This is a maven project that should compile and run if the [run.mycode.basiclti](https://github.com/mrbdahlem/Basic-Lti) dependency is installed.
(You'll have to download and mvn install yourself, not currently available on maven central).

The project should compile and execute with the command ```mvn spring-boot:run```

In Canvas, you will be able to create an app with the url ```http://localhost:8080/lti/``` using the consumer key ```KEY00001``` and shared secret
```secretkey``` then create an Assignment with External Tool Url ```http://localhost:8080/lti/test```

## Dependencies
This demo Spring Web MVC app relies on the [IMS Global basiclti-util-java](https://github.com/IMSGlobal/basiclti-util-java) for signature verification, 
and my own [basic lti](https://github.com/mrbdahlem/Basic-Lti) authentication filter. You should only have to manually install my filter, it will
install the IMS Global LTI util.

## What to look for
```run.mycode.basicltidemo.controller.LtiController```
A demo controller. It shows an example endpoint /lti/test that only appears to properly verified LTI launch requests. It extracts the LTI Launch data
provided by the tool consumer into an LtiLaunchData object.

```run.mycode.basicltidemo.config.LtiSecurityConfig```
Configures the http stack to use the LtiAuthenticationProcessingFilter for all requests to the /lti/** tree (anything under /lti)
The LtiAuthenticationFilter requires a KeyService which can provide secrets for the consumer keys that are included in the launch request.

```run.mycode.basicltidemo.service.MockKeyService```
This is a demo key service that simply generates some consumer keys and associates them with ```User```s. (Keys and secrets should be harder than
this to guess.)

```run.mycode.basicltidemo.persistence.model.user```
This is a demo User class that inherits from LtiKey to link consumer keys and shared secrets

