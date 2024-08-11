package Config.repro.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Respone {
    @JsonProperty("direct_upload")
    public DirectUpload directUpload;
}
