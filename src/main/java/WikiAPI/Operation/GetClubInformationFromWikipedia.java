package WikiAPI.Operation;

import WikiAPI.Config.WikipediaConfig;
import WikiAPI.Http.Dto.WikipediaSearchDto;
import com.squareup.moshi.Moshi;
import java.io.IOException;
import lombok.AllArgsConstructor;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@AllArgsConstructor
public class GetClubInformationFromWikipedia {

  private static final String QUERY_PARAM_ACTION = "action";
  private static final String QUERY_PARAM_ACTION_VALUE = "query";
  private static final String QUERY_PARAM_LIST = "list";
  private static final String QUERY_PARAM_LIST_VALUE = "search";
  private static final String QUERY_PARAM_FORMAT = "format";
  private static final String QUERY_PARAM_FORMAT_VALUE = "json";
  private static final String QUERY_PARAM_SRLIMIT = "srlimit";
  private static final String QUERY_PARAM_SRLIMIT_VALUE = "10";
  private static final String QUERY_PARAM_SEARCH = "srsearch";
  private final OkHttpClient okHttpClient = new OkHttpClient();
  private final Moshi moshi = new Moshi.Builder().build();
  private WikipediaConfig wikipediaConfig;

  public WikipediaSearchDto execute(String clubName) throws IOException {
    Request request = buildRequest(clubName);
    Response response = okHttpClient.newCall(request).execute();
    if (!response.isSuccessful()) {
      throw new IOException("Unexpected code " + response);
    }

    WikipediaSearchDto wikipediaSearchDto =
        moshi.adapter(WikipediaSearchDto.class).fromJson(response.body().source());

    return wikipediaSearchDto;
  }

  private Request buildRequest(String clubName) {
    HttpUrl httpBuider =
        HttpUrl.parse(String.format("%s%s", wikipediaConfig.getApiBaseUrl(), "/w/api.php"))
            .newBuilder()
            .addQueryParameter(QUERY_PARAM_ACTION, QUERY_PARAM_ACTION_VALUE)
            .addQueryParameter(QUERY_PARAM_LIST, QUERY_PARAM_LIST_VALUE)
            .addQueryParameter(QUERY_PARAM_FORMAT, QUERY_PARAM_FORMAT_VALUE)
            .addQueryParameter(QUERY_PARAM_SRLIMIT, QUERY_PARAM_SRLIMIT_VALUE)
            .addQueryParameter(QUERY_PARAM_SEARCH, clubName)
            .build();

    return new Request.Builder().url(httpBuider).get().build();
  }
}
