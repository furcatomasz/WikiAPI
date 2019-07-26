package WikiAPI.Config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class WikipediaConfig {

  private String apiBaseUrl;
}
