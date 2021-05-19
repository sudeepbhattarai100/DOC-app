package com.example.doctorappointmentsystem.serverResponse;

public class patientResponse {


    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String token;
    private  String address;


    private String profileImage;



    public patientResponse(String user, String firstName, String lastName){
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;

    }


    public patientResponse( String username, String email, String firstName, String lastName, String address, int update){
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.address = address;
    }

//
public patientResponse(String username, String email, String firstname, String lastname, String address)
{
    this.email=email;
    this.username = username;
    this.firstName= firstname;

    this.lastName=lastname;
    this.address=address;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
