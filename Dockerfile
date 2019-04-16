FROM tomcat:8.5-jre8
MAINTAINER sashapotapovich@gmail.com
COPY /target/app.war /usr/local/tomcat/webapps/app.war
CMD ["catalina.sh", "run"]
