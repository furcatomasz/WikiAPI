package WikiAPI.Operation;

import WikiAPI.Http.Dto.WikipediaPage;
import WikiAPI.Http.Dto.WikipediaSearchDto;
import java.util.List;
import java.util.stream.Collectors;

public class MatchWikipediaPageWithClubName {

  private static final String CHARACTERS_TO_VERIFY_CLUB = "football";

  public WikipediaPage execute(WikipediaSearchDto wikipediaSearchDto, String clubName) {

    List<WikipediaPage> wikipediaPages =
        wikipediaSearchDto.getQuery().getSearch().stream()
            .filter(
                (wikipediaPage ->
                    wikipediaPage.getSnippet().indexOf(CHARACTERS_TO_VERIFY_CLUB) > 0))
            .collect(Collectors.toList());

    return (wikipediaPages.isEmpty()) ? null : wikipediaPages.get(0);
  }
}
