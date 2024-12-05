package com.vung.restful.Controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;
import com.vung.restful.Service.CompanyService;
import com.vung.restful.domain.DTO.ResultPaginationDTO;
import com.vung.restful.domain.Entity.Company;
import com.vung.restful.util.CustomAnnotition.APImessage;

import jakarta.validation.Valid;


@RequestMapping("/api/v1")
@RestController
public class CompanyController {
    private CompanyService companyService;
    public CompanyController(CompanyService companyService){
        this.companyService = companyService;
    }
    @APImessage("call API create company")
    @PostMapping("/companies")
    public ResponseEntity<Company> handleCreateCompany(@Valid @RequestBody Company company) {
        return ResponseEntity.ok().body(this.companyService.saveCompany(company));
    }
    @APImessage("call API get company")
    @GetMapping("/companies")
    public ResponseEntity<ResultPaginationDTO> handleGetCompany(
        @Filter Specification<Company> spec ,
        Pageable pageable){
        // String sCurrent = currentPageOptional.isPresent() ? currentPageOptional.get() : "";
        // String sSize = sizePageOptional.isPresent() ? sizePageOptional.get() : "";
        // int PageNumber = Integer.parseInt(sCurrent);
        // int Size = Integer.parseInt(sSize);
        // Pageable pageable = PageRequest.of(PageNumber - 1, Size);
        
        ResultPaginationDTO resultPaginationDTO = this.companyService.getAllCompany(pageable , spec);
        
        return ResponseEntity.ok().body(resultPaginationDTO);
    }

    @APImessage("call API delete company")
    @DeleteMapping("/companies/{id}")
    public ResponseEntity<Company> handleDeleteCompany(@PathVariable long id)
    {
        Company company = this.companyService.getCompany(id);
        this.companyService.deleteCompany(company);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @APImessage("call API update company")
    @PutMapping("/companies")
    public ResponseEntity<Company> handleUpdateCompany( @RequestBody Company company) {
        //TODO: process PUT request
        Company newCompany = this.companyService.updateCompany(company);
        return ResponseEntity.ok().body(newCompany); 
    }
}
