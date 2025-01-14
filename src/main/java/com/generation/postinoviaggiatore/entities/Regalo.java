package com.generation.postinoviaggiatore.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Regalo extends BaseEntity {

    int punteggio;
    double prezzo;


}
