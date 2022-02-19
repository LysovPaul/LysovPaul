import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProfilePage {
    /**
     * конструктор класса, занимающийся инициализацией полей класса
     */
    public WebDriver driver;
    public ProfilePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver; }
    /**
     * определение локатора меню Users
     */
    @FindBy(xpath = "//span[.='Users']")
    private WebElement menuUsers;
    /**
     * определение локатора меню Players
     */
    @FindBy(xpath = "(//*[contains(text(),'Players')])[1]")
    private WebElement menuPlayers;
    /**
     * определение локатора кнопки Registration date
     */
    @FindBy(xpath = "//a[contains(text(),'Registration date')]")
    private WebElement buttonRegistration_date;
    /**
     * определение локатора даты первого пользователя
     */
    @FindBy(xpath = "(//div[@id='payment-system-transaction-grid']/*/tbody/tr[1]/*[10])")
    private WebElement firstDateTable;
    /**
     * определение локатора даты второго пользователя
     */
    @FindBy(xpath = "(//div[@id='payment-system-transaction-grid']/*/tbody/tr[2]/*[10])")
    private WebElement secondDateTable;
    /**
     * определение локатора даты третьего пользователя
     */
    @FindBy(xpath = "(//div[@id='payment-system-transaction-grid']/*/tbody/tr[3]/*[10])")
    private WebElement thirdDateTable;
    /**
     * метод для нажатия кнопки Users
     */
    public void entryUsers() {
        menuUsers.click(); }
    /**
     * метод для нажатия кнопки Players
     */
    public void entryPlayers() {
        menuPlayers.click(); }
    /**
     * метод для нажатия кнопки сортировки Registration date
     */
    public void entryRegistration_date() {
        buttonRegistration_date.click(); }
    /**
     * метод для получения даты первого пользователя
     */
    public String getFirstDate() throws InterruptedException {
        //таймаут для загрузки страницы
        Thread.sleep(2000);
        String firstDate = firstDateTable.getText();
        return firstDate; }
    /**
     * метод для получения даты второго пользователя
     */
    public String getSecondDate() {
        String secondDate = secondDateTable.getText();
        return secondDate; }
    /**
     * метод для получения даты третьего пользователя
     */
    public String getThirdDate() {
        String thirdDate = thirdDateTable.getText();
        return thirdDate; }
}