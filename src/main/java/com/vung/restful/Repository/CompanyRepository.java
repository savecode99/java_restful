package com.vung.restful.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.vung.restful.domain.Entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> , JpaSpecificationExecutor<Company>{
    public Company save(Company company);
    public List<Company> findAll();
    public void delete(Company company);
    public Company findById(long id);
}
