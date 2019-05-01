package hkSystem.Internal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

class HomeworkDatabase {

    Homework addHomework(Homework homework) throws Exception {
        Database db = new Database();
        db.execute("INSERT INTO public.homework (user_id, class_id, contents, date) VALUES ('" +homework.getUserId()+ "', '" +homework.getClassId()+ "', '" +homework.getDescription()+ "', '" +homework.getDateTime()+ "');");

        if (db.getUpdatedRows() < 1) throw new SQLException("Couldn't add new homeowork");

        homework.setId(db.getGeneratedId());

        return homework;
    }

    private ArrayList<Homework> AppendDataToHomework(ResultSet rs) throws Exception {
        ArrayList<Homework> homeworks = new ArrayList<>();

        while (rs.next()) {
            Homework homework = new Homework(rs.getInt("id"), rs.getInt("class_id"));
            homework.setDescription(rs.getString("contents"));
            homework.setDateTime(rs.getTimestamp("date"));
            homework.setUserId(rs.getInt("user_id"));
            homeworks.add(homework);
        }

        return homeworks;
    }
}
