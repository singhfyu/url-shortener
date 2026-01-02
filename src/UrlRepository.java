public interface UrlRepository {
    void save(String shortCode, String longUrl);
    String find(String shortCode);

   // void save();
}
