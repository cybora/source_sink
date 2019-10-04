The application was made in microservice architecture as it consists of the main fixture program , gateway and server registry.

The microservices are dockerized and orchestrated via docker-compose

To start the program please run the build-all.sh with the profile argument ( if no arg is given then it gets the default profile )

build-all.sh prod -> for production ready version to be run on the docker environment
build-all.sh default -> dev and testing version to be run on the localhost

After the application is started, please make a GET request to the URL below in order to start the process

Assume ADDRESS is the address of the environment , it may be localhost in case of run in the localhost or the IP / host name for the remote environment

http://ADDRESS://8080/api/fixtures/start
