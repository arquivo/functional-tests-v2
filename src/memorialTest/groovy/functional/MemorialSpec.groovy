package functional

import geb.Browser
import geb.spock.GebSpec

import groovy.yaml.YamlSlurper

import org.openqa.selenium.By;

import spock.lang.Specification

import wslite.rest.*

class MemorialSpec extends GebSpec {

    def WAYBACK_PATH = "/wayback"

    def getWaybackURL(memorialConfig) {
        if (memorialConfig.waybackUrl != null) {
            /* escape ? character */
            def waybackUrl = memorialConfig.waybackUrl.replace('?',/\?/)
            return waybackUrl
        } else {
            /* escape ? character */
            def url = memorialConfig.url.replace('?',/\?/)
            return  WAYBACK_PATH + "/" + memorialConfig.timestamp  + "/" + url
        }
    }

    def "All memorial website are available"() {
        when: "accessing memorial's URL"
        def url = config.url.toURL()

        then: "the memorial text is available"
        url.text =~ config.redirectPageText

        when: "clicking on 'Ver no Arquivo.pt|Browse on Arquivo.pt'"
        go config.url
        report "memorial"
        $("#"+config.redirectID).click()

        then: "we are at the right memorial URL"
        getCurrentUrl() =~ getWaybackURL(config)

        and: "we can confirm the text inside the iframe"
        withFrame($("#replay_iframe")) {
            if (config.waybackTextXPath) {
                waitFor {
                    $(By.xpath(config.waybackTextXPath)).text() =~ config.waybackText
                }
            } else {
                title == config.title
            }
        }

        where:
        config << (new YamlSlurper().parse(getClass().getResourceAsStream("/memorialConfig.yml"))).configs
    }
}
