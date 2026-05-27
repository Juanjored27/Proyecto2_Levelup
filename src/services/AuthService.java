package services;

import dao.UsuarioDAO;
import dao.UsuarioDAOImpl;
import model.Usuario;
import util.HashUtil;

public class AuthService {
    private final UsuarioDAO usuarioDAO;

    public AuthService() {
        this.usuarioDAO = new UsuarioDAOImpl();
    }

    public Usuario login(String identificador, String passwordPlano) {
        return usuarioDAO.login(identificador, HashUtil.sha256(passwordPlano));
    }
}
