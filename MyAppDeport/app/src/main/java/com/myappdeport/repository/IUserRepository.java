package com.myappdeport.repository;

import com.google.android.gms.tasks.Task;
import com.myappdeport.model.entity.database.EUser;

public interface IUserRepository {
    Task<EUser> getByEmail(String email);

    Task<EUser> getByEmailWithActivity(String email);
}
