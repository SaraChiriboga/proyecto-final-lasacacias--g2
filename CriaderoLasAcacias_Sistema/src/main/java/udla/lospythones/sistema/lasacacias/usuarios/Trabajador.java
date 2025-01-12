package udla.lospythones.sistema.lasacacias.usuarios;


import udla.lospythones.sistema.lasacacias.BaseDeDatos;
import udla.lospythones.sistema.lasacacias.caballos.Caballo;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Trabajador extends Usuario {
    private String cargo;
    private double salario;
    private String turno;
    public Trabajador() {
        super(1, "", "", "");
    }

    //instancias de clases con metodos a los que tiene acceso un trabajador
    Caballo caballito = new Caballo();

    // Constructor con parámetros para inicializar todos los atributos del trabajador.
    public Trabajador(int idUser, String nombre, String contrasena, String CI, String cargo, double salario, String turno) {
        super(idUser, nombre, contrasena, CI);
        this.cargo = cargo;
        this.salario = salario;
        this.turno = turno;
    }

    // Métodos getter y setter para los atributos propios de la clase.
    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    // Metodo que presenta opciones al usuario y devuelve un valor basado en la selección.
    @Override
    public int opcionesUsuario(){

        String[] object={"", "Mostrar caballos", "Salir"};
        Object option= null;
        option= JOptionPane.showInputDialog(null, "Elegir..", "Opciones a realizar", JOptionPane.QUESTION_MESSAGE,null, object,object[0]);
        // Si no se selecciona ninguna opción, se muestra un mensaje y se vuelve a llamar al método.

        if (option==null){
            JOptionPane.showMessageDialog(null, "Por favor, escoja una opción");
            opcionesUsuario();
        }

        switch (option.toString()){
            case "Mostrar caballos":
                caballito.impresionCaballos();
                break;
            case "Salir":
                return 1;
        }
        return 0;
    }

    // Metodo para crear un nuevo usuario trabajador.
    @Override
    public void nuevoUsuario() throws IOException {
        BaseDeDatos db = new BaseDeDatos(); //se inicia una conexión con la base de datos para emplearla en esta clase, se hace el mismo procedimiento en las demás
        Connection acacias = db.connectToDataBase();

        BufferedReader sc = new BufferedReader(new java.io.InputStreamReader(System.in));

        System.out.println("======================================");
        System.out.println("Ingrese los datos del nuevo usuario...");
        System.out.println("======================================");

        try {
            // Captura del ID del usuario.
            System.out.print("ID: ");
            setIdUser(Integer.parseInt(sc.readLine()));
            // Captura del salario, se verifica que sea un número válido.
            System.out.println("Salario: ");
            setSalario(Double.parseDouble(sc.readLine()));
        }catch (NumberFormatException e){
            // Si se produce un error en el formato, se solicita que se reingresen los datos.
            System.out.println("Es ncesario que ingrese todos los datos solicitados");
            nuevoUsuario();
        }

        // Captura del resto de datos del usuario, los cuales no son posibles de generar Errores
        System.out.println("Nombre: ");
        setNombre(sc.readLine());
        System.out.println("Contraseña: ");
        setContrasena(sc.readLine());
        System.out.println("CI: ");
        setCI(sc.readLine());
        System.out.println("Cargo");
        setCargo(sc.readLine());

        System.out.println("Turno: \n 1. Diurno\n 2.Nocturno");
        int ans= 0;
        ans=Integer.parseInt(sc.readLine());
        switch (ans) {
            case 1:
                setTurno("Diurno");
                break;
            case 2:
                setTurno("Nocturno");
                break;
            default:
                System.out.println("Opción inválida. Turno predeterminado a 'Diurno'.");
                setTurno("Diurno");
                break;
        }

        // Ingreso de los datos del trabajador en la base de datos.
        String datos = "INSERT INTO trabajador(idUser, nombre, contrasena, CI, cargo, salario, turno) VALUES(?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement insertar = acacias.prepareStatement(datos)){

            insertar.setInt(1, getIdUser());
            insertar.setString(2, getNombre());
            insertar.setString(3, getContrasena());
            insertar.setString(4, getCI());
            insertar.setString(5, getCargo());
            insertar.setDouble(6, getSalario());
            insertar.setString(7, getTurno());

            insertar.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        // Mensaje de confirmación al crear un nuevo usuario.
        System.out.println("Nuevo Usuario creado con exito" + "\n ID: " + getIdUser() + "\n Nombre: " + getNombre()
                + "\n CI: " + getCI());    }
    }


