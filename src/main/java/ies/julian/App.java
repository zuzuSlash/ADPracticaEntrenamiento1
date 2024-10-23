package ies.julian;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        conectarEmbebido();

        File propertiesFile = new File("./src/main/resources/config.properties");
        FileReader fileReader = new FileReader(propertiesFile);
        Properties props = new Properties();
        props.load(fileReader);
        String driver = props.getProperty("database.driver");
        String url = props.getProperty("database.url");
        String user = props.getProperty("database.user");
        String password = props.getProperty("database.password");
        System.out.println("Driver : " + driver);
        System.out.println("Url : " + url);
        System.out.println("User : " + user);
        System.out.println("Password : " + password);
    }
}


