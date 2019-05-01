package hkSystem.Internal;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Collections;

public class User {
    //Properties
    private long Id;
    private String GoogleToken;
    private String Token;
    private Timestamp Created;
    private boolean Auth;
    public final int AUTH_GOOGLE_TOKEN = 0;
    public final int AUTH_TOKEN = 1;

    private final UserDatabase Database = new UserDatabase();

    //Constructors
    User() {

    }

    public User(String googleToken, String token) throws Exception {
        if (googleToken != null) {
            this.GoogleToken = googleToken;
        }
        else if (token != null) {
            this.Token = token;
        } else {
            throw new IOException("GoogleToken and Token both can't be null");
        }
    }

    //public methods
    public void newToken() throws SQLException {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        this.Token = bytes.toString();

        Database.save(this);
    }

    /**
     * This authenticates the user
     * @param auth What should be authenticated.
     */
    public void authenticate(int auth) throws Exception {
        switch (auth) {
            case 0:
                this.authenticateGoogle();
                break;
            case 1:
                this.authenticateToken();
                break;
            default:
                throw new IOException("Unknown auth param");
        }
    }

    //private methods
    private void authenticateGoogle() throws IOException, GeneralSecurityException, SQLException, AuthenticationException {
        if (this.GoogleToken == null) throw new IOException("GoogleToken can't be null");

        GoogleIdTokenVerifier vertifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
            .setAudience(Collections.singletonList(System.getenv("CLIENT_ID")))
            .build();

        GoogleIdToken idToken = vertifier.verify(this.GoogleToken);
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            this.GoogleToken = payload.getSubject();
            this.Auth = true;

            User user = Database.getByGoogleToken(this.GoogleToken);

            this.Id = user.Id;
            this.Created = user.Created;
            this.Token = user.Token;

            if (!user.Auth) newUser();
        } else {
            this.Auth = false;
        }
    }

    private void authenticateToken() throws IOException, SQLException{
        if (this.Token == null) throw new IOException("Token can't be null");
        User user = Database.getByToken(this.Token);

        this.Auth = user.Auth;

        if (!this.Auth) return;
        else {
            this.Id = user.Id;
            this.GoogleToken = user.GoogleToken;
            this.Created = user.Created;
        }
    }

    private void newUser() throws AuthenticationException, SQLException {
        if (!this.Auth) throw new AuthenticationException("This user is not authenticated");

        newToken();
        Database.save(this);
    }

    //get properties
    public long getId() { return this.Id; }
    public String getGoogleToken() { return this.GoogleToken; }
    public String getToken() { return this.Token; }
    public Timestamp getCreated() { return this.Created; }
    public boolean getAuth() { return this.Auth; }

    //save properties
    void setId(long id) { this.Id = id; }
    void setGoogleToken(String googleToken) { this.GoogleToken = googleToken; }
    void setToken(String token) { this.Token = token; }
    void setCreated(Timestamp created) { this.Created = created; }
    void setAuth(Boolean auth) { this.Auth = auth; }

    //Exception
    public class AuthenticationException extends Exception {
        AuthenticationException(String msg){
            super(msg);
        }
    }
}
