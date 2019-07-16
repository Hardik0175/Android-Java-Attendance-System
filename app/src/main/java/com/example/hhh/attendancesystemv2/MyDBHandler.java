package com.example.hhh.attendancesystemv2;

/**
 * Created by hhh on 5/30/2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 7;

    private static final String DATABASE_NAME = "studentDB.db";


    //Variables Students DB
    public static final String TABLE_PRODUCTS = "Students";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_STUDENTNAME = "studentname";
    public static final String COLUMN_STUDENTMIDDLENAME = "studentmiddlename";
    public static final String COLUMN_STUDENTLASTNAME = "studentlastname";
    public static final String COLUMN_SID = "sid";
    public static final String COLUMN_USR_ID = "s_usr_id";
    public static final String COLUMN_PICKUPROUTE= "s_piuroute";
    public static final String COLUMN_DROPROUTE = "s_drroute";
    public static final String COLUMN_STUDENTCLASS = "studentclass";
    public static final String COLUMN_STUDENTCLASSID = "studentclassid";
    public static final String COLUMN_STUDENTAGE = "studentage";
    public static final String COLUMN_STUDENTCARDID = "studentcardid";
    public static final String COLUMN_STUDENTPHONE = "studentphone";
    public static final String COLUMN_STUDENTSTATUS = "studentstatus";
    public static final String COLUMN_STUDENTPIC = "studentpic";
    public static final String COLUMN_STUDENTFIRSTSLOT = "studententry";
    public static final String COLUMN_STUDENTSECONDSLOT = "studentexit";
    public static final String COLUMN_STUDENTACTIVE = "studentactive";


    //Variables Routes DB
    public static final String TABLE_PRODUCTS_ROUTES = "Routes";
    public static final String COLUMN_ID_ROUTES = "_id";
    public static final String COLUMN_ROUTENAME = "routename";
    public static final String COLUMN_ROUTESTARTTIME = "routestarttime";
    public static final String COLUMN_ROUTEENDTIME = "routeendtime";
    public static final String COLUMN_ROUTETYPE = "routetype";
    public static final String COLUMN_ROUTEMESSAGE = "routemessage";
    public static final String COLUMN_ROUTEID = "routeid";

    //Variables Class DB
    public static final String TABLE_PRODUCTS_CLASS = "Class";
    public static final String COLUMN_ID_CLASS = "_id";
    public static final String COLUMN_CID = "cid";
    public static final String COLUMN_CLASSNAME = "classname";
    public static final String COLUMN_CLASSNUMB = "classnumb";
    public static final String COLUMN_CLASSENTRY = "classentry";
    public static final String COLUMN_CLASSEXIT= "classexit";
    public static final String COLUMN_CLASSMSG = "classmsg";

    //Variables univ DB
    public static final String TABLE_PRODUCTS_UNIV= "Univ";
    public static final String COLUMN_ID_UNI = "_id";
    public static final String COLUMN_UID= "uid";
    public static final String COLUMN_UNIVNAME = "univname";
    public static final String COLUMN_UNIVBEGINTIME = "begintime";
    public static final String COLUMN_UNIVENDTIME = "endtime";
    public static final String COLUMN_UNIVESTATUS = "status";


    //Fragements table
    public static final String TABLE_FRAGMENT= "frag";
    public static final String COLUMN_FRAGMAIN="fragmain";
    public static final String COLUMN_CLASSFRAG="classfrag";
    public static final String COLUMN_ROUTEFRAG="routefrag";
    public static final String COLUMN_GATEFRAG ="gatefrag";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_PRODUCTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_STUDENTNAME + " TEXT, " +
                COLUMN_STUDENTMIDDLENAME + " TEXT, " +
                COLUMN_STUDENTLASTNAME+ " TEXT, " +
                COLUMN_STUDENTCLASS + " TEXT, " +
                COLUMN_STUDENTCLASSID + " TEXT, " +
                COLUMN_STUDENTAGE + " TEXT, " +
                COLUMN_STUDENTCARDID + " TEXT, " +
                COLUMN_STUDENTPHONE+ " TEXT, " +
                COLUMN_SID+ " TEXT, " +
                COLUMN_USR_ID+ " TEXT, " +
                COLUMN_PICKUPROUTE+ " TEXT, " +
                COLUMN_DROPROUTE+ " TEXT, " +
                COLUMN_STUDENTSTATUS+ " TEXT, " +
                COLUMN_STUDENTPIC+ " TEXT, " +
                COLUMN_STUDENTFIRSTSLOT+ " TEXT, " +
                COLUMN_STUDENTSECONDSLOT+ " TEXT, " +
                COLUMN_STUDENTACTIVE+ " TEXT " +
                 ");";

        String queryroute = "CREATE TABLE " + TABLE_PRODUCTS_ROUTES + "(" +
                COLUMN_ID_ROUTES + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ROUTEID + " TEXT, " +
                COLUMN_ROUTENAME + " TEXT, " +
                COLUMN_ROUTESTARTTIME + " TEXT, " +
                COLUMN_ROUTEENDTIME+ " TEXT, " +
                COLUMN_ROUTETYPE + " TEXT, " +
                COLUMN_ROUTEMESSAGE + " TEXT " +
                ");";

        String queryclass = "CREATE TABLE " + TABLE_PRODUCTS_CLASS + "(" +
                COLUMN_ID_CLASS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CID + " TEXT, " +
                COLUMN_CLASSNAME + " TEXT, " +
                COLUMN_CLASSNUMB + " TEXT, " +
                COLUMN_CLASSENTRY+ " TEXT, " +
                COLUMN_CLASSEXIT + " TEXT, " +
                COLUMN_CLASSMSG + " TEXT " +
                ");";

        String queryuniv = "CREATE TABLE " + TABLE_PRODUCTS_UNIV + "(" +
                COLUMN_ID_UNI + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_UID + " TEXT, " +
                COLUMN_UNIVNAME + " TEXT, " +
                COLUMN_UNIVBEGINTIME + " TEXT, " +
                COLUMN_UNIVENDTIME+ " TEXT, " +
                COLUMN_UNIVESTATUS+" TEXT " +
                ");";

        String queryfrag = "CREATE TABLE " + TABLE_FRAGMENT + "(" +
                COLUMN_FRAGMAIN+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CLASSFRAG + " TEXT, " +
                COLUMN_ROUTEFRAG + " TEXT, " +
                COLUMN_GATEFRAG + " TEXT " +
                ");";

        db.execSQL(query);
        db.execSQL(queryroute);
        db.execSQL(queryclass);
        db.execSQL(queryuniv);
        db.execSQL(queryfrag);

       // addfrag();

    }

    //Lesson 51
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRAGMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS_ROUTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS_UNIV);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS_CLASS);


        onCreate(db);
    }

    public void addfrag()
    {
        ContentValues values=new ContentValues();
        values.put(COLUMN_CLASSFRAG,"1");
        values.put(COLUMN_ROUTEFRAG,"0");
        values.put(COLUMN_GATEFRAG,"1");
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_FRAGMENT, null, values);
        db.close();
    }

    public String selectclassfrag()
    {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String main="1";
        String query = "SELECT * FROM " + TABLE_FRAGMENT + " WHERE " + COLUMN_FRAGMAIN + "=\"" + main + "\";";
        //Cursor points to a location in your result
        Cursor recordSet = db.rawQuery(query, null);
        recordSet.moveToFirst();
        while (!recordSet.isAfterLast()) {
            if (recordSet.getString(recordSet.getColumnIndex("classfrag")) != null) {
                dbString = recordSet.getString(recordSet.getColumnIndex("classfrag"));
            }
            recordSet.moveToNext();
        }
        db.close();
        return dbString;
    }

    public void changeclassfrag(String classnumber)
    {
        String main="1";
        String queryupdate = "UPDATE " + TABLE_FRAGMENT + " SET " +  COLUMN_CLASSFRAG + "=" + classnumber +" WHERE " + COLUMN_FRAGMAIN + "=\"" + main + "\";";
        SQLiteDatabase db1 = getWritableDatabase();
        db1.execSQL(queryupdate);

    }


    public String selectroutefrag()
    {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String main="1";
        String query = "SELECT * FROM " + TABLE_FRAGMENT + " WHERE " + COLUMN_FRAGMAIN + "=\"" + main + "\";";
        //Cursor points to a location in your result
        Cursor recordSet = db.rawQuery(query, null);
        recordSet.moveToFirst();
        while (!recordSet.isAfterLast()) {
            if (recordSet.getString(recordSet.getColumnIndex("routefrag")) != null) {
                dbString = recordSet.getString(recordSet.getColumnIndex("routefrag"));
            }
            recordSet.moveToNext();
        }
        db.close();
        return dbString;

    }

    public void changeroutefrag(String routenuber)
    {
        String main="1";
        String queryupdate = "UPDATE " + TABLE_FRAGMENT + " SET " +  COLUMN_ROUTEFRAG + "=" + routenuber +" WHERE " + COLUMN_FRAGMAIN + "=\"" + main + "\";";
        SQLiteDatabase db1 = getWritableDatabase();
        db1.execSQL(queryupdate);
    }

    public String selectgatefrag(String value)
    {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_FRAGMENT + " WHERE " + COLUMN_FRAGMAIN + "=\"" + value + "\";";

        //Cursor points to a location in your result
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("begintime")) != null) {
                dbString = recordSet.getString(recordSet.getColumnIndex("begintime"));
            }
            recordSet.moveToNext();
        }
        db.close();
        return dbString;
    }




    //Add a new row to the database
    public void addProduct(StudentsDB Student) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_STUDENTNAME, Student.get_Studentname());
        values.put(COLUMN_STUDENTMIDDLENAME, Student.get_Studentmiddlename());
        values.put(COLUMN_STUDENTLASTNAME, Student.get_Studentlastname());
        values.put(COLUMN_STUDENTCLASS, Student.get_Studentclass());
        values.put(COLUMN_STUDENTCLASSID, Student.get_Studentclassid());
        values.put(COLUMN_STUDENTAGE, Student.get_Studentage());
        values.put(COLUMN_STUDENTCARDID, Student.get_Studentid());
        values.put(COLUMN_STUDENTPHONE, Student.get_Studentphone());
        values.put(COLUMN_SID, Student.get_Sid());
        values.put(COLUMN_USR_ID, Student.get_Susrid());
        values.put(COLUMN_PICKUPROUTE, Student.get_pickuproute());
        values.put(COLUMN_DROPROUTE, Student.get_droproute());
        values.put(COLUMN_STUDENTSTATUS, Student.get_Studentstatus());
        values.put(COLUMN_STUDENTPIC, Student.get_Studentpic());
        values.put(COLUMN_STUDENTFIRSTSLOT,Student.get_Studentfirstslot());
        values.put(COLUMN_STUDENTSECONDSLOT,Student.get_Studentsecondslot());
        values.put(COLUMN_STUDENTACTIVE,"no");
        deleteProduct(Student.get_Sid());
        SQLiteDatabase db = getWritableDatabase();
            db.insert(TABLE_PRODUCTS, null, values);
            db.close();
    }

    public void addProductRoutes(RouteDB routes) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROUTEID, routes.get_id_route());
        values.put(COLUMN_ROUTENAME, routes.get_Routename());
        values.put(COLUMN_ROUTESTARTTIME, routes.get_Routestarttime());
        values.put(COLUMN_ROUTEENDTIME, routes.get_Routeendtime());
        values.put(COLUMN_ROUTETYPE, routes.get_Routetype());
        values.put(COLUMN_ROUTEMESSAGE, routes.get_Routemessage());
        deleteProduct(routes.get_Routename());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_PRODUCTS_ROUTES, null, values);
        db.close();

    }
    public void addProductclass(ClassDB classes) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CID, classes.getCid());
        values.put(COLUMN_CLASSNAME, classes.getC_name());
        values.put(COLUMN_CLASSNUMB, classes.getC_numb());
        values.put(COLUMN_CLASSENTRY, classes.getC_slotentry());
        values.put(COLUMN_CLASSEXIT, classes.getC_slotexit());
        values.put(COLUMN_CLASSMSG, classes.getC_msg());
        deleteProduct(classes.getCid());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_PRODUCTS_CLASS, null, values);
        db.close();

    }

    public void addProductuniv(UnivDB univ) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_UID, univ.getUid());
        values.put(COLUMN_UNIVNAME, univ.getSlotname());
        values.put(COLUMN_UNIVBEGINTIME, univ.getBegintime());
        values.put(COLUMN_UNIVENDTIME, univ.getEndtime());
        values.put(COLUMN_UNIVESTATUS,"inactive");
        //deletetuniv(univ.getSlotname());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_PRODUCTS_UNIV, null, values);
        db.close();

    }
    public String testforuniv(){

        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_PRODUCTS_UNIV + " WHERE 1";

        //Cursor points to a location in your result
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("univname")) != null) {
                dbString += recordSet.getString(recordSet.getColumnIndex("univname"));
                dbString += "~";
            }
            recordSet.moveToNext();
        }
        db.close();
        return dbString;
    }


    public void setunivactive(String value)
    {
        String queryupdate = "UPDATE " + TABLE_PRODUCTS_UNIV + " SET " +  COLUMN_UNIVESTATUS + "=\"" + "active" +"\" WHERE " +COLUMN_UNIVNAME + "=\"" + value +"\";" ;
        SQLiteDatabase db1 = getWritableDatabase();
        db1.execSQL(queryupdate);
    }
//    public void setunivinaciveall()
//    {
//
//    }
        public void setunivinaciveall()
        {
            String queryupdate = "UPDATE " + TABLE_PRODUCTS_UNIV + " SET " +  COLUMN_UNIVESTATUS + "=\"" + "inactive" +"\" WHERE 1" ;
            SQLiteDatabase db1 = getWritableDatabase();
            db1.execSQL(queryupdate);
        }
    public String getactiveuniv(){

        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_PRODUCTS_UNIV + " WHERE " +COLUMN_UNIVESTATUS + "=\"" +"active" +"\";" ;

        //Cursor points to a location in your result
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("univname")) != null) {
                dbString += recordSet.getString(recordSet.getColumnIndex("univname"));

            }
            recordSet.moveToNext();
        }
        db.close();
        return dbString;
    }

    public String stringforroute(){

        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_PRODUCTS_ROUTES + " WHERE 1";

        //Cursor points to a location in your result
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("routemessage")) != null) {
                dbString += recordSet.getString(recordSet.getColumnIndex("routemessage"));
                dbString += "~";
            }
            recordSet.moveToNext();
        }
        db.close();
        return dbString;
    }
    public String gettimingsbegintime(String value){

        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_PRODUCTS_UNIV + " WHERE " + COLUMN_UNIVNAME + "=\"" + value + "\";";

        //Cursor points to a location in your result
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("begintime")) != null) {
                dbString = recordSet.getString(recordSet.getColumnIndex("begintime"));
            }
            recordSet.moveToNext();
        }
        db.close();
        return dbString;
    }
    public String gettimingsendtime(String value){

        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_PRODUCTS_UNIV + " WHERE " + COLUMN_UNIVNAME + "=\"" + value + "\";";

        //Cursor points to a location in your result
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("endtime")) != null) {
                dbString = recordSet.getString(recordSet.getColumnIndex("endtime"));

            }
            recordSet.moveToNext();
        }
        db.close();
        return dbString;
    }


    public void deletetuniv(String productName){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PRODUCTS_UNIV + " WHERE " + COLUMN_UNIVNAME + "=\"" + productName + "\";");
    }

    //Delete a product from the database
    public void deleteProduct(String productName){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_SID + "=\"" + productName + "\";");
    }

    public void deletetable()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PRODUCTS + " WHERE 1");
    }

    public void deletetableuniv()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PRODUCTS_UNIV + " WHERE 1");
    }

    public void deletetableroutes()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PRODUCTS_ROUTES + " WHERE 1");
    }

    // this is goint in record_TextView in the Main activity.

    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_STUDENTCLASS + "=\"" + "7" + "\";";

        //Cursor points to a location in your result
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("studentname")) != null) {
                dbString += recordSet.getString(recordSet.getColumnIndex("studentname"));
                //dbString += "\n";
            }
            recordSet.moveToNext();
        }
        db.close();
        return dbString;
    }
    public String sidtopic(String id){

        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " +COLUMN_SID + "=\"" + id + "\";";

        //Cursor points to a location in your result
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("studentpic")) != null) {
                dbString = recordSet.getString(recordSet.getColumnIndex("studentpic"));
                //dbString += "\n";
            }
            recordSet.moveToNext();
        }
        db.close();
        return dbString;
    }



    public String class1()
    {
        String dbString = "";
        SQLiteDatabase db1 = getWritableDatabase();
        String queryclass1 = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_STUDENTCLASS + "=\"" + "1" + "\";";
        Cursor recordSet = db1.rawQuery(queryclass1, null);
        //Move to the first row in your results
        recordSet.moveToFirst();
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("studentname")) != null) {
                dbString += recordSet.getString(recordSet.getColumnIndex("studentname"));
                dbString += "#";
            }
            recordSet.moveToNext();
        }

        db1.close();
        return dbString;
    }

    public String class2()
    {
        String dbString = "";
        SQLiteDatabase db1 = getWritableDatabase();
        String queryclass1 = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_STUDENTCLASS + "=\"" + "2" + "\";";
        Cursor recordSet = db1.rawQuery(queryclass1, null);
        //Move to the first row in your results
        recordSet.moveToFirst();
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("studentname")) != null) {
                dbString += recordSet.getString(recordSet.getColumnIndex("studentname"));
                dbString += "#";
            }
            recordSet.moveToNext();
        }

        db1.close();
        return dbString;
    }
    public String class3()
    {
        String dbString = "";
        SQLiteDatabase db1 = getWritableDatabase();
        String queryclass1 = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_STUDENTCLASS + "=\"" + "3" + "\";";
        Cursor recordSet = db1.rawQuery(queryclass1, null);
        //Move to the first row in your results
        recordSet.moveToFirst();
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("studentname")) != null) {
                dbString += recordSet.getString(recordSet.getColumnIndex("studentname"));
                dbString += "#";
            }
            recordSet.moveToNext();
        }

        db1.close();
        return dbString;
    }


    public String class4()
    {
        String dbString = "";
        SQLiteDatabase db1 = getWritableDatabase();
        String queryclass1 = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_STUDENTCLASS + "=\"" + "4" + "\";";
        Cursor recordSet = db1.rawQuery(queryclass1, null);
        //Move to the first row in your results
        recordSet.moveToFirst();
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("studentname")) != null) {
                dbString += recordSet.getString(recordSet.getColumnIndex("studentname"));
                dbString += "#";
            }
            recordSet.moveToNext();
        }

        db1.close();
        return dbString;
    }
    public String class5()
    {
        String dbString = "";
        SQLiteDatabase db1 = getWritableDatabase();
        String queryclass1 = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_STUDENTCLASS + "=\"" + "5" + "\";";
        Cursor recordSet = db1.rawQuery(queryclass1, null);
        //Move to the first row in your results
        recordSet.moveToFirst();
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("studentname")) != null) {
                dbString += recordSet.getString(recordSet.getColumnIndex("studentname"));
                dbString += "#";
            }
            recordSet.moveToNext();
        }

        db1.close();
        return dbString;
    }
    public String class6()
    {
        String dbString = "";
        SQLiteDatabase db1 = getWritableDatabase();
        String queryclass1 = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_STUDENTCLASS + "=\"" + "6" + "\";";
        Cursor recordSet = db1.rawQuery(queryclass1, null);
        //Move to the first row in your results
        recordSet.moveToFirst();
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("studentname")) != null) {
                dbString += recordSet.getString(recordSet.getColumnIndex("studentname"));
                dbString += "#";
            }
            recordSet.moveToNext();
        }

        db1.close();
        return dbString;
    }
    public String class7()
    {
        String dbString = "";
        SQLiteDatabase db1 = getWritableDatabase();
        String queryclass1 = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_STUDENTCLASS + "=\"" + "7" + "\";";
        Cursor recordSet = db1.rawQuery(queryclass1, null);
        //Move to the first row in your results
        recordSet.moveToFirst();
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("studentname")) != null) {
                dbString += recordSet.getString(recordSet.getColumnIndex("studentname"));
                dbString += "#";
            }
            recordSet.moveToNext();
        }

        db1.close();
        return dbString;
    }
    public String class8()
    {
        String dbString = "";
        SQLiteDatabase db1 = getWritableDatabase();
        String queryclass1 = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_STUDENTCLASS + "=\"" + "8" + "\";";
        Cursor recordSet = db1.rawQuery(queryclass1, null);
        //Move to the first row in your results
        recordSet.moveToFirst();
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("studentname")) != null) {
                dbString += recordSet.getString(recordSet.getColumnIndex("studentname"));
                dbString += "#";
            }
            recordSet.moveToNext();
        }

        db1.close();
        return dbString;
    }
    public String class9()
    {
        String dbString = "";
        SQLiteDatabase db1 = getWritableDatabase();
        String queryclass1 = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_STUDENTCLASS + "=\"" + "9" + "\";";
        Cursor recordSet = db1.rawQuery(queryclass1, null);
        //Move to the first row in your results
        recordSet.moveToFirst();
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("studentname")) != null) {
                dbString += recordSet.getString(recordSet.getColumnIndex("studentname"));
                dbString += "#";
            }
            recordSet.moveToNext();
        }

        db1.close();
        return dbString;
    }
    public String getworktime()
    {
        String dbString = "";
        SQLiteDatabase db1 = getWritableDatabase();
        String queryclass1 = "SELECT * FROM " + TABLE_PRODUCTS_UNIV + " WHERE 1";
        Cursor recordSet = db1.rawQuery(queryclass1, null);
        //Move to the first row in your results
        recordSet.moveToFirst();
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("univname")) != null) {
                dbString += recordSet.getString(recordSet.getColumnIndex("univname"));
                dbString += "#";
            }
            recordSet.moveToNext();
        }

        db1.close();
        return dbString;
    }

    public String class10()
    {
        String dbString = "";
        SQLiteDatabase db1 = getWritableDatabase();
        String queryclass1 = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_STUDENTCLASS + "=\"" + "10" + "\";";
        Cursor recordSet = db1.rawQuery(queryclass1, null);
        //Move to the first row in your results
        recordSet.moveToFirst();
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("studentname")) != null) {
                dbString += recordSet.getString(recordSet.getColumnIndex("studentname"));
                dbString += "#";
            }
            recordSet.moveToNext();
        }

        db1.close();
        return dbString;
    }


    public String idtostring(String id)
    {
        String suserid="";
        String classid="";
        String dbString = "";
        String phoneno="";

        SQLiteDatabase db1 = getWritableDatabase();
        String queryclass1 = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_STUDENTCARDID + "=\"" + id + "\";";
        Cursor recordSet = db1.rawQuery(queryclass1, null);
        //Move to the first row in your results
        recordSet.moveToFirst();
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("studentname")) != null) {
                dbString = recordSet.getString(recordSet.getColumnIndex("studentname"));
                phoneno = recordSet.getString(recordSet.getColumnIndex("studentphone"));
                classid = recordSet.getString(recordSet.getColumnIndex("studentclassid"));
                suserid = recordSet.getString(recordSet.getColumnIndex("s_usr_id"));
            }

            recordSet.moveToNext();
        }

        db1.close();
        return dbString+"#"+phoneno+"#"+classid+"#"+suserid;
    }

    public String usridtosid(String id)
    {
        String suid="";


        SQLiteDatabase db1 = getWritableDatabase();
        String queryclass1 = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_USR_ID + "=\"" + id + "\";";
        Cursor recordSet = db1.rawQuery(queryclass1, null);
        //Move to the first row in your results
        recordSet.moveToFirst();
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("sid")) != null) {
                suid = recordSet.getString(recordSet.getColumnIndex("sid"));
            }

            recordSet.moveToNext();
        }

        db1.close();
        return suid;
    }

    public String sidtostring(String id)
    {
        String suserid="";
        String classid="";
        String dbString = "";
        String phoneno="";

        SQLiteDatabase db1 = getWritableDatabase();
        String queryclass1 = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_SID + "=\"" + id + "\";";
        Cursor recordSet = db1.rawQuery(queryclass1, null);
        //Move to the first row in your results
        recordSet.moveToFirst();
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("studentname")) != null) {
                dbString = recordSet.getString(recordSet.getColumnIndex("studentname"));
                phoneno = recordSet.getString(recordSet.getColumnIndex("studentphone"));
                classid = recordSet.getString(recordSet.getColumnIndex("studentclassid"));
                suserid = recordSet.getString(recordSet.getColumnIndex("s_usr_id"));
            }

            recordSet.moveToNext();
        }

        db1.close();
        return dbString+"#"+phoneno+"#"+classid+"#"+suserid;
    }
    public String valueforcard(String classnum)
    {
        String Name="";
        String stuid="";
        String stustatus="";
        String allstudents="";
        SQLiteDatabase db1 = getWritableDatabase();
        String queryclass1 = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_STUDENTCLASS + "=\"" + classnum + "\";";
        Cursor recordSet = db1.rawQuery(queryclass1, null);
        //Move to the first row in your results
        recordSet.moveToFirst();
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("studentname")) != null) {
                Name = recordSet.getString(recordSet.getColumnIndex("studentname"));
                stuid = recordSet.getString(recordSet.getColumnIndex("sid"));
                stustatus = recordSet.getString(recordSet.getColumnIndex("studentstatus"));
                allstudents += Name + "&" + stustatus+ "&" + stuid;
                allstudents += "#";
            }


            recordSet.moveToNext();
        }

        db1.close();
        return allstudents+"%";
    }

    public String getstatus(String id)
    {
        String status="";

        SQLiteDatabase db1 = getWritableDatabase();
        String queryclass1 = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_STUDENTCARDID + "=\"" + id + "\";";
        Cursor recordSet = db1.rawQuery(queryclass1, null);
        //Move to the first row in your results
        recordSet.moveToFirst();
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("studentstatus")) != null) {
                status = recordSet.getString(recordSet.getColumnIndex("studentstatus"));

            }


            recordSet.moveToNext();
        }

        db1.close();
        return status;
    }

    public String activestudents(String classnum)
    {
        String Name="";
        String stuid="";
        String stustatus="";
        String allstudents="";
        String value="yes";
        SQLiteDatabase db1 = getWritableDatabase();
        String queryclass1 = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_STUDENTCLASS + "=\"" + classnum +"\" AND "   +  COLUMN_STUDENTACTIVE + "=\"" + value  +"\";" ;
     //   String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_STUDENTNAME + "=\"" + Studenname + "\" AND "   +  COLUMN_STUDENTCLASS + "=\"" + Studentclass +"\";" ;
        Cursor recordSet = db1.rawQuery(queryclass1, null);
        //Move to the first row in your results
        recordSet.moveToFirst();
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("studentname")) != null) {
                Name = recordSet.getString(recordSet.getColumnIndex("studentname"));
                stuid = recordSet.getString(recordSet.getColumnIndex("sid"));
                stustatus = recordSet.getString(recordSet.getColumnIndex("studentstatus"));
                allstudents += Name + "&" + stustatus+ "&" + stuid;
                allstudents += "#";
            }


            recordSet.moveToNext();
        }

        db1.close();
        return allstudents+"%";
    }

    public String valuefordroute(String route,String slot)
    {
        String Name = "";
        String stuid = "";
        String stustatus = "";
        String allstudents = "";
        SQLiteDatabase db1 = getWritableDatabase();
        if(slot.equals("(M)")) {


            String queryclass1 = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_PICKUPROUTE + "=\"" + route + "\";";
            Cursor recordSet = db1.rawQuery(queryclass1, null);
            //Move to the first row in your results
            recordSet.moveToFirst();
            while (!recordSet.isAfterLast()) {
                // null could happen if we used our empty constructor
                if (recordSet.getString(recordSet.getColumnIndex("studentname")) != null) {
                    Name = recordSet.getString(recordSet.getColumnIndex("studentname"));
                    stuid = recordSet.getString(recordSet.getColumnIndex("sid"));
                    stustatus = recordSet.getString(recordSet.getColumnIndex("studentstatus"));
                    allstudents += Name + "&" + stustatus + "&" + stuid;
                    allstudents += "#";
                }


                recordSet.moveToNext();
            }
        }
      //  String queryclass1 = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_STUDENTCLASS + "=\"" + classnum +"\" AND "   +  COLUMN_STUDENTACTIVE + "=\"" + value  +"\";" ;
        else {
            String queryclass1 = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_DROPROUTE + "=\"" + route +"\" AND "   +  COLUMN_STUDENTSECONDSLOT + "=\"" + slot  +"\";" ;
            Cursor recordSet = db1.rawQuery(queryclass1, null);
            //Move to the first row in your results
            recordSet.moveToFirst();
            while (!recordSet.isAfterLast()) {
                // null could happen if we used our empty constructor
                if (recordSet.getString(recordSet.getColumnIndex("studentname")) != null) {
                    Name = recordSet.getString(recordSet.getColumnIndex("studentname"));
                    stuid = recordSet.getString(recordSet.getColumnIndex("sid"));
                    stustatus = recordSet.getString(recordSet.getColumnIndex("studentstatus"));
                    allstudents += Name + "&" + stustatus + "&" + stuid;
                    allstudents += "#";
                }


                recordSet.moveToNext();
            }
        }

        db1.close();
        return allstudents+"%";
    }

    public String valuetodisplayincard(String sid)
    {
        String Name="";
        String LName="";
        String Classnum="";
        String sage="";
        String cardid="";
        String phone="";
        String stustatus="";
        String picroute="";
        String droproute="";
        String allstudents="";
        SQLiteDatabase db1 = getWritableDatabase();
        String queryclass1 = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_SID + "=\"" + sid + "\";";
        Cursor recordSet = db1.rawQuery(queryclass1, null);
        //Move to the first row in your results
        recordSet.moveToFirst();
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("studentname")) != null) {
                Name = recordSet.getString(recordSet.getColumnIndex("studentname"));
                LName = recordSet.getString(recordSet.getColumnIndex("studentlastname"));
                Classnum =recordSet.getString(recordSet.getColumnIndex("studentclass"));
                sage = recordSet.getString(recordSet.getColumnIndex("studentage"));
                cardid=recordSet.getString(recordSet.getColumnIndex("studentcardid"));
                phone=recordSet.getString(recordSet.getColumnIndex("studentphone"));
                stustatus=recordSet.getString(recordSet.getColumnIndex("studentstatus"));
                picroute=recordSet.getString(recordSet.getColumnIndex("s_piuroute"));
                droproute=recordSet.getString(recordSet.getColumnIndex("s_drroute"));
                allstudents = Name + "&" + LName+ "&" + Classnum+ "&" + sage+ "&" + cardid+ "&" + phone+ "&" + stustatus+ "&" + picroute+ "&" + droproute;

            }


            recordSet.moveToNext();
        }

        db1.close();
        return allstudents;
    }

    public void updateAttendance(String StudentRFID)
    {
        String queryupdate = "UPDATE " + TABLE_PRODUCTS + " SET " +  COLUMN_STUDENTSTATUS + "='PRESENT' WHERE " + COLUMN_STUDENTCARDID + "=\"" + StudentRFID + "\";";
        SQLiteDatabase db1 = getWritableDatabase();
        db1.execSQL(queryupdate);

    }
    public String Stustatus(String Studenname,String Studentclass)
    {

        SQLiteDatabase db1 = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_STUDENTNAME + "=\"" + Studenname + "\" AND "   +  COLUMN_STUDENTCLASS + "=\"" + Studentclass +"\";" ;
        String status="";
        Cursor recordSet = db1.rawQuery(query, null);
             recordSet.moveToFirst();
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("studentstatus")) != null) {
                status = recordSet.getString(recordSet.getColumnIndex("studentstatus"));
            }
            recordSet.moveToNext();
        }

        db1.close();
        return status;
    }


    public void updateAttendance2(String sid,String studentclass,String status)
    {
        String queryupdate = "UPDATE " + TABLE_PRODUCTS + " SET " +  COLUMN_STUDENTSTATUS + "=\"" + status +"\" WHERE " +COLUMN_SID + "=\"" + sid + "\" AND "   +  COLUMN_STUDENTCLASS + "=\"" + studentclass +"\";" ;
        SQLiteDatabase db1 = getWritableDatabase();
        db1.execSQL(queryupdate);

    }
    public void makeinactiveall()
    {
        String queryupdate = "UPDATE " + TABLE_PRODUCTS + " SET " +  COLUMN_STUDENTACTIVE + "=\"" + "no" +"\" WHERE 1" ;
        SQLiteDatabase db1 = getWritableDatabase();
        db1.execSQL(queryupdate);
    }
    public void updateactivestudent(String timing)
    {
        String queryupdate = "UPDATE " + TABLE_PRODUCTS + " SET " +  COLUMN_STUDENTACTIVE + "=\"" + "yes" +"\" WHERE " +COLUMN_STUDENTFIRSTSLOT + "=\"" + timing +"\";" ;
        SQLiteDatabase db1 = getWritableDatabase();
        db1.execSQL(queryupdate);
    }
    public void updateactivestudentslotsec(String timing)
    {
        String queryupdate = "UPDATE " + TABLE_PRODUCTS + " SET " +  COLUMN_STUDENTACTIVE + "=\"" + "yes" +"\" WHERE " +COLUMN_STUDENTSECONDSLOT + "=\"" + timing +"\";" ;
        SQLiteDatabase db1 = getWritableDatabase();
        db1.execSQL(queryupdate);
    }

    public  void clearlist()
    {
        SQLiteDatabase db1 = getWritableDatabase();
        String query ="SELECT * FROM " + TABLE_PRODUCTS + " WHERE 1";
        String queryupdate = "UPDATE " + TABLE_PRODUCTS + " SET " +  COLUMN_STUDENTSTATUS + "='-' WHERE 1";
        db1.execSQL(queryupdate);

    }
    public String route()
    {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE 1";

        //Cursor points to a location in your result
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();
        ArrayList<String> mylist;
        mylist =new ArrayList<>();
        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("s_piuroute")) != null && !recordSet.getString(recordSet.getColumnIndex("s_piuroute")).equals("null") && !recordSet.getString(recordSet.getColumnIndex("s_piuroute")).equals("")) {
                if(!mylist.contains(recordSet.getString(recordSet.getColumnIndex("s_piuroute"))))
                {
                    dbString += recordSet.getString(recordSet.getColumnIndex("s_piuroute"));
                    dbString += "#";
                    mylist.add(recordSet.getString(recordSet.getColumnIndex("s_piuroute")));
                }

            }
            recordSet.moveToNext();
        }
        db.close();
        return dbString;


    }

    public String dynaimcroute(String value)
    {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();


        if (value.equals("(M)"))
        {
            String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_STUDENTFIRSTSLOT + "=\"" + value + "\";";
            //String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE 1";

            //Cursor points to a location in your result
            Cursor recordSet = db.rawQuery(query, null);
            //Move to the first row in your results
            recordSet.moveToFirst();
            ArrayList<String> mylist;
            mylist = new ArrayList<>();
            while (!recordSet.isAfterLast()) {
                // null could happen if we used our empty constructor
                if (recordSet.getString(recordSet.getColumnIndex("s_piuroute")) != null && !recordSet.getString(recordSet.getColumnIndex("s_piuroute")).equals("null") && !recordSet.getString(recordSet.getColumnIndex("s_piuroute")).equals("")) {
                    if(!mylist.contains(recordSet.getString(recordSet.getColumnIndex("s_piuroute"))))
                    {
                        dbString += recordSet.getString(recordSet.getColumnIndex("s_piuroute"));
                        dbString += "#";
                        mylist.add(recordSet.getString(recordSet.getColumnIndex("s_piuroute")));
                    }

                }
                recordSet.moveToNext();
            }

        }
        else if(value.equals("(E)"))
        {
            String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_STUDENTSECONDSLOT + "=\"" + value + "\";";

            //Cursor points to a location in your result
            Cursor recordSet = db.rawQuery(query, null);
            //Move to the first row in your results
            recordSet.moveToFirst();
            ArrayList<String> mylist;
            mylist = new ArrayList<>();
            while (!recordSet.isAfterLast()) {
                // null could happen if we used our empty constructor
                if (recordSet.getString(recordSet.getColumnIndex("s_drroute")) != null && !recordSet.getString(recordSet.getColumnIndex("s_drroute")).equals("null") && !recordSet.getString(recordSet.getColumnIndex("s_drroute")).equals("")) {
                    if(!mylist.contains(recordSet.getString(recordSet.getColumnIndex("s_drroute"))))
                    {
                        dbString += recordSet.getString(recordSet.getColumnIndex("s_drroute"));
                        dbString += "#";
                        mylist.add(recordSet.getString(recordSet.getColumnIndex("s_drroute")));
                    }

                }
                recordSet.moveToNext();
            }
        }
        else if(value.equals("(A)"))
        {
            String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_STUDENTSECONDSLOT + "=\"" + value + "\";";

            //Cursor points to a location in your result
            Cursor recordSet = db.rawQuery(query, null);
            //Move to the first row in your results
            recordSet.moveToFirst();
            ArrayList<String> mylist;
            mylist = new ArrayList<>();
            while (!recordSet.isAfterLast()) {
                // null could happen if we used our empty constructor
                if (recordSet.getString(recordSet.getColumnIndex("s_drroute")) != null && !recordSet.getString(recordSet.getColumnIndex("s_drroute")).equals("null") && !recordSet.getString(recordSet.getColumnIndex("s_drroute")).equals("")) {
                    if(!mylist.contains(recordSet.getString(recordSet.getColumnIndex("s_drroute"))))
                    {
                        dbString += recordSet.getString(recordSet.getColumnIndex("s_drroute"));
                        dbString += "#";
                        mylist.add(recordSet.getString(recordSet.getColumnIndex("s_drroute")));
                    }

                }
                recordSet.moveToNext();
            }
        }
        //Position after the last row means the end of the results

        db.close();
        return dbString;


    }

    public String classvalue()
    {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE 1";

        //Cursor points to a location in your result
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();
        ArrayList<String> mylist;
        mylist =new ArrayList<>();
        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("studentclass")) != null && !recordSet.getString(recordSet.getColumnIndex("studentclass")).equals("null") ) {
                if(!mylist.contains(recordSet.getString(recordSet.getColumnIndex("studentclass"))))
                {
                    dbString += recordSet.getString(recordSet.getColumnIndex("studentclass"));
                    dbString += "#";
                    mylist.add(recordSet.getString(recordSet.getColumnIndex("studentclass")));
                }

            }
            recordSet.moveToNext();
        }
        db.close();
        return dbString;
    }
    public String validatestudentroute(String id,String route,String slot) {
        String status = "";

        if (slot.equals("(M)")) {
            SQLiteDatabase db1 = getWritableDatabase();
            String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_STUDENTCARDID + "=\"" + id + "\" AND " + COLUMN_PICKUPROUTE + "=\"" + route + "\";";

            Cursor recordSet = db1.rawQuery(query, null);
            recordSet.moveToFirst();
            while (!recordSet.isAfterLast()) {
                // null could happen if we used our empty constructor
                if (recordSet.getString(recordSet.getColumnIndex("studentname")) != null) {
                    status = recordSet.getString(recordSet.getColumnIndex("studentname"));
                }
                recordSet.moveToNext();
            }
            db1.close();
        } else
        {
            SQLiteDatabase db1 = getWritableDatabase();
            String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_STUDENTCARDID + "=\"" + id + "\" AND " + COLUMN_DROPROUTE + "=\"" + route + "\" AND " + COLUMN_STUDENTSECONDSLOT + "=\"" + slot + "\";";

            Cursor recordSet = db1.rawQuery(query, null);
            recordSet.moveToFirst();
            while (!recordSet.isAfterLast()) {
                // null could happen if we used our empty constructor
                if (recordSet.getString(recordSet.getColumnIndex("studentname")) != null) {
                    status = recordSet.getString(recordSet.getColumnIndex("studentname"));
                }
                recordSet.moveToNext();
            }
            db1.close();
        }
        return status;
    }
}