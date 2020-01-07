import { from, Observable, timer, Subscription } from 'rxjs';
import { concatMap, map } from 'rxjs/operators';
import { HttpCommonService } from '../shared/http-common.service';

export class RaccomandazioniService {
  private polledReccomendation: Observable<any>;
  private subscription: Subscription;

  constructor(private http: HttpCommonService) {
    const bitcoin: Observable<any> = from(this.http.getRequest('https://smart-cart-acenture.herokuapp.com/api/products/'));
    const trigger: Observable<number> = timer(0, 1000);
    this.polledReccomendation = trigger.pipe(
      concatMap(() => bitcoin),
      // map((response) => {}), Uncomment to transform each response into other
    );
  }

  public enable() {
    this.subscription = this.polledReccomendation.subscribe((response) => (console.log(response)));
  }

  public disable() {
    this.subscription.unsubscribe();
  }
}
