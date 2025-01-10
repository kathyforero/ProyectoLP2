package Controladores;

import Clases.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import Bases.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class EditarAutoController {

    @FXML
    private Label lblUser;
    @FXML
    private TextField tfPlaca;
    @FXML
    private TextField tfPrecio;
    @FXML
    private ComboBox<String> cbMarca;
    @FXML
    private ComboBox<String> cbModelo;
    @FXML
    private ComboBox<String> cbTipo;
    @FXML
    private TextField tfAnio;
    @FXML
    private TextField tfKM;
    @FXML
    private ComboBox<String> cbMotor;
    @FXML
    private ComboBox<String> cbTransmision;
    @FXML
    private TextField tfPeso;
    @FXML
    private ComboBox<String> cbUbicacion;
    @FXML
    private ComboBox<String> cbEstado;
    @FXML
    private Button btonGuardar;
    @FXML
    private Button btnError;
    @FXML
    private Button btnSubirImagen;
    @FXML
    private Button btnRegresar;
    @FXML
    private ImageView ivAdelante;
    @FXML
    private ImageView ivAtras;
    @FXML
    private Label lblImagenes;
    @FXML
    private ImageView ivFotoAuto;
    @FXML
    private ImageView ivQuitarImagen;
    private Usuario usuario;
    private Auto auto;
    private DoublyCircularList<File> fotos = new DoublyCircularList<>();
    private DoublyCircularNode<File> Node;
    private DoublyCircularList<Auto> autos = Archivos.leerAutos();
    private String placaPredet;
    private ArrayList<Reporte> reportes;
    private Comparator<String> compString = Sistema.comparadorString();

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        lblUser.setText(usuario.getNombre() + " " + usuario.getApellido() + "!");
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
        fotos = auto.getFotos();
        Node = fotos.getHeader();
        cargarCampos();
        actualizarImagenes();
    }

    public ArrayList<Reporte> getReportes() {
        return reportes;
    }

    public void setReportes(ArrayList<Reporte> reportes) {
        this.reportes = reportes;
    }

    public void setPlacaPredet(Auto auto) {
        this.placaPredet = auto.getPlaca();
    }

    @FXML
    public void regresar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("misautos.fxml"));
            Parent root = loader.load();
            MisAutosController MisautosController = loader.getController();
            MisautosController.setUsuario(usuario);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("GuayacoCar - Mis Autos");
            stage.show();

            Stage miStage = (Stage) btnRegresar.getScene().getWindow();
            miStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void adelanteImagen() {
        Node = Node.getNext();
        Image image = new Image(Node.getContent().toURI().toString());
        ivFotoAuto.setImage(image);
        actualizarContador();
    }

    @FXML
    public void atrasImagen() {
        Node = Node.getPrevious();
        Image image = new Image(Node.getContent().toURI().toString());
        ivFotoAuto.setImage(image);
        actualizarContador();
    }

    @FXML
    public void subirImagen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar fotos");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.gif", "*.jpeg"));
        java.util.List<File> selectedFiles = fileChooser.showOpenMultipleDialog(btnSubirImagen.getScene().getWindow());

        if (selectedFiles != null) {
            for (File file : selectedFiles) {
                System.out.println("Archivo seleccionado: " + file.getAbsolutePath());

                File destDir = new File("src/main/resources/imagenes");
                if (!destDir.exists()) {
                    destDir.mkdirs();
                }

                File destFile = new File(destDir, file.getName());
                try {
                    Files.copy(file.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                fotos.addLast(destFile);
                Image image = new Image(file.toURI().toString());
            }
        }

        if (fotos.size() > 0) {
            actualizarImagenes();
        }
    }

    @FXML
    public void eliminarFoto() {
        fotos.removeNode(Node);
        actualizarImagenes();
    }

    public void msgError(String msg) {
        btnError.setVisible(true);
        btnError.setText(msg);
    }

    public void msgErrorOff() {
        btnError.setVisible(false);
    }

    public void actualizarImagenes() {
        if (fotos.size() > 0) {
            Node = fotos.getHeader();
            Image image = new Image(Node.getContent().toURI().toString());
            ivFotoAuto.setImage(image);
            actualizarContador();
        } else {
            String rutaRelativa = "/Controladores/preview.png";
            String rutaCompleta = getClass().getResource(rutaRelativa).toExternalForm();
            Image image = new Image(rutaCompleta);
            ivFotoAuto.setImage(image);
            actualizarContador();

        }
    }

    public void actualizarContador() {
        if (fotos.size() > 0) {
            int index = fotos.getIndex(Node);
            lblImagenes.setText((index + 1) + "/" + fotos.size());
        } else {
            lblImagenes.setText("0/0");
        }
    }

    public void cargarCampos() {
        cargarTipos();
        cargarMarca();
        cargarMotor();
        cargarModeloInicio();
        cargarTransmision();
        cargarUbicacion();
        cargarEstado();
        cargarPlaca();
        cargarPrecio();
        cargarAnio();
        cargarKilometraje();
        cargarPeso();
    }

    public void cargarTipos() {
        Iterator<String> iterator = Tipo.iterator();
        while (iterator.hasNext()) {
            cbTipo.getItems().add(iterator.next());
        }
        cbTipo.setValue(auto.getTipo().getDisplayName());
    }

    public void cargarMarca() {
        Iterator<String> iterator = MarcaDeAuto.iterator();
        while (iterator.hasNext()) {
            cbMarca.getItems().add(iterator.next());
        }
        cbMarca.setValue(auto.getMarca().getName());
    }

    public void cargarModelo() {
        msgErrorOff();
        cbModelo.getSelectionModel().clearSelection();
        cbModelo.getItems().clear();
        cbModelo.setValue(null);
        String txtMarca = cbMarca.getValue();
        // esto lo debo hacer con iterator
        Iterator<MarcaDeAuto> iterator = MarcaDeAuto.iteratorMarcaDeAuto();
        while (iterator.hasNext()) {
            MarcaDeAuto marca = iterator.next();
            if (Sistema.comparadorString().compare(marca.getName(), txtMarca) == 0) {
                ArrayList<String> modelos = marca.getModels();
                for (int i = 1; i <= modelos.size(); i++) {
                    cbModelo.getItems().add(modelos.get(i));
                }
            }
        }
    }

    public void cargarModeloInicio() {
        String txtMarca = cbMarca.getValue();
        // esto lo debo hacer con iterator
        Iterator<MarcaDeAuto> iterator = MarcaDeAuto.iteratorMarcaDeAuto();
        while (iterator.hasNext()) {
            MarcaDeAuto marca = iterator.next();
            if (Sistema.comparadorString().compare(marca.getName(), txtMarca) == 0) {
                ArrayList<String> modelos = marca.getModels();
                for (int i = 1; i <= modelos.size(); i++) {
                    cbModelo.getItems().add(modelos.get(i));
                }
            }
        }
        cbModelo.setValue(auto.getModelo());
    }

    public void cargarMotor() {
        Iterator<String> iterator = Motor.iterator();
        while (iterator.hasNext()) {
            cbMotor.getItems().add(iterator.next());
        }
        cbMotor.setValue(auto.getMotor().getDisplayName());
    }

    public void cargarTransmision() {
        Iterator<String> iterator = Transmision.iterator();
        while (iterator.hasNext()) {
            cbTransmision.getItems().add(iterator.next());
        }
        cbTransmision.setValue(auto.getTransmisión().getDisplayName());
    }

    public void cargarUbicacion() {
        Iterator<String> iterator = Ubicacion.iterator();
        while (iterator.hasNext()) {
            cbUbicacion.getItems().add(iterator.next());
        }
        cbUbicacion.setValue(auto.getUbicacion().getDisplayName());
    }

    public void cargarEstado() {
        Iterator<String> iterator = Estado.iterator();
        while (iterator.hasNext()) {
            cbEstado.getItems().add(iterator.next());
        }
        cbEstado.setValue(auto.getEstado().getDisplayName());

    }

    public void cargarPlaca() {
        tfPlaca.setText(auto.getPlaca());
    }

    public void cargarPrecio() {
        tfPrecio.setText("" + auto.getPrecio());
    }

    public void cargarAnio() {
        tfAnio.setText("" + auto.getAño());
    }

    public void cargarKilometraje() {
        tfKM.setText("" + auto.getKilometraje());
    }

    public void cargarPeso() {
        tfPeso.setText("" + auto.getPeso());
    }

    @FXML
    public void verificarModelo() {
        if (cbMarca.getValue() == null) {
            msgError("Primero tienes que escoger una marca.");
        }
    }
    
    // EN ALGUNAS VERIFICACIONES SE USA UN FOR-EACH, NO SE USA ITERADOR YA QUE SE USA UN char[] (Char Array), EL CUAL NO IMPLEMENTE ITERABLE Y NO SE PUEDE ITERAR CON LA CLASE ITERATOR QUE PROPORCIONA JAVA.

    public boolean verificarPlaca() {
        String placa = tfPlaca.getText();
        int numLetras = 0;
        int numNum = 0;
        if (placa != null) {
            for (char c : placa.toCharArray()) {
                if (Character.isLetter(c)) {
                    numLetras++;
                } else if (Character.isDigit(c)) {
                    numNum++;
                }
            }
        }
        if (numLetras != 3 || numNum < 3 || numNum > 4 || placa == null) {
            msgError("Ingrese una placa válida.");
            return false;
        } else {
            msgErrorOff();
            return true;
        }
    }

    public boolean verificarPlacaExistente() {
        String placa = tfPlaca.getText();
        Iterator<Auto> it = autos.iterator();
        while (it.hasNext()) {
            Auto autoit = it.next();
            System.out.println("Tf :" + placa.toLowerCase());
            System.out.println("It: " + autoit.getPlaca().toLowerCase());
            System.out.println("PD: " + placaPredet.toLowerCase());
            System.out.println("");
            if (compString.compare(autoit.getPlaca().toLowerCase(), placa.toLowerCase()) == 0
                    && compString.compare(this.placaPredet.toLowerCase(), autoit.getPlaca().toLowerCase()) != 0) {
                msgError("Esa placa ya existe!! Ingrese una placa válida.");
                return false;
            }
        }
        return true;
    }

    public boolean verificarAño() {
        String año = tfAnio.getText();
        int numNum = 0;
        int añoActual = LocalDate.now().getYear();
        if (año != null) {
            for (char c : año.toCharArray()) {
                if (Character.isLetter(c)) {
                    msgError("Ingrese un año válido.");
                    return false;
                } else if (Character.isDigit(c)) {
                    numNum++;
                    continue;
                }
            }
        }
        if (numNum != 4 || año == null || Integer.valueOf(año) < 1950 || Integer.valueOf(año) > añoActual + 1) {
            msgError("Ingrese un año válido.");
            return false;
        } else {
            return true;
        }
    }

    public boolean verificarKilometraje() {
        String kilometraje = tfKM.getText();
        if (kilometraje != null) {
            for (char c : kilometraje.toCharArray()) {
                if (Character.isDigit(c)) {
                    continue;
                } else {
                    msgError("Ingrese un kilometraje válido.");
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    public boolean verificarNuevo() {
        String km = tfKM.getText();
        Float kmF = Float.valueOf(km);
        System.out.println(cbEstado.getValue());
        System.out.println(kmF);
        if (compString.compare(cbEstado.getValue().toLowerCase(), "Nuevo".toLowerCase()) == 0 && kmF > 150) {
            msgError("Un auto nuevo no puede tener esa cantidad de kilometraje!!");
            return false;
        }
        return true;
    }

    public boolean verificarPeso() {
        String peso = tfPeso.getText();
        if (peso != null) {
            try {
                Float.parseFloat(peso);
            } catch (NumberFormatException e) {
                msgError("Ingrese un peso válido.");
                return false;
            }
        } else {
            msgError("Ingrese un peso válido.");
            return false;
        }
        return true;
    }

    public boolean verificarPrecio() {
        String precio = tfPrecio.getText();
        if (precio != null) {
            try {
                Float.parseFloat(precio);
            } catch (NumberFormatException e) {
                msgError("Ingrese un precio válido.");
                return false;
            }
        } else {
            msgError("Ingrese un precio válido.");
            return false;
        }
        return true;
    }

    public boolean verificarImagen() {
        if (fotos.size() == 0) {
            msgError("Ingrese al menos una imagen!");
            return false;
        } else {
            return true;
        }
    }

    public boolean verificarMarca() {
        if (cbMarca.getValue() == null) {
            msgError("Seleccione una marca.");
            return false;
        } else {
            return true;
        }
    }

    public boolean verificarModeloII() {
        if (cbModelo.getValue() == null) {
            msgError("Seleccione un modelo.");
            return false;
        } else {
            return true;
        }
    }

    public boolean verificarTipo() {
        if (cbTipo.getValue() == null) {
            msgError("Seleccione un tipo.");
            return false;
        } else {
            return true;
        }
    }

    public boolean verificarMotor() {
        if (cbMotor.getValue() == null) {
            msgError("Seleccione un motor.");
            return false;
        } else {
            return true;
        }
    }

    public boolean verificarTransmision() {
        if (cbTransmision.getValue() == null) {
            msgError("Seleccione una transmisión.");
            return false;
        } else {
            return true;
        }
    }

    public boolean verificarUbicacion() {
        if (cbUbicacion.getValue() == null) {
            msgError("Seleccione una ubicación.");
            return false;
        } else {
            return true;
        }
    }

    public boolean verificarEstado() {
        if (cbEstado.getValue() == null) {
            msgError("Seleccione un estado.");
            return false;
        } else {
            return true;
        }
    }

    private boolean verificarTodo() {

        return verificarPlaca() && verificarPrecio() && verificarAño() && verificarKilometraje() && verificarPeso()
                && verificarImagen() && verificarMarca() && verificarModeloII() && verificarTipo() && verificarMotor()
                && verificarTransmision() && verificarUbicacion() && verificarEstado() && verificarPlacaExistente()
                && verificarNuevo();
        /* verificarmarca & verificarModelo() que este lleno */
        /* verificar tipo *//* verificar motor & verificar transmision */
        /* verificar ubicacion & verificarestado */
    }

    // Método para obtener el valor del enum Tipo a partir del string seleccionado
    public Tipo obtenerTipo(String tipoSeleccionado) {
        Iterator<Tipo> iterator = Tipo.iteratorTipo();
        while (iterator.hasNext()) {
            Tipo tipo = iterator.next();
            if (Sistema.comparadorString().compare(tipo.getDisplayName().toLowerCase(),
                    tipoSeleccionado.toLowerCase()) == 0) {
                return tipo;
            }
        }

        return null;
    }

    // Método para obtener el valor del enum MarcaDeAuto a partir del string
    // seleccionado
    public MarcaDeAuto obtenerMarca(String marcaSeleccionada) {
        Iterator<MarcaDeAuto> iterator = MarcaDeAuto.iteratorMarcaDeAuto();
        while (iterator.hasNext()) {
            MarcaDeAuto marca = iterator.next();

            if (Sistema.comparadorString().compare(marca.getName().toLowerCase(),
                    marcaSeleccionada.toLowerCase()) == 0) {
                return marca;
            }
        }
        return null;
    }

    // Método para obtener el valor del enum Motor a partir del string seleccionado
    public Motor obtenerMotorDesdeString(String motorSeleccionado) {
        Iterator<Motor> iterator = Motor.iteratorMotor();
        while (iterator.hasNext()) {
            Motor motor = iterator.next();
            if (Sistema.comparadorString().compare(motor.getDisplayName().toLowerCase(),
                    motorSeleccionado.toLowerCase()) == 0) {
                return motor;
            }
        }
        return null;
    }

    // Método para obtener el valor del enum Transmision a partir del string
    // seleccionado
    public Transmision obtenerTransmisionDesdeString(String transmisionSeleccionada) {
        Iterator<Transmision> iterator = Transmision.iteratorTransmision();
        while (iterator.hasNext()) {
            Transmision transmision = iterator.next();
            if (Sistema.comparadorString().compare(transmision.getDisplayName().toLowerCase(),
                    transmisionSeleccionada.toLowerCase()) == 0) {
                return transmision;
            }
        }
        return null;
    }

    // Método para obtener el valor del enum Ubicacion a partir del string
    // seleccionado
    public Ubicacion obtenerUbicacionDesdeString(String ubicacionSeleccionada) {
        Iterator<Ubicacion> iterator = Ubicacion.iteratorUbicacion();
        while (iterator.hasNext()) {
            Ubicacion ubicacion = iterator.next();
            if (Sistema.comparadorString().compare(ubicacion.getDisplayName().toLowerCase(),
                    ubicacionSeleccionada.toLowerCase()) == 0) {
                return ubicacion;
            }
        }
        return null;
    }

    // Método para obtener el valor del enum Estado a partir del string seleccionado
    public Estado obtenerEstadoDesdeString(String estadoSeleccionado) {
        Iterator<Estado> iterator = Estado.iteratorEstado();
        while (iterator.hasNext()) {
            Estado estado = iterator.next();
            if (Sistema.comparadorString().compare(estado.getDisplayName().toLowerCase(),
                    estadoSeleccionado.toLowerCase()) == 0) {
                return estado;
            }
        }
        return null;
    }

    @FXML
    public void abrirReporte() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("anadirReporte.fxml"));
            Parent root = loader.load();
            AnadirReporteController anadirreportecontroller = loader.getController();
            anadirreportecontroller.setUsuario(usuario);
            anadirreportecontroller.setAuto(auto);
            anadirreportecontroller.setProcedencia("e");
            if (reportes != null) {
                anadirreportecontroller.setReportesArr(reportes);
            }
            anadirreportecontroller.cargarTodo();
            anadirreportecontroller.setEditarAutoController(this);
            System.out.println(reportes);
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.setTitle("GuayacoCar - Autos a tu Alcance");
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void alertaAutoEditado() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Auto Editado");
        alert.setHeaderText("Excelente, has editado un auto!");
        alert.setContentText("Auto editado con éxito.");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                regresar();
            }
        });

    }

    private void alertaAutoEliminado() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Auto Eliminado");
        alert.setHeaderText("Tu auto ha sido eliminado de la lista de ventas!");
        alert.setContentText("Auto eliminado con éxito.");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                regresar();
            }
        });

    }

    @FXML
    private void guardarAuto() {
        if (verificarTodo()) {
            try {
                float precio = Float.parseFloat(tfPrecio.getText());
                MarcaDeAuto prueba = obtenerMarca(cbMarca.getValue());
                String modelo = cbModelo.getValue();
                Tipo tipo = obtenerTipo(cbTipo.getValue());
                int anio = Integer.parseInt(tfAnio.getText());
                String placa = tfPlaca.getText();
                int kilometraje = Integer.parseInt(tfKM.getText());
                Motor motor = obtenerMotorDesdeString(cbMotor.getValue());
                Transmision transmision = obtenerTransmisionDesdeString(cbTransmision.getValue());
                float peso = Float.parseFloat(tfPeso.getText());
                Ubicacion ubicacion = obtenerUbicacionDesdeString(cbUbicacion.getValue());
                Estado estado = obtenerEstadoDesdeString(cbEstado.getValue());
                Auto runrunAuto = Sistema.crearAuto(precio, prueba, modelo, tipo, anio, placa, kilometraje, motor,
                        transmision, peso, ubicacion, usuario, estado, fotos);
                System.out.println("AUTO REPORTE: " + auto.getReportes());
                if (auto.getReportes() != null) {
                    ArrayList<Reporte> lr = auto.getReportes();
                    if (reportes != null) {
                        Iterator<Reporte> it = reportes.iterator();
                        while (it.hasNext()) {
                            Reporte reporte = it.next();
                            for (int i = 1; i <= lr.size(); i++) {
                                if (Sistema.comparadorString().compare(lr.get(i).getCategoria(),
                                        reporte.getCategoria()) != 0
                                        || Sistema.comparadorString().compare(lr.get(i).getDescripcion(),
                                                reporte.getDescripcion()) != 0) {
                                    lr.addLast(reporte);
                                }
                            }
                        }
                    }
                    runrunAuto.setReportes(lr);
                } else {
                    if (reportes != null) {
                        System.out.println(reportes);
                        runrunAuto.setReportes(reportes);
                    }
                }
                System.out.println("El auto ha sido creado!");
                System.out.println("el auto creado es " + runrunAuto.toString());
                System.out.println("El auto se intentará guardar");
                Boolean removed = Sistema.eliminarAuto(auto);
                Boolean saved = false;
                if (removed == true) {
                    saved = Sistema.guardarAuto(runrunAuto);
                }
                System.out.println(saved);
                alertaAutoEditado();
            } catch (IllegalArgumentException i) {
                System.out.println("Un error ocurrio al querer guardar el auto en editarAutoController: ");
                System.out.println(i);
            }
        } else {
            System.out.println("Algo no está completado");
        }

    }

    @FXML
    private void eliminarAuto() {
        try {
            Sistema.eliminarAutoFavoritoGlobal(auto);
            Map<String, Usuario> usuarios = Archivos.leerUsuarios();
            Usuario u = usuarios.get(usuario.getCorreo());
            usuario = u;
            Boolean removed = Sistema.eliminarAuto(auto);
            alertaAutoEliminado();
            System.out.println("EDITARAUTO CONTROLLER: Auto eliminado con exito");
        } catch (Exception e) {
            System.out.println("Un error ocurrio al querer eliminar el auto en editarAutoController: ");
            System.out.println(e);
        }
    }

}