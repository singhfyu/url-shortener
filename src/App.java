import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class App {
    public static void main(String[] args) throws IOException, SQLException {
        //1. create repository(storage)
        //UrlRepository repository=new InMemoryUrlRepository(); //can be easily replaced with DB backed repository
        UrlRepository repository=new DBUrlRepository();
        UrlCache cache= new UrlCache();
        //2.create short code generator
        ShortCodeGenerator generator=new ShortCodeGenerator();
        //3.create service(business logic)
        UrlService urlService=new UrlService(repository,generator,cache);
        //4.create http server
        HttpServer server= HttpServer.create(new InetSocketAddress(8080),0);
        //5.Register handler
        server.createContext("/", new MyHandler(urlService)); //maps "/" -> MyHandler.handle()
        //6.start server
        server.start();

        System.out.println("URL Shortener running on http://localhost:8080");
    }
}
