# CA3 Part 2 - Vagrant Environment Setup for Spring Boot Application


## Introduction

This document provides the steps to setup a Vagrant environment for a Spring Boot application. The Vagrant environment will be setup with the following:
The purpose of this part of the assignment is to use Vagrant to automatically generate two virtual machines,
one for the webServer and the other for the database.

## Steps

### 1. Initial Solution

The initial Vagrant configuration is based on the repository at https://bitbucket.org/pssmatos/vagrant-multi-spring-tut-demo/.
This configuration is a starting point that provides the necessary setup for creating and provisioning the two VMs.

### 2. Vagrantfile Study

The `Vagrantfile` from the initial solution is studied to understand how it creates and provisions the two VMs:
- `web`: This VM is configured to run Tomcat and the Spring Boot "basic" application.
- `db`: This VM is for executing the H2 server database.

For students with MacBooks that have M1/M2 chips, special considerations are noted in the `readme.md` to ensure compatibility.

### 3. Vagrant Environment Setup

3.1 Update the Vagrantfile to be compatible with the current project:

```Vagrantfile
    Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/bionic64"

  # This provision is common for both VMs
  config.vm.provision "shell", inline: <<-SHELL
    sudo apt-get update -y
    sudo apt-get install -y iputils-ping avahi-daemon libnss-mdns unzip \
        openjdk-17-jdk-headless
    # ifconfig
  SHELL

  #============
  # Configurations specific to the database VM
  config.vm.define "db" do |db|
    db.vm.box = "ubuntu/bionic64"
    db.vm.hostname = "db"
    db.vm.network "private_network", ip: "192.168.56.11"

    # We want to access H2 console from the host using port 8082
    # We want to connet to the H2 server using port 9092
    db.vm.network "forwarded_port", guest: 8082, host: 8082
    db.vm.network "forwarded_port", guest: 9092, host: 9092

    # We need to download H2
    db.vm.provision "shell", inline: <<-SHELL
      wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar
    SHELL

    # The following provision shell will run ALWAYS so that we can execute the H2 server process
    # This could be done in a different way, for instance, setiing H2 as as service, like in the following link:
    # How to setup java as a service in ubuntu: http://www.jcgonzalez.com/ubuntu-16-java-service-wrapper-example
    #
    # To connect to H2 use: jdbc:h2:tcp://192.168.33.11:9092/./jpadb
    db.vm.provision "shell", :run => 'always', inline: <<-SHELL
      java -cp ./h2*.jar org.h2.tools.Server -web -webAllowOthers -tcp -tcpAllowOthers -ifNotExists > ~/out.txt &
    SHELL
  end

  #============
  # Configurations specific to the webserver VM
  config.vm.define "web" do |web|
    web.vm.box = "ubuntu/bionic64"
    web.vm.hostname = "web"
    web.vm.network "private_network", ip: "192.168.56.10"

    # We set more ram memmory for this VM
    web.vm.provider "virtualbox" do |v|
      v.memory = 1024
    end

    # We want to access tomcat from the host using port 8080
    web.vm.network "forwarded_port", guest: 8080, host: 8080

        web.vm.provision "shell", inline: <<-SHELL, privileged: false
          # sudo apt-get install git -y
          # sudo apt-get install nodejs -y
          # sudo apt-get install npm -y
          # sudo ln -s /usr/bin/nodejs /usr/bin/node
          # sudo apt install -y tomcat9 tomcat9-admin
          # If you want to access Tomcat admin web page do the following:
          # Edit /etc/tomcat9/tomcat-users.xml
          # uncomment tomcat-users and add manager-gui to tomcat user

          # Change the following command to clone your own repository!
          git clone https://github.com/Pedrotheofilo/devops-23-24-PSM-JPE-1231855
          cd devops-23-24-PSM-JPE-1231855/CA2-Part2/react-and-spring-data-rest-basic
          chmod u+x gradlew
          ./gradlew clean build
          ./gradlew bootRun
          # To deploy the war file to tomcat9 do the following command:
          # sudo cp ./build/libs/react-and-spring-data-rest-basic-0.0.1-SNAPSHOT.war /var/lib/tomcat9/webapps
        SHELL
      end
    end
```
3.2 Run the command `vagrant up` to create the virtual machines and run the application.
    * After the command is executed, the virtual machines are created via VirtualBox.
     
3.3 Access the web application from the browser in the host machine.
    * Open the web browser in http://192.168.56.10:8080/basic-0.0.1-SNAPSHOT/.

![image devops.png](..%2F..%2F..%2F..%2FOneDrive%2F%C1rea%20de%20Trabalho%2Fimage%20devops.png)
      
3.4 Access the H2 console from the browser in the host machine.
    * Open the web browser in localhost:8082.
    * Use the following connection settings:
        * Driver Class: org.h2.Driver
        * JDBC URL: jdbc:h2:tcp://192.168.56.11:9092/./jpadb
        * User Name: sa
    * Check if the table Employee is created.

![H2 - devops.jpg](..%2F..%2F..%2F..%2FOneDrive%2F%C1rea%20de%20Trabalho%2FH2%20-%20devops.jpg)

### Note: Commands of Vagrant

**`vagrant init`**:
    - Initializes a new Vagrant environment by creating a `Vagrantfile` in the current directory.
    - Usage: `vagrant init`

**`vagrant up`**:
    - Starts and provisions the VMs as defined in the `Vagrantfile`.
    - Usage: `vagrant up`

**`vagrant halt`**:
    - Stops the running VMs.
    - Usage: `vagrant halt`

**`vagrant suspend`**:
    - Suspends the running VMs, saving their state to be resumed later.
    - Usage: `vagrant suspend`

**`vagrant resume`**:
    - Resumes the VMs from a suspended state.
    - Usage: `vagrant resume`

**`vagrant reload`**:
    - Restarts the VMs and re-provisions them, applying any changes in the `Vagrantfile`.
    - Usage: `vagrant reload`

**`vagrant provision`**:
    - Runs the provisioners on the VMs without restarting them.
    - Usage: `vagrant provision`

**`vagrant ssh`**:
    - Logs into a running VM via SSH.
    - Usage: `vagrant ssh [vm-name]`

**`vagrant status`**:
    - Displays the status of the VMs defined in the `Vagrantfile`.
    - Usage: `vagrant status`

**`vagrant destroy`**:
    - Stops and removes the VMs defined in the `Vagrantfile`.
    - Usage: `vagrant destroy [vm-name]`

**`vagrant box`**:
    - Manages Vagrant box resources (list, add, remove, etc.).
    - Usage: `vagrant box <command>`

**`vagrant global-status`**:
    - Lists all active Vagrant environments across different directories.
    - Usage: `vagrant global-status`

### 4. Tag the Repository

Tag the repository with the name `CA3Part2`
```bash
git tag ca3-part2
```

Push the tag to the repository
```bash
git push origin ca3-part2
```
## Different Hypervisor Option: VirtualBox

### 1. Virtualization Characteristics Contrast:

1.1 UTM:

UTM primarily targets Mac systems with ARM architecture, including the M1 and M2 chips.
It utilizes Apple's Hypervisor framework for virtualization, ensuring seamless integration with macOS.
UTM provides an intuitive GUI for VM management and supports a broad spectrum of operating systems.
It facilitates easy setup of networking, storage, and hardware emulation.
Performance is tailored for macOS and harmonizes well with ARM-based platforms.

VirtualBox:

VirtualBox, an open-source hypervisor developed by Oracle, is freely available and widely adopted across multiple platforms such as Windows, Linux, and Intel-based Macs.
It boasts a rich array of features for VM creation and management, including functionalities like snapshots, cloning, and guest additions for enhanced host system integration.
Extensive support for diverse network configurations and USB device pass-through is provided.
Both graphical user interface and command-line tools are available for automation and integration with other software.
Modern CPU hardware virtualization features are leveraged to optimize guest performance.
While its primary focus is on x86 and AMD64/Intel64 architectures, efforts are ongoing to enhance support for ARM-based hosts like Apple's M1 and M2 chips.

### 2. Vagrant Integration Overview:

2.1 UTM:
UTM lacks native Vagrant support, necessitating additional steps or plugins for seamless integration.
Certain aspects may require manual configuration that Vagrant typically automates on other hypervisors.
Given UTM's focus on macOS with ARM architecture, Vagrant support might be limited due to a smaller user base and less community-driven development in this context.

VirtualBox:

VirtualBox enjoys full support from Vagrant out of the box, making it one of the most commonly utilized providers for Vagrant environments.
Vagrant can directly manage VirtualBox to execute commands for VM creation, configuration, provisioning, and deletion.
Integration is seamless, enabling scripting of the entire VM setup, provisioning, and teardown process.
It ensures a consistent workflow across different operating systems, facilitating collaboration on development environments for teams.
A plethora of pre-built Vagrant boxes for VirtualBox are available from the community, enabling rapid deployment of VMs with specific configurations.

### 3. Vagrant File Configuration:

3.1 Vagrantfile for VirtualBox Hypervisor:

Vagrant.configure("2") do |config|
config.vm.box = "ubuntu/focal64"
config.ssh.insert_key = false

# This provision is common for both VMs
config.vm.provision "shell", inline: <<-SHELL
sudo apt-get update -y
sudo apt-get install -y iputils-ping avahi-daemon libnss-mdns unzip \
openjdk-17-jdk-headless
# ifconfig
SHELL

#============
# Configurations specific to the database VM
config.vm.define "db" do |db|
db.vm.box = "ubuntu/focal64"
db.vm.hostname = "db"
db.vm.network "private_network", ip: "192.168.56.11"

    # Accessing H2 console from the host using port 8082
    # Connecting to the H2 server using port 9092
    db.vm.network "forwarded_port", guest: 8082, host: 8082
    db.vm.network "forwarded_port", guest: 9092, host: 9092

    # Downloading H2
    db.vm.provision "shell", inline: <<-SHELL
      wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar
    SHELL

    # Running H2 server process
    db.vm.provision "shell", :run => 'always', inline: <<-SHELL
      java -cp ./h2*.jar org.h2.tools.Server -web -webAllowOthers -tcp -tcpAllowOthers -ifNotExists > ~/out.txt &
    SHELL
end

#============
# Configurations specific to the webserver VM
config.vm.define "web" do |web|
web.vm.box = "ubuntu/focal64"
web.vm.hostname = "web"
web.vm.network "private_network", ip: "192.168.56.10"

    # Allocating more memory for this VM
    web.vm.provider "virtualbox" do |v|
      v.memory = 1024
    end

    # Accessing Tomcat from the host using port 8080
    web.vm.network "forwarded_port", guest: 8080, host: 8080

    web.vm.provision "shell", inline: <<-SHELL, privileged: false
      sudo apt install -y tomcat9 tomcat9-admin

      # Cloning repository (modify as needed)
      git clone https://github.com/Pedrotheofilo/devops-23-24-PSM-JPE-1231855
      cd devops-23-24-PSM-JPE-1231855/CA2-Part2/react-and-spring-data-rest-basic
      chmod u+x gradlew
      ./gradlew clean build
      nohup ./gradlew bootRun > /home/vagrant/spring-boot-app.log 2>&1 &
      # To deploy the war file to Tomcat9
      sudo cp ./build/libs/react-and-spring-data-rest-basic-0.0.1-SNAPSHOT.war /var/lib/tomcat9/webapps
    SHELL
end 
end

## 5. Conclusion:
In conclusion, this assignment underscored the utilization of Vagrant for crafting a virtual ecosystem tailored to execute a Spring Boot application tutorial. Through meticulous adherence to provided guidelines encompassing setup, configuration, and rigorous testing, the assignment adeptly showcased mastery in virtualization methodologies employing Vagrant.





  