package nakwon.real;

import java.util.ArrayList;
import java.util.List;

import javafx.stage.Stage;

public class StageManager {

    /* 전체적인 화면을 관리하기 위함.
     * 상품 주문 과정에서 계속해서 나타나 있는 화면들은 openStages를 통해 지니고 있음
     * 마지막 결재 완료 화면 이후에 openStages에 저장되어 있는 모든 창을 closeAllstges를 통해 닫음.
     */
    private static StageManager instance;
    private List<Stage> openStages;

    private StageManager() {
        openStages = new ArrayList<>();
    }

    public static StageManager getInstance() {
        if (instance == null) {
            instance = new StageManager();
        }
        return instance;
    }

    public void addStage(Stage stage) {
        openStages.add(stage);
    }

    public void removeStage(Stage stage) {
        openStages.remove(stage);
    }

    public void closeAllStages() {
        for (Stage stage : openStages) {
            stage.close();
        }
        openStages.clear();
    }

}
