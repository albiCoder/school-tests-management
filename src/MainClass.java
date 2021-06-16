import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;


public class MainClass {

	private static PreparedStatement preparedStatement = null;
	private static Connection connection = null;

	public static void connectToDB() {
		connection = JDBCMySQLConnection.getConnection();
	}

	public static Student getStudent(int studentId) {
		ResultSet rs = null;

		Student s = null;
		String query = "SELECT * FROM student WHERE studentid = ?";

		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, studentId);

			rs = preparedStatement.executeQuery(query);

			if (rs.next()) {
				s = new Student();
				s.setId(rs.getInt("studentId"));
				s.setName(rs.getString("name"));
				s.setEmail(rs.getString("email"));
				s.setPassword(rs.getString("password"));
				s.setYear(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return s;
	}

	public static Perdorues logIntoSystem(String loginAccountType, String email, String password) {

		// perdora statement pasi nuk mund te vendos emrin e tabeles me ane te
		// preparedStatement
		String query = "SELECT * FROM " + loginAccountType + " WHERE email = ? AND password = ? ;";

		Perdorues user = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				if (loginAccountType == "Administrator") {
					user = new Administrator();
				} else if (loginAccountType == "Student") {
					user = new Student();
				} else if (loginAccountType == "Pedagog") {
					user = new Pedagog();
				}
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
			} else {
				System.out.println("No user found!");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

	public static void createNewStaff(Pedagog newStaff) {
		String query = "INSERT INTO pedagog(name, email, password, titulli) VALUES(?, ?, ?, ?)";
		try {
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, newStaff.getName());
			preparedStatement.setString(2, newStaff.getEmail());
			preparedStatement.setString(3, newStaff.getPassword());
			preparedStatement.setString(4, newStaff.getTitulli());

			preparedStatement.executeQuery();
			System.out.println("New staff member added into database");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void createNewStudent(Student newStudent) {
		String query = "INSERT INTO student (name, email, password, year) VALUES(?, ?, ?, ?)";
		try {
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, newStudent.getName());
			preparedStatement.setString(2, newStudent.getEmail());
			preparedStatement.setString(3, newStudent.getPassword());
			preparedStatement.setInt(4, newStudent.getYear());

			preparedStatement.executeUpdate();
			System.out.println("New student added into database");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Pedagog> getStaffList() {
		String query = "SELECT * FROM pedagog";

		ArrayList<Pedagog> staffList = new ArrayList<>();
		try {
			preparedStatement = connection.prepareStatement(query);

			ResultSet rs = preparedStatement.executeQuery();
			Pedagog user;
			while (rs.next()) {
				if (rs.getInt("id") == 0)
					continue;

				user = new Pedagog();
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setTitulli(rs.getString("titulli"));
				staffList.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return staffList;
	}

//	public static <T extends Perdorues> ArrayList<T> getStaffList(T user) {
//		String query = "SELECT * FROM pedagog";
//		
//		ArrayList<T> staffList = new ArrayList<>();
//		try {
//			preparedStatement = connection.prepareStatement(query);
//			
//			ResultSet rs = preparedStatement.executeQuery();
////			T user;
//			while (rs.next()) {
//				if(rs.getInt("id") == 0) continue;
//				
//				user =(T) new Perdorues();
//				user.setName(rs.getString("name"));
//				user.setEmail(rs.getString("email"));
//				user.setTitulli(rs.getString("titulli"));
//				staffList.add(user);
//			} 
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return staffList;
//	}

	public static ArrayList<Student> getStudentsList() {
		String query = "SELECT * FROM student";

		ArrayList<Student> studentsList = new ArrayList<>();
		try {
			preparedStatement = connection.prepareStatement(query);

			ResultSet rs = preparedStatement.executeQuery();
			Student user;
			while (rs.next()) {
				if (rs.getInt("id") == 0)
					continue;

				user = new Student();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setYear(rs.getInt("year"));
				studentsList.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return studentsList;
	}

	public static void addCourseToStaff(KursStudimi kurs, String loginAccountType, Pedagog user) {
		String insertKursQry = "INSERT INTO kursstudimi(emriKursit, pedagogId) VALUES(?, ?)";
		String insertTestQry = "INSERT INTO test(emertimiTestit, kursId) VALUES(?, ?)";
		String insertQuestionQry = "INSERT INTO pyetje(pyetja, pergjigjja, testId) VALUES(?, ?, ?)";
		String getLastInsertedIdQry = "SELECT LAST_INSERT_ID()";

		System.out.println(kurs.toString());
		try {
			// add course
			preparedStatement = connection.prepareStatement(insertKursQry);
			preparedStatement.setString(1, kurs.getEmriKursit());
			preparedStatement.setInt(2, user.getId());
			preparedStatement.executeUpdate();

			// get course id
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(getLastInsertedIdQry);
			if (rs.next()) {
				kurs.setKursId(Integer.parseInt(rs.getString(1)));
			}

			// add tests
			preparedStatement = connection.prepareStatement(insertTestQry);
			Test test;
			Pyetje pyetje;
			PreparedStatement questionPreStat = connection.prepareStatement(insertQuestionQry);
			statement = connection.prepareStatement(insertQuestionQry);
			for (int i = 0; i < kurs.getTests().size(); i++) {
				test = kurs.getTests().get(i);
				preparedStatement.setString(1, test.getEmertimiTestit());
				preparedStatement.setInt(2, kurs.getKursId());
				preparedStatement.executeUpdate();
				// get test id
				rs = statement.executeQuery(getLastInsertedIdQry);
				if (rs.next()) {
					test.setTestId(Integer.parseInt(rs.getString(1)));
				}
				// add test's questions
				for (int j = 0; j < test.getPyetje().size(); j++) {
					pyetje = test.getPyetje().get(j);
					questionPreStat.setString(1, pyetje.getPyetja());
					questionPreStat.setInt(2, pyetje.getPergjigjja());
					questionPreStat.setInt(3, test.getTestId());
					questionPreStat.executeUpdate();
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Course adding failed");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Course adding failed");
		}

	}

	public static String[] getStaffCourses(int userId) {
		String query = "SELECT DISTINCT emriKursit FROM kursStudimi NATURAL JOIN pedagog WHERE pedagogId=?";
		String getStudentsCount = "SELECT COUNT(kursId) FROM kursStudimi";
		String[] coursesList = null;
		try {
			Statement stat = connection.createStatement();

			ResultSet rs = stat.executeQuery(getStudentsCount);
			if (rs.next()) {
				coursesList = new String[Integer.parseInt(rs.getString(1))];
			} else {
				System.out.println("The current staff member does not have any course created");
				return null;
			}
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, userId);
			rs = preparedStatement.executeQuery();
			int i = 0;
			while (rs.next()) {
				coursesList[i] = rs.getString("emriKursit");
				i++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return coursesList;
	}

//	public static ArrayList<KursStudimi> getStaffCourses(int userId) {
//		String query = "SELECT DISTINCT kursId, emriKursit FROM kursStudimi NATURAL JOIN pedagog WHERE pedagogId=?";
//		String getStudentsCount = "SELECT COUNT(kursId) FROM kursStudimi";
//		ArrayList<KursStudimi> coursesList = null;
//		try {
//			Statement stat = connection.createStatement();
//			
//			ResultSet rs = stat.executeQuery(getStudentsCount);
//			if(rs.next()) {
//				coursesList = new ArrayList<>(Integer.parseInt(rs.getString(1)));
//			} else {
//				System.out.println("The current staff member does not have any course created");
//				return null;
//			}
//			preparedStatement = connection.prepareStatement(query);
//			preparedStatement.setInt(1, userId);
//			rs = preparedStatement.executeQuery();
//
//			KursStudimi kurs;
//			while (rs.next()) {
//				kurs = new KursStudimi(rs.getInt("kursId"), rs.getString("emriKursit"));
//				coursesList.add(kurs);
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return coursesList;
//	}

	public static void addStudentsToCourse(ArrayList<Integer> idList, String courseName) {
		String query = "INSERT INTO rregjistri(studentId, kursId) VALUES(?, ?);";
		String getCourseId = "SELECT kursId FROM `kursstudimi` WHERE emriKursit LIKE ?";

		int courseId = -1;

		try {
			preparedStatement = connection.prepareStatement(getCourseId);
			preparedStatement.setString(1, courseName);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				courseId = rs.getInt("kursId");
			}

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(2, courseId);
			for (int k = 0; k < idList.size(); k++) {
				preparedStatement.setInt(1, idList.get(k));
				preparedStatement.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static ArrayList<Mesazh> getMessagesList(int staffId) {
		String query = "SELECT mesazhId, name, teksti FROM `mesazh` LEFT JOIN student ON studentId=id WHERE pedagogId=?;";

		ArrayList<Mesazh> messagesList = new ArrayList<>();
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, staffId);
			ResultSet rs = preparedStatement.executeQuery();

			Mesazh message;
			while (rs.next()) {
				message = new Mesazh();
				message.setMesazhId(rs.getInt("mesazhId"));
				message.setStudent(new Student(rs.getString("name")));
				message.setMesazhi(rs.getString("teksti"));
				messagesList.add(message);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return messagesList;
	}

	public static ArrayList<KursStudimi> getStudentCoursesList(int studentId) {

		String query = "SELECT DISTINCT(kursstudimi.kursId), kursstudimi.emriKursit FROM `student` INNER JOIN rregjistri ON "
				+ "student.id = rregjistri.studentId INNER JOIN kursstudimi ON rregjistri.kursId = kursstudimi.kursId WHERE student.id = ?";
		
		ArrayList<KursStudimi> coursesList = new ArrayList<>();
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, studentId);
			ResultSet rs = preparedStatement.executeQuery();

			KursStudimi kurs;
			while (rs.next()) {
				kurs = new KursStudimi(rs.getInt("kursId"), rs.getString("emriKursit"));
			
				coursesList.add(kurs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return coursesList;
	}
	
	public static ArrayList<Test> getCourseTests(int kursId) {
		String query = "SELECT * FROM test WHERE kursId = ?";
		
		ArrayList<Test> testsList = new ArrayList<>();
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, kursId);
			ResultSet rs = preparedStatement.executeQuery();

			Test test;
			while (rs.next()) {
				test = new Test(rs.getInt("testId"), rs.getString("emertimiTestit"));
			
				testsList.add(test);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return testsList;
	}

	public static LinkedList<Pyetje> getTest(int testId) {
		String query = "SELECT * FROM pyetje WHERE testId = ?";

		LinkedList<Pyetje> questionsList = new LinkedList<>();
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, testId);
			ResultSet rs = preparedStatement.executeQuery();

			Pyetje p;
			while (rs.next()) {
				p = new Pyetje(rs.getInt("pyetjeId"), rs.getString("pyetja"), rs.getInt("pergjigjja"));
			
				questionsList.add(p);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return questionsList;	
	}
}