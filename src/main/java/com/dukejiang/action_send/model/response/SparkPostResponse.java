package com.dukejiang.action_send.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "results")
public class SparkPostResponse {
    @JsonProperty("total_rejected_recipients")
    private String totalRejectedRecipients;
    @JsonProperty("total_accepted_recipients")
    private String totalAcceptedRecipients;
    @JsonProperty("id")
    private Long transmissionId;
}
