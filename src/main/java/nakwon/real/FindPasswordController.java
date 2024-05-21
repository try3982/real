package nakwon.real;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FindPasswordController  {

    public Button got0ChangePasswordBtn;
    @FXML
    private Button goChangePassword;


    @FXML
    private Button backtologinBtn;



    @FXML
    private TextField fp_answer;

    @FXML
    private Button proccedBtn;

    @FXML
    public ComboBox<String> fp_question;


    @FXML
    public TextField fp_username;


    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Alert alert;

    private String[] questionList = {"좋아하는 색깔은 무엇입니까?", "좋아하는 음식은 무엇입니까?", "당신의 생일은 언제입니까?"};

//    public void forgotPassQuestionList() {
//
//        List<String> listQ = new ArrayList<>();
//
//        for (String data : questionList) {
//            listQ.add(data);
//        }
//
//        ObservableList listData = FXCollections.observableArrayList(listQ);
//        fp_question.setItems(listData);

//    }

    public void forgotPassQuestionList() {

        // 어려웠던 부분
        List<String> listQ = new ArrayList<>();
        for (String data : questionList) {
            listQ.add(data);
        }

        if (fp_question != null) { // fp_question이 null인지 확인합니다.
            ObservableList<String> listData = FXCollections.observableArrayList(listQ);
            fp_question.setItems(listData);
        } else {
            System.out.println("fp_question ComboBox가 null입니다.");
        }
    }


//    @FXML
//    void fp_backtologinBtn(ActionEvent event) {
//        try {
//            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
//            Stage stage = (Stage)backtologinBtn.getScene().getWindow();
//
//            Scene scene = new Scene(root);
//            stage.setScene(scene);
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }


    @FXML
    void proceedBtn(ActionEvent event) {
        if (fp_username.getText().isEmpty() || fp_question.getSelectionModel().getSelectedItem() == null
                || fp_answer.getText().isEmpty()) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("모든 필드를 채워주세요.");
            alert.showAndWait();

        } else {

            String selectData = "SELECT username, question, answer FROM employee WHERE username = ? AND question = ? AND answer = ?";
            connect = database.connectDB();

            try {

                prepare = connect.prepareStatement(selectData);
                prepare.setString(1, fp_username.getText());
                prepare.setString(2, fp_question.getSelectionModel().getSelectedItem());
                prepare.setString(3, fp_answer.getText());

                result = prepare.executeQuery();

                if (result.next()) {
                    data.username = fp_username.getText();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("아이디가 확인되었습니다. 새로운 비밀번호를 입력해 주세요.");
                    alert.showAndWait();

                    try {
                        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ChangePassword.fxml")));
                        Stage stage = (Stage)got0ChangePasswordBtn.getScene().getWindow();

                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("일치하는 정보가 없습니다.");
                    alert.showAndWait();
                }

            } catch (Exception e) {
                e.printStackTrace();

            }
        }

    }

    public void gotoChangePasswordBtn() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ChangePassword.fxml")));
            Stage stage = (Stage)got0ChangePasswordBtn.getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void initialize() {
        // regQuestionList();
        forgotPassQuestionList();
    }


}
