package nakwon.real;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateAccountController {

    @FXML // fx:id="su_answer"
    private TextField su_answer; // Value injected by FXMLLoader

    @FXML // fx:id="su_back"
    private Button backtoLoginBtn; // Value injected by FXMLLoader

    @FXML // fx:id="su_password"
    private PasswordField su_password; // Value injected by FXMLLoader

    @FXML // fx:id="su_question"
    private ComboBox<String> su_question; // Value injected by FXMLLoader

    @FXML // fx:id="su_repassword"
    private PasswordField su_repassword; // Value injected by FXMLLoader

    @FXML // fx:id="su_signupBtn"
    private Button su_signupBtn; // Value injected by FXMLLoader

    @FXML // fx:id="su_signupForm"
    private AnchorPane su_signupForm; // Value injected by FXMLLoader

    @FXML // fx:id="su_username"
    private TextField su_username; // Value injected by FXMLLoader

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Alert alert;




    private String[] questionList = {"좋아하는 색깔은 무엇입니까?", "좋아하는 음식은 무엇입니까?", "당신의 생일은 언제입니까?"};

    public void regQuestionList() {
        List<String> listQ = new ArrayList<>();

        for (String data : questionList) {
            listQ.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listQ);
        su_question.setItems(listData);
    }


    @FXML
    void regBtn(ActionEvent event) {
        if (su_username.getText().isEmpty() || su_password.getText().isEmpty()
                || su_question.getSelectionModel().getSelectedItem() == null
                || su_answer.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            String regData = "INSERT INTO employee (username, password, question, answer, date) "
                    + "VALUES(?,?,?,?,?)";
            connect = database.connectDB();
            try {
                String checkUsername = "SELECT username FROM employee WHERE username = '"
                        + su_username.getText() + "'";
                prepare = connect.prepareStatement(checkUsername);
                result = prepare.executeQuery();
                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(su_username.getText() + " 아이디가 이미 존재합니다.");
                    alert.showAndWait();
                } else if (su_password.getText().length() < 4) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("비밀번호는 4글자 이상이어야 합니다.");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(regData);
                    prepare.setString(1, su_username.getText());
                    prepare.setString(2, su_password.getText());
                    prepare.setString(3, (String) su_question.getSelectionModel().getSelectedItem());
                    prepare.setString(4, su_answer.getText());
                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(5, String.valueOf(sqlDate));
                    prepare.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("계정이 성공적으로 생성되었습니다.");
                    alert.showAndWait();
                    su_username.setText("");
                    su_password.setText("");
                    su_question.getSelectionModel().clearSelection();
                    su_answer.setText("");

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void backtoLoginBtn(ActionEvent event) {
        try {
            // login.fxml 화면을 로드합니다.
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

            // 현재 Stage를 가져옵니다.
            Stage stage = (Stage) backtoLoginBtn.getScene().getWindow();

            // 로그인 화면을 새로운 Scene으로 설정합니다.
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void initialize() {
        regQuestionList();
    }
}