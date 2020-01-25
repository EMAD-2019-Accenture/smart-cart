import { BehaviorSubject } from 'rxjs';
import { HttpCommonService } from '../shared/http-common.service';
import { Product } from '../shared/model/product';
import { Recommendation } from '../shared/model/recommendation';

export class RaccomandazioniService {
  private recommendations: Recommendation[];
  private recommendationSubject: BehaviorSubject<Recommendation[]>;

  constructor(private http: HttpCommonService) {
    this.recommendations = [];
    this.recommendationSubject = new BehaviorSubject(this.recommendations);
  }

  public getRecommendationSubject(): BehaviorSubject<Recommendation[]> {
    return this.recommendationSubject;
  }

  public addRecommendation(recommendation: Recommendation) {
    this.recommendations.push(recommendation);
    this.recommendationSubject.next(this.recommendations);
  }

  public deleteRecommendation(index: number) {
    this.recommendations.splice(index, 1);
    this.recommendationSubject.next(this.recommendations);
  }

  public getNewRecommendation(productsInCart: Product[]) {
    // TODO: Post request that sends these data and get a new recommendation and remove this DEBUG oject
    const recommendation = new Recommendation({
      id: 1,
      product: JSON.parse(JSON.stringify(productsInCart[1])),
      date: new Date('01-01-2020')
    });
    this.addRecommendation(recommendation);
  }
}
