#!/bin/bash

if [ -z "$1" ]; then
	env_profile="default"
else
	if [ "$1" != "default" ] && [ "$1" != "prod" ]; then
		echo "The profile should be default or prod"
		exit
	fi

	env_profile=$1
fi

export env_profile;

echo "Profile set to "$env_profile

./mvnw clean install

docker-compose down

docker-compose build

docker-compose up -d --build --force-recreate