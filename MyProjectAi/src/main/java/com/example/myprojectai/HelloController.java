package com.example.myprojectai;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.util.Duration;
import javafx.util.StringConverter;
import java.net.URL;
import java.util.*;

public class HelloController   implements Initializable {

    @FXML
    private Button Step;


    @FXML
    private Button Rest;


    @FXML
    int counter = 0;


    List<Integer[]> hillStep = new ArrayList<>();
    List<Integer[]> solutionItreation = new ArrayList<>();

    List<Integer[]> simulatedStep = new ArrayList<>();
    private static final int MAX_ITERATIONS = 1000;
    static double tempreture = 0;
    Label nQueenLabel = new Label("N-QUEEN");
    static double curr_T =0.0;
    @FXML
    private Button simulated;
    static int n1 = 0;
    @FXML
    private ChoiceBox<String> choicebox;
    private String[] q = {"4", "5", "6", "7", "8"};
    @FXML
    private Button hill;
    static boolean flagHill = false;
    static boolean flagSimulated = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        choicebox.getItems().addAll(q);
        choicebox.setConverter(new StringConverter<String>() {
            @Override
            public String toString(String s) {
                return (s == null) ? "Select Size" : s;
            }

            @Override
            public String fromString(String s) {
                return null;
            }
        });
        choicebox.setOnAction(this::getSelctedChoiceBox);
    }

    public void getSelctedChoiceBox(ActionEvent event) {
        String u = choicebox.getValue();
        if (u != null) {
            if (u.equals("4"))
                n1 = 4;
            else if (u.equals("5"))
                n1 = 5;
            else if (u.equals("6"))
                n1 = 6;
            else if (u.equals("7"))
                n1 = 7;
            else if (u.equals("8"))
                n1 = 8;


            hill.setOnAction(e -> {
                String S = choicebox.getSelectionModel().getSelectedItem();
                if (S != null) {
                    flagHill = true;
                    if (n1 > 0) {
                        open_W(n1, true);
                    }
                }
            });

            simulated.setOnAction(e -> {
                String S = choicebox.getSelectionModel().getSelectedItem();
                if (S != null) {
                    flagSimulated = true;
                    if (n1 > 0) {
                        open_W(n1, true);
                    }
                }
            });
            ;

        }

    }




    private void open_W(int element, boolean ss) {

        nQueenLabel.setStyle("-fx-font-size: 50px; -fx-font-weight: bold; -fx-text-fill: #74254d;-fx-font-family: \"Arial\";");

        List<Integer[]> finalSol;
        VBox vbox = new VBox(nQueenLabel);
        vbox.setSpacing(10);
        vbox.setFillWidth(false);
        vbox.setMaxWidth(200);


        animateText();

        Label text = new Label();

        Step = new Button("STEP");
        Rest = new Button("REST");

        Stage NQueenChess = new Stage();
        NQueenChess.setHeight(600);
        NQueenChess.setWidth(600);

        AnchorPane Anc = new AnchorPane();

        NQueenChess.setTitle("N-QUEEN");


        GridPane NQueenGrid = new GridPane();
        NQueenGrid.setPadding(new Insets(10));



        if (flagHill) {
            finalSol = solveHill(n1);
            solutionItreation = hillStep;

            if (finalSol.isEmpty()) {
                System.out.println("No solution found.");
                return;
            }
        } else if (flagSimulated) {
            finalSol = solveSimulated(n1);
            solutionItreation = simulatedStep;

            if (finalSol.isEmpty()) {
                System.out.println("No solution found.");
                return;
            }

        } else {
            finalSol = null;
        }


        for (Integer [] solution : finalSol) {
            for (int row = 0; row < element; row++) {
                for (int col = 0; col < element; col++) {
                    Button cell = new Button();
                    cell.setMinSize(50, 50);
                    cell.setMaxSize(50, 50);


                    Image img = new Image("C:\\Users\\hp\\IdeaProjects\\MyProjectAi\\src\\main\\resources\\com\\example\\myprojectai\\AA.png");


                    ImageView cellA = new ImageView();
                    cellA.setFitWidth(50);
                    cellA.setFitHeight(50);
                    cell.setStyle((row + col) % 2 == 0 ? "-fx-background-color: white;" : "-fx-background-color:#74254d");


                    if (solution[col] == row) {
                        cellA.setImage(img);
                    }
                    NQueenGrid.add(cell, col, row);

                    NQueenGrid.add(cellA, col, row);


                    Rest.setOnAction(e -> {
                        for (Integer [] so : solutionItreation) {
                            for (int ro = 0; ro < element; ro++) {
                                for (int co = 0; co < element; co++) {
                                    Image I = new Image("C:\\Users\\hp\\IdeaProjects\\MyProjectAi\\src\\main\\resources\\com\\example\\myprojectai\\AA.png");

                                    ImageView cellB = new ImageView();
                                    cellB.setFitWidth(50);
                                    cellB.setFitHeight(50);
                                    Button cellC = new Button();
                                    cellC.setMinSize(50, 50);
                                    cellC.setMaxSize(50, 50);

                                    cellC.setStyle((ro + co) % 2 == 0 ? "-fx-background-color: white;" : "-fx-background-color:#74254d");

                                    if (so[co] == ro) {
                                        cellB.setImage(I);
                                    }
                                    NQueenGrid.add(cellC, co, ro);
                                    NQueenGrid.add(cellB, co, ro);

                                }
                            }
                            break;

                        }
                            }
                    );


                    Step.setOnAction(e ->{


                        for (int i = counter; i< solutionItreation.size(); i++) {
                            if(flagSimulated)
                                text.setText("Temp :"+ curr_T);
                            counter+=1;
                            Integer [] solA = solutionItreation.get(i);

                            for (int ro = 0; ro < element; ro++) {
                                for (int co = 0; co < element; co++) {
                                    Image I = new Image("C:\\Users\\hp\\IdeaProjects\\MyProjectAi\\src\\main\\resources\\com\\example\\myprojectai\\AA.png");

                                    ImageView cellB = new ImageView();
                                    cellB.setFitWidth(50);
                                    cellB.setFitHeight(50);
                                    Button cellC = new Button();
                                    cellC.setMinSize(50, 50);
                                    cellC.setMaxSize(50, 50);

                                    cellC.setStyle((ro + co) % 2 == 0 ? "-fx-background-color: white;" : "-fx-background-color:#74254d");

                                    if (solA[co] == ro) {

                                        cellB.setImage(I);
                                    }
                                    NQueenGrid.add(cellC, co, ro);
                                    NQueenGrid.add(cellB, co, ro);

                                }
                            }


                            break;

                        }

                    });



                }
            }


            NQueenGrid.add(new Button(), n1, 0);
        }
        text.setStyle("-fx-background-color: #74254d;" +
                "-fx-text-fill: white;");

        Step.setStyle("-fx-background-color: #74254d;" +
                "-fx-text-fill: white;");
        Rest.setStyle("-fx-background-color: #74254d;" +
                "-fx-text-fill: white;");

        Anc.setStyle("-fx-background-color: #ffe6f9;");
        if (flagSimulated) {
          //  text.setText("Temp : " + tempreture);
            text.setVisible(true);
        }
        if(flagHill){
            text.setVisible(false);
        }
        nQueenLabel.setAlignment(Pos.TOP_CENTER);
        Anc.getChildren().addAll(nQueenLabel, Rest, Step, text,NQueenGrid);
        Scene chessScene = new Scene(Anc);


        chessScene.widthProperty().addListener((observable, oldValue, newValue) -> centerChessBoard(Anc, NQueenGrid));
        chessScene.heightProperty().addListener((observable, oldValue, newValue) -> centerChessBoard(Anc, NQueenGrid));


        chessScene.widthProperty().addListener((observable, oldValue, newValue) -> centerButtonInCenterLeft(Anc, Step, 0));
        chessScene.heightProperty().addListener((observable, oldValue, newValue) -> centerButtonInCenterLeft(Anc, Step, 0));

        chessScene.widthProperty().addListener((observable, oldValue, newValue) -> centerButtonInCenterLeft(Anc, Rest, 40));
        chessScene.heightProperty().addListener((observable, oldValue, newValue) -> centerButtonInCenterLeft(Anc, Rest, 40));

        chessScene.widthProperty().addListener((observable, oldValue, newValue) -> centerLabelInCenterLeft(Anc, text, 80));
        chessScene.heightProperty().addListener((observable, oldValue, newValue) -> centerLabelInCenterLeft(Anc, text, 80));

        chessScene.widthProperty().addListener((observable, oldValue, newValue) -> centerLabelInCenterTop(Anc, nQueenLabel, 0));
        chessScene.heightProperty().addListener((observable, oldValue, newValue) -> centerLabelInCenterTop(Anc, nQueenLabel, 0));

        NQueenChess.setScene(chessScene);
        NQueenChess.show();


    }


    //// Hill Climbing

    private List<Integer[]> solveHill(int size) {
        List<Integer[]> allSol = new ArrayList<>();

        int steps = 0;

        while (allSol.size() < size) {
            Integer[] current = initialRandom(size);
            boolean hasNeig;

            do {
                hasNeig = false;
                List<Integer[]> neig = getneig(current);

                int cValue = calcHue(current);

                for (Integer[] neighbor : neig) {
                    int nValue = calcHue(neighbor);
                    steps++; // Increment the steps counter for each neighbor checked

                    if (nValue < cValue) {
                        current = neighbor;
                        hasNeig = true;
                        break;
                    }
                }
                hillStep.add(current);
            } while (hasNeig && attackNQueen(current));

            if (!attackNQueen(current)) {
                allSol.add(current);
            }
        }


        return allSol;
    }




    private boolean attackNQueen(Integer[] solution) {
        for (int col = 0; col < solution.length; col++) {
            for (int nextCol = col + 1; nextCol < solution.length; nextCol++) {
                int row = solution[col];
                int nextRow = solution[nextCol];

                if (row == nextRow || Math.abs(row - nextRow) == Math.abs(col - nextCol)) {
                    return true;
                }
            }
        }

        return false;
    }
    private Integer[] initialRandom(int size) {
        Integer[] initailS = new Integer[size];

        for (int col = 0; col < size; col++) {
            initailS[col] = (int) (Math.random() * size);
        }

        return initailS;
    }

    private List<Integer[]> getneig(Integer[] solution) {
        List<Integer[]> neig = new ArrayList<>();

        for (int col = 0; col < solution.length; col++) {
            for (int row = 0; row < solution.length; row++) {
                if (solution[col] != row) {
                    Integer[] neighbor = solution.clone();
                    neighbor[col] = row;
                    neig.add(neighbor);
                }
            }
        }

        return neig;
    }

    private int calcHue(Integer[] sol) {
        int heu = 0;

        for (int col = 0; col < sol.length; col++) {
            for (int nextCol = col + 1; nextCol < sol.length; nextCol++) {
                int row = sol[col];
                int nextRow = sol[nextCol];

                if (row == nextRow || Math.abs(row - nextRow) == Math.abs(col - nextCol)) {
                    heu++;
                }
            }
        }

        return heu;
    }

    // Simulated annealing

    private List<Integer[]> solveSimulated(int size) {
        List<Integer[]> allSol = new ArrayList<>();

        int maxIte = 500;
        double initialTemp = 100;
        double coolRate = 0.05;
        double finalTemperature = 0.1;

        Integer[] currSol = initialRandom(size);
        Integer[] bestSol = currSol.clone();

        for (int i = 1; i <= maxIte; i++) {
             curr_T = calcTemp(i, initialTemp, coolRate);
            Integer[] nextSol = randomSucc(currSol);

            int curr = calcHue(currSol);
            int next = calcHue(nextSol);
            int diff = next - curr;

            if (diff < 0 || Math.exp(-diff / curr_T) > Math.random()) {
                currSol = nextSol;
            }

            if (calcHue(currSol) < calcHue(bestSol)) {
                bestSol = currSol.clone();

            }

            System.out.println("Iteration: " + i);
            System.out.println("Current Temperature: " + curr_T);
            System.out.println("Current Energy: " + curr);
            System.out.println("Current Solution:");
            pr(currSol);
            simulatedStep.add(currSol);
            allSol.add(currSol.clone());

            if (calcHue(currSol) == 0) {
                break;
            }
        }

        return allSol;
    }

    private void pr(Integer[] board) {
        int n = board.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i] == j) {
                    System.out.print("Q ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }
    private double calcTemp(int iteration, double initialTemperature, double coolingRate) {
        tempreture=initialTemperature / (10 + coolingRate * iteration);
        return initialTemperature / (10 + coolingRate * iteration);
    }

    private Integer[] randomSucc(Integer[] solution) {
        Integer[] neighbor = solution.clone();
        Random rand = new Random();
        int col = rand.nextInt(solution.length);
        int row = rand.nextInt(solution.length);
        neighbor[col] = row;
        return neighbor;
    }


    //////////////////////////

    private void centerChessBoard(AnchorPane anchorPane, GridPane chessBoard) {
        double anchorPaneWidth = anchorPane.getWidth();
        double anchorPaneHeight = anchorPane.getHeight();
        double chessBoardWidth = chessBoard.getWidth();
        double chessBoardHeight = chessBoard.getHeight();

        double xOffset = (anchorPaneWidth - chessBoardWidth) / (2);
        double yOffset = (anchorPaneHeight - chessBoardHeight) / (2);

        AnchorPane.setLeftAnchor(chessBoard, xOffset);
        AnchorPane.setTopAnchor(chessBoard, yOffset);
    }

    private void centerButtonInCenterLeft(AnchorPane anchorPane, Button button, int x) {
        double anchorPaneHeight = anchorPane.getHeight();
        double buttonHeight = button.getHeight();

        double yOffset = (anchorPaneHeight - buttonHeight) / (2);

        AnchorPane.setLeftAnchor(button, 500.0);

        AnchorPane.setTopAnchor(button, yOffset + x);

    }

    private void centerLabelInCenterLeft(AnchorPane anchorPane, Label textField, int x) {
        double anchorPaneHeight = anchorPane.getHeight();
        double textFieldHeight = textField.getHeight();

        double yOffset = (anchorPaneHeight - textFieldHeight) / 2;

        AnchorPane.setLeftAnchor(textField, 502.0);
        AnchorPane.setTopAnchor(textField, yOffset + x);
    }

    private void centerLabelInCenterTop(AnchorPane anchorPane, Label label, int x) {
        double anchorPaneWidth = anchorPane.getWidth();
        double labelWidth = label.getWidth();

        double xOffset = (anchorPaneWidth - labelWidth) / 2;

        AnchorPane.setTopAnchor(label, 10.0);
        AnchorPane.setLeftAnchor(label, xOffset + x);
    }

    private void animateText() {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(2), nQueenLabel);
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);
        scaleTransition.setToX(1.2);
        scaleTransition.setToY(1.2);
        scaleTransition.setCycleCount(ScaleTransition.INDEFINITE);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();
    }

}