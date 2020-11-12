package com.myappdeport.repository;

import com.google.android.gms.tasks.Task;
import com.myappdeport.model.entity.database.EPosition;

import java.util.List;

public interface IPositionRepository<I> extends IRepository<EPosition, I> {
    public Task<List<EPosition>> findByIdERoute(I id);
}
