package project_akhir.project2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.util.function.Supplier;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class GameGUI extends JFrame {
    private static final long serialVersionUID = 1L;

    private static final int PLAYER_MAX_HP = 150;

    private final JTextArea logArea = new JTextArea();
    private final JLabel playerHpLabel = new JLabel("-");
    private final JLabel musuhHpLabel = new JLabel("-");
    private final JLabel playerNameLabel = new JLabel("-");
    private final JLabel enemyNameLabel = new JLabel("-");
    private final JProgressBar playerHpBar = new JProgressBar(0, PLAYER_MAX_HP);
    private final JProgressBar musuhHpBar = new JProgressBar();
    private JButton attackButton;
    private JButton healButton;
    private JButton ultimateButton;
    private JButton startButton;
    private final EnemyOption[] enemyOptions = new EnemyOption[] {
        new EnemyOption("Kroco (Goblin)", () -> new Musuh("Kroco (Goblin)")),
        new EnemyOption("Boss Naga Kuwad", () -> new Boss("Boss Naga Kuwad")),
        new EnemyOption("Super Boss Raja Iblis", () -> new SuperBoss("Super Boss Raja Iblis"))
    };
    private final JComboBox<String> musuhCombo = new JComboBox<>(getEnemyNames());

    private Player player;
    private Musuh musuh;
    private String playerName;
    private int musuhMaxHp;

    public GameGUI() {
        super("RPG Fantasy GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 480);
        setLocationRelativeTo(null);
        setResizable(false);

        buildLayout();
        bindActions();
        promptPlayerName();
    }

    private void buildLayout() {
        setLayout(new BorderLayout(12, 12));
        getContentPane().setBackground(new Color(18, 22, 28));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(12, 12, 12, 12));

        JLabel title = new JLabel("RPG Fantasy Arena");
        title.setFont(new Font("Serif", Font.BOLD, 24));
        title.setForeground(new Color(247, 208, 96));
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(new Color(18, 22, 28));
        titlePanel.add(title);

        JPanel statsPanel = buildStatsPanel();
        JPanel selectPanel = buildSelectPanel();

        JPanel leftPanel = new JPanel(new BorderLayout(8, 8));
        leftPanel.setBackground(new Color(18, 22, 28));
        leftPanel.add(titlePanel, BorderLayout.NORTH);
        leftPanel.add(statsPanel, BorderLayout.CENTER);
        leftPanel.add(selectPanel, BorderLayout.SOUTH);

        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        logArea.setBackground(new Color(26, 31, 38));
        logArea.setForeground(Color.WHITE);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setBorder(new EmptyBorder(8, 8, 8, 8));

        JPanel actionPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        actionPanel.setBackground(new Color(18, 22, 28));
        attackButton = new RoundButton("SERANG", new Color(78, 190, 159));
        healButton = new RoundButton("PENYEMBUHAN", new Color(94, 140, 255));
        ultimateButton = new RoundButton("ULTIMATE", new Color(232, 99, 143));
        attackButton.addActionListener(e -> executePlayerTurn(TurnAction.ATTACK));
        healButton.addActionListener(e -> executePlayerTurn(TurnAction.HEAL));
        ultimateButton.addActionListener(e -> executePlayerTurn(TurnAction.ULTIMATE));
        actionPanel.add(attackButton);
        actionPanel.add(healButton);
        actionPanel.add(ultimateButton);

        add(leftPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.EAST);

        setButtonsEnabled(false);
    }

    private void bindActions() {
        // Action listeners sudah di-set di buildActionPanel dan buildSelectPanel
    }

    private void promptPlayerName() {
        playerName = JOptionPane.showInputDialog(this, "Masukkan nama hero:", "Nama Hero", JOptionPane.QUESTION_MESSAGE);
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "Hero";
        }
        player = new Player(playerName);
        playerNameLabel.setText(playerName);
        appendLog("Selamat datang, " + playerName + "!");
        updateStatus();
    }

    private void startBattle() {
        musuh = enemyOptions[musuhCombo.getSelectedIndex()].factory.get();
        musuhMaxHp = musuh.getDarah();
        musuhHpBar.setMaximum(musuhMaxHp);
        musuhHpBar.setValue(musuhMaxHp);
        player = new Player(playerName);
        logArea.setText("");
        appendLog("Melawan " + musuh.getNama() + "!");
        enemyNameLabel.setText(musuh.getNama());
        setButtonsEnabled(true);
        updateStatus();
    }

    private void executePlayerTurn(TurnAction action) {
        if (musuh == null) {
            JOptionPane.showMessageDialog(this, "Pilih musuh dan mulai pertarungan terlebih dahulu.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try {
            switch (action) {
                case ATTACK:
                    player.serangMusuh(musuh);
                    appendLog(player.getNama() + " menyerang " + musuh.getNama());
                    break;
                case HEAL:
                    player.penyembuhan();
                    appendLog(player.getNama() + " memulihkan HP");
                    break;
                case ULTIMATE:
                    player.seranganBurst(musuh);
                    appendLog(player.getNama() + " menggunakan ULTIMATE!");
                    break;
                default:
                    break;
            }

            if (!musuh.isMati()) {
                musuh.serangan(player);
                appendLog(musuh.getNama() + " menyerang balik!");
            }

            updateStatus();
            checkOutcome();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void checkOutcome() {
        if (musuh == null || player == null) {
            return;
        }
        if (musuh.isMati()) {
            appendLog("Kamu menang melawan " + musuh.getNama() + "!");
            setButtonsEnabled(false);
        } else if (player.getDarah() <= 0) {
            appendLog("HP habis. Game over...");
            setButtonsEnabled(false);
        }
    }

    private void updateStatus() {
        if (player == null) {
            playerHpLabel.setText("-");
            playerHpBar.setValue(0);
            playerHpBar.setString("0 / " + PLAYER_MAX_HP);
        } else {
            playerHpLabel.setText(String.valueOf(player.getDarah()));
            playerHpBar.setValue(player.getDarah());
            playerHpBar.setString(player.getDarah() + " / " + PLAYER_MAX_HP);
            playerNameLabel.setText(player.getNama());
        }

        if (musuh == null) {
            musuhHpLabel.setText("-");
            musuhHpBar.setValue(0);
            musuhHpBar.setString("0 / -");
            enemyNameLabel.setText("-");
        } else {
            musuhHpLabel.setText(String.valueOf(musuh.getDarah()));
            musuhHpBar.setValue(musuh.getDarah());
            musuhHpBar.setString(musuh.getDarah() + " / " + musuhMaxHp);
            enemyNameLabel.setText(musuh.getNama());
        }
    }

    private void setButtonsEnabled(boolean enabled) {
        attackButton.setEnabled(enabled);
        healButton.setEnabled(enabled);
        ultimateButton.setEnabled(enabled);
    }

    private void appendLog(String text) {
        logArea.append(text + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    private String[] getEnemyNames() {
        String[] names = new String[enemyOptions.length];
        for (int i = 0; i < enemyOptions.length; i++) {
            names[i] = enemyOptions[i].label;
        }
        return names;
    }

    private JPanel buildStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBackground(new Color(18, 22, 28));

        JPanel heroCard = createCard("Hero", playerNameLabel, playerHpLabel, playerHpBar, new Color(39, 52, 68));
        JPanel enemyCard = createCard("Musuh", enemyNameLabel, musuhHpLabel, musuhHpBar, new Color(52, 36, 64));

        panel.add(heroCard);
        panel.add(enemyCard);
        return panel;
    }

    private JPanel buildSelectPanel() {
        JPanel selectPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        selectPanel.setBackground(new Color(18, 22, 28));
        JLabel pilihLabel = new JLabel("Pilih Musuh:");
        pilihLabel.setForeground(Color.WHITE);
        selectPanel.add(pilihLabel);
        selectPanel.add(musuhCombo);
        startButton = new RoundButton("MULAI", new Color(247, 208, 96));
        startButton.addActionListener(e -> startBattle());
        selectPanel.add(startButton);
        return selectPanel;
    }

    private JPanel createCard(String title, JLabel nameLabel, JLabel hpLabel, JProgressBar hpBar, Color bg) {
        JPanel card = new JPanel(new BorderLayout(6, 6));
        card.setBackground(bg);
        card.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel heading = new JLabel(title);
        heading.setFont(new Font("Serif", Font.BOLD, 16));
        heading.setForeground(new Color(247, 208, 96));

        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        hpLabel.setForeground(Color.LIGHT_GRAY);

        hpBar.setStringPainted(true);
        hpBar.setForeground(new Color(94, 140, 255));
        hpBar.setBackground(new Color(33, 40, 52));
        hpBar.setValue(hpBar.getMaximum());

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(heading, BorderLayout.WEST);
        top.add(nameLabel, BorderLayout.EAST);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);
        bottom.add(hpBar, BorderLayout.CENTER);
        bottom.add(hpLabel, BorderLayout.EAST);

        card.add(top, BorderLayout.NORTH);
        card.add(bottom, BorderLayout.CENTER);
        return card;
    }

    private enum TurnAction {
        ATTACK,
        HEAL,
        ULTIMATE
    }

    // Custom Rounded Button
    private static class RoundButton extends JButton {
        private Color baseColor;
        private boolean hovered = false;
        
        public RoundButton(String text, Color color) {
            super(text);
            this.baseColor = color;
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setForeground(Color.WHITE);
            setFont(new Font("SansSerif", Font.BOLD, 13));
            setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            
            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    hovered = true;
                    repaint();
                }
                
                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    hovered = false;
                    repaint();
                }
            });
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            Color color = hovered ? baseColor.brighter() : baseColor;
            g2d.setColor(color);
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
            
            // Draw text
            FontMetrics fm = g2d.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(getText())) / 2;
            int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
            
            g2d.setColor(Color.WHITE);
            g2d.setFont(getFont());
            g2d.drawString(getText(), x, y);
        }
    }

    private static class EnemyOption {
        final String label;
        final Supplier<Musuh> factory;

        EnemyOption(String label, Supplier<Musuh> factory) {
            this.label = label;
            this.factory = factory;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameGUI gui = new GameGUI();
            gui.setVisible(true);
        });
    }
}
