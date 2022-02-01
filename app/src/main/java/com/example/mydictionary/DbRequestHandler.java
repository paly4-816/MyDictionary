package com.example.mydictionary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DbRequestHandler {
    private DbRequestHandler handler;
    private SQLiteDatabase database;
    private DbOpenHelper openHelper;
    private final Context context;
    private final String DATABASE_NAME;
    private final int version;
    private final String TABLE_NAME ="Dictionary";
    private final String ID ="_id";
    private final String WORD = "word";
    private final String TRANSLATION = "translation";
    private final String RATING ="rating";
    private final String LAST_REPETITION ="last_repetition_date";

    private DbRequestHandler(Context context, String databaseName,int version){
        this.context = context;
        DATABASE_NAME = databaseName;
        this.version = version;
    }
    public DbRequestHandler getInstance(Context context,String databaseName,int version){
        if(handler==null){
            handler = new DbRequestHandler(context,databaseName,version);
        }
        return handler;
    }

    public void open(){
        openHelper = new DbOpenHelper(context,DATABASE_NAME,version);
        database = openHelper.getReadableDatabase();
    }
    public void close(){
        openHelper.close();
    }

    private class DbOpenHelper extends SQLiteOpenHelper{

        public DbOpenHelper(@Nullable Context context, @Nullable String name, int version) {
            super(context, name,null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table " +
                    TABLE_NAME+ "(" +
                    ID +" integer primary key autoincrement, "+
                    WORD+ " text, " +
                    TRANSLATION+ " text, " +
                    RATING+ " integer, " +
                    LAST_REPETITION+ "integer" +
                    ");"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}
