package syntax_checker;

import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;

public class SymbolTest {
    String genRandomStr() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();

        return saltStr;
    }

    @Test
    public void toStringTest() {
        for (int i = 0; i < 10; i++) {
            String name = genRandomStr();
            assertEquals(Symbol.symbol(name).toString(), name);
        }
    }

    @Test
    public void testEq() {
        for (int i = 0; i < 10; i++) {
            String name = genRandomStr();
            Symbol s = Symbol.symbol(name);
            Symbol t = Symbol.symbol(name);
            assertTrue(s.eq(s));
            assertTrue(t.eq(t));
            assertTrue(s.eq(t));
            assertTrue(t.eq(s));
        }
    }
}
