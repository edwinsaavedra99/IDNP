package com.myappdeport.repository.firebase;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.myappdeport.model.entity.database.EPosition;
import com.myappdeport.model.entity.database.ERoute;
import com.myappdeport.repository.IRouteRepository;

import java.util.Objects;
import java.util.Optional;

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
            Objects.requireNonNull(optionalERoute).ifPresent(eRoute -> eRoute.setPositions(positionRepository.findByIdERoute(eRoute.getDocumentId()).getResult()));
            return Tasks.forResult(optionalERoute);
        });
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Task<ERoute> saveWithPositions(ERoute eRoute) {
        return save(eRoute).onSuccessTask(eRoute1 -> {
            for (EPosition position : Objects.requireNonNull(eRoute1).getPositions()) {
                position.setERouteDocumentId(eRoute1.getDocumentId());
            }
            return this.positionRepository.saveAll(eRoute1.getPositions()).onSuccessTask(ePositions -> Tasks.call(() -> {
                eRoute1.setPositions(ePositions);
                return eRoute1;
            }));
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
