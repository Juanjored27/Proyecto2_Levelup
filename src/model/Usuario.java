package model;

import java.time.LocalDateTime;

public class Usuario {
    private int idUsuario;
    private int idRol;
    private String rolNombre;
    private String nombre;
    private String apellidos;
    private String email;
    private String password;
    private LocalDateTime fechaCreacion;

    public Usuario() {}

    public Usuario(int idUsuario, int idRol, String rolNombre, String nombre, String apellidos, String email, String password, LocalDateTime fechaCreacion) {
        this.idUsuario = idUsuario;
        this.idRol = idRol;
        this.rolNombre = rolNombre;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.password = password;
        this.fechaCreacion = fechaCreacion;
    }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public int getIdRol() { return idRol; }
    public void setIdRol(int idRol) { this.idRol = idRol; }
    public String getRolNombre() { return rolNombre; }
    public void setRolNombre(String rolNombre) { this.rolNombre = rolNombre; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public boolean esAdministrador() {
        return rolNombre != null && rolNombre.equalsIgnoreCase("ADMINISTRADOR");
    }

    @Override
    public String toString() {
        return String.format("Usuario{id=%d, nombre='%s %s', email='%s', rol='%s'}", idUsuario, nombre, apellidos, email, rolNombre);
    }
}
