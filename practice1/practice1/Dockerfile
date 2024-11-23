# Image Dockerfile is your notebook
# install java

# IMAGENAME: VERSION, MUST BE FIRST LINE IN FILE
# Get the server
FROM eclipse-temurin:23-jdk

LABEL maintainers = "yuaunlim"


##How to bulid the app

#Create a directory /app and change directory into /app
# Outside of /app  
# Files added here

WORKDIR /app
# Inside of /app directory
# Copy files over src destinations, 
#for filescan just put a .
COPY mvnw .
COPY pom.xml .

#For directory must give name
COPY src src
COPY .mvn .mvn

#Build the application
RUN chmod a+x ./mvnw && /app/mvnw package -Dmaven.test.skip=true

#If bulid is successful then the jar is in ./target/filename.jar



##How to run the application

# Set the environment variable default port is 8080
#ENV SERVER_PORT=8080 

#For Railway
ENV PORT=8080

# What port does the application need
EXPOSE ${PORT}


#Run the app
ENTRYPOINT java -jar target/practice1-0.0.1-SNAPSHOT.jar

