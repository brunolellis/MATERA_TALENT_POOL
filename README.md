# employees-api

Sample project to handle employees, creating, updating, retrieving and deleting (actually disabling) employees, exposing Employee resource through a REST API.


## API first
The idea is to start with the API definition, so it allows better alignment between a service producer and client consumers - also within our team.

Employee API definition can be found [here](src/main/resources/static/swagger.yml). The api definition is also embedded and available while the service is running ([here](http://localhost:8080/swagger.yml)). 

Please, copy and paste the definition on [Swagger Editor](https://editor.swagger.io/) for better visualization.

Note: a good way to improve the reliability of `swagger.yml` is to use `swagger-codegen-maven-plugin` which is a code generator.

## docker-compose: how to run

There are 2 docker containers: one responsible to start the api, using java 8 and spring (port 8080); and another one responsible for storing data using MySQL (port 3306). They should be started together: using `docker-compose`.

Let's create our artifact `employees-api.jar` running: 

    mvn clean package

Now to start our environment:

    docker-compose up

To stop the whole environment, either press `Ctrl+C` or run: 

    docker-compose stop

To start everything again: 

    docker-compose start

To remove everything:

    docker-compose down


## REST operations using curl

#### creating an employee

Request:

    curl -v -H "Content-Type: application/json" \
    -d '{ "firstName": "Bruno", "lastName": "Lellis", "dateOfBirth": "1985-06-24", "dateOfEmployment": "2015-09-01" }' \
    -X POST http://localhost:8080/api/v1/employees

Response:
- should return HTTP status code = 201
- should return the new employee with its id on response body

#### retrieving all employees

    curl -v http://localhost:8080/api/v1/employees

#### retrieving all employees with pagination

first page: 

    curl -v http://localhost:8080/api/v1/employees\?size\=5\&page\=0

second page: 

    curl -v http://localhost:8080/api/v1/employees\?size\=5\&page\=1

Note: 
- `size` represents how many items will be returned on each page - default: 20
- `page` starts from 0 and should be incremented on each page

#### retrieving all employees with pagination and sorting

sort by id descending: 

    curl -v http://localhost:8080/api/v1/employees\?size\=20\&page\=0\&sort\=id,desc

sort by dateOfBirth and then by dateOfEmployment: 

    curl -v http://localhost:8080/api/v1/employees\?size\=20\&page\=0\&sort\=dateOfBirth,asc\&sort\=dateOfEmployment,asc


#### retrieving one employee

    curl -v http://localhost:8080/api/v1/employees/1

#### updating employee

    curl -v -H "Content-Type: application/json" \
    -d '{ "firstName": "Bruno", "middleInitial": "H", "lastName": "Lellis", "dateOfBirth": "1985-06-24", "dateOfEmployment": "2015-09-01" }' \
    -X PUT http://localhost:8080/api/v1/employees/1

#### ~~deleting~~ disabling employee

    curl -v -X DELETE http://localhost:8080/api/v1/employees/1

