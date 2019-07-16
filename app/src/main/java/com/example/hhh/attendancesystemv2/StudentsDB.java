package com.example.hhh.attendancesystemv2;

/**
 * Created by hhh on 5/30/2018.
 */

public class StudentsDB {

    private int _id;
    private String _Sid;
    private String _Susrid;
    private String _Studentname;
    private String _Studentmiddlename;
    private String _Studentlastname;
    private String _Studentclass;
    private String _Studentclassid;
    private String _Studentid;
    private String _Studentage;
    private String _Studentphone;
    private String _pickuproute;
    private String _droproute;
    private String _Studentstatus;
    private String _Studentpic;
    private String _Studentfirstslot;
    private String _Studentsecondslot;





    public StudentsDB(String StudentName,String StudentMiddleName,String StudentLastName,String StudentClass,String StudentClassid,String StudentAge,String StudentId,String Studentphone,String Sid,String Studentuserid,String Pickuproute,String Droproute,String studentstatus,String Studentpic,String Studentfirstslot,String Studentsecondslot) {

        this._Studentname = StudentName;
        this._Studentmiddlename = StudentMiddleName;
        this._Studentlastname = StudentLastName;
        this._Studentclass = StudentClass;
        this._Studentclassid= StudentClassid;
        this._Studentage = StudentAge;
        this._Studentid = StudentId;
        this._Studentphone = Studentphone;
        this._Sid = Sid;
        this._Susrid = Studentuserid;
        this._pickuproute = Pickuproute;
        this._droproute = Droproute;
        this._Studentstatus = studentstatus;
        this._Studentpic =Studentpic;
        this._Studentfirstslot=Studentfirstslot;
        this._Studentsecondslot=Studentsecondslot;

    }

    public int get_id() {
        return _id;
    }


    public void set_id(int _id) {
        this._id = _id;
    }



    public void set_Studentstatus(String _Studentstatus) {
        this._Studentstatus = _Studentstatus;
    }





    public void set_Studentclass(String _Studentclass) {
        this._Studentclass = _Studentclass;
    }



    public void set_Studentname(String _Studentname) {
        this._Studentname = _Studentname;
    }



    public void set_Studentage(String _Studentage) {
        this._Studentage = _Studentage;
    }



    public void set_Studentid(String _Studentid) {
        this._Studentid = _Studentid;
    }

    public void set_Studentphone(String _Studentphone) {
        this._Studentphone = _Studentphone;
    }






    public void set_Studentmiddlename(String _Studentmiddlename)
    {
        this._Studentmiddlename=_Studentmiddlename;
    }

    public void set_Studentlastname(String _Studentlastname)
    {
     this._Studentlastname=_Studentlastname;
    }

    public void set_Sid(String _Sid)
    {
        this._Sid=_Sid;
    }

    public void set_Susrid(String _Susrid)
    {
        this._Susrid=_Susrid;
    }

    public void set_droproute(String _droproute)
    {
        this._droproute=_droproute;
    }


    public void set_pickuproute(String _pickuproute)
    {
        this._pickuproute=_pickuproute;
    }


    public void set_Studentclassid(String _Studentclassid)
    {
        this._Studentclassid=_Studentclassid;
    }
    public void set_Studentpic(String _setudentpic)
    {
        this._Studentpic=_setudentpic;
    }
    public void set_Studentfirstslot(String _Studentfirstslot)
    {
        this._Studentfirstslot=_Studentfirstslot;
    }
    public void set_Studentsecondslot(String _Studentsecondslot)
    {
        this._Studentsecondslot=_Studentsecondslot;
    }



    public String get_Studentname() {
        return _Studentname;
    }
    public String get_Studentmiddlename()
    {
        return _Studentmiddlename;
    }
    public String get_Studentlastname()
    {
        return _Studentlastname;
    }
    public String get_Sid()
    {
        return _Sid;
    }
    public String get_Susrid()
    {
        return _Susrid;
    }
    public String get_droproute()
    {
        return _droproute;
    }
    public String get_pickuproute()
    {
        return _pickuproute;
    }
    public String get_Studentclassid()
    {
        return _Studentclassid;
    }
    public String get_Studentstatus() {
        return _Studentstatus;
    }
    public String get_Studentclass() {
        return _Studentclass;
    }

    public String get_Studentage() {
        return _Studentage;
    }
    public String get_Studentid() {
        return _Studentid;
    }
    public String get_Studentphone() {
        return _Studentphone;
    }
    public String get_Studentpic() {
        return _Studentpic;
    }
    public String get_Studentfirstslot() {
        return _Studentfirstslot;
    }
    public String get_Studentsecondslot() {
        return _Studentsecondslot;
    }




}