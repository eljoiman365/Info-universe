package com.multi_universe.info_universe.Entity;

import com.multi_universe.info_universe.DTO.DatosPlanetaDTO;
import com.multi_universe.info_universe.DTO.ImagenTierraSateliteDTO;
import com.multi_universe.info_universe.RestController.DTO_Controller.POST.PostImagenTierraSatelital;
import com.multi_universe.info_universe.RestController.DTO_Controller.PUT.PutImagenTierraSatelital;
import jakarta.persistence.*;

@Entity
@Table(name = "imagenes_tierra_satelital")
public class ImagenTierraSatelital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String date;
    @Column(unique = true)
    private String idImagen;
    private String url;
    private String dataset;
    private String planet;

    public ImagenTierraSatelital(){
    }

    public ImagenTierraSatelital(ImagenTierraSateliteDTO imagenTierraSateliteDTO, DatosPlanetaDTO datosPlanetaDTO){

        this.date = imagenTierraSateliteDTO.date();
        this.idImagen = imagenTierraSateliteDTO.id();
        this.url = imagenTierraSateliteDTO.url();
        this.dataset = datosPlanetaDTO.dataset();
        this.planet = datosPlanetaDTO.planet();
    }

    public void actualizarDatos(PutImagenTierraSatelital actualizarImagen){
        this.date = actualizarImagen.fecha();
        this.dataset = actualizarImagen.satelite();
        this.url = actualizarImagen.urlImagen();
    }

    public void agregarDatos(PostImagenTierraSatelital agregarImagen){
        this.date = agregarImagen.fecha();
        this.idImagen = agregarImagen.idImagen();
        this.url = agregarImagen.urlImagen();
        this.dataset = agregarImagen.satelite();
        this.planet = agregarImagen.planeta();
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getDataset() {
        return dataset;
    }

    public String getIdImagen() {
        return idImagen;
    }

    public String getDate() {
        return date;
    }

    public String getPlanet() {
        return planet;
    }

    @Override
    public String toString(){
        return
                ":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n" +
                "\n"+
                "** Id: " + idImagen + "\n" +
                "** Fecha y zona horaria de la imagen: " + date + "\n" +
                "** Nombre del satelite: " + dataset + "\n" +
                "** Planeta: " + planet + "\n" +
                "** Imagen: " + url + "\n" +
                "\n" +
                ":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n";
    }
}
