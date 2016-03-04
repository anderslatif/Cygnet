/**
 * Created by Anders on 3/1/2016.
 */
public class Variable {

    public String name;
    public String content;

    public Variable(String name, String content){
        this.name = name;
        this.content = content;
    }

    public String toString(){
        return "var: " + name + " = " + content;
    }


}
