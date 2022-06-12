package com.y54.bookkeep;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "bill.db";
    private static final String TBL_NAME = "BillTBL";
    private SQLiteDatabase db;

    DBHelper(Context c){
        super(c,DB_NAME,null,2);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        String CREATE_TBL = "create table BillTBL(_id integer primary key autoincrement,date text not null,ioType text not null,type text not null,cost text not null)";
        db.execSQL(CREATE_TBL);
    }
    //insert
    public void insert(ContentValues values){
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TBL_NAME,null,values);
        db.close();
    }
    //query
    public Cursor query(int i){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.query(TBL_NAME,null,null,null,null,null,null);
        if(i==1)
            c = db.query(TBL_NAME,null,null,null,null,null,null);
        else if(i==-1)
            c = db.rawQuery("select * from BillTBL order by _id desc",null);
        return c;
    }

    public double getSumCost(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.query(TBL_NAME,null,null,null,null,null,null);
        Double use_sum=0.0;
        if(c.moveToFirst()){
            do{
                use_sum=use_sum+Double.valueOf(c.getString(4));
            }while (c.moveToNext());
            use_sum=-use_sum;
        }
        return use_sum;
    }
    //del
    public void del(int id){
        if(db == null){
            db = getWritableDatabase();
        }
        db.delete(TBL_NAME,"_id=?",new String[]{String.valueOf(id)});
    }
    //close
    public void colse(){
        if(db!=null)
            db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
