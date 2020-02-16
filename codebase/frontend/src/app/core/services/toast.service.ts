import { Injectable } from '@angular/core';
import { ToastController } from '@ionic/angular';

@Injectable({
  providedIn: 'root'
})
export class ToastService {

  constructor(private toastController: ToastController) { }

  public async presentToast(message: string, duration: number, showCloseButton: boolean, color: string, translucent: boolean) {
    const toast = await this.toastController.create({
      message,
      duration,
      showCloseButton,
      closeButtonText: 'Chiudi',
      color,
      translucent
    });
    toast.present();
  }

  public async dismiss() {
    await this.toastController.dismiss();
  }
}
