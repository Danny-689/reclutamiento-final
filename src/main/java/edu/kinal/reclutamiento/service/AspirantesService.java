package main.java.edu.kinal.reclutamiento.service;

import main.java.edu.kinal.reclutamiento.exception.ValidarException;
import main.java.edu.kinal.reclutamiento.model.Aspirantes;
import main.java.edu.kinal.reclutamiento.crud.CrudRepository;
import java.util.List;

public class AspirantesService {

    private final CrudRepository<Aspirantes, Integer> repository;

    public AspirantesService(
            CrudRepository<Aspirantes, Integer> repository) {
        this.repository = repository;
    }

    public void guardar(Aspirantes a) {
        validar(a);
        repository.guardar(a);
    }

    public void actualizar(Aspirantes a) {
        if (a.getIdAspirante() <= 0) {
            throw new ValidarException(
                    "Selecciona un aspirante.");
        }

        validar(a);
        repository.actualizar(a);
    }

    public void eliminar(int id) {
        if (id <= 0) {
            throw new ValidarException(
                    "Selecciona un aspirante.");
        }

        repository.eliminar(id);
    }

    public List<Aspirantes> listar() {
        return repository.listar();
    }

    public Aspirantes buscarPorId(int id) {
        return repository.buscarPorId(id);
    }

    private void validar(Aspirantes a) {
        if (a.getNombre() == null || a.getNombre().isBlank()
                || a.getApellido() == null || a.getApellido().isBlank()
                || a.getCorreo() == null || a.getCorreo().isBlank()
                || a.getTelefono() == null || a.getTelefono().isBlank()
                || a.getDireccion() == null || a.getDireccion().isBlank()) {

            throw new ValidarException(
                    "Todos los campos son obligatorios.");
        }

        if (a.getEdad() <= 0) {
            throw new ValidarException(
                    "La edad debe ser mayor que cero.");
        }
    }
}
