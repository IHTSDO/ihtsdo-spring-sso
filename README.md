# Example IMS Integration
Example Java webapp demonstrating integration with the SNOMED International's Identity Management Service Integration

## Overview
The commits in this repository start with a plain webapp using spring-boot. Any framework compatible with spring-security could be used.

In latest version of this project the latest IMS integration style is used where the username and roles are passed to the application by Nginx using headers.
In a production environment Nginx would fetch this information from IMS but for local development test values could be hardcoded.

## Detail
For local development authentication details can be hardcoded in your Nginx config and passed into the application via http headers.

[Install nginx](https://www.google.com/?#q=install%20nginx) then use the nginx.conf which is part of this project.
Once your application is started on port 8080 and nginx is running your application should be accessible here http://127.0.0.1/

## Notes
- The example security-context.xml includes "ROLE_ihtsdo-sca-author" which is the Crowd/IMS role "ihtsdo-sca-author". You should change this to your application specific role.
- If you wish to run your application locally without using IMS security just comment out the "intercept-url" lines in security-context.xml which require a role.
