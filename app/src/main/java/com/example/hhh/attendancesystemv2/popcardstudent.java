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
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
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
import java.util.jar.Attributes;

/**
 * Created by hhh on 7/8/2018.
 */

public class popcardstudent extends AppCompatActivity{
  //  ImageView studentpic;
   // TextView studentstatus;

    String combineApi="http://studentshield.in/attendance/apiandupdate.php?classid=";

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
    String name;
    boolean value;
    ImageView studentimage;
    MyDBHandler dbHandlerList;
    String[] mycard;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        dbHandlerList = new MyDBHandler(getApplicationContext(), null, null, 7);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardlayout);
        Intent intent = getIntent();
         name = intent.getExtras().getString("Name");
        String timer = intent.getExtras().getString("timer");
        // String thumbnail=intent.getExtras().getString("Thumbnail2");

        final String id = intent.getExtras().getString("StudentID");
       mycard = dbHandlerList.valuetodisplayincard(id).split("&");

        //this.setFinishOnTouchOutside(false);

        int pic = intent.getExtras().getInt("Thumbnail");
        String status = intent.getExtras().getString("status");


        DisplayMetrics screen = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(screen);
        // Toast.makeText(getApplicationContext(),timer,Toast.LENGTH_SHORT).show();

        int Width = screen.widthPixels;
        int height = screen.heightPixels;
        getWindow().setLayout((int) (Width * .9), (int) (height * .28));
        lstudentname = (TextView) findViewById(R.id.lnamestudent);
        nameplate = (TextView) findViewById(R.id.Name);

        //  studentstatus=(TextView)findViewById(R.id.statusofstudent);
        absent = (RadioButton) findViewById(R.id.radioabsent);
        present = (RadioButton) findViewById(R.id.radiopresnt);
        if (status.equals("PRESENT")) {
            present.setChecked(true);
            absent.setChecked(false);
            value = true;

        } else {
            present.setChecked(false);
            absent.setChecked(true);
            value = false;

        }
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


        absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                present.setChecked(!value);
                absent.setChecked(value);
                if (value == true) {

                    //Toast.makeText(getApplicationContext(), "Absent", Toast.LENGTH_SHORT).show();
                    dbHandlerList.updateAttendance2(id, mycard[2], "ABSENT");


                } else {
                    //Toast.makeText(getApplicationContext(), "Present", Toast.LENGTH_SHORT).show();
                    dbHandlerList.updateAttendance2(id, mycard[2], "PRESENT");
                }
                value = !value;


            }
        });
        present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//
                present.setChecked(!value);
                absent.setChecked(value);
                if (value == false) {
                   // Toast.makeText(getApplicationContext(), "Present", Toast.LENGTH_SHORT).show();
                    dbHandlerList.updateAttendance2(id, mycard[2], "PRESENT");
                } else {
                  //  Toast.makeText(getApplicationContext(), "Absent", Toast.LENGTH_SHORT).show();
                    dbHandlerList.updateAttendance2(id, mycard[2], "ABSENT");
                }

                value = !value;
            }
        });

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

        if(timer.equals("Yes")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent startPageIndent = new Intent(popcardstudent.this, MainActivity.class);
                    startActivity(startPageIndent);
                    finish();
                }
            }, 1000);
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String attd= dbHandlerList.idtostring(mycard[4]);
        if(value)
        {
            if(attd.equals("###")) {
                Toast.makeText(getApplicationContext(), "Wrong Id or Id is not registered", Toast.LENGTH_SHORT).show();

            }
            else {
                String splitattd[] = (attd).split("#");
               Toast.makeText(getApplicationContext(), splitattd[0].trim()+ " is present", Toast.LENGTH_SHORT).show();
                //new GetMethodDemo().execute(combineApi+splitattd[2] + "&sid=" + splitattd[3] + "&sname=" + name.trim()+ "&sphone=" + mycard[5].trim());
                new GetMethodDemo().execute(combineApi + splitattd[2] + "&sid=" + splitattd[3] + "&sname=" + splitattd[0].trim() + "&sphone=" + splitattd[1].trim());
            }

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
