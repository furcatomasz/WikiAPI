package WikiAPI.Http.Dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class WikipediaSearchDto {
  Query query;

  @Data
  @Builder
  public static class Query {
    List<WikipediaPage> search;
  }
}
