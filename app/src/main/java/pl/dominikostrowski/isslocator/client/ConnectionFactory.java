package pl.dominikostrowski.isslocator.client;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class ConnectionFactory {

    @NonNull
    private final URLFactory urlFactory;

    ConnectionFactory(@NonNull final URLFactory urlFactory) {
        this.urlFactory = urlFactory;
    }

    @NonNull
    HttpURLConnection createIssNowRequest() throws IOException {
        final URL url = urlFactory.createURL("http://api.open-notify.org/iss-now.json");
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(2000);
        connection.setReadTimeout(2000);
        connection.setRequestMethod("GET");
        return connection;
    }

    @NonNull
    HttpURLConnection createAstrosRequest() throws IOException {
        final URL url = urlFactory.createURL("http://api.open-notify.org/astros.json");
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(2000);
        connection.setReadTimeout(2000);
        connection.setRequestMethod("GET");
        return connection;
    }
}

class URLFactory {
    @NonNull
    URL createURL(String source) throws MalformedURLException {
        return new URL(source);
    }
}
