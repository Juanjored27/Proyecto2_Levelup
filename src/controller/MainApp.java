package controller;

import config.ConfigLoader;
import dao.CategoriaDAO;
import dao.CategoriaDAOImpl;
import dao.ClienteDAO;
import dao.ClienteDAOImpl;
import dao.ProductoDAO;
import dao.ProductoDAOImpl;
import dao.ProveedorDAO;
import dao.ProveedorDAOImpl;
import model.Categoria;
import model.Cliente;
import model.Producto;
import model.Proveedor;
import model.Usuario;
import services.AuthService;
import services.LlmService;
import util.InputValidator;
import util.SetupDatos;
import view.InterfazConsola;

public class MainApp {
    private final InterfazConsola vista = new InterfazConsola();
    private final AuthService authService = new AuthService();
    private final ClienteDAO clienteDAO = new ClienteDAOImpl();
    private final CategoriaDAO categoriaDAO = new CategoriaDAOImpl();
    private final ProveedorDAO proveedorDAO = new ProveedorDAOImpl();
    private final ProductoDAO productoDAO = new ProductoDAOImpl();
    private final LlmService llmService = new LlmService();

    public static void main(String[] args) {
        new MainApp().iniciar();
    }

    private void iniciar() {
        vista.mostrarMensaje("Bienvenido a " + ConfigLoader.getAppName());
        try {
            SetupDatos.cargarDatosIniciales();
        } catch (Exception e) {
            vista.mostrarMensaje("Aviso: no se pudieron cargar datos iniciales: " + e.getMessage());
        }

        boolean salir = false;
        while (!salir) {
            try {
                int op = vista.menuInicio();
                switch (op) {
                    case 1 -> flujoLogin();
                    case 0 -> salir = true;
                    default -> vista.mostrarMensaje("Opción no válida");
                }
            } catch (Exception e) {
                vista.mostrarMensaje("Error: " + e.getMessage());
            }
        }
        vista.mostrarMensaje("Aplicación finalizada.");
    }

    private void flujoLogin() {
        String identificador = vista.pedirTexto("Usuario o email");
        String password = vista.pedirTexto("Password");

        if (!InputValidator.isNotBlank(identificador)) {
            vista.mostrarMensaje("Debes introducir un usuario o email.");
            return;
        }

        Usuario usuario = authService.login(identificador, password);
        if (usuario == null) {
            vista.mostrarMensaje("Credenciales incorrectas.");
            return;
        }

        vista.mostrarMensaje("Login correcto. Bienvenido " + usuario.getNombre() + " [" + usuario.getRolNombre() + "]");
        menuPrincipal(usuario);
    }

    private void menuPrincipal(Usuario usuario) {
        boolean volver = false;
        while (!volver) {
            try {
                int op = vista.menuPrincipal(usuario.esAdministrador());
                switch (op) {
                    case 1 -> gestionarClientes(usuario);
                    case 2 -> gestionarCategorias(usuario);
                    case 3 -> gestionarProveedores(usuario);
                    case 4 -> gestionarProductos(usuario);
                    case 5 -> generarDescripcionIA();
                    case 6 -> sugerirCategoriaIA();
                    case 0 -> volver = true;
                    default -> vista.mostrarMensaje("Opción no válida");
                }
            } catch (Exception e) {
                vista.mostrarMensaje("Error en menú principal: " + e.getMessage());
            }
        }
    }

    private void gestionarClientes(Usuario usuario) {
        boolean volver = false;
        while (!volver) {
            int op = vista.submenuCrud("clientes", usuario.esAdministrador());
            switch (op) {
                case 1 -> vista.mostrarCliente(clienteDAO.obtenerPorId(vista.pedirEntero("ID cliente")));
                case 2 -> vista.mostrarClientes(clienteDAO.listarTodos());
                case 3 -> {
                    if (!usuario.esAdministrador()) {
                        denegado();
                        break;
                    }
                    Cliente c = vista.pedirCliente();
                    if (!InputValidator.isValidEmail(c.getEmail())) {
                        vista.mostrarMensaje("Email no válido.");
                    } else {
                        vista.mostrarMensaje(clienteDAO.insertar(c) ? "Cliente insertado." : "No se pudo insertar.");
                    }
                }
                case 4 -> {
                    if (!usuario.esAdministrador()) {
                        denegado();
                        break;
                    }
                    int id = vista.pedirEntero("ID cliente a actualizar");
                    Cliente c = vista.pedirCliente();
                    c.setIdCliente(id);
                    vista.mostrarMensaje(clienteDAO.actualizar(c) ? "Cliente actualizado." : "No se pudo actualizar.");
                }
                case 5 -> {
                    if (!usuario.esAdministrador()) {
                        denegado();
                        break;
                    }
                    vista.mostrarMensaje(clienteDAO.eliminar(vista.pedirEntero("ID cliente a eliminar")) ? "Cliente eliminado." : "No se pudo eliminar.");
                }
                case 0 -> volver = true;
                default -> vista.mostrarMensaje("Opción no válida");
            }
        }
    }

    private void gestionarCategorias(Usuario usuario) {
        boolean volver = false;
        while (!volver) {
            int op = vista.submenuCrud("categorías", usuario.esAdministrador());
            switch (op) {
                case 1 -> vista.mostrarCategoria(categoriaDAO.obtenerPorId(vista.pedirEntero("ID categoría")));
                case 2 -> vista.mostrarCategorias(categoriaDAO.listarTodas());
                case 3 -> {
                    if (!usuario.esAdministrador()) {
                        denegado();
                        break;
                    }
                    Categoria c = vista.pedirCategoria();
                    vista.mostrarMensaje(categoriaDAO.insertar(c) ? "Categoría insertada." : "No se pudo insertar.");
                }
                case 4 -> {
                    if (!usuario.esAdministrador()) {
                        denegado();
                        break;
                    }
                    int id = vista.pedirEntero("ID categoría a actualizar");
                    Categoria c = vista.pedirCategoria();
                    c.setIdCategoria(id);
                    vista.mostrarMensaje(categoriaDAO.actualizar(c) ? "Categoría actualizada." : "No se pudo actualizar.");
                }
                case 5 -> {
                    if (!usuario.esAdministrador()) {
                        denegado();
                        break;
                    }
                    vista.mostrarMensaje(categoriaDAO.eliminar(vista.pedirEntero("ID categoría a eliminar")) ? "Categoría eliminada." : "No se pudo eliminar.");
                }
                case 0 -> volver = true;
                default -> vista.mostrarMensaje("Opción no válida");
            }
        }
    }

    private void gestionarProveedores(Usuario usuario) {
        boolean volver = false;
        while (!volver) {
            int op = vista.submenuCrud("proveedores", usuario.esAdministrador());
            switch (op) {
                case 1 -> vista.mostrarProveedor(proveedorDAO.obtenerPorId(vista.pedirEntero("ID proveedor")));
                case 2 -> vista.mostrarProveedores(proveedorDAO.listarTodos());
                case 3 -> {
                    if (!usuario.esAdministrador()) {
                        denegado();
                        break;
                    }
                    Proveedor p = vista.pedirProveedor();
                    vista.mostrarMensaje(proveedorDAO.insertar(p) ? "Proveedor insertado." : "No se pudo insertar.");
                }
                case 4 -> {
                    if (!usuario.esAdministrador()) {
                        denegado();
                        break;
                    }
                    int id = vista.pedirEntero("ID proveedor a actualizar");
                    Proveedor p = vista.pedirProveedor();
                    p.setIdProveedor(id);
                    vista.mostrarMensaje(proveedorDAO.actualizar(p) ? "Proveedor actualizado." : "No se pudo actualizar.");
                }
                case 5 -> {
                    if (!usuario.esAdministrador()) {
                        denegado();
                        break;
                    }
                    vista.mostrarMensaje(proveedorDAO.eliminar(vista.pedirEntero("ID proveedor a eliminar")) ? "Proveedor eliminado." : "No se pudo eliminar.");
                }
                case 0 -> volver = true;
                default -> vista.mostrarMensaje("Opción no válida");
            }
        }
    }

    private void gestionarProductos(Usuario usuario) {
        boolean volver = false;
        while (!volver) {
            int op = vista.submenuCrud("productos", usuario.esAdministrador());
            switch (op) {
                case 1 -> vista.mostrarProducto(productoDAO.obtenerPorId(vista.pedirEntero("ID producto")));
                case 2 -> vista.mostrarProductos(productoDAO.listarTodos());
                case 3 -> {
                    if (!usuario.esAdministrador()) {
                        denegado();
                        break;
                    }
                    mostrarAyudaProductos();
                    Producto p = vista.pedirProducto();
                    if (!validarProducto(p)) {
                        break;
                    }
                    vista.mostrarMensaje(productoDAO.insertar(p) ? "Producto insertado." : "No se pudo insertar. Revisa que los IDs existan y que no haya errores en la base de datos.");
                }
                case 4 -> {
                    if (!usuario.esAdministrador()) {
                        denegado();
                        break;
                    }
                    int id = vista.pedirEntero("ID producto a actualizar");
                    if (productoDAO.obtenerPorId(id) == null) {
                        vista.mostrarMensaje("No existe ningún producto con ese ID.");
                        break;
                    }
                    mostrarAyudaProductos();
                    Producto p = vista.pedirProducto();
                    p.setIdProducto(id);
                    if (!validarProducto(p)) {
                        break;
                    }
                    vista.mostrarMensaje(productoDAO.actualizar(p) ? "Producto actualizado." : "No se pudo actualizar. Revisa la base de datos.");
                }
                case 5 -> {
                    if (!usuario.esAdministrador()) {
                        denegado();
                        break;
                    }
                    vista.mostrarMensaje(productoDAO.eliminar(vista.pedirEntero("ID producto a eliminar")) ? "Producto eliminado." : "No se pudo eliminar.");
                }
                case 0 -> volver = true;
                default -> vista.mostrarMensaje("Opción no válida");
            }
        }
    }

    private boolean validarProducto(Producto producto) {
        if (producto == null) {
            vista.mostrarMensaje("No se pudo leer el producto.");
            return false;
        }
        if (!InputValidator.isNotBlank(producto.getNombre())) {
            vista.mostrarMensaje("El nombre del producto es obligatorio.");
            return false;
        }
        if (!InputValidator.isPositiveDouble(producto.getPrecio())) {
            vista.mostrarMensaje("El precio no puede ser negativo.");
            return false;
        }
        if (!InputValidator.isPositiveInt(producto.getStock())) {
            vista.mostrarMensaje("El stock no puede ser negativo.");
            return false;
        }
        if (categoriaDAO.obtenerPorId(producto.getIdCategoria()) == null) {
            vista.mostrarMensaje("La categoría indicada no existe.");
            return false;
        }
        if (proveedorDAO.obtenerPorId(producto.getIdProveedor()) == null) {
            vista.mostrarMensaje("El proveedor indicado no existe.");
            return false;
        }
        return true;
    }

    private void mostrarAyudaProductos() {
        vista.mostrarMensaje("Categorías disponibles:");
        vista.mostrarCategorias(categoriaDAO.listarTodas());
        vista.mostrarMensaje("Proveedores disponibles:");
        vista.mostrarProveedores(proveedorDAO.listarTodos());
    }

    private void generarDescripcionIA() {
        try {
            int id = vista.pedirEntero("ID del producto para generar descripción");
            Producto producto = productoDAO.obtenerPorId(id);
            if (producto == null) {
                vista.mostrarMensaje("Producto no encontrado.");
                return;
            }
            String resultado = llmService.generarDescripcionProducto(producto);
            vista.mostrarMensaje("\nDescripción generada por IA:\n" + resultado);
        } catch (Exception e) {
            vista.mostrarMensaje("Error al generar descripción con IA: " + e.getMessage());
        }
    }

    private void sugerirCategoriaIA() {
        try {
            int id = vista.pedirEntero("ID del producto para sugerir categoría");
            Producto producto = productoDAO.obtenerPorId(id);
            if (producto == null) {
                vista.mostrarMensaje("Producto no encontrado.");
                return;
            }
            String resultado = llmService.sugerirCategoriaProducto(producto);
            vista.mostrarMensaje("\nCategoría sugerida por IA:\n" + resultado);
        } catch (Exception e) {
            vista.mostrarMensaje("Error al sugerir categoría con IA: " + e.getMessage());
        }
    }

    private void denegado() {
        vista.mostrarMensaje("Acción no permitida para el rol EMPLEADO.");
    }
}
