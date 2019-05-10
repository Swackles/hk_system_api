package sahtelPlusPlus.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import sahtelPlusPlus.Sahtel.API;
import sahtelPlusPlus.Sahtel.Subject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class SubjectController {
    @RequestMapping("/subject")
    public ResponseEntity<String> Teacher() {
        try {
            ObjectWriter ow = new ObjectMapper().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();

            API api = new API();
            ArrayList<Subject> subjects = api.getSubject();

            return ResponseEntity.ok(ow.writeValueAsString(subjects));
        } catch (Exception e) {
            System.out.println(e);
            return ErrorResponse.Response(e, "Failed to get subjects");
        }
    }
}
