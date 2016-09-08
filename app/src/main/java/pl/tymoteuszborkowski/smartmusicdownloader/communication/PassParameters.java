package pl.tymoteuszborkowski.smartmusicdownloader.communication;

import android.os.Environment;

import org.apache.commons.io.FileUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

public class PassParameters implements Runnable {


    private static final String DOWNLOAD_MP3_URI = "http://10.0.2.2:8080/api/mp3";

    private String artist;
    private String title;

    public PassParameters(String artist, String title){
        this.artist = artist;
        this.title = title;
    }


    @Override
    public void run() {

        final Client client = ClientBuilder.newClient();
        final WebTarget target = client.target(DOWNLOAD_MP3_URI);
        final UriBuilder uriBuilder = target.getUriBuilder();

        uriBuilder.queryParam("title", title);
        uriBuilder.queryParam("artist", artist);
        final URI uri = uriBuilder.build();
        final WebTarget webTarget = client.target(uri);
        Response response = webTarget.request().get();


        if(response.getStatus() == 200){
            InputStream inputStream = (InputStream) response.getEntity();
            if (inputStream != null) {
                String filename = response.getHeaderString("filename");
                File downloadDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + filename);
                try {
                    FileUtils.copyInputStreamToFile(inputStream, downloadDir);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }else{
            //// TODO: 08.09.16 IMPLEMENT MESSAGE "wrong status response - server fault"
        }

    }
}
