package com.din.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dinesh on 8/6/2015.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "blogsManager";

    // Blogs table name
    private static final String TABLE_BLOGS = "blogs";

    // Contacts Table Columns names
    private static final String BLOG_NO = "blog_no";
    private static final String PHOTO = "photo";
    private static final String USER_NAME = "username";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BLOGS_TABLE = "CREATE TABLE " + TABLE_BLOGS + "("
                + BLOG_NO + " INTEGER PRIMARY KEY," + PHOTO + " BLOB,"
                + USER_NAME + " TEXT NOT NULL," + TITLE + " TEXT NOT NULL,"
                + DESCRIPTION + " TEXT NOT NULL" + ")";
        db.execSQL(CREATE_BLOGS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BLOGS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    public void  addBlog(Blog blog) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PHOTO, blog.getPhoto()); // Contact Name
        values.put(USER_NAME, blog.getName()); // Contact Phone
        values.put(TITLE,blog.getTitle());
        values.put(DESCRIPTION, blog.getDesc());
        // Inserting Row
        db.insert(TABLE_BLOGS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single blog
    Blog getBlog(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
//
//		public Cursor query(String table, String[] columns, String selection,
//				String[] selectionArgs,
// 				String groupBy, String having, String orderBy, String limit)

        Cursor cursor = db.query(TABLE_BLOGS, new String[] { BLOG_NO,PHOTO,USER_NAME,TITLE, DESCRIPTION },
                BLOG_NO + "=?",	new String[] { String.valueOf(id) },
                null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Blog blog = new Blog(Integer.parseInt(cursor.getString(0)), cursor.getBlob(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        // return contact
        return blog;
    }

    // Getting All Blogs
    public List<Blog> getAllBlogs() {
        List<Blog> blogList = new ArrayList<Blog>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_BLOGS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Blog blog = new Blog();
//				blog.setID());
                blog.setBlog_no(Integer.parseInt(cursor.getString(0)));
                blog.setPhoto(cursor.getBlob(1));
                blog.setName(cursor.getString(2));
                blog.setTitle(cursor.getString(3));
                blog.setDesc(cursor.getString(4));
                // Adding contact to list
                blogList.add(blog);
            } while (cursor.moveToNext());
        }

        // return contact list
        return blogList;
    }

    // Updating single blog
    public int updateBlog(Blog blog) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PHOTO, blog.getPhoto());
        values.put(USER_NAME, blog.getName());
        values.put(TITLE,blog.getTitle());
        values.put(DESCRIPTION,blog.getDesc());

        // updating row
//		 public int update(String table, ContentValues values, String whereClause, String[] whereArgs)
        return db.update(TABLE_BLOGS, values, BLOG_NO + " = ?",
                new String[] { String.valueOf(blog.getBlog_no()) });
    }

    // Deleting single blog
    public void deleteBlog(Blog blog) {
        SQLiteDatabase db = this.getWritableDatabase();
        //int delete(String table, String whereClause, String[] whereArgs)
        db.delete(TABLE_BLOGS, BLOG_NO + " = ?",
                new String[] { String.valueOf(blog.getBlog_no()) });
        db.close();
    }


    // Getting blogs Count
    public int getBlogsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_BLOGS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }


    public void truncTable(){
        String trunQuery = "Delete from "+ TABLE_BLOGS+";";
        String trunc2 = "DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"+TABLE_BLOGS+"'";
//        String trunc3 = "DELETE FROM sqlite_sequence WHERE name = 'blogs'";
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(trunQuery);
//        db.execSQL(trunc3);
        db.close();
    }
}
