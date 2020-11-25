package com.myappdeport.repository.room;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.myappdeport.model.entity.database.EUser;
import com.myappdeport.repository.IAuthRepository;
import lombok.SneakyThrows;

import java.util.Objects;

public class AuthRoomRepository implements IAuthRepository<Void> {
    private final String TAG;
    private LiveData<EUser> eUserLiveData;
    private static AuthRoomRepository INSTANCE;
    private final UserRoomRepository userRoomRepository;

    public static synchronized AuthRoomRepository getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new AuthRoomRepository(context);
        return INSTANCE;
    }

    private AuthRoomRepository(Context context) {
        this.TAG = AuthRoomRepository.class.getSimpleName();
        this.userRoomRepository = UserRoomRepository.getInstance(context);
        EUser eUser = new EUser("Anonymous", "no_email@example.com", null, null, true);
        this.userRoomRepository.getByEmail(eUser.getEmail()).addOnCompleteListener(task -> {
            if (task.getResult() != null) {
                this.eUserLiveData = new MutableLiveData<>(task.getResult());
            } else {
                this.userRoomRepository.save(eUser).addOnCompleteListener(task1 -> {
                    if (task.getResult() != null) {
                        this.eUserLiveData = new MutableLiveData<>(task1.getResult());
                    } else {
                        Log.e(TAG, "Unexpected error on save User.");
                    }
                });
            }
        });
    }

    /**
     * Iniciar sesión.
     *
     * @param unused Credenciales para ingreso de sesión.
     * @return
     */
    @Override
    public Task<EUser> signIn(Void unused) {
        return Tasks.forResult(this.eUserLiveData.getValue());
    }

    /**
     * Registrar un usuario
     *
     * @param eUser Es el usuario que se registrara.
     * @return
     */
    @SneakyThrows
    @Override
    public Task<EUser> register(EUser eUser) {
        throw new NoSuchMethodException("This Method not yet implemented.");
    }

    /**
     * Es el usuario que inicio sesion ahora.
     *
     * @return
     */
    @Override
    public Task<EUser> getCurrentUser() {
        return Tasks.call(() -> {
            if (this.eUserLiveData.getValue().getIsAuthenticated()) {
                return this.eUserLiveData.getValue();
            }
            return null;
        });
    }

    /**
     * Es para cerrar sesión del ususario.
     *
     * @return
     */
    @Override
    public Task<Void> singOut() {
        return Tasks.call(() -> {
            Objects.requireNonNull(this.eUserLiveData.getValue()).setIsAuthenticated(false);
            return null;
        });
    }
}
