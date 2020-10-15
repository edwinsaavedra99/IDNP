package com.practica04.ejercicio1_2lab04.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.practica04.ejercicio1_2lab04.Activity.FormStudentActivity;
import com.practica04.ejercicio1_2lab04.Activity.FormStudentActivity2;
import com.practica04.ejercicio1_2lab04.MainActivity;
import com.practica04.ejercicio1_2lab04.Model.Student;
import com.practica04.ejercicio1_2lab04.R;
import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> implements Filterable {
    //edit user
    public static final String EXTRA_MESSAGE = "com.practica04.ejercicio1_2lab04.MESSAGE";
    // edit user

    private static final int THIRD_ACTIVITY_REQUEST_CODE = 2;
    private List<Student> items;
    private List<Student> itemsFull;
    private Context context;

    public  static class StudentViewHolder extends RecyclerView.ViewHolder{

        TextView nameStudent;
        TextView emailStudent;
        TextView cuiStudent;
        ImageView optionItem;

        StudentViewHolder(View v){
            super(v);
            nameStudent = v.findViewById(R.id.name_user_item);
            emailStudent =  v.findViewById(R.id.email_user_item);
            cuiStudent = v.findViewById(R.id.cod_user_item);
            optionItem =  v.findViewById(R.id.optionItem);
        }
    }
    public StudentAdapter(List<Student> items, Context context){
        this.context = context;
        this.items = items;
        itemsFull = new ArrayList<>(items);
    }

    public void addElement(Student student){
        items.add(student);
        notifyDataSetChanged();
    }
    public void editElement(Student student, int position){
        items.get(position).setName(student.getName()+" "+student.getLastName() );
        items.get(position).setEmail(student.getEmail());
        items.get(position).setCui(student.getCui());
        notifyDataSetChanged();
    }
    public void deleteElement(int position){
        items.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        return items.size();
    }
    @NonNull
    @Override
    public StudentAdapter.StudentViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list_student,viewGroup,false);
        return new StudentAdapter.StudentViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final StudentAdapter.StudentViewHolder fileViewHolder, final int i){
        fileViewHolder.nameStudent.setText(items.get(i).getName() + " " +items.get(i).getLastName());
        fileViewHolder.emailStudent.setText("Email : "+items.get(i).getEmail());
        fileViewHolder.cuiStudent.setText("CUI : "+items.get(i).getCui());
        fileViewHolder.optionItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(),v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_item_student,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("Edit")){
                            Intent intent = new Intent (context, FormStudentActivity2.class);
                            intent.putExtra("data",items.get(i));
                            intent.putExtra("position",i);
                            ((Activity) context).startActivityForResult(intent, THIRD_ACTIVITY_REQUEST_CODE);

                        }else if(item.getTitle().equals("Delete")){
                            showAlertDialogDelete(i);
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }
    @Override
    public Filter getFilter(){
        return itemsFilter;
    }
    private Filter itemsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Student> filterPatient = new ArrayList<>();
            if(constraint == null || constraint.length() == 0 || constraint.toString().isEmpty()){
                filterPatient.addAll(itemsFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for ( Student student : itemsFull){
                    String fullName = student.getName() + " " + student.getLastName();
                    if(fullName.toLowerCase().contains(filterPattern) ||
                            student.getEmail().toLowerCase().contains(filterPattern) ||
                            String.valueOf(student.getCui()).contains(filterPattern)){
                        filterPatient.add(student);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterPatient;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            items.clear();
            items.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    private void showAlertDialogDelete(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("DELETE STUDENT");
        builder.setMessage("Are you sure to delete this student? All your information will be removed" );
        builder.setCancelable(false);
        builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteElement(position);
                Toast.makeText(context,"Delete Successfully",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog= builder.create();
        dialog.show();
    }

}
