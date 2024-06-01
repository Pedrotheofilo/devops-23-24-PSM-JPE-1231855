# CA4 Part 1 - Containers with Docker


## Introduction

The aim of this class assignment is to showcase the use of Docker containers. Docker is a platform that enables developers to build, ship, and run applications in containers. The primary goal is to run the chat server in a container and connect to it from the host machine.

## Steps

## First Version

### 1. Dockerfile Creation

Create a file named Dockerfile in the project directory with the following content:

#### Use a Gradle image with JDK 17 to build the application

```Dockerfile
FROM gradle:jdk17 AS builder

#### Set the working directory for the build
WORKDIR /CA4/Part1

#### Clone the repository
RUN git clone https://bitbucket.org/pssmatos/gradle_basic_demo.git

#### Set the working directory to the cloned repository
WORKDIR /CA4/Part1/gradle_basic_demo

#### Ensure the Gradle wrapper has the correct permissions
RUN chmod +x gradlew

#### Build the application
RUN ./gradlew build

#### Use a slim JRE image for the runtime
FROM eclipse-temurin:17-jre

#### Set the working directory
WORKDIR /app

#### Copy the built JAR file from the builder stage
COPY --from=builder /CA4/Part1/gradle_basic_demo/build/libs/basic_demo-0.1.0.jar /app/basic_demo-0.1.0.jar

#### Expose the port the server will run on
EXPOSE 59001

#### Set the entry point to run the server
ENTRYPOINT ["java", "-cp", "/app/basic_demo-0.1.0.jar", "basic_demo.ChatServerApp", "59001"]
````

### 2. Building the Docker Image

In the terminal, navigate to the directory where the Dockerfile is located and run the following command to build the Docker image:

```bash
docker build -t pedrotheofilo/chatserver:version1 .
````

### 3. Pushing the Docker Image to Docker Hub

To publish the Docker image to Docker Hub, first log in to Docker Hub using the following command:


```bash
docker login
````

Then, tag the image with your Docker Hub username and push it to Docker Hub:

```bash
docker tag pedrotheofilo/chatserver:version1 pedrotheofilo/chatserver:version1
docker push pedrotheofilo/chatserver:version1
````

### 4. Running the Docker Container

To run the Docker container, use the following command:

```bash
docker run -p 59001:59001 pedrotheofilo/chatserver:version1
````

### 5. Connecting to the Chat Server

To connect to the chat server from the host machine, use the following command:

```bash
telnet localhost 59001
````

## Second Version

Building the Chat Server on the Host Machine (and copying the JAR file to the Docker image)

### 1. Compile the Chat Server

Compile the Chat Server on the host machine using the following command:

```bash
./gradlew build
````

This will generate the JAR file in the `build/libs` directory.

### 2. Create a Dockerfile

Create a new Dockerfile with the following content:

```Dockerfile
# Use a Gradle image with JDK 17 to build the application
FROM gradle:jdk17 AS builder

# Set the working directory for the build
WORKDIR /CA4/Part1

# Clone the repository
RUN git clone https://bitbucket.org/pssmatos/gradle_basic_demo.git

# Set the working directory to the cloned repository
WORKDIR /CA4/Part1/gradle_basic_demo

# Ensure the Gradle wrapper has the correct permissions
RUN chmod +x gradlew

# Build the application
RUN ./gradlew build

# Use a slim JRE image for the runtime
FROM eclipse-temurin:17-jre

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /CA4/Part1/gradle_basic_demo/build/libs/basic_demo-0.1.0.jar /app/basic_demo-0.1.0.jar

# Expose the port the server will run on
EXPOSE 59001

# Set the entry point to run the server
ENTRYPOINT ["java", "-cp", "/app/basic_demo-0.1.0.jar", "basic_demo.ChatServerApp", "59001"]
````

### 3. Building the Docker Image

In the terminal, navigate to the directory where the Dockerfile is located and run the following command to build the Docker image:

```bash
docker build -t pedrotheofilo/chatserver:version2 .
````

### 4. Pushing the Docker Image to Docker Hub

To publish the Docker image to Docker Hub, first log in to Docker Hub using the following command:

```bash
docker login
````

Then, tag the image with your Docker Hub username and push it to Docker Hub:

```bash
docker tag pedrotheofilo/chatserver:version2 pedrotheofilo/chatserver:version2
docker push pedrotheofilo/chatserver:version2
````

### 5. Running the Docker Container

To run the Docker container, use the following command:

```bash
docker run -p 59001:59001 pedrotheofilo/chatserver:version2
````

### 6. Connecting to the Chat Server

To connect to the chat server from the host machine, use the following command:

```bash
telnet localhost 59001
````

## Error Handling

If you encounter an error while running the Docker container, ensure that the JAR file is present in the project directory and that the Dockerfile is correctly referencing the JAR file.
Locate the JAR file: Gradle typically places the built JAR file in the build/libs directory. Verify that the JAR file exists in this location:

```bash
ls build/libs
````

If the JAR file is not present, try rebuilding the application using the Gradle build command:

```bash
./gradlew build
````

After rebuilding the application, check the build/libs directory again to ensure that the JAR file is present.

If the JAR file is present, update the Dockerfile to copy the JAR file from the correct location. Modify the COPY command in the Dockerfile to reference the correct path to the JAR file:

```Dockerfile
COPY --from=builder /path/to/your/jarfile.jar /app/your-jarfile.jar
````

## Creating tag for the Class Assignment

    git tag -a ca4-part1 -m "CA4 Part 1"
    git push origin ca4-part1   


## Conclusion

In this class assignment, we demonstrated how to build a Docker image for a chat server application and run it in a container. By following the steps outlined in this guide, you can create a Docker image for your application and run it in a container environment. Docker provides a convenient way to package and deploy applications, making it easier to manage dependencies and ensure consistent behavior across different environments.


