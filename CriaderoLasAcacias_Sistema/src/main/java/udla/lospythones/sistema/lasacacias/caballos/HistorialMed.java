package udla.lospythones.sistema.lasacacias.caballos;

import udla.lospythones.sistema.lasacacias.BaseDeDatos;

import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class HistorialMed {
    //atributos
    private int id;
    private String vacuna;
    private String desparacitacion;
    private String enfermedad;

    BufferedReader sc = new BufferedReader(new InputStreamReader(System.in)); //lector
    BaseDeDatos db = new BaseDeDatos(); //conexión con la base de datos
    Connection acacias = db.connectToDataBase();

    //getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVacuna() {
        return vacuna;
    }

    public void setVacuna(String vacuna) {
        this.vacuna = vacuna;
    }

    public String getDesparacitacion() {
        return desparacitacion;
    }

    public void setDesparacitacion(String desparacitacion) {
        this.desparacitacion = desparacitacion;
    }

    public String getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(String enfermedad) {
        this.enfermedad = enfermedad;
    }

    //metodo para ACTUALIZAR el registro o AGREGAR un historial
    //en este proceso se ingresan los datos desde el inicio en caso de que se busque actualizar
    public void actualizarRegistro() throws IOException, SQLException {
        boolean i = true;
        boolean confirmar = true;
        while (i) {
            try {
                while (confirmar) {//ingreso de datos
                    System.out.println("Ingrese los siguientes datos...");
                    System.out.printf("ID: ");
                    setId(Integer.parseInt(sc.readLine()));

                    //verificar id
                    String verificar = "SELECT * FROM caballo WHERE  idcaballo = '"+ getId() +"'";
                    Statement st = acacias.createStatement();
                    ResultSet rs = st.executeQuery(verificar);

                    if (rs.next()){
                        System.out.printf("Vacunas: ");
                        setVacuna(sc.readLine());
                        System.out.printf("Desparacitación: ");
                        setDesparacitacion(sc.readLine());
                        System.out.printf("Enfermedades: ");
                        setEnfermedad(sc.readLine());

                        //se insertan los datos en la base de datos
                        String s = "INSERT INTO historialmed (idCaballo, vacuna, desparacitacion, enfermedad) VALUES (?, ?, ?,?)";
                        try (PreparedStatement insertar = acacias.prepareStatement(s)) {
                            insertar.setInt(1, getId()); //se ubica información segun el tipo de valor que admite cada columna
                            insertar.setString(2, getVacuna());
                            insertar.setString(3, getDesparacitacion());
                            insertar.setString(4, getEnfermedad());
                            insertar.executeUpdate(); //actualización
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                        System.out.printf("La información ingresada es correcta? Una vez ingresada no será posible modificarla. (1. SI - 2.NO)");
                        int conf = Integer.parseInt(sc.readLine());
                        if (conf == 1) {
                            confirmar = false;
                        }
                    } else{
                        System.out.println("No hay un caballo con tal id en el criadero...");
                        confirmar = false;
                    }
                }


            } catch (NumberFormatException e) {
                System.out.println("Ingrese un valor valido!");
            }

            //iteracion
            System.out.printf("Ingresar otro historial? (1. Si - 2.No)");
            int siono = Integer.parseInt(sc.readLine());
            if (siono == 2) {
                i = false;
            }
        }
    }

    //metodo para obtener el modelo de tabla con los historiales medicos (modelo de tabla)
    public DefaultTableModel transferHistoriales(){
        String l = "SELECT * FROM historialmed";
        DefaultTableModel historial = new DefaultTableModel();
        historial.addColumn("ID"); //CONSIDERACION: se puede repetir el id, ya que cada visita al veterinario se genera información del mismo caballo
        historial.addColumn("Vacuna");
        historial.addColumn("Desparacitacion");
        historial.addColumn("Enfermedad");

        try (Statement st = acacias.createStatement();
             ResultSet rs = st.executeQuery(l)) { //se crea una lista que contiene información sobre la tabla
            while (rs.next()) { //recorre la lista recuperando detalles y los almacena en un arreglo
                Object[] fila = { //un arreglo Object almacena datos de diversos tipos (int, double, String, etc)
                        rs.getInt("idCaballo"),
                        rs.getString("vacuna"),
                        rs.getString("desparacitacion"),
                        rs.getString("enfermedad")

                };
                historial.addRow(fila); //una vez que se llena el arreglo con los datos, se agrega una fila y se repite el proceso
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return historial;
    }

    //metodo para mostrar los historiales medicos
    public void mostrarHistoriales(){
        TablaMed historiales = new TablaMed();
        System.out.println("Abriendo registro. Corrobore los cambios...");
        
        // Usar un bloqueo para esperar a que la ventana sea cerrada
        final Object lock = new Object();

        historiales.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                synchronized (lock) {
                    lock.notify(); // Notificar que la ventana fue cerrada
                }
            }
        });

        historiales.setVisible(true);

        // Bloquear el hilo principal hasta que se cierre la ventana
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
        }
     }
    }

    //metodo para eliminar un historial en base al id
    public void eliminarHistorial(int id) throws SQLException { //PRECAUCION!!! este metodo borra todos los historiales del caballo
        String verificacion = "SELECT * FROM historialmed WHERE idCaballo = '"+ id+"'";
        String instruccion = "DELETE FROM historialmed WHERE idCaballo = '"+id+"'"; //esta solicita que se elimine de la tabla caballo aquella fila que contenga la id ingresada como parametro

        //verificar si hay historiales con la id ingresada
        Statement st = acacias.createStatement();
        ResultSet verificarID =  st.executeQuery(verificacion);

        if (verificarID.next()){
            try(PreparedStatement borrar = acacias.prepareStatement(instruccion)) {
                System.out.println("Historial eliminado exitosamente!");
                borrar.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else{
            System.out.println("No se encontraron historiales con tal id...");
        }
    }

    //metodo para obtener el historial medico de un caballo basado en su id (modelo de tabla)
    public DefaultTableModel buscarHistorial() throws IOException {
        //se debe diseñar un nuevo modelo de tabla donde solo se encuentre información del caballo solicitado
        DefaultTableModel m = new DefaultTableModel();
        try {
            System.out.printf("ID del caballo: ");
            int buscar = Integer.parseInt(sc.readLine());
            String sql = "SELECT * FROM historialmed WHERE idCaballo ='" + buscar + "'";
            m.addColumn("ID"); //se agregan y se definen los nombres de las columnas
            m.addColumn("Vacuna");
            m.addColumn("Desparacitacion");
            m.addColumn("Enfermedad");

            try (Statement st = acacias.createStatement();
                 ResultSet rs = st.executeQuery(sql)) { //se crea una lista que contiene información sobre la tabla
                while (rs.next()) { //recorre la lista recuperando detalles y los almacena en un arreglo
                    Object[] fila = { //un arreglo Object almacena datos de diversos tipos (int, double, String, etc)
                            rs.getInt("idCaballo"),
                            rs.getString("vacuna"),
                            rs.getString("desparacitacion"),
                            rs.getString("enfermedad")
                    };
                    m.addRow(fila); //una vez que se llena el arreglo con los datos, se agrega una fila y se repite el proceso
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (NumberFormatException e) {
            System.out.println("Ingrese un valor válido!");
        }
        return m;
    }

}
