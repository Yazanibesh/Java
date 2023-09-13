package javacrudapi.Controller;

import javacrudapi.Exception.BadRequestException;
import javacrudapi.Model.Employee;
import javacrudapi.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAllEmployees() {
        logger.info("Received a GET all Employees request");
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        logger.info("Received a GET Employee By Id request");
        return employeeService.getEmployeeById(id);
    }

    @PostMapping
    public ResponseEntity<String> createEmployee(@RequestBody Employee employee) {
        logger.info("Received a employee Create request");
        boolean result = employeeService.createEmployee(employee);
        if (result != false) {
            logger.info("Employee created request successfully completed");
            return ResponseEntity.status(HttpStatus.CREATED).body("Employee added successfully");
        } else {
            logger.error("Employee created request Failed");
            throw new BadRequestException("Failed to add employee");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        logger.info("Received a employee Update request");
        boolean result = employeeService.updateEmployee(id, employee);
        if (result != false) {
            logger.info("Employee updated request successfully completed");
            return ResponseEntity.status(HttpStatus.CREATED).body("Employee Updated successfully");
        } else {
            logger.error("Employee updated request Failed");
            throw new BadRequestException("Failed to update employee");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        logger.info("Received a employee Delete request");
        boolean result = employeeService.deleteEmployee(id);
        if (result != false) {
            logger.info("Employee Deleted request successfully completed");
            return ResponseEntity.status(HttpStatus.CREATED).body("Employee Deleted successfully");
        } else {
            logger.error("Employee Delete request Failed");
            throw new BadRequestException("Failed to delete employee");
        }
    }

}