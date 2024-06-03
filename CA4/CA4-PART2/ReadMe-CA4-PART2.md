# CA4-PART2 - Dockerizing the Spring Basic Tutorial Application

## Introduction
In this comprehensive guide, we are going to enhance our proficiency with Docker by configuring a containerized environment capable of running a Gradle-based version of the Spring Basic Tutorial application. Unlike our prior task where Vagrant was employed, this time around, our focus will be on leveraging Docker and Docker Compose to orchestrate and manage our containerized services.

We will delve into the intricacies of setting up the Docker infrastructure, ensuring that the environment is primed for our Gradle application. This involves creating Dockerfiles tailored to our application's needs, configuring Docker Compose files to manage multi-container setups, and exploring advanced Docker commands to streamline our workflow. Our objective is to move beyond the foundational knowledge of containerization, gaining a deeper understanding of how Docker and Docker Compose can be utilized to efficiently deploy and maintain complex applications.

# First Step: Setting Up the Docker Infrastructure

## 1.1 Create Dockerfiles

To begin, we need to create Dockerfiles for our Spring Basic Tutorial application. Dockerfiles are used to define the environment and dependencies required to run an application in a Docker container. We will create two Dockerfiles:

1.1.1 Create dockerfile in web directory
```bash 
# Create a directory for the project and clone the repository there
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app/

# Update package list and install Git
RUN apt-get update && apt-get install -y git

# Clone the repository and navigate to the project directory
RUN git clone https://github.com/Pedrotheofilo/devops-23-24-PSM-JPE-1231855.git .

# Navigate to the project directory
WORKDIR /usr/src/app/CA2/Part2/react-and-spring-data-rest-basic

# Change the permissions of the gradlew file to make it executable
RUN chmod +x gradlew

# Run the gradle build command
RUN ./gradlew build

# Copy the generated WAR file to the Tomcat webapps directory
RUN cp ./build/libs/react-and-spring-data-rest-basic-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/

# State the port that our application will run on
EXPOSE 8080

# Start Tomcat automatically when the container starts
CMD ["catalina.sh", "run"]
```

1.1.2 Create dockerfile in db directory
```bash
FROM ubuntu:latest

RUN apt-get update && \
    apt-get install -y openjdk-11-jdk-headless && \
    apt-get install unzip -y && \
    apt-get install wget -y

RUN mkdir -p /usr/src/app

WORKDIR /usr/src/app/

RUN wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar

EXPOSE 8082
EXPOSE 9092

CMD ["java", "-cp", "./h2-1.4.200.jar", "org.h2.tools.Server", "-web", "-webAllowOthers", "-tcp", "-tcpAllowOthers", "-ifNotExists"]
```

## 1.2 Create docker-compose.yml

Next, we will create a `docker-compose.yml` file to define and manage our multi-container setup. This file will specify the services, networks, and volumes required for our application. Here is an example of a `docker-compose.yml` file for our Spring Basic Tutorial application:

```bash
services:
  web:
    build: web
    ports:
      - "8080:8080"
    networks:
      default:
        ipv4_address: 192.168.56.10
    depends_on:
      - "db"
  db:
    build: db
    ports:
      - "8082:8082"
      - "9092:9092"
    volumes:
      - ./data:/usr/src/data-backup
    networks:
      default:
        ipv4_address: 192.168.56.11
networks:
  default:
    ipam:
      driver: default
      config:
        - subnet: 192.168.56.0/24
```

## 1.3 Building and run the Docker containers

To build and run the Docker containers, execute the following commands:

```bash
docker-compose build
docker-compose up
```

## 1.4 Publishing the Docker Image to Docker Hub

To publish the Docker image to Docker Hub, first log in to Docker Hub using the following command:

```bash
docker login
```

Then, tag the image with your Docker Hub username and push it to Docker Hub:

```bash
docker tag ca4-part2-web:ca4-part2 pedrotheofilo/ca4-part2:ca4-part2-web
docker tag ca4-part2-db:ca4-part2 pedrotheofilo/ca4-part2:ca4-part2-db
```

```bash
docker push pedrotheofilo/ca4-part2:ca4-part2-web
docker push pedrotheofilo/ca4-part2:ca4-part2-db
```

## 1.5 Running the Docker Container

To run the Docker container, use the following command:

```bash
docker run -p 8080:8080 pedrotheofilo/part2-web
```

## 1.6 Connecting to the Spring Basic Tutorial Application
    
To connect to the Spring Basic Tutorial application from the host machine, use the following command:

```bash
curl http://localhost:8080
```

# Working with Docker and Docker Compose

## 2.1 Docker and Docker compose Commands

Here are some useful Docker commands that you can use to manage your containers:

- `docker ps`: List all running containers
- `docker ps -a`: List all containers (including stopped ones)
- `docker stop <container_id>`: Stop a running container
- `docker start <container_id>`: Start a stopped container
- `docker rm <container_id>`: Remove a container
- `docker rmi <image_id>`: Remove an image
- `docker logs <container_id>`: View the logs of a container
- `docker exec -it <container_id> /bin/bash`: Access the shell of a running container
- `docker-compose up`: Start the services defined in the `docker-compose.yml` file
- `docker-compose down`: Stop and remove the services defined in the `docker-compose.yml` file
- `docker-compose logs`: View the logs of the services defined in the `docker-compose.yml` file
- `docker-compose exec <service_name> /bin/bash`: Access the shell of a running service
- `docker-compose build`: Build the services defined in the `docker-compose.yml` file
- `docker-compose push`: Push the services to Docker Hub
- `docker-compose pull`: Pull the services from Docker Hub
- `docker-compose restart`: Restart the services defined in the `docker-compose.yml` file
- `docker-compose stop`: Stop the services defined in the `docker-compose.yml` file
- `docker-compose start`: Start the services defined in the `docker-compose.yml` file
- `docker-compose up -d`: Start the services in detached mode
- `docker-compose down -v`: Stop and remove the services and volumes defined in the `docker-compose.yml` file
- `docker-compose ps`: List the services defined in the `docker-compose.yml` file
- `docker-compose images`: List the images used by the services defined in the `docker-compose.yml` file
- `docker-compose top`: Display the running processes of the services defined in the `docker-compose.yml` file
- `docker-compose port <service_name> <port_number>`: Print the public port mapped to a private port of a service
- `docker-compose logs -f <service_name>`: View the logs of a specific service in real-time
- `docker-compose exec <service_name> <command>`: Execute a command in a running service
- `docker-compose run <service_name> <command>`: Run a one-off command in a service
- `docker-compose config`: Validate and view the configuration of the `docker-compose.yml` file
- `docker-compose events`: Get real-time events from the services defined in the `docker-compose.yml` file
- `docker-compose help`: Get help on the `docker-compose` command
- `docker-compose version`: Show the Docker Compose version information
- `docker-compose --verbose`: Show verbose output for debugging
- `docker-compose --log-level`: Set the log level (`debug`, `info`, `warn`, `error`, `fatal`, `panic`)

## 2.2 Create and Use a Volume for the Database

Use Docker exec to access the db container and copy the database file to the volume:

```bash
docker -compose exec db bash
```

Inside de container shell:

```bash
    cp /usr/src/app/h2-1.4.200.jar /usr/src/data-backup
   ```

## Creating tag for de Class Assignment 4

```bash
git tag -a ca4-part2 -m "CA4 Part 2"
git push origin ca4-part2
```

# Conclusion

In this guide, we have explored the process of Dockerizing the Spring Basic Tutorial application using Docker and Docker Compose. By creating Dockerfiles, configuring Docker Compose files, and executing advanced Docker commands, we have successfully containerized our Gradle-based application. This exercise has provided us with valuable insights into the intricacies of containerization and the benefits of using Docker for deploying and managing applications. We have also learned how to publish Docker images to Docker Hub and connect to containerized services from the host machine. By mastering these concepts, we are better equipped to leverage Docker and Docker Compose for developing and deploying complex applications in a containerized environment.

## Investigating Heroku as a Viable Option

We'll be examining one of the recommended alternatives: Heroku. Although it doesn't serve as a direct substitute for Docker, it offers a different approach for deploying containers to the cloud.

Unlike many of the open-source tools we've utilized in this course, Heroku is a commercial service. Consequently, we won't be implementing this alternative fully, but we'll provide a detailed walkthrough of the necessary steps.

### 3.1 Setting Up Heroku

To begin, you'll need to create a Heroku account and install the Heroku CLI. The Heroku CLI allows you to manage your Heroku applications directly from the command line.

### 3.2 Deploying the Spring Basic Tutorial Application to Heroku

To deploy the Spring Basic Tutorial application to Heroku, follow these steps:

1. Create a new Heroku application using the Heroku CLI:
```bash
heroku create
```

2. Deploy the application to Heroku:
```bash
git push heroku master
```

3. Open the deployed application in your web browser:
```bash
heroku open
```

4. View the logs of the deployed application:
```bash
heroku logs --tail
```

### 3.3 Conclusion
After completing these steps, you can access your application via the unique URL generated during the application creation process. Open your browser to view the information about Frodo Baggins.

