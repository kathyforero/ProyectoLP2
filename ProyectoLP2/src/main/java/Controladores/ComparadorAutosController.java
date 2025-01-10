package Controladores;

import Bases.*;
import Clases.*;
import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DERS
 */
public class ComparadorAutosController {

    @FXML
    private Button btnRegresar;
    @FXML
    private Label lblUsuario;
    @FXML
    private Label lblPlaca1;
    @FXML
    private Label lblPrecio1;
    @FXML
    private Label lblMarca1;
    @FXML
    private Label lblModelo1;
    @FXML
    private Label lblTipo1;
    @FXML
    private Label lblKilom1;
    @FXML
    private Label lblMotor1;
    @FXML
    private Label lblTrans1;
    @FXML
    private Label lblPeso1;
    @FXML
    private Label lblUbi1;
    @FXML
    private Label lblEstado1;
    @FXML
    private Label lblAño1;
    @FXML
    private ImageView imgViewCar;
    @FXML
    private Label lblPlaca11;
    @FXML
    private Label lblPrecio11;
    @FXML
    private Label lblMarca11;
    @FXML
    private Label lblModelo11;
    @FXML
    private Label lblTipo11;
    @FXML
    private Label lblKilom11;
    @FXML
    private Label lblMotor11;
    @FXML
    private Label lblTrans11;
    @FXML
    private Label lblPeso11;
    @FXML
    private Label lblUbi11;
    @FXML
    private Label lblEstado11;
    @FXML
    private Label lblAño11;
    @FXML
    private ImageView imgViewCar1;
    private Auto auto1;
    private Auto auto2;
    private Usuario usuario;
    DoublyCircularNode<File> foto;

    public void setUsuario(Usuario usuario, Auto a1, Auto a2) {
        this.usuario = usuario;
        this.auto1 = a1;
        this.auto2 = a2;
        lblUsuario.setText(usuario.getNombre() + " " + usuario.getApellido() + "!");
        cargarAuto1(a1);
        cargarAuto2(a2);
    }

    public void cargarAuto1(Auto auto) {
        foto = auto.getFotos().getHeader();
        Image image = new Image(foto.getContent().toURI().toString());
        imgViewCar.setImage(image);
        lblPlaca1.setText(auto.getPlaca());
        lblPrecio1.setText("$ " + Float.toString(auto.getPrecio()));
        lblMarca1.setText(auto.getMarca().getName());
        lblModelo1.setText(auto.getModelo());
        lblTipo1.setText(auto.getTipo().getDisplayName());
        lblAño1.setText(Integer.toString(auto.getAño()));
        lblKilom1.setText(Integer.toString(auto.getKilometraje()) + " km");
        lblMotor1.setText(auto.getMotor().getDisplayName());
        lblTrans1.setText(auto.getTransmisión().getDisplayName());
        lblPeso1.setText(Float.toString(auto.getPeso()) + " kg");
        lblUbi1.setText(auto.getUbicacion().getDisplayName());
        lblEstado1.setText(auto.getEstado().getDisplayName());
    }

    @FXML
    public void abrirReporte1() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vistaReporte.fxml"));
            Parent root = loader.load();
            VistaReporteController vistareportecontroller = loader.getController();
            vistareportecontroller.setUsuario(usuario);
            vistareportecontroller.setAuto(auto1);
            vistareportecontroller.cargarTabla();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.setTitle("GuayacoCar - Autos a tu Alcance");
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarAuto2(Auto auto) {
        foto = auto.getFotos().getHeader();
        Image image = new Image(foto.getContent().toURI().toString());
        imgViewCar1.setImage(image);
        lblPlaca11.setText(auto.getPlaca());
        lblPrecio11.setText("$ " + Float.toString(auto.getPrecio()));
        lblMarca11.setText(auto.getMarca().getName());
        lblModelo11.setText(auto.getModelo());
        lblTipo11.setText(auto.getTipo().getDisplayName());
        lblAño11.setText(Integer.toString(auto.getAño()));
        if (auto.getKilometraje() > 1) {
            lblKilom11.setText(Integer.toString(auto.getKilometraje()) + " km");
        } else {
            lblKilom11.setText(Integer.toString(auto.getKilometraje()) + " km");
        }
        lblMotor11.setText(auto.getMotor().getDisplayName());
        lblTrans11.setText(auto.getTransmisión().getDisplayName());
        if (auto.getPeso() > 1) {
            lblPeso11.setText(Float.toString(auto.getPeso()) + " kg");
        } else {
            lblPeso11.setText(Float.toString(auto.getPeso()) + " kg");
        }
        lblUbi11.setText(auto.getUbicacion().getDisplayName());
        lblEstado11.setText(auto.getEstado().getDisplayName());
    }

    @FXML
    public void abrirReporte2() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vistaReporte.fxml"));
            Parent root = loader.load();
            VistaReporteController vistareportecontroller = loader.getController();
            vistareportecontroller.setUsuario(usuario);
            vistareportecontroller.setAuto(auto2);
            vistareportecontroller.cargarTabla();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.setTitle("GuayacoCar - Autos a tu Alcance");
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAuto1(Auto auto) {
        this.auto1 = auto1;

    }

    public void setAuto2(Auto auto2) {
        this.auto2 = auto2;
    }

    @FXML
    public void regresar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("usuario.fxml"));
            Parent root = loader.load();
            UsuarioController usuarioController = loader.getController();
            usuarioController.setUsuario(usuario);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("GuayacoCar - Autos a tu Alcance");
            stage.show();

            Stage miStage = (Stage) btnRegresar.getScene().getWindow();
            miStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
