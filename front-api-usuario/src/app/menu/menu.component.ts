import { Component } from '@angular/core'
import { Router } from '@angular/router'
import { SharedService } from '../shared.service'

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
})
export class MenuComponent {
  public id: string = 'main-navigation'

  constructor(
    private router: Router,
    public sharedService: SharedService
  ) {}

  public menuItems: any[] = [
    {
      id: 1,
      icon: 'home',
      name: 'Página Inicial',
      url: '/',
      isSpaLinkBehavior: true,
    },
    {
      id: 2,
      icon: 'users-cog',
      name: 'Formulário',
      url: '/formulario',
      isSpaLinkBehavior: true,
    },
    {
      id: 3,
      icon: 'sign-in-alt',
      name: 'Sign-In',
      url: '/sign-in',
      isSpaLinkBehavior: true,
    },
    {
      id: 4,
      icon: 'cookie',
      name: 'Cookiebar',
      url: '/cookiebar',
      isSpaLinkBehavior: true,
    },
  ]

  navigate(route: any) {
    const { detail: pathRouter } = route
    this.router.navigate(pathRouter)
  }
}
