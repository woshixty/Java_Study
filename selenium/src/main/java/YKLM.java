import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @author qyyzxty@icloud.com
 * 2021/4/8
 **/
public class YKLM {

    public static void main(String[] args) {
        //驱动
        System.setProperty("webdriver.chrome.driver", "/Users/mr.stark/Downloads/driver/chromedriver");
        //创建ChromeDriver对象
        ChromeDriver driver = new ChromeDriver();
        //创建窗口最大化
        driver.manage().window().maximize();
        //打开网页
        driver.get("http://www.uooc.net.cn/home/learn/index#/567340986/1323296315/1663561042");
        //获取当前要看的列表
//        WebElement element = driver.findElement(By.id("1663561042"));
//        element.click();
        //获取元素列表-catalogItem ng-scope-章节
//        List<WebElement> list = driver.findElements(By.className("catalogItem ng-scope"));
        //遍历
//        for (WebElement liElement : list) {
            //获取未完成的小节
//            List<WebElement> basicUncompleted = liElement.findElements(By)
//            if (check(liElement, By.className("rank-2 ng-scope"))) {
//                System.out.println("无需点击");
//            } else {
//            }
//        WebElement ulElement = element.findElement(By.className("rank-2 ng-scope"));
//        List<WebElement> clips = ulElement.findElements(By.className("basic ng-scope"));

        //登录
        WebElement login = driver.findElement(By.id("account"));
        WebElement password = driver.findElement(By.id("password"));
        login.sendKeys("13467477002");
        password.sendKeys("13974653156Xie");
        WebElement yanzhen = driver.findElement(By.id("Group3"));
        yanzhen.click();

        WebElement element1 = driver.findElement(By.xpath("//*[@id=\"1663561042\"]/div[2]/div[2]"));
        element1.click();

    }

    public static Boolean check(WebElement element, By selector) {
        try {
            element.findElement(selector);
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
    }
}
