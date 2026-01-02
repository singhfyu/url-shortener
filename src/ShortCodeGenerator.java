import java.util.UUID;

public class ShortCodeGenerator {

    //private final String longURL;
    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public String getShortCode(String longUrl){
        //int hash=Math.abs(longUrl.hashCode());//Convert longURL to a unique integer
        UUID uuid = UUID.randomUUID();
        long value = uuid.getMostSignificantBits() & Long.MAX_VALUE;

        return toBase62(value);
    }

    public String toBase62(long num){
        if(num==0) return "0";
        StringBuilder hashStr= new StringBuilder();
        while(num>0){
            long remainder=num%62;
            hashStr.append(BASE62.charAt((int)(remainder)));
            num/=62;
        }
        return hashStr.reverse().toString() ;
    }

}
