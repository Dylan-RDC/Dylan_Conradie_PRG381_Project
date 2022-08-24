package com.studentportal.webapp.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentportal.webapp.models.admin;
import com.studentportal.webapp.repo.adminRepo;

@Service
@Transactional
public class adminService {
    
    @Autowired
    adminRepo adRepo;

    public List<admin> getAllAdmins()
    {
        return adRepo.findAll();
    }

    public String addAdmin(admin newad) {
        newad = adRepo.save(newad);

        return String.format("New admin has been added into Database, admin number: %d",newad.getAdmin_id() );
    }

    public String deleteAdmin(Long ad_id)
    {
        adRepo.deleteById(ad_id);
        return String.format("admin with ID: %s was deleted successfully", ad_id);
    }

    public String updateAdmin(admin ad)
    {
        adRepo.save(ad);
        return String.format("admin with ID: %s was updated successfully", ad.getAdmin_id());
    }

    public admin getAdmin(Long ad_id)
    {
        return adRepo.getReferenceById(ad_id);
    }

    public admin getAdminByEmail(String email)
    {
        return adRepo.getAdminByEmail(email);
    }

}

