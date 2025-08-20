module principal.petmonitormaven {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.desktop;
    requires java.sql;
    requires java.base;
    requires javafx.base;
    requires com.github.librepdf.openpdf;

    opens application to javafx.fxml;
    opens model.classes.prescricoes to javafx.base;
    opens model.classes.controleEstoque to javafx.base;
    opens controllerSubTelas to javafx.fxml;
    opens model.classes to javafx.fxml;
    exports application;
    exports model.classes;
    opens view.utils to javafx.fxml;
    exports view.utils;
    opens pdf to com.github.librepdf.openpdf;
}
