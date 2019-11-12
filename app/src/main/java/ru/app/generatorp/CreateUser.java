package ru.app.generatorp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class CreateUser extends AppCompatActivity {

    EditText Sity;
    EditText firstName;
    EditText lastName;
    EditText email;

    EditText age;
    EditText work;
    EditText gender;
    EditText area;
    EditText cars;

    Button button;

    ImageView foto;
    Bitmap bitmap;
    String fotoBitmap;
    int id;
    String gorodId;
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
    private static final String TAG = "CreateUser";
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user);

        Intent intent = getIntent();
        gorodId = intent.getStringExtra("gorodId");

        //----------------------коннект к базе  -----------------------------
        final AppDataBase db = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "production")
                .allowMainThreadQueries()
                .build();

        //List<User> users = db.userDao().getAllUsers(gorodId);
        //-------------------------------------------------------------------

        foto = findViewById(R.id.foto);

        Sity = findViewById(R.id.sity);
        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        email = findViewById(R.id.email);
        button = findViewById(R.id.button);

        age = findViewById(R.id.age);
        work = findViewById(R.id.work);
        gender = findViewById(R.id.gender);
        area = findViewById(R.id.area);
        cars = findViewById(R.id.cars);


        Sity.setText(gorodId);

        foto.setImageResource(R.drawable.mm);
        bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.mm);

        fotoBitmap = convertToBase64(bitmap);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO save to database 20/08/2019
                Log.d(TAG, "onClick: firstName:   "+firstName.getText().toString());
/**/
         //------------вставка в базу данных---------------------------------
                User user = new User(
                        id,
                        Sity.getText().toString(),
                        fotoBitmap,
                        firstName.getText().toString(),
                        lastName.getText().toString(),
                        email.getText().toString(),
                        age.getText().toString(),
                        work.getText().toString(),
                        gender.getText().toString(),
                        area.getText().toString(),
                        cars.getText().toString()

                );
                db.userDao().insertAll(user);
                db.close();
         //-------------------------------------------------------------------


               // startActivity(new Intent(CreateUser.this,MainActivity.class));
                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                intent.putExtra("gorodId",Sity.getText().toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getBaseContext().startActivity(intent);


            }
        });

    }
}
