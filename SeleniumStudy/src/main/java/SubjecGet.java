import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.image.DirectColorModel;
import java.util.List;

/**
 * @author qyyzxty@icloud.com
 * @data 2021/8/18
 **/
public class SubjecGet {
    public static void main(String[] args) throws InterruptedException {
        //设置驱动
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\qyyzx\\Desktop\\chromedriver\\chromedriver.exe");
        //创建ChromeDriver对象
        ChromeDriver driver = new ChromeDriver();
        //创建窗口最大化
        driver.manage().window().maximize();
        //打开网页
        driver.get("https://yz.chsi.com.cn/zyk/");

        //四种类型硕博遍历
        List<WebElement> shuobos = driver.findElements(By.xpath("//*[@id=\"ccUl\"]/li"));
        for (WebElement shuobo : shuobos) {
            shuobo.click();
            Thread.sleep(1000);

            //获取所有的学科大类
            List<WebElement> bigSubjects = driver.findElements(By.xpath("//*[@id=\"mlUl\"]/li"));
            for (WebElement bigSubject : bigSubjects) {
                bigSubject.click();
                Thread.sleep(1000);

                //获取当前大类信息
                String bigSubjectName = bigSubject.getText().replaceAll("\n\uE6A2", "");
                System.out.println("###############" + bigSubjectName + "###############");

                //获取当前大类下所有一级学科列表
                List<WebElement> firstSubjects = driver.findElements(By.xpath("//*[@id=\"xkUl\"]/li"));
                for (WebElement firstSubject : firstSubjects) {
                    if (firstSubject.getText().equals("")) continue;
                    firstSubject.click();
                    Thread.sleep(1000);

                    //解析一级学科与二级学科

                    //获取一级学科名称
                    String firstSubjectName = firstSubject.getText();
                    firstSubjectName = firstSubjectName.replaceAll("\n\uE6A2", "");
                    if (firstSubjectName.equals("")) continue;
                    //获取一级学科代码值
                    WebElement element = driver.findElement(By.xpath("//*[@id=\"listResult\"]/table/tbody/tr[2]/td[2]"));
                    String firstSubjectCode = element.getText().trim().substring(0, 4);
                    System.out.println("  -" + firstSubjectName + "-----" + firstSubjectCode);

                    //解析对应的二级学科
                    List<WebElement> secondsSubjects = driver.findElements(By.xpath("//*[@id=\"listResult\"]/table/tbody/tr"));
                    for (int j = 1; j < secondsSubjects.size(); j++) {
                        WebElement secondsSubject = secondsSubjects.get(j);
                        //获取二级学科名称
                        WebElement td = secondsSubject.findElement(By.tagName("td"));
                        String secondSubjectName = td.getText();
                        //获取二级学科代码值
                        String secondSubjectCode = secondsSubject.findElement(By.xpath("./td[2]")).getText().trim();
                        System.out.println("      -" + secondSubjectName + "-----" + secondSubjectCode);
                    }
                    System.out.println();
                }
            }
        }
    }
}
