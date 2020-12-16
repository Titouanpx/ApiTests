import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class GameSpotApi extends AbstractApi{
    final String API_KEY = "c72d9db428845fb697a0efc06c3af8be96e77ac0";

    public int getGameID(String gameName)  {
        try {
            URL urlGameID = new URL("https://www.gamespot.com/api/games/?api_key=" + API_KEY + "&format=json&filter=name:" + gameName);

            return jsonReader(urlGameID).getJSONArray("results").getJSONObject(0).getInt("id");

        } catch (Exception e) {
            System.out.println(e.toString());
            return 0;
        }
    }

    public String getScore(String gameName)  {
        try {
            URL urlScore = new URL("https://www.gamespot.com/api/reviews/?api_key=" + API_KEY + "&format=json&filter=title:" + gameName);

            return jsonReader(urlScore).getJSONArray("results").getJSONObject(0).getString("score");
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    public void getInfos(String gameName)  {
        try {
            URL urlInfos = new URL("https://www.gamespot.com/api/games/?api_key=" + API_KEY + "&format=json&filter=name:" + gameName);

            String name = jsonReader(urlInfos).getJSONArray("results").getJSONObject(0).getString("name");
            System.out.println("name is " + name);

            String urlImage = jsonReader(urlInfos).getJSONArray("results").getJSONObject(0).getJSONObject("image").getString("original");
            System.out.println("image's url is " + urlImage);

            JSONArray genres = jsonReader(urlInfos).getJSONArray("results").getJSONObject(0).getJSONArray("genres");
            List<String> lstGenres = new ArrayList<>();
            for (Object genre: genres) {
                lstGenres.add(((JSONObject) genre).getString("name"));
            }
            for (int i = 1; i < lstGenres.size()+1; i++) {
                System.out.println("genre n°" + i + " is " + lstGenres.get(i-1));
            }

            JSONArray themes = jsonReader(urlInfos).getJSONArray("results").getJSONObject(0).getJSONArray("themes");
            List<String> lstThemes = new ArrayList<>();
            for (Object theme: themes) {
                lstThemes.add(((JSONObject) theme).getString("name"));
            }
            for (int i = 1; i < lstThemes.size()+1; i++) {
                System.out.println("theme n°" + i + " is " + lstThemes.get(i-1));
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
