package org.foden.pojo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {
        @JsonProperty("error")
        private InnerError innerError;

        @JsonProperty("error")
        public InnerError getError() {
            return innerError;
        }

        @JsonProperty("error")
        public void setError(InnerError innerError) {
            this.innerError = innerError;
        }
}
