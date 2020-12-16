import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Launcher {
    public static void main(String[] args) throws MalformedURLException {
        System.out.println("World of Tanks API: \n");
        WotApi wotApi = new WotApi();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your World of Tanks nickname: "); //Suggested value: Madra_Mac_Tire
        String userName = scanner.nextLine();
        System.out.println();

        System.out.printf("%s has played %d battles%n", userName, wotApi.getNbrBattles(userName));
        System.out.println(userName +" has " + wotApi.getWinRate(userName) + "% win rate");
        System.out.printf("%s has cut down %d trees%n", userName, wotApi.getTreesCut(userName));
        System.out.println(userName + "'s average tier is " + wotApi.getAverageTier(userName));


        System.out.println("\nGameSpot API: \n");

        GameSpotApi gsApi = new GameSpotApi();
        String gameName = "World_Of_Tanks";

        URL url = new URL("https://www.gamespot.com/api/games/?api_key=c72d9db428845fb697a0efc06c3af8be96e77ac0&format=json&filter=name:" + gameName);

        System.out.println(gameName + "'s GameSpot id is: " + gsApi.getGameID(gameName));
        System.out.println(gameName + "'s GameSpot score is: " + gsApi.getScore(gameName) + "/10");
        gsApi.getInfos(gameName);



    }
}
