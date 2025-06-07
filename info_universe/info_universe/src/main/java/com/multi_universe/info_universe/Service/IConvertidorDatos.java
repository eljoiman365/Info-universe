package com.multi_universe.info_universe.Service;

public interface IConvertidorDatos {

    <T> T obtenerDatos(String json, Class<T> clase);
}
