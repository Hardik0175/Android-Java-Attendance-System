package com.example.hhh.attendancesystemv2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

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
import java.util.jar.Attributes;

/**
 * Created by hhh on 7/8/2018.
 */

public class popcardstudentred extends AppCompatActivity implements View.OnKeyListener{
    //  ImageView studentpic;
    // TextView studentstatus;
    int jump=0;
    String combineApi="http://studentshield.in/attendance/apiandupdate.php?classid=";
    String rfid;
    EditText rfidincard;

    ArrayList<String> routesort;

    String routevalue[];

    Switch switchfor;

    TextView studentname;
    TextView lstudentname;
    TextView nameplate;
    TextView classplate;
    TextView classinput;
    TextView ageplate;
    TextView ageinput;
    TextView cardplate;
    TextView cardinput;
    TextView phoneplate;
    TextView phoneinput;
    TextView picplate;
    TextView picinput;
    TextView dropplate;
    TextView dropinput;
    TextView statusplate;
    TextView absentinput;
    TextView presentinput;
    RadioButton present;
    RadioButton absent;
    boolean value;
    ImageView studentimage;
    MyDBHandler dbHandlerList;
     String[] mycard;
     String id;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        jump++;

        dbHandlerList = new MyDBHandler(getApplicationContext(), null, null, 7);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popcardstudentred);
        Intent intent = getIntent();

        rfidincard = (EditText) findViewById(R.id.rfidincard);
        rfidincard.setOnKeyListener(this);


        switchfor=(Switch) findViewById(R.id.switchstatus);

        name = intent.getExtras().getString("Name");
        String timer = intent.getExtras().getString("timer");
        // String thumbnail=intent.getExtras().getString("Thumbnail2");

        id = intent.getExtras().getString("StudentID");
        mycard = dbHandlerList.valuetodisplayincard(id).split("&");

        //this.setFinishOnTouchOutside(false);

        int pic = intent.getExtras().getInt("Thumbnail");



        DisplayMetrics screen = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(screen);

      //

        int Width = screen.widthPixels;
        int height = screen.heightPixels;
        getWindow().setLayout((int) (Width * .9), (int) (height * .28));
        lstudentname = (TextView) findViewById(R.id.lnamestudent);
        nameplate = (TextView) findViewById(R.id.Name);
//
//        //  studentstatus=(TextView)findViewById(R.id.statusofstudent);
//        absent = (RadioButton) findViewById(R.id.radioabsent);
//        present = (RadioButton) findViewById(R.id.radiopresnt);


        studentimage = (ImageView) findViewById(R.id.studentpicture);

        String mypic = dbHandlerList.sidtopic(id);
        // Toast.makeText(getApplicationContext(),mypic,Toast.LENGTH_SHORT).show();
        if (!mypic.equals("DEFAULT")) {
            Glide.with(getApplicationContext())
                    .load(mypic)
                    .into(studentimage);

//            new DownloadImageTask((ImageView) findViewById(R.id.studentpicture))
//                    .execute(mypic);

        } else if (mypic.equals("DEFAULT")) {
            studentimage.setImageResource(pic);
        }


//        absent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                present.setChecked(!value);
//                absent.setChecked(value);
//                if (value == true) {
//
//                    Toast.makeText(getApplicationContext(), "Absent", Toast.LENGTH_SHORT).show();
//                    dbHandlerList.updateAttendance2(id, mycard[2], "ABSENT");
//
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "Present", Toast.LENGTH_SHORT).show();
//                    dbHandlerList.updateAttendance2(id, mycard[2], "PRESENT");
//                }
//                value = !value;
//            }
//        });
//        present.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                present.setChecked(!value);
//                absent.setChecked(value);
//                if (value == false) {
//                    Toast.makeText(getApplicationContext(), "Present", Toast.LENGTH_SHORT).show();
//                    dbHandlerList.updateAttendance2(id, mycard[2], "PRESENT");
//                } else {
//                    Toast.makeText(getApplicationContext(), "Absent", Toast.LENGTH_SHORT).show();
//                    dbHandlerList.updateAttendance2(id, mycard[2], "ABSENT");
//                }
//                value = !value;
//            }
//        });


        classplate = (TextView) findViewById(R.id.classnumber);
        classinput = (TextView) findViewById(R.id.classinput);
        ageplate = (TextView) findViewById(R.id.agenumber);
        ageinput = (TextView) findViewById(R.id.ageinput);
        cardplate = (TextView) findViewById(R.id.cardnumber);
        cardinput = (TextView) findViewById(R.id.cardinput);
        phoneplate = (TextView) findViewById(R.id.phonenumber);
        phoneinput = (TextView) findViewById(R.id.phoneinput);
        picplate = (TextView) findViewById(R.id.picroutenumber);
        picinput = (TextView) findViewById(R.id.picrouteinput);
        dropplate = (TextView) findViewById(R.id.droproutenumber);
        dropinput = (TextView) findViewById(R.id.droprouteinput);
        statusplate = (TextView) findViewById(R.id.statusnumber);
        absentinput = (TextView) findViewById(R.id.absentnumber);
        presentinput = (TextView) findViewById(R.id.presentnumber);


        studentname = (TextView) findViewById(R.id.namestudent);
        studentimage = (ImageView) findViewById(R.id.studentpicture);

        Typeface face = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Roboto-Black.ttf");
        Typeface face2 = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Roboto-Regular.ttf");

        classinput.setText(mycard[2]);
        classinput.setTypeface(face2);
        ageinput.setText(mycard[3]);
        ageinput.setTypeface(face2);
        cardinput.setText(mycard[4]);
        cardinput.setTypeface(face2);
        phoneinput.setText(mycard[5]);
        phoneinput.setTypeface(face2);
        if(mycard.length>8)
        {
            picinput.setText(mycard[7]);
            dropinput.setText(mycard[8]);
        }
        else if(mycard.length==8)
        {
            picinput.setText(mycard[7]);
        }
        picinput.setTypeface(face2);

        dropinput.setTypeface(face2);
        studentname.setText(name);
        studentname.setTypeface(face2);
        lstudentname.setText(mycard[1]);
        lstudentname.setTypeface(face2);
        absentinput.setTypeface(face2);
        presentinput.setTypeface(face2);
        nameplate.setTypeface(face);
        ageplate.setTypeface(face);
        cardplate.setTypeface(face);
        phoneplate.setTypeface(face);
        dropplate.setTypeface(face);
        picplate.setTypeface(face);
        statusplate.setTypeface(face);
        classplate.setTypeface(face);


        String status = dbHandlerList.getstatus(cardinput.getText().toString());

       // Toast.makeText(getApplicationContext(),cardinput.getText().toString(),Toast.LENGTH_SHORT).show();

        if (status.equals("PRESENT")) {
            switchfor.setChecked(true);
            value = true;

        } else {
            switchfor.setChecked(false);
            value = false;

        }

//        if(timer.equals("Yes")) {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                    Intent startPageIndent = new Intent(popcardstudentred.this, MainActivity.class);
//                    startActivity(startPageIndent);
//                    finish();
//                }
//            }, 1000);
//        }
      //  Toast.makeText(getApplicationContext(), "onCreate "+ jump, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        jump++;
        super.onStart();
      //  Toast.makeText(getApplicationContext(), "onStart "+ jump, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onResume() {
        jump++;
        super.onResume();
        if(jump>3)
        {
            Intent main=new Intent(popcardstudentred.this,MainActivity.class);
            startActivity(main);
        }
       // Toast.makeText(getApplicationContext(), "onResume "+ jump, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        jump++;
        super.onPause();
        //Toast.makeText(getApplicationContext(), "onPause "+ jump, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        jump++;
        super.onStop();
      //  Toast.makeText(getApplicationContext(), "onStop "+jump, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String attd = dbHandlerList.idtostring(mycard[4]);
        if(switchfor.isChecked())
        {
           // Toast.makeText(getApplicationContext(), id+" " +mycard[2], Toast.LENGTH_SHORT).show();

            if (attd.equals("###")) {
                Toast.makeText(getApplicationContext(), "Wrong Id or Id is not registered", Toast.LENGTH_SHORT).show();

            } else {
                dbHandlerList.updateAttendance2(id, mycard[2], "PRESENT");
                String splitattd[] = (attd).split("#");
                Toast.makeText(getApplicationContext(), splitattd[0].trim() + " is present", Toast.LENGTH_SHORT).show();
                //new GetMethodDemo().execute(combineApi+splitattd[2] + "&sid=" + splitattd[3] + "&sname=" + name.trim()+ "&sphone=" + mycard[5].trim());
                new GetMethodDemo().execute(combineApi + splitattd[2] + "&sid=" + splitattd[3] + "&sname=" + splitattd[0].trim() + "&sphone=" + splitattd[1].trim());
            }
            //Toast.makeText(getApplicationContext(), "Present", Toast.LENGTH_SHORT).show();
        }
        else
        {
            dbHandlerList.updateAttendance2(id, mycard[2], "ABSENT");
            Toast.makeText(getApplicationContext(), "Absent", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onKey(View view, int i, KeyEvent event) {
        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                (i == KeyEvent.KEYCODE_ENTER && rfidincard.toString() != "")) {
            String attd;
            String rfid2="";
            rfid = rfidincard.getText().toString();
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
                    attd= dbHandlerList.idtostring(rfid2);
                }
                else
                {

                    attd= dbHandlerList.idtostring(rfid);
                }
                rfidincard.getText().clear();

                if (attd.equals("###")) {
                    Toast.makeText(getApplicationContext(), "Wrong Id or Id is not registered", Toast.LENGTH_SHORT).show();

                } else {

                    String route=dbHandlerList.dynaimcroute(dbHandlerList.getactiveuniv());

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

                    int cond=0;
                    String splitattd[] = (attd).split("#");

                    // new GetMethodDemo().execute(mylink + splitattd[2] + "&sid=" + splitattd[3]);
                    // String route=dbHandlerList.dynaimcroute(dbHandlerList.getactiveuniv());


                    int classnumber=Integer.valueOf(dbHandlerList.selectroutefrag())+1;
                    //  Toast.makeText(getApplicationContext(),rfid+" " +routesort.get(classnumber-1)+" "+dbHandlerRFID.getactiveuniv(),Toast.LENGTH_SHORT).show();
                    // Toast.makeText(getApplicationContext(),dbHandlerRFID.validatestudentroute(rfid,routesort.get(classnumber-1),dbHandlerRFID.getactiveuniv()),Toast.LENGTH_SHORT).show();
                    if(dbHandlerList.validatestudentroute(rfid,routesort.get(classnumber-1),dbHandlerList.getactiveuniv()).equals(""))
                    {
                        cond=1;
                        Toast.makeText(getApplicationContext(),splitattd[0] + " is not in the selected route",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), splitattd[0] + " is in the bus !" + "SMS is send to " + splitattd[1] + " ", Toast.LENGTH_LONG).show();
                        new GetMethodDemo().execute(combineApi+splitattd[2] + "&sid=" + splitattd[3] + "&sname=" + splitattd[0].trim()+ "&sphone=" + splitattd[1].trim());
                    }
                    if(cond==0)
                    {
                        if(switchfor.isChecked())
                        {

                            String attd2= dbHandlerList.idtostring(mycard[4]);
                            //Toast.makeText(getApplicationContext(), id+" " +mycard[2], Toast.LENGTH_SHORT).show();

                            if (attd2.equals("###")) {
                                Toast.makeText(getApplicationContext(), "Wrong Id or Id is not registered", Toast.LENGTH_SHORT).show();

                            } else {
                                String splitattd2[] = (attd2).split("#");
                                dbHandlerList.updateAttendance2(id, mycard[2], "PRESENT");
                                //String splitattd[] = (attd).split("#");
                                Toast.makeText(getApplicationContext(), name.trim() + " is present", Toast.LENGTH_SHORT).show();
                                //new GetMethodDemo().execute(combineApi+splitattd[2] + "&sid=" + splitattd[3] + "&sname=" + name.trim()+ "&sphone=" + mycard[5].trim());
                                new GetMethodDemo().execute(combineApi + splitattd2[2] + "&sid=" + splitattd2[3] + "&sname=" + name.trim() + "&sphone=" + splitattd2[1].trim());
                            }
                            //Toast.makeText(getApplicationContext(), "Present", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            dbHandlerList.updateAttendance2(id, mycard[2], "ABSENT");
                            Toast.makeText(getApplicationContext(), "Absent", Toast.LENGTH_SHORT).show();
                        }

                        dbHandlerList.updateAttendance(rfid);
                        Intent intent = new Intent(popcardstudentred.this,popcardstudent.class);
                        intent.putExtra("Name",splitattd[0]);
                        intent.putExtra("StudentID",dbHandlerList.usridtosid(splitattd[3]));
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
                        if(switchfor.isChecked())
                        {

                            String attd2= dbHandlerList.idtostring(mycard[4]);
                          //  Toast.makeText(getApplicationContext(), id+" " +mycard[2], Toast.LENGTH_SHORT).show();

                            if (attd2.equals("###")) {
                                Toast.makeText(getApplicationContext(), "Wrong Id or Id is not registered", Toast.LENGTH_SHORT).show();

                            } else {
                                String splitattd2[] = (attd2).split("#");
                                dbHandlerList.updateAttendance2(id, mycard[2], "PRESENT");
                                //String splitattd[] = (attd).split("#");
                                Toast.makeText(getApplicationContext(), name.trim() + " is present", Toast.LENGTH_SHORT).show();
                                //new GetMethodDemo().execute(combineApi+splitattd[2] + "&sid=" + splitattd[3] + "&sname=" + name.trim()+ "&sphone=" + mycard[5].trim());
                                new GetMethodDemo().execute(combineApi + splitattd2[2] + "&sid=" + splitattd2[3] + "&sname=" + name.trim() + "&sphone=" + splitattd2[1].trim());
                            }
                            //Toast.makeText(getApplicationContext(), "Present", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            dbHandlerList.updateAttendance2(id, mycard[2], "ABSENT");
                            Toast.makeText(getApplicationContext(), "Absent", Toast.LENGTH_SHORT).show();
                        }
                        Intent intentmain = new Intent(popcardstudentred.this,MainActivity.class);
                        intentmain.putExtra("Nameextra",splitattd[0]);
                        intentmain.putExtra("StudentIDextra",dbHandlerList.usridtosid(splitattd[3]));
                        intentmain.putExtra("Thumbnailextra",R.drawable.themartian);
                        intentmain.putExtra("Thumbnail2extra","DEFAULT");
                        intentmain.putExtra("classextra","5");
                        intentmain.putExtra("statusextra",dbHandlerList.getstatus(rfid));
                        intentmain.putExtra("timerextra","Yes");
                        intentmain.putExtra("jump","jump");
                        intentmain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //Toast.makeText(getApplicationContext(),dbHandlerRFID.getstatus(rfid),Toast.LENGTH_SHORT).show();
                        getApplicationContext().startActivity(intentmain);

//                        Intent intent = new Intent(popcardstudentred.this,popcardstudentred.class);
//                        intent.putExtra("Name",splitattd[0]);
//                        intent.putExtra("StudentID",dbHandlerList.usridtosid(splitattd[3]));
//                        intent.putExtra("Thumbnail",R.drawable.themartian);
//                        intent.putExtra("Thumbnail2","DEFAULT");
//                        intent.putExtra("class","5");
//                        intent.putExtra("status",dbHandlerList.getstatus(rfid));
//                        intent.putExtra("timer","Yes");
//                        intent.putExtra("jump","jump");
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//                        Toast.makeText(getApplicationContext(),dbHandlerRFID.getstatus(rfid),Toast.LENGTH_SHORT).show();
//                        getApplicationContext().startActivity(intent);

                    }
                }
            }}
        return false;

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    public class GetMethodDemo extends AsyncTask<String , Void ,String> {
        String server_response;

        @Override
        protected String doInBackground(String... strings) {

            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                int responseCode = urlConnection.getResponseCode();
                if(responseCode == HttpURLConnection.HTTP_OK){
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

// Converting InputStream to String

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

}
