package com.example.hhh.attendancesystemv2;

/**
 * Created by hhh on 7/7/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
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
import java.util.List;

/**
 * Created by Aws on 28/01/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {


    private Context mContext ;
    private List<Studentcardid> mData ;
    String mylink = "http://studentshield.in/attendance/updateattendance.php?classid=";
    String Apimsg="https://enterprise.smsgupshup.com/GatewayAPI/rest?method=SendMessage&send_to=";
    String Apimsg2="&msg_type=TEXT&userid=2000137573&auth_scheme=plain&password=kapila8143&v=1.1&format=text";

    String combineApi="http://studentshield.in/attendance/apiandupdate.php?classid=";

    MyDBHandler dbHandlerList;

   // private SortedList<Studentcardid> newmdata;

    public RecyclerViewAdapter(final Context mContext, List<Studentcardid> mData) {

        this.mContext = mContext;

        this.mData = mData;

        dbHandlerList = new MyDBHandler(mContext, null, null, 7);

        List<Studentcardid> absent;

        List<Studentcardid> present;

        absent = new ArrayList<>();

        present = new ArrayList<>();

    }
    public void addAll(List<Studentcardid> cardid) {

        for (int i = 0; i < cardid.size(); i++) {
            mData.add(cardid.get(i));
        }
    }
    public void clear() {

        //remove items at end, to avoid unnecessary array shifting
        while (mData.size() > 0) {
            mData.remove(mData.size() - 1);
        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardveiw_item_book,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv_book_title.setText(mData.get(position).getName());


        String value=dbHandlerList.sidtopic(mData.get(position).getStudid());
        if(!value.equals("DEFAULT"))
        {
            Glide.with(mContext)
                    .load("http://studentshield.in/wp-content/uploads/2018/07/WIN_20180717_12_21_04_Pro.jpg")
                    .into(holder.img_book_thumbnail);
        }
        else
        {
            if(position%4==0)
            {
                Glide.with(mContext)
                        .load("http://studentshield.in/wp-content/uploads/2018/07/WIN_20180717_12_21_04_Pro.jpg")
                        .into(holder.img_book_thumbnail);
            }
            else if(position%4==1)
            {
                Glide.with(mContext)
                        .load("http://studentshield.in/wp-content/uploads/2018/08/download-1.jpg")
                        .into(holder.img_book_thumbnail);
            }
            else if(position%4==2)
            {
                Glide.with(mContext)
                        .load("http://studentshield.in/wp-content/uploads/2018/07/1.jpg")
                        .into(holder.img_book_thumbnail);
            }
            else if(position%4==3)
            {
                holder.img_book_thumbnail.setImageResource(mData.get(position).getThumbnail());
            }
        }


        if(mData.get(position).getStatus().equals("PRESENT"))
        {
            holder.simpleCheckedTextView.setCheckMarkDrawable(R.drawable.check_dark);
        }
        else {
            holder.simpleCheckedTextView.setCheckMarkDrawable(R.drawable.ic_clear_black_24dp);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!mData.get(position).getName().equals("Empty")) {


                    // Toast.makeText(mContext,mData.get(position).getStudid()+String.valueOf(mData.get(position).getClassnum()),Toast.LENGTH_SHORT).show();
                    if (mData.get(position).getStatus().equals("ABSENT")) {
                        dbHandlerList.updateAttendance2(mData.get(position).getStudid(), String.valueOf(mData.get(position).getClassnum()), "PRESENT");


                        String attd = dbHandlerList.sidtostring(mData.get(position).getStudid());

                        String splitattd[] = (attd).split("#");

                        Toast.makeText(mContext, splitattd[0].trim() + " is present " + "SMS is send to " + splitattd[1], Toast.LENGTH_LONG).show();
                        //new RecyclerViewAdapter.GetMethodDemo().execute(mylink + splitattd[2] + "&sid=" + splitattd[3]);
                        //new RecyclerViewAdapter.GetMethodDemo().execute(Apimsg + splitattd[1].trim() + "&msg=" + splitattd[0].trim() + "%20is%20present" + Apimsg2);

                        new RecyclerViewAdapter.GetMethodDemo().execute(combineApi+splitattd[2] + "&sid=" + splitattd[3] + "&sname=" + splitattd[0].trim()+ "&sphone=" + splitattd[1].trim());
                    }

                    mData.get(position).setStatus("PRESENT");

                    //newmdata.get(position).setName("aaa");

                    Studentcardid itemLabel = mData.get(position);
                    //Toast.makeText(mContext,String.valueOf(value),Toast.LENGTH_SHORT).show();
                    mData.remove(position);
                    notifyItemRemoved(position);

                    notifyItemRangeChanged(position, mData.size());
                    int x = mData.size();
                    mData.add(x, itemLabel);

                    notifyItemInserted(x);
                }
                else if(mData.get(position).getName().equals("Empty"))
                {
                    Toast.makeText(mContext, "Class is empty", Toast.LENGTH_LONG).show();

                }

            }
        });
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(!mData.get(position).getName().equals("Empty")) {
                Intent intent = new Intent(mContext,popcardstudent.class);
              //  Toast.makeText(mContext,dbHandlerList.valuetodisplayincard(mData.get(position).getStudid()),Toast.LENGTH_SHORT).show();
                // passing data to the book activity

                String value=dbHandlerList.sidtopic(mData.get(position).getStudid());
                intent.putExtra("Name",mData.get(position).getName());
                intent.putExtra("StudentID",mData.get(position).getStudid());
                intent.putExtra("Thumbnail",mData.get(position).getThumbnail());
                intent.putExtra("Thumbnail2",value);
                intent.putExtra("timer","no");

                intent.putExtra("class",mData.get(position).getClassnum());
                intent.putExtra("status",mData.get(position).getStatus());
                // start the activity
                  //  Toast.makeText(mContext,mData.get(position).getName()+"-"+mData.get(position).getStudid()+"-"+mData.get(position).getThumbnail()+"-"+value+"-"+mData.get(position).getClassnum()+"-"+mData.get(position).getStatus(),Toast.LENGTH_SHORT).show();
                mContext.startActivity(intent);
                }
                else if(mData.get(position).getName().equals("Empty"))
                {
                    Toast.makeText(mContext, "Class is empty", Toast.LENGTH_LONG).show();

                }

                return false;

            }
        });


    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_book_title;
        ImageView img_book_thumbnail;

        CardView cardView ;
        CheckedTextView simpleCheckedTextView;
        public MyViewHolder(View itemView) {
            super(itemView);
           simpleCheckedTextView = (CheckedTextView) itemView.findViewById(R.id.checkedbox);


            tv_book_title = (TextView) itemView.findViewById(R.id.book_title_id) ;
            img_book_thumbnail = (ImageView) itemView.findViewById(R.id.book_img_id);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
        //


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
