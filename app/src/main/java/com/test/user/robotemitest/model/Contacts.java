package com.test.user.robotemitest.model;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Contacts extends RealmObject {

    @PrimaryKey
    private int id;
    private String avatar;
    private String firstName;
    private String lastName;
    private String phone;
    private String gender;
    private String email;
    private String address;

    private long lastTime;
    private String lastMessage;
    private String lastTimeFormatted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastTimeFormatted() {
        return lastTimeFormatted;
    }

    public void setLastTimeFormatted(String lastTimeFormatted) {
        this.lastTimeFormatted = lastTimeFormatted;
    }
}
