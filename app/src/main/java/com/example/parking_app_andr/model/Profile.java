package com.example.parking_app_andr.model;

import java.util.Date;


public class Profile {
    //u_name,u_email,u_phone_num,u_car_plate_num,u_pass_word
    private String id;
    private String name;
    private String email;
    private String  u_phone_num;
    private String u_car_plate_num;
    private String u_pass_word;

    public Profile(String id, String name, String email, String u_phone_num, String u_car_plate_num, String u_pass_word) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.u_phone_num = u_phone_num;
        this.u_car_plate_num = u_car_plate_num;
        this.u_pass_word = u_pass_word;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getU_phone_num() {
        return u_phone_num;
    }

    public void setU_phone_num(String u_phone_num) {
        this.u_phone_num = u_phone_num;
    }

    public String getU_car_plate_num() {
        return u_car_plate_num;
    }

    public void setU_car_plate_num(String u_car_plate_num) {
        this.u_car_plate_num = u_car_plate_num;
    }

    public String getU_pass_word() {
        return u_pass_word;
    }

    public void setU_pass_word(String u_pass_word) {
        this.u_pass_word = u_pass_word;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", u_phone_num='" + u_phone_num + '\'' +
                ", u_car_plate_num='" + u_car_plate_num + '\'' +
                ", u_pass_word='" + u_pass_word + '\'' +
                '}';
    }
}
