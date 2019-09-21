package lotto;

import java.util.*;
import java.util.stream.IntStream;

public class LottoSimulator {

    private final int NUMBERS_EACH_ROW;
    private final int LOWEST_NUMBER = 1;
    private final int HIGHEST_NUMBER = 41;
    private int[][] numbers;
    private int[] randomNumbers;
    private int[] correctGuessesEachRow;
    private Random random = new Random();
    private int rows;

    public LottoSimulator(int numbersEachRow) {
        this.NUMBERS_EACH_ROW = numbersEachRow;
    }

    public void initialize(int userInputRows) {
        rows = userInputRows;
        correctGuessesEachRow = new int[rows];
        numbers = new int[rows][NUMBERS_EACH_ROW];
        randomNumbers = getRowOfRandomNumbers(random);
    }

    public void calculateAmountOfCorrectNumbersInEachRow() {
        for (int rowNumber = 0; rowNumber < rows; rowNumber++) {
            correctGuessesEachRow[rowNumber] = getAmountOfCorrectNumbersInRow(numbers[rowNumber]);
        }
    }

    private int getAmountOfCorrectNumbersInRow(int[] guessedRow) {
        int correctNumbers = 0;

        for (int guessedNumber : guessedRow) {
            if (isNumberInArray(randomNumbers, guessedNumber)) correctNumbers++;
        }

        return correctNumbers;
    }

    public boolean isNumberInArray(int[] array, int number) {
        return IntStream.of(array).anyMatch(x -> x == number);
    }

    /**
     * Uses Collections.shuffle to create 7 unique numbers from 1 - 41.
     */
    private int[] getRowOfRandomNumbers(Random rand) {
        int[] randomNumbers = new int[NUMBERS_EACH_ROW];
        ArrayList<Integer> possibleNumbers = new ArrayList<>();
        for (int i = LOWEST_NUMBER; i <= HIGHEST_NUMBER; i++) {
            possibleNumbers.add(i);
        }
        Collections.shuffle(possibleNumbers, rand);

        for (int i = 0; i < NUMBERS_EACH_ROW; i++) {
            randomNumbers[i] = possibleNumbers.get(i);
        }
        Arrays.sort(randomNumbers);
        return randomNumbers;
    }

    public boolean validateUserInputNumber(String input) {
        try {
            int number = Integer.parseInt(input);
            if (number < LOWEST_NUMBER || number > HIGHEST_NUMBER) {
                System.out.println("    Tallene må være innefor intervallet [1, 41]");
                return false;
            }
        } catch (Exception e) {
            System.out.println("    Skriv du inn noe annet enn heltall?");
            return false;
        }
        return true;
    }

    public int[] validateUserInputNumbers(String input) {
        int[] numbers = new int[NUMBERS_EACH_ROW];
        input = input.replaceAll("\\s", "");
        try {
            List<String> userInputNumbers = Arrays.asList(input.split("\\s*,\\s*"));
            if (userInputNumbers.size() != NUMBERS_EACH_ROW) {
                System.out.println("    Sjekk antall tall.");
                return null;
            }
            for (int i = 0; i < userInputNumbers.size(); i++) {
                // Number is not in interval [1,41]
                if (!validateUserInputNumber(userInputNumbers.get(i))) {
                    return null;
                }

                int newNumber = Integer.parseInt(userInputNumbers.get(i));
                // Checks if number already exists in users row
                if (isNumberInArray(numbers, newNumber)) {
                    System.out.println("    Du kan ikke legge til flere like tall i samme rad.");
                    return null;
                }
                numbers[i] = newNumber;
            }
        } catch (Exception e) {
            return null;
        }
        return numbers;
    }

    public boolean validateUserInputRows(String input) {
        try {
            if (Integer.parseInt(input) < LOWEST_NUMBER) {
                System.out.println("    Oppgi et antall rader høyere enn 0.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("    Oppgi hvor mange rader du ønsker ved hjelp av ett heltall.");
            return false;
        }
        return true;
    }

    public boolean validateUserInputFastMode(String input) {
        input = input.replaceAll("\\s", "");
        List<String> userInput = Arrays.asList(input.split("\\s*;\\s*"));

        // Check if rows is in correct format, return false if not.
        if (!validateUserInputRows(userInput.get(0))) return false;
        rows = Integer.parseInt(userInput.get(0));

        initialize(rows);

        // Returns false if not entered in correct format.
        if (userInput.size() != (rows + 1)) return false;

        // Check if each row of number sis in correct format, return false if not.
        for (int i = 1; i < userInput.size(); i++) {
            int[] numbersInRow = validateUserInputNumbers(userInput.get(i));
            if (numbersInRow == null) return false;
            setNumbersInRow(numbersInRow, i - 1);
        }

        return true;
    }

    // Getters and setters

    public int getRows() {
        return rows;
    }

    public int[] getCorrectGuessesEachRow() {
        return this.correctGuessesEachRow;
    }

    public int getNUMBERS_EACH_ROW() {
        return NUMBERS_EACH_ROW;
    }

    public int[] getRandomNumbers() {
        return this.randomNumbers;
    }

    public int[][] getNumbers() {
        return this.numbers;
    }

    private void setRows(int rows) {
        this.rows = rows;
    }

    public void setNumbersInRow(int[] userNumbers, int row) {
        numbers[row] = userNumbers;
    }

}
