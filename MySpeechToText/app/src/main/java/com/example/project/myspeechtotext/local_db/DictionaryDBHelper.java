package com.example.project.myspeechtotext.local_db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.project.myspeechtotext.ui.dashboard.entity.model.RawDictionary;
import com.example.project.myspeechtotext.ui.dashboard.entity.response.Dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DictionaryDBHelper extends SQLiteOpenHelper {

    private Context context;
    private String DB_PATH = "";
    private SQLiteDatabase dB;
    private String TAG = "DictionaryDBHelper";

    // Dictionary Table Columns
    private static final String KEY_DICID = "DICID";
    private static final String KEY_DICWORD= "DICWORD";
    private static final String KEY_DISCOUNT = "DISCOUNT";
    private static final String KEY_UPDATE_TIME = "DISUPDATETIME";

    private static DictionaryDBHelper sInstance;

    public static synchronized DictionaryDBHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (sInstance == null) {
            sInstance = new DictionaryDBHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     */
    private DictionaryDBHelper(Context context) {
        super(context, ConstantsDB.DB_NAME, null, ConstantsDB.DB_Version_OLD);
        DB_PATH = context.getDatabasePath(ConstantsDB.DB_NAME).getPath();
    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_POSTS_TABLE = "CREATE TABLE " + ConstantsDB.TABLE_SPEECH +
                "(" +
                KEY_DICID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + // Define a primary key
                KEY_DICWORD + " VARCHAR , "+
                KEY_DISCOUNT + " INT(4) ," +
                KEY_UPDATE_TIME + " VARCHAR" +
                ")";

        db.execSQL(CREATE_POSTS_TABLE);
    }



    private String getCurrentTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + ConstantsDB.TABLE_SPEECH);
            onCreate(db);
        }
    }

    // Insert a post into the database
       public synchronized boolean insertDistionarWord(List<Dictionary> dictionariesList) {
        boolean isSuccess = false;
        SQLiteDatabase db = this.getWritableDatabase();

        if (dictionariesList != null && dictionariesList.size() > 0) {
            for (int i = 0; i < dictionariesList.size(); i++) {
                try {
                    // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
                    // consistency of the database.
                    db.beginTransaction();
                    Dictionary dictionary = dictionariesList.get(i);
                    ContentValues values = new ContentValues();
                    values.put(KEY_DICWORD, dictionary.getWord());
                    values.put(KEY_DISCOUNT, dictionary.getFrequency());
                    values.put(KEY_UPDATE_TIME, getCurrentTimeStamp());


                    // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
                    db.insertOrThrow(ConstantsDB.TABLE_SPEECH, null, values);
                    db.setTransactionSuccessful();
                    isSuccess = true;
                } catch (Exception e) {
                    isSuccess = false;
                    Log.d(TAG, "Error while trying to add post to database");
                } finally {
                    db.endTransaction();
                }
            }
        }
        return isSuccess;
    }


    // Get all posts in the database
    public List<RawDictionary> getDictionarDbData() {
        List<RawDictionary> res = new ArrayList<>();
        String SQL = "SELECT * FROM " + ConstantsDB.TABLE_SPEECH;
        Cursor c = null;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            c = db.rawQuery(SQL, null);
            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                RawDictionary re;
                do {
                    re = new RawDictionary();
                    re.setId(c.getInt(c.getColumnIndex(KEY_DICID)));
                    re.setDicWord(c.getString(c.getColumnIndex(KEY_DICWORD)));
                    re.setDicCount(c.getInt(c.getColumnIndex(KEY_DISCOUNT)));
                    res.add(re);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, e.toString());
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }
        return res;

    }



    //*******after speaking update database with match key word
    public  Map<Integer, Integer> updateAllSpeakingFrequency(String rawString){
        Map<Integer, Integer> selPositionmap = new HashMap<>();
        int count_first = 0;
        boolean returnBool = false;
        String [] rawSpreakData = rawString.split("\\s+");
        SQLiteDatabase db = this.getWritableDatabase();

        // ******** Step One *****************************//
        Map<String, Integer> wordMapData= new HashMap<String, Integer>();
        for(String word : rawSpreakData){
            Integer oldWordcnt =wordMapData.get(word);
            if(oldWordcnt == null){
                oldWordcnt = 0;
            }
            wordMapData.put(word,oldWordcnt+1);
        }
       /* Log.e("Split_Length ==", "=="+rawSpreakData.length);
        Log.e("Split_Length ==",  " 0 =="+rawSpreakData[0]);
        Log.e("Map Data::", wordMapData.toString());
        Log.e("Map Data::", "length="+wordMapData.size());*/

        for (Map.Entry<String, Integer> entry : wordMapData.entrySet()) {
            String keyWord = entry.getKey();
            int keyValue = entry.getValue();

            // Define a projection that specifies which columns from the database
            String[] projection = {
                    KEY_DICID, KEY_DISCOUNT
            };

            // Filter results WHERE "DICWORD" LIKE "Hello"
            String selection = KEY_DICWORD + " LIKE  ?";
            String[] selectionArgs = {keyWord};
            Cursor cursor = db.query(ConstantsDB.TABLE_SPEECH,  projection,  selection,  selectionArgs,   null, null, null );

            if(cursor != null && cursor.getColumnCount() >0 && cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex(KEY_DICID));
                int oldFrequency = cursor.getInt(cursor.getColumnIndex(KEY_DISCOUNT));
                int newFrequency = oldFrequency + keyValue;
                selPositionmap.put(id, newFrequency);
                ContentValues contentValues = new ContentValues();
                contentValues.put(KEY_DISCOUNT, newFrequency);
                String selection1 = KEY_DICID + " = ?";
                String[] selectionArgs1 = { String.valueOf(id)};
                count_first += db.update(ConstantsDB.TABLE_SPEECH, contentValues,selection1,selectionArgs1);
            }
        }
       // ******** ************* Step Two *****************************//
        wordMapData.clear();
        if(rawSpreakData.length == 1){
            return selPositionmap;
        }

        /***
         * making possible two word combination
         * Fetching two word (Like Quantum inventions )
         */
        for(int i=0; i< rawSpreakData.length - 1; i++){
            String makeWord = rawSpreakData[i]+" "+rawSpreakData[i+1];
            Integer oldWordCnt =wordMapData.get(makeWord);
            if(oldWordCnt == null){
                oldWordCnt = 0;
            }
            wordMapData.put(makeWord,oldWordCnt+1);
        }

        for (Map.Entry<String, Integer> entry : wordMapData.entrySet()) {
            String keyWord = entry.getKey();
            int keyValue = entry.getValue();
            // Define a projection that specifies which columns from the database
            String[] projection = {
                    KEY_DICID, KEY_DISCOUNT
            };

            // Filter results WHERE "DICWORD" LIKE "Quantum Inventions"
            String selection = KEY_DICWORD + " LIKE  ?";
            String[] selectionArgs = {"%"+keyWord+"%" };
            Cursor cursor = db.query(ConstantsDB.TABLE_SPEECH,  projection,  selection,  selectionArgs,   null, null, null );

            if(cursor != null && cursor.getColumnCount() >0 && cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex(KEY_DICID));
                int oldFrequency = cursor.getInt(cursor.getColumnIndex(KEY_DISCOUNT));
                int newFrequency = oldFrequency + keyValue;
                selPositionmap.put(id, newFrequency);
                ContentValues contentValues = new ContentValues();
                contentValues.put(KEY_DISCOUNT, newFrequency);
                String selection1 = KEY_DICID + " = ?";
                String[] selectionArgs1 = { String.valueOf(id)};
                count_first += db.update(ConstantsDB.TABLE_SPEECH, contentValues,selection1,selectionArgs1);
            }

        }

        /*Log.e("Map Data::", wordMapData.toString());
        Log.e("Map Data::", "length="+wordMapData.size());*/

        return   selPositionmap;
    }
}
