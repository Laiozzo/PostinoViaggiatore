package com.generation.postinoviaggiatore.entities;

import jakarta.persistence.Entity;

@Entity
public class Valuta extends BaseEntity {

    String valuta;
    int change_from_dollar;
}
