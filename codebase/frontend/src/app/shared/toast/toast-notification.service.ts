import { Injectable } from '@angular/core';
import { ToastController } from '@ionic/angular';

@Injectable({
  providedIn: 'root'
})
export class ToastNotificationService {

  constructor(public toastController: ToastController) {}

  async presentToast(_message: string, _color: string) {
    const toast = await this.toastController.create({
      message: _message,
      duration: 2500,
      showCloseButton: true,
      translucent: true,
      color: _color
    });
    toast.present();
  }
}
