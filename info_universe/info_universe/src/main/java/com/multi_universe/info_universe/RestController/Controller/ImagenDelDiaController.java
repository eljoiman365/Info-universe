package com.multi_universe.info_universe.RestController.Controller;

import com.multi_universe.info_universe.Entity.ImagenDelDia;
import com.multi_universe.info_universe.RepositorioDB.ImagenDelDiaRepository;
import com.multi_universe.info_universe.RestController.DTO_Controller.GET.GetImagenDelDia;
import com.multi_universe.info_universe.RestController.DTO_Controller.POST.PostImagenDelDia;
import com.multi_universe.info_universe.RestController.DTO_Controller.PUT.PutImagenDelDia;
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
@RequestMapping("/imagen-del-dia")
public class ImagenDelDiaController {
    @Autowired
    private ImagenDelDiaRepository imagenDelDiaRepository;

    @GetMapping
    public ResponseEntity<Page<GetImagenDelDia>> ObtenerDatosImagenDelDia (@PageableDefault(size = 5)Pageable paginacion){

        if (imagenDelDiaRepository.findAll(paginacion).isEmpty()) {
            throw new ValidadorDatosException("""
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    
                    Sin resultados, aún no se han registrado datos!!
                    
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
        }

        return ResponseEntity.ok(imagenDelDiaRepository.findAll(paginacion).map(GetImagenDelDia::new));
    }

    @GetMapping("id/{id}")
    public ResponseEntity<Page<GetImagenDelDia>> ObtenerDatosPorId(@PathVariable Long id, @PageableDefault(size = 5) Pageable pageable){

        Page<ImagenDelDia> resultado = imagenDelDiaRepository.findById(id,pageable);

        if (resultado.isEmpty()) {
            throw new ValidadorDatosException("""
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    
                    Error: Datos no encontrados!!
                    
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
        }

        return ResponseEntity.ok(resultado.map(GetImagenDelDia::new));
    }
    @GetMapping("titulo/{titulo}")
    public ResponseEntity<Page<GetImagenDelDia>> ObtenerDatosPorNombre(@PathVariable String titulo,@PageableDefault(size = 3) Pageable pageable){

        if (imagenDelDiaRepository.findByTitulo(titulo,pageable).isEmpty()) {
            throw new ValidadorDatosException("""
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    
                    Error: Datos no encontrados!!
                    
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
        }

        return ResponseEntity.ok(imagenDelDiaRepository.findByTitulo(titulo,pageable).map(GetImagenDelDia::new));
    }

    @PutMapping("modificar-datos")
    @Transactional
    public ResponseEntity modificarDatos(@RequestBody PutImagenDelDia putImagenDelDia){
        ImagenDelDia imagenDelDia = imagenDelDiaRepository.getReferenceById(putImagenDelDia.id());

        if (!imagenDelDiaRepository.existsById(putImagenDelDia.id())){
            throw new ValidadorDatosException("""
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    
                    Error: Id no encontrado!!
                    
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
        } else if (putImagenDelDia.imagen() == null || putImagenDelDia.fecha_publicacion() == null || putImagenDelDia.contexto() == null){
            throw new ValidadorDatosException("""
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    
                    Error: Todos los campos son obligatorios!!
                    
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");

        } else if (putImagenDelDia.imagen().isEmpty() || putImagenDelDia.fecha_publicacion().isEmpty() || putImagenDelDia.contexto().isEmpty()){
            throw new ValidadorDatosException("""
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    
                    Error: No se permiten cadenas de texto vacías!!
                    
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
        }else {
            imagenDelDia.actualizarDatos(putImagenDelDia);
        }

        return ResponseEntity.ok(new GetImagenDelDia(imagenDelDia.getId(),imagenDelDia.getTitulo(),imagenDelDia.getFechaPublicacion(),
        imagenDelDia.getContexto(),imagenDelDia.getImagen()));
    }
    @PostMapping("agregar-datos")
    @Transactional
    public ResponseEntity agregarDatos(@RequestBody PostImagenDelDia postImagenDelDia){
        ImagenDelDia imagenDelDia = new ImagenDelDia(postImagenDelDia);



        if (postImagenDelDia.imagen() == null || postImagenDelDia.fecha_publicacion() == null || postImagenDelDia.contexto() == null ||
        postImagenDelDia.titulo() == null){
            throw new ValidadorDatosException("""
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    
                    Error: Todos los campos son obligatorios!!
                    
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
        } else if (postImagenDelDia.imagen().isEmpty() || postImagenDelDia.fecha_publicacion().isEmpty() || postImagenDelDia.contexto().isEmpty() ||
                postImagenDelDia.titulo().isEmpty()) {
            throw new ValidadorDatosException("""
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    
                    Error: No se permiten cadenas de texto vacías!!
                    
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
        } else {
            imagenDelDiaRepository.save(imagenDelDia);
        }

        return ResponseEntity.ok(new GetImagenDelDia(imagenDelDia.getId(),imagenDelDia.getTitulo(),imagenDelDia.getFechaPublicacion(),
                imagenDelDia.getContexto(),imagenDelDia.getImagen()));
    }

    @DeleteMapping("eliminar-datos/{id}")
    @Transactional
    public ResponseEntity eliminarDatos(@PathVariable Long id){

        if (!imagenDelDiaRepository.existsById(id)){
            throw new ValidadorDatosException("""
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    
                    Error: Id no encontrado!!
                    
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
        }

        imagenDelDiaRepository.deleteById(id);
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
