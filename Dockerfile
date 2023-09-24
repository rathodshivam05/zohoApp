from openjdk:8
expose 8080
add target/ZohoApplication-0.0.1-SNAPSHOT.jar ZohoApplication-0.0.1-SNAPSHOT.jar
entrypoint ["java","-jar","/ZohoApplication-0.0.1-SNAPSHOT.jar"]