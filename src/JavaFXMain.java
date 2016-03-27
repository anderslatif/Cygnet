import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by Anders on 3/1/2016.
 */
public class JavaFXMain extends Application {

    public static void main(String[] args){
        launch(args);
    }

    static TextArea codeInput;
    static Label variablesList;
    static Label consoleOutput;

    @Override
    public void start(Stage primaryStage) throws Exception {

        //  compile the code first where equations are calculated
        // handle that spaces should be removed when parsed but printed statements should include spaces
        // allow period if // comes before, then remove the two slashes
        // not allowed to have the same variable if that occurs then delete both the variable and the corresponding value


        // before parsing there are lexers that lexes into lexemes

        BorderPane borderPane = new BorderPane();
        HBox menu = getHeaderMenu();
        borderPane.setTop(menu);

        Label variableListDescription = new Label("List of variables\nin the memory:\n\n");
        variablesList = new Label();
        variablesList.setMinWidth(100);
        VBox leftVBox = new VBox();
        leftVBox.getChildren().addAll(variableListDescription, variablesList);
        borderPane.setLeft(leftVBox);

        codeInput = new TextArea();
        borderPane.setCenter(codeInput);

        codeInput.setText("print hello.\n" +
                "equate 1 + 1.\n" +
                "assign a = 3.");



        consoleOutput = new Label("Console:");
        consoleOutput.setMinHeight(100);
        borderPane.setBottom(consoleOutput);




        primaryStage.setTitle("Cygnet");
        Scene scene = new Scene(borderPane, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }





    public static HBox getHeaderMenu(){
        Menu preferencesMenu = new Menu("About");

        MenuItem preferences = new MenuItem("_Preferences");
        SeparatorMenuItem separator = new SeparatorMenuItem();
        MenuItem about = new MenuItem("_About");
        about.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.SHIFT_DOWN, KeyCombination.CONTROL_DOWN));
        about.setOnAction( e -> aboutTheProduct());
        preferencesMenu.getItems().addAll(preferences, separator, about);

        Button run = new Button("Run");
        run.setOnAction( e -> execute());

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(preferencesMenu);

        HBox hBox = new HBox();
        hBox.setSpacing(60);
        hBox.getChildren().addAll(run, menuBar);

        return hBox;
    }


    private static void aboutTheProduct() {
        Stage aboutWindow = new Stage();
        aboutWindow.initModality(Modality.APPLICATION_MODAL);
        aboutWindow.setTitle("All About - About All");
        aboutWindow.setMinWidth(250);

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));

        Label label1 = new Label("Product: Cygnet Programming Language");
        Label label2 = new Label("Version: 0.0.1");
        Label label3 = new Label("Written in JavaFX");
        Label label4 = new Label("Created by: Anders Latif");


        vBox.getChildren().addAll(label1, label2, label3, label4);

        Scene scene = new Scene(vBox);
        aboutWindow.setScene(scene);
        aboutWindow.showAndWait();
    }



    public static void execute(){

        tokenize(codeInput.getText());
        consoleOutput.setText("");
        try {
            String string = "";
            for (Variable v : variablesInMemory) {
                string += v + "\n";
            }
            variablesList.setText(string);

            String string2 = "";
            for (String s : thingsToPrint) {
                string2 += s + "\n";
            }
            consoleOutput.setText(string2);
        } catch (Exception e){
            e.printStackTrace();
            consoleOutput.setText("Compile Error");
        }

    }



    static ArrayList<String> tokens = new ArrayList<>();
    static ArrayList<Variable> variablesInMemory = new ArrayList<>();
    static ArrayList<String> thingsToPrint = new ArrayList<>();

    public String[] primitiveTypes = {"number", "string"};
    public String[] recognizedOperators = {"+", "-", "*", "/"};
    public String[] recognizedExpressions = {"print", "equate", "assign"};



    public static void tokenize(String text){
        int counter;
        String tempString;
        tokens.clear();
        variablesInMemory.clear();
        thingsToPrint.clear();

        String contents = (text.replaceAll(" ", "").replaceAll("\n", "").replaceAll("\t", ""));

        for(counter = 0; counter <= contents.length()-1; counter++){

            if(contents.charAt(counter) == 'p' && contents.charAt(counter+1) == 'r' && contents.charAt(counter+2) == 'i' && contents.charAt(counter+3) == 'n' && contents.charAt(counter+4) == 't'){
                tokens.add("print");
                counter += 5;
                tempString = "";

                while(contents.charAt(counter) != '.'){
                    tempString += contents.charAt(counter);
                    counter++;
                }
                tokens.add(tempString);
            }

            if(contents.charAt(counter) == 'e' && contents.charAt(counter+1) == 'q' && contents.charAt(counter+2) == 'u' && contents.charAt(counter+3) == 'a'
                    && contents.charAt(counter+4) == 't' && contents.charAt(counter+5) == 'e'){
                tokens.add("equate");
                counter += 6;
                tempString = "";

                while(contents.charAt(counter) != '.'){
                    tempString += contents.charAt(counter);
                    counter++;
                }
                tokens.add(tempString);
            }

            if(contents.charAt(counter) == 'a' && contents.charAt(counter+1) == 's' && contents.charAt(counter+2) == 's' && contents.charAt(counter+3) == 'i'
                    && contents.charAt(counter+4) == 'g' && contents.charAt(counter+5) == 'n'){
                tokens.add("assign");
                counter += 6;
                tempString = "";
                String tempString2 = "";

                while(contents.charAt(counter) != '='){
                    tempString += contents.charAt(counter);
                    counter++;
                }
                counter++; //gets rid of the equals sign
                while(contents.charAt(counter) != '.'){
                    tempString2 += contents.charAt(counter);
                    counter++;
                }
                tokens.add(tempString);
                tokens.add(tempString2);
            }



        }
        parse();
    }


    public static void parse(){

        for(int i = 0; i < tokens.size(); i++){

            if(tokens.get(i).equals("print")){
                thingsToPrint.add(tokens.get(i+1));
                i++;
            }

            if(tokens.get(i).equals("equate")){
                equate();
                i++;
            }

            if(tokens.get(i).equals("assign")){
                Variable variable = new Variable(tokens.get(i+1), tokens.get(i+2));
                variablesInMemory.add(variable);
                i+=2;
            }


        }
    }


    public static void equate(){
        System.out.println("This method should calculate.");
    }




}
