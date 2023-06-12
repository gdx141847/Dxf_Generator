package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import com.jsevy.jdxf.*;
import com.jsevy.jdxf.DXFGraphics;
import javafx.stage.Stage;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;



public class Controller implements Initializable {

    FileChooser fileChooser = new FileChooser();

    DXFDocument dxfDocument = new DXFDocument();

    @FXML
    private TextField lengthInput;
    @FXML
    private TextField heightInput;
    @FXML
    private Label measureWarningLabel;
    @FXML
    private Label doneLabel;
    @FXML
    private Label lengthWarningLabel;
    @FXML
    private Label heightWarningLabel;
    @FXML
    private Button inputButton;
    @FXML
    private Button createButton;
    @FXML
    private Button saveButton;

    double length;
    double height;




    public void inputData(ActionEvent event) {

        try{
            if(lengthInput.getText().isEmpty() || heightInput.getText().isEmpty()){

                measureWarningLabel.setText("Wpisz wymiar!");

            }else{
                length = Double.parseDouble(lengthInput.getText());
                height = Double.parseDouble(heightInput.getText());

                if(length <=0 || height <=0){
                    measureWarningLabel.setText("Liczbą ujemna!");


                }else if(length <500){
                    lengthWarningLabel.setText("Minimalna długość 500 mm!");

                }else if(height <300){
                    heightWarningLabel.setText("Minimalna wysokość 300 mm!");

                }else{
                    doneLabel.setText("Gotowe!");

                }
            }
        }catch(NumberFormatException e){
            measureWarningLabel.setText("Wpisz liczbę!");
        }
        catch(Exception e){
            measureWarningLabel.setText("error");

        }


    }

    @FXML
    void createDxf(ActionEvent event) {

        drawDXF();

        lengthInput.setText("");
        heightInput.setText("");
        doneLabel.setText("Dxf stworzony!");


    }

    @FXML
    void saveDxf(ActionEvent event) {
        File file = fileChooser.showSaveDialog(new Stage());
        if(file != null) {

            String dxfText = dxfDocument.toDXFString();

            saveSystem(file, dxfText);

        }
        doneLabel.setText("");



    }

    public void drawDXF(){

        DXFGraphics dxfGraphics = dxfDocument.getGraphics();

        dxfGraphics.setColor(Color.WHITE);

        dxfGraphics.drawLine(0, 0, 0, -(height-20));
        dxfGraphics.drawLine(0, -(height-20), 20, -(height-20));
        dxfGraphics.drawLine(20, -(height-20), 20, -(height));
        dxfGraphics.drawLine(20, -(height), length-20, -(height));
        dxfGraphics.drawLine(length-20,-(height), length-20, -(height-20));
        dxfGraphics.drawLine(length-20, -(height-20), length, -(height-20));
        dxfGraphics.drawLine(length, -(height-20), length, 0);
        dxfGraphics.drawLine(length, 0, 0, 0);
        dxfGraphics.drawOval(10,-12.5,5,5);
        dxfGraphics.drawOval(length-12.5,-12.5,5,5);
        dxfGraphics.drawOval(length/2-2.5,-12.5,5,5);
        dxfGraphics.drawOval(50,-(height/2),22,22);
        dxfGraphics.drawRect(length/2,-(height/2+30),100,40);

        String stringOutput = dxfDocument.toDXFString();
        System.out.println(stringOutput);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fileChooser.setInitialDirectory(new File("C:\\Users\\dolob\\Desktop\\wsb\\semestr 7\\160922\\sem dyplomowe\\dxf"));
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("DXF file(*.dxf)","*.dxf");
        fileChooser.getExtensionFilters().addAll(extensionFilter);
    }

    public void saveSystem(File file, String content){

        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.write(content);
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}

