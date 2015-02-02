KangarooService   [![Build Status](https://travis-ci.org/mehikmat/KangarooService.svg?branch=master)](https://travis-ci.org/mehikmat/KangarooService)
===============

Web Services for Kangaroo Android Apllication (https://github.com/mehikmat/Kangaroo)

This is Restful Web Services which provides restful query system backed by HyperSQLDB.

Android Based Application can use this system to store contacts and to re-store contacts


Technologies: Java, JSP, Servlet, JAX-RS, Jersey, HyperSQLDB,JSON,XML

How to deploy?
---------------
```
$> git clone https://github.com/mehikmat/KangarooService.git
$> cd kangarooservice
$> mvn clean package
$> mvn tomcat7:run
     OR
     
just copy the target/ROOT.war file to your own server

```
View HDSQLDB in GUI
```
java -cp target/repo/org/hsqldb/hsqldb/2.3.1/hsqldb-2.3.1.jar org.hsqldb.util.DatabaseManagerSwing
```
HSQLDB
-------
HSQLDB comes in a jar named hsqldb.jar
The HSQLDB jar package, hsqldb.jar, is located in the /lib directory of the ZIP
package or can be used from maven and contains several components and programs.

####Components of the Hsqldb jar package####
   - HyperSQL RDBMS Engine (HSQLDB)
   - HyperSQL JDBC Driver
   - Database Manager (GUI database access tool, with Swing and AWT versions)

The HyperSQL RDBMS and JDBC Driver provide the core functionality.
DatabaseManagers are general-purpose database access tools that can be
used with any database engine that has a JDBC driver.

####Main classes for the Hsqldb jar####
    - org.hsqldb.util.DatabaseManager (for accessing database)
    - org.hsqldb.util.DatabaseManagerSwing (for accessing database)
    - org.hsqldb.server.Server (for running in server mode)
    - org.hsqldb.server.WebServer(for running in HTTP server mode)

A HyperSQL Database
----------------------
Each HyperSQL database is called a catalog.
There are three types of catalog depending on how the data is stored.

Types of catalog data (###in-process mode###)
    - mem: stored entirely in RAM (jdbc:hsqldb:mem:mymemdb)
    - file: stored in filesystem files(jdbc:hsqldb:file:/opt/db/testdb)
    - res: stored in a Java resource, such as a Jar and always read-only(jdbc:hsqldb:res:org.my.path.resdb)

A file: catalog consists of between 2 to 6 files
    - test.properties
    - test.script
    - test.log
    - test.data
    - test.backup
    - test.lobs

###Server Mode###

For most applications, in-process access is faster, as the data is not converted
and sent over the network. The main drawback is that it is not possible by default
to connect to the database from outside your application.
As a result you cannot check the contents of the database with external tools such
as Database Manager while your application is running.

API EndPoints:
----------------
 - /api/auth/validate  for authentication purpose- sign in
 - /api/auth/add       for adding new customer- sign up
 - /api/contact/add    for adding new contact
 - /api/contact/list   for getting list of contacts
 - /api/customer/add   for adding new customer

 NOTE: All of the above endpoints are POST endpoints and MIME type JSON



