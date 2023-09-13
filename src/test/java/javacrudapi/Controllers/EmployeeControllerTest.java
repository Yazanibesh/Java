package javacrudapi.Controllers;

import javacrudapi.Controller.EmployeeController;
import javacrudapi.Exception.BadRequestException;
import javacrudapi.Model.Employee;
import javacrudapi.Services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeControllerTest {

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(1L, "John", "Doe", "john@example.com"));
        employeeList.add(new Employee(2L, "Jane", "Smith", "jane@example.com"));
        when(employeeService.getAllEmployees()).thenReturn(employeeList);

        List<Employee> result = employeeController.getAllEmployees();

        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Jane", result.get(1).getFirstName());
        assertEquals("Doe", result.get(0).getLastName());
        assertEquals("Smith", result.get(1).getLastName());
        assertEquals("john@example.com", result.get(0).getEmail());
        assertEquals("jane@example.com", result.get(1).getEmail());

    }

    @Test
    void testGetEmployeeById() {
        Long employeeId = 1L;
        Employee employee = new Employee(employeeId, "John", "Doe", "john@example.com");
        when(employeeService.getEmployeeById(employeeId)).thenReturn(employee);

        Employee result = employeeController.getEmployeeById(employeeId);

        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("john@example.com", result.getEmail());
    }

    @Test
    void testCreateEmployee() {
        Employee employee = new Employee(1L, "John", "Doe", "john@example.com");
        when(employeeService.createEmployee(employee)).thenReturn(true);

        ResponseEntity<String> response = employeeController.createEmployee(employee);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Employee added successfully", response.getBody());
    }

    @Test
    void testUpdateEmployee() {
        Long employeeId = 1L;
        Employee updatedEmployee = new Employee(employeeId, "Updated", "Name", "updated@example.com");
        when(employeeService.updateEmployee(employeeId, updatedEmployee)).thenReturn(true);

        ResponseEntity<String> response = employeeController.updateEmployee(employeeId, updatedEmployee);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Employee Updated successfully", response.getBody());
    }
    @Test
    void testDeleteEmployee() {
        Long employeeId = 1L;
        when(employeeService.deleteEmployee(employeeId)).thenReturn(true);

        ResponseEntity<String> response = employeeController.deleteEmployee(employeeId);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Employee Deleted successfully", response.getBody());
    }
}