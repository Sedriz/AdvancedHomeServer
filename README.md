# AdvancedHomeServer
###### created by SedrikDE
The Backend application that your clients will talk to.  
Works with Raspberry PI 4.

Spring boot Application

## Run Server
1. Clone this repository.
2. Update variables in file: ``docker_smarthome/secret.env``
3. Start docker compose with: ``docker_advanced_home/docker-compose up -d``

### Required:
- Java 17
- Docker
- MQTT (Mosquitto)

### Profiles:
1. Prod (default):
2. Dev:
   1. H2 Database
   2. Env. var in application-dev.yml

### MQTT Topics:
1. Button event:
###### Topic:
main/998/

###### Payload:
{
  "timestamp": 1643896156000,
  "locationId": 5,
  "motionEvent": false
}

---

2. Device event:
###### Topic:
main/{deviceID}/{datatypeID}

###### Payload:
JSON as defined

---

## Help:
#### MQTT Error:
#####MQTT Container Permission denied exception:
``sudo chmod -u+x /path/to/dir``

#### Postgres db password or username wrong:
Make sure that these variables in secret.env are the same:
DB_USERNAME -> POSTGRES_USER
DB_PASSWORD -> POSTGRES_PASSWORD



### Todo:
1. Write tests
2. Implement Google Dialog Flow

### Issues:
If you have a issue with mqtt:
Change CRLF to LF -> https://stackoverflow.com/a/52665687/16598884
- spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning
-> possible fix: spring.jpa.open-in-view=false
-> test if everything is still working
