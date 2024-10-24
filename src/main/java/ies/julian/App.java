package ies.julian;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class App {
    public App() throws FileNotFoundException {
    }

    private static void conectarEmbebido() throws SQLException {
        String connectionstring =
                "jdbc:derby:/home/juljimgar/JJGdb;create=false;user=julian;password=Majnga81$";
        Connection conn = null;
        conn = DriverManager.getConnection(connectionstring);

        Statement s = conn.createStatement ();
        PreparedStatement psDelete = null;
        PreparedStatement psInsertar = null;
        PreparedStatement psUpdate = null;

        psDelete = conn.prepareStatement( "delete from APP.TABLE_USUARIO");

        /*
        psInsertar = conn.prepareStatement( "insert into APP.TABLE_USUARIO(COLUMN_NOMBRE, COLUMN_APELLIDO, COLUMN_MAIL, COLUMN_ACTIVO) values (?,?,?,?)");
        psInsertar.setString(1, "Cristian");
        psInsertar.setString(2, "Rodriguez Ruiz");
        psInsertar.setString(3, "RodRuiCris@gmail.com");
        psInsertar.setBoolean(4, true);

         */
        psDelete.executeUpdate();
        s.close();


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
        conn.close();



        conectarEmbebido();
    }

}


