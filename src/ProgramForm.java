import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.util.Pair;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
//import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.text.DecimalFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;



public class ProgramForm {
    Stage stage;
    Label lblEmp_id, lblEmpName, lblEmpSurName;
    TextField txtEmpName, txtEmpSurname;
    ComboBox<String> comboEmpId;

    Button btnGo, btnSave, btnExportToExcel;
    HBox hBoxEmployee, hBoxTable, hBoxSaveExport;
    String month, monthMinus1, resultEmpId;
    int index;

    ArrayList<WorkShifts> wshifts = new ArrayList<WorkShifts>();
    ArrayList<String> wshifts2 = new ArrayList<String>();
    ArrayList<String> wshifts2Temp = new ArrayList<String>();
    ArrayList<WorkShifts> wshifts3 = new ArrayList<WorkShifts>();
    ArrayList<String> employees = new ArrayList<String>();
    ArrayList<String> employees2 = new ArrayList<String>();

    ArrayList<Pair<LocalDate, Pair<String, String>>> pairs = new ArrayList<>();
    TreeMap<String, ArrayList<String>> treeMap = new TreeMap<>();
    ArrayList<String> months = new ArrayList<>();
   // Map.Entry<LocalDate, String> entry = new AbstractMap.SimpleEntry<LocalDate, String>();

    ObservableList<String> wsList = FXCollections.observableArrayList();

    TableView<TableViewProgram> tableView;
    SortedSet<LocalDate> timeOffList = new TreeSet<LocalDate>();

    TableColumn<TableViewProgram, LocalDate> dayColumn;
    TableColumn<TableViewProgram, String> wsColumn;
    TableColumn<TableViewProgram, String> empColumn;
    TableColumn<TableViewProgram, String> nameColumn;
    TableColumn<TableViewProgram, String> surnameColumn;

    ObservableList<TableViewProgram> programPairsList = FXCollections.observableArrayList();


    ArrayList<String> numbers = new ArrayList<>();
    ArrayList<String> numbers2 = new ArrayList<>();

    Connection connection;

    public ProgramForm(Connection conn, String month) {

        connection = conn;

        months.add("January");
        months.add("February");
        months.add("Mars");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");

        this.month = month;
        if (month.equals("January")){
             index = months.indexOf(month);
        }
         else {
             index = months.indexOf(month)-1;
        }
         monthMinus1 = months.get(index);
         System.out.println("monthMinus1"+ monthMinus1);


        for (int i = 1; i < 25; i++) {
            if (i < 10) {
                numbers.add("0" + i);
            } else {
                numbers.add(String.valueOf(i));
            }
        }

        for (int i = 0; i < 60; i++) {
            if (i < 10) {
                numbers2.add("0" + i);
            } else {
                numbers2.add(String.valueOf(i));
            }
        }


        //grid
        GridPane grid = new GridPane();
        grid.setHgap(11);
        grid.setVgap(13);


        lblEmp_id = new Label("Select Program by Employee Id:");
        comboEmpId = new ComboBox<String>();
        comboEmpId.setEditable(true);
        new AutoCompleteComboBoxListener<>(comboEmpId);
        comboEmpId.setPrefWidth(90);
        comboEmpId.setValue("All*");



        lblEmpName = new Label("Name:");
        txtEmpName = new TextField();
        txtEmpName.setPrefWidth(200);

        lblEmpSurName = new Label("Surname:");
        txtEmpSurname = new TextField();
        txtEmpSurname.setPrefWidth(200);

        btnGo = new Button("Go");

        btnSave = new Button("Save changes");

        btnExportToExcel = new Button("Export to Excel");


        hBoxEmployee = new HBox();
        hBoxEmployee.getChildren().addAll(lblEmp_id, comboEmpId, lblEmpName, txtEmpName, lblEmpSurName, txtEmpSurname, btnGo);
        hBoxEmployee.setSpacing(7);
        hBoxEmployee.setAlignment(Pos.CENTER);
        hBoxEmployee.setMinHeight(20);


        //fill the combobox - employee id and fill the employee name and surname automatically
        try {
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM employee ORDER BY emp_id");

            comboEmpId.getItems().add("All*");
            while (resultSet.next()) {


                Employee e = new Employee(
                        resultSet.getString("emp_id"),
                        resultSet.getString("emp_name"),
                        resultSet.getString("emp_surname"),
                        resultSet.getString("address"),
                        resultSet.getString("phonenumber"),
                        resultSet.getString("email"),
                        resultSet.getBoolean("deactivate"),
                        resultSet.getString("notes")
                );

                comboEmpId.getItems().add(e.getId());



                comboEmpId.setOnAction(z -> {

                            if (comboEmpId.getValue().equals("All*")) {
                                txtEmpName.setText("");
                                txtEmpSurname.setText("");
                                System.out.println("All employees were selected");

                            } else {
                                loadNameSurname();
                            }
                        }
                );


            }
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        btnExportToExcel.setOnAction(z -> {

                   export(tableView);
                   System.out.println("export completed");
                }
        );


        //tableView
        dayColumn = new TableColumn<>("Date");
        dayColumn.setCellValueFactory(new PropertyValueFactory<>("the_day"));
        dayColumn.setMinWidth(150);

        wsColumn = new TableColumn<>("Workshift");
        wsColumn.setCellValueFactory(new PropertyValueFactory<>("ws"));
        wsColumn.setMinWidth(150);

        empColumn = new TableColumn<>("Employee");
        empColumn.setCellValueFactory(new PropertyValueFactory<>("emp_id"));
        empColumn.setMinWidth(100);

        nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setMinWidth(200);

        surnameColumn = new TableColumn<>("Surname");
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        surnameColumn.setMinWidth(250);


        tableView = new TableView<>();

        tableView.getColumns().add(dayColumn);
        tableView.getColumns().add(wsColumn);
        tableView.getColumns().add(empColumn);
        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(surnameColumn);


        //dayColumn.setCellFactory(ComboBoxTableCell.forTableColumn());
        wsColumn.setCellFactory(ComboBoxTableCell.forTableColumn(wsList));
        //empColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        tableView.setPrefWidth(1200);
        tableView.setPrefHeight(600);
        tableView.setEditable(true);

        try {
            CallableStatement callstmt = connection.prepareCall("call loadWorkShifts()");

            ResultSet newresult = callstmt.executeQuery();

            while (newresult.next()) {
                wshifts3.add(new WorkShifts(
                        newresult.getTime("from_hour").toLocalTime(),
                        newresult.getTime("to_hour").toLocalTime(),
                        newresult.getInt("noOfEmp")));
                //tableView.setItems(wsList);

            }


            newresult.close();
            callstmt.close();

            //System.out.println("wshifts: " + wshifts);

            for (int i = 0; i < wshifts3.size(); i++) {
                wsList.add(String.valueOf(wshifts3.get(i).getFromHour() + "-" + wshifts3.get(i).getToHour()));
            }
            System.out.println("*=============================*");
            System.out.println("wsList: " + wsList);

        } catch(SQLException d){
            d.printStackTrace();
        }


        wsColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TableViewProgram, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<TableViewProgram, String> event) {
                System.out.println("row changed: ------------------");

                TableViewProgram p = tableView.getSelectionModel().getSelectedItem();
                p.setWs(event.getNewValue());
                //tableView.getItems().add(tableView.getSelectionModel().getSelectedIndex(), p);

           }
        });



        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        hBoxTable = new HBox();
        hBoxTable.getChildren().addAll(tableView);
        hBoxTable.setPadding(new Insets(50, 50, 50, 50));
        hBoxTable.setAlignment(Pos.CENTER);

        hBoxSaveExport = new HBox();
        hBoxSaveExport.getChildren().addAll(btnSave, btnExportToExcel);
        hBoxSaveExport.setSpacing(10);
        hBoxSaveExport.setAlignment(Pos.CENTER);
        hBoxSaveExport.setMinHeight(10);


        VBox pane = new VBox();
        pane.getChildren().addAll(hBoxEmployee, hBoxTable, hBoxSaveExport);
        pane.setPadding((new Insets(10, 10, 10, 10)));
        pane.setAlignment(Pos.CENTER);

        grid.getChildren().add(pane);



        LocalDate initial = switch (month) {
            case "January" -> LocalDate.of(LocalDate.now().getYear(), 1, 1);
            case "February" -> LocalDate.of(LocalDate.now().getYear(), 2, 1);
            case "Mars" -> LocalDate.of(LocalDate.now().getYear(), 3, 1);
            case "April" -> LocalDate.of(LocalDate.now().getYear(), 4, 1);
            case "May" -> LocalDate.of(LocalDate.now().getYear(), 5, 1);
            case "June" -> LocalDate.of(LocalDate.now().getYear(), 6, 1);
            case "July" -> LocalDate.of(LocalDate.now().getYear(), 7, 1);
            case "August" -> LocalDate.of(LocalDate.now().getYear(), 8, 1);
            case "September" -> LocalDate.of(LocalDate.now().getYear(), 9, 1);
            case "October" -> LocalDate.of(LocalDate.now().getYear(), 10, 1);
            case "November" -> LocalDate.of(LocalDate.now().getYear(), 11, 1);
            case "December" -> LocalDate.of(LocalDate.now().getYear(), 12, 1);
            default -> null;
        };

        LocalDate end = initial.withDayOfMonth(initial.getMonth().length(initial.isLeapYear()));


        btnGo.setOnAction(e-> {
            if (comboEmpId.getValue().equals("All*")){
                loadDB(connection, initial, end);
            }else {
                loadProgramById(connection, initial, end);
            }
                });


        btnSave.setOnAction(e-> {
            //delete previous data...


            if (!comboEmpId.getValue().equals("All*")){
                String empId = comboEmpId.getValue();
                try {
                    String delQuery = "DELETE FROM program " +
                            " WHERE emp_id = '" + empId + "' AND the_day >= '" + initial + "' AND the_day <= '" + end + "' ";

                    PreparedStatement statement = conn.prepareStatement(delQuery);
                    statement.executeUpdate();

                    statement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            else {
                try {
                    String delQuery = "DELETE FROM program " +
                            " WHERE the_day >= '" + initial + "' AND the_day <= '" + end + "' ";

                    PreparedStatement statement = conn.prepareStatement(delQuery);
                    statement.executeUpdate();

                    statement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }


            //and insert the new one
            for (int i = 0; i < tableView.getItems().size(); i++) {
                Program pr = new Program();

                pr.setThe_day(dayColumn.getCellData(i));
                System.out.println(dayColumn.getCellData(i));

                pr.setWs(wsColumn.getCellData(i));
                System.out.println(wsColumn.getCellData(i));

                pr.setEmp_id(empColumn.getCellData(i));
                System.out.println(empColumn.getCellData(i));

                try {
                    String insQuery = "INSERT INTO program" +
                            " (the_day, ws, emp_id)" +
                            " VALUES(?,?,?)";

                    PreparedStatement insStmt = conn.prepareStatement(insQuery);
                    insStmt.setObject(1, pr.getThe_day());
                    insStmt.setString(2, pr.getWs());
                    insStmt.setString(3, pr.getEmp_id());

                    //System.out.println("Values are: " + pr.getThe_day() + " " +  pr.getWs()+ " " + pr.getEmp_id());
                    insStmt.executeUpdate();

                    insStmt.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

        });


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
        stage.setTitle("Program");
        stage.setX((Screen.getPrimary().getVisualBounds().getWidth() - 1000) / 2);
        stage.setY((Screen.getPrimary().getVisualBounds().getHeight() - 800) / 2);
        stage.setWidth(1000);
        stage.setHeight(800);
        stage.initModality(Modality.APPLICATION_MODAL);


        try {
            CallableStatement callstmt = connection.prepareCall("call loadChecked()");

            ResultSet newresult = callstmt.executeQuery();

            while (newresult.next()) {

               String checked_month =  newresult.getString("checked_month");
               String empList = newresult.getString("empList");
               String replace = empList.replaceAll(" ", "");
               String replace2 = replace.replaceAll("^\\[|]$", "");
               treeMap.put(checked_month, new ArrayList<String>(Arrays.asList(replace2.split(","))));
            }

            newresult.close();
            callstmt.close();

            //System.out.println("Employees: " + employees);
            System.out.println("treeMap: " + treeMap);


        } catch (SQLException d) {
            d.printStackTrace();
        }



        //if the program for a specific month already exists, the user has the ability to produce it again
        // in case for example a workshift, a repo or a list of employees changes
        if(treeMap.get(month) != null) {
            MessageBoxOldNew m = new MessageBoxOldNew("Program for this month has already been produced.\n" +
                    "Do you want to keep old program or replace it with a new one?", "WARNING!");
            boolean response = m.getResponse();
            if (response) {

                //delete previous data and insert the new one
                try {
                    String delQuery = "DELETE FROM program " +
                            " WHERE the_day >= '" + initial + "' AND the_day <= '" + end + "' AND ws != 'repo' AND ws != 'sick leave' AND ws != 'time off'";

                    PreparedStatement statement = conn.prepareStatement(delQuery);
                    statement.executeUpdate();

                    statement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                try {
                    String delQuery = "DELETE FROM checkedMonths_andList " +
                            " WHERE checked_month = '" + month + "'";

                    PreparedStatement statement = conn.prepareStatement(delQuery);
                    statement.executeUpdate();

                    statement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                loadEmployees();
                loadWorkShifts();
                calculateMonth(month);

            }
            else{
                loadDB(conn, initial, end);
            }
        }
        else {

            loadEmployees();
            loadWorkShifts();
            calculateMonth(month);
        }

        stage.show();
    }




    public void loadEmployees() {

        Statement stmt = null;

        try {
            CallableStatement callstmt = connection.prepareCall("call loadEmployees()");

            ResultSet newresult = callstmt.executeQuery();

            while (newresult.next()) {

                employees2.add(newresult.getString("emp_id"));


            }

            if (treeMap.get(monthMinus1) != null ){

                employees = treeMap.get(monthMinus1);

                System.out.println("---------------------");
                System.out.println("---------------------");
                System.out.println("before employees2" + employees2 + "size: " + employees2.size() );
                System.out.println("before(checkedlist-employees)" + employees + "size: " + employees.size() );

                //in case an employee saved from the previous month is deactivated on the next month
                ArrayList<String> tempList = new ArrayList<>(employees);
                System.out.println("tempList: " + tempList);
                tempList.removeAll(employees2);
                System.out.println("tempList (after removal): " + tempList);
                System.out.println("before(checkedlist-employees)" + employees + "size: " + employees.size() );

                employees2.removeAll(employees);
                System.out.println("---------------------");
                System.out.println("Remove all method");


                System.out.println("(employees2" + employees2 + employees2.size());
                System.out.println("(checkedList - employees) " + employees + "size: " + employees.size());

                employees.removeAll(tempList);
                System.out.println("employees (after tempList removed): " + employees);

                employees.addAll(employees2);
                System.out.println("---------------------");
                System.out.println("Add all method");
                System.out.println("employees2" + employees2 + "size: " + employees2.size());
                System.out.println("(checkedlist-employees)" + employees + "size: " + employees.size());

                employees2 = employees;
                System.out.println("---------------------");
                System.out.println("ep2=emp1");
                System.out.println("employees2" + employees2);
                System.out.println("(checkedlist-employees)" + employees);

            }


            newresult.close();
            callstmt.close();

            //System.out.println("Employees: " + employees);
            System.out.println("Employees2: " + employees2);


        } catch (SQLException d) {
            d.printStackTrace();
        }


    }

    public void loadWorkShifts() {

        Statement stmt = null;

        try {
            CallableStatement callstmt = connection.prepareCall("call loadWorkShifts()");

            ResultSet newresult = callstmt.executeQuery();

            while (newresult.next()) {
                wshifts.add(new WorkShifts(
                        newresult.getTime("from_hour").toLocalTime(),
                        newresult.getTime("to_hour").toLocalTime(),
                        newresult.getInt("noOfEmp")));
                //tableView.setItems(wsList);

            }


            newresult.close();
            callstmt.close();


        /*    for (int i = 0; i < wshifts.size(); i++) {
                for (int j = 0; j < wshifts.get(i).getNoOfEmp(); j++) {
                    wshifts2.add(String.valueOf(wshifts.get(i).getFromHour() + "-" + wshifts.get(i).getToHour()));
                }
            }
        */

            System.out.println("=========================================");
                  ArrayList<Integer> nofWorkShifts = new ArrayList<>();


                    for (int i = 0; i < wshifts.size(); i++) {
                        nofWorkShifts.add(wshifts.get(i).getNoOfEmp());
                    }
                    System.out.println(nofWorkShifts);

                for (int w = 0;  w < wshifts.size(); w++) {
                    for (int i = 0; i < wshifts.size(); i++) {
                        if (nofWorkShifts.get(i) == 0)
                            continue;
                        wshifts2.add(String.valueOf(wshifts.get(i).getFromHour() + "-" + wshifts.get(i).getToHour()));
                        nofWorkShifts.set(i, nofWorkShifts.get(i) - 1);
                        System.out.println("w: " + w);
                        System.out.println(nofWorkShifts);
                        System.out.println("wshifts2: " + wshifts2);
                        System.out.println("wshifts2.size: "+ wshifts2.size());
                    }
                }



            System.out.println("employees2.size: "+ employees2.size());
            System.out.println("wshifts2.size: "+ wshifts2.size());
            if (employees2.size() != wshifts2.size()) {
                int sub = employees2.size() - wshifts2.size();
                if (employees2.size() > wshifts2.size()) {
                    for (int j = 0; j < sub; j++) {
                        wshifts2.add("choose workshift");
                    }
                } else {
                    for (int j = 0; j < sub; j++) {
                        employees2.add("null");
                    }
                }
            }



            } catch(SQLException d){
                d.printStackTrace();
            }


        System.out.println("----------------------");
        System.out.println(employees2);
        System.out.println(wshifts2);


        }


        public void calculateMonth(String month) {



            Statement stmt = null;
            LocalDate ld1 = LocalDate.parse("2022-08-01");
            LocalDate ld2 = LocalDate.parse("2022-08-31");
            String s;
            LocalDate l1 = null;
            LocalDate l2 = null;

            LocalDate initial = switch (month) {
                case "January" -> LocalDate.of(LocalDate.now().getYear(), 1, 1);
                case "February" -> LocalDate.of(LocalDate.now().getYear(), 2, 1);
                case "Mars" -> LocalDate.of(LocalDate.now().getYear(), 3, 1);
                case "April" -> LocalDate.of(LocalDate.now().getYear(), 4, 1);
                case "May" -> LocalDate.of(LocalDate.now().getYear(), 5, 1);
                case "June" -> LocalDate.of(LocalDate.now().getYear(), 6, 1);
                case "July" -> LocalDate.of(LocalDate.now().getYear(), 7, 1);
                case "August" -> LocalDate.of(LocalDate.now().getYear(), 8, 1);
                case "September" -> LocalDate.of(LocalDate.now().getYear(), 9, 1);
                case "October" -> LocalDate.of(LocalDate.now().getYear(), 10, 1);
                case "November" -> LocalDate.of(LocalDate.now().getYear(), 11, 1);
                case "December" -> LocalDate.of(LocalDate.now().getYear(), 12, 1);
                default -> null;
            };

            LocalDate start = initial;
            LocalDate startMinus1 = start.plus(-1, ChronoUnit.DAYS);
            LocalDate end = initial.withDayOfMonth(initial.getMonth().length(initial.isLeapYear()));
            LocalDate endMinus1 = end.plus(-1, ChronoUnit.DAYS);


            System.out.println("start-1 day: " + startMinus1 + " start day: " + start + " end day " + end);

            String out = employees2.remove(employees2.size()-1);

            System.out.println("employees2 " + employees2);
            System.out.println("wshifts2 " + wshifts2);

            //group all the repos and timeoff days
            try {
                CallableStatement callstmt = connection.prepareCall("call loadRandT(?,?)");
                callstmt.setObject(1, start);
                callstmt.setObject(2, end);

                ResultSet newresult = callstmt.executeQuery();

                while(newresult.next()){


                   Pair<LocalDate, Pair<String, String>> p =  new Pair<LocalDate, Pair<String, String>> (
                            newresult.getDate("the_day").toLocalDate(),
                            new Pair<>(newresult.getString("emp_id"), newresult.getString("ws"))
                    );

                    pairs.add(p);


                }

                System.out.println("pairs!!!: " + pairs);


                newresult.close();
                callstmt.close();

            } catch (SQLException d) {
                d.printStackTrace();
            }



            //start an the first day of the month and end at the last one
            //correspond workshift per employee (stable workshift during week, rolling workshift after week,
            // taking into account repos and timeOff)
            while(startMinus1.isBefore(end)) {  //till month ends...


                employees2.add(out);

                System.out.println("added " + employees2);
                int z = 0; //counts employees

                    for (int i = 0; i < 7; i++) { //for every day of the week...

                        if(startMinus1.isAfter(endMinus1)){ //break the program if month ends
                            System.out.println("program ended");
                            break;
                        }

                        System.out.println();
                        System.out.println();
                        System.out.println("i: " + i );


                        wshifts2Temp.clear();
                        wshifts2Temp.addAll(wshifts2);
                        System.out.println("##############");
                        System.out.println("wshifts2Temp: " + wshifts2Temp);
                        System.out.println("##############");

                        startMinus1 = startMinus1.plus(1, ChronoUnit.DAYS);
                        System.out.println("startMinus1 : " + startMinus1);
                        String repo = "repo";
                        String timeOff = "time off";
                        String sickleave = "sick leave";


                        for (z = 0; z < employees2.size(); z++) {  //for each employee

                            int r = 0;
                            int t = 0;
                            int sl = 0;

                            //before getting the program, check whether an employee has a day or a time off, if yes go to the next one
                            Pair<LocalDate, Pair<String, String>> p2 = new Pair<LocalDate, Pair<String, String>>(startMinus1, new Pair<>(employees2.get(z), timeOff));

                            Pair<LocalDate, Pair<String, String>> p3 = new Pair<LocalDate, Pair<String, String>>(startMinus1, new Pair<>(employees2.get(z), repo));

                            Pair<LocalDate, Pair<String, String>> p4 = new Pair<LocalDate, Pair<String, String>>(startMinus1, new Pair<>(employees2.get(z), sickleave));

                            if(pairs.contains(p2)){
                                System.out.println("");
                                System.out.println("found one, day: " + startMinus1 + " employee: " + employees2.get(z) + " wshift: " +  timeOff);
                                t +=1;
                            }
                            if(pairs.contains(p3)){
                                System.out.println("");
                                System.out.println("found one, day: " + startMinus1 + " employee: " + employees2.get(z) + " wshift: " +  repo);
                                r +=1;
                            }
                            if(pairs.contains(p4)){
                                System.out.println("");
                                System.out.println("found one, day: " + startMinus1 + " employee: " + employees2.get(z) + " wshift: " +  sickleave);
                                sl +=1;
                            }

                            System.out.println("");
                            System.out.println("************************************");
                            System.out.println("wshits2: " + wshifts2 + " size: " +wshifts2.size());
                            System.out.println("wshits2Temp: " + wshifts2Temp + " size: " +wshifts2Temp.size());
                            System.out.println("employees2: " + employees2 + " size: "+ employees2.size());
                            System.out.println("============");
                            System.out.println("employee: " +  employees2.get(z) + " checked");

                            if (t!=0 | r !=0 | sl!=0){

                                System.out.println("wshits2Temp: " + wshifts2Temp);
                                int lastIndex =  wshifts2Temp.size()-1;

                                System.out.println(lastIndex);
                                System.out.println("t: " + t + " r:" + r + " sl: " + sl);

                                wshifts2Temp.add(z, wshifts2Temp.get(lastIndex));
                                System.out.println(wshifts2Temp.get(lastIndex) + " added at place: " + z);
                                System.out.println("wshifts2 new(a): " + wshifts2Temp);

                                wshifts2Temp.remove(lastIndex + 1);
                                System.out.println("index : "+ (lastIndex+1) +" removed");
                                System.out.println("wshifts2 new(b): " + wshifts2Temp);
                                continue;
                            }

                            //insert into the database workshifts per employee (excluded the ones with days and times off)
                            try {
                            String insQuery = "INSERT INTO program " +
                                    " (the_day, ws, emp_id)" +
                                    " VALUES(?,?,?)" ;

                            PreparedStatement insStmt = connection.prepareStatement(insQuery);
                            insStmt.setObject(1, startMinus1);
                            insStmt.setString(2, wshifts2Temp.get(z));
                            insStmt.setString(3, employees2.get(z));
                            insStmt.executeUpdate();

                            System.out.println("Insertion completed: " + employees2.get(z));
                            System.out.println("");


                            insStmt.close();


                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                            //////////////
                         //   System.out.println("Day: " + startMinus1 + "Workshift: " + wshifts2.get(z) + "Employee: " + employees2.get(z));
                        }
                    }


                //remove first index (employee) and add it again to the next loop (next week) in order to cycle the workshifts weekly
                out = employees2.remove(0);
                System.out.println("removed " + employees2);
            }  //end of while

            employees2.add(0, out);

            //save to the database the month for which the program was produced and the employees last order
            try {
                String insQuery = "INSERT INTO checkedMonths_andList " +
                        " (checked_month, empList)" +
                        " VALUES(?,?)" ;

                PreparedStatement insStmt = connection.prepareStatement(insQuery);
                insStmt.setString(1, month);
                insStmt.setString(2, employees2.toString());
                insStmt.executeUpdate();


                insStmt.close();


            } catch (SQLException throwables) {
                    throwables.printStackTrace();
            }

            loadDB(connection, start, end);

        }

        public void loadNameSurname(){
            resultEmpId = comboEmpId.getValue();
            System.out.println(resultEmpId);
            try {
                CallableStatement callstmt = connection.prepareCall("call loadEmp(?)");
                callstmt.setString(1, resultEmpId);


                ResultSet newresult = callstmt.executeQuery();

                while(newresult.next()){

                    txtEmpName.setText(newresult.getString("emp_name"));
                    txtEmpSurname.setText(newresult.getString("emp_surname"));
                }
                newresult.close();
                callstmt.close();
            } catch (SQLException d) {
                d.printStackTrace();
            }
        }

        public void loadProgramById(Connection conn, LocalDate start, LocalDate end){
        tableView.getItems().clear();
        resultEmpId = comboEmpId.getValue();
            Statement stmt = null;
            try {
                stmt = conn.createStatement();
                ResultSet resultSet = stmt.executeQuery("SELECT p.the_day, p.ws, p.emp_id, e.emp_name, e.emp_surname " +
                        "from program p join employee e WHERE  p.emp_id = e.emp_id AND p.emp_id = '" + resultEmpId + "' " +
                        "AND p.the_day >= '" + start + "' AND p.the_day <= '" + end + "'" +
                        " ORDER BY the_day, ws, emp_id");

                //check if table is empty
                if (!resultSet.next()) {
                    System.out.println("Table is empty");

                } else {

                    do {
                        programPairsList.add(new TableViewProgram(
                                resultSet.getDate("the_day").toLocalDate(),
                                resultSet.getString("ws"),
                                resultSet.getString("emp_id"),
                                resultSet.getString("emp_name"),
                                resultSet.getString("emp_surname")));

                        tableView.setItems(programPairsList);


                    } while (resultSet.next());
                }
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }



    public void loadDB(Connection conn, LocalDate start, LocalDate end) {
        tableView.getItems().clear();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT p.the_day, p.ws, p.emp_id, e.emp_name, e.emp_surname from program p join employee e WHERE  p.emp_id = e.emp_id AND p.the_day >= '" + start + "' AND p.the_day <= '" + end + "'" +
                    " ORDER BY the_day, ws, emp_id");

            //check if table is empty
            if (!resultSet.next()) {
                System.out.println("Table is empty");

            } else {

                do {

                    programPairsList.add(new TableViewProgram(
                            resultSet.getDate("the_day").toLocalDate(),
                            resultSet.getString("ws"),
                            resultSet.getString("emp_id"),
                            resultSet.getString("emp_name"),
                            resultSet.getString("emp_surname")));

                    tableView.setItems(programPairsList);


                } while (resultSet.next());
            }
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void export(TableView<TableViewProgram> tableView) {

        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet hssfSheet = hssfWorkbook.createSheet("Sheet1");
        HSSFRow firstRow = hssfSheet.createRow(0);

        //set column widths
        hssfSheet.setColumnWidth(0, 256*12);
        hssfSheet.setColumnWidth(1, 256*18);
        hssfSheet.autoSizeColumn(2);
        hssfSheet.setColumnWidth(3, 256*15);
        hssfSheet.setColumnWidth(4, 256*15);



        ///set titles of columns
        for (int i = 0; i < tableView.getColumns().size(); i++) {

            firstRow.createCell(i).setCellValue(tableView.getColumns().get(i).getText());

        }


        for (int row = 0; row < tableView.getItems().size(); row++) {

            HSSFRow hssfRow = hssfSheet.createRow(row + 1);

            for (int col = 0; col < tableView.getColumns().size(); col++) {

                Object celValue = tableView.getColumns().get(col).getCellObservableValue(row).getValue();

                try {
                    if (celValue != null && Double.parseDouble(celValue.toString()) != 0.0) {
                        hssfRow.createCell(col).setCellValue(Double.parseDouble(celValue.toString()));
                    }
                } catch (NumberFormatException e) {

                    hssfRow.createCell(col).setCellValue(celValue.toString());
                }

            }

        }

        //save excel file and close the workbook
        try {
            hssfWorkbook.write(new FileOutputStream("WorkBook.xls"));
            hssfWorkbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }






