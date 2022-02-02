package org.magadiflo.webapp.jsf3.services;

import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.magadiflo.webapp.jsf3.entities.Categoria;
import org.magadiflo.webapp.jsf3.entities.Producto;
import org.magadiflo.webapp.jsf3.repositories.CrudRepository;
import org.magadiflo.webapp.jsf3.repositories.ProductoRepository;

import java.util.List;
import java.util.Optional;

@Stateless
@DeclareRoles({"USER", "ADMIN"})
public class ProductoServiceImpl implements ProductoService {

    @Inject
    private ProductoRepository productoCrudRepository;

    @Inject
    private CrudRepository<Categoria> categoriaCrudRepository;

    @PermitAll
    @Override
    public List<Producto> listar() {
        return this.productoCrudRepository.listar();
    }

    @RolesAllowed({"USER", "ADMIN"})
    @Override
    public Optional<Producto> porId(Long id) {
        return Optional.ofNullable(this.productoCrudRepository.porId(id));
    }

    @RolesAllowed({"ADMIN"})
    @Override
    public void guardar(Producto p) {
        this.productoCrudRepository.guardar(p);
    }

    @RolesAllowed({"ADMIN"})
    @Override
    public void eliminar(Long id) {
        this.productoCrudRepository.eliminar(id);
    }

    @RolesAllowed({"USER", "ADMIN"})
    @Override
    public List<Categoria> listarCategorias() {
        return this.categoriaCrudRepository.listar();
    }

    @RolesAllowed({"USER", "ADMIN"})
    @Override
    public Optional<Categoria> porIdCategoria(Long id) {
        return Optional.ofNullable(this.categoriaCrudRepository.porId(id));
    }

    @RolesAllowed({"USER", "ADMIN"})
    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        return this.productoCrudRepository.buscarPorNombre(nombre);
    }
}
