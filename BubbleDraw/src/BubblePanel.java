import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Color;

public class BubblePanel extends JPanel {
    Random rand = new Random();
    ArrayList<Bubble> bubbleList;
    int size = 25;

    public BubblePanel() {
        bubbleList = new ArrayList<Bubble>();
        setBackground(Color.BLACK);
        // testBubbles();
        addMouseListener(new BubbleListener());
        addMouseMotionListener(new BubbleListener());
        addMouseWheelListener(new BubbleListener());
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
    private class BubbleListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            bubbleList.add(new Bubble(e.getX(), e.getY(), size));
            repaint();
        }
        public void mouseDragged(MouseEvent e) {
            bubbleList.add(new Bubble(e.getX(), e.getY(), size));
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
        private int x;
        private int y;
        private int size;
        private Color color;

        // Constructor
        public Bubble(int x, int y, int size) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
        }

        // Drawing the bubble to the canvas.
        public void draw(Graphics canvas) {
            canvas.setColor(this.color);
            canvas.fillOval(this.x - size / 2, this.y - size / 2, size, size);
        }
    }
}