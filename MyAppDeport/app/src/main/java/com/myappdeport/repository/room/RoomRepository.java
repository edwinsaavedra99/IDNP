package com.myappdeport.repository.room;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.myappdeport.model.entity.database.EntityDatabase;
import com.myappdeport.repository.IRepository;
import com.myappdeport.repository.room.dao.IRoomDao;

import java.util.List;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;

public abstract class RoomRepository<E extends EntityDatabase> implements IRepository<E, Long> {

    private static final String TAG = RoomRepository.class.getSimpleName();

    private final IRoomDao<E, Long> roomDao;

    public RoomRepository(Class<E> entityClass, Context context) {
        this.roomDao = FactoryDaoRoom.getRoomDao(entityClass, context);
    }

    /**
     * Operación guardar o actualizar en el almacen de datos.
     *
     * @param entity Es el objeto que sera persistido
     * @return Es el objeto persistido con cierto cambio.
     */
    @Override
    public Task<E> save(E entity) {
        return Tasks.call(() -> this.roomDao.insert(entity)).continueWithTask(task -> {
            entity.setId(Objects.requireNonNull(task.getResult()));
            return Tasks.forResult(entity);
        });
    }

    /**
     * Operación  actualizar en el almacen de datos.
     *
     * @param entity Es el objeto que sera persistido
     */
    @Override
    public Task<Void> update(E entity) {
        return Tasks.call(() -> {
            this.roomDao.update(entity);
            return null;
        });
    }

    /**
     * Operación que guarda todos los elementos dentro del almacen de datos.
     *
     * @param entities Son todos los objetos a ser guardados.
     * @return Son los objetos que fueron persistidos con ciertos cambios.
     */
    @Override
    public Task<List<E>> saveAll(List<E> entities) {
        return Tasks.call(
                () -> this.roomDao.insertAll(entities)
        ).continueWithTask(task -> {
            Iterator<E> entityIterator = entities.iterator();
            Iterator<Long> identifierIterator = Objects.requireNonNull(task.getResult()).iterator();
            while (entityIterator.hasNext() && identifierIterator.hasNext()) {
                entityIterator.next().setId(identifierIterator.next());
            }
            return Tasks.forResult(entities);
        });
    }

    /**
     * Operación obtener por su id del almacen de datos.
     *
     * @param identifier Es su identificador en el almacen de datos.
     * @return Es el elemento que se encontro envuelto por un optional si no se encuentra.
     * @see Optional
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Task<Optional<E>> findById(Long identifier) {
        return Tasks.call(() -> Optional.of(this.roomDao.findById(identifier)));
    }

    /**
     * Operación optener todos los elementos de el almacen de datos.
     *
     * @return RSon todas las instancias que se encuentran en el almacen de datos.
     */
    @Override
    public Task<List<E>> findAll() {
        return Tasks.call(this.roomDao::findAll);
    }

    /**
     * Es la operación eliminar de el almacen de datos
     *
     * @param entity Es la entidad que se borrara de el almacen de datos.
     */
    @Override
    public Task<Void> delete(E entity) {
        return Tasks.call(() -> {
            this.roomDao.delete(entity);
            return null;
        });
    }

    /**
     * Es la operacion borrar varios elementos de el almacen de datos.
     *
     * @param entities Son las entidades a ser borradas
     */
    @Override
    public Task<List<Void>> delete(List<E> entities) {
        return Tasks.call(() -> {
            this.roomDao.delete(entities);
            return null;
        });
    }

    /**
     * Es la operacion borrar todos los elementos del almacen de datos.
     */
    @Override
    public Task<Void> deleteAll() {
        return Tasks.call(() -> {
            this.roomDao.deleteAll();
            return null;
        });
    }

    /**
     * Operación obtener el total de elemetos del alamcen de datos.
     *
     * @return Es el numero total de entidades disponibles.
     */
    @Override
    public Task<Integer> count() {
        return Tasks.call(this.roomDao::count);
    }
}
