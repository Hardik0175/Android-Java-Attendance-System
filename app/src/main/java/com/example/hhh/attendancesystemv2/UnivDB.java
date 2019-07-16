package com.example.hhh.attendancesystemv2;

/**
 * Created by hhh on 7/29/2018.
 */

public class UnivDB {
    private String id;
    private String uid;
    private String slotname;
    private String begintime;
    private String endtime;

    public UnivDB(String uid,String slotname,String begintime,String endtime)
    {
        this.uid=uid;
        this.slotname=slotname;
        this.begintime=begintime;
        this.endtime=endtime;
    }


    public String getUid()
    {
        return uid;
    }
    public String getSlotname()
    {
        return slotname;
    }
    public String getBegintime()
    {
        return begintime;
    }
    public String getEndtime()
    {
        return endtime;
    }



    public void setUid(String uid)
    {
        this.uid=uid;
    }
    public void setSlotname(String slotname)
    {
        this.slotname=slotname;
    }
    public void setBegintime(String begintime)
    {
        this.begintime=begintime;
    }
    public void setEndtime(String endtime)
    {
        this.endtime=endtime;
    }

}
