package com.multi_universe.info_universe.RestController.Controller;

import com.multi_universe.info_universe.Entity.FotosMarte;
import com.multi_universe.info_universe.Entity.ImagenTierraSatelital;
import com.multi_universe.info_universe.RepositorioDB.ImagenTierraSatelitalRepository;
import com.multi_universe.info_universe.RestController.DTO_Controller.GET.GetFotosMarte;
import com.multi_universe.info_universe.RestController.DTO_Controller.GET.GetImagenTierraSatelital;
import com.multi_universe.info_universe.RestController.DTO_Controller.POST.PostFotosMarte;
import com.multi_universe.info_universe.RestController.DTO_Controller.POST.PostImagenTierraSatelital;
import com.multi_universe.info_universe.RestController.DTO_Controller.PUT.PutFotosMarte;
import com.multi_universe.info_universe.RestController.DTO_Controller.PUT.PutImagenTierraSatelital;
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
@RequestMapping("imagen-tierra-satelital")
public class ImagenTierraSatelitalController {

    @Autowired
    ImagenTierraSatelitalRepository imagenTierraSatelitalRepository;

    @GetMapping
    public ResponseEntity<Page<GetImagenTierraSatelital>> consultaCompleta(@PageableDefault(size = 10) Pageable pageable){

        if (imagenTierraSatelitalRepository.findAll(pageable).isEmpty()) {
            throw new ValidadorDatosException("""
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    
                    Sin resultados, aún no se han registrado datos!!
                    
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
        }else {
            return ResponseEntity.ok(imagenTierraSatelitalRepository.findAll(pageable).map(GetImagenTierraSatelital::new));
        }
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<Page<GetImagenTierraSatelital>> consultaPorId(@PathVariable Long id, @PageableDefault(size = 5) Pageable pageable ){
        Page<ImagenTierraSatelital> resultado = imagenTierraSatelitalRepository.findById(id,pageable);

        if (resultado.isEmpty()) {
            throw new ValidadorDatosException("""
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    
                    Error: Datos no encontrados!!
                    
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
        }else {
            return ResponseEntity.ok(resultado.map(GetImagenTierraSatelital::new));
        }
    }

    @PutMapping("/modificar-datos")
    @Transactional
    public ResponseEntity<GetImagenTierraSatelital> modificarDatos(@RequestBody PutImagenTierraSatelital putImagenTierraSatelital){

        ImagenTierraSatelital actualizarFoto = imagenTierraSatelitalRepository.getReferenceById(putImagenTierraSatelital.id());

        if (!imagenTierraSatelitalRepository.existsById(putImagenTierraSatelital.id())){
            throw new ValidadorDatosException("""
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    
                    Error: Id no encontrado!!
                    
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
        } else if (putImagenTierraSatelital.satelite() == null || putImagenTierraSatelital.satelite().isEmpty()
                || putImagenTierraSatelital.fecha() == null || putImagenTierraSatelital.fecha().isEmpty()
                || putImagenTierraSatelital.urlImagen() == null || putImagenTierraSatelital.urlImagen().isEmpty()){
            throw new ValidadorDatosException("""
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    
                    Error: Todos los campos son obligatorios y/o los valores son erróneos!!
                    
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
        }else {
            actualizarFoto.actualizarDatos(putImagenTierraSatelital);
        }

        return ResponseEntity.ok(new GetImagenTierraSatelital(actualizarFoto.getId(),actualizarFoto.getIdImagen(),
                actualizarFoto.getDataset(), actualizarFoto.getUrl(),
                actualizarFoto.getDate(), actualizarFoto.getPlanet()));
    }

    @PostMapping("agregar-datos")
    @Transactional
    public ResponseEntity<GetImagenTierraSatelital> agregarDatos (@RequestBody PostImagenTierraSatelital postImagenTierraSatelital){
        ImagenTierraSatelital adicionarFoto = new ImagenTierraSatelital();

        if (postImagenTierraSatelital.satelite() == null || postImagenTierraSatelital.satelite().isEmpty()
                || postImagenTierraSatelital.fecha() == null || postImagenTierraSatelital.fecha().isEmpty()
                || postImagenTierraSatelital.urlImagen() == null || postImagenTierraSatelital.urlImagen().isEmpty()
                || postImagenTierraSatelital.idImagen() == null || postImagenTierraSatelital.idImagen().isEmpty()){
            throw new ValidadorDatosException("""
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    
                    Error: Todos los campos son obligatorios y/o los valores son erróneos!!
                    
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
        } else if (imagenTierraSatelitalRepository.existsByIdImagen(postImagenTierraSatelital.idImagen())) {
            throw new ValidadorDatosException("""
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    
                    Error: el id ingresado para el campo: id_conjunto_fotos ya existe!!
                    
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
        }
        else {
            adicionarFoto.agregarDatos(postImagenTierraSatelital);
            imagenTierraSatelitalRepository.save(adicionarFoto);
        }
        return ResponseEntity.ok(new GetImagenTierraSatelital(adicionarFoto.getId(),adicionarFoto.getDate(),
                adicionarFoto.getIdImagen(), adicionarFoto.getUrl(),
                adicionarFoto.getDataset(), adicionarFoto.getPlanet()));
    }

    @DeleteMapping("eliminar-datos/{id}")
    @Transactional
    public ResponseEntity eliminarDatos(@PathVariable Long id){

        if (!imagenTierraSatelitalRepository.existsById(id)) {
            throw new ValidadorDatosException("""
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    
                    Error: Id no encontrado!!
                    
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
        } else {
            imagenTierraSatelitalRepository.deleteById(id);
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
