import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SignInController {

	private WebDriver driver;
	private FileReader profileReader;
	private File newProfile;
	private String username;
	private String password;

	public static final String COURSEWORKS_URL = "https://courseworks.columbia.edu/welcome/";
	public static final String SSOL_URL = "https://ssol.columbia.edu/";

	public SignInController() {
		try {
			profileReader = new FileReader("profile.txt");
			Scanner scan = new Scanner(profileReader);
			while (scan.hasNext()) {
				username = scan.nextLine();
				password = scan.nextLine();
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println("No existing profile found.");
			username = "";
			password = "";
		}
	}

	public void run(String page) {
		ReentrantLock lock = new ReentrantLock();
		if(driver != null) {
			driver.quit();
		}
		driver = new ChromeDriver();
		lock.lock();
		if (page.equals("courseworks")) {
			courseworks();
		} else if (page.equals("ssol")) {
			ssol();
		}
		lock.unlock();
	}

	public void courseworks() {
		driver.get(COURSEWORKS_URL);
		WebElement login = driver.findElement(By.linkText("Log In"));
		login.click();

		WebElement usernameElement = driver.findElement(By.name("username"));
		usernameElement.sendKeys(username);

		WebElement passwordElement = driver.findElement(By.name("password"));
		passwordElement.sendKeys(password);
		passwordElement.submit();
	}

	public void ssol() {
		driver.get(SSOL_URL);
		WebElement usernameElement = driver.findElement(By
				.cssSelector("input[name=u_id]"));

		WebElement passwordElement = driver.findElement(By
				.cssSelector("input[name=u_pw]"));

		usernameElement.sendKeys(username);
		passwordElement.sendKeys(password);
		passwordElement.submit();
		
		WebElement gradesLink = driver.findElement(By.linkText("Grades and Registration Status"));
		gradesLink.click();
	}

	public void setProfile(String username, String password)
			throws FileNotFoundException {
		this.username = username;
		this.password = password;
		PrintWriter out = new PrintWriter(new File("profile.txt"));
		out.println(username);
		out.println(password);
		out.close();

	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}
