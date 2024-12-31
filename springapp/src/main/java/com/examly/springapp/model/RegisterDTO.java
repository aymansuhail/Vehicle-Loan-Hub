package com.examly.springapp.model;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
 
public class RegisterDTO {
   
    @NotBlank(message="Username can not be empty")
    private String username;
   
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is Mandatory")
    private String email;
 
    @NotBlank(message = "Password should not be blank")
    @Pattern( regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one digit, and one special character" )
    private String password;

    @NotBlank(message = "Mobile Number should not be blank")
    @Pattern( regexp = "^\\d{10}$", message = "Mobile number must be 10 digits")
    private String  mobileNumber;

    @NotBlank(message = "User role should not be blank")
    private String userRole;
 
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public String getMobileNumber() {
        return mobileNumber;
    }
 
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
 
    public String getUserRole() {
        return userRole;
    }
 
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
 
 
   
}
 