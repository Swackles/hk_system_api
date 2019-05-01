package hkSystem.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import hkSystem.Sahtel.API;
import hkSystem.Sahtel.Klass;
import hkSystem.Sahtel.Tunniplaan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class TunniplaanController {
    @RequestMapping("/tunniplaan")
    public ResponseEntity<String> Tunniplaan() {
        try {
            ObjectWriter ow = new ObjectMapper().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();

            API api = new API();
            ArrayList<Klass> tunniplaan = api.getTunniplaan();

            return ResponseEntity.ok(ow.writeValueAsString(tunniplaan));
        } catch (Exception e) {
            return ErrorResponse.Response(e, "Failed to get tunniplaan");
        }
    }
}
