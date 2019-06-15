package sahtelPlusPlus.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import sahtelPlusPlus.Sahtel.API;
import sahtelPlusPlus.Sahtel.Klass;
import sahtelPlusPlus.Sahtel.Tunniplaan;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;

@RestController
public class TunniplaanController {
    @RequestMapping(value = "/tunniplaan", method = RequestMethod.GET)
    public ResponseEntity<String> Tunniplaan() {
        try {
            ObjectWriter ow = new ObjectMapper().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();

            API api = new API();
            ArrayList<Klass> tunniplaan = api.getTunniplaan();
            for (Klass item:tunniplaan) {
            }
            return ResponseEntity.ok(ow.writeValueAsString(tunniplaan));
        } catch (Exception e) {
            return ErrorResponse.Response(e, "Failed to get tunniplaan");
        }
    }

    @RequestMapping(value = "/tunniplaan", method = RequestMethod.POST)
    public ResponseEntity<String> Tunniplaan(@RequestBody Map<String, Object> payload) {
        try {
            ObjectWriter ow = new ObjectMapper().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();

            JSONObject request = new JSONObject(payload);

            Tunniplaan tunniplaan;

            if(!request.has("startDate") || !request.has("endDate") || !request.has("checked") || !request.has("classes") || !request.has("subjects") || !request.has("teachers") || !request.has("rooms")) ErrorResponse.Response(null, "Invalid inputs");

            API api = new API();
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            Calendar cal = Calendar.getInstance();

            cal.setTime(sdf.parse(request.getString("startDate")));
            api.setDateStart(cal);

            cal.setTime((sdf.parse(request.getString("endDate"))));
            api.setDateEnd(cal);

            api.setChecked(request.getBoolean("checked"));

            String[] arr = request.getString("classes").split("-");
            api.setClasses(new ArrayList<>(Arrays.asList(arr)));

            arr = request.getString("subjects").split("-");
            api.setSubjects(new ArrayList<>(Arrays.asList(arr)));

            arr = request.getString("teachers").split("-");
            api.setTeachers(new ArrayList<>(Arrays.asList(arr)));

            arr = request.getString("rooms").split("-");
            api.setRooms(new ArrayList<>(Arrays.asList(arr)));

            return ResponseEntity.ok(ow.writeValueAsString(api.getTunniplaan()));
        } catch (Exception e) {
            return ErrorResponse.Response(e, "Failed to get tunniplaan");
        }
    }
}
