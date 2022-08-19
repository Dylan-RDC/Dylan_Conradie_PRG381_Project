package com.studentportal.webapp.models;

public interface Iuser {
    public String getEmail();
    public String getPassword();
    public String getRole();
    public String getUsername();
    public boolean isEnabled();
}
