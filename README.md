# Glamour Connect

The main functionality of the project is the backend of a makeup site, when I got this assignment I remembered that my girlfriend wanted me to make her a makeup site
so, I combined these two into one single project.

I used an in memory database, H2 Database that has 4 main tables, the appointment table, makeup styles table, review table and also user table.
I tried to make simple endpoint for every one of them, like saving, finding, updating and deleting entries.
I logged actions and make use of exceptions for every corner case.

I wanted to make some different functionality so, I used Specification interface to make a custom search on makeups based on some simple filters.

For the boilerplate I used Lombok, for mapping from DTOs to POJOS I used a library called MapStruct. Related to the testing part I wrote some unit tests for the
exercise purpose not to cover the entire project, also I made an integration test with the same purpose for an endpoint using RestAssured.

The authentication part was a basic one based on encoding the user:password, the password was saved via BCrypt, I added a script to run at every application start and
add some users for testing purpose. I added 2 roles, USER and ADMIN, these roles were added per method/endpoint via @PreAuthorize, and I configured the FilterChain to
accept only authenticated request unless they are for swagger or h2-database console.

As a new Java 17+ feature I chose to use Records even though I had some issues. I added the Jackson library for serialization of LocalDateTime.

If I had more time I would want to add a proper authentication based on JWT with register, login and logout functionality. For the simplicity I also added just a role
not a set of roles.

As a bonus to this I would integrate Kafka and also TestContainers.

http://localhost:8080/swagger-ui/index.html - swagger

src/main/resources/postman_collection.json - Postman Collection
