import org.junit.Test;

import static org.junit.Assert.*;

public class test {
    @Test
    public void isDerivable() {
        assertFalse(main.check("rpa"));

        assertTrue(main.check("c"));

        assertFalse(main.check("caccbc"));

        assertTrue(main.check("ac"));

        assertFalse(main.check("bdaa"));

        assertTrue(main.check("bc"));
    }

}
