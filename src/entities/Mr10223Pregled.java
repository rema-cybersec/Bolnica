package entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "MR10223_PREGLED", schema = "BAZE1")
public class Mr10223Pregled {
    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "lekarId.ime", column = @Column(name = "LEKAR_IME")),
            @AttributeOverride(name = "lekarId.prz", column = @Column(name = "LEKAR_PRZ")),
            @AttributeOverride(name = "lekarId.spec", column = @Column(name = "LEKAR_SPEC")),
            @AttributeOverride(name = "pacijentId.ime", column = @Column(name = "PACIJENT_IME")),
            @AttributeOverride(name = "pacijentId.prz", column = @Column(name = "PACIJENT_PRZ")),
            @AttributeOverride(name = "pacijentId.datrodj", column = @Column(name = "PACIJENT_DATRODJ")),
    })
    // ZASTO SAM ODLUCIO DA CU MODELOVATI BAZU SA KOMPOZITNIM KLJUCEVIMA MRZIM HIBERNATE
    private Mr10223PregledKey id;

    @Column(name="VREME", nullable = false)
    private String vreme;

    @Column(name="CENA", nullable = true)
    private Double cena;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "sifra",  column = @Column(name = "DIJAGNOZA_SIFRA", nullable = true)),
    })
    private Mr10223DijagnozaKey dijagnozaId;

    public Mr10223Pregled() {}

    public Mr10223Pregled(String l_ime, String l_prz, String l_spec, String p_ime, String p_prz, LocalDate p_datrodj, String d_sifra, LocalDateTime dat, String vreme, Double cena) {
        this.id = new Mr10223PregledKey(
                new Mr10223LekarKey(l_ime, l_prz, l_spec),
                new Mr10223PacijentKey(p_ime, p_prz, p_datrodj),
                dat
                );
        this.dijagnozaId = new Mr10223DijagnozaKey(d_sifra);
        this.vreme = vreme;
        this.cena = cena;
    }

    public Mr10223PregledKey getId() {
        return id;
    }

    public void setId(Mr10223PregledKey id) {
        this.id = id;
    }

    public String getVreme() {
        return vreme;
    }

    public void setVreme(String vreme) {
        this.vreme = vreme;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }

    public Mr10223DijagnozaKey getDijagnozaId() {
        return dijagnozaId;
    }

    public void setDijagnozaId(Mr10223DijagnozaKey dijagnozaId) {
        this.dijagnozaId = dijagnozaId;
    }

    public String toString() {
        String out = "Pregled {";
        for(String field : this.id.getFields()) {
            out += field + ", ";
        }
        out += this.vreme.toString();
        if(this.cena != null) {
            out += ", " + this.cena;
        }
        return out + "}";
    }
}