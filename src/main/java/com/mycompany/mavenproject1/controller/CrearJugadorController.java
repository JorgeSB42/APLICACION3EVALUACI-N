package com.mycompany.mavenproject1.controller;

import com.mycompany.mavenproject1.model.Jugador;
import com.mycompany.mavenproject1.model.JugadorDAO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jorge
 */
public class CrearJugadorController implements Initializable {

    @FXML
    private TextField nombre;

    @FXML
    private TextField edad;

    @FXML
    private TextField correo;

    private JugadoresController parent;

    private Object params;

    private Stage myStage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setStage(Stage myStage) {
        this.myStage = myStage;
    }

    public void setParent(JugadoresController p) {
        this.parent = p;
    }

    public void setParams(Object p) {
        params = p;
    }

    @FXML
    private void anadir() {
        String Nombre = this.nombre.getText();
        String Correo = this.correo.getText();
        String Edad = this.edad.getText();

        if (Nombre.trim().length() > 0 && Correo.trim().length() > 0 && Edad.trim().length() > 0) {
            Jugador newJugador = new Jugador();
            newJugador.setNombre(Nombre);
            newJugador.setCorreo(Correo);
            int Eddad = Integer.parseInt(Edad);
            newJugador.setEdad(Eddad);

            JugadorDAO J = new JugadorDAO(newJugador);
            int CD = J.save();

            newJugador.setCD(CD);

            if (this.myStage != null) {
                this.myStage.close();
            }
        } else {
            if (parent != null) {
                parent.showWarning("Error de validación", "Corrija los errores", "Debe contener información");
            }
        }

    }

    @FXML
    private void cancelar() {
        if (this.myStage != null) {
            this.myStage.close();
        }
    }
}
