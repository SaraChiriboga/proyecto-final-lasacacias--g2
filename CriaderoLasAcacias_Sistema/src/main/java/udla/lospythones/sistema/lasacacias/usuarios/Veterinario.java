package udla.lospythones.sistema.lasacacias.usuarios;

import udla.lospythones.sistema.lasacacias.BaseDeDatos;
import udla.lospythones.sistema.lasacacias.caballos.Caballo;
import udla.lospythones.sistema.lasacacias.caballos.HistorialMed;
import udla.lospythones.sistema.lasacacias.caballos.TablaMed;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Veterinario extends Usuario {

    // Atributos específicos de la clase Veterinario
    private String registroSenecyt;
   private String especializacion;
   private int telefono;

   //instancias de clases que contienen los métodos a los que puede acceder el vet
    Caballo caballito = new Caballo();
    HistorialMed historiales = new HistorialMed();
    TablaMed hist = new TablaMed();

    BufferedReader sc = new BufferedReader(new InputStreamReader(System.in)); //lector

    // Constructor por defecto
    public Veterinario() {
        super(0, "", "", "");
    }

    // Constructor con parámetros, incluyendo atributos heredados y específicos
    public Veterinario(int idUser, String nombre, String contrasena, String CI, String registroSenecyt, String especializacion, String experiencia, int telefono) {
        super(idUser, nombre, contrasena, CI);
        this.registroSenecyt = registroSenecyt;
        this.especializacion = especializacion;
        this.telefono = telefono;
    }

    // Métodos getter y setter para los atributos específicos
    public String getRegistroSenecyt() {
        return registroSenecyt;
    }

    public void setRegistroSenecyt(String registroSenecyt) {
        this.registroSenecyt = registroSenecyt;
    }

    public String getEspecializacion() {
        return especializacion;
    }

    public void setEspecializacion(String especializacion) {
        this.especializacion = especializacion;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    @Override
    public int opcionesUsuario() throws SQLException, IOException {
        System.out.println("+-----------------------------------------------+");
        System.out.println("| Se encuentra en la consola de **VETERINARIO** |");
        System.out.println("+-----------------------------------------------+");
        // Opciones disponibles para el veterinario
        String[] object={"", "Agregar historial médico","Eliminar todos los historiales de un caballo", "Buscar historial","Mostrar historiales","Mostrar caballos", "Salir"};
        Object option= null;
        option= JOptionPane.showInputDialog(null, "Elegir..", "Opciones a realizar", JOptionPane.QUESTION_MESSAGE,null, object,object[0]);
        if (option==null){
            JOptionPane.showMessageDialog(null, "Por favor, escoja una opción");
            opcionesUsuario();//Bucle para que el usuario escoja una opción a realizar
        }

        // Switch donde se ejecutará acciones según la opción seleccionada
        switch (option.toString()){
            case "Agregar historial médico":
                historiales.actualizarRegistro();
                break;
            case "Eliminar todos los historiales de un caballo":
                try{
                    System.out.printf("ID del caballo: ");
                    int deletehist = Integer.parseInt(sc.readLine());
                    historiales.eliminarHistorial(deletehist);
                }catch (NumberFormatException e){
                    System.out.println("Es necesario que coloque el id del caballo...");}
                break;
            case "Buscar historial":
                hist.mostrarHistorialEncontrado();
                break;
            case "Mostrar historiales":
                historiales.mostrarHistoriales();
                break;
            case "Mostrar caballos":
                caballito.impresionCaballos();
                break;
            case "Salir":
                return 1;//Se retorna para culminar el sistema
        }
        return 0;
    }

    @Override
    public void nuevoUsuario() throws IOException {

            BaseDeDatos db = new BaseDeDatos(); //se inicia una conexión con la base de datos
            Connection acacias = db.connectToDataBase();

            //Asignación de los valores ingresados por el usuario
            BufferedReader sc = new BufferedReader(new java.io.InputStreamReader(System.in));
            System.out.println("======================================");
            System.out.println("Ingrese los datos del nuevo usuario...");
            System.out.println("======================================");
            try {
                System.out.print("ID: ");//Ingreso de valores enteros los cuales podrían causar errores
                setIdUser(Integer.parseInt(sc.readLine()));
            }catch (NumberFormatException e){
                System.out.println("Es ncesario que ingrese el ID del usuario");
                nuevoUsuario();
            }
            System.out.println("Nombre: ");
            setNombre(sc.readLine());
            System.out.println("Contraseña: ");
            setContrasena(sc.readLine());
            System.out.println("CI: ");
            setCI(sc.readLine());
            System.out.println("Registro SENECYT: ");
            setRegistroSenecyt(sc.readLine());
            System.out.println("Especialización: ");
            setEspecializacion(sc.readLine());
            System.out.println("Teléfono: ");
            setTelefono(Integer.parseInt(sc.readLine()));

            //Ingreso de datos en la base de datos de MYSQL
            String datos = "INSERT INTO veterinaria(idUser, nombre, contrasena, CI, registroSenecyt, Especializacion, telefono) VALUES(?, ?, ?, ?, ?, ?, ?)";
            try(PreparedStatement insertar = acacias.prepareStatement(datos)){

                insertar.setInt(1, getIdUser());
                insertar.setString(2, getNombre());
                insertar.setString(3, getContrasena());
                insertar.setString(4, getCI());
                insertar.setString(5, getRegistroSenecyt());
                insertar.setString(6, getEspecializacion());
                insertar.setInt(7, getTelefono());

                insertar.executeUpdate();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }

            //Mensaje de confirmación sobre el ingreso de datos.
            System.out.println("Nuevo Usuario creado con exito" + "\n ID: " + getIdUser() + "\n Nombre: " + getNombre()
                    + "\n CI: " + getCI());    }
        }

