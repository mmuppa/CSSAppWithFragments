package edu.uw.tacoma.mmuppa.cssappwithfragments.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;

import edu.uw.tacoma.mmuppa.cssappwithfragments.model.Course;

/**
 * Created by mmuppa on 4/14/15.
 */
public class CourseDB {

    private static final String DATABASE_NAME = "course.db";
    private static final int DATABASE_VERSION = 1;
    private static final String COURSES_TABLE = "Courses";

    private Context context;
    private SQLiteDatabase db;

    private SQLiteStatement insertStmt;
    private static final String INSERT = "insert into " + COURSES_TABLE
            + "(id, shortDesc, longDesc, prereqs) values (?, ?, ?, ?)";

    public CourseDB(Context context) {
        this.context = context;
        OpenHelper openHelper = new OpenHelper(this.context);
        this.db = openHelper.getWritableDatabase();
        this.insertStmt = this.db.compileStatement(INSERT);
    }

    /**
     * Inserts the id and name into example
     * If successful, returns the rowid otherwise -1.
     */
    public long insert(String id, String shortDesc, String longDesc, String prereqs) throws Exception {
        this.insertStmt.bindString(1, id);
        this.insertStmt.bindString(2, shortDesc);
        this.insertStmt.bindString(3, longDesc);
        if (prereqs != null)
            this.insertStmt.bindString(4, prereqs);
        else
            this.insertStmt.bindNull(4);

        long rowID = this.insertStmt.executeInsert();
        if (rowID == -1) {
            throw new Exception("Unable to insert");
        }
        return rowID;
    }

    /**
     * Delete everything from example
     */
    public void deleteAll() {
        this.db.delete(COURSES_TABLE, null, null);
    }


    /**
     * Return an array list of edu.uw.tacoma.mmuppa.cssappwithwebservices.model.Course objects from the
     * data returned from select query on Courses table.
     *
     * @return
     */
    public ArrayList<Course> selectAll() {
        ArrayList<Course> list = new ArrayList<Course>();
        Cursor cursor = this.db.query(COURSES_TABLE, new String[]
                {"id", "shortDesc", "longDesc", "prereqs"}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {

                Course e = new Course(cursor.getString(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3));
                list.add(e);
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return list;
    }

    /**
     * Return the name when id is passed.
     * null if no record found.
     *
     * @param id
     * @return
     */
    public String selectByID(int id) {
        Cursor cursor = this.db.query(COURSES_TABLE, new String[]
                        {"shortDesc"}, "id=?",
                new String[]
                        {Long.toString(id)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                return cursor.getString(0);
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return null;
    }

    /**
     * Close the connection
     */
    public void close() {
        db.close();
    }

    private static class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + COURSES_TABLE
                    + " (id TEXT PRIMARY KEY, shortDesc TEXT, longDesc TEXT, prereqs TEXT)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("Example",
                    "Upgrading database, this will drop tables and recreate.");
            db.execSQL("DROP TABLE IF EXISTS " + COURSES_TABLE);
            onCreate(db);
        }
    }
}
