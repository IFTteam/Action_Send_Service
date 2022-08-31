package com.dukejiang.action_send.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduledTransmissionRequest {
    @JsonProperty("campaign_id")
    private String campaignId;

    @JsonProperty("options")
    private Options options;

    @JsonProperty("recipients")
    private List<Address> addressList;

    @JsonProperty("content")
    private Content content;

    @JsonProperty("audience_id")
    private Long audienceId;

    @JsonProperty("user_id")
    private Long userId;
}
