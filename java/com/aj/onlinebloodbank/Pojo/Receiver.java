package com.aj.onlinebloodbank.Pojo;

/**
 * Created by jadhave on 08-Jun-17.
 */

public class Receiver {

    String Name, Phone, Blood;

    public Receiver(String name, String phone, String blood) {
        Name = name;
        Phone = phone;
        Blood = blood;
    }

    public String getName() {
        return Name;
    }

    public String getPhone() {
        return Phone;
    }

    public String getBlood() {
        return Blood;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setBlood(String blood) {
        Blood = blood;
    }
}
