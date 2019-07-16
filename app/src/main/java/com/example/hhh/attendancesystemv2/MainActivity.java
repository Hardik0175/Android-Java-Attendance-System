package com.example.hhh.attendancesystemv2;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import com.example.hhh.attendancesystemv2.RequestPermissionHandler;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnKeyListener {
    android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
    EditText rfidinout;
    private RequestPermissionHandler mRequestPermissionHandler;

  //  String mylink = "http://studentshield.in/attendance/updateattendance.php?classid=";
   // String Apimsg = "https://enterprise.smsgupshup.com/GatewayAPI/rest?method=SendMessage&send_to=";
    //String Apimsg2 = "&msg_type=TEXT&userid=2000137573&auth_scheme=plain&password=kapila8143&v=1.1&format=text";

    String combineApi="http://studentshield.in/attendance/apiandupdate.php?classid=";

    String condtojump;
    String rfid;
    String frag;
    int frno;
    ArrayList<String> routesort;
    String routevalue[];
    SharedPreferences sp;

    String nameextra;
    String studentidextra;
    String thumbnailextra;
    String thumbnail2extra;
    String classextra;
    String statusextra;
    String timerextra;

    MyDBHandler dbHandlerRFID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mRequestPermissionHandler = new RequestPermissionHandler();

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();

//
//        intentmain.putExtra("Nameextra",splitattd[0]);
//        intentmain.putExtra("StudentIDextra",dbHandlerList.usridtosid(splitattd[3]));
//        intentmain.putExtra("Thumbnailextra",R.drawable.themartian);
//        intentmain.putExtra("Thumbnail2extra","DEFAULT");
//        intentmain.putExtra("classextra","5");
//        intentmain.putExtra("statusextra",dbHandlerList.getstatus(rfid));
//        intentmain.putExtra("timerextra","Yes");
//
        Bundle bbundle = getIntent().getExtras();
        //Intent intent = getIntent();

        if (bbundle != null) {
        nameextra = bbundle.getString("Nameextra");
        studentidextra = bbundle.getString("StudentIDextra");
        thumbnailextra = bbundle.getString("Thumbnailextra");
        thumbnail2extra = bbundle.getString("Thumbnail2extra");
        classextra = bbundle.getString("classextra");
        statusextra = bbundle.getString("statusextra");
        timerextra = bbundle.getString("timerextra");
        condtojump = bbundle.getString("jump");
         //   Toast.makeText(getApplicationContext(),nameextra + " " + studentidextra + " "+ condtojump,Toast.LENGTH_SHORT).show();
            //Intent intent=getIntent();
            //String frag=intent.getExtras().getString("frgvalue");
        }
        handleButtonClicked();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHandlerRFID = new MyDBHandler(getApplicationContext(), null, null, 7);
        dbHandlerRFID.addfrag();

        rfidinout = (EditText) findViewById(R.id.RFIDvalue);
        rfidinout.setOnKeyListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        fragmentManager
                .beginTransaction().replace(R.id.content, new Id()).commit();

        sp = getSharedPreferences("state", MODE_PRIVATE);
        if (sp.getBoolean("class", false)) {
           // Toast.makeText(getApplicationContext(),"Current state:class",Toast.LENGTH_SHORT).show();
            fragmentManager.beginTransaction().replace(R.id.content, new Studentlistclass()).commit();
           // gotostate("class");
        }
        else if (sp.getBoolean("routes", false)) {
           // gotostate("routes");
            //Toast.makeText(getApplicationContext(),"Current state:routes",Toast.LENGTH_SHORT).show();
            fragmentManager.beginTransaction().replace(R.id.content, new Studentlistroutes()).commit();
        }
        else if (sp.getBoolean("id", false)) {
            // gotostate("routes");
         //   Toast.makeText(getApplicationContext(),"ID",Toast.LENGTH_SHORT).show();
            fragmentManager.beginTransaction().replace(R.id.content, new Id()).commit();
        }
        else if (sp.getBoolean("gate", false)) {
            // gotostate("routes");
            //   Toast.makeText(getApplicationContext(),"ID",Toast.LENGTH_SHORT).show();
            fragmentManager.beginTransaction().replace(R.id.content, new Studentlistgate()).commit();
        }

    if(condtojump!=null) {
    //Toast.makeText(getApplicationContext(),"yess",Toast.LENGTH_SHORT).show();
    if (condtojump.equals("jump")) {
//            String nameextra;
//            String studentidextra;
//            String thumbnailextra;
//            String thumbnail2extra;
//            String classextra;
//            String statusextra;
//            String timerextra;

        Intent intentt = new Intent(MainActivity.this, popcardstudentred.class);
        intentt.putExtra("Name", nameextra);
        intentt.putExtra("StudentID", studentidextra);
        intentt.putExtra("Thumbnail", thumbnailextra);
        intentt.putExtra("Thumbnail2", thumbnail2extra);
        intentt.putExtra("class", classextra);
        intentt.putExtra("status", statusextra);
        intentt.putExtra("timer", timerextra);
        intentt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       // intentt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        //
        getApplicationContext().startActivity(intentt);
    } else {

    }
}

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Class) {

            sp.edit().putBoolean("class",true).apply();
            sp.edit().putBoolean("routes",false).apply();
            sp.edit().putBoolean("id",false).apply();
            sp.edit().putBoolean("gate",false).apply();


            fragmentManager.beginTransaction().replace(R.id.content, new Studentlistclass()).commit();


        } else if (id == R.id.Route) {
            sp.edit().putBoolean("routes",true).apply();
            sp.edit().putBoolean("class",false).apply();
            sp.edit().putBoolean("id",false).apply();
            sp.edit().putBoolean("gate",false).apply();
            fragmentManager.beginTransaction().replace(R.id.content, new Studentlistroutes()).commit();
        } else if (id == R.id.Gate) {
            sp.edit().putBoolean("gate",true).apply();
            sp.edit().putBoolean("class",false).apply();
            sp.edit().putBoolean("id",false).apply();
            sp.edit().putBoolean("routes",false).apply();
            fragmentManager.beginTransaction().replace(R.id.content, new Studentlistgate()).commit();

        } else if (id == R.id.id) {
            sp.edit().putBoolean("routes",false).apply();
            sp.edit().putBoolean("class",false).apply();
            sp.edit().putBoolean("id",true).apply();
            sp.edit().putBoolean("gate",false).apply();
            fragmentManager.beginTransaction().replace(R.id.content, new Id()).commit(); //
        }
        else if(id == R.id.addnew)
        {
            fragmentManager.beginTransaction().replace(R.id.content, new Addstudentform()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent event) {
        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                (i == KeyEvent.KEYCODE_ENTER && rfidinout.toString() != "")) {
            String attd;
            String rfid2="";
            rfid = rfidinout.getText().toString();
            if(rfid.equals("")){
                Toast.makeText(getApplicationContext(), "Please swipe tag on rfid sensor...", Toast.LENGTH_SHORT).show();
            }
            else {
            char ch=rfid.charAt(0);


            //  Toast.makeText(getApplicationContext(), String.valueOf(rfid.length()), Toast.LENGTH_SHORT).show();
           // Toast.makeText(getApplicationContext(), " p"+ rfid+ "p ", Toast.LENGTH_SHORT).show();
            if(ch=='\n') {

                for (i = 1; i <rfid.length(); i++) {
                    rfid2 +=String.valueOf(rfid.charAt(i));
                }
               // Toast.makeText(getApplicationContext(), rfid2+" "+rfid2.length(), Toast.LENGTH_SHORT).show();
                attd= dbHandlerRFID.idtostring(rfid2);
            }
            else
            {

              attd= dbHandlerRFID.idtostring(rfid);
            }
            rfidinout.getText().clear();

            if (attd.equals("###")) {
                Toast.makeText(getApplicationContext(), "Wrong Id or Id is not registered", Toast.LENGTH_SHORT).show();

            } else {
                int cond=0;
                String splitattd[] = (attd).split("#");

               // new GetMethodDemo().execute(mylink + splitattd[2] + "&sid=" + splitattd[3]);

                if (sp.getBoolean("class", false)) {
                    Toast.makeText(getApplicationContext(), splitattd[0] + " is present  in the class " + "SMS is send to " + splitattd[1] + " ", Toast.LENGTH_LONG).show();
                 //   new GetMethodDemo().execute(combineApi + splitattd[1] + "&msg=" + splitattd[0] + "%20is%20present%20in%20the%20class" + Apimsg2);
                  //  new GetMethodDemo().execute(Apimsg + splitattd[1] + "&msg=" + splitattd[0] + "%20is%20present%20in%20the%20class" + Apimsg2);
                    new GetMethodDemo().execute(combineApi+splitattd[2] + "&sid=" + splitattd[3] + "&sname=" + splitattd[0].trim()+ "&sphone=" + splitattd[1].trim());
                }
                else if (sp.getBoolean("routes", false)) {

                    String route=dbHandlerRFID.dynaimcroute(dbHandlerRFID.getactiveuniv());
                    routesort = new ArrayList<>();
                    ArrayList<String> mStringList= new ArrayList<String>();
                    routevalue=route.split("#");
                    for(int k=0;k<routevalue.length;k++)
                    {

                        routesort.add(routevalue[k]);

                    }
                    Collections.sort(routesort, new Comparator<String>() {
                        @Override
                        public int compare(String studentcardid, String t1) {
                            if(studentcardid.length()>t1.length())
                            {
                                return 1;
                            }
                            else if(studentcardid.length()<t1.length())
                            {
                                return -1;
                            }
                            return studentcardid.compareToIgnoreCase(t1);

                        }
                    });

                    int classnumber=Integer.valueOf(dbHandlerRFID.selectroutefrag())+1;
                  //  Toast.makeText(getApplicationContext(),rfid+" " +routesort.get(classnumber-1)+" "+dbHandlerRFID.getactiveuniv(),Toast.LENGTH_SHORT).show();
                   // Toast.makeText(getApplicationContext(),dbHandlerRFID.validatestudentroute(rfid,routesort.get(classnumber-1),dbHandlerRFID.getactiveuniv()),Toast.LENGTH_SHORT).show();
                    if(dbHandlerRFID.validatestudentroute(rfid,routesort.get(classnumber-1),dbHandlerRFID.getactiveuniv()).equals(""))
                    {
                        cond=1;
                        Toast.makeText(getApplicationContext(),splitattd[0] + " is not in the selected route",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                            Toast.makeText(getApplicationContext(), splitattd[0] + " is in the bus !" + "SMS is send to " + splitattd[1] + " ", Toast.LENGTH_LONG).show();
                            new GetMethodDemo().execute(combineApi+splitattd[2] + "&sid=" + splitattd[3] + "&sname=" + splitattd[0].trim()+ "&sphone=" + splitattd[1].trim());
                    }
                }
                else if (sp.getBoolean("id", false)) {
                    Toast.makeText(getApplicationContext(), splitattd[0] + " is in the school ! " + "SMS is send to " + splitattd[1] + " ", Toast.LENGTH_LONG).show();
                    new GetMethodDemo().execute(combineApi+splitattd[2] + "&sid=" + splitattd[3] + "&sname=" + splitattd[0].trim()+ "&sphone=" + splitattd[1].trim());

                }
                else if(sp.getBoolean("gate", false)){
                    Toast.makeText(getApplicationContext(), splitattd[0] + " is in the school ! " + "SMS is send to " + splitattd[1] + " ", Toast.LENGTH_LONG).show();
                    new GetMethodDemo().execute(combineApi+splitattd[2] + "&sid=" + splitattd[3] + "&sname=" + splitattd[0].trim()+ "&sphone=" + splitattd[1].trim());

                }
                if(cond==0)
                {
                dbHandlerRFID.updateAttendance(rfid);
                Intent intent = new Intent(MainActivity.this,popcardstudent.class);
                intent.putExtra("Name",splitattd[0]);
                intent.putExtra("StudentID",dbHandlerRFID.usridtosid(splitattd[3]));
                intent.putExtra("Thumbnail",R.drawable.themartian);
                intent.putExtra("Thumbnail2","DEFAULT");
                intent.putExtra("class","5");
                intent.putExtra("status","PRESENT");
                intent.putExtra("timer","Yes");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // Toast.makeText(getContext(),splitattd[0]+"-"+dbHandlerRFID.usridtosid(splitattd[3])+"-"+R.drawable.themartian+"-"+"DEFAULT"+"-"+"10"+"-"+"PRESENT",Toast.LENGTH_SHORT).show();
                getApplicationContext().startActivity(intent);
            }
            else
                {
                    Intent intent = new Intent(getApplicationContext(),popcardstudentred.class);
                    intent.putExtra("Name",splitattd[0]);
                    intent.putExtra("StudentID",dbHandlerRFID.usridtosid(splitattd[3]));
                    intent.putExtra("Thumbnail",R.drawable.themartian);
                    intent.putExtra("Thumbnail2","DEFAULT");
                    intent.putExtra("class","5");
                    intent.putExtra("status",dbHandlerRFID.getstatus(rfid));
                    intent.putExtra("timer","Yes");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  //  Toast.makeText(getApplicationContext(),dbHandlerRFID.getstatus(rfid),Toast.LENGTH_SHORT).show();
                    getApplicationContext().startActivity(intent);
                }
            }
        }}
        return false;
    }
    public class GetMethodDemo extends AsyncTask<String, Void, String> {
        String server_response;

        @Override
        protected String doInBackground(String... strings) {

            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    server_response = readStream(urlConnection.getInputStream());
                    Log.v("CatalogClient", server_response);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.e("Response", "" + server_response);


        }

    }
    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }
    public void gotostate(String state){

        switch (state){
            case "class":


                break;
            case "routes":


                break;
        }

    }
    public void setAlarm(long time,int value) {
        //getting the alarm manager

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //creating a new intent specifying the broadcast receiver
        Intent i = new Intent(getApplicationContext(), Updatestudentlist.class);

        //creating a pending intent using the intent
        if(value==1) {
           // Toast.makeText(getApplicationContext(), "morning", Toast.LENGTH_SHORT).show();
            i.putExtra("value","Morning session starts");
            PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 6, i, 0);

            //setting the repeating alarm that will be fired every day
            am.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pi);
        }
        else if (value==2) {
          //  Toast.makeText(getApplicationContext(), "evening", Toast.LENGTH_SHORT).show();
            i.putExtra("value","Morning session ends");
            PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 5, i, 0);

            //setting the repeating alarm that will be fired every day
            am.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pi);
        }
        else if (value==3) {
            //  Toast.makeText(getApplicationContext(), "evening", Toast.LENGTH_SHORT).show();
            i.putExtra("value","Afternoon session starts");
            PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 7, i, 0);

            //setting the repeating alarm that will be fired every day
            am.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pi);
        }

        else if (value==4) {

            i.putExtra("value","Afternoon session ends");
            PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 8, i, 0);

            //setting the repeating alarm that will be fired every day
            am.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pi);
        }
        else if (value==5) {
//              Toast.makeText(getApplicationContext(), "evening", Toast.LENGTH_SHORT).show();
            i.putExtra("value","Evening session starts");
            PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 10, i, 0);

            //setting the repeating alarm that will be fired every day
            am.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pi);
        }

        else if (value==6) {

            i.putExtra("value","Evening session ends");
            PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 9, i, 0);
            //setting the repeating alarm that will be fired every day
            am.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pi);
        }
    }

    private void handleButtonClicked(){
        mRequestPermissionHandler.requestPermission(this, new String[] {
                Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE
        }, 123, new RequestPermissionHandler.RequestPermissionListener() {
            @Override
            public void onSuccess() {
               // Toast.makeText(MainActivity.this, "request permission success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed() {
                Toast.makeText(MainActivity.this, "request permission failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mRequestPermissionHandler.onRequestPermissionsResult(requestCode, permissions,
                grantResults);
    }
}
