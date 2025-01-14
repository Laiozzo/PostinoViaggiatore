package com.generation.postinoviaggiatore.entities;

import jakarta.persistence.*;

@Entity
public class Bambino extends BaseEntity {

    int nome;
    int punteggio_Bonta;

    @ManyToOne
            @JoinColumn(name="nazione_id", referencedColumnName = "id")
    Paese nazione;
}
