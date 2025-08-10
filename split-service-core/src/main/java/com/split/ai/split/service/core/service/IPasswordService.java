package com.split.ai.split.service.core.service;

public interface IPasswordService {

    String encode(String rawPassword);

    boolean matchesAndUpgrade(String rawPassword, String encodedPassword, java.util.function.Consumer<String> upgradeAction);
}
