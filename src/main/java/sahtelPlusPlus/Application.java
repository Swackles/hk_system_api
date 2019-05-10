package sahtelPlusPlus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Application {
    private static final Map<String, String> envVariables  = new HashMap<>();
    static {
        envVariables.put("ENV", "Current environment: ");
        envVariables.put("CLIENT_ID", "Google client id: ");
        envVariables.put("DATABASE_URL", null);
        envVariables.put("POSTGRES_USER", null);
        envVariables.put("POSTGRES_PASSWORD", null);
        envVariables.put("POSTGRES_DB", null);
    }

    public static void main(String[] args) {
        try {
            environmentAuthentication();
        } catch (IOException e) {
            System.out.println(e);
            Runtime.getRuntime().exit(404);
        }

        SpringApplication.run(Application.class, args);
    }

    private static void environmentAuthentication() throws IOException {

        for (Map.Entry<String, String> env:envVariables.entrySet()) {
            if (System.getenv(env.getKey()) != null && env.getValue() != null) System.out.println(env.getValue() + System.getenv(env.getKey()));
            else if (System.getenv(env.getKey()) != null) System.out.println(env.getKey()+ " is set");
            else throw new IOException("Environment variable \"" +env.getKey()+ "\" dose not exist!");
        }
    }
}
