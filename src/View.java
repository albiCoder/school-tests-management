import java.util.ArrayList;
import java.util.LinkedList;

import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class View extends Application {

	// app window size
	public static final int appWidth = 600;
	public static final int appHeight = 600;

	// login form fields
	private TextField email;
	private PasswordField password;
	private ComboBox<String> chooseAccountType;
	private Perdorues loggedInUser;
	private Button loginBtn;
	private Text errorMessage;

	// all scenes
	private Text sceneInfoText = new Text();

	// admin scene
	Button addStaffBtn;
	Button addStudentBtn;
	Button listStaffBtn;
	Button listStudentBtn;

	// add new user fields
	private TextField newUserName;
	private TextField newUserEmail;
	private PasswordField newUserPassword;

	// add new student specific fields
	private ComboBox<Integer> newStudentYear;
	private Button saveNewStudentBtn;

	// add new staff specific fields
	private TextField newStaffTitle;
	private TextField newStaffCourse;
	private Button saveNewStaffBtn;

	// log out
	private Button signOutBtn = new Button("Dilni nga llogaria");

	public enum accountType {
		Student, Administrator, Pedagog
	};

	public String getEmail() {
		return email.getText();
	}

	public void setEmail(String email) {
		this.email.setText(email);
	}

	public String getPassword() {
		return password.getText();
	}

	public void setPassword(String password) {
		this.password.setText(password);
	}

	public String getLoginType() {
		return this.chooseAccountType.getValue().toString();
	}

	public String getNewUserName() {
		return newUserName.getText();
	}

	public String getNewUserEmail() {
		return newUserEmail.getText();
	}

	public String getNewUserPassword() {
		return newUserPassword.getText();
	}

	public int getNewStudentYear() {
		return newStudentYear.getValue();
	}

	public String getNewStaffTitle() {
		return newStaffTitle.getText();
	}

//	public String getNewStaffCourse() {
//		
//		return new KursStudimi(newStaffCourse.getText());
//	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		sceneInfoText.setFont(Font.font("Abyssinica SIL", FontWeight.BOLD, FontPosture.REGULAR, 25));
		sceneInfoText.setFill(Color.BLUE);// setting color of the text to blue
		sceneInfoText.setStroke(Color.BLUE);// setting the stroke for the text

		//
		// log in scene
		//
		Scene loginScene = createLoginScene();

		//
		// admin page
		//
		Scene adminScene = createAdminScene();

		//
		// staff scene
		//
		Button addQuestionBtn = new Button("Krijo nje pyetje");
		Button addTestBtn = new Button("Krijo nje test");
		Button addStudentToCourseBtn = new Button("Shtoni nje student ne kurs");

		Button editStaffProfileBtn = new Button("Ndrysho profilin");
		Button addCourseBtn = new Button("Krijo nje kurs");
		Button showMessages = new Button("Shiko mesazhet");

		VBox staffToolbars = new VBox(20);
		staffToolbars.getChildren().addAll(editStaffProfileBtn, addCourseBtn, addStudentToCourseBtn, showMessages);
		staffToolbars.setAlignment(Pos.CENTER);

		BorderPane staffPage = new BorderPane();
		staffPage.setCenter(staffToolbars);
		staffPage.setBottom(signOutBtn);

		Scene staffScene = new Scene(staffPage, appWidth, appHeight);

		//
		// student page
		//
		Button editStudentProfileBtn = new Button("Ndrysho profilin");
		editStudentProfileBtn.setOnAction(e -> {

		});
		Button chooseTestBtn = new Button("Shfaq nje test");
		chooseTestBtn.setOnAction(e -> {

		});
		Button listResultsBtn = new Button("Shfaq rezultatet");
		listResultsBtn.setOnAction(e -> {

		});
		Button createMessageBtn = new Button("Dergo nje mesazh pedagogut");
		listStaffBtn.setOnAction(e -> {

		});

		VBox studentToolbars = new VBox(20);
		studentToolbars.getChildren().addAll(editStudentProfileBtn, chooseTestBtn, listResultsBtn, createMessageBtn);
		studentToolbars.setAlignment(Pos.CENTER);

		BorderPane studentPage = new BorderPane();
		studentPage.setCenter(studentToolbars);
		studentPage.setBottom(signOutBtn);

		Scene studentScene = new Scene(studentPage, appWidth, appHeight);

		//
		// add user components
		//

		newUserEmail = new TextField();
		newUserPassword = new PasswordField();
		newUserName = new TextField();

		HBox newUserNameFields = new HBox();
		newUserNameFields.getChildren().addAll(new Label("Name: "), newUserName);
		newUserNameFields.setAlignment(Pos.CENTER);
		HBox newUserEmailFields = new HBox();
		newUserEmailFields.getChildren().addAll(new Label("Email: "), newUserEmail);
		newUserEmailFields.setAlignment(Pos.CENTER);
		HBox newUserPasswordFields = new HBox();
		newUserPasswordFields.getChildren().addAll(new Label("Fjalekalimi: "), newUserPassword);
		newUserPasswordFields.setAlignment(Pos.CENTER);

		TextField newUserEmail1 = new TextField();
		PasswordField newUserPassword1 = new PasswordField();
		TextField newUserName1 = new TextField();

		HBox newUserNameFields1 = new HBox();
		newUserNameFields1.getChildren().addAll(new Label("Name: "), newUserName1);
		newUserNameFields1.setAlignment(Pos.CENTER);
		HBox newUserEmailFields1 = new HBox();
		newUserEmailFields1.getChildren().addAll(new Label("Email: "), newUserEmail1);
		newUserEmailFields1.setAlignment(Pos.CENTER);
		HBox newUserPasswordFields1 = new HBox();
		newUserPasswordFields1.getChildren().addAll(new Label("Fjalekalimi: "), newUserPassword1);
		newUserPasswordFields1.setAlignment(Pos.CENTER);

		//
		// add staff scene
		//
		sceneInfoText.setText("Plotesoni te dhenat per pedagogun e ri");

		newStaffTitle = new TextField();
		newStaffCourse = new TextField();

		HBox newStaffTitleFields = new HBox();
		newStaffTitleFields.getChildren().addAll(new Label("Title: "), newStaffTitle);
		newStaffTitleFields.setAlignment(Pos.CENTER);

		saveNewStaffBtn = new Button("Ruani te dhenat");

		VBox createNewStaffForm = new VBox();
		createNewStaffForm.getChildren().addAll(newUserNameFields1, newUserEmailFields1, newUserPasswordFields1,
				newStaffTitleFields, saveNewStaffBtn);
		createNewStaffForm.setSpacing(20);
		createNewStaffForm.setAlignment(Pos.CENTER);

		Scene createNewStaffScene = new Scene(createNewStaffForm, appWidth, appHeight);

		//
		// add student scene
		//
		sceneInfoText.setText("Plotesoni te dhenat per studentin e ri");

		newStudentYear = new ComboBox<>();
		newStudentYear.getItems().addAll(1, 2, 3, 4, 5);
		newStudentYear.setValue(1);

		HBox newStudentYearFields = new HBox();
		newStudentYearFields.getChildren().addAll(new Label("Year: "), newStudentYear);
		newStudentYearFields.setAlignment(Pos.CENTER);

		saveNewStudentBtn = new Button("Ruani te dhenat");

		VBox createNewStudentForm = new VBox();
		createNewStudentForm.getChildren().addAll(newUserNameFields, newUserEmailFields, newUserPasswordFields,
				newStudentYearFields, saveNewStudentBtn);
		createNewStudentForm.setSpacing(20);
		createNewStudentForm.setAlignment(Pos.CENTER);

		Scene createNewStudentScene = new Scene(createNewStudentForm, appWidth, appHeight);

		//
		// list staff members
		//

		Scene listStaffScene = getUsersList(sceneInfoText, "staff's members", MainClass.getStaffList(), "Titulli",
				"Name", "Email");

		//
		// list students members
		//

		Scene listStudentsScene = getUsersList(sceneInfoText, "students", MainClass.getStudentsList(), "Name", "Email",
				"Year");

		//
		// create course scene
		//
		// course
		sceneInfoText.setText("Kursi i ri");
		TextField newCourseName = new TextField();
		Button saveCourseBtn = new Button("Ruani kursin");
		saveCourseBtn.setDisable(true);

		HBox newCourseNameFields = new HBox();
		newCourseNameFields.getChildren().addAll(new Label("Name: "), newCourseName, saveCourseBtn);
		newCourseNameFields.setAlignment(Pos.CENTER);
		// test
		TextField newTestName = new TextField();
		Button saveTestNameBtn = new Button("Ruani emrin e testit");
		Button saveTestBtn = new Button("Ruani testin");
		saveTestBtn.setDisable(true);
		HBox newTestFields = new HBox();
		newTestFields.getChildren().addAll(new Label("Emri testit: "), newTestName, saveTestNameBtn, saveTestBtn);
		newTestFields.setAlignment(Pos.CENTER);
		// question
		TextField question = new TextField();
		ToggleGroup group = new ToggleGroup();
		RadioButton button1 = new RadioButton("Po");
		RadioButton button2 = new RadioButton("Jo");
		button1.setToggleGroup(group);
		button2.setToggleGroup(group);
		Button saveQuestionBtn = new Button("Ruani pyetjen");
		saveQuestionBtn.setDisable(true);
		HBox newQuestionFields = new HBox();
		newQuestionFields.setSpacing(10);
		newQuestionFields.getChildren().addAll(new Label("Pyetja: "), question, new Label("Pergjigja: "), button1,
				button2, saveQuestionBtn);
		newQuestionFields.setAlignment(Pos.CENTER);

		VBox createNewCourseForm = new VBox();
		createNewCourseForm.getChildren().addAll(sceneInfoText, newCourseNameFields, newTestFields, newQuestionFields);
		createNewCourseForm.setSpacing(20);
		createNewCourseForm.setAlignment(Pos.CENTER);

		Scene createNewCourseScene = new Scene(createNewCourseForm, appWidth, appHeight);

		KursStudimi kursiRi = new KursStudimi();

		//
		// add students to a course scene
		//
		Text txt = new Text("Shtoni student ne nje kurs");
		txt.setFont(Font.font("Abyssinica SIL", FontWeight.BOLD, FontPosture.REGULAR, 25));
		txt.setFill(Color.BLUE);// setting color of the text to blue
		txt.setStroke(Color.BLUE);// setting the stroke for the text

		ComboBox<String> chooseCourse = new ComboBox<>();

		String[] coursesList = MainClass.getStaffCourses(2);
		for (int i = 0; i < coursesList.length; i++) {
			chooseCourse.getItems().add(coursesList[i]);
		}
		chooseCourse.setValue(coursesList[0]);
		
//		ArrayList<KursStudimi> coursesList = MainClass.getStaffCourses(2);
//		for (int i = 0; i < coursesList.size(); i++) {
//			chooseCourse.getItems().addAll((Collection<? extends String>) coursesList.get(i));
//		}
//		chooseCourse.setValue(coursesList.get(0).getEmriKursit());

		Button addStudentsToCourseBtn = new Button("Shtoni studentet e zgjedhur ne kurs");
		
		TableView<Student> table = new TableView<>();
		ObservableList<Student> data = FXCollections.observableArrayList(MainClass.getStudentsList());

		table.setEditable(true);
		table.setMaxWidth(300);

		TableColumn<Student, Integer> tableCol1 = new TableColumn<>("id");
		tableCol1.setCellValueFactory(new PropertyValueFactory<Student, Integer>("id"));
		TableColumn<Student, String> tableCol2 = new TableColumn<>("Name");
		tableCol2.setCellValueFactory(new PropertyValueFactory<Student, String>("Name"));
		TableColumn<Student, Boolean> checkBoxCol = new TableColumn<>("Choosen");
		checkBoxCol.setCellValueFactory(new PropertyValueFactory<Student, Boolean>("selected"));
		checkBoxCol.setCellFactory(CheckBoxTableCell.forTableColumn(checkBoxCol));
		checkBoxCol.setEditable(true);
		
		ArrayList<Integer> idList = new ArrayList<>();
		addStudentsToCourseBtn.setOnAction(actionEvent -> {
			idList.clear();
	        for (int row = 0; row < table.getItems().size(); row++) {
	            if(checkBoxCol.getCellData(row)) {
	            	idList.add(tableCol1.getCellData(row));
	            }
	        }
	        System.out.println(idList);
	        MainClass.addStudentsToCourse(idList, chooseCourse.getValue());
	    });

		table.setItems(data);
		table.getColumns().addAll(tableCol1, tableCol2, checkBoxCol);

		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(txt, chooseCourse, addStudentsToCourseBtn, table);

		Scene addingStudentsToCourseScene = new Scene(vbox, appWidth, appHeight);
		
		
		//
		// display messages scene
		//
		Text mssText;
		TableView<Mesazh> messagesTable;
		ObservableList<Mesazh> messages;
		TableColumn<Mesazh, Integer> messageId;
		TableColumn<Mesazh, String> studentName;
		TableColumn<Mesazh, String> messageContent;
		VBox messagesContainer;
		mssText = new Text("Mesazhet tuaja");
		mssText.setFont(Font.font("Abyssinica SIL", FontWeight.BOLD, FontPosture.REGULAR, 25));
		mssText.setFill(Color.BLUE);// setting color of the text to blue
		mssText.setStroke(Color.BLUE);// setting the stroke for the text

		
		messagesTable = new TableView<>();
		// problem. Funksioni poshte duhet thirrur pas klikimit te butonin shfaqe mesazhet
		//
		messages = FXCollections.observableArrayList(MainClass.getMessagesList(2));

		messagesTable.setEditable(true);
		messagesTable.setMaxWidth(300);

		messageId = new TableColumn<>("Id");
		messageId.setCellValueFactory(new PropertyValueFactory<Mesazh, Integer>("mesazhId"));
		studentName = new TableColumn<>("Studenti");
		studentName.setCellValueFactory(new PropertyValueFactory<Mesazh, String>("studentName"));
		messageContent = new TableColumn<>("Mesazhi");
		messageContent.setCellValueFactory(new PropertyValueFactory<Mesazh, String>("mesazhi"));
		
		messagesTable.setItems(messages);
		messagesTable.getColumns().addAll(messageId, studentName, messageContent);

		messagesContainer = new VBox();
		messagesContainer.setSpacing(5);
		messagesContainer.setPadding(new Insets(10, 0, 0, 10));
		messagesContainer.getChildren().addAll(mssText, messagesTable);

		Scene messagesScene = new Scene(messagesContainer, appWidth, appHeight);

		
		//
		//
		// set handlers
		//
		//

		// all
		loginBtn.setOnAction(e -> {
			loggedInUser = MainClass.logIntoSystem(getLoginType(), getEmail(), getPassword());
			if (loggedInUser != null) {
				if (getLoginType() == "Administrator") {
					primaryStage.setScene(adminScene);
				} else if (getLoginType() == "Student") {
					primaryStage.setScene(studentScene);
				} else if (getLoginType() == "Pedagog") {
					primaryStage.setScene(staffScene);
				}
			} else {
				errorMessage.setVisible(true);
			}
		});

		// admin
		addStaffBtn.setOnAction(e -> {
			primaryStage.setScene(createNewStaffScene);
		});

		addStudentBtn.setOnAction(e -> {
			primaryStage.setScene(createNewStudentScene);
		});

		listStaffBtn.setOnAction(e -> {
			primaryStage.setScene(listStaffScene);
		});

		listStudentBtn.setOnAction(e -> {
			primaryStage.setScene(listStudentsScene);
		});

		saveNewStaffBtn.setOnAction(e -> {
//			 duhet krijuar kursi perpara pedagogut
			Pedagog newStaff = new Pedagog(getNewUserName(), getNewUserEmail(), getNewUserPassword(),
					getNewStaffTitle());
			MainClass.createNewStaff(newStaff);
			primaryStage.setScene(adminScene);
		});

		saveNewStudentBtn.setOnAction(e -> {
			Student newStudent = new Student(getNewUserName(), getNewUserEmail(), getNewUserPassword(),
					getNewStudentYear());
			MainClass.createNewStudent(newStudent);
			primaryStage.setScene(adminScene);
		});

		signOutBtn.setOnAction(e -> {
			primaryStage.setScene(loginScene);
		});

		// staff
		editStaffProfileBtn.setOnAction(e -> {
			Pedagog user = (Pedagog) loggedInUser;
			primaryStage.setScene(createNewStaffScene);
//			System.out.println(newUserEmail1 == null);
//			newUserEmail1.setText(user.getEmail());
//			newUserPassword1.setText(user.getPassword());
//			newUserName1.setText(user.getName());
//			newStaffTitle.setText(user.getTitulli());
//			newStaffCourse.setText(user.getKursStudimi());
		});

		//
		// creating new course handlers
		//
		saveTestNameBtn.setOnAction(e -> {
			kursiRi.getTests().addFirst(new Test(newTestName.getText()));
			saveCourseBtn.setDisable(true);
			saveTestBtn.setDisable(true);
			newTestName.setDisable(true);
			saveQuestionBtn.setDisable(false);
		});
		addCourseBtn.setOnAction(e -> {
			primaryStage.setScene(createNewCourseScene);
		});

		saveQuestionBtn.setOnAction(e -> {
			// ruajme pyetjen
			Pyetje q = new Pyetje(question.getText(), getAnswer(group));

			kursiRi.getTests().getFirst().getPyetje().addFirst(q);

			saveTestBtn.setDisable(false);
			// reset the question
			question.setText("");
		});

		saveTestBtn.setOnAction(e -> {
			saveCourseBtn.setDisable(false);
			newTestName.setDisable(false);
			newTestName.setText("");
		});

		saveCourseBtn.setOnAction(e -> {
			kursiRi.setEmriKursit(newCourseName.getText());
			MainClass.addCourseToStaff(kursiRi, getLoginType(), (Pedagog) loggedInUser);
			primaryStage.setScene(staffScene);
			newCourseName.setText("");
		});

		addStudentToCourseBtn.setOnAction(e -> {

			primaryStage.setScene(addingStudentsToCourseScene);

		});
		showMessages.setOnAction(e -> {
			primaryStage.setScene(messagesScene);

		});

		//
		// set stage
		//
		primaryStage.setScene(loginScene);
		primaryStage.setTitle("Log in");
		primaryStage.show();
	}

	public Scene createAdminScene() {
		addStaffBtn = new Button("Shto pedagog te ri");
		addStudentBtn = new Button("Shto student te ri");
		listStaffBtn = new Button("Afisho listen e pedagogeve");
		listStudentBtn = new Button("Afisho listen e studenteve");

		VBox adminToolbars = new VBox(20);
		adminToolbars.getChildren().addAll(addStaffBtn, addStudentBtn, listStaffBtn, listStudentBtn);
		adminToolbars.setAlignment(Pos.CENTER);

		BorderPane adminPage = new BorderPane();
		adminPage.setCenter(adminToolbars);
		adminPage.setBottom(signOutBtn);

		Scene adminScene = new Scene(adminPage, appWidth, appHeight);
		return adminScene;
	}

	public Scene createLoginScene() {
		Text loginText = new Text("Hyni ne llogari!");
		loginText.setFont(Font.font("Abyssinica SIL", FontWeight.BOLD, FontPosture.REGULAR, 35));
		loginText.setFill(Color.GREEN);// setting color of the text to blue
		loginText.setStroke(Color.GREEN);// setting the stroke for the text

		email = new TextField();

		password = new PasswordField();

		chooseAccountType = new ComboBox<>();
		chooseAccountType.getItems().addAll(accountType.Student.toString(), accountType.Pedagog.toString(),
				accountType.Administrator.toString());
		chooseAccountType.setValue(accountType.Pedagog.toString());

		loginBtn = new Button("Logohuni");

		errorMessage = new Text("No account with this email and password");
		errorMessage.setFill(Color.RED);
		errorMessage.setStroke(Color.RED);
		errorMessage.setVisible(false);

		HBox emailFields = new HBox();
		emailFields.getChildren().addAll(new Label("Email: "), email);
		emailFields.setAlignment(Pos.CENTER);
		HBox passwordFields = new HBox();
		passwordFields.getChildren().addAll(new Label("Fjalekalimi: "), password);
		passwordFields.setAlignment(Pos.CENTER);

		VBox loginForm = new VBox();
		loginForm.getChildren().addAll(loginText, emailFields, passwordFields, chooseAccountType, loginBtn,
				errorMessage);
		loginForm.setSpacing(20);
		loginForm.setAlignment(Pos.CENTER);

		Scene loginScene = new Scene(loginForm, appWidth, appHeight);
		return loginScene;
	}

	public <T> Scene getUsersList(Text sceneInfoText, String text, ArrayList<T> list, String Col1, String Col2,
			String Col3) {
		sceneInfoText.setText("All " + text);

		TableView<T> table = new TableView<>();
		ObservableList<T> data = FXCollections.observableArrayList(list);

		table.setEditable(true);
		table.setMaxWidth(300);

		TableColumn<T, String> tableCol1 = new TableColumn<>(Col1);
		tableCol1.setCellValueFactory(new PropertyValueFactory<T, String>(Col1));
		TableColumn<T, String> tableCol2 = new TableColumn<>(Col2);
		tableCol2.setCellValueFactory(new PropertyValueFactory<T, String>(Col2));
		TableColumn<T, String> tableCol3 = new TableColumn<>(Col3);
		tableCol3.setCellValueFactory(new PropertyValueFactory<T, String>(Col3));

		table.setItems(data);
		table.getColumns().addAll(tableCol1, tableCol2, tableCol3);

		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(sceneInfoText, table);

		Scene listingScene = new Scene(vbox, appWidth, appHeight);

		return listingScene;
	}

	public static int getAnswer(ToggleGroup group) {
		String pergjigja = ((RadioButton) group.getSelectedToggle()).getText();
		if (pergjigja == "Po")
			return 1;
		else if (pergjigja == "Jo")
			return 0;
		return 0;
	}

	public static void main(String[] args) {
		MainClass.connectToDB();
		launch(args);
	}
}
