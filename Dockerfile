FROM tomcat:8.5.16-jre8-alpine

MAINTAINER Fazliddin Makhsudov "fazl.makhsudov@gmail.com"

ADD target/online-library.war /usr/local/tomcat/webapps/

EXPOSE 8080

CMD ["catalina.sh", "run"]
