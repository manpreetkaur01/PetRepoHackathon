package pet_treasure_Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lalit on 9/12/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "UserManager.db";

    // User table name
    private static final String TABLE_USER = "petDetail";

    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_PHONE = "user_phone";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_USER_ADDRESS = "user_address";

    // pet details Columns names
    private static final String COLUMN_PET_NAME = "pet_name";
    private static final String COLUMN_PET_AGE = "pet_age";
    private static final String COLUMN_PET_BREAD = "pet_bread";
    private static final String COLUMN_PET_COLOR = "pet_color";
    private static final String COLUMN_PET_RATING = "pet_rating";
    private static final String COLUMN_PET_IMAGE = "pet_image";
    private static final String COLUMN_PET_FORADOPTION = "pet_forAdoption";

    public static final String IMAGE_ID = "id";
    public static final String IMAGE = "image";

    List<String> userNameArray = new ArrayList<String>();
    List<String> userPhoneArray = new ArrayList<String>();
    List<String> userAddressArray = new ArrayList<String>();
    List<String> petNameArray = new ArrayList<String>();
    List<String> petAgeArray = new ArrayList<String>();
    List<String> petBreadArray = new ArrayList<String>();
    List<String> petColorArray = new ArrayList<String>();
    List<String> petRatingArray = new ArrayList<String>();
    List<String> petImagesArray = new ArrayList<String>();
    List<String> petForAdoptionArray = new ArrayList<String>();






    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_PHONE + " TEXT," + COLUMN_USER_ADDRESS + " TEXT," + COLUMN_USER_PASSWORD + " TEXT," + COLUMN_PET_NAME + " TEXT," + COLUMN_PET_AGE + " TEXT," +
            COLUMN_PET_BREAD + " TEXT," + COLUMN_PET_COLOR + " TEXT," + COLUMN_PET_IMAGE + " TEXT," + COLUMN_PET_RATING + " TEXT," + COLUMN_PET_FORADOPTION + " TEXT" +")";

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    /**
     * Constructor
     * 
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);

        // Create tables again
        onCreate(db);

    }

    /**
     * This method is to create user record
     *
     * @param user
     */
    public void addUserAndPet(User user, Pet pet) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_PHONE, user.getPhone());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_ADDRESS, user.getaddress());

        values.put(COLUMN_PET_NAME, pet.getName());
        values.put(COLUMN_PET_AGE, pet.getAge());
        values.put(COLUMN_PET_BREAD, pet.getBread());
        values.put(COLUMN_PET_COLOR, pet.getColor());
        values.put(COLUMN_PET_RATING, pet.getRating());
        values.put(COLUMN_PET_FORADOPTION, pet.getForAdoption());
        values.put(COLUMN_PET_IMAGE, pet.getImage());

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_PHONE,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD,
                COLUMN_PET_IMAGE
        };
        // sorting orders
        String sortOrder =
                COLUMN_USER_NAME + " ASC";
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                Pet pet = new Pet();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setPhone(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PHONE)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                pet.setImg(cursor.getBlob(cursor.getColumnIndex(COLUMN_PET_IMAGE)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }

    /**
     * This method to update user record
     *
     * @param user
     */
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_PHONE, user.getPhone());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_PHONE + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method to check user exist or not
     *
     * @param username
     * @param password
     * @return true/false
     */
    public boolean checkUser(String username, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_NAME + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {username, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }


    public void getAllData(){



        SQLiteDatabase db = this.getReadableDatabase();

        Cursor  cursor = db.rawQuery("select * from "+ TABLE_USER,null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String userName = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME));
                String userPhone = cursor.getString(cursor.getColumnIndex(COLUMN_USER_PHONE));
                String userAddress = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ADDRESS));
                String petName = cursor.getString(cursor.getColumnIndex(COLUMN_PET_NAME));
                String petAge = cursor.getString(cursor.getColumnIndex(COLUMN_PET_AGE));
                String petBread = cursor.getString(cursor.getColumnIndex(COLUMN_PET_BREAD));
                String petColor = cursor.getString(cursor.getColumnIndex(COLUMN_PET_COLOR));
                String petImages = cursor.getString(cursor.getColumnIndex(COLUMN_PET_IMAGE));
                String petRating = cursor.getString(cursor.getColumnIndex(COLUMN_PET_RATING));
                String petadoption = cursor.getString(cursor.getColumnIndex(COLUMN_PET_FORADOPTION));


                userNameArray.add(userName);
                userPhoneArray.add(userPhone);
                userAddressArray.add(userAddress);
                petNameArray.add(petName);
                petAgeArray.add(petAge);
                petBreadArray.add(petBread);
                petColorArray.add(petColor);
                petImagesArray.add(petImages);
                petRatingArray.add(petRating);
                petForAdoptionArray.add(petadoption);


                cursor.moveToNext();
            }
        }
    }



}
