package com.example.baitaplab2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Add extends AppCompatActivity {

    private Button btnAdd;
    private Button btnCancel;
    private EditText edId;
    private EditText edName;
    private EditText edPhone;
    private EditText edEmail;
    private CheckBox cbStatus;
    private ImageView img;
    private String uri_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        edId = findViewById(R.id.edId);
        edPhone = findViewById(R.id.edPhone);
        edName = findViewById(R.id.edName);
        edEmail = findViewById(R.id.edEmail);
        img = findViewById(R.id.img);
        cbStatus = findViewById(R.id.cbStatus);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);

        Intent intent = getIntent();

        Bundle b = intent.getExtras();
        if(b!=null){
            int id = b.getInt("Id");
            String image = b.getString("Image");
            uri_img = image;
            String name = b.getString("Name");
            String phone = b.getString("Phone");
            String email = b.getString("Email");
            Boolean status = b.getBoolean("Status");
            edId.setText(String.valueOf(id));
            edName.setText(name);
            edPhone.setText(phone);
            edEmail.setText(email);
            cbStatus.setChecked(status);
            if(image!=null)
            {
                Glide.with(Add.this).load(image).into(img);
            }
            btnAdd.setText("Sửa");
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id = Integer.parseInt(edId.getText().toString());
                String name = edName.getText().toString();
                String phone = edPhone.getText().toString();
                String email = edEmail.getText().toString();
                Boolean status = cbStatus.isChecked();
                String img = uri_img;
                Intent i = new Intent();
                Bundle b = new Bundle();

                b.putInt("Id", id);
                b.putString("Name", name);
                b.putString("Phone", phone);
                b.putString("Email", email);
                b.putString("Image", img);
                b.putBoolean("Status", status);

                i.putExtras(b);
                setResult(150, i);
                finish();
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,250);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==250 && resultCode==RESULT_OK && data!=null){
            Uri uri = data.getData();
            uri_img = getRealPathFromURI(uri,Add.this);
            if(uri_img!=null)
            {
                Glide.with(Add.this).load(uri_img).into(img);
            }
            Log.d("a1", "onActivityResult: " + uri_img);
        }
    }
    private String getRealPathFromURI(Uri uri, Context context) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;

        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
    }
}