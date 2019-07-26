package WikiAPI.Operation;

import WikiAPI.Http.Dto.WikipediaPage;
import WikiAPI.Http.Dto.WikipediaSearchDto;
import WikiAPI.Http.Dto.WikipediaSearchDto.Query;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FindFootballClubWikipediaPageTest {

  FindFootballClubWikipediaPage findFootballClubWikipediaPage;

  @BeforeEach
  void setUp() {
    findFootballClubWikipediaPage = new FindFootballClubWikipediaPage();
  }

  @Test
  public void should_return_wikipedia_page() {
    // given
    List<WikipediaPage> wikipediaPages = new ArrayList<>();
    wikipediaPages.add(WikipediaPage.builder().title("Liverpool").snippet("rugby club").build());
    wikipediaPages.add(WikipediaPage.builder().title("Liverpool FC").snippet("New football club").build());

    WikipediaSearchDto wikipediaSearchDto =
        WikipediaSearchDto.builder()
            .query(
                Query.builder()
                    .search(wikipediaPages)
                    .build())
            .build();

    // when / then
    WikipediaPage wikipediaPage = findFootballClubWikipediaPage.execute(wikipediaSearchDto);

    Assert.assertNotNull(wikipediaPage);
    Assert.assertEquals("Liverpool FC", wikipediaPage.getTitle());
  }

  @Test
  public void should_return_null() {
    // given
    List<WikipediaPage> wikipediaPages = new ArrayList<>();
    wikipediaPages.add(WikipediaPage.builder().title("Liverpool").snippet("rugby club").build());
    wikipediaPages.add(WikipediaPage.builder().title("Liverpool FC").snippet("tenis club").build());

    WikipediaSearchDto wikipediaSearchDto =
        WikipediaSearchDto.builder()
            .query(
                Query.builder()
                    .search(wikipediaPages)
                    .build())
            .build();

    // when / then
    WikipediaPage wikipediaPage = findFootballClubWikipediaPage.execute(wikipediaSearchDto);

    Assert.assertNull(wikipediaPage);
  }
}
