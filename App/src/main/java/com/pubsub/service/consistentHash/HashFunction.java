package com.pubsub.service.consistentHash;

public interface HashFunction {
    long hash(String key);
}