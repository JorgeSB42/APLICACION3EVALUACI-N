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
public class JugadorDAO extends Jugador {

    private boolean persist;

    public JugadorDAO() {
        super();
        persist = false;
    }

    public JugadorDAO(int CD, String nombre, int edad, String correo) {
        super(CD, nombre, edad, correo);
        persist = false;
    }

    public JugadorDAO(String nombre, int edad, String correo) {
        super(-1, nombre, edad, correo);
        persist = false;
    }

    public JugadorDAO(Jugador j) {
        CD = j.CD;
        nombre = j.nombre;
        edad = j.edad;
        correo = j.correo;
    }

    public JugadorDAO(int CD) {
        super();

        try {
            java.sql.Connection conn = ConnectionUtil.getConnection();
            String q = "SELECT * FROM jugador WHERE CD=" + CD;
            PreparedStatement ps = conn.prepareStatement(q);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                if (rs.next()) {
                    this.CD = CD;
                    this.nombre = rs.getString("nombre");
                    this.edad = rs.getInt("edad");
                    this.correo = rs.getString("correo");
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            Logger.getLogger(JugadorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void persist() {
        persist = true;
    }

    public void detach() {
        persist = false;
    }

    @Override
    public void setCorreo(String correo) {
        super.setCorreo(correo);
        if (persist) {
            save();
        }
    }

    @Override
    public void setEdad(int edad) {
        super.setEdad(edad);
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

    public int save() {
        int resultado = -1;

        try {
            java.sql.Connection csql = ConnectionUtil.getConnection();

            if (this.CD > 0) {
                // ACTUALIZAR
                String q = "UPDATE jugador SET nombre = ?, edad = ?, correo = ? WHERE CD = ?";
                PreparedStatement ps = csql.prepareStatement(q);
                ps.setString(1, nombre);
                ps.setInt(2, edad);
                ps.setString(3, correo);
                ps.setInt(4, CD);
                resultado = ps.executeUpdate();

            } else {
                // INSERTAR
                String q = "INSERT INTO jugador (CD, nombre, edad, correo) VALUES(NULL,?,?,?)";
                PreparedStatement ps = csql.prepareStatement(q, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, nombre);
                ps.setInt(2, edad);
                ps.setString(3, correo);
                resultado = ps.executeUpdate();
                try ( ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        resultado = generatedKeys.getInt(1);
                    }
                }
                this.CD = resultado;
            }

        } catch (SQLException ex) {
            Logger.getLogger(JugadorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultado;
    }

    // Sin parametros de busqueda
    public static List<Jugador> selectAll() {
        return selectAll("");
    }

    // Busqueda por patron
    public static List<Jugador> selectAll(String patron) {
        List<Jugador> resultado = new ArrayList<>();

        try {
            java.sql.Connection conn = ConnectionUtil.getConnection();
            String q = "SELECT * FROM jugador";

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
                    Jugador j = new Jugador();
                    j.setCD(rs.getInt("cd"));
                    j.setNombre(rs.getString("nombre"));
                    j.setEdad(rs.getInt("edad"));
                    j.setCorreo(rs.getString("correo"));
                    resultado.add(j);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            Logger.getLogger(JugadorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    public int remove() {
        int resultado = -1;
        if (this.CD > 0) {
            try {
                java.sql.Connection csql = ConnectionUtil.getConnection();
                String q = "DELETE FROM jugador WHERE CD=" + this.CD;

                PreparedStatement ps = csql.prepareStatement(q);
                resultado = ps.executeUpdate();

                if (resultado > 0) {
                    this.CD = -1;
                }
            } catch (SQLException ex) {
                Logger.getLogger(JugadorDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resultado;
    }

}
