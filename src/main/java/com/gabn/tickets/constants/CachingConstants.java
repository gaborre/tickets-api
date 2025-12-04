package com.gabn.tickets.constants;

public interface CachingConstants {
    String TICKETS_API_CACHE_TAG = "tickets-api";
    String USER_TICKETS_KEY =
        "'user-'.concat(#criteriaDomain.userId).concat('-tickets')"
        + ".concat(#criteriaDomain.status != null && #criteriaDomain.status != '' ? '-'.concat(#criteriaDomain.status) : '')";
}
