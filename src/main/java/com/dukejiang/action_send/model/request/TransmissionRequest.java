package com.dukejiang.action_send.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransmissionRequest {
    @JsonProperty("campaign_id")
    private String campaignId;

    @JsonProperty("recipients")
    private List<Address> addressList;

    @JsonProperty("content")
    private Content content;

    @JsonProperty("audience_id")
    private Long audienceId;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("journey_id")
    private Long journeyId;

}
