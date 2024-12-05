package com.vung.restful.Service;

import java.lang.reflect.Field;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.vung.restful.Repository.CompanyRepository;
import com.vung.restful.domain.DTO.Meta;
import com.vung.restful.domain.DTO.ResultPaginationDTO;
import com.vung.restful.domain.Entity.Company;

@Service
public class CompanyService {
    private CompanyRepository companyRepository;
    public CompanyService(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }
    public Company saveCompany(Company company)
    {
        return this.companyRepository.save(company);
    }

    public ResultPaginationDTO getAllCompany(Pageable pageable , Specification<Company> spec)
    {

        Page<Company> PageCompany = this.companyRepository.findAll(spec, pageable);
        ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
        Meta meta = new Meta();

        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());

        meta.setPages(PageCompany.getTotalPages());
        meta.setTotal((int) PageCompany.getTotalElements());

        resultPaginationDTO.setResult(PageCompany.getContent());
        resultPaginationDTO.setMeta(meta);
        return resultPaginationDTO;
    }

    public void deleteCompany(Company company){
        this.companyRepository.delete(company);
    }
    public Company getCompany(long id){
        return this.companyRepository.findById(id);
    }

    public Company updateCompany(Company company){
        Company oldCompany = this.getCompany(company.getId());
        mergeObjects(company,oldCompany);
        return this.companyRepository.save(oldCompany);
    }
    public <T> T mergeObjects(T source, T target) {
        Field[] fields = source.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true); // Cho phép truy cập các field private
            try {
                Object value = field.get(source);
                if (value != null&& !value.equals("")) { // Chỉ set field nếu giá trị không null
                    field.set(target, value);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Could not access field: " + field.getName(), e);
            }
        }
        return target;
    }
    
}
