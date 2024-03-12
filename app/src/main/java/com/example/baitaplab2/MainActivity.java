package com.example.baitaplab2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private Button btnAdd;
    private Button btnDelete;
    private ListView lstContact;
    private EditText edNameMain;
    private ArrayList<Contact> ContactList;
    private Adapter ListAdapter;
    private int SelectedItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Yêu cầu quyền truy cập vào bộ nhớ
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    300);
        }

        btnAdd = findViewById(R.id.btnAdd);
        btnDelete = findViewById(R.id.btnDelete);
        lstContact = findViewById(R.id.lstContact);
        edNameMain = findViewById(R.id.edName);

        registerForContextMenu(lstContact);

        ContactList = new ArrayList<>();
        ContactList.add(new Contact(1,"Park Chaeyoung","0987654321","a1@gmail.com","https://media.vov.vn/sites/default/files/styles/large/public/2023-09/4_47.jpg",false));
        ContactList.add(new Contact(2,"Kim Jisoo","0987654321","a2@gmail.com","https://i.pinimg.com/originals/4b/7b/a6/4b7ba64d0bd06cd071929e0ac48694f6.jpg",true));
        ContactList.add(new Contact(3,"Kim Jennie","0987654321","a3@gmail.com","https://i.pinimg.com/originals/dc/16/18/dc1618c466e42be9797cc031fa64a576.jpg",false));
        ContactList.add(new Contact(4,"Kim Jennie","0987654321","a3@gmail.com","/storage/emulated/0/Download/6168e-16550201091769-1920.jpg",false));

        ListAdapter = new Adapter(ContactList,this);
        lstContact.setAdapter(ListAdapter);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
                b.setTitle("Xác nhận");
                b.setMessage("Bạn có chắc chắn muốn xoá không?");
                b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Iterator<Contact> iterator = ContactList.iterator();
                        while(iterator.hasNext()){
                            Contact x = iterator.next();
                            if(x.getStatus())
                                iterator.remove();
                        }
                        ListAdapter.notifyDataSetChanged();
                    }
                });

                b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog al = b.create();
                al.show();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Add.class);
                startActivityForResult(intent,100);
            }
        });
        lstContact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                SelectedItemId = position;
                Log.d("long", "onItemLongClick: " );
                return false;
            }
        });
        lstContact.setOnCreateContextMenuListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode==150)
        {
            Bundle b = data.getExtras();
            int id = b.getInt("Id");
            String name = b.getString("Name");
            String phone = b.getString("Phone");
            String email = b.getString("Email");
            String img = b.getString("Image");
            Boolean status = b.getBoolean("Status");
            Contact newcontact = new Contact(id,name,phone,email,img,status);
            Log.d("a-main", "onActivityResult: "+ img);
            ContactList.add(newcontact);
            ListAdapter.notifyDataSetChanged();
        }
        if(requestCode == 200 && resultCode==150){
            Bundle b = data.getExtras();
            int id = b.getInt("Id");
            String name = b.getString("Name");
            String phone = b.getString("Phone");
            String email = b.getString("Email");
            String img = b.getString("Image");
            Boolean status = b.getBoolean("Status");
            Contact newcontact = new Contact(id,name,phone,email,img,status);
            ContactList.set(SelectedItemId,newcontact);
            ListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        Log.d("MainActivity", "onCreateContextMenu called");
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contextmenu,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Contact c = ContactList.get(SelectedItemId);
        if(item.getItemId()== R.id.mnuEdit){

            Intent intent = new Intent(MainActivity.this, Add.class);
            Bundle b = new Bundle();
            b.putInt("Id",c.getId());
            b.putString("Name",c.getName());
            b.putString("Phone",c.getPhone());
            b.putString("Email",c.getEmail());
            b.putString("Image",c.getImage());
            b.putBoolean("Status",c.getStatus());
            intent.putExtras(b);
            startActivityForResult(intent,200);
        } else if (item.getItemId()== R.id.mnuCall) {
            Intent in = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + c.getPhone()));
            startActivity(in);
        }
        else if(item.getItemId()==R.id.mnuSms){
            Intent in = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:" + c.getPhone()));
            startActivity(in);
        } else if (item.getItemId()==R.id.mnuEmail) {
            Intent in = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + c.getEmail()));
            startActivity(in);
        } else if (item.getItemId()==R.id.mnuFacebook) {
            Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"+ c.getName()));
            startActivity(in);
        }
        return super.onContextItemSelected(item);
    }
}