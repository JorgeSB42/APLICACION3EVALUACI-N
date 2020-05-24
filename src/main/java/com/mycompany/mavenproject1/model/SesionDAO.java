/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.model;

import com.mycompany.mavenproject1.Utils.ConnectionUtil;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jorge
 */
public class SesionDAO extends Sesion {

    final String borrado = "DELETE FROM sesion WHERE nombre = ?";

    private boolean persist;

    public SesionDAO() {
        super();
        persist = false;
    }

    public SesionDAO(int CS, int CD, int CF, String nombre, int duracion) {
        super(CS, CD, CF, nombre, duracion);
        persist = false;
    }

    public SesionDAO(int CD, int CF, String nombre, int duracion) {
        super(-1, CD, CF, nombre, duracion);
        persist = false;
    }

    public SesionDAO(Sesion s) {
        CS = s.CS;
        CD = s.CD;
        CF = s.CF;
        nombre = s.nombre;
        duracion = s.duracion;

    }

    public SesionDAO(int CS) {
        super();

        try {
            java.sql.Connection conn = ConnectionUtil.getConnection();
            String q = "SELECT * FROM sesion WHERE CS=" + CS;
            PreparedStatement ps = conn.prepareStatement(q);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                if (rs.next()) {
                    this.CS = CS;
                    this.CD = rs.getInt("CD");
                    this.CF = rs.getInt("CF");
                    this.nombre = rs.getString("nombre");
                    this.duracion = rs.getInt("duracion");
                  
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            Logger.getLogger(SesionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void persist() {
        persist = true;
    }

    public void detach() {
        persist = false;
    }

    @Override
    public void setCD(int CD) {
        super.setCD(CD);
        if (persist) {
            save();
        }
    }
    @Override
    public void setCF(int CF) {
        super.setCF(CF);
        if (persist) {
            save();
        }
    }


    @Override
    public void setNombre(String nombre) {
        super.setNombre(nombre);
        if (persist) {
            save();
        }
    }
    @Override
    public void setDuracion(int duracion) {
        super.setDuracion(duracion);
        if (persist) {
            save();
        }
    }

    public int save() {
        int resultado = -1;

        try {
            java.sql.Connection csql = ConnectionUtil.getConnection();

            if (this.CS > 0) {
                // ACTUALIZAR
                String q = "UPDATE sesion SET CD = ?, CF = ?, nombre = ?, duracion = ? WHERE CS = ?";
                PreparedStatement ps = csql.prepareStatement(q);
                ps.setInt(1, CD);
                ps.setInt(2, CF);
                ps.setString(3, nombre);
                ps.setInt(4, duracion);
                ps.setInt(5, CS);
                resultado = ps.executeUpdate();

            } else {
                // INSERTAR
                String q = "INSERT INTO sesion (CS, CD, CF, nombre, duracion) VALUES(NULL,?,?,?,?)";
                PreparedStatement ps = csql.prepareStatement(q, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, CD);
                ps.setInt(2, CF);
                ps.setString(3, nombre);
                ps.setInt(4, duracion);
                resultado = ps.executeUpdate();
                try ( ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        resultado = generatedKeys.getInt(1);
                    }
                }
                this.CS = resultado;
            }

        } catch (SQLException ex) {
            Logger.getLogger(SesionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultado;
    }

    // Sin parametros de busqueda
    public static List<Sesion> selectAll() {
        return selectAll("");
    }

    // Busqueda por patron
    public static List<Sesion> selectAll(String patron) {
        List<Sesion> resultado = new ArrayList<>();

        try {
            java.sql.Connection conn = ConnectionUtil.getConnection();
            String q = "SELECT * FROM sesion";

            if (patron.length() > 0) {
                q += "WHERE nombre LIKE ?";
            }
            PreparedStatement ps = conn.prepareStatement(q);

            if (patron.length() > 0) {
                ps.setString(1, patron + "%");
            }

            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Sesion j = new Sesion();
                    j.setCS(rs.getInt("CS"));
                    j.setCD(rs.getInt("CD"));
                    j.setCF(rs.getInt("CF"));
                    j.setNombre(rs.getString("nombre"));
                    j.setDuracion(rs.getInt("duracion"));
                    
                    resultado.add(j);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            Logger.getLogger(SesionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

   public int remove() {
        int resultado = -1;
        if (this.CS > 0) {
            try {
                java.sql.Connection csql = ConnectionUtil.getConnection();
                String q = "DELETE FROM sesion WHERE CS=" + this.CS;

                PreparedStatement ps = csql.prepareStatement(q);
                resultado = ps.executeUpdate();

                if (resultado > 0) {
                    this.CS = -1;
                }
            } catch (SQLException ex) {
                Logger.getLogger(SesionDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resultado;
    }
}
