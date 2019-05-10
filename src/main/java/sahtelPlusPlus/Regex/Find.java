package sahtelPlusPlus.Regex;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Find {
    private final String Input;
    private final String Start;
    private final String End;
    private String Result;

    public Find(String input, String start, String end) {
        this.Input = input;
        this.Start = start;
        this.End = end;
    }

    public void Parse() throws IOException {
        Pattern pattern = Pattern.compile(this.getStart() + "(.*?)" + this.getEnd());
        Matcher matcher = pattern.matcher(this.getInput());
        if (matcher.find()) {
            this.Result = matcher.group(1);
        } else {
            throw new IOException("sahtelPlusPlus.Regex.Find | Invalid input");
        }
    }

    private String getInput() { return this.Input; }
    private String getStart() { return this.Start; }
    private String getEnd() { return this.End; }
    public String getResult() { return this.Result; }
}
