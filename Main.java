import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        readFile("src/MyProgram.basic");


    }



    static ArrayList<String> tokens = new ArrayList<>();
    static ArrayList<Variable> variablesInMemory = new ArrayList<>();
    static ArrayList<String> thingsToPrint = new ArrayList<>();

    public String[] primitiveTypes = {"number", "string"};
    public String[] recognizedOperators = {"+", "-", "*", "/"};
    public String[] recognizedExpressions = {"print", "equate", "assign"};

    public static void readFile(String filePath){
        File file = new File(filePath);
        Scanner input = null;
        try {
            input = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder str = new StringBuilder();

        while(input.hasNextLine()){
            str.append(input.nextLine().toLowerCase());
        }
        input.close();

        tokenize(str.toString());
    }

        static int counter = 0;
        static String tempString;
    public static void tokenize(String stringFromFile){


        String contents = (stringFromFile.replaceAll(" ", "").replaceAll("\n", "").replaceAll("\t", ""));

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
