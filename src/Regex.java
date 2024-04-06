import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    boolean Search (String line, String regex){
        //İçerisinde bir şeyler yazan tek satır doc olup olmadığını kontrol ediyor
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()){
            return true;
        }
        return false;
    }
}
