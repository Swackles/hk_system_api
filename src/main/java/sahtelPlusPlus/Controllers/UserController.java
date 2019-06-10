package sahtelPlusPlus.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.web.bind.annotation.RequestMethod;
import sahtelPlusPlus.Internal.User;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {
    @RequestMapping(value = "/user/auth", method = RequestMethod.POST)
    public ResponseEntity<String> Authenticate(@RequestBody Map<String, Object> payload) {
        try {
            ObjectWriter ow = new ObjectMapper().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();

            JSONObject request = new JSONObject(payload);

            User user;

            if(request.has("googleToken")) user = new User(request.getString("googleToken"));
            else return ResponseEntity.status(404).body("googleToken can't be null");

            user.authenticate();

            return ResponseEntity.ok(ow.writeValueAsString(user));
        } catch (Exception e) {
            return ErrorResponse.Response(e, "Failed to authenticate user");
        }
    }
}
