public class UrlService {


        private final UrlRepository urlRepository;
        private final ShortCodeGenerator generator;  //used for creating shortUrl
        private final UrlCache cache;

        public UrlService( UrlRepository urlRepository, ShortCodeGenerator generator, UrlCache cache){
            this.urlRepository=urlRepository;
            this.generator=generator;
            this.cache=cache;
        }
        public String createShortUrl(String longUrl){
            int maxTries=5;
              for(int i=0;i<maxTries;i++) {
                String shortCode=generator.getShortCode(longUrl);
                try {
                    urlRepository.save(shortCode, longUrl);
                    return shortCode;
                } catch (RuntimeException e) {
                    shortCode = generator.getShortCode(longUrl);
                }
            }
            throw new RuntimeException("Failed to generate unique short code after retries");
        }

        public String getLongUrl(String shortCode){
            //look cache  first
            String cachedUrl=cache.get(shortCode);
            if(cachedUrl!=null){
                System.out.println("Cache hit for "+ shortCode);
                urlRepository.incrementClickCount(shortCode);
                return cachedUrl;
            }
            //look DB if cache didn't have the link
            String longUrl= urlRepository.find(shortCode);
            if(longUrl != null){
                System.out.println("DB hit for "+ shortCode);
                urlRepository.incrementClickCount(shortCode);
                cache.put(shortCode, longUrl);
            }
            return longUrl;
        }

}
