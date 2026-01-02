import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUrlRepository implements UrlRepository{
    private Map<String,String > repository= new ConcurrentHashMap<>();
    @Override
    public void save(String shortCode, String longUrl){
        repository.put(shortCode,longUrl);
    }

    @Override
    public String find(String shortCode){
        return repository.get(shortCode);
    }


}
