import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @author qyyzxty@icloud.com
 * @data 2021/4/7
 **/
public class Bilibili {

    public static void main(String[] args) {
        //设置驱动
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\qyyzx\\Desktop\\chromedriver\\chromedriver.exe");
        //创建ChromeDriver对象
        ChromeDriver driver = new ChromeDriver();
        //创建窗口最大化
        driver.manage().window().maximize();
        //打开网页
        driver.get("https://passport.bilibili.com/login");
        //用ID定位输入框
        WebElement account = driver.findElement(By.id("login-username"));
        //输入账号
        account.sendKeys("13467477002");
        //用ID定位
        WebElement password = driver.findElement(By.id("login-passwd"));
        //输入密码
        password.sendKeys("13974653156Xie");
        //点击登录
        WebElement login = driver.findElement(By.xpath("//*[@id=\"geetest-wrap\"]/div/div[5]/a[1]"));
        login.click();
        //给出一定的响应时间
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//        //使用Xpath解析数据
//        driver.quit();
    }

}
