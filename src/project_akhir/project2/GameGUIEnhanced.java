package project_akhir.project2;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class GameGUIEnhanced extends JFrame {
    private static final long serialVersionUID = 1L;
    
    // Components
    private JTextArea logArea;
    private JLabel playerNameLabel, playerHpLabel, playerLevelLabel, playerExpLabel;
    private JLabel enemyNameLabel, enemyHpLabel;
    private JProgressBar playerHpBar, enemyHpBar, expBar;
    private JLabel comboLabel, goldLabel, achievementLabel;
    private JButton attackButton, healButton, ultimateButton, startButton;
    private JComboBox<String> enemyCombo;
    private JPanel achievementPanel;
    
    // Game state
    private PlayerEnhanced player;
    private Musuh musuh;
    private String playerName;
    private int musuhMaxHp;
    private Achievement.AchievementManager achievementManager;
    private Random random = new Random();
    private int maxComboThisBattle = 0;
    
    public GameGUIEnhanced() {
        super("⚔️ RPG Fantasy Arena - Enhanced Edition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        
        achievementManager = new Achievement.AchievementManager();
        buildLayout();
        promptPlayerName();
    }
    
    private void buildLayout() {
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(new Color(18, 22, 28));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));
        
        // Top panel - Title & Stats
        add(buildTopPanel(), BorderLayout.NORTH);
        
        // Left panel - Character Info
        add(buildLeftPanel(), BorderLayout.WEST);
        
        // Center - Battle Log
        add(buildCenterPanel(), BorderLayout.CENTER);
        
        // Right - Actions & Achievements
        add(buildRightPanel(), BorderLayout.EAST);
        
        // Bottom - Enemy Selection
        add(buildBottomPanel(), BorderLayout.SOUTH);
    }
    
    private JPanel buildTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(18, 22, 28));
        
        JLabel title = new JLabel("⚔️ RPG FANTASY ARENA ⚔️");
        title.setFont(new Font("Serif", Font.BOLD, 32));
        title.setForeground(new Color(247, 208, 96));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        
        panel.add(title, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel buildLeftPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(18, 22, 28));
        panel.setPreferredSize(new Dimension(280, 0));
        
        // Player Card
        panel.add(createPlayerCard());
        panel.add(Box.createVerticalStrut(15));
        
        // Enemy Card
        panel.add(createEnemyCard());
        panel.add(Box.createVerticalStrut(15));
        
        // Stats Card
        panel.add(createStatsCard());
        
        return panel;
    }
    
    private JPanel createPlayerCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(39, 52, 68));
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(78, 190, 159), 2, true),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel header = new JLabel("🛡️ HERO");
        header.setFont(new Font("Serif", Font.BOLD, 18));
        header.setForeground(new Color(78, 190, 159));
        
        playerNameLabel = new JLabel("-");
        playerNameLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        playerNameLabel.setForeground(Color.WHITE);
        
        playerLevelLabel = new JLabel("Level: 1");
        playerLevelLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        playerLevelLabel.setForeground(new Color(247, 208, 96));
        
        playerHpBar = new JProgressBar(0, 150);
        playerHpBar.setStringPainted(true);
        playerHpBar.setForeground(new Color(94, 255, 140));
        playerHpBar.setBackground(new Color(33, 40, 52));
        playerHpBar.setPreferredSize(new Dimension(250, 25));
        
        playerHpLabel = new JLabel("100 / 150");
        playerHpLabel.setForeground(Color.LIGHT_GRAY);
        playerHpLabel.setFont(new Font("Monospaced", Font.BOLD, 12));
        
        expBar = new JProgressBar(0, 100);
        expBar.setStringPainted(true);
        expBar.setString("EXP: 0 / 100");
        expBar.setForeground(new Color(247, 208, 96));
        expBar.setBackground(new Color(33, 40, 52));
        expBar.setPreferredSize(new Dimension(250, 20));
        
        playerExpLabel = new JLabel("EXP: 0 / 100");
        playerExpLabel.setForeground(Color.LIGHT_GRAY);
        playerExpLabel.setFont(new Font("Monospaced", Font.PLAIN, 11));
        
        card.add(header);
        card.add(Box.createVerticalStrut(10));
        card.add(playerNameLabel);
        card.add(playerLevelLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(playerHpBar);
        card.add(Box.createVerticalStrut(5));
        card.add(expBar);
        
        return card;
    }
    
    private JPanel createEnemyCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(52, 36, 64));
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(232, 99, 143), 2, true),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel header = new JLabel("👹 MUSUH");
        header.setFont(new Font("Serif", Font.BOLD, 18));
        header.setForeground(new Color(232, 99, 143));
        
        enemyNameLabel = new JLabel("-");
        enemyNameLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        enemyNameLabel.setForeground(Color.WHITE);
        
        enemyHpBar = new JProgressBar(0, 100);
        enemyHpBar.setStringPainted(true);
        enemyHpBar.setForeground(new Color(232, 99, 143));
        enemyHpBar.setBackground(new Color(33, 40, 52));
        enemyHpBar.setPreferredSize(new Dimension(250, 25));
        
        enemyHpLabel = new JLabel("- / -");
        enemyHpLabel.setForeground(Color.LIGHT_GRAY);
        enemyHpLabel.setFont(new Font("Monospaced", Font.BOLD, 12));
        
        card.add(header);
        card.add(Box.createVerticalStrut(10));
        card.add(enemyNameLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(enemyHpBar);
        
        return card;
    }
    
    private JPanel createStatsCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(39, 52, 68));
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(94, 140, 255), 2, true),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel header = new JLabel("📊 STATS");
        header.setFont(new Font("Serif", Font.BOLD, 16));
        header.setForeground(new Color(94, 140, 255));
        
        comboLabel = new JLabel("🔥 Combo: 0x");
        comboLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        comboLabel.setForeground(new Color(255, 140, 0));
        
        goldLabel = new JLabel("💰 Gold: 0");
        goldLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        goldLabel.setForeground(new Color(247, 208, 96));
        
        achievementLabel = new JLabel("🏆 Achievements: 0/6");
        achievementLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        achievementLabel.setForeground(new Color(160, 255, 200));
        
        card.add(header);
        card.add(Box.createVerticalStrut(10));
        card.add(comboLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(goldLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(achievementLabel);
        
        return card;
    }
    
    private JPanel buildCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(18, 22, 28));
        
        JLabel logTitle = new JLabel("⚔️ BATTLE LOG");
        logTitle.setFont(new Font("Serif", Font.BOLD, 16));
        logTitle.setForeground(new Color(247, 208, 96));
        logTitle.setBorder(new EmptyBorder(0, 0, 10, 0));
        
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        logArea.setBackground(new Color(26, 31, 38));
        logArea.setForeground(Color.WHITE);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setBorder(new LineBorder(new Color(94, 140, 255), 2, true));
        
        panel.add(logTitle, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel buildRightPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(18, 22, 28));
        panel.setPreferredSize(new Dimension(200, 0));
        
        JLabel actionLabel = new JLabel("⚔️ ACTIONS");
        actionLabel.setFont(new Font("Serif", Font.BOLD, 16));
        actionLabel.setForeground(new Color(247, 208, 96));
        actionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(actionLabel);
        panel.add(Box.createVerticalStrut(15));
        
        attackButton = createActionButton("⚔️ SERANG", new Color(78, 190, 159));
        healButton = createActionButton("💚 HEAL", new Color(94, 140, 255));
        ultimateButton = createActionButton("💥 ULTIMATE", new Color(232, 99, 143));
        
        attackButton.addActionListener(e -> executeAction("attack"));
        healButton.addActionListener(e -> executeAction("heal"));
        ultimateButton.addActionListener(e -> executeAction("ultimate"));
        
        panel.add(attackButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(healButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(ultimateButton);
        panel.add(Box.createVerticalStrut(20));
        
        // Achievements
        JLabel achLabel = new JLabel("🏆 ACHIEVEMENTS");
        achLabel.setFont(new Font("Serif", Font.BOLD, 14));
        achLabel.setForeground(new Color(247, 208, 96));
        achLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        achievementPanel = new JPanel();
        achievementPanel.setLayout(new BoxLayout(achievementPanel, BoxLayout.Y_AXIS));
        achievementPanel.setBackground(new Color(26, 31, 38));
        achievementPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        updateAchievementDisplay();
        
        JScrollPane achScroll = new JScrollPane(achievementPanel);
        achScroll.setPreferredSize(new Dimension(180, 250));
        achScroll.setBorder(new LineBorder(new Color(160, 255, 200), 2, true));
        
        panel.add(achLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(achScroll);
        
        setButtonsEnabled(false);
        
        return panel;
    }
    
    private JPanel buildBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panel.setBackground(new Color(18, 22, 28));
        
        JLabel selectLabel = new JLabel("Pilih Musuh:");
        selectLabel.setForeground(Color.WHITE);
        selectLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        String[] enemies = {"Kroco (Goblin)", "Boss Naga Kuwad", "Super Boss Raja Iblis"};
        enemyCombo = new JComboBox<>(enemies);
        enemyCombo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        enemyCombo.setPreferredSize(new Dimension(200, 30));
        
        startButton = createActionButton("🎮 MULAI BATTLE", new Color(247, 208, 96));
        startButton.addActionListener(e -> startBattle());
        
        panel.add(selectLabel);
        panel.add(enemyCombo);
        panel.add(startButton);
        
        return panel;
    }
    
    private JButton createActionButton(String text, Color color) {
        JButton button = new RoundButton(text, color);
        button.setPreferredSize(new Dimension(180, 45));
        button.setMaximumSize(new Dimension(180, 45));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }
    
    private void promptPlayerName() {
        playerName = JOptionPane.showInputDialog(this, "Masukkan nama hero:", "Nama Hero", JOptionPane.QUESTION_MESSAGE);
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "Hero";
        }
        player = new PlayerEnhanced(playerName);
        playerNameLabel.setText(playerName);
        appendLog("════════════════════════════════════════");
        appendLog("  Selamat datang, " + playerName + "!");
        appendLog("════════════════════════════════════════\n");
        updateStatus();
    }
    
    private void startBattle() {
        int selectedIndex = enemyCombo.getSelectedIndex();
        switch (selectedIndex) {
            case 0: musuh = new Musuh("Kroco (Goblin)"); break;
            case 1: musuh = new Boss("Boss Naga Kuwad"); break;
            case 2: musuh = new SuperBoss("Super Boss Raja Iblis"); break;
        }
        
        musuhMaxHp = musuh.getDarah();
        enemyHpBar.setMaximum(musuhMaxHp);
        maxComboThisBattle = 0;
        
        logArea.setText("");
        appendColoredLog("═══════════════════════════════════════", Color.YELLOW);
        appendColoredLog("     ⚔️ BATTLE START! ⚔️", Color.YELLOW);
        appendColoredLog("     VS " + musuh.getNama(), Color.RED);
        appendColoredLog("═══════════════════════════════════════\n", Color.YELLOW);
        
        enemyNameLabel.setText(musuh.getNama());
        setButtonsEnabled(true);
        updateStatus();
    }
    
    private void executeAction(String action) {
        if (musuh == null || musuh.isMati()) return;
        
        try {
            switch (action) {
                case "attack":
                    int damage = player.serangMusuh(musuh);
                    boolean isCritical = damage < 0;
                    damage = Math.abs(damage);
                    
                    if (isCritical) {
                        appendColoredLog("💥 CRITICAL HIT! " + playerName + " menyerang " + damage + " damage!", new Color(255, 100, 100));
                        playSound();
                    } else {
                        appendColoredLog("⚔️ " + playerName + " menyerang! Damage: " + damage, new Color(78, 190, 159));
                    }
                    
                    if (isCritical) {
                        achievementManager.checkAndUnlock("Critical Strike");
                    }
                    
                    if (player.getCombo() > maxComboThisBattle) {
                        maxComboThisBattle = player.getCombo();
                    }
                    
                    if (player.getCombo() >= 5) {
                        achievementManager.checkAndUnlock("Combo Master");
                    }
                    break;
                    
                case "heal":
                    int beforeHp = player.getDarah();
                    player.penyembuhan();
                    int healed = player.getDarah() - beforeHp;
                    appendColoredLog("💚 " + playerName + " sembuh +" + healed + " HP!", new Color(94, 255, 140));
                    break;
                    
                case "ultimate":
                    int ultDamage = player.seranganBurst(musuh);
                    appendColoredLog("🌟 ULTIMATE ATTACK! " + playerName + " menghasilkan " + ultDamage + " damage!", new Color(255, 215, 0));
                    screenShake();
                    break;
            }
            
            if (!musuh.isMati()) {
                musuh.serangan(player);
                appendColoredLog("👹 " + musuh.getNama() + " menyerang balik!", new Color(232, 99, 143));
            }
            
            updateStatus();
            checkOutcome();
            
        } catch (Exception ex) {
            appendColoredLog("❌ " + ex.getMessage(), Color.RED);
        }
    }
    
    private void checkOutcome() {
        if (musuh == null) return;
        
        if (musuh.isMati()) {
            player.onEnemyDefeated();
            
            // Calculate rewards
            int expGain = musuhMaxHp / 2;
            int goldGain = random.nextInt(50) + 20;
            
            player.gainExp(expGain);
            player.addGold(goldGain);
            
            appendColoredLog("\n════════════════════════════════════════", new Color(0, 255, 0));
            appendColoredLog("       ⭐ KEMENANGAN! ⭐", new Color(0, 255, 0));
            appendColoredLog("  " + musuh.getNama() + " telah dikalahkan!", Color.WHITE);
            appendColoredLog("  💎 EXP +" + expGain, new Color(247, 208, 96));
            appendColoredLog("  💰 Gold +" + goldGain, new Color(247, 208, 96));
            appendColoredLog("  🔥 Max Combo: " + maxComboThisBattle + "x", new Color(255, 140, 0));
            appendColoredLog("════════════════════════════════════════\n", new Color(0, 255, 0));
            
            // Check achievements
            achievementManager.checkAndUnlock("First Blood");
            if (!player.hasUsedHealing()) {
                achievementManager.checkAndUnlock("Immortal");
            }
            if (musuh instanceof Boss) {
                achievementManager.checkAndUnlock("Boss Slayer");
            }
            if (musuh instanceof SuperBoss) {
                achievementManager.checkAndUnlock("Ultimate Power");
            }
            
            updateAchievementDisplay();
            setButtonsEnabled(false);
            
        } else if (player.getDarah() <= 0) {
            appendColoredLog("\n════════════════════════════════════════", Color.RED);
            appendColoredLog("       💀 GAME OVER 💀", Color.RED);
            appendColoredLog("       HP kamu habis...", Color.WHITE);
            appendColoredLog("════════════════════════════════════════\n", Color.RED);
            setButtonsEnabled(false);
        }
    }
    
    private void updateStatus() {
        if (player == null) return;
        
        // Player stats
        playerHpLabel.setText(player.getDarah() + " / " + player.getMaxDarah());
        playerHpBar.setMaximum(player.getMaxDarah());
        playerHpBar.setValue(player.getDarah());
        playerHpBar.setString(player.getDarah() + " / " + player.getMaxDarah());
        
        // Color gradient for HP bar
        float hpPercent = (float)player.getDarah() / player.getMaxDarah();
        if (hpPercent > 0.6f) {
            playerHpBar.setForeground(new Color(94, 255, 140));
        } else if (hpPercent > 0.3f) {
            playerHpBar.setForeground(new Color(255, 215, 0));
        } else {
            playerHpBar.setForeground(new Color(255, 100, 100));
        }
        
        // Level & EXP
        playerLevelLabel.setText("Level: " + player.getLevel());
        expBar.setMaximum(player.getExpForNextLevel());
        expBar.setValue(player.getExp());
        expBar.setString("EXP: " + player.getExp() + " / " + player.getExpForNextLevel());
        
        // Enemy stats
        if (musuh != null) {
            enemyHpLabel.setText(musuh.getDarah() + " / " + musuhMaxHp);
            enemyHpBar.setValue(musuh.getDarah());
            enemyHpBar.setString(musuh.getDarah() + " / " + musuhMaxHp);
            enemyNameLabel.setText(musuh.getNama());
        }
        
        // Game stats
        comboLabel.setText("🔥 Combo: " + player.getCombo() + "x");
        goldLabel.setText("💰 Gold: " + player.getGold());
        achievementLabel.setText("🏆 Achievements: " + achievementManager.getUnlockedCount() + "/6");
    }
    
    private void updateAchievementDisplay() {
        achievementPanel.removeAll();
        for (Achievement ach : achievementManager.getAchievements()) {
            JLabel achLabel = new JLabel(ach.toString());
            achLabel.setFont(new Font("Monospaced", Font.PLAIN, 11));
            achLabel.setForeground(ach.isUnlocked() ? new Color(0, 255, 100) : Color.GRAY);
            achievementPanel.add(achLabel);
            achievementPanel.add(Box.createVerticalStrut(5));
        }
        achievementPanel.revalidate();
        achievementPanel.repaint();
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
    
    private void appendColoredLog(String text, Color color) {
        appendLog(text);
    }
    
    private void screenShake() {
        Point originalLocation = getLocation();
        Timer shakeTimer = new Timer(50, null);
        final int[] shakeCount = {0};
        
        shakeTimer.addActionListener(e -> {
            if (shakeCount[0] < 6) {
                int offsetX = random.nextInt(10) - 5;
                int offsetY = random.nextInt(10) - 5;
                setLocation(originalLocation.x + offsetX, originalLocation.y + offsetY);
                shakeCount[0]++;
            } else {
                setLocation(originalLocation);
                ((Timer)e.getSource()).stop();
            }
        });
        shakeTimer.start();
    }
    
    private void playSound() {
        Toolkit.getDefaultToolkit().beep();
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
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    hovered = true;
                    repaint();
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
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
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            
            FontMetrics fm = g2d.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(getText())) / 2;
            int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
            
            g2d.setColor(Color.WHITE);
            g2d.setFont(getFont());
            g2d.drawString(getText(), x, y);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameGUIEnhanced gui = new GameGUIEnhanced();
            gui.setVisible(true);
        });
    }
}
