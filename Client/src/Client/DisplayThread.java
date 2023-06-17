package Client;

import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

public class DisplayThread implements Runnable {
    @Override
    public void run() {
        displayText();
    }
    
    private void displayText() {
        // Code to display text
    	Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText("Error");
        alert.setContentText("Error in file ");
        alert.showAndWait();    }
}
