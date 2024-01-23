package com.example.connect2;

import javafx.application.Application;

import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.stage.*;
import java.io.*;
import java.nio.file.*;
import java.net.URI;
import javax.sound.sampled.*;
import java.util.*;


// Main class
public class Connect  extends Application {
    Stage stage;
    Scene connectScene;
    BorderPane borderPane;
    FlowPane flowPane;
    VBox vBox;
    TextField textFieldImage, textFieldStatus, textFieldName, textFieldAddFriend;
    Label userNameLabel, statusLabel, friendsLabel, mainLabel, addedFriendLabel;
    Button statusChangeButton, imageChangeButton, addFriendButton, addButton, deleteButton, lookupButton, quotesButton;
    ImageView imageView;
    static TreeSet<User> setOfUsers = new TreeSet<>();
    static TreeSet<String> setOfNames = new TreeSet<>();
    String userName;
    boolean profileIsCreated = false;
    public static File dataFile = new File("data.txt");

    @Override
    public void start(Stage stage) {

        this.stage = stage;
        // Set the application title
        stage.setTitle("C O N N E C T");

        // Set the application icon
        Image icon = new Image("iconImage.png");
        stage.getIcons().add(icon);

        /* Scene[1] : Welcoming Scene */
        // Nodes of the welcoming scene
        // (1) : image
        Image backgroundImage = new Image("welcomeImage.png");
        ImageView backgroundImageView = new ImageView(backgroundImage);

        // (2) : the next button
        Button nextButton = new Button("NEXT");
        nextButton.setTranslateY(-100);
        nextButton.setTranslateX(425);

        // create a new pane then add the nodes into the pane
        BorderPane welcomePane = new BorderPane();
        welcomePane.setStyle("-fx-background-color: #29292B;");
        welcomePane.setCenter(backgroundImageView);
        welcomePane.setBottom(nextButton);


        // Create the first scene
        Scene welcomeScene = new Scene(welcomePane, 900, 600);
        stage.setScene(welcomeScene);
        stage.setResizable(false);
        stage.show();

        /* Scene[2] : The main program */
        /* PANES */
        // The main pane
        borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: rgba(173, 216, 230, 0.3);");


        // VBox for the left
        VBox leftVBox = new VBox();
        leftVBox.setSpacing(30);
        leftVBox.setAlignment(Pos.CENTER_LEFT);
        leftVBox.setPadding(new Insets(30, 30, 30, 30));

        // HBox for the right
        HBox topBox = new HBox();
        topBox.setAlignment(Pos.CENTER);
        topBox.setSpacing(30);
        topBox.setPadding(new Insets(30, 30, 30, 30));

        // Flow pane for the center
        FlowPane flowPane = new FlowPane();
        flowPane.setAlignment(Pos.CENTER_LEFT);
        flowPane.setStyle("-fx-background-color: white;");
        flowPane.getChildren().add(new Label()); // this line to have the flowPane white


        /* NODES: */
        /* [LEFT]: WHAT IS IN VBox */
        // (1): Change status and its text field
        statusChangeButton = new Button("Change Status");
        textFieldStatus = new TextField();
        statusChangeButton.setPadding(new Insets(5, 50, 5, 50));
        statusChangeButton.setTranslateX(17);
        statusChangeButton.setTranslateY(-20);
        textFieldStatus.setPrefWidth(220);

        // (2): Change image and its text field
        imageChangeButton = new Button("Change Image");
        textFieldImage = new TextField();
        imageChangeButton.setPadding(new Insets(5, 50, 5, 50));
        imageChangeButton.setTranslateX(17);
        imageChangeButton.setTranslateY(-20);
        textFieldImage.setPrefWidth(150);

        // (3): Add friend and its text field
        addFriendButton = new Button("Add Friend");
        addFriendButton.setDefaultButton(true);
        textFieldAddFriend = new TextField();
        addFriendButton.setPadding(new Insets(5, 60, 5, 60));
        addFriendButton.setTranslateX(17);
        addFriendButton.setTranslateY(-20);
        textFieldAddFriend.setPrefWidth(150);


        // Adding the nodes to the pane
        leftVBox.getChildren().addAll(textFieldStatus, statusChangeButton, textFieldImage, imageChangeButton,
                textFieldAddFriend, addFriendButton);


        /* [TOP]: WHAT IS IN HBox */
        // (1): Name label and its text field
        Label nameLabel = new Label("Name:");
        textFieldName = new TextField();

        // (2): the three buttons
        Image deleteIcon = new Image("deleteIcon.png");
        ImageView deleteIconImageView = new ImageView(deleteIcon);
        deleteIconImageView.setFitWidth(20);
        deleteIconImageView.setFitHeight(20);
        deleteButton = new Button("Delete",deleteIconImageView);

        Image lookupIcon = new Image("lookupIcon.png");
        ImageView lookupIconImageView = new ImageView(lookupIcon);
        lookupIconImageView.setFitWidth(20);
        lookupIconImageView.setFitHeight(20);
        lookupButton = new Button("Lookup",lookupIconImageView);

        Image addIcon = new Image("addIcon.png");
        ImageView addIconImageView = new ImageView(addIcon);
        addIconImageView.setFitWidth(20); // Set the width of the icon
        addIconImageView.setFitHeight(20);
        addButton = new Button("Add",addIconImageView);

        // Fun activity, positive quotes generator
        Image quoteIcon = new Image("quoteIcon.png");
        ImageView quoteIconImageView = new ImageView(quoteIcon);
        quoteIconImageView.setFitWidth(20); // Set the width of the icon
        quoteIconImageView.setFitHeight(20);
        quotesButton = new Button("Pick a Positive Quote",quoteIconImageView);



        // Adding the nodes to the pane
        topBox.getChildren().addAll(nameLabel, textFieldName, addButton, deleteButton, lookupButton, quotesButton);

        // add the three minor panes into the main pane, the border pane
        borderPane.setCenter(flowPane);
        borderPane.setTop(topBox);
        borderPane.setLeft(leftVBox);

        // set the second scene with the border pane
        connectScene = new Scene(borderPane, 900, 600);

        // Actions handlers:
        nextButton.setOnAction(new NextSceneHandler());                        // 1. (NEXT) Button

        addButton.setOnAction(new CreateProfileHandler());                     // 2. (Add) Button

        statusChangeButton.setOnAction(new ChangeStatusHandler());            // 3. (Change Status) Button

        imageChangeButton.setOnAction(new ChangeImageHandler());              // 4. (Change Image) Button

        addFriendButton.setOnAction(new AddFriendHandler());                  // 5. (Add Friend) Button

        lookupButton.setOnAction(new LookupHandler());                        // 6. (Lookup) Button

        deleteButton.setOnAction( new DeleteProfileHandler());                // 7. (Delete) Button

        quotesButton.setOnAction(new PositiveQuoteHandler());                 // 8. (Positive quotes) Button


        //call the method that generates sound at the beginning
        playSound("startingSound.wav");
    }


    public static void main(String[] args) {
        try {
            readFromFile(dataFile);
        }
        catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
        System.out.println(setOfUsers);
        launch();
        writeInFile(setOfUsers);

    }

    // A method to write in the file in order to store the data after closing the program
    public static void writeInFile(TreeSet<User> setOffUsers){
        try (PrintWriter output = new PrintWriter(dataFile)){
            for (User user : setOffUsers){
                output.println(user.toString());
            }
        }
        catch (FileNotFoundException exception){
            System.out.println("no such file");
        }
    }

    // A method to read from the data file that contains users information in order to use them while running the program
    public static void readFromFile(File file) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                // read each line and assign it to an array of string by splitting the string line by commas
                String[] line = scanner.nextLine().split(",");

                // add the userName, index[0], in the set of userNames
                setOfNames.add(line[0]);

                for (String userName : setOfNames) {
                    // create a new user with this userName
                    User newUser = new User(userName);

                    // Add this user to the listOfUsers
                    setOfUsers.add(newUser);

                    // set her/his status, index[1]
                    newUser.setStatus(line[1]);

                    // set her/his image, index[2]
                    newUser.setUserImage(line[2]);

                    // add her/his friends to setOFFriends, index[3] until the end
                    TreeSet<String> friends = new TreeSet<>(Arrays.asList(line).subList(3, line.length));
                    newUser.setOfFriends.addAll(friends);

                }
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    public static void playSound(String filePath) {
        try {
            InputStream audioInputStream = Connect.class.getClassLoader().getResourceAsStream(filePath);
            if (audioInputStream != null) {
                AudioInputStream stream = AudioSystem.getAudioInputStream(audioInputStream);
                Clip clip = AudioSystem.getClip();
                clip.open(stream);
                clip.start();
            }
            else {
                System.out.println("Audio file not found: " + filePath);
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void displayUI(){
        profileIsCreated = true;
        statusChangeButton.setDisable(false);
        imageChangeButton.setDisable(false);
        addFriendButton.setDisable(false);

        // Flow pane for the center
        flowPane = new FlowPane();
        flowPane.setAlignment(Pos.TOP_LEFT);
        flowPane.setVgap(30);
        flowPane.setOrientation(Orientation.VERTICAL);
        flowPane.setStyle("-fx-background-color: white;");

        /* [CENTER]: WHAT IS IN THE flowPane */
        // (1): Name label:
        userNameLabel = new Label(userName);
        userNameLabel.setTranslateX(20);
        userNameLabel.setTranslateY(20);
        userNameLabel.setTextFill(Color.web("#2E7B7B"));
        userNameLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        // (2): Image
        Image image = new Image("noImage.png");
        imageView = new ImageView(image);
        imageView.setFitHeight(230);
        imageView.setFitWidth(210);
        imageView.setTranslateX(20);


        // (3): Status Label
        statusLabel = new Label("No current status");
        statusLabel.setTranslateX(20);
        statusLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 15));


        // (4): Friends
        friendsLabel = new Label("Friends:");
        friendsLabel.setTranslateX(300);
        friendsLabel.setTranslateY(-330);
        friendsLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        vBox = new VBox();
        vBox.getChildren().add(friendsLabel);

        // (5): New Profile Created
        mainLabel = new Label("New Profile Created");
        mainLabel.setTranslateX(200);
        mainLabel.setTranslateY(50);
        mainLabel.setFont(Font.font("Verdana", FontWeight.MEDIUM, 15));

        HBox mainLabelBox = new HBox(mainLabel);
        mainLabelBox.setAlignment(Pos.CENTER);
        mainLabelBox.setTranslateY(-80);

        // Adding the nodes to the flowPane:
        flowPane.getChildren().addAll(userNameLabel, imageView, statusLabel,vBox);

        // Adding the flowPane to the main pane, borderPane:
        borderPane.setCenter(flowPane);
        borderPane.setBottom(mainLabelBox);
    }

    // A method to display an alert when needed
    public static void displayAlert(Button button, boolean disable, String title, String content){
        button.setDisable(disable);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private static User getUser(String userName) {
        User user1 = new User(userName);
        for (User user : setOfUsers) {
            if (user.getUserName().equals(userName)) {
                user1 = user;
            }
        }
        return user1;
    }

    class NextSceneHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            stage.setScene(connectScene);
        }
    }


    // Account Creating
    class CreateProfileHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            // generate a sound foe the button
            playSound("buttonSound.wav");

            // Check: is the profile existed before?
            userName = textFieldName.getText();

            if (setOfNames.contains(userName)){
                displayAlert( addButton, false, "This profile is already on CONNECT",
                        "Sorry \nYou can not creat a new profile with ( " + userName + " ) userName because this " +
                                "userName is already existed on CONNECT, please choose another userName");
            }
            else if (userName.isEmpty() || userName.length() >20){
                displayAlert( addButton, false, "Invalid Username",
                        "Sorry \nYou can not creat a new profile with ( " + userName + " ) userName because this " +
                                "userName should be with at least 20 characters, please choose another userName");
            }

            else {
                displayUI();
                User currentUser = new User(userName);
                if (!setOfUsers.contains(currentUser)){
                    setOfUsers.add(currentUser);
                    setOfNames.add(userName);
                }

                System.out.println(setOfUsers);

            }
        }

    }

    class ChangeImageHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {
            // generate a sound foe the button
            playSound("buttonSound.wav");
            if (profileIsCreated) {
                // To let the user choose an image:
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));

                // Show the file dialog and get the selected file
                File selectedImageFile = fileChooser.showOpenDialog(null);

                // If a file is selected, load and display the image
                if (selectedImageFile != null) {
                    // Convert the file to URI
                    URI selectedImageURI = selectedImageFile.toURI();

                    // Convert the URI to a string
                    String selectedImagePath = selectedImageURI.toString();

                    //Creat a new object of type Image:
                    Image updatedImage = new Image(selectedImagePath);

                    // reSet the imageview to the updated image
                    imageView.setImage(updatedImage);

                    // Convert the image File to Path
                    Path photoPath = Paths.get(selectedImageURI);

                    // Get the photo userName from the Path
                    String photoName = photoPath.getFileName().toString();

                    // type the userName of the image in the text field
                    textFieldImage.setText(photoName);

                    // set the user image in order to display it
                    userName = textFieldName.getText();
                    User user = getUser(userName);
                    user.setUserImage(selectedImagePath);

                    mainLabel.setText("Picture updated");
                }
            }

            else {
                displayAlert(imageChangeButton,true,
                        "You did not create a profile yet!", "Please create a profile");


            }
        }
    }

    // Change Status
    class ChangeStatusHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            // generate a sound foe the button
            playSound("buttonSound.wav");
            if (profileIsCreated) {
                userName = textFieldName.getText();
                User statusUser = null;
                for (User user : setOfUsers) {
                    if (user.getUserName().equals(userName)) {
                        statusUser = user;
                    }
                }
                String status = textFieldStatus.getText();
                statusLabel.setText(status);
                if (statusUser != null) {
                    statusUser.setStatus(status);
                }
                mainLabel.setText("Status updated to: " + status);

            }
            else {
                displayAlert(statusChangeButton,true, "You did not create a profile yet!",
                        "Please create a profile");
            }
        }
    }

    class AddFriendHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            // generate a sound foe the button
            playSound("buttonSound.wav");
            try {
                if (profileIsCreated) {
                    userName = textFieldName.getText();
                    String friendName = textFieldAddFriend.getText();

                    User user =  getUser(userName);

                    User friend = getUser(friendName);
                    if ((friendName.equals(userName))){
                        displayAlert(addFriendButton,false," ",
                                " You can not add yourself ");

                    }
                    else if (user.setOfFriends.contains(friendName)) {
                        displayAlert(addFriendButton,false,"Existing Friend",
                                "( " + friendName + " )  is already your friend  :)");

                    }
                    else if (setOfNames.contains(friendName)) {
                        addedFriendLabel = new Label(friendName);
                        addedFriendLabel.setTranslateX(320);
                        addedFriendLabel.setTranslateY(-300);
                        addedFriendLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                        vBox.getChildren().add(addedFriendLabel);
                        mainLabel.setText(friendName + " is added as a friend");

                        // because friends are reciprocal
                        user.setOfFriends.add(friendName);
                        friend.setOfFriends.add(userName);

                    }
                    else {
                        displayAlert(addFriendButton,false,"User does not Exist",
                                "there is no user called " + friendName);
                    }

                }
                else {
                    displayAlert(addFriendButton,true, "You did not create a profile yet!",
                            "Please create a profile");
                }

            } catch (NullPointerException ex) {
                System.out.println(ex.getMessage());
            }

        }
    }
    class LookupHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            // generate a sound foe the button
            playSound("buttonSound.wav");

            userName = textFieldName.getText();
            if (setOfNames.contains(userName)) {
                displayUI();
                User lookupUser = getUser(userName);

                userNameLabel.setText(userName);
                imageView.setImage(lookupUser.getUserImage());
                statusLabel.setText(lookupUser.getStatus());
                mainLabel.setText("Displaying " + userName);

                for (String friend : lookupUser.setOfFriends) {
                    addedFriendLabel = new Label(friend);
                    addedFriendLabel.setTranslateX(320);
                    addedFriendLabel.setTranslateY(-300);
                    addedFriendLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                    vBox.getChildren().add(addedFriendLabel);
                }
            }

            else {
                displayAlert(lookupButton,false,"User does not Exist",
                        "there is no user called " + userName + " please search for another userName");
            }
        }
    }
    class DeleteProfileHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            // generate a sound for the button
            playSound("buttonSound.wav");

            StackPane stackPane = new StackPane();
            stackPane.setStyle("-fx-background-color: white;");

            String deletedUserName = textFieldName.getText();
            if (!setOfNames.contains(deletedUserName)) {
                displayAlert(deleteButton,false,"User does not Exist",
                        "there is no user called " + userName + " please search for another userName");
            }
            else {

                User deletedUser = getUser(deletedUserName);
                // remove the user from the sets
                setOfUsers.remove(deletedUser);
                setOfNames.remove(deletedUserName);

                // delete the deleted friend from its friends set
                for (User user : setOfUsers) {
                    user.setOfFriends.remove(deletedUserName);
                }

                profileIsCreated = false;

                Label deletLabel = new Label("Profile of " + deletedUserName + " is deleted");
                deletLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 17));

                stackPane.getChildren().add(deletLabel);
                borderPane.setCenter(stackPane);
            }
        }
    }

    public static class PositiveQuoteHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            // generate a sound for the button
            playSound("buttonSound.wav");

            // Create a new stage (window)
            Stage newStage = new Stage();

            // Create a new pane
            BorderPane quotePane = new BorderPane();

            // create a hello label and set it in the top of the pane
            Label helloMessage = new Label("Hello CONNECTer, your today's quote:\n");
            helloMessage.setTextFill(Color.web("#E35D1C"));
            helloMessage.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
            helloMessage.setTranslateY(20);
            helloMessage.setTranslateX(40);
            quotePane.setTop(helloMessage);

            // create the quote label and set it in the center of the pane
            Label quote = new Label(displayQuotes());
            quote.setTextFill(Color.web("#E90F6D"));
            quote.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
            quotePane.setCenter(quote);

            // Create a new scene
            Scene newScene = new Scene(quotePane, 850, 100);
            newStage.setScene(newScene);

            // Set the icon and title for the stage
            Image icon = new Image("iconImage.png");
            newStage.getIcons().add(icon);
            newStage.setTitle("Quote of the day :)");

            // Show the new window
            newStage.show();
        }
        public static String displayQuotes(){
            String[] arrayOfQuotes = {
            /*1*/   "“Keep your face always toward the sunshine—and shadows will fall behind you.” —Walt Whitman",
            /*2*/   "“Setting goals is the first step in turning the invisible into the visible.” —Tony Robbins",
            /*3*/   "“You can have it all. Just not all at once.” —Oprah Winfrey",
            /*4*/   "\"Train your mind to see the good in every situation.\" —Unknown",
            /*5*/   "\"Only in the darkness can you see the stars.\" —Martin Luther King, Jr.",
            /*6*/   "\"You do not find the happy life. You make it.\" —Thomas S.Monson",
            /*7*/   "“Try to be a rainbow in someone’s cloud.” —Maya Angelou",
            /*8*/   " \"Memories of our lives, of our works and our deeds will continue in others.\" —Rosa Parks",
            /*9*/   "\"All our dreams can come true, if we have the courage to pursue them.\" —Walt Disney",
            /*10*/  "Believe you can and you're halfway there.\n-Theodore Roosevelt",
            /*11*/  "The only way to do great work is to love what you do. \n-Steve Jobs",
            /*12*/  "Don't watch the clock; do what it does. Keep going. \n-Sam Levenson",
            /*13*/  "The future belongs to those who believe in the beauty of their dreams.\n -Eleanor Roosevelt",
            /*14*/  "Your time is limited, don't waste it living someone else's life.\n -Steve Jobs",
            /*15*/  "The only limit to our realization of tomorrow will be our doubts of today.\n -Franklin D. Roosevelt",
            /*16*/  "It's not whether you get knocked down, it's whether you get up.\n -Vince Lombardi",
            /*17*/  "You are never too old to set another goal or to dream a new dream.\n -C.S. Lewis",
            /*18*/  "The only person you are destined to become is the person you decide to be.\n -Ralph Waldo Emerson",
            /*19*/  "I find that the harder I work, the more luck I seem to have.\n -Thomas Jefferson",
            /*20*/  "The only place where success comes before work is in the dictionary.\n -Vidal Sassoon",
            /*21*/  "The way to get started is to quit talking and begin doing.\n -Walt Disney",
            /*22*/  "Don't be afraid to give up the good to go for the great.\n -John D. Rockefeller",
            };

            return arrayOfQuotes[(int) (Math.random() * arrayOfQuotes.length)];
        }

    }

}