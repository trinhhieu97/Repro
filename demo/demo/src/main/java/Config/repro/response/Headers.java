package Config.repro.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Headers {
    @JsonProperty("Content-Type")
    public String contentType;

    @JsonProperty("Content-MD5")
    public String contentMd5;

    @JsonProperty("Content-Disposition")
    public String contentDisposition;
}
