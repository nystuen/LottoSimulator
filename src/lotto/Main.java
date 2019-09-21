package lotto;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        LottoSimulator lottoSimulator = new LottoSimulator(7);
        Scanner inputAgent = new Scanner(System.in);
        boolean quickWorkflow = false;

        System.out.println("    Velkommen til Lottosimulatoren!");
        System.out.println("    Ønsker du å spille i kjapt modus? (Y/N)");

        while (true) {
            quickWorkflow = getWorkflowMode(inputAgent);
            if (quickWorkflow) {
                initQuickWorkflow(lottoSimulator, inputAgent);
            } else {
                initNormalWorkflow(lottoSimulator, inputAgent);
            }
            System.out.println("    Ønsker du å spille igjen? Oppgi isåfall om du ønsker kjapt modus eller ikke. (Y/N)");
        }

    }

    private static void initQuickWorkflow(LottoSimulator lottoSimulator, Scanner inputAgent) {
        System.out.println("    KJAPT MODUS: Skriv på følgende format:" +
                "\n    Format:    rad; x,x,x,x,x,x; x,x,x,x,x,x; x,x,x,x,x,x;" +
                "\n    Eks:       3; 1,2,3,4,5,6,7; 8,9,10,11,12,13,14; 15,16,17,18,19,20,21;");

        while (!lottoSimulator.validateUserInputFastMode(inputAgent.nextLine())) {
            System.out.println("    Vennligst oppgi antall rader og tall per rad på det gitte formatet.");
        }

        System.out.println("    Dine rader: " + Arrays.deepToString(lottoSimulator.getNumbers()));
        System.out.println("    Vinnertall: " + Arrays.toString(lottoSimulator.getRandomNumbers()));

        lottoSimulator.calculateAmountOfCorrectNumbersInEachRow();
        int[] correctGuessesEachRow = lottoSimulator.getCorrectGuessesEachRow();

        System.out.println("    Antall riktig for hhv. hver rad: " + Arrays.toString(correctGuessesEachRow));
    }

    private static void initNormalWorkflow(LottoSimulator lottoSimulator, Scanner inputAgent) {
        System.out.println("    Vennligst oppgi hvor mange rader du ønsker å gjette.");
        getUserInputRowsNormalWorkflow(lottoSimulator, inputAgent);

        System.out.println("    Nå er det på tide å oppgi dine tall for hver rad." + "\n    Dette gjøres ved å skrive 7 tall, separert med komma." + "\n    Eksempel: 5,9,10,1,20,41,34");

        for (int row = 0; row < lottoSimulator.getRows(); row++) {
            System.out.println("    Vennligst oppgi dine tall på rad: " + row);
            int[] rowNumbers = getUserInputNumbersNormalWorkflow(lottoSimulator, inputAgent, row);
            lottoSimulator.setNumbersInRow(rowNumbers, row);
        }

        System.out.println("    Dine rader: " + Arrays.deepToString(lottoSimulator.getNumbers()));
        System.out.println("    Vinnertall: " + Arrays.toString(lottoSimulator.getRandomNumbers()));

        lottoSimulator.calculateAmountOfCorrectNumbersInEachRow();
        int[] correctGuessesEachRow = lottoSimulator.getCorrectGuessesEachRow();

        System.out.println("    Antall riktig for hhv. hver rad: " + Arrays.toString(correctGuessesEachRow));
    }

    private static int[] getUserInputNumbersNormalWorkflow(LottoSimulator lotto, Scanner inputAgent, int row) {
        String userInput = inputAgent.nextLine();
        int[] numbers = lotto.validateUserInputNumbers(userInput);
        while (numbers == null) {
            System.out.println("    Det oppsto en feil. Prøv igjen! \n    Rad: " + row);
            numbers = lotto.validateUserInputNumbers(inputAgent.nextLine());
        }

        return numbers;
    }

    private static void getUserInputRowsNormalWorkflow(LottoSimulator lotto, Scanner inputAgent) {
        String userInput = inputAgent.nextLine();
        int rows;

        while (!lotto.validateUserInputRows(userInput)) {
            System.out.println("    Prøv igjen :)");
            userInput = inputAgent.nextLine();
        }

        lotto.initialize(Integer.parseInt(userInput));
    }

    // True indicates fast mode, false indicates normal mode
    private static boolean getWorkflowMode(Scanner inputAgent) {
        String userInput = inputAgent.nextLine();

        while (validateWorkflowInput(userInput) == -1) {
            System.out.println("    En feil oppsto. Skriv enten 'Y' eller 'N' for å indikere om du vil spille i kjapt modus.");
            userInput = inputAgent.nextLine();
        }

        return validateWorkflowInput(userInput) == 1;
    }

    private static int validateWorkflowInput(String workflowString) {
        if (workflowString.length() == 1) {
            if (workflowString.toLowerCase().equals("y")) {
                return 1;
            } else if (workflowString.toLowerCase().equals("n")) {
                return 0;
            }
        }
        return -1;
    }

}
