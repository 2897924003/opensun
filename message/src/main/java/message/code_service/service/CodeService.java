package message.code_service.service;


import message.code_service.domain.Code;

public interface CodeService {
    void sendCode(Code code);

    boolean verifyCode(Code code);
}
