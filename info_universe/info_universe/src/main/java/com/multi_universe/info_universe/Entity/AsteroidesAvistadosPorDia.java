package com.multi_universe.info_universe.Entity;

import com.multi_universe.info_universe.DTO.AsteroideDTO;
import com.multi_universe.info_universe.RestController.DTO_Controller.POST.PostAsteroides;
import com.multi_universe.info_universe.RestController.DTO_Controller.PUT.PutAsteroides;
import jakarta.persistence.*;

@Entity
@Table(name = "asteroides_avistados")
public class AsteroidesAvistadosPorDia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer cantidadAsteroidesPorFecha;
    private String name;
    private String fecha;
    @Column(unique = true)
    private String neo_reference_id;
    private double absolute_magnitude_h;
    private Boolean is_potentially_hazardous_asteroid;

    public AsteroidesAvistadosPorDia() {
    }

    public AsteroidesAvistadosPorDia(AsteroideDTO asteroide) {
        this.name = asteroide.name();
        this.neo_reference_id = asteroide.neo_reference_id();
        this.absolute_magnitude_h = asteroide.absolute_magnitude_h();
        this.is_potentially_hazardous_asteroid = asteroide.is_potentially_hazardous_asteroid();
    }

    public void agregarDatos(PostAsteroides postAsteroides) {
        this.name = postAsteroides.nombre();
        this.fecha = postAsteroides.fecha_identificacion();
        this.cantidadAsteroidesPorFecha = postAsteroides.cantidad_asteroides_por_fecha();
        this.neo_reference_id = postAsteroides.id_asteroide();
        this.absolute_magnitude_h = postAsteroides.magnitud_asteroide();
        this.is_potentially_hazardous_asteroid = postAsteroides.es_peligroso();
    }

    public void modificarDatos(PutAsteroides putAsteroides){
        this.name = putAsteroides.nombre();
        this.absolute_magnitude_h = putAsteroides.magnitud_asteroide();
        this.is_potentially_hazardous_asteroid = putAsteroides.es_peligroso();
    }

    @Override
    public String toString(){
        return
                ":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n" +
                "\n"+
                "** Fecha : " + fecha + "\n" +
                "\n"+
                "** Total de objetos identificados en este periodo: " + cantidadAsteroidesPorFecha + "\n" +
                "** Id del asteoride: " + neo_reference_id + "\n" +
                "** Nombre: " + name + "\n" +
                "** Magnitud aproximada: " + absolute_magnitude_h + "\n" +
                "** ¿Es potencialmente peligroso?: " + is_potentially_hazardous_asteroid + "\n" +
                "\n" +
                ":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n";
    }

    //getters and setters


    public Long getId() {
        return id;
    }

    public String getNeo_reference_id() {
        return neo_reference_id;
    }

    public String getName() {
        return name;
    }

    public Boolean getIs_potentially_hazardous_asteroid() {
        return is_potentially_hazardous_asteroid;
    }

    public double getAbsolute_magnitude_h() {
        return absolute_magnitude_h;
    }

    public Integer getCantidadAsteroidesPorFecha() {
        return cantidadAsteroidesPorFecha;
    }

    public String getFecha() {
        return fecha;
    }

    public int setCantidadAsteroides(Integer cantidadAsteroides) {
        this.cantidadAsteroidesPorFecha = cantidadAsteroides;
        return 0;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
