package com.greglturnquist.payroll;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    void testToString() {
        Employee employee = new Employee("John", "Doe", "Developer", 5, "frodobeggins@hotmail.com");
        assertEquals("Employee{id=null, firstName='John', lastName='Doe', description='Developer', JobYears=5, email='frodobeggins@hotmail.com'}", employee.toString());
    }

}