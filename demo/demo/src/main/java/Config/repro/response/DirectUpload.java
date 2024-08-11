package Config.repro.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DirectUpload {

    String url;

    @JsonProperty("headers")
    Headers headers;

}
