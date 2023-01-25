import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
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
import javafx.util.converter.IntegerStringConverter;

import java.sql.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;


public class WorkShiftForm {
    Stage stage;
    Label lblTitle, lblFromHour, lblToHour, lblEmpNo;
    TextField txtEmpNo;

    ComboBox<String> comboFromHour, comboFromMin, comboToHour, comboToMin;


    Button btnAddWorkShift, btnDeleteLine, btnRefresh, btnEdit;

    HBox hBox1, hBox2, hBox3, hBoxTable;
    String state;

    TableView<WorkShifts> tableView;

    TableColumn<WorkShifts, LocalTime> fromHourColumn;
    TableColumn<WorkShifts, LocalTime> toHourColumn;
    TableColumn<WorkShifts, Integer> noOfEmpColumn;



    ArrayList<String> numbers = new ArrayList<>();
    ArrayList<String> numbers2 = new ArrayList<>();

    ObservableList<WorkShifts> wsList = FXCollections.observableArrayList();

    Connection connection;

    public WorkShiftForm(Connection conn) {

        for (int i=0; i<24; i++){
            if(i<10){
                numbers.add("0"+i);
            }else {
                numbers.add(String.valueOf(i));
            }
        }

        for (int i=0; i<60; i++){
            if(i<10){
                numbers2.add("0"+i);
            }else {
                numbers2.add(String.valueOf(i));
            }
        }

        connection = conn;

        //grid
        GridPane grid = new GridPane();
        grid.setHgap(11);
        grid.setVgap(13);


        //label
        lblTitle = new Label("Add a work shift");
        lblTitle.setFont(Font.font(null, FontWeight.BOLD, 15));


        //select work shift
        lblFromHour = new Label("From: ");
        comboFromHour = new ComboBox<String>();
        comboFromHour.setPromptText("Hour");
        comboFromHour.getItems().addAll(numbers);
        comboFromHour.setPrefWidth(85);

        comboFromMin = new ComboBox<String>();
        comboFromMin.setPromptText("Minute");
        comboFromMin.getItems().addAll(numbers2);
        comboFromMin.setPrefWidth(85);



        lblToHour = new Label("To: ");
        comboToHour = new ComboBox<String>();
        comboToHour.setPromptText("Hour");
        comboToHour.getItems().addAll(numbers);
        comboToHour.setPrefWidth(85);

        comboToMin = new ComboBox<String>();
        comboToMin.setPromptText("Minute");
        comboToMin.getItems().addAll(numbers2);
        comboToMin.setPrefWidth(85);


        hBox1 = new HBox();
        hBox1.getChildren().addAll(lblFromHour, comboFromHour, comboFromMin, lblToHour, comboToHour, comboToMin);
        hBox1.setSpacing(10);
        hBox1.setMinHeight(100);
        hBox1.setAlignment(Pos.CENTER);

        lblEmpNo = new Label("Set min number of employees");
        txtEmpNo = new TextField();
        txtEmpNo.setPrefWidth(50);

        btnAddWorkShift = new Button("Add line");


        btnDeleteLine = new Button("Delete selected line");

        btnEdit = new Button("Edit selected line");

        btnRefresh = new Button("Refresh");


        hBox2 = new HBox();
        hBox2.getChildren().addAll(lblEmpNo, txtEmpNo, btnAddWorkShift, btnRefresh);
        hBox2.setSpacing(10);
        hBox2.setAlignment(Pos.CENTER);

        hBox3 = new HBox();
        hBox3.getChildren().addAll(btnEdit, btnDeleteLine);
        hBox3.setSpacing(10);
        hBox3.setAlignment(Pos.CENTER);
        hBox3.setMinHeight(100);


        //tableView
        fromHourColumn = new TableColumn<>("From Hour");
        fromHourColumn.setCellValueFactory(new PropertyValueFactory<>("fromHour"));
        fromHourColumn.setMinWidth(150);

        toHourColumn = new TableColumn<>("To Hour");
        toHourColumn.setCellValueFactory(new PropertyValueFactory<>("toHour"));
        toHourColumn.setMinWidth(150);

        noOfEmpColumn = new TableColumn<>("Min no of Employees");
        noOfEmpColumn.setCellValueFactory(new PropertyValueFactory<>("noOfEmp"));
        noOfEmpColumn.setMinWidth(150);


        tableView = new TableView<>();

        tableView.getColumns().add(fromHourColumn);
        tableView.getColumns().add(toHourColumn);
        tableView.getColumns().add(noOfEmpColumn);


        fromHourColumn.setCellFactory(ComboBoxTableCell.forTableColumn());
        toHourColumn.setCellFactory(ComboBoxTableCell.forTableColumn());
        noOfEmpColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        tableView.setPrefWidth(450);
        tableView.setPrefHeight(500);
        tableView.setEditable(true);

        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        hBoxTable = new HBox();
        hBoxTable.getChildren().addAll(tableView);
        hBoxTable.setPadding(new Insets(50, 50, 50, 50));
        hBoxTable.setAlignment(Pos.CENTER);

        VBox pane = new VBox();
        pane.getChildren().addAll(hBox1, hBox2, hBox3, hBoxTable);
        pane.setPadding((new Insets(10, 10, 10, 10)));
        pane.setAlignment(Pos.CENTER);

        //buttons
        btnAddWorkShift.setOnAction(e -> {

            //check if all fields are filled in
            if (comboFromHour.getValue() == null){
                MessageBoxOK mb = new MessageBoxOK("Please select hour.", "WARNING!");
                System.out.println("Please select hour.");
            }
            else if (comboFromMin.getValue() == null) {
                MessageBoxOK mb = new MessageBoxOK("Please select minutes.", "WARNING!");
                System.out.println("PPlease select minutes.");
            }
            else if (comboToHour.getValue() == null) {
                MessageBoxOK mb = new MessageBoxOK("Please select hour.", "WARNING!");
                System.out.println("Please select hour.");
            }
            else if (comboToMin.getValue() == null) {
                MessageBoxOK mb = new MessageBoxOK("Please select minutes.", "WARNING!");
                System.out.println("Please select minutes.");
            }
            else if (txtEmpNo.getText().isEmpty()) {
                MessageBoxOK mb = new MessageBoxOK("Please enter min number of employees.", "WARNING!");
                System.out.println("Please enter min number of employees.");
            }

            //everything is ok! add productline to the DB and the tableView
            else {
             System.out.println("Validate: OK");
                String fh = comboFromHour.getValue();
                String fm = comboFromMin.getValue();
                String th = comboToHour.getValue();
                String tm = comboToMin.getValue();
                LocalTime fromHour = LocalTime.of(Integer.parseInt(fh), Integer.parseInt(fm));
                LocalTime toHour = LocalTime.of(Integer.parseInt(th), Integer.parseInt(tm));
                int noEmp = Integer.parseInt(txtEmpNo.getText());

                System.out.println("from: " + fromHour + " to: " + toHour + " emp: " + noEmp);

                WorkShifts ws = new WorkShifts();
                ws.setFromHour(fromHour);
                ws.setToHour(toHour);
                ws.setNoOfEmp(noEmp);

                try {
                    String insQuery = "INSERT INTO workshifts " +
                            " (from_hour, to_hour, noOfEmp)" +
                            " VALUES(?,?,?)" ;

                    PreparedStatement insStmt = conn.prepareStatement(insQuery);
                    insStmt.setObject(1, ws.getFromHour());
                    insStmt.setObject(2, ws.getToHour());
                    insStmt.setInt(3, ws.getNoOfEmp());
                    insStmt.executeUpdate();

                    insStmt.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                //add workshift to the tableView
                tableView.getItems().add(ws);

                //prepare labels for next insertion
                comboFromHour.setValue(null);
                comboFromMin.setValue(null);
                comboToHour.setValue(null);
                comboToMin.setValue(null);
                txtEmpNo.clear();
             }
        });

        btnRefresh.setOnAction(e->{
            loadTableView();
        });

        btnDeleteLine.setOnAction(e -> {
            MessageBoxCancelYes m = new MessageBoxCancelYes("Are you sure you want to delete \n the selected Workshift?","Warning!");
            System.out.println("Are you sure you want to delete the selected Workshift?");
            boolean response = m.getResponse();
            if (response) {

                //remove workshift from the DB
                try {
                    String delQuery = "DELETE FROM workshifts " +
                            " WHERE from_hour = '" + fromHourColumn.getCellData(tableView.getSelectionModel().getSelectedItem())  + "' AND to_hour = '" + toHourColumn.getCellData(tableView.getSelectionModel().getSelectedItem()) +  "'";

                    PreparedStatement statement = connection.prepareStatement(delQuery);
                    statement.executeUpdate();

                    statement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                //remove workshift from the tableView
                tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItem());
            }
            else {
                System.out.println("Deletion cancelled");
            }

        });

        btnEdit.setOnAction(e -> {
            WorkShifts ws = tableView.getSelectionModel().getSelectedItem();
            EditWorkShift ews = new EditWorkShift(conn, ws.getFromHour(), ws.getToHour(), ws.getNoOfEmp());
        });


        grid.getChildren().add(pane);


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
        stage.setTitle("Add WorkShifts");
        stage.setX((Screen.getPrimary().getVisualBounds().getWidth() - 600) / 2);
        stage.setY((Screen.getPrimary().getVisualBounds().getHeight() - 800) / 2);
        stage.setWidth(600);
        stage.setHeight(800);
        stage.initModality(Modality.APPLICATION_MODAL);


        loadTableView();
        stage.show();

    }

    public void loadTableView() {
        tableView.getItems().clear();
        Statement stmt = null;
        //Stock st = new Stock();
        try {
            CallableStatement callstmt = connection.prepareCall("call loadTableView()");

            ResultSet newresult = callstmt.executeQuery();

            while (newresult.next()) {
                wsList.add(new WorkShifts(
                        newresult.getTime("from_hour").toLocalTime(),
                        newresult.getTime("to_hour").toLocalTime(),
                        newresult.getInt("noOfEmp")));
                tableView.setItems(wsList);
            }
            newresult.close();
            callstmt.close();
        } catch (SQLException d) {
            d.printStackTrace();
        }
    }


}



