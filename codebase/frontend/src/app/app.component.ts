// tslint:disable: max-line-length
import { Component, QueryList, ViewChildren } from '@angular/core';
import { Router } from '@angular/router';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';
import { ActionSheetController, IonRouterOutlet, MenuController, ModalController, Platform, PopoverController } from '@ionic/angular';
import { ToastService } from './core/services/toast.service';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss']
})
export class AppComponent {
  private lastTimeBackPress = 0;
  private timePeriodToExit = 2000;

  @ViewChildren(IonRouterOutlet)
  private routerOutlets: QueryList<IonRouterOutlet>;

  constructor(
    private platform: Platform,
    private splashScreen: SplashScreen,
    private statusBar: StatusBar,
    private actionSheetCtrl: ActionSheetController,
    private popoverCtrl: PopoverController,
    private modalCtrl: ModalController,
    private menuCtrl: MenuController,
    private toast: ToastService,
    private router: Router
  ) {
    this.initializeApp();
  }

  initializeApp() {
    this.platform.ready().then(() => {
      this.statusBar.styleDefault();
      this.splashScreen.hide();
      this.initializeBackButton();
    });
  }

  initializeBackButton() {
    this.platform.backButton.subscribe(async () => {
      // close action sheet
      try {
        const element = await this.actionSheetCtrl.getTop();
        if (element) {
          element.dismiss();
          return;
        }
      } catch (error) {
        console.log(error);
      }

      // close popover
      try {
        const element = await this.popoverCtrl.getTop();
        if (element) {
          element.dismiss();
          return;
        }
      } catch (error) {
        console.log(error);
      }

      // close modal
      try {
        const element = await this.modalCtrl.getTop();
        if (element) {
          element.dismiss();
          return;
        }
      } catch (error) {
        console.log(error);
      }

      // close side menu
      try {
        const element = await this.menuCtrl.getOpen();
        if (element) {
          this.menuCtrl.close();
          return;
        }
      } catch (error) {
        console.log(error);
      }

      this.routerOutlets.forEach(async (outlet: IonRouterOutlet) => {
        if (outlet && outlet.canGoBack()) {
          outlet.pop();
        } else if (this.router.isActive('/index', false)) {
          if (new Date().getTime() - this.lastTimeBackPress < this.timePeriodToExit) {
            // this.platform.exitApp(); // < Ionic 4
            navigator['app'].exitApp(); // >= Ionic 4
          } else {
            await this.toast.presentToast('Premi di nuovo indietro per uscire', 2000, false, 'success', true);
            this.lastTimeBackPress = new Date().getTime();
          }
        }
      });
    });
  }
}
