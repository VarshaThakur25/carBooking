package com.varshathakur.cabbooking.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Driver {

    String id;

    String driverName;

    @Setter
    String cabID;

    String licenceNumber;

    String sex;

    Integer age;

    public Driver(String id, String driverName, String licenceNumber, String sex, Integer age) {
        this.id = id;
        this.driverName = driverName;
        this.licenceNumber = licenceNumber;
        this.sex = sex;
        this.age = age;
    }
}
