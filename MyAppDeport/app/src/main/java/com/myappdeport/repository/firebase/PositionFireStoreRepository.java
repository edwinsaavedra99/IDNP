package com.myappdeport.repository.firebase;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.myappdeport.model.entity.database.EPosition;
import com.myappdeport.model.entity.database.ERoute;
import com.myappdeport.model.entity.funcional.Position;
import com.myappdeport.repository.IPositionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PositionFireStoreRepository extends FireStoreRepository<EPosition> implements IPositionRepository<String> {

    private static PositionFireStoreRepository INSTANCE;

    public synchronized static PositionFireStoreRepository getInstance() {
        if (INSTANCE == null)
            INSTANCE = new PositionFireStoreRepository();
        return INSTANCE;
    }

    private PositionFireStoreRepository() {
        super(EPosition.class);
        this.TAG = PositionFireStoreRepository.class.getSimpleName();
    }


    /**
     * Obtener posiciones por sus ids.
     *
     * @param id_s Son las id con las cuales extraeremos las posiciones.
     * @return La tarea de obtención de posiciones.
     */
    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Task<List<EPosition>> findByIds(List<String> id_s) {
        return Tasks.call(() -> {
            List<EPosition> positions = new ArrayList<>();
            for (String documentId : id_s) {
                findById(documentId).getResult().ifPresent(positions::add);
            }
            return positions;
        });
    }
}
