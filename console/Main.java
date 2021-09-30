package console;
import java.util.regex.*;
public class Main 
{
    public static void main(String[] args) 
    {
        String input = String.join("", args);
        Pattern p = Pattern.compile("--(\\w+)?<(.*?)?>");
        Matcher m = p.matcher("--insert<a,b,c> --delete<a>");
        while(m.find())
        {
            String command = m.group(1);
            String[] parameters = m.group(2).split(",");
            System.out.println(command);
            for(String i: parameters)
            {
                System.out.println(i);
            }
            System.out.println();
        }
        
    }
}
