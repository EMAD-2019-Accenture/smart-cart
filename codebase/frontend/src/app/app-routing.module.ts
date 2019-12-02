import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: '', redirectTo: 'auth', pathMatch: 'full' },
  { path: 'index', redirectTo: 'auth', pathMatch: 'full' },
  { path: 'auth', loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule) },
  { path: 'carrello', loadChildren: () => import('./carrello/carrello.module').then(m => m.CarrelloModule) },
  { path: 'catalogo', loadChildren: () => import('./catalogo/catalogo.module').then(m => m.CatalogoModule) },
  { path: 'preferenze', loadChildren: () => import('./preferenze/preferenze.module').then(m => m.PreferenzeModule) },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules }),
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
