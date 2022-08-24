package com.bkacad.nnt.demoandroidroomd04;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Config DB
    private AppDatabase appDatabase;
    private ContactDAO contactDAO;

    // Listview
    private ListView lvContact;
    private List<String> data;
    private List<Contact> contactList;
    private ArrayAdapter<String> arrayAdapter;
    // EditText
    private EditText edtName, edtPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvContact = findViewById(R.id.lvContact);
        edtName = findViewById(R.id.edtFullName);
        edtPhone = findViewById(R.id.edtPhone);

        // Khai báo DB
        appDatabase = Room.databaseBuilder(this, AppDatabase.class, "contact_db")
                .allowMainThreadQueries()
                .build();

        contactDAO = appDatabase.getContactDAO();

        // Thử nghiệm xem Listview có hoạt động hay ko?

        data = new ArrayList<>();

        // Chuyển đổi List<Contact> -> List<String>
        contactList = contactDAO.getAll();
        for (Contact contact : contactList) {
            data.add(contact.toString());
        }

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                data);
        lvContact.setAdapter(arrayAdapter);

        // Nhấn giũ để xoá
        lvContact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Xoá trong DB
                Contact contact = contactList.get(i);
                contactDAO.deleteAll(contact);
                // Xoá trong Data, => báo adapter
                data.remove(i);
                arrayAdapter.notifyDataSetChanged();

                return false;
            }
        });
    }

    public void addNewContact(View view) {
        String name = edtName.getText().toString();
        String phone = edtPhone.getText().toString();
        // Tự validate thêm nhé
        data.add(name + " - " + phone);
        arrayAdapter.notifyDataSetChanged();

        // Lưu vào Database
        Contact contact = new Contact();
        contact.setFullName(name);
        contact.setPhone(phone);
        contactDAO.insertAll(contact);

    }
}