package tests;

import lotto.LottoSimulator;
import org.junit.jupiter.api.*;

import java.util.HashSet;

class LottoTest {

    private LottoSimulator lottoSimulator;

    @BeforeEach
    void setUp() {
        lottoSimulator = new LottoSimulator(7);
        lottoSimulator.initialize(2);
    }

    @Test
    void initialize() {
        Assertions.assertNotNull(lottoSimulator.getRows());
        Assertions.assertNotNull(lottoSimulator.getCorrectGuessesEachRow());
        Assertions.assertNotNull(lottoSimulator.getRandomNumbers());
    }

    @Test
    void isNumberInArray() {
        int[] numbers = {1, 2, 3, 4, 5, 6, 7};
        int numberInList = 3;
        int numberNotInList = 10;
        Assertions.assertTrue(lottoSimulator.isNumberInArray(numbers, numberInList));
        Assertions.assertFalse(lottoSimulator.isNumberInArray(numbers, numberNotInList));
    }

    // Generates 100 new lottoSimulators and checks for duplicates by using HashSet
    @Test
    void getRowOfRandomNumbers() {
        lottoSimulator = new LottoSimulator(7);
        lottoSimulator.initialize(1);
        int[] randomnumbers = lottoSimulator.getRandomNumbers();
        HashSet<Integer> numbersCheck = new HashSet<>();
        for (int number : randomnumbers) {
            if (!numbersCheck.add(number)) Assertions.fail();
        }
    }

    @Test
    void validateUserInputNumber() {
        String lowNumber = "0";
        String highNumber = "42";
        String okNumber = "30";
        String notANumber = "abc";
        Assertions.assertEquals(false, lottoSimulator.validateUserInputNumber(lowNumber));
        Assertions.assertEquals(false, lottoSimulator.validateUserInputNumber(highNumber));
        Assertions.assertEquals(true, lottoSimulator.validateUserInputNumber(okNumber));
        Assertions.assertEquals(false, lottoSimulator.validateUserInputNumber(notANumber));
    }

    @Test
    void validateUserInputNumbers() {
        String tooManyNumbers = "1,2,3,4,5,6,7,8";
        String duplicateNumbers = "1,2,3,4,5,5,6";
        String notANumber = "1, 2, 3, 4, F, 5, 6";
        String okNumbers = "10, 15, 20, 25, 30, 40, 1";
        String okNumbersWithLotsOfSpaces = "   10, 15,      20, 25, 30,       40      , 1";
        Assertions.assertNull(lottoSimulator.validateUserInputNumbers(tooManyNumbers));
        Assertions.assertNull(lottoSimulator.validateUserInputNumbers(duplicateNumbers));
        Assertions.assertNull(lottoSimulator.validateUserInputNumbers(notANumber));
        Assertions.assertNotNull(lottoSimulator.validateUserInputNumbers(okNumbers));
        Assertions.assertNotNull(lottoSimulator.validateUserInputNumbers(okNumbersWithLotsOfSpaces));
    }

    @Test
    void validateUserInputRows() {
        String lowNumber = "0";
        String negativeNumber = "-1";
        String okNumber = "2";
        String notANumber = "abc";
        Assertions.assertEquals(false, lottoSimulator.validateUserInputRows(lowNumber));
        Assertions.assertEquals(false, lottoSimulator.validateUserInputRows(negativeNumber));
        Assertions.assertEquals(true, lottoSimulator.validateUserInputRows(okNumber));
        Assertions.assertEquals(false, lottoSimulator.validateUserInputRows(notANumber));
    }

}