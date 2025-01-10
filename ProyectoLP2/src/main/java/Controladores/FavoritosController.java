package Controladores;

import Clases.*;
import Bases.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Comparator;
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
public class FavoritosController {

    @FXML
    private Label lblUser;
    @FXML
    private Button btnRegresar;
    @FXML
    private Button btnError;
    @FXML
    private ImageView imgAuto1;
    @FXML
    private Label tituloAuto1;
    @FXML
    private Label anioAuto1;
    @FXML
    private Label kmAutos1;
    @FXML
    private Label precioAuto1;
    @FXML
    private ImageView imgAuto2;
    @FXML
    private Label tituloAuto2;
    @FXML
    private Label precioAuto2;
    @FXML
    private Label precioAuto3;
    @FXML
    private ImageView imgAuto3;
    @FXML
    private Label provAuto1;
    @FXML
    private Label tituloAuto3;
    @FXML
    private Label anioAuto2;
    @FXML
    private Label kmAutos2;
    @FXML
    private Label anioAuto3;
    @FXML
    private Label kmAutos3;
    @FXML
    private Label provAuto2;
    @FXML
    private Label provAuto3;
    @FXML
    private ImageView imgAuto4;
    @FXML
    private Label tituloAuto4;
    @FXML
    private Label anioAuto4;
    @FXML
    private Label kmAutos4;
    @FXML
    private Label precioAuto4;
    @FXML
    private ImageView imgAuto5;
    @FXML
    private Label tituloAuto5;
    @FXML
    private Label precioAuto5;
    @FXML
    private Label precioAuto6;
    @FXML
    private ImageView imgAuto6;
    @FXML
    private Label provAuto4;
    @FXML
    private Label tituloAuto6;
    @FXML
    private Label anioAuto5;
    @FXML
    private Label kmAutos5;
    @FXML
    private Label anioAuto6;
    @FXML
    private Label kmAutos6;
    @FXML
    private Label provAuto5;
    @FXML
    private Label provAuto6;
    @FXML
    private ImageView mostrarAutosAdelante;
    @FXML
    private ImageView mostrarAutosAtras;

    private Usuario usuario;
    private DoublyCircularList<Auto> autos = Archivos.leerAutos();
    private DoublyCircularList<Auto> favoritos;
    private DoublyCircularNode<Auto> autoDNodo;
    private DoublyCircularNode<File> foto;
    private Comparator<String> compString = Sistema.comparadorString();

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        lblUser.setText(usuario.getNombre() + " " + usuario.getApellido() + "!");
        favoritos = usuario.getFavoritos();
        cargarAutos();
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

    @FXML
    public void mostrarAutosAdelante() {

        int index = 1;
        do {
            Auto auto = autoDNodo.getContent();
            try {
                // Obtener y configurar ImageView
                Field imgField = getClass().getDeclaredField("imgAuto" + index);
                imgField.setAccessible(true);
                ImageView imgView = (ImageView) imgField.get(this);
                foto = auto.getFotos().getHeader();
                Image image = new Image(foto.getContent().toURI().toString());
                imgView.setImage(image);
                imgView.setDisable(false);
                imgView.setOpacity(1);
                imgView.setOnMouseClicked(event -> mostrarAuto(auto));

                Field FtituloAuto = getClass().getDeclaredField("tituloAuto" + index);
                FtituloAuto.setAccessible(true);
                Label tituloAuto = (Label) FtituloAuto.get(this);
                tituloAuto.setText(auto.getMarca().getName() + " - " + auto.getModelo());
                tituloAuto.setOpacity(1);

                Field FanioAuto1 = getClass().getDeclaredField("anioAuto" + index);
                FanioAuto1.setAccessible(true);
                Label anioAuto1 = (Label) FanioAuto1.get(this);
                anioAuto1.setText(Integer.toString(auto.getAño()) + " •");
                anioAuto1.setOpacity(1);

                Field FkmAutos = getClass().getDeclaredField("kmAutos" + index);
                FkmAutos.setAccessible(true);
                Label kmAutos = (Label) FkmAutos.get(this);
                kmAutos.setText(Integer.toString(auto.getKilometraje()) + " km");
                kmAutos.setOpacity(1);

                Field FprovAuto = getClass().getDeclaredField("provAuto" + index);
                FprovAuto.setAccessible(true);
                Label provAuto = (Label) FprovAuto.get(this);
                provAuto.setText(auto.getUbicacion().getDisplayName());
                provAuto.setOpacity(1);

                Field FprecioAuto1 = getClass().getDeclaredField("precioAuto" + index);
                FprecioAuto1.setAccessible(true);
                Label precioAuto1 = (Label) FprecioAuto1.get(this);
                precioAuto1.setText("$" + auto.getPrecio());
                precioAuto1.setOpacity(1);

            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            index++;
            autoDNodo = autoDNodo.getNext();

        } while (autoDNodo != favoritos.getHeader() && index <= 6);
        if (compString.compare(autoDNodo.getContent().getPlaca(), favoritos.getHeader().getContent().getPlaca()) == 0) {
            ponerBlanco(index);
        }

    }

    public void mostrarAuto(Auto auto) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vistaauto.fxml"));
            Parent root = loader.load();
            VistaAutoController VistaautoController = loader.getController();
            VistaautoController.setAuto(auto);
            VistaautoController.setProcedencia("favoritos.fxml");
            VistaautoController.setUsuario(usuario);
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

    @FXML
    public void mostrarAutosAtras() {

        int indicePRB = 0;
        if (favoritos.getIndex(autoDNodo) == 6 && (favoritos.size() % 6) != 0) {
            int indice = favoritos.size() - (favoritos.size() % 6);
            autoDNodo = favoritos.getNodo(indice); // Asegurar que el índice sea válido en la lista circular
        } else if (compString.compare(autoDNodo.getContent().getPlaca(),
                favoritos.getHeader().getContent().getPlaca()) == 0) {
            if (favoritos.size() % 6 == 0) {
                indicePRB = 6 + 6;
            } else {
                indicePRB = 6 + (favoritos.size() % 6);
            }
        } else {
            indicePRB = 12;
        }
        for (int i = 1; i <= indicePRB; i++) {
            autoDNodo = autoDNodo.getPrevious();
        }
        mostrarAutosAdelante();

    }

    public void cargarAutos() {

        if (favoritos.size() > 0) {
            mostrarAutosAdelante.setVisible(true);
            mostrarAutosAtras.setVisible(true);
            System.out.println("USUARIO CONTROLLER: Hay " + autos.size() + " autos en la DCLL actual");
            autoDNodo = favoritos.getHeader();
            mostrarAutosAdelante();
            if (favoritos.size() < 7) {
                mostrarAutosAdelante.setVisible(false);
                mostrarAutosAtras.setVisible(false);
            } else {
                mostrarAutosAdelante.setVisible(true);
                mostrarAutosAtras.setVisible(true);
            }

            msgErrorOff();
        } else {
            mostrarAutosAdelante.setVisible(false);
            mostrarAutosAtras.setVisible(false);
            ponerBlanco(1);
            msgError("No hay autos que mostrar");
            System.out.println("USUARIO CONTROLLER: no hay autos en la lista actual");
        }
    }

    public void msgError(String msg) {
        btnError.setVisible(true);
        btnError.setText(msg);
    }

    public void msgErrorOff() {
        btnError.setVisible(false);
    }

    public void ponerBlanco(int index) {

        while (index <= 6) {
            try {
                Field imgField = getClass().getDeclaredField("imgAuto" + index);
                imgField.setAccessible(true);
                ImageView imgView = (ImageView) imgField.get(this);
                imgView.setOpacity(0);
                imgView.setDisable(true);

                Field FtituloAuto = getClass().getDeclaredField("tituloAuto" + index);
                FtituloAuto.setAccessible(true);
                Label tituloAuto = (Label) FtituloAuto.get(this);
                tituloAuto.setOpacity(0);

                Field FkmAutos = getClass().getDeclaredField("kmAutos" + index);
                FkmAutos.setAccessible(true);
                Label kmAutos = (Label) FkmAutos.get(this);
                kmAutos.setOpacity(0);

                Field FprovAuto = getClass().getDeclaredField("provAuto" + index);
                FprovAuto.setAccessible(true);
                Label provAuto = (Label) FprovAuto.get(this);
                provAuto.setOpacity(0);

                Field FanioAuto1 = getClass().getDeclaredField("anioAuto" + index);
                FanioAuto1.setAccessible(true);
                Label anioAuto1 = (Label) FanioAuto1.get(this);
                anioAuto1.setOpacity(0);

                Field FprecioAuto1 = getClass().getDeclaredField("precioAuto" + index);
                FprecioAuto1.setAccessible(true);
                Label precioAuto1 = (Label) FprecioAuto1.get(this);
                precioAuto1.setOpacity(0);

            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            index++;
        }
    }
}
