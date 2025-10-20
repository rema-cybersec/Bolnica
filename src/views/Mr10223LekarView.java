package views;

import entities.Mr10223Lekar;

import javax.persistence.Column;

public class Mr10223LekarView implements  View<Mr10223Lekar> {
    @Column
    private String ime;
    @Column
    private String prz;
    @Column
    private String spec;

    public Mr10223LekarView() {}

    public Mr10223LekarView(Mr10223Lekar lekar) {
        this.ime = lekar.getId().getIme();
        this.prz = lekar.getId().getPrz();
        this.spec = lekar.getId().getSpec();
    }

    @Override
    public void load(Mr10223Lekar e) {
        this.ime = e.getId().getIme();
        this.prz = e.getId().getPrz();
        this.spec = e.getId().getSpec();
    }

    public String getIme() {
        return ime;
    }

    public String getPrz() {
        return prz;
    }

    public String getSpec() {
        return spec;
    }

    public String toString() {
        return "Lekar {" + this.ime + ", " + this.prz + ", "+ this.spec + "}";
    }
}
