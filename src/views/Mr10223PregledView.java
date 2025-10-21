package views;

import entities.Mr10223Pregled;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Mr10223PregledView implements View<Mr10223Pregled> {
    private String lekar_ime;
    private String lekar_prz;
    private String lekar_spec;
    private String pacijent_ime;
    private String pacijent_prz;
    private LocalDate pacijent_datrodj;
    private String dijagnoza_sifra;
    private LocalDateTime dat;
    private String vreme;
    private double cena;

    public Mr10223PregledView() {}

    public void load(Mr10223Pregled e) {
        this.lekar_ime = e.getId().getLekarId().getIme();
        this.lekar_prz = e.getId().getLekarId().getPrz();
        this.lekar_spec = e.getId().getLekarId().getSpec();
        this.pacijent_ime = e.getId().getPacijentId().getIme();
        this.pacijent_prz = e.getId().getPacijentId().getPrz();
        this.pacijent_datrodj = e.getId().getPacijentId().getDatrodj();
        this.dijagnoza_sifra = e.getDijagnozaId() == null ?  "/" : e.getDijagnozaId().getSifra();
        this.dat = e.getId().getDat();
        this.vreme = e.getVreme();
        this.cena = e.getCena();
    }

    public String getLekar_ime() {
        return lekar_ime;
    }

    public String getLekar_prz() {
        return lekar_prz;
    }

    public String getLekar_spec() {
        return lekar_spec;
    }

    public String getPacijent_ime() {
        return pacijent_ime;
    }

    public String getPacijent_prz() {
        return pacijent_prz;
    }

    public LocalDate getPacijent_datrodj() {
        return pacijent_datrodj;
    }

    public String getDijagnoza_sifra() {
        return dijagnoza_sifra;
    }

    public LocalDateTime getDat() {
        return dat;
    }

    public String getVreme() {
        return vreme;
    }

    public double getCena() {
        return cena;
    }

    public String toString() {
        return "Pregled {" + this.lekar_ime + ", " + this.lekar_prz + ", " + this.lekar_spec + ", " + this.pacijent_ime + ", " + this.pacijent_ime + ", " + this.pacijent_prz + ", " + this.dijagnoza_sifra + ", " + this.dat + ", " + this.vreme + ", " + this.cena + "}";
    }
}
