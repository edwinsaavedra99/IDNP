package com.myappdeport.repository;

import java.util.Optional;

/**
 * Se implementa las operaiocens crud que seran las base para todos los repositorios existentes.
 *
 * @param <E> Es la clase que se sera persistida.
 * @param <I> Es la clave de la clase que sera persistida.
 */
public interface CrudRepository<E, I> {
    /**
     * Operación guardar o actualizar en el almacen de datos.
     *
     * @param entity Es el objeto que sera persistido
     * @param <S>    Es algun elemento que extiende de la clase persistida.
     * @return Es el objeto persistido con cierto cambio.
     */
    <S extends E> S save(S entity);

    /**
     * Operación que guarda todos los elementos dentro del almacen de datos.
     *
     * @param entities Son todos los objetos a ser guardados.
     * @param <S>      Es algun elemento que extiende de la clase persistida.
     * @return Son los objetos que fueron persistidos con ciertos cambios.
     */
    <S extends E> Iterable<S> saveAll(Iterable<S> entities);

    /**
     * Operación obtener por su id del almacen de datos.
     *
     * @param identifier Es su identificador en el almacen de datos.
     * @return Es el elemento que se encontro envuelto por un optional si no se encuentra.
     * @see Optional
     */
    Optional<E> findById(I identifier);

    /**
     * Operación optener todos los elementos de el almacen de datos.
     *
     * @return RSon todas las instancias que se encuentran en el almacen de datos.
     */
    Iterable<E> findAll();

    /**
     * Es la operación eliminar de el almacen de datos
     *
     * @param entity Es la entidad que se borrara de el almacen de datos.
     */
    void delete(E entity);

    /**
     * Es la operacion borrar varios elementos de el almacen de datos.
     *
     * @param entities
     */
    void deleteAll(Iterable<? extends E> entities);

    /**
     * Operación obtener el total de elemetos del alamcen de datos.
     *
     * @return Es el numero total de entidades disponibles.
     */
    long count();
}
