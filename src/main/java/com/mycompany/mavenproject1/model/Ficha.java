package com.mycompany.mavenproject1.model;

/**
 *
 * @author Jorge
 */
public class Ficha {

    protected int CF;
    protected String nombre;
    protected String raza;
    protected String clase;
    protected int FUE;
    protected int AGI;
    protected int MAG;

    public Ficha(int CF, String nombre, String raza, String clase, int FUE, int AGI, int MAG) {
        this.CF = CF;
        this.nombre = nombre;
        this.raza = raza;
        this.clase = clase;
        this.FUE = FUE;
        this.AGI = AGI;
        this.MAG = MAG;
    }

    public Ficha() {
        this(-1, "", "", "", -1, -1, -1);
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

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public int getFUE() {
        return FUE;
    }

    public void setFUE(int FUE) {
        this.FUE = FUE;
    }

    public int getAGI() {
        return AGI;
    }

    public void setAGI(int AGI) {
        this.AGI = AGI;
    }

    public int getMAG() {
        return MAG;
    }

    public void setMAG(int MAG) {
        this.MAG = MAG;
    }

    @Override
    public String toString() {
        return "Ficha{" + "CF=" + CF + ", nombre=" + nombre + ", raza=" + raza + ", clase=" + clase + ", FUE=" + FUE + ", AGI=" + AGI + ", MAG=" + MAG + '}';
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
        final Ficha other = (Ficha) obj;
        if (this.CF != other.CF) {
            return false;
        }
        return true;
    }

}
