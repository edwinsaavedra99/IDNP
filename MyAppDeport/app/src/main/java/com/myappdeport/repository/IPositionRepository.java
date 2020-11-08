package com.myappdeport.repository;

import com.google.android.gms.tasks.Task;
import com.myappdeport.model.entity.database.EPosition;

import java.util.List;

public interface IPositionRepository<I> extends IRepository<EPosition, I> {
    /**
     * Obtener posiciones por sus ids.
     *
     * @param id_s Son las id con las cuales extraeremos las posiciones.
     * @return La tarea de obtención de posiciones.
     */
    public Task<List<EPosition>> findByIds(List<I> id_s);
}
