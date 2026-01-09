package project_akhir.project2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GameGUI extends Application {
    
    private enum TurnAction {
        ATTACK, HEAL, ULTIMATE
    }
    
    private Player player;
    private Musuh musuh;
    private String playerName;
    private int musuhMaxHp;
    
    private Label playerNameLabel;
    private Label playerHpLabel;
    private Label enemyNameLabel;
    private Label enemyHpLabel;
    private ProgressBar playerHpBar;
    private ProgressBar enemyHpBar;
    private TextArea logArea;
    private ComboBox<String> enemyCombo;
    private Button startButton;
    private Button attackButton;
    private Button healButton;
    private Button ultimateButton;
    
    private static final int PLAYER_MAX_HP = 150;
    
    @Override
    public void start(Stage stage) {
        stage.setTitle("⚔️ RPG Fantasy Arena");
        stage.setWidth(1000);
        stage.setHeight(650);
        stage.setResizable(false);
        
        // Main Layout
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #121619;");
        root.setPadding(new Insets(15));
        
        // Top - Title
        VBox topBox = createTopPanel();
        root.setTop(topBox);
        
        // Left - Stats & Controls
        VBox leftBox = createLeftPanel();
        leftBox.setPrefWidth(280);
        root.setLeft(leftBox);
        
        // Center - Log Area
        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setWrapText(true);
        logArea.setStyle("-fx-control-inner-background: #1a1f26; -fx-text-fill: white; -fx-font-family: 'Monospaced'; -fx-font-size: 12;");
        logArea.setPrefRowCount(25);
        
        ScrollPane scrollPane = new ScrollPane(logArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #1a1f26;");
        root.setCenter(scrollPane);
        
        // Right - Action Buttons
        VBox rightBox = createActionPanel();
        root.setRight(rightBox);
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
        // Prompt for player name
        promptPlayerName();
    }
    
    private VBox createTopPanel() {
        VBox topBox = new VBox(10);
        topBox.setStyle("-fx-background-color: #121619;");
        topBox.setPadding(new Insets(10));
        
        Label titleLabel = new Label("⚔️ RPG Fantasy Arena");
        titleLabel.setStyle("-fx-font-size: 32; -fx-font-weight: bold; -fx-text-fill: #f7d060;");
        
        topBox.getChildren().add(titleLabel);
        return topBox;
    }
    
    private VBox createLeftPanel() {
        VBox leftBox = new VBox(15);
        leftBox.setStyle("-fx-background-color: #121619;");
        
        // Player Stats
        VBox playerStats = createStatsBox("Player Stats", "#4ebea0");
        playerNameLabel = new Label("...");
        playerNameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold;");
        playerHpLabel = new Label("HP: 150/150");
        playerHpLabel.setStyle("-fx-text-fill: white; -fx-font-size: 13;");
        playerHpBar = new ProgressBar(1.0);
        playerHpBar.setPrefWidth(240);
        playerHpBar.setStyle("-fx-accent: #4ebea0;");
        playerStats.getChildren().addAll(playerNameLabel, playerHpLabel, playerHpBar);
        
        // Enemy Stats
        VBox enemyStats = createStatsBox("Enemy Stats", "#e8638f");
        enemyNameLabel = new Label("Belum dipilih");
        enemyNameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold;");
        enemyHpLabel = new Label("HP: 0/0");
        enemyHpLabel.setStyle("-fx-text-fill: white; -fx-font-size: 13;");
        enemyHpBar = new ProgressBar(0);
        enemyHpBar.setPrefWidth(240);
        enemyHpBar.setStyle("-fx-accent: #e8638f;");
        enemyStats.getChildren().addAll(enemyNameLabel, enemyHpLabel, enemyHpBar);
        
        // Enemy Selection
        VBox selectBox = createStatsBox("Pilih Musuh", "#5e8cff");
        enemyCombo = new ComboBox<>();
        enemyCombo.getItems().addAll(
            "Kroco (Goblin)",
            "Boss Naga Kuwad",
            "Super Boss Raja Iblis"
        );
        enemyCombo.setValue("Kroco (Goblin)");
        enemyCombo.setStyle("-fx-font-size: 12; -fx-padding: 8;");
        enemyCombo.setPrefWidth(240);
        
        startButton = new Button("START BATTLE");
        startButton.setStyle("-fx-font-size: 14; -fx-padding: 12; -fx-background-color: #5e8cff; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5;");
        startButton.setPrefWidth(240);
        startButton.setOnAction(e -> startBattle());
        
        selectBox.getChildren().addAll(enemyCombo, startButton);
        
        leftBox.getChildren().addAll(playerStats, enemyStats, selectBox);
        return leftBox;
    }
    
    private VBox createStatsBox(String title, String color) {
        VBox box = new VBox(10);
        box.setStyle("-fx-border-color: " + color + "; -fx-border-width: 2; -fx-padding: 12;");
        box.setPrefHeight(150);
        
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: " + color + ";");
        box.getChildren().add(titleLabel);
        
        return box;
    }
    
    private VBox createActionPanel() {
        VBox actionBox = new VBox(15);
        actionBox.setStyle("-fx-background-color: #121619;");
        actionBox.setPrefWidth(160);
        actionBox.setAlignment(Pos.TOP_CENTER);
        actionBox.setPadding(new Insets(20, 10, 20, 10));
        
        Label actionLabel = new Label("Actions");
        actionLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: #f7d060;");
        
        attackButton = createActionButton("SERANG", "#4ebea0");
        healButton = createActionButton("PENYEMBUHAN", "#5e8cff");
        ultimateButton = createActionButton("ULTIMATE", "#e8638f");
        
        attackButton.setOnAction(e -> executePlayerTurn(TurnAction.ATTACK));
        healButton.setOnAction(e -> executePlayerTurn(TurnAction.HEAL));
        ultimateButton.setOnAction(e -> executePlayerTurn(TurnAction.ULTIMATE));
        
        setButtonsEnabled(false);
        
        actionBox.getChildren().addAll(actionLabel, attackButton, healButton, ultimateButton);
        return actionBox;
    }
    
    private Button createActionButton(String text, String color) {
        Button btn = new Button(text);
        btn.setStyle("-fx-font-size: 13; -fx-padding: 15; -fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5;");
        btn.setPrefWidth(140);
        return btn;
    }
    
    private void promptPlayerName() {
        TextInputDialog dialog = new TextInputDialog("Hero");
        dialog.setTitle("Input Nama Hero");
        dialog.setHeaderText("Masukkan nama hero Anda:");
        dialog.setContentText("Nama:");
        dialog.showAndWait().ifPresent(name -> {
            playerName = name.isEmpty() ? "Hero" : name;
            player = new Player(playerName);
            playerNameLabel.setText(playerName);
            appendLog("Selamat datang, " + playerName + "!");
            updateStatus();
        });
    }
    
    private void startBattle() {
        String selectedEnemy = enemyCombo.getValue();
        switch(selectedEnemy) {
            case "Kroco (Goblin)" -> musuh = new Musuh("Kroco (Goblin)");
            case "Boss Naga Kuwad" -> musuh = new Boss("Boss Naga Kuwad");
            case "Super Boss Raja Iblis" -> musuh = new SuperBoss("Super Boss Raja Iblis");
            default -> musuh = new Musuh("Kroco (Goblin)");
        }
        
        musuhMaxHp = musuh.getDarah();
        enemyHpBar.setProgress(1.0);
        player = new Player(playerName);
        logArea.clear();
        appendLog("═══════════════════════════════════════");
        appendLog("Melawan " + musuh.getNama() + "!");
        appendLog("═══════════════════════════════════════\n");
        enemyNameLabel.setText(musuh.getNama());
        setButtonsEnabled(true);
        updateStatus();
    }
    
    private void executePlayerTurn(TurnAction action) {
        if (musuh == null) {
            showAlert("Pilih musuh dan mulai pertarungan terlebih dahulu.");
            return;
        }
        
        try {
            switch(action) {
                case ATTACK:
                    player.serangMusuh(musuh);
                    appendLog("[" + playerName + "] Menyerang " + musuh.getNama() + "! Damage: " + musuh.getSerangan());
                    break;
                case HEAL:
                    player.penyembuhan();
                    appendLog("[" + playerName + "] Menggunakan penyembuhan! HP: " + player.getDarah());
                    break;
                case ULTIMATE:
                    player.seranganBurst(musuh);
                    appendLog("[" + playerName + "] Menggunakan ULTIMATE! Damage: " + (musuh.getSerangan() * 8));
                    break;
            }
            
            if (musuh.getDarah() <= 0) {
                appendLog("\n✓✓✓ " + musuh.getNama() + " telah dikalahkan! ✓✓✓\n");
                setButtonsEnabled(false);
                return;
            }
            
            // Enemy turn - simple random action
            java.util.Random rand = new java.util.Random();
            if (rand.nextBoolean()) {
                musuh.serangan(player);
                appendLog("[" + musuh.getNama() + "] Menyerang balik! Damage: " + musuh.getSerangan());
            } else {
                appendLog("[" + musuh.getNama() + "] Menghindar!");
            }
            
            if (player.getDarah() <= 0) {
                appendLog("\n✗✗✗ " + playerName + " telah dikalahkan! ✗✗✗\n");
                setButtonsEnabled(false);
                return;
            }
            
            updateStatus();
        } catch (Exception ex) {
            showAlert("Error: " + ex.getMessage());
        }
    }
    
    private void updateStatus() {
        playerHpLabel.setText("HP: " + player.getDarah() + "/150");
        playerHpBar.setProgress((double) player.getDarah() / PLAYER_MAX_HP);
        
        if (musuh != null) {
            enemyHpLabel.setText("HP: " + musuh.getDarah() + "/" + musuhMaxHp);
            enemyHpBar.setProgress((double) musuh.getDarah() / musuhMaxHp);
        }
    }
    
    private void appendLog(String message) {
        logArea.appendText(message + "\n");
    }
    
    private void setButtonsEnabled(boolean enabled) {
        attackButton.setDisable(!enabled);
        healButton.setDisable(!enabled);
        ultimateButton.setDisable(!enabled);
    }
    
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
