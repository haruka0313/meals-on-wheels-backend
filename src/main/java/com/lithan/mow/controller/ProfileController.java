package com.lithan.mow.controller;

import com.lithan.mow.constraint.EGender;
import com.lithan.mow.entity.Customer;
import com.lithan.mow.entity.Partner;
import com.lithan.mow.payload.request.UpdateProfileRequest;
import com.lithan.mow.payload.response.ProfileResponse;
import com.lithan.mow.service.CustomerService;
import com.lithan.mow.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/me")

public class ProfileController {

    @Autowired
    CustomerService customerService;

    @Autowired
    PartnerService partnerService;

    @GetMapping("profile")
    public ResponseEntity<?> getProfile(@RequestParam("email")String email,@RequestParam("role")String role){
        ProfileResponse res = new ProfileResponse();
        System.out.println(email);
        System.out.println(role);
        if(role.equals("ROLE_PARTNER")){
           Partner partner = partnerService.getPartnerByEmail(email).get();
            res.setName(partner.getName());
            res.setAddress(partner.getAddress());
            res.setEmail(partner.getEmail());
            res.setPicture(partner.getProfilePicture());
            res.setBackground(partner.getProfileBackground());

        }else {
            Customer customer = customerService.getCustomerByEmail(email).get();
            res.setName(customer.getName());
            res.setEmail(customer.getEmail());
            res.setGender(customer.getGender().toString());
            res.setAddress(customer.getAddress());
            res.setPicture(customer.getProfilePicture());
            res.setBackground(customer.getProfileBackground());
            res.setBirthDate(customer.getBirthDate());
        }
        return ResponseEntity.ok(res);
    }
    @PostMapping("update-profile")
    public ResponseEntity<?> updateProfile(@RequestBody UpdateProfileRequest request) {

        System.out.println("ROLE"+request.getRole());

        try {
            if (request.getRole().equals("ROLE_PARTNER")) {
                Partner partner = partnerService.getPartnerByEmail(request.getEmail()).orElse(null);
                if (partner == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Partner not found");
                }
                partner.setName(request.getName());
                partner.setAddress(request.getAddress());
                partner = partnerService.updateProfile(partner);
                return ResponseEntity.ok(partner);
            } else {
                Customer customer = customerService.getCustomerByEmail(request.getEmail()).orElse(null);
                if (customer == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
                }
                customer.setName(request.getName());
                customer.setAddress(request.getAddress());
                customer.setGender(EGender.valueOf(request.getGender()));
                customer.setBirthDate(request.getBirthDay());
                customer.setDistance(request.getDistance());
                customer = customerService.updateProfile(customer);
                return ResponseEntity.ok(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @PostMapping("update-picture")
    public ResponseEntity<?> updatePicture(@RequestParam("picture") MultipartFile picture, @RequestParam("role") String role, @RequestParam("email") String email) {
        try {
            // Check file size
            if (picture.getSize() > 304857) {
                return ResponseEntity.badRequest().body("File size exceeds the allowed limit. File must be under 300 KB.");
            }

            byte[] convertedPicture = picture.getBytes();
            if (role.equals("ROLE_PARTNER")) {
                Partner partner = partnerService.getPartnerByEmail(email).get();
                partner.setProfilePicture(convertedPicture);
                partner = partnerService.updateProfile(partner);
                return ResponseEntity.ok(partner);
            } else {
                Customer customer = customerService.getCustomerByEmail(email).get();
                customer.setProfilePicture(convertedPicture);
                customer = customerService.updateProfile(customer);
                return ResponseEntity.ok(customer);
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the file.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    } @PostMapping("update-background")
    public ResponseEntity<?> updateBackground(@RequestParam("background") MultipartFile background, @RequestParam("role") String role, @RequestParam("email") String email) {
        try {
            // Check file size
            if (background.getSize() > 304857) {
                return ResponseEntity.badRequest().body("File size exceeds the allowed limit. File must be under 300 KB.");
            }


            byte[] convertedBackground = background.getBytes();
            if (role.equals("ROLE_PARTNER")) {
                Partner partner = partnerService.getPartnerByEmail(email).get();
                partner.setProfileBackground(convertedBackground);
                partner = partnerService.updateProfile(partner);
                return ResponseEntity.ok(partner);
            } else {
                Customer customer = customerService.getCustomerByEmail(email).get();
                customer.setProfileBackground(convertedBackground);
                customer = customerService.updateProfile(customer);
                return ResponseEntity.ok(customer);
            }
        } catch (IOException e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the file.");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }



}
