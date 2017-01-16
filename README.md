# SNOMED International - Single Sign On for the Spring Framework

## Overview
There are two modules:
* **ihtsdo-spring-sso** should be used as a library to enable integration with the SSO solution. 
* **ihtsdo-spring-sso-example** Is an example Spring Boot webapp which uses SSO.

Although the example uses Spring Boot any framework compatible with spring-security could be used.

## Detail
The latest style of IMS integration is that NGINX does the IMS authentication and then the username, roles and authentication token is passed to the java application in http headers.

Backend java applications should also have basic authentication security as a belt and braces approach in case they are accessed directly without going through NGINX.

The example security-context.xml includes "ROLE_ihtsdo-sca-author" which is the Crowd/IMS role "ihtsdo-sca-author". You should change this to your application specific role.

When making requests from your application to other backend applications the user's SSO security token should be forwarded. 
Simply set a HTTP header in the request named "Cookie" with the value returned from ```ControllerHelper.getAuthenticationToken()```.

## Local Devlopment
If you wish to run your application locally without using IMS security just comment out the "intercept-url" lines in security-context.xml which require a role.

To use NGINX [install it](https://www.google.com/?#q=install%20nginx) then update and use the nginx.conf which is part of this project. 

In a production environment NGINX would fetch the username, roles and authentication token from IMS but for local development test-values could be hardcoded into HTTP headers in your Nginx config.


Once your application is started on port 8080 and NGINX is running your application should be accessible here http://127.0.0.1/
