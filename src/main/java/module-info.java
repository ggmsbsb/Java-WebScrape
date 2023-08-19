module com.example.webscraperjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jsoup;


    opens com.example.webscraperjavafx to javafx.fxml;
    exports com.example.webscraperjavafx;
 // Replace with the actual package/module name


}