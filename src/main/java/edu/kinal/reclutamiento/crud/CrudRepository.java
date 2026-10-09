package main.java.edu.kinal.reclutamiento.crud;

import java.util.List;

public interface CrudRepository<T, ID> {

    void guardar(T entidad);

    void actualizar(T entidad);

    void eliminar(ID id);

    List<T> listar();

    T buscarPorId(ID id);
}

