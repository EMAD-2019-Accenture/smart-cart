import { HttpCommonService } from '../shared/http-common.service';
import { Product } from '../shared/model/product';
import { IRecommendation } from '../shared/model/recommendation';

export class RaccomandazioniService {
  private recommendationsKey = 'recommendations';

  constructor(private http: HttpCommonService) {
    const recommendations: IRecommendation[] = [];
    localStorage.setItem(this.recommendationsKey, JSON.stringify(recommendations));
  }

  public getRecomendations(): IRecommendation[] {
    return JSON.parse(localStorage.getItem(this.recommendationsKey)) as IRecommendation[];
  }

  public getNewRecommendation(productsInCart: Product[]) {
    // TODO: Post request that sends these data and get a new recommendation
    // TODO: Remove this DEBUG object
    const recommendation = {
      id: 1,
      product: JSON.parse(JSON.stringify(productsInCart[1])),
      date: new Date('01-01-2020')
    };
    this.addRecommendation(recommendation);
  }

  public addRecommendation(recommendation: IRecommendation) {
    const recommendations: IRecommendation[] = this.getRecomendations();
    recommendations.push(recommendation);
    this.updateRecommendations(recommendations);
  }

  public deleteRecommendation(index: number) {
    const recommendations: IRecommendation[] = this.getRecomendations();
    recommendations.splice(index, 1);
    this.updateRecommendations(recommendations);
  }

  private updateRecommendations(recommendations: IRecommendation[]): void {
    localStorage.setItem(this.recommendationsKey, JSON.stringify(recommendations));
  }
}
