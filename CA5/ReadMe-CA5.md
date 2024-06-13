# ReadMe - CA5 - CI/CD - Pipelines with Jenkins

## Introduction

The tutorial is structured into two sections, each concentrating on a different application. The first section details the setup of a Jenkins pipeline for the Gradle Basic Demo application from CA2/part1. The second section focuses on establishing a Jenkins pipeline for the React and Spring Data REST application from CA2/part2. The pipelines are crafted using Jenkinsfile declarative syntax, encompassing stages for building, testing, generating Javadoc, and creating a Docker image. These pipelines are configured to operate on a Jenkins server, streamlining the processes of building, testing, and deploying the applications.

The tutorial offers detailed, step-by-step guidance on how to create and run the pipelines in Jenkins to build and deploy the applications effectively. Each pipeline is designed to provide feedback on the build status, generate test result reports, and produce Javadoc. The Docker image created in the tutorial's second section is uploaded to Docker Hub for deployment. This tutorial showcases how to configure Jenkins, establish the pipelines, and execute them, automating the CI/CD process for both applications.

Additionally, the tutorial emphasizes best practices for CI/CD, ensuring that users not only learn the mechanics of pipeline creation but also understand the underlying principles of efficient and reliable application deployment.

### Part 1: Setting up Jenkins Pipeline for Gradle Basic Demo Application

The first section of the tutorial focuses on setting up a Jenkins pipeline for the Gradle Basic Demo application from CA2/part1. The pipeline is designed to build the application, run tests, generate Javadoc, and create a Docker image. The pipeline is created using Jenkinsfile declarative syntax and is configured to execute on a Jenkins server.

#### Prerequisites

Before you begin, ensure you have the following installed on your Jenkins server:

- Jenkins
- Git

#### Pipeline Configuration

The Jenkins pipeline is defined in the `Jenkinsfile` located in the project directory. Below is an overview of the
stages involved in the pipeline:

1. **Checkout**: Clones the project repository from GitHub.
2. **Assemble**: Builds the project using Gradle.
3. **Test**: Runs the tests and records the results.
4. **Archive**: Archives the built JAR file.

##### Jenkinsfile

```groovy
pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out from repository'
                git url: 'https://github.com/Pedrotheofilo/devops-23-24-PSM-JPE-1231855.git', branch: 'main'
            }
        }
        stage('Assemble') {
            steps {

                dir('CA2-Part1/gradleBasicDemo') {
                    echo 'Assembling...'
                    sh 'chmod +x gradlew'
                    sh './gradlew clean assemble'
                }
            }
        }
        stage('Test') {
            steps {

                dir('CA2-Part1/gradleBasicDemo') {
                    echo 'Running Tests...'
                    sh './gradlew test'
                    junit 'build/test-results/test/*.xml'
                }
            }
            post {
                always {

                    junit 'CA2-Part1/gradleBasicDemo/build/test-results/test/*.xml'
                }
            }
        }
        stage('Archive') {
            steps {

                dir('CA2-Part1/gradleBasicDemo') {
                    echo 'Archiving artifacts...'

                    archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
                }
            }
        }
    }
}
```
#### Create a New Pipeline Job in Jenkins

1. Go to Jenkins and click on `New Item`.
2. Enter a name for the pipeline and select `Pipeline`.
3. Under `Pipeline`, select `Pipeline script from SCM`.
4. Choose `Git` as the SCM and enter the repository URL.
5. Enter `main` as the branch.
6. In the `Script Path`, enter `ca5/gradle_basic_demo/Jenkinsfile`.
7. Click on `Save`.

#### Run the Pipeline

1. Click on `Build Now` to run the pipeline.
2. Monitor the progress of the pipeline in the Jenkins console output.

#### Conclusion - Part1

In this section, a Jenkins pipeline was established to build and test the Gradle Basic Demo application from CA2/part1. The pipeline automates the steps of compiling the application and executing tests, delivering feedback on the build status.

### Part 2: Setting up Jenkins Pipeline for React and Spring Data REST Basic Application

The second section of the tutorial focuses on setting up a Jenkins pipeline for the React and Spring Data REST Basic application from CA2/part2. The pipeline is designed to build the application, run tests, generate Javadoc, and create a Docker image. The pipeline is created using Jenkinsfile declarative syntax and is configured to execute on a Jenkins server.

#### Prerequisites

Before you begin, ensure you have the following installed on your Jenkins server:

- Jenkins
- Docker
- Git

#### Pipeline Configuration

The Jenkins pipeline is defined in the `Jenkinsfile` located in the project directory. Below is an overview of the
stages
involved in the pipeline:

1. **Checkout**: Clones the repository from GitHub.
2. **Create Dockerfile**: Creates a `Dockerfile` if it doesn't exist.
3. **Assemble**: Builds the project using Gradle.
4. **Test**: Runs unit tests and records the results.
5. **Javadoc**: Generates Javadoc and publishes it as an HTML report.
6. **Archive**: Archives the built JAR file.
7. **Build Docker Image**: Builds a Docker image and pushes it to Docker Hub.

##### Jenkinsfile

```groovy
pipeline {
    agent any

    environment {
        DOCKERHUB_TOKEN = credentials('dockerhub')
        IMAGE_NAME = "${env.DOCKERHUB_TOKEN_USR}/jenkins-ca5-devops"
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out...'
                git branch: 'main', credentialsId: 'jenkins', url: 'https://github.com/Pedrotheofilo/devops-23-24-PSM-JPE-1231855.git'
            }
        }

        stage('Assemble') {
            steps {
                dir('CA2-Part2/react-and-spring-data-rest-basic') {
                    echo 'Assembling...'
                    sh 'chmod +x gradlew'
                    sh './gradlew clean assemble'
                }
            }
        }

        stage('Test') {
            steps {
                dir('CA2-Part2/react-and-spring-data-rest-basic') {
                    echo 'Testing...'
                    sh './gradlew test'
                    junit 'build/test-results/test/*.xml'
                }
            }
        }

        stage('Javadoc') {
            steps {
                dir('CA2-Part2/react-and-spring-data-rest-basic') {
                    echo 'Generating Javadocs...'
                    sh './gradlew javadoc'
                    publishHTML(target: [
                            reportDir  : 'build/docs/javadoc',
                            reportFiles: 'index.html',
                            reportName : 'Javadoc'])
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                dir('CA2-Part2/react-and-spring-data-rest-basic') {
                    echo 'Building Docker Image...'
                    script {
                        dockerImage = docker.build("${IMAGE_NAME}:${BUILD_NUMBER}", "-f Dockerfile .")
                    }
                }
            }
        }

        stage('Docker Login') {
            steps {
                echo 'Logging in to Docker Hub...'
                sh 'echo $DOCKERHUB_TOKEN_PSW | docker login -u $DOCKERHUB_TOKEN_USR --password-stdin'
            }
        }

        stage('Push Docker Image') {
            steps {
                echo 'Pushing Docker Image...'
                sh 'docker push $IMAGE_NAME:$BUILD_NUMBER'
            }
        }

        stage('Archiving') {
            steps {
                dir('CA2-Part2/react-and-spring-data-rest-basic') {
                    echo 'Archiving...'
                    archiveArtifacts 'build/libs/*'
                }
            }
        }
    }

    post {
        always {
            echo 'Cleaning up...'
            sh 'docker rmi $IMAGE_NAME:$BUILD_NUMBER'
            sh 'docker logout'
        }
    }
}
```

#### Dockerfile

```groovy
FROM tomcat:10-jdk17

COPY build/libs/react-and-spring-data-rest-basic-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/

EXPOSE 8080
````

#### Configure Docker Credentials in Jenkins

1. Go to Manage Jenkins > Manage Credentials > System > Global credentials (unrestricted) > Add Credentials.
2. Select `Username with password` as the kind.
3. Enter your Docker Hub username and password.
4. Set the ID to `docker-credentials`.

#### Create a New Pipeline in Jenkins

1. Go to Jenkins and click on `New Item`.
2. Enter a name for the pipeline and select `Pipeline`.
3. Under `Pipeline`, select `Pipeline script from SCM`.
4. Choose `Git` as the SCM and enter the repository URL.
5. Enter `main` as the branch.
6. In the `Script Path`, enter `ca5/react-and-spring-data-rest-basic/Jenkinsfile`.
7. Click on `Save`.

#### Run the Pipeline

1. Click on `Build Now` to run the pipeline.
2. Monitor the progress of the pipeline in the Jenkins console output.


#### Conclusion - Part 2

In this section, a Jenkins pipeline was established to build, test, generate Javadoc, and create a Docker image for the React and Spring Data REST application from CA2/part2. This pipeline automates the entire process, from compiling and testing the application to deploying it on Docker Hub.

## Summary

The tutorial provides a comprehensive guide on setting up Jenkins pipelines for two different applications, demonstrating the automation of the CI/CD process. By following the step-by-step instructions and best practices outlined in the tutorial, users can effectively configure and execute Jenkins pipelines for their projects, streamlining the build, test, and deployment processes. The tutorial emphasizes the importance of automation, feedback, and reliability in the CI/CD pipeline setup, ensuring that users gain a solid understanding of the principles and practices involved in efficient application deployment.

By leveraging Jenkins pipelines, developers can enhance their development workflow, reduce manual intervention, and accelerate the delivery of high-quality software products. The tutorial serves as a valuable resource for individuals looking to implement CI/CD pipelines in their projects, enabling them to leverage the power of automation and continuous integration for improved software development practices.

## Creating a Tag for the Class Assignment 5

```bash
git tag -a ca5 -m "CA5"
git push origin ca5
```

