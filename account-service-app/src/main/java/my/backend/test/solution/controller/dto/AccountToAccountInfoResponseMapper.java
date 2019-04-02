package my.backend.test.solution.controller.dto;

import my.backend.test.solution.domain.Account;

public class AccountToAccountInfoResponseMapper {


    public static AccountInfoResponse map(Account account, String token) {
        AccountInfoResponse rs = new AccountInfoResponse();
        rs.setToken(token);
        rs.setBalance(account.getCurrentBalance());
        rs.setId(account.getId());
        rs.setNumber(account.getNumber());
        rs.setDateOpen(account.getDateOpen());
        return rs;
    }



}
