package com.manager.transaction.consumer;

import com.manager.transaction.consumer.api.WalletApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "wallet", url = "http://wallet:8081")
public interface WalletConsumer extends WalletApi {
}
