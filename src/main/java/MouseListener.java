import lc.kra.system.mouse.GlobalMouseHook;
import lc.kra.system.mouse.event.GlobalMouseAdapter;
import lc.kra.system.mouse.event.GlobalMouseEvent;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class MouseListener {
    private static boolean run = true;
    private static int x;
    private static int y;
    private static long oldTime;
    private static long clickTimeDelta;
    private static boolean fdhgdfh = false;
    public static void mouseListener() {

        GlobalMouseHook mouseHook = new GlobalMouseHook();
        mouseHook.addMouseListener(new GlobalMouseAdapter() {
            @Override
            public void mousePressed(GlobalMouseEvent event) {
                clickTimeDelta = (System.nanoTime() - oldTime);
                System.out.println(clickTimeDelta / 1_000_000);
                oldTime = System.nanoTime();
                x = event.getX();
                y = event.getY();
                System.out.println("mouse pressed");
                System.out.println(x + ", " + y + " " + event.getButton());
                try {
                    ImageCapture.imageCapture(x, y,  clickTimeDelta);
                } catch (AWTException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                if (event.getButton() == GlobalMouseEvent.BUTTON_MIDDLE) {
                    run = false;
                }
            }
        });

        try {
            while (run) {
                Thread.sleep(128);
            }
        } catch (InterruptedException e) {
        } finally {
            mouseHook.shutdownHook();
        }
    }

    public static int getX() {
        return x;
    }

    public static int getY() {
        return y;
    }

    public static double getTime() {
        return clickTimeDelta;
    }
}