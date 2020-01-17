package ru.vartanyan.entities;

import javax.persistence.*;

@Entity
public class Counteragent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String nameDirector;
    private String phone;
    private String address;
    private String email;
    private String INN;
    private String OGRN;



    public Counteragent() {

    }

    public Counteragent(String name, String nameDirector, String phone, String address, String email, String INN, String OGRN) {
        this.name = name;
        this.nameDirector = nameDirector;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.INN = INN;
        this.OGRN = OGRN;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameDirector() {
        return nameDirector;
    }

    public void setNameDirector(String nameDirector) {
        this.nameDirector = nameDirector;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getINN() {
        return INN;
    }

    public void setINN(String INN) {
        this.INN = INN;
    }

    public String getOGRN() {
        return OGRN;
    }

    public void setOGRN(String OGRN) {
        this.OGRN = OGRN;
    }
}
