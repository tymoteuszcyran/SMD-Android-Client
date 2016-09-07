package pl.tymoteuszborkowski.smartmusicdownloader.communication;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;

public class PassParameters implements Runnable {


    private static final String PASS_PARAMETERS_URL = "http://10.0.2.2:8080/api/enter";

    private String artist;
    private String title;

    public PassParameters(String artist, String title){
        this.artist = artist;
        this.title = title;
    }


    @Override
    public void run() {

        final Client client = ClientBuilder.newClient();
        final WebTarget target = client.target(PASS_PARAMETERS_URL);
        final UriBuilder uriBuilder = target.getUriBuilder();

        uriBuilder.queryParam("title", title);
        uriBuilder.queryParam("artist", artist);
        final URI uri = uriBuilder.build();
        final WebTarget webTarget = client.target(uri);
        webTarget.request().get();

    }
}
