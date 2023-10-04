import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Tree {
    private List<String> treeFile;
    String fileName;
    String mix = "";

    public Tree() throws NoSuchAlgorithmException {
        for (String input : treeFile) {
            mix += input + "\n";
        }
        fileName = sha1(mix);
        File file = new File("objects/" + fileName);
    }

    public void add(String input) throws NoSuchAlgorithmException, IOException {
        treeFile.add(input);
        mix += input;
        fileName = sha1(mix);
        write(fileName);
    }

    public void remove(String name) throws NoSuchAlgorithmException, IOException {
        treeFile.removeIf(input -> {
            String[] array = input.split(" : ");
            if (array.length >= 3) {

                String type = array[0];
                String sha1 = array[1];
                String optional = array[2];
                return sha1.equals(name) || optional.equals(name);
            }
            return false;
        });
        for (String input : treeFile) {
            mix += input + "\n";
        }
        fileName = sha1(mix);
        write(fileName);
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

    public void write(String fileName) throws IOException, NoSuchAlgorithmException {
        String mix = "";
        for (String input : treeFile) {
            mix += input + "\n";
        }
        File file = new File("objects/" + fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            writer.write(mix);
        }
    }

}
