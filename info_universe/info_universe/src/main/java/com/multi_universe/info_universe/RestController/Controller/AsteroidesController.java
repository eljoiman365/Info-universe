package com.multi_universe.info_universe.RestController.Controller;

import com.multi_universe.info_universe.Entity.AsteroidesAvistadosPorDia;
import com.multi_universe.info_universe.RepositorioDB.AsteroidesAvistadosPorDiaRepository;
import com.multi_universe.info_universe.RestController.DTO_Controller.GET.GetAsteroides;
import com.multi_universe.info_universe.RestController.DTO_Controller.POST.PostAsteroides;
import com.multi_universe.info_universe.RestController.DTO_Controller.PUT.PutAsteroides;
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
@RequestMapping("/asteroides")
public class AsteroidesController {

    @Autowired
    private AsteroidesAvistadosPorDiaRepository asteroidesAvistadosPorDiaRepository;

    @GetMapping
    public ResponseEntity<Page<GetAsteroides>> consultaCompleta(@PageableDefault(size = 10)Pageable pageable){

        if (asteroidesAvistadosPorDiaRepository.findAll(pageable).isEmpty()) {
            throw new ValidadorDatosException("""
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    
                    Sin resultados, aún no se han registrado datos!!
                    
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
        }else {
            return ResponseEntity.ok(asteroidesAvistadosPorDiaRepository.findAll(pageable).map(GetAsteroides::new));
        }
    }
    @GetMapping("/id/{id}")
        public ResponseEntity<Page<GetAsteroides>> consultaPorId(@PathVariable Long id, @PageableDefault(size = 5) Pageable pageable ){
        Page<AsteroidesAvistadosPorDia> resultado = asteroidesAvistadosPorDiaRepository.findById(id,pageable);

        if (resultado.isEmpty()) {
            throw new ValidadorDatosException("""
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    
                    Error: Datos no encontrados!!
                    
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
        }else {
            return ResponseEntity.ok(resultado.map(GetAsteroides::new));
        }
    }

    @GetMapping("/{name}")
        public ResponseEntity<Page<GetAsteroides>> consultaPorNombre(@PathVariable String name, @PageableDefault(size = 5) Pageable pageable){

        if (asteroidesAvistadosPorDiaRepository.findByName(name,pageable).isEmpty()) {
            throw new ValidadorDatosException("""
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    
                    Error: Datos no encontrados!!
                    
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
        } else {
            return ResponseEntity.ok(asteroidesAvistadosPorDiaRepository.findByName(name,pageable).map(GetAsteroides::new));
        }
    }

    @PutMapping("/modificar-datos")
    @Transactional
    public ResponseEntity<GetAsteroides> modificarDatos(@RequestBody PutAsteroides putAsteroides){

        AsteroidesAvistadosPorDia actualizarAsteroide = asteroidesAvistadosPorDiaRepository.getReferenceById(putAsteroides.id());
        if (!asteroidesAvistadosPorDiaRepository.existsById(putAsteroides.id())){
            throw new ValidadorDatosException("""
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    
                    Error: Id no encontrado!!
                    
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
        } else if (putAsteroides.nombre() == null || putAsteroides.magnitud_asteroide() <= 0 || putAsteroides.nombre().isEmpty()){
            throw new ValidadorDatosException("""
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    
                    Error: Todos los campos son obligatorios y/o los valores son erróneos!!
                    
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
        }else {
            actualizarAsteroide.modificarDatos(putAsteroides);
        }
        return ResponseEntity.ok(new GetAsteroides(actualizarAsteroide.getId(), actualizarAsteroide.getCantidadAsteroidesPorFecha(),
                actualizarAsteroide.getName(),actualizarAsteroide.getFecha(), actualizarAsteroide.getNeo_reference_id(),
                actualizarAsteroide.getAbsolute_magnitude_h(), actualizarAsteroide.getIs_potentially_hazardous_asteroid()));
    }

    @PostMapping("agregar-datos")
    @Transactional
    public ResponseEntity<GetAsteroides> agregarDatos (@RequestBody PostAsteroides postAsteroides){
        AsteroidesAvistadosPorDia agregarAsteroides = new AsteroidesAvistadosPorDia();

        if (postAsteroides.nombre() == null || postAsteroides.nombre().isEmpty() || postAsteroides.magnitud_asteroide() <= 0 || postAsteroides.fecha_identificacion() == null ||
                postAsteroides.id_asteroide() == null || postAsteroides.cantidad_asteroides_por_fecha() <= 0
                || postAsteroides.fecha_identificacion().isEmpty() || postAsteroides.id_asteroide().isEmpty()){
            throw new ValidadorDatosException("""
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    
                    Error: Todos los campos son obligatorios y/o los valores son erróneos!!
                    
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
        }else {
            agregarAsteroides.agregarDatos(postAsteroides);
            asteroidesAvistadosPorDiaRepository.save(agregarAsteroides);
        }
        return ResponseEntity.ok(new GetAsteroides(agregarAsteroides.getId(), agregarAsteroides.getCantidadAsteroidesPorFecha(),
                agregarAsteroides.getName(),agregarAsteroides.getFecha(), agregarAsteroides.getNeo_reference_id(),
                agregarAsteroides.getAbsolute_magnitude_h(), agregarAsteroides.getIs_potentially_hazardous_asteroid()));
    }

    @DeleteMapping("eliminar-datos/{id}")
    @Transactional
    public ResponseEntity eliminarDatos(@PathVariable Long id){

        if (!asteroidesAvistadosPorDiaRepository.existsById(id)) {
            throw new ValidadorDatosException("""
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    
                    Error: Id no encontrado!!
                    
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
        } else {
            asteroidesAvistadosPorDiaRepository.deleteById(id);
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
