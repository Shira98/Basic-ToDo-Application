# Basic_ToDo_Application
A *REST* based *Spring* to-do applcation with basic functionalities - *create* item, *delete* item and *retrieve* all items. Uses *MySQL* database connection and *Postman* for verifying the requests. 
Includes unit and integration tests for every functionality. Testing is done using the in-memory database *H2*.

## Application Structure
### src/main/java
Contains the general model, controller, service and repository folders under the package **com.practice.todo**.

### src/test/java
Contains **integration** and **unit** test folders under the package **com.practice.todo**. The **unit** folder further contains folders for testing controller, service and repository functionalities respectively.

## MySQL Database Connection Setup
The database *name*, *username* and *password* can be changed in the **todo/src/main/resources/application.properties** file based on  preference. 
 
## H2 Connection Setup
Configuration details are present in the **todo/src/test/resources/appplication-test.properties** file and can be changed based on preference.

## Postman for Verifying the Connection
Uses Postman to perform *GET*, *POST* and *DELETE* requests, and checks if the changes are reflected in the database respectively. Check by logging in as the user and by using the database specified during the MySQL connection setup while performing the requests through *Postman*.
