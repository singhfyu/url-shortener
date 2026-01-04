import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UrlCache {
    private final Map<String, String> cache=new ConcurrentHashMap<>();

    public String get(String shortCode){
        return cache.get(shortCode);
    }

    public void put(String shortCode, String longUrl){
        cache.put(shortCode,longUrl);
    }
    /*public boolean contains(String shortCode){
        return cache.containsKey(shortCode);
    }*/
}
