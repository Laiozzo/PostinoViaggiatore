package com.generation.postinoviaggiatore.entities;

import jakarta.persistence.*;

import java.util.List;
@Entity
public class Paese extends BaseEntity{

    String nome;
    int distanza;

    @OneToOne(cascade =  CascadeType.ALL)
            @JoinColumn(name ="valuta_id", referencedColumnName = "id")
    Valuta valuta;

    @OneToMany(mappedBy = "paese", cascade = CascadeType.ALL)
    List<Bambino> bambini;
}
