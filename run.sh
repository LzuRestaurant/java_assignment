#!/bin/zsh

mvn clean package
sudo cp target/PegasusHospital-1.0-SNAPSHOT.war /var/lib/tomcat10/webapps/ROOT.war

# scp -P 5032 target/PegasusHospital-1.0-SNAPSHOT.war chen@119.3.189.165:/home/chen/target/
# sudo cp target/PegasusHospital-1.0-SNAPSHOT.war /var/lib/tomcat10/webapps/ROOT.war
# ls /var/lib/tomcat10/webapps/
# sudo systemctl restart tomcat10
# sudo vim /etc/tomcat10/server.xml

# 部署（运行一次即可）
# sudo systemctl stop tomcat10
# sudo rm -rf /var/lib/tomcat10/webapps/ROOT/
# sudo cp target/PegasusHospital-1.0-SNAPSHOT.war /var/lib/tomcat10/webapps/ROOT.war
# sudo systemctl start tomcat10