FROM adoptopenjdk/openjdk11
EXPOSE 8080
ADD target/*.jar spring_boot_redis_caching_app.jar
ENTRYPOINT ["java","-jar","spring_boot_redis_caching_app.jar"]
