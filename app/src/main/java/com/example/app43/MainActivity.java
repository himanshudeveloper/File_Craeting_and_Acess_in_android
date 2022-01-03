package com.example.app43;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.app43.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityMainBinding binding;

    ArrayList<String > filePathNames;
    File[] files;

    public static  final  int REQUEST_CODE = 1234;


    public boolean isStoragePermissionGranted(){
        if (Build.VERSION.SDK_INT >= 23){
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                Log.v("LOG","permission is granted");
                return true;
            }else {
                Log.v("LOG","Permission is revoked");
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                return false;
            }
        }else {
            Log.v("LOG","permission is granted");
            return true;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Log.v("LOG",permissions[0] + "was"+grantResults[0]);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SDCARDChecker.checkWheatherExternalStorageAvailableOrNot(MainActivity.this);
        isStoragePermissionGranted();

        binding.btnDownloadDirectory.setOnClickListener(this);
        binding.btnMusicDirectory.setOnClickListener(this);
        binding.btnDocumentFolder.setOnClickListener(this);
        binding.btnRingTonesFolder.setOnClickListener(this);
        binding.btnProdcastFolder.setOnClickListener(this);
        binding.btnMoviesFolder.setOnClickListener(this);
        binding.btnAlarmFolder.setOnClickListener(this);
        binding.btnPictureFolder.setOnClickListener(this);
        binding.btnSaveFile.setOnClickListener(this);
        binding.btnRetriveInfo.setOnClickListener(this);

        binding.animal.setOnClickListener(this);

        binding.btnAllowAccessPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStoragePermissionGranted()){

                    filePathNames = new ArrayList<String >();
                    File filepath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"Instagram");
                    if (filepath.isDirectory() && filepath !=null){
                        files = filepath.listFiles();

                        for (int index = 0; index < files.length;index++){
                            filePathNames.add(files[index].getAbsolutePath());
                        }
                    }
                }
                for (int index = 0; index<filePathNames.size();index ++){
                    final ImageView imageView = new ImageView(MainActivity.this);
                    imageView.setImageURI(Uri.parse(filePathNames.get(index)));
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(500,500));

                    final int i = index;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            imageView.setImageURI(Uri.parse(filePathNames.get(i)));
                        }
                    });

                    binding.linearLayoutHorizontal.addView(imageView);


                }


            }
        });


    }

    public File returnStorageDirectoryForFolderName(String direcotyName,String nameOfFolder){

        File filePath = new File(Environment.getExternalStoragePublicDirectory(direcotyName),nameOfFolder);

        if (!filePath.mkdirs()) {
            letsCrateATost("There can not be such directory sd card");
        }else  {
            letsCrateATost("your folder is created and its name us"+nameOfFolder);
        }
        return filePath;


    }

    public void letsCrateATost(String message){
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }


    public void letsSaveFileToDocumentsFolder(){

        File filepath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),"NiceFile.txt");
        try{

            FileOutputStream fileOutputStream = new FileOutputStream(filepath);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.append("hay this is txt file");
            outputStreamWriter.close();
            letsCrateATost("Saved");



        }catch (Exception e){
            Log.i("log",e.toString());
            letsCrateATost("Exception occured check the log for more info");
        }

    }


    public void letsRetriveFileDataFromDocumentsFolder(){
        File filepath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),"NiceFile.txt");


            try {
                FileInputStream fileInputStream = new FileInputStream(filepath);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                String fileData = "";
                String bufferData  = "";
                while ((fileData = bufferedReader.readLine()) != null){
                    bufferData = bufferData + fileData + "\n";



                }
                binding.txtValue.setText(bufferData);
                bufferedReader.close();


            } catch (Exception e) {
                e.printStackTrace();
                letsCrateATost("Exception occured check Log for more info");
            }


    }

    public void letsSaveTheImageToPictureFolder(){
        try{
            Bitmap bitmap =BitmapFactory.decodeResource(getResources(),R.drawable.bitcoin);
            File filepath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"bitcoin3.png");
            OutputStream outputStream = new FileOutputStream(filepath);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
            outputStream.flush();
            outputStream.close();
            letsCrateATost("Your image has been successfully saved");
        }catch (Exception e){
            e.printStackTrace();
            letsCrateATost("Exception occured check log for more info");
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnDownloadDirectory:

                returnStorageDirectoryForFolderName(Environment.DIRECTORY_DOWNLOADS,"Nice Downloads");
                break;

            case R.id.btnMusicDirectory:

                returnStorageDirectoryForFolderName(Environment.DIRECTORY_MUSIC,"nice music");
                break;

            case R.id.btnDocumentFolder:
                returnStorageDirectoryForFolderName(Environment.DIRECTORY_DOCUMENTS,"nice documents");
                break;

            case R.id.btnRingTonesFolder:
                returnStorageDirectoryForFolderName(Environment.DIRECTORY_RINGTONES,"nice rigtones");
                break;

            case R.id.btnProdcastFolder:
                returnStorageDirectoryForFolderName(Environment.DIRECTORY_PODCASTS,"nice products");
                break;

            case R.id.btnMoviesFolder:
                returnStorageDirectoryForFolderName(Environment.DIRECTORY_MOVIES,"nice MoVies");
                break;

            case R.id.btnAlarmFolder:
                returnStorageDirectoryForFolderName(Environment.DIRECTORY_ALARMS,"nice Alarms");
                break;

            case R.id.btnPictureFolder:
                returnStorageDirectoryForFolderName(Environment.DIRECTORY_PICTURES,"nice pictures");
                break;

            case R.id.btnSaveFile:
                letsSaveFileToDocumentsFolder();
                break;

            case R.id.btnRetriveInfo:
                letsRetriveFileDataFromDocumentsFolder();
                break;

            case  R.id.animal:
                letsSaveTheImageToPictureFolder();
                break;





        }



    }


}