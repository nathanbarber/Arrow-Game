import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.*;

public class Arrow extends JPanel implements KeyListener {
    static int windowx = 600;
    static int windowy = 600;

    int encroaching = 0;
    int plusser = 8;
    int hits = 0;
    int resets = 0;
    int timeelapsed = 0;
    int timeover = 100;

    int expected = (int)(Math.random() * 4) + 37;
    int selected;
    boolean justchanged = true;
    boolean done = true;
    boolean prepped = true;
    javax.swing.Timer t;

    int points = 0;

    public Arrow() {
        ActionListener action = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Arrow.this.repaint();
            }
        };
        this.t = new javax.swing.Timer(1, action);
        this.t.setInitialDelay(0);
    }

    public void gameOver(Graphics g) {
        g.setColor(new Color(50, 180, 200));
        g.fillRect(0,0,windowx,windowy);
        g.setColor(new Color(20,200,200));
        g.setFont(new Font("Courir", Font.BOLD, 80));
        g.drawString("RESTART ∆", 200, 210);
        selected = 0;
        this.t.stop();
        hits = 0;
        plusser = 8;
        encroaching = 0;
        resets = 0;
        timeelapsed = 0;
        done = true;
        prepped = false;
    }

    public void paintComponent(Graphics g2) {
        super.paintComponent(g2);
        Graphics2D g = (Graphics2D)(g2);
        g.setRenderingHint(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(Color.WHITE);
        g.fillRect(0,0,windowx, windowy);
        g.setColor(new Color(50, 180, 200));
        g.fillRect(0,0,encroaching,windowy);
        g.setColor(Color.BLUE);
        switch(expected) {
            case 37:
                //draw left triangle
                int[] xlpoints = {windowx-140, windowx-140, 140};
                int[] ylpoints = {140, windowy-140, windowy/2};
                g.fillPolygon(xlpoints, ylpoints, 3);
                break;
            case 38:
                //draw up triangle
                int[] xupoints = {140, windowx/2, windowx-140};
                int[] yupoints = {windowy-140, 140, windowy-140};
                g.fillPolygon(xupoints, yupoints, 3);
                break;
            case 39:
                //draw right triangle
                int[] xrpoints = {140, 140, windowx-140};
                int[] yrpoints = {140, windowy-140, windowy/2};
                g.fillPolygon(xrpoints, yrpoints, 3);
                break;
            case 40:
                //draw down triangle
                int[] xwpoints = {140, windowx/2, windowx-140};
                int[] ywpoints = {140, windowy-140, 140};
                g.fillPolygon(xwpoints, ywpoints, 3);
                break;
        }
        if(selected == 0) {

        } else if(selected == expected) {
            expected = (int)(Math.random() * 4) + 37;
            encroaching = 0;
            selected = 0;
            hits++;
            if(hits / 5 > 0) {
                resets++;
                if(plusser < 50) {
                    plusser += resets;
                } else {
                    plusser ++;
                }
                hits = 0;
            }
            points += ((windowx - encroaching) / 50) * plusser;
            System.out.println(points);
        } else {
            gameOver(g);
        }

        if(encroaching >= windowx) {
            gameOver(g);
        } else {
            timeelapsed += plusser;
            if(timeelapsed > timeover) {
                encroaching += 2;
                timeelapsed = 0;
            }
        }
        if(!prepped) {
            g.setColor(new Color(50, 180, 200));
            g.fillRect(0,0,windowx,windowy);
            g.setColor(new Color(20,200,200));
            g.setFont(new Font("Courir", Font.BOLD, 80));
            g.drawString("RESTART ∆", 50, windowy / 2);
        }
        g.setFont(new Font("Courir", Font.PLAIN, 40));
        g.setColor(new Color(20,200,200));
        g.drawString("" + points, 50, 50);
    }

    public void keyPressed(KeyEvent e) {
        justchanged = true;
        if(done) {
            this.t.start();
            done = false;
        } else if(!prepped) {
            prepped = true;
            points = 0;
            encroaching = 0;
            timeelapsed = 0;
        } else {
            selected = e.getKeyCode();
        }
    };
    public void keyReleased(KeyEvent e) {};
    public void keyTyped(KeyEvent e) {};

    public static void main(String[] args) {
        Arrow panel = new Arrow();
        panel.setOpaque(true);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.addKeyListener(panel);
        frame.setSize(windowx, windowy);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}