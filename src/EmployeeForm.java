import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import java.sql.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;


public class EmployeeForm {
    Stage stage;
    Label lblId, lblName, lblSurName,  lblAddress, lblPhoneNumber, lblEmail, lblDaysOff, lblDaysOff2,
            lblSickLeave, lblSickLeave2, lblSickLeave3,
            lblTimeOff, lblTimeOff2, lblTimeOff3, lblTimeOff4, lblTimeOff5, lblDate, lblDate2, lblFromDate, lblFromDate2, lblToDate, lblToDate2,
            lblDeactivated, lblTitle, reason;
    TextField txtId, txtName, txtSurName,  txtAddress, txtPhoneNumber, txtEmail, notes;
    TextArea taDaysOff, taTimeOff, taTimeOff2;
    CheckBox cb;
    ComboBox<String> combo1st, combo2nd;
    DatePicker datepicker1, datepicker2, datepicker3, datepicker4, datepicker5, datepicker6;

    ListView<Employee> listEmployees;
    Button  btnAddDays, btnAddDays2, btnAddDays3, btnShowAllDaysOff, btnShowAllSickLeave, btnShowAllTimeOff, btnSetDaysOff;
    Button  btnNew, btnEdit, btnDelete;
    Button btnSave, btnCancel;
    HBox hboxTimeOff,hboxTimeOff2,hboxTimeOff3, hboxDaysOff, hboxDeactivated, hBoxSaveCancel;
    String state;

    SortedSet<LocalDate> timeOffList = new TreeSet<LocalDate>();
    SortedSet<LocalDate> timeOffList2 = new TreeSet<LocalDate>();


    Connection connection;

    public EmployeeForm(Connection conn) {

        connection = conn;

        //grid
        GridPane grid = new GridPane();
        grid.setHgap(11);
        grid.setVgap(13);

        //label
        lblTitle = new Label();
        lblTitle.setFont(Font.font(null, FontWeight.BOLD, 15));


        grid.add(lblTitle, 1, 3);
        GridPane.setHalignment(lblTitle, HPos.RIGHT);
        GridPane.setValignment(lblTitle, VPos.CENTER);

        //id
        lblId = new Label("Id: ");
        txtId = new TextField();

        grid.add(lblId, 1, 5);
        GridPane.setHalignment(lblId, HPos.RIGHT);
        GridPane.setValignment(lblId, VPos.CENTER);

        grid.add(txtId, 2, 5);
        GridPane.setHalignment(txtId, HPos.LEFT);
        GridPane.setValignment(txtId, VPos.CENTER);


        //name
        lblName = new Label("Name: ");
        txtName = new TextField();

        grid.add(lblName, 1, 6);
        GridPane.setHalignment(lblName, HPos.RIGHT);
        GridPane.setValignment(lblName, VPos.CENTER);

        grid.add(txtName, 2, 6);
        GridPane.setHalignment(txtName, HPos.LEFT);
        GridPane.setValignment(txtName, VPos.CENTER);

        //surName
        lblSurName = new Label("SurName: ");
        txtSurName = new TextField();

        grid.add(lblSurName, 1, 7);
        GridPane.setHalignment(lblSurName, HPos.RIGHT);
        GridPane.setValignment(lblSurName, VPos.CENTER);

        grid.add(txtSurName, 2, 7);
        GridPane.setHalignment(txtSurName, HPos.LEFT);
        GridPane.setValignment(txtSurName, VPos.CENTER);


        //address
        lblAddress = new Label("Address: ");
        txtAddress = new TextField();

        grid.add(lblAddress, 1, 8);
        GridPane.setHalignment(lblAddress, HPos.RIGHT);
        GridPane.setValignment(lblAddress, VPos.CENTER);

        grid.add(txtAddress, 2, 8);
        GridPane.setHalignment(txtAddress, HPos.LEFT);
        GridPane.setValignment(txtAddress, VPos.CENTER);


        //phoneNumber
        lblPhoneNumber = new Label("Phone Number: ");
        txtPhoneNumber = new TextField();

        grid.add(lblPhoneNumber, 1, 9);
        GridPane.setHalignment(lblPhoneNumber, HPos.RIGHT);
        GridPane.setValignment(lblPhoneNumber, VPos.CENTER);

        grid.add(txtPhoneNumber, 2, 9);
        GridPane.setHalignment(txtPhoneNumber, HPos.LEFT);
        GridPane.setValignment(txtPhoneNumber, VPos.CENTER);


        //email
        lblEmail = new Label("Email: ");
        txtEmail = new TextField();

        grid.add(lblEmail, 1, 10);
        GridPane.setHalignment(lblEmail, HPos.RIGHT);
        GridPane.setValignment(lblEmail, VPos.CENTER);

        grid.add(txtEmail, 2, 10);
        GridPane.setHalignment(txtEmail, HPos.LEFT);
        GridPane.setValignment(txtEmail, VPos.CENTER);


        //daysOff
        lblDate = new Label("Date:");
        datepicker1 = new DatePicker();
        lblDate2 = new Label("Date:");
        datepicker2 = new DatePicker();
        btnSetDaysOff = new Button("Set Repos");
        btnShowAllDaysOff = new Button("Show All");

        lblDaysOff = new Label("DaysOff(current week): ");
        lblDaysOff2 = new Label("DaysOff: ");
        lblDaysOff2.setVisible(false);
        //taDaysOff = new TextArea();

        grid.add(lblDaysOff, 1, 11);
        GridPane.setHalignment(lblDaysOff, HPos.RIGHT);
        GridPane.setValignment(lblDaysOff, VPos.CENTER);
        grid.add(lblDaysOff2, 1, 11);
        GridPane.setHalignment(lblDaysOff2, HPos.RIGHT);
        GridPane.setValignment(lblDaysOff2, VPos.CENTER);
        hboxDaysOff = new HBox();
        hboxDaysOff.getChildren().addAll(lblDate, datepicker1, lblDate2, datepicker2, btnSetDaysOff, btnShowAllDaysOff);
        hboxDaysOff.setSpacing(10);
        hboxDaysOff.setAlignment(Pos.CENTER_LEFT);
        grid.add(hboxDaysOff, 2, 11);


        //Sick leave and timeOff
        lblFromDate = new Label("From Date:");
        lblToDate = new Label("to Date:");
        datepicker3 = new DatePicker();  //sick leave
        datepicker4 = new DatePicker();  //sick leave
        datepicker5 = new DatePicker();  //time off
        datepicker6 = new DatePicker();  //time off

        btnAddDays = new Button("Add Days");
        btnShowAllTimeOff = new Button("Show All");
        taTimeOff = new TextArea();

        lblSickLeave = new Label("Sick leave: ");
        lblSickLeave2 = new Label("Sick leave(left): ");
        lblSickLeave3 = new Label("Sick leave: ");

        btnAddDays2 = new Button("Add Days");
        btnAddDays3 = new Button("Add Days");
        btnShowAllSickLeave = new Button("Show All");
        taTimeOff2 = new TextArea();


        grid.add(lblSickLeave, 1, 12);
        GridPane.setHalignment(lblSickLeave, HPos.RIGHT);
        GridPane.setValignment(lblSickLeave, VPos.CENTER);


        hboxTimeOff = new HBox();
        hboxTimeOff.getChildren().addAll(lblFromDate, datepicker3, lblToDate,datepicker4, btnAddDays, btnShowAllSickLeave);
        hboxTimeOff.setSpacing(5);
        hboxTimeOff.setAlignment(Pos.CENTER_LEFT);
        grid.add(hboxTimeOff, 2, 12);


        grid.add(lblSickLeave3, 1, 13);
        GridPane.setHalignment(lblSickLeave3, HPos.RIGHT);
        GridPane.setValignment(lblSickLeave3, VPos.CENTER);

        grid.add(lblSickLeave2, 1, 13);
        GridPane.setHalignment(lblSickLeave2, HPos.RIGHT);
        GridPane.setValignment(lblSickLeave2, VPos.CENTER);

        grid.add(taTimeOff, 2, 13);
        GridPane.setHalignment(taTimeOff, HPos.LEFT);
        GridPane.setValignment(taTimeOff, VPos.CENTER);

        //Time Off
        lblTimeOff = new Label("Time Off: ");
        lblTimeOff2 = new Label("Select 1st fortnight: ");
        lblTimeOff5 = new Label("2nd fortnight: ");
        lblFromDate2 = new Label("OR SELECT from Date:");
        lblToDate2 = new Label("to Date:");


        grid.add(lblTimeOff, 1, 14);
        GridPane.setHalignment(lblTimeOff, HPos.RIGHT);
        GridPane.setValignment(lblTimeOff, VPos.CENTER);

        combo1st = new ComboBox<String>();
        combo1st.getItems().add("1st January");
        combo1st.getItems().add("2nd January");
        combo1st.getItems().add("1st February");
        combo1st.getItems().add("2nd February");
        combo1st.getItems().add("1st Mars");
        combo1st.getItems().add("2nd Mars");
        combo1st.getItems().add("1st April");
        combo1st.getItems().add("2nd April");
        combo1st.getItems().add("1st May");
        combo1st.getItems().add("2nd May");
        combo1st.getItems().add("1st June");
        combo1st.getItems().add("2nd June");
        combo1st.getItems().add("1st July");
        combo1st.getItems().add("2nd July");
        combo1st.getItems().add("1st August");
        combo1st.getItems().add("2nd August");
        combo1st.getItems().add("1st September");
        combo1st.getItems().add("2nd September");
        combo1st.getItems().add("1st October");
        combo1st.getItems().add("2nd October");
        combo1st.getItems().add("1st November");
        combo1st.getItems().add("2nd November");
        combo1st.getItems().add("1st December");
        combo1st.getItems().add("2nd December");

        combo2nd = new ComboBox<String>();
        combo2nd.getItems().add("1st January");
        combo2nd.getItems().add("2nd January");
        combo2nd.getItems().add("1st February");
        combo2nd.getItems().add("2nd February");
        combo2nd.getItems().add("1st Mars");
        combo2nd.getItems().add("2nd Mars");
        combo2nd.getItems().add("1st April");
        combo2nd.getItems().add("2nd April");
        combo2nd.getItems().add("1st May");
        combo2nd.getItems().add("2nd May");
        combo2nd.getItems().add("1st June");
        combo2nd.getItems().add("2nd June");
        combo2nd.getItems().add("1st July");
        combo2nd.getItems().add("2nd July");
        combo2nd.getItems().add("1st August");
        combo2nd.getItems().add("2nd August");
        combo2nd.getItems().add("1st September");
        combo2nd.getItems().add("2nd September");
        combo2nd.getItems().add("1st October");
        combo2nd.getItems().add("2nd October");
        combo2nd.getItems().add("1st November");
        combo2nd.getItems().add("2nd November");
        combo2nd.getItems().add("1st December");
        combo2nd.getItems().add("2nd December");

        combo1st.setOnAction(e->{
            convertToLocalDate();
        });

        hboxTimeOff2 = new HBox();
        hboxTimeOff2.getChildren().addAll(lblTimeOff2, combo1st, lblTimeOff5, combo2nd, btnAddDays2);
        hboxTimeOff2.setSpacing(5);
        hboxTimeOff2.setAlignment(Pos.CENTER_LEFT);
        grid.add(hboxTimeOff2, 2, 14);

        hboxTimeOff3 = new HBox();
        hboxTimeOff3.getChildren().addAll(lblFromDate2, datepicker5, lblToDate2, datepicker6, btnAddDays3, btnShowAllTimeOff);
        hboxTimeOff3.setSpacing(5);
        hboxTimeOff3.setAlignment(Pos.CENTER_LEFT);
        grid.add(hboxTimeOff3, 2, 15);

        lblTimeOff3 = new Label("Time Off(left): ");
        lblTimeOff4 = new Label("Time Off: ");

        grid.add(lblTimeOff3, 1, 16);
        GridPane.setHalignment(lblTimeOff3, HPos.RIGHT);
        GridPane.setValignment(lblTimeOff3, VPos.CENTER);

        grid.add(lblTimeOff4, 1, 16);
        GridPane.setHalignment(lblTimeOff4, HPos.RIGHT);
        GridPane.setValignment(lblTimeOff4, VPos.CENTER);

        grid.add(taTimeOff2, 2, 16);
        GridPane.setHalignment(taTimeOff2, HPos.LEFT);
        GridPane.setValignment(taTimeOff2, VPos.CENTER);

        lblDeactivated = new Label("Deactivated: ");
        cb = new CheckBox();

        notes = new TextField();
        notes.setPrefWidth(600);
        reason = new Label("reason: ");


        grid.add(lblDeactivated, 1, 17);
        GridPane.setHalignment(lblDeactivated, HPos.RIGHT);
        GridPane.setValignment(lblDeactivated, VPos.CENTER);

        hboxDeactivated = new HBox();
        hboxDeactivated.getChildren().addAll(cb, reason, notes);
        hboxDeactivated.setSpacing(5);
        hboxDeactivated.setAlignment(Pos.CENTER_LEFT);
        grid.add(hboxDeactivated, 2, 17);


        //buttons
        btnShowAllDaysOff.setOnAction(e -> {
            Employee em = listEmployees.getSelectionModel().getSelectedItem();
            String id = em.getId();
            AllDaysOffForm adof = new AllDaysOffForm(conn, id);
        });


        btnShowAllSickLeave.setOnAction(e -> {
            Employee em = listEmployees.getSelectionModel().getSelectedItem();
            String id = em.getId();
            AllSickLeavefForm aslf = new AllSickLeavefForm(conn, id); //Sick Leave
        });

        btnShowAllTimeOff.setOnAction(e -> {
            Employee em = listEmployees.getSelectionModel().getSelectedItem();
            String id = em.getId();
            AllTimeOffForm atof = new AllTimeOffForm(conn, id);
        });


        //Add selected duration between sick leave to the textArea
        btnAddDays.setOnAction(e -> {
            LocalDate fromDate = datepicker3.getValue();
            LocalDate toDate = datepicker4.getValue();

            long days = Duration.between(fromDate.atStartOfDay(), toDate.atStartOfDay()).toDays();
            System.out.println("duration of days(sick leave): " + days);


            timeOffList.clear();
            for (int i=0; i<=days; i++){
                LocalDate newDate = (LocalDate) fromDate.plus(i, ChronoUnit.DAYS);
                timeOffList.add(newDate);
            }
            System.out.println(timeOffList);
            taTimeOff.setText(timeOffList.toString());
        });

        //Add to the textArea, 15 time off days starting from the selected date, plus 15 time off days after 6 months
        btnAddDays2.setOnAction(e -> {

            LocalDate fromDate = convertToLocalDate();
            System.out.println("from date " + fromDate);
            timeOffList2.clear();
            for (int i=0; i<15; i++){
                LocalDate newDate = (LocalDate) fromDate.plus(i, ChronoUnit.DAYS);
                timeOffList2.add(newDate);
                System.out.println("newDate was added... " + newDate);
            }

            LocalDate after6months = fromDate.plus(6,ChronoUnit.MONTHS);
            System.out.println("after 6 months: " + after6months);
            for (int i=0; i<15; i++){
                LocalDate newDate = (LocalDate) after6months.plus(i, ChronoUnit.DAYS);
                timeOffList2.add(newDate);
                System.out.println("newDate was added " + newDate);
            }

            System.out.println(timeOffList2);
            taTimeOff2.setText(timeOffList2.toString());
        });

        //Add selected duration between time off days to the textArea
        btnAddDays3.setOnAction(e -> {
            LocalDate fromDate = datepicker5.getValue();
            LocalDate toDate = datepicker6.getValue();

            long days = Duration.between(fromDate.atStartOfDay(), toDate.atStartOfDay()).toDays();
            System.out.println("duration of days: " + days);


            for (int i=0; i<=days; i++){
                LocalDate newDate = (LocalDate) fromDate.plus(i, ChronoUnit.DAYS);
                timeOffList2.add(newDate);
            }
            System.out.println(timeOffList2);
            taTimeOff2.setText(timeOffList2.toString());
        });

        btnSetDaysOff.setOnAction(p->{


            DaysOff d = new DaysOff();
            d.setId(txtId.getText());
            d.setDayOff1(datepicker1.getValue());
            d.setDayOff2(datepicker2.getValue());

            LocalDate ld1 = d.getDayOff1();
            LocalDate ld2 = d.getDayOff2();
            String repo = "repo";


            LocalDate endOfYear = LocalDate.of(ld1.getYear(), 12, 31);
            while (ld2.isBefore(endOfYear)){

                //insert all of the repos of the year starting with the values selected by the user (1 random day of the week and 1 sunday)
                try {
                    String insQuery = "INSERT INTO repos " +
                            " (emp_id, dayOff1, dayOff2)" +
                            " VALUES(?,?,?)" ;

                    PreparedStatement insStmt = conn.prepareStatement(insQuery);
                    insStmt.setString(1, d.getId());
                    insStmt.setObject(2, ld1);
                    insStmt.setObject(3, ld2);
                    insStmt.executeUpdate();

                    //set program
                    String insQuery2 = "INSERT INTO program " +
                            " (the_day, ws, emp_id)" +
                            " VALUES(?,?,?)" ;

                    PreparedStatement insStmt2 = conn.prepareStatement(insQuery2);
                    insStmt2.setObject(1, ld1);
                    insStmt2.setString(2, repo);
                    insStmt2.setString(3, d.getId());
                    insStmt2.executeUpdate();

                    String insQuery3 = "INSERT INTO program " +
                            " (the_day, ws, emp_id)" +
                            " VALUES(?,?,?)" ;

                    PreparedStatement insStmt3 = conn.prepareStatement(insQuery3);
                    insStmt3.setObject(1, ld2);
                    insStmt3.setString(2, repo);
                    insStmt3.setString(3, d.getId());
                    insStmt3.executeUpdate();


                    insStmt.close();
                    insStmt2.close();
                    insStmt3.close();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                //every week repo goes 1 day after the previous week
                ld1 = (LocalDate) ld1.plus(8, ChronoUnit.DAYS);
                if (ld1.getDayOfWeek() == DayOfWeek.SUNDAY){
                    System.out.println("Sunday: " + ld1);
                    ld1 = (LocalDate) ld1.plus(-6, ChronoUnit.DAYS);
                }

                //constant repo every sunday
                ld2 = (LocalDate) ld2.plus(7, ChronoUnit.DAYS);;

                System.out.println("repo1: " + ld1);
                System.out.println("repo2: " + ld2);
                System.out.println("-------------");

            }

            MessageBoxOK mb = new MessageBoxOK("Repos of the year have been set", "INFORMATION!");
            System.out.println("Repos of the year have been set");
        });



        btnSave = new Button("Save");
        btnSave.setOnAction(e -> {
            if (listEmployees.getItems().size()==0 ){
                System.out.println("Nothing to save...");
            }
            if (validateForm()) {
                System.out.println("Validate: OK");
                if (state.equals("EDIT")) {


                    Employee em = listEmployees.getSelectionModel().getSelectedItem();
                    System.out.println("==================");
                    System.out.println("is Deactivated?: " + em.isDeactivated());


                    String oldValue = em.getId();

                    em.setId(txtId.getText());
                    em.setName(txtName.getText());
                    em.setSurname(txtSurName.getText());
                    em.setAddress(txtAddress.getText().equals("") ? null : txtAddress.getText());
                    em.setPhoneNumber(txtPhoneNumber.getText());
                    em.setEmail(txtEmail.getText().equals("") ? null : txtEmail.getText());
                    em.setDeactivated(cb.isSelected());
                    em.setNotes(notes.getText().equals("") ? null : notes.getText());

                    DaysOff d = new DaysOff();
                    d.setDayOff1(datepicker1.getValue());
                    d.setDayOff2(datepicker2.getValue());


                    try {
                        String updQuery = "UPDATE employee" +
                                " SET emp_id = ?, emp_name = ?, emp_surname = ?, address = ?, " +
                                "     phonenumber = ?, email = ?, deactivate = ?, notes = ? " +
                                " WHERE emp_id = ? ";

                        PreparedStatement updStmt = conn.prepareStatement(updQuery);
                        updStmt.setString(1, em.getId());
                        updStmt.setString(2, em.getName());
                        updStmt.setString(3, em.getSurname());
                        updStmt.setObject(4, em.getAddress());
                        updStmt.setString(5, em.getPhoneNumber());
                        updStmt.setObject(6, em.getEmail());
                        updStmt.setObject(7, em.isDeactivated());
                        updStmt.setString(8, em.getNotes());
                        updStmt.setString(9, oldValue);
                        updStmt.executeUpdate();
                        System.out.println("---------------------");
                        System.out.println("Old Value (emp_id):" + oldValue);

                        updStmt.close();
                    } catch (SQLException throwables) {
                        //throwables.printStackTrace();
                        MessageBoxOK mb = new MessageBoxOK("You cannot edit the employee's id", "WARNING!");
                        System.out.println("You cannot edit the employee's id");
                    }

                    //delete only current week's data and update with the new one
                    LocalDate today = LocalDate.now();
                    LocalDate date1=null;
                    LocalDate date2=null;


                    //find the current week's repo dates
                    Statement stmt = null;
                    try {
                        stmt = conn.createStatement();
                    ResultSet resultSet = stmt.executeQuery("SELECT emp_id, dayOff1, dayOff2 FROM repos WHERE emp_id = '" + oldValue + "' AND  dayOff2 >= '" + today + "' LIMIT 1");

                        while (resultSet.next()){

                            DaysOff dof = new DaysOff(
                                    resultSet.getString("emp_id"),
                                    resultSet.getDate("dayOff1").toLocalDate(),
                                    resultSet.getDate("dayOff2").toLocalDate()
                            );

                            date1 = dof.getDayOff1();
                            date2 = dof.getDayOff2();
                            System.out.println("Dates: " + date1 + " + " + date2);

                            System.out.println("dof: " + dof);

                        }

                    resultSet.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


                   //delete old repos
                    try {
                        String delQuery = "DELETE FROM repos " +
                                        " WHERE emp_id = '" + oldValue + "' AND dayOff1 = '" + date1 + "' AND dayOff2 = '" + date2 + "' " ;

                        PreparedStatement statement = conn.prepareStatement(delQuery);
                        statement.executeUpdate();

                        statement.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }


                    try {
                        String delQuery = "DELETE FROM program " +
                                " WHERE emp_id = '" + oldValue + "' AND ws = 'repo' AND the_day = '" + date1 + "' " ;

                        PreparedStatement statement = conn.prepareStatement(delQuery);
                        statement.executeUpdate();

                        statement.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    try {
                        String delQuery = "DELETE FROM program " +
                                " WHERE emp_id = '" + oldValue + "' AND ws = 'repo' AND the_day = '" + date2 + "' " ;

                        PreparedStatement statement = conn.prepareStatement(delQuery);
                        statement.executeUpdate();

                        statement.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    //insert new repos
                    try {
                        String insQuery = "INSERT INTO repos " +
                                " (emp_id, dayOff1, dayOff2)" +
                                " VALUES(?,?,?)" ;

                        PreparedStatement insStmt = conn.prepareStatement(insQuery);
                        insStmt.setString(1, em.getId());
                        insStmt.setObject(2, datepicker1.getValue());
                        insStmt.setObject(3, datepicker2.getValue());
                        insStmt.executeUpdate();

                        //set program
                        String insQuery2 = "INSERT INTO program " +
                                " (the_day, ws, emp_id)" +
                                " VALUES(?,?,?)" ;

                        PreparedStatement insStmt2 = conn.prepareStatement(insQuery2);
                        insStmt2.setObject(1, datepicker1.getValue());
                        insStmt2.setString(2, "repo");
                        insStmt2.setString(3, em.getId());
                        insStmt2.executeUpdate();

                        String insQuery3 = "INSERT INTO program " +
                                " (the_day, ws, emp_id)" +
                                " VALUES(?,?,?)" ;

                        PreparedStatement insStmt3 = conn.prepareStatement(insQuery3);
                        insStmt3.setObject(1, datepicker2.getValue());
                        insStmt3.setString(2, "repo");
                        insStmt3.setString(3, em.getId());
                        insStmt3.executeUpdate();


                        insStmt.close();
                        insStmt2.close();
                        insStmt3.close();

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }



                    //delete previous data (starting from today) from sickleave and program table and insert edited data
                    try {
                        String delQuery = "DELETE FROM sickLeave " +
                                " WHERE emp_id = '" + oldValue + "' AND sick_leave >= '" + today + "'";

                        PreparedStatement statement = conn.prepareStatement(delQuery);
                        statement.executeUpdate();

                        statement.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    try {
                        String delQuery = "DELETE FROM program " +
                                " WHERE emp_id = '" + oldValue + "' AND ws = 'sick leave'  AND the_day >= '" + today + "'";

                        PreparedStatement statement = conn.prepareStatement(delQuery);
                        statement.executeUpdate();

                        statement.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    //delete previous data (starting from today) from offwork and program table and insert edited data
                    try {
                        String delQuery = "DELETE FROM offwork " +
                                " WHERE emp_id = '" + oldValue + "' AND timeOff >= '" + today + "'";

                        PreparedStatement statement = conn.prepareStatement(delQuery);
                        statement.executeUpdate();

                        statement.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    try {
                        String delQuery = "DELETE FROM program " +
                                " WHERE emp_id = '" + oldValue + "' AND ws = 'time off'  AND the_day >= '" + today + "'";

                        PreparedStatement statement = conn.prepareStatement(delQuery);
                        statement.executeUpdate();

                        statement.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }


                    if (!taTimeOff.getText().equals("")) {
                        String s = taTimeOff.getText();
                        String replace = s.replaceAll("^\\[|]$", "");
                        List<String> myList = new ArrayList<String>(Arrays.asList(replace.split(", ")));
                        System.out.println("myList (sick leave): " + myList);


                        for (int i = 0; i < myList.size(); i++) {
                            try {
                                String insQuery = "INSERT INTO sickLeave " +
                                        " (emp_id, sick_leave)" +
                                        " VALUES(?,?)";

                                PreparedStatement insStmt = conn.prepareStatement(insQuery);
                                insStmt.setString(1, em.getId());
                                insStmt.setObject(2, LocalDate.parse(myList.get(i)));
                                insStmt.executeUpdate();

                                insStmt.close();
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }

                            try {
                                String insQuery = "INSERT INTO program " +
                                        " (the_day, ws, emp_id)" +
                                        " VALUES(?,?,?)";

                                PreparedStatement insStmt = conn.prepareStatement(insQuery);
                                insStmt.setObject(1, LocalDate.parse(myList.get(i)));
                                insStmt.setString(2, "sick leave");
                                insStmt.setString(3, em.getId());
                                insStmt.executeUpdate();

                                insStmt.close();
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }
                    }

                    if (!taTimeOff2.getText().equals("")) {
                        String s = taTimeOff2.getText();
                        String replace = s.replaceAll("^\\[|]$", "");
                        List<String> myList = new ArrayList<String>(Arrays.asList(replace.split(", ")));
                        System.out.println("myList: " + myList);


                        for (int i = 0; i < myList.size(); i++) {
                            try {
                                String insQuery = "INSERT INTO offwork " +
                                        " (emp_id, timeOff)" +
                                        " VALUES(?,?)";

                                PreparedStatement insStmt = conn.prepareStatement(insQuery);
                                insStmt.setString(1, em.getId());
                                insStmt.setObject(2, LocalDate.parse(myList.get(i)));
                                insStmt.executeUpdate();

                                insStmt.close();
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                            try {
                                String insQuery = "INSERT INTO program " +
                                        " (the_day, ws, emp_id)" +
                                        " VALUES(?,?,?)";

                                PreparedStatement insStmt = conn.prepareStatement(insQuery);
                                insStmt.setObject(1, LocalDate.parse(myList.get(i)));
                                insStmt.setString(2, "time off");
                                insStmt.setString(3, em.getId());
                                insStmt.executeUpdate();

                                insStmt.close();
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }
                    }

                    //delete double records (in case for instance repo is same day as sick leave or time off day)
                    try {
                        String delQuery = " delete from program p1 where exists (select dwe from \n" +
                                "                    (select the_day, ws, emp_id as dwe from program p2\n" +
                                "                    where p1.the_day = p2.the_day AND p1.emp_id = p2.emp_id AND p1.ws = 'sick leave' AND p2.ws = 'repo')\n" +
                                "                    as p)";

                        PreparedStatement statement = conn.prepareStatement(delQuery);
                        statement.executeUpdate();

                        statement.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    try {
                        String delQuery = " delete from program p1 where exists (select dwe from \n" +
                                "                    (select the_day, ws, emp_id as dwe from program p2\n" +
                                "                    where p1.the_day = p2.the_day AND p1.emp_id = p2.emp_id AND p1.ws = 'time off' AND p2.ws = 'repo')\n" +
                                "                    as p)";

                        PreparedStatement statement = conn.prepareStatement(delQuery);
                        statement.executeUpdate();

                        statement.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    try {
                        String delQuery = " delete from program p1 where exists (select dwe from \n" +
                                "                    (select the_day, ws, emp_id as dwe from program p2\n" +
                                "                    where p1.the_day = p2.the_day AND p1.emp_id = p2.emp_id AND p1.ws = 'time off' AND p2.ws = 'sick leave')\n" +
                                "                    as p)";

                        PreparedStatement statement = conn.prepareStatement(delQuery);
                        statement.executeUpdate();

                        statement.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }


                    timeOffList.clear();
                    taTimeOff.clear();
                    timeOffList2.clear();
                    taTimeOff2.clear();
                    combo1st.setValue(null);
                    combo2nd.setValue(null);
                    datepicker1.getEditor().clear();
                    datepicker2.getEditor().clear();
                    datepicker3.getEditor().clear();
                    datepicker4.getEditor().clear();
                    datepicker5.getEditor().clear();
                    datepicker6.getEditor().clear();
                    state = "VIEW";
                    changeState(state, em);



                } else if (state.equals("ADD")) {

                    Employee em = new Employee();
                    em.setId(txtId.getText());
                    em.setName(txtName.getText());
                    em.setSurname(txtSurName.getText());
                    em.setAddress(txtAddress.getText().equals("")?null: txtAddress.getText());
                    em.setPhoneNumber(txtPhoneNumber.getText());
                    em.setEmail(txtEmail.getText().equals("")?null: txtEmail.getText());
                    em.setDeactivated((cb.isSelected()));
                    em.setNotes(notes.getText().equals("") ? null: notes.getText());

                    try {
                        String insQuery = "INSERT INTO employee" +
                                " (emp_id, emp_name, emp_surname, address, phonenumber, email, deactivate, notes)" +
                                " VALUES(?,?,?,?,?,?,?,?)" ;

                        PreparedStatement insStmt = conn.prepareStatement(insQuery);
                        insStmt.setString(1, em.getId());
                        insStmt.setString(2, em.getName());
                        insStmt.setString(3, em.getSurname());
                        insStmt.setObject(4, em.getAddress());
                        insStmt.setString(5, em.getPhoneNumber());
                        insStmt.setObject(6, em.getEmail());
                        insStmt.setObject(7, em.isDeactivated());
                        insStmt.setString(8, em.getNotes());
                        insStmt.executeUpdate();

                        insStmt.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    //***************************//
                    //***************************//


                    DaysOff d = new DaysOff();
                    d.setId(txtId.getText());
                    d.setDayOff1(datepicker1.getValue());
                    d.setDayOff2(datepicker2.getValue());


                    String timeOff = "time off";
                    String sickLeave = "sick leave";
                    String repo = "repo";
                    LocalDate ld1 = d.getDayOff1();
                    LocalDate ld2 = d.getDayOff2();


                    LocalDate endOfYear = LocalDate.of(ld1.getYear(), 12, 31);
                    while (ld2.isBefore(endOfYear)){

                        //insert all of the repos of the year starting with the values selected by the user (1 random day of the week and 1 sunday)
                        try {
                            String insQuery = "INSERT INTO repos " +
                                    " (emp_id, dayOff1, dayOff2)" +
                                    " VALUES(?,?,?)" ;

                            PreparedStatement insStmt = conn.prepareStatement(insQuery);
                            insStmt.setString(1, d.getId());
                            insStmt.setObject(2, ld1);
                            insStmt.setObject(3, ld2);
                            insStmt.executeUpdate();

                            //set program
                            String insQuery2 = "INSERT INTO program " +
                                    " (the_day, ws, emp_id)" +
                                    " VALUES(?,?,?)" ;

                            PreparedStatement insStmt2 = conn.prepareStatement(insQuery2);
                            insStmt2.setObject(1, ld1);
                            insStmt2.setString(2, repo);
                            insStmt2.setString(3, d.getId());
                            insStmt2.executeUpdate();

                            String insQuery3 = "INSERT INTO program " +
                                    " (the_day, ws, emp_id)" +
                                    " VALUES(?,?,?)" ;

                            PreparedStatement insStmt3 = conn.prepareStatement(insQuery3);
                            insStmt3.setObject(1, ld2);
                            insStmt3.setString(2, repo);
                            insStmt3.setString(3, d.getId());
                            insStmt3.executeUpdate();


                            insStmt.close();
                            insStmt2.close();
                            insStmt3.close();

                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                        //every week repo goes 1 day after the previous week
                        ld1 = (LocalDate) ld1.plus(8, ChronoUnit.DAYS);
                        if (ld1.getDayOfWeek() == DayOfWeek.SUNDAY){
                            System.out.println("Sunday: " + ld1);
                            ld1 = (LocalDate) ld1.plus(-6, ChronoUnit.DAYS);
                        }

                        //constant repo every sunday
                        ld2 = (LocalDate) ld2.plus(7, ChronoUnit.DAYS);;

                        System.out.println("repo1: " + ld1);
                        System.out.println("repo2: " + ld2);
                        System.out.println("-------------");
                    }


                    //********************************//
                    //********************************//



                    //insert into sickleave table sick leave days
                    if(!taTimeOff.getText().equals("")) {

                        String s = taTimeOff.getText();
                        String replace = s.replaceAll("^\\[|]$", "");
                        List<String> myList = new ArrayList<String>(Arrays.asList(replace.split(", ")));
                        System.out.println("myList (sick leave): " + myList);


                        for (int i = 0; i < myList.size(); i++) {
                            try {
                                String insQuery = "INSERT INTO sickLeave " +
                                        " (emp_id, sick_leave)" +
                                        " VALUES(?,?)";

                                PreparedStatement insStmt = conn.prepareStatement(insQuery);
                                insStmt.setString(1, em.getId());
                                insStmt.setObject(2, LocalDate.parse(myList.get(i)));
                                insStmt.executeUpdate();

                                insStmt.close();
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                            try {
                                String insQuery = "INSERT INTO program " +
                                        " (the_day, ws, emp_id)" +
                                        " VALUES(?,?,?)";

                                PreparedStatement insStmt = conn.prepareStatement(insQuery);
                                insStmt.setObject(1, LocalDate.parse(myList.get(i)));
                                insStmt.setString(2, sickLeave);
                                insStmt.setString(3, em.getId());
                                insStmt.executeUpdate();

                                insStmt.close();
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }
                    }


                    //insert into offwork table timeOff days
                    if(!taTimeOff2.getText().equals("")) {

                        String s = taTimeOff2.getText();
                        String replace = s.replaceAll("^\\[|]$", "");
                        List<String> myList = new ArrayList<String>(Arrays.asList(replace.split(", ")));
                        System.out.println("myList: " + myList);


                        for (int i = 0; i < myList.size(); i++) {
                            try {
                                String insQuery = "INSERT INTO offwork " +
                                        " (emp_id, timeOff)" +
                                        " VALUES(?,?)";

                                PreparedStatement insStmt = conn.prepareStatement(insQuery);
                                insStmt.setString(1, em.getId());
                                insStmt.setObject(2, LocalDate.parse(myList.get(i)));
                                insStmt.executeUpdate();

                                insStmt.close();
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }

                            try {
                                String insQuery = "INSERT INTO program " +
                                        " (the_day, ws, emp_id)" +
                                        " VALUES(?,?,?)";

                                PreparedStatement insStmt = conn.prepareStatement(insQuery);
                                insStmt.setObject(1, LocalDate.parse(myList.get(i)));
                                insStmt.setString(2, timeOff);
                                insStmt.setString(3, em.getId());
                                insStmt.executeUpdate();

                                insStmt.close();
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }
                    }

                    //delete double records (in case for instance repo is same day as sick leave or time off day)
                    try {
                        String delQuery = " delete from program p1 where exists (select dwe from \n" +
                                "                    (select the_day, ws, emp_id as dwe from program p2\n" +
                                "                    where p1.the_day = p2.the_day AND p1.emp_id = p2.emp_id AND p1.ws = 'sick leave' AND p2.ws = 'repo')\n" +
                                "                    as p)";

                        PreparedStatement statement = conn.prepareStatement(delQuery);
                        statement.executeUpdate();

                        statement.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    try {
                        String delQuery = " delete from program p1 where exists (select dwe from \n" +
                                "                    (select the_day, ws, emp_id as dwe from program p2\n" +
                                "                    where p1.the_day = p2.the_day AND p1.emp_id = p2.emp_id AND p1.ws = 'time off' AND p2.ws = 'repo')\n" +
                                "                    as p)";

                        PreparedStatement statement = conn.prepareStatement(delQuery);
                        statement.executeUpdate();

                        statement.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    try {
                        String delQuery = " delete from program p1 where exists (select dwe from \n" +
                                "                    (select the_day, ws, emp_id as dwe from program p2\n" +
                                "                    where p1.the_day = p2.the_day AND p1.emp_id = p2.emp_id AND p1.ws = 'time off' AND p2.ws = 'sick leave')\n" +
                                "                    as p)";

                        PreparedStatement statement = conn.prepareStatement(delQuery);
                        statement.executeUpdate();

                        statement.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    timeOffList.clear();
                    taTimeOff.clear();
                    timeOffList2.clear();
                    taTimeOff2.clear();
                    notes.setText("");
                    combo1st.setValue(null);
                    combo2nd.setValue(null);
                    datepicker1.getEditor().clear();
                    datepicker2.getEditor().clear();
                    datepicker3.getEditor().clear();
                    datepicker4.getEditor().clear();
                    datepicker5.getEditor().clear();
                    datepicker6.getEditor().clear();

                    state = "VIEW";
                    listEmployees.getItems().add(em);
                    listEmployees.getSelectionModel().select(em);
                    changeState(state, em);
                }
            }
        });



        btnCancel = new Button("Cancel");
        btnCancel.setOnAction(e -> {
            state = "VIEW";
            if (listEmployees.getItems().size() ==0){
                System.out.println("No action");
                changeState(state, new Employee());

            }else {
                changeState(state, listEmployees.getSelectionModel().getSelectedItem());
            }
        });
        hBoxSaveCancel = new HBox();
        hBoxSaveCancel.getChildren().addAll(btnSave, btnCancel);
        hBoxSaveCancel.setSpacing(10);
        hBoxSaveCancel.setAlignment(Pos.CENTER);
        grid.add(hBoxSaveCancel, 1, 18);
        GridPane.setRowSpan(hBoxSaveCancel, 2);


        // the listview
        listEmployees = new ListView<>();
        loadDB(conn);
        if(listEmployees.getItems().size()==0){
            System.out.println("Empty list");
            state = "VIEW";

        } else{
            listEmployees.getSelectionModel().selectedItemProperty().
                    addListener((observable, oldValue, newValue) -> {
                        load_form(newValue);
                        System.out.println(oldValue);
                        System.out.println(newValue);
                    });
        }

        grid.add(listEmployees, 0, 0);
        GridPane.setRowSpan(listEmployees, grid.getRowCount() + 1);
        GridPane.setHalignment(listEmployees, HPos.LEFT);


        // buttons
        btnNew = new Button();
        btnNew.setGraphic(new ImageView(new Image("add-icon.png")));
        btnNew.setPrefWidth(listEmployees.getWidth() / 3 - 3);
        btnNew.setOnAction(e -> {
            state = "ADD";
            changeState(state, null);
        });
        btnEdit = new Button();
        btnEdit.setGraphic(new ImageView(new Image("edit_icon.png")));
        btnEdit.setPrefWidth(listEmployees.getWidth() / 3 - 3);
        btnEdit.setOnAction(e -> {
            if (listEmployees.getItems().size()==0){
                System.out.println("Nothing to edit...");
            } else {
                state = "EDIT";
                changeState(state, listEmployees.getSelectionModel().getSelectedItem());
            }
        });
        btnDelete = new Button();
        btnDelete.setGraphic(new ImageView(new Image("delete_icon.png")));
        btnDelete.setPrefWidth(listEmployees.getWidth() / 3 - 3);
        btnDelete.setOnAction(e -> {
            if (listEmployees.getItems().size()==0){
                System.out.println("The list is empty. No data to delete...");
                state = "VIEW";
            } else {
                MessageBoxCancelYes m = new MessageBoxCancelYes("Are you sure you want to\n  delete the selected employee?", "Warning!");
                boolean response = m.getResponse();
                if (response) {

                    Employee em = listEmployees.getSelectionModel().getSelectedItem();
                    listEmployees.getItems().remove(em);

                    if (listEmployees.getItems().size() > 0)
                        listEmployees.getSelectionModel().select(0);
                    else {

                        state = "ADD";
                        changeState(state, null);
                    }

                    try {
                        String delQuery = "DELETE FROM sickLeave " +
                                " WHERE emp_id = '" + em.getId() + "'";

                        PreparedStatement statement = conn.prepareStatement(delQuery);
                        statement.executeUpdate();

                        statement.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    try {
                        String delQuery = "DELETE FROM offwork " +
                                " WHERE emp_id = '" + em.getId() + "'";

                        PreparedStatement statement = conn.prepareStatement(delQuery);
                        statement.executeUpdate();

                        statement.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    try {
                        String delQuery = "DELETE FROM repos " +
                                " WHERE emp_id = '" + em.getId() + "'";

                        PreparedStatement statement = conn.prepareStatement(delQuery);
                        statement.executeUpdate();

                        statement.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    try {
                        String delQuery = "DELETE FROM program " +
                                " WHERE emp_id = '" + em.getId() + "'";

                        PreparedStatement statement = conn.prepareStatement(delQuery);
                        statement.executeUpdate();

                        statement.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    try {
                        String delQuery = "DELETE FROM employee " +
                                " WHERE emp_id = '" + em.getId() + "'";

                        PreparedStatement statement = conn.prepareStatement(delQuery);
                        statement.executeUpdate();

                        statement.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }//else
        });

        //move cursor with enter key
        txtId.setOnKeyPressed( evt ->{
            System.out.println(KeyCode.ENTER + ", " + evt.getCode());
            if(evt.getCode().equals(KeyCode.ENTER)){
                txtName.requestFocus();
            }
        });
        txtName.setOnKeyPressed( evt ->{
            System.out.println(KeyCode.ENTER + ", " + evt.getCode());
            if(evt.getCode().equals(KeyCode.ENTER)){
                txtSurName.requestFocus();
            }
        });
        txtSurName.setOnKeyPressed( evt ->{
            System.out.println(KeyCode.ENTER + ", " + evt.getCode());
            if(evt.getCode().equals(KeyCode.ENTER)){
                txtAddress.requestFocus();
            }
        });
        txtAddress.setOnKeyPressed( evt ->{
            System.out.println(KeyCode.ENTER + ", " + evt.getCode());
            if(evt.getCode().equals(KeyCode.ENTER)){
                txtPhoneNumber.requestFocus();
            }
        });
        txtPhoneNumber.setOnKeyPressed( evt ->{
            System.out.println(KeyCode.ENTER + ", " + evt.getCode());
            if(evt.getCode().equals(KeyCode.ENTER)){
                txtEmail.requestFocus();
            }
        });

        HBox hbox = new HBox();
        hbox.getChildren().addAll(btnNew, btnEdit, btnDelete);
        hbox.setSpacing(50);
        hbox.setAlignment(Pos.TOP_CENTER);
        grid.add(hbox, 2, 0);

        // grid constraints
        GridPane.setColumnSpan(hBoxSaveCancel, 2);
        GridPane.setHalignment(hBoxSaveCancel, HPos.CENTER);
        grid.setPrefWidth(700);
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setPadding(new Insets(5));

        ColumnConstraints col0 = new ColumnConstraints();
        col0.setPrefWidth(200);
        col0.setHalignment(HPos.LEFT);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.SOMETIMES);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.SOMETIMES);

        Region rEmpty = new Region();
        grid.add(rEmpty, 3, 0);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setHgrow(Priority.SOMETIMES);


        grid.getColumnConstraints().addAll(col0, col1, col2, col3);

        // the scene
        Scene scene = new Scene(grid);
        scene.heightProperty().addListener(
                (observable, oldValue, newValue) -> {
                    grid.setPrefHeight(scene.getHeight());
                    grid.setPrefHeight(scene.getHeight());
                });
        scene.widthProperty().addListener(
                (observable, oldValue, newValue) -> {
                    grid.setPrefWidth(scene.getWidth());
                });

        stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Employee");
        stage.setX((Screen.getPrimary().getVisualBounds().getWidth() - 1050) / 2);
        stage.setY((Screen.getPrimary().getVisualBounds().getHeight() - 750) / 2);
        stage.setWidth(1050);
        stage.setHeight(750);
        stage.initModality(Modality.APPLICATION_MODAL);



        if (listEmployees.getItems().size()!=0) {
            listEmployees.getSelectionModel().select(0);
            state = "VIEW";
            changeState(state, listEmployees.getSelectionModel().getSelectedItem());
        } else{
            System.out.println("Data empty");
            state = "VIEW";
            changeState(state, new Employee());
        }
        stage.show();

    }

    private boolean validateForm() {
        String errors = "";
        // Id: 3 characters
        String id = txtId.getText();
        if (id.length() != 3) {
            errors += "Id must be exactly 3 characters\n";
        }

        // Name: <=20 chars and non empty
        String name = txtName.getText();
        if (name.length() > 20) {
            errors += "Name must be <= 20 chars\n";
        }
        if (name.isBlank()) {
            errors += "Name can't be empty\n";
        }

        // Surname: <=20 chars and non empty
        String surname = txtSurName.getText();
        if (surname.length() > 20) {
            errors += "Surname must be <= 20 chars\n";
        }
        if (surname.isBlank()) {
            errors += "Surname can't be empty\n";
        }

        // Address: <=30 chars
        String address = txtAddress.getText();
        if (address.length() > 30) {
            errors += "Address must be <= 30 chars\n";
        }

        // PhoneNumber: =10 chars
        String phone = txtPhoneNumber.getText();
        if (phone.length() != 10) {
            errors += "Phone must be exactly 10 chars\n";
        }

        // Email: <=50 chars
        String email = txtEmail.getText();
        if (email.length() > 50) {
            errors += "Email must be <= 50 chars\n";
        }
        if (errors.length() > 0) {
            MessageBoxOK mb = new MessageBoxOK(errors, "ERRORS");
            return false;
        } else {
            String output =
                    "id " + id + "\n" +
                            "name: " + name + "\n" +
                            "address: " + address + "\n" +
                            "phonenumber: " + phone + "\n" +
                            "email: " + email + "\n";
            System.out.println(output);
            return true;
        }
    }

    public void load_form(Employee em) {
        Statement stmt = null;
        String id = em.getId();
        LocalDate today = LocalDate.now();

        txtId.setText(em.getId());
        txtName.setText(em.getName());
        txtSurName.setText(em.getSurname());
        txtAddress.setText(em.getAddress() == null ? "" : em.getAddress());
        txtPhoneNumber.setText(em.getPhoneNumber());
        txtEmail.setText(em.getEmail() == null ? "" : em.getEmail());
        cb.setSelected(em.isDeactivated());
        notes.setText(em.getNotes() == null ? "" : em.getNotes());


       //load current week's days off
        try {
            datepicker1.setValue(null);
            datepicker2.setValue(null);
            CallableStatement callstmt = connection.prepareCall("call loadDaysOff(?,?)");
            callstmt.setString(1, id);
            callstmt.setObject(2, today);


            ResultSet newresult = callstmt.executeQuery();

            while(newresult.next()){
                DaysOff d = new DaysOff(
                        newresult.getString("emp_id"),
                        newresult.getDate("dayOff1").toLocalDate(),
                        newresult.getDate("dayOff2").toLocalDate()
                );

                String empId = d.getId();
                LocalDate date1 = d.getDayOff1();
                LocalDate date2 = d.getDayOff2();
                datepicker1.setValue(date1);
                datepicker2.setValue(date2);

            }
            newresult.close();
            callstmt.close();

        } catch (SQLException d) {
            d.printStackTrace();
        }

        //load saved sickleave starting from today
        try {
            timeOffList.clear();
            taTimeOff.clear();
            CallableStatement callstmt = connection.prepareCall("call loadSickLeave(?,?)");
            callstmt.setString(1, id);
            callstmt.setObject(2, today);


            ResultSet newresult = callstmt.executeQuery();

            while(newresult.next()){
                TimeOff t = new TimeOff(
                        newresult.getString("emp_id"),
                        newresult.getDate("sick_leave").toLocalDate()
                );

                String empId = t.getId();
                LocalDate date1 = t.getTimeOff();
                timeOffList.add(date1);
                taTimeOff.setText(timeOffList.toString());



            }
            newresult.close();
            callstmt.close();

        } catch (SQLException d) {
            d.printStackTrace();
        }


        //load saved timeOff starting from today
        try {
            timeOffList2.clear();
            taTimeOff2.clear();
            CallableStatement callstmt = connection.prepareCall("call loadTimeOff(?,?)");
            callstmt.setString(1, id);
            callstmt.setObject(2, today);


            ResultSet newresult = callstmt.executeQuery();

            while(newresult.next()){
                TimeOff t = new TimeOff(
                        newresult.getString("emp_id"),
                        newresult.getDate("timeOff").toLocalDate()
                );

                String empId = t.getId();
                LocalDate date1 = t.getTimeOff();
                timeOffList2.add(date1);
                taTimeOff2.setText(timeOffList2.toString());



            }
            newresult.close();
            callstmt.close();

        } catch (SQLException d) {
            d.printStackTrace();
        }



    }


    public void changeState(String state, Employee em){
        switch (state) {
            case "ADD":

                lblTitle.setText("Add Employee");
                txtId.setEditable(true);
                txtName.setEditable(true);
                txtSurName.setEditable(true);
                txtAddress.setEditable(true);
                txtPhoneNumber.setEditable(true);
                txtEmail.setEditable(true);
                notes.setEditable(true);

                txtId.setText("");
                txtName.setText("");
                txtSurName.setText("");
                txtAddress.setText("");
                txtPhoneNumber.setText("");
                txtEmail.setText("");
                notes.setText("");
                cb.setSelected(false);
                taTimeOff.clear();
                taTimeOff2.clear();
                datepicker1.setValue(null);
                datepicker2.setValue(null);
                datepicker3.setValue(null);
                datepicker4.setValue(null);
                datepicker5.getEditor().clear();
                datepicker6.getEditor().clear();
                btnShowAllDaysOff.setVisible(true);
                btnSetDaysOff.setDisable(false);
                btnAddDays.setDisable(false);
                btnAddDays2.setDisable(false);
                btnAddDays3.setDisable(false);
                btnShowAllDaysOff.setDisable(true);
                btnShowAllSickLeave.setDisable(true);
                btnShowAllTimeOff.setDisable(true);
                lblSickLeave2.setVisible(false);
                lblSickLeave3.setVisible(true);
                lblTimeOff3.setVisible(false);
                lblTimeOff4.setVisible(true);
                lblDaysOff.setVisible(false);
                lblDaysOff2.setVisible(true);

                hBoxSaveCancel.setVisible(true);
                btnNew.setDisable(true);
                btnEdit.setDisable(true);
                btnDelete.setDisable(true);
                break;

            case "EDIT":
                lblTitle.setText("Edit Employee");
                txtId.setEditable(true);
                txtName.setEditable(true);
                txtSurName.setEditable(true);
                txtAddress.setEditable(true);
                txtPhoneNumber.setEditable(true);
                txtEmail.setEditable(true);
                notes.setEditable(true);
                datepicker1.setValue(null);
                datepicker2.setValue(null);
                datepicker3.setValue(null);
                datepicker4.setValue(null);
                datepicker5.setValue(null);
                datepicker6.setValue(null);
                datepicker1.setEditable(true);
                datepicker2.setEditable(true);
                datepicker3.setEditable(true);
                datepicker4.setEditable(true);
                btnShowAllDaysOff.setVisible(true);
                btnSetDaysOff.setDisable(false);
                btnAddDays.setDisable(false);
                btnAddDays2.setDisable(false);
                btnAddDays3.setDisable(false);
                btnShowAllDaysOff.setDisable(false);
                btnShowAllSickLeave.setDisable(false);
                btnShowAllTimeOff.setDisable(false);
                lblSickLeave2.setVisible(true);
                lblDaysOff2.setVisible(false);
                lblDaysOff.setVisible(true);
                lblSickLeave2.setVisible(true);
                lblSickLeave3.setVisible(false);
                lblTimeOff3.setVisible(true);
                lblTimeOff4.setVisible(false);

                load_form(em);

                hBoxSaveCancel.setVisible(true);
                btnNew.setDisable(true);
                btnEdit.setDisable(true);
                btnDelete.setDisable(true);
                break;

            case "VIEW":
                lblTitle.setText("View Employee");
                txtId.setEditable(false);
                txtName.setEditable(false);
                txtSurName.setEditable(false);
                txtAddress.setEditable(false);
                txtPhoneNumber.setEditable(false);
                txtEmail.setEditable(false);
                notes.setEditable(false);
                datepicker1.setValue(null);
                datepicker2.setValue(null);
                datepicker3.setValue(null);
                datepicker4.setValue(null);
                datepicker5.setValue(null);
                datepicker6.setValue(null);
                datepicker1.setEditable(false);
                datepicker2.setEditable(false);
                datepicker3.setEditable(false);
                datepicker4.setEditable(false);
                btnShowAllDaysOff.setVisible(true);
                btnSetDaysOff.setDisable(true);
                btnAddDays.setDisable(true);
                btnAddDays2.setDisable(true);
                btnAddDays3.setDisable(true);
                btnShowAllDaysOff.setDisable(false);
                btnShowAllSickLeave.setDisable(false);
                btnShowAllTimeOff.setDisable(false);
                lblSickLeave2.setVisible(true);
                lblDaysOff2.setVisible(false);
                lblDaysOff.setVisible(true);
                lblSickLeave2.setVisible(true);
                lblSickLeave3.setVisible(false);
                lblTimeOff3.setVisible(true);
                lblTimeOff4.setVisible(false);

                load_form(em);

                hBoxSaveCancel.setVisible(false);
                btnNew.setDisable(false);
                btnEdit.setDisable(false);
                btnDelete.setDisable(false);


        }
    }


    public void loadDB(Connection conn) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM employee" +
                    " ORDER BY emp_id");

            //check if table is empty
            if (!resultSet.next()) {
                System.out.println("Table is empty");

            } else {

                do {
                    Employee em = new Employee(
                            resultSet.getString("emp_id"),
                            resultSet.getString("emp_name"),
                            resultSet.getString("emp_surname"),
                            (String) resultSet.getObject("address"),  //in case it's null
                            resultSet.getString("phonenumber"),
                            (String) resultSet.getObject("email"),  //in case it's null
                            resultSet.getBoolean("deactivate"),
                            resultSet.getString("notes")
                    );
                    listEmployees.getItems().add(em);

                } while (resultSet.next());
            }
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public LocalDate convertToLocalDate(){

            String forthnight = combo1st.getValue();

        LocalDate fromDate = switch (forthnight) {
            case "1st January" -> LocalDate.of(LocalDate.now().getYear(), 1, 1);
            case "2nd January" -> LocalDate.of(LocalDate.now().getYear(), 1, 15);

            case "1st February" -> LocalDate.of(LocalDate.now().getYear(), 2, 1);
            case "2nd February" -> LocalDate.of(LocalDate.now().getYear(), 2, 15);

            case "1st Mars" -> LocalDate.of(LocalDate.now().getYear(), 3, 1);
            case "2nd Mars" -> LocalDate.of(LocalDate.now().getYear(), 3, 15);

            case "1st April" -> LocalDate.of(LocalDate.now().getYear(), 4, 1);
            case "2nd April" -> LocalDate.of(LocalDate.now().getYear(), 4, 15);

            case "1st May" -> LocalDate.of(LocalDate.now().getYear(), 5, 1);
            case "2nd May" -> LocalDate.of(LocalDate.now().getYear(), 5, 15);

            case "1st June" -> LocalDate.of(LocalDate.now().getYear(), 6, 1);
            case "2nd June" -> LocalDate.of(LocalDate.now().getYear(), 6, 15);

            case "1st July" -> LocalDate.of(LocalDate.now().getYear(), 7, 1);
            case "2nd July" -> LocalDate.of(LocalDate.now().getYear(), 7, 15);

            case "1st August" -> LocalDate.of(LocalDate.now().getYear(), 8, 1);
            case "2nd August" -> LocalDate.of(LocalDate.now().getYear(), 8, 15);

            case "1st September" -> LocalDate.of(LocalDate.now().getYear(), 9, 1);
            case "2nd September" -> LocalDate.of(LocalDate.now().getYear(), 9, 15);

            case "1st October" -> LocalDate.of(LocalDate.now().getYear(), 10, 1);
            case "2nd October" -> LocalDate.of(LocalDate.now().getYear(), 10, 15);

            case "1st November" -> LocalDate.of(LocalDate.now().getYear(), 11, 1);
            case "2nd November" -> LocalDate.of(LocalDate.now().getYear(), 11, 15);

            case "1st December" -> LocalDate.of(LocalDate.now().getYear(), 12, 1);
            case "2nd December" -> LocalDate.of(LocalDate.now().getYear(), 12, 15);

            default -> null;
        };


            System.out.println("from date " + fromDate);



            if (fromDate.equals(LocalDate.of(LocalDate.now().getYear(), 1, 1)))
                combo2nd.setValue("1st July");
            else if(fromDate.equals(LocalDate.of(LocalDate.now().getYear(), 1, 15)))
                combo2nd.setValue("2nd July");
            else if(fromDate.equals(LocalDate.of(LocalDate.now().getYear(), 2, 1)))
                combo2nd.setValue("1st August");
            else if(fromDate.equals(LocalDate.of(LocalDate.now().getYear(), 2, 15)))
                combo2nd.setValue("2nd August");
            else if(fromDate.equals(LocalDate.of(LocalDate.now().getYear(), 3, 1)))
                combo2nd.setValue("1st September");
            else if(fromDate.equals(LocalDate.of(LocalDate.now().getYear(), 3, 15)))
                combo2nd.setValue("2nd September");
            else if(fromDate.equals(LocalDate.of(LocalDate.now().getYear(), 4, 1)))
                combo2nd.setValue("1st October");
            else if(fromDate.equals(LocalDate.of(LocalDate.now().getYear(), 4, 15)))
                combo2nd.setValue("2nd October");
            else if(fromDate.equals(LocalDate.of(LocalDate.now().getYear(), 5, 1)))
                combo2nd.setValue("1st November");
            else if(fromDate.equals(LocalDate.of(LocalDate.now().getYear(), 5, 15)))
                combo2nd.setValue("2nd November");
            else if(fromDate.equals(LocalDate.of(LocalDate.now().getYear(), 6, 1)))
                combo2nd.setValue("1st December");
            else if(fromDate.equals(LocalDate.of(LocalDate.now().getYear(), 6, 15)))
                combo2nd.setValue("2nd December");



            return fromDate;
    }
}

