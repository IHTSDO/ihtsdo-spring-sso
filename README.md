# Example IMS Integration
Example Java webapp with IHTSDO Identity Management Service Integration

## Overview
The first commit to this repository is with the plain webapp using spring-boot. Any framework compatible with spring-security could be used.

The second commit to this repository adds the IMS integration.

## Detail
When you log in to the dev IMS service [dev-ims.ihtsdotools.org](https://dev-ims.ihtsdotools.org/) you will get a cookie tied to the *.ihtsdotools.org domain.

In order to use this cookie on your local machine you will need to make your application accessible under the *.ihtsdotools.org domain on port 80.

The recommended way to do this is to [install nginx](https://www.google.com/?#q=install%20nginx) then use the nginx.conf which is part of this project.
Once your application is started on port 8080 and nginx is running your application should be accessible here http://local.ihtsdotools.org

If you are not logged in to IMS you will get a "403 - Access Denied" response.

## Notes
- The example security-context.xml includes "ROLE_ihtsdo-sca-author" which is the Crowd/IMS role "ihtsdo-sca-author". You should change this to your application specific role.
- The crowd.properties file is only relevant for the development environment.
- There is an issue on the macbook where only one in three IMS requests are authenticated. This is something to do with having multiple network interfaces and each producing a different machine signature but the issue is unsolved.
- If you wish to run your application locally without using IMS security just comment out the "intercept-url" lines in security-context.xml which require a role.
