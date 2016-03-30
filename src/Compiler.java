import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.ArrayList;

/**
 * Created by Anders on 3/30/2016.
 */
public class Compiler {


    public static void execute(TextArea codeInput, Label variablesList, Label consoleOutput){

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
    public String[] operators = {"+", "-", "*", "/"};
    public String[] expressions = {"print", "equate", "assign"};



    public static void tokenize(String content){
        int counter;
        String tempString;
        tokens.clear();
        variablesInMemory.clear();
        thingsToPrint.clear();


        for(counter = 0; counter <= content.length()-1; counter++){

            if(content.charAt(counter) == 'p' && content.charAt(counter+1) == 'r' && content.charAt(counter+2) == 'i' && content.charAt(counter+3) == 'n' && content.charAt(counter+4) == 't'){
                tokens.add("print");
                counter += 5;
                tempString = "";

                while(content.charAt(counter) != '.'){
                    tempString += content.charAt(counter);
                    counter++;
                }
                tokens.add(tempString);
            }

            if(content.charAt(counter) == 'e' && content.charAt(counter+1) == 'q' && content.charAt(counter+2) == 'u' && content.charAt(counter+3) == 'a'
                    && content.charAt(counter+4) == 't' && content.charAt(counter+5) == 'e'){
                tokens.add("equate");
                counter += 6;
                tempString = "";

                while(content.charAt(counter) != '.'){
                    tempString += content.charAt(counter);
                    counter++;
                }
                tokens.add(tempString);
            }

            if(content.charAt(counter) == 'a' && content.charAt(counter+1) == 's' && content.charAt(counter+2) == 's' && content.charAt(counter+3) == 'i'
                    && content.charAt(counter+4) == 'g' && content.charAt(counter+5) == 'n'){
                tokens.add("assign");
                counter += 6;
                tempString = "";
                String tempString2 = "";

                while(content.charAt(counter) != '='){
                    tempString += content.charAt(counter);
                    counter++;
                }
                counter++; //gets rid of the equals sign
                while(content.charAt(counter) != '.'){
                    tempString2 += content.charAt(counter);
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
                Equate.equate(tokens.get(i+1));
                i++;
            }

            if(tokens.get(i).equals("assign")){
                Variable variable = new Variable(tokens.get(i+1), tokens.get(i+2));
                variablesInMemory.add(variable);
                i+=2;
            }


        }
    }



}
