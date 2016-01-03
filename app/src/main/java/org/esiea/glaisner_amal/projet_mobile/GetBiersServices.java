package org.esiea.glaisner_amal.projet_mobile;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetBiersServices extends IntentService {

    private static final String get_all_biers = "org.esiea.perillat_frick.inf4044.get_all_biers";

    public GetBiersServices() {
        super("GetBiersServices");
    }

    public static void startActionBiers(Context context) {
        Intent intent = new Intent(context, GetBiersServices.class);
        intent.setAction(get_all_biers);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(intent != null) {
            handleActionBiers();
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(SecondActivity.BIERS_UPDATE));
        }
    }


    private void copyInputStreamToFile(InputStream in, File file){
        try{
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;

            while ((len = in.read(buf)) > 0){
                out.write(buf,0,len);
            }

            out.close();
            in.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void handleActionBiers() {
        Log.d("Get_All_Biers", "Thread service name " + Thread.currentThread().getName());
        URL url = null;

        try{
            url = new URL("http://binouze.fabrigli.fr/bieres.json");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            if(HttpURLConnection.HTTP_OK == conn.getResponseCode()){
                copyInputStreamToFile(conn.getInputStream(), new File(getCacheDir(),"bieres.json"));
                Log.d("Get_All_Biers", "bieres.json downloaded !");
            }

        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

}