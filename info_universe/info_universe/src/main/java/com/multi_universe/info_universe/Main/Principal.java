package com.multi_universe.info_universe.Main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.multi_universe.info_universe.Entity.AsteroidesAvistadosPorDia;
import com.multi_universe.info_universe.Entity.FotosMarte;
import com.multi_universe.info_universe.Entity.ImagenDelDia;
import com.multi_universe.info_universe.Entity.ImagenTierraSatelital;
import com.multi_universe.info_universe.RepositorioDB.AsteroidesAvistadosPorDiaRepository;
import com.multi_universe.info_universe.RepositorioDB.FotosMarteRepository;
import com.multi_universe.info_universe.RepositorioDB.ImagenDelDiaRepository;
import com.multi_universe.info_universe.RepositorioDB.ImagenTierraSatelitalRepository;
import com.multi_universe.info_universe.DTO.*;
import com.multi_universe.info_universe.Service.ConsultaApi;
import com.multi_universe.info_universe.Service.ConvierteDatos;


import java.util.*;

public class Principal {

    private final String API_KEY = "khx16XFIxhqOQ8fkwzrGvRkWGgag9vdxr6bV5WbF";
    private final ConvierteDatos convierteDatos = new ConvierteDatos();
    private final ConsultaApi consultaApi = new ConsultaApi();
    Scanner teclado = new Scanner(System.in);
    int input;
    ImagenDelDiaRepository imagenDelDiaRepository;
    FotosMarteRepository fotosMarteRepository;
    AsteroidesAvistadosPorDiaRepository asteroidesAvistadosPorDiaRepository;
    ImagenTierraSatelitalRepository imagenTierraSatelitalRepository;

    public Principal(ImagenTierraSatelitalRepository imagenTierra, ImagenDelDiaRepository imagenDia, FotosMarteRepository fotosMarte, AsteroidesAvistadosPorDiaRepository asteroidesAvistados){
        this.imagenTierraSatelitalRepository = imagenTierra;
        this.imagenDelDiaRepository = imagenDia;
        this.fotosMarteRepository = fotosMarte;
        this.asteroidesAvistadosPorDiaRepository = asteroidesAvistados;
    };

    public void imagenDelDia() {

        String jsonImagenDelDia = consultaApi.cuerpoApi("https://api.nasa.gov/planetary/apod?api_key=" + API_KEY);
        ImagenDelDiaDTO mImagenDelDia = convierteDatos.obtenerDatos(jsonImagenDelDia, ImagenDelDiaDTO.class);

        List<ImagenDelDiaDTO> lista_ImagenDelDiaDTO = new ArrayList<>(List.of
                (new ImagenDelDiaDTO(mImagenDelDia.title(), mImagenDelDia.date(), mImagenDelDia.explanation(), mImagenDelDia.image())));
        List<ImagenDelDia> listaImagenDelDia = new ArrayList<>();

        for (ImagenDelDiaDTO datos : lista_ImagenDelDiaDTO) {
            ImagenDelDia imagenDelDia = new ImagenDelDia(datos);
            listaImagenDelDia.add(imagenDelDia);
        }
        listaImagenDelDia.forEach(System.out::println);

        while (input != 5 & input != 0){
            System.out.println("""
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                Deseas guardar la consulta realizada en la base de datos?
                
                Pulsa 5 para confirmar y 0 cancelar
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
            input = teclado.nextInt();

            if (input == 5){
                imagenDelDiaRepository.saveAll(listaImagenDelDia);
                break;
            } else if (input == 0) {
                break;
            } else{
                System.out.println("""
                 :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                 El número ingresado es invalido!!!
                 :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                 """);
            }
        }

        System.out.println("""
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                Mas opciones de consulta:
                *************************
                
                1 - Ver lista completa de imagenes guardadas 
                2 - Ver solo 5 imagenes guardados
                3 - Ver imagenes guardadas por una fecha en específico
                4 - Consultar imagen por nombre
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");

        input = teclado.nextInt();

        switch (input){
            case 1:
                var listaCompleta = imagenDelDiaRepository.findAll();
                if (listaCompleta.isEmpty()) {
                    System.out.println("""
                            :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                            --No se encontraron resultados--
                            :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                            """);
                }else {
                    listaCompleta.forEach(System.out::println);
                }
                return;
            case 2:
                var listafiltrada = imagenDelDiaRepository.findTop5ByOrderByFechaPublicacionDesc();
                if (listafiltrada.isEmpty()) {
                    System.out.println("""
                            :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                            --No se encontraron resultados--
                            :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                            """);
                }else {
                    listafiltrada.forEach(System.out::println);
                }
                return;
            case 3:
                System.out.println("""
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                Ingresa la fecha de la cual deseas consultar las imagenes guardadas, recuerda
                utilizar el formato YYYY-MM-DD
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                """);
                teclado.nextLine();
                String input2 = teclado.nextLine();
                var listaPorFecha = imagenDelDiaRepository.imagenPorFecha(input2);
                if (listaPorFecha.isEmpty()) {
                    System.out.println("""
                            :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                            --No se encontraron resultados--
                            :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                            """);
                }else {
                    listaPorFecha.forEach(System.out::println);
                }
                return;
            case 4:
                System.out.println("""
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                Ingresa el título de la imagen que deseas consultar de la base de datos:
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                """);
                teclado.nextLine();
                String input3 = teclado.nextLine();
                var listaPorNombre = imagenDelDiaRepository.imagenPorNombre(input3);
                if (listaPorNombre.isEmpty()) {
                    System.out.println("""
                            :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                            --No se encontraron resultados--
                            :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                            """);
                }else {
                    listaPorNombre.forEach(System.out::println);
                }
                return;
            default:
                System.out.println("""
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                Error!!  -  número invalido:
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                """);
                break;
        }
    }

    public void consultaObjetosPorDia() throws JsonProcessingException {
        try {
        System.out.println("""
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                Ingresa la fecha inicial desde donde deseas realizar la consulta de los 
                asteroides identificados en dicho periodo de tiempo:
                
                NOTA: Recuerda solo realizar consultas con un intervalo máximo de 7 días
                      y el formato de la fecha debe ser: YYYY-MM-DD
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
        String fechaInicial = teclado.nextLine();
        System.out.println("""
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                Ingresa la fecha final desde donde deseas realizar la consulta de los 
                asteroides identificados en dicho periodo de tiempo:
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
        String fechaFinal = teclado.nextLine();
        String jsonObjetoDelDia = consultaApi.cuerpoApi
                ("https://api.nasa.gov/neo/rest/v1/feed?start_date=" + fechaInicial + "&end_date=" + fechaFinal + "&api_key=" + API_KEY);

        AsteroideDatosGeneralDTO asteroides = convierteDatos.obtenerDatos(jsonObjetoDelDia, AsteroideDatosGeneralDTO.class);
        List<AsteroidesAvistadosPorDia> asteroidesAvistadosPorDiaList = new ArrayList<>();

// Recorrido de cada llave, relacionada a las fechas


            for (Map.Entry<String, List<AsteroideDTO>> entrada : asteroides.near_earth_objects().entrySet()) {
                String fecha = entrada.getKey();
                List<AsteroideDTO> listaAsteroides = entrada.getValue();

                // Recorrido de cada lista de asteroides
                for (AsteroideDTO asteroide : listaAsteroides) {
                    AsteroidesAvistadosPorDia asteroidesDTO = new AsteroidesAvistadosPorDia(asteroide);
                    asteroidesDTO.setFecha(fecha);
                    asteroidesDTO.setCantidadAsteroides(listaAsteroides.size());
                    asteroidesAvistadosPorDiaList.add(asteroidesDTO);
                }
            }
            asteroidesAvistadosPorDiaList.forEach(System.out::println);

        while (input != 5 & input != 0){
            System.out.println("""
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                Deseas guardar la consulta realizada en la base de datos?
                
                Pulsa 5 para confirmar y 0 cancelar
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
            input = teclado.nextInt();

            if (input == 5){
                asteroidesAvistadosPorDiaRepository.saveAll(asteroidesAvistadosPorDiaList);
                break;
            } else if (input == 0) {
                break;
            } else{
                System.out.println("""
                 :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                 El número ingresado es invalido!!!
                 :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                 """);
            }
        }
        } catch (RuntimeException e) {
            System.out.println("""
                    ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    Error: Las consultas solo se pueden realizar máximo en un período de 7 días
                    o en su defecto los datos ingresados son inválidos
                    :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    """
            );
            return;
        }

        System.out.println("""
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                Mas opciones de consulta:
                *************************
                
                1 - Ver lista completa de asteroides guardados 
                2 - Ver solo 10 asteroides guardados
                3 - Ver asteroides guardados por una fecha en específico
                4 - Consultar asteroide por nombre
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
        input = teclado.nextInt();

        switch (input){
            case 1:
                var listaCompleta = asteroidesAvistadosPorDiaRepository.findAll();
                if (listaCompleta.isEmpty()) {
                    System.out.println("""
                            :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                            --No se encontraron resultados--
                            :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                            """);
                }else {
                    listaCompleta.forEach(System.out::println);
                }
                return;
            case 2:
                var listafiltrada = asteroidesAvistadosPorDiaRepository.findTop10ByOrderByFechaDesc();
                if (listafiltrada.isEmpty()) {
                    System.out.println("""
                            :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                            --No se encontraron resultados--
                            :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                            """);
                }else {
                    listafiltrada.forEach(System.out::println);
                }
                return;
            case 3:
                System.out.println("""
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                Ingresa la fecha de la cual deseas consultar los objetos guardados, recuerda
                utilizar el formato YYYY-MM-DD
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                """);
                teclado.nextLine();
                String input2 = teclado.nextLine();
                var listaPorFecha = asteroidesAvistadosPorDiaRepository.asteroidesPorFecha(input2);
                if (listaPorFecha.isEmpty()) {
                    System.out.println("""
                            :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                            --No se encontraron resultados--
                            :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                            """);
                }else {
                    listaPorFecha.forEach(System.out::println);
                }
                return;
            case 4:
                System.out.println("""
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                Ingresa el nombre de asteroide que deseas consultar de la base de datos:
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                """);
                teclado.nextLine();
                String input3 = teclado.nextLine();
                var listaPorNombre = asteroidesAvistadosPorDiaRepository.asteroidesPorNombre(input3);
                if (listaPorNombre.isEmpty()) {
                    System.out.println("""
                            :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                            --No se encontraron resultados--
                            :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                            """);
                }else {
                    listaPorNombre.forEach(System.out::println);
                }
                return;
            default:
                System.out.println("""
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                Error!!  -  número invalido:
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                """);
                break;
        }
    }

    public void fotoMarte() {
        String jsonFotoMarte =
                consultaApi.cuerpoApi("https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&api_key=" + API_KEY);

        FotosMarteDTO fotosMarteDTO = convierteDatos.obtenerDatos(jsonFotoMarte, FotosMarteDTO.class);

        List<FotosMarte> fotosMarteList = new ArrayList<>();

        for(FotosMarteDatosDTO datos : fotosMarteDTO.photos()){
            FotosMarteFotoDTO camera = datos.camera();
            FotosMarte fotosMarte = new FotosMarte(datos,camera);
            fotosMarteList.add(fotosMarte);
        }
        fotosMarteList.forEach(System.out::println);

        while (input != 5 & input != 0){
            System.out.println("""
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                Deseas guardar la consulta realizada en la base de datos?
                
                Pulsa 5 para confirmar y 0 cancelar
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
            input = teclado.nextInt();

            if (input == 5){
                fotosMarteRepository.saveAll(fotosMarteList);
                break;
            } else if (input == 0) {
                break;
            } else {
                System.out.println("""
                 :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                 El número ingresado es invalido!!!
                 :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                 """);
            }
        }

        System.out.println("""
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                Mas opciones de consulta:
                *************************
                
                1 - Ver lista completa de fotos guardadas 
                2 - Ver solo 5 fotos de marte guardadas
                3 - Ver fotos guardadas en una fecha en específico
                4 - Consultar foto por nombre
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
        input = teclado.nextInt();

        switch (input){
            case 1:
                var listaCompleta = fotosMarteRepository.findAll();
                if (listaCompleta.isEmpty()) {
                    System.out.println("""
                            :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                            --No se encontraron resultados--
                            :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                            """);
                }else {
                    listaCompleta.forEach(System.out::println);
                }
                return;
            case 2:
                var listafiltrada = fotosMarteRepository.findTop5ByOrderByIdDesc();
                if (listafiltrada.isEmpty()) {
                    System.out.println("""
                            :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                            --No se encontraron resultados--
                            :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                            """);
                }else {
                    listafiltrada.forEach(System.out::println);
                }
                return;
            case 3:
                System.out.println("""
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                Ingresa la fecha de la cual deseas consultar las imagenes guardadas, recuerda
                utilizar el formato YYYY-MM-DD
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                """);
                teclado.nextLine();
                String input2 = teclado.nextLine();
                var listaPorFecha = fotosMarteRepository.fotosPorFecha(input2);
                listaPorFecha.forEach(System.out::println);
                return;
            case 4:
                System.out.println("""
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                Ingresa el título de la imagen que deseas consultar de la base de datos:
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                """);
                teclado.nextLine();
                String input3 = teclado.nextLine();
                var listaPorNombre = fotosMarteRepository.fotosPorNombre(input3);
                if (listaPorNombre.isEmpty()) {
                    System.out.println("""
                            :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                            --No se encontraron resultados--
                            :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                            """);
                }else {
                    listaPorNombre.forEach(System.out::println);
                }
                return;
            default:
                System.out.println("""
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                Error!!  -  número invalido:
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                """);
                break;
        }
    }

    public void imagenTierraSatelital(){

        try {
            teclado.nextLine();

            System.out.println("""
                    :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    Ingresa la fecha desde donde deseas realizar la consulta:
                    
                    NOTA: las fechas deben ser del año 2014 en adelante 
                          y el formato de la fecha debe ser: YYYY-MM-DD
                    :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
            String fecha = teclado.nextLine();
            ;

            System.out.println("""
                    :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    Ingresa la longitud desde donde deseas realizar la consulta:
                    :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
            String longitud = teclado.nextLine();
            ;
            System.out.println("""
                    :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    Ingresa la latitud desde donde deseas realizar la consulta:
                    :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
            String latitud = teclado.nextLine();
            ;

            String jsonTierraSatelital =
                    consultaApi.cuerpoApi("https://api.nasa.gov/planetary/earth/assets?lon=" + longitud + "&lat=" + latitud + "&date=" + fecha +
                            "&dim=0.15&api_key=" + API_KEY);


            List<ImagenTierraSateliteDTO> imagenTierraSateliteDTOList = new ArrayList<>(List.of(convierteDatos.obtenerDatos(jsonTierraSatelital, ImagenTierraSateliteDTO.class)));
            List<ImagenTierraSatelital> imagenTierraSatelitallist = new ArrayList<>();

            for (ImagenTierraSateliteDTO general : imagenTierraSateliteDTOList) {
                DatosPlanetaDTO datos = new DatosPlanetaDTO(general.datosPlanetaDTO().dataset(), general.datosPlanetaDTO().planet());
                ImagenTierraSatelital datosPlanetaDTO = new ImagenTierraSatelital(general, datos);
                imagenTierraSatelitallist.add(datosPlanetaDTO);
            }

            imagenTierraSatelitallist.forEach(System.out::println);


            while (input != 5 & input != 0) {
                System.out.println("""
                        :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                        Deseas guardar la consulta realizada en la base de datos?
                        
                        Pulsa 5 para confirmar y 0 cancelar
                        :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
                input = teclado.nextInt();

                if (input == 5) {
                    imagenTierraSatelitalRepository.saveAll(imagenTierraSatelitallist);
                    break;
                } else if (input == 0) {
                    break;
                } else {
                    System.out.println("""
                            :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                            El número ingresado es invalido!!!
                            :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                            """);
                }
            }
        }catch (RuntimeException e){
            System.out.println("""
                    :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    ERROR: Los datos ingresados son inválidos, por favor revisar e intentar nuevamente
                    :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    """);
            return;
        }

        System.out.println("""
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                Mas opciones de consulta:
                *************************
                
                1 - Ver lista completa de fotos guardadas 
                2 - Ver solo 5 fotos guardadas
                3 - Ver fotos guardadas en una fecha en específico
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
        input = teclado.nextInt();

        switch (input){
            case 1:
                var listaCompleta = imagenTierraSatelitalRepository.findAll();
                if (listaCompleta.isEmpty()) {
                    System.out.println("""
                            :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                            --No se encontraron resultados--
                            :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                            """);
                }else {
                    listaCompleta.forEach(System.out::println);
                }
                return;
            case 2:
                var listafiltrada = imagenTierraSatelitalRepository.findTop5ByOrderByIdDesc();
                if (listafiltrada.isEmpty()) {
                    System.out.println("""
                            :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                            --No se encontraron resultados--
                            :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                            """);
                }else {
                    listafiltrada.forEach(System.out::println);
                }
                return;
            case 3:
                System.out.println("""
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                Ingresa la fecha de la cual deseas consultar las imagenes guardadas, recuerda
                utilizar el formato YYYY-MM-DD
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                """);
                teclado.nextLine();
                String input2 = teclado.nextLine();
                var listaPorFecha = imagenTierraSatelitalRepository.satelitePorFecha(input2);
                if (listaPorFecha.isEmpty()) {
                    System.out.println("""
                            :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                            --No se encontraron resultados--
                            :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                            """);
                }else {
                    listaPorFecha.forEach(System.out::println);
                }
                return;
            default:
                System.out.println("""
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                Error!!  -  número invalido:
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                """);
                break;
        }
    }

    public void menuPrincipal () throws JsonProcessingException {

        System.out.println("""
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                Bienvenido a la API-consulta de la NASA:
                ****************************************
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");

        System.out.println("""
                
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                En esta API podrás consultar algunos datos recolectados por la NASA, acerca del planeta
                y sus alrededores:
                ***************************************************************************************
                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");

        try {
            System.out.println("""
                    
                    :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    Dígita cualquier número mayor a 0 para ingresar al menú principal o
                    directamente el 0 para salir del programa:
                    ***************************************************************************************
                    :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
            input = teclado.nextInt();

            while (input != 0) {

                System.out.println("""
                        
                        :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                        Menú de consulta:
                        *****************
                        
                        Digita el número correspondiente a la opción de consulta que desees seleccionar:
                        ********************************************************************************
                        
                        1 - Ver imagen del día
                        2 - Visualizar los asteroides avistados alrededor de la tierra en una fecha 
                            determinada
                        3 - Consultar asombrosas fotos capturadas de Marte
                        4 - Observar impresionantes imagenes de la tierra, capturadas por el satélite
                            LANDSAT 8 
                        0 - Salir del programa  
                        
                        :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");

                input = teclado.nextInt();

                switch (input) {
                    case 1:

                        while (input == 1) {
                            imagenDelDia();
                            System.out.println("""
                                    :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                                    Deseas repetir la misma consulta?
                                    
                                    Pulsa 1 para confirmar o 5 para retornar la menú principal
                                    :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
                            input = teclado.nextInt();
                            if (input == 0) {
                                break;
                            }
                        }
                        break;
                    case 2:

                        while (input == 2) {
                            teclado.nextLine();
                            consultaObjetosPorDia();
                            System.out.println("""
                                    :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                                    Deseas repetir la misma consulta?
                                    
                                    Pulsa 2 para confirmar o 5 para retornar la menú principal
                                    :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
                            input = teclado.nextInt();
                            if (input == 0) {
                                break;
                            }
                        }
                        break;
                    case 3:

                        while (input == 3) {
                            fotoMarte();
                            System.out.println("""
                                    :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                                    Deseas repetir la misma consulta?
                                    
                                    Pulsa 3 para confirmar o 5 para retornar la menú principal
                                    :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
                            input = teclado.nextInt();
                            if (input == 0) {
                                break;
                            }
                        }
                        break;
                    case 4:

                        while (input == 4) {
                            imagenTierraSatelital();
                            System.out.println("""
                                    :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                                    Deseas repetir la misma consulta?
                                    
                                    Pulsa 4 para confirmar o 5 para retornar la menú principal
                                    :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
                            input = teclado.nextInt();
                            if (input == 0) {
                                break;
                            }
                        }
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("""
                                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                                Error: El número digitado es invalido, prueba nuevamente!!!
                                :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::""");
                        break;
                }
            }
        }catch (InputMismatchException e){
            System.out.println("""
                    :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    ERROR: Los datos ingresados son invalidos!!!
                    :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
                    """);
            return ;
        }
    }
}
