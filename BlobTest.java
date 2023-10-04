import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BlobTest {
    private String filePath;
    private Blob blob;

    @BeforeEach
    public void setUp() {
        filePath = "testFile.txt";
    }

    @Test
    void testCopyData() throws Exception {
        File test = new File(filePath);
        blob = new Blob(filePath);

        String expected = Files.readString(Path.of(filePath));
        String result = blob.getFileContent();

        assertEquals(result, expected);
    }

    @Test
    void testHash() throws Exception {
        File test = new File(filePath);
        blob = new Blob(filePath);

        String expected = sha1(filePath);
        String result = blob.getHashString();

        assertEquals(result, expected);
    }

    static String sha1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}
