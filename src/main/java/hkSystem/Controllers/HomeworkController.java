package hkSystem.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import hkSystem.Internal.Homework;
import hkSystem.Internal.User;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeworkController {
    @RequestMapping("/homework/add")
    public ResponseEntity<String> Authenticate(@RequestBody Map<String, Object> payload) {
        try {
            ObjectWriter ow = new ObjectMapper().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();

            JSONObject request = new JSONObject(payload);

            if(!validateInput(request)) return ResponseEntity.status(404).body("Missing arguments");

            User user = new User(null, request.getString("token"));
            user.authenticate(user.AUTH_TOKEN);

            if (!user.getAuth()) return ResponseEntity.status(401).body("Invalid user");

            Homework homework = new Homework(200);

            return ResponseEntity.status(200).body(ow.writeValueAsString(homework));
        } catch (Exception e) {
            return ErrorResponse.Response(e, "Failed to add homework");
        }
    }

    private boolean validateInput(JSONObject obj) {
        return obj.has("token")
                && obj.has("classId")
                && obj.has("status")
                && obj.has("description")
                && obj.has("dateTime");
    }

}
