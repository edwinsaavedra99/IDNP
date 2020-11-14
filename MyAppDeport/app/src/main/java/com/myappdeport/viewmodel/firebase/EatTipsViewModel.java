package com.myappdeport.viewmodel.firebase;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.Task;
import com.myappdeport.model.entity.database.ENutritionalAdvice;
import com.myappdeport.repository.INutritionalAdviceRepository;
import com.myappdeport.repository.firebase.NutritionalAdviceFireStoreRepository;

import java.util.List;

public class EatTipsViewModel extends AndroidViewModel {
    public INutritionalAdviceRepository nutritionalAdviceRepository;
    public MutableLiveData<List<ENutritionalAdvice>> nutritionalAdviceLiveData;
    public List<ENutritionalAdvice> list;
    public EatTipsViewModel(Application application){
        super(application);
        //nutritionalAdviceRepository = (INutritionalAdviceRepository) new NutritionalAdviceFireStoreRepository();
        /*nutritionalAdviceRepository = */
        nutritionalAdviceRepository = NutritionalAdviceFireStoreRepository.getInstance();
        nutritionalAdviceRepository.findAll();
        //nutritionalAdviceRepository.
        //list = nutritionalAdviceRepository.findAll().getResult();



        /*).addOnSuccessListener(data -> {
            Log.e(TAG_EAT, data.toString());
            System.out.println("FirebaseEatTag : SUCCEESS");
            list = data;
        }).addOnFailureListener(e -> {
            Log.e(TAG_EAT, e.getMessage(), e.getCause());

            System.out.println("FirebaseEatTag : FAIL");
        });*/
    }

    public Task<List<ENutritionalAdvice>> getAllEat(){

        //list.add( new ENutritionalAdvice("Ensalada de Palta","Esta es un rica ensalada de palta por ahora","asdasd","dsad"));
        //foodList.add( new Food("Ensalada de Papa","Esta es un rica ensalada de adsasdasdasdasd por ahora","asdasd"));
        //foodList.add( new Food("Ensalada de Mais","Esta es un rica ensalada de paltaasdasdasdasd por ahora","asdasd"));
        //foodList.add( new Food("Ensalada de Ajo","Esta es un rica ensalada de palta poasdasdasdasdasdr ahora","asdasd"));
        /*nutritionalAdviceRepository.findAll().addOnSuccessListener(new OnSuccessListener<List<ENutritionalAdvice>>() {
            @Override
            public void onSuccess(List<ENutritionalAdvice> eNutritionalAdvices) {
                list = eNutritionalAdvices;
            }

        });*/
        return nutritionalAdviceRepository.findAll();

    }

}
