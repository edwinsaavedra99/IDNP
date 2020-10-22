package com.myappdeport.repository;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;
import androidx.annotation.RequiresApi;
import com.google.firebase.firestore.*;
import com.myappdeport.model.database.firebase.FirebaseEntity;

import java.util.*;

public class FirebaseRepository<E extends FirebaseEntity> implements CrudRepository<E, String> {

    private static final String TAG = FirebaseRepository.class.getSimpleName();

    private static FirebaseRepository instance;

    @SuppressLint("StaticFieldLeak")
    private static FirebaseFirestore firebaseDB = FirebaseFirestore.getInstance();

    @SuppressWarnings(value = "rawtypes")
    private final Map<Class, Object> repositoriesMap = new HashMap<>();

    private CollectionReference collectionReference;

    private FirebaseRepository() {
    }

    @SuppressWarnings(value = "unchecked")
    public synchronized static <E extends FirebaseEntity> FirebaseRepository<E> getInstance(Class<E> elementClass) throws InstantiationException, IllegalAccessException {
        if (!instance.repositoriesMap.containsKey(elementClass)) {
            FirebaseRepository<E> repository = FirebaseRepository.class.newInstance();
            instance.repositoriesMap.put(elementClass, repository);
            repository.collectionReference = firebaseDB.collection(elementClass.getSimpleName());
        }
        return (FirebaseRepository<E>) instance.repositoriesMap.get(elementClass);
    }

    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }


    /**
     * Operación guardar o actualizar en el almacen de datos.
     *
     * @param entity Es el objeto que sera persistido
     * @return Es el objeto persistido con cierto cambio.
     */
    @Override
    public <S extends E> S save(S entity) {
        if (entity == null) {
            throw new NullPointerException("Entidad nula");
        } else if (entity.getDocumentId() != null) {
            /*Operacioón actualizar*/
            collectionReference.document(entity.getDocumentId()).set(entity);
            return entity;
        } else {
            /*Operación crear*/
            final String[] identifierFirestore = new String[1];
            collectionReference.add(entity).addOnSuccessListener(documentReference -> identifierFirestore[0] = documentReference.getId());
            entity.setDocumentId(identifierFirestore[0]);
            return entity;
        }
    }

    /**
     * Operación que guarda todos los elementos dentro del almacen de datos.
     *
     * @param entities Son todos los objetos a ser guardados.
     * @return Son los objetos que fueron persistidos con ciertos cambios.
     */
    @Override
    public <S extends E> Iterable<S> saveAll(Iterable<S> entities) {
        for (S entity : entities) {
            save(entity);
        }
        return entities;
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
    public Optional<E> findById(String identifier) {
        final E[] entity = (E[]) new FirebaseEntity[]{(E) new Object()};
        collectionReference.document(identifier).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                entity[0] = (E) documentSnapshot.toObject(entity[0].getClass());
            }
        });
        return Optional.of(entity[0]);
    }

    /**
     * Operación optener todos los elementos de el almacen de datos.
     *
     * @return RSon todas las instancias que se encuentran en el almacen de datos.
     */
    @Override
    public Iterable<E> findAll() {
        final E[] entityTest = (E[]) new FirebaseEntity[]{(E) new Object()};
        List<E> allEntities = new ArrayList<E>();
        collectionReference.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                if (documentSnapshot.exists()) {
                    E entity = (E) documentSnapshot.toObject(entityTest[0].getClass());
                    allEntities.add(entity);
                }
            }
        }).addOnFailureListener(e -> Log.e(TAG, "onFailure: ", e.getCause()));
        return allEntities;
    }

    /**
     * Es la operación eliminar de el almacen de datos
     *
     * @param entity Es la entidad que se borrara de el almacen de datos.
     */
    @Override
    public void delete(E entity) {
        collectionReference.document(entity.getDocumentId()).delete().addOnFailureListener(e -> Log.e(TAG, "onFailure: ", e.getCause()));
    }

    /**
     * Es la operacion borrar varios elementos de el almacen de datos.
     *
     * @param entities
     */
    @Override
    public void deleteAll(Iterable<? extends E> entities) {
        for (E entity : entities) {
            delete(entity);
        }
    }

    /**
     * Operación obtener el total de elemetos del alamcen de datos.
     *
     * @return Es el numero total de entidades disponibles.
     */
    @Override
    public long count() {
        final Integer[] counter = new Integer[1];
        collectionReference.get().addOnSuccessListener(queryDocumentSnapshots -> {
            counter[0] = queryDocumentSnapshots.size();
        }).addOnFailureListener(e -> Log.e(TAG, "onFailure: ", e.getCause()));
        return counter[0];
    }
}
