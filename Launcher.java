import java.util.Scanner;

public class Launcher {
    public static void main(String[] args) {
        WotApi wotApi = new WotApi();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your World of Tanks nickname: "); //Suggested value: Madra_Mac_Tire
        String userName = scanner.nextLine();
        System.out.println();

        System.out.printf("%s has played %d battles%n", userName, wotApi.getNbrBattles(userName));
        System.out.println(userName +" has " + wotApi.getWinRate(userName) + "% win rate");
        System.out.printf("%s has cut down %d trees%n", userName, wotApi.getTreesCut(userName));
        System.out.println(userName + "'s average tier is " + wotApi.getAverageTier(userName));
    }
}
