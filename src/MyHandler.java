import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

class MyHandler implements HttpHandler {

    private final UrlService urlService;

    public MyHandler(UrlService urlService){
        this.urlService = urlService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method=exchange.getRequestMethod();
        String path=exchange.getRequestURI().getPath();
        if("POST".equals(method) && "/shorten".equals(path)){
            hanldePost(exchange);
        } else if ("GET".equals(method)) {
            hanldeGet(exchange);
        }
        else{
            exchange.sendResponseHeaders(405,-1);
        }
    }

    private void hanldeGet(HttpExchange exchange) throws IOException {

        String path=exchange.getRequestURI().getPath(); //"/abc123"
        String shortCode=path.substring(1); //"abc123"

        //UrlService service = new UrlService();
        String longUrl= urlService.getLongUrl(shortCode);

        if (longUrl== null) {
            exchange.sendResponseHeaders(404, -1);
            return;
        }

        exchange.getResponseHeaders().add("Location", longUrl);
        exchange.sendResponseHeaders(302, -1);
    }

    private void hanldePost(HttpExchange exchange) throws IOException{

        String longUrl= new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);

        String shortCode= urlService.createShortUrl(longUrl);  //you can apply Base62 for this
        byte[] response= shortCode.getBytes(StandardCharsets.UTF_8);

        exchange.sendResponseHeaders(200, response.length);

        OutputStream os= exchange.getResponseBody();
        os.write(response);
        os.close();
    }
}
