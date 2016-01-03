package org.esiea.glaisner_amal.projet_mobile;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SecondActivity extends AppCompatActivity {

    protected RecyclerView rv;
    public final static String BIERS_UPDATE = "com.octip.cours.inf4042_11.BIERS_UPDATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        GetBiersServices.startActionBiers(this);

        rv = (RecyclerView)findViewById(R.id.rv_biere);

        IntentFilter intentFilter = new IntentFilter(BIERS_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BierUpdate(), intentFilter);

        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(new BiersAdapter(getBiersFromFIle()));
    }

    class BierUpdate extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            Log.d("BierDownload", "" + getIntent().getAction());
            OnNotifyAction2();
            BiersAdapter ba = (BiersAdapter)rv.getAdapter();
            ba.setNewBier(getBiersFromFIle());
        }
    }

    class BiersAdapter extends RecyclerView.Adapter<BiersAdapter.BierHolder> {

        private JSONArray biers;

        public BiersAdapter(JSONArray array){
            this.biers = array;
        }

        public void setNewBier(JSONArray update){
            this.notifyDataSetChanged();
        }

        @Override
        public BierHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_bier_element, parent, false);
            return new BierHolder(view);
        }

        @Override
        public void onBindViewHolder(BierHolder holder, int position) {
            try {
                JSONObject jsonObject = biers.getJSONObject(position);
                String name = jsonObject.getString("name");
                holder.name.setText(name);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return biers.length();
        }

        class BierHolder extends RecyclerView.ViewHolder{

            public TextView name;

            public BierHolder(View itemView) {

                super(itemView);
                this.name = (TextView)itemView;
            }
        }
    }

    public JSONArray getBiersFromFIle(){
        try {
            InputStream in = new FileInputStream(getCacheDir()+"/"+"bieres.json");
            byte buffer[] = new byte[in.available()];
            in.read(buffer);
            in.close();
            return new JSONArray(new String(buffer,"UTF-8"));
        }catch (IOException e){
            e.printStackTrace();
            return new JSONArray();
        }catch (JSONException e){
            e.printStackTrace();
            return new JSONArray();
        }
    }

    public void OnNotifyAction2(){
        NotificationCompat.Builder notif = new NotificationCompat.Builder(this);
        notif.setContentTitle("Telechargement");
        notif.setContentText("Fin");
        notif.setSmallIcon(R.drawable.ic_action_arrow_bottom);

        NotificationManager notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notifManager.notify(1, notif.build());


    }
    public void OnP1Action(View v){
        createAndStartIntent();
    }

    public void createAndStartIntent(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
