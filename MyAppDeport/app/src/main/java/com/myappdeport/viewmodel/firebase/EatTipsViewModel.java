package com.myappdeport.viewmodel.firebase;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.tasks.Task;
import com.myappdeport.model.entity.database.ENutritionalAdvice;
import com.myappdeport.repository.INutritionalAdviceRepository;
import com.myappdeport.repository.firebase.NutritionalAdviceFireStoreRepository;
import java.util.List;
import java.util.Objects;

import static com.myappdeport.utils.HelperClass.logErrorMessage;

public class EatTipsViewModel extends AndroidViewModel {
    private Task<List<ENutritionalAdvice>> mAllNutritional;
    private List<ENutritionalAdvice> list;
    public LiveData<List<ENutritionalAdvice>> listLiveData;

    public EatTipsViewModel(Application application){
        super(application);
        INutritionalAdviceRepository mRepositoryNutritional = NutritionalAdviceFireStoreRepository.getInstance();
        mAllNutritional = mRepositoryNutritional.findAll();
    }
    public void findAll(){
        listLiveData = getAll();
    }

    public MutableLiveData<List<ENutritionalAdvice>> getAll(){
        MutableLiveData<List<ENutritionalAdvice>> mutableLiveData = new MutableLiveData<>();
        mAllNutritional.addOnCompleteListener(task -> {
           if (task.isSuccessful()){
               list = task.getResult();
               if(list!=null)
                   mutableLiveData.setValue(list);
           }else {
               logErrorMessage(Objects.requireNonNull(task.getException()).getMessage());
           }
        });
        return mutableLiveData;
    }
}
