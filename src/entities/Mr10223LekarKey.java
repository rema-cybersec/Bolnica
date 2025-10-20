package entities;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class Mr10223LekarKey implements Serializable {
    @Column(name="IME", nullable=false)
    private String ime;

    @Column(name="PRZ", nullable=false)
    private String prz;

    @Column(name="SPEC", nullable = false)
    private String spec;

    public Mr10223LekarKey() {}

    public Mr10223LekarKey(String ime, String prz, String spec) {
        this.ime = ime;
        this.prz = prz;
        this.spec = spec;
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

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String[] getFields() {
        String[] fields = new String[2];
        fields[0] = this.ime;
        fields[1] = this.prz;
        return fields;
    }
}
