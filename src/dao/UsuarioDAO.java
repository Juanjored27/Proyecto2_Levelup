package dao;

import model.Usuario;

public interface UsuarioDAO {
    Usuario login(String email, String passwordHash);
}
