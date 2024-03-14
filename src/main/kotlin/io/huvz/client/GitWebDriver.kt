package io.huvz.client

import io.vertx.core.http.impl.HttpClientConnection.log
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.RemoteWebDriver
import java.net.URL
import java.time.Duration


class GitWebDriver {


    val host = "http://127.0.0.1"
    val port = "4444"
    fun getOptions(): ChromeOptions {
        val options = ChromeOptions()

        var timeout = Duration.ofSeconds(18)


        options.setScriptTimeout(timeout); // 设置脚本执行超时时间为 30 秒
        timeout = Duration.ofSeconds(18)
        options.setPageLoadTimeout(timeout) // 设置页面加载超时时间为 30 秒


        //options.addArguments("--headless")

        options.addArguments("--incognito")
         options.addArguments("--no-sandbox") // fix:DevToolsActivePort file doesn't exist
         options.addArguments("--disable-gpu")  // fix:DevToolsActivePort file doesn't exist
        options.addArguments("disable-infobars"); // 禁用信息栏
        options.addArguments("disable-notifications"); // 禁用通知
//        options.addArguments("--disable-dev-shm-usage")  // fix:DevToolsActivePort file doesn't exist
//        options.addArguments("--remote-debugging-port=9222")  // fix:DevToolsActivePort file doesn't
        //log.info("${host}:${port}")
        return options
    }
    fun getFireFox(): FirefoxOptions {
        val options = FirefoxOptions()

        var timeout = Duration.ofSeconds(30)


        options.setScriptTimeout(timeout); // 设置脚本执行超时时间为 30 秒
        timeout = Duration.ofSeconds(30)
        options.setPageLoadTimeout(timeout) // 设置页面加载超时时间为 30 秒


        //options.addArguments("--headless")


        options.addArguments("--no-sandbox") // fix:DevToolsActivePort file doesn't exist
        //options.addArguments("--disable-gpu")  // fix:DevToolsActivePort file doesn't exist
        //options.addArguments("disable-infobars"); // 禁用信息栏
        //options.addArguments("disable-notifications"); // 禁用通知
//        options.addArguments("--disable-dev-shm-usage")  // fix:DevToolsActivePort file doesn't exist
//        options.addArguments("--remote-debugging-port=9222")  // fix:DevToolsActivePort file doesn't
        //log.info("${host}:${port}")
        return options
    }

    fun getWebDriver(): RemoteWebDriver {
        val webDriver = RemoteWebDriver(URL("${host}:${port}"), getOptions())
        return webDriver
    }
    fun getDriver(): WebDriver {
        var webDriver:WebDriver;
        try{
            webDriver = ChromeDriver(getOptions());
        }catch (e:Exception){
            //webDriver = getWebDriver();
            webDriver = FirefoxDriver(getFireFox());
        }
        return webDriver;
    }
    fun getLocalDriver(): ChromeDriver {
        val webDriver = ChromeDriver(getOptions())
        return webDriver
    }
}