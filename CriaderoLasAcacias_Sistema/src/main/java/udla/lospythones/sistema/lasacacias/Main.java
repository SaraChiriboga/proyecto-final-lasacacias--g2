package udla.lospythones.sistema.lasacacias;

import udla.lospythones.sistema.lasacacias.usuarios.Inicio;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        ImageIcon logoAcacias = new ImageIcon("C:\\Users\\saril\\Desktop\\OneDrive - Universidad de Las Américas\\3\\PROGRAMACION II\\Proyecto Final\\logoacacias.jpg");//Dirección del logo de la Hacienda Las Acacias
        JOptionPane.showMessageDialog(null, "Bienvenid@!" +
                "\n\t\t\t Hacienda \"Las Acacias\"","Bienvenida", JOptionPane.PLAIN_MESSAGE, logoAcacias);
        Inicio inicio = new Inicio();
        inicio.paginadeInicio();
    }
}
