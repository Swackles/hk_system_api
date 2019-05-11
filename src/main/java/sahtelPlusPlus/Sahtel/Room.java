package sahtelPlusPlus.Sahtel;

import java.util.ArrayList;

public class Room {
    private int Id;
    private String Name;

    public Room(int id, String name) {
        Id = id;
        Name = name;
    }

    public static ArrayList<Room> Parse(String input) {
        input = input.replaceAll("(?s)<!DOCTYPE html>.*?<option value=\"0\">- Ruum -</option><option value=\"(.*?)<br>.*?</html>", "$1");

        String[] rows = input.split("<\\/option><option value=\"");

        ArrayList<Room> teachers = new ArrayList<>();
        for (String row:rows) {
            String[] data = row.split("\">");
            teachers.add(new Room(Integer.parseInt(data[0]), data[1]));
        }
        return teachers;
    }

    public int getId() { return Id; }
    public void setId(int id) { Id = id; }

    public String getName() { return Name; }
    public void setName(String name) { Name = name; }
}
