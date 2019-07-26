package WikiAPI.Operation;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.unauthorized;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

import WikiAPI.Config.WikipediaConfig;
import WikiAPI.Http.Dto.WikipediaSearchDto;
import com.github.tomakehurst.wiremock.WireMockServer;
import java.io.IOException;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GetClubInformationFromWikipediaTest {

  private WireMockServer wireMockServer;

  private GetClubInformationFromWikipedia getClubInformationFromWikipedia;

  @BeforeEach
  void setUp() {
    getClubInformationFromWikipedia =
        new GetClubInformationFromWikipedia(
            WikipediaConfig.builder().apiBaseUrl("http://localhost:8080").build());

    wireMockServer = new WireMockServer(8080);
    wireMockServer.start();
  }

  @AfterEach
  void tearDown() {
    wireMockServer.stop();
  }

  @Test
  public void should_return_wikipedia_pages() throws IOException {
    // given
    stubFor(
        get(urlPathEqualTo("/w/api.php"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        "{\"batchcomplete\":\"\",\"continue\":{\"sroffset\":10,\"continue\":\"-||\"},\"query\":{\"searchinfo\":{\"totalhits\":60379},\"search\":[{\"ns\":0,\"title\":\"Liverpool F.C.\",\"pageid\":18119,\"size\":98057,\"wordcount\":7941,\"snippet\":\"<span class=\\\"searchmatch\\\">Liverpool</span> Football Club is a professional football club in <span class=\\\"searchmatch\\\">Liverpool</span>, England, that competes in the Premier League, the top tier of English football. The\",\"timestamp\":\"2019-07-25T04:34:58Z\"},{\"ns\":0,\"title\":\"Liverpool\",\"pageid\":18081,\"size\":215638,\"wordcount\":20133,\"snippet\":\"<span class=\\\"searchmatch\\\">Liverpool</span> is a city and metropolitan borough in North West England, with an estimated population of 491,500. Its metropolitan area is the fifth-largest\",\"timestamp\":\"2019-07-22T00:00:10Z\"},{\"ns\":0,\"title\":\"2019 Netball World Cup\",\"pageid\":47539846,\"size\":88235,\"wordcount\":2378,\"snippet\":\"four years. The tournament was held from 12\\u201321 July 2019 at the <span class=\\\"searchmatch\\\">Liverpool</span> Arena in <span class=\\\"searchmatch\\\">Liverpool</span>, England with matches being held on two courts. Host nation England\",\"timestamp\":\"2019-07-26T06:26:02Z\"},{\"ns\":0,\"title\":\"Hillsborough disaster\",\"pageid\":59687,\"size\":190974,\"wordcount\":21252,\"snippet\":\"disaster was a fatal crush of people during an FA Cup football match between <span class=\\\"searchmatch\\\">Liverpool</span> and Nottingham Forest at Hillsborough Stadium in Sheffield, England, on\",\"timestamp\":\"2019-07-24T15:45:53Z\"},{\"ns\":0,\"title\":\"Daniel Sturridge\",\"pageid\":4877697,\"size\":83292,\"wordcount\":7782,\"snippet\":\"footballer who plays as a striker, most recently for Premier League club <span class=\\\"searchmatch\\\">Liverpool</span> and the English national team. Born in Birmingham, Sturridge spent four\",\"timestamp\":\"2019-07-19T02:55:24Z\"},{\"ns\":0,\"title\":\"Philippe Coutinho\",\"pageid\":18942673,\"size\":76731,\"wordcount\":6561,\"snippet\":\"joined English club <span class=\\\"searchmatch\\\">Liverpool</span> for \\u00a38.5\\u00a0million. He flourished at <span class=\\\"searchmatch\\\">Liverpool</span> and earned himself the nickname &quot;The Magician&quot; from <span class=\\\"searchmatch\\\">Liverpool</span> fans and teammates\",\"timestamp\":\"2019-07-25T09:48:30Z\"},{\"ns\":0,\"title\":\"Steven Gerrard\",\"pageid\":547384,\"size\":128815,\"wordcount\":11226,\"snippet\":\"spent the majority of his playing career as a central midfielder for <span class=\\\"searchmatch\\\">Liverpool</span>, with most of that time spent as club captain, as well as captaining the\",\"timestamp\":\"2019-07-25T23:02:40Z\"},{\"ns\":0,\"title\":\"Peter Crouch\",\"pageid\":1001231,\"size\":95565,\"wordcount\":8164,\"snippet\":\"regained his form, which would ultimately prompt his joining <span class=\\\"searchmatch\\\">Liverpool</span> in July 2005. At <span class=\\\"searchmatch\\\">Liverpool</span> he enjoyed considerable success, winning the FA Cup and FA\",\"timestamp\":\"2019-07-20T20:18:27Z\"},{\"ns\":0,\"title\":\"Mohamed Salah\",\"pageid\":35440786,\"size\":155117,\"wordcount\":13233,\"snippet\":\"professional footballer who plays as a forward for Premier League club <span class=\\\"searchmatch\\\">Liverpool</span> and the Egypt national team. Considered one of the best players in the\",\"timestamp\":\"2019-07-22T16:09:41Z\"},{\"ns\":0,\"title\":\"Sadio Man\\u00e9\",\"pageid\":34758489,\"size\":49129,\"wordcount\":3852,\"snippet\":\"professional footballer who plays as a winger for Premier League club <span class=\\\"searchmatch\\\">Liverpool</span> and captains the Senegal national team. Having begun his career with Metz\",\"timestamp\":\"2019-07-25T16:09:13Z\"}]}}")));

    // when / then
    WikipediaSearchDto wikipediaSearchDto = getClubInformationFromWikipedia.execute("Liverpool");

    Assert.assertNotNull(wikipediaSearchDto.getQuery().getSearch());
  }

  @Test
  public void should_not_return_wikipedia_pages() throws IOException {
    // given
    stubFor(
        get(urlPathEqualTo("/w/api.php"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        "{\"batchcomplete\":\"\",\"query\":{\"searchinfo\":{\"totalhits\":0},\"search\":[]}}")));

    // when / then
    WikipediaSearchDto wikipediaSearchDto = getClubInformationFromWikipedia.execute("Liverpool");

    Assert.assertNotNull(wikipediaSearchDto.getQuery().getSearch());
  }

  @Test
  public void should_throw_401_from_wikipedia() throws IOException {
    // given
    stubFor(get(urlPathEqualTo("/w/api.php")).willReturn(unauthorized()));

    // when / then
    assertThrows(IOException.class, () -> getClubInformationFromWikipedia.execute("Liverpool"));
  }
}
