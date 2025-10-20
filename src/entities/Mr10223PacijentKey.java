package entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
public class Mr10223PacijentKey implements Serializable {
    @Column(name="IME", nullable = false)
    private String ime;

    @Column(name="PRZ", nullable = false)
    private String prz;

    @Column(name="DATRODJ", nullable = false)
    private LocalDate datrodj;

    public Mr10223PacijentKey() {}

    public Mr10223PacijentKey(String ime, String prz, LocalDate datrodj) {
        this.ime = ime;
        this.prz = prz;
        this.datrodj = datrodj;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrz() {
        return prz;
    }

    public void setPrz(String prz) {
        this.prz = prz;
    }

    public LocalDate getDatrodj() {
        return datrodj;
    }

    public void setDatrodj(LocalDate datrodj) {
        this.datrodj = datrodj;
    }

    public String[] getFields() {
        String[] fields = new String[3];
        fields[0] = this.ime;
        fields[1] = this.prz;
        fields[2] = this.datrodj.toString();
        return fields;
    }
}