package ies.julian;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class App {

    /*
    private static void conectarEmbebido() throws SQLException {
        String connectionstring = "jdbc:derby:/home/juljimgar/JJGdb;create=false;user=julian;password=Majnga81$";
        Connection conn = null;
        conn = DriverManager.getConnection(connectionstring);

        if (conn != null) {
            PreparedStatement pstm = conn.prepareStatement("SELECT * FROM APP.TABLE_USUARIO");
            pstm.execute();
            pstm.close();
            conn.close();
            System.out.println("SENTENCIA SQL EFECTUADA CORRECTAMENTE");
        } else {
            throw new SQLException("No se ha podido conectar");
        }
    }

     */

    public static void menuCRUD(Connection conn) throws SQLException {
        Statement s = conn.createStatement();
        PreparedStatement psDelete = null;
        PreparedStatement psInsertar = null;
        PreparedStatement psUpdate = null;
        PreparedStatement psConsultar = null;

        Scanner scanner = new Scanner(System.in);
        String opcion = "";

        while (!opcion.equals("5")) {
            System.out.println("Seleccione una opción:\n1. Escribir\n2. Leer\n3. Modificar\n4. Eliminar\n5. Salir");
            opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    System.out.println("Escribir");
                    psInsertar = conn.prepareStatement("INSERT INTO APP.TABLE_USUARIO(COLUMN_NOMBRE, COLUMN_APELLIDO, COLUMN_MAIL, COLUMN_ACTIVO, COLUMN_USUARIO, COLUMN_CONTRASEÑA) VALUES (?,?,?,?,?,?)");
                    System.out.print("Escribe un nombre: ");
                    psInsertar.setString(1, scanner.nextLine());
                    System.out.print("Escribe apellido: ");
                    psInsertar.setString(2, scanner.nextLine());
                    System.out.print("Escribe mail: ");
                    psInsertar.setString(3, scanner.nextLine());
                    System.out.print("Escribe activo (true/false): ");
                    psInsertar.setBoolean(4, scanner.nextBoolean());
                    scanner.nextLine();
                    System.out.print("Escribe usuario: ");
                    psInsertar.setString(5, scanner.nextLine());
                    System.out.print("Escribe contraseña: ");
                    String contra = scanner.nextLine();
                    psInsertar.setString(6, contra);
                    StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
                    encryptor.setAlgorithm("PBEWithMD5AndDES");
                    encryptor.setPassword(contra);
                    psInsertar.executeUpdate();
                    System.out.println("Registro insertado con éxito.");
                    break;
                case "2":
                    System.out.println("Leer");
                    psConsultar = conn.prepareStatement("SELECT * FROM APP.TABLE_USUARIO");
                    ResultSet rs = psConsultar.executeQuery();
                    while (rs.next()) {
                        System.out.println("Nombre usuario: " + rs.getString("COLUMN_NOMBRE")
                                + "\nApellido usuario: " + rs.getString("COLUMN_APELLIDO")
                                + "\nEmail usuario: " + rs.getString("COLUMN_MAIL")
                                + "\nActivo: " + rs.getString("COLUMN_ACTIVO")
                                + "\nUsuario usuario: " + rs.getString("COLUMN_USUARIO")
                        );

                    }
                    System.out.println("Consulta realizada con exito.");
                    break;
                case "3":
                    System.out.println("Modificar");

                    break;
                case "4":
                    System.out.println("Eliminar");

                    break;
                case "5":
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida, por favor intenta de nuevo.");
                    break;
            }
        }
        s.close();
        scanner.close();
    }





    public static void main(String[] args) throws SQLException, IOException {

        File f = new File((Objects.requireNonNull(App.class.getResource("/config.properties")).getPath()));
        FileReader fr = new FileReader(f);
        Properties props = new Properties();
        props.load(fr);
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        encryptor.setPassword("clavebd");
        String connectionstring = props.getProperty("database.path");
        String user = encryptor.decrypt(props.getProperty("database.user"));
        String password = encryptor.decrypt(props.getProperty("database.password"));
        Connection conn = null;
        conn = DriverManager.getConnection(connectionstring+";"+"user="+user+";"+"password="+password+";");

        if(conn!=null){
            System.out.println("Conectado");
        }

        menuCRUD(conn);
        conn.close();



        //conectarEmbebido();
    }

}


