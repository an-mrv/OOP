import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Class for finding a given string in a file.
 */
public class SubstringFinder {
    Integer bytesRead;
    Integer currFoundIndOfSubstr;

    /**
     * Constructor.
     */
    public SubstringFinder() {
        this.bytesRead = 0;
        this.currFoundIndOfSubstr = 0;
    }

    /**
     * Function that defines the index of the beginning of each occurrence of a given substring.
     * @param fileName the name of file
     * @param substring the given substring
     * @return the array of indexes of each occurrence of the string.
     */
    public ArrayList<Integer> find(String fileName, String substring) {
        this.bytesRead = 0;
        this.currFoundIndOfSubstr = 0;
        ArrayList<Integer> result = new ArrayList<>();
        if (substring.isEmpty()) {
            return result;
        }
        substring = new String(substring.getBytes(), StandardCharsets.UTF_8);
        char[] myBuffer = new char[128];
        int size;
        StringBuilder line = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8));
            while ((size = in.read(myBuffer, 0, 128)) != -1) {
                for (char c : myBuffer) {
                    line.append(c);
                }
                for (int i = 0; i < size; i++) {
                    if ((line.charAt(i) != substring.charAt(this.currFoundIndOfSubstr))
                            && (this.currFoundIndOfSubstr != 0)) {
                        this.currFoundIndOfSubstr = 0;
                        result.remove(result.size() - 1);
                    }
                    if (line.charAt(i) == substring.charAt(this.currFoundIndOfSubstr)) {
                        if (this.currFoundIndOfSubstr == (substring.length() - 1)) {
                            if (this.currFoundIndOfSubstr == 0) {
                                result.add(bytesRead);
                            } else {
                                this.currFoundIndOfSubstr = 0;
                            }
                        } else {
                            if (this.currFoundIndOfSubstr == 0) {
                                result.add(bytesRead);
                                this.currFoundIndOfSubstr++;
                            } else {
                                this.currFoundIndOfSubstr++;
                            }
                        }
                    } else {
                        if (this.currFoundIndOfSubstr != 0) {
                            result.remove(result.size() - 1);
                            this.currFoundIndOfSubstr = 0;
                        }
                    }
                    this.bytesRead++;
                }
                line.delete(0, line.toString().length());
            }
            if (this.currFoundIndOfSubstr != 0) {
                result.remove(result.size() - 1);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
