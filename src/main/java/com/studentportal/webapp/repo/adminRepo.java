package com.studentportal.webapp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.studentportal.webapp.models.admin;

@Repository
public interface adminRepo extends JpaRepository<admin, Long>{
    @Query(value = "SELECT a.* FROM administrator a WHERE a.admin_email = ?1",nativeQuery = true )
    public admin getAdminByEmail(@Param("email") String email);
}
