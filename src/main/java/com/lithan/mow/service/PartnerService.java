package com.lithan.mow.service;

import com.lithan.mow.entity.Customer;
import com.lithan.mow.entity.Partner;
import com.lithan.mow.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PartnerService {

    @Autowired
    PartnerRepository partnerRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Partner save(Partner partner){
        String password = passwordEncoder.encode(partner.getPassword());
        partner.setPassword(password);
        return  partnerRepository.save(partner);
    }
    public Boolean isPartnerExist(String email){
        Optional<Partner> customerOptional = partnerRepository.findByEmail(email);
        if (customerOptional.isPresent()){
            return true; // Customer exists
        }
        return false; // Customer does not exist
    }

    public Boolean isActive(String email){
        return partnerRepository.findActiveByEmail(email);
    }

    public Optional<Partner> getPartnerByEmail(String email){
        return partnerRepository.findByEmail(email);
    }

    public Partner updateProfile(Partner partner){
        return partnerRepository.save(partner);
    }


}
