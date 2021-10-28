import spock.lang.Shared
import spock.lang.Specification
import wslite.rest.*

class PageSearchAPISpec extends APITemplateSpec {

    @Shared
    def restClient = new RESTClient(testURL)

    def "request text extracted API"() {

        when: "accessing the endpoint textextracted to extract text from a page"
        def response = restClient.get(path: "/textextracted?m=http://www.fccn.pt//19961013145650")

        then: "the output is the content of the FCCN page "
        response.text == """Fundação para a Computação Científica Nacional " A promoção de\
 infraestruturas no domínio da computação científica gerindo-as, nuns casos, e doando-as noutros,\
 é o principal objectivo da Fundação , que actua predominantemente no âmbito das Universidades,\
 Centros e Laboratórios de I&D." A Fundação tem a responsabilidade de gerir e desenvolver a RCCN,\
 Rede da Comunidade Científica Nacional, a rede académica portuguesa. RCCN Outras actividades:\
 SCCN | CCCN | ICCN | ACCN Eventos Publicações Serviços disponíveis Servidores WWW das instituições\
 ligadas à RCCN Home Page de Portugal Estatísticas de utilização deste servidor 5685 visitantes\
 desde 95-11-22 Fundação para a Computação Científica Nacional (FCCN) Av. Brasil, 101 1799 Lisboa\
 Codex Tel. +351 1 8481906 Fax. +351 1 8472167 email. info@fccn.pt Este servidor é mantido por:\
 Florentino dos Santos Gameiro - flo@rccn.net Última alteração: Wednesday, 10-Jan-96 18:13:25 CUT\
 Internet URL- http://www.fccn.pt:80/"""

    }
}
