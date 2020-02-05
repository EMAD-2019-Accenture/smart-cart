import { Injectable } from '@angular/core';
import { LoadingController } from '@ionic/angular';

@Injectable({
  providedIn: 'root'
})
export class LoadingService {

  constructor(private loadingController: LoadingController) { }

  public async presentWait(message?: string, translucent?: boolean): Promise<HTMLIonLoadingElement> {
    const loading: HTMLIonLoadingElement = await this.create(message, translucent);
    loading.present();
    return loading;
  }

  private create(message?: string, translucent?: boolean): Promise<HTMLIonLoadingElement> {
    return this.loadingController.create({
      message,
      translucent
    });
  }
}
