module com.example.java_api_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;


    opens com.example.java_api_project to javafx.fxml;
    exports com.example.java_api_project;
}