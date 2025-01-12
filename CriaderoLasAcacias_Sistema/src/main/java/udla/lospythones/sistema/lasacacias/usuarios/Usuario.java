package udla.lospythones.sistema.lasacacias.usuarios;

import java.io.IOException;
import java.sql.SQLException;

//CLASE ABSTRACTA
//Heredará a las clases Veterinario, Trabajador y Administrador.

abstract public class Usuario {
    //Declaración de atributos en protected
    protected int idUser;
    protected String nombre;
    protected String contrasena;
    protected String CI;

    public Usuario(int idUser, String nombre, String contrasena, String CI) {
        this.idUser = idUser;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.CI = CI;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCI() {
        return CI;
    }

    public void setCI(String CI) {
        this.CI = CI;
    }

    //Métodos que se usará en cada clase que se herede de acuerdo a su necesidad
    abstract public void nuevoUsuario() throws IOException;
    abstract public int opcionesUsuario() throws IOException, SQLException;

}
