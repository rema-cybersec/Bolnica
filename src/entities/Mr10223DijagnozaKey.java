package entities;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class Mr10223DijagnozaKey implements Serializable {
    @Column(name="sifra", nullable=false)
    private String sifra;

    public Mr10223DijagnozaKey() {}

    public Mr10223DijagnozaKey(String sifra) {
        this.sifra = sifra;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String[] getFields(){
        return new String[] {this.sifra};
    }
}