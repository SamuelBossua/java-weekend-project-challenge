package safeBankTests;

import org.junit.jupiter.api.BeforeEach;
import safeBank.ui.SafeBankApp;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class SafeBankAppTest {

    public void displayMenuAfterAccountIsCreated() {
        String simulatedInput = "Sam\n20000\n5\n";
        ByteArrayInputStream fakeKeyboard = new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8));
        InputStream realKeyboard =  System.in;
        try{
            System.setIn(fakeKeyboard);
            SafeBankApp.main(new String[0]);
        }
        finally{
            System.setIn(realKeyboard);
        }

    }
}
