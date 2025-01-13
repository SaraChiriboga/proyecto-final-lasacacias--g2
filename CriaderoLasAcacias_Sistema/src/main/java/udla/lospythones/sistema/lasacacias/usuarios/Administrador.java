package udla.lospythones.sistema.lasacacias.usuarios;


import udla.lospythones.sistema.lasacacias.*;
import udla.lospythones.sistema.lasacacias.caballos.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class Administrador extends Usuario {
    BaseDeDatos db = new BaseDeDatos(); //se inicia una conexión con la base de datos
    Connection acacias = db.connectToDataBase();

    //el administrador tiene la posibilidad de acceso a los registros de los caballos, historiales medicos, campos del criadero y lista de usuarios
    Caballo caballito = new Caballo(); //se crean instancias de cada clase
    HistorialMed historiales = new HistorialMed();
    Criadero campos = new Criadero();
    Tabla caballos = new Tabla(); //estas instancias en particular porque se requiere un metodo especifico que trabaja en conjunto con la clase caballo
    TablaMed histmed = new TablaMed();
    BufferedReader sc = new BufferedReader(new InputStreamReader(System.in)); //lector

    public Administrador() {
        super(0, "", "", "");
    }

    // Constructor con parámetros para inicializar todos los atributos heredados.
    public Administrador(int idUser, String nombre, String contrasena, String CI) {
        super(idUser, nombre, contrasena, CI);
    }

    // Metodo que presenta las opciones disponibles para el administrador.
    @Override
    public int opcionesUsuario() throws IOException, SQLException {
        //Indica que estas en modo administrador
        System.out.println("+-------------------------------------------------+");
        System.out.println("| Se encuentra en la consola de **ADMINISTRADOR** |");
        System.out.println("+-------------------------------------------------+");

        // Opciones que se muestran al administrador.
        String[] object={"", "Creación de usuario","Eliminación de usuario", "Mostrar usuarios", "Agregar Caballo","Editar información caballo", "Eliminar Caballo","Buscar Caballo", "Mostrar caballos","Agregar/Actualizar historial médico", "Eliminar todos los historiales de un caballo", "Buscar historial", "Mostrar historiales", "Agregar un campo", "Eliminar campo", "Mostrar campos", "Salir"};
        Object option= null;
        option= JOptionPane.showInputDialog(null, "Elegir..", "Opciones a realizar", JOptionPane.QUESTION_MESSAGE,null, object,object[0]);

        // Si no se selecciona una opción válida, se solicita nuevamente.
        if (option==null){
            JOptionPane.showMessageDialog(null, "Por favor, escoja una opción");
            opcionesUsuario();
        }

        // Crea un nuevo usuario basado en el rol seleccionado.
        switch (option.toString()){
            case "Creación de usuario":
                String[] object1 ={"Administrador","Veterinario","Trabajador"};
                int ans = 0;
                while (true){
                    ans=JOptionPane.showOptionDialog(null, "Escoger rol del usuario a crear...", "Seleccion del rol",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, object1, null)+1;
                    if (ans!=0){
                        break;
                    }
                    JOptionPane.showMessageDialog(null, "Por favor, escoja una opción");
                }
                switch (ans){
                    case 1:
                        Administrador administrador2 = new Administrador();
                        administrador2.nuevoUsuario();
                        break;
                    case 2:
                        Veterinario veterinario = new Veterinario();
                        veterinario.nuevoUsuario();
                        break;
                    case 3:
                        Trabajador trabajador = new Trabajador();
                        trabajador.nuevoUsuario();
                        break;
                }
                break;

            case "Eliminación de usuario":
                String[] object2 ={"Administrador","Veterinario","Trabajador"};
                int ans1 = 0;
                while (true){
                    ans1 =JOptionPane.showOptionDialog(null, "Escoger rol del usuario a eliminar...", "Seleccion del rol",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, object2, null)+1;
                    if (ans1 !=0){
                        break;
                    }
                    JOptionPane.showMessageDialog(null, "Por favor, escoja una opción");
                }

                // Determina el tipo de usuario basado en la opción seleccionada.
                String tipoUser;
                if(ans1==1){
                    tipoUser = "administrador";
                } else if (ans1==2) {
                    tipoUser = "veterinaria";
                }else {
                    tipoUser = "trabajador";
                }
                eliminarUsuario(tipoUser);
                break;
            case "Mostrar usuarios":
                mostrarUsers();
                break;
            case "Agregar Caballo":
                caballito.obtencionDetalles(); //se ejecuta el metodo de obtencion
                break;
            case "Editar información caballo":
                try{
                    System.out.printf("ID del caballo: ");
                    int edit = Integer.parseInt(sc.readLine());
                    caballito.editarCaballo(edit);
                }catch (NumberFormatException e){System.out.println("Es necesario que coloque el id del caballo...");}
                break;
            case "Eliminar Caballo":
                try {
                    System.out.printf("ID del caballo: ");
                    int id = Integer.parseInt(sc.readLine());
                    caballito.eliminarCaballo(id);
                }catch (NumberFormatException e){
                    System.out.println("Es necesario que coloque el id del caballo...");
                }
                break;
            case "Buscar Caballo":
                caballos.mostrarResultadoBusqueda();
                break;
            case "Mostrar caballos":
                caballito.impresionCaballos();
                break;
            case "Agregar/Actualizar historial médico":
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
                histmed.mostrarHistorialEncontrado();
                break;
            case "Mostrar historiales":
                historiales.mostrarHistoriales();
                break;
            case "Agregar un campo":
                campos.agregarCampo();
                break;
            case "Eliminar campo":
                campos.eliminarCampo();
                break;
            case "Mostrar campos":
                campos.mostrarCampos();
                break;
            case "Salir":
                return 1;
        }
        return 0; // Retorna por defecto si no se selecciona "Salir".
    }

    // Metodo para crear un nuevo usuario administrador.
    @Override
    public void nuevoUsuario() throws IOException {

        BufferedReader sc = new BufferedReader(new java.io.InputStreamReader(System.in));
        System.out.println("======================================");
        try {
            // Solicita el ID del usuario y lo valida, debido a que estos datos son posibles de generar error
            System.out.print("ID: ");
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

        // Inserta los datos del usuario en la base de datos.
        String datos = "INSERT INTO administrador(idUser, nombre, contrasena, CI) VALUES(?, ?, ?, ?)";
        try(PreparedStatement insertar = acacias.prepareStatement(datos)){

            insertar.setInt(1, getIdUser());
            insertar.setString(2, getNombre());
            insertar.setString(3, getContrasena());
            insertar.setString(4, getCI());

            insertar.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        // Confirma la creación del usuario.
        System.out.println("Nuevo Usuario creado con exito" + "\n ID: " + getIdUser() + "\n Nombre: " + getNombre()
        + "\n CI: " + getCI());
    }

    // Metodo para eliminar un usuario de la base de datos.
    public void eliminarUsuario(String tipoUSer) throws SQLException {
        try{

            // Solicita el ID del usuario a eliminar.
            int id = Integer.parseInt(JOptionPane.showInputDialog(null, "ID del usuario a eliminar del registro"));
            Inicio user = new Inicio();
            String nombre = user.verificacionUserSinPs(acacias, id, tipoUSer); // Verifica si el usuario existe.

            // Confirma la eliminación del usuario.
            int ans=JOptionPane.showOptionDialog(null,
                    "¿Está seguro de eliminar a "+ nombre + " del sistema?",
                    "Confirmación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    null,
                    null);
            if (ans==0){
                // Elimina el usuario de la tabla correspondiente.
                String instruccion = "DELETE FROM " + tipoUSer + " WHERE idUser = '"+ id +"'";
                try(PreparedStatement borrar = acacias.prepareStatement(instruccion)) {
                    System.out.println("Usuario eliminado exitosamente!");
                    borrar.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null,"Es necesario que ingrese el ID del usuario a eliminar...");//Excepción en caso de ingresar un dato erroneo
            eliminarUsuario(tipoUSer);
        }

    }

    //modelo de tabla para administradores
    public DefaultTableModel admins() throws SQLException {
        String sql = "SELECT * FROM administrador";
        DefaultTableModel administradores = new DefaultTableModel(); //creacion de modelo
        administradores.addColumn("IdUser"); //definiendo columnas
        administradores.addColumn("Nombre");
        administradores.addColumn("Contraseña");
        administradores.addColumn("Cédula de identidad");

        Statement st = acacias.createStatement(); //creacion de statement
        ResultSet rs = st.executeQuery(sql); // result set con información de la tabla administrador
        while (rs.next()){ //se recorre el result set y se llena un arreglo tipo Object con la información
            Object[] fila = {rs.getInt("idUser"), rs.getString("nombre"), rs.getString("contrasena"), rs.getString("CI")};
            administradores.addRow(fila); //se adjunta a una fila
        }
        return administradores; //retorno del modelo, listo para usarse
    }

    //modelo de tabla para trabajadores
    public DefaultTableModel trabajadores() throws SQLException {
        String sql = "SELECT * FROM trabajador";
        DefaultTableModel trabajadores = new DefaultTableModel(); //creacion de modelo
        trabajadores.addColumn("IdUser"); //definiendo columnas
        trabajadores.addColumn("Nombre");
        trabajadores.addColumn("Contraseña");
        trabajadores.addColumn("Cédula de identidad");
        trabajadores.addColumn("Cargo");
        trabajadores.addColumn("Salario");
        trabajadores.addColumn("Turno");

        Statement st = acacias.createStatement(); //creacion de statement
        ResultSet rs = st.executeQuery(sql); // result set con información de la tabla administrador
        while (rs.next()) { //se recorre el result set y se llena un arreglo tipo Object con la información
            Object[] fila = {rs.getInt("idUser"), rs.getString("nombre"),
                    rs.getString("contrasena"), rs.getInt("CI"), rs.getString("cargo"),
                    rs.getDouble("salario"), rs.getString("turno")};
            trabajadores.addRow(fila); //se adjunta a una fila
        }
        return trabajadores; //retorno del modelo, listo para usarse
    }

    //modelo de tabla para veterinarios
    public DefaultTableModel vets() throws SQLException {
        String sql = "SELECT * FROM veterinaria";
        DefaultTableModel vet = new DefaultTableModel(); //creacion de modelo
        vet.addColumn("IdUser"); //definiendo columnas
        vet.addColumn("Nombre");
        vet.addColumn("Contraseña");
        vet.addColumn("Cédula de identidad");
        vet.addColumn("Registro Senecyt");
        vet.addColumn("Especialización");
        vet.addColumn("Telefono");

        Statement st = acacias.createStatement(); //creacion de statement
        ResultSet rs = st.executeQuery(sql); // result set con información de la tabla administrador
        while (rs.next()) { //se recorre el result set y se llena un arreglo tipo Object con la información
            Object[] fila = {rs.getInt("idUser"), rs.getString("nombre"),
                    rs.getString("contrasena"), rs.getString("CI"), rs.getString("registroSenecyt"),
                    rs.getString("especializacion"), rs.getString("telefono")};
            vet.addRow(fila); //se adjunta a una fila
        }
        return vet; //retorno del modelo, listo para usarse
    }

    //metodo para mostrar usuarios, ESTA OPCION ES UNICA PARA EL ADMIN
     public void mostrarUsers() throws SQLException {
        TablaUsers usuarios = new TablaUsers();
        System.out.println("Abriendo registros...");

        // Usar un bloqueo para esperar a que la ventana sea cerrada
        final Object lock = new Object();

        usuarios.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                synchronized (lock) {
                    lock.notify(); // Notificar que la ventana fue cerrada
                }
            }
        });

        usuarios.setVisible(true);

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