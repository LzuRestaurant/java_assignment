#!/bin/bash

mvn clean package

scp -P 5032 target/PegasusHospital-1.0.war chen@119.3.189.165:/home/chen/target/
ssh -p 5032 chen@119.3.189.165 ./run_tomcat.sh