# luxsoft-test
luxsoft-test

## Requirements

For development and use of the project the following dependencies are required:

- [Git] (https://git-scm.com/): Makes Git available on the machine, including the Git Bash utility, used to run project scripts.
- [Spring MVC] (https://spring.io/): Java utility, with tools needed for execution, * backend * build
- [Maven] (https://maven.apache.org/): Dependency control for Java projects.

### Project Dependencies

Dependencies required to run the project:

 `` `
<dependency>
<groupId> org.springframework.boot </groupId>
<artifactId> spring-boot-starter-web </artifactId>
</dependency>

<dependency>
<groupId> org.springframework.boot </groupId>
<artifactId> spring-boot-starter-tomcat </artifactId>
<scope> provided </scope>
</dependency>
<dependency>
<groupId> org.springframework.boot </groupId>
<artifactId> spring-boot-starter-test </artifactId>
<scope> test </scope>
</dependency>
<dependency>
<groupId> org.springframework.boot </groupId>
<artifactId> spring-boot-devtools </artifactId>
<scope> runtime </scope>
<optional> true </optional>
</dependency>
<dependency>
<groupId> org.projectlombok </groupId>
<artifactId> lombok </artifactId>
<version> 1.18.8 </version>
</dependency>
 `` `
## Settings

This section details the settings required to create a local development environment.

### Git

Using http:

`` `sh
 git clone http://github.com/rafaj2ee/luxsoft-test.git
`` `

Using ssh:

`` `sh
 git clone https://github.com/rafaj2ee/luxsoft-test.git
`` `
After cloning the repository, access the project folder and configure the git user with the following commands:

`` `sh
$ git config --local user.name "<name>"
$ git config --local user.email "<email github>"
`` `

* Note * .: If the git user has already been configured, disregard this step.

#### Backend (API)

If you are using the Eclipse IDE, simply import the project through the menu | File | Import | Maven | Existing Maven Projects and create a configuration to run the application.

After that, install the other local project dependencies using:

`$ mvn install`

to run the project execute the command below:

`$ mvn spring-boot: run`

* Note * .: You can run the project through the IDE using the RUN AS | Java application.

The API will be running at the address [http: // localhost: 8095] (http: // localhost: 8095).
http://localhost:8095/search?term="Put your search term here"