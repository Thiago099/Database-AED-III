package hash_table;

import java.util.*;

public class hashmap {
    public static void main(String[] args)
    {
        Map<String, String> map = new HashMap<String, String>();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");
        map.put("d", "4");
        map.put("e", "5");
        
        System.out.println(map.get("a"));       
    }
}
