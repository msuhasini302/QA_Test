package LoginSetup;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class LoginFunctionality {
	
		private WebDriver driver;
	
	@FindBy(xpath="//input[@class='inputtext _55r1 inputtext _1kbt inputtext _1kbt']")
	private WebElement usernameInput;
	
	@FindBy(xpath="//input[@class='inputtext _55r1 inputtext _9npi inputtext _9npi']")
	private WebElement passwordInput;
	
	@FindBy(xpath="//button[@id='loginbutton']")
	private WebElement loginButton;
	
	public LoginFunctionality(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver,this);
		WebDriverWait wait= new WebDriverWait(driver,Duration.ofMinutes(2));
		wait.until(ExpectedConditions.visibilityOf(usernameInput));
        wait.until(ExpectedConditions.visibilityOf(passwordInput));
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
		
	}
	
	public void enterUsername(String username) throws Exception {
		if(usernameInput==null) {
			throw new Exception("usernameInput element is not inialized");
		}
		usernameInput.sendKeys(username);
	}
	public void enterPassword(String password) throws Exception {
		if(passwordInput==null) {
			throw new Exception("passwordInput element is not inialized");
		}
		
		passwordInput.sendKeys(password);
		
	}
	public void clickLoginButton() throws Exception {
		if(loginButton==null) {
			throw new Exception("LoginButton element is not inialized");
		}
		loginButton.click();
	}
	
	

}
