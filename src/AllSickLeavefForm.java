import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.util.converter.IntegerStringConverter;
import java.sql.*;
import java.time.LocalDate;


public class AllSickLeavefForm {
    Stage stage;


    HBox hboxTable, hboxItems;
    TableView<TimeOff> tableView;


    TableColumn<TimeOff, String> empIdColumn;
    TableColumn<TimeOff, LocalDate> sickLeaveColumn;



    Connection connection;
    String emp_id;


    ObservableList<TimeOff> allSickLeaveList = FXCollections.observableArrayList();

    public AllSickLeavefForm(Connection conn, String emp_id) {

        connection = conn;
        String theId = emp_id;


        //tableView
        empIdColumn = new TableColumn<>("Employee Id");
        empIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        empIdColumn.setMinWidth(190);

        sickLeaveColumn = new TableColumn<>("Sick Leave");
        sickLeaveColumn.setCellValueFactory(new PropertyValueFactory<>("timeOff"));
        sickLeaveColumn.setMinWidth(190);



        tableView = new TableView<>();
        tableView.getColumns().add(empIdColumn);
        tableView.getColumns().add(sickLeaveColumn);



        empIdColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        sickLeaveColumn.setCellValueFactory(new PropertyValueFactory<>("timeOff"));



        empIdColumn.setEditable(true);
        sickLeaveColumn.setEditable(true);


        tableView.setPrefWidth(400);
        tableView.setPrefHeight(700);
        tableView.setEditable(true);

        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        hboxTable = new HBox();
        hboxTable.getChildren().addAll(tableView);
        hboxTable.setPadding(new Insets(50, 50, 50, 50));
        hboxTable.setAlignment(Pos.CENTER);


        VBox pane = new VBox();
        pane.getChildren().addAll(hboxTable);

        // the scene
        Scene scene = new Scene(pane);
        scene.heightProperty().addListener(
                (observable, oldValue, newValue) -> {
                    pane.setPrefHeight(scene.getHeight());

                });
        scene.widthProperty().addListener(
                (observable, oldValue, newValue) -> {
                    pane.setPrefWidth(scene.getWidth());
                });

        //the stage
        stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("All Sick Leave");
        stage.setX((Screen.getPrimary().getVisualBounds().getWidth() - 500) / 2);
        stage.setY((Screen.getPrimary().getVisualBounds().getHeight() - 800) / 2);
        stage.setWidth(500);
        stage.setHeight(800);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setMaximized(false);
        stage.show();

        Statement stmt = null;

        try {
            CallableStatement callstmt = connection.prepareCall("call loadAllSickLeave(?)");
            callstmt.setString(1, theId);

            ResultSet newresult = callstmt.executeQuery();

            while (newresult.next()) {
                allSickLeaveList.add(new TimeOff(
                        newresult.getString("emp_id"),
                        newresult.getDate("sick_leave").toLocalDate()));
                tableView.setItems(allSickLeaveList);
            }
            newresult.close();
            callstmt.close();
        } catch (SQLException d) {
            d.printStackTrace();
        }


    }
}

