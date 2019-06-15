package sahtelPlusPlus.Internal;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.*;
import java.util.Collections;

public class User {
    //Properties
    private long Id;
    final private String GoogleToken;
    private long GoogleId;
    private Timestamp Created;
    private boolean Auth;

    private final UserDatabase Database = new UserDatabase();

    public User(String googleToken) {
        this.GoogleToken = googleToken;
    }

    //private methods
    public void authenticate() throws IOException, GeneralSecurityException, SQLException, AuthenticationException {
        if (this.GoogleToken == null) throw new IOException("GoogleToken can't be null");

        GoogleIdTokenVerifier vertifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
            .setAudience(Collections.singletonList(System.getenv("CLIENT_ID")))
            .build();

        GoogleIdToken idToken = vertifier.verify(this.GoogleToken);
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            this.Auth = true;
        } else {
            this.Auth = false;
        }
    }

    private void newUser() throws AuthenticationException, SQLException {
        if (!this.Auth) throw new AuthenticationException("This user is not authenticated");

        Database.save(this);
    }

    //get properties
    public long getId() { return this.Id; }
    public String getGoogleToken() { return this.GoogleToken; }
    public Timestamp getCreated() { return this.Created; }
    public boolean getAuth() { return this.Auth; }
    public long getGooleId() { return this.GoogleId; }

    //save properties
    void setId(long id) { this.Id = id; }
    void setCreated(Timestamp created) { this.Created = created; }
    void setAuth(Boolean auth) { this.Auth = auth; }
    void setGoogleId(long googleId) { this.GoogleId = googleId; }

    //Exception
    public class AuthenticationException extends Exception {
        AuthenticationException(String msg){
            super(msg);
        }
    }
}
