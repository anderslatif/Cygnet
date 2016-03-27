package HugeInteger;

/**
 * Created by Anders on 3/27/2016.
 */
public class HugeInteger {

    private String hugeInteger;

    public HugeInteger(String hugeInteger){
        this.hugeInteger = hugeInteger;
    }

    public String getHugeInteger(){
        return hugeInteger;
    }

    public void setHugeInteger(String hugeInteger){
        this.hugeInteger = hugeInteger;
    }


    public String add(String operandB){
        StringBuilder result = new StringBuilder();
        int length;
        int otherLength;
        String operandA = hugeInteger;

        if(operandB.charAt(0) == '-'){
            // subtract(operandB);    //make sure that we don't return here
        }
        if(operandA.charAt(0) == '-'){
            setHugeInteger(operandB);
            // subtract(operandA)  // make sure that we don't return here
        }

        if(operandA.length() > operandB.length()){
            length = operandA.length();
            otherLength = operandB.length();

            int lengthDifference = length - otherLength;
            String zerosToAdd = "";
            for(int i = 0; i < lengthDifference; i++){
                zerosToAdd += "0";
            }
            operandB = zerosToAdd + operandB;
        } else {
            length = operandB.length();
            otherLength = operandA.length();

            int lengthDifference = length - otherLength;
            String zerosToAdd = "";
            for(int i = 0; i < lengthDifference; i++){
                zerosToAdd += "0";
            }
            operandA = zerosToAdd + operandA;
        }


        boolean withCarry = false;
        int carry = 0;

        int a;
        int b;

        for(int i = length - 1; i >= 0; i--){

            a = Character.getNumericValue(operandA.charAt(i));
            b = Character.getNumericValue(operandB.charAt(i));

            a = a + b;
            if(withCarry){
                a += carry;
            }

            String temporaryResult = Integer.toString(a);

            if(a >= 10){
                carry = Character.getNumericValue(temporaryResult.charAt(0));
                int intToAdd = Character.getNumericValue(temporaryResult.charAt(1));
                result.insert(0, intToAdd);
                withCarry = true;
                if(i == 0){
                    result.insert(0, carry);
                    return result.toString();
                }
            } else {
                result.insert(0, temporaryResult);
                withCarry = false;
            }
        }
        setHugeInteger(result.toString());
        return result.toString();
    }


    public String subtract(String operandB){
        String operandA = hugeInteger;
        StringBuilder result = new StringBuilder();

        if(operandA.charAt(0) == '-'){
            // call a different method
        }

        setHugeInteger(result.toString());
        return result.toString();
    }

/*
    public String divide(String operandA, String operandB){


    }

    public String multiply(String operandA, String operandB){


    }*/




}
