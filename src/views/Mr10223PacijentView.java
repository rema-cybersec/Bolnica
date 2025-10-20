package views;

import entities.Mr10223Pacijent;

import java.time.LocalDate;

public class Mr10223PacijentView implements View<Mr10223Pacijent> {
    private String ime;
    private String prz;
    private LocalDate dat;
    private boolean osig;

    public Mr10223PacijentView(){}

    @Override
    public void load(Mr10223Pacijent e) {
        this.ime = e.getId().getIme();
        this.prz = e.getId().getPrz();
        this.dat = e.getId().getDatrodj();
        this.osig = e.isOsig();
    }

    public String getIme() {
        return ime;
    }
    public String getPrz() {
        return prz;
    }
    public LocalDate getDat() {return dat;}
    public boolean isOsig() {return osig;}

    public String toString() {
        return "Pacijent {" + this.ime + ", " + this.prz + ", " + this.dat + ", " + this.osig + "}";
    }
}
