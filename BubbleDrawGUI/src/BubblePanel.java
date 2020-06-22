import java.awt.event.*;
import javax.swing.Timer;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JSlider;

public class BubblePanel extends JPanel {
    Random rand = new Random();
    ArrayList<Bubble> bubbleList;
    int size = 25;
    Timer timer;
    int delay = 33;  // miliseconds
    JSlider slSpeed;

    public BubblePanel() {
        timer = new Timer(delay, new BubbleListener());
        bubbleList = new ArrayList<Bubble>();
        setBackground(Color.BLACK);

        slSpeed = new JSlider();

        slSpeed.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                timer.setDelay(1000 / (slSpeed.getValue() + 1));
            }
        });

        slSpeed.setPaintTicks(true);
        slSpeed.setPaintLabels(true);
        slSpeed.setValue(5);
        slSpeed.setMajorTickSpacing(20);
        slSpeed.setMinorTickSpacing(5);
        slSpeed.setMaximum(120);
        add(slSpeed);

        JPanel panel = new JPanel();
        add(panel);

        JButton btnPause = new JButton("Pause");
        btnPause.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                JButton btn = (JButton)e.getSource();
                if (btn.getText().equals("Pause")) {
                    timer.stop();
                    btn.setText("Start");
                }
                else {
                    timer.start();
                    btn.setText("Pause");
                }
        	}
        });
        panel.add(btnPause);

        JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
                bubbleList = new ArrayList<>();
                repaint();
        	}
        });
        panel.add(btnClear);
        // testBubbles();
        addMouseListener(new BubbleListener());
        addMouseMotionListener(new BubbleListener());
        addMouseWheelListener(new BubbleListener());
        timer.start();
    }

    public void paintComponent(Graphics canvas) {
        super.paintComponent(canvas);
        for (Bubble b : bubbleList) {
            b.draw(canvas);
        }
    }

    public void testBubbles() {
        // Used for testing that the Bubble class works.
        for (int i = 0; i < 500; i++) {
            int x = rand.nextInt(600);
            int y = rand.nextInt(400);
            int size = rand.nextInt(50);
            bubbleList.add(new Bubble(x, y, size));
        }
        repaint();
    }

    // Mouse event listeners.
    // Implements ActionListener to make animation: "A timer triggers an actionPerformed() event"
    private class BubbleListener extends MouseAdapter implements ActionListener {
        public void mousePressed(MouseEvent e) {
            bubbleList.add(new Bubble(e.getX(), e.getY(), size));
            repaint();
        }
        public void mouseDragged(MouseEvent e) {
            bubbleList.add(new Bubble(e.getX(), e.getY(), size));
            repaint();
        }

        public void actionPerformed(ActionEvent e) {
            for (Bubble b : bubbleList) {
                b.update();
            }
            repaint();
        }

        // Changes the size of the bubble
        public void mouseWheelMoved(MouseWheelEvent e) {
            if (size >= 1000) size = 1000;
            size -= e.getUnitsToScroll();
            if (size <= 1) {
                size = 2;
            }
        }
    }

    private class Bubble {
        // Instance variable
        private int x, y;
        private int size;
        private Color color;
        private int xspeed, yspeed;
        private final int MAX_SPEED = 5;

        // Constructor
        public Bubble(int x, int y, int size) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.color = new Color( rand.nextInt(256), // R
                                    rand.nextInt(256), // G
                                    rand.nextInt(256), // B
                                    rand.nextInt(256)  // Opacity
            );
            do {
                xspeed = rand.nextInt(MAX_SPEED * 2 + 1) - MAX_SPEED;  // Max value will look like (5 * 2) - 5 = 5, since + 1 is to insure we get 10 as a possibility
                yspeed = rand.nextInt(MAX_SPEED * 2 + 1) - MAX_SPEED;  // Negative values moves the bubbles to the left
            } while (xspeed == 0 && yspeed == 0);
        }

        // Drawing the bubble to the canvas.
        public void draw(Graphics canvas) {
            canvas.setColor(this.color);
            canvas.fillOval(this.x - size / 2, this.y - size / 2, size, size);
        }

        public void update() {
            x += xspeed;
            y += yspeed;
            /// Checks to see if obj reached the edge of the screen
            // (0,0) er i midten, så vi fjerner halvdelen af cirklen for at kigge på toppen, og tilføjer for at kigge på bunden.
            if (x - size / 2 <= 0 || x + size / 2 >= getWidth())
                xspeed = -xspeed;
            if (y - size / 2 <= 0 || y + size / 2 >= getHeight())
                yspeed = -yspeed;
        }
    }
}