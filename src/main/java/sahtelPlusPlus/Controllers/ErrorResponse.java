package sahtelPlusPlus.Controllers;

import org.springframework.http.ResponseEntity;

public class ErrorResponse {
    public static ResponseEntity Response(Exception e, String msg) {
        String env = System.getenv("ENV");

        String conOutPut = String.format("===============; ERROR ;===============\n%s\n===============; ERROR ;===============", e);
        System.out.println(conOutPut);

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
