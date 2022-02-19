import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.AfterClass;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class LoginTest {
    public static LoginPage loginPage;
    public static ProfilePage profilePage;
    public static WebDriver driver;

    /**
     * осуществление первоначальной настройки
     */
    @BeforeClass
    public static void setup() {
        //определение пути до драйвера и его настройка
        System.setProperty("webdriver.chrome.driver", "chromedriver/chromedriver.exe");
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        profilePage = new ProfilePage(driver);
        //окно разворачивается на полный экран
        driver.manage().window().maximize();
        //задержка на выполнение теста = 10 сек.
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //получение ссылки на страницу входа из файла настроек
        driver.get(AppProperties.getProperty("loginpage"));
    }
    /**
     * тестовый метод для осуществления аутентификации
     */
    @Test
    public void loginTest() throws Exception {
        //получение доступа к методам класса LoginPage для взаимодействия с элементами страницы
        //значение login/password берется из файла настроек по аналогии с loginpage
        //вводим логин
        loginPage.inputLogin(AppProperties.getProperty("login"));
        //вводим пароль
        loginPage.inputPasswd(AppProperties.getProperty("password"));
        //нажимаем кнопку входа
        loginPage.clickLoginBtn();
        //нажимаем кнопку Users
        profilePage.entryUsers();
        //нажимаем кнопку Players
        profilePage.entryPlayers();
        //нажимаем кнопку сортировки Registration date
        profilePage.entryRegistration_date();
        //получаем дату первого пользователя
        String first = profilePage.getFirstDate();
        //получаем дату второго пользователя
        String second = profilePage.getSecondDate();
        //получаем дату третьего пользователя
        String third = profilePage.getThirdDate();
        //сравниваем даты и проверяем сортировку
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        Date date1 = format1.parse(first);
        DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        Date date2 = format2.parse(second);
        DateFormat format3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        Date date3 = format3.parse(third);
        int result1 = date1.compareTo(date2);
        int result2 = date2.compareTo(date3);
        if (result1 < 0 & result2 < 0) {
            System.out.println("Корректная сортировка");
        }
        else {
            throw new RuntimeException("Некорректная сортировка");
        }
    }
    /**
     * закрытие окна браузера
     */
    @AfterClass
    public static void tearDown() {
        driver.quit();
    }
}