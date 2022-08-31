package io.graversen.minecraft.rcon.commands;

import io.graversen.minecraft.rcon.commands.base.ICommand;
import io.graversen.minecraft.rcon.util.GameRules;
import org.apache.commons.text.StringSubstitutor;

import java.util.Map;
import java.util.Objects;

public class GameRulesCommands {
    private GameRulesCommands() {

    }

    public static ICommand setGameRule(GameRules gameRule, int value) {
        return setGameRule(gameRule.getGameRuleName(), String.valueOf(value));
    }

    public static ICommand setGameRule(GameRules gameRule, boolean value) {
        return setGameRule(gameRule.getGameRuleName(), String.valueOf(value));
    }

    public static ICommand setGameRule(String gameRule, int value) {
        return setGameRule(gameRule, String.valueOf(value));
    }

    public static ICommand setGameRule(String gameRule, boolean value) {
        return setGameRule(gameRule, String.valueOf(value));
    }

    private static ICommand setGameRule(String gameRule, String value) {
        Objects.requireNonNull(gameRule);
        Objects.requireNonNull(value);

        return () -> StringSubstitutor.replace(
                "gamerule ${gamerule} ${value}",
                Map.of(
                        "gamerule", gameRule,
                        "value", value
                )
        );
    }

    private static ICommand getGameRule(String gameRule) {
        Objects.requireNonNull(gameRule);

        return () -> StringSubstitutor.replace(
                "gamerule ${gamerule}",
                Map.of("gamerule", gameRule)
        );
    }
}
