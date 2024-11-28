package biblioteca1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Biblioteca1 {

    private static final String URL = "jdbc:mariadb://localhost:3306/BIBLIOTECA";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            String categoria = LeerDatosTeclado.leerString("Introduce una categoría");
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM libro WHERE UPPER(categoria) = UPPER(" + categoria + ")");
            System.out.println(rs);
            /*
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
            */
        } catch (SQLException ex) {
            Logger.getLogger(Biblioteca1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Biblioteca1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

//Opción menú 1 - Vaciar todas las tablas
/*
Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement stmt = con.createStatement();
            String[] tablas = {"prestamos", "usuario", "libro", "categoria"};
            con.setAutoCommit(false);
            for(String tabla : tablas){
                String sql = "DELETE FROM " + tabla;
                stmt.addBatch(sql);
            }
            stmt.executeBatch();
            con.commit();
 */
//Opcion menú 2 - Rellenar tabla categorías con valores por defecto
/*
Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement stmt = con.createStatement();
            //Cargar fichero script (datos.sql)
            BufferedReader br = new BufferedReader(new FileReader("./ficheros/categorias.sql"));
            String linea;
            while ((linea = br.readLine()) != null) {
                stmt.execute(linea);
            }
 */
//Opción menú 3 - Cargar fichero script (datos.sql)
/*
Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement stmt = con.createStatement();
            File f = new File("./ficheros/datos.sql");
            //Cargar fichero script (datos.sql)
            BufferedReader br = new BufferedReader(new FileReader(f));
            String linea;
            while ((linea = br.readLine()) != null) {
                stmt.execute(linea);
            }
 */
//Opción menú 4 - Mostrar todos los libros prestados, en esa consulta se tiene que mostrar toda la información del libro (incluye la categoría del libro en modo texto), junto con  el usuario (nombre y apellidos) y la fecha de préstamo. Todo ordenado por nombre del libro
/*
Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
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
 */

//Opción 5 - Muestra todos los libros de una determinada categoría, preguntando ésta por terminal
