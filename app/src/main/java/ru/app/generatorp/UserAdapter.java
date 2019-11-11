package ru.app.generatorp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorSpace;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.List;


class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    List<User> users;
    Bitmap bitmap;

    Context context;

//----------------------------------------------------------------------------------------
//конвертируем Bitmap на Base64
public String convertToBase64(Bitmap bitmap) {
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.PNG,100,os);
    byte[] byteArray = os.toByteArray();
    return Base64.encodeToString(byteArray, 0);
}

    //и наоборот
    public Bitmap convertToBitmap(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap bitmapResult = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return bitmapResult;
    }
//-----------------------------------------------------------------------------------------

    public UserAdapter(List<User> users,Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

                holder.userId.setText(String.valueOf(users.get(position).getId()));

                bitmap = convertToBitmap(users.get(position).getFotoName());

                holder.fotoName.setImageBitmap(bitmap);
                holder.firstName.setText(users.get(position).getFirstName());
                holder.lastName.setText(users.get(position).getLastName());
                holder.email.setText(users.get(position).getEmail());

                holder.gender.setText(users.get(position).getGender());
                holder.age.setText(users.get(position).getAge());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("logs", "title-"+ holder.firstName.getText()+", date-"+holder.lastName.getText()+", id = "+holder.userId.getText());


                Intent i = new Intent(context,DataUser.class);
              //  i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               // i.putExtra("userId",String.valueOf(position));
                i.putExtra("userId",holder.userId.getText().toString());
               // i.putExtra("FirstName",holder.firstName.getText());
                context.startActivity(i);

            }
        });



    }

    @Override
    public int getItemCount() {
        return users.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView userId;
        public ImageView fotoName;
        public TextView firstName;
        public TextView lastName;
        public TextView email;

        public TextView gender;
        public TextView age;

        public ViewHolder(View itemView){
            super(itemView);

            userId = itemView.findViewById(R.id.user_Id);
            fotoName = itemView.findViewById(R.id.foto);
            firstName = itemView.findViewById(R.id.first_name);
            lastName = itemView.findViewById(R.id.last_name);
            email = itemView.findViewById(R.id.email);

            gender = itemView.findViewById(R.id.gender);
            age = itemView.findViewById(R.id.age);

        }
    }
}
