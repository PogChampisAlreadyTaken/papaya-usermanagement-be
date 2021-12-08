package de.PogChampIsAlreadyTaken.Papaya.Webshop;

import javax.persistence.*;

@Entity
public class Customer {

    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long customerId;
    private String lastName;
    private String firstName;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }
}
