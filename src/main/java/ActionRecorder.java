import java.awt.*;
import java.io.IOException;
import java.sql.*;

public class ActionRecorder {
    private static int idForAction_id;
    private static boolean isDoubleclick = false;
    public static void actionRecord(int x, int y, String shortFileName, long delta) throws IOException, AWTException, SQLException {
        if ((delta / 1_000_000) < 500) {
            isDoubleclick = true;
        }
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/actions_recorded", "root", "root")) {
            Statement statement = connection.createStatement();

            try (ResultSet resultSet = statement.executeQuery("select max(action_id) from actions")) {
                if (resultSet.next()) {
                    idForAction_id = resultSet.getInt(1);
                    System.out.println(idForAction_id);
                }
            }

            idForAction_id++;

            String sql = "insert into actions(action_id, x_coord, y_coord, isDoubleclick, filename) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement addition = connection.prepareStatement(sql);

            addition.setInt(1, idForAction_id);
            addition.setInt(2, x);
            addition.setInt(3, y);
            addition.setBoolean(4, isDoubleclick);
            addition.setString(5, shortFileName);

            addition.executeUpdate();
        }
    }
}
