package com.lithan.mow.repository;


import com.lithan.mow.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartnerRepository extends JpaRepository<Partner,Long> {
    public Optional<Partner> findByEmail(String email);
    @Query("SELECT p.active FROM Partner p WHERE p.email = :email")
    Boolean findActiveByEmail(@Param("email") String email);



}
