package WikiAPI;

import WikiAPI.Config.WikipediaConfig;
import WikiAPI.Http.Dto.WikipediaPage;
import WikiAPI.Http.Dto.WikipediaSearchDto;
import WikiAPI.Operation.GetClubInformationFromWikipedia;
import WikiAPI.Operation.FindFootballClubWikipediaPage;
import java.io.IOException;

public class App {
  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      System.out.println("First parameter should be club name.");
      System.exit(0);
    }

    String clubName = args[0];
    GetClubInformationFromWikipedia getClubInformationFromWikipedia =
        new GetClubInformationFromWikipedia(
            WikipediaConfig.builder().apiBaseUrl("https://en.wikipedia.org").build()
        );

    WikipediaSearchDto wikipediaSearchDto = getClubInformationFromWikipedia.execute(clubName);
    WikipediaPage wikipediaPage = new FindFootballClubWikipediaPage().execute(wikipediaSearchDto);

    if(wikipediaPage == null) {
      System.out.println("Cannot find football club with given name.");
      System.exit(0);
    }

    System.out.println(String.format("https://en.wikipedia.org/wiki/%s", wikipediaPage.getTitle()));
    System.exit(0);
  }
}
