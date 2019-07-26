package WikiAPI.Http.Dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WikipediaSearchDto {
  Query query;

  @Data
  public static class Query {
    List<WikipediaPage> search;
  }
}
