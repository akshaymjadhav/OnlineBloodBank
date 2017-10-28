package com.aj.onlinebloodbank.Pojo;

/**
 * Created by jadhave on 08-Jun-17.
 */

public class Donor {

    String Name,Address,Phone,Blood,Age;

    public Donor(String name, String address, String phone, String blood, String age) {
        Name = name;
        Address = address;
        Phone = phone;
        Blood = blood;
        Age = age;
    }

    public String getName() {
        return Name;
    }

    public String getAddress() {
        return Address;
    }

    public String getPhone() {
        return Phone;
    }

    public String getBlood() {
        return Blood;
    }

    public String getAge() {
        return Age;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setBlood(String blood) {
        Blood = blood;
    }

    public void setAge(String age) {
        Age = age;
    }
}


