package Controladores;

import Bases.DoublyCircularList;
import Bases.DoublyCircularNode;
import Clases.*;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
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
public class VistaAutoController {

    @FXML
    private Button btnRegresar;
    @FXML
    private Button btnReporte;
    @FXML
    private Label lblPlaca;
    @FXML
    private Label lblPrecio;
    @FXML
    private Label lblMarca;
    @FXML
    private Label lblModelo;
    @FXML
    private Label lblTipo;
    @FXML
    private Label lblKilom;
    @FXML
    private Label lblMotor;
    @FXML
    private Label lblTrans;
    @FXML
    private Label lblPeso;
    @FXML
    private Label lblUbi;
    @FXML
    private Label lblEstado;
    @FXML
    private Label lblAño;
    @FXML
    private ImageView btnDerecha;
    @FXML
    private ImageView btnIzquierda;
    @FXML
    private Label lblContador;
    @FXML
    private Label lblUsuario;
    @FXML
    private Label lblVendedor;

    private Usuario usuario;

    private Auto auto;
    @FXML
    private ImageView imgViewCar;

    private DoublyCircularNode<File> Node;

    private DoublyCircularList<File> fotos;
    @FXML
    private ImageView imgAñadirFav;
    @FXML
    private ImageView imgQuitarFav;
    private String procedencia;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        System.out.println("VISTA AUTO: Usuario seteado");
        lblUsuario.setText(usuario.getNombre() + " " + usuario.getApellido() + "!");
        cargarAuto();
        if (verificarFav()) {
            mostrarQuitarFav();
        } else {
            mostrarAñadirFav();
        }
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;

    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    public Auto getAuto() {
        return auto;
    }

    public void cargarAuto() {
        lblPlaca.setText(auto.getPlaca().toUpperCase());
        lblPrecio.setText(Float.toString(auto.getPrecio()));
        lblMarca.setText(auto.getMarca().getName());
        lblModelo.setText(auto.getModelo());
        lblTipo.setText(auto.getTipo().getDisplayName());
        lblAño.setText(Integer.toString(auto.getAño()));
        lblKilom.setText(Integer.toString(auto.getKilometraje()) + " km");
        lblMotor.setText(auto.getMotor().getDisplayName());
        lblTrans.setText(auto.getTransmisión().getDisplayName());
        lblPeso.setText(Float.toString(auto.getPeso()) + " kg");
        lblUbi.setText(auto.getUbicacion().getDisplayName());
        lblEstado.setText(auto.getEstado().getDisplayName());
        lblVendedor.setText(auto.getUsuario().getNombre() + " " + auto.getUsuario().getApellido());
        actualizarImagenes();
        adelanteImagen();
        atrasImagen();
    }

    public void actualizarImagenes() {
        fotos = auto.getFotos();
        if (fotos.size() > 0) {
            Node = fotos.getHeader();
            Image image = new Image(Node.getContent().toURI().toString());
            imgViewCar.setImage(image);
            actualizarContador();
        } else {
            String rutaRelativa = "/Controladores/preview.png";
            String rutaCompleta = getClass().getResource(rutaRelativa).toExternalForm();
            Image image = new Image(rutaCompleta);
            imgViewCar.setImage(image);
            actualizarContador();

        }
    }

    @FXML
    public void adelanteImagen() {
        Node = Node.getNext();
        Image image = new Image(Node.getContent().toURI().toString());
        imgViewCar.setImage(image);
        actualizarContador();
    }

    @FXML
    public void atrasImagen() {
        Node = Node.getPrevious();
        Image image = new Image(Node.getContent().toURI().toString());
        imgViewCar.setImage(image);
        actualizarContador();
    }

    public void actualizarContador() {
        if (fotos.size() > 0) {
            int index = fotos.getIndex(Node);
            lblContador.setText((index + 1) + "/" + fotos.size());
        } else {
            lblContador.setText("0/0");
        }
    }

    @FXML
    public void añadirFav() {
        usuario.addFavorito(auto);
        System.out.println("Se agrego a auto a favoritos ");
        mostrarQuitarFav();
        Sistema.actualizarUsuario(usuario);
    }

    @FXML
    public void quitarFav() {
        if (eliminarAutoFavorito()) {
            System.out.println("Se elimino a auto a favoritos ");
        }
        mostrarAñadirFav();
        Sistema.actualizarUsuario(usuario);

    }

    public boolean eliminarAutoFavorito() {
        try {
            DoublyCircularList<Auto> fav = usuario.getFavoritos();
            if (fav.size() > 0) {
                DoublyCircularNode<Auto> a = fav.getHeader();
                boolean bandera = true;
                do {
                    Auto au = a.getContent();
                    if (Sistema.comparadorString().compare(au.getPlaca().toLowerCase(),
                            auto.getPlaca().toLowerCase()) == 0) {
                        fav.removeNode(a);
                        bandera = false;
                    } else {
                        a = a.getNext();
                    }
                } while (bandera && a != fav.getHeader());
            }
            usuario.setFavorito(fav);

            return true;
        } catch (Exception e) {
            System.err.println("ERROR AL ELIMINAR AUTO DE FAVORITO!!! " + e.getMessage());
            return false;
        }
    }

    public boolean verificarFav() {
        DoublyCircularList<Auto> favoritos = usuario.getFavoritos();
        if (favoritos.size() > 0) {
            Iterator<Auto> it = favoritos.iterator();
            while (it.hasNext()) {
                Auto autoNodo = it.next();
                if (esFavorito(autoNodo)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean esFavorito(Auto p) {
        return /*
                * Double.compare(auto.getPrecio(), p.getPrecio()) == 0 &&
                * auto.getAño() == p.getAño() &&
                * auto.getKilometraje() == p.getKilometraje() &&
                * Double.compare(auto.getPeso(), p.getPeso()) == 0 &&
                * Objects.equals(auto.getMarca().getName(), p.getMarca().getName()) &&
                * Objects.equals(auto.getModelo(), p.getModelo()) &&
                * Objects.equals(auto.getTipo().getDisplayName(),p.getTipo().getDisplayName())
                * &&
                */
        Sistema.comparadorString().compare(auto.getPlaca().toLowerCase(), p.getPlaca().toLowerCase()) == 0;
        /*
         * &&
         * Objects.equals(auto.getMotor().getDisplayName(),p.getMotor().getDisplayName()
         * ) &&
         * Objects.equals(auto.getTransmisión().getDisplayName(),p.getTransmisión().
         * getDisplayName()) &&
         * Objects.equals(auto.getUbicacion().getDisplayName(),p.getUbicacion().
         * getDisplayName()) &&
         * Objects.equals(auto.getEstado().getDisplayName(),p.getEstado().getDisplayName
         * ()) ;
         * //Objects.equals(auto.getUsuario(),this.usuario);
         */
    }

    public void mostrarAñadirFav() {
        // OCULTAR QUITAR MOSTRAR ANIADIR
        imgAñadirFav.setVisible(true);
        imgAñadirFav.setDisable(false);
        imgQuitarFav.setVisible(false);
        imgQuitarFav.setDisable(true);
    }

    public void mostrarQuitarFav() {
        // OCULTAR ANIADIR MOSTRAR QUITAR
        imgAñadirFav.setVisible(false);
        imgAñadirFav.setDisable(true);
        imgQuitarFav.setVisible(true);
        imgQuitarFav.setDisable(false);
    }

    @FXML
    public void abrirReporte() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vistaReporte.fxml"));
            Parent root = loader.load();
            VistaReporteController vistareportecontroller = loader.getController();
            vistareportecontroller.setUsuario(usuario);
            vistareportecontroller.setAuto(auto);
            vistareportecontroller.cargarTabla();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.setTitle("GuayacoCar - Autos a tu Alcance");
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void regresar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(procedencia));
            Parent root = loader.load();
            if (Sistema.comparadorString().compare(procedencia.toLowerCase(), "usuario.fxml".toLowerCase()) == 0) {
                UsuarioController Controller = loader.getController();
                Controller.setUsuario(usuario);
            } else {
                FavoritosController Controller = loader.getController();
                Controller.setUsuario(usuario);
            }

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
