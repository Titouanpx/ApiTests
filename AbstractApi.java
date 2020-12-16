import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/*
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URLConnection;
*/

public abstract class AbstractApi {

    public JSONObject jsonReader(URL url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String jsonText = reader.readLine();
            return new JSONObject(jsonText);

        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /*
    public Document xmlReader(URL url) {
        try {
            URLConnection uc =  url.openConnection();
            HttpURLConnection conn = (HttpURLConnection )uc;

            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(conn.getInputStream());
            doc.getDocumentElement().normalize();

            return doc;
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }*/
}
