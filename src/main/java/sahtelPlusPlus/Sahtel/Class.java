package sahtelPlusPlus.Sahtel;

import java.util.ArrayList;

public class Class {
    private int Id;
    private String Name;

    public Class(int id, String name) {
        Id = id;
        Name = name;
    }

    public static ArrayList<Class> Parse(String input) {
        input = input.replaceAll("(?s)<!DOCTYPE html>.*?<option value=\"0\">- Kursus -</option><option value=\"(.*?)<br>.*?</html>", "$1");

        String[] rows = input.split("<\\/option><option value=\"");

        ArrayList<Class> teachers = new ArrayList<>();
        for (String row:rows) {
            String[] data = row.split("\">");
            teachers.add(new Class(Integer.parseInt(data[0]), data[1]));
        }
        return teachers;
    }

    public int getId() { return Id; }
    public void setId(int id) { Id = id; }

    public String getName() { return Name; }
    public void setName(String name) { Name = name; }
}
