package by.zubchenok.gitfadvicer;

public class Gift {
    private String name;
    private String holiday;
    private String sex;
    private String age;
    private String price;
    private int imageId;

    public Gift(String name, String price, int imageId) {
        this.name = name;
        this.price = price;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public String getHoliday() {
        return holiday;
    }

    public String getSex() {
        return sex;
    }

    public String getAge() {
        return age;
    }

    public String getPrice() {
        return price;
    }

    public int getImageId() {
        return imageId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
