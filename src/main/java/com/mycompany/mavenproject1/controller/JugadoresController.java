package com.mycompany.mavenproject1.controller;

import com.mycompany.mavenproject1.App;
import com.mycompany.mavenproject1.model.Jugador;
import com.mycompany.mavenproject1.model.JugadorDAO;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage; 

/**
 * FXML Controller class
 *
 * @author Jorge
 */
public class JugadoresController implements Initializable {

    @FXML
    private TableView<Jugador> table;

    @FXML
    private TableColumn<Jugador, Integer> cdcolumn;

    @FXML
    private TableColumn<Jugador, String> nombrecolumn;

    @FXML
    private TableColumn<Jugador, Integer> edadcolumn;

    @FXML
    private TableColumn<Jugador, String> correocolumn;
    
    private PrimaryController parent;

    private Object params;

    private Stage myStage;
    
    public void setStage(Stage myStage) {
        this.myStage = myStage;
    }

    public void setParent(PrimaryController p) {
        this.parent = p;
    }

    public void setParams(Object p) {
        params = p;
    }

    private ObservableList<Jugador> data;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.data = FXCollections.observableArrayList();

        List<Jugador> misJugadores = JugadorDAO.selectAll();
        data.addAll(misJugadores);

        this.cdcolumn.setCellValueFactory(eachRowData -> {
            return new SimpleObjectProperty<>(eachRowData.getValue().getCD());
        });

        this.nombrecolumn.setCellValueFactory(eachRowData -> {
            return new SimpleObjectProperty<>(eachRowData.getValue().getNombre());
        });

        this.edadcolumn.setCellValueFactory(eachRowData -> {
            return new SimpleObjectProperty<>(eachRowData.getValue().getEdad());
        });

        this.correocolumn.setCellValueFactory(eachRowData -> {
            return new SimpleObjectProperty<>(eachRowData.getValue().getCorreo());
        });

        nombrecolumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nombrecolumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Jugador, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Jugador, String> t) {

                Jugador selected = (Jugador) t.getTableView().getItems().get(
                        t.getTablePosition().getRow());

                selected.setNombre(t.getNewValue());  //<<- update lista en vista

                JugadorDAO dao = new JugadorDAO(selected); //update en mysql
                dao.save();
            }
        }
        );

        correocolumn.setCellFactory(TextFieldTableCell.forTableColumn());
        correocolumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Jugador, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Jugador, String> t) {

                Jugador selected = (Jugador) t.getTableView().getItems().get(
                        t.getTablePosition().getRow());

                selected.setCorreo(t.getNewValue());  //<<- update lista en vista

                JugadorDAO dao = new JugadorDAO(selected); //update en mysql
                dao.save();
            }
        }
        );
        table.setEditable(true);
        table.setItems(data);

    }

    @FXML
    public void borrarjugador() {
        Jugador selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (!showConfirm(selected.getNombre())) {
                return;
            }
            data.remove(selected);
        } else {
            showWarning("¡!", "Ningún Jugador a borrar", "Seleccione un Jugador antes de pulsar BORRAR");
        }
    }

    public void showWarning(String title, String header, String description) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(description);
        alert.showAndWait();
    }

    public boolean showConfirm(String nombre) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("Borrar");
        alert.setContentText("Desea borrar el Jugador " + nombre);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

    public void openCrearJugador() {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("CrearJugador.fxml"));
        Parent modal;
        try {
            modal = fxmlLoader.load();

            Stage modalStage = new Stage();
            modalStage.setTitle("New Jugador");
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initOwner(App.rootstage); //ojo no existía, hay que crearlo

            Scene modalScene = new Scene(modal);
            modalStage.setScene(modalScene);

            CrearJugadorController modalController = fxmlLoader.getController();
            if (modalController != null) {
                if (modalController != null) {
                    modalController.setStage(modalStage);
                    modalController.setParent(this);
                }
            }

            modalStage.showAndWait();

        } catch (IOException ex) {
            Logger.getLogger(JugadoresController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
