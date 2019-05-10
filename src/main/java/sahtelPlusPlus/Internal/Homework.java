package sahtelPlusPlus.Internal;

import java.io.IOException;
import java.sql.Timestamp;

public class Homework {
    private long Id;
    private int UserId;
    private final int ClassId;
    /*
     * 0 - Homework
     * 1 - Test
     * 2 - Exam
     */
    private byte Status;
    private Timestamp DateTime;
    private String Description;

    private final HomeworkDatabase Database = new HomeworkDatabase();

    /**
     * Initialize new homework class
     * @param id - The id in the database
     * @param classId - The id of the class it goes to
     */
    Homework(long id, int classId) {
        this.Id = id;
        this.ClassId = classId;
    }

    public Homework(int classId) {
        this.ClassId = classId;
    }

    public long getId() { return this.Id; }
    public void setId(long id) { this.Id = id; }

    int getClassId() { return this.ClassId; }

    int getUserId() { return this.UserId; }
    void setUserId(int userID) { this.UserId = userID; }

    byte getStatus() { return this.Status; }
    void setStatus(byte status) throws IOException {
        String[] statusString = { "Homework", "Test", "Exam"};

        if (status < statusString.length) {
            this.Status = status;
        } else {
            throw new IOException("Invalid status for homework");
        }
    }

    Timestamp getDateTime() { return this.DateTime; }
    void setDateTime(Timestamp dateTime) { this.DateTime = dateTime; }

    String getDescription() { return this.Description; }
    void setDescription(String description) { this.Description = description; }
}
