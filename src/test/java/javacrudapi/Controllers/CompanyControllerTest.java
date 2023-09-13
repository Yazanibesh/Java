package javacrudapi.Controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import javacrudapi.Controller.CompanyController;
import javacrudapi.Exception.BadRequestException;
import javacrudapi.Model.Company;
import javacrudapi.Model.Employee;
import javacrudapi.Services.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompanyControllerTest {

    @InjectMocks
    private CompanyController companyController;

    @Mock
    private CompanyService companyService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllCompany() {
        List<Company> companyList = new ArrayList<>();
        companyList.add(new Company(1L, "Company1", "Address1"));
        companyList.add(new Company(2L, "Company2", "Address2"));
        when(companyService.getAllCompany()).thenReturn(companyList);

        List<Company> result = companyController.getAllCompany();

        assertEquals(2, result.size());
        assertEquals("Company1", result.get(0).getName());
        assertEquals("Company2", result.get(1).getName());
    }

    @Test
    void testGetCompanyById() {
        Long companyId = 1L;
        Company company = new Company(companyId, "John", "john@example.com");
        when(companyService.getCompanyById(companyId)).thenReturn(company);

        Company result = companyController.getCompanyById(companyId);
        System.out.println("Result: " + result);

        assertEquals("John", result.getName());
        assertEquals("john@example.com", result.getAddress());
    }

    @Test
    void testCreateCompany() {
        Company company = new Company(1L, "Company1", "Address1");
        when(companyService.createCompany(company)).thenReturn(true);

        ResponseEntity<String> response = companyController.createCompany(company);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Company added successfully", response.getBody());
    }

    @Test
    void testUpdateCompany() {
        Long companyId = 1L;
        Company updatedCompany = new Company(companyId, "UpdatedCompany", "UpdatedAddress");
        when(companyService.updateCompany(companyId, updatedCompany)).thenReturn(true);

        ResponseEntity<String> response = companyController.updateCompany(companyId, updatedCompany);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Company updated successfully", response.getBody());
    }

    @Test
    void testDeleteCompany() {
        Long companyId = 1L;
        when(companyService.deleteCompany(companyId)).thenReturn(true);

        ResponseEntity<String> response = companyController.deleteCompany(companyId);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Company Deleted successfully", response.getBody());
    }

}