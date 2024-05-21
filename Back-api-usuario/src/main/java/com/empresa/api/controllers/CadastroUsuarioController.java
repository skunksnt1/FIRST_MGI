package com.empresa.api.controllers;

import com.empresa.api.dto.UsuarioDTO;
import com.empresa.api.dto.UsuarioResponseDTO;
import com.empresa.api.repository.UsuarioRepository;
import com.empresa.api.model.Usuario;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cadastro/usuario")
@CrossOrigin(origins = "http://localhost:4200")
public class CadastroUsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ApiOperation(value = "Cadastra usuários")
    @Transactional
    public ResponseEntity<UsuarioResponseDTO> cadastrarUsuario(
            @RequestBody @Valid UsuarioDTO usuarioRecord, UriComponentsBuilder uriBuilder) {
        Usuario usuarioEntity = modelMapper.map(usuarioRecord, Usuario.class);
        Usuario usuarioPersistence = usuarioRepository.save(usuarioEntity);
        UsuarioResponseDTO usuarioRecordMap = modelMapper.map(usuarioPersistence, UsuarioResponseDTO.class);

        var uri = uriBuilder.path("/cadastro/usuario/{id}").buildAndExpand(usuarioPersistence.getId()).toUri();
        return ResponseEntity.created(uri).body(usuarioRecordMap);
    }

    @GetMapping("/listar")
    @ApiOperation(value = "Lista todos os usuários cadastrados")
    public ResponseEntity<Page<UsuarioResponseDTO>> listaCadastrosDeUsuario(
            @ApiParam(value = "Número da página (começa do zero)", defaultValue = "0") @RequestParam(defaultValue = "0") int page,
            @ApiParam(value = "Tamanho da página", defaultValue = "10") @RequestParam(defaultValue = "10") int size,
            @ApiParam(value = "Ordenação dos resultados (campo,direção)", defaultValue = "nome,asc") @RequestParam(defaultValue = "nome,asc") String sort) {
        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        String sortOrder = sortParams[1];

        Pageable pageable = PageRequest.of(page, size, Sort.by(
                Sort.Order.by(sortField).with(Sort.Direction.fromString(sortOrder))
        ));

        Page<Usuario> usuarios = usuarioRepository.findAll(pageable);

        List<UsuarioResponseDTO> usuarioResponseDTOList = usuarios.getContent().stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioResponseDTO.class))
                .collect(Collectors.toList());

        Page<UsuarioResponseDTO> pageResponse = new PageImpl<>(usuarioResponseDTOList, pageable, usuarios.getTotalElements());

        return ResponseEntity.ok(pageResponse);
    }

}
