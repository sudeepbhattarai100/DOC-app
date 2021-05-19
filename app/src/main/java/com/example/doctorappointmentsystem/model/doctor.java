package com.example.doctorappointmentsystem.model;

public class doctor {

    private String _id;
    private String username;
    private String password;
    private String firstName;
    private String qualification;
    private String lastName;
    private String address;
    private String email;
    private String categoryName;
    private String profileImage;

//    public doctor(String username, String email,String password, String firstName, String qualification, String lastName, String address, String profileImage)
//    {
//        this.email=email;
//        this.username = username;
//        this.firstName= firstName;
//        this.password=password;
//        this.lastName=lastName;
//        this.address=address;
//        this.profileImage = profileImage;
//        this.qualification= qualification;
//
//    }


    public doctor(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public doctor(String firstName, String qualification, String profileImage){
        this.firstName = firstName;
        this.qualification= qualification;
        this.profileImage= profileImage;
    }

    public doctor(String username, String password, String firstName, String qualification, String lastName, String address, String email, String categoryName, String profileImage) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.qualification = qualification;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.categoryName = categoryName;
        this.profileImage = profileImage;
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
