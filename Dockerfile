FROM tomcat:8.5.47

MAINTAINER Fazliddin Makhsudov "fazl.makhsudov@gmail.com"

ADD target/library.war /usr/local/tomcat/webapps/library.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
