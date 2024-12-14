package org.foden.pojo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Getter @Setter //Can use @Data = @Getter + @Setter + some other useful methods
@Jacksonized
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {
        @JsonProperty("error")
        private InnerError error;
}
