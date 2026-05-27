package model;

public class Producto {
    private int idProducto;
    private int idProveedor;
    private int idCategoria;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;

    public Producto() {}
    public Producto(int idProducto, int idProveedor, int idCategoria, String nombre, String descripcion, double precio, int stock) {
        this.idProducto = idProducto;
        this.idProveedor = idProveedor;
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
    }
    public Producto(int idProveedor, int idCategoria, String nombre, String descripcion, double precio, int stock) {
        this.idProveedor = idProveedor;
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
    }

    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }
    public int getIdProveedor() { return idProveedor; }
    public void setIdProveedor(int idProveedor) { this.idProveedor = idProveedor; }
    public int getIdCategoria() { return idCategoria; }
    public void setIdCategoria(int idCategoria) { this.idCategoria = idCategoria; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    @Override
    public String toString() {
        return String.format("Producto{id=%d, nombre='%s', idCategoria=%d, idProveedor=%d, precio=%.2f, stock=%d}", idProducto, nombre, idCategoria, idProveedor, precio, stock);
    }
}
