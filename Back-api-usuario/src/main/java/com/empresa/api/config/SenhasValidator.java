package com.empresa.api.config;

import com.empresa.api.dto.UsuarioDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SenhasValidator implements ConstraintValidator<SenhasValidadorIguais, UsuarioDTO> {

    @Override
    public void initialize(SenhasValidadorIguais constraintAnnotation) {
    }

    @Override
    public boolean isValid(UsuarioDTO usuarioDTO, ConstraintValidatorContext context) {
        String senha = usuarioDTO.getSenha();
        String confirmacaoDeSenha = usuarioDTO.getConfirmacaoDeSenha();

        return senha != null && senha.equals(confirmacaoDeSenha);
    }
}
