package nakwon.real;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class payment implements Initializable{

    /* card_pay_btn, cash_pay_btn -> 각각 결재 방법 선택 시 '카드' / '현금' 버튼을 통해 결재 방법 선택
     * card_pay_box, cash_pay_box -> 결재 진행 화면을 보여주기 위함.
     * card_insert_msg, cash_insert_msg -> 실제 결재 장면을 시뮬레이션 하기 위해 마우스 클릭 시 결재가 진행 중임을 나타내는 label을 가짐.
     * pay -> 결재 완료 화면에서 총 결재된 금액을 나타내기 위함.
     */
    @FXML
    private Button card_pay_btn;

    @FXML
    private Button cash_pay_btn;

    @FXML
    private VBox card_pay_box;

    @FXML
    private Label card_insert_msg;

    @FXML
    private Label cash_insert_msg;

    @FXML
    private VBox cash_pay_box;

    @FXML
    private Label pay;

    initialController initial_ctrl = initialController.instance;

    /*
     * 마지막 결재 화면을 끝으로 다시 처음으로 돌아갈 때 이전 창들을 닫고 처음 창을 띄우는 과정에서 창들을 관리하기 위함.
     */
    private StageManager stageManager = StageManager.getInstance();

    /* cash_pay_click, card_pay_click -> 각 현금, 카드 결재 진행시 마우스 클릭이 일어나면 결재 진행 중임을 알림
     * 마우스 클릭 3초 이후에 결재 완료 화면을 자동으로 나타내는 과정.
     */
    @FXML
    public void cash_pay_click(MouseEvent event) {
        cash_pay_box.getChildren().remove(cash_insert_msg);

        // 새로운 Label 생성
        Label cash_pay_msg = new Label("결재 진행 중입니다");
        Font font = Font.font("HYsupB", 20);
        cash_pay_msg.setFont(font);
        cash_pay_msg.setStyle("-fx-text-fill: #5b4547;");
        cash_pay_msg.setPadding(new Insets(30, 0, 0, 0));
        cash_pay_box.getChildren().add(cash_pay_msg);

        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> {
            try {
                finish(event);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        pause.play();

    }

    @FXML
    public void card_pay_click(MouseEvent event) {
        card_pay_box.getChildren().remove(card_insert_msg);

        // 새로운 Label 생성
        Label card_pay_msg = new Label("결재 진행 중입니다");
        Font font = Font.font("HYsupB", 20);
        card_pay_msg.setFont(font);
        card_pay_msg.setStyle("-fx-text-fill: #5b4547;");
        card_pay_msg.setPadding(new Insets(30, 0, 0, 0));
        card_pay_box.getChildren().add(card_pay_msg);

        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> {
            try {
                finish(event);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        pause.play();

    }

    /* cash_pay, card_pay -> '현금'/'카드' 버튼 클릭 시 각 현금, 카드 결재 화면으로 이동.
     *
     */
    public void cash_pay(MouseEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("cash.fxml"));
        Stage pay_stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        pay_stage.setScene(scene);
        stageManager.addStage(pay_stage);
        pay_stage.show();

    }

    public void card_pay(MouseEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("card.fxml"));
        Stage pay_stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        pay_stage.setScene(scene);
        stageManager.addStage(pay_stage);
        pay_stage.show();

    }

    /* 결재 완료 화면이 나타나고 1.5초 뒤에 이전에 모든 창을 닫고
     * 새로운 첫 화면을 띄우면 시작.
     */
    private void finish(MouseEvent event) throws IOException{

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("finish.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();

        payment controller = loader.getController();
        controller.show_pay();

        stage.setScene(new Scene(root));
        stageManager.addStage(stage);
        PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
        pause.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("startscreen1.fxml"));
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                    stageManager.closeAllStages();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        pause.play();
        stage.show();

    }
    /* 결재 완료 화면에서 총 결재 금액을 나타내줌.
     *
     */
    private void show_pay() {
        int totalPrice = 0;
        for (ClientProductData product : initial_ctrl.order_list) {
            totalPrice += product.getPrice() * product.getQuantity();
        }
        pay.setText("총 결잼 금액은 : " + totalPrice + ClientController.WON);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
