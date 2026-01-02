public class UrlService {


        private final UrlRepository urlRepository;
        private final ShortCodeGenerator generator;  //used for creating shortUrl

        public UrlService( UrlRepository urlRepository, ShortCodeGenerator generator){
            this.urlRepository=urlRepository;
            this.generator=generator;
        }
        public String createShortUrl(String longUrl){

            String shortCode=generator.getShortCode(longUrl);
            while(urlRepository.exists(shortCode)){
                shortCode=generator.getShortCode(longUrl);
            }
            urlRepository.save(shortCode,longUrl);
            return shortCode;
        }

        public String getLongUrl(String shortCode){
            return urlRepository.find(shortCode);
        }



}
