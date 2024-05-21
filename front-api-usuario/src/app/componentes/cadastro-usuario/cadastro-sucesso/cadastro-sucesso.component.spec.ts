import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CadastroSucessoComponent } from './cadastro-sucesso.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { UsuarioService } from 'src/app/services/service-usuario.service';
import { of } from 'rxjs';

describe('CadastroSucessoComponent', () => {
  let component: CadastroSucessoComponent;
  let fixture: ComponentFixture<CadastroSucessoComponent>;
  let usuarioServiceSpy: jasmine.SpyObj<UsuarioService>;

  beforeEach(async () => {
    const spy = jasmine.createSpyObj('UsuarioService', ['listarUsuarios']);

    await TestBed.configureTestingModule({
      declarations: [CadastroSucessoComponent],
      imports: [HttpClientTestingModule],
      providers: [{ provide: UsuarioService, useValue: spy }]
    })
    .compileComponents();

    usuarioServiceSpy = TestBed.inject(UsuarioService) as jasmine.SpyObj<UsuarioService>;
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CadastroSucessoComponent);
    component = fixture.componentInstance;
    usuarioServiceSpy.listarUsuarios.and.returnValue(of({ content: [] }));
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should list users on init', () => {
    const mockUsuarios = {
      content: [
        { id: 1, nome: 'Usuario 1', email: 'usuario1@example.com' },
        { id: 2, nome: 'Usuario 2', email: 'usuario2@example.com' }
      ]
    };

    usuarioServiceSpy.listarUsuarios.and.returnValue(of(mockUsuarios));
    component.ngOnInit();
    expect(component.usuarios).toEqual(mockUsuarios.content);
  });
});
