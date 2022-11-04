import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

public class ImageCapture {

    private static String id;
    private static String fullPath = "";
    private static String shortFileName = "";
    public static String getShortFileName() {
        return shortFileName;
    }

    private static void generateId() {
        UUID idNew = UUID.randomUUID();
        id = idNew.toString();
    }
    static void imageCapture(int x, int y, long delta) throws AWTException, IOException, SQLException {
        BufferedImage capture = new Robot().createScreenCapture(
                new Rectangle(x - 40, y - 50, 80, 100));
        String path = (new File("")).getAbsolutePath();
        fullPath = path.concat("\\src\\main\\resources\\images\\");
        generateId();
        shortFileName = id.concat(".png");
        ImageIO.write(capture, "png", new File(fullPath.concat(shortFileName)));
        ActionRecorder.actionRecord(x, y, shortFileName, delta);
    }
}
