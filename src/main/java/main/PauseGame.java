import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PauseGame extends JPanel implements Runnable {
    private Thread gameThread;
    private boolean running = false;
    private boolean paused = false;

    private int x = 50, y = 50;

    private JButton resumeButton;
    private JButton quitButton;

    public PauseGame(JFrame frame) {
        setPreferredSize(new Dimension(600, 400));
        setBackground(Color.BLACK);
        setLayout(null);

        resumeButton = new JButton("Resume");
        resumeButton.setBounds(230, 170, 140, 40);
        resumeButton.setFont(new Font("Arial", Font.BOLD, 16));
        resumeButton.setVisible(false);

        quitButton = new JButton("Quit");
        quitButton.setBounds(230, 230, 140, 40);
        quitButton.setFont(new Font("Arial", Font.BOLD, 16));
        quitButton.setVisible(false);

        add(resumeButton);
        add(quitButton);

        resumeButton.addActionListener(e -> {
            paused = false;
            toggleMenu(false);
            requestFocusInWindow();
        });

        quitButton.addActionListener(e -> {
            running = false;
            frame.dispose();
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    paused = !paused;
                    toggleMenu(paused);
                }
            }
        });
        setFocusable(true);
    }

    private void toggleMenu(boolean show) {
        resumeButton.setVisible(show);
        quitButton.setVisible(show);
    }

    public void startGame() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (running) {
            if (!paused) {
                updateGame();
            }
            repaint();

            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateGame() {
        x += 2;
        if (x > getWidth()) x = 0;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (paused) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(new Color(0, 0, 0, 150));
            g2d.fillRect(0, 0, getWidth(), getHeight());

            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 40));
            FontMetrics fm = g2d.getFontMetrics();
            String text = "PAUSED";
            int textWidth = fm.stringWidth(text);
            g2d.drawString(text, (getWidth() - textWidth) / 2, 120);

            g2d.dispose();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pause Game - Simple");
        PauseGame game = new PauseGame(frame);
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        game.startGame();
    }
}
