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
public class FichaDAO extends Ficha {
    
    private boolean persist;

    public FichaDAO() {
        super();
        persist = false;
    }

    public FichaDAO(int CF, String nombre, String raza, String clase, int FUE, int AGI, int MAG) {
        super(CF, nombre, raza, clase, FUE, AGI, MAG);
        persist = false;
    }

    public FichaDAO(String nombre, String raza, String clase, int FUE, int AGI, int MAG) {
        super(-1, nombre, raza, clase, FUE, AGI, MAG);
        persist = false;
    }

    public FichaDAO(Ficha f) {
        CF = f.CF;
        nombre = f.nombre;
        raza = f.raza;
        clase = f.clase;
        FUE = f.FUE;
        AGI = f.AGI;
        MAG = f.MAG;
    }

    public FichaDAO(int CF) {
        super();
        Connection c = new Connection("localhost", "basededatos", "root", "");
        try {
            java.sql.Connection conn = ConnectionUtil.getConnection();
            String q = "SELECT * FROM ficha WHERE CF=" + CF;
            PreparedStatement ps = conn.prepareStatement(q);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                if (rs.next()) {
                    this.CF = CF;
                    this.nombre = rs.getString("nombre");
                    this.raza = rs.getString("raza");
                    this.clase = rs.getString("clase");
                    this.FUE = rs.getInt("FUE");
                    this.AGI = rs.getInt("AGI");
                    this.MAG = rs.getInt("MAG");
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            Logger.getLogger(FichaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void persist() {
        persist = true;
    }

    public void detach() {
        persist = false;
    }

    @Override
    public void setMAG(int MAG) {
        super.setMAG(MAG);
        if (persist) {
            save();
        }
    }

    @Override
    public void setAGI(int AGI) {
        super.setAGI(AGI);
        if (persist) {
            save();
        }
    }

    @Override
    public void setFUE(int FUE) {
        super.setFUE(FUE);
        if (persist) {
            save();
        }
    }

    @Override
    public void setClase(String clase) {
        super.setClase(clase);
        if (persist) {
            save();
        }
    }

    @Override
    public void setRaza(String raza) {
        super.setRaza(raza);
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

            if (this.CF > 0) {
                // ACTUALIZAR
                String q = "UPDATE ficha SET nombre = ?, raza = ?, clase = ?, FUE = ?, AGI = ?, MAG = ? WHERE CF = ?";
                PreparedStatement ps = csql.prepareStatement(q);
                ps.setString(1, nombre);
                ps.setString(2, raza);
                ps.setString(3, clase);
                ps.setInt(4, FUE);
                ps.setInt(5, AGI);
                ps.setInt(6, MAG);
                ps.setInt(7, CF);
                resultado = ps.executeUpdate();

            } else {
                // INSERTAR
                String q = "INSERT INTO ficha (CF, nombre, raza, clase, FUE, AGI, MAG) VALUES(NULL,?,?,?,?,?,?)";
                PreparedStatement ps = csql.prepareStatement(q, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, nombre);
                ps.setString(2, raza);
                ps.setString(3, clase);
                ps.setInt(4, FUE);
                ps.setInt(5, AGI);
                ps.setInt(6, MAG);
                resultado = ps.executeUpdate();
                try ( ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        resultado = generatedKeys.getInt(1);
                    }
                }
                this.CF = resultado;
            }

        } catch (SQLException ex) {
            Logger.getLogger(FichaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    // Sin parametros de busqueda
    public static List<Ficha> selectAll() {
        return selectAll("");
    }

    // Busqueda por patron
    public static List<Ficha> selectAll(String patron) {
        List<Ficha> resultado = new ArrayList<>();

        try {
            java.sql.Connection conn = ConnectionUtil.getConnection();
            String q = "SELECT * FROM ficha";

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
                    Ficha f = new Ficha();
                    f.setCF(rs.getInt("cf"));
                    f.setNombre(rs.getString("nombre"));
                    f.setRaza(rs.getString("raza"));
                    f.setClase(rs.getString("clase"));
                    f.setFUE(rs.getInt("FUE"));
                    f.setAGI(rs.getInt("AGI"));
                    f.setMAG(rs.getInt("MAG"));
                    resultado.add(f);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            Logger.getLogger(FichaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    public int remove() {
        int resultado = -1;
        if (this.CF > 0) {
            try {
                java.sql.Connection csql = ConnectionUtil.getConnection();
                String q = "DELETE FROM ficha WHERE CF=" + this.CF;

                PreparedStatement ps = csql.prepareStatement(q);
                resultado = ps.executeUpdate();

                if (resultado > 0) {
                    this.CF = -1;
                }
            } catch (SQLException ex) {
                Logger.getLogger(FichaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resultado;
    }
}
