import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Application;
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
import javafx.scene.control.TableCell;
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
	private Perdorues loggedInUser;
	private String loginType;

	public enum accountType {
		Student, Administrator, Pedagog
	};

	public Perdorues getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(Perdorues loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getLoginType() {
		return this.loginType;
	}

	//
	// Scenes
	//

	// login
	Scene loginScene = null;
	// admin
	Scene adminScene = null;
	Scene createNewStudentScene = null;
	Scene createNewStaffScene = null;
	Scene listStaffScene = null;
	Scene listStudentsScene = null;
	// staff
	Scene staffScene = null;
	Scene createNewCourseScene = null;
	Scene addingStudentsToCourseScene = null;
	Scene messagesScene = null;
	// student
	Scene studentScene = null;
	Scene testScene = null;
	Scene coursesListScene = null;

	// scenes history
	ArrayList<Scene> scenesHistory = new ArrayList<>();
	int historyIndex = 0;

	//
	// gui starter
	//
	@Override
	public void start(Stage primaryStage) throws Exception {

		// load the first scene
		loginScene = createLoginScene(primaryStage);

		//
		// set stage
		//
		primaryStage.setScene(loginScene);
		primaryStage.setTitle("Sistem menaxhimi provimesh");
		primaryStage.show();
	}

	//
	// Initializing scenes
	//

	public void initializeAdminScenes(Stage primaryStage) {
		adminScene = createAdminScene(primaryStage);
		listStaffScene = getUsersList("staff's members", MainClass.getStaffList(), "Titulli", "Name", "Email");
		listStudentsScene = getUsersList("students", MainClass.getStudentsList(), "Name", "Email", "Year");

		createNewStudentScene = addStudentScene(primaryStage, null, null, null, null);
		createNewStaffScene = addStaffScene(primaryStage, null, null, null, null);
	}

	public void initializeStaffScenes(Stage primaryStage) {
		staffScene = createStaffScene(primaryStage);
		createNewCourseScene = addCourseScene(primaryStage);
		addingStudentsToCourseScene = addStudentsToCourseScene(primaryStage);
		messagesScene = listMessagesScene(primaryStage);

		Pedagog p = MainClass.getStaff(loggedInUser.getId());
		createNewStaffScene = addStaffScene(primaryStage, p.getEmail(), p.getPassword(), p.getName(), p.getTitulli());
	}

	public void initializeStudentScenes(Stage primaryStage) {
		studentScene = createStudentScene(primaryStage);
		coursesListScene = coursesListScene(primaryStage);

		Student s = MainClass.getStudent(loggedInUser.getId());
		createNewStudentScene = addStudentScene(primaryStage, s.getEmail(), s.getPassword(), s.getName(), s.getYear());
	}

	//
	// Creating/adding
	//
	public Scene addCourseScene(Stage primaryStage) {
		KursStudimi kursiRi = new KursStudimi();

		Text sceneInfoText = sceneInfoText("Kursi i ri");

		TextField newCourseName = new TextField();

		Button saveCourseBtn = saveCourseButton(kursiRi, newCourseName, primaryStage);

		HBox newCourseNameFields = new HBox();
		newCourseNameFields.getChildren().addAll(new Label("Name: "), newCourseName, saveCourseBtn);
		newCourseNameFields.setAlignment(Pos.CENTER);
		// test
		TextField question = new TextField();
		TextField newTestName = new TextField();
		HBox newTestFields = new HBox();
		newTestFields.setAlignment(Pos.CENTER);
		// question
		ToggleGroup group = new ToggleGroup();
		RadioButton button1 = new RadioButton("Po");
		RadioButton button2 = new RadioButton("Jo");
		button1.setToggleGroup(group);
		button2.setToggleGroup(group);
		HBox newQuestionFields = new HBox();
		newQuestionFields.setSpacing(10);
		newQuestionFields.setAlignment(Pos.CENTER);

		VBox createNewCourseForm = new VBox();
		createNewCourseForm.setSpacing(20);
		createNewCourseForm.setAlignment(Pos.CENTER);

		Button saveTestBtn = saveTestButton(saveCourseBtn, newTestName);
		Button saveQuestionBtn = saveQuestionButton(kursiRi, question, group, saveTestBtn);
		Button saveTestNameBtn = saveTestNameButton(kursiRi, saveCourseBtn, newTestName, saveTestBtn, saveQuestionBtn);

		newTestFields.getChildren().addAll(new Label("Emri testit: "), newTestName, saveTestNameBtn, saveTestBtn);
		newQuestionFields.getChildren().addAll(new Label("Pyetja: "), question, new Label("Pergjigja: "), button1,
				button2, saveQuestionBtn);
		createNewCourseForm.getChildren().addAll(sceneInfoText, newCourseNameFields, newTestFields, newQuestionFields);

		return new Scene(createNewCourseForm, appWidth, appHeight);
	}

	public Scene addStaffScene(Stage primaryStage, String email, String password, String name, String title) {
		Text sceneInfoText = sceneInfoText("Plotesoni te dhenat per pedagogun " + name != null ? name : "e ri");

		TextField newUserEmail = new TextField(email != null ? email : "");
		PasswordField newUserPassword = new PasswordField();
		newUserPassword.setText(password != null ? password : "");
		TextField newUserName = new TextField(name != null ? name : "");

		HBox newUserNameFields = new HBox();
		newUserNameFields.getChildren().addAll(new Label("Name: "), newUserName);
		newUserNameFields.setAlignment(Pos.CENTER);
		HBox newUserEmailFields = new HBox();
		newUserEmailFields.getChildren().addAll(new Label("Email: "), newUserEmail);
		newUserEmailFields.setAlignment(Pos.CENTER);
		HBox newUserPasswordFields = new HBox();
		newUserPasswordFields.getChildren().addAll(new Label("Fjalekalimi: "), newUserPassword);
		newUserPasswordFields.setAlignment(Pos.CENTER);

		TextField newStaffTitle = new TextField(title != null ? title : "");

		HBox newStaffTitleFields = new HBox();
		newStaffTitleFields.getChildren().addAll(new Label("Title: "), newStaffTitle);
		newStaffTitleFields.setAlignment(Pos.CENTER);

		Button saveNewStaffBtn = saveNewStaffButton(primaryStage, newUserEmail, newUserPassword, newUserName,
				newStaffTitle, name);

		VBox createNewStaffForm = new VBox();
		createNewStaffForm.getChildren().addAll(sceneInfoText, newUserNameFields, newUserEmailFields,
				newUserPasswordFields, newStaffTitleFields, saveNewStaffBtn);
		createNewStaffForm.setSpacing(20);
		createNewStaffForm.setAlignment(Pos.CENTER);

		return new Scene(createNewStaffForm, appWidth, appHeight);

	}

	public Scene addStudentScene(Stage primaryStage, String email, String password, String name, Integer year) {
		Text sceneInfoText = sceneInfoText("Plotesoni te dhenat per studentin " + name != null ? name : "e ri");

		TextField newUserEmail = new TextField(email != null ? email : "");
		PasswordField newUserPassword = new PasswordField();
		newUserPassword.setText(password != null ? password : "");
		TextField newUserName = new TextField(name != null ? name : "");

		HBox newUserNameFields = new HBox();
		newUserNameFields.getChildren().addAll(new Label("Name: "), newUserName);
		newUserNameFields.setAlignment(Pos.CENTER);
		HBox newUserEmailFields = new HBox();
		newUserEmailFields.getChildren().addAll(new Label("Email: "), newUserEmail);
		newUserEmailFields.setAlignment(Pos.CENTER);
		HBox newUserPasswordFields = new HBox();
		newUserPasswordFields.getChildren().addAll(new Label("Fjalekalimi: "), newUserPassword);
		newUserPasswordFields.setAlignment(Pos.CENTER);
		System.out.println(year);
		ComboBox<Integer> newStudentYear = new ComboBox<>();
		newStudentYear.getItems().addAll(1, 2, 3, 4, 5);
		newStudentYear.setValue(year != null ? year : 1);

		HBox newStudentYearFields = new HBox();
		newStudentYearFields.getChildren().addAll(new Label("Year: "), newStudentYear);
		newStudentYearFields.setAlignment(Pos.CENTER);

		Button saveNewStudentBtn = saveNewStudentButton(primaryStage, newUserEmail, newUserPassword, newUserName,
				newStudentYear, name);

		VBox createNewStudentForm = new VBox();
		createNewStudentForm.getChildren().addAll(sceneInfoText, newUserNameFields, newUserEmailFields,
				newUserPasswordFields, newStudentYearFields, saveNewStudentBtn);
		createNewStudentForm.setSpacing(20);
		createNewStudentForm.setAlignment(Pos.CENTER);

		return new Scene(createNewStudentForm, appWidth, appHeight);

	}

	@SuppressWarnings("unchecked")
	public Scene addStudentsToCourseScene(Stage primaryStage) {
		Text txt = sceneInfoText("Shtoni student ne nje kurs");

		ComboBox<String> chooseCourse = new ComboBox<>();

		String[] coursesList = MainClass.getStaffCourses(2);
		for (int i = 0; i < coursesList.length; i++) {
			chooseCourse.getItems().add(coursesList[i]);
		}
		chooseCourse.setValue(coursesList[0]);

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
		Button addStudentsToCourseBtn = addStudentsToCourseButton(primaryStage, chooseCourse, table, tableCol1, checkBoxCol, idList);

		table.setItems(data);
		table.getColumns().addAll(tableCol1, tableCol2, checkBoxCol);

		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(txt, chooseCourse, addStudentsToCourseBtn, table);

		return new Scene(vbox, appWidth, appHeight);
	}

	public Scene createAdminScene(Stage primaryStage) {
		Text sceneInfoText = sceneInfoText("Llogari administratori");
		
		Button addStaffBtn = addStaffButton(primaryStage);
		Button addStudentBtn = addStudentButton(primaryStage);
		Button listStaffBtn = listStaffButton(primaryStage);
		Button listStudentBtn = listStudentsButton(primaryStage);
		Button signOutBtn = signOutButton(primaryStage);

		VBox adminToolbars = new VBox(20);
		adminToolbars.getChildren().addAll(sceneInfoText, addStaffBtn, addStudentBtn, listStaffBtn, listStudentBtn);
		adminToolbars.setAlignment(Pos.CENTER);

		BorderPane adminPage = new BorderPane();
		adminPage.setCenter(adminToolbars);
		adminPage.setBottom(signOutBtn);

		Scene adminScene = new Scene(adminPage, appWidth, appHeight);
		return adminScene;
	}

	public Scene createLoginScene(Stage primaryStage) {

		Text text = sceneLoginText("Hyni ne llogari!");
		Text errorMessage = sceneErrorText("No account with this email and password");

		TextField email = new TextField();
		PasswordField password = new PasswordField();

		ComboBox<String> chooseAccountType = new ComboBox<>();
		chooseAccountType.getItems().addAll(accountType.Student.toString(), accountType.Pedagog.toString(),
				accountType.Administrator.toString());
		chooseAccountType.setValue(accountType.Pedagog.toString());

		Button loginBtn = loginButton(primaryStage, errorMessage, email, password, chooseAccountType);

		HBox emailFields = new HBox();
		emailFields.getChildren().addAll(new Label("Email: "), email);
		emailFields.setAlignment(Pos.CENTER);
		HBox passwordFields = new HBox();
		passwordFields.getChildren().addAll(new Label("Fjalekalimi: "), password);
		passwordFields.setAlignment(Pos.CENTER);

		VBox loginForm = new VBox();
		loginForm.getChildren().addAll(text, emailFields, passwordFields, chooseAccountType, loginBtn, errorMessage);
		loginForm.setSpacing(20);
		loginForm.setAlignment(Pos.CENTER);

		return new Scene(loginForm, appWidth, appHeight);
	}

	public Scene createStaffScene(Stage primaryStage) {
		Text sceneInfoText = sceneInfoText("Llogari pedagogu");
		
		Button editStaffProfileBtn = editStaffProfileButton(primaryStage);
		Button addStudentToCourseBtn = addStudentToCourseButton(primaryStage);
		Button addCourseBtn = addCourseButton(primaryStage);
		Button showMessages = showMessagesButton(primaryStage);

		VBox staffToolbars = new VBox(20);
		staffToolbars.getChildren().addAll(sceneInfoText, editStaffProfileBtn, addCourseBtn, addStudentToCourseBtn, showMessages);
		staffToolbars.setAlignment(Pos.CENTER);

		BorderPane staffPage = new BorderPane();
		staffPage.setCenter(staffToolbars);
//		staffPage.setBottom(signOutBtn);

		return new Scene(staffPage, appWidth, appHeight);
	}

	public Scene createStudentScene(Stage primaryStage) {
		Text sceneInfoText = sceneInfoText("Llogari studenti");
		
		Button editStudentProfileBtn = new Button("Ndrysho profilin");
		editStudentProfileBtn.setOnAction(e -> {
			primaryStage.setScene(createNewStudentScene);

		});
		Button chooseTestBtn = new Button("Zgjidhni nje test");
		chooseTestBtn.setOnAction(e -> {
			primaryStage.setScene(coursesListScene);

		});
		Button listResultsBtn = new Button("Shfaq rezultatet");
		listResultsBtn.setOnAction(e -> {
		});
		Button createMessageBtn = new Button("Dergo nje mesazh pedagogut");
		createMessageBtn.setOnAction(e -> {

		});

		VBox studentToolbars = new VBox(20);
		studentToolbars.getChildren().addAll(sceneInfoText, editStudentProfileBtn, chooseTestBtn, listResultsBtn, createMessageBtn);
		studentToolbars.setAlignment(Pos.CENTER);

		BorderPane studentPage = new BorderPane();
		studentPage.setCenter(studentToolbars);
//		studentPage.setBottom(signOutBtn);

		return new Scene(studentPage, appWidth, appHeight);

	}

	//
	// listing
	//

	@SuppressWarnings("unchecked")
	public Scene listMessagesScene(Stage primaryStage) {
		Text mssText = sceneInfoText("Mesazhet tuaja");

		TableView<Mesazh> messagesTable = new TableView<>();
		// problem. Funksioni poshte duhet thirrur pas klikimit te butonin shfaqe
		// mesazhet
		//
		ObservableList<Mesazh> messages = FXCollections.observableArrayList(MainClass.getMessagesList(2));

		messagesTable.setEditable(true);
		messagesTable.setMaxWidth(300);

		TableColumn<Mesazh, Integer> messageId = new TableColumn<>("Id");
		messageId.setCellValueFactory(new PropertyValueFactory<Mesazh, Integer>("mesazhId"));
		TableColumn<Mesazh, String> studentName = new TableColumn<>("Studenti");
		studentName.setCellValueFactory(new PropertyValueFactory<Mesazh, String>("studentName"));
		TableColumn<Mesazh, String> messageContent = new TableColumn<>("Mesazhi");
		messageContent.setCellValueFactory(new PropertyValueFactory<Mesazh, String>("mesazhi"));

		messagesTable.setItems(messages);
		messagesTable.getColumns().addAll(messageId, studentName, messageContent);

		VBox messagesContainer = new VBox();
		messagesContainer.setSpacing(5);
		messagesContainer.setPadding(new Insets(10, 0, 0, 10));
		messagesContainer.getChildren().addAll(mssText, messagesTable);

		return new Scene(messagesContainer, appWidth, appHeight);

	}

	@SuppressWarnings("unchecked")
	public Scene coursesListScene(Stage primaryStage) {
		TableView<KursStudimi> enrolledCourseTbl = new TableView<>();
		ObservableList<KursStudimi> enrolledCourseData = FXCollections
				.observableArrayList(MainClass.getStudentCoursesList(2));

		enrolledCourseTbl.setEditable(true);
		enrolledCourseTbl.setMaxWidth(300);

		TableColumn<KursStudimi, Integer> courseIDCol = new TableColumn<>("Id");
		courseIDCol.setCellValueFactory(new PropertyValueFactory<KursStudimi, Integer>("kursId"));
		TableColumn<KursStudimi, String> courseNameCol = new TableColumn<>("Course Name");
		courseNameCol.setCellValueFactory(new PropertyValueFactory<KursStudimi, String>("emriKursit"));

		enrolledCourseTbl.setItems(enrolledCourseData);
		enrolledCourseTbl.getColumns().addAll(courseIDCol, courseNameCol);

		VBox tests = new VBox();
		VBox courseAndTests = new VBox();
		courseAndTests.getChildren().addAll(enrolledCourseTbl, tests);

		this.<KursStudimi>addButtonToTable(enrolledCourseTbl, "Shfaq Testet", tests, "CourseListTable", primaryStage);

		// tests list

		return new Scene(courseAndTests, appWidth, appHeight);

	}

	@SuppressWarnings("unchecked")
	private void listCourseTests(int kursId, VBox tests, Stage primaryStage) {
		tests.getChildren().clear();
		tests.getChildren().add(new Text("Testet per kete kurs: "));

		TableView<Test> enrolledTestsTbl = new TableView<>();
		ObservableList<Test> enrolledTestsData = FXCollections.observableArrayList(MainClass.getCourseTests(kursId));

		enrolledTestsTbl.setEditable(true);
		enrolledTestsTbl.setMaxWidth(300);

		TableColumn<Test, Integer> testIdCol = new TableColumn<>("Id");
		testIdCol.setCellValueFactory(new PropertyValueFactory<Test, Integer>("testId"));
		TableColumn<Test, String> testNameCol = new TableColumn<>("Test Name");
		testNameCol.setCellValueFactory(new PropertyValueFactory<Test, String>("emertimiTestit"));

		enrolledTestsTbl.setItems(enrolledTestsData);
		enrolledTestsTbl.getColumns().addAll(testIdCol, testNameCol);

		tests.getChildren().addAll(enrolledTestsTbl);
		this.<Test>addButtonToTable(enrolledTestsTbl, "Zgjidhni Testin", tests, "TestListTable", primaryStage);
	}

	@SuppressWarnings("unchecked")
	public <T> Scene getUsersList(String text, ArrayList<T> list, String Col1, String Col2, String Col3) {
		Text sceneInfoText = sceneInfoText("All " + text);

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

	@SuppressWarnings("unchecked")
	private void openTestScene(int testId, Stage primaryStage) {
		Test test = new Test();
		test.setPyetjet(MainClass.getTest(testId));

		VBox vbox = new VBox();

		TableView<Pyetje> questionsTbl = new TableView<>();
		ObservableList<Pyetje> questionsData = FXCollections.observableArrayList(MainClass.getTest(testId));

		questionsTbl.setEditable(true);
		questionsTbl.setMaxWidth(300);

		TableColumn<Pyetje, String> questionCol = new TableColumn<>("Pyetja");
		questionCol.setCellValueFactory(new PropertyValueFactory<Pyetje, String>("pyetja"));
		TableColumn<Pyetje, Boolean> answerCol = new TableColumn<>("Pergjigja");
		answerCol.setCellValueFactory(new PropertyValueFactory<Pyetje, Boolean>("selected"));
		answerCol.setCellFactory(CheckBoxTableCell.forTableColumn(answerCol));
		answerCol.setEditable(true);

		Button submitTest = new Button("Perfundoni testin");

		ArrayList<Integer> answersList = new ArrayList<>();
		submitTest.setOnAction(actionEvent -> {
			answersList.clear();
			for (int row = 0; row < questionsTbl.getItems().size(); row++) {
				answersList.add(answerCol.getCellData(row) ? 1 : 0);
			}

			String result = getTestPoints(answersList, MainClass.getTest(testId));
			vbox.getChildren().add(new Label("Rezultati juaj: " + result));
		});

		questionsTbl.setItems(questionsData);
		questionsTbl.getColumns().addAll(questionCol, answerCol);

		vbox.getChildren().addAll(questionsTbl, submitTest);
		testScene = new Scene(vbox, appWidth, appHeight);
		primaryStage.setScene(testScene);
	}

	//
	// table formating
	//

	public static int getAnswer(ToggleGroup group) {
		String pergjigja = ((RadioButton) group.getSelectedToggle()).getText();
		if (pergjigja == "Po")
			return 1;
		else if (pergjigja == "Jo")
			return 0;
		return 0;
	}

	public String getTestPoints(ArrayList<Integer> answersList, LinkedList<Pyetje> questions) {
		final int POINT_PER_QUESTION = 1;
		final int TOTAL_POINTS = POINT_PER_QUESTION * questions.size();

		int testScore = 0;
		for (int i = 0; i < questions.size(); i++) {
			if (answersList.get(i) == questions.get(i).getPergjigjja()) {
				testScore++;
			}
		}

		return "" + testScore + "/" + TOTAL_POINTS;
	}

	public int getRowId(String data) {
		int kursId = -1;
		Pattern p = Pattern.compile("\\d+");
		try {
			Matcher m = p.matcher(data);
			if (m.find()) {
				kursId = Integer.parseInt(m.group());
			}
		} catch (Exception ex) {

		}
		return kursId;
	}

	public <T> void addButtonToTable(TableView<T> table, String buttonText, VBox tests, String tableName,
			Stage primaryStage) {
		TableColumn<T, Void> colBtn = new TableColumn<>("Button Column");

		Callback<TableColumn<T, Void>, TableCell<T, Void>> cellFactory = new Callback<TableColumn<T, Void>, TableCell<T, Void>>() {
			@Override
			public TableCell<T, Void> call(final TableColumn<T, Void> param) {
				final TableCell<T, Void> cell = new TableCell<T, Void>() {

					private final Button btn = new Button(buttonText);

					{
						btn.setOnAction(e -> {
							String data = getTableView().getItems().get(getIndex()).toString();
							int id = getRowId(data);

							if (tableName == "CourseListTable")
								listCourseTests(id, tests, primaryStage);
							else
								openTestScene(id, primaryStage);

						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btn);
						}
					}
				};
				return cell;
			}
		};

		colBtn.setCellFactory(cellFactory);

		table.getColumns().add(colBtn);

	}

	//
	// History
	//

	public void pushHistory(Stage primaryStage, Scene scene) {
		scenesHistory.add(scene);
		historyIndex++;
		primaryStage.setScene(scene);
	}

	//
	// text format
	//

	public Text sceneLoginText(String str) {
		Text text = new Text(str);
		text.setFont(Font.font("Abyssinica SIL", FontWeight.BOLD, FontPosture.REGULAR, 35));
		text.setFill(Color.GREEN);// setting color of the text to blue
		text.setStroke(Color.GREEN);// setting the stroke for the text
		return text;
	}

	public Text sceneErrorText(String str) {
		Text errorMessage = new Text(str);
		errorMessage.setFill(Color.RED);
		errorMessage.setStroke(Color.RED);
		errorMessage.setVisible(false);
		return errorMessage;
	}

	public Text sceneInfoText(String str) {
		Text sceneInfoText = new Text(str);
		sceneInfoText.setFont(Font.font("Abyssinica SIL", FontWeight.BOLD, FontPosture.REGULAR, 25));
		sceneInfoText.setFill(Color.BLUE);// setting color of the text to blue
		sceneInfoText.setStroke(Color.BLUE);// setting the stroke for the text
		return sceneInfoText;
	}

	//
	// Buttons
	//
	public Button loginButton(Stage primaryStage, Text errorMessage, TextField email, PasswordField password,
			ComboBox<String> chooseAccountType) {
		Button loginBtn = new Button("Logohuni");
		loginBtn.setOnAction(e -> {
			loggedInUser = MainClass.logIntoSystem(chooseAccountType.getValue(), email.getText(), password.getText());
			if (loggedInUser != null) {
				if (chooseAccountType.getValue() == "Administrator") {
					initializeAdminScenes(primaryStage);
					primaryStage.setScene(adminScene);
				} else if (chooseAccountType.getValue() == "Student") {
					initializeStudentScenes(primaryStage);
					primaryStage.setScene(studentScene);
				} else if (chooseAccountType.getValue() == "Pedagog") {
					initializeStaffScenes(primaryStage);
					primaryStage.setScene(staffScene);
				}
			} else {
				errorMessage.setVisible(true);
			}
		});
		return loginBtn;
	}

	public Button saveTestNameButton(KursStudimi kursiRi, Button saveCourseBtn, TextField newTestName,
			Button saveTestBtn, Button saveQuestionBtn) {
		Button saveTestNameBtn = new Button("Ruani emrin e testit");
		saveTestNameBtn.setOnAction(e -> {
			kursiRi.getTests().addFirst(new Test(newTestName.getText()));
			saveCourseBtn.setDisable(true);
			saveTestBtn.setDisable(true);
			newTestName.setDisable(true);
			saveQuestionBtn.setDisable(false);
		});
		return saveTestNameBtn;
	}

	public Button saveQuestionButton(KursStudimi kursiRi, TextField question, ToggleGroup group, Button saveTestBtn) {
		Button saveQuestionBtn = new Button("Ruani pyetjen");
		saveQuestionBtn.setOnAction(e -> {
			// ruajme pyetjen
			Pyetje q = new Pyetje(question.getText(), getAnswer(group));

			kursiRi.getTests().getFirst().getPyetje().addFirst(q);

			saveTestBtn.setDisable(false);
			// reset the question
			question.setText("");
		});
		saveQuestionBtn.setDisable(true);
		return saveQuestionBtn;
	}

	public Button saveTestButton(Button saveCourseBtn, TextField newTestName) {
		Button saveTestBtn = new Button("Ruani testin");
		saveTestBtn.setOnAction(e -> {
			saveCourseBtn.setDisable(false);
			newTestName.setDisable(false);
			newTestName.setText("");
		});
		saveTestBtn.setDisable(true);
		return saveTestBtn;
	}

	public Button saveCourseButton(KursStudimi kursiRi, TextField newCourseName, Stage primaryStage) {
		Button saveCourseBtn = new Button("Ruani kursin");
		saveCourseBtn.setDisable(true);
		saveCourseBtn.setOnAction(e -> {
			kursiRi.setEmriKursit(newCourseName.getText());
			MainClass.addCourseToStaff(kursiRi, getLoginType(), (Pedagog) loggedInUser);
			primaryStage.setScene(staffScene);
			newCourseName.setText("");
		});
		return saveCourseBtn;
	}

	public Button saveNewStaffButton(Stage primaryStage, TextField newUserEmail, PasswordField newUserPassword,
			TextField newUserName, TextField newStaffTitle, String name) {

		if (name == null) {
			Button saveNewStaffBtn = new Button("Ruani te dhenat");

			saveNewStaffBtn.setOnAction(e -> {
				Pedagog newStaff = new Pedagog(newUserName.getText(), newUserEmail.getText(), newUserPassword.getText(),
						newStaffTitle.getText());
				MainClass.createNewStaff(newStaff);
				primaryStage.setScene(adminScene);
			});
			return saveNewStaffBtn;
		} else {
			Button updateStaffBtn = new Button("Ndryshoni te dhenat");

			updateStaffBtn.setOnAction(e -> {
				loggedInUser = new Pedagog(loggedInUser.getId(), newUserName.getText(), newUserEmail.getText(),
						newUserPassword.getText(), newStaffTitle.getText());
				MainClass.updateStaff((Pedagog) loggedInUser);
				primaryStage.setScene(staffScene);
			});

			return updateStaffBtn;
		}
	}

	public Button saveNewStudentButton(Stage primaryStage, TextField newUserEmail, PasswordField newUserPassword,
			TextField newUserName, ComboBox<Integer> newStudentYear, String name) {
		if (name == null) {
			Button saveNewStudentBtn = new Button("Ruani te dhenat");

			saveNewStudentBtn.setOnAction(e -> {
				Student s = new Student(newUserName.getText(), newUserEmail.getText(), newUserPassword.getText(),
						newStudentYear.getValue());
				MainClass.createNewStudent(s);
				primaryStage.setScene(adminScene);
			});

			return saveNewStudentBtn;
		} else {
			Button updateStudentBtn = new Button("Ndryshoni te dhenat");

			updateStudentBtn.setOnAction(e -> {
				loggedInUser = new Student(loggedInUser.getId(), newUserName.getText(), newUserEmail.getText(),
						newUserPassword.getText(), newStudentYear.getValue());
				MainClass.updateStudent((Student) loggedInUser);
				primaryStage.setScene(studentScene);
			});

			return updateStudentBtn;
		}
	}

	public Button addStudentsToCourseButton(Stage primaryStage, ComboBox<String> chooseCourse, TableView<Student> table,
			TableColumn<Student, Integer> tableCol1, TableColumn<Student, Boolean> checkBoxCol,
			ArrayList<Integer> idList) {
		Button addStudentsToCourseBtn = new Button("Shtoni studentet e zgjedhur ne kurs");
		addStudentsToCourseBtn.setOnAction(actionEvent -> {
			idList.clear();
			for (int row = 0; row < table.getItems().size(); row++) {
				if (checkBoxCol.getCellData(row)) {
					idList.add(tableCol1.getCellData(row));
				}
			}
			System.out.println(idList);
			MainClass.addStudentsToCourse(idList, chooseCourse.getValue());
			primaryStage.setScene(staffScene);
		});
		return addStudentsToCourseBtn;
	}

	public Button signOutButton(Stage primaryStage) {
		Button signOutBtn = new Button("Dilni nga llogaria");

		signOutBtn.setOnAction(e -> {
			primaryStage.setScene(loginScene);
		});
		return signOutBtn;
	}

	public Button listStudentsButton(Stage primaryStage) {
		Button listStudentBtn = new Button("Afisho listen e studenteve");
		listStudentBtn.setOnAction(e -> {
			primaryStage.setScene(listStudentsScene);
		});
		return listStudentBtn;
	}

	public Button listStaffButton(Stage primaryStage) {
		Button listStaffBtn = new Button("Afisho listen e pedagogeve");
		listStaffBtn.setOnAction(e -> {
			primaryStage.setScene(listStaffScene);
		});
		return listStaffBtn;
	}

	public Button addStudentButton(Stage primaryStage) {
		Button addStudentBtn = new Button("Shto student te ri");
		addStudentBtn.setOnAction(e -> {
			primaryStage.setScene(createNewStudentScene);
		});
		return addStudentBtn;
	}

	public Button addStaffButton(Stage primaryStage) {
		Button addStaffBtn = new Button("Shto pedagog te ri");
		addStaffBtn.setOnAction(e -> {
			primaryStage.setScene(createNewStaffScene);
		});
		return addStaffBtn;
	}

	public Button showMessagesButton(Stage primaryStage) {
		Button showMessages = new Button("Shiko mesazhet");
		showMessages.setOnAction(e -> {
			primaryStage.setScene(messagesScene);

		});
		return showMessages;
	}

	public Button addCourseButton(Stage primaryStage) {
		Button addCourseBtn = new Button("Krijo nje kurs");
		addCourseBtn.setOnAction(e -> {
			primaryStage.setScene(createNewCourseScene);
		});
		return addCourseBtn;
	}

	public Button addStudentToCourseButton(Stage primaryStage) {
		Button addStudentToCourseBtn = new Button("Shtoni student ne kurs");
		addStudentToCourseBtn.setOnAction(e -> {
			primaryStage.setScene(addingStudentsToCourseScene);
		});
		return addStudentToCourseBtn;
	}

	public Button editStaffProfileButton(Stage primaryStage) {
		Button editStaffProfileBtn = new Button("Ndrysho profilin");
		editStaffProfileBtn.setOnAction(e -> {
			primaryStage.setScene(createNewStaffScene);
		});
		return editStaffProfileBtn;
	}

	public Button goBackButton(Stage primaryStage) {
		Button goBackBtn = new Button("Back");

		goBackBtn.setOnAction(e -> {
			primaryStage.setScene(scenesHistory.get(--historyIndex));
		});

		return goBackBtn;
	}

	public Button goForwardButton(Stage primaryStage) {
		Button goForwardBtn = new Button("Forward");

		goForwardBtn.setOnAction(e -> {
			primaryStage.setScene(scenesHistory.get(++historyIndex));
		});

		return goForwardBtn;
	}

	//
	// Main
	//

	public static void main(String[] args) {
		MainClass.connectToDB();
		launch(args);
	}
}
