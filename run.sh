#!/bin/zsh

mvn clean package
sudo cp target/PegasusHospital-1.0-SNAPSHOT.war /var/lib/tomcat10/webapps/hospital.war