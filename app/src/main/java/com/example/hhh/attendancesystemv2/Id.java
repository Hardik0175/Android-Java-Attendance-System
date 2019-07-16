package com.example.hhh.attendancesystemv2;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Id extends Fragment implements View.OnKeyListener{
    private static final String URL_SYNCSTUDENTS = "http://studentshield.in/attendance/syncstudentdata.php";
    private static final String URL_SYNCCLASS= "http://studentshield.in/attendance/syncclassdata.php";
    private static final String URL_SYNCPIC= "http://studentshield.in/attendance/syncphotos.php";
    private static final String URL_SYNCROUTES = "http://studentshield.in/attendance/syncroutedata.php";
    private static final String URL_SYNCUNIV = "http://studentshield.in/attendance/syncunivdata.php";
    int c=1;

    ArrayList<String> calendertimings;

//    String mylink = "http://studentshield.in/attendance/updateattendance.php?classid=";
//    String Apimsg="https://enterprise.smsgupshup.com/GatewayAPI/rest?method=SendMessage&send_to=";
//    String Apimsg2="&msg_type=TEXT&userid=2000137573&auth_scheme=plain&password=kapila8143&v=1.1&format=text";
    String combineApi="http://studentshield.in/attendance/apiandupdate.php?classid=";

    Map<String, String> map = new HashMap<String, String>();
    Map<String, String> map2 = new HashMap<String, String>();

    Map<String, String> map3 = new HashMap<String, String>();  //for slots in the class
    Map<String, String> map4 = new HashMap<String, String>();  //for slots in the class

    String rfid;
    EditText editText;
    TextView tv1;
    Button sync;
    Button upload;
    MyDBHandler dbHandlerRFID;
    TextView value;
    TextView value2;
    public Id() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppThemeforform);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        View v= localInflater.inflate(R.layout.fragment_id, container, false);
       // Toast.makeText(getContext(),   "onCreate", Toast.LENGTH_LONG).show();
        dbHandlerRFID = new MyDBHandler(getContext(), null, null, 6);
        editText = (EditText)v.findViewById(R.id.textInputEditTextFirstName);

        tv1 = (TextView) v.findViewById(R.id.textView2);
        value=(TextView) v.findViewById(R.id.textView3);
        value2=(TextView) v.findViewById(R.id.textView4);

        sync =(Button) v.findViewById(R.id.sync);
        loadClass();
        loadpic();


        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c=0;
                Toast.makeText(getContext(), "NEW LIST IS CREATED", Toast.LENGTH_SHORT).show();
                String createnew="http://studentshield.in/attendance/createnew.php";
                new GetMethodDemo().execute(createnew);
                loadStudents();
                loadRoutes();
                loaduniv();
            }
        });
        editText.setOnKeyListener(this);



        Typeface face= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Regular.ttf");
        Typeface face2= Typeface.createFromAsset(getActivity().getAssets(),"fonts/CabinSketch-Bold.ttf");
        Typeface face3= Typeface.createFromAsset(getActivity().getAssets(),"fonts/freestyle.TTF");

        Button upload=(Button)v.findViewById(R.id.button);
        tv1.setTypeface(face2);
        value.setTypeface(face3);
        value2.setTypeface(face3);
        editText.setTypeface(face);
        sync.setTypeface(face);
        upload.setTypeface(face);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rfid = editText.getText().toString();

                if(rfid.equals("")){
                    Toast.makeText(getContext(), "Please swipe tag on rfid sensor...", Toast.LENGTH_SHORT).show();
                }
                else {
                    String attd= dbHandlerRFID.idtostring(rfid);
                    //Toast.makeText(getContext(), " -" + attd, Toast.LENGTH_SHORT).show();

                    if(attd.equals("###")) {
                        Toast.makeText(getContext(), "Wrong Id or Id is not registered", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        String splitattd[]=(attd).split("#");

                        Toast.makeText(getContext(),   splitattd[0]+ " is present " + "SMS is send to " + splitattd[1], Toast.LENGTH_LONG).show();

                        new GetMethodDemo().execute(combineApi+splitattd[2] + "&sid=" + splitattd[3] + "&sname=" + splitattd[0].trim()+ "&sphone=" + splitattd[1].trim());


                        dbHandlerRFID.updateAttendance(rfid);
                        Intent intent = new Intent(getContext(),popcardstudent.class);
                        intent.putExtra("Name",splitattd[0]);
                        intent.putExtra("StudentID",dbHandlerRFID.usridtosid(splitattd[3]));
                        intent.putExtra("Thumbnail",R.drawable.themartian);
                        intent.putExtra("Thumbnail2","DEFAULT");
                        intent.putExtra("class","5");
                        intent.putExtra("status","PRESENT");
                        intent.putExtra("timer","Yes");
                       // Toast.makeText(getContext(),splitattd[0]+"-"+dbHandlerRFID.usridtosid(splitattd[3])+"-"+R.drawable.themartian+"-"+"DEFAULT"+"-"+"10"+"-"+"PRESENT",Toast.LENGTH_SHORT).show();
                       getContext().startActivity(intent);



                    }

                }
                editText.setText("");
                editText.getText().clear();

            }
        });
        return v;
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent event) {
        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                (i == KeyEvent.KEYCODE_ENTER && editText.toString()!="")) {

            String attd;
            String rfid2="";
            rfid = editText.getText().toString();
            if(rfid.equals("")){
                Toast.makeText(getContext(), "Please swipe tag on rfid sensor...", Toast.LENGTH_SHORT).show();
            }
            else {
            char ch=rfid.charAt(0);
          //  Toast.makeText(getContext(), String.valueOf(rfid.length()), Toast.LENGTH_SHORT).show();
            //Toast.makeText(getContext(), " p"+ rfid+ "p ", Toast.LENGTH_SHORT).show();

            if(ch=='\n') {

                for (i = 1; i <rfid.length(); i++) {

                    rfid2 +=String.valueOf(rfid.charAt(i));

                }
               attd= dbHandlerRFID.idtostring(rfid2);
               // Toast.makeText(getContext(), rfid2+" "+rfid2.length(), Toast.LENGTH_SHORT).show();

            }
            else
            {

               attd= dbHandlerRFID.idtostring(rfid);
            }

            editText.getText().clear();

            if(attd.equals("###")) {
                Toast.makeText(getContext(), "Wrong Id or Id is not registered", Toast.LENGTH_SHORT).show();
              //  editText.getText().clear();

            }
            else
            {
                String splitattd[]=(attd).split("#");
                Toast.makeText(getContext(),   splitattd[0] +" is present " + "SMS is send to " + splitattd[1], Toast.LENGTH_LONG).show();
                new GetMethodDemo().execute(combineApi+splitattd[2] + "&sid=" + splitattd[3] + "&sname=" + splitattd[0].trim()+ "&sphone=" + splitattd[1].trim());
                //Getting intent and PendingIntent instance
                dbHandlerRFID.updateAttendance(rfid);
                Intent intent = new Intent(getContext(),popcardstudent.class);
                intent.putExtra("Name",splitattd[0]);
                intent.putExtra("StudentID",dbHandlerRFID.usridtosid(splitattd[3]));
                intent.putExtra("Thumbnail",R.drawable.themartian);
                intent.putExtra("Thumbnail2","DEFAULT");
                intent.putExtra("class","5");
                intent.putExtra("status","PRESENT");
                intent.putExtra("timer","Yes");
                // Toast.makeText(getContext(),splitattd[0]+"-"+dbHandlerRFID.usridtosid(splitattd[3])+"-"+R.drawable.themartian+"-"+"DEFAULT"+"-"+"10"+"-"+"PRESENT",Toast.LENGTH_SHORT).show();
                getContext().startActivity(intent);

            }

        }
        }

        return false;
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

    private void loadStudents() {
        /*
        * Creating a String Request
        * The request type is GET defined by first parameter
        * The URL is defined in the second parameter
        * Then we have a Response Listener and a Error Listener
        * In response listener we will get the JSON response as a String
        * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_SYNCSTUDENTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            //converting the string to json array object
                            dbHandlerRFID.deletetable();
                            JSONArray array = new JSONArray(response);
                            // Toast.makeText(getContext(),String.valueOf(array),Toast.LENGTH_SHORT).show();
                            // dbHandler.deletetable();
                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                String valueofpic;
                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);
                                // Toast.makeText(getContext(),String.valueOf(product),Toast.LENGTH_SHORT).show();
                                if(map2.get(product.getString("wp_usr_id"))!=(null))
                                {
                                    valueofpic=map2.get(product.getString("wp_usr_id"));
                                    //  Toast.makeText(getContext(),map2.get(product.getString("wp_usr_id")),Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    valueofpic="DEFAULT";
                                }

                                ADDstudent(product.getString("s_fname"),product.getString("s_mname"),product.getString("s_lname"),map.get(product.getString("class_id")),product.getString("class_id"),"4",product.getString("s_cardid"),product.getString("s_phone"),product.getString("sid"),product.getString("wp_usr_id"),product.getString("s_piuroute"),product.getString("s_drroute"),product.getString("s_status"),valueofpic,map3.get(product.getString("class_id")),map4.get(product.getString("class_id")));
                              //  Toast.makeText(getContext(),product.getString("s_fname")+ ": " +map4.get(product.getString("class_id")),Toast.LENGTH_SHORT).show();
                            }
                            // map.clear();
                            //loadfromDB(1);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        //adding our stringrequest to queue
        Volley.newRequestQueue(getContext()).add(stringRequest);

    }

    private void loadRoutes() {
        /*
        * Creating a String Request
        * The request type is GET defined by first parameter
        * The URL is defined in the second parameter
        * Then we have a Response Listener and a Error Listener
        * In response listener we will get the JSON response as a String
        * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_SYNCROUTES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            dbHandlerRFID.deletetableroutes();

                            JSONArray array = new JSONArray(response);
                            //  Toast.makeText(getContext(),String.valueOf(array),Toast.LENGTH_LONG).show();
                            for (int i = 0; i < array.length(); i++) {



                                JSONObject product = array.getJSONObject(i);

                                ADDroute(product.getString("id"),product.getString("v_route"),product.getString("begintime"),product.getString("endtime"),product.getString("type"),product.getString("r_message"));

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }
    private void loaduniv() {
        /*
        * Creating a String Request
        * The request type is GET defined by first parameter
        * The URL is defined in the second parameter
        * Then we have a Response Listener and a Error Listener
        * In response listener we will get the JSON response as a String
        * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_SYNCUNIV,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                           // Toast.makeText(getContext(),dbHandlerRFID.testforuniv(),Toast.LENGTH_LONG).show();
                            //dbHandlerRFID.testforuniv();
                            dbHandlerRFID.deletetableuniv();

                            JSONArray array = new JSONArray(response);
                              //Toast.makeText(getContext(),String.valueOf(array),Toast.LENGTH_LONG).show();
                            for (int i = 0; i < array.length(); i++) {



                                JSONObject product = array.getJSONObject(i);
                              //Toast.makeText(getContext(),"begin: "+ product.getString("begintime")+" end: "+product.getString("endtime"),Toast.LENGTH_SHORT).show();

                                ADDuniv(product.getString("id"),product.getString("slot"),product.getString("begintime"),product.getString("endtime"),array.length());

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        Volley.newRequestQueue(getContext()).add(stringRequest);

    }

    private void loadClass() {

        map.clear();
        map3.clear();
        map4.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_SYNCCLASS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            //  Toast.makeText(getContext(),String.valueOf(array),Toast.LENGTH_SHORT).show();
                            // Toast.makeText(getContext(),String.valueOf(array),Toast.LENGTH_LONG).show();

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {
                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);
                                map.put(product.getString("cid"),product.getString("c_numb"));
                                map3.put(product.getString("cid"),product.getString("c_slotentry"));
                                map4.put(product.getString("cid"),product.getString("c_slotexit"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        //adding our stringrequest to queue
        Volley.newRequestQueue(getContext()).add(stringRequest);
        //  loadStudents();

    }
    private void loadpic() {
        map2.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_SYNCPIC,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            // Toast.makeText(getContext(),String.valueOf(array),Toast.LENGTH_LONG).show();

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {
                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);
                                String[] parts = String.valueOf(product.getString("meta_value")).split("\"");
                                //  Toast.makeText(getContext(),parts[parts.length-2],Toast.LENGTH_SHORT).show();
                                map2.put(product.getString("user_id"),parts[parts.length-2]);
                            }

                            //loadfromDB(1);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        //adding our stringrequest to queue
        Volley.newRequestQueue(getContext()).add(stringRequest);
        //  loadStudents();

    }
    void ADDstudent(String stuF,String stuM,String stuL,String stuC,String stuCid,String stuAge,String stuId,String stuph,String sid,String susrid,String pr,String dr,String status,String pic,String fslot,String sslot)
    {
        StudentsDB newstudent = new StudentsDB(stuF,stuM,stuL,stuC,stuCid,stuAge,stuId,stuph,sid,susrid,pr,dr,status,pic,fslot,sslot);
     //  Toast.makeText(getContext(),stuCid+"  "+stuF+ " "+ map.get(stuCid)+" " + sslot,Toast.LENGTH_SHORT).show();
        dbHandlerRFID.addProduct(newstudent);

    }
    void ADDroute(String rouId,String rouN,String rouSt,String rouEn,String routy,String roumsg)
    {
        RouteDB newroute = new RouteDB(rouId,rouN,rouSt,rouEn,routy,roumsg);
        // Toast.makeText(getContext(),stuCid+"  "+stuF+ " "+ map.get(stuCid),Toast.LENGTH_SHORT).show();
        dbHandlerRFID.addProductRoutes(newroute);
    }
    void ADDuniv(String univId,String univN,String begin,String end,int times)
    {
        UnivDB newroute = new UnivDB(univId,univN,begin,end);
        dbHandlerRFID.addProductuniv(newroute);
        Calendar calendar = Calendar.getInstance();
        Calendar calendar1=Calendar.getInstance();
        String[] myvariable=begin.split(":");
        String[] myvariableforend=end.split(":");
        ++c;
        if(c==times)
        {
           // Toast.makeText(getContext(),"time :"+ String.valueOf(times)+" c :"+String.valueOf(c), Toast.LENGTH_SHORT).show();
            setalarm();
        }

    }

    void setalarm(){
        calendertimings=new ArrayList<String>(1);
       // Toast.makeText(getContext(),dbHandlerRFID.getworktime(),Toast.LENGTH_SHORT).show();
        String[] eachslot=dbHandlerRFID.getworktime().split("#");
        for(int i=0;i<eachslot.length;i++)
        {
            calendertimings.add(eachslot[i]);
        }
        Collections.sort(calendertimings, new Comparator<String>() {
            @Override
            public int compare(String studentcardid, String t1) {
                // Toast.makeText(mContext,studentcardid.getName() + " " + String.valueOf(studentcardid.getName().compareTo(t1.getName())) + " " +t1.getName(),Toast.LENGTH_SHORT).show();
                return studentcardid.compareToIgnoreCase(t1);

            }
        });
        for(int i=0;i<eachslot.length;i++)
        {

           // Toast.makeText(getContext(),"mylsi"+calendertimings.get(i),Toast.LENGTH_SHORT).show();

        }

        Calendar calendarbeginm = Calendar.getInstance();
        Calendar calendarendm =Calendar.getInstance();
        Calendar calendarbegina = Calendar.getInstance();
        Calendar calendarenda =Calendar.getInstance();
        Calendar calendarbegine = Calendar.getInstance();
        Calendar calendarende =Calendar.getInstance();
        if(calendertimings.get(0).equals("(A)") && eachslot.length==3)
        {
            if(calendertimings.get(2).equals("(M)"))
            {
                //Toast.makeText(getContext(), "Alarm is set at -" + "(m)b-"+begin, Toast.LENGTH_SHORT).show();
                if (android.os.Build.VERSION.SDK_INT >= 23) {
                    String valueb=dbHandlerRFID.gettimingsbegintime("(M)");
                    String valuee=dbHandlerRFID.gettimingsendtime("(M)");
                    String[] value1=valueb.split(":");
                    String[] value2=valuee.split(":");
                    //Toast.makeText(getContext(), "-(M)"+value1[0]+"  "+value1[1]+ " "+value2[0]+" "+ value2[1], Toast.LENGTH_SHORT).show();
                    calendarendm.set(calendarendm.get(Calendar.YEAR), calendarendm.get(Calendar.MONTH), calendarendm.get(Calendar.DAY_OF_MONTH),
                            Integer.valueOf(value2[0]), Integer.valueOf(value2[1]), 0);
                    calendarbeginm.set(calendarbeginm.get(Calendar.YEAR), calendarbeginm.get(Calendar.MONTH), calendarbeginm.get(Calendar.DAY_OF_MONTH),
                            Integer.valueOf(value1[0]), Integer.valueOf(value1[1]), 0);
                }
                else {
                    String valueb=dbHandlerRFID.gettimingsbegintime("(M)");
                    String valuee=dbHandlerRFID.gettimingsendtime("(M)");
                    String[] value1=valueb.split(":");
                    String[] value2=valuee.split(":");

                    calendarendm.set(calendarendm.get(Calendar.YEAR), calendarendm.get(Calendar.MONTH), calendarendm.get(Calendar.DAY_OF_MONTH),
                            Integer.valueOf(value2[0]), Integer.valueOf(value2[1]), 0);
                    calendarbeginm.set(calendarbeginm.get(Calendar.YEAR), calendarbeginm.get(Calendar.MONTH), calendarbeginm.get(Calendar.DAY_OF_MONTH),
                            Integer.valueOf(value1[0]), Integer.valueOf(value1[1]), 0);

                }


                ((MainActivity)getActivity()).setAlarm(calendarbeginm.getTimeInMillis(),1);
                ((MainActivity)getActivity()).setAlarm(calendarendm.getTimeInMillis(),2);
            }
            else if(calendertimings.get(1).equals("(M)"))
            {
                //Toast.makeText(getContext(), "Alarm is set at -" + "(m)b-"+begin, Toast.LENGTH_SHORT).show();
                if (android.os.Build.VERSION.SDK_INT >= 23) {
                    String valueb=dbHandlerRFID.gettimingsbegintime("(M)");
                    String valuee=dbHandlerRFID.gettimingsendtime("(M)");
                    String[] value1=valueb.split(":");
                    String[] value2=valuee.split(":");
                    //Toast.makeText(getContext(), "-(M)"+value1[0]+"  "+value1[1]+ " "+value2[0]+" "+ value2[1], Toast.LENGTH_SHORT).show();

                    calendarendm.set(calendarendm.get(Calendar.YEAR), calendarendm.get(Calendar.MONTH), calendarendm.get(Calendar.DAY_OF_MONTH),
                            Integer.valueOf(value2[0]), Integer.valueOf(value2[1]), 0);
                    calendarbeginm.set(calendarbeginm.get(Calendar.YEAR), calendarbeginm.get(Calendar.MONTH), calendarbeginm.get(Calendar.DAY_OF_MONTH),
                            Integer.valueOf(value1[0]), Integer.valueOf(value1[1]), 0);
                }
                else {
                    String valueb=dbHandlerRFID.gettimingsbegintime("(M)");
                    String valuee=dbHandlerRFID.gettimingsendtime("(M)");
                    String[] value1=valueb.split(":");
                    String[] value2=valuee.split(":");

                    calendarendm.set(calendarendm.get(Calendar.YEAR), calendarendm.get(Calendar.MONTH), calendarendm.get(Calendar.DAY_OF_MONTH),
                            Integer.valueOf(value2[0]), Integer.valueOf(value2[1]), 0);
                    calendarbeginm.set(calendarbeginm.get(Calendar.YEAR), calendarbeginm.get(Calendar.MONTH), calendarbeginm.get(Calendar.DAY_OF_MONTH),
                            Integer.valueOf(value1[0]), Integer.valueOf(value1[1]), 0);

                }


                ((MainActivity)getActivity()).setAlarm(calendarbeginm.getTimeInMillis(),1);
                ((MainActivity)getActivity()).setAlarm(calendarendm.getTimeInMillis(),2);
            }

            if (android.os.Build.VERSION.SDK_INT >= 23) {
                String valueb=dbHandlerRFID.gettimingsbegintime("(A)");
                String valuee=dbHandlerRFID.gettimingsendtime("(A)");
                String[] value1=valueb.split(":");
                String[] value2=valuee.split(":");
                // Toast.makeText(getContext(), "-(A)"+value1[0]+"  "+value1[1]+ " "+value2[0]+" "+ value2[1], Toast.LENGTH_SHORT).show();
                calendarenda.set(calendarenda.get(Calendar.YEAR), calendarenda.get(Calendar.MONTH), calendarenda.get(Calendar.DAY_OF_MONTH),
                        Integer.valueOf(value2[0]), Integer.valueOf(value2[1]), 0);
                calendarbegina.set(calendarbegina.get(Calendar.YEAR), calendarbegina.get(Calendar.MONTH), calendarbegina.get(Calendar.DAY_OF_MONTH),
                        Integer.valueOf(value1[0]), Integer.valueOf(value1[1]), 0);
            }
            else {
                String valueb=dbHandlerRFID.gettimingsbegintime("(A)");
                String valuee=dbHandlerRFID.gettimingsendtime("(A)");
                String[] value1=valueb.split(":");
                String[] value2=valuee.split(":");

                calendarenda.set(calendarenda.get(Calendar.YEAR), calendarenda.get(Calendar.MONTH), calendarenda.get(Calendar.DAY_OF_MONTH),
                        Integer.valueOf(value2[0]), Integer.valueOf(value2[1]), 0);
                calendarbegina.set(calendarbegina.get(Calendar.YEAR), calendarbegina.get(Calendar.MONTH), calendarbegina.get(Calendar.DAY_OF_MONTH),
                        Integer.valueOf(value1[0]), Integer.valueOf(value1[1]), 0);

            }
            ((MainActivity)getActivity()).setAlarm(calendarbegina.getTimeInMillis(),3);
            ((MainActivity)getActivity()).setAlarm(calendarenda.getTimeInMillis(),4);


            if(calendertimings.get(1).equals("(E)"))
            {
                if (android.os.Build.VERSION.SDK_INT >= 23) {
                    String valueb=dbHandlerRFID.gettimingsbegintime("(E)");
                    String valuee=dbHandlerRFID.gettimingsendtime("(E)");
                    String[] value1=valueb.split(":");
                    String[] value2=valuee.split(":");
                   // Toast.makeText(getContext(), "-(E)"+value1[0]+"  "+value1[1]+ " "+value2[0]+" "+ value2[1], Toast.LENGTH_SHORT).show();
                    calendarende.set(calendarende.get(Calendar.YEAR), calendarende.get(Calendar.MONTH), calendarende.get(Calendar.DAY_OF_MONTH),
                            Integer.valueOf(value2[0]), Integer.valueOf(value2[1]), 0);
                    calendarbegine.set(calendarbegine.get(Calendar.YEAR), calendarbegine.get(Calendar.MONTH), calendarbegine.get(Calendar.DAY_OF_MONTH),
                            Integer.valueOf(value1[0]), Integer.valueOf(value1[1]), 0);
                }
                else {
                    String valueb=dbHandlerRFID.gettimingsbegintime("(E)");
                    String valuee=dbHandlerRFID.gettimingsendtime("(E)");
                    String[] value1=valueb.split(":");
                    String[] value2=valuee.split(":");

                    calendarende.set(calendarende.get(Calendar.YEAR), calendarende.get(Calendar.MONTH), calendarende.get(Calendar.DAY_OF_MONTH),
                            Integer.valueOf(value2[0]), Integer.valueOf(value2[1]), 0);
                    calendarbegine.set(calendarbegine.get(Calendar.YEAR), calendarbegine.get(Calendar.MONTH), calendarbegine.get(Calendar.DAY_OF_MONTH),
                            Integer.valueOf(value1[0]), Integer.valueOf(value1[1]), 0);

                }


                ((MainActivity)getActivity()).setAlarm(calendarbegine.getTimeInMillis(),5);
                ((MainActivity)getActivity()).setAlarm(calendarende.getTimeInMillis(),6);


            }

        }
       else if(calendertimings.get(0).equals("(A)") && eachslot.length==2)
        {
            if(calendertimings.get(1).equals("(M)"))
            {
                //Toast.makeText(getContext(), "Alarm is set at -" + "(m)b-"+begin, Toast.LENGTH_SHORT).show();
                if (android.os.Build.VERSION.SDK_INT >= 23) {
                    String valueb=dbHandlerRFID.gettimingsbegintime("(M)");
                    String valuee=dbHandlerRFID.gettimingsendtime("(M)");
                    String[] value1=valueb.split(":");
                    String[] value2=valuee.split(":");
                    //Toast.makeText(getContext(), "-(M)"+value1[0]+"  "+value1[1]+ " "+value2[0]+" "+ value2[1], Toast.LENGTH_SHORT).show();
                    calendarendm.set(calendarendm.get(Calendar.YEAR), calendarendm.get(Calendar.MONTH), calendarendm.get(Calendar.DAY_OF_MONTH),
                            Integer.valueOf(value2[0]), Integer.valueOf(value2[1]), 0);
                    calendarbeginm.set(calendarbeginm.get(Calendar.YEAR), calendarbeginm.get(Calendar.MONTH), calendarbeginm.get(Calendar.DAY_OF_MONTH),
                            Integer.valueOf(value1[0]), Integer.valueOf(value1[1]), 0);
                }
                else {
                    String valueb=dbHandlerRFID.gettimingsbegintime("(M)");
                    String valuee=dbHandlerRFID.gettimingsendtime("(M)");
                    String[] value1=valueb.split(":");
                    String[] value2=valuee.split(":");

                    calendarendm.set(calendarendm.get(Calendar.YEAR), calendarendm.get(Calendar.MONTH), calendarendm.get(Calendar.DAY_OF_MONTH),
                            Integer.valueOf(value2[0]), Integer.valueOf(value2[1]), 0);
                    calendarbeginm.set(calendarbeginm.get(Calendar.YEAR), calendarbeginm.get(Calendar.MONTH), calendarbeginm.get(Calendar.DAY_OF_MONTH),
                            Integer.valueOf(value1[0]), Integer.valueOf(value1[1]), 0);

                }


                ((MainActivity)getActivity()).setAlarm(calendarbeginm.getTimeInMillis(),1);
                ((MainActivity)getActivity()).setAlarm(calendarendm.getTimeInMillis(),2);
            }


            if (android.os.Build.VERSION.SDK_INT >= 23) {
                String valueb=dbHandlerRFID.gettimingsbegintime("(A)");
                String valuee=dbHandlerRFID.gettimingsendtime("(A)");
                String[] value1=valueb.split(":");
                String[] value2=valuee.split(":");
              //  Toast.makeText(getContext(), "-(A)"+value1[0]+"  "+value1[1]+ " "+value2[0]+" "+ value2[1], Toast.LENGTH_SHORT).show();
                calendarenda.set(calendarenda.get(Calendar.YEAR), calendarenda.get(Calendar.MONTH), calendarenda.get(Calendar.DAY_OF_MONTH),
                        Integer.valueOf(value2[0]), Integer.valueOf(value2[1]), 0);
                calendarbegina.set(calendarbegina.get(Calendar.YEAR), calendarbegina.get(Calendar.MONTH), calendarbegina.get(Calendar.DAY_OF_MONTH),
                        Integer.valueOf(value1[0]), Integer.valueOf(value1[1]), 0);
            }
            else {
                String valueb=dbHandlerRFID.gettimingsbegintime("(A)");
                String valuee=dbHandlerRFID.gettimingsendtime("(A)");
                String[] value1=valueb.split(":");
                String[] value2=valuee.split(":");

                calendarenda.set(calendarenda.get(Calendar.YEAR), calendarenda.get(Calendar.MONTH), calendarenda.get(Calendar.DAY_OF_MONTH),
                        Integer.valueOf(value2[0]), Integer.valueOf(value2[1]), 0);
                calendarbegina.set(calendarbegina.get(Calendar.YEAR), calendarbegina.get(Calendar.MONTH), calendarbegina.get(Calendar.DAY_OF_MONTH),
                        Integer.valueOf(value1[0]), Integer.valueOf(value1[1]), 0);

            }
            ((MainActivity)getActivity()).setAlarm(calendarbegina.getTimeInMillis(),3);
            ((MainActivity)getActivity()).setAlarm(calendarenda.getTimeInMillis(),4);


            if(calendertimings.get(1).equals("(E)"))
            {
                if (android.os.Build.VERSION.SDK_INT >= 23) {
                    String valueb=dbHandlerRFID.gettimingsbegintime("(E)");
                    String valuee=dbHandlerRFID.gettimingsendtime("(E)");
                    String[] value1=valueb.split(":");
                    String[] value2=valuee.split(":");
                  //  Toast.makeText(getContext(), "-(E)"+value1[0]+"  "+value1[1]+ " "+value2[0]+" "+ value2[1], Toast.LENGTH_SHORT).show();
                    calendarende.set(calendarende.get(Calendar.YEAR), calendarende.get(Calendar.MONTH), calendarende.get(Calendar.DAY_OF_MONTH),
                            Integer.valueOf(value2[0]), Integer.valueOf(value2[1]), 0);
                    calendarbegine.set(calendarbegine.get(Calendar.YEAR), calendarbegine.get(Calendar.MONTH), calendarbegine.get(Calendar.DAY_OF_MONTH),
                            Integer.valueOf(value1[0]), Integer.valueOf(value1[1]), 0);
                }
                else {
                    String valueb=dbHandlerRFID.gettimingsbegintime("(E)");
                    String valuee=dbHandlerRFID.gettimingsendtime("(E)");
                    String[] value1=valueb.split(":");
                    String[] value2=valuee.split(":");

                    calendarende.set(calendarende.get(Calendar.YEAR), calendarende.get(Calendar.MONTH), calendarende.get(Calendar.DAY_OF_MONTH),
                            Integer.valueOf(value2[0]), Integer.valueOf(value2[1]), 0);
                    calendarbegine.set(calendarbegine.get(Calendar.YEAR), calendarbegine.get(Calendar.MONTH), calendarbegine.get(Calendar.DAY_OF_MONTH),
                            Integer.valueOf(value1[0]), Integer.valueOf(value1[1]), 0);

                }


                ((MainActivity)getActivity()).setAlarm(calendarbegine.getTimeInMillis(),5);
                ((MainActivity)getActivity()).setAlarm(calendarende.getTimeInMillis(),6);


            }

        }
        else if(calendertimings.get(0).equals("(A)") && eachslot.length==1)
        {
            if (android.os.Build.VERSION.SDK_INT >= 23) {
                String valueb=dbHandlerRFID.gettimingsbegintime("(A)");
                String valuee=dbHandlerRFID.gettimingsendtime("(A)");
                String[] value1=valueb.split(":");
                String[] value2=valuee.split(":");
              //  Toast.makeText(getContext(), "-(A)"+value1[0]+"  "+value1[1]+ " "+value2[0]+" "+ value2[1], Toast.LENGTH_SHORT).show();
                calendarenda.set(calendarenda.get(Calendar.YEAR), calendarenda.get(Calendar.MONTH), calendarenda.get(Calendar.DAY_OF_MONTH),
                        Integer.valueOf(value2[0]), Integer.valueOf(value2[1]), 0);
                calendarbegina.set(calendarbegina.get(Calendar.YEAR), calendarbegina.get(Calendar.MONTH), calendarbegina.get(Calendar.DAY_OF_MONTH),
                        Integer.valueOf(value1[0]), Integer.valueOf(value1[1]), 0);
            }
            else {
                String valueb=dbHandlerRFID.gettimingsbegintime("(A)");
                String valuee=dbHandlerRFID.gettimingsendtime("(A)");
                String[] value1=valueb.split(":");
                String[] value2=valuee.split(":");

                calendarenda.set(calendarenda.get(Calendar.YEAR), calendarenda.get(Calendar.MONTH), calendarenda.get(Calendar.DAY_OF_MONTH),
                        Integer.valueOf(value2[0]), Integer.valueOf(value2[1]), 0);
                calendarbegina.set(calendarbegina.get(Calendar.YEAR), calendarbegina.get(Calendar.MONTH), calendarbegina.get(Calendar.DAY_OF_MONTH),
                        Integer.valueOf(value1[0]), Integer.valueOf(value1[1]), 0);

            }
            ((MainActivity)getActivity()).setAlarm(calendarbegina.getTimeInMillis(),3);
            ((MainActivity)getActivity()).setAlarm(calendarenda.getTimeInMillis(),4);

        }
        else if(calendertimings.get(0).equals("(E)") && eachslot.length==2)
        {
            if(calendertimings.get(1).equals("(M)"))
            {
                //Toast.makeText(getContext(), "Alarm is set at -" + "(m)b-"+begin, Toast.LENGTH_SHORT).show();
                if (android.os.Build.VERSION.SDK_INT >= 23) {
                    String valueb=dbHandlerRFID.gettimingsbegintime("(M)");
                    String valuee=dbHandlerRFID.gettimingsendtime("(M)");
                    String[] value1=valueb.split(":");
                    String[] value2=valuee.split(":");
                  //  Toast.makeText(getContext(), "-(M)"+value1[0]+"  "+value1[1]+ " "+value2[0]+" "+ value2[1], Toast.LENGTH_SHORT).show();
                    calendarendm.set(calendarendm.get(Calendar.YEAR), calendarendm.get(Calendar.MONTH), calendarendm.get(Calendar.DAY_OF_MONTH),
                            Integer.valueOf(value2[0]), Integer.valueOf(value2[1]), 0);
                    calendarbeginm.set(calendarbeginm.get(Calendar.YEAR), calendarbeginm.get(Calendar.MONTH), calendarbeginm.get(Calendar.DAY_OF_MONTH),
                            Integer.valueOf(value1[0]), Integer.valueOf(value1[1]), 0);
                }
                else {
                    String valueb=dbHandlerRFID.gettimingsbegintime("(M)");
                    String valuee=dbHandlerRFID.gettimingsendtime("(M)");
                    String[] value1=valueb.split(":");
                    String[] value2=valuee.split(":");

                    calendarendm.set(calendarendm.get(Calendar.YEAR), calendarendm.get(Calendar.MONTH), calendarendm.get(Calendar.DAY_OF_MONTH),
                            Integer.valueOf(value2[0]), Integer.valueOf(value2[1]), 0);
                    calendarbeginm.set(calendarbeginm.get(Calendar.YEAR), calendarbeginm.get(Calendar.MONTH), calendarbeginm.get(Calendar.DAY_OF_MONTH),
                            Integer.valueOf(value1[0]), Integer.valueOf(value1[1]), 0);

                }


                ((MainActivity)getActivity()).setAlarm(calendarbeginm.getTimeInMillis(),1);
                ((MainActivity)getActivity()).setAlarm(calendarendm.getTimeInMillis(),2);
            }



                if (android.os.Build.VERSION.SDK_INT >= 23) {
                    String valueb=dbHandlerRFID.gettimingsbegintime("(E)");
                    String valuee=dbHandlerRFID.gettimingsendtime("(E)");
                    String[] value1=valueb.split(":");
                    String[] value2=valuee.split(":");
                  //  Toast.makeText(getContext(), "-(E)"+value1[0]+"  "+value1[1]+ " "+value2[0]+" "+ value2[1], Toast.LENGTH_SHORT).show();
                    calendarende.set(calendarende.get(Calendar.YEAR), calendarende.get(Calendar.MONTH), calendarende.get(Calendar.DAY_OF_MONTH),
                            Integer.valueOf(value2[0]), Integer.valueOf(value2[1]), 0);
                    calendarbegine.set(calendarbegine.get(Calendar.YEAR), calendarbegine.get(Calendar.MONTH), calendarbegine.get(Calendar.DAY_OF_MONTH),
                            Integer.valueOf(value1[0]), Integer.valueOf(value1[1]), 0);
                }
                else {
                    String valueb=dbHandlerRFID.gettimingsbegintime("(E)");
                    String valuee=dbHandlerRFID.gettimingsendtime("(E)");
                    String[] value1=valueb.split(":");
                    String[] value2=valuee.split(":");

                    calendarende.set(calendarende.get(Calendar.YEAR), calendarende.get(Calendar.MONTH), calendarende.get(Calendar.DAY_OF_MONTH),
                            Integer.valueOf(value2[0]), Integer.valueOf(value2[1]), 0);
                    calendarbegine.set(calendarbegine.get(Calendar.YEAR), calendarbegine.get(Calendar.MONTH), calendarbegine.get(Calendar.DAY_OF_MONTH),
                            Integer.valueOf(value1[0]), Integer.valueOf(value1[1]), 0);

                }


                ((MainActivity)getActivity()).setAlarm(calendarbegine.getTimeInMillis(),5);
                ((MainActivity)getActivity()).setAlarm(calendarende.getTimeInMillis(),6);
        }
        else if(calendertimings.get(0).equals("(E)") && eachslot.length==1)
        {
            if (android.os.Build.VERSION.SDK_INT >= 23) {
                String valueb=dbHandlerRFID.gettimingsbegintime("(E)");
                String valuee=dbHandlerRFID.gettimingsendtime("(E)");
                String[] value1=valueb.split(":");
                String[] value2=valuee.split(":");
            //    Toast.makeText(getContext(), "-(E)"+value1[0]+"  "+value1[1]+ " "+value2[0]+" "+ value2[1], Toast.LENGTH_SHORT).show();
                calendarende.set(calendarende.get(Calendar.YEAR), calendarende.get(Calendar.MONTH), calendarende.get(Calendar.DAY_OF_MONTH),
                        Integer.valueOf(value2[0]), Integer.valueOf(value2[1]), 0);
                calendarbegine.set(calendarbegine.get(Calendar.YEAR), calendarbegine.get(Calendar.MONTH), calendarbegine.get(Calendar.DAY_OF_MONTH),
                        Integer.valueOf(value1[0]), Integer.valueOf(value1[1]), 0);
            }
            else {
                String valueb=dbHandlerRFID.gettimingsbegintime("(E)");
                String valuee=dbHandlerRFID.gettimingsendtime("(E)");
                String[] value1=valueb.split(":");
                String[] value2=valuee.split(":");

                calendarende.set(calendarende.get(Calendar.YEAR), calendarende.get(Calendar.MONTH), calendarende.get(Calendar.DAY_OF_MONTH),
                        Integer.valueOf(value2[0]), Integer.valueOf(value2[1]), 0);
                calendarbegine.set(calendarbegine.get(Calendar.YEAR), calendarbegine.get(Calendar.MONTH), calendarbegine.get(Calendar.DAY_OF_MONTH),
                        Integer.valueOf(value1[0]), Integer.valueOf(value1[1]), 0);

            }


            ((MainActivity)getActivity()).setAlarm(calendarbegine.getTimeInMillis(),5);
            ((MainActivity)getActivity()).setAlarm(calendarende.getTimeInMillis(),6);
        }
        else if(calendertimings.get(0).equals("(M)") && eachslot.length==1)
        {

            //Toast.makeText(getContext(), "Alarm is set at -" + "(m)b-"+begin, Toast.LENGTH_SHORT).show();
            if (android.os.Build.VERSION.SDK_INT >= 23) {
                String valueb=dbHandlerRFID.gettimingsbegintime("(M)");
                String valuee=dbHandlerRFID.gettimingsendtime("(M)");
                String[] value1=valueb.split(":");
                String[] value2=valuee.split(":");
             //   Toast.makeText(getContext(), "-(M)"+value1[0]+"  "+value1[1]+ " "+value2[0]+" "+ value2[1], Toast.LENGTH_SHORT).show();
                calendarendm.set(calendarendm.get(Calendar.YEAR), calendarendm.get(Calendar.MONTH), calendarendm.get(Calendar.DAY_OF_MONTH),
                        Integer.valueOf(value2[0]), Integer.valueOf(value2[1]), 0);
                calendarbeginm.set(calendarbeginm.get(Calendar.YEAR), calendarbeginm.get(Calendar.MONTH), calendarbeginm.get(Calendar.DAY_OF_MONTH),
                        Integer.valueOf(value1[0]), Integer.valueOf(value1[1]), 0);
            }
            else {
                String valueb=dbHandlerRFID.gettimingsbegintime("(M)");
                String valuee=dbHandlerRFID.gettimingsendtime("(M)");
                String[] value1=valueb.split(":");
                String[] value2=valuee.split(":");

                calendarendm.set(calendarendm.get(Calendar.YEAR), calendarendm.get(Calendar.MONTH), calendarendm.get(Calendar.DAY_OF_MONTH),
                        Integer.valueOf(value2[0]), Integer.valueOf(value2[1]), 0);
                calendarbeginm.set(calendarbeginm.get(Calendar.YEAR), calendarbeginm.get(Calendar.MONTH), calendarbeginm.get(Calendar.DAY_OF_MONTH),
                        Integer.valueOf(value1[0]), Integer.valueOf(value1[1]), 0);

            }

            ((MainActivity)getActivity()).setAlarm(calendarbeginm.getTimeInMillis(),1);
            ((MainActivity)getActivity()).setAlarm(calendarendm.getTimeInMillis(),2);
        }

    }

}
