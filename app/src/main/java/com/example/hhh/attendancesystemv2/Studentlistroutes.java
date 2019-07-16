package com.example.hhh.attendancesystemv2;


import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.TypedArrayUtils;
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
 */
public class Studentlistroutes extends Fragment {



    private static final String URL_SYNCSTUDENTS = "http://studentshield.in/attendance/syncstudentdata.php";
    private static final String URL_SYNCCLASS= "http://studentshield.in/attendance/syncclassdata.php";

    Map<String, String> map = new HashMap<String, String>();

    private Bundle savedState = null;


    // String myStr = getArguments().getString("my_key");
    String named;
    EditText name;
    TextView classname;
    int classnumber;

    Button button;


    NumberPicker np;
    String type;


    ArrayList<String> itemList;
    ArrayList<String> routesort;

    List<Studentcardid> cardid ;
    List<Studentcardid> temp;
    List<Studentcardid> cardidclass1;
    List<Studentcardid> cardidclass2;
    List<Studentcardid> cardidclass3;
    List<Studentcardid> cardidclass4;
    List<Studentcardid> cardidclass5;
    List<Studentcardid> cardidclass6;
    List<Studentcardid> cardidclass7;
    List<Studentcardid> cardidclass8;
    List<Studentcardid> cardidclass9;
    List<Studentcardid> cardidclass10;

    MyDBHandler dbHandler;
    RecyclerViewAdapter myAdapter;


    String routevalue[];
   // String mStringArray;

    public Studentlistroutes() {


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null && savedState == null) {
            //savedState = savedInstanceState.getBundle("STAV");
        }
        if(savedState != null) {
            // vstup.setText(savedState.getCharSequence(App.VSTUP));
        }
        savedState = null;

        //loadClass();
        cardid = new ArrayList<>();
        routesort = new ArrayList<>();

//        cardidclass1 = new ArrayList<>();
//        cardidclass2 = new ArrayList<>();
//        cardidclass3 = new ArrayList<>();
//        cardidclass4 = new ArrayList<>();
//        cardidclass5 = new ArrayList<>();
//        cardidclass6 = new ArrayList<>();
//        cardidclass7 = new ArrayList<>();
//        cardidclass8 = new ArrayList<>();
//        cardidclass9 = new ArrayList<>();
//        cardidclass10 = new ArrayList<>();


        View v = inflater.inflate(R.layout.fragment_studentlistroutes, container, false);

        cardid.add(new Studentcardid("name","present","40","1",R.drawable.themartian));


        dbHandler = new MyDBHandler(getContext(), null, null, 7);


        String route=dbHandler.dynaimcroute(dbHandler.getactiveuniv());
        ArrayList<String>  mStringList= new ArrayList<String>();
        routevalue=route.split("#");
        for(int i=0;i<routevalue.length;i++){

            routesort.add(routevalue[i]);

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

        if(dbHandler.getactiveuniv().equals("(M)"))
        {
            Toast.makeText(getContext(),"GOOD Morning Students !!",Toast.LENGTH_LONG).show() ;
        }else if(dbHandler.getactiveuniv().equals("(A)"))
        {
            Toast.makeText(getContext(),"GOOD AFTERNOON Students !!",Toast.LENGTH_LONG).show() ;
        }
        else if(dbHandler.getactiveuniv().equals("(E)"))
        {
            Toast.makeText(getContext(),"GOOD EVENING Students !!",Toast.LENGTH_LONG).show() ;
        }
        else {
            Toast.makeText(getContext(),"NO SLOT !!",Toast.LENGTH_LONG).show() ;
        }




        String[] mStringArray = new String[routesort.size()];
        mStringArray = routesort.toArray(mStringArray);

        Typeface face= Typeface.createFromAsset(getActivity().getAssets(),"fonts/CabinSketch-Bold.ttf");
        NumberPicker np=(NumberPicker) v.findViewById(R.id.numberPicker);

        np.setMaxValue(mStringArray.length);
        np.setMinValue(1);
        np.setDisplayedValues(mStringArray);

        //Toast.makeText(getContext(),dbHandler.selectroutefrag(),Toast.LENGTH_LONG).show() ;

        set_numberpicker_text_colour(np);
        np.setValue(Integer.valueOf(dbHandler.selectroutefrag())+1);

        np.setOnValueChangedListener(onValueChangeListener);
        //np.setValue(Integer.valueOf(dbHandler.selectroutefrag()));
        final TextView clasname=(TextView) v.findViewById(R.id.classname);
        clasname.setTypeface(face);

        itemList=new ArrayList<String>(1);

        temp = new ArrayList<>();
        classnumber=1;
        if(mStringArray.length>Integer.valueOf(dbHandler.selectroutefrag()))
        {
         //   Toast.makeText(getContext(),dbHandler.selectroutefrag(),Toast.LENGTH_LONG).show() ;
            if(Arrays.asList(mStringArray).contains(mStringArray[Integer.valueOf(dbHandler.selectroutefrag())]))
            {
                classnumber=Integer.valueOf(dbHandler.selectroutefrag())+1;
           //     Toast.makeText(getContext(),"ffff",Toast.LENGTH_LONG).show() ;

            }
        }
        else
        {
             classnumber=1;
            dbHandler.changeroutefrag(String.valueOf(classnumber-1));

        }

        //classnumber=1;

        int initalvalue=Integer.valueOf(dbHandler.selectroutefrag());
        if(route!="") {
            //Toast.makeText(getContext(), route, Toast.LENGTH_LONG).show();
            if (!dbHandler.valuefordroute(routesort.get(Integer.valueOf(dbHandler.selectroutefrag())), dbHandler.getactiveuniv()).equals("%")) {

                //  Toast.makeText(getContext(),routesort.get(Integer.valueOf(dbHandler.selectroutefrag())),Toast.LENGTH_LONG).show() ;
                String Class1Student = dbHandler.valuefordroute(routesort.get(Integer.valueOf(dbHandler.selectroutefrag())), dbHandler.getactiveuniv());
                String[] parts = Class1Student.split("#");
                for (String a : parts) {
                    if (!a.equals("%")) {
                        String[] eachstudent = a.split("&");
                        // Toast.makeText(getContext(),eachstudent[0]+" - "+eachstudent[1],Toast.LENGTH_LONG).show() ;
                        temp.add(new Studentcardid(eachstudent[0], eachstudent[1], eachstudent[2], "1", R.drawable.themartian));
                    }
                }
            }
        }
        else
        {
            temp.add(new Studentcardid("Empty", "ABSENT", "100", "1", R.drawable.themartian));
        }



        final RecyclerView myrv = (RecyclerView) v.findViewById(R.id.recyclerview_id);
        myAdapter = new RecyclerViewAdapter(getContext(),cardid);
        myrv.setLayoutManager(new GridLayoutManager(getContext(),3));
        myrv.setAdapter(myAdapter);

        Typeface face2= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Regular.ttf");
        button = (Button) v.findViewById(R.id.button5);
        button.setTypeface(face);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
             //   dbHandler.selectroutefrag()

            //Toast.makeText(getContext(),"-"+routesort.get(classnumber-1)+"-"+String.valueOf(classnumber-1),Toast.LENGTH_LONG).show() ;
            dbHandler.changeroutefrag(String.valueOf(classnumber-1));
              // Toast.makeText(getContext(),dbHandler.selectroutefrag(),Toast.LENGTH_LONG).show() ;
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

        });


        return v;


    }



    NumberPicker.OnValueChangeListener onValueChangeListener =

            new NumberPicker.OnValueChangeListener(){
                @Override
                public void onValueChange(NumberPicker numberPicker, int i, int i1) {

                    classnumber=i1;


                   //Toast.makeText(getContext(),routesort.get(i1-1),Toast.LENGTH_LONG).show() ;
                    temp = new ArrayList<>();
                    if(!dbHandler.valuefordroute(routesort.get(i1-1),dbHandler.getactiveuniv()).equals("%")) {

                        String Class1Student = dbHandler.valuefordroute(routesort.get(i1-1),dbHandler.getactiveuniv());
                     //   dbHandler.changeroutefrag(Class1Student);
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





    private void loadStudents() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_SYNCSTUDENTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {

                                JSONObject product = array.getJSONObject(i);

                            }
                            map.clear();


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

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_SYNCCLASS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {

                                JSONObject product = array.getJSONObject(i);

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

        Volley.newRequestQueue(getContext()).add(stringRequest);
        loadStudents();

    }




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
      //  Toast.makeText(getContext(),"resume",Toast.LENGTH_SHORT).show();


        temp = new ArrayList<>();
        if(!dbHandler.valuefordroute(routesort.get(classnumber-1),dbHandler.getactiveuniv()).equals("%")) {
            String Class1Student = dbHandler.valuefordroute(routesort.get(classnumber-1),dbHandler.getactiveuniv());
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
}
