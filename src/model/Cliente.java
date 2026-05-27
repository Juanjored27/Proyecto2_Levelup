package model;

import java.time.LocalDateTime;

public class Cliente {
    private int idCliente;
    private String nombre;
    private String apellidos;
    private String direccion;
    private String email;
    private String telefono;
    private LocalDateTime fechaRegistro;

    public Cliente() {}

    public Cliente(int idCliente, String nombre, String apellidos, String direccion, String email, String telefono, LocalDateTime fechaRegistro) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.fechaRegistro = fechaRegistro;
    }

    public Cliente(String nombre, String apellidos, String direccion, String email, String telefono) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
    }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    @Override
    public String toString() {
        return String.format("Cliente{id=%d, nombre='%s', apellidos='%s', email='%s', telefono='%s'}", idCliente, nombre, apellidos, email, telefono);
    }
}
