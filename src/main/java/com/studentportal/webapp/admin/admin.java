package com.studentportal.webapp.admin;

import javax.persistence.*;



@Entity 
@Table(name= "administrator")
public class admin {
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

    public String getPassword() {
        return password;
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
    public String toString() {
        return "admin [admin_id=" + admin_id + ", admin_name=" + admin_name + ", contact=" + contact + ", password="
                + password + "]";
    }

    
    
    
    
}
