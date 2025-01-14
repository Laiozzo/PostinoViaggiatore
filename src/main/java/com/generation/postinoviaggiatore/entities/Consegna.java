package com.generation.postinoviaggiatore.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Consegna extends BaseEntity{

    @ManyToOne
    @JoinColumn(name="bambino_id", nullable = false)
    private Bambino bambino;

    @ManyToOne
    @JoinColumn(name="regalo_id", nullable = false)
    private Regalo regalo;


    public Bambino getBambino() {
        return bambino;
    }

    public void setBambino(Bambino bambino) {
        this.bambino = bambino;
    }

    public Regalo getRegalo() {
        return regalo;
    }

    public void setRegalo(Regalo regalo) {
        this.regalo = regalo;
    }
}
