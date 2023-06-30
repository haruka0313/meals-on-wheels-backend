package com.lithan.mow.repository;

import com.lithan.mow.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    public Optional<Customer> findByEmail(String email);

    @Query("SELECT c.active FROM Customer c WHERE c.email = :email")
    Boolean findActiveByEmail(@Param("email") String email);



}
