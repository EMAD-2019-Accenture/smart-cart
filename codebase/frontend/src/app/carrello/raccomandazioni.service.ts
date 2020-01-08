import { HttpCommonService } from '../shared/http-common.service';
import { Product } from '../shared/model/product';
import { Recommendation } from '../shared/model/recommendation';

export class RaccomandazioniService {
  private recommendationsKey = 'recommendations';

  constructor(private http: HttpCommonService) {
    /*
    const bitcoin: Observable<any> = from(this.http.getRequest('https://smart-cart-acenture.herokuapp.com/api/products/'));
    const trigger: Observable<number> = timer(0, 1000);
    this.polledReccomendation = trigger.pipe(
      concatMap(() => bitcoin),
      // map((response) => {}), Uncomment to transform each response into other
    );
    */
  }

  public makeEmptyRecommendations() {
    const recommendations: Recommendation[] = [];
    localStorage.setItem(this.recommendationsKey, JSON.stringify(recommendations));
    return recommendations;
  }

  public getRecomendations(): Recommendation[] {
    return JSON.parse(localStorage.getItem(this.recommendationsKey)) as Recommendation[];
  }

  public updateRecommendations(recommendations: Recommendation[]): void {
    localStorage.setItem(this.recommendationsKey, JSON.stringify(recommendations));
  }

  // TODO: Change username to ID as soon as possible
  public getNewRecommendation(username: string, productsInCart: Product[]) {
    // TODO: Post request that sends these data and get a new recommendation
    // TODO: Call addRecommendation with the obtained recommendation
  }

  public addRecommendation(recommendation: Recommendation) {
    const recommendations: Recommendation[] = this.getRecomendations();
    recommendations.push(recommendation);
    this.updateRecommendations(recommendations);
  }

  public removeRecommendation() {

  }
}
