import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.*;

public class WotApi extends AbstractApi{
    final String APPLICATION_ID = "5585aac07c7c77014e635f6da4c2f32d";

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

    public int getAccountID(String nickname) {
        try {
            URL urlGetAccountId = new URL("https://api.worldoftanks.eu/wot/account/list/?application_id=" + APPLICATION_ID + "&search=" + nickname);

            return jsonReader(urlGetAccountId).getJSONArray("data").getJSONObject(0).getInt("account_id");
        } catch (Exception e) {
            System.out.println(e.toString());
            return 0;
        }
    }

    public int getNbrBattles(String nickname) {
        try {
            URL urlGetAccountPersonalData = new URL("https://api.worldoftanks.eu/wot/account/info/?application_id=" + APPLICATION_ID + "&account_id=" + getAccountID(nickname) + "&fields=statistics.trees_cut%2C+statistics.all.wins%2C+statistics.all.battles");
            JSONObject statPersonal = jsonReader(urlGetAccountPersonalData).getJSONObject("data").getJSONObject(String.valueOf(getAccountID(nickname))).getJSONObject("statistics");

            JSONObject statsFFA = statPersonal.getJSONObject("all");
            return statsFFA.getInt("battles");
        } catch (Exception e) {
            System.out.println(e.toString());
            return 0;
        }
    }

    public int getNbrWins(String nickname) {
        try {
            URL urlGetAccountPersonalData = new URL("https://api.worldoftanks.eu/wot/account/info/?application_id=" + APPLICATION_ID + "&account_id=" + getAccountID(nickname) + "&fields=statistics.trees_cut%2C+statistics.all.wins%2C+statistics.all.battles");
            JSONObject statPersonal = jsonReader(urlGetAccountPersonalData).getJSONObject("data").getJSONObject(String.valueOf(getAccountID(nickname))).getJSONObject("statistics");

            JSONObject statsFFA = statPersonal.getJSONObject("all");
            return statsFFA.getInt("wins");
        } catch (Exception e) {
            System.out.println(e.toString());
            return 0;
        }
    }

    public float getWinRate(String nickname) {
        int nbrBattles = getNbrBattles(nickname);
        float winRate = (float) 0;
        if (nbrBattles > 0) winRate = (float) getNbrWins(nickname)/nbrBattles*100;
        return limitDecimals(winRate);
    }

    public float getAverageTier(String nickname) {
        int nbrBattles = getNbrBattles(nickname);
        try {
            URL urlGetTanksById = new URL("https://api.worldoftanks.eu/wot/encyclopedia/vehicles/?application_id=" + APPLICATION_ID + "&fields=tier");
            JSONObject dataTankIds = jsonReader(urlGetTanksById).getJSONObject("data");

            URL urlGetPlayersTanks = new URL("https://api.worldoftanks.eu/wot/account/tanks/?application_id=" + APPLICATION_ID + "&account_id=" + getAccountID(nickname));
            JSONArray statsPlayersTanks = jsonReader(urlGetPlayersTanks).getJSONObject("data").getJSONArray(String.valueOf(getAccountID(nickname)));

            float averageTier = 0;
            for (int i = 0; i < statsPlayersTanks.length(); i++) {
                int tankId = statsPlayersTanks.getJSONObject(i).getInt(("tank_id"));
                int tankTier = dataTankIds.getJSONObject(String.valueOf(tankId)).getInt("tier");

                int tankNbrBattles = statsPlayersTanks.getJSONObject(i).getJSONObject("statistics").getInt(("battles"));

                averageTier += (tankTier * tankNbrBattles);
            }
            if (nbrBattles > 0) averageTier = averageTier / nbrBattles;
            else averageTier = (float) 0;
            return limitDecimals(averageTier);
        } catch (Exception e) {
            System.out.println(e.toString());
            return 0;
        }
    }

    public int getTreesCut(String nickname) {
        try {
            URL urlGetAccountPersonalData = new URL("https://api.worldoftanks.eu/wot/account/info/?application_id=" + APPLICATION_ID + "&account_id=" + getAccountID(nickname) + "&fields=statistics.trees_cut%2C+statistics.all.wins%2C+statistics.all.battles");

            JSONObject statPersonal = jsonReader(urlGetAccountPersonalData).getJSONObject("data").getJSONObject(String.valueOf(getAccountID(nickname))).getJSONObject("statistics");
            return statPersonal.getInt("trees_cut");
        } catch (Exception e) {
            System.out.println(e.toString());
            return 0;
        }
    }

    public float limitDecimals(float number) {
        BigDecimal numberFormat = new BigDecimal(number).setScale(2, RoundingMode.HALF_EVEN);
        return (float) numberFormat.doubleValue();
    }
}
