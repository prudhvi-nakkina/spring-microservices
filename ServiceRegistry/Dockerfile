FROM openjdk:17
# target - source folder(seriveReg)
ARG JAR_FILE=target/*.jar

#${} to access vars
# copying to new name in container
COPY ${JAR_FILE} serviceregistry.jar

# when container is started - ENTRYPOINT
# multiple commands - [], / is working dir
ENTRYPOINT ["java", "-jar", "/serviceregistry.jar"]

# starts in specified port
EXPOSE 8761

# docker build -t repo/imagename:tag {Dfile path} - build image from docker file