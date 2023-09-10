# AWS

## Task

**Step 1. AWS Account Creation**

Visit https://aws.amazon.com/free

Click on "Create new account", if you don't have one

![](media/01.png)

Enter email and username for account

![](media/02.png)

Apply verification code from email

![](media/03.png)

Fill password boxes

![](media/04.png)

Fill other information about account

![](media/05.png)

Fill credit card info

![](media/06.png)

Sign up for AWS

![](media/07.png)

Enter verification code from SMS

![](media/08.png)

Choose plan and complete registration

![](media/09.png)

Proceed to AWS Management Console

![](media/10.png)

Login to access AWS

![](media/11.png)

![](media/12.png)

**Step 2. Application Role Creation**

Go to IAM section and navigate to Roles section

![](media/13.png)

Create new role

![](media/14.png)

Select Trusted entity

![](media/15.png)

Add permissions

![](media/16.png)

Enter role name and check data from previous steps

![](media/17.png)

*Optional: check if role was created and profile applied*

Open aws cloud shell console in the bottom-left corner

![](media/18.png)

Enter console command to show list of roles:

>aws iam list-roles

![](media/19.png)

As we can see role has been applied. Now, check if instance profile for the role has been created.

Enter console command to show list of profile instances:

>aws iam list-instance-profiles

![](media/20.png)

Everything is fine, instance has been created.

Alternatively instance profile can be created with these two commands in aws cloud shell:

>aws iam create-instance-profile --instance-profile-name %instance_profile_name%
>aws iam add-role-to-instance-profile --role-name %role_name% --instance-profile-name %instance_profile_name%

**Step 3. Launch RDS instance**

**_NOTE: Considering that we need the link to our database and currently can't configure it dynamically, I decided to 
create RDS instance first, override database url in properties yml file and package it with new properties for the next
step._**

**_So Step 3 and 4 was swapped_**

Open RDS dashboard

![](media/21.png)

Click on Create database in Create database section

![](media/22.png)

Configure setting for new database

In Choose database creation method choose Standard create option
In Engine options choose MySql and Engine version compatible with database, in this case 8.0.33
In Templates choose Free Tier option
In Setting set name for database and password, in this case properties are same as in properties.yml file
In Connectivity choose Yes for Public Access option
Click Create Database at the end of page

![](media/23.png)

**_NOTE: Creating instance takes time, so be sure that status changed from 'Creating' to 'Available'_**

Created database instance

![](media/24.png)

Configure security groups for RDS

Open EC2 dashboard

![](media/25.png)

On the left side find menu 'Network & Security' select option 'Security Groups'

![](media/26.png)

Edit inbound rules

![](media/27.png)

Add new rule

![](media/28.png)

Choose in Type box MySQL/Aurora type, in Source Info box AnywhereIPv4, in next box 0.0.0.0/0. Save rules

![](media/29.png)

As we can see security group rule has been applied

![](media/30.png)

Check if instance can be reached

Open RDS dashboard

![](media/31.png)

In Resources section open DB Instances

![](media/32.png)

Click on database instance

![](media/33.png)

Copy link from 'Connectivity & security' -> 'Endpoint & port'

![](media/34.png)

Add database within IntellijIdea via database manager

![](media/35.png)

Provide database information for created db and save

![](media/36.png)

Connect

![](media/37.png)

As we can see database was successfully reached. Write test script to ensure connection is successful

![](media/38.png)

Script ran successfully. Remove all created modifications. 

Configure Database

Option 1. Load scripts manually via IDE

Open IDE, execute .sql scripts

![](media/39.png)

Option 2. Load scripts automatically via Spring Configs

Open IDE, open application.yml or application.properties file, edit url, username, password according to RDS configs

![](media/40.png)

**_NOTE: Populating and configuring tables with this option is not recommended, because Spring will run data.sql and 
schema.sql always at startup and will overwrite any data if tables was changed_**

Option 3. Load scripts from the S3 Bucket

Open S3 Bucket dashboard

![](media/41.png)

Click 'Create bucket'

![](media/42.png)

Type name and click 'Create bucket' at the bottom of the page

![](media/43.png)

Click on just created bucket

![](media/44.png)

Click 'Upload'

![](media/45.png)

Drag and drop files or use explorer for adding files, click upload

![](media/46.png)

Files loaded successfully

![](media/47.png)

Now open CloudShell and execute command for sql scripts located in previously created bucket

>aws s3  cp  %path-to-file-in-bucket% - | mysql -h %database-host% -P %database-port% -u %user-name% -p %password% %database-name%

Lets run schema.sql

![](media/48.png)

Tables were created successfully

![](media/49.png)

Now populate these tables with data.sql, populating time depends on the file size

![](media/50.png)

Check if any changes applied, as we can see all data was populated 

![](media/51.png)

**_NOTE: I am checking by the last line of the data.sql file_**

![](media/52.png)

RDS section complete

**Step 4. Upload Application Jar to AWS S3**

Before uploading .jar configure application.yml or application.properties in your project according to 
RDS instance config (url, username, password)

![](media/53.png)

Package project to .jar file

**_NOTE: I am doing this with Maven, so I packaged my project with commands clean & package_**

![](media/54.png)

Navigate to S3 Buckets dashboard

![](media/55.png)

Click 'Create bucket'

![](media/56.png)

Type name and click 'Create bucket' at the bottom of the page

![](media/57.png)

Click on just created bucket

![](media/58.png)

Click 'Upload'

![](media/59.png)

Drag and drop files or use explorer for adding files, click upload

![](media/60.png)

Files loaded successfully

![](media/61.png)

Edit bucket policies

Navigate to S3 Buckets dashboard

![](media/62.png)

Click on bucket

![](media/63.png)

Go to Permissions -> Bucket Policy -> Edit

![](media/64.png)

Click on 'Policy generator'

![](media/65.png)

Copy role arn from IAM -> Roles -> Click on role -> Copy ARN

![](media/66.png)

Copy bucket arn from S3 -> Buckets -> Click on bucket -> Properties -> Copy ARN

![](media/67.png)

Insert into Policy Generator fields Principal - Role ARN, Amazon Resource Name - Bucket ARN, click 'Generate Policy'

![](media/68.png)

Copy JSON from pop up window and go back to Bucket Policy

![](media/69.png)

Click save changes

![](media/70.png)

S3 section complete

**Step 5. Launch EC2 instance**

Navigate to EC2 dashboard

![](media/71.png)

Click on 'Launch Instance'

![](media/72.png)

Type web server name in Name and Tags section
Select Amazon Linux 2 in Application and OS Images section
Select instance type t3.micro in Instance type section
Click Create new key pair in Key Pair (login) section

![](media/73.png)

In popped window type key pair name and select .ppk extension, click 'Create key pair'

![](media/74.png)

In Network settings choose default security group

![](media/75.png)

And finally click 'Launch instance' in Summary section on the right

![](media/76.png)

If everything alright, EC2 instance will pop shortly in dashboard list

![](media/77.png)

Apply our custom role to EC2 Instance

![](media/78.png)

Choose custom role from list and Update IAM role

![](media/79.png)

Now configure our policies, navigate in the menu on the left to Network & Security section and click on 
Security Groups, then on Edit inbound rules

![](media/80.png)

Add TCP on 8080 port and SSH on 22 port and Save rules

![](media/81.png)

Connect to EC2 instance

Download and install [Putty](https://putty.org.ru/download.html)

Before opening Putty, copy Public IPv4 EC2 Instance from Instance Page

![](media/82.png)

Open Putty, in the menu Connection -> SSh -> Auth -> Credentials apply .ppk key

![](media/83.png)

Go to the Session section in the menu and paste Public IPv4 into Host name text box and click Open

![](media/84.png)

Click Accept and proceed

![](media/85.png)

Login as ec2-user

![](media/86.png)

Close Putty

Now go to EC2 Instance Dashboard and stop EC2 Instance 

![](media/87.png)

Now go to EC2 Instance Page and select in Actions menu -> Instance settings -> Edit user data

![](media/88.png)

Write script for downloading java, copying project .jar from S3 bucket and running application

```
Content-Type: multipart/mixed; boundary="//"
MIME-Version: 1.0

--//
Content-Type: text/cloud-config; charset="us-ascii"
MIME-Version: 1.0
Content-Transfer-Encoding: 7bit
Content-Disposition: attachment; filename="cloud-config.txt"

#cloud-config
cloud_final_modules:
- [scripts-user, always]

--//
Content-Type: text/x-shellscript; charset="us-ascii"
MIME-Version: 1.0
Content-Transfer-Encoding: 7bit
Content-Disposition: attachment; filename="userdata.txt"

#!/bin/bash
sudo yum update -y
sudo yum install -y java-1.8.0-openjdk-devel -y
aws s3 cp s3://mjc--jar-s3-bucket-for-this-task/rest-api-security-0.0.1.jar ~/
java -jar ~/rest-api-security-0.0.1.jar
--//
```

Click Save

![](media/89.png)

Restart EC2 instance

![](media/90.png)

Check if Instance state is 'Running' and click 'Connect'

![](media/91.png)

Copy public IP address and connect to instance

![](media/92.png)

In opened window run to see if any scripts run

First, lets check java version with

```
java -version
```
Java was installed on instance.

![](media/93.png)

Second, lets check if project .jar was downloaded and Spring Application has started by checking logs with

```
sudo cat /var/log/cloud-init-output.log
```

![](media/94.png)

Project's .jar was downloaded and Spring Application has started

And finally lets check if we can reach our instance with previously copied public ip in Postman

![](media/95.png)


**Step 6. Terminate or remove all created resources/services**

Terminate EC2 instance(s)

![](media/96.png)

Delete S3 Buckets

![](media/97.png)

Delete RDS instance(s)

![](media/98.png)
