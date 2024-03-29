## README

## Technical Report For CA1: Assignment Documentation

# Introduction

I am pleased to present this technical report documenting the progress and outcomes of Class Assignment 1 (CA1). This
assignment primarily focused on implementing best practices in version control utilizing Git, along with an exploration
of an alternative version control system for comparative analysis.
Throughout the assignment, key objectives included:

1. Implementation of Version Control Practices: The adoption of Git as the primary version control system allowed for
   the efficient management of project files and codebase changes.

2. Comparative Analysis: In addition to Git, an alternative version control system was examined to evaluate its
   suitability and efficiency compared to Git.

The assignment's division into distinct phases facilitated a comprehensive understanding of version control principles
and practices, culminating in a well-rounded assessment of the advantages and limitations of Git and alternative
systems.

# Implementation Steps

## First step

To begin, you'll want to replicate this repository (https://github.com/spring-guides/tut-react-and-spring-data-rest onto
your personal computer. This repository will act as the foundation for your task. For this assignment, only the basic
module is necessary; you can safely remove any other modules.

Subsequently, the remaining steps of the assignment can be completed by launching a bash terminal and executing the
subsequent commands:

1. Navigate to the project directory (assuming the Tutorial React.js and Spring Data REST Application is already locally
   available):
   ```bash
   cd path/to/TutorialReactSpringDataREST
   ```
    - command cd changes the current directory

2. Copy the application into a new CA1 folder:
   ```bash
   cp -r . ../CA1
   cd ../CA1
   ```
3. Initialize the Git repository (if not already a Git repository):
   ```bash
   git init
   ```
    - adds a ".git" directory to the current directory (the added directory contains alal the information required for
      the repository work process)

4. Add all files to the staging area:
   ```bash
   git add .
   ```
    - before being ready to be commited and then pushed to the remote repository, changes must be added to a staging
      area, covered by this command. The "." notation indicates that ALL the unstaged files in the repository directory
      should be staged.

5. Commit the added files:
   ```bash
   git commit -m "Initial commit"
   ```
    - creates a new commit, containing the current contents of the index (the staged changes to the files in the
      repository) and the given log message (after "-m") describing the changes.

6. Push the commit to the remote repository:
   ```bash
   git remote add origin <repository-URL>
   git push -u origin master
   ```
    - the first step is only necessary if the local repository is not yet linked to the remote one, as it is its
      function.
    - The second step uploads local repository content to the remote repository location.

7. Add a tag to the commit:
   ```bash
   git tag -a v1.1.0 "v1.1.0
   git push origin v1.1.0
   ```
    - the first step creates a new tag, with the name "v1.0" and the message "First version of the application"
    - the second step uploads the tag to the remote repository

# Implementing Changes
## Adding the job years field (CA1 - Part 1)

The initial phase of this task occurs within the primary branch, aiming to introduce a supplementary attribute to the application, specifically, the duration of the user's tenure in their current role. The procedural steps to accomplish this are as follows:

1. Add the new field to the _Employee_ class:
   ```java
   private int jobYears;
   ```

2. Add the new field to the _Employee_ class constructor:
   ```java
    public Employee(String firstName, String lastName, String description, int JobYears, String email) {
        if (firstName == null || firstName.isEmpty())
            throw new IllegalArgumentException("Invalid first name");
        if (lastName == null || lastName.isEmpty())
            throw new IllegalArgumentException("Invalid last name");
        if (description == null || description.isEmpty())
            throw new IllegalArgumentException("Invalid description");
        if (JobYears < 0)
            throw new IllegalArgumentException("Invalid job years");       

        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.JobYears = JobYears;
    }
    ```
5. Add the new field to the _Employee_ class getters and setters:
   ```java
   public int getJobYears() {
       return jobYears;
   }

   public void setJobYears(int jobYears) {
       this.jobYears = jobYears;
   }
   ```
   
6. Add the new field to the _Employee_ class toString method:
   ```java
   return "Employee{" + "id=" + this.id + ", name='" + this.name + '\'' + ", role='" + this.role + '\'' + ", jobYears=" + this.jobYears + '}'; 
   ```

7. Add the new field to the equals method:
    ```java
    public boolean equals(Object o) {
		 if (this == o) return true;
		 if (o == null || getClass() != o.getClass()) return false;
		 Employee employee = (Employee) o;
		 return Objects.equals(id, employee.id) &&
			Objects.equals(firstName, employee.firstName) &&
			Objects.equals(lastName, employee.lastName) &&
			Objects.equals(description, employee.description) &&
			Objects.equals(jobYears, employee.jobYears)
	}
    ```
8. Add the new field to the _Employee_ class hashCode method:
    ```java
    public int hashCode() {
		 return Objects.hash(id, firstName, lastName, description, jobYears);
	}
    ```
9. Add the new field to the _Employee_ class toString method:
    ```java
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", description='" + description + '\'' +
                ", JobYears=" + JobYears +
   '}';
    }
    ```   

10. Add the new field to the render methods in the app.js Javascript file:
```javascript
   class EmployeeList extends React.Component{
	render() {
		const employees = this.props.employees.map(employee =>
			<Employee key={employee._links.self.href} employee={employee}/>
		);
		return (
			<table>
				<tbody>
					<tr>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Description</th>
						<th>Job Years</th>                       
					</tr>
					{employees}
				</tbody>
			</table>
		)
	}
}
````
```javascript
class Employee extends React.Component{
	render() {
		return (
			<tr>
				<td>{this.props.employee.firstName}</td>
				<td>{this.props.employee.lastName}</td>
				<td>{this.props.employee.description}</td>
				<td>{this.props.employee.jobYears}</td>				
			</tr>
		)
	}
}
````

11. Add the new field to the run method in the DatabaseLoader class (you can also add new entries):
```java
   	public void run(String... strings) throws Exception { // <4>
   this.repository.save(new Employee("Frodo", "Baggins", "ring bearer",4));
}
```
12. Create an EmployeeTest class and add unit tests for the new field, ensuring that all methods and validations are covered:
   ```java
   class EmployeeTest {
    @Test
    void testEmployeeConstructor() {
        Employee employee = new Employee("John", "Doe", "Developer", 5, "frodobeggins@hotmail.com");
        assertEquals("John", employee.getFirstName());
        assertEquals("Doe", employee.getLastName());
        assertEquals("Developer", employee.getDescription());
        assertEquals(5, employee.getJobYears());
    }

    @Test
    void testFirstNameIsNull() {
        Exception exc = assertThrows(IllegalArgumentException.class, () -> new Employee(null, "Doe", "Developer", 5, "frodobeggins@hotmail.com"));
        assertTrue(exc.getMessage().contains("Invalid first name"));
    }

    @Test
    void testFirstNameIsEmpty() {
        Exception exc = assertThrows(IllegalArgumentException.class, () -> new Employee("", "Doe", "Developer", 5, "frodobeggins@hotmail.com"));
        assertTrue(exc.getMessage().contains("Invalid first name"));
    }

    @Test
    void testLastNameIsNull() {
        Exception exc = assertThrows(IllegalArgumentException.class, () -> new Employee("John", null, "Developer", 5, "frodobeggins@hotmail.com"));
        assertTrue(exc.getMessage().contains("Invalid last name"));
    }

    @Test
    void testLastNameIsEmpty() {
        Exception exc = assertThrows(IllegalArgumentException.class, () -> new Employee("John", "", "Developer", 5, "frodobeggins@hotmail.com"));
        assertTrue(exc.getMessage().contains("Invalid last name"));
    }

    @Test
    void testDescriptionIsNull() {
        Exception exc = assertThrows(IllegalArgumentException.class, () -> new Employee("John", "Doe", null, 5, "frodobeggins@hotmail.com"));
        assertTrue(exc.getMessage().contains("Invalid description"));
    }

    @Test
    void testDescriptionIsEmpty() {
        Exception exc = assertThrows(IllegalArgumentException.class, () -> new Employee("John", "Doe", "", 5, "frodobeggins@hotmail.com"));
        assertTrue(exc.getMessage().contains("Invalid description"));
    }

    @Test
    void testJobYearsIsNegative() {
        Exception exc = assertThrows(IllegalArgumentException.class, () -> new Employee("John", "Doe", "Developer", -5, "frodobeggins@hotmail.com"));
        assertTrue(exc.getMessage().contains("Invalid job years"));
    }

    @Test
    void testEmailIsNull() {
        Exception exc = assertThrows(IllegalArgumentException.class, () -> new Employee("John", "Doe", "Developer", 5, null));
        assertTrue(exc.getMessage().contains("Invalid email"));
    }

    @Test
    void testEmailIsEmpty() {
        Exception exc = assertThrows(IllegalArgumentException.class, () -> new Employee("John", "Doe", "Developer", 5, ""));
        assertTrue(exc.getMessage().contains("Invalid email"));
    }

    @Test
    void testEquals() {
        Employee employee1 = new Employee("John", "Doe", "Developer", 5, "frodobeggins@hotmail.com");
        Employee employee2 = new Employee("John", "Doe", "Developer", 5, "frodobeggins@hotmail.com");
        assertTrue(employee1.equals(employee2));
    }

    @Test
    void testHashCode() {
        Employee employee1 = new Employee("John", "Doe", "Developer", 5, "frodobeggins@hotmail.com");
        Employee employee2 = new Employee("John", "Doe", "Developer", 5, "frodobeggins@hotmail.com");
        assertEquals(employee1.hashCode(), employee2.hashCode());
    }

    @Test
    void testGetId() {
        Employee employee = new Employee("John", "Doe", "Developer", 5, "frodobeggins@hotmail.com");
        employee.setId(1L);
        assertEquals(1L, employee.getId());
    }

    @Test
    void testSetId() {
        Employee employee = new Employee("John", "Doe", "Developer", 5, "frodobeggins@hotmail.com");
        employee.setId(1L);
        assertEquals(1L, employee.getId());
    }

    @Test
    void testSetFirstName() {
        Employee employee = new Employee("John", "Doe", "Developer", 5, "frodobeggins@hotmail.com");
        employee.setFirstName("Jane");
        assertEquals("Jane", employee.getFirstName());
    }

    @Test
    void testSetLastName() {
        Employee employee = new Employee("John", "Doe", "Developer", 5, "frodobeggins@hotmail.com");
        employee.setLastName("Smith");
        assertEquals("Smith", employee.getLastName());
    }

    @Test
    void testSetDescription() {
        Employee employee = new Employee("John", "Doe", "Developer", 5, "frodobeggins@hotmail.com");
        employee.setDescription("Tester");
        assertEquals("Tester", employee.getDescription());
    }

    @Test
    void testSetJobYears() {
        Employee employee = new Employee("John", "Doe", "Developer", 5, "frodobeggins@hotmail.com");
        employee.setJobYears(3);
        assertEquals(3, employee.getJobYears());
    }

    @Test
    void testGetEmail() {
        Employee employee = new Employee("John", "Doe", "Developer", 5, "john.doe@example.com");
        assertEquals("john.doe@example.com", employee.getEmail());
    }

    @Test
    void testSetEmail() {
        Employee employee = new Employee("John", "Doe", "Developer", 5, "john.doe@example.com");
        employee.setEmail("new.email@example.com");
        assertEquals("new.email@example.com", employee.getEmail());
    }

    @Test
    void testSetEmailWithoutAtSign() {
        Employee employee = new Employee("John", "Doe", "Developer", 5, "john.doe@example.com");
        assertThrows(IllegalArgumentException.class, () -> employee.setEmail("invalidemail"));
    }

    @Test
    void testToString() {
        Employee employee = new Employee("John", "Doe", "Developer", 5, "frodobeggins@hotmail.com");
        assertEquals("Employee{id=null, firstName='John', lastName='Doe', description='Developer', JobYears=5, email='frodobeggins@hotmail.com'}", employee.toString());
    }

}
   ```

13. Open a bash in the basic folder of the app and run the following command:
```bash
./mvnw spring-boot:run
```
14. Open a browser and navigate to [http://localhost:8080/employees](http://localhost:8080/employees) to see the changes.

![img.png](readme-images/img.png)

15. Commit the changes to the main branch:
```bash
git add .
git commit -m "Inclusao do Jobyears"
```
16. Push the changes to the remote repository:
```bash
git push
```
17. Add a tag to the commit:
```bash
git tag -a v1.2.0 "v1.2.0"
git push origin v1.2.0
```
18. Add a tag to mark the finish of this first part of the assignment:
```bash
git tag -a ca1-part1 "ca1-part1"
git push origin ca1-part1
```

# Implementing Changes
## Adding the email-field (CA1 - Part 2)

1. Create and switch to the `email-field` branch:
 ```bash
   git branch email-field
   git checkout email-field
   git push -u origin email-field
   ```
2. Add the new field to the _Employee_ class:
   ```java
   private String email;
   ```

3. Add the new field to the _Employee_ class constructor:
   ```java
    public Employee(String firstName, String lastName, String description, int JobYears, String email) {
        if (firstName == null || firstName.isEmpty())
            throw new IllegalArgumentException("Invalid first name");
        if (lastName == null || lastName.isEmpty())
            throw new IllegalArgumentException("Invalid last name");
        if (description == null || description.isEmpty())
            throw new IllegalArgumentException("Invalid description");
        if (JobYears < 0)
            throw new IllegalArgumentException("Invalid job years");
        if (email == null || email.isEmpty())
            throw new IllegalArgumentException("Invalid email");

        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.JobYears = JobYears;
        this.email = email;
    }
    ```
4. Add the new field to the _Employee_ class getters and setters:
   ```java
   public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(email == null || email.isEmpty() || !email.contains("@"))
            throw new IllegalArgumentException("Invalid email");
        this.email = email;
    }
   ```

5. Add the new field to the _Employee_ class toString method:
   ```java
   public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", description='" + description + '\'' +
                ", JobYears=" + JobYears +
                ", email='" + email + '\'' +
                '}';
    } 
   ```

6. Add the new field to the equals method:
    ```java
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) &&
                Objects.equals(firstName, employee.firstName) &&
                Objects.equals(lastName, employee.lastName) &&
                Objects.equals(description, employee.description) &&
                Objects.equals(JobYears, employee.JobYears) &&
                Objects.equals(email, employee.email);
    }
    ```
7. Add the new field to the _Employee_ class hashCode method:
    ```java
    public int hashCode() {

        return Objects.hash(id, firstName, lastName, description, JobYears, email);
    }
    ```
8. Add the new field to the _Employee_ class toString method:
    ```java
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", description='" + description + '\'' +
                ", JobYears=" + JobYears +
                ", email='" + email + '\'' +
                '}';
    }
    }
    ```   

9. Add the new field to the render methods in the app.js Javascript file:
```javascript
   class EmployeeList extends React.Component{
	render() {
		const employees = this.props.employees.map(employee =>
			<Employee key={employee._links.self.href} employee={employee}/>
		);
		return (
			<table>
				<tbody>
					<tr>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Description</th>
						<th>Job Years</th>
                                                <th>email</th>                       
					</tr>
					{employees}
				</tbody>
			</table>
		)
	}
}
````
```javascript
class Employee extends React.Component{
	render() {
		return (
			<tr>
				<td>{this.props.employee.firstName}</td>
				<td>{this.props.employee.lastName}</td>
				<td>{this.props.employee.description}</td>
				<td>{this.props.employee.jobYears}</td>
                                <td>{this.props.employee.email}</td>
			</tr>
		)
	}
}
````

10. Add the new field to the run method in the DatabaseLoader class (you can also add new entries):
```java
   	public void run(String... strings) throws Exception { // <4>
		this.repository.save(new Employee("Frodo", "Baggins", "ring bearer",4,"frodobaggins@hotmail.com"));
	}
}
````
11. Create an EmployeeTest class and add unit tests for the new field, ensuring that all methods and validations are covered:
   ```java
   class EmployeeTest {
    @Test
    void testEmployeeConstructor() {
        Employee employee = new Employee("John", "Doe", "Developer", 5, "frodobeggins@hotmail.com");
        assertEquals("John", employee.getFirstName());
        assertEquals("Doe", employee.getLastName());
        assertEquals("Developer", employee.getDescription());
        assertEquals(5, employee.getJobYears());
    }

    @Test
    void testFirstNameIsNull() {
        Exception exc = assertThrows(IllegalArgumentException.class, () -> new Employee(null, "Doe", "Developer", 5, "frodobeggins@hotmail.com"));
        assertTrue(exc.getMessage().contains("Invalid first name"));
    }

    @Test
    void testFirstNameIsEmpty() {
        Exception exc = assertThrows(IllegalArgumentException.class, () -> new Employee("", "Doe", "Developer", 5, "frodobeggins@hotmail.com"));
        assertTrue(exc.getMessage().contains("Invalid first name"));
    }

    @Test
    void testLastNameIsNull() {
        Exception exc = assertThrows(IllegalArgumentException.class, () -> new Employee("John", null, "Developer", 5, "frodobeggins@hotmail.com"));
        assertTrue(exc.getMessage().contains("Invalid last name"));
    }

    @Test
    void testLastNameIsEmpty() {
        Exception exc = assertThrows(IllegalArgumentException.class, () -> new Employee("John", "", "Developer", 5, "frodobeggins@hotmail.com"));
        assertTrue(exc.getMessage().contains("Invalid last name"));
    }

    @Test
    void testDescriptionIsNull() {
        Exception exc = assertThrows(IllegalArgumentException.class, () -> new Employee("John", "Doe", null, 5, "frodobeggins@hotmail.com"));
        assertTrue(exc.getMessage().contains("Invalid description"));
    }

    @Test
    void testDescriptionIsEmpty() {
        Exception exc = assertThrows(IllegalArgumentException.class, () -> new Employee("John", "Doe", "", 5, "frodobeggins@hotmail.com"));
        assertTrue(exc.getMessage().contains("Invalid description"));
    }

    @Test
    void testJobYearsIsNegative() {
        Exception exc = assertThrows(IllegalArgumentException.class, () -> new Employee("John", "Doe", "Developer", -5, "frodobeggins@hotmail.com"));
        assertTrue(exc.getMessage().contains("Invalid job years"));
    }

    @Test
    void testEmailIsNull() {
        Exception exc = assertThrows(IllegalArgumentException.class, () -> new Employee("John", "Doe", "Developer", 5, null));
        assertTrue(exc.getMessage().contains("Invalid email"));
    }

    @Test
    void testEmailIsEmpty() {
        Exception exc = assertThrows(IllegalArgumentException.class, () -> new Employee("John", "Doe", "Developer", 5, ""));
        assertTrue(exc.getMessage().contains("Invalid email"));
    }

    @Test
    void testEquals() {
        Employee employee1 = new Employee("John", "Doe", "Developer", 5, "frodobeggins@hotmail.com");
        Employee employee2 = new Employee("John", "Doe", "Developer", 5, "frodobeggins@hotmail.com");
        assertTrue(employee1.equals(employee2));
    }

    @Test
    void testHashCode() {
        Employee employee1 = new Employee("John", "Doe", "Developer", 5, "frodobeggins@hotmail.com");
        Employee employee2 = new Employee("John", "Doe", "Developer", 5, "frodobeggins@hotmail.com");
        assertEquals(employee1.hashCode(), employee2.hashCode());
    }

    @Test
    void testGetId() {
        Employee employee = new Employee("John", "Doe", "Developer", 5, "frodobeggins@hotmail.com");
        employee.setId(1L);
        assertEquals(1L, employee.getId());
    }

    @Test
    void testSetId() {
        Employee employee = new Employee("John", "Doe", "Developer", 5, "frodobeggins@hotmail.com");
        employee.setId(1L);
        assertEquals(1L, employee.getId());
    }

    @Test
    void testSetFirstName() {
        Employee employee = new Employee("John", "Doe", "Developer", 5, "frodobeggins@hotmail.com");
        employee.setFirstName("Jane");
        assertEquals("Jane", employee.getFirstName());
    }

    @Test
    void testSetLastName() {
        Employee employee = new Employee("John", "Doe", "Developer", 5, "frodobeggins@hotmail.com");
        employee.setLastName("Smith");
        assertEquals("Smith", employee.getLastName());
    }

    @Test
    void testSetDescription() {
        Employee employee = new Employee("John", "Doe", "Developer", 5, "frodobeggins@hotmail.com");
        employee.setDescription("Tester");
        assertEquals("Tester", employee.getDescription());
    }

    @Test
    void testSetJobYears() {
        Employee employee = new Employee("John", "Doe", "Developer", 5, "frodobeggins@hotmail.com");
        employee.setJobYears(3);
        assertEquals(3, employee.getJobYears());
    }

    @Test
    void testGetEmail() {
        Employee employee = new Employee("John", "Doe", "Developer", 5, "john.doe@example.com");
        assertEquals("john.doe@example.com", employee.getEmail());
    }

    @Test
    void testSetEmail() {
        Employee employee = new Employee("John", "Doe", "Developer", 5, "john.doe@example.com");
        employee.setEmail("new.email@example.com");
        assertEquals("new.email@example.com", employee.getEmail());
    }

    @Test
    void testSetEmailWithoutAtSign() {
        Employee employee = new Employee("John", "Doe", "Developer", 5, "john.doe@example.com");
        assertThrows(IllegalArgumentException.class, () -> employee.setEmail("invalidemail"));
    }

    @Test
    void testToString() {
        Employee employee = new Employee("John", "Doe", "Developer", 5, "frodobeggins@hotmail.com");
        assertEquals("Employee{id=null, firstName='John', lastName='Doe', description='Developer', JobYears=5, email='frodobeggins@hotmail.com'}", employee.toString());
    }

}
   ```
13. Open a bash in the basic folder of the app and run the following command:
```bash
./mvnw spring-boot:run
```
14.Open a browser and navigate to [http://localhost:8080/employees](http://localhost:8080/employees) to see the changes.)

![img_1.png](readme-images/img_1.png)

15. Commit the changes to the email-field branch:
```bash
git add .
git commit -m "Insercao email"
git push --set-upstream origin fix-invalid-email
```
17. Switch to the main branch:
```bash
git checkout main
```
18. Merge the email-field branch into the main branch:
```bash
git merge --no-ff email-field
```
19. Push the changes to the remote repository:
```bash
git push origin main
```
20. Add a new tag to the commit:
```bash
git tag -a v1.3.0 "v1.3.0"
git push origin v1.3.
```
### Issues
During the development of the assignment, if any problems arose, is it possible to use the GitHub issues feature. Issues can be opened in the Github repository website.
1. The issue can be assigned to someone, labeled, and commented on. It can also be closed when the problem is solved by associating it with a commit:
```java
git commit -m "Fixes #1"
```

## Analysis of an Alternative Version Control System

### Mercurial (Hg) as an Option

Mercurial (Hg) stands as an alternative version control system, differing from Git in several key aspects. Hg boasts a distributed model similar to Git, yet with its own set of unique features and workflow nuances.

### Contrasting Mercurial with Git
1. Distributed Nature: Like Git, Mercurial embraces a distributed architecture, empowering developers with local repositories. However, Hg employs a subtly different approach to managing branches and commits.

2. Workflow Flexibility: Mercurial offers a diverse range of workflow options, catering to different team dynamics and project requirements. While Git provides flexibility through its branching model, Hg's approach may appeal to teams seeking simplicity and clarity in their version control workflows.

3. Performance and Scalability: Mercurial and Git exhibit comparable performance characteristics, with differences often emerging in edge cases or specific usage scenarios. Both systems excel in handling small to medium-sized repositories but may encounter challenges with exceptionally large or complex projects.

4. User Interface: Mercurial boasts an intuitive and user-friendly interface, making it accessible to users of varying technical backgrounds. This focus on usability may appeal to teams prioritizing ease of adoption and streamlined collaboration processes.

### Executing Assignment Goals with Mercurial
Initial Setup Procedures
1. Repository Creation: Begin by establishing a new Mercurial repository on a shared server accessible to all project contributors.

```bash
hg init /path/to/repository
```

2. Repository Cloning: Developers clone the central repository to their local machines to commence work on the project.

```bash
hg clone http://hg.example.com/repository
```

Incorporating the Tutorial Application

3. Repository Integration: Navigate to the project directory and include the project files within the repository.
```bash
cd myproject
hg add *
```

4. Initial Commit: Commit the newly added files to the repository.

```bash
hg commit -m "Initial import of the Tutorial React.js and Spring Data REST Application"
```
Version Tagging in Mercurial

5. Creating an Initial Tag: In Mercurial, tags are established through the use of the "tag" command, designating specific revisions for future reference.

```bash
hg tag -r <revision> v1.1.0
```
Implementing New Features and Addressing Bugs

6. Feature Branch Creation: To isolate development efforts for a new feature, developers create a named branch within the repository.

```bash
hg branch new-feature
```

7. Integration of Feature Branch: Upon feature completion, the changes from the feature branch are merged back into the main branch.

```bash
hg merge new-feature
hg commit -m "Merged new-feature branch into main branch"
```
### Implementing Mercurial as an Alternative Approach
The outlined steps elucidate how Mercurial can be effectively employed to achieve analogous version control objectives to Git within the context of this assignment. While differing in some respects, Mercurial's robust features and intuitive interface make it a viable alternative, particularly for teams seeking a straightforward yet powerful version control solution.

##### Useful commands for Mercurial

Here is a list of commands that may be useful when using Mercurial:
- hg clone: This command is used to create a copy of an existing repository.
- hg pull: This command is used to pull changes from a remote repository to the local repository.
- hg update: This command is used to update the working directory to a specific revision.
- hg branch: This command is used to create a new branch in the repository.
- hg merge: This command is used to merge two branches in the repository.
- hg tag: This command is used to create a tag for a specific revision in the repository.
- hg log: This command is used to view the history of the repository.
- hg status: This command is used to view the status of the working directory.
- hg diff: This command is used to view the changes made to the working directory.
- hg revert: This command is used to revert changes made to the working directory.
- hg rollback: This command is used to undo the last commit in the repository.
- hg help: This command is used to view the help documentation for Mercurial.
- hg version: This command is used to view the version of Mercurial installed on the machine.
- hg outgoing: This command is used to view the changes that are ready to be pushed to a remote repository.
- hg incoming: This command is used to view the changes that are available to be pulled from a remote repository.
