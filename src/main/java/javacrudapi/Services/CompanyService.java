package javacrudapi.Services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.Query;
import jakarta.persistence.StoredProcedureQuery;
import javacrudapi.Controller.CompanyController;
import javacrudapi.Exception.KeyNotFoundException;
import javacrudapi.Interfaces.ICompanyService;
import javacrudapi.Model.Company;
import javacrudapi.Repository.CompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService implements ICompanyService {
    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EntityManager entityManager;

    public CompanyService(CompanyRepository companyRepository) {
    }

    @Override
    public List<Company> getAllCompany() {
        logger.info("Getting all companies in Service using database view");
        String sql = "SELECT * FROM all_companies_view";
        Query query = entityManager.createNativeQuery(sql);
        List<Company> result = query.getResultList();
        return result;
    }

    @Override
    public Company getCompanyById(Long id) {
        logger.info("Getting company by ID: {}", id);
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isPresent()) {
            return optionalCompany.get();
        }
        logger.error("Company not found with ID: {}", id);
        throw new KeyNotFoundException("Company not found with id: " + id);
    }

    @Override
    public boolean createCompany(Company company) {
        logger.info("Creating a new company: {} using database store procedure");
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("insert_company");
        storedProcedure.registerStoredProcedureParameter("name", String.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("address", String.class, ParameterMode.IN);
        storedProcedure.setParameter("name", company.getName());
        storedProcedure.setParameter("address", company.getAddress());
        storedProcedure.execute();
        return  true;
    }

    @Override
    public boolean updateCompany(Long id, Company company) {
        logger.info("Update company by ID: {}", id);
        Company existingCompany = getCompanyById(id);
        if (existingCompany != null) {
            existingCompany.setName(company.getName());
            existingCompany.setAddress(company.getAddress());
            companyRepository.save(existingCompany);
            logger.info("Company Updated successfully by ID", id);
            return true;
        }
        logger.error("Company Updated Failed with Id", id);
        return false;
    }

    @Override
    public boolean deleteCompany(Long id) {
        logger.info("Deleted company by ID: {}", id);
        Company existingEmployee = getCompanyById(id);
        if (existingEmployee != null) {
            companyRepository.deleteById(id);
            logger.info("Company Deleted successfully by ID", id);
            return true;
        }
        logger.error("Company Deleted Failed with Id", id);
        return false;
    }
}

