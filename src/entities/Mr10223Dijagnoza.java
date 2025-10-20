package entities;

import javax.persistence.*;

@Entity
@Table(name = "MR10223_DIJAGNOZA", schema = "BAZE1")
public class Mr10223Dijagnoza {
    @EmbeddedId
    Mr10223DijagnozaKey id;

    @Column(name="naziv", nullable = false)
    String naziv;

    @Column(name="opis", nullable = false)
    String opis;

    public Mr10223Dijagnoza() {}

    public Mr10223Dijagnoza(String sifra, String naziv, String opis) {
        this.id = new Mr10223DijagnozaKey(sifra);
        this.naziv = naziv;
        this.opis = opis;
    }

    public Mr10223DijagnozaKey getId() {
        return id;
    }

    public void setId(Mr10223DijagnozaKey id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String toString() {
        String out = "Dijagnoza {";
        for(String field : this.id.getFields()) {
            out += field + ", ";
        }
        out += this.naziv + ", ";
        out += this.opis;
        return out + "}";
    }
}