package model;

public class Proveedor {
    private int idProveedor;
    private String nombre;
    private String direccion;
    private String email;
    private String contacto;
    private String telefono;

    public Proveedor() {}
    public Proveedor(int idProveedor, String nombre, String direccion, String email, String contacto, String telefono) {
        this.idProveedor = idProveedor;
        this.nombre = nombre;
        this.direccion = direccion;
        this.email = email;
        this.contacto = contacto;
        this.telefono = telefono;
    }
    public Proveedor(String nombre, String direccion, String email, String contacto, String telefono) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.email = email;
        this.contacto = contacto;
        this.telefono = telefono;
    }

    public int getIdProveedor() { return idProveedor; }
    public void setIdProveedor(int idProveedor) { this.idProveedor = idProveedor; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    @Override
    public String toString() {
        return String.format("Proveedor{id=%d, nombre='%s', email='%s', telefono='%s'}", idProveedor, nombre, email, telefono);
    }
}
