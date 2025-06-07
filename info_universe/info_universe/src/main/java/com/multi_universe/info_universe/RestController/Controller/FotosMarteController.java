package com.multi_universe.info_universe.RestController.Controller;

import com.multi_universe.info_universe.Entity.FotosMarte;
import com.multi_universe.info_universe.RepositorioDB.FotosMarteRepository;
import com.multi_universe.info_universe.RestController.DTO_Controller.GET.GetFotosMarte;
import com.multi_universe.info_universe.RestController.DTO_Controller.POST.PostFotosMarte;
import com.multi_universe.info_universe.RestController.DTO_Controller.PUT.PutFotosMarte;
import com.multi_universe.info_universe.Service.ValidadorDatosException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("fotos-marte")
public class FotosMarteController {

    @Autowired
    FotosMarteRepository fotosMarteRepository;

    @GetMapping
    public ResponseEntity<Page<GetFotosMarte>> consultaCompleta(@PageableDefault(size = 10) Pageable pageable){

        if (fotosMarteRepository.findAll(pageable).isEmpty()) {
            throw new ValidadorDatosException("""
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    
                    Sin resultados, aún no se han registrado datos!!
                    
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
        }else {
            return ResponseEntity.ok(fotosMarteRepository.findAll(pageable).map(GetFotosMarte::new));
        }
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<Page<GetFotosMarte>> consultaPorId(@PathVariable Long id, @PageableDefault(size = 5) Pageable pageable ){
        Page<FotosMarte> resultado = fotosMarteRepository.findById(id,pageable);

        if (resultado.isEmpty()) {
            throw new ValidadorDatosException("""
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    
                    Error: Datos no encontrados!!
                    
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
        }else {
            return ResponseEntity.ok(resultado.map(GetFotosMarte::new));
        }
    }

    @GetMapping("/nombre/{name}")
    public ResponseEntity<Page<GetFotosMarte>> consultaPorNombre(@PathVariable String name, @PageableDefault(size = 5) Pageable pageable){

        if (fotosMarteRepository.findByName(name,pageable).isEmpty()) {
            throw new ValidadorDatosException("""
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    
                    Error: Datos no encontrados!!
                    
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
        } else {
            return ResponseEntity.ok(fotosMarteRepository.findByName(name,pageable).map(GetFotosMarte::new));
        }
    }

    @PutMapping("/modificar-datos")
    @Transactional
    public ResponseEntity<GetFotosMarte> modificarDatos(@RequestBody PutFotosMarte putFotosMarte){

        FotosMarte actualizarFoto = fotosMarteRepository.getReferenceById(putFotosMarte.id());

        if (!fotosMarteRepository.existsById(putFotosMarte.id())){
            throw new ValidadorDatosException("""
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    
                    Error: Id no encontrado!!
                    
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
        } else if (putFotosMarte.imagen() == null || putFotosMarte.imagen().isEmpty() || putFotosMarte.nombre_camara() == null || putFotosMarte.nombre_camara().isEmpty()
                    || putFotosMarte.fecha() == null || putFotosMarte.fecha().isEmpty() || putFotosMarte.titulo() == null || putFotosMarte.titulo().isEmpty()){
            throw new ValidadorDatosException("""
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    
                    Error: Todos los campos son obligatorios y/o los valores son erróneos!!
                    
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
        }else {
            actualizarFoto.actulizarDatos(putFotosMarte);
        }

        return ResponseEntity.ok(new GetFotosMarte(actualizarFoto.getId(),actualizarFoto.getIdConjunto(),
                actualizarFoto.getImg_src(), actualizarFoto.getEarth_date(),
                actualizarFoto.getIdFoto(), actualizarFoto.getFull_name(), actualizarFoto.getName()));
    }

    @PostMapping("agregar-datos")
    @Transactional
    public ResponseEntity<GetFotosMarte> agregarDatos (@RequestBody PostFotosMarte postFotosMarte){
        FotosMarte adicionarFoto = new FotosMarte();

        if (postFotosMarte.imagen() == null || postFotosMarte.imagen().isEmpty() || postFotosMarte.nombre_camara() == null || postFotosMarte.nombre_camara().isEmpty()
                || postFotosMarte.fecha() == null || postFotosMarte.fecha().isEmpty() || postFotosMarte.titulo() == null || postFotosMarte.titulo().isEmpty()){
            throw new ValidadorDatosException("""
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    
                    Error: Todos los campos son obligatorios y/o los valores son erróneos!!
                    
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
        } else if (fotosMarteRepository.existsByIdConjunto(postFotosMarte.id_conjunto_fotos())) {
            throw new ValidadorDatosException("""
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    
                    Error: el id ingresado para el campo: id_conjunto_fotos ya existe!!
                    
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
        }else if (fotosMarteRepository.existsByIdFoto(postFotosMarte.id_foto())) {
            throw new ValidadorDatosException("""
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    
                    Error: el id ingresado para el campo: id_foto ya existe!!
                    
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
        }
        else {
            adicionarFoto.agregarDatos(postFotosMarte);
            fotosMarteRepository.save(adicionarFoto);
        }
        return ResponseEntity.ok(new GetFotosMarte(adicionarFoto.getId(),adicionarFoto.getIdConjunto(),
                adicionarFoto.getImg_src(), adicionarFoto.getEarth_date(),
                adicionarFoto.getIdFoto(), adicionarFoto.getFull_name(), adicionarFoto.getName()));
    }

    @DeleteMapping("eliminar-datos/{id}")
    @Transactional
    public ResponseEntity eliminarDatos(@PathVariable Long id){

        if (!fotosMarteRepository.existsById(id)) {
            throw new ValidadorDatosException("""
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    
                    Error: Id no encontrado!!
                    
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
        } else {
            fotosMarteRepository.deleteById(id);
        }
        return ResponseEntity.ok("""
                ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                
                El objeto seleccionado fué eliminado con exito
                
                ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                """);
    }

    @RestControllerAdvice
    public static class ExceptionHandlerController{
        @ExceptionHandler(ValidadorDatosException.class)
        public ResponseEntity<String>mensajeValidadorDatosException(ValidadorDatosException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
