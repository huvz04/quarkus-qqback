package io.huvz.service.impl

import io.huvz.client.GitWebDriver
import io.huvz.service.IApiService
import io.vertx.core.http.impl.HttpClientConnection
import io.vertx.core.http.impl.HttpClientConnection.log
import jakarta.enterprise.context.ApplicationScoped
import org.openqa.selenium.*
import org.openqa.selenium.remote.RemoteWebDriver

@ApplicationScoped
class GithubSerivce : IApiService{

    /**
     * 获取github主页
     */
    fun getimg(name:String): ByteArray? {
        var webDriver: RemoteWebDriver? = null
        try{
            webDriver = GitWebDriver().getWebDriver()
            // 设置窗口大小
            val windowSize = Dimension(1500, 2000)
            webDriver.manage().window().size = windowSize
            // 创建一个 Cookie 对象
            //val url = "https://github.com/$name"
            val url = "https://hub.nuaa.cf/$name"
            //更换为镜像地址
            //val url = "https://hub.yzuu.cf/$name"
            log.info(url)
            webDriver.get(url)
            var element:  WebElement? = null;
            element = try {
                webDriver.findElement(By.className("application-main"))
            }catch (e: NoSuchElementException) {
                //如果找不到结果就抓404的
                webDriver.findElement(By.className("font-mktg"))
            }catch (e:Exception){
                webDriver.findElement(By.tagName("html"))
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
        var webDriver: RemoteWebDriver? = null
        return try {
            webDriver = GitWebDriver().getWebDriver()
            HttpClientConnection.log.info("http://43.142.135.84:9085/v2api/view/gitee?name=${name}");
            webDriver.get("http://43.142.135.84:9085/v2api/view/gitee?name=${name}")
            val s = webDriver.findElement(By.className("detail1"))
            val base64 = s.getScreenshotAs(OutputType.BYTES)
            webDriver.close();
            webDriver.quit()
            base64
        }catch (e:Exception){
            webDriver?.close();
            webDriver?.quit()
            HttpClientConnection.log.error("erro in ${e}")
            null;
        }


    }

}