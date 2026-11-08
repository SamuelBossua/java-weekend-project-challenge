package safeBankTests;


import org.junit.jupiter.api.Test;
import safeBank.ui.SafeBankApp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SafeBankAppTest {

    private String runAppAndCaptureOutput(String simulatedInput) {
        InputStream realKeyboard = System.in;
        PrintStream realScreen = System.out;
        ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();
        PrintStream capturedScreen =
                new PrintStream(capturedOutput, true, StandardCharsets.UTF_8);

        try {
            System.setIn(new ByteArrayInputStream(
                    simulatedInput.getBytes(StandardCharsets.UTF_8)
            ));
            System.setOut(capturedScreen);
            SafeBankApp.main(new String[0]);
        } finally {
            System.setIn(realKeyboard);
            System.setOut(realScreen);
            capturedScreen.close();
        }

        return capturedOutput.toString(StandardCharsets.UTF_8);
    }

    @Test
    public void testThatDisplayMenuAfterAccountIsCreated() {
        String simulatedInput = "Sam\n20000\nabc\n5\n";

        String printedText = runAppAndCaptureOutput(simulatedInput);
        assertTrue(printedText.contains("Deposit"));
        assertTrue(printedText.contains("Goodbye"));
        assertTrue(printedText.contains("Invalid menu selection"));

    }

    @Test
    public void testThatMenuOptionShowsCurrentBalance(){
        String simulatedInput = "Sam\n20000\n3\n5\n";
        String printedText = runAppAndCaptureOutput(simulatedInput);
        assertTrue(printedText.contains("Current balance: ₦20000"));
    }

    @Test
    public void testThatDepositCanBeMadeThroughMenu() {
        String simulatedInput = "Sam\n20000\n1\n1000\n5\n";

        String printedText = runAppAndCaptureOutput(simulatedInput);

        assertTrue(
                printedText.contains(
                        "Deposit successful. New balance: ₦21000"
                )
        );
    }

    @Test
    public void testThatTransactionHistoryIsVisibleForDepositThroughMenu() {
        String simulatedInput = "Sam\n20000\n1\n1000\n4\n5\n";

        String printedText = runAppAndCaptureOutput(simulatedInput);

        assertTrue(
                printedText.contains("DEPOSIT"),
                "Actual output was:\n" + printedText
        );
    }

    @Test
    public void testThatTransactionHistoryIsVisibleForWithdrawalThroughMenu() {
        String simulatedInput = "Sam\n20000\n2\n5000\n5\n";
        String printedText = runAppAndCaptureOutput(simulatedInput);

        assertTrue(printedText.contains("Withdrawal successful. New balance: ₦15000"));
    }

    @Test
    public void testInsufficientFundsThenSuccessfulWithdrawal() {
        String simulatedInput = "Sam\n20000\n2\n25000\n2\n5000\n5\n";

        String printedText = runAppAndCaptureOutput(simulatedInput);

        assertTrue(
                printedText.contains("Insufficient Funds")
        );
        assertTrue(
                printedText.contains("Withdrawal successful. New balance: ₦15000"),
                "Expected the later withdrawal to succeed. Actual output:\n" + printedText
        );
    }

    @Test
    public void testNonNumericDepositCanBeCorrected() {
        String simulatedInput = "Sam\n20000\n1\nabc\n1\n1000\n5\n";


        String printedText = runAppAndCaptureOutput(simulatedInput);

        assertTrue(
                printedText.contains("valid numeric amount"),
                "Expected a helpful message for non-numeric input. Actual output:\n"
                        + printedText
        );
        assertTrue(
                printedText.contains("Deposit successful. New balance: ₦21000"),
                "Expected the later deposit to succeed. Actual output:\n"
                        + printedText
        );
    }

    @Test
    public void testThatWithdrawalLimitIsActually100000() {
        String simulatedInput = "Sam\n200000\n2\n120000\n5\n";

        String printedText = runAppAndCaptureOutput(simulatedInput);

        assertTrue(
                printedText.contains("Withdrawal limit of 100000 exceeded"));
    }

}
