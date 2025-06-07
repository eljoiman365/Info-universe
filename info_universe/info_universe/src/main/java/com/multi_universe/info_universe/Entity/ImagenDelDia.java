package com.multi_universe.info_universe.Entity;

import com.multi_universe.info_universe.DTO.ImagenDelDiaDTO;
import com.multi_universe.info_universe.RestController.DTO_Controller.POST.PostImagenDelDia;
import com.multi_universe.info_universe.RestController.DTO_Controller.PUT.PutImagenDelDia;
import jakarta.persistence.*;

@Entity
@Table(name = "imagenes_del_dia")
public class ImagenDelDia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String fechaPublicacion;
    @Column(columnDefinition = "TEXT")
    private String contexto;
    private String imagen;

    public ImagenDelDia(){
    }

    public ImagenDelDia (ImagenDelDiaDTO mImagenDelDia){
        this.titulo = mImagenDelDia.title();
        this.fechaPublicacion = mImagenDelDia.date();
        this.contexto = mImagenDelDia.explanation();
        this.imagen = mImagenDelDia.image();
    }

    public void actualizarDatos(PutImagenDelDia putImagenDelDia){
        this.contexto = putImagenDelDia.contexto();
        this.imagen = putImagenDelDia.imagen();
        this.fechaPublicacion = putImagenDelDia.fecha_publicacion();
    }

    public ImagenDelDia(PostImagenDelDia postImagenDelDia){
        this.titulo = postImagenDelDia.titulo();
        this.contexto = postImagenDelDia.contexto();
        this.imagen = postImagenDelDia.imagen();
        this.fechaPublicacion = postImagenDelDia.fecha_publicacion();
    }

    @Override
    public String toString(){
        return
                ":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n" +
                        "\n"+
                "** Título: " + titulo + "\n" +
                "** Fecha de publicación: " + fechaPublicacion + "\n" +
                "** Contexto: " + contexto + "\n" +
                "** Imagen: " + imagen + "\n" +
                        "\n" +
                ":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n";
    }

    public Long getId() {
        return id;
    }
    public String getTitulo() {
        return titulo;
    }
    public String getFechaPublicacion(){
        return fechaPublicacion;
    }
    public String getContexto(){
        return contexto;
    }
    public String getImagen() {
        return imagen;
    }
}


