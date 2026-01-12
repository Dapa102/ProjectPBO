package project_akhir.project2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.paint.CycleMethod;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.animation.*;
import javafx.util.Duration;

public class GameGUI extends Application {
    
    private enum TurnAction {
        ATTACK, HEAL, ULTIMATE
    }
    
    private Player player;
    private Musuh musuh;
    private String playerName;
    private int musuhMaxHp;
    
    private Label playerHpLabel;
    private Label enemyHpLabel;
    private ProgressBar playerHpBar;
    private ProgressBar enemyHpBar;
    private TextArea logArea;
    private ComboBox<String> enemyCombo;
    private Button startButton;
    private Button exitButton;
    private Button attackButton;
    private Button healButton;
    private Button ultimateButton;
    
    private StackPane mainContainer;
    private Stage primaryStage;
    private BorderPane menuPane;
    private StackPane vsPane;
    private BorderPane battlePane;
    
    // Battle character containers for damage animation
    private StackPane playerCharacterContainer;
    private StackPane enemyCharacterContainer;
    
    private static final int PLAYER_MAX_HP = 150;
    
    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        stage.setTitle("⚔️ RPG Fantasy Arena");
        stage.setWidth(1000);
        stage.setHeight(650);
        stage.setResizable(false);
        
        // Main container with stacked screens
        mainContainer = new StackPane();
        
        // Create all screens
        menuPane = createMenuScreen();
        vsPane = createVSScreen();
        battlePane = createBattleScreen();
        
        // Initially show only menu
        mainContainer.getChildren().addAll(menuPane, vsPane, battlePane);
        vsPane.setVisible(false);
        battlePane.setVisible(false);
        
        Scene scene = new Scene(mainContainer);
        stage.setScene(scene);
        stage.show();
        
        // Prompt for player name
        promptPlayerName();
    }
    
    private BorderPane createMenuScreen() {
        // Use StackPane as root to layer background and content
        StackPane root = new StackPane();
        
        // Fantasy background gradient
        Stop[] bgStops = new Stop[] {
            new Stop(0, Color.web("#1a0d2e")),
            new Stop(0.3, Color.web("#16213e")),
            new Stop(0.7, Color.web("#0f3460")),
            new Stop(1, Color.web("#1a0d2e"))
        };
        LinearGradient bgGradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, bgStops);
        
        Rectangle background = new Rectangle(1000, 650);
        background.setFill(bgGradient);
        
        // Main layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: transparent;");
        mainLayout.setPrefSize(1000, 650);
        
        // Top - Title
        VBox topBox = createTopPanel();
        topBox.setStyle("-fx-background-color: transparent;");
        mainLayout.setTop(topBox);
        
        // Center - Menu content
        VBox centerBox = new VBox(20);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(40));
        
        // Enemy Selection
        VBox selectBox = new VBox(20);
        selectBox.setAlignment(Pos.CENTER);
        selectBox.setStyle("-fx-border-color: #4d7dbf; -fx-border-width: 3; -fx-padding: 40; -fx-background-color: rgba(26, 31, 38, 0.9); -fx-background-radius: 15; -fx-border-radius: 15;");
        selectBox.setPrefWidth(500);
        selectBox.setMaxWidth(500);
        
        Label selectLabel = new Label("⚔️ Pilih Musuh ⚔️");
        selectLabel.setStyle("-fx-font-size: 28; -fx-font-weight: bold; -fx-text-fill: #ffd700; -fx-effect: dropshadow(gaussian, rgba(255,215,0,0.6), 15, 0.6, 0, 0);");
        selectLabel.setWrapText(false);
        
        enemyCombo = new ComboBox<>();
        enemyCombo.getItems().addAll(
            "Kroco (Goblin)",
            "Boss Naga Kuwad",
            "Super Boss Raja Iblis"
        );
        enemyCombo.setValue("Kroco (Goblin)");
        enemyCombo.setStyle("-fx-font-size: 15; -fx-padding: 12;");
        enemyCombo.setPrefWidth(380);
        
        startButton = new Button("⚔️ START BATTLE");
        startButton.setStyle("-fx-font-size: 18; -fx-padding: 18; -fx-background-color: #2d5a8f; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-border-color: #4d7dbf; -fx-border-width: 2; -fx-border-radius: 8;");
        startButton.setPrefWidth(380);
        startButton.setOnAction(e -> startBattle());
        
        exitButton = new Button("🚪 EXIT");
        exitButton.setStyle("-fx-font-size: 16; -fx-padding: 15; -fx-background-color: #8f2d2d; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-border-color: #af4d4d; -fx-border-width: 2; -fx-border-radius: 8;");
        exitButton.setPrefWidth(380);
        exitButton.setOnAction(e -> exitGame());
        
        selectBox.getChildren().addAll(selectLabel, enemyCombo, startButton, exitButton);
        centerBox.getChildren().add(selectBox);
        mainLayout.setCenter(centerBox);
        
        root.getChildren().addAll(background, mainLayout);
        
        // Wrap in BorderPane for consistency with other screens
        BorderPane wrapper = new BorderPane();
        wrapper.setCenter(root);
        return wrapper;
    }
    
    private StackPane createVSScreen() {
        StackPane vsRoot = new StackPane();
        
        // Background with gradient
        Stop[] stops = new Stop[] {
            new Stop(0, Color.web("#1a0033")),
            new Stop(0.5, Color.web("#2d1b3d")),
            new Stop(1, Color.web("#1a0033"))
        };
        LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);
        
        Rectangle background = new Rectangle(1000, 650);
        background.setFill(gradient);
        
        // Main layout
        HBox mainLayout = new HBox();
        mainLayout.setPrefSize(1000, 650);
        
        // Left side - Player
        VBox playerSide = createCharacterPanel(true);
        playerSide.setPrefWidth(400);
        
        // Center - VS
        VBox centerVS = new VBox(20);
        centerVS.setAlignment(Pos.CENTER);
        centerVS.setPrefWidth(200);
        
        Label vsLabel = new Label("VS");
        vsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 80));
        vsLabel.setTextFill(Color.WHITE);
        vsLabel.setStyle("-fx-effect: dropshadow(gaussian, rgba(255,255,255,0.8), 20, 0.5, 0, 0);");
        
        centerVS.getChildren().add(vsLabel);
        
        // Right side - Enemy
        VBox enemySide = createCharacterPanel(false);
        enemySide.setPrefWidth(400);
        
        mainLayout.getChildren().addAll(playerSide, centerVS, enemySide);
        
        vsRoot.getChildren().addAll(background, mainLayout);
        return vsRoot;
    }
    
    private VBox createCharacterPanel(boolean isPlayer) {
        VBox panel = new VBox(15);
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new Insets(50));
        
        Color mainColor = isPlayer ? Color.web("#2d7a68") : Color.web("#a8335f");
        Color bgColor = isPlayer ? Color.web("#0f2d3f") : Color.web("#3f0f2d");
        
        // Character "sprite" (placeholder)
        VBox characterBox = new VBox(10);
        characterBox.setAlignment(Pos.CENTER);
        
        // Main body
        Rectangle body = new Rectangle(100, 120);
        body.setFill(mainColor);
        body.setArcWidth(20);
        body.setArcHeight(20);
        
        // Head
        Circle head = new Circle(30);
        head.setFill(mainColor.brighter());
        
        // Eyes
        HBox eyes = new HBox(15);
        eyes.setAlignment(Pos.CENTER);
        Circle eye1 = new Circle(5, Color.web("#cc00cc"));
        Circle eye2 = new Circle(5, Color.web("#cc00cc"));
        eyes.getChildren().addAll(eye1, eye2);
        
        // Weapon/accessory
        Rectangle weapon = new Rectangle(15, 60);
        weapon.setFill(Color.web("#b8960f"));
        
        StackPane characterSprite = new StackPane();
        VBox bodyGroup = new VBox(-10);
        bodyGroup.setAlignment(Pos.CENTER);
        bodyGroup.getChildren().addAll(head, eyes, body);
        
        HBox spriteWithWeapon = new HBox(10);
        spriteWithWeapon.setAlignment(Pos.CENTER);
        if (isPlayer) {
            spriteWithWeapon.getChildren().addAll(weapon, bodyGroup);
        } else {
            spriteWithWeapon.getChildren().addAll(bodyGroup, weapon);
        }
        
        characterSprite.getChildren().add(spriteWithWeapon);
        characterBox.getChildren().add(characterSprite);
        
        // Stats panel
        VBox statsPanel = new VBox(5);
        statsPanel.setAlignment(Pos.CENTER);
        statsPanel.setStyle("-fx-background-color: " + toRgbString(bgColor) + "; -fx-padding: 15; -fx-border-color: " + toRgbString(mainColor) + "; -fx-border-width: 3;");
        statsPanel.setMaxWidth(250);
        
        Label nameLabel = new Label(isPlayer ? (playerName != null ? playerName : "Hero") : (musuh != null ? musuh.getNama() : "Enemy"));
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        nameLabel.setTextFill(Color.WHITE);
        
        Label hpLabel = new Label("HP " + (isPlayer ? "21200" : "21200"));
        hpLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        hpLabel.setTextFill(Color.WHITE);
        
        Label spdLabel = new Label("SPD 20");
        spdLabel.setFont(Font.font("Arial", 14));
        spdLabel.setTextFill(Color.WHITE);
        
        Label atkLabel = new Label("ATK " + (isPlayer ? "19" : "19"));
        atkLabel.setFont(Font.font("Arial", 14));
        atkLabel.setTextFill(Color.WHITE);
        
        statsPanel.getChildren().addAll(nameLabel, hpLabel, spdLabel, atkLabel);
        
        panel.getChildren().addAll(characterBox, statsPanel);
        
        return panel;
    }
    
    private String toRgbString(Color color) {
        return String.format("#%02X%02X%02X", 
            (int)(color.getRed() * 255),
            (int)(color.getGreen() * 255),
            (int)(color.getBlue() * 255));
    }
    
    private BorderPane createBattleScreen() {
        // Use StackPane as root for proper layering
        StackPane root = new StackPane();
        
        // Fantasy background gradient
        Stop[] bgStops = new Stop[] {
            new Stop(0, Color.web("#1a0d2e")),
            new Stop(0.3, Color.web("#16213e")),
            new Stop(0.7, Color.web("#0f3460")),
            new Stop(1, Color.web("#1a0d2e"))
        };
        LinearGradient bgGradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, bgStops);
        
        Rectangle background = new Rectangle(1000, 650);
        background.setFill(bgGradient);
        
        // Battle ground effect
        Rectangle ground = new Rectangle(1000, 150);
        ground.setFill(Color.web("#0a0a1a"));
        ground.setOpacity(0.5);
        ground.setTranslateY(250);
        
        // Main layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: transparent;");
        mainLayout.setPrefSize(1000, 650);
        
        // Center - Battle Arena with characters side by side
        HBox battleArena = new HBox(100);
        battleArena.setAlignment(Pos.CENTER);
        battleArena.setPadding(new Insets(60, 50, 20, 50));
        
        // Player side (left)
        VBox playerSide = createBattleCharacter(true);
        
        // Enemy side (right)
        VBox enemySide = createBattleCharacter(false);
        
        battleArena.getChildren().addAll(playerSide, enemySide);
        mainLayout.setCenter(battleArena);
        
        // Bottom - Action buttons and action message
        VBox bottomBox = createBattleBottomPanel();
        mainLayout.setBottom(bottomBox);
        
        root.getChildren().addAll(background, ground, mainLayout);
        
        // Wrap in BorderPane for consistency
        BorderPane wrapper = new BorderPane();
        wrapper.setCenter(root);
        return wrapper;
    }
    
    private VBox createBattleCharacter(boolean isPlayer) {
        VBox container = new VBox(15);
        container.setAlignment(Pos.CENTER);
        container.setPrefWidth(350);
        
        Color mainColor = isPlayer ? Color.web("#2d7a68") : Color.web("#a8335f");
        Color bgColor = isPlayer ? Color.web("#0f2d3f") : Color.web("#3f0f2d");
        
        // Character sprite with damage animation container
        StackPane characterContainer = new StackPane();
        characterContainer.setPrefSize(200, 250);
        
        // Character visual
        VBox characterBox = new VBox(10);
        characterBox.setAlignment(Pos.CENTER);
        
        // Main body
        Rectangle body = new Rectangle(100, 120);
        body.setFill(mainColor);
        body.setArcWidth(20);
        body.setArcHeight(20);
        
        // Head
        Circle head = new Circle(30);
        head.setFill(mainColor.brighter());
        
        // Eyes
        HBox eyes = new HBox(15);
        eyes.setAlignment(Pos.CENTER);
        Circle eye1 = new Circle(5, Color.web("#cc00cc"));
        Circle eye2 = new Circle(5, Color.web("#cc00cc"));
        eyes.getChildren().addAll(eye1, eye2);
        
        // Weapon
        Rectangle weapon = new Rectangle(15, 60);
        weapon.setFill(Color.web("#b8960f"));
        
        VBox bodyGroup = new VBox(-10);
        bodyGroup.setAlignment(Pos.CENTER);
        bodyGroup.getChildren().addAll(head, eyes, body);
        
        HBox spriteWithWeapon = new HBox(10);
        spriteWithWeapon.setAlignment(Pos.CENTER);
        if (isPlayer) {
            spriteWithWeapon.getChildren().addAll(weapon, bodyGroup);
        } else {
            spriteWithWeapon.getChildren().addAll(bodyGroup, weapon);
        }
        
        characterBox.getChildren().add(spriteWithWeapon);
        characterContainer.getChildren().add(characterBox);
        
        // Store reference for damage animation
        if (isPlayer) {
            playerCharacterContainer = characterContainer;
        } else {
            enemyCharacterContainer = characterContainer;
        }
        
        // Stats panel
        VBox statsPanel = new VBox(8);
        statsPanel.setAlignment(Pos.CENTER);
        statsPanel.setStyle("-fx-background-color: " + toRgbString(bgColor) + "; -fx-padding: 15; -fx-border-color: " + toRgbString(mainColor) + "; -fx-border-width: 3; -fx-background-radius: 10; -fx-border-radius: 10;");
        statsPanel.setMaxWidth(300);
        
        Label nameLabel = new Label(isPlayer ? (playerName != null ? playerName : "Hero") : "Enemy");
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        nameLabel.setTextFill(Color.WHITE);
        
        // HP Bar with label
        HBox hpBox = new HBox(10);
        hpBox.setAlignment(Pos.CENTER);
        Label hpText = new Label("HP:");
        hpText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        hpText.setTextFill(Color.WHITE);
        
        ProgressBar hpBar = new ProgressBar(1.0);
        hpBar.setPrefWidth(180);
        hpBar.setPrefHeight(20);
        hpBar.setStyle("-fx-accent: " + toRgbString(mainColor) + ";");
        
        Label hpLabel = new Label("150/150");
        hpLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        hpLabel.setTextFill(Color.WHITE);
        
        hpBox.getChildren().addAll(hpText, hpBar, hpLabel);
        
        Label spdLabel = new Label("SPD: 20");
        spdLabel.setFont(Font.font("Arial", 13));
        spdLabel.setTextFill(Color.LIGHTGRAY);
        
        Label atkLabel = new Label("ATK: " + (isPlayer ? "19" : "19"));
        atkLabel.setFont(Font.font("Arial", 13));
        atkLabel.setTextFill(Color.LIGHTGRAY);
        
        statsPanel.getChildren().addAll(nameLabel, hpBox, spdLabel, atkLabel);
        
        container.getChildren().addAll(characterContainer, statsPanel);
        
        // Store references
        if (isPlayer) {
            playerHpBar = hpBar;
            playerHpLabel = hpLabel;
        } else {
            enemyHpBar = hpBar;
            enemyHpLabel = hpLabel;
        }
        
        return container;
    }
    
    private VBox createBattleBottomPanel() {
        VBox bottomBox = new VBox(15);
        bottomBox.setPadding(new Insets(10, 30, 25, 30));
        bottomBox.setStyle("-fx-background-color: transparent;");
        
        // Action message box (single line pop-up style)
        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setWrapText(false);
        logArea.setStyle("-fx-control-inner-background: rgba(15, 20, 25, 0.8); -fx-text-fill: #ffd700; -fx-font-family: 'Arial'; -fx-font-size: 16; -fx-font-weight: bold; -fx-border-color: #ffd700; -fx-border-width: 2; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 10;");
        logArea.setPrefHeight(50);
        logArea.setMaxHeight(50);
        logArea.setText("Pilih aksi untuk memulai pertarungan!");
        
        // Action buttons
        HBox actionBox = new HBox(15);
        actionBox.setAlignment(Pos.CENTER);
        actionBox.setPadding(new Insets(5));
        
        attackButton = createBattleActionButton("SERANG", "#2d7a68");
        healButton = createBattleActionButton("PENYEMBUHAN", "#2d5a8f");
        ultimateButton = createBattleActionButton("ULTIMATE", "#a8335f");
        
        Button exitBtn = new Button("EXIT");
        exitBtn.setStyle("-fx-font-size: 14; -fx-padding: 15 25; -fx-background-color: #8f2d2d; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-border-color: #8f2d2d88; -fx-border-width: 2; -fx-border-radius: 8;");
        exitBtn.setMinWidth(180);
        exitBtn.setPrefWidth(180);
        exitBtn.setMaxWidth(180);
        exitBtn.setPrefHeight(55);
        exitBtn.setWrapText(true);
        exitBtn.setOnAction(e -> exitGame());
        
        attackButton.setOnAction(e -> executePlayerTurn(TurnAction.ATTACK));
        healButton.setOnAction(e -> executePlayerTurn(TurnAction.HEAL));
        ultimateButton.setOnAction(e -> executePlayerTurn(TurnAction.ULTIMATE));
        
        setButtonsEnabled(false);
        
        actionBox.getChildren().addAll(attackButton, healButton, ultimateButton, exitBtn);
        
        bottomBox.getChildren().addAll(logArea, actionBox);
        return bottomBox;
    }
    
    private Button createBattleActionButton(String text, String color) {
        Button btn = new Button(text);
        btn.setStyle("-fx-font-size: 14; -fx-padding: 15 25; -fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-border-color: " + color + "88; -fx-border-width: 2; -fx-border-radius: 8;");
        btn.setMinWidth(180);
        btn.setPrefWidth(180);
        btn.setMaxWidth(180);
        btn.setPrefHeight(55);
        btn.setWrapText(true);
        btn.setTextAlignment(TextAlignment.CENTER);
        
        // Hover effect
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-font-size: 14; -fx-padding: 15 25; -fx-background-color: derive(" + color + ", 20%); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-border-color: " + color + "; -fx-border-width: 3; -fx-border-radius: 8;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-font-size: 14; -fx-padding: 15 25; -fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-border-color: " + color + "88; -fx-border-width: 2; -fx-border-radius: 8;"));
        
        return btn;
    }
    
    private VBox createTopPanel() {
        VBox topBox = new VBox(10);
        topBox.setStyle("-fx-background-color: transparent;");
        topBox.setPadding(new Insets(10));
        topBox.setAlignment(Pos.CENTER);
        
        Label titleLabel = new Label("⚔️ RPG Fantasy Arena");
        titleLabel.setStyle("-fx-font-size: 36; -fx-font-weight: bold; -fx-text-fill: #ffd700; -fx-effect: dropshadow(gaussian, rgba(255,215,0,0.6), 15, 0.7, 0, 0);");
        
        topBox.getChildren().add(titleLabel);
        return topBox;
    }
    
    private void promptPlayerName() {
        TextInputDialog dialog = new TextInputDialog("Hero");
        dialog.setTitle("Masukkan Nama Warrior");
        dialog.setHeaderText("Siapa nama pahlawan Anda?");
        dialog.setContentText("Nama:");
        
        dialog.showAndWait().ifPresent(name -> {
            playerName = name.isEmpty() ? "Hero" : name;
            player = new Player(playerName);
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
        player = new Player(playerName);
        
        // Recreate VS screen with updated enemy name
        vsPane = createVSScreen();
        mainContainer.getChildren().set(1, vsPane);
        vsPane.setVisible(false);
        
        // Show opening screen
        showOpeningScreen();
    }
    
    private void showOpeningScreen() {
        // Create opening overlay
        StackPane openingPane = new StackPane();
        
        Stop[] stops = new Stop[] {
            new Stop(0, Color.web("#000000")),
            new Stop(0.5, Color.web("#1a0d2e")),
            new Stop(1, Color.web("#000000"))
        };
        LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);
        
        Rectangle bg = new Rectangle(1000, 650);
        bg.setFill(gradient);
        
        VBox textBox = new VBox(30);
        textBox.setAlignment(Pos.CENTER);
        
        Label title = new Label(playerName + "  VS  " + musuh.getNama());
        title.setFont(Font.font("Arial", FontWeight.BOLD, 45));
        title.setTextFill(Color.web("#FFD700"));
        
        Label subtitle = new Label("⚔️ PERTANDINGAN DIMULAI! ⚔️");
        subtitle.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        subtitle.setTextFill(Color.web("#FF6B6B"));
        
        textBox.getChildren().addAll(title, subtitle);
        openingPane.getChildren().addAll(bg, textBox);
        
        // Add to main container
        mainContainer.getChildren().add(openingPane);
        
        // Fade in text
        textBox.setOpacity(0);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(800), textBox);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        
        fadeIn.setOnFinished(e -> {
            // Pulse animation
            ScaleTransition pulse = new ScaleTransition(Duration.millis(500), title);
            pulse.setFromX(1.0);
            pulse.setFromY(1.0);
            pulse.setToX(1.1);
            pulse.setToY(1.1);
            pulse.setCycleCount(4);
            pulse.setAutoReverse(true);
            
            pulse.setOnFinished(event -> {
                // Wait then show VS screen
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(ev -> {
                    mainContainer.getChildren().remove(openingPane);
                    showVSScreen();
                });
                pause.play();
            });
            
            pulse.play();
        });
        
        fadeIn.play();
    }
    
    private void showVSScreen() {
        // Hide menu, show VS screen
        menuPane.setVisible(false);
        vsPane.setVisible(true);
        vsPane.setOpacity(0);
        
        // Fade in animation
        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), vsPane);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        
        // Wait 2 seconds then transition to battle
        fadeIn.setOnFinished(e -> {
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(event -> transitionToBattle());
            pause.play();
        });
        
        fadeIn.play();
    }
    
    private void transitionToBattle() {
        // Fade out VS screen
        FadeTransition fadeOut = new FadeTransition(Duration.millis(500), vsPane);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        
        fadeOut.setOnFinished(e -> {
            vsPane.setVisible(false);
            battlePane.setVisible(true);
            battlePane.setOpacity(0);
            
            // Initialize battle
            enemyHpBar.setProgress(1.0);
            appendLog("⚔️ Melawan " + musuh.getNama() + "! Pilih aksi untuk menyerang!");
            setButtonsEnabled(true);
            updateStatus();
            
            // Fade in battle screen
            FadeTransition fadeInBattle = new FadeTransition(Duration.millis(500), battlePane);
            fadeInBattle.setFromValue(0);
            fadeInBattle.setToValue(1);
            fadeInBattle.play();
        });
        
        fadeOut.play();
    }
    
    private void executePlayerTurn(TurnAction action) {
        if (musuh == null) {
            showAlert("Pilih musuh dan mulai pertarungan terlebih dahulu.");
            return;
        }
        
        try {
            int previousEnemyHp = musuh.getDarah();
            int previousPlayerHp = player.getDarah();
            
            switch(action) {
                case ATTACK:
                    player.serangMusuh(musuh);
                    int damage = previousEnemyHp - musuh.getDarah();
                    showDamageAnimation(enemyCharacterContainer, damage, false);
                    appendLog("⚔️ " + playerName + " menyerang! Damage: " + damage);
                    break;
                case HEAL:
                    player.penyembuhan();
                    int healAmount = player.getDarah() - previousPlayerHp;
                    showHealAnimation(playerCharacterContainer, healAmount);
                    appendLog("💚 " + playerName + " menggunakan penyembuhan! +" + healAmount + " HP");
                    break;
                case ULTIMATE:
                    player.seranganBurst(musuh);
                    int ultDamage = previousEnemyHp - musuh.getDarah();
                    showDamageAnimation(enemyCharacterContainer, ultDamage, true);
                    appendLog("💥 " + playerName + " melancarkan ULTIMATE ATTACK! Damage: " + ultDamage);
                    break;
            }
            
            updateStatus();
            
            if (musuh.getDarah() <= 0) {
                appendLog("✅ VICTORY! " + musuh.getNama() + " telah dikalahkan!");
                setButtonsEnabled(false);
                PauseTransition victoryDelay = new PauseTransition(Duration.millis(1000));
                victoryDelay.setOnFinished(e -> showVictoryScreen());
                victoryDelay.play();
                return;
            }
            
            // Enemy turn - simple random action
            PauseTransition enemyDelay = new PauseTransition(Duration.millis(800));
            enemyDelay.setOnFinished(evt -> {
                if (musuh == null || musuh.isMati() || player == null || player.getDarah() <= 0) {
                    return;
                }
                int playerPrevHp = player.getDarah();
                java.util.Random rand = new java.util.Random();
                if (rand.nextBoolean()) {
                    musuh.serangan(player);
                    int enemyDamage = playerPrevHp - player.getDarah();
                    showDamageAnimation(playerCharacterContainer, enemyDamage, false);
                    appendLog("🔥 " + musuh.getNama() + " menyerang balik! Damage: " + enemyDamage);
                    
                    updateStatus();
                    
                    if (player.getDarah() <= 0) {
                        appendLog("❌ DEFEAT! " + playerName + " telah dikalahkan!");
                        setButtonsEnabled(false);
                        PauseTransition defeatDelay = new PauseTransition(Duration.millis(1000));
                        defeatDelay.setOnFinished(e -> showDefeatScreen());
                        defeatDelay.play();
                    }
                } else {
                    appendLog("💨 " + musuh.getNama() + " mencoba menghindar dari serangan!");
                }
            });
            enemyDelay.play();
            
        } catch (Exception ex) {
            showAlert("Error: " + ex.getMessage());
        }
    }
    
    private void updateStatus() {
        if (player != null && playerHpLabel != null) {
            playerHpLabel.setText("HP: " + player.getDarah() + "/150");
            playerHpBar.setProgress((double) player.getDarah() / PLAYER_MAX_HP);
        }
        
        if (musuh != null && enemyHpLabel != null) {
            enemyHpLabel.setText("HP: " + musuh.getDarah() + "/" + musuhMaxHp);
            enemyHpBar.setProgress((double) musuh.getDarah() / musuhMaxHp);
        }
    }
    
    private void appendLog(String message) {
        if (logArea != null) {
            logArea.setText(message);
        }
    }
    
    private void setButtonsEnabled(boolean enabled) {
        attackButton.setDisable(!enabled);
        healButton.setDisable(!enabled);
        ultimateButton.setDisable(!enabled);
    }
    
    private void showDamageAnimation(StackPane targetPane, int damage, boolean isCritical) {
        Label damageLabel = new Label("-" + damage);
        damageLabel.setFont(Font.font("Arial", FontWeight.BOLD, isCritical ? 40 : 30));
        damageLabel.setTextFill(isCritical ? Color.web("#ff3333") : Color.web("#ff6666"));
        damageLabel.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0.5, 0, 0);");
        damageLabel.setOpacity(0);
        
        // Position slightly above center
        StackPane.setAlignment(damageLabel, Pos.CENTER);
        StackPane.setMargin(damageLabel, new Insets(-50, 0, 0, 0));
        
        targetPane.getChildren().add(damageLabel);
        
        // Fade in and move up animation
        TranslateTransition moveUp = new TranslateTransition(Duration.millis(800), damageLabel);
        moveUp.setByY(-60);
        
        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), damageLabel);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        
        FadeTransition fadeOut = new FadeTransition(Duration.millis(400), damageLabel);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setDelay(Duration.millis(400));
        
        // Shake animation for character
        Timeline shake = new Timeline(
            new KeyFrame(Duration.millis(0), new KeyValue(targetPane.translateXProperty(), 0)),
            new KeyFrame(Duration.millis(50), new KeyValue(targetPane.translateXProperty(), -10)),
            new KeyFrame(Duration.millis(100), new KeyValue(targetPane.translateXProperty(), 10)),
            new KeyFrame(Duration.millis(150), new KeyValue(targetPane.translateXProperty(), -8)),
            new KeyFrame(Duration.millis(200), new KeyValue(targetPane.translateXProperty(), 8)),
            new KeyFrame(Duration.millis(250), new KeyValue(targetPane.translateXProperty(), 0))
        );
        
        fadeIn.play();
        moveUp.play();
        shake.play();
        fadeOut.play();
        
        fadeOut.setOnFinished(e -> targetPane.getChildren().remove(damageLabel));
    }
    
    private void showHealAnimation(StackPane targetPane, int healAmount) {
        Label healLabel = new Label("+" + healAmount);
        healLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        healLabel.setTextFill(Color.web("#33ff66"));
        healLabel.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0.5, 0, 0);");
        healLabel.setOpacity(0);
        
        StackPane.setAlignment(healLabel, Pos.CENTER);
        StackPane.setMargin(healLabel, new Insets(-50, 0, 0, 0));
        
        targetPane.getChildren().add(healLabel);
        
        TranslateTransition moveUp = new TranslateTransition(Duration.millis(800), healLabel);
        moveUp.setByY(-60);
        
        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), healLabel);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        
        FadeTransition fadeOut = new FadeTransition(Duration.millis(400), healLabel);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setDelay(Duration.millis(400));
        
        fadeIn.play();
        moveUp.play();
        fadeOut.play();
        
        fadeOut.setOnFinished(e -> targetPane.getChildren().remove(healLabel));
    }
    
    private void showVictoryScreen() {
        StackPane victoryPane = new StackPane();
        
        // Gradient background - pastel sky colors
        Stop[] bgStops = new Stop[] {
            new Stop(0, Color.web("#a8c0ff")),
            new Stop(0.5, Color.web("#c4b5fd")),
            new Stop(1, Color.web("#fbc2eb"))
        };
        LinearGradient bgGradient = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, bgStops);
        
        Rectangle background = new Rectangle(1000, 650);
        background.setFill(bgGradient);
        
        VBox content = new VBox(40);
        content.setAlignment(Pos.CENTER);
        
        // YOU WIN text
        Label winText = new Label("YOU WIN!!!");
        winText.setFont(Font.font("Arial", FontWeight.BOLD, 70));
        winText.setTextFill(Color.WHITE);
        
        // Trophy sederhana
        Label trophy = new Label("🏆");
        trophy.setFont(Font.font(100));
        
        // Buttons
        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);
        
        Button playAgainBtn = new Button("MAIN LAGI");
        playAgainBtn.setStyle("-fx-font-size: 16; -fx-padding: 15 30; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8;");
        playAgainBtn.setOnAction(e -> {
            mainContainer.getChildren().remove(victoryPane);
            resetGame();
        });
        
        Button exitBtn = new Button("KELUAR");
        exitBtn.setStyle("-fx-font-size: 16; -fx-padding: 15 30; -fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8;");
        exitBtn.setOnAction(e -> primaryStage.close());
        
        buttons.getChildren().addAll(playAgainBtn, exitBtn);
        
        content.getChildren().addAll(winText, trophy, buttons);
        victoryPane.getChildren().addAll(background, content);
        
        // Add to container
        mainContainer.getChildren().add(victoryPane);
        
        // Animations
        content.setOpacity(0);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(800), content);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        
        ScaleTransition scaleWin = new ScaleTransition(Duration.millis(600), winText);
        scaleWin.setFromX(0);
        scaleWin.setFromY(0);
        scaleWin.setToX(1);
        scaleWin.setToY(1);
        
        // Bounce animation for trophy
        TranslateTransition bounceTrophy = new TranslateTransition(Duration.millis(500), trophy);
        bounceTrophy.setFromY(0);
        bounceTrophy.setToY(-30);
        bounceTrophy.setCycleCount(Animation.INDEFINITE);
        bounceTrophy.setAutoReverse(true);
        bounceTrophy.setInterpolator(javafx.animation.Interpolator.EASE_BOTH);
        
        fadeIn.play();
        scaleWin.play();
        bounceTrophy.play();
    }
    
    private void showDefeatScreen() {
        StackPane defeatPane = new StackPane();
        
        // Dark gradient background
        Stop[] bgStops = new Stop[] {
            new Stop(0, Color.web("#1a0000")),
            new Stop(0.5, Color.web("#330000")),
            new Stop(1, Color.web("#1a0000"))
        };
        LinearGradient bgGradient = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, bgStops);
        
        Rectangle background = new Rectangle(1000, 650);
        background.setFill(bgGradient);
        
        VBox content = new VBox(40);
        content.setAlignment(Pos.CENTER);
        
        // YOU LOSE text
        Label loseText = new Label("YOU LOSE...");
        loseText.setFont(Font.font("Arial", FontWeight.BOLD, 70));
        loseText.setTextFill(Color.web("#ff6b6b"));
        
        // Skull
        VBox skull = new VBox(5);
        skull.setAlignment(Pos.CENTER);
        
        Circle head = new Circle(50, Color.web("#e0e0e0"));
        
        HBox eyes = new HBox(20);
        eyes.setAlignment(Pos.CENTER);
        Polygon leftEye = new Polygon(0, 0, 15, 0, 7.5, 20);
        leftEye.setFill(Color.BLACK);
        Polygon rightEye = new Polygon(0, 0, 15, 0, 7.5, 20);
        rightEye.setFill(Color.BLACK);
        eyes.getChildren().addAll(leftEye, rightEye);
        
        Rectangle jaw = new Rectangle(60, 30);
        jaw.setFill(Color.web("#e0e0e0"));
        jaw.setArcWidth(15);
        jaw.setArcHeight(15);
        
        StackPane skullStack = new StackPane();
        VBox skullParts = new VBox(-10);
        skullParts.setAlignment(Pos.CENTER);
        skullParts.getChildren().addAll(head, eyes, jaw);
        skullStack.getChildren().add(skullParts);
        
        // Buttons
        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);
        
        Button retryBtn = new Button("COBA LAGI");
        retryBtn.setStyle("-fx-font-size: 16; -fx-padding: 15 30; -fx-background-color: #ff6b6b; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8;");
        retryBtn.setOnAction(e -> {
            mainContainer.getChildren().remove(defeatPane);
            resetGame();
        });
        
        Button exitBtn = new Button("KELUAR");
        exitBtn.setStyle("-fx-font-size: 16; -fx-padding: 15 30; -fx-background-color: #666; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8;");
        exitBtn.setOnAction(e -> primaryStage.close());
        
        buttons.getChildren().addAll(retryBtn, exitBtn);
        
        content.getChildren().addAll(loseText, skullStack, buttons);
        defeatPane.getChildren().addAll(background, content);
        
        // Add to container
        mainContainer.getChildren().add(defeatPane);
        
        // Animations
        content.setOpacity(0);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(800), content);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        
        TranslateTransition shake = new TranslateTransition(Duration.millis(100), loseText);
        shake.setFromX(-5);
        shake.setToX(5);
        shake.setCycleCount(10);
        shake.setAutoReverse(true);
        
        fadeIn.play();
        fadeIn.setOnFinished(e -> shake.play());
    }
    
    private void resetGame() {
        // Reset to menu screen
        menuPane.setVisible(true);
        vsPane.setVisible(false);
        battlePane.setVisible(false);
        logArea.clear();
        player = null;
        musuh = null;
    }
    
    private void exitGame() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Game");
        alert.setHeaderText("Apakah Anda yakin ingin keluar?");
        alert.setContentText("Progress battle akan hilang.");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                primaryStage.close();
            }
        });
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
