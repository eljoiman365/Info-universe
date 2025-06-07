package com.multi_universe.info_universe.Entity;

import com.multi_universe.info_universe.DTO.FotosMarteDatosDTO;
import com.multi_universe.info_universe.DTO.FotosMarteFotoDTO;
import com.multi_universe.info_universe.RestController.DTO_Controller.POST.PostFotosMarte;
import com.multi_universe.info_universe.RestController.DTO_Controller.PUT.PutFotosMarte;
import jakarta.persistence.*;

@Entity
@Table(name = "fotos_marte")
public class FotosMarte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private int idConjunto;
    private String img_src;
    private  String earth_date;
    private int idFoto;
    private String name;
    private String full_name;

    public FotosMarte(){}

    public FotosMarte(FotosMarteDatosDTO fotosMarteDatosDTO, FotosMarteFotoDTO fotosMarteFotoDTO){
        this.idConjunto = fotosMarteDatosDTO.idConjunto();
        this.img_src = fotosMarteDatosDTO.img_src();
        this.earth_date = fotosMarteDatosDTO.earth_date();
        this.idFoto = fotosMarteFotoDTO.idFoto();
        this.name = fotosMarteFotoDTO.name();
        this.full_name = fotosMarteFotoDTO.full_name();
    }

    public void actulizarDatos(PutFotosMarte putFotosMarte){
        this.img_src = putFotosMarte.imagen();
        this.name = putFotosMarte.titulo();
        this.full_name = putFotosMarte.nombre_camara();
        this.earth_date = putFotosMarte.fecha();
    }

    public void agregarDatos (PostFotosMarte postFotosMarte){
        this.idConjunto = postFotosMarte.id_conjunto_fotos() ;
        this.img_src = postFotosMarte.imagen();
        this.earth_date = postFotosMarte.fecha();
        this.idFoto = postFotosMarte.id_foto();
        this.name = postFotosMarte.titulo();
        this.full_name = postFotosMarte.nombre_camara();
    }

    @Override
    public String toString(){
        return
                ":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n" +
                        "\n"+
                        "** Id: " + idConjunto + "\n" +
                        "** Fecha de publicación: " + earth_date + "\n" +
                        "** Autor: " + name + "\n" +
                        "** Nombre foto: " + full_name + "\n" +
                        "** Id Foto: " + idFoto + "\n" +
                        "** Imagen: " + img_src + "\n" +
                        "\n" +
                ":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n";
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public int getIdConjunto() {
        return idConjunto;
    }

    public int getIdFoto() {
        return idFoto;
    }

    public String getEarth_date() {
        return earth_date;
    }

    public String getFull_name() {
        return full_name;
    }
    public String getImg_src(){
        return img_src;
    }
}
