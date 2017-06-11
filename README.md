# spring-boot-kotlin-elasticsearch-restful

Spring Boot **Kotlin**  Application Example with **Spring-Data-Jpa** and **Elasticsearch**. 

Also **Spring Actuator** integretion with Kotlin.

* first change "application.yml"

**gradle clean**

**gradle build**

**gradle bootRun**

*curl -H "Content-Type: application/json" -X POST -d 
'{"name": "Oguzhan","surname": "Karacullu","username": "oguz00","age": 27}' http://localhost:8080/person*

*curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET 'http://localhost:8080/person/all'*

### spring-boot-kotlin-actuator
*curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET 'http://localhost:8080/details'*

For Example Monitoring Endpoints:
*-X GET 'http://localhost:8080/details/mappings'*


* PS: Continue to integration elasticsearch impl.
