import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IndexTest {
    private Index index;

    @BeforeEach
    // @BeforeEach is cool it like runs this before each method so I dont have to
    // create index each time. (:
    public void setUP() {
        index = new Index();
    }

    @Test
    void testAdd() throws Exception {
        index.init();

        String testFileName = "testFile.txt";
        index.add(testFileName);

        assertTrue(containsEntry(index.indexPath, testFileName));

        // File file = new File("test.txt");
        // file.createNewFile();
        // System.out.println("YOOO" + file.getAbsolutePath());

        // Blob blob = new Blob("test.txt");
        // File blobFile = new File("objects/" + blob.getHashString());

        // assertTrue(blobFile.exists());
    }

    @Test
    void testInit() throws Exception {
        index.init();
        // Index index = new Index ("index");
        // assertTrue(index.exists());x

        assertTrue(Files.exists(Paths.get(index.indexPath)));
        assertTrue(Files.exists(Paths.get(index.objectsFolderPath)));
    }

    @Test
    void testRemove() throws Exception {
        index.init();

        String testFileName = "testFile.txt";
        index.add(testFileName);
        index.remove(testFileName);

        assertFalse(containsEntry(index.indexPath, testFileName));
    }

    private boolean containsEntry(String indexPath, String fileName) throws IOException {
        String content = Files.readString(Path.of(indexPath));
        return content.contains(fileName);
    }
}
