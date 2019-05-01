package hkSystem.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import hkSystem.Internal.User;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.util.Map;

@RestController
public class UserController {
    @RequestMapping("/user/authenticate")
    public ResponseEntity<String> Authenticate(@RequestBody Map<String, Object> payload) {
        try {
            ObjectWriter ow = new ObjectMapper().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();

            JSONObject request = new JSONObject(payload);

            User user;

            System.out.println(request.toString());
            System.out.println(request.has("googleToken"));
            System.out.println(request.getString("googleToken"));
            System.out.println("--------------------");

            if(request.has("googleToken")) user = new User(request.getString("googleToken"), null);
            else return ResponseEntity.status(404).body("googleToken can't be null");

            user.authenticate(user.AUTH_GOOGLE_TOKEN);

            if (user.getAuth()) return ResponseEntity.ok(ow.writeValueAsString(user.getToken()));
            else return ResponseEntity.status(404).body("Failed to authenticate the user");

        } catch (Exception e) {
            return ErrorResponse.Response(e, "Failed to authenticate user");
        }
    }

    @RequestMapping("/user/newtoken")
    public ResponseEntity<String> newToken(@RequestBody Map<String, Object>  payload) {
        try {
            ObjectWriter ow = new ObjectMapper().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();

            JSONObject request = new JSONObject(payload);

            User user;

            if(request.has("token")) user = new User(null, request.getString("token"));
            else return ResponseEntity.status(404).body("Token can't be null");

            user.authenticate(user.AUTH_TOKEN);
            if (!user.getAuth()) return  ResponseEntity.status(401).body("Invalid token");

            user.newToken();

            return ResponseEntity.status(200).body(user.getToken());
        } catch (Exception e) {
            return ErrorResponse.Response(e, "Failed to get new token");
        }
    }
}
