package dev.unnm3d.rediseconomy.api;

import dev.unnm3d.rediseconomy.currency.Currency;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.UUID;

@SuppressWarnings("unused")
public abstract class RedisEconomyAPI {
    protected static RedisEconomyAPI INSTANCE = null;

    /**
     * Get all the currencies
     * @return all the currencies in a collection
     */
    public abstract Collection<Currency> getCurrencies();

    /**
     * Get a currency by its name
     * @param name the name of the currency
     * @return the currency
     */
    public abstract Currency getCurrencyByName(String name);

    /**
     * Get default currency (vault currency)
     * @return the default currency
     */
    public abstract Currency getDefaultCurrency();

    /**
     * Get a currency by its symbol
     * @param symbol the symbol of the currency
     * @return the currency
     */
    public abstract Currency getCurrencyBySymbol(String symbol);

    /**
     * Get uuid from cache
     * @param username the username of the player
     * @return the uuid of the player
     */
    public abstract UUID getUUIDFromUsernameCache(String username);

    /**
     * Get username from cache
     * @param uuid the uuid of the player
     * @return the username of the player
     */
    public abstract String getUsernameFromUUIDCache(UUID uuid);

    /**
     * Get API instance
     * @return the API instance
     */
    public static @Nullable RedisEconomyAPI getAPI(){
        return INSTANCE;
    }

}