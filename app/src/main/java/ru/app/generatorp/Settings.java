package ru.app.generatorp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;

public class Settings extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{

    private AppDataBase db;

    private String gorodId;

    private SeekBar seekBarP;
    private SeekBar seekBarMin;
    private SeekBar seekBarMax;
    private TextView txtP;
    private TextView txtMax;
    private TextView txtMin;
    int Id=1;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        button = findViewById(R.id.button);

        seekBarP = findViewById(R.id.seekBarP);
        seekBarP.setOnSeekBarChangeListener(this);

        seekBarP.setMax(30);
        seekBarP.setProgress(10);

        seekBarMin = findViewById(R.id.seekBarAgeMin);
        seekBarMin.setOnSeekBarChangeListener(this);

        seekBarMin.setMax(60);
        seekBarMin.setProgress(22);

        seekBarMax = findViewById(R.id.seekBarAgeMax);
        seekBarMax.setOnSeekBarChangeListener(this);

        seekBarMax.setMax(60);
        seekBarMax.setProgress(35);

        txtP = findViewById(R.id.txtP);


        txtMin = findViewById(R.id.txtMin);


        txtMax = findViewById(R.id.txtMax);

        Intent intent = getIntent();
        gorodId = intent.getStringExtra("gorodId");

        //----------------------коннект к базе  -----------------------------
        db = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "production")
                .allowMainThreadQueries()
                .build();

        //java.util.List<Setting> settingList = db.settingsDao().getAllSettings();

        //----------------------коннект к базе  -----------------------------

        txtP.setText(db.settingsDao().getPeriod());
        txtMin.setText(db.settingsDao().getAge_Min());
        txtMax.setText(db.settingsDao().getAge_Max());


        if(txtP.getText().equals("")) {
            Setting setting = new Setting(
                    Id,
                    "10",
                    "22",
                    "35"


            );
            db.settingsDao().insertAll(setting);

            txtP.setText("10");
            txtMin.setText("22");
            txtMax.setText("35");
        }


//-------------------------------------------------------------------

        //-------------------------------------------------------------------
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

/**/
                //------------вставка в базу данных если таблицы нет иначе апдейт существующей------------------------



            Setting setting = new Setting(
                    Id,
                    txtP.getText().toString(),
                    txtMin.getText().toString(),
                    txtMax.getText().toString()


            );
            db.settingsDao().updateAll(setting);

                //-----------------------------------------------------------------------------------------------------


                // startActivity(new Intent(CreateUser.this,MainActivity.class));

                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                intent.putExtra("gorodId",gorodId);
                intent.putExtra("Period",txtP.getText().toString());
                intent.putExtra("AgeMin",txtMin.getText().toString());
                intent.putExtra("AgeMax",txtMax.getText().toString());

                getBaseContext().startActivity(intent);


            }

        });




    }//oncreate

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    public void upSeek() {

        if(seekBarP.getProgress() < 3){seekBarP.setProgress(3);}

        if(seekBarMin.getProgress() < 18){seekBarMin.setProgress(18);}
        if(seekBarMin.getProgress() > 60){seekBarMin.setProgress(60);}

        if(seekBarMax.getProgress() < seekBarMin.getProgress()){
            seekBarMax.setProgress(seekBarMin.getProgress());

        }
        if(seekBarMax.getProgress() > 60){seekBarMax.setProgress(60);}

        txtMin.setText(String.valueOf(seekBarMin.getProgress()));
        txtMax.setText(String.valueOf(seekBarMax.getProgress()));

        txtP.setText(String.valueOf(seekBarP.getProgress()));

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if (b) { upSeek(); }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private class List<T> {
    }
}
