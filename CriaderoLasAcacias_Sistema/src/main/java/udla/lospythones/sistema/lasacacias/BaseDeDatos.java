package udla.lospythones.sistema.lasacacias;

import java.sql.*;

public class BaseDeDatos {
    //string de conexión
    String url = "jdbc:mysql://localhost:3306/las_acacias_sistema"; //dirección a la base de datos
    String user = "root";   //usuario de ingreso
    String password = "sasa"; //contraseña

    //Se establece conexión con la base de datos
    public Connection connectToDataBase(){ //se crea un metodo de conexion para poder usarlo en otra clases mediante un objeto
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
