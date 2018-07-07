package com.example.manpreetkaur.pet_treasure;

import android.content.DialogInterface;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;


import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import pet_treasure_Model.DatabaseHelper;
import pet_treasure_Model.Pet;
import pet_treasure_Model.User;

public class Registration_Activity extends AppCompatActivity {

    EditText ownerName;
    EditText address;
    EditText phone;
    EditText pName;
    EditText pAge;
    EditText pBread;
    EditText pColor;
    ImageView pImage;
    RadioButton r1Yes;
    RadioButton r2No;
    Button registerBtn;
    private User user;
    private Pet pet;
    private DatabaseHelper databaseHelper;
    private int GALLERY = 1;
    private int CAMERA = 2;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final String IMAGE_DIRECTORY = "/Pets";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_);

        user = new User();
        pet = new Pet();
        databaseHelper = new DatabaseHelper(this);


        ownerName = (EditText) findViewById(R.id.edOwnername);
        address = (EditText) findViewById(R.id.edAddress);
        phone = (EditText) findViewById(R.id.edPhone);
        pName = (EditText) findViewById(R.id.edPetname);
        pAge = (EditText) findViewById(R.id.edAge);
        pBread = (EditText) findViewById(R.id.edBread);
        pColor = (EditText) findViewById(R.id.edColor);
        pImage = (ImageView) findViewById(R.id.petImgView);
        r1Yes = (RadioButton) findViewById(R.id.radioYes);
        r2No = (RadioButton) findViewById(R.id.radioNo);


        registerBtn = (Button) findViewById(R.id.btnRegister);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ownerName.getText().length() == 0) {
                    ownerName.setError("FIELD CANNOT BE EMPTY");

                } else if (address.getText().length() == 0) {
                    address.setError("FIELD CANNOT BE EMPTY");

                } else if (address.getText().length() < 7) {
                    address.setError("FULL ADDRESS REQUIRED");

                } else if (phone.getText().length() == 0) {

                    phone.setError("FIELD CANNOT BE EMPTY");
                } else if (phone.getText().length() < 10) {

                    phone.setError("NOT A VALID PHONE NUMBER");
                } else {
                    postDataToSQLite();

                }

            }
        });


        pImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });

    }

    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void postDataToSQLite() {




        /*if (ownerName.getText().length() == 0){
            ownerName.setError("FIELD SHOULD NOT BE EMPTY");
        }
        if (address.getText().length() == 0) {
            address.setError("FIELD SHOULD NOT BE EMPTY");
        }
        if (phone.getText().length() == 0) {
            phone.setError("FIELD SHOULD NOT BE EMPTY");
        }
        if (pName.getText().length() == 0) {
            pName.setError("FIELD SHOULD NOT BE EMPTY");
        }
        if (pAge.getText().length() == 0) {
            pAge.setError("FIELD SHOULD NOT BE EMPTY");
        }
        if (pBread.getText().length() == 0) {
            pBread.setError("FIELD SHOULD NOT BE EMPTY");
        }
        if (pColor.getText().length() == 0) {
            pColor.setError("FIELD SHOULD NOT BE EMPTY");
        }*/


        System.out.println("ehde ander aa janda k na" + ownerName.getText().toString().trim());

        user.setName(ownerName.getText().toString().trim());

        user.setPhone(phone.getText().toString().trim());
        user.setPassword(getRandomNumber());
        pet.setName(pName.getText().toString().trim());
        pet.setAge(pAge.getText().toString().trim());
        pet.setBread(pBread.getText().toString().trim());
        pet.setColor(pColor.getText().toString().trim());

        pet.setImage(1);


        if (r1Yes.isSelected()) {
            pet.setForAdoption(true);
        } else {
            pet.setForAdoption(false);
        }


        databaseHelper.addUserAndPet(user, pet);

        Toast.makeText(this, "Your Username is" + ownerName.getText().toString().trim() +  " and Your pwd is:" + user.getPassword(), Toast.LENGTH_LONG).show();
        ;
        Intent loginIntent = new Intent(getApplicationContext(), Login_Activity.class);
        startActivity(loginIntent);


    }


    public String getRandomNumber() {

        String val = "" + ((int) (Math.random() * 9000) + 1000);

        return val;
    }


    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    // BitmapFactory bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);



                    Toast.makeText(Registration_Activity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    pImage.setImageBitmap(bitmap);

                    getBitmapAsByteArray(bitmap);    //to set byte array to save in database

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Registration_Activity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            pImage.setImageBitmap(thumbnail);
            saveImage(thumbnail);

            Toast.makeText(Registration_Activity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }

        }
    }



    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }



    public void getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        pet.setImg(outputStream.toByteArray());
    }



}
