# B2Boost Assignment 
> https://b2boost.bitbucket.io/grails-problem-1/

[![Grails Version][grails-image]][grails-url]

## Requirements

A running docker agent with an internet connection

## Checklist

- [ ] The endpoint will return custom error JSON messages in the payload, additionally to the standard HTTP response codes
- [ ] The controller of the endpoint will not contain any business logic, and will limit itself to
- [ ] marshalling data from the http layer to the service layer (using grails Command objects)
- [ ] reporting error conditions in the response
- [ ] marshalling results back to the http layer, including custom errors
- [ ] The service layer will be transactional and encapsulate all validation and database interactions
- [ ] The application will run with an embedded H2 in-memory database
- [ ] The application will have a health check endpoint
- [ ] The application will have suitable functional tests
- [ ] No authentication/security necessary


## Installation

OS X & Linux & Windows:
```sh
docker build -t bvanmelle/b2b-assignment .
docker run -it -p 8080:8080 bvanmelle/b2b-assignment grails
```
Once Grails command line open, you can then run grails commands:
- run-app
- test-app	
- test-app --integration
- package (You need to mount a volume on Docker to get the build output)

## Health check

Standard SpringBoot HealthCheck can be reached at the URL: /actuator/health

## Partners API

```sh
curl http://localhost:8080/..
```

## Development setup

You may want to mount local volume on the container to get the changes live. In this case, bind local directories.

Windows:
```sh
docker run -it --mount type=bind,src=%CD%\test-results,dst=/usr/b2b-rest-app/build/test-results --mount type=bind,src=%CD%\build.gradle,dst=/usr/b2b-rest-app/build.gradle --mount type=bind,src=%CD%\grails-app,dst=/usr/b2b-rest-app/grails-app --mount type=bind,src=%CD%\src,dst=/usr/b2b-rest-app/src -p 8080:8080 bvanmelle/b2b-assignment grails
```
OS X & Linux (not verify yet):
```sh
docker run -it --mount type=bind,src=$(pwd)/test-results,dst=/usr/b2b-rest-app/build/test-results --mount type=bind,src=$(pwd)/build.gradle,dst=/usr/b2b-rest-app/build.gradle --mount type=bind,src=$(pwd)/grails-app,dst=/usr/b2b-rest-app/grails-app --mount type=bind,src=$(pwd)/src,dst=/usr/b2b-rest-app/src -p 8080:8080 bvanmelle/b2b-assignment grails
```

Alternatively, if you wish to develop in you own env, you need to install jdk8 and grails 4.0.3; 
See: http://docs.grails.org/latest/guide/gettingStarted.html

Run the tests:
```sh
grails run-test
```
Start the App:
```sh
grails run-app
```

## Contact

Benjamin Van Melle – [@LinkedIn](https://www.linkedin.com/in/benjaminvm/) – vanmelle.benjamin@gmail.com

<!-- Markdown link & img dfn's -->
[grails-image]: https://img.shields.io/badge/Grails-4.0.3-blue
[grails-url]: https://docs.grails.org/4.0.3/guide/single.html