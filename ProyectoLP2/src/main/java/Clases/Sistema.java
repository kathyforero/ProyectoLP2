package Clases;

import Bases.*;
import java.io.File;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Sistema implements Serializable {
    private static final long serialVersionUID = 2004140222041502L;
    // Aqui se cambio todos los equals por comparadores naturales.

    public static Usuario crearUsuario(String nombre, String apellido, String correo, String contraseña) {
        Usuario u = new Usuario(nombre, apellido, correo, contraseña);
        return u;
    }

    public static Auto crearAuto(float precio, MarcaDeAuto marca, String modelo, Tipo tipo, int año, String placa,
            int kilometraje, Motor motor, Transmision transmisión, float peso,
            Ubicacion ubicacion, Usuario usuario, Estado estado, DoublyCircularList<File> fotos) {
        Auto auto = new Auto(precio, marca, modelo, tipo, año, placa, kilometraje, motor, transmisión,
                peso, ubicacion, usuario, estado, fotos);
        return auto;
    }

    public static boolean guardarUsuario(Usuario u) {
        try {
            Map<String, Usuario> usuarios = Archivos.leerUsuarios();
            usuarios.put(u.getCorreo(), u);
            Archivos.guardarUsuarios(usuarios);
            System.out.println("SISTEMA: se guardó usuario " + u.getCorreo());
            return true;
        } catch (Exception e) {
            System.err.println("ERROR AL GUARDAR USUARIO!!! " + e.getMessage());
            return false;
        }
    }

    public static boolean existeUser(String correo) {
        Map<String, Usuario> Usuarios = Archivos.leerUsuarios();
        return Usuarios.containsKey(correo);
    }

    public static boolean logearUser(String correo, String contraseña) {
        Map<String, Usuario> Usuarios = Archivos.leerUsuarios();
        Usuario u = Usuarios.get(correo);
        return u.getContraseña().compareTo(contraseña) == 0;
    }

    public static Usuario getUsuario(String correo) {
        Map<String, Usuario> Usuarios = Archivos.leerUsuarios();
        Usuario u = Usuarios.get(correo);
        return u;
    }

    public static boolean guardarAuto(Auto auto) {
        try {
            DoublyCircularList<Auto> autos = Archivos.leerAutos();
            autos.addLast(auto);
            Archivos.guardarAutos(autos);
            System.out.println("SISTEMA: se guardó el auto de Archivos.guardarAutos");
            return true;
        } catch (Exception e) {
            System.err.println("ERROR AL GUARDAR AUTO!!! " + e.getMessage());
            return false;
        }
    }

    public static Comparator<Auto> ordenarPrecioKilometraje() {
        Comparator<Auto> comparator = new Comparator<Auto>() {
            @Override
            public int compare(Auto auto1, Auto auto2) {
                float p1 = auto1.getPrecio();
                float p2 = auto2.getPrecio();
                if (p1 == p2) {
                    int km1 = auto1.getKilometraje();
                    int km2 = auto2.getKilometraje();
                    return Integer.compare(km1, km2);
                } else {
                    return Float.compare(p1, p2);
                }
            }
        };
        return comparator;
    }

    public static void ordenar(DoublyCircularList<Auto> lista, Comparator<Auto> comp) {
        // Si la lista está vacía o tiene un solo elemento, no se hace nada
        if (lista.getLast() == null || lista.getLast().getNext() == lista.getLast()) {
            return;
        }

        // Inicializamos 'current' en el primer nodo de la lista.
        DoublyCircularNode<Auto> current = lista.getLast().getNext();
        // DoublyCircularNode<Auto> current = lista.getHeader(); tambien sirve

        // Iteramos sobre la lista desde el primer nodo hasta que volvemos al nodo
        // cabecera
        while (current != lista.getLast()) {
            // Guardamos el siguiente nodo
            DoublyCircularNode<Auto> next = current.getNext();
            // Guardamos el contenido del nodo actual
            Auto currentValue = current.getContent();
            // Empezamos a comparar desde el nodo anterior al nodo actual
            DoublyCircularNode<Auto> sortedNode = current;

            // Movemos nodos en la parte ordenada hacia la derecha para hacer espacio
            // para 'currentValue' en la posición correcta
            while (sortedNode.getPrevious() != lista.getLast()
                    && comp.compare(sortedNode.getPrevious().getContent(), currentValue) > 0) {
                // Desplazamos el contenido del nodo ordenado al siguiente nodo
                sortedNode.setContent(sortedNode.getPrevious().getContent());
                // Retrocedemos al nodo anterior en la lista ordenada
                sortedNode = sortedNode.getPrevious();
            }

            // Insertamos 'currentValue' en la posición correcta encontrada
            sortedNode.setContent(currentValue);
            // Avanzamos al siguiente nodo en la lista original
            current = next;
        }
    }

    public static boolean eliminarAuto(Auto auto) {
        try {
            DoublyCircularList<Auto> autos = Archivos.leerAutos();
            for (DoublyCircularNode<Auto> n = autos.getLast().getNext(); n != autos.getLast(); n = n.getNext()) {
                if (n.getContent().getPlaca().compareTo(auto.getPlaca()) == 0) {
                    autos.removeNode(n);
                }
            }
            if (autos.getLast().getContent().getPlaca().compareTo(auto.getPlaca()) == 0) {
                autos.removeNode(autos.getLast());
            }
            // autos.addLast(auto);
            Archivos.guardarAutos(autos);
            System.out.println("SISTEMA: AUTO ELIMINADO CON EXITO");
            return true;
        } catch (Exception e) {
            System.err.println("ERROR AL ELIMINAR AUTO!!! " + e.getMessage());
            return false;
        }
    }

    public static void eliminarAutoFavoritoGlobal(Auto auto) {
        try {
            Map<String, Usuario> usuarios = Archivos.leerUsuarios();
            Set correos = usuarios.keySet();
            Iterator<String> it = correos.iterator();
            while (it.hasNext()) {
                Usuario u = usuarios.get(it.next());
                eliminarAutoFavorito(auto, u);
            }
            System.out.println("SISTEMA: Se elimino el auto de TODAS las listas de favoritos");
        } catch (Exception e) {
            System.err.println("ERROR AL ELIMINAR FAVORITOS GLOBAL!!! " + e.getMessage());
        }

    }

    private static void eliminarAutoFavorito(Auto auto, Usuario usuario) {
        try {
            DoublyCircularList<Auto> fav = usuario.getFavoritos();
            if (fav.size() > 0) {
                DoublyCircularNode<Auto> a = fav.getHeader();
                boolean bandera = true;
                do {
                    Auto au = a.getContent();
                    if (au.getPlaca().compareTo(auto.getPlaca()) == 0) {
                        fav.removeNode(a);
                        System.out.println("SISTEMA: Auto con placa " + auto.getPlaca()
                                + " eliminado de favoritos del usuario " + usuario.getCorreo());
                        bandera = false;
                        actualizarUsuario(usuario);
                    } else {
                        a = a.getNext();
                    }
                } while (bandera && a != fav.getHeader());
            }
            usuario.setFavorito(fav);
        } catch (Exception e) {
            System.err.println("ERROR AL ELIMINAR AUTO DE FAVORITO!!! " + e.getMessage());
        }

    }

    public static boolean actualizarUsuario(Usuario user) {
        try {
            Map<String, Usuario> Usuarios = Archivos.leerUsuarios();
            Usuarios.remove(user.getCorreo());
            Usuarios.put(user.getCorreo(), user);
            Archivos.guardarUsuarios(Usuarios);
            System.out.println("SISTEMA: USUARIO ACTUALIZADO " + user.getCorreo());
            return true;
        } catch (Exception e) {
            System.err.println("ERROR AL ELIMINAR Usuario!!! " + e.getMessage());
            return false;
        }
    }

    public static Comparator<String> comparadorString() {
        Comparator<String> comp = new Comparator<>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareTo(s2);
            }
        };

        return comp;
    }
}
