package com.myappdeport.repository.firebase;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.myappdeport.model.entity.database.EPosition;
import com.myappdeport.model.entity.database.ERoute;
import com.myappdeport.repository.IRouteRepository;

import java.util.Optional;
import java.util.stream.Collectors;

import lombok.SneakyThrows;

public class RouteFireStoreRepository extends FireStoreRepository<ERoute> implements IRouteRepository<String> {

    private static RouteFireStoreRepository INSTANCE;
    private final PositionFireStoreRepository positionRepository;

    public synchronized static RouteFireStoreRepository getInstance() {
        if (INSTANCE == null)
            INSTANCE = new RouteFireStoreRepository();
        return INSTANCE;
    }

    private RouteFireStoreRepository() {
        super(ERoute.class);
        this.TAG = RouteFireStoreRepository.class.getSimpleName();
        this.positionRepository = PositionFireStoreRepository.getInstance();
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Task<Optional<ERoute>> findByIdWithPositions(String documentId) {
        return this.findById(documentId).onSuccessTask(optionalERoute -> {
            optionalERoute.ifPresent(eRoute -> eRoute.setPositions(positionRepository.findByIds(eRoute.getPositionDocumentIds()).getResult()));
            return Tasks.forResult(optionalERoute);
        });
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Task<ERoute> saveWithPositions(ERoute eRoute) {
        return this.positionRepository.saveAll(eRoute.getPositions()).onSuccessTask(ePositions -> {
            eRoute.setPositions(ePositions);
            eRoute.setPositionDocumentIds(ePositions.stream().map(EPosition::getDocumentId).collect(Collectors.toList()));
            return save(eRoute);
        });
    }


    @Override
    @SneakyThrows
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Task<Void> updateWithPositions(ERoute eRoute) {
        return this.positionRepository.updateAll(eRoute.getPositions()).onSuccessTask(ePositions -> update(eRoute));
    }


    @Override
    public Task<Void> deleteWithPositions(ERoute eRoute) {
        return this.positionRepository.delete(eRoute.getPositions()).onSuccessTask(voids -> delete(eRoute));
    }
}
