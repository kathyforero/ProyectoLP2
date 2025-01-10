package Controladores;

import Clases.Auto;
import Clases.Reporte;
import Clases.Usuario;
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Kathy
 */
public class VistaReporteController {

    @FXML
    private Label lblUser;
    @FXML
    private TableView<Reporte> tvCatDesc;

    private Usuario usuario;

    private Auto auto;
    @FXML
    private TableColumn<Reporte, String> tcCat;
    @FXML
    private TableColumn<Reporte, String> tcDesc;

    private ObservableList<Reporte> reportes = FXCollections.observableArrayList();

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        lblUser.setText(usuario.getNombre() + " " + usuario.getApellido() + "!");
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    public void centrar(TableColumn<Reporte, String> c) {
        c.setCellFactory(column -> {
            return new TableCell<Reporte, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        setText(item);
                        setAlignment(Pos.CENTER);
                    }
                }
            };
        });

    }

    public void cargarTabla() {
        tcCat.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        tcDesc.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        if (auto.getReportes() != null) {
            Iterator<Reporte> it = auto.getReportes().iterator();
            while (it.hasNext()) {
                reportes.add(it.next());
            }
        } else {
            tvCatDesc.getItems().add(new Reporte("No hay datos para mostrar.", "No hay datos para mostrar."));
        }

        if (tvCatDesc != null) {
            tvCatDesc.setItems(reportes);
        }
        centrar(tcCat);
        centrar(tcDesc);
    }
}
