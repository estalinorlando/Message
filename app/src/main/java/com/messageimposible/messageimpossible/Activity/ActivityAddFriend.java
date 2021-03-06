package com.messageimposible.messageimpossible.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.messageimposible.messageimpossible.Adapter.AdapterListViewAddFriend;
import com.messageimposible.messageimpossible.Entity.EntityListItemAddFriend;
import com.messageimposible.messageimpossible.Entity.EntityUsers;
import com.messageimposible.messageimpossible.R;

import java.util.ArrayList;

public class ActivityAddFriend extends AppCompatActivity{

    private ListView lv;
    private ArrayList<EntityListItemAddFriend> listContacts;
    private ArrayList<EntityListItemAddFriend> listFriends;
    private AdapterListViewAddFriend adapter;

    private String name;
    private String email;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_friend);

        Bundle b = getIntent().getExtras();

        if(b.getString("name")!= null){

            name = b.getString("name");
            email = b.getString("email");

        }

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("users");

        listFriends = GetlistChat();
        adapter = new AdapterListViewAddFriend(this, listFriends);

        lv = findViewById(R.id.listView_friends);
        lv.setAdapter(adapter);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //que cree un menu emergente donde ponga nombre de la persona a la que quieres invitar

                Intent i = new Intent(ActivityAddFriend.this, ActivitySendInvites.class);
                i.putExtra("name_target", listFriends.get(position).getUsername());
                i.putExtra("id_target", listFriends.get(position).getId());
                i.putExtra("username", name);
                i.putExtra("email", email);
                startActivity(i);
                //si confirma debera aparecer una invitacion en la pestaña de invites del taget

                //si el target acepta, debera aparecer en la pestaña de contactos el nuevo amigo aceptado,
                //tanto en la del target como en la del owner.

            }
        });

    }


    private ArrayList<EntityListItemAddFriend> GetlistChat(){
        listContacts = new ArrayList<EntityListItemAddFriend>();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listContacts.clear();
                if (dataSnapshot.exists()){

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        EntityListItemAddFriend user = snapshot.getValue(EntityListItemAddFriend.class);


                        if(!user.getUsername().equals(name)){

                           listContacts.add(user);

                        }

                    }

                    adapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return listContacts;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item_friend,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int res_id = item.getItemId();

        if(res_id == R.id.action_search_friend){

            Toast.makeText(getApplicationContext(), "search", Toast.LENGTH_LONG).show();

        }else if(res_id == R.id.action_back_friend){

            Toast.makeText(getApplicationContext(), "Back", Toast.LENGTH_LONG).show();
            finish();

        }else if (res_id == R.id.action_logout_friend){

            mAuth.signOut();
            Toast.makeText(getApplicationContext(), "Loged out", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, ActivityLogin.class);
            startActivity(i);
            finish();
        }

        return true;
    }

}
