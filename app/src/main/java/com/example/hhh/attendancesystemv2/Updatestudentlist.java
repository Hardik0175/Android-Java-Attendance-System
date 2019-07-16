package com.example.hhh.attendancesystemv2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Updatestudentlist extends BroadcastReceiver {
    MyDBHandler dbHandlerupdate;
    @Override
    public void onReceive(Context context, Intent intent) {
        dbHandlerupdate = new MyDBHandler(context, null, null, 7);
        String value=intent.getExtras().getString("value");

            if(value.equals("Morning session starts"))
            {
                Toast.makeText(context,"(List is updated)-" + value,Toast.LENGTH_SHORT).show();
                dbHandlerupdate.makeinactiveall();
                dbHandlerupdate.updateactivestudent("(M)");
                dbHandlerupdate.setunivinaciveall();
                dbHandlerupdate.setunivactive("(M)");
                //Toast.makeText(context,dbHandlerupdate.testforuniv(),Toast.LENGTH_SHORT).show();
            }else if(value.equals("Morning session ends"))
            {
                dbHandlerupdate.setunivinaciveall();
                Toast.makeText(context,"(List is updated)-" + value,Toast.LENGTH_SHORT).show();
                dbHandlerupdate.makeinactiveall();
            }
            else if(value.equals("Afternoon session starts"))
            {
                Toast.makeText(context,"(List is updated)-" + value,Toast.LENGTH_SHORT).show();
                dbHandlerupdate.makeinactiveall();
                dbHandlerupdate.updateactivestudentslotsec("(A)");
                dbHandlerupdate.setunivinaciveall();
                dbHandlerupdate.setunivactive("(A)");
               // Toast.makeText(context,dbHandlerupdate.testforuniv(),Toast.LENGTH_SHORT).show();


            }
            else if(value.equals("Afternoon session ends"))
            {
                dbHandlerupdate.setunivinaciveall();
                Toast.makeText(context,"(List is updated)-" + value,Toast.LENGTH_SHORT).show();
                dbHandlerupdate.makeinactiveall();

            }
            else if(value.equals("Evening session starts"))
            {
                Toast.makeText(context,"(List is updated)-" + value,Toast.LENGTH_SHORT).show();
                dbHandlerupdate.makeinactiveall();
                dbHandlerupdate.updateactivestudentslotsec("(E)");
                dbHandlerupdate.setunivinaciveall();
                dbHandlerupdate.setunivactive("(E)");
              //  Toast.makeText(context,dbHandlerupdate.testforuniv(),Toast.LENGTH_SHORT).show();

            }
            else if(value.equals("Evening session ends"))
            {
                dbHandlerupdate.setunivinaciveall();
                Toast.makeText(context,"(List is updated)-" + value,Toast.LENGTH_SHORT).show();
                dbHandlerupdate.makeinactiveall();

            }

    }
}
