package de.PogChampIsAlreadyTaken.Papaya.Webshop.Baseclasses;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.smallrye.common.constraint.Nullable;

import javax.persistence.*;

@Entity
public class Customer extends PanacheEntityBase {

    @Id
    public String id;
    public String last_name;
    public String first_name;
    @Column(nullable = true)
    public long customer_address_id ;
}

