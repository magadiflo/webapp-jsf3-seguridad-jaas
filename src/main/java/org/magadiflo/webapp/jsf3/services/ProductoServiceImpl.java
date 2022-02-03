package org.magadiflo.webapp.jsf3.services;

import jakarta.annotation.Resource;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.SessionContext;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.magadiflo.webapp.jsf3.entities.Categoria;
import org.magadiflo.webapp.jsf3.entities.Producto;
import org.magadiflo.webapp.jsf3.repositories.CrudRepository;
import org.magadiflo.webapp.jsf3.repositories.ProductoRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Stateless
@DeclareRoles({"USER", "ADMIN"})
public class ProductoServiceImpl implements ProductoService {

    @Inject
    private ProductoRepository productoCrudRepository;

    @Inject
    private CrudRepository<Categoria> categoriaCrudRepository;

    @Resource
    private SessionContext ctx;

    @PermitAll
    @Override
    public List<Producto> listar() {
        Principal usuario = this.ctx.getCallerPrincipal();
        String username = usuario.getName();
        System.out.println("username ----------------> " + username);
        if(this.ctx.isCallerInRole("ADMIN")){
            System.out.println("******** Hola soy un administrador ***** ");
        } else if(this.ctx.isCallerInRole("USER")){
            System.out.println("******** Hola soy un usuario normal ***** ");
        } else {
            System.out.println("*********** Hola soy un usuario anonimo *******");
            //throw new SecurityException("Lo sentimos no tiene permismo para acceder a esta pagina");
        }
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
