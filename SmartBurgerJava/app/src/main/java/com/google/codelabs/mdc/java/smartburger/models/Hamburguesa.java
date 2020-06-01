package com.google.codelabs.mdc.java.smartburger.models;

import android.widget.TextView;

import java.io.Serializable;

public class Hamburguesa implements Serializable {
    public boolean cheddar;
    public boolean panceta;
    public boolean cebolla;
    public boolean muzzarella;
    public boolean tomate;
    public boolean lechuga;
    public boolean huevo;
    public boolean champignon;
    public boolean pepino;
    public boolean barbacoa;
    public boolean ketchup;
    public boolean mayonesa;
    public boolean provoleta;


    public boolean isCheddar() {
        return cheddar;
    }

    public void setCheddar(boolean cheddar) {
        this.cheddar = cheddar;
    }

    public boolean isPanceta() {
        return panceta;
    }

    public void setPanceta(boolean panceta) {
        this.panceta = panceta;
    }

    public boolean isCebolla() {
        return cebolla;
    }

    public void setCebolla(boolean cebolla) {
        this.cebolla = cebolla;
    }

    public boolean isMuzzarella() {
        return muzzarella;
    }

    public void setMuzzarella(boolean muzzarella) {
        this.muzzarella = muzzarella;
    }

    public boolean isTomate() {
        return tomate;
    }

    public void setTomate(boolean tomate) {
        this.tomate = tomate;
    }

    public boolean isLechuga() {
        return lechuga;
    }

    public void setLechuga(boolean lechuga) {
        this.lechuga = lechuga;
    }

    public boolean isHuevo() {
        return huevo;
    }

    public void setHuevo(boolean huevo) {
        this.huevo = huevo;
    }

    public boolean isChampignon() {
        return champignon;
    }

    public void setChampignon(boolean champignon) {
        this.champignon = champignon;
    }

    public boolean isPepino() {
        return pepino;
    }

    public void setPepino(boolean pepino) {
        this.pepino = pepino;
    }

    public boolean isBarbacoa() {
        return barbacoa;
    }

    public void setBarbacoa(boolean barbacoa) {
        this.barbacoa = barbacoa;
    }

    public boolean isKetchup() {
        return ketchup;
    }

    public void setKetchup(boolean ketchup) {
        this.ketchup = ketchup;
    }

    public boolean isMayonesa() {
        return mayonesa;
    }

    public void setMayonesa(boolean mayonesa) {
        this.mayonesa = mayonesa;
    }

    public boolean isProvoleta() {
        return provoleta;
    }

    public void setProvoleta(boolean provoleta) {
        this.provoleta = provoleta;
    }

    public String getCarne() {
        return carne;
    }

    public void setCarne(String carne) {
        this.carne = carne;
    }

    public int getCantMedallones() {
        return cantMedallones;
    }

    public void setCantMedallones(int cantMedallones) {
        this.cantMedallones = cantMedallones;
    }

    public String carne;
    public int cantMedallones;
    public String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public enum MatchBurger {
        Cheeseburger("Cheeseburger", 0),
        BigBurger("BigBurger", 1),
        ProvoBurger("ProvoBurger", 2),
        Veggie("Veggie", 3);


        private String stringValue;
        private int intValue;

        private MatchBurger(String toString, int value) {
            stringValue = toString;
            intValue = value;
        }
    }
}