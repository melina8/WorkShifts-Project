import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TimeOff {
    private String id;
    private LocalDate timeOff;



    public TimeOff() {
    }

    public TimeOff(String id, LocalDate timeOff) {
        this.id = id;
        this.timeOff = timeOff;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getTimeOff() {
        return timeOff;
    }

    public void setTimeOff(LocalDate timeOff) {
        this.timeOff = timeOff;
    }

    @Override
    public String toString() {
        return "TimeOff{" +
                "id='" + id + '\'' +
                ", timeOff=" + timeOff +
                '}';
    }
}


