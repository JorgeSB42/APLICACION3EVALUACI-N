/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.model;

import java.util.Objects;

/**
 *
 * @author Jorge
 */
public class Sesion {
    protected int CS;
    protected int CD;
    protected int CF;
    protected String nombre;
    protected int duracion;

    public Sesion(int CS, int CD, int CF, String nombre, int duracion) {
        this.CS = CS;
        this.CD = CD;
        this.CF = CF;
        this.nombre = nombre;
        this.duracion = duracion;
    }

   

    public Sesion() {
        this(-1,-1,-1,"",-1);
    }

    public int getCS() {
        return CS;
    }

    public void setCS(int CS) {
        this.CS = CS;
    }

    public int getCD() {
        return CD;
    }

    public void setCD(int CD) {
        this.CD = CD;
    }

    public int getCF() {
        return CF;
    }

    public void setCF(int CF) {
        this.CF = CF;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    @Override
    public String toString() {
        return "Sesion{" + "CS=" + CS + ", CD=" + CD + ", CF=" + CF + ", nombre=" + nombre + ", duracion=" + duracion + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sesion other = (Sesion) obj;
        if (this.CS != other.CS) {
            return false;
        }
        return true;
    }

    

   
}
