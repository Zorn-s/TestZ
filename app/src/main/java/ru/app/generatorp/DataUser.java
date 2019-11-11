package ru.app.generatorp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;


public class DataUser extends AppCompatActivity {
    Bitmap bitmap;
    ImageView Foto;
    TextView FirstName;
    TextView LastName;
    TextView Email;
    TextView Age;
    TextView Work;
    TextView Gender;
    TextView Area;
    TextView Cars;

    String fotoBitmap;
    Button button;
    private int ind=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_user);

        Foto = findViewById(R.id.foto);
        FirstName = (TextView) findViewById(R.id.first_name);
        LastName = (TextView) findViewById(R.id.last_name);
        Email= (TextView) findViewById(R.id.email);
        Age = (TextView) findViewById(R.id.age);
        Work = (TextView) findViewById(R.id.work);
        Gender = (TextView) findViewById(R.id.gender);
        Area = (TextView) findViewById(R.id.area);
        Cars = (TextView) findViewById(R.id.cars);

        button = findViewById(R.id.button);



        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");



        LastName.setText(userId);
//----------------------коннект к базе  -----------------------------
       final AppDataBase db = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "production")
                .allowMainThreadQueries()
                .build();

        ind = (Integer.parseInt(userId))+1;
        //F = db.userDao().getFirsName(String.valueOf(ind));


        bitmap = convertToBitmap(db.userDao().getFoto(String.valueOf(ind)));
        Foto.setImageBitmap(bitmap);
        FirstName.setText(db.userDao().getFirsName(String.valueOf(ind)));
        LastName.setText(db.userDao().getLastName(String.valueOf(ind)));
        Email.setText(db.userDao().getEmail(String.valueOf(ind)));
        Age.setText(db.userDao().getAge(String.valueOf(ind)));
        Work.setText(db.userDao().getWork(String.valueOf(ind)));
        Gender.setText(db.userDao().getGender(String.valueOf(ind)));
        Area.setText(db.userDao().getArea(String.valueOf(ind)));
        Cars.setText(db.userDao().getCars(String.valueOf(ind)));
//-------------------------------------------------------------------


        fotoBitmap = convertToBase64(bitmap);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //------------вставка в базу данных---------------------------------
                User user = new User(
                        ind,
                        fotoBitmap,
                        FirstName.getText().toString(),
                        LastName.getText().toString(),
                        Email.getText().toString(),
                        Age.getText().toString(),
                        Work.getText().toString(),
                        Gender.getText().toString(),
                        Area.getText().toString(),
                        Cars.getText().toString()

                );
                db.userDao().updateAll(user);
                //-------------------------------------------------------------------
                startActivity(new Intent(DataUser.this,MainActivity.class));

            }
        });




    }







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






}
