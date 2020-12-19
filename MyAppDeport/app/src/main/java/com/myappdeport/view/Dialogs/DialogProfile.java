package com.myappdeport.view.Dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.myappdeport.R;
import com.myappdeport.model.entity.database.EUser;
import com.myappdeport.model.entity.kill.EUserEDWIN;
import com.myappdeport.utils.ParseMetrics;
import com.myappdeport.view.activitys.Register;
import com.myappdeport.viewmodel.AuthViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class DialogProfile {
    //private EUser eUser;
    private EditText edit_nombre;
    private EditText edit_email;
    private TextView auxiliar;
    private EditText edit_fecha_nacimiento;
    private EditText edit_estatura;
    private EditText edit_edad;
    private EditText edit_peso,editTextTextPersonName2,editTextTextPersonName6,editTextTextPersonName7,editTextTextPersonName8;
    private Button btn_aceptar;
    private StorageReference storageReference;
    private ImageButton imageButton_edit_profile_image;
    private Button btn_cancelar;
    private Uri uri;
    private static final int GALLERY_INTENT=1;
    private int flag;
    private String currentPhotoPath;
    private boolean flag2 = false;
    private Activity activity;
    private Dialog dialog;
    private ProgressBar progress_barr;
    private EditText dateTextView;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    public DialogProfile(Context context, EUserEDWIN eUser, AuthViewModel authViewModel, Activity activity){
        //this.eUser = eUser;
        dialog = new Dialog(context);
        this.activity = activity;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //dialog.setCancelable(false);
        //dialog.getWindow().setBackgroundDrawable( new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_profile);
        editTextTextPersonName2 = dialog.findViewById(R.id.editTextTextPersonName2);
        editTextTextPersonName6 = dialog.findViewById(R.id.editTextTextPersonName6);
        editTextTextPersonName8 = dialog.findViewById(R.id.editTextTextPersonName8);
        dateTextView = dialog.findViewById(R.id.editTextTextPersonName9);
        auxiliar = dialog.findViewById(R.id.auxiliar);

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                //editText = 2;
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        context, R.style.DialogTheme, dateSetListener, year, month, day);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                //dialog.getDatePicker().mode
                dialog.show();

            }
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                dateTextView.setText(date);
                auxiliar.setText(ParseMetrics.edad(year,month,dayOfMonth)+"");
            }
        };

        storageReference = FirebaseStorage.getInstance().getReference();
        imageButton_edit_profile_image = dialog.findViewById(R.id.imageButton_edit_profile_image);
        imageButton_edit_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                activity.startActivityForResult(intent,GALLERY_INTENT);

            }
        });

        editTextTextPersonName2.setText(eUser.name);
        editTextTextPersonName6.setText(eUser.altura);
        editTextTextPersonName8.setText(eUser.peso);
        dateTextView.setText(eUser.fechaNacimiento);
        btn_aceptar = dialog.findViewById(R.id.button_edit_pofile_aceptar);
        btn_aceptar.setActivated(true);
        btn_cancelar = dialog.findViewById(R.id.button_edit_pofile_cancel);
        btn_cancelar.setActivated(true);
        progress_barr = dialog.findViewById(R.id.progress_barr);
        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_aceptar.setActivated(false);
                btn_cancelar.setActivated(false);
                EUserEDWIN datos = new EUserEDWIN();
                datos.name = String.valueOf(editTextTextPersonName2.getText());
                datos.altura = String.valueOf(editTextTextPersonName6.getText());
                datos.peso= String.valueOf(editTextTextPersonName8.getText());
                datos.fechaNacimiento = String.valueOf(dateTextView.getText());
                datos.edad = String.valueOf(auxiliar.getText());
                datos.photoUrl = eUser.photoUrl;
                if(flag2){
                    progress_barr.setVisibility(View.VISIBLE);
                    final  String randomkey = UUID.randomUUID().toString();
                    StorageReference filePath = storageReference.child("fotos").child(Objects.requireNonNull(uri.getLastPathSegment()));
                    filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> firebaseUri = taskSnapshot.getStorage().getDownloadUrl();
                            firebaseUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            progress_barr.setProgress(0);
                                        }
                                    },500);
                                    System.out.println("****************");
                                    //taskSnapshot
                                    datos.photoUrl = uri.toString();
                                    System.out.println(datos.photoUrl);
                                    System.out.println("****************");
                                    authViewModel.updateUser(datos);
                                    progress_barr.setVisibility(View.INVISIBLE);
                                    btn_aceptar.setActivated(true);
                                    btn_cancelar.setActivated(true);
                                    dialog.dismiss();
                                    Toast.makeText(context,"La foto se actualizara en un momento ..",Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progress_barr.setVisibility(View.INVISIBLE);
                            Toast.makeText(context,"Lo sentimos, error al cargar Imagen",Toast.LENGTH_SHORT).show();
                            btn_aceptar.setActivated(true);
                            btn_cancelar.setActivated(true);
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progressporcert = (100.00 *taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progress_barr.setProgress((int)progressporcert);
                            //pd.setMessage("Porcentage:"+(int)progressporcert+" %");
                            btn_aceptar.setActivated(false);
                            btn_cancelar.setActivated(false);
                        }
                    });
                }else {
                    dialog.dismiss();
                    btn_aceptar.setActivated(true);
                    btn_cancelar.setActivated(true);
                    authViewModel.updateUser(datos);
                }
            }
        });
        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    dialog.show();
    }

    public void updateImage( boolean flag){
        //imageButton_edit_profile_image.setImageBitmap(imageBitmap);
        this.flag2 = flag;
    }

    public void udireccionM(Uri udireccion){
        this.uri = udireccion;

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK && data!=null){
            Uri uri = data.getData();
            Glide.with(dialog.getContext())  //2
                    .load(uri) //url de descarga
                    .centerCrop() //propiedad de redimencion
                    .into(imageButton_edit_profile_image); //8
            udireccionM(uri);
            updateImage(true);
        }
    }



}
