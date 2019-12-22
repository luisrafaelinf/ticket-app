# ticket-app

To build and run the project you need to have Java 8+, Maven 3.6+ and Nodejs installed in your system. After the build, a `target` directory is generated for you with a jar file named **ticket.jar** in it. Run the jar with `java -jar ticket.jar`, then go to your 
browser and write `localhost:8080`

Additionally, some initial data are provided in the app when run the program.

``` shell
# Clone the source code or download the .zip file from github.
git clone https://github.com/luisrafaelinf/ticket-app.git

# Enter the project directory.
cd ticket-app

# Compile the project.
mvn clean package

# Optionally, you can run the project whitout compile it.
mvn spring-boot:run

```
For nodejs requeriment follow the next steep:

You can install a new one:
  - by following the https://nodejs.org/en/download/ guide to install it globally
  - or by running the frontend-maven-plugin goal to install it in this project:
  
``` shell

  $ mvn com.github.eirslett:frontend-maven-plugin:1.7.6:install-node-and-npm -DnodeVersion="v12.13.0" 
  
  ```
