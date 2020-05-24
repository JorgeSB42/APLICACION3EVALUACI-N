package com.mycompany.mavenproject1.model;

/**
 *
 * @author Jorge
 */
public class Jugador {

    protected int CD;
    protected String nombre;
    protected int edad;
    protected String correo;

    public Jugador(int CD, String nombre, int edad, String correo) {
        this.CD = CD;
        this.nombre = nombre;
        this.edad = edad;
        this.correo = correo;
    }

    public Jugador() {
        this(-1, "", -1, "");
    }

    public int getCD() {
        return CD;
    }

    public void setCD(int CD) {
        this.CD = CD;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "Jugador{" + "CD=" + CD + ", nombre=" + nombre + ", edad=" + edad + ", correo=" + correo + '}';
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
        final Jugador other = (Jugador) obj;
        if (this.CD != other.CD) {
            return false;
        }
        return true;
    }

}
