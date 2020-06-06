# B2Boost Assignment 
> Benjamin Van Melle - June 2020
> - https://b2boost.bitbucket.io/grails-problem-1/

## Requirements

A running docker agent with an internet connection

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
- package (Mount a volume on Docker to get the output)

## Usage example

```sh
curl http://localhost:8080/..
```

## Development setup

You may want to mount local volume on the container to get the changes live. In this case, bind local directories.

Windows:
```sh
docker run -it --mount type=bind,src=%CD%\grails-app,dst=/usr/b2b-rest-app/grails-app --mount type=bind,src=%CD%\src,dst=/usr/b2b-rest-app/src -p 8080:8080 bvanmelle/b2b-assignment grails
```
OS X & Linux (not verify yet):
```sh
docker run -it --mount type=bind,src=$(pwd)/grails-app,dst=/usr/b2b-rest-app/grails-app --mount type=bind,src=$(pwd)/src,dst=/usr/b2b-rest-app/src -p 8080:8080 bvanmelle/b2b-assignment grails
```

Alternatively, if you wish to develop in you own env, you need to install jdk8 and grails 4.0.1; 
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
