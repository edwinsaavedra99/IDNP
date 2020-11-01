package com.myappdeport.repository.firebase;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.myappdeport.model.entity.database.EntityDatabase;
import com.myappdeport.repository.IRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class FireStoreRepository<E extends EntityDatabase> implements IRepository<E, String> {

    protected String TAG = FireStoreRepository.class.getSimpleName();

    protected final CollectionReference collectionReference;

    protected final Class<E> entityClass;

    public FireStoreRepository(Class<E> entityClass) {
        this.entityClass = entityClass;
        this.collectionReference = FirebaseFirestore.getInstance().collection(this.entityClass.getSimpleName());
    }

    /**
     * Operación guardar o actualizar en el almacen de datos.
     *
     * @param entity Es el objeto que sera persistido
     * @return Es el objeto persistido con cierto cambio.
     */
    @Override
    public Task<E> save(E entity) {
        return this.collectionReference.add(entity).continueWithTask(task -> {
            entity.setDocumentId(Objects.requireNonNull(task.getResult()).getId());
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
        if (entity.getDocumentId() != null)
            /*Operacioón actualizar*/
            return this.collectionReference.document(entity.getDocumentId()).set(entity);
        else
            return null;
    }

    /**
     * Operación que guarda todos los elementos dentro del almacen de datos.
     *
     * @param entities Son todos los objetos a ser guardados.
     * @return Son los objetos que fueron persistidos con ciertos cambios.
     */
    @Override
    public Task<List<E>> saveAll(List<E> entities) throws InstantiationException, IllegalAccessException {
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        for (E entity : entities) {
            DocumentReference documentReference = collectionReference.document();
            entity.setDocumentId(documentReference.getId());
            batch.set(documentReference, entity);
        }
        return batch.commit().continueWithTask(task -> {
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
    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Task<Optional<E>> findById(String identifier) {
        return this.collectionReference.document(identifier).get().continueWithTask(task -> Tasks.forResult(Optional.ofNullable(Objects.requireNonNull(task.getResult()).toObject(entityClass))));
    }

    /**
     * Operación optener todos los elementos de el almacen de datos.
     *
     * @return RSon todas las instancias que se encuentran en el almacen de datos.
     */
    @Override
    public Task<List<E>> findAll() {
        return this.collectionReference.get().continueWithTask(task -> Tasks.forResult(Objects.requireNonNull(task.getResult()).toObjects(entityClass)));
    }

    /**
     * Es la operación eliminar de el almacen de datos
     *
     * @param entity Es la entidad que se borrara de el almacen de datos.
     */
    @Override
    public Task<Void> delete(E entity) {
        return Tasks.call(this.collectionReference.document(entity.getDocumentId()).delete()::getResult);
    }

    /**
     * Es la operacion borrar varios elementos de el almacen de datos.
     *
     * @param entities Son las entidades a ser borradas
     */
    @Override
    public Task<List<Void>> delete(List<E> entities) {
        return Tasks.call(() -> {
            List<Void> entitiesList = new ArrayList<>();
            for (E entity : entities) {
                entitiesList.add(delete(entity).getResult());
            }
            return entitiesList;
        });
    }

    /**
     * Es la operacion borrar todos los elementos del almacen de datos.
     */
    @Override
    public Task<Void> deleteAll() {
        return Tasks.call(() -> {
            Objects.requireNonNull(this.collectionReference.getParent()).delete();
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
        return this.collectionReference.get().continueWithTask(task -> Tasks.forResult(Objects.requireNonNull(task.getResult()).getDocuments().size()));
    }
}
