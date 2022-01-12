package de.PogChampIsAlreadyTaken.Papaya.Webshop.Baseclasses.Admin.Timemanagement;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class Opening extends PanacheEntity {
    public String city;
    public int house_number;
    public String street;
    public int zip;
}
