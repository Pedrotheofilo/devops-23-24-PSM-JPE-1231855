## README

## Technical Report For CA2 Part1: Build Tools with Gradle

# Introduction

This technical report aims to document the process and insights gained from completing Part 1 of Class Assignment 2 (CA2), which focuses on mastering build tools through the practical application of Gradle. The assignment entails a hands-on approach, commencing with the setup of an example application and progressing to utilizing Gradle for task automation, encompassing server execution, unit testing, and file management tasks.

# Implementation Steps

## First step

To begin, you'll want to replicate this repository ( https://bitbucket.org/pssmatos/gradle_basic_demo/ )
your personal computer. This repository will act as the foundation for your task. For this assignment, only the basic
module is necessary; you can safely remove any other modules.

Subsequently, the remaining steps of the assignment can be completed by launching a bash terminal and executing the
subsequent commands:

1. Navigate to the project directory (assuming the gradle_basic_demo) is already locally
   available):
```bash
   cd path/to/Repositorio/devops-23-24-PSM-JPE-1231855/CA2-Part1
```
   
2. Copy the application into a new CA1 folder:

```bash
   cp -r . ../CA2-Part1   
  
   cd ../CA2-Part1 
```

# Implementing Changes

## Read the instructions available in the readme.md file and experiment with the application.

1. Open a bash in the basic folder of the app and run the following command:
```bash
./gradlew build 

% java -cp build/libs/basic_demo-0.1.0.jar basic_demo.ChatServerApp 59001 

% ./gradlew runClient 

% ./gradlew runSever 

```
![Devops CA2PArt1.jpg](..%2F..%2F..%2FOneDrive%2F%C1rea%20de%20Trabalho%2FDevops%20CA2PArt1.jpg)

2. **Add a new task to execute the server**.

First we open the build.grade package and then add the following task inside:

```bash
task runServer(type:JavaExec, dependsOn: classes){
    group = "DevOps"
    description = "Launches a chat server that listens on port 59001"

    classpath = sourceSets.main.runtimeClasspath

    mainClass = 'basic_demo.ChatServerApp'

    args '59001'
}
```
3. After adding the task, execute the server using the following command:
```bash
./gradlew runServer
```

4. Committing the Changes:
   After implementing the server task, commit the changes to your repository:
   ```bash
   git add .
   git commit -m "#3 Inclusion of the runServer task"
   git push
   ```
5. **Add simple unit test**.

First, we need to create a new file called ChatServerAppTest.java in the src/test/java/basic_demo folder. Then, we add the following code to the file:

```bash
@Test
    public void testAppHasAGreeting() {
        App classUnderTest = new App();
        assertNotNull("appshould have agreeting", classUnderTest.getGreeting());
    }
```

6. Running the Test:
   Execute the test using the Gradle command:
   ```bash
   ./gradlew test
   ```
7. Committing the Changes:
   After implementing the unit test, commit the changes to your repository:
   ```bash
   git add .
   git commit -m "closes #3 Add a simple unit test"
   git push
   ```
   
8. **Add a task to generate of type Copy**.

First we open the build.grade package and then add the following task inside:

```bash
task backup(type: Copy){
group = "DevOps"
description = "Backup the chat logs"

    from 'src'
    into 'backup'
}
```
9. Running the test:
    Execute the test using the Gradle command:
    ```bash
    ./gradlew backup
    ```
10. Committing the Changes:
    After implementing the backup task, commit the changes to your repository:
    ```bash
    git add .
    git commit -m "closes #4 Add a backup task"
    git push
    ```
    
11. **Add a task to generate a zip file**.

First we open the build.grade package and then add the following task inside:

```bash
task zip(type: Zip){
    group = "DevOps"
    description = "Zip the chat logs"

    from 'src'
    archiveFileName = 'backup.zip'
    destinationDir(file('build'))
}
```
12. Running the test:
    Execute the test using the Gradle command:
    ```bash
    ./gradlew zip
    ```
13. Committing the Changes:
    After implementing the zip task, commit the changes to your repository:
    ```bash
    git add .
    git commit -m "closes #5 Add a zip task"
    git push
    ```

## Finalizing the Assignment

1. **Tagging the Repository**:
   After completing all tasks, tag your repository to signify the completion of CA2 Part 1:
  ```bash
  git tag -a ca2-part1 -m "Completed CA2-Part1"
  git push origin ca2-part1
  ```

2. **Updating README.md**:
   Make sure the README.md reflects all the steps, commands, and code snippets used throughout the assignment, following the markdown syntax for clarity. Adding an issue(#6) to readMe.
    
## Conclusion

Participating in this segment of Class Assignment 2 (CA2) offered a valuable opportunity for practical engagement with Gradle, revealing its efficacy and adaptability as a tool for automating builds. Through the completion of the activities detailed in this report—such as running a server, implementing unit tests, creating backups, and organizing source files—an enriched understanding of Gradle's fundamentals and its practical utility in real-world contexts was attained.





