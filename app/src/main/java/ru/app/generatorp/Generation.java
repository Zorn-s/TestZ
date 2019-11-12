package ru.app.generatorp;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Base64;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.room.Room;

import java.io.ByteArrayOutputStream;

public class Generation extends Service {





    int id;

    private String[] firstName;
    private String[] lastName;
    private String[] work;
    private String[] raions;
    private String[] email;

    private  int lName;
    private  int w;
    private  int r;
    private  int e;

    private String gorodId;

    private Bitmap bitmapM;
    private Bitmap bitmapF;
    private String fotoBitmap;


    public Generation() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.



        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();



        Toast.makeText(this, "Служба создана ",
                Toast.LENGTH_SHORT).show();

        firstName = getResources().getStringArray(R.array.first_name);
        lastName = getResources().getStringArray(R.array.last_name);
        work = getResources().getStringArray(R.array.work);
        raions = getResources().getStringArray(R.array.raions);
        email = getResources().getStringArray(R.array.email);

        lName = lastName.length;
        w = work.length;
        r = raions.length;
        e = email.length;


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        if (intent !=null && intent.getExtras()!=null){

        gorodId = intent.getStringExtra("gorodId");
           }


        Toast.makeText(this, "Служба запущена , генерируем для "+gorodId, Toast.LENGTH_SHORT).show();

         final  int minAge = 22;
         final  int maxAge = 35;

//-------------------------------генерация и вставка в базу-----------------------------------------
/*
        for(int i=0;i<firstName.length;i++) {

            int rand1 = (int)(Math.random() * lName);
            int rand2 = (int)(Math.random() * w);
            int rand3 = (int)(Math.random() * r);
            int rand4 = (int)(rnd(minAge,maxAge));

            int avto = (int)(Math.random() * 2);

            String strName = firstName[i];
            String lastChar = strName.substring(strName.length()- 1);
            if( lastChar.equals("а")||lastChar.equals("я") ){

                System.out.println("Имя "+firstName[i]+" Фамилия "+ lastName[rand1]+"а"+" Возраст "+rand4+" должность "+ work[rand2]+" г."+gorodId+" район "+raions[rand3]+" avto "+
                                ( (avto == 1) ? "Yes" : "No")
                        );
            }else
                System.out.println("Имя "+firstName[i]+" Фамилия "+ lastName[rand1]+" Возраст "+rand4+" должность "+ work[rand2]+" г."+gorodId+" район "+raions[rand3]+" avto "+avto);
        }



 */

        bitmapM = BitmapFactory.decodeResource(this.getResources(),R.drawable.mm);
        bitmapF = BitmapFactory.decodeResource(this.getResources(),R.drawable.ff);


        //----------------------коннект к базе  -----------------------------
        final AppDataBase db = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "production")
                .allowMainThreadQueries()
                .build();

        Thread t = new Thread(new Runnable() {
            public void run() {
                try {


        for(int i=0;i<firstName.length;i++) {

            int rand1 = (int) (Math.random() * lName);
            int rand2 = (int) (Math.random() * w);
            int rand3 = (int) (Math.random() * r);
            int rand4 = (int) (rnd(minAge, maxAge));
            int em = (int) (Math.random() * e);
            int avto = (int) (Math.random() * 2);

            String strName = firstName[i];
            String lastChar = strName.substring(strName.length() - 1);
            if (lastChar.equals("а") || lastChar.equals("я")) {

               // System.out.println("Имя " + firstName[i] + " Фамилия " + lastName[rand1] + "а" + " Возраст " + rand4 + " должность " + work[rand2] + " г." + gorodId + " район " + raions[rand3] + " avto " + ((avto == 1) ? "Yes" : "No"));

                fotoBitmap = convertToBase64(bitmapF);

                //------------вставка в базу данных---------------------------------
                User user = new User(
                        id,
                        gorodId,
                        fotoBitmap,
                        firstName[i],
                        (lastName[rand1] + "а"),
                        email[em],
                        String.valueOf(rand4),
                        work[rand2],
                        "Female",
                        raions[rand3],
                        ((avto == 1) ? "Yes" : "No")

                );
                db.userDao().insertAll(user);
                //-------------------------------------------------------------------


            } else {
                //System.out.println("Имя " + firstName[i] + " Фамилия " + lastName[rand1] + " Возраст " + rand4 + " должность " + work[rand2] + " г." + gorodId + " район " + raions[rand3] + " avto " + avto);
                fotoBitmap = convertToBase64(bitmapM);
                //------------вставка в базу данных---------------------------------
                User user = new User(
                        id,
                        gorodId,
                        fotoBitmap,
                        firstName[i],
                        (lastName[rand1]),
                        email[em],
                        String.valueOf(rand4),
                        work[rand2],
                        "Female",
                        raions[rand3],
                        ((avto == 1) ? "Yes" : "No")

                );
                db.userDao().insertAll(user);
                //-------------------------------------------------------------------
            }
        }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();

        db.close();

 //-------------------
        Intent intentS = new Intent(MainActivity.RECEIVER_INTENT);
        intentS.putExtra(MainActivity.RECEIVER_MESSAGE, "update");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intentS);
 //-------------------


//--------------------------------------------------------------------------------------------------





        return START_STICKY;
       // return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Toast.makeText(this, "Служба остановлена",
                Toast.LENGTH_SHORT).show();

    }

    public static int rnd(int min, int max)
    {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }


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
