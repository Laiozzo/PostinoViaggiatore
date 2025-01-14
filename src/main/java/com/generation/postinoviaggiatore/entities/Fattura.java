package com.generation.postinoviaggiatore.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Fattura extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "valuta_id")
    Valuta valuta;









    public Valuta getValuta() {
        return valuta;
    }

    public void setValuta(Valuta valuta) {
        this.valuta = valuta;
    }


}
