package hkSystem.Sahtel;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Teacher {
    private int Id;
    private String Name;

    public Teacher(int id, String name) {
        Id = id;
        Name = name;
    }

    public Teacher() {
    }

    public static ArrayList<Teacher> Parse(String input) {
        input = input.replaceAll("<option value=\"0\">- Õppejõud -</option><option value=\"", "");
        String[] rows = input.split("<\\/option><option value=\"");

        ArrayList<Teacher> teachers = new ArrayList<>();
        for (String row:rows) {
            String[] data = row.split("\">");
            teachers.add(new Teacher(Integer.parseInt(data[0]), data[1]));
        }
        return teachers;
    }

    public int getId() { return Id; }
    public void setId(int id) { Id = id; }

    public String getName() { return Name; }
    public void setName(String name) { Name = name; }
}
