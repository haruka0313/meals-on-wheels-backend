package com.lithan.mow.service;

import com.lithan.mow.entity.Donation;
import com.lithan.mow.payload.request.DonationRequest;
import com.lithan.mow.repository.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DonationService {
    @Autowired
    private DonationRepository donationRepository;

    public Donation saveDonation(DonationRequest donationRequest) {
        BigDecimal maxAmount = new BigDecimal("9999999");
        if (donationRequest.getAmount().compareTo(maxAmount) > 0) {
            return null;
        }
        Donation donation = new Donation();

        donation.setPayerName(donationRequest.getPayerName());
        donation.setEmail(donationRequest.getEmail());
        donation.setPaymentSource(donationRequest.getPaymentSource());
        donation.setAmount(donationRequest.getAmount());
        donation.setTransactionDate(donationRequest.getTransactionDate());

        donationRepository.save(donation);
        return donation;
    }
}
