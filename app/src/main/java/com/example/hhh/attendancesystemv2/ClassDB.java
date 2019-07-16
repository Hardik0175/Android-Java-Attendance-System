package com.example.hhh.attendancesystemv2;

/**
 * Created by hhh on 7/29/2018.
 */

public class ClassDB {
    private String id;
    private String cid;
    private String c_name;
    private String c_numb;
    private String c_slotentry;
    private String c_slotexit;
    private String c_msg;
    public ClassDB(String cid,String c_name,String c_numb,String c_slotentry,String c_slotexit,String c_msg)
    {
        this.cid=cid;
        this.c_name=c_name;
        this.c_numb=c_numb;
        this.c_slotentry=c_slotentry;
        this.c_slotexit=c_slotexit;
        this.c_msg=c_msg;
    }

    public String getCid()
    {
        return cid;
    }
    public String getC_name()
    {
        return c_name;
    }
    public String getC_numb()
    {
        return c_numb;
    }
    public String getC_slotentry()
    {
        return c_slotentry;
    }
    public String getC_slotexit()
    {
        return c_slotexit;
    }

    public String getC_msg()
    {
        return c_msg;
    }
    private void setCid(String cid)
    {
        this.cid=cid;
    }
    private void setC_name(String c_name)
    {
        this.c_name=c_name;
    }
    private void setC_numb(String c_numb)
    {
        this.c_numb=c_numb;
    }
    private void setC_slotentry(String c_slotentry)
    {
        this.c_slotentry=c_slotentry;
    }
    private void setC_slotexit(String c_slotexit)
    {
        this.c_slotexit=c_slotexit;
    }
    private void setC_msg(String c_msg)
    {
        this.c_msg=c_msg;
    }







}
