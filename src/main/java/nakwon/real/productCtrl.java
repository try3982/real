package nakwon.real;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class productCtrl implements Initializable {
    /* X_Button -> 장바구니에 추가된 상품을 취소하려고할 때 x 버튼을 상품 오른쪽 상단에 표시하기 위함.
     * order_img, order_name, order_price -> 장바구니에 추가한 상품 이미지,이름,가격
     * img, nameLabel, priceLabel -> 상품 데이터를 초기화할 때 저장하기 위한 것
     * L_set_box, R_set_box, single_box -> 단품 메뉴를 선택 했을 때 라지 세트, 일반 세트, 단품을 나타내기 위함.
     * L_set_box2, R_set_box2 -> 세트 메뉴 선택 시 라지 세트, 일반 세트를 나타내기 위함.
     * quantity_Label -> 장바구니에 추가된 상품의 수량 표시.
     * cancel_button -> 사이드, 음료만 선택시 장바구니에 추가/취소 여부를 확인할 때 취소 버튼을 나타내기 위함.
     */
    @FXML
    private Button X_Button;

    @FXML
    private ImageView order_img;

    @FXML
    private Label order_name;

    @FXML
    private Label order_price;

    @FXML
    private ImageView img;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;

    @FXML
    public VBox L_set_box;

    @FXML
    public VBox R_set_box;

    @FXML
    public VBox L_set_box2;

    @FXML
    public VBox R_set_box2;

    @FXML
    public VBox single_box;

    @FXML
    public VBox product_box;

    @FXML
    private Button cancelButton;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Button dashboardbtn;


    @FXML
    private Label quantityLabel;
    /* order_product, choosenProdcut -> order_product는 최종 장바구니에 담을 상품에 대한 정보를 가짐, choosenProduct는 장바구니에 추가하기전 메뉴판에서 선택한 상품을 기억하기 위함.
     * single_cart, R_set_cart, L_set_cart -> 각각 최초 메뉴 선택시 나오는 화면에 넣을 상품들의 정보를 담기 위함.
     * chooseName, chooseImageSrc, choosePrice, choosequantity -> 선택한 메뉴의 이름, 이미지, 가격, 수량을 표시
     * result_pay -> 최종 결재 금액을 담기 위함.
     */
    private int quantity = 1;
    private ClientProductData product;
    private ClientProductData order_product;
    public ClientProductData choosenProduct;
    public ClientProductData single_cart;
    public ClientProductData R_set_cart;
    public ClientProductData L_set_cart;
    public String chooseName;
    public String chooseImageSrc;
    public int choosePrice;
    public int choosequantity;
    public int result_pay = 0;

    /* initialController의 controller를 가져오기 위함.
     * 이를 통해 productCtrl에서 계산되는 장바구니의 총 금액을 나타내기 위함.
     */
    initialController initial_ctrl = initialController.instance;

    /* 장바구니에 있는 상품에 대해 '-' 버튼을 누르면 수량을 줄여주고
     * 총 가격을 업데이트 함.
     */
    @FXML
    private void decreaseQuantity(MouseEvent event) {

        if (quantity > 1) {
            quantity--;
            order_product.setQuantity(quantity);
            updateQuantityLabel();
        }
        updateTotal();
    }

    /* 장바구니에 있는 상품에 대해 '+' 버튼을 누르면 수량을 늘여주고
     * 총 가격을 업데이트 함.
     */
    @FXML
    private void increaseQuantity(MouseEvent event) {
        quantity++;
        order_product.setQuantity(quantity);
        updateQuantityLabel();
        updateTotal();
    }

    /* cancelButton을 통해 사이드, 음료만 선택할 때 장바구니에 담는 것을 취소할 시 현재 창을 닫음.
     *
     */
    @FXML
    private void cancelButtonClicked(MouseEvent event) {
        // 현재 창 닫기
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /* 장바구니 추가 과정에서 각 단품, 라지 세트, 일반 세트를 선택하면 선택 창을 닫고 장바구니에 추가됨.
     * 사이드, 음료는 장바구니 추가 과정에서 확인 버튼을 누르면 장바구니에 추가하도록 진행.
     */
    @FXML
    private void Single_CartClick(MouseEvent event) {
        choosenProduct = single_cart;
        choosenProduct.quantity = quantity;
        initial_ctrl.addToCart(choosenProduct);
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

    }

    @FXML
    private void L_set_CartClick(MouseEvent event) {
        choosenProduct = L_set_cart;
        choosenProduct.quantity = quantity;
        initial_ctrl.addToCart(choosenProduct);

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

    }

    @FXML
    private void R_set_CartClick(MouseEvent event) {
        choosenProduct = R_set_cart;
        choosenProduct.quantity = quantity;
        initial_ctrl.addToCart(choosenProduct);

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

    }

    @FXML
    private void Product_CartClick(MouseEvent event) {
        choosenProduct.quantity = quantity;
        initial_ctrl.addToCart(choosenProduct);

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

    }

    public void chooseProduct(ClientProductData product) {
        this.choosenProduct = product;
        this.chooseName = product.getName();
        this.chooseImageSrc = "File:" + product.getImageSrc();
        this.choosePrice = product.getPrice();
        this.choosequantity = quantity;
    }

    /* 장바구니에 있는 상품의 수량 label을 update 하기 위함.
     *
     */
    private void updateQuantityLabel() {
        quantityLabel.setText(String.valueOf(quantity));

    }

    /* initial_ctrl에 선언된 result_price를 상품이 추가되거나 취소될 때마다 update 해줌.
     * 이를 통해 장바구니에 담겨져 있는 총 금액을 실시간으로 확인 가능. 여기서 result_pay는 최종 결재 화면에서 결재 금액을 알려주기 위함.
     */
    public void updateTotal() {
        int totalPrice = 0;
        for (ClientProductData product : initial_ctrl.order_list) {
            totalPrice += product.getPrice() * product.getQuantity();
        }

        initial_ctrl.result_price.setText("총 : " + totalPrice + ClientController.WON);

        result_pay = totalPrice;
    }

    /* X_Button 클릭 시 장바구니의 있는 상품을 없애기 위함.
     *
     */
    public void removeFromCart(MouseEvent event) {
        // 클릭된 'x' 버튼의 부모 요소를 찾아서 VBox를 가져옴
        X_Button = (Button) event.getSource();
        VBox vbox = (VBox) X_Button.getParent().getParent();
        // 장바구니에서 해당 VBox 제거
        initial_ctrl.cart_box.getChildren().remove(vbox);
        order_product.setQuantity(0);
        updateTotal();
    }

    /* 화면에 상품을 로드 하기 위해 상품에 대한 정보를 기억함.
     *
     */
    public void setData(ClientProductData product) {

        this.product = product;
        String path = "File:" + product.getImageSrc();
        Image image = new Image(path, 100, 75, false, true);
        img.setImage(image);
        nameLabel.setText(product.getName());
        priceLabel.setText(product.getPrice() + ClientController.WON);

    }

    /* 장바구니에 추가할 상품에 대한 정보를 기억하기 위함.
     *
     */
    public void order_setData(ClientProductData product) {

        this.order_product = product;
        String path = "File:" + product.getImageSrc();
        Image image = new Image(path, 100, 75, false, true);
        order_img.setImage(image);
        order_name.setText(product.getName());
        order_price.setText(product.getPrice() + ClientController.WON);

    }



    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub

    }

}
