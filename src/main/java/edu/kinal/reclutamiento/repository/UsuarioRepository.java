package main.java.edu.kinal.reclutamiento.repository;
import main.java.edu.kinal.reclutamiento.config.DataBaseConnection;
import main.java.edu.kinal.reclutamiento.exception.AccesoDatosException;
import main.java.edu.kinal.reclutamiento.model.Usuarios;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioRepository {

    public void guardar(Usuarios usuario) {
        String sql = """
            INSERT INTO usuario
            (nombre, correo, password, id_rol)
            VALUES (?, ?, ?, ?)
            """;

        try (Connection con = DataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombreUsuario());
            ps.setString(2, usuario.getCorreo());
            ps.setString(3, usuario.getContrasenaUsuario());
            ps.setInt(4, usuario.getIdRol());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new AccesoDatosException(
                    "No se pudo registrar el usuario.", e);
        }
    }

    public Usuarios buscarPorCorreo(String correo) {
        String sql = """
            SELECT id_usuario, nombre, correo, password, id_rol
            FROM usuario
            WHERE correo = ?
            """;

        try (Connection con = DataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, correo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuarios(
                        rs.getInt("idUsuario"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("correo"),
                        rs.getString("password"),
                        rs.getInt("id_rol")
                    );
                }
            }

            return null;

        } catch (SQLException e) {
            throw new AccesoDatosException(
                    "No se pudo buscar el usuario.", e);
        }
    }
}
