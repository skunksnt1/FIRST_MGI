import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { Usuario } from '../entidades/usuario';
import { UsuarioResponse } from '../entidades/usuarioResponse';
import { API_PATH } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(private http: HttpClient) { }

  cadastrar(usuario: Usuario): Observable<UsuarioResponse>{
    return this.http.post<UsuarioResponse>(`${API_PATH}`, usuario)
    .pipe(
      catchError(this.handleError)
    );
  }

  listarUsuarios(page: number = 0, size: number = 10, sort: string = 'nome,asc'): Observable<any> {
    const url = `${API_PATH}/listar?page=${page}&size=${size}&sort=${sort}`;
    return this.http.get<any>(url)
    .pipe(
      catchError(this.handleError)
    );
  }
  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'Erro desconhecido';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Erro: ${error.error.message}`;
    } else {
      errorMessage = `Código do erro: ${error.status}, mensagem: ${error.message}`;
    }
    return throwError('Tente novamente mais tarde.');
  }

}
