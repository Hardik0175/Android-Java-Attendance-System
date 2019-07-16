package com.example.hhh.attendancesystemv2;


import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */public class Studentlistclass extends Fragment {


    private static final String URL_SYNCSTUDENTS = "http://studentshield.in/attendance/syncstudentdata.php";
    private static final String URL_SYNCCLASS= "http://studentshield.in/attendance/syncclassdata.php";

    Map<String, String> map = new HashMap<String, String>();




    // String myStr = getArguments().getString("my_key");
    String named;
    EditText name;
    TextView classname;
    int classnumber=1;

    Button button;


    NumberPicker np;
   // String type;


    ArrayList<String> itemList;


    List<Studentcardid> temp;
    List<Studentcardid> cardid ;
    RecyclerViewAdapter myAdapter;
    MyDBHandler dbHandler;
    String routevalue[]=null;
    public Studentlistclass() {


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  Toast.makeText(getContext(),"oncreateView",Toast.LENGTH_SHORT).show();
        //loadClass();
        cardid = new ArrayList<>();
        View v = inflater.inflate(R.layout.fragment_studentlistclass, container, false);


        cardid.add(new Studentcardid("name","present","40","1",R.drawable.themartian));


        dbHandler = new MyDBHandler(getContext(), null, null, 7);
        String classno=dbHandler.classvalue();
       // Toast.makeText(getContext(),dbHandler.selectclassfrag(),Toast.LENGTH_SHORT).show();

        Typeface face= Typeface.createFromAsset(getActivity().getAssets(),"fonts/CabinSketch-Bold.ttf");

        NumberPicker np=(NumberPicker) v.findViewById(R.id.numberPicker);

        np.setMaxValue(10);
        np.setMinValue(1);

        set_numberpicker_text_colour(np);

        np.setValue( Integer.valueOf(dbHandler.selectclassfrag()));

        np.setOnValueChangedListener(onValueChangeListener);
        final TextView clasname=(TextView) v.findViewById(R.id.classname);
        clasname.setTypeface(face);


        classnumber=Integer.valueOf(dbHandler.selectclassfrag());

        itemList=new ArrayList<String>(1);


        temp = new ArrayList<>();

        if(!dbHandler.valueforcard(String.valueOf(classnumber)).equals("%")) {

            String Class1Student = dbHandler.valueforcard(String.valueOf(classnumber));
            String[] parts = Class1Student.split("#");

            for (String a : parts) {
                if(!a.equals("%"))
                {  String[] eachstudent = a.split("&");
                    temp.add(new Studentcardid(eachstudent[0], eachstudent[1], eachstudent[2], "1", R.drawable.themartian));

                }
            }
        }
        else
        {
            temp.add(new Studentcardid("Empty", "ABSENT", "100", "1", R.drawable.themartian));
        }


        myAdapter = new RecyclerViewAdapter(getContext(),cardid);
        RecyclerView myrv = (RecyclerView) v.findViewById(R.id.recyclerview_id);

        myrv.setLayoutManager(new GridLayoutManager(getContext(),3));
        myrv.setAdapter(myAdapter);
        // myAdapter.addAll(cardid);



        //final CustomAdapter customAdapter = new CustomAdapter(getContext(), itemList);

        //  listView.setAdapter(customAdapter);
        /*if(getArgument!=null)
        {
            itemList1.set(0,getArgument);
        }*/
        //editText=(EditText) findViewById(R.id.EditText11);
        Typeface face2= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Regular.ttf");
        button = (Button) v.findViewById(R.id.button5);
        button.setTypeface(face);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                dbHandler.changeclassfrag(String.valueOf(classnumber));

                int z=cardid.size();
                if(temp.size()>=cardid.size())
                { int z2=temp.size()-z;
                    for(int q=0;q<z;q++)
                    {
                        cardid.set(q,temp.get(q));

                    }
                    if(z2>0) {
                        for (int q = 0; q < z2; q++) {
                            cardid.add(temp.get(z+q));
                        }
                    }
                }
                else
                {
                    int z2=z-temp.size();
                    for(int q=0;q<temp.size();q++)
                    {
                        cardid.set(q,temp.get(q));
                    }
                    for (int q = z-1; q >= z-z2; q--) {
                        cardid.remove(q);
                    }
                }

                List<Studentcardid> absent ;
                List<Studentcardid> present ;
                absent = new ArrayList<>();
                present = new ArrayList<>();
                int size=cardid.size();
                for(int j=0;j<size;j++)
                {
                    //  Toast.makeText(getContext(),cardid.get(j).getName()+" " +cardid.get(j).getStatus() + " " + size,Toast.LENGTH_SHORT).show();
                    Studentcardid newvalue=cardid.get(j);
                    if(cardid.get(j).getStatus().equals("ABSENT"))
                    {


                        absent.add(newvalue);
                    }
                    else if(cardid.get(j).getStatus().equals("PRESENT"))
                    {
                        present.add(newvalue);
                    }
                    //cardid.remove(j);

                }
                while (cardid.size() > 0) {
                    cardid.remove(cardid.size() - 1);
                }
                Collections.sort(absent, new Comparator<Studentcardid>() {
                    @Override
                    public int compare(Studentcardid studentcardid, Studentcardid t1) {
                        // Toast.makeText(mContext,studentcardid.getName() + " " + String.valueOf(studentcardid.getName().compareTo(t1.getName())) + " " +t1.getName(),Toast.LENGTH_SHORT).show();
                        return studentcardid.getName().compareToIgnoreCase(t1.getName());

                    }
                });

                Collections.sort(present, new Comparator<Studentcardid>() {
                    @Override
                    public int compare(Studentcardid studentcardid, Studentcardid t1) {
                        // Toast.makeText(mContext,studentcardid.getName() + " " + String.valueOf(studentcardid.getName().compareTo(t1.getName())) + " " +t1.getName(),Toast.LENGTH_SHORT).show();
                        return studentcardid.getName().compareToIgnoreCase(t1.getName());

                    }
                });
                for(int p=0;p<absent.size();p++)
                {
                    cardid.add(absent.get(p));

                }
                for(int q=0;q<present.size();q++)
                {
                    cardid.add(present.get(q));
                }



                myAdapter.notifyDataSetChanged();


            }

        });


        return v;


    }



    NumberPicker.OnValueChangeListener onValueChangeListener =

            new NumberPicker.OnValueChangeListener(){

                @Override
                public void onValueChange(NumberPicker numberPicker, int i, int i1) {


                    // type=String.valueOf(i1);
                    classnumber=i1;

                    //Toast.makeText(getContext(),routesort.get(i1-1),Toast.LENGTH_LONG).show() ;
                    temp = new ArrayList<>();
                    if(!dbHandler.valueforcard(String.valueOf(i1)).equals("%")) {

                        String Class1Student = dbHandler.valueforcard(String.valueOf(i1));
                        //Toast.makeText(getContext(),Class1Student,Toast.LENGTH_LONG).show() ;
                        String[] parts = Class1Student.split("#");

                        for (String a : parts) {
                            if(!a.equals("%"))
                            {  String[] eachstudent = a.split("&");
                                temp.add(new Studentcardid(eachstudent[0], eachstudent[1], eachstudent[2], "1", R.drawable.themartian));

                            }
                        }
                    }
                    else
                    {
                        temp.add(new Studentcardid("Empty", "ABSENT", "100", "1", R.drawable.themartian));
                    }

                }


            };
    NumberPicker.OnValueChangeListener onchangeListener=
            new NumberPicker.OnValueChangeListener(){
                @Override
                public void onValueChange(NumberPicker numberPicker, int i, int i1) {




                }


            };




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
                            JSONArray array = new JSONArray(response);
                            //  Toast.makeText(getContext(),String.valueOf(array),Toast.LENGTH_SHORT).show();
                            // dbHandler.deletetable();
                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);
                                // Toast.makeText(getContext(),String.valueOf(product),Toast.LENGTH_SHORT).show();
                                //   ADDstudent(product.getString("s_fname"),product.getString("s_mname"),product.getString("s_lname"),map.get(product.getString("class_id")),product.getString("class_id"),"4",product.getString("s_cardid"),product.getString("s_phone"),product.getString("sid"),product.getString("wp_usr_id"),product.getString("s_piuroute"),product.getString("s_drroute"),product.getString("s_status"));

                            }
                            map.clear();
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
    private void loadClass() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_SYNCCLASS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {
                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);
                                //Toast.makeText(getContext(),String.valueOf(product),Toast.LENGTH_SHORT).show();
                                map.put(product.getString("cid"),product.getString("c_numb"));
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
        loadStudents();

    }

//    void ADDstudent(String stuF,String stuM,String stuL,String stuC,String stuCid,String stuAge,String stuId,String stuph,String sid,String susrid,String pr,String dr,String status,String pic)
//    {
//        StudentsDB newstudent = new StudentsDB(stuF,stuM,stuL,map.get(stuCid),stuCid,stuAge,stuId,stuph,sid,susrid,pr,dr,status,pic);
//        //Toast.makeText(getContext(),stuCid+"  "+stuF+ " "+ map.get(stuCid),Toast.LENGTH_SHORT).show();
//        dbHandler.addProduct(newstudent);
//
//    }


//
//    private final Runnable m_Runnable = new Runnable()
//    {
//        public void run()
//
//        {
//            Toast.makeText(getContext(),"in runnable",Toast.LENGTH_SHORT).show();
//
//            Students.this.handler.postDelayed(m_Runnable, 5000);
//        }
//
//    };//runnable

    private void set_numberpicker_text_colour(NumberPicker number_picker) {
        final int count = number_picker.getChildCount();
        final int color = getResources().getColor(R.color.colorPrimaryDark);

        final Typeface fontTexto = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CabinSketch-Bold.ttf");


        for (int i = 0; i < count; i++) {
            View child = number_picker.getChildAt(i);

            try {
                Field wheelpaint_field = number_picker.getClass().getDeclaredField("mSelectorWheelPaint");
                wheelpaint_field.setAccessible(true);

                ((Paint) wheelpaint_field.get(number_picker)).setColor(color);
                ((Paint) wheelpaint_field.get(number_picker)).setTypeface(fontTexto);
                ((Paint) wheelpaint_field.get(number_picker)).setTextSize(30);

                ((EditText) child).setTextColor(color);
                ((EditText) child).setTypeface(fontTexto);
                ((EditText) child).setTextSize(18);
                number_picker.invalidate();
            } catch (NoSuchFieldException e) {
                Log.w("setNumberTextColor", e);
            } catch (IllegalAccessException e) {
                Log.w("setNickerTextColor", e);
            } catch (IllegalArgumentException e) {
                Log.w("setNumberPxtColor", e);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        Toast.makeText(getContext(),"resume",Toast.LENGTH_SHORT).show();

        temp = new ArrayList<>();
        if(!dbHandler.valueforcard(String.valueOf(classnumber)).equals("%")) {
            String Class1Student = dbHandler.valueforcard(String.valueOf(classnumber));
            String[] parts = Class1Student.split("#");

            for (String a : parts) {
                if(!a.equals("%"))
                {  String[] eachstudent = a.split("&");
                    temp.add(new Studentcardid(eachstudent[0], eachstudent[1], eachstudent[2],  String.valueOf(classnumber), R.drawable.themartian));
                }
            }
        }
        else
        {
            temp.add(new Studentcardid("Empty", "ABSENT", "100", "1", R.drawable.themartian));
        }
        int z=cardid.size();
        if(temp.size()>=cardid.size())
        { int z2=temp.size()-z;
            for(int q=0;q<z;q++)
            {
                cardid.set(q,temp.get(q));

            }
            if(z2>0) {
                for (int q = 0; q < z2; q++) {
                    cardid.add(temp.get(z+q));
                }
            }
        }
        else
        {
            int z2=z-temp.size();
            for(int q=0;q<temp.size();q++)
            {
                cardid.set(q,temp.get(q));
            }
            for (int q = z-1; q >= z-z2; q--) {
                cardid.remove(q);
            }
        }
        List<Studentcardid> absent ;
        List<Studentcardid> present ;
        absent = new ArrayList<>();
        present = new ArrayList<>();

        int size=cardid.size();
        for(int j=0;j<size;j++)
        {
            // Toast.makeText(getContext(),cardid.get(j).getName()+" " +cardid.get(j).getStatus() + " " + size,Toast.LENGTH_SHORT).show();
            Studentcardid newvalue=cardid.get(j);
            if(cardid.get(j).getStatus().equals("ABSENT"))
            {


                absent.add(newvalue);
            }
            else if(cardid.get(j).getStatus().equals("PRESENT"))
            {
                present.add(newvalue);
            }
            //cardid.remove(j);

        }
        while (cardid.size() > 0) {
            cardid.remove(cardid.size() - 1);
        }


        Collections.sort(absent, new Comparator<Studentcardid>() {
            @Override
            public int compare(Studentcardid studentcardid, Studentcardid t1) {
                // Toast.makeText(mContext,studentcardid.getName() + " " + String.valueOf(studentcardid.getName().compareTo(t1.getName())) + " " +t1.getName(),Toast.LENGTH_SHORT).show();
                return studentcardid.getName().compareToIgnoreCase(t1.getName());

            }
        });

        Collections.sort(present, new Comparator<Studentcardid>() {
            @Override
            public int compare(Studentcardid studentcardid, Studentcardid t1) {
                // Toast.makeText(mContext,studentcardid.getName() + " " + String.valueOf(studentcardid.getName().compareTo(t1.getName())) + " " +t1.getName(),Toast.LENGTH_SHORT).show();
                return studentcardid.getName().compareToIgnoreCase(t1.getName());

            }
        });
        for(int p=0;p<absent.size();p++)
        {
            cardid.add(absent.get(p));

        }
        for(int q=0;q<present.size();q++)
        {
            cardid.add(present.get(q));
        }
        myAdapter.notifyDataSetChanged();
    }


    @Override
    public void onStart() {
        super.onStart();
        //Toast.makeText(getContext(),"onstart",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
     // Toast.makeText(getContext(),"onpause",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        //Toast.makeText(getContext(),"onstop",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   Toast.makeText(getContext(),"oncreate",Toast.LENGTH_SHORT).show();
    }
}
