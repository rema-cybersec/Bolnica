package entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "MR10223_PACIJENT", schema = "BAZE1")
public class Mr10223Pacijent {
    @EmbeddedId
    Mr10223PacijentKey id;

    @Column(name = "OSIG", nullable=false)
    private boolean osig;

    public Mr10223Pacijent() {}

    public Mr10223Pacijent(String ime, String prz, LocalDate dat, boolean osig) {
        this.id = new Mr10223PacijentKey(ime, prz, dat);
        this.osig = osig;
    }

    public Mr10223PacijentKey getId() {
        return id;
    }

    public void setId(Mr10223PacijentKey id) {
        this.id = id;
    }

    public boolean isOsig() {
        return osig;
    }

    public void setOsig(boolean osig) {
        this.osig = osig;
    }

    public String toString() {
         return this.getId().getIme() + " " + this.getId().getPrz() + "(" + this.getId().getDatrodj() +")";
    }
}