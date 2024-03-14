package io.huvz.service.impl

import io.huvz.client.GitWebDriver
import io.huvz.service.IApiService
import io.vertx.core.http.impl.HttpClientConnection
import io.vertx.core.http.impl.HttpClientConnection.log
import jakarta.enterprise.context.ApplicationScoped
import org.openqa.selenium.*
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

@ApplicationScoped
class SyEDUSerivce : IApiService{

    /**
     * 请求api
     */
    fun htmlToImg(name:String): ByteArray? {
        var webDriver: WebDriver? = null
        return try {
            webDriver = GitWebDriver().getDriver()
            val url = "http://localhost:9085/v2api/view/sy?name=${name}";
            HttpClientConnection.log.info(url);
            webDriver.get(url)
            val s = webDriver.findElement(By.className("detail1"))
            val base64 = s.getScreenshotAs(OutputType.BYTES)
            webDriver.close();
            //webDriver.quit()
            base64
        } catch (e: Exception) {
            webDriver?.close();
            webDriver?.quit()
            HttpClientConnection.log.error("erro in ${e}")
            null;
        }


    }
}