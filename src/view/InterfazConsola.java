package view;

import model.Categoria;
import model.Cliente;
import model.Producto;
import model.Proveedor;
import util.InputValidator;

import java.util.List;
import java.util.Scanner;

public class InterfazConsola {
    private final Scanner scanner = new Scanner(System.in);

    public void mostrarTitulo(String titulo) {
        System.out.println("\n====================================");
        System.out.println(titulo);
        System.out.println("====================================");
    }

    public String pedirTexto(String etiqueta) {
        System.out.print(etiqueta + ": ");
        return scanner.nextLine();
    }

    public String pedirTextoObligatorio(String etiqueta) {
        while (true) {
            String valor = pedirTexto(etiqueta);
            if (InputValidator.isNotBlank(valor)) {
                return valor.trim();
            }
            mostrarMensaje("El campo '" + etiqueta + "' no puede estar vacío.");
        }
    }

    public int pedirEntero(String etiqueta) {
        while (true) {
            try {
                System.out.print(etiqueta + ": ");
                return InputValidator.parseInt(scanner.nextLine());
            } catch (Exception e) {
                mostrarMensaje("Introduce un número entero válido.");
            }
        }
    }

    public double pedirDouble(String etiqueta) {
        while (true) {
            try {
                System.out.print(etiqueta + " (usa punto o coma decimal): ");
                return InputValidator.parseDoubleFlexible(scanner.nextLine());
            } catch (Exception e) {
                mostrarMensaje("Introduce un número decimal válido. Ejemplo: 59.99 o 59,99");
            }
        }
    }

    public int menuInicio() {
        mostrarTitulo("LEVELUP ARCADE - INICIO");
        System.out.println("1. Iniciar sesión");
        System.out.println("0. Salir");
        return pedirEntero("Opción");
    }

    public int menuPrincipal(boolean admin) {
        mostrarTitulo("MENÚ PRINCIPAL");
        System.out.println("1. Clientes");
        System.out.println("2. Categorías");
        System.out.println("3. Proveedores");
        System.out.println("4. Productos");
        System.out.println("5. Generar descripción de producto con IA");
        System.out.println("6. Sugerir categoría de producto con IA");
        System.out.println("0. Cerrar sesión");
        if (!admin) {
            System.out.println("Modo empleado: solo consulta");
        }
        return pedirEntero("Opción");
    }

    public int submenuCrud(String entidad, boolean admin) {
        mostrarTitulo("GESTIÓN DE " + entidad.toUpperCase());
        System.out.println("1. Buscar por ID");
        System.out.println("2. Listar todos");
        if (admin) {
            System.out.println("3. Insertar");
            System.out.println("4. Actualizar");
            System.out.println("5. Eliminar");
        }
        System.out.println("0. Volver");
        return pedirEntero("Opción");
    }

    public Cliente pedirCliente() {
        return new Cliente(
                pedirTextoObligatorio("Nombre"),
                pedirTextoObligatorio("Apellidos"),
                pedirTexto("Dirección"),
                pedirTextoObligatorio("Email"),
                pedirTexto("Teléfono")
        );
    }

    public Categoria pedirCategoria() {
        return new Categoria(
                pedirTextoObligatorio("Nombre"),
                pedirTexto("Descripción")
        );
    }

    public Proveedor pedirProveedor() {
        return new Proveedor(
                pedirTextoObligatorio("Nombre"),
                pedirTexto("Dirección"),
                pedirTexto("Email"),
                pedirTexto("Contacto"),
                pedirTexto("Teléfono")
        );
    }

    public Producto pedirProducto() {
        return new Producto(
                pedirEntero("ID proveedor"),
                pedirEntero("ID categoría"),
                pedirTextoObligatorio("Nombre"),
                pedirTexto("Descripción"),
                pedirDouble("Precio"),
                pedirEntero("Stock")
        );
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarCliente(Cliente cliente) {
        System.out.println(cliente == null ? "No encontrado" : cliente);
    }

    public void mostrarCategoria(Categoria categoria) {
        System.out.println(categoria == null ? "No encontrada" : categoria);
    }

    public void mostrarProveedor(Proveedor proveedor) {
        System.out.println(proveedor == null ? "No encontrado" : proveedor);
    }

    public void mostrarProducto(Producto producto) {
        System.out.println(producto == null ? "No encontrado" : producto);
    }

    public void mostrarClientes(List<Cliente> lista) {
        mostrarLista(lista);
    }

    public void mostrarCategorias(List<Categoria> lista) {
        mostrarLista(lista);
    }

    public void mostrarProveedores(List<Proveedor> lista) {
        mostrarLista(lista);
    }

    public void mostrarProductos(List<Producto> lista) {
        mostrarLista(lista);
    }

    private <T> void mostrarLista(List<T> lista) {
        if (lista == null || lista.isEmpty()) {
            System.out.println("No hay registros.");
            return;
        }
        for (T item : lista) {
            System.out.println(item);
        }
    }
}
