package entities;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
public class Mr10223PregledKey implements Serializable {
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "ime", column = @Column(name = "LEKAR_IME")),
            @AttributeOverride(name = "prz", column = @Column(name = "LEKAR_PRZ")),
            @AttributeOverride(name = "spec", column = @Column(name = "LEKAR_SPEC"))
    })
    private Mr10223LekarKey lekarId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "ime", column = @Column(name = "PACIJENT_IME")),
            @AttributeOverride(name = "prz", column = @Column(name = "PACIJENT_PRZ")),
            @AttributeOverride(name = "datrodj", column = @Column(name = "PACIJENT_DATRODJ"))
    })
    // ne ovo nije normalno
    // sati provedeno namestajuci hibernate: 14
    private Mr10223PacijentKey pacijentId;

    @Column(name="dat", nullable = false)
    private LocalDateTime dat;

    public Mr10223PregledKey() {}

    public Mr10223PregledKey(Mr10223LekarKey lekarId, Mr10223PacijentKey pacijentId, LocalDateTime dat) {
        this.lekarId = lekarId;
        this.pacijentId = pacijentId;
        this.dat = dat;
    }

    public Mr10223LekarKey getLekarId() {
        return lekarId;
    }

    public void setLekarId(Mr10223LekarKey lekarId) {
        this.lekarId = lekarId;
    }

    public Mr10223PacijentKey getPacijentId() {
        return pacijentId;
    }

    public void setPacijentId(Mr10223PacijentKey pacijentId) {
        this.pacijentId = pacijentId;
    }

    public LocalDateTime getDat() {
        return dat;
    }

    public void setDat(LocalDateTime dat) {
        this.dat = dat;
    }

    public String[] getFields() {
        String[] lekarFields = this.lekarId.getFields();
        String[] pacijentFields = this.pacijentId.getFields();

        String[] fields =  new String[lekarFields.length + pacijentFields.length + 1];

        int index = 0;
        for(String field : lekarFields){
            fields[index++] = field;
        }
        for(String field : pacijentFields){
            fields[index++] = field;
        }
        fields[index] =  this.dat.toString();
        return fields;
    }
}
