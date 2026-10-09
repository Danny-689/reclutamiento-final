package main.java.edu.kinal.reclutamiento.repository;

import main.java.edu.kinal.reclutamiento.config.DataBaseConnection;
import main.java.edu.kinal.reclutamiento.exception.AccesoDatosException;
import main.java.edu.kinal.reclutamiento.model.Aspirantes;
import main.java.edu.kinal.reclutamiento.crud.CrudRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AspiranteRepository
        implements CrudRepository<Aspirantes, Integer> {

    @Override
    public void guardar(Aspirantes a) {
        String sql = """
            INSERT INTO aspirantes
            (nombre, apellido, correo, telefono, direccion, edad)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (Connection con = DataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, a.getNombre());
            ps.setString(2, a.getApellido());
            ps.setString(3, a.getCorreo());
            ps.setString(4, a.getTelefono());
            ps.setString(5, a.getDireccion());
            ps.setInt(6, a.getEdad());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new AccesoDatosException(
                    "No se pudo guardar el aspirante.", e);
        }
    }

    @Override
    public void actualizar(Aspirantes a) {
        String sql = """
            UPDATE aspirantes
            SET nombre=?, apellido=?, correo=?,
                telefono=?, direccion=?, edad=?
            WHERE id_aspirante=?
            """;

        try (Connection con = DataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, a.getNombre());
            ps.setString(2, a.getApellido());
            ps.setString(3, a.getCorreo());
            ps.setString(4, a.getTelefono());
            ps.setString(5, a.getDireccion());
            ps.setInt(6, a.getEdad());
            ps.setInt(7, a.getIdAspirante());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new AccesoDatosException(
                    "No se pudo actualizar el aspirante.", e);
        }
    }

    @Override
    public void eliminar(Integer id) {
        String sql =
                "DELETE FROM aspirantes WHERE id_aspirante=?";

        try (Connection con = DataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new AccesoDatosException(
                    "No se pudo eliminar el aspirante.", e);
        }
    }

    @Override
    public List<Aspirantes> listar() {
        List<Aspirantes> lista = new ArrayList<>();
        String sql = "SELECT * FROM aspirantes ORDER BY id_aspirante";

        try (Connection con = DataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }

            return lista;

        } catch (SQLException e) {
            throw new AccesoDatosException(
                    "No se pudo listar los aspirantes.", e);
        }
    }

    @Override
    public Aspirantes buscarPorId(Integer id) {
        String sql =
                "SELECT * FROM aspirantes WHERE id_aspirante=?";

        try (Connection con = DataBaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }

            return null;

        } catch (SQLException e) {
            throw new AccesoDatosException(
                    "No se pudo buscar el aspirante.", e);
        }
    }

    private Aspirantes mapear(ResultSet rs) throws SQLException {
        return new Aspirantes(
            rs.getInt("idAspirante"),
            rs.getString("nombre"),
            rs.getString("apellido"),
            rs.getString("correo"),
            rs.getString("telefono"),
            rs.getString("direccion"),
            rs.getInt("edad")
        );
    }
}
