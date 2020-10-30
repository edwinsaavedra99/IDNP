package com.myappdeport.repository;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.Optional;

public interface IRepository<E, I> {
    /**
     * Operación guardar o actualizar en el almacen de datos.
     *
     * @param entity Es el objeto que sera persistido
     * @return Es el objeto persistido con cierto cambio.
     */
    Task<E> save(E entity);

    /**
     * Operación  actualizar en el almacen de datos.
     *
     * @param entity Es el objeto que sera persistido
     */
    Task<Void> update(E entity);

    /**
     * Operación que guarda todos los elementos dentro del almacen de datos.
     *
     * @param entities Son todos los objetos a ser guardados.
     * @return Son los objetos que fueron persistidos con ciertos cambios.
     */
    Task<List<E>> saveAll(List<E> entities);

    /**
     * Operación obtener por su id del almacen de datos.
     *
     * @param identifier Es su identificador en el almacen de datos.
     * @return Es el elemento que se encontro envuelto por un optional si no se encuentra.
     * @see Optional
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    Task<Optional<E>> findById(I identifier);

    /**
     * Operación optener todos los elementos de el almacen de datos.
     *
     * @return RSon todas las instancias que se encuentran en el almacen de datos.
     */
    Task<List<E>> findAll();

    /**
     * Es la operación eliminar de el almacen de datos
     *
     * @param entity Es la entidad que se borrara de el almacen de datos.
     */
    Task<Void> delete(E entity);

    /**
     * Es la operacion borrar varios elementos de el almacen de datos.
     *
     * @param entities Son las entidades a ser borradas
     */
    Task<List<Void>> delete(List<E> entities);

    /**
     * Es la operacion borrar todos los elementos del almacen de datos.
     */
    Task<Void> deleteAll();

    /**
     * Operación obtener el total de elemetos del alamcen de datos.
     *
     * @return Es el numero total de entidades disponibles.
     */
    Task<Integer> count();
}
