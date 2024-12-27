package message.inbound.rest;

import message.code_service.domain.Code;
import message.code_service.service.CodeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/code")
public class CodeController {

    private final CodeService codeService;

    public CodeController(CodeService codeService) {
        this.codeService = codeService;
    }

    // 发送验证码的 REST API
    @PostMapping("/send")
    public void sendCode(@RequestBody Code code) {
        codeService.sendCode(code);
    }

    // 验证验证码的 REST API
    @PostMapping("/verify")
    public boolean verifyCode(@RequestBody Code code) {
        return codeService.verifyCode(code);
    }
}