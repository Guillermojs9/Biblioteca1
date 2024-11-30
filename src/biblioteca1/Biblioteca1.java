package biblioteca1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Biblioteca1 {

    private static final String URL = "jdbc:mariadb://172.26.157.64:3306/BIBLIOTECA";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static Connection con;
    private static final int VACIAR_TABLAS = 1;
    private static final int RELLENAR_CATEGORIAS = 2;
    private static final int CARGAR_FICHERO = 3;
    private static final int MOSTRAR_LIBROS_PRESTADOS = 4;
    private static final int MOSTRAR_LIBROS_POR_CATEGORIA = 5;
    private static final int SALIR = 0;

    public static void main(String[] args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            boolean salir = false;
            do {
                mostrarMenuPrincipal();
                int opcion = LeerDatosTeclado.leerInt("Selecciona una opción del menú:", 0, 5);
                System.out.println();
                switch (opcion) {
                    case VACIAR_TABLAS:
                        vaciarTablas();
                        break;
                    case RELLENAR_CATEGORIAS:
                        defaultCategorias();
                        break;
                    case CARGAR_FICHERO:
                        File f = new File("./ficheros/datos.sql");
                        ejecutarScript(f);
                        break;
                    case MOSTRAR_LIBROS_PRESTADOS:
                        mostrarLibrosPrestados();
                        break;
                    case MOSTRAR_LIBROS_POR_CATEGORIA:
                        mostrarLibrosPorCategoria();
                        break;
                    case SALIR:
                        salir = true;
                }
            } while (!salir);
        } catch (SQLException ex) {
            Logger.getLogger(Biblioteca1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Biblioteca1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void mostrarMenuPrincipal() {
        List<String> opcionesMenu = Arrays.asList(
                "Salir",
                "Vaciar todas las tablas",
                "Rellenar tabla categorías con valores por defecto",
                "Cargar fichero script (datos.sql)",
                "Mostrar todos los libros prestados",
                "Mostrar libro por categoría"
        );
        System.out.println();
        for (int i = 0; i < opcionesMenu.size(); i++) {
            System.out.println(i + "." + opcionesMenu.get(i));
        }
        System.out.println();
    }

    public static void mostrarCategorias() {
        List<String> categorias = Arrays.asList(
                "Base de datos",
                "Programacion",
                "Redes",
                "Ofimatica",
                "Hardware",
                "Seguridad",
                "Aplicaciones Web",
                "Sistemas Operativos",
                "Miscelanea"
        );
        System.out.println();
        for (int i = 0; i < categorias.size(); i++) {

            System.out.println((i + 1) + "-." + categorias.get(i));
        }
        System.out.println();
    }

    public static void vaciarTablas() {
        try {
            Statement stmt = con.createStatement();
            String[] tablas = {"prestamos", "usuario", "libro", "categoria"};
            con.setAutoCommit(false);
            for (String tabla : tablas) {
                String sql = "DELETE FROM " + tabla;
                stmt.addBatch(sql);
            }
            stmt.executeBatch();
            con.commit();
            System.out.println("Se han vaciado todas las tablas \n");
        } catch (SQLException ex) {
            Logger.getLogger(Biblioteca1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void defaultCategorias() {
        try {
            Statement stmt = con.createStatement();
            BufferedReader br = new BufferedReader(new FileReader("./ficheros/categorias.sql"));
            String linea;
            while ((linea = br.readLine()) != null) {
                stmt.execute(linea);
            }
            System.out.println("Se han importado todas las categorías");
        } catch (SQLException ex) {
            Logger.getLogger(Biblioteca1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Biblioteca1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void ejecutarScript(File f) {
        try {
            con.setAutoCommit(false);
            Statement stmt = con.createStatement();
            BufferedReader br = new BufferedReader(new FileReader(f));
            String linea;
            while ((linea = br.readLine()) != null) {
                stmt.execute(linea);
            }
            con.commit();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Biblioteca1.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Biblioteca1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Biblioteca1.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void mostrarLibrosPrestados() {
        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT libro.id, libro.nombre as 'Nombre libro', autor, editorial, categoria, usuario.nombre as 'Nombre Usuario', usuario.apellidos, fechaPrestamo "
                    + "FROM libro INNER JOIN prestamos ON idLibro = libro.id "
                    + "INNER JOIN usuario ON idUsuario = usuario.id ORDER BY libro.nombre");
            while (rs.next()) {
                String id = rs.getString("id");
                String nombreLibro = rs.getString("Nombre Libro");
                String autor = rs.getString("autor");
                String editorial = rs.getString("editorial");
                String categoria = rs.getString("categoria");
                String nombreUsuario = rs.getString("Nombre Usuario");
                String apellidosUsuario = rs.getString("apellidos");
                String fechaPrestamo = rs.getString("fechaPrestamo");
                System.out.println("ID: " + id + ", Nombre Libro: " + nombreLibro + ", Autor: " + autor
                        + ", Editorial: " + editorial + ", Categoria: " + categoria
                        + ", Nombre Usuario: " + nombreUsuario + ", Apellidos Usuario: " + apellidosUsuario
                        + ", Fecha Préstamo: " + fechaPrestamo);

            }
            System.out.println();
        } catch (SQLException ex) {
            Logger.getLogger(Biblioteca1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void mostrarLibrosPorCategoria() {
        try {
            mostrarCategorias();
            int numCategoria = LeerDatosTeclado.leerInt("Introduce el número de una categoría:", 1, 9);
            System.out.println();
            String consulta = "SELECT * FROM libro WHERE UPPER(categoria) = UPPER(?)";
            PreparedStatement stmt = con.prepareStatement(consulta);
            stmt.setInt(1, numCategoria);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                do {
                    String id = rs.getString("id");
                    String nombre = rs.getString("nombre");
                    System.out.println("Id: " + id + ", Nombre: " + nombre);
                } while (rs.next());
                System.out.println();
            } else {
                System.out.println("No se ha encontrado ningún libro con esa categoría");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Biblioteca1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
