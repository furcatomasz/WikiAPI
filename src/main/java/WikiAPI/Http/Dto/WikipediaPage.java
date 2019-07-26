package WikiAPI.Http.Dto;

import lombok.Data;

@Data
public class WikipediaPage {
  String title;
  Integer pageid;
  String snippet;
}
