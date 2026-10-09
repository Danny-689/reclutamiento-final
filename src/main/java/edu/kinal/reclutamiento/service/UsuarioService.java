package main.java.edu.kinal.reclutamiento.service;

import main.java.edu.kinal.reclutamiento.dto.LoginDto;
import main.java.edu.kinal.reclutamiento.exception.ValidarException;
import main.java.edu.kinal.reclutamiento.model.Usuarios;
import main.java.edu.kinal.reclutamiento.repository.UsuarioRepository;
import main.java.edu.kinal.reclutamiento.security.PasswordUtil;

public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public void registrar(Usuarios usuario) {
        if (usuario.getNombreUsuario() == null
                || usuario.getNombreUsuario().isBlank()
                || usuario.getCorreo() == null
                || usuario.getCorreo().isBlank()
                || usuario.getContrasenaUsuario() == null
                || usuario.getContrasenaUsuario().isBlank()) {

            throw new ValidarException(
                    "Todos los campos son obligatorios.");
        }

        if (repository.buscarPorCorreo(usuario.getCorreo()) != null) {
            throw new ValidarException(
                    "El correo ya está registrado.");
        }

        usuario.setContrasenaUsuario(
                PasswordUtil.encriptar(usuario.getContrasenaUsuario()));

        repository.guardar(usuario);
    }

    public Usuarios iniciarSesion(LoginDto dto) {
        if (dto.getCorreo() == null
                || dto.getCorreo().isBlank()
                || dto.getPassword() == null
                || dto.getPassword().isBlank()) {

            throw new ValidarException(
                    "Ingresa el correo y la contraseña.");
        }

        Usuarios usuario =
                repository.buscarPorCorreo(dto.getCorreo());

        if (usuario != null
                && PasswordUtil.verificar(
                        dto.getPassword(), usuario.getContrasenaUsuario())) {
            return usuario;
        }

        return null;
    }
}
