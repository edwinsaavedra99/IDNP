package com.myappdeport.repository;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.myappdeport.model.database.firebase.FirebaseEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class FireStoreRepository<E extends FirebaseEntity> implements IRepository<E> {

    private static final String TAG = FireStoreRepository.class.getSimpleName();

    private final CollectionReference collectionReference;

    private final Class<E> eClass;

    public FireStoreRepository(Class<E> eClass) {
        this.eClass = eClass;
        this.collectionReference = FirebaseFirestore.getInstance().collection(this.eClass.getSimpleName());
    }

    /**
     * Operación guardar o actualizar en el almacen de datos.
     *
     * @param entity Es el objeto que sera persistido
     * @return Es el objeto persistido con cierto cambio.
     */
    @Override
    public Task<E> save(E entity) {
        return this.collectionReference.add(entity).continueWithTask(new Continuation<DocumentReference, Task<E>>() {
            @Override
            public Task<E> then(@NonNull Task<DocumentReference> task) throws Exception {
                entity.setDocumentId(task.getResult().getId());
                return Tasks.forResult(entity);
            }
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
    public Task<Collection<E>> saveAll(Collection<E> entities) {
        Collection<E> entitiesList = new ArrayList<>();
        for (E entity : entities) {
            entitiesList.add(save(entity).getResult());
        }
        return Tasks.forResult(entitiesList);
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
        return this.collectionReference.document(identifier).get().continueWithTask(new Continuation<DocumentSnapshot, Task<Optional<E>>>() {
            @Override
            public Task<Optional<E>> then(@NonNull Task<DocumentSnapshot> task) throws Exception {
                return Tasks.forResult(Optional.of(task.getResult().toObject(eClass)));
            }
        });
    }

    /**
     * Operación optener todos los elementos de el almacen de datos.
     *
     * @return RSon todas las instancias que se encuentran en el almacen de datos.
     */
    @Override
    public Task<Collection<E>> findAll() {
        return this.collectionReference.get().continueWithTask(new Continuation<QuerySnapshot, Task<Collection<E>>>() {
            @Override
            public Task<Collection<E>> then(@NonNull Task<QuerySnapshot> task) throws Exception {
                return Tasks.forResult(task.getResult().toObjects(eClass));
            }
        });
    }

    /**
     * Es la operación eliminar de el almacen de datos
     *
     * @param entity Es la entidad que se borrara de el almacen de datos.
     */
    @Override
    public Task<Void> delete(E entity) {
        if (entity.getDocumentId() != null)
            return this.collectionReference.document(entity.getDocumentId()).delete();
        else
            return null;
    }

    /**
     * Es la operacion borrar varios elementos de el almacen de datos.
     *
     * @param entities Son las entidades a ser borradas
     */
    @Override
    public Task<Collection<Void>> deleteAll(Collection<E> entities) {
        Collection<Void> entitiesList = new ArrayList<>();
        for (E entity : entities) {
            entitiesList.add(delete(entity).getResult());
        }
        return Tasks.forResult(entitiesList);
    }

    /**
     * Operación obtener el total de elemetos del alamcen de datos.
     *
     * @return Es el numero total de entidades disponibles.
     */
    @Override
    public Task<Integer> count() {
        return this.collectionReference.get().continueWithTask(new Continuation<QuerySnapshot, Task<Integer>>() {
            @Override
            public Task<Integer> then(@NonNull Task<QuerySnapshot> task) throws Exception {
                return Tasks.forResult(task.getResult().getDocuments().size());
            }
        });
    }
}
