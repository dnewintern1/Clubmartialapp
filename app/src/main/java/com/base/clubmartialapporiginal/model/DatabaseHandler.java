package com.base.clubmartialapporiginal.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {


    private  static final String DATABASE_NAME = "martialartdatabase";
    private  static  final  int DATABASE_VERSION = 1;
    private static  final String MARTIAL_ARTS_TABLE = "MartialArts";
    private static  final String ID_KEY = "id";
    private static  final String NAME_KEY = "name";
    private static  final String PRICE_KEY = "price";
    private static  final String COLOR_KEY = "color";

    public  DatabaseHandler(Context context){
        super(context ,DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createDatabaseSQL = "create table " + MARTIAL_ARTS_TABLE + "( " +   ID_KEY +
                " integer primary key autoincrement" +
                ", " + NAME_KEY + " text" + ", " + PRICE_KEY + " real" + ", " + COLOR_KEY + " text" + " )";

        db.execSQL(createDatabaseSQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + MARTIAL_ARTS_TABLE) ;
        onCreate(db);
    }

    public void addMartialArt(MartialArt martialArtobj){

        SQLiteDatabase database = getWritableDatabase();
        String addMartialArtSQLCommand =  "insert into " + MARTIAL_ARTS_TABLE + " values(null, '" + martialArtobj.getMartialArtname() +
                "', '" + martialArtobj.getMartialArtPrice() +
                "', '" + martialArtobj.getMartialArtColor()+ "')";

        database.execSQL(addMartialArtSQLCommand);
        database.close();
    }

    public void deleteMartialArtObjectFromDatabaseById(int id){

        SQLiteDatabase database = getWritableDatabase();
        String deleteMartialArtSQLCommand = "delete from " + MARTIAL_ARTS_TABLE +
                                            " where " + ID_KEY + " = " + id;


        database.execSQL(deleteMartialArtSQLCommand);
        database.close();
    }

    public void ModifyMartialArtObject(int martialArtID, String martialartName, double martialArtPrice, String martialArtColor){

        SQLiteDatabase database = getWritableDatabase();
        String modifyMartialArtSQLCommand ="update " + MARTIAL_ARTS_TABLE +
                " set " + NAME_KEY + " = '" + martialartName +
                "', " + PRICE_KEY +" = '" + martialArtPrice +
                "', " + COLOR_KEY + " = '" + martialArtColor +
                "' " + "where " + ID_KEY + " = " + martialArtID;

        database.execSQL(modifyMartialArtSQLCommand);
        database.close();


    }

    public ArrayList<MartialArt> returnAllMartialArtObject(){
        SQLiteDatabase database = getWritableDatabase();
        String sqlQueryCommand = "select * from " + MARTIAL_ARTS_TABLE;
        Cursor cursor = database.rawQuery(sqlQueryCommand, null);

        ArrayList<MartialArt> martialArts = new ArrayList<>();

        while(cursor.moveToNext()){

            MartialArt currentMartialArtObject  = new MartialArt(
                    Integer.parseInt(cursor.getString(0)),cursor.getString(1),
                    cursor.getDouble(2), cursor.getString(3));

            martialArts.add(currentMartialArtObject);
        }
        database.close();
        return martialArts;

    }


    public MartialArt returnMartialArtObjectById(int id){


        SQLiteDatabase database = getWritableDatabase();
        String sqlQueryCommand = "select * from " + MARTIAL_ARTS_TABLE + " where" + ID_KEY + " = " + id;
        Cursor cursor = database.rawQuery(sqlQueryCommand, null);

        MartialArt martialArtObject = null;

        while(cursor.moveToFirst()){

            martialArtObject  = new MartialArt(
                    Integer.parseInt(cursor.getString(0)),cursor.getString(1),
                    cursor.getDouble(2), cursor.getString(3));

        }
        database.close();
        return martialArtObject;

    }
}