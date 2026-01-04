public interface UrlRepository {
    void save(String shortCode, String longUrl);
    //boolean exists(String shortCode);
    String find(String shortCode);
    public void incrementClickCount(String shortCode);
   // void save();
}
