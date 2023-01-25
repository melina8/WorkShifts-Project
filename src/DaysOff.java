import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DaysOff {
    private String id;
    private LocalDate dayOff1;
    private LocalDate dayOff2;


    public DaysOff() {
    }

    public DaysOff(String id, LocalDate dayOff1, LocalDate dayOff2) {
        this.id = id;
        this.dayOff1 = dayOff1;
        this.dayOff2 = dayOff2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDayOff1() {
        return dayOff1;
    }

    public void setDayOff1(LocalDate dayOff1) {
        this.dayOff1 = dayOff1;
    }

    public LocalDate getDayOff2() {
        return dayOff2;
    }

    public void setDayOff2(LocalDate dayOff2) {
        this.dayOff2 = dayOff2;
    }

    @Override
    public String toString() {
        return "DaysOff{" +
                "id='" + id + '\'' +
                ", dayOff1=" + dayOff1 +
                ", dayOff2=" + dayOff2 +
                '}';
    }
}


