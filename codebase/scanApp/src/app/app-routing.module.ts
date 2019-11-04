import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: '', loadChildren: './pages/home/home.module#HomePageModule' },
  { path: 'product', loadChildren: './pages/product/product.module#ProductPageModule' },
  {
    path: 'scan',
    loadChildren: './pages/scan/scan.module#ScanPageModule'
  },
  { path: 'recommendation', loadChildren: './pages/recommendation/recommendation.module#RecommendationPageModule' }
 
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
