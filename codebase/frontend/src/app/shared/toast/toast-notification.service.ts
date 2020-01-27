import { Injectable } from '@angular/core';
import { ToastController } from '@ionic/angular';

@Injectable({
  providedIn: 'root'
})
export class ToastNotificationService {

  constructor(public toastController: ToastController) {}

  async presentToast(message: string, duration: number, showCloseButton: boolean, color: string, translucent: boolean) {
    const toast = await this.toastController.create({
      message,
      duration,
      showCloseButton,
      color,
      translucent
    });
    toast.present();
  }
}
