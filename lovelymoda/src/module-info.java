/**
 * 
 */
/**
 * 
 */
module lovelymoda {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
	requires javafx.web;
    
    exports com.lovelymoda;
    opens com.lovelymoda to javafx.fxml;
    opens com.lovelymoda.controller to javafx.fxml;
    opens com.lovelymoda.models to javafx.base, javafx.fxml;
}
