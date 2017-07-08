# spring-boot-kotlin-elasticsearch-restful

**Spring Boot** **Kotlin**  Application Example with **Spring-Data-Jpa** and **Elasticsearch Java API** in **Docker** container. 

Also **Spring Actuator** integretion with Kotlin.



### Elasticsearch Create Index Template
Create an Elasticsearch Template with following command: 

curl -XPUT -u elastic:changeme 'localhost:9200/_template/people_1?pretty' -H 'Content-Type: application/json' -d'                                                                                              
{    
  "template": "people_1_*",
  "settings": {
    "number_of_shards": 1,
    "refresh_interval": "3s"
  },                        
  "mappings": {  
    "people_type": {
      "_source": {
        "enabled": true
      },
      "properties": {
        "id": {
          "type": "text",
      "fields": {
         "keyword": {
           "type": "keyword",
           "ignore_above":256
          }
      }
        },
        "name": {
          "type": "text",
      "fields": {
         "keyword": {
           "type": "keyword",
           "ignore_above":256
          }
      }
        },
        "surname": {
          "type": "text",
      "fields": {
         "keyword": {
           "type": "keyword",
           "ignore_above":256
          }
      }
        },
        "username": {
          "type": "text",
      "fields": {
         "keyword": {
           "type": "keyword",
           "ignore_above":256
          }
      }
        },
        "age": {
            "type": "integer"
        },
        "created_at": {
          "type": "date"
        }
      }
    }
  },
  "aliases": {}
}
'
### Elasticsearch post new document to *people_1_june* index

curl -XPUT -u elastic:changeme 'localhost:9200/people_1_june/people_type' -H 'Content-Type: application/json' -d'
    {
        "id": "3d7797e5-736f-441a-8a13-9a997ee71e17",
        "age": 27,
        "name": "oguzhan",
        "surname":"karacullu",
        "username":"mete00",
        "created_at": "2017-06-17"
    }               
'
* After creating the ES indexes and documents,

* first change "application.yml" then,

* **gradle clean**

* **gradle build**

* **gradle bootRun**

### Create Person with RestApi:
*curl -H "Content-Type: application/json" -X POST -d 
'{"name": "Oguzhan","surname": "Karacullu","username": "oguz00","age": 27}' http://localhost:8080/person*

### Get All Person from ES index wşth Java Api if exist, else getter from local PostgreSql DB
*curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET 'http://localhost:8080/person/all'*

### spring-boot-kotlin-actuator
*curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET 'http://localhost:8080/details'*

For Example Monitoring Endpoints:
*-X GET 'http://localhost:8080/details/mappings'*


* PS: Continue to integration elasticsearch impl.
