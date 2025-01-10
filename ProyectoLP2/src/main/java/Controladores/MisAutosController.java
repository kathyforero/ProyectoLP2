package Controladores;

import Clases.*;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import Bases.DoublyCircularList;
import Bases.DoublyCircularNode;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Comparator;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
public class MisAutosController {

    @FXML
    private Button btnRegresar;

    @FXML
    private Label lblUser;

    private Usuario usuario;
    private DoublyCircularList<Auto> autos = Archivos.leerAutos();
    private DoublyCircularList<Auto> autosDueño;
    private DoublyCircularNode<Auto> autoDNodo;
    private DoublyCircularNode<File> foto;
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
    private Label lblNoHay;
    @FXML
    private ImageView mostrarAutosAtras;
    @FXML
    private Button btnError;

    private Comparator<String> compString = Sistema.comparadorString();

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        lblUser.setText(usuario.getNombre() + " " + usuario.getApellido() + "!");
        settearAutosD();
        if (autosDueño.size() > 0) {
            autoDNodo = autosDueño.getHeader();
        }
        cargarAutos();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setAutos(DoublyCircularList<Auto> autos) {
        this.autos = autos;
    }

    public DoublyCircularList<Auto> getAutos() {
        return autos;
    }

    public void settearAutosD() {
        if (autos != null) {
            autosDueño = new DoublyCircularList<>();
            if (autos.size() >= 1) {
                DoublyCircularNode<Auto> n = autos.getHeader();
                do {
                    if (compString.compare(n.getContent().getUsuario().getNombre(), this.usuario.getNombre()) == 0
                            && compString.compare(n.getContent().getUsuario().getApellido(),
                                    this.usuario.getApellido()) == 0
                            && compString.compare(n.getContent().getUsuario().getCorreo(),
                                    this.usuario.getCorreo()) == 0
                            && compString.compare(n.getContent().getUsuario().getContraseña(),
                                    this.usuario.getContraseña()) == 0) {
                        autosDueño.addLast(n.getContent());
                    }
                    n = n.getNext();
                } while (n != autos.getHeader());
            }
        }
    }    
    
    public void cargarAutos(){
        
        if (autosDueño.size()>0){
            mostrarAutosAdelante.setVisible(true);
            mostrarAutosAtras.setVisible(true);
            System.out.println("USUARIO CONTROLLER: Hay "+autos.size()+" autos en la DCLL actual");
            autoDNodo=autosDueño.getHeader();
            mostrarAutosAdelante();
            if (autosDueño.size() < 7) {
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
                imgView.setDisable(false);
                imgView.setImage(image);
                imgView.setOpacity(1);
                imgView.setOnMouseClicked(event -> mostrarEditor(auto));

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

        } while (autoDNodo != autosDueño.getHeader() && index <= 6);

        if (compString.compare(autoDNodo.getContent().getPlaca(),
                autosDueño.getHeader().getContent().getPlaca()) == 0) {
            ponerBlanco(index);

        }

    }

    @FXML
    public void mostrarAutosAtras() {

        int indicePRB = 0;
        if (autosDueño.getIndex(autoDNodo) == 6 && (autosDueño.size() % 6) != 0) {
            int indice = autosDueño.size() - (autosDueño.size() % 6);
            autoDNodo = autosDueño.getNodo(indice); // Asegurar que el índice sea válido en la lista circular
        } else if (compString.compare(autoDNodo.getContent().getPlaca(),
                autosDueño.getHeader().getContent().getPlaca()) == 0) {
            if (autosDueño.size() % 6 == 0) {
                indicePRB = 6 + 6;
            } else {
                indicePRB = 6 + (autosDueño.size() % 6);
            }
        } else {
            indicePRB = 12;
            System.out.println(autosDueño.getIndex(autoDNodo));
        }
        for (int i = 1; i <= indicePRB; i++) {
            autoDNodo = autoDNodo.getPrevious();
        }
        mostrarAutosAdelante();

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

    public void mostrarEditor(Auto auto) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("editarauto.fxml"));
            Parent root = loader.load();
            EditarAutoController EditarautoController = loader.getController();
            EditarautoController.setAuto(auto);
            EditarautoController.setUsuario(usuario);
            EditarautoController.setPlacaPredet(auto);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("GuayacoCar - Editar Auto");
            stage.show();

            Stage miStage = (Stage) imgAuto1.getScene().getWindow();
            miStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void alertaInfo() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText("¿Como editar o remover un vehículo?");
        alert.setContentText("Presiona un auto para poder editar sus características o eliminarlo!");
        alert.showAndWait();
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