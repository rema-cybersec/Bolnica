package views;

import entities.Mr10223Dijagnoza;

public class Mr10223DijagnozaView implements View<Mr10223Dijagnoza> {
    private String sifra;
    private String naziv;
    private String opis;

    public Mr10223DijagnozaView() {}

    public Mr10223DijagnozaView(String sifra, String naziv, String opis) {
        this.sifra = sifra;
        this.naziv = naziv;
        this.opis = opis;
    }

    @Override
    public void load(Mr10223Dijagnoza e) {
        this.sifra = e.getId().getSifra();
        this.naziv = e.getNaziv();
        this.opis = e.getOpis();
    }

    public String getSifra() {
        return sifra;
    }

    public String getNaziv() {
        return naziv;
    }

    public String getOpis() {
        return opis;
    }

    public String toString() {
        return "Dijagnoza {" + this.sifra + ", " + this.naziv + ", " + this.opis + "}";
    }
}
