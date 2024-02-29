package io.huvz.service.impl

import io.huvz.client.GitWebDriver
import io.huvz.service.IApiService
import io.vertx.core.http.impl.HttpClientConnection
import jakarta.enterprise.context.ApplicationScoped
import org.openqa.selenium.*
import org.openqa.selenium.remote.RemoteWebDriver

@ApplicationScoped
class GiteeSerivce : IApiService{

    /**
     * 获取gitee主页
     */
    fun getUrl4img(name:String): ByteArray? {
        var webDriver: WebDriver? = null
        try{
            webDriver = GitWebDriver().getDriver()
            // 设置窗口大小
            val windowSize = Dimension(1280, 2000)
            webDriver.manage().window().size = windowSize
            // 创建一个 Cookie 对象
            webDriver.get("https://gitee.com/$name")
            val cookie = Cookie("slide_id", "10")
            // 设置 Cookie
            webDriver.manage().addCookie(cookie)
            webDriver.navigate().refresh()
            var element:  WebElement? = null;
            element = try {
                webDriver.findElement(By.id("user-show-detail"))
            }catch (e: NoSuchElementException) {
                //如果找不到结果就抓404的
                webDriver.findElement(By.className("content"))
            }catch (e:Exception){
                webDriver.findElement(By.className("ui_basic"))
            }
            val base64 = element?.getScreenshotAs(OutputType.BYTES)
            webDriver.close();
            webDriver.quit()
            return base64;

        }catch (e:Exception){
            webDriver?.close();
            webDriver?.quit()
            HttpClientConnection.log.error("erro in ${e}")
            return null;
        }
    }

    /**
     * 渲染Gitee查询接口
     */
    fun htmlToImg(name:String): ByteArray? {
        var webDriver: WebDriver? = null
        return try {
            webDriver = GitWebDriver().getDriver()
            val url = "http://localhost:9085/v2api/view/gitee?name=${name}";
            HttpClientConnection.log.info(url);
            webDriver.get(url)
            val s = webDriver.findElement(By.className("detail1"))
            val base64 = s.getScreenshotAs(OutputType.BYTES)
            webDriver.close();
            //webDriver.quit()
            base64
        }catch (e:Exception){
            webDriver?.close();
            webDriver?.quit()
            HttpClientConnection.log.error("erro in ${e}")
            null;
        }


    }

}