package de.PogChampIsAlreadyTaken.Papaya.Webshop.Baseclasses.Admin.Timemanagement;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Franziska Hesselfeld
 */
@Entity
public class Delivertime extends PanacheEntityBase {
    @Id
    public int id;
    public int time_in_minutes;
}
