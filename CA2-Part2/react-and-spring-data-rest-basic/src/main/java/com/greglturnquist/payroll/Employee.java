/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.greglturnquist.payroll;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * @author Greg Turnquist
 */
// tag::code[]
@Entity // <1>
public class Employee {

    private @Id
    @GeneratedValue Long id; // <2>
    private String firstName;
    private String lastName;
    private String description;
    private int JobYears;

    private String email;

    private Employee() {
    }

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

    @Override
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

    @Override
    public int hashCode() {

        return Objects.hash(id, firstName, lastName, description, JobYears, email);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getJobYears() {
        return JobYears;
    }

    public void setJobYears(int JobYears) {
        this.JobYears = JobYears;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(email == null || email.isEmpty() || !email.contains("@"))
            throw new IllegalArgumentException("Invalid email");
        this.email = email;
    }

    @Override
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
// end::code[]
