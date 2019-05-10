package sahtelPlusPlus.Sahtel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Subject {
    private int Id;
    private String Name;
    private String Code;

    Subject(int id, String name, String code) {
        Id = id;
        Name = name;
        Code = code;
    }

    private static Matcher ParseNameCode(String input) throws IOException {
        Pattern pattern = Pattern.compile("<option value=\"(.*?)\">(.*?)\\((.*?)\\)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher;
        } else {
            throw new IOException("sahtelPlusPlus.Sahtel.Subject | Invalid input");
        }
    }

    static ArrayList<Subject> Parse(String input) throws IOException {
        input = input.replaceAll("<option value=\"0\">- Aine -</option>", "");
        String[] rows = input.split("<\\/option>");

        ArrayList<Subject> subjects = new ArrayList<>();

        for (String row:rows) {
            Matcher matcher = ParseNameCode(row);
            Subject subject = new Subject(Integer.parseInt(matcher.group(1)), matcher.group(2), matcher.group(3));

            subjects.add(subject);
        }
        return subjects;
    }

    public void setId(int id) { Id = id; }
    public int getId() { return Id; }

    public void setName(String name) { Name = name; }
    public String getName() { return Name; }

    public void setCode(String code) { Code = code; }
    public String getCode() { return Code; }
}
