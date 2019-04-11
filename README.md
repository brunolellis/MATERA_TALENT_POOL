# employees-api

Sample project to handle employees, creating, updating, retrieving and deleting (actually disabling) employees, exposing Employee resource through a REST API.


## API first
The idea is to start with the API definition, so it allows better alignment between a service producer and client consumers - also within our team.

Employee API definition can be found [here](src/main/resources/static/swagger.yml). The api definition is also embedded and available while the service is running ([here](http://localhost:8080/swagger.yml)). 

Please, copy and paste the definition on [Swagger Editor](https://editor.swagger.io/) for better visualization.


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

#### retrieving one employee

    curl -v http://localhost:8080/api/v1/employees/1

#### updating employee

    curl -v -H "Content-Type: application/json" \
    -d '{ "firstName": "Bruno", "middleInitial": "H", "lastName": "Lellis", "dateOfBirth": "1985-06-24", "dateOfEmployment": "2015-09-01" }' \
    -X PUT http://localhost:8080/api/v1/employees/1

#### ~~deleting~~ disabling employee

    curl -v -X DELETE http://localhost:8080/api/v1/employees/1


