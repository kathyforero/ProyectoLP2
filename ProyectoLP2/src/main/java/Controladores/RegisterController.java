package Controladores;

import Clases.*;
import java.io.IOException;
import java.util.Comparator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {

    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApellido;
    @FXML
    private TextField tfCorreo;
    @FXML
    private Button logeo;
    @FXML
    private PasswordField pfContraseña;
    @FXML
    private PasswordField pfConfirmarcontraseña;
    @FXML
    private Button btIniciarSesion;
    @FXML
    private Button btnError;
    @FXML
    private CheckBox cbTerminos;
    private Comparator<String> compString = Sistema.comparadorString();

    /**
     * Initializes the controller class.
     */

    @FXML
    public void crearUsuario() {
        String correo = tfCorreo.getText();
        String contraseña = pfContraseña.getText();
        String confContraseña = pfConfirmarcontraseña.getText();
        String nombre = tfNombre.getText();
        String apellido = tfApellido.getText();
        if (verificarCorreo(correo)) {
            if (verificarContraseña(contraseña, confContraseña)) {
                if (verificarCampos(nombre, apellido)) {
                    if (verificarNombre(nombre)) {
                        if (verificarApellido(apellido)) {
                            Usuario u = Sistema.crearUsuario(nombre, apellido, correo, contraseña);
                            Sistema.guardarUsuario(u);
                            alertaCuentaCreada();
                            System.out.println("REGISTER: usuario creado con existo");
                        }
                    }
                }
            } else {
                pfContraseña.requestFocus();
            }
        } else {
            tfCorreo.requestFocus();
        }
    }

    @FXML
    public void mostrarLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("GuayacoCar - Iniciar Sesión");
            stage.show();
            Stage miStage = (Stage) btIniciarSesion.getScene().getWindow();
            miStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void alertaCuentaCreada() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Cuenta Creada");
        alert.setHeaderText("Excelente, ya eres parte de nuestra comunidad");
        alert.setContentText("Cuenta creada con éxito.");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                mostrarLogin();
            }
        });
    }

    public boolean verificarCampos(String nombre, String apellido) {
        if (compString.compare(nombre, "") == 0) {
            msgError("El nombre no debe estar vacio!");
            tfNombre.requestFocus();
            return false;
        } else if (compString.compare(nombre, "") == 0) {
            msgError("El apellido no debe estar vacio!");
            tfApellido.requestFocus();
            return false;
        } else if (!cbTerminos.isSelected()) {
            msgError("Debes aceptar los términos y condiciones.");
            cbTerminos.requestFocus();
            return false;
        }
        return true;
    }

    public boolean verificarCorreo(String correo) {
        if (!(correo.contains("@")) || correo.length() < 3) {
            msgError("Ingresa un correo válido :( ");
            return false;
        } else if (Sistema.existeUser(correo)) {
            msgError("¡El correo ya está registrado!");
            return false;
        }
        return true;
    }

    public boolean verificarContraseña(String ccntraseña, String confContraseña) {
        if (compString.compare(ccntraseña, confContraseña) != 0) {
            msgError("¡Las contraseñas no son iguales!");
            return false;
        } else if (ccntraseña.length() < 3) {
            msgError("La contraseña debe ser de más de 3 caracteres");
            return false;
        }
        return true;
    }

    public boolean verificarNombre(String nombre) {
        for (char c : nombre.toCharArray()) {
            if (!Character.isLetter(c)) {
                msgError("Ingrese un nombre válido.");
                return false;
            }
        }
        return true;
        // Se intento usar Iteradores para verificar cada letra pero tenia demasiadas
        // complicaciones
    }

    public boolean verificarApellido(String apellido) {
        for (char c : apellido.toCharArray()) {
            if (!Character.isLetter(c)) {
                msgError("Ingrese un apellido válido.");
                return false;
            }
        }
        return true;
        // Se intento usar Iteradores para verificar cada letra pero tenia demasiadas
        // complicaciones
    }

    public void msgError(String msg) {
        btnError.setVisible(true);
        btnError.setText(msg);
    }

}
