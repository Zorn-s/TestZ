package ru.app.generatorp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
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


    public static final String RECEIVER_INTENT = "RECEIVER_INTENT";
    public static final String RECEIVER_MESSAGE = "RECEIVER_MESSAGE";
    BroadcastReceiver  mBroadcastReceiver;

   private AppDataBase db;
   // private static final String TAG = "MainActivity";
   public RecyclerView recyclerView;
   public RecyclerView.Adapter recycleradapter;

    ArrayAdapter adapter;
    Spinner spinner;

    ProgressBar progressBar;
    TextView txt;
    Handler handler;
    int ind=0;

    String gorodId="Москва";

    String Period="10";
    String AgeMin="22";
    String AgeMax="35";

    List<User> users;


    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((mBroadcastReceiver),
                new IntentFilter(RECEIVER_INTENT)
        );
    }
    @Override
    protected void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//---------------------------------------------------------
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String message = intent.getStringExtra(RECEIVER_MESSAGE);
                // call any method you want here


                if (message.equals("update")){
                    System.out.println(message);

                    progressBar.setVisibility(View.VISIBLE);
                    txt.setVisibility(View.VISIBLE);
//------------------------------------------
                    handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            progressBar.setProgress(ind);
                            ind++;
                            txt.setText(String.valueOf(ind) + " %");

                            handler.postDelayed(this, 1000);

//------------------------------останавливаем поток
                            if (ind >= (Integer.parseInt(Period))) {
                                handler.removeCallbacksAndMessages(null);

                                txt.setText(String.valueOf(ind) + " %");
                                ind = 0;
                                progressBar.setVisibility(View.INVISIBLE);
                                txt.setVisibility(View.INVISIBLE);

                                upd(gorodId);

                            }
//------------------------------

                        }
                    });
//------------------------------------------

                }

            }
        };
//----------------------------------------------------------


            Intent intent = getIntent();
       if (intent !=null && intent.getExtras()!=null) {

            gorodId = intent.getStringExtra("gorodId");
            Period = intent.getStringExtra("Period");
            AgeMin = intent.getStringExtra("AgeMin");
            AgeMax = intent.getStringExtra("AgeMax");
        }


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
       db = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "production")
                .allowMainThreadQueries()
                .build();

        users = db.userDao().getAllUsers(gorodId);
        Period = db.settingsDao().getPeriod();

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


                //----------------запускаем сервис--------------------

                Intent intent = new Intent(getBaseContext(),Generation.class);
                intent.putExtra("gorodId",gorodId);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getBaseContext().startService(intent);


                //----------------------------------------------------


            }
        });


        adapter = (ArrayAdapter) spinner.getAdapter();

        int position = adapter.getPosition(gorodId);
        spinner.setSelection(position);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.goroda);

                gorodId = choose[selectedItemPosition];


                upd(gorodId);


            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        upd(gorodId);


    }

    @Override
    protected void onResume() {
        super.onResume();
        //upd(gorodId);
    }



    public void upd(String gorodId){
        try {
            db = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "production")
                    .allowMainThreadQueries()
                    .build();

            users = db.userDao().getAllUsers(gorodId);

            recycleradapter = new UserAdapter(users, getBaseContext());
            recyclerView.setAdapter(recycleradapter);
            db.close();
        }catch (Exception e){
            e.printStackTrace();
        }


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



        switch (item.getItemId()) {

            case R.id.action_settings:

                //Toast.makeText(MainActivity.this, "clicking the settings!", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(MainActivity.this,Settings.class));
                Intent intent1 = new Intent(getBaseContext(),Settings.class);
                intent1.putExtra("gorodId",gorodId);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getBaseContext().startActivity(intent1);

                return true;

            case R.id.new_user:

                //Toast.makeText(MainActivity.this, "clicking the settings!", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(MainActivity.this,CreateUser.class));

                Intent intent = new Intent(getBaseContext(),CreateUser.class);
                intent.putExtra("gorodId",gorodId);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getBaseContext().startActivity(intent);


                return true;


            default:
                // return super.onOptionsItemSelected(item);

        }



        return super.onOptionsItemSelected(item);
    }



}
