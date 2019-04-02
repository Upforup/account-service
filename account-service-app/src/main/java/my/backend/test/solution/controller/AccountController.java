package my.backend.test.solution.controller;

import io.javalin.*;
import lombok.extern.slf4j.Slf4j;
import my.backend.test.solution.controller.dto.AccountInfoResponse;
import my.backend.test.solution.controller.dto.AccountToAccountInfoResponseMapper;
import my.backend.test.solution.domain.Account;
import my.backend.test.solution.exception.AccountNotFound;
import my.backend.test.solution.service.AccountOperations;

import java.math.BigDecimal;

@Slf4j
public class AccountController {

    private final AccountOperations operations;
    private final TokenProvider tokenProvider;

    public AccountController(AccountOperations operations, TokenProvider tokenProvider) {
        this.operations = operations;
        this.tokenProvider = tokenProvider;
    }

    public void accountInfo(Context ctx, String accountNumber) {
        try {
            log.debug("Fetching account info for account with number {}", accountNumber);
            Account accountInfo = operations.getAccountInfo(accountNumber);
            ctx.json(infoRs(accountInfo));
        } catch (AccountNotFound nf) {
            log.error("Account not found: ", nf);
            throw new NotFoundResponse();
        } catch (Exception e) {
            log.error("Exception occurred while fetching account info: ", e);
           throw new InternalServerErrorResponse();
        }
    }

    public void transfer(Context ctx, String srcAccNum, String tgtAccNum, BigDecimal amount) {
        try {
            log.debug("Transfer requested from {} to {} on amount {}", srcAccNum, tgtAccNum, amount);
            String token = ctx.header("If-Match");
            operations.transfer(srcAccNum, tgtAccNum, amount, (Account account) -> {
                if (!tokenProvider.getToken(account).equals(token)) {
                    throw new BadRequestResponse("Token is out of date!");
                }
            });
        } catch (HttpResponseException e) {
            throw e;
        } catch (Exception e) {
            log.error("Transfer failed from {} to {} on amount {}, exception was: {}", srcAccNum, tgtAccNum, amount, e);
            throw new InternalServerErrorResponse();
        }
    }


    private AccountInfoResponse infoRs(Account account) {
        return AccountToAccountInfoResponseMapper.map(account, tokenProvider.getToken(account));
    }

}
