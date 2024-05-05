# Technical Report for CA3 Part 1: Virtualization with Vagrant

## Introduction


In the dynamic realm of software engineering, establishing and maintaining consistent development environments is paramount. Virtualization presents a robust solution, enabling developers to simulate various operating systems on a single hardware platform, thereby enhancing control over a range of development scenarios. This segment of Assignment 3 (CA3) centers on harnessing virtualization capabilities utilizing VirtualBox and Vagrant, tailored specifically for students employing either conventional PCs or Apple devices with ARM64 architecture.

The principal aim of this task is to employ virtualization techniques to transfer and execute existing projects from preceding assignments within a virtualized Ubuntu environment. This configuration will replicate the development environment uniformly across diverse systems, ensuring that all features and dependencies are managed consistently and isolated from the host systems.

This document endeavors to outline the procedures involved in establishing the virtual machine (VM), configuring essential development tools, and executing two distinct projects: a Spring Boot tutorial and a Gradle demonstration. By providing comprehensive steps and elucidative explanations, this report serves as a thorough guide, enabling future students to replicate the setup and comprehend the pragmatic implications of virtualization in software development workflows.


### Creating the Virtual Machine

#### 1. **Download and Install VirtualBox or UTM**: Start by downloading VirtualBox from [Oracle's website](https://www.virtualbox.org/).

#### 2. **Setting Up a New VM**:

- Launch VirtualBox/UTM and select "New" to begin crafting a fresh virtual machine.

- Provide a name for your VM (e.g., "UbuntuDev") and opt for "Linux" as the operating system type and "Ubuntu (64-bit)" as the version.

- Assign memory (RAM): It's advisable to allocate a minimum of 2048 MB for satisfactory performance.

- Generate a virtual hard disk: Choose VDI (VirtualBox Disk Image) and allocate at least 10 GB of storage space. Opt for dynamic allocation for the storage on the physical hard disk.


#### 3. **Install Ubuntu on the Virtual Machine**:

- Fetch the Ubuntu Server ISO file from the [Ubuntu's official site](https://ubuntu.com/download/server).

- Attach the ISO file to your VM: In VirtualBox, pick your VM, access "Settings," navigate to "Storage," select "Empty" under Controller: IDE, then hit the disk icon beside "Optical Drive" and pick "Choose a disk file..." Find and pick your downloaded ISO file.

- Launch the VM and adhere to the prompts on the screen to initiate the Ubuntu installation process. While installing, opt for the standard server utilities and, if requested, install the OpenSSH server for remote access.

#### 4. **Virtualization and Networking Setup**:
4.1 **Create a Host-Only Network**:
- Open your VM application (e.g., VirtualBox).
- Navigate to the **Host Network Manager**.
- Click on **Create** to add a new host-only network.
- Name and configure this network within my VM's network settings.

4.2 **Setup Networking for the VM**:
- In your VM's settings, set **Network Adapter 2** to be a **Host-only Adapter**.
- Check the IP range for the host-only network (e.g., `192.168.56.1/24`).
- Assign an appropriate IP from this range to the adapter, such as `192.168.56.5`.

#### 5. Initial VM Setup
- Start your VM and log in.
- Update package repositories:
  ```bash
  sudo apt update
  ```
- Install necessary network tools:
  ```bash
  sudo apt install net-tools
  ```
- Configure the network interface by editing the Netplan configuration file:
  ```bash
  sudo nano /etc/netplan/01-netcfg.yaml
  ```

-This is how the `01-netcfg.yaml`should look like:

    network:
        version: 2
        renderer: networkd
        ethernets:
            enp0s3:
                dhcp4: yes
            enp0s8:
                addresses:
                    - 192.168.56.5

- Apply the changes with:
  ```bash
  sudo netplan apply
  ```

#### 6. Additional Utilities
- **SSH Setup**:
    - Install OpenSSH Server:
      ```bash
      sudo apt install openssh-server
      ```
    - Enable password authentication in the SSH configuration.
    - Restart SSH service:
      ```bash
      sudo systemctl restart ssh
      ```

- **FTP Setup**:
    - Install `vsftpd`:
      ```bash
      sudo apt install vsftpd
      ```
    - Enable write access in the FTP configuration.
    - Restart FTP service:
      ```bash
      sudo systemctl restart vsftpd
      ```

#### Software Installation

Once the Ubuntu server is up and running, proceed with installing the necessary software for your development environment:

7**Update Your System**:
- Open a terminal and run the following commands to update your system:
  ``` bash
  sudo apt update
  sudo apt upgrade
  ```

8**Install Essential Tools**:
- **Git**: To clone and manage your project repositories.
  ``` bash
  sudo apt install git
  ```
- **Java Development Kit (JDK)**: Essential for running Java applications.
  ``` bash
  sudo apt install openjdk-17-jdk openjdk-17-jre
  ```
    - The installed JDK version 17 was installed in order for the projects (namingly, CA2 Part2 built with java 17) to be able to run


- **Maven**: For building and managing Java-based projects.
  ``` bash
  sudo apt install maven
  ```
- **Gradle**: For building and automating Java projects.
  ```bash
  wget https://services.gradle.org/distributions/gradle-8.6-bin.zip
  sudo mkdir /opt/gradle
  sudo unzip -d /opt/gradle gradle-8.6-bin.zip
  echo "export GRADLE_HOME=/opt/gradle/gradle-8.6" >> ~/.bashrc
  echo "export PATH=$GRADLE_HOME/bin:$PATH
  source ~/.bashrc
  ```
    - The version 8.6 was chosen for compatibility purposes with Gradle-built projects

9**Verify Installations**:
- Ensure all installations are correct by checking the versions of the installed software:
  ``` bash
  git --version
  java -version
  mvn -version
  gradle -version
  ```

Following these steps will prepare your virtual machine with all the necessary tools and configurations to proceed with cloning your repository and running the projects. This setup ensures a standardized development environment that mimics a real-world server setup, providing a robust foundation for further development and learning.

#### Cloning the Repository

1. **Open a Terminal in Your VM**:
  - Access the terminal through your VM's interface. If you are using SSH to connect to the VM, ensure it's set up during the Ubuntu installation.

2. **Clone Your Repository**:
  - Navigate to a directory where you want to store your projects, such as `/home/username/projects`.
  - Use the git command to clone your repository. Replace `<repository-url>` with the URL of your GitHub repository:
    ``` bash
    git clone <repository-url>
    ```
  - Enter your directory containing the projects:
    ``` bash
    cd dirctory/holding/projects
    ```


#### Setting Up the Projects

1.  - Configure Maven Wrapper and Gradle Wrapper to give executing permissions:
      ```bash
       chmod +x mvnw
       chmod +x gradlew
       ```
2. **CA1**:
  - Navigate to the project directory:
    ``` bash
    cd directory/with/spring-boot-tutorial-basic
    ```
  - Build the project using Maven:
    ``` bash
    ./mvnw clean install
    ```
  - Run the project:
    ``` bash
    ./mvnw spring-boot:run
    ```
  - Check that the application is running correctly by accessing it from your host machine’s web browser using the VM’s IP address and the port configured in the project.

   ``` bash
    ip addr
   ```
  - put the IP and the port 8080 on the browser address.


3. **CA2 Part1**:
  - Navigate to the Gradle project directory:
    ``` bash
    cd directory/holding/gradle_basic_demo
    ```
  - Before building, ensure all Gradle dependencies are set up properly. Since some functionalities might not work due to the lack of a desktop environment, adjust the `build.gradle` if necessary.

  - Build the project using Gradle and run the server:
    ``` bash
    ./gradlew build
    ./gradlew runServer
    ```
  - Build the project in your computer terminal and run the client:
    ``` bash
    gradle runClient --args="192.168.56.5 59001"
    ```
  - The project should run smoothly



5. **CA2 Part2**:
  - Navigate to the basic folder:
   ``` bash
   cd Repositorio\devops-23-24-PSM-JPE-1231855\CA1
   ```
  - Run with gradle:
   ``` bash
   ./gradlew build
    ./gradlew bootRun
   ```
  - Check your VM's IP:
   ```bash
   ip addr
   ```
  - Write `localhost:8080` on your browser address. The application should run smoothly.

### Issues

- Create ReadMe CA3-Part1 #12

### Conclusion

After finishing CA3 Part 1, we'll have effectively established a virtual development space using VirtualBox or UTM and transferred two vital projects into this setup. This activity not only deepens our grasp of virtualization tech but also furnishes us with essential abilities in setting up and overseeing independent development spaces. These proficiencies prove invaluable in a professional context where development and testing setups frequently demand close replication of production environments.

#### Committing Changes to Your Repository

- **Regular Commits**: Make frequent commits to your repository with descriptive commit messages that clearly explain the changes or enhancements made.
  ```
  git add .
  git commit -m "created README.md file"
  git push origin main
  ```  

#### Tagging for Release

- **Tagging the Final Submission**: Once you complete your assignment, tag your repository to mark the version of the project that corresponds to your submission.
  ```
  git tag -a CA3-PART1 -m "Created CA3 Part 1"
  git push origin --tags
  ```


