package ru.app.generatorp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

   // private static final String TAG = "MainActivity";
   public RecyclerView recyclerView;
   public RecyclerView.Adapter recycleradapter;

    Spinner spinner;
    ProgressBar progressBar;
    TextView txt;
    Handler handler;
    int ind=0;

    String gorodId="Москва";
    List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = getIntent();
        gorodId = intent.getStringExtra("gorodId");



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

/*
        ArrayList<User>  users = new ArrayList<>();

        for (int i=0;i<10;i++){

        final User user = new User(
                     0,
                    "",
                    "0000",
                    "Daniel"+i,
                    "Malone",
                    "DanielMalone@gmail.com",
                    "23",
                    "IT",
                    "Famaly",
                    "Dzerjinskiy",
                    "Yes");

            users.add(user);
        }
*/


//----------------------коннект к базе  -----------------------------
       final AppDataBase db = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "production")
                .allowMainThreadQueries()
                .build();


        users = db.userDao().getAllUsers(gorodId);
//-------------------------------------------------------------------

        progressBar = findViewById(R.id.progress);
        txt = (TextView)findViewById(R.id.textView);

        spinner = findViewById(R.id.spinner);

        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recycleradapter = new UserAdapter(users, this);
        recyclerView.setAdapter(recycleradapter);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setImageBitmap(textAsBitmap("Gen", 30, Color.WHITE));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // Log.d(TAG, "onClick: presed!");

                //startActivity(new Intent(MainActivity.this,CreateUser.class));

                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();

            H(350);



            }
        });


       //String selected = spinner.getSelectedItem().toString();
        //Toast.makeText(getApplicationContext(), selected, Toast.LENGTH_SHORT).show();

        ArrayAdapter adapter = (ArrayAdapter) spinner.getAdapter();

        int position = adapter.getPosition(gorodId);
        spinner.setSelection(position);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.goroda);

                gorodId = choose[selectedItemPosition];



                users = db.userDao().getAllUsers(gorodId);
                recycleradapter = new UserAdapter(users, getBaseContext());
                recyclerView.setAdapter(recycleradapter);

                Toast toast = Toast.makeText(getApplicationContext(),
                        "Ваш выбор: " + choose[selectedItemPosition]+"="+selectedItemPosition, Toast.LENGTH_SHORT);
                toast.show();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }
//-----------------------------------------------------------
public static Bitmap textAsBitmap(String text, float textSize, int textColor) {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setTextSize(textSize);
    paint.setColor(textColor);
    paint.setTextAlign(Paint.Align.LEFT);
    float baseline = -paint.ascent(); // ascent() is negative
    int width = (int) (paint.measureText(text) + 0.0f); // round
    int height = (int) (baseline + paint.descent() + 0.0f);
    Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

    Canvas canvas = new Canvas(image);
    canvas.drawText(text, 0, baseline, paint);
    return image;
}
//-----------------------------------------------------------
private void H(final int delay) {

    progressBar.setVisibility(View.VISIBLE);
    txt.setVisibility(View.VISIBLE);


    if (handler != null) {
        handler.removeCallbacksAndMessages(null);
    }

    handler = new Handler();
    handler.post(new Runnable() {
        @Override
        public void run() {
            //------------------------------
            progressBar.setProgress(ind);
            ind+=3;
            txt.setText(String.valueOf(ind) + " %");

            //------------------------------

            handler.postDelayed(this, delay);

//------------------------------останавливаем поток
            if (ind >= 7) {
                handler.removeCallbacksAndMessages(null);

                txt.setText(String.valueOf(ind) + " %");
                ind = 0;
                progressBar.setVisibility(View.INVISIBLE);
                txt.setVisibility(View.INVISIBLE);

    //----------------запускаем сервис--------------------

                Intent intent = new Intent(getBaseContext(),Generation.class);
                intent.putExtra("gorodId","test");
                getBaseContext().startService(intent);


                // startService(new Intent(MainActivity.this, Generation.class));

    //----------------------------------------------------


            }
//------------------------------

        }
    });
}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, Generation.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
      //  int id = item.getItemId();

        //noinspection SimplifiableIfStatement
      //  if (id == R.id.action_settings) {
      //      return true;
      //  }


        switch (item.getItemId()) {
            case R.id.action_settings:

                //Toast.makeText(MainActivity.this, "clicking the settings!", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(MainActivity.this,CreateUser.class));

                Intent intent = new Intent(getBaseContext(),CreateUser.class);
                intent.putExtra("gorodId",gorodId);
                getBaseContext().startActivity(intent);


                return true;


            default:
                // return super.onOptionsItemSelected(item);

        }



        return super.onOptionsItemSelected(item);
    }



}
