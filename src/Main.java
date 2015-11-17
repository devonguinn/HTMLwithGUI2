import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by devon on 11/16/15.
 */
public class Main {

    private void updateTextArea(final String text) {

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                textArea.append(text);
            }
        });
    }

    private void redirectSystemStreams() {
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                updateTextArea(String.valueOf((char) b));
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                updateTextArea(new String(b, off, len));
            }

            @Override
            public void write(byte[] b) throws IOException {
                write(b, 0, b.length);
            }
        };

        System.setOut(new PrintStream(out, true));
        System.setErr(new PrintStream(out, true));
    }
    public void frame() throws  Exception{
        JFrame f = new JFrame("HTML PROJECT");
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        JTextArea textArea = new JTextArea();
        f.add(textArea);
        f.setSize(800,600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

        redirectSystemStreams();
        htmlRender html = new htmlRender();
        html.html();


    }
    public static void main(String[] args) throws Exception{
        Main main = new Main();

        main.frame();
    }
}
