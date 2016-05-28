# 499Capstone

Welcome to the repository for our ICS-499 capstone project!

The public IP address for our server is : 52.39.83.97

I won't be posting any SSH credentials here, if you would like me to get you a key so you can explore / learn about our linux server, don't hesitate to ask.

Spring Boot: 
Spring boot is a Java framework that allows us to easily set up a webserver that will implement the Model-View-Controller architectural pattern for our web application.

We will be using Gradle, an application build tool, to assist us with building the project.

In order to build a copy of the webserver on your own machine (and have gradle 2.4+ installed), simple clone the repository, go into the root directory, and type: 

gradle build && java -jar build/libs/gs-spring-boot-0.1.0.jar

This will compile the java files, set up the application context, and wrap the application in an instance of Apache tomcat webserver so that you can try it locally!

To test the default endpoint I currently have set up (subject to change after further controller implementation), run the command: curl localhost:8080?input=5 

GenMyModel account info : morganebridges@gmail.com / Software1984!!

