package by.zubchenok.gitfadvicer.data;

import java.io.Serializable;

public class Gift implements Serializable{
    int Id;
    private String name;
    private String holiday;
    // 0 - female, 1 - male, -1 - any sex
    private int sex;
    private int ageMin;
    private int ageMax;
    private int age;
    private int priceMin;
    private int priceMax;
    private String imageId;

    private int reasonAny;
    private int reasonBirthday;
    private int reasonNewYear;
    private int reasonWedding;
    private int reason8Mar;
    private int reason23Feb;
    private int reasonValentinesDay;

    public Gift() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAgeMin() {
        return ageMin;
    }

    public void setAgeMin(int ageMin) {
        this.ageMin = ageMin;
    }

    public int getAgeMax() {
        return ageMax;
    }

    public void setAgeMax(int ageMax) {
        this.ageMax = ageMax;
    }

    public int getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(int priceMin) {
        this.priceMin = priceMin;
    }

    public int getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(int priceMax) {
        this.priceMax = priceMax;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public int getReasonAny() {
        return reasonAny;
    }

    public void setReasonAny(int reasonAny) {
        this.reasonAny = reasonAny;
    }

    public int getReasonBirthday() {
        return reasonBirthday;
    }

    public void setReasonBirthday(int reasonBirthday) {
        this.reasonBirthday = reasonBirthday;
    }

    public int getReasonNewYear() {
        return reasonNewYear;
    }

    public void setReasonNewYear(int reasonNewYear) {
        this.reasonNewYear = reasonNewYear;
    }

    public int getReasonWedding() {
        return reasonWedding;
    }

    public void setReasonWedding(int reasonWedding) {
        this.reasonWedding = reasonWedding;
    }

    public int getReason8Mar() {
        return reason8Mar;
    }

    public void setReason8Mar(int reason8Mar) {
        this.reason8Mar = reason8Mar;
    }

    public int getReason23Feb() {
        return reason23Feb;
    }

    public void setReason23Feb(int reason23Feb) {
        this.reason23Feb = reason23Feb;
    }

    public int getReasonValentinesDay() {
        return reasonValentinesDay;
    }

    public void setReasonValentinesDay(int reasonValentinesDay) {
        this.reasonValentinesDay = reasonValentinesDay;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}


