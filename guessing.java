import java.io.*;
import java.util.*;

public class guessing {

    public static void main(String[] args) throws FileNotFoundException {

        Scanner scan = new Scanner(new File("input.txt"));
        Scanner type = new Scanner(System.in);
        HashMap<String, ArrayList<String>> players = new HashMap<>();

        while (scan.hasNext()) {
            ArrayList<String> list = new ArrayList<>(Arrays.asList(scan.nextLine().split("/")));
            if (list.get(4).length() == 2) {
                list.set(4, list.get(4).substring(1));
            }
            String name = list.remove(0);
            players.put(name, list);
        }

        Random random = new Random();
        List<String> keys = new ArrayList<>(players.keySet());
        String randomName = keys.get(random.nextInt(keys.size()));
        ArrayList<String> randomInfo = players.get(randomName);

        System.out.println("Welcome to guess that NBA player! Type an NBA player and we will list all the attributes to see if you are close depending on which team, conference, division, position, height, weight, age and jersey number!");
        System.out.println("You have 8 tries to guess this random NBA player!");

        String[][] table = new String[16][9];
        for (String[] row : table) {
            Arrays.fill(row, " ");
        }

        int g = 0;
        String guessName = "";

        while (!guessName.equals(randomName) && g < 16) {

            guessName = type.nextLine();
            System.out.println();

            if (!players.containsKey(guessName)) {
                System.out.println("Please type the player name correctly!");
                System.out.println();
                continue;
            }

            if (!guessName.equals(randomName)) {
                ArrayList<String> first = players.get(guessName);

                for (int i = 0; i < 8; i++) {
                    table[g][i] = first.get(i);
                }

                compareAttributes(first, randomInfo, table, g);
                g += 2;
                printTable(table);
            }
        }

        if (guessName.equals(randomName)) {
            for (int i = 0; i < 8; i++) {
                table[14][i] = randomInfo.get(i);
                table[15][i] = "Green";
            }
            printTable(table);
            System.out.println("Congrats! You have won the game!!");
        } else {
            System.out.println("Sorry! You are out of guesses! The correct answer is " + randomName);
        }
    }

    public static void printTable(String[][] table) {
        for (String[] row : table) {
            for (String cell : row) {
                System.out.print(cell + "  ");
            }
            System.out.println();
        }
    }

    public static void compareAttributes(ArrayList<String> first, ArrayList<String> randomInfo, String[][] table, int g) {
        table[g + 1][1] = first.get(0).equals(randomInfo.get(0)) ? "Team:Green" : "Team:Grey";
        table[g + 1][2] = first.get(1).equals(randomInfo.get(1)) ? "Conference:Green" : "Conference:Grey";
        table[g + 1][3] = first.get(2).equals(randomInfo.get(2)) ? "Division:Green" : "Division:Grey";
        table[g + 1][4] = first.get(3).equals(randomInfo.get(3)) ? "Position:Green" : "Position:Grey";

        int heightOne = heightFormatter(first);
        int heightTwo = heightFormatter(randomInfo);

        if (heightOne == heightTwo) {
            table[g + 1][5] = "Height:Green";
        } else if (Math.abs(heightOne - heightTwo) <= 3) {
            table[g + 1][5] = "Height:Yellow";
        } else {
            table[g + 1][5] = "Height:Grey";
        }

        compareNumeric(first.get(5), randomInfo.get(5), "Weight", table, g, 6);
        compareNumeric(first.get(6), randomInfo.get(6), "Age", table, g, 7);
        compareNumeric(first.get(7), randomInfo.get(7), "Number", table, g, 8);
    }

    public static void compareNumeric(String valueOne, String valueTwo, String attribute, String[][] table, int g, int column) {
        int intOne = Integer.parseInt(valueOne);
        int intTwo = Integer.parseInt(valueTwo);

        if (intOne == intTwo) {
            table[g + 1][column] = attribute + ":Green";
        } else if (Math.abs(intOne - intTwo) <= 3) {
            table[g + 1][column] = attribute + ":Yellow";
        } else {
            table[g + 1][column] = attribute + ":Grey";
        }
    }

    public static int heightFormatter(ArrayList<String> attributes) {
        String height = attributes.get(4);
        String[] h = height.split("-");
        return Integer.parseInt(h[0]) * 12 + Integer.parseInt(h[1]);
    }
}
