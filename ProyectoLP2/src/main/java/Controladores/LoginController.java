package Controladores;

import Clases.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.io.IOException;
import java.util.Comparator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private Button btnIniciarSesion;

    @FXML
    private PasswordField pfContraseña;

    @FXML
    private TextField tfCorreo;

    @FXML
    private Button btCrearCuenta;
    @FXML
    private Button btnError;
    Comparator<String> compString = Sistema.comparadorString();

    @FXML
    public void iniciarSesion() {
        String correo = tfCorreo.getText();
        String contraseña = pfContraseña.getText();
        if (compString.compare(correo, "") != 0 && correo.contains("@")) {
            if (!(contraseña.isEmpty())) {
                if (!(Sistema.existeUser(correo))) {
                    msgError("Correo no encontrado");
                    System.out.println("LOGIN CONTROLLER: Esa clave(Correo) no existe en el Mapa Usuarios!");
                    tfCorreo.requestFocus();
                } else {
                    if (!Sistema.logearUser(correo, contraseña)) {
                        msgError("Contraseña incorrecta");
                        System.out.println("LOGIN CONTROLLER: Esa no es la contrasenia correcta del Usuarios!");
                        pfContraseña.requestFocus();
                    } else {
                        System.out.println("LOGIN CONTROLLER: Usuario existe en Mapa y la contrasenia es correcta!");
                        Usuario u = Sistema.getUsuario(correo);
                        mostrarGUI(u);
                    }
                }
            } else {
                msgError("Ingrese una contraseña :/");
                System.out.println("LOGIN CONTROLLER: El campo de contrasenia no es valido");
                pfContraseña.requestFocus();
            }
        } else {
            msgError("Ingrese un correo valido");
            System.out.println("LOGIN CONTROLLER: El campo de correo no es valido");
            tfCorreo.requestFocus();
        }
    }

    public void msgError(String msg) {
        btnError.setVisible(true);
        btnError.setText(msg);
    }

    @FXML
    public void mostrarRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("register.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("GuayacoCar - Crear Usuario");
            stage.show();

            Stage miStage = (Stage) btCrearCuenta.getScene().getWindow();
            miStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mostrarGUI(Usuario u) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("usuario.fxml"));
            Parent root = loader.load();
            UsuarioController usuarioController = loader.getController();
            usuarioController.setUsuario(u);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("GuayacoCar - Autos a tu Alcance");
            stage.show();

            Stage miStage = (Stage) btCrearCuenta.getScene().getWindow();
            miStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
