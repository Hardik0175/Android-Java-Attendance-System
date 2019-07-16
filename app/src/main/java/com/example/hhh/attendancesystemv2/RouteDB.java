package com.example.hhh.attendancesystemv2;

/**
 * Created by hhh on 7/26/2018.
 */

public class RouteDB {
    private String _id_route;
    private String _Routename;
    private String _Routestarttime;
    private String _Routeendtime;
    private String _Routetype;
    private String _Routemessage;

    public RouteDB(String Routeid,String RouteName,String RouteStartTime,String RouteEndTime,String RouteType,String RouteMessage) {
        this._id_route = Routeid;
        this._Routename = RouteName;
        this._Routestarttime = RouteStartTime;
        this._Routeendtime = RouteEndTime;
        this._Routetype= RouteType;
        this._Routemessage = RouteMessage;


    }
    public String get_id_route() {
        return _id_route;
    }

    public String get_Routename() {
        return _Routename;
    }
    public String get_Routestarttime() {
        return _Routestarttime;
    }
    public String get_Routeendtime() {
        return _Routeendtime;
    }
    public String get_Routetype() {
        return _Routetype;
    }
    public String get_Routemessage() {
        return _Routemessage;
    }


    public void set_id_route(String _id_route) {
        this._id_route = _id_route;
    }
    public void set_Routename(String  _Routename) {
        this._Routename = _Routename;
    }

    public void set_Routestarttime(String _Routestarttime) {
        this._Routestarttime = _Routestarttime;
    }
    public void set_Routeendtime(String  _Routeendtime) {
        this._Routeendtime = _Routeendtime;
    }
    public void set_Routetype(String  _Routetype) {
        this._Routetype = _Routetype;
    }
    public void set_Routemessage(String  _Routemessage) {
        this._Routemessage = _Routemessage;
    }
}
