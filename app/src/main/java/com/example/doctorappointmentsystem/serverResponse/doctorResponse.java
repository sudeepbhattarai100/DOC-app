package com.example.doctorappointmentsystem.serverResponse;

public class doctorResponse {
    private String username;
    private String firstName;
    private String lastName;
    private String token;
    private String email;
    private String profileImage;

    public doctorResponse(String user, String firstName, String lastName){
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;

    }

    public String getToken() { return token; }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

}
