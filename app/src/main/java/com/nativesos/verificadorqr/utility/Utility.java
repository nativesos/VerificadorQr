package com.nativesos.verificadorqr.utility;

public class Utility {

    // COntantes campos tabla usuarios
    public static final String TABLA_HISTORY = "history";
    public static final String ID = "id";
    public static final String URL = "url";
    public static final String DATE_INSIDE = "entrada";
    public static final String DATE_OURSIDE = "salida";
    public static final String DATE = "fecha";

    public static final String CREATE_TABLE_HISTORY = "CREATE TABLE IF NOT EXISTS "+TABLA_HISTORY+" ("+ID+" TEXT, "+URL+" TEXT, "+DATE_INSIDE+" TEXT, "+DATE_OURSIDE+" TEXT,"+DATE+" TEXT)";


}
