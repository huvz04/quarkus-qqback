package io.huvz.service.impl

import io.huvz.client.GitWebDriver
import io.huvz.config.MyAppConfig
import io.vertx.core.http.impl.HttpClientConnection.log
import jakarta.enterprise.context.ApplicationScoped
import kotlinx.coroutines.runBlocking
import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration


@ApplicationScoped
class JhcService {




    suspend fun jhcToClass(name:String): ByteArray?  = runBlocking {
        var webDriver: RemoteWebDriver? = null
        return@runBlocking try {
            webDriver = GitWebDriver().getTestWebDriver()
            val JhcUrl = "https://webvpn.jhc.cn/login"
            log.info(JhcUrl)
            webDriver.get(JhcUrl)
            // 找到用户名输入框，并填写用户名

                val waitDuration = Duration.ofSeconds(10)
                val wait = WebDriverWait(webDriver,waitDuration ) // 设置最大等待时间为10秒

                 val usernameInput: WebElement = webDriver.findElement(By.id("user_name"));
                log.info(usernameInput)

            // 创建Actions对象
            val actions = Actions(webDriver)
            println(MyAppConfig.username)
                // 使用sendKeys方法点击输入框
                actions.moveToElement(usernameInput).click()
                actions.sendKeys(usernameInput,MyAppConfig.username).perform();
                actions.sendKeys(Keys.ENTER)
                // 找到密码输入框，并填写密码
                val passwordInput: WebElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")))
                passwordInput.sendKeys(MyAppConfig.password)

                val submitButton: WebElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login")))
                //submitButton.submit()
                submitButton.click()
                log.info(submitButton)

            // 等待0.5秒
            try {
                Thread.sleep(500)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
                val JHcjwglxt = "https://webvpn.jhc.cn/https/77726476706e69737468656265737421fae046903f24265a760bc7af96/"
                log.info(JHcjwglxt)
                webDriver.get(JHcjwglxt)

            // 等待0.5秒
            try {
                Thread.sleep(300)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            // 再登录一遍
                // 找到密码输入框，并填写密码
                val usernameInput2: WebElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")))
                usernameInput2.sendKeys(MyAppConfig.username)
                // 找到密码输入框，并填写密码
                val passwordInput2: WebElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ppassword")))
                passwordInput2.sendKeys(MyAppConfig.password)

            val submitButton2: WebElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dl")))
            //submitButton.submit()
            submitButton2.click()


            log.info(submitButton)

            // 等待0.6秒
            try {
                Thread.sleep(600)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            val base64 = webDriver.getScreenshotAs(OutputType.BYTES)
            //webDriver.close();
            //webDriver.quit()
            base64
        }catch (e:Exception){
            webDriver?.close();
            webDriver?.quit()
            log.error("erro in ${e}")
            null;
        }


    }



}