package WikiAPI.Http.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WikipediaPage {
  String title;
  String snippet;
}
