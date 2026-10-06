package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// estabelcemos la conexion con la base de datos
public class miConexion {
    private static String url = "jdbc:mariadb://localhost:3306/universidad_grupo15";
    private static String usuario = "root";
    private static String password = "";

    private static Connection conexion = null; // lo importante como dice el profe

    public static Connection getConexion() {
        if (conexion == null) { // si es la primera vez
            try {

                Class.forName("org.mariadb.jdbc.Driver");

                conexion = DriverManager.getConnection(url, usuario, password);
            } catch (ClassNotFoundException e) {
                System.err.println("No se puede conectar o cargar el driver de MariaDB: " + e.getMessage());
            } catch (SQLException e) {
                System.err.println("No se puede conectar a la base de datos: " + e.getMessage());
            }
        }
        return conexion;
    }
}
    
