# CI-CD

## Task

1. Configure Jenkins security (install Role strategy plugin). 
   Remove anonymous access. Create administrator user (all permissions) and developer user (build job, cancel builds). 
   Add Jenkins credentials to Readme file in your git repository.
2. Configure Jenkins build job (poll, run test, build) to check out your repository, use polling interval.
3. Install SonarQube. Configure Jenkins to use local SonarQube installation. 
   Analyze your source code with SonarQube after Maven builds your project. Use JaCoCo for code coverage.
4. Jenkins should deploy your application (after passing SonarQube quality gate) under your local tomcat server. 
   Please use Jenkins Tomcat Deploy plugin.

For the work we will need following tools and plugins

**Tools:**
1. Java 17 - runtime environment and compiler
2. Apache Maven 3.8.3 - packaging and deploying
3. Jenkins 2.414.2 LTS - ci-cd system
4. Git - version control system
5. SonarQube 10.2 - code quality and security inspection
6. Apache Tomcat 9.0 - container for application

**Maven Plugins**
1. Sonar Plugin - SonarQube integration
2. Jacoco Plugin - JaCoCo integration
3. Tomcat 7 Plugin - Tomcat integration

**Jenkins Plugins**
1. Locale - change language of Jenkins
2. Role-base Authorization Strategy - adds options related with roles
3. Maven Integration - adds maven options in Jenkins
4. SonarQube Scanner - integration with SonarQube server
5. Sonar Quality Gates - checks if predefined sonar quality gates meets quality conditions
6. JaCoCo - code coverage
7. Deploy to container - deploying application to container (Tomcat)

## Solution

### Step 1. Install Java

**NOTE: This section can be skipped if Java 17 was installed previously**

Download [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

![](media/01.png)

Find downloaded file and execute. Press next to continue installation

![](media/02.png)

Check if Java will be installed to proper folder. Change destination if not. Press next to proceed

![](media/03.png)

After installation quit installation wizard

![](media/04.png)

On Start panel type into Search 'Environment variables' -> Open Change environment variables

![](media/05.png)

In opened window press on Environment variables

![](media/06.png)

Click create

![](media/07.png)

**a. First Java installation**

In popped up window type env variable name (JAVA_HOME) and path to installed Java 17 JDK (C:\Program Files\jdk17)
Click OK

![](media/08.png)

Find Path env variable and click Edit

![](media/09.png)

Click Create. Type %JAVA_HOME%\bin. Press OK

![](media/10.png)

**b. Another Java installation**

Find JAVA_HOME env variable and click Edit

![](media/11.png)

Change JAVA_HOME env value to Java 17 JDK path and press OK

![](media/12.png)

Close every opened window by pressing OK or Apply. Reboot PC

On Start panel type into Search 'cmd' -> Command Line -> Run as Administrator

![](media/13.png)

Type following commands (checks if java, java compiler and java env were applied properly)

> java -version
> 
> javac -version
> 
> echo %JAVA_HOME%

![](media/14.png)

If everything set properly these commands will show version of java, java compiler and java path

### Step 2. Install Apache Maven 3.8.3

**NOTE: This section can be skipped if Apache Maven 3.8+ was installed previously**

Download [Apache Maven](https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.8.3/)

![](media/15.png)

Extract content of archive into folder

![](media/16.png)

On Start panel type into Search 'Environment variables' -> Open Change environment variables

![](media/17.png)

In opened window press on Environment variables

![](media/18.png)

Click create

![](media/19.png)

In popped up window type env variable name (MAVEN_HOME) and path to unpacked Apache Maven 
(C:\Program Files\apache-maven-3.8.3). Click OK

![](media/20.png)

Find Path env variable and click Edit

![](media/21.png)

Click Create. Type %MAVEN_HOME%\bin. Press OK

![](media/22.png)

Close every opened window by pressing OK or Apply. Reboot PC

On Start panel type into Search 'cmd' -> Command Line -> Run as Administrator

![](media/23.png)

Type following commands (checks if maven and maven env were applied properly)

> mvn -version
>
> echo %MAVEN_HOME%

![](media/24.png)

If everything set properly these commands will show version of maven and maven path

### Step 3. Install Jenkins

**NOTE: This section can be skipped if Jenkins was installed previously**

Download [Jenkins](https://www.jenkins.io/download/)

![](media/25.png)

Execute downloaded file and press Next

![](media/26.png)

Specify installation path and press Next

![](media/27.png)

Check Run service as LocalSystem in Logon Type and press Next

![](media/28.png)

Select Port Number. Test if port available. If yes - press Next, if no - change port

**NOTE: Jenkins runs on port 8080 by default same as Tomcat Server, ensure that Jenkins and Tomcat will run on 
separate ports**

![](media/29.png)

Select path of JDK or JRE and press Next

![](media/30.png)

Select options for Jenkins Service

![](media/31.png)

Press Install

![](media/32.png)

Press Finish

![](media/33.png)

### Step 4. Configure Jenkins

Open browser and proceed to http://localhost:8086/ for further configurations

![](media/34.png)

Copy path to password location, open explorer and copy it into address line, open file with text editor, 
copy Administrator password

![](media/35.png)

Paste it in Jenkins and press Continue

![](media/36.png)

Install suggested plugin

![](media/37.png)

Wait until all plugins will be installed

![](media/38.png)

Fill data for admin user. Save and Continue

![](media/39.png)

Configure instance

![](media/40.png)

Start using Jenkins

![](media/41.png)

### Step 5. Change language (optional)

If your locale is not set to English by default, you can manage this by Locale plugin

To install plugin go to Manage Jenkins -> Plugins

![](media/42.png)

On Plugins page go to Available Plugins -> Type in search 'Locale' -> select Locale plugin -> Click Install

![](media/43.png)

When all updates and installations succeeds reboot Jenkins by selecting Reboot Jenkins after installation

![](media/44.png)

After rebooting (re-log if needed) go to Manage Jenkins -> System

![](media/45.png)

Find Locale section, type in text box 'ENGLISH' and check 'Ignore browser preference and force this language to all users'.
Apply and save

![](media/46.png)

Go back to dashboard

### Step 6. Configure Jenkins security

Install Role-based Strategy plugin

To install plugin go to Manage Jenkins -> Plugins

![](media/47.png)

On Plugins page go to Available Plugins -> Type in search 'Role-based Authorization Strategy' 
-> select Role-based Authorization Strategy plugin -> Click Install

![](media/48.png)

When all updates and installations succeeds reboot Jenkins by selecting Reboot Jenkins after installation

![](media/49.png)

After rebooting (re-log if needed) go to Manage Jenkins -> Security.

In Authentication section choose from dropdown Role-Based Strategy option. Apply and Save

![](media/50.png)

Now new option will appear in Mange Jenkins -> Security section

Proceed to Manage and Assign Roles

![](media/51.png)

As we can see admin role is already created with all permissions

Create developer role and add it. Check only Job Build and Job Cancel options for it. Apply and Save

![](media/52.png)

Create users

Go to Manage Jenkins -> Users

![](media/53.png)

Create developer user

![](media/54.png)

Fill out form and click Create user

![](media/55.png)

Now developer user created

![](media/56.png)

Assign roles

Go to Manage Jenkins -> Manage and Assign Roles

![](media/57.png)

Select Assign Roles tab on the left side. Click Add User in Global Roles section. In popped message type developer name 
from created developer user before. Apply and Save

![](media/58.png)

Add credentials to git repository

![](media/59.png)

Push to repository

![](media/60.png)

### Step 7. Build job for project polling with polling interval, testing and building

Install Git service (if not installed already)

Download [Git SCM](https://git-scm.com/download/win) 

![](media/61.png)

Start downloaded executable

![](media/62.png)

Press Next on every installation steps (installation with default options) and press Finish at the end

![](media/63.png)

Check in cmd that git installed

>git version

![](media/64.png)

Now proceed into Manage Jenkins -> Tools

![](media/65.png)

In section JDK installations, click Add JDK, set name (e.g. Java 17) and path to JDK in JAVA_HOME

![](media/66.png)

In section Maven installations, remove check from Install automatically option, set name (e.g. Maven) 
and path to Maven in MAVEN_HOME

Apply and Save

![](media/67.png)

Install Maven Integration plugin

Go to Manage Jenkins -> Plugins

![](media/68.png)

On Plugins page go to Available Plugins -> Type in search 'Maven Integration' -> select Role-based Maven Integration 
plugin -> Click Install

![](media/69.png)

When all updates and installations succeeds reboot Jenkins by selecting Reboot Jenkins after installation

![](media/70.png)

Go to the Dashboard and click Create a job

![](media/71.png)

Set name for a job, choose job type and click OK down below

**NOTE: I will use Pipeline type in this task**

![](media/72.png)

Go to GIT, copy link to your project's git repo (https://github.com/jelrus/rest-api-security.git)

![](media/73.png)

Go back to Jenkins Pipeline Configuration.

In General section:

Fill description (optional)

Check GitHub project box and paste git project's link

![](media/74.png)

In Build Triggers section:

Check Poll SCM box

Set Schedule for every 5 minutes

> H/5 * * * *

Means SCM will check for changes every 5 minutes, if repo was affected by changes it pipeline will do every step 
describes in the Pipeline section

![](media/75.png)

In Pipeline section:

Write script

      pipeline {
         agent any

         tools{
            maven "Maven"
         }
      
         stages {
            stage('GIT Checkout/Test/Build Artifact') {
               steps {
                git 'https://github.com/jelrus/rest-api-security.git'
                bat "mvn clean"
                bat "mvn test"
                bat "mvn package -Dmaven.test.skip=true"
               }
            }
         }
      }

Apply and Save

**Script do the following:**
1. Checks out GIT repo
2. Cleans project
3. Runs tests in project
4. Packs project into java runnable (.jar, .war, .ear, - packaging can be configured from pom.xml)

![](media/76.png)

Now we can manually build project

![](media/77.png)

Click on project

![](media/78.png)

As we can see build was successful

![](media/79.png)

Lets checkout if SCM Polling works

Edit README.md, commit and push

![](media/80.png)

As we can see GIT SCM Polling works and build was successful

![](media/81.png)

### Step 8. Install SonarQube

Download [Docker](https://www.docker.com/products/docker-desktop/)

![](media/82.png)

Start downloaded executable

**NOTE: Before installing Docker ensure that virtual machine option in BIOS/UEFI is enabled 
(location depends on distribution) and WSL Kernel is installed/updated (from Microsoft Store)**

![](media/83.png)

Wait for installation and click Close

![](media/84.png)

Now run Docker, in search section write 'sonarqube' and find the first image, pull it

![](media/85.png)

Click on Images (on the right side) and checkout if SonarQube image was installed

![](media/86.png)

Open cmd and type command for starting SonarQube server from image

>docker run -d --name sonarqube -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p 9000:9000 sonarqube:latest

![](media/87.png)

Go back to Docker, open Containers tab (on the right) and see that the SonarQube server has been started

![](media/88.png)

In browser go to http://localhost:9000/

**NOTE: By default SonarQube login and password are admin/admin**

Fill admin default credentials, click Log in button and proceed

![](media/89.png)

Create new password for admin user and go to the next page

![](media/90.png)

That's all, we configured SonarQube Server

![](media/91.png)

**Integration with Jenkins**

Go to Dashboard -> Manage Jenkins -> Plugins

![](media/92.png)

Click Available Plugins tab on the left -> Enter into search bar 'sonarqube' -> 
Find and select SonarQube Scanner plugin -> Install

![](media/93.png)

After installation fo back to Available Plugins tab on the left -> Enter into search bar 'sonar quality gates' ->
Find and select Sonar Quality Gates plugin -> Install

![](media/94.png)

Restart Jenkins after plugins has been applied

![](media/95.png)

**Integration with Maven Project**

Open up project, find pom.xml, add packaging .war type under description tag

![](media/96.png)

Add Sonar Maven plugin into plugins section

![](media/97.png)

Commit changes

![](media/98.png)

**Create SonarQube project**

Access SonarQube Server by http://localhost:9000/ and find Create project manually option

![](media/99.png)

Fill out form and click Next

![](media/100.png)

Choose Use the global settings option, create project

![](media/101.png)

Choose Locally option

![](media/102.png)

Generate SonarQube access token

![](media/103.png)

Copy created token somewhere, click Continue

![](media/104.png)

Choose options for generating sonar run script

Mention, that SonarQube asks for local installation to run this script, copy script somewhere

![](media/105.png)

**SonarQube local installation**

Download [SonarQube](https://docs.sonarsource.com/sonarqube/10.2/analyzing-source-code/scanners/sonarscanner/)

![](media/106.png)

Choose folder and extract downloaded SonarQube archive

![](media/107.png)

Now proceed to the extraction destination, open up conf folder, find and open 

![](media/108.png)

Open sonar-scanner.properties file with text editor

Add the following lines into .properties file, by these lines we provide project's key, name, version and 
binaries location for scanning, save file

**NOTE: If file needs special permissions for editing:**

1. Copy original file somewhere
2. Delete original file from sonarqube/conf folder 
3. Edit copied file 
4. Insert it into sonarqube/conf folder

![](media/109.png)

Now add SonarQube as environment variable

Enter into search bar Edit environment variables, open it

![](media/110.png)

Click on Environment variables

![](media/111.png)

Create -> Enter environment variable name and path to previously extracted SonarQube

![](media/112.png)

Find Path variable -> Edit... -> Create -> Type %SONAR_HOME%\bin -> Click OK

![](media/113.png)

Reboot

Check if SonarQube env was set correctly

Open cmd and run command

>echo %SONAR_HOME%

![](media/114.png)

As we can see env was set correctly

**SonarQube Scanner Configuration for Jenkins**

Reach Jenkins by http://localhost:8086/ -> Manage Jenkins -> Tools

![](media/115.png)

Find SonarQube Scanner Installations section -> Set name -> Specify path of previously locally installed SonarQube 
-> Apply and Save

![](media/116.png)

**SonarQube Server Configuration for Jenkins**

Go to Dashboard -> Manage Jenkins -> System

![](media/117.png)

Find SonarQube Servers section -> Set name, Server URL -> Click Add under Server authentication token section

![](media/118.png)

In popped up window in Kind section select Secret text option

In Secret text box type your previously saved token

In ID text box type your project name. Add

![](media/119.png)

Apply and Save

**SonarQube Quality Gates Configuration for Jenkins**

Navigate to Manage Jenkins -> System (if not in the System section already)

![](media/120.png)

Navigate to Quality Gates - SonarQube section -> Fill out form with default values and apply your previously generated 
token -> Add Sonar Instance

![](media/121.png)

Apply and Save

**Pipeline configuration for SonarQube**

Proceed to Jenkins Dashboard -> Select Configure option from pipeline menu

![](media/122.png)

Navigate to Pipeline section, add sonar part into pipeline script

      stage('SonnarQube scan') {
         steps {
            withSonarQubeEnv('SonarQubeServer') {
            bat "mvn sonar:sonar \
            -Dsonar.projectKey=rest-api-security-project \
            -Dsonar.host.url=http://192.168.1.34:9000"
            }
         }
      }

![](media/123.png)

In this case name must be the same as we configured in Manage Jenkins -> System -> Quality Gates - SonarQube

Project Key must be the same as we created in SonarQube

Apply and Save

**NOTE: To see open IP of your local machine you can use this command in cmd**

>ipconfig

![](media/124.png)

Build pipeline

![](media/125.png)

As we can see pipeline job ran successfully

Check SonarQube server by http://localhost:9000/

![](media/126.png)

No JaCoCo has been applied to project yet, so no coverage has been computed

**JaCoCo Configuration for Jenkins**

Go to Jenkins by http://localhost:8086/

Go to Manage Jenkins -> Plugins

![](media/127.png)

Click Available Plugins tab on the left -> Enter into search bar 'jacoco' ->
Find and select JaCoCo plugin -> Install

![](media/128.png)

Restart Jenkins after plugin has been installed

![](media/129.png)

**JaCoCo Configuration for Maven Project**

Go to project find pom.xml and add JaCoCo plugin

![](media/130.png)

Commit and push changes

![](media/131.png)

**Configure Pipeline for JaCoCo**

Go to Dashboard -> Select job -> Configure

![](media/132.png)

Navigate to Pipeline section and add another part of the script

      stage('JaCoCo coverage') {
            steps {
                jacoco( 
                    execPattern: 'target/*.exec',
                    classPattern: 'target/classes',
                    sourcePattern: 'src/main/java',
                    exclusionPattern: 'src/test*'
                    )
            }
      }

![](media/133.png)

Apply and Save

Execute pipeline

![](media/134.png)

As we can see pipeline was executed successfully

Check SonarQube server by http://localhost:9000/

![](media/135.png)

As JaCoCo has been applied to project, coverage has been computed

### Step 9. Deploy application to Tomcat

**Tomcat Installation and Configuration**

Download [Tomcat](https://tomcat.apache.org/download-90.cgi)

![](media/136.png)

Extract downloaded archive

![](media/137.png)

Type Edit environment variables in Start search bar -> Open Edit environment variables

![](media/138.png)

Click Environment variables...

![](media/139.png)

Click Create -> Set variable name - CATALINA_HOME and Value of variable - path to extracted Tomcat -> Click OK

![](media/140.png)

Select Path variable -> Click Edit -> Click Add -> Add %CATALINA_HOME%\bin parameter -> Click OK

![](media/141.png)

Go to Apache Tomcat folder -> find conf folder -> find and open with text-editor tomcat-users.xml

Add following lines just before enclosing of tomcat users tag

      <role rolename="manager-gui"/>
      <role rolename="manager-status"/>
      <role rolename="manager-script"/>
      <role rolename="manager-jmx"/>
      <user username="tomcat" password="password" roles="manager-gui, manager-status, manager-script, manager-jmx"/>

By this we are adding tomcat manager user with necessary policies, close and save file

![](media/142.png)

Optional: Edit context path

Go to Apache Tomcat folder -> find conf folder -> find and open with text-editor context.xml

![](media/143.png)

Edit following lines

      <Context>

With 

      <Context path="" docBase="rest-api-security">

![](media/144.png)

Now our project will deploy into http://localhost:8080/ instead of http://localhost:8080/rest-api-security

**Run Tomcat Server**

Open up Apache Tomcat Folder -> find bin folder and open it -> find startup.bat and execute it

![](media/145.png)

Console window will open - don't close it

**Configure Tomcat for Maven Project**

Open Maven project -> Find pom.xml -> Add Tomcat Spring Boot dependency

![](media/146.png)

Next in pom.xml add final name in build

![](media/147.png)

Next in pom.xml add Tomcat Maven plugin

![](media/148.png)

**NOTE: username and password should be the same as we defined in tomcat-users.xml previously** 

**NOTE: path should be the same as in context.xml, if this option was skipped path should look like**

      <path>/<your-app-name></path>

Go to Main class of Spring Boot Application and extend it from SpringBootServletInitializer class

![](media/149.png)

Commit changes to repo

**Configure Tomcat for Jenkins**

Open Jenkins server by http://localhost:9000/

![](media/150.png)

Click Available Plugins tab on the left -> Enter into search bar 'deploy to container' ->
Find and select Deploy to container plugin -> Install

![](media/151.png)

Restart Jenkins after plugin has been installed

![](media/152.png)

**Configure Pipeline for Tomcat**

Go to Dashboard -> Select job -> Configure

![](media/153.png)

Navigate to Pipeline section, add deployment part into pipeline script

      stage('Deployment') {
         steps{
            bat "mvn tomcat7:redeploy"
         }
      }

![](media/154.png)

Apply and Save

Execute pipeline

![](media/155.png)

As we can see build was successful

**NOTE: Previous build failed (#6) because Tomcat server wasn't running**

Proceed to http://localhost:8080/ in Postman or browser

![](media/156.png)