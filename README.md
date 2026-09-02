# SNOMED International - Single Sign On for the Spring Framework

## Overview
There are two modules:
* **ihtsdo-spring-sso** should be used as a library to enable integration with the SSO solution. 
* **ihtsdo-spring-sso-example** Is an example Spring Boot webapp which uses SSO.

Although the example uses Spring Boot any framework compatible with spring-security could be used.

As of 7.0.0 this project builds against Spring Boot 4 (Spring Framework 7, Spring Security 7).

## Detail
The latest style of IMS integration is that NGINX does the IMS authentication and then the username, roles and authentication token is passed to the java application in http headers.

Backend java applications should also have basic authentication security as a belt and braces approach in case they are accessed directly without going through NGINX.

The example security-context.xml includes "ROLE_ihtsdo-sca-author" which is the Crowd/IMS role "ihtsdo-sca-author". You should change this to your application specific role.

### Spring Security 7 notes

If you are copying security-context.xml into your own application, note the following. These are all changes that Spring Security 7 requires; configuration written for earlier versions will fail to start or will reject every request.

* The schema must be `spring-security.xsd` (or `spring-security-7.1.xsd`). Any older versioned XSD is rejected outright with *"You cannot use any XSD older than spring-security-7.1.xsd"*.
* `<global-method-security>` no longer works — its parser depends on classes removed in Spring Security 7. Use `<method-security pre-post-enabled="..." secured-enabled="..."/>` instead.
* `IS_AUTHENTICATED_ANONYMOUSLY` is not a valid `access` value. It is evaluated as a SpEL expression and fails. Use `permitAll`.
* Passwords in `<user-service>` need a password encoder prefix, e.g. `{noop}example-password`. The example's credentials are for local development only — real deployments should not carry credentials in this file.
* Requests that match no `<intercept-url>` are denied, so `/error` needs an explicit `permitAll` rule. Without it every error response to an unauthenticated caller is rewritten as a 401 and the real status code (404, 500, ...) is lost.

When making requests from your application to other backend applications the user's SSO security token should be forwarded. 
Simply set a HTTP header in the request named "Cookie" with the value returned from ```SecurityUtil.getAuthenticationToken()```.

## Local Development
If you wish to run your application locally without using IMS security, change the "intercept-url" lines in security-context.xml which require a role so that they use `permitAll`:

```xml
<intercept-url pattern="/**" access="permitAll" />
```

Do not simply comment those lines out. Under Spring Security 7 a request that matches no rule is denied, so removing the rule locks the endpoint rather than opening it.

To use NGINX [install it](https://www.google.com/?#q=install%20nginx) then update and use the nginx.conf which is part of this project. 

In a production environment NGINX would fetch the username, roles and authentication token from IMS but for local development test-values could be hardcoded into HTTP headers in your Nginx config.


Once your application is started on port 8080 and NGINX is running your application should be accessible here http://127.0.0.1/
