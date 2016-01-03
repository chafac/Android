package org.esiea.glaisner_amal.projet_mobile;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import android.app.AlertDialog;


public class MainActivity extends AppCompatActivity {

    protected TextView tv_hw;
    private DatePickerDialog dpd;
    private AlertDialog.Builder alertDialog;
    private DialogInterface.OnClickListener alertListener;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        tv_hw = (TextView)findViewById(R.id.tv_hello_world);

        String date = DateUtils.formatDateTime(getApplicationContext(), (new Date()).getTime(), DateFormat.FULL);
        tv_hw.setText(date);

        dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = DateUtils.formatDateTime(getApplicationContext(), new GregorianCalendar(year,monthOfYear,dayOfMonth).getTimeInMillis(), DateFormat.FULL);
                tv_hw.setText(date);
            }
        }, 03,01,2016);



    }
    public void OnP2Action(View v){
        createAndStartIntent();
    }

    public void createAndStartIntent(){
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    public void OnNotifyAction(View v){
        NotificationCompat.Builder notif = new NotificationCompat.Builder(this);
        notif.setContentTitle("Notification");
        notif.setContentText("Hello World !");
        notif.setSmallIcon(R.drawable.ic_action_warning);

        NotificationManager notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notifManager.notify(1, notif.build());
    }

    public void onTextViewClicked(View v){
        dpd.show();
    }

    public void OnClickAction(View v){
        Toast.makeText(getApplicationContext(), getString(R.string.hello_world), Toast.LENGTH_LONG).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}