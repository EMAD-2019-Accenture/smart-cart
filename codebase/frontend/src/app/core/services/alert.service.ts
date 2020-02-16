import { Injectable } from '@angular/core';
import { AlertController } from '@ionic/angular';
import { AlertButton } from '@ionic/core';

@Injectable({
  providedIn: 'root'
})
export class AlertService {

  constructor(private alertController: AlertController) { }

  public async presentOkayAlert(message: string, title?: string, subTitle?: string): Promise<void> {
    const alert: HTMLIonAlertElement = await this.create(message, ['Okay'], title, subTitle);
    alert.present();
  }

  public async presentAlert(message: string, buttons: string[], title?: string, subTitle?: string): Promise<void> {
    const alert: HTMLIonAlertElement = await this.create(message, buttons, title, subTitle);
    alert.present();
  }

  public async presentConfirm(message: string, buttons: AlertButton[], title?: string, subTitle?: string): Promise<void> {
    const alert: HTMLIonAlertElement = await this.create(message, buttons, title, subTitle);
    alert.present();
  }

  private create(message?: string, buttons?: string[] | AlertButton[], title?: string, subTitle?: string): Promise<HTMLIonAlertElement> {
    return this.alertController.create({
      message,
      buttons
    });
  }
}
