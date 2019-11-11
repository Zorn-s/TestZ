package ru.app.generatorp;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    public User(int id,String sity,String fotoName,String firstName, String lastName, String email,String age,String work,String gender,String area,String cars) {
        this.id = id;
        this.sity = sity;
        this.fotoName = fotoName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;

        this.age = age;
        this.work = work;
        this.gender = gender;
        this.area = area;
        this.cars = cars;

    }



    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "sity")
    private String sity;

    @ColumnInfo(name = "foto_name")
    private String fotoName;

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "age")
    private String age;

    @ColumnInfo(name = "work")
    private String work;

    @ColumnInfo(name = "gender")
    private String gender;

    @ColumnInfo(name = "area")
    private String area;

    @ColumnInfo(name = "cars")
    private String cars;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSity(String Sity) {
        this.sity = Sity;
    }

    public void setFotoName(String fotoName) {
        this.fotoName = fotoName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setAge(String age) {
        this.age = age;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setCars(String cars) {
        this.cars = cars;
    }

    public String getSity() {
        return sity;
    }

    public String getFotoName() {
        return fotoName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getAge() {
        return age;
    }
    public String getWork() {
        return work;
    }
    public String getGender() {
        return gender;
    }
    public String getArea() {
        return area;
    }
    public String getCars() {
        return cars;
    }

}
