package udla.lospythones.sistema.lasacacias.caballos;

import udla.lospythones.sistema.lasacacias.BaseDeDatos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class Caballo {
    //atributos
    private int id;
    private int edad;
    private String nombre;
    private String raza = "Caballo homosexual de las montañas";
    private String sexo; //Hembra(H) ; Macho(M)
    private String descripcion;
    private String estadoSalud;
    private String estadoEntrenamiento;
    private double peso;
    private double valorComercial;
    private double alimentacion; //este atributo se calcula mediante un metodo y es medida en kg
    private String campo;

    BufferedReader sc = new BufferedReader(new InputStreamReader(System.in)); //lector
    BaseDeDatos db = new BaseDeDatos(); //se inicia una conexión con la base de datos para emplearla en esta clase, se hace el mismo procedimiento en las demás
    Connection acacias = db.connectToDataBase();

    //getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstadoSalud() {
        return estadoSalud;
    }

    public void setEstadoSalud(String estadoSalud) {
        this.estadoSalud = estadoSalud;
    }

    public String getEstadoEntrenamiento() {
        return estadoEntrenamiento;
    }

    public void setEstadoEntrenamiento(String estadoEntrenamiento) {
        this.estadoEntrenamiento = estadoEntrenamiento;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getValorComercial() {
        return valorComercial;
    }

    public void setValorComercial(double valorComercial) {
        this.valorComercial = valorComercial;
    }

    public double getAlimentacion() {
        return alimentacion;
    }

    public void setAlimentacion(double alimentacion) {
        this.alimentacion = alimentacion;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    //metodo para calcular la cantidad apropiada de alimento para el caballo
    public void alimentacion(int edad, double peso, String sexo){
        //ESTOS VALORES SON FICTICIOS
        /*si el caballo es menor de 4 años y es macho, debe comer el 2.5% de su peso, si
         * es hembra, el 1.5% de su peso*/
        /*si el caballo es mayor de 4 años y es macho, debe comer el 3% de su peso, si
         * es hembra, el 2.5% de su peso*/
        if (edad < 4 && sexo.equals("M")){
            setAlimentacion(peso * 0.025);
        }else if (edad < 4 && sexo.equals("H")){
            setAlimentacion(peso * 0.015);
        }else if (edad >= 4 && sexo.equals("M")){
            setAlimentacion(peso * 0.03);
        }else if (edad >= 4 && sexo.equals("H")){
            setAlimentacion(peso * 0.025);
        }
    }

    //metodo de obtencion de detalles del caballo
    public void obtencionDetalles() throws IOException, UniqueValueException, SxException {
        boolean i = true;
        while (i){
            try{
                System.out.println("Ingrese los datos del caballo...");
                System.out.printf("ID: ");
                setId(Integer.parseInt(sc.readLine()));
                System.out.printf("Edad: ");
                setEdad(Integer.parseInt(sc.readLine()));
                System.out.printf("Nombre: ");
                setNombre(sc.readLine());
                System.out.printf("Raza: ");
                setRaza(sc.readLine());
                System.out.printf("Sexo (M - Macho | H - Hembra): ");
                setSexo(sc.readLine());
                if (!getSexo().equals("M") && !getSexo().equals("H")){throw new SxException("M corresponde a Macho, H a Hembra. Ingrese de nuevo!");} //exception del sexo del caballo
                System.out.printf("Descripción: ");
                setDescripcion(sc.readLine());
                System.out.printf("Estado de salud: ");
                setEstadoSalud(sc.readLine());
                System.out.printf("Estado de entrenamiento: ");
                setEstadoEntrenamiento(sc.readLine());
                System.out.printf("Peso: ");
                setPeso(Double.parseDouble(sc.readLine()));
                System.out.printf("Valor: ");
                setValorComercial(Double.parseDouble(sc.readLine()));
                System.out.printf("Campo: ");
                setCampo(sc.readLine());

                //se debe verificar que exista el campo en el que se lo posiciona
                Statement a = acacias.createStatement();
                String camp = "SELECT * FROM criadero WHERE nombre = '"+this.campo+"'";
                ResultSet campos = a.executeQuery(camp);
                while (!campos.next()){
                    if (!campos.next()){
                        throw new CamposDisponiblesException("No hay ningun campo con ese nombre. Ingrese de nuevo...");
                    }
                }

                //el sistema calcula automaticamente la alimentacion adecuada
                alimentacion(getEdad(), getPeso(), getSexo());

                //el id es unico, se debe verificar que no se repita
                try(Statement verificar = acacias.createStatement()){
                    String similar = "SELECT * FROM caballo WHERE idcaballo = '" + this.id + "'";
                    ResultSet rs = verificar.executeQuery(similar);
                    if (rs.next()) {
                        throw new UniqueValueException("El ID de cada caballo es único! Por favor, ingrese un valor diferente...");
                    } else {
                        // ingreso de datos a la base de datos
                        String datos = "INSERT INTO caballo (idcaballo, edad, nombre, raza, sexo, descripcion, estadoSalud, estadoEntrenamiento, peso, valorComercial, campo, alimentacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        try (PreparedStatement insertar = acacias.prepareStatement(datos)) {
                            insertar.setInt(1, getId()); //obteniendo informacion por columna
                            insertar.setInt(2, getEdad());
                            insertar.setString(3, getNombre());
                            insertar.setString(4, getRaza());
                            insertar.setString(5, getSexo());
                            insertar.setString(6, getDescripcion());
                            insertar.setString(7, getEstadoSalud());
                            insertar.setString(8, getEstadoEntrenamiento());
                            insertar.setDouble(9, getPeso());
                            insertar.setDouble(10, getValorComercial());
                            insertar.setString(11, getCampo());
                            insertar.setDouble(12, getAlimentacion());

                            // se actualiza la tabla con los nuevos datos
                            insertar.executeUpdate();
                            System.out.println("Caballo ingresado exitosamente!");
                        }
                    }
                }catch (SQLException e){
                    throw new RuntimeException(e);
                }catch (UniqueValueException un){
                    System.out.println(un.getMessage());
                }
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un valor valido!");
            }catch (SxException | CamposDisponiblesException sx){
                System.out.println(sx.getMessage());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            //iteracion con boolean
            System.out.println("Ingresar otro caballo? (1.Si - 2.No)");
            int siono = Integer.parseInt(sc.readLine());
            if (siono == 2){i = false;}
        }
    }

    //metodo obtener un modelo de tabla basado en el listado de caballos de la base de datos
    public DefaultTableModel transferDetalles(){
        String sql = "SELECT * FROM caballo";
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID"); //se agregan y se definen los nombres de las columnas
        model.addColumn("Edad");
        model.addColumn("Nombre");
        model.addColumn("Raza");
        model.addColumn("Sexo");
        model.addColumn("Descripción");
        model.addColumn("Estado de Salud");
        model.addColumn("Estado de Entrenamiento");
        model.addColumn("Peso");
        model.addColumn("Valor");
        model.addColumn("Campo");
        model.addColumn("Alimentación");
        
        try (Statement st = acacias.createStatement();
             ResultSet rs = st.executeQuery(sql)) { //se crea una lista que contiene información sobre la tabla
            while (rs.next()) { //recorre la lista recuperando detalles y los almacena en un arreglo
                Object[] fila = { //un arreglo Object almacena datos de diversos tipos (int, double, String, etc)
                        rs.getInt("idcaballo"),
                        rs.getInt("edad"),
                        rs.getString("nombre"),
                        rs.getString("raza"),
                        rs.getString("sexo"),
                        rs.getString("descripcion"),
                        rs.getString("estadoSalud"),
                        rs.getString("estadoEntrenamiento"),
                        rs.getDouble("peso"),
                        rs.getDouble("valorComercial"),
                        rs.getString("campo"),
                        rs.getDouble("alimentacion")
                };
                model.addRow(fila); //una vez que se llena el arreglo con los datos, se agrega una fila y se repite el proceso
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    //metodo para editar caballo
    public void editarCaballo(int id) throws IOException, SQLException {
        // Verificar si el id existe
        String consultaExistencia = "SELECT * FROM caballo WHERE idcaballo = ?";
        try (PreparedStatement verificar = acacias.prepareStatement(consultaExistencia)) {
            verificar.setInt(1, id);
            ResultSet rs = verificar.executeQuery();

            if (rs.next()) {
                //modificación de base de datos
                String datos = "UPDATE caballo SET edad = ?, descripcion = ?, estadoSalud = ?, estadoEntrenamiento = ?, peso = ?, valorComercial = ?, campo = ?, alimentacion = ? WHERE idcaballo = ?";
                try (PreparedStatement editar = acacias.prepareStatement(datos)) {
                    System.out.printf("Edad: ");
                    setEdad(Integer.parseInt(sc.readLine()));
                    editar.setInt(1, getEdad());
                    System.out.printf("Descripción: ");
                    setDescripcion(sc.readLine());
                    editar.setString(2, getDescripcion());
                    System.out.printf("Estado de salud: ");
                    setEstadoSalud(sc.readLine());
                    editar.setString(3, getEstadoSalud());
                    System.out.printf("Estado de entrenamiento: ");
                    setEstadoEntrenamiento(sc.readLine());
                    editar.setString(4, getEstadoEntrenamiento());
                    System.out.printf("Peso: ");
                    setPeso(Double.parseDouble(sc.readLine()));
                    editar.setDouble(5, getPeso());
                    System.out.printf("Valor: ");
                    setValorComercial(Double.parseDouble(sc.readLine()));
                    editar.setDouble(6, getValorComercial());
                    System.out.printf("Campo: ");
                    setCampo(sc.readLine());
                    editar.setString(7, getCampo());

                    //se actualiza la cantidad de alimento en caso de cambios en el peso y edad del caballo
                    setSexo(rs.getString("sexo"));
                    alimentacion(getEdad(), getPeso(), getSexo());
                    editar.setDouble(8, getAlimentacion());

                    // se añade el id en la instrucción
                    editar.setInt(9, id);

                    // se actualiza la tabla con los nuevos datos
                    editar.executeUpdate();
                    System.out.println("Registro actualizado exitosamente!");
                } catch (NumberFormatException e) {
                    System.out.println("Ingrese un valor válido!");
                }
            } else {
                System.out.println("No se encontraron datos para el ID especificado.");
            }
        }
    }

    //metodo para eliminar un caballo de los registros del criadero en base a su id
    public void eliminarCaballo(int id){
        String instruccion = "DELETE FROM caballo WHERE idcaballo = ?";
        try (PreparedStatement borrar = acacias.prepareStatement(instruccion)) {
            borrar.setInt(1, id);
            int filasAfectadas = borrar.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Registro eliminado exitosamente!");
            } else {
                System.out.println("No se encontró ningún caballo con tal id");
            }
        } catch (SQLException e) {
            System.out.println("Error al intentar eliminar el registro: " + e.getMessage());
        }
    }

    //metodo para buscar un caballo en base a su id (modelo de tabla)
    public DefaultTableModel buscarCaballo() throws IOException {
        try {
            System.out.printf("ID del caballo: ");
            int id = Integer.parseInt(sc.readLine());
        }catch (NumberFormatException e){
            System.out.println("Es necesario que coloque el id del caballo...");
            buscarCaballo();
        }
        String sql = "SELECT * FROM caballo WHERE idcaballo ='"+id+"'";
        //se debe diseñar un nuevo modelo de tabla donde solo se encuentre información del caballo solicitado
        DefaultTableModel modeloEncontrado = new DefaultTableModel();
        modeloEncontrado.addColumn("ID"); //se agregan y se definen los nombres de las columnas
        modeloEncontrado.addColumn("Edad");
        modeloEncontrado.addColumn("Nombre");
        modeloEncontrado.addColumn("Raza");
        modeloEncontrado.addColumn("Sexo");
        modeloEncontrado.addColumn("Descripción");
        modeloEncontrado.addColumn("Estado de Salud");
        modeloEncontrado.addColumn("Estado de Entrenamiento");
        modeloEncontrado.addColumn("Peso");
        modeloEncontrado.addColumn("Valor");
        modeloEncontrado.addColumn("Campo");
        modeloEncontrado.addColumn("Alimentación");

        try (Statement st = acacias.createStatement();
             ResultSet rs = st.executeQuery(sql)) { //se crea una lista que contiene información sobre la tabla
            while (rs.next()) { //recorre la lista recuperando detalles y los almacena en un arreglo
                Object[] fila = { //un arreglo Object almacena datos de diversos tipos (int, double, String, etc)
                        rs.getInt("idcaballo"),
                        rs.getInt("edad"),
                        rs.getString("nombre"),
                        rs.getString("raza"),
                        rs.getString("sexo"),
                        rs.getString("descripcion"),
                        rs.getString("estadoSalud"),
                        rs.getString("estadoEntrenamiento"),
                        rs.getDouble("peso"),
                        rs.getDouble("valorComercial"),
                        rs.getString("campo"),
                        rs.getDouble("alimentacion")
                };
                modeloEncontrado.addRow(fila); //una vez que se llena el arreglo con los datos, se agrega una fila y se repite el proceso
            }
        } catch (SQLException e) {
            System.out.println("No se encontró ningun caballo con tal id");
        }
        return modeloEncontrado;
    }

    //metodo para imprimir la lista de caballos en el criadero
    public void impresionCaballos(){
        Tabla tabla = new Tabla();
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
