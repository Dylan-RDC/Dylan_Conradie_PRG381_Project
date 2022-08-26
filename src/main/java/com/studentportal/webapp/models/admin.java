package com.studentportal.webapp.models;

import javax.persistence.*;



@Entity 
@Table(name= "administrator",
uniqueConstraints =  @UniqueConstraint(columnNames = {"admin_email"})         
)
public class admin implements Iuser{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long admin_id;

    @Column(name = "admin_name")
    private String admin_name;

    @Column(name = "password")
    private String password;

    @Column(name = "contact")
    private String contact;

    @Column(name = "admin_email")
    private String email;

    @Transient()
     private String role = "ADMIN";

     //Start Constructors
    public admin() {
    }

    

    public admin(String email) {
        this.email = email;
    }



    public admin(String admin_name, String password, String contact, String admin_email) {
        this.admin_name = admin_name;
        this.password = password;
        this.contact = contact;
        this.email = admin_email;
    }

    


    public admin(Long admin_id, String admin_name, String password, String contact, String admin_email) {
        this.admin_id = admin_id;
        this.admin_name = admin_name;
        this.password = password;
        this.contact = contact;
        this.email = admin_email;
    }
  //End Constructors

    //getter and setters
    public Long getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(Long admin_id) {
        this.admin_id = admin_id;
    }

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    

    @Override
    public String getEmail() {
        return email;
        
    }

    @Override
    public String getPassword() {
        return password;
    }



    public void setEmail(String email) {
        this.email = email;
    }



    @Override
    public String getRole() {
        return this.role;
    }

    @Override
    public String getUsername() {
       return this.email;
    }



    @Override
    public boolean isEnabled() {
        return true;
    }

    
}
