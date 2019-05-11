package sahtelPlusPlus.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import sahtelPlusPlus.Sahtel.API;
import sahtelPlusPlus.Sahtel.Class;
import sahtelPlusPlus.Sahtel.Room;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class ClassController {
    @RequestMapping("/class")
    public ResponseEntity<String> classes() {
        try {
            ObjectWriter ow = new ObjectMapper().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();

            API api = new API();
            ArrayList<Class> classes = api.getClas();

            return ResponseEntity.ok(ow.writeValueAsString(classes));
        } catch (Exception e) {
            return ErrorResponse.Response(e, "Failed to get teachers");
        }
    }
}
