package jgameengine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class JGameEngine extends JFrame {
    private final JGameOptions options = JGameOptions.getInstance();
    private boolean fullscreen = options.isFullscreen();
    private final ArrayList<JDrawable> drawables = new ArrayList<>();

    public JGameEngine() {
        super(JGameOptions.getInstance().getTitle());
        JGameUtil.init(this);

        setSize(options.getWidth(), options.getHeight());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        if (options.isFullscreen()) {
            setUndecorated(true);
        }
        addKeyListener(options.getKeyListener());

        setupMenu();

        JGameCanvas canvas = new JGameCanvas(drawables);
        setContentPane(canvas);
    }

    private void setFullscreen() {
        if (options.isFullscreen()) {
            dispose();
            setExtendedState(MAXIMIZED_BOTH);
            setUndecorated(true);
        } else {
            dispose();
            setUndecorated(false);
        }
        setVisible(true);
    }

    private void setupMenu() {
        JMenuBar jMenuBar = new JMenuBar();
        // Options
        JMenu opts = new JMenu("Options");
        opts.add("Fullscreen").addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                options.setFullscreen(!options.isFullscreen());
                setFullscreen();
            }
        });

        opts.add("Debug").addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                options.setDebug(!options.isDebug());
                getContentPane().repaint();
            }
        });

        jMenuBar.add(opts);

        // Close
        JMenuItem close = new JMenu("Close");
        close.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                boolean confirmed = JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to close the program?") == JOptionPane.YES_OPTION;
                if (confirmed) {
                    dispose();
                }
            }
        });
        jMenuBar.add(close);
        setJMenuBar(jMenuBar);
    }

    public void start() {
        setVisible(true);

        options.setWidth(getContentPane().getWidth());
        options.setHeight(getContentPane().getHeight());

        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            private long last;
            private int count;

            @Override
            public void run() {
                if (!options.isPaused()) {
                    options.setTicks(count);

                    if (++count % 10 == 0) {
                        options.setFps(Math.abs(10000f / (last - (last = System.currentTimeMillis()))));
                    }

                    boolean draw = false;

                    ArrayList<JDrawable> copy = new ArrayList<>(drawables);
                    synchronized (drawables) {
                        for (JDrawable drawable : copy) {
                            if (drawable instanceof JTextDrawable) {
                                draw = true;
                            } else {
                                if (drawable.tick()) {
                                    draw = true;
                                }
                            }
                        }
                    }


                    if (draw) getContentPane().repaint();
                }

                if (options.isFullscreen() && !fullscreen) {
                    fullscreen = true;
                    setFullscreen();
                } else if (!options.isFullscreen() && fullscreen) {
                    fullscreen = false;
                    setFullscreen();
                }
            }
        }, 0, 1000 / options.getTicksASecond());
    }

    public ArrayList<JDrawable> getDrawables() {
        return drawables;
    }

    public synchronized void addDrawable(JDrawable drawable) {
        drawables.add(drawable);
    }

    public synchronized void removeDrawable(JDrawable drawable) {
        drawables.remove(drawable);
    }
}
