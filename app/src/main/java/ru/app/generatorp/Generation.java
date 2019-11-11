package ru.app.generatorp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class Generation extends Service {



    private String[] firstName;
    private String[] lastName;
    private String[] work;
    private String[] raions;

    private  int lName;
    private  int w;
    private  int r;

    private String gorodId;

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

        lName = lastName.length;
        w = work.length;
        r = raions.length;



    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //Intent intent = Intent.makeMainActivity(); getIntent();
       // gorodId = intent.getStringExtra("userId");
        //gorodId = intent.getStringExtra("gorodId");


        //if (intent !=null && intent.getExtras()!=null){
            //gorodId = intent.getStringExtra("gorodId");
        gorodId = intent.getStringExtra("gorodId");
          // }


        Toast.makeText(this, "Служба запущена"+gorodId, Toast.LENGTH_SHORT).show();

            int minAge = 22;
            int maxAge = 35;


        for(int i=0;i<firstName.length;i++) {

            int rand1 = (int)(Math.random() * lName);
            int rand2 = (int)(Math.random() * w);
            int rand3 = (int)(Math.random() * r);
            int rand4 = (int)(rnd(minAge,maxAge));

            String strName = firstName[i];
            String lastChar = strName.substring(strName.length()- 1);
            if( lastChar.equals("а")||lastChar.equals("я") ){
               // System.out.println("Имя------------------------------- "+firstName[i]);
                System.out.println("Имя "+firstName[i]+" Фамилия "+ lastName[rand1]+"а"+" Возраст "+rand4+" должность "+ work[rand2]+" район "+raions[rand3]);
            }else
                System.out.println("Имя "+firstName[i]+" Фамилия "+ lastName[rand1]+" Возраст "+rand4+" должность "+ work[rand2]+" район "+raions[rand3]);
        }
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

}
