package entities;

import javax.persistence.*;

@Entity
@Table(name = "MR10223_Lekar", schema = "BAZE1")
public class Mr10223Lekar {
    @EmbeddedId
    private Mr10223LekarKey id;

    public Mr10223Lekar() {}

    public Mr10223Lekar(String ime, String prz, String spec) {
        this.id =  new Mr10223LekarKey(ime, prz, spec);
    }

    public Mr10223LekarKey getId() {
        return id;
    }

    public void setId(Mr10223LekarKey id) {
        this.id = id;
    }

    public String toString() {
        return "Lekar: {" + "ime=" + id.getIme() + ", prz=" + id.getPrz() + ", spec=" + id.getSpec() + '}';
    }
}
