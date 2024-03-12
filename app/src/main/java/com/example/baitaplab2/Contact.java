package com.example.baitaplab2;

public class Contact {
    int Id;
    String Name;
    String Phone;
    String Email;
    String Image;
    Boolean Status;

    public Contact(int id, String name, String phone, String email, String image, Boolean status) {
        Id = id;
        Name = name;
        Phone = phone;
        Email = email;
        Image = image;
        Status = status;
    }

    public Contact() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }
}

