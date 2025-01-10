package Controladores;

import Clases.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Bases.*;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UsuarioController implements Initializable {

    @FXML
    private Text txtEncuentreAutoSuenios;

    @FXML
    private TextField tfBusqueda;
    @FXML
    private Button btnCrearAuto;
    @FXML
    private ComboBox<String> cmMarca;
    @FXML
    private ComboBox<String> cmModelo;
    @FXML
    private TextField tfPrecioDesde;
    @FXML
    private TextField tfPrecioHasta;
    @FXML
    private TextField tfKMDesde;
    @FXML
    private TextField tfKMHasta;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnCerrarSesion;
    @FXML
    private ComboBox<String> cbOrdenar;
    @FXML
    private Button btnAplicar;
    @FXML
    private Label lblUser;

    private Usuario usuario;

    @FXML
    private MenuItem menuMisAutos;
    @FXML
    private MenuBar menuBar;
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
    private ImageView buscar;
    @FXML
    private MenuItem menuFav;
    private ArrayList<ImageView> imagenes = new ArrayList<>();
    private DoublyCircularList<Auto> autos = Archivos.leerAutos();
    private DoublyCircularNode<Auto> autoNodo;
    private DoublyCircularNode<File> foto;
    private int autosMostrados;
    @FXML
    private Button btnError;
    @FXML
    private ImageView mostrarAutosAdelante;
    @FXML
    private ImageView borrarFiltro;
    @FXML
    private ImageView filtroAvanzado;
    @FXML
    private ComboBox<String> cbTipo;
    @FXML
    private ImageView mostrarAutosAtras;
    @FXML
    private CheckBox c1;
    @FXML
    private CheckBox c2;
    @FXML
    private CheckBox c3;
    @FXML
    private CheckBox c4;
    @FXML
    private CheckBox c5;
    @FXML
    private CheckBox c6;
    @FXML
    private Label msg1;
    @FXML
    private Label msg2;
    @FXML
    private CheckBox checkCalidad;
    @FXML
    private ImageView btnCancelar;
    @FXML
    private Button btnComparar;
    @FXML
    private Button btnListo;
    private Map<CheckBox, Auto> checksAutos;

    private Comparator<String> compString = Sistema.comparadorString();

    public Map<CheckBox, Auto> getChecksAutos() {
        return checksAutos;
    }

    public void setChecksAutos(Map<CheckBox, Auto> autosSeleccionados) {
        this.checksAutos = autosSeleccionados;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        System.out.println("USUARIO CONTROLLER: Usuario seteado!");
        lblUser.setText(usuario.getNombre() + " " + usuario.getApellido() + "!");
        // cargarControladores();
        quitarChecks();
        cbOrdenar.setValue("Precio");
        ordenarAutoPorXCriterio();
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

    @FXML
    private void cerrarSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("GuayacoCar - Iniciar Sesión");
            stage.show();

            Stage miStage = (Stage) btnCerrarSesion.getScene().getWindow();
            miStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void mostrarCrearAuto() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("crearauto.fxml"));
            Parent root = loader.load();
            CrearAutoController CrearAutoController = loader.getController();
            CrearAutoController.setUsuario(usuario);
            CrearAutoController.cargarCampos();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("GuayacoCar - Autos a tu Alcance");
            stage.show();

            Stage miStage = (Stage) btnCrearAuto.getScene().getWindow();
            miStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void mostrarMisAutos() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("misautos.fxml"));
            Parent root = loader.load();
            MisAutosController MisautosController = loader.getController();
            MisautosController.setUsuario(usuario);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("GuayacoCar - Mis Autos");
            stage.show();

            Stage miStage = (Stage) menuBar.getScene().getWindow();
            miStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void mostrarFavoritos() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("favoritos.fxml"));
            Parent root = loader.load();
            FavoritosController favoritosController = loader.getController();
            favoritosController.setUsuario(usuario);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("GuayacoCar - Autos a tu Alcance");
            stage.show();

            Stage miStage = (Stage) menuBar.getScene().getWindow();
            miStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarAutos() {

        if (autos.size() > 0) {
            mostrarAutosAdelante.setVisible(true);
            mostrarAutosAtras.setVisible(true);
            System.out.println("USUARIO CONTROLLER: Hay " + autos.size() + " autos en la DCLL actual");
            autoNodo = autos.getHeader();
            mostrarAutosAdelante();
            if (autos.size() < 7) {
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
        Map<CheckBox, Auto> checksAutos = new LinkedHashMap();
        do {
            Auto auto = autoNodo.getContent();
            try {
                /*
                 * Esta fue la mejor forma que se encontró para poder hacer que los campos se
                 * oculten/muestren de manera dinamica
                 * basicamente el metodo obtiene los campos segun un string declarado y los ira
                 * iterando ya que el primer ImageView
                 * se llama imgAuto1, el segundo imgAuto2, asi sucesivamente hasta llegar al 6 y
                 * con los titulos, precios, etc.
                 * 
                 */
                Field imgField = getClass().getDeclaredField("imgAuto" + index);
                imgField.setAccessible(true);
                ImageView imgView = (ImageView) imgField.get(this);
                foto = auto.getFotos().getHeader();
                Image image = new Image(foto.getContent().toURI().toString());
                imgView.setDisable(false);
                imgView.setImage(image);
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

                Field check = getClass().getDeclaredField("c" + index);
                check.setAccessible(true);
                CheckBox checkBox = (CheckBox) check.get(this);
                checksAutos.put(checkBox, auto);
                setChecksAutos(checksAutos);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            index++;
            autoNodo = autoNodo.getNext();
        } while (autoNodo != autos.getHeader() && index <= 6);
        if (compString.compare(autoNodo.getContent().getPlaca(), autos.getHeader().getContent().getPlaca()) == 0
                || autos.size() == 1) {
            if (index < 7) {
                ponerBlanco(index);
            }
        }

    }

    @FXML
    public void mostrarAutosAtras() {
        /*
         * Este metodo controla el nodo cabecera
         */
        int indicePRB = 0;
        if (autos.getIndex(autoNodo) == 6 && (autos.size() % 6) != 0) {
            int indice = autos.size() - (autos.size() % 6);
            // Se establece el nodo para mostrar los ultimos autos
            autoNodo = autos.getNodo(indice);
        } else if (compString.compare(autoNodo.getContent().getPlaca(),
                autos.getHeader().getContent().getPlaca()) == 0) {
            // si el auto mostrado es el ultimo, calculara la pagina anterior
            if (autos.size() % 6 == 0) {
                // si no tiene paginas incompletas, retrocederá 12 nodos
                indicePRB = 6 + 6;
            } else {
                // si tiene una pag incompleta calculara los 6 de la pag + el residuo del tamaño
                // de la lista
                indicePRB = 6 + (autos.size() % 6);
            }
        } else {
            // si es una pag x retrocedera 12 nodos
            indicePRB = 12;
        }
        // retrocedo los nodos necesarios!
        for (int i = 1; i <= indicePRB; i++) {
            autoNodo = autoNodo.getPrevious();
        }
        // Este metodo esta adaptado para funcionar con una cantidad N de nodos
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
        System.out.println("USUARIO CONTROLLER: se ocultan " + (7 - index) + " espacios");
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

    public void mostrarAuto(Auto auto) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vistaauto.fxml"));
            Parent root = loader.load();
            VistaAutoController VistaautoController = loader.getController();
            VistaautoController.setAuto(auto);
            VistaautoController.setProcedencia("usuario.fxml");
            VistaautoController.setUsuario(usuario);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("GuayacoCar - Autos a tu Alcance");
            stage.show();

            Stage miStage = (Stage) menuBar.getScene().getWindow();
            miStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void buscar() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Lo sentimos");
        alert.setHeaderText("Opción no disponible.");
        alert.setContentText("Esta opción aún está en desarrollo! :(");
        alert.showAndWait();
    }

    @FXML
    public void VerificarModelo() {
        if (cmMarca.getValue() == null) {
            msgError("Primero tienes que escoger una marca.");
            cmMarca.requestFocus();

        }
    }

    @FXML
    public void cargarModelo() {
        msgErrorOff();
        cmModelo.getSelectionModel().clearSelection();
        cmModelo.getItems().clear();
        cmModelo.setValue(null);
        String txtMarca = cmMarca.getValue();
        // esto lo debo hacer con iteratorya

        if (txtMarca != null) {
            Iterator<MarcaDeAuto> iterator = MarcaDeAuto.iteratorMarcaDeAuto();

            while (iterator.hasNext()) {
                MarcaDeAuto marca = iterator.next();
                if (Sistema.comparadorString().compare(marca.getName(), txtMarca) == 0) {
                    ArrayList<String> modelos = marca.getModels();
                    for (int i = 1; i <= modelos.size(); i++) {
                        cmModelo.getItems().add(modelos.get(i));
                    }
                }
            }
        }

    }

    public void cargarMarca() {
        Iterator<String> iterator = MarcaDeAuto.iterator();
        while (iterator.hasNext()) {
            cmMarca.getItems().add(iterator.next());
        }

    }

    public void cargarTipo() {
        Iterator<String> iterator = Tipo.iterator();
        while (iterator.hasNext()) {
            cbTipo.getItems().add(iterator.next());
        }
    }

    public Comparator<Auto> ordenarPorComp() {
        String criterio = cbOrdenar.getValue();
        if (compString.compare(criterio, "Marca y Modelo") == 0) {
            return new Comparator<Auto>() {
                @Override
                public int compare(Auto auto1, Auto auto2) {
                    String marca1 = auto1.getMarca().getName();
                    String marca2 = auto2.getMarca().getName();
                    if (compString.compare(marca1, marca2) != 0) {
                        return marca1.compareTo(marca2);
                    } else if (compString.compare(auto1.getModelo(), auto2.getModelo()) != 0) {
                        return auto1.getModelo().compareTo(auto2.getModelo());
                    } else {
                        return Double.compare(auto1.getPrecio(), auto2.getPrecio());
                    }
                }
            };
        } else if (compString.compare(criterio, "Precio") == 0) {
            return new Comparator<Auto>() {
                @Override
                public int compare(Auto auto1, Auto auto2) {
                    float p1 = auto1.getPrecio();
                    float p2 = auto2.getPrecio();
                    if (p1 == p2) {
                        int km1 = auto1.getKilometraje();
                        int km2 = auto2.getKilometraje();
                        return Integer.compare(km1, km2);
                    } else {
                        return Double.compare(p1, p2);
                    }

                }
            };
        } else if (compString.compare(criterio, "Año del Auto") == 0) {
            return Comparator.comparingInt(Auto::getAño).reversed(); // Ordenar por año en orden descendente
        } else if (compString.compare(criterio, "Kilometraje") == 0) {
            return new Comparator<Auto>() {
                @Override
                public int compare(Auto auto1, Auto auto2) {
                    int km1 = auto1.getKilometraje();
                    int km2 = auto2.getKilometraje();
                    if (km1 == km2) {
                        double p1 = auto1.getPrecio();
                        double p2 = auto2.getPrecio();
                        return Double.compare(p1, p2);
                    } else {
                        return Integer.compare(km1, km2);
                    }
                }
            };
        }
        return null;
    }

    @FXML
    public void ordenarAutoPorXCriterio() {
        Comparator<Auto> comp = ordenarPorComp();

        if (comp != null && autos.size() > 0) {
            ordenar(autos, comp);

            autoNodo = autos.getHeader();
            cargarAutos();
            msg1.setVisible(false);
            msg2.setVisible(false);
            checkCalidad.setVisible(false);
        } else {
            System.out.println("No se seleccionó un criterio de ordenación válido.");
        }

    }

    private void ordenar(DoublyCircularList<Auto> lista, Comparator<Auto> comp) {
        if (lista.getLast() == null || lista.getLast().getNext() == lista.getLast()) {
            // Si la lista está vacía o tiene un solo elemento, no se hace nada
            return;
        }

        boolean swapped;
        do {
            swapped = false;
            DoublyCircularNode<Auto> current = lista.getLast().getNext();
            do {
                DoublyCircularNode<Auto> nextNode = current.getNext();
                if (comp.compare(current.getContent(), nextNode.getContent()) > 0) {
                    // Intercambiar contenidos de los nodos en lugar de los nodos mismos
                    Auto tempContent = current.getContent();
                    current.setContent(nextNode.getContent());
                    nextNode.setContent(tempContent);
                    swapped = true;
                }
                current = nextNode;
            } while (current != lista.getLast());
        } while (swapped);
    }

    public Comparator<Auto> ordenarPorReporte() {
        Comparator<Auto> comp = new Comparator<>() {
            @Override
            public int compare(Auto a1, Auto a2) {
                ArrayList<Reporte> reporte1 = a1.getReportes();
                ArrayList<Reporte> reporte2 = a2.getReportes();
                int mant1 = 0;
                int rep1 = 0;
                int accid1 = 0;
                int mant2 = 0;
                int rep2 = 0;
                int accid2 = 0;
                Iterator<Reporte> it1 = reporte1.iterator();
                while (it1.hasNext()) {
                    String cat = it1.next().getCategoria();
                    if (compString.compare(cat.toLowerCase(), "mantenimiento".toLowerCase()) == 0) {
                        mant1 += 1;
                    } else if (compString.compare(cat.toLowerCase(), "reparación".toLowerCase()) == 0) {
                        rep1 += 1;
                    } else {
                        accid1 += 1;
                    }
                }
                Iterator<Reporte> it2 = reporte2.iterator();
                while (it2.hasNext()) {
                    String cat = it2.next().getCategoria();
                    if (compString.compare(cat.toLowerCase(), "mantenimiento".toLowerCase()) == 0) {
                        mant2 += 1;
                    } else if (compString.compare(cat.toLowerCase(), "reparación".toLowerCase()) == 0) {
                        rep2 += 1;
                    } else {
                        accid2 += 1;
                    }
                }
                if (accid1 != accid2) {
                    return Integer.compare(accid1, accid2);
                } else if (mant1 != mant2) {
                    return Integer.compare(mant1, mant2);
                } else {
                    return Integer.compare(rep1, rep2);
                }
            }
        };
        return comp;
    }

    @FXML
    public void ordenarAutoPorReporte() {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Filtro Avanzado Por Reportes");
        alert.setHeaderText(
                "El filtro avanzado por reportes filtra los autos con reportes de mejor a peor calidad basado en un análisis de los mismos. Los autos con accidentes se consideran de peor calidad.");
        alert.setContentText("¿Filtrar autos por reportes?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Comparator<Auto> comp = ordenarPorReporte();

            DoublyCircularList<Auto> autosConReport = new DoublyCircularList<>();
            autos = Archivos.leerAutos();
            for (DoublyCircularNode<Auto> n = autos.getLast().getNext();; n = n.getNext()) {
                if (n.getContent().getReportes() != null) {
                    if (n.getContent().getReportes().size() > 0) {
                        autosConReport.addLast(n.getContent());
                    }
                }
                if (n == autos.getLast()) {
                    break;
                }
            }
            autos = autosConReport;

            if (comp != null && autos.size() > 0) {

                ordenar(autos, comp);
                autoNodo = autos.getHeader();
                cargarAutos();
                msg1.setVisible(true);
                msg2.setVisible(true);
                checkCalidad.setVisible(true);
            } else {
                System.out.println("USUARIO CONTROLLER: No hay autos con reportes");
                cargarAutos();
                msgError("No hay autos con reportes :(");

            }
        } else {
            System.out.println("USUARIO CONTROLLER: Usuario seleccionó No o cerró el diálogo.");
        }
    }

    @FXML
    public void checkExcelenteCalidad() {
        if (checkCalidad.isSelected()) {
            DoublyCircularList<Auto> autosExcelentes = new DoublyCircularList<>();
            Iterator<Auto> it = autos.iterator();
            while (it.hasNext()) {
                boolean entra = true;
                System.out.println("SE REPITE");
                Auto auto = it.next();
                if (auto.getReportes() != null) {
                    ArrayList<Reporte> reportes = auto.getReportes();
                    for (int i = 1; i <= reportes.size(); i++) {
                        if (compString.compare(reportes.get(i).getCategoria().toLowerCase(),
                                "Accidente".toLowerCase()) == 0) {
                            entra = false;
                        }
                    }
                }
                if (entra == true) {
                    autosExcelentes.addLast(auto);
                }
            }
            System.out.println("Lista de AUTOS EXCELENTES: " + autosExcelentes.toString());
            System.out.println(autosExcelentes.size() + " & " + autos.size());
            setAutos(autosExcelentes);
            cargarAutos();

        } else {

            Comparator<Auto> comp = ordenarPorReporte();

            DoublyCircularList<Auto> autosConReport = new DoublyCircularList<>();
            autos = Archivos.leerAutos();
            for (DoublyCircularNode<Auto> n = autos.getLast().getNext();; n = n.getNext()) {
                if (n.getContent().getReportes() != null) {
                    if (n.getContent().getReportes().size() > 0) {
                        autosConReport.addLast(n.getContent());
                    }
                }
                if (n == autos.getLast()) {
                    break;
                }
            }
            autos = autosConReport;

            if (comp != null && autos.size() > 0) {

                ordenar(autos, comp);

                autoNodo = autos.getHeader();
                cargarAutos();
                msg1.setVisible(true);
                msg2.setVisible(true);
                checkCalidad.setVisible(true);
            } else {
                System.out.println("No hay autos.");
                btnError.setText("No hay autos con reportes :(");
            }

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarMarca(); // Llama a cargarCampos al inicializar el
        cargarTipo();
        cbOrdenar.getItems().addAll("Marca y Modelo", "Año del Auto", "Precio", "Kilometraje");
    }

    @FXML
    private void filtroAvanzado() {
        DoublyCircularList<Auto> filtrados = new DoublyCircularList<>();
        Iterator<Auto> it = Archivos.leerAutos().iterator();
        while (it.hasNext()) {
            Auto auto = it.next();
            if (filtrado(auto)) {
                filtrados.addLast(auto);
            }
        }
        setAutos(filtrados);
        if (autos.size() > 0) {
            autoNodo = autos.getHeader();
            ordenarAutoPorXCriterio();
        } else {
            System.out.println("No hay resultados para el filtro avanzado");
            ponerBlanco(1);
        }

    }

    public boolean filtrado(Auto auto) {
        boolean bandera = true;
        bandera = bandera && marcaFiltro(auto);
        bandera = bandera && modeloFiltro(auto);
        bandera = bandera && precioDesdeFiltro(auto);
        bandera = bandera && precioHastaFiltro(auto);
        bandera = bandera && KMDesdeFiltro(auto);
        bandera = bandera && KMHastaFiltro(auto);
        bandera = bandera && tipoFiltro(auto);
        return bandera;
    }

    public boolean marcaFiltro(Auto auto) {
        boolean bandera = true;
        if (cmMarca.getValue() != null) {
            bandera = compString.compare(cmMarca.getValue(), auto.getMarca().getName()) == 0;
            cbOrdenar.setValue("Marca y Modelo");
        }
        return bandera;

    }

    public boolean modeloFiltro(Auto auto) {
        boolean bandera = true;
        if (cmModelo.getValue() != null) {
            bandera = compString.compare(cmModelo.getValue(), auto.getModelo()) == 0;
            cbOrdenar.setValue("Marca y Modelo");
        }
        return bandera;
    }

    public boolean precioDesdeFiltro(Auto auto) {
        boolean bandera = true;
        String precioDesde = tfPrecioDesde.getText();
        if (!precioDesde.isEmpty()) {
            try {
                float preciodesde = Float.parseFloat(precioDesde);
                System.out.println("PRECIO DESDE" + preciodesde);
                System.out.println(Float.parseFloat(precioDesde) < auto.getPrecio());
                bandera = Float.parseFloat(precioDesde) < auto.getPrecio();
                cbOrdenar.setValue("Precio");
                msgErrorOff();
            } catch (NumberFormatException nfe) {
                msgError("\"Precio desde\" en formato incorrecto.");
                return false;
            }
            return bandera;
        }

        return bandera;
    }

    public boolean precioHastaFiltro(Auto auto) {
        boolean bandera = true;
        String precioHasta = tfPrecioHasta.getText();
        if (!precioHasta.isEmpty()) {
            try {
                float preciohasta = Float.parseFloat(precioHasta);
                System.out.println("PRECIO HASTA" + precioHasta);
                System.out.println(Float.parseFloat(precioHasta) > auto.getPrecio());
                bandera = Float.parseFloat(precioHasta) > auto.getPrecio();
                cbOrdenar.setValue("Precio");
                msgErrorOff();
            } catch (NumberFormatException nfe) {
                msgError("\"Precio hasta\" en formato incorrecto.");
                return false;
            }

        }
        return bandera;
    }

    public boolean KMDesdeFiltro(Auto auto) {
        boolean bandera = true;
        String KMDesde = tfKMDesde.getText();
        if (!KMDesde.isEmpty()) {
            try {
                bandera = Integer.parseInt(KMDesde) < auto.getKilometraje();
                cbOrdenar.setValue("Kilometraje");
            } catch (NumberFormatException nfe) {
                msgError("\"Kilometraje desde\" en formato incorrecto.");
                return false;
            }
        }
        return bandera;
    }

    public boolean KMHastaFiltro(Auto auto) {
        boolean bandera = true;
        String KMHasta = tfKMHasta.getText();
        if (!KMHasta.isEmpty()) {
            try {
                bandera = Integer.parseInt(KMHasta) > auto.getKilometraje();
                cbOrdenar.setValue("Kilometraje");
            } catch (NumberFormatException nfe) {
                msgError("\"Kilometraje hasta\" en formato incorrecto.");
                return false;
            }
        }
        return bandera;
    }

    public boolean tipoFiltro(Auto auto) {
        boolean bandera = true;
        if (cbTipo.getValue() != null) {
            bandera = compString.compare(cbTipo.getValue(), auto.getTipo().getDisplayName()) == 0;
            cbOrdenar.setValue("Marca y Modelo");
        }
        return bandera;
    }

    @FXML
    public void limpiarCampos() {

        cmMarca.setEditable(true);
        cmMarca.setValue(null);
        cmMarca.setPromptText("Marca");
        cmMarca.setEditable(false);
        cmModelo.setEditable(true);
        cmModelo.setValue(null);
        cmModelo.setPromptText("Modelo");
        cmModelo.setEditable(false);
        cbTipo.setEditable(true);
        cbTipo.setValue(null);
        cbTipo.setPromptText("Tipo");
        cbTipo.setEditable(false);

        tfPrecioDesde.setText("");
        tfPrecioHasta.setText("");
        tfKMDesde.setText("");
        tfKMHasta.setText("");
        cbOrdenar.setValue("Precio");

        msg1.setVisible(false);
        msg2.setVisible(false);
        checkCalidad.setVisible(false);

        setAutos(Archivos.leerAutos());
        ordenarAutoPorXCriterio();

    }

    @FXML
    private void mostrarCheck() {
        mostrarAutosAtras.setVisible(false);
        mostrarAutosAdelante.setVisible(false);

        if (imgAuto1.getOpacity() != 0) {
            c1.setVisible(true);
        }
        if (imgAuto2.getOpacity() != 0) {
            c2.setVisible(true);
        }
        if (imgAuto3.getOpacity() != 0) {
            c3.setVisible(true);
        }
        if (imgAuto4.getOpacity() != 0) {
            c4.setVisible(true);
        }
        if (imgAuto5.getOpacity() != 0) {
            c5.setVisible(true);
        }
        if (imgAuto6.getOpacity() != 0) {
            c6.setVisible(true);
        }

        btnListo.setVisible(true);
        btnListo.setDisable(false);
        btnCancelar.setVisible(true);
        btnCancelar.setDisable(false);
        btnComparar.setVisible(false);
        btnComparar.setDisable(true);

    }

    @FXML
    private void mostrarComparar() {

        Map<CheckBox, Auto> mapaCheckAutos = getChecksAutos();
        ArrayList<Auto> seleccionados = new ArrayList<>();
        int seleccs = 0;

        try {
            for (int i = 1; i < 7; i++) {
                Field checkField = getClass().getDeclaredField("c" + i);
                checkField.setAccessible(true);
                CheckBox checkBox = (CheckBox) checkField.get(this);
                if (checkBox.isSelected()) {
                    seleccs++;
                    seleccionados.addLast(mapaCheckAutos.get(checkBox));
                }
            }
        } catch (Exception e) {
            System.out.println("HUBO UN PROBLEMA AL CONTAR LOS CHECKBOXS" + e.getMessage());
        }
        if (seleccs < 1) {
            msgError("No has seleccionado ningún vehículo, debes seleccionar 2 vehículos para compararlos!");
        } else if (seleccs == 1) {
            msgError("Debes seleccionar más de un vehículo para compararlo con otro!");
        } else if (seleccs > 2) {
            msgError("Solo puedes comparar dos vehículos, ¡selecciona solo dos por favor!");
        } else {
            msgErrorOff();
            abrirComparar(seleccionados.get(1), seleccionados.get(2));
        }

    }

    private void abrirComparar(Auto a1, Auto a2) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("comparadorAutos.fxml"));
            Parent root = loader.load();
            ComparadorAutosController comparadorController = loader.getController();
            comparadorController.setUsuario(usuario, a1, a2);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("GuayacoCar - Comparador de Autos");
            stage.show();

            Stage miStage = (Stage) btnListo.getScene().getWindow();
            miStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void quitarChecks() {
        if (autos.size() > 6) {
            mostrarAutosAtras.setVisible(true);
            mostrarAutosAdelante.setVisible(true);
        }

        c1.setVisible(false);
        c1.setSelected(false);
        c2.setVisible(false);
        c2.setSelected(false);
        c3.setVisible(false);
        c3.setSelected(false);
        c4.setVisible(false);
        c4.setSelected(false);
        c5.setVisible(false);
        c5.setSelected(false);
        c6.setVisible(false);
        c6.setSelected(false);
        btnListo.setVisible(false);
        btnListo.setDisable(true);
        btnCancelar.setVisible(false);
        btnCancelar.setDisable(true);
        btnComparar.setVisible(true);
        btnComparar.setDisable(false);
    }

}
