package udla.lospythones.sistema.lasacacias.caballos;

import udla.lospythones.sistema.lasacacias.BaseDeDatos;

import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class Criadero {
    //atributos
    private String nombreCampo;
    private String descripcion;

    BaseDeDatos db = new BaseDeDatos(); //se inicia una conexión con la base de datos
    Connection acacias = db.connectToDataBase();
    BufferedReader sc = new BufferedReader(new InputStreamReader(System.in)); //lector

    //GETTERS Y SETTERS
    public String getNombreCampo() {
        return nombreCampo;
    }

    public void setNombreCampo(String nombreCampo) {
        this.nombreCampo = nombreCampo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // metodo para agregar campo
    public void agregarCampo() throws IOException, UniqueValueException, SQLException{
        boolean i = true;
        while (i) {
            System.out.printf("Nombre del campo: ");
            setNombreCampo(sc.readLine());
            System.out.printf("Descripción: ");
            setDescripcion(sc.readLine());

            // asegurarse de que el nombre del campo sea único mediante una excepción
            try (Statement verificar = acacias.createStatement()) {
                String similar = "SELECT * FROM criadero WHERE nombre = '" + this.nombreCampo + "'";
                ResultSet rs = verificar.executeQuery(similar);
                if (rs.next()) {
                    throw new UniqueValueException("El nombre de cada campo es único! Por favor, ingrese un valor diferente...");
                } else {
                    // ingreso de información a la base de datos
                    String add = "INSERT INTO criadero (nombre, descripcion) VALUES (?, ?)";
                    try (PreparedStatement agregar = acacias.prepareStatement(add)) {
                        agregar.setString(1, getNombreCampo());
                        agregar.setString(2, getDescripcion());
                        agregar.executeUpdate();
                        System.out.println("Campo agregado exitosamente.");
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (UniqueValueException un) {
                System.out.println(un.getMessage());
            }

            // iteración con boolean
            System.out.println("Ingresar otro campo? (1.Si - 2.No)");
            int siono = Integer.parseInt(sc.readLine());
            if (siono == 2) {
                i = false;
            }
        }
    }

    //metodo para eliminar campo
    public void eliminarCampo() throws IOException {
        System.out.printf("Ingrese el nombre del campo: ");
        String name = sc.readLine();
        String instruccion = "DELETE FROM criadero WHERE nombre = '"+name+"'"; //esta solicita que se elimine de la tabla criadero
        try(PreparedStatement borrar = acacias.prepareStatement(instruccion)) {
            System.out.println("Campo eliminado exitosamente!");
            borrar.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //mostrar información
        mostrarCampos();
    }

    //metodo para transferir informacion de la base de datos a una ventana
    public DefaultTableModel transferCampos(){
        String sql = "SELECT * FROM criadero";
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Nombre del campo"); //se agregan y se definen los nombres de las columnas
        model.addColumn("Descripción");

        try (Statement st = acacias.createStatement();
             ResultSet rs = st.executeQuery(sql)) { //se crea una lista que contiene información sobre la tabla
            while (rs.next()) { //recorre la lista recuperando detalles y los almacena en un arreglo
                Object[] fila = { //un arreglo Object almacena datos de diversos tipos (int, double, String, etc)
                        rs.getString("nombre"),
                        rs.getString("descripcion")};
                model.addRow(fila); //una vez que se llena el arreglo con los datos, se agrega una fila y se repite el proceso
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    //metodo para mostrar la tabla en una ventada
    public void mostrarCampos(){
        TablaCriadero tabla = new TablaCriadero();
        System.out.println("Abriendo registro. Corrobore los cambios...");
        
        // Usar un bloqueo para esperar a que la ventana sea cerrada
        final Object lock = new Object();

        tabla.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                synchronized (lock) {
                    lock.notify(); // Notificar que la ventana fue cerrada
                }
            }
        });

        tabla.setVisible(true);

        // Bloquear el hilo principal hasta que se cierre la ventana
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
        }
     }
    }

}
