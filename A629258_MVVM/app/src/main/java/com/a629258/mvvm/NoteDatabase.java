package com.a629258.mvvm;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JacksonInject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context){

        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class,"note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;

    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{

        private NoteDao noteDao;

        private PopulateDbAsyncTask(NoteDatabase db){
            noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title1","Description1",1));
            noteDao.insert(new Note("Title2","Description2",2));
            noteDao.insert(new Note("Title3","Description3",3));
            try {
                String jsonString = "[\n" +
                        "{\n" +
                        "\"id\": null,\n" +
                        "\"title\":\"Ajinkya\",\n" +
                        "\"description\":\"Android\",\n" +
                        "\"priority\": 1\n" +
                        "},\n" +
                        "{\n" +
                        "\"id\": null,\n" +
                        "\"title\":\"Aashish\",\n" +
                        "\"description\":\"Ionic\",\n" +
                        "\"priority\": 2\n" +
                        "},\n" +
                        "{\n" +
                        "\"id\": null,\n" +
                        "\"title\":\"Akash\",\n" +
                        "\"description\":\"Backend\",\n" +
                        "\"priority\": 3\n" +
                        "}\n" +
                        "]\n";

                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); i++){
                    Note note = (Note) JacksonUtils.fromJsonToJava(jsonArray.get(i).toString(),Note.class);
                    noteDao.insert(note);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

}
