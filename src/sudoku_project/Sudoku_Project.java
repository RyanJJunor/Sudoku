/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku_project;

import java.util.Random;

import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Title: Sudoku Project
 *
 * @author Ryan J Junor. Version: 1.0 Date: 19/04/2017
 */
public class Sudoku_Project extends Application {

    // creating the image for the window
    Image sudoku = new Image("Images/unnamed.png");

    @Override

    public void start(Stage primaryStage) {

        StackPane stackPane = new StackPane();

        Label play = new Label("Press above to play!");

        play.getStylesheets().add("/CSS/myDialogs.css");
        play.getStyleClass().add("watermark");

        //create Image
        ImageView titleImg = new ImageView(sudoku);

        titleImg.setPreserveRatio(true);
        titleImg.setFitHeight(145);

        //create a title label
        Label title = new Label("Sudoku", titleImg);
        title.setContentDisplay(ContentDisplay.BOTTOM);
        title.getStylesheets().add("/CSS/myDialogs.css");
        title.getStyleClass().add("label");
        StackPane.setMargin(title, new Insets(0, 0, 35, 0));

        //create event to move on
        title.setOnMouseClicked(e -> {

            difficultyWindow(primaryStage);
            primaryStage.close();

        });

        //add children
        stackPane.getChildren().add(play);
        stackPane.getChildren().add(title);

        //align the components
        StackPane.setAlignment(play, Pos.BOTTOM_CENTER);
        StackPane.setMargin(play, new Insets(0, 0, 40, 0));
        stackPane.setBackground(
                new Background(new BackgroundFill(Color.rgb(84, 154, 185), CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(stackPane, 500, 315);

        //Format the stage
        primaryStage.setTitle("Sudoku");
        primaryStage.getIcons().add(sudoku);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.setOpacity(1);


    }

    /**
     * Creates and formats the second window that shows up
     * @param primaryStage The stage that first shows up, used to pass on to the window after this one
     */
    private void difficultyWindow(Stage primaryStage) {

        Stage difficultyStage = new Stage();


        GridPane root = new GridPane();

        Button easyBtn = new Button("Easy");
        easyBtn.setPrefSize(102, 65);
        easyBtn.getStylesheets().add("/CSS/myDialogs.css");
        easyBtn.getStyleClass().add("button");

        Button medBtn = new Button("Medium");
        medBtn.setPrefSize(102, 65);
        medBtn.getStylesheets().add("/CSS/myDialogs.css");
        medBtn.getStyleClass().add("button");

        Button hardBtn = new Button("Hard");
        hardBtn.setPrefSize(102, 65);
        hardBtn.getStylesheets().add("/CSS/myDialogs.css");
        hardBtn.getStyleClass().add("button");

        Label label = new Label("Choose a difficulty");
        label.getStylesheets().add("/CSS/myDialogs.css");
        label.getStyleClass().add("difficulty");
        GridPane.setHalignment(label, HPos.CENTER);

        // Panes to create more of the grid
        Pane[] spring = new Pane[5];
        for (int count = 0; count < 5; count++) {

            spring[count] = new Pane();
            spring[count].setPrefSize(102, 65);

        }

        // Using panes to create more rows and columns in the grid
        root.add(spring[0], 1, 1);
        root.add(spring[1], 2, 2);
        root.add(spring[2], 3, 3);
        root.add(spring[3], 4, 4);
        root.add(spring[4], 5, 5);

        root.add(easyBtn, 2, 3);
        root.add(medBtn, 3, 3);
        root.add(hardBtn, 4, 3);

        root.add(label, 2, 1, 3, 2);

        root.setBackground(
                new Background(new BackgroundFill(Color.rgb(84, 154, 185), CornerRadii.EMPTY, Insets.EMPTY)));

        // root.setGridLinesVisible(true);

        Scene scene = new Scene(root, 500, 315);

        difficultyStage.setTitle("Sudoku");
        difficultyStage.getIcons().add(sudoku);
        difficultyStage.setScene(scene);
        difficultyStage.show();
        difficultyStage.setResizable(false);
        difficultyStage.setOpacity(1);
        spring[2].requestFocus();


        easyBtn.setOnMouseClicked(e -> {
            char difficulty = 'e';

            createSudokuWindow(difficulty, primaryStage);
            difficultyStage.close();

        });

        medBtn.setOnMouseClicked(e -> {
            char difficulty = 'm';

            createSudokuWindow(difficulty, primaryStage);
            difficultyStage.close();

        });

        hardBtn.setOnMouseClicked(e -> {
            char difficulty = 'h';

            createSudokuWindow(difficulty, primaryStage);
            difficultyStage.close();

        });

    }

    /**
     * Creates and formats the window that holds the actual sudoku
     * @param diff hold the users choice of difficulty
     * @param primaryStage holds the first window so it can be called if this one closes
     */
    private void createSudokuWindow(char diff, Stage primaryStage) {

        //To get a random number to choose the random puzzle
        Random rndm = new Random();

        int random = rndm.nextInt(3);

        char difficulty = diff;

        Stage puzzleStage = new Stage();
        // create the grid to fill with cells
        GridPane root = new GridPane();

        // Set the size of the individual cells
        final int CELL_MAX_SIZE = 35;

        // num is used to keep track of what cell is being generated
        int num = 0;

        // creating an array of 81 text fields for cells
        TextField[] cell = new TextField[81];

        // length is used to determine how many times to loop
        int length = cell.length;

        // loop which creates and formats each cell
        for (int index = 0; index < length; index++) {
            cell[index] = new TextField();
            cell[index].setEditable(false);
            cell[index].setPrefSize(CELL_MAX_SIZE, CELL_MAX_SIZE);
            cell[index].setFont(Font.font("Arial Rounded MT Bold", 18));
            cell[index].setAlignment(Pos.CENTER);

            // If there is a number in the text field, another cannot be
            // added

            cell[index].textProperty().addListener((observable, oldValue, newValue) -> {

                StringProperty textProperty = (StringProperty) observable;

                TextField textField = (TextField) textProperty.getBean();

                String message = newValue.substring(0, 1);

                textField.setText(message);

            });

        }

        // loop to add the cells in rows of 9
        for (int count = 1; count < 10; count++) {

            int cnt2 = num;

            // adds a row of 9 cells
            root.addRow(count, cell[num], cell[++num], cell[++num], cell[++num], cell[++num], cell[++num], cell[++num],
                    cell[++num], cell[++num]);
            num++;

            // if its the 3rd or 6th row of cells, add a margin to the bottom
            // for formatting
            if (count == 3 || count == 6) {

                // adding the margin to each cell on the row
                for (int cnt = 0; cnt < 9; cnt++) {

                    GridPane.setMargin(cell[cnt2 + cnt], new Insets(0, 0, 5, 0));

                }
            }

        }

        // loops through each cell and if it's the 3rd, 6th or 9th of the row
        // then adds a margin to the right.
        for (int cnt3 = 0; cnt3 < 81; cnt3++) {

            if (((cnt3 + 1) % 3) == 0) {

                GridPane.setMargin(cell[cnt3], new Insets(0, 5, 0, 0));
            }

            // but if it's the bottom right cell of the grid of three it has
            // margins on the bottom and right
            if ((cnt3 + 1 == 21) || (cnt3 + 1 == 24) || (cnt3 + 1 == 27) || (cnt3 + 1 == 48) || (cnt3 + 1 == 51)
                    || (cnt3 + 1 == 54)) {

                GridPane.setMargin(cell[cnt3], new Insets(0, 5, 5, 0));

            }

        }

        // Panes to create more of the grid
        Pane[] spring = new Pane[5];
        for (int count = 0; count < 5; count++) {

            spring[count] = new Pane();
            spring[count].setPrefSize(CELL_MAX_SIZE, CELL_MAX_SIZE);

        }
        spring[4].setPrefSize(CELL_MAX_SIZE - 5, CELL_MAX_SIZE - 5);

        // Using panes to create more rows and columns in the grid
        root.add(spring[0], 10, 1);
        root.add(spring[1], 11, 1);
        root.add(spring[2], 12, 1);
        root.add(spring[3], 13, 1);
        root.add(spring[4], 14, 1);

        // create reset button
        Button resetBtn = new Button("Reset");
        resetBtn.setPrefSize(105, 55);
        resetBtn.getStylesheets().add("/CSS/myDialogs.css");
        resetBtn.getStyleClass().add("buttons");
        resetBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                // creating the icon in the dialogPane
                Image icon = new Image("Images/sudoku_dialog.png");
                ImageView dialogImage = new ImageView(icon);

                Alert alert = new Alert(AlertType.CONFIRMATION);

                alert.setGraphic(dialogImage);

                alert.setTitle("Sudoku");
                alert.setHeaderText("Are you sure you would like to reset the puzzle?");
                alert.setContentText("All progress will be lost");

                DialogPane dialogPane = alert.getDialogPane();

                // adding css to the dialogPane
                dialogPane.getStylesheets().add("/CSS/myDialogs.css");
                dialogPane.getStyleClass().add("dialog-pane");
                alert.initOwner(puzzleStage);

                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(icon);

                alert.showAndWait();

                ButtonType btnType;

                btnType = alert.getResult();

                if (btnType == ButtonType.OK) {
                    Reset(cell);
                }


            }
        });

        // create check button
        Button checkBtn = new Button("Check");
        checkBtn.setPrefSize(105, 55);
        checkBtn.getStylesheets().add("/CSS/myDialogs.css");
        checkBtn.getStyleClass().add("buttons");
        checkBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                CheckResults(cell, puzzleStage, difficulty, random);

            }
        });

        Button instructionsBtn = new Button("Instructions");
        instructionsBtn.setPrefSize(105, 55);
        instructionsBtn.getStylesheets().add("/CSS/myDialogs.css");
        instructionsBtn.getStyleClass().add("instructionsButton");
        instructionsBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                instructions(puzzleStage);

            }
        });

        Label watermark = new Label();
        watermark.setText("Sudoku Project - Ryan J Junor");
        watermark.getStylesheets().add("/CSS/myDialogs.css");
        watermark.getStyleClass().add("watermark");
        watermark.setOpacity(.4);
        GridPane.setValignment(watermark, VPos.BOTTOM);
        GridPane.setHalignment(watermark, HPos.RIGHT);
        root.add(watermark, 11, 9, 4, 1);

        //root.setGridLinesVisible(true);

        // Add the buttons
        root.add(checkBtn, 11, 1, 3, 3);
        root.add(resetBtn, 11, 4, 3, 3);
        root.add(instructionsBtn, 11, 7, 3, 3);

        char[] display = choosePuzzle(difficulty, random);

        // inserts the puzzle in to the textFields
        populateGrid(cell, display);


        root.setBackground(
                new Background(new BackgroundFill(Color.rgb(84, 154, 185), CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(root, 500, 315);

        puzzleStage.setTitle("Sudoku");
        puzzleStage.getIcons().add(sudoku);
        puzzleStage.setScene(scene);
        puzzleStage.show();
        puzzleStage.setResizable(false);
        puzzleStage.centerOnScreen();
        puzzleStage.setOpacity(1);
        checkBtn.requestFocus();
        puzzleStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent e) {

                ButtonType btnType;
                btnType = close(puzzleStage);

                if (btnType == ButtonType.CANCEL) {
                    e.consume();
                } else if (btnType == ButtonType.OK) {

                    start(primaryStage);

                }
            }
        });

    }

    /**
     * Gets a puzzle from the specified difficulty
     * @param diff holds the users selected difficulty
     * @param random holds the random number used to determine the puzzle selected
     * @return the puzzle used to fill the grid with
     */
    private static char[] choosePuzzle(char diff, int random) {

        char difficulty = diff;

        char[] display = new char[81];

        switch (difficulty) {

            case 'e':
                display = displayEasy(random);
                break;

            case 'm':
                display = displayMed(random);
                break;

            case 'h':
                display = displayHard(random);
                break;

        }

        return display;

    }

    /** Checks the user's input to see if it matches the solution
     * @param cell the array holding the cells of the grid
     * @param puzzleStage holds the stage which is passed on to specify the parent window
     * @param diff holds the difficulty selected by the user
     * @param random holds the random number used to determine the puzzle selected
     */
    private void CheckResults(TextField[] cell, Stage puzzleStage, char diff, int random) {
        boolean correct = true;

        char difficulty = diff;

        if (difficulty == 'e') {

            String[] easyAnswer = easyAnswers(random);

            // comparing each textfield input to the answer
            for (int count = 0; count < 81; count++) {
                if (cell[count].getText().compareTo(easyAnswer[count]) != 0) {
                    correct = false;
                }
            }
        }

        if (difficulty == 'm') {

            String[] medAnswer = medAnswers(random);

            // comparing each textfield input to the answer
            for (int count = 0; count < 81; count++) {
                if (cell[count].getText().compareTo(medAnswer[count]) != 0) {
                    correct = false;
                }
            }
        }

        if (difficulty == 'h') {

            String[] hardAnswer = hardAnswers(random);

            // comparing each textfield input to the answer
            for (int count = 0; count < 81; count++) {
                if (cell[count].getText().compareTo(hardAnswer[count]) != 0) {
                    correct = false;
                }
            }
        }

        displayResult(puzzleStage, correct);

    }

    /** Clears the users input so far
     * @param cell the array holding the cells of the grid
     */
    private void Reset(TextField[] cell) {

        // resetting the puzzle
        for (int count = 0; count < 81; count++) {

            if (cell[count].isEditable()) {
                cell[count].setText(Character.toString('\u0000'));
            }

        }

    }

    /**
     * Displays instructions for Sudoku
     * @param puzzleStage The stage to use as a parent
     */
    private void instructions(Stage puzzleStage) {

        Image icon = new Image("Images/sudoku_dialog.png");
        ImageView dialogImage = new ImageView(icon);

        Alert instruct = new Alert(AlertType.INFORMATION);

        instruct.setGraphic(dialogImage);

        DialogPane dialogPane = instruct.getDialogPane();

        // adding css to the dialogPane
        dialogPane.getStylesheets().add("/CSS/myDialogs.css");
        dialogPane.getStyleClass().add("instructionsButtons");
        instruct.initOwner(puzzleStage);

        Stage stage = (Stage) instruct.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);

        instruct.setTitle("Sudoku");
        instruct.setHeaderText("Instructions!");
        instruct.setContentText("The objective of sudoku is to enter a digit from 1 through 9 in each cell, in such a way that:"
                + "\n\nEach horizontal row contains each digit exactly once."
                + "\n\nEach vertical column contains each digit exactly once."
                + "\n\nEach subgrid or region contains each digit exactly once.");

        instruct.show();
    }

    /**
     * Is called when the user closes the window
     * @param puzzleStage holds the current stage for the dialog box's parent
     * @return the Button type to decide whether the user wishes to close the window or not
     */
    private ButtonType close(Stage puzzleStage) {

        ButtonType btnType;

        Image icon = new Image("Images/sudoku_dialog.png");
        ImageView dialogImage = new ImageView(icon);

        Alert closeAlert = new Alert(AlertType.CONFIRMATION);

        closeAlert.setGraphic(dialogImage);

        closeAlert.setTitle("Exit");
        closeAlert.setHeaderText("Would you like to return to the main screen?");
        closeAlert.setContentText("You will lose all progress!");

        DialogPane closeDialogPane = closeAlert.getDialogPane();

        closeAlert.initOwner(puzzleStage);

        // adding css to the dialogPane
        closeDialogPane.getStylesheets().add("/CSS/myDialogs.css");
        closeDialogPane.getStyleClass().add("dialog-pane");

        Stage stage = (Stage) closeAlert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);

        closeAlert.showAndWait();
        btnType = closeAlert.getResult();

        return btnType;

    }

    /**Fills the grid with the puzzle
     * @param cell the array of textfields making up the grid
     * @param display the array holding the puzzle
     */
    private void populateGrid(TextField[] cell, char[] display) {

        for (int count = 0; count < 81; count++) {

            cell[count].setText(Character.toString(display[count]));

            if (display[count] == '\u0000') {

                cell[count].setEditable(true);
                cell[count].setFont(Font.font("Arial Rounded MT Bold", 18));
                cell[count].setStyle("-fx-text-inner-color: rgb(107, 176, 207)");
                cell[count].setAlignment(Pos.CENTER);

            }
        }

    }

    /**
     * Gets a random easy puzzle and converts it to chars so it is able to be displayed
     * @param random holds the random number which determines the puzzle
     * @return the puzzle to be displayed
     */
    private static char[] displayEasy(int random) {

        char[] display = new char[81];

        int[] easy = generateEasy(random);
        int index;

        for (index = 0; index < 81; index++) {

            display[index] = (char) (easy[index] + '0');

            // clearing the empty squares
            if (display[index] == '0') {
                display[index] = '\u0000';
            }

        }

        return display;
    }

    /**Gets a random medium puzzle and converts it to chars so it is able to be displayed
     * @param random holds the random number which determines the puzzle
     * @return the puzzle to be displayed
     */
    private static char[] displayMed(int random) {

        char[] display = new char[81];

        int[] med = generateMed(random);
        int index;

        for (index = 0; index < 81; index++) {

            display[index] = (char) (med[index] + '0');

            // clearing the empty squares
            if (display[index] == '0') {
                display[index] = '\u0000';
            }

        }

        return display;
    }

    /**Gets a random hard puzzle and converts it to chars so it is able to be displayed
     * @param random random holds the random number which determines the puzzle
     * @return the puzzle to be displayed
     */
    private static char[] displayHard(int random) {

        char[] display = new char[81];

        int[] hard = generateHard(random);
        int index;

        for (index = 0; index < 81; index++) {

            display[index] = (char) (hard[index] + '0');

            // clearing the empty squares
            if (display[index] == '0') {
                display[index] = '\u0000';
            }

        }

        return display;
    }

	/** Uses the random number to determine which easy puzzle to choose
	 * @param random holds the random number which determines the puzzle to choose
	 * @return the chosen puzzle
	 */
	private static int[] generateEasy(int random) {

		int[][] easy = new int[3][81];

		int[] easy0 = {0,9,3, 4,7,6, 0,0,1,
				 	   4,1,0, 3,0,0, 6,0,0,
				 	   8,0,6, 1,0,0, 4,0,0,

				 	   0,0,5, 2,1,4, 9,0,0,
				 	   0,4,2, 0,3,0, 0,1,0,
				 	   0,0,1, 0,0,0, 2,0,0,

				 	   2,3,4, 6,0,8, 1,0,0,
				 	   0,0,0, 0,4,0, 0,0,2,
				 	   0,5,0, 0,2,0, 0,0,0};

		int[] easy1 = {0,0,7, 9,3,0, 1,4,0,
					   0,0,9, 0,0,8, 3,0,0,
					   3,0,4, 0,0,0, 0,8,0,

					   0,1,6, 5,8,0, 0,0,3,
					   0,0,5, 0,1,0, 8,0,0,
					   4,0,8, 0,9,6, 7,5,0,

					   0,7,1, 8,0,0, 0,0,4,
					   0,0,3, 4,0,0, 2,0,0,
					   0,4,2, 1,7,9, 5,0,0};

		int[] easy2 = {1,5,0, 0,6,0, 0,9,7,
					   8,0,0, 9,2,1, 5,3,0,
					   9,2,4, 0,3,7, 0,8,0,

					   4,0,0, 0,8,0, 0,0,5,
					   3,7,8, 4,1,5, 6,0,9,
					   6,0,5, 2,7,0, 0,0,8,

					   0,8,0, 0,5,6, 7,4,3,
					   7,0,0, 3,9,2, 8,5,1,
					   0,0,0, 0,4,0, 0,6,2};

		easy[0] = easy0;
		easy[1] = easy1;
		easy[2] = easy2;

		return easy[random];

	}

	/** Uses the random number to determine which medium puzzle to choose
	 * @param random holds the random number which determines the puzzle to choose
	 * @return the chosen puzzle
	 */
	private static int[] generateMed(int random) {

		int[][] medium = new int[3][81];

		int[] med0 = {0,7,3, 0,5,0, 0,0,0,
				 	  5,0,0, 0,0,0, 8,7,0,
				 	  0,6,0, 0,7,0, 0,0,5,

				 	  0,0,4, 1,0,0, 0,0,7,
				 	  0,5,0, 9,4,6, 0,1,0,
				 	  3,0,0, 0,0,7, 6,0,0,

				 	  2,0,0, 0,8,0, 0,4,0,
				 	  0,0,5, 0,0,0, 0,0,0,
				 	  0,0,0, 0,9,0, 7,8,0};

		int[] med1 = {0,0,0, 0,0,2, 0,0,0,
					  0,8,0, 0,7,0, 0,3,0,
					  9,0,6, 0,0,0, 8,0,5,

					  0,0,8, 0,0,0, 0,0,2,
					  0,4,0, 0,1,0, 0,7,0,
					  5,0,0, 0,0,0, 9,0,0,

					  6,0,2, 0,0,0, 4,0,9,
					  0,3,0, 0,8,0, 0,0,0,
					  0,0,0, 4,0,0, 1,0,0};

		int[] med2 = {0,0,4, 0,6,0, 0,3,0,
					  0,0,1, 0,3,8, 4,5,0,
					  0,0,0, 0,0,2, 0,0,7,

					  2,0,0, 7,0,5, 0,1,0,
					  0,0,0, 0,9,0, 0,0,0,
					  0,3,0, 1,0,6, 0,0,9,

					  9,0,0, 3,0,0, 0,0,0,
					  0,8,7, 6,5,0, 2,0,0,
					  0,4,0, 0,7,0, 5,0,0};

		medium[0] = med0;
		medium[1] = med1;
		medium[2] = med2;

		return medium[random];
	}

	/** Uses the random number to determine which hard puzzle to choose
	 * @param random holds the random number which determines the puzzle to choose
	 * @return the chosen puzzle
	 */
	private static int[] generateHard(int random) {

		int[][] hard = new int[3][81];

		int[] hard0 = {0,0,0, 0,0,0, 0,0,4,
					   4,0,2, 8,7,0, 0,0,0,
					   0,5,0, 0,0,6, 0,2,0,

					   8,0,5, 0,0,0, 0,0,2,
					   0,9,0, 4,0,7, 0,8,0,
					   1,0,0, 0,0,0, 4,0,6,

					   0,2,0, 1,0,0, 0,7,0,
					   0,0,0, 0,8,9, 3,0,5,
					   6,0,0, 0,0,0, 0,0,0};

		int[] hard1 = {0,0,0, 0,7,9, 1,0,0,
					   0,3,0, 2,0,0, 6,0,5,
					   0,4,0, 0,0,0, 0,7,0,

					   0,0,0, 0,6,0, 0,5,9,
					   0,0,5, 0,0,0, 8,0,0,
					   6,8,0, 0,9,0, 0,0,0,

					   0,7,0, 0,0,0, 0,3,0,
					   5,0,1, 0,0,7, 0,6,0,
					   3,0,2, 5,4,0, 0,0,0};

		int[] hard2 = {0,0,0, 0,0,1, 0,0,0,
					   7,0,6, 5,8,0, 0,0,4,
					   0,2,0, 0,0,6, 0,3,0,

					   0,3,0, 0,4,0, 0,5,0,
					   4,0,2, 0,0,0, 7,0,3,
					   0,1,0, 0,3,0, 0,0,0,

					   0,8,0, 1,0,0, 0,9,0,
					   1,0,0, 0,2,0, 6,0,5,
					   0,0,0, 7,0,0, 0,0,0};


		hard[0] = hard0;
		hard[1] = hard1;
		hard[2] = hard2;

		return hard[random];

	}

	/**Uses the random number to select the appropriate easy solution
	 * @param random holds the random number which determines the solution to choose
	 * @return the appropriate solution to compare the users answers with
	 */
	private String[] easyAnswers(int random) {

		String[][] easyAnswers = new String[3][81];

		String[] answerEasy0 = {"5","9","3", "4","7","6", "8","2","1",
				 				"4","1","7", "3","8","2", "6","5","9",
				 				"8","2","6", "1","9","5", "4","7","3",

				 				"3","7","5", "2","1","4", "9","6","8",
				 				"6","4","2", "8","3","9", "7","1","5",
				 				"9","8","1", "5","6","7", "2","3","4",

				 				"2","3","4", "6","5","8", "1","9","7",
				 				"1","6","9", "7","4","3", "5","8","2",
				 				"7","5","8", "9","2","1", "3","4","6"};

		String[] answerEasy1 = {"8","6","7", "9","3","2", "1","4","5",
 								"1","5","9", "6","4","8", "3","7","2",
 								"3","2","4", "7","5","1", "6","8","9",

 								"2","1","6", "5","8","7", "4","9","3",
 								"7","9","5", "3","1","4", "8","2","6",
 								"4","3","8", "2","9","6", "7","5","1",

 								"5","7","1", "8","2","3", "9","6","4",
 								"9","8","3", "4","6","5", "2","1","7",
 								"6","4","2", "1","7","9", "5","3","8"};

		String[] answerEasy2 = {"1","5","3", "8","6","4", "2","9","7",
								"8","6","7", "9","2","1", "5","3","4",
								"9","2","4", "5","3","7", "1","8","6",

								"4","1","2", "6","8","9", "3","7","5",
								"3","7","8", "4","1","5", "6","2","9",
								"6","9","5", "2","7","3", "4","1","8",

								"2","8","9", "1","5","6", "7","4","3",
								"7","4","6", "3","9","2", "8","5","1",
								"5","3","1", "7","4","8", "9","6","2"};



		easyAnswers[0] = answerEasy0;
		easyAnswers[1] = answerEasy1;
		easyAnswers[2] = answerEasy2;

		return easyAnswers[random];
	}

	/**Uses the random number to select the appropriate medium solution
	 * @param random holds the random number which determines the solution to choose
	 * @return the appropriate solution to compare the users answers with
	 */
	private String[] medAnswers(int random) {

		String[][] medAnswers = new String[3][81];

		String[] answerMed0 = {"4","7","3", "2","5","8", "1","6","9",
				 			   "5","2","1", "4","6","9", "8","7","3",
				 			   "9","6","8", "3","7","1", "4","2","5",

				 			   "6","8","4", "1","3","5", "2","9","7",
				 			   "7","5","2", "9","4","6", "3","1","8",
				 			   "3","1","9", "8","2","7", "6","5","4",

				 			   "2","9","7", "6","8","3", "5","4","1",
				 			   "8","4","5", "7","1","2", "9","3","6",
				 			   "1","3","6", "5","9","4", "7","8","2"};

		String[] answerMed1 = {"1","5","3", "8","6","2", "7","9","4",
 							   "2","8","4", "9","7","5", "6","3","1",
 							   "9","7","6", "1","3","4", "8","2","5",

 							   "7","6","8", "5","9","3", "1","4","2",
 							   "3","4","9", "2","1","6", "5","7","8",
 							   "5","2","1", "7","4","8", "9","6","3",

 							   "6","1","2", "3","5","7", "4","8","9",
 							   "4","3","5", "6","8","9", "2","1","7",
 							   "8","9","7", "4","2","1", "3","5","6"};

		String[] answerMed2 = {"8","2","4", "5","6","7", "9","3","1",
							   "6","7","1", "9","3","8", "4","5","2",
							   "5","9","3", "4","1","2", "6","8","7",

							   "2","6","9", "7","8","5", "3","1","4",
							   "4","1","8", "2","9","3", "7","6","5",
							   "7","3","5", "1","4","6", "8","2","9",

							   "9","5","6", "3","2","4", "1","7","8",
							   "1","8","7", "6","5","9", "2","4","3",
							   "3","4","2", "8","7","1", "5","9","6"};



		medAnswers[0] = answerMed0;
		medAnswers[1] = answerMed1;
		medAnswers[2] = answerMed2;

		return medAnswers[random];
	}

	/**Uses the random number to select the appropriate hard solution
	 * @param random holds the random number which determines the solution to choose
	 * @return the appropriate solution to compare the users answers with
	 */
	private String[] hardAnswers(int random) {

		String[][] hardAnswers = new String[3][81];

		String[] answerHard0 = {"3","7","8", "5","9","2", "6","1","4",
				 				"4","6","2", "8","7","1", "5","3","9",
				 				"9","5","1", "3","4","6", "8","2","7",

				 				"8","4","5", "6","1","3", "7","9","2",
				 				"2","9","6", "4","5","7", "1","8","3",
				 				"1","3","7", "9","2","8", "4","5","6",

				 				"5","2","3", "1","6","4", "9","7","8",
				 				"7","1","4", "2","8","9", "3","6","5",
				 				"6","8","9", "7","3","5", "2","4","1"};

		String[] answerHard1 = {"2","5","6", "4","7","9", "1","8","3",
 								"7","3","9", "2","1","8", "6","4","5",
 								"1","4","8", "6","5","3", "9","7","2",

 								"4","1","7", "8","6","2", "3","5","9",
 								"9","2","5", "7","3","4", "8","1","6",
 								"6","8","3", "1","9","5", "4","2","7",

 								"8","7","4", "9","2","6", "5","3","1",
 								"5","9","1", "3","8","7", "2","6","4",
 								"3","6","2", "5","4","1", "7","9","8"};

		String[] answerHard2 = {"3","4","8", "2","9","1", "5","7","6",
								"7","9","6", "5","8","3", "1","2","4",
								"5","2","1", "4","7","6", "8","3","9",

								"8","3","7", "6","4","2", "9","5","1",
								"4","6","2", "9","1","5", "7","8","3",
								"9","1","5", "8","3","7", "4","6","2",

								"6","8","3", "1","5","4", "2","9","7",
								"1","7","9", "3","2","8", "6","4","5",
								"2","5","4", "7","6","9", "3","1","8"};



		hardAnswers[0] = answerHard0;
		hardAnswers[1] = answerHard1;
		hardAnswers[2] = answerHard2;

		return hardAnswers[random];
	}

	/**Displays a dialog box stating if the users answer is correct or not
	 * @param puzzleStage stage used as the owner for the dialog boxes
	 * @param correct the variable used to determine if the users input matches the answers
	 */
    private void displayResult(Stage puzzleStage, boolean correct) {

        // creating the icon in the dialogPane
        Image icon = new Image("Images/sudoku_dialog.png");
        ImageView dialogImage = new ImageView(icon);

        Alert alert = new Alert(AlertType.INFORMATION);

        alert.setGraphic(dialogImage);

        DialogPane dialogPane = alert.getDialogPane();

        // adding css to the dialogPane
        dialogPane.getStylesheets().add("/CSS/myDialogs.css");
        dialogPane.getStyleClass().add("dialog-pane");
        alert.initOwner(puzzleStage);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);

        // selecting the dialogPane to show
        if (correct == true) {

            alert.setTitle("Sudoku");
            alert.setHeaderText("Congratulations!");
            alert.setContentText("You solved it!");

            alert.show();
        } else {

            alert.setTitle("Sudoku");
            alert.setHeaderText("Not Yet!");
            alert.setContentText("You haven't solved it yet!");

            alert.show();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        launch(args);

    }

}
