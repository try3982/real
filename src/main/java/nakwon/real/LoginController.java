package nakwon.real;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    @FXML
    private Button createAccountBtn;
    @FXML
    private Button CreateBtn;

    @FXML
    private Button loginBtn;

    @FXML
    private AnchorPane loginForm;

    @FXML
    private Hyperlink si_forgotPassword;

    @FXML
    private PasswordField si_password;

    @FXML
    private TextField si_username;

    @FXML
    private AnchorPane side_form;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Alert alert;



    @FXML
    public void loginBtn(ActionEvent event) {
        if (si_username.getText().isEmpty() || si_password.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("아이디와 비밀번호를 입력해주세요");
            alert.showAndWait();
        } else {
            String selectData = "SELECT username, password FROM employee WHERE username = ? and password = ?";

            connect = database.connectDB();

            try {
                prepare = connect.prepareStatement(selectData);
                prepare.setString(1, si_username.getText());
                prepare.setString(2, si_password.getText());

                result = prepare.executeQuery();

                if (result.next()) {
                    data.username = si_username.getText();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("로그인 성공!");
                    alert.showAndWait();


                    // Load and show StartScreen1.fxml
                    Parent startScreenRoot = FXMLLoader.load(getClass().getResource("/nakwon/real/StartScreen1.fxml"));
                    Scene startScreenScene = new Scene(startScreenRoot);

                    Stage startScreenStage = new Stage();
                    startScreenStage.setTitle("낙원 키오스크 - Start Screen");
                    startScreenStage.setScene(startScreenScene);
                    startScreenStage.show();


                    Parent root = FXMLLoader.load(getClass().getResource("Admin.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    stage.setTitle("NakWon Kiosk");
                    stage.setMinWidth(1100);
                    stage.setMinHeight(600);

                    stage.setScene(scene);
                    stage.show();

                    //si_loginBtn.getScene().getWindow().hide();

//                    // Load and show admin.fxml
//                    Parent adminRoot = FXMLLoader.load(getClass().getResource("Admin.fxml"));
//                    Stage adminStage = new Stage();
//                    Scene adminScene = new Scene(adminRoot);
//                    adminStage.setTitle("낙원 키오스크 - Admin Screen");
//                    adminStage.setScene(adminScene);
//                    adminStage.show();
//
//                    // Load and show chicken.fxml
//                    Parent chickenRoot = FXMLLoader.load(getClass().getResource("Kitchen.fxml"));
//                    Stage chickenStage = new Stage();
//                    Scene chickenScene = new Scene(chickenRoot);
//                    chickenStage.setTitle("낙원 키오스크 - Chicken Screen");
//                    chickenStage.setScene(chickenScene);
//                    chickenStage.show();

//                    Parent startScreenRoot1 = FXMLLoader.load(getClass().getResource("/nakwon/real/Kitchen"));
//                    Scene startScreenScene1 = new Scene(startScreenRoot1);
//
//                    Stage startScreenStage1 = new Stage();
//                    startScreenStage1.setTitle("주방화면 - Start Screen");
//                    startScreenStage1.setScene(startScreenScene1);
//                    startScreenStage1.show();


                    loginBtn.getScene().getWindow().hide();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("아이디와 비밀번호가 일치하지 않습니다.");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void forgotPass(ActionEvent event) {

        // 비밀번호 재설정
        try {
            // createAccount.fxml 화면
            Parent root = FXMLLoader.load(getClass().getResource("FindPassword.fxml"));

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("비밀번호 찾기");
            stage.setScene(scene);
            stage.show();


            ((Node)(event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    public void CreateAccountBtn(ActionEvent event) {
        try {
            // createAccount.fxml 화면을 로드합니다.
            Parent root = FXMLLoader.load(getClass().getResource("CreateAccount.fxml"));

            // 새로운 Stage를 생성하여 계정 생성 화면을 보여줍니다.
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("계정 생성");
            stage.setScene(scene);
            stage.show();

            // 현재 화면을 닫습니다. (선택사항)
            ((Node)(event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
