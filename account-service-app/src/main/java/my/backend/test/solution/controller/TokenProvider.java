package my.backend.test.solution.controller;

import com.google.common.hash.Funnel;
import com.google.common.hash.HashFunction;
import my.backend.test.solution.domain.Account;

public class TokenProvider {

    private final HashFunction hashFunction;

    public TokenProvider(HashFunction hashFunction) {
        this.hashFunction = hashFunction;
    }

    /**
     * Get state of account represented as hash value
     * @param account
     * @return
     */
    public String getToken(Account account) {
        return hashFunction.hashObject(account, (Funnel<Account>) (from, into) -> {
            into.putLong(from.getId());
            into.putInt(from.getCurrentBalance().hashCode());
        }).toString();
    }


}
