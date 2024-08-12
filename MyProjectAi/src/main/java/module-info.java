module com.example.myprojectai {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.desktop;

    opens com.example.myprojectai to javafx.fxml;
    exports com.example.myprojectai;
}