package javacrudapi.Controller;

import javacrudapi.Exception.BadRequestException;
import javacrudapi.Model.Company;
import javacrudapi.Services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {
    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public List<Company> getAllCompany() {
        logger.info("Received a GET all companies request");
        return companyService.getAllCompany();
    }

    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable Long id) {
        logger.info("Received a GET company By Id request");
        return companyService.getCompanyById(id);
    }

    @PostMapping
    public ResponseEntity<String> createCompany(@RequestBody Company company) {
        logger.info("Received a company Create request");
        boolean result =  companyService.createCompany(company);
        if (result != false) {
            logger.info("Company created request successfully completed");
            return ResponseEntity.status(HttpStatus.CREATED).body("Company added successfully");
        } else {
            logger.error("Company created request Failed");
            throw new BadRequestException("Failed to add company");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCompany(@PathVariable Long id, @RequestBody Company company) {
        logger.info("Received a company Update request");
        boolean result =  companyService.updateCompany(id, company);
        if (result != false) {
            logger.info("Company updated request successfully completed");
            return ResponseEntity.status(HttpStatus.CREATED).body("Company updated successfully");
        } else {
            logger.error("Company updated request Failed");
            throw new BadRequestException("Failed to add company");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id) {
        logger.info("Received a company Delete request");
        boolean result = companyService.deleteCompany(id);
        if (result != false) {
            logger.info("Company Deleted request successfully completed");
            return ResponseEntity.status(HttpStatus.CREATED).body("Company Deleted successfully");
        } else {
            logger.error("Company Delete request Failed");
            throw new BadRequestException("Failed to delete company");
        }
    }

}