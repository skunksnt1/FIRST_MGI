import { Component, OnInit } from '@angular/core';
import { UsuarioService } from 'src/app/services/service-usuario.service';

@Component({
  selector: 'app-cadastro-sucesso',
  templateUrl: './cadastro-sucesso.component.html',
  styleUrls: ['./cadastro-sucesso.component.css']
})
export class CadastroSucessoComponent implements OnInit {
  usuarios: any[] = [];
  mostrarUsuarios: boolean = false;
  erro: string = '';

  constructor(private usuarioService: UsuarioService) { }

  ngOnInit(): void {
    this.usuarioService.listarUsuarios().subscribe(
      data => {
        this.usuarios = data.content;
      },
      error => {
        console.error('Erro ao listar usuários', error);
        this.erro = error;
      }
    );
  }
  mostrarOcultarUsuarios() {
    this.mostrarUsuarios = !this.mostrarUsuarios;
    this.usuarioService.listarUsuarios().subscribe(
      data => {
        this.usuarios = data.content;
      },
      error => {
        console.error('Erro ao listar usuários', error);
        this.erro = error;
      }
    );
  }

  fecharMensagemErro() {
    this.erro = '';
  }
}
