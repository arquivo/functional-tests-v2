import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import geb.driver.SauceLabsDriverFactory

def sauceLabsBrowser = System.getProperty("geb.saucelabs.browser")
if (sauceLabsBrowser) {
    def configs = sauceLabsBrowser.split(System.lineSeparator())
    def sauceLabsBrowserConfig = ""
    def desiredCaps = [:]
    configs.each {
        if ( it =~ "browserName" || it =~ "platformName") {
            sauceLabsBrowserConfig += it + System.lineSeparator()
        }
        else {
            def (k,v) = it.split("=")
            desiredCaps[k] = v
        }
    }
    driver = {
       def username = System.getenv("GEB_SAUCE_LABS_USER")
       assert username
       def accessKey = System.getenv("GEB_SAUCE_LABS_ACCESS_PASSWORD")
       assert accessKey
       new SauceLabsDriverFactory().create(sauceLabsBrowserConfig, username, accessKey, ["sauce:options":desiredCaps])
    }
}

waiting {
    timeout = 2
}

environments {

    // run via “./gradlew chromeTest”
    // See: http://code.google.com/p/selenium/wiki/ChromeDriver
    chrome {
        driver = { new ChromeDriver() }
    }

    // run via “./gradlew chromeHeadlessTest”
    // See: http://code.google.com/p/selenium/wiki/ChromeDriver
    chromeHeadless {
        driver = {
            ChromeOptions o = new ChromeOptions()
            o.addArguments('headless')
            new ChromeDriver(o)
        }
    }

    // run via “./gradlew firefoxTest”
    // See: http://code.google.com/p/selenium/wiki/FirefoxDriver
    firefox {
        atCheckWaiting = 1

        driver = { new FirefoxDriver() }
    }
}
