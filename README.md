# iam-poc
Full-stack spring boot / angular small multi-iam login template

This projets manages identity management through its spring boot back-end
The iam framework used to implement identity management on top of spring-security is pac4j

Instructions : 

Back-end is in https, and the properties taking the certificate into account is in back-end\src\main\resources\application.yaml, meaning, if you want to run the app locally : 
 - you need to create your own auto-signed cert / keystore
 - create a lauch configuration in your IDE setting the absolute path of your store + password