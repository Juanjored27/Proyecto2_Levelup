import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.HashUtil;

public class HashUtilTest {

    @Test
    void generaHashConLongitudCorrecta() {
        String hash = HashUtil.sha256("admin");
        Assertions.assertNotNull(hash);
        Assertions.assertEquals(64, hash.length());
    }

    @Test
    void mismoTextoMismoHash() {
        Assertions.assertEquals(HashUtil.sha256("usuario"), HashUtil.sha256("usuario"));
    }
}
