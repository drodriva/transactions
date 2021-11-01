# The Transactions project

### Small spring boot example with REST APIs for customers transactions handling.

* Runs redis embedded locally for customers caching
* TransactionController contains and api with paginated and sorted results example
* It contains apis for some filtering examples, i.e transactions between dates or with amounts greater than a value.
* Initial data is loaded on H2 database when starting the app. It's data.sql file.

It runs in memory H2 database and redis embedded for caching purposes (not using redis repositories).

The h2 console is enable by default, running the app locally you can access it from here:

```
http://localhost:8080/h2-console/
```

The project has three rest controllers to handle and manage customers, accounts and transactions entities. Documentation from open API can be checked here when
running the app locally:

```
http://localhost:8080/swagger-ui.html
```

Embedded Redis runs in port 6379. If the application doesn't stop properly the proccess could keep running so it needs to be manually stopped, i.e

In Ubuntu run:

```
> lsof -i :6379
```
and then kill the idle process id where redis embedded is running


You can install and pack the project in your local repository by using maven:
```
> mvn clean install
```

To execute the unit test suite run the following:
```
> mvn test
```

To run the spring boot app locally:
```
> mvn spring-boot:run
```
or use your prefered IDE :)

