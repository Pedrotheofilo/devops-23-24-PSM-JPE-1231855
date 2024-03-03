package com.greglturnquist.payroll;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {
    @Test
    void testEmployeeConstructor() {
        Employee employee = new Employee("John", "Doe", "Developer", 5);
        assertEquals("John", employee.getFirstName());
        assertEquals("Doe", employee.getLastName());
        assertEquals("Developer", employee.getDescription());
        assertEquals(5, employee.getJobYears());
    }

    @Test
    void testFirstNameIsNull() {
        Exception exc = assertThrows(IllegalArgumentException.class, () -> new Employee(null, "Doe", "Developer", 5));
        assertTrue(exc.getMessage().contains("Invalid first name"));
    }

    @Test
    void testFirstNameIsEmpty() {
        Exception exc = assertThrows(IllegalArgumentException.class, () -> new Employee("", "Doe", "Developer", 5));
        assertTrue(exc.getMessage().contains("Invalid first name"));
    }

    @Test
    void testLastNameIsNull() {
        Exception exc = assertThrows(IllegalArgumentException.class, () -> new Employee("John", null, "Developer", 5));
        assertTrue(exc.getMessage().contains("Invalid last name"));
    }

    @Test
    void testLastNameIsEmpty() {
        Exception exc = assertThrows(IllegalArgumentException.class, () -> new Employee("John", "", "Developer", 5));
        assertTrue(exc.getMessage().contains("Invalid last name"));
    }

    @Test
    void testDescriptionIsNull() {
        Exception exc = assertThrows(IllegalArgumentException.class, () -> new Employee("John", "Doe", null, 5));
        assertTrue(exc.getMessage().contains("Invalid description"));
    }

    @Test
    void testDescriptionIsEmpty() {
        Exception exc = assertThrows(IllegalArgumentException.class, () -> new Employee("John", "Doe", "", 5));
        assertTrue(exc.getMessage().contains("Invalid description"));
    }

    @Test
    void testJobYearsIsNegative() {
        Exception exc = assertThrows(IllegalArgumentException.class, () -> new Employee("John", "Doe", "Developer", -5));
        assertTrue(exc.getMessage().contains("Invalid job years"));
    }

    @Test
    void testEquals() {
        Employee employee1 = new Employee("John", "Doe", "Developer", 5);
        Employee employee2 = new Employee("John", "Doe", "Developer", 5);
        assertTrue(employee1.equals(employee2));
    }

    @Test
    void testHashCode() {
        Employee employee1 = new Employee("John", "Doe", "Developer", 5);
        Employee employee2 = new Employee("John", "Doe", "Developer", 5);
        assertEquals(employee1.hashCode(), employee2.hashCode());
    }

    @Test
    void testGetId() {
        Employee employee = new Employee("John", "Doe", "Developer", 5);
        employee.setId(1L);
        assertEquals(1L, employee.getId());
    }

    @Test
    void testSetId() {
        Employee employee = new Employee("John", "Doe", "Developer", 5);
        employee.setId(1L);
        assertEquals(1L, employee.getId());
    }

    @Test
    void testSetFirstName() {
        Employee employee = new Employee("John", "Doe", "Developer", 5);
        employee.setFirstName("Jane");
        assertEquals("Jane", employee.getFirstName());
    }

    @Test
    void testSetLastName() {
        Employee employee = new Employee("John", "Doe", "Developer", 5);
        employee.setLastName("Smith");
        assertEquals("Smith", employee.getLastName());
    }

    @Test
    void testSetDescription() {
        Employee employee = new Employee("John", "Doe", "Developer", 5);
        employee.setDescription("Tester");
        assertEquals("Tester", employee.getDescription());
    }

    @Test
    void testSetJobYears() {
        Employee employee = new Employee("John", "Doe", "Developer", 5);
        employee.setJobYears(3);
        assertEquals(3, employee.getJobYears());
    }

    @Test
    void testToString() {
        Employee employee = new Employee("John", "Doe", "Developer", 5);
        assertEquals("Employee{id=null, firstName='John', lastName='Doe', description='Developer', JobYears=5}", employee.toString());
    }

}