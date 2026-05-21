package it.cigbase.sigarette.entity;

import jakarta.persistence.*;
import org.springframework.web.bind.annotation.GetMapping;

@Entity
public class Sigaretta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "prezzo", nullable = false)
    private double prezzo;

    @Column(name = "nicotina", nullable = false)
    private double nicotina;

    @Column(name = "catrame", nullable = false)
    private double catrame;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "descrizione", nullable = false)
    private String descrizione;

    @Column(name = "url_immagine", nullable = false)
    private String url_immagine;

    public Sigaretta(){}

    public Sigaretta(int id, String brand, double prezzo, double nicotina, double catrame, String tipo, String descrizione, String url_immagine) {
        this.id = id;
        this.brand = brand;
        this.prezzo = prezzo;
        this.nicotina = nicotina;
        this.catrame = catrame;
        this.tipo = tipo;
        this.descrizione = descrizione;
        this.url_immagine = url_immagine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public double getNicotina() {
        return nicotina;
    }

    public void setNicotina(double nicotina) {
        this.nicotina = nicotina;
    }

    public double getCatrame() {
        return catrame;
    }

    public void setCatrame(double catrame) {
        this.catrame = catrame;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getUrl_immagine() {
        return url_immagine;
    }

    public void setUrl_immagine(String url_immagine) {
        this.url_immagine = url_immagine;
    }
}
