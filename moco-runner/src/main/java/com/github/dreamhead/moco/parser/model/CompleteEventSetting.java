package com.github.dreamhead.moco.parser.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.github.dreamhead.moco.Moco;
import com.github.dreamhead.moco.MocoEventAction;
import com.google.common.base.MoreObjects;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CompleteEventSetting {
    private boolean async;
    private LatencyContainer latency;
    private PostSetting post;
    private GetSetting get;

    public MocoEventAction createTrigger() {
        if (!async && latency != null) {
            throw new IllegalArgumentException("Latency only works for async mode");
        }

        MocoEventAction action = doCreateAction();

        if (this.async) {
            return Moco.async(action, Moco.latency(latency.getLatency(), latency.getUnit()));
        }

        return action;

    }

    private MocoEventAction doCreateAction() {
        if (get != null) {
            return get.createAction();
        }

        if (post != null) {
            return post.createAction();
        }

        return null;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("async", async)
                .add("latency", latency)
                .add("post", post)
                .add("get", get)
                .toString();
    }
}
