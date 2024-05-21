package com.empresa.api.controllers;

import com.empresa.api.dto.UsuarioDTO;
import com.empresa.api.dto.UsuarioResponseDTO;
import com.empresa.api.repository.UsuarioRepository;
import com.empresa.api.model.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class CadastroUsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void testListaCadastrosDeUsuario() throws Exception {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(new Usuario(1L, "Usuário 1", "usuario1@example.com", "senha1", null));

        Pageable pageable = PageRequest.of(0, 10);
        Page<Usuario> pageUsuarios = new PageImpl<>(usuarios, pageable, usuarios.size());
        when(usuarioRepository.findAll(any(Pageable.class))).thenReturn(pageUsuarios);

        mockMvc.perform(MockMvcRequestBuilders.get("/cadastro/usuario/listar"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void testCadastrarUsuario() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Novo Usuário");
        usuarioDTO.setEmail("novo_usuario@example.com");
        usuarioDTO.setSenha("senha123");
        usuarioDTO.setConfirmacaoDeSenha("senha123");

        Usuario usuarioMock = new Usuario();
        usuarioMock.setId(1L);
        usuarioMock.setNome(usuarioDTO.getNome());
        usuarioMock.setEmail(usuarioDTO.getEmail());
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioMock);
        mockMvc.perform(MockMvcRequestBuilders.post("/cadastro/usuario")
                        .content(asJsonString(usuarioDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", "http://localhost/cadastro/usuario/1"));
    }


    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCadastrarUsuario_EmailInvalido() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Novo Usuário");
        usuarioDTO.setEmail("novo_usuario@.com");
        usuarioDTO.setSenha("senha123");
        usuarioDTO.setConfirmacaoDeSenha("senha123");

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/cadastro/usuario")
                        .content(asJsonString(usuarioDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }





}
