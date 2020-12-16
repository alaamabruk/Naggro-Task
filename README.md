# Naggro-Task

Statement Task 
notes the application
i make a simple Spring boot app for news Subscription and using spring boot security and JWT authentication for user login and authenticate for further requests by Jwt token i make it expires after 5 minutes..

using Spring Boot , Spring Security (JWT) , Rest Apis , MSAccess as the DB!.

application services :
 * Statement Task microservice : runs on port 2020
steps
1.Update application.properties 
   db.conectionstring=jdbc:ucanaccess://e:/accountsdb.accdb;showSchema=true
2.prepare your enviroment if you work locally or on a production :
  * clone the git repository
  * setup java ide (sts, intelij,etc)
  * import the News Subscription microservicein the ide and download dependencies.
4. run the microservice.
5. you can now work with the rest apis.

These apis implement user-management functionality, where user can login through an /login and then can use the /api/ by specifing the returned Bearer token in authorization header of request

There are two different roles:
   Admin: Can findStatement by filter using parameters (amount and dates)
   User: Can can not findStatement by filter using parameters (amount and dates) just by AccountId 


endpoints ( default is GET )
* localhost:2020/login           //User Login and authentication retrive (jwt token)to use in all further      requests (POST).
sample Json  for /login: 
{
	"password":"adminpassword",
	"userName":"testadmin"
}

* localhost:2020/api/findStatement/{accountId}           //User Can find statements as adminRole with add parm for date and amount (GET).

* localhost:2020/api/findStatement/{accountId}           //User Can find statements as UserRole without add  any (GET).

* localhost:2020/logOut           //User LogOut and (jwt token) (GET).
