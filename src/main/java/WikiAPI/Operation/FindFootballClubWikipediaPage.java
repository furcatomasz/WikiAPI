package WikiAPI.Operation;

import WikiAPI.Http.Dto.WikipediaPage;
import WikiAPI.Http.Dto.WikipediaSearchDto;
import java.util.List;
import java.util.stream.Collectors;

public class FindFootballClubWikipediaPage {

  private static final String CHARACTERS_TO_VERIFY_CLUB = "football";

  public WikipediaPage execute(WikipediaSearchDto wikipediaSearchDto) {

    List<WikipediaPage> wikipediaPages =
        wikipediaSearchDto.getQuery().getSearch().stream()
            .filter(
                (wikipediaPage ->
                    wikipediaPage.getSnippet().contains(CHARACTERS_TO_VERIFY_CLUB)))
            .collect(Collectors.toList());

    return (wikipediaPages.isEmpty()) ? null : wikipediaPages.get(0);
  }
}
