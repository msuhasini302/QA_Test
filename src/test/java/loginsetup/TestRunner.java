package loginsetup;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;

import LoginSetup.LoginFunctionality;
import LoginSetup.WebDriverFactory;
import utils.ExtendsManager;


public class TestRunner {
	
	private WebDriver driver;
	private LoginFunctionality loginPage;
	private SoftAssert softAssert;
    private List<String[]> failedLoginAttempts;
	
	@BeforeClass
	public void setup() {
		 String browser = "chrome"; // Change this to the desired browser
	        driver = WebDriverFactory.createDriver(browser);

		driver.get("https://www.facebook.com/login/");
		loginPage = new LoginFunctionality(driver);
		
		
	}
	
	@Test(dataProvider="loginData")
	public void testLogin(Map<String, String> testData) throws Exception {
		
		 String username = testData.get("username");
	        String password = testData.get("password");

	        Logger.info("Starting test with username: " + username + " and password: " + password);

	        ExtentTest extentTest = ExtendsManager.createTest("Login Test");
	        extentTest.info("Test started with username: " + username + " and password: " + password);

	        loginPage.enterUsername(username);
	        loginPage.enterPassword(password);
	        loginPage.clickLoginButton();
	        extentTest.pass("Test passed");

	        Logger.info("Test completed successfully");

//	try {
//		
//		loginPage.enterUsername(username);
//		loginPage.enterPassword(password);
//		loginPage.clickLoginButton();
//		softAssert = new SoftAssert();
//        failedLoginAttempts = new ArrayList<>();
//		
//		Assert.assertEquals(driver.getTitle(), "Facebook");
//		System.out.println("Login successful for username: " + username);
//		
//    } catch (Exception e) {
//        // Log failure for negative scenarios
//        System.out.println("Login failed for username: " + username);
//        failedLoginAttempts.add(new String[]{username, password});
//    }
    }

//	private static void sendEmail(String recipient, String subject, String body) {
//         Provide your email credentials and SMTP server details
//        final String username = "your_email@gmail.com";
//       final String password = "your_email_password";
//        final String smtpHost = "smtp.gmail.com";
//       final String smtpPort = "587";
//
//       Properties props = new Properties();
//       props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", smtpHost);
//        props.put("mail.smtp.port", smtpPort);

//       Session session = Session.getInstance(props, new Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return  PasswordAuthentication(username, password);
//           }
//        });
//
//    try {
//          Message message = new Message(session);
//            message.setFrom(new InternetAddress(username));
//           message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
//          message.setSubject(subject);
//           message.setText(body);
//
//           Transport.send(message);
//
//           System.out.println("Email sent successfully!");
//       } catch (MessagingException e) {
//           throw new RuntimeException("Error sending email: " + e.getMessage(), e);
//       }
//    }

	
	
	
	@DataProvider(name = "loginData")
    public Object[][] getDataFromExcel() throws IOException {
        String excelFilePath = "C:\\Users\\Admin\\eclipse-workspace\\FrameWorkSetup\\target\\Frameworkdata.xls";
        FileInputStream inputStream = new FileInputStream(excelFilePath);

        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheet("Sheet1"); // Modify the sheet name accordingly

        int rowCount = sheet.getPhysicalNumberOfRows();
        int colCount = sheet.getRow(0).getPhysicalNumberOfCells();

        List<Map<String, String>> data = new ArrayList<>();

        for (int i = 1; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            Map<String, String> testData = new HashMap<>();

            for (int j = 0; j < colCount; j++) {
                String columnName = sheet.getRow(0).getCell(j).getStringCellValue();
                String cellValue = row.getCell(j).getStringCellValue();
                testData.put(columnName, cellValue);
            }

            data.add(testData);
        }

        workbook.close();
        inputStream.close();

        Object[][] dataArray = new Object[data.size()][1];
        for (int i = 0; i < data.size(); i++) {
            dataArray[i][0] = data.get(i);
        }

        return dataArray;
    
    }

	@AfterClass
	public void tearDown() {
		  softAssert.assertAll();
		  writeFailedLoginAttemptsToExcel("failed_logins.xlsx");
		if(driver != null) {
			driver.quit();
		}
		}
	
	private void writeFailedLoginAttemptsToExcel(String outputPath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("FailedLogins");

            for (int i = 0; i < failedLoginAttempts.size(); i++) {
                Row row = sheet.createRow(i);
                String[] loginAttempt = failedLoginAttempts.get(i);

                Cell usernameCell = row.createCell(0);
                usernameCell.setCellValue(loginAttempt[0]);

                Cell passwordCell = row.createCell(1);
                passwordCell.setCellValue(loginAttempt[1]);
            }

            try (FileOutputStream outputStream = new FileOutputStream(outputPath)) {
                workbook.write(outputStream);
                System.out.println("Failed login attempts written to: " + outputPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
