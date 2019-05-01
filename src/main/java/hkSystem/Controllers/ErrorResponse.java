package hkSystem.Controllers;

import org.springframework.http.ResponseEntity;

import java.lang.reflect.Method;

public class ErrorResponse {
    public static ResponseEntity Response(Exception e, String msg) {
        String env = System.getenv("ENV");
        System.out.println(e);

        switch (env) {
            case "development":
                return ResponseEntity.status(500).body(e);
            case "production":
                return ResponseEntity.status(500).body(msg);
            default:
                return ResponseEntity.status(500).body("Inavlid environment");
        }
    }
}
