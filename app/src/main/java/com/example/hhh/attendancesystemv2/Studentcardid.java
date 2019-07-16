package com.example.hhh.attendancesystemv2;

/**
 * Created by hhh on 7/7/2018.
 */

public class Studentcardid {

private String Name;
private String Studid;
private String Status;
private String Classnum;
private int Thumbnail;

    public Studentcardid(String name, String status, String studid, String classnum, int thumbnail) {
        this.Name = name;
        this.Status = status;
        this.Studid = studid;
        this.Classnum = classnum;
        this.Thumbnail = thumbnail;
    }

    public String getName(){
        return Name;
    }
    public String getStudid(){
        return Studid;
    }
    public String getClassnum(){
        return Classnum;
    }
    public String getStatus()
    {
        return Status;
    }
    public int getThumbnail()
    {
        return Thumbnail;
    }
    public void setName(String studentname)
    {
        this.Name=studentname;
    }
    public void setStudid(String studentid)
    {
        this.Studid=studentid;
    }
    public void setClassnum(String studentclass)
    {
        this.Classnum=studentclass;
    }
    public void setStatus(String studentstatus)
    {
        this.Status=studentstatus;
    }
    public void setThumbnail(int studentthumbnail)
    {
        this.Thumbnail=studentthumbnail;
    }


}