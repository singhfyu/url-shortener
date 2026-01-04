import java.sql.*;
import java.time.LocalDateTime;

public class DBUrlRepository implements UrlRepository {
    private Connection connection;

    public DBUrlRepository() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/url_shortener", "root", "password");
    }

    public void save(String shortCode, String longUrl) {
        try {
            LocalDateTime expiryTime=LocalDateTime.now().plusHours(1);
            String sql = "INSERT INTO url_mapping(short_code, long_url, expires_at) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, shortCode);
            ps.setString(2, longUrl);
            ps.setTimestamp(3,Timestamp.valueOf(expiryTime));
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

   /* @Override
    public boolean exists(String shortCode) {
        try {
            String sql = "SELECT 1 FROM url_mapping WHERE short_code= ? LIMIT 1";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, shortCode);

            ResultSet resultSet = ps.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }*/

    @Override
    public String find(String shortCode) {
        try {
            String sql = "SELECT long_url from url_mapping WHERE short_code = ? AND (expires_at IS NULL OR expires_at >NOW());";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, shortCode);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("long_url");
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("DB error while finding URL", e);
        }
    }
    @Override
    public void incrementClickCount(String shortCode){
        try{
            String sql= "UPDATE url_mapping SET click_count=click_count+1 where short_code=? AND (expires_at>NOW() OR expires_at IS NULL)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, shortCode);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Failed to increment click count",e);
        }
    }
     
}