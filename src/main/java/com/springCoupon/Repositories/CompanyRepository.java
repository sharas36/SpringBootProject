package com.springCoupon.Repositories;

import com.springCoupon.Entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


public interface CompanyRepository extends JpaRepository<Company, Integer> {


    List<Company> findByName();

    List<Company> findByEmailAndPassword(String email, String password);

    Optional<Company> findByEmail(String email);

    Optional<Company> findByCompanyName(String company_Name);


}
