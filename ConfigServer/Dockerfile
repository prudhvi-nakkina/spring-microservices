FROM openjdk:17
# target - source folder(seriveReg)
ARG JAR_FILE=target/*.jar

#${} to access vars
# copying to new name in container
COPY ${JAR_FILE} configserver.jar

# when container is started - ENTRYPOINT
# multiple commands - [], / is working dir
ENTRYPOINT ["java", "-jar", "/configserver.jar"]

# starts in specified port
EXPOSE 9296

# docker build -t repo/imagename:tag {Dfile path} - build image from docker file
#docker run -d -p9296:9296 -e EUREKA_SERVER_ADDRESS=http://host.docker.internal:8761/eureka --name configserver d49b08024f65