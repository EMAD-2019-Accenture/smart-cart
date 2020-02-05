import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Product, IProduct } from '../model/product';
import { Recommendation, Status, IRecommendation } from '../model/recommendation';
import { HttpCommonService } from './http-common.service';

@Injectable({
  providedIn: 'root',
})
export class RaccomandazioniService {
  private static readonly THRESHOLD = 0.1;
  private recommendations: Recommendation[];
  private recommendationSubject: BehaviorSubject<Recommendation[]>;
  private recommendationsPath = 'https://smart-cart-acenture.herokuapp.com/api/recommendations';

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
    this.recommendations[index].setStatus(Status.deleted);
    this.recommendationSubject.next(this.recommendations);
  }

  public acceptRecommendation(id: number) {
    const recommendation: Recommendation = this.recommendations.find(r => r.getId() === id);
    if (recommendation) {
      recommendation.setStatus(Status.accepted);
      this.recommendationSubject.next(this.recommendations);
    }
  }

  public async getNewRecommendation(productsInCart: Product[]): Promise<Recommendation> {
    // TODO: Do not recommend if a similar product has recently been scanned
    if (Math.random() >= RaccomandazioniService.THRESHOLD) {
      const productsId: string = JSON.stringify({
        productsId: productsInCart.map(prod => prod.getId())
      });
      const recommendedProduct: any = await this.http.postRequest(this.recommendationsPath, productsId) as IProduct;
      const recommendation = new Recommendation({
        id: Math.floor(Math.random() * 1000) + 1,
        product: JSON.parse(JSON.stringify(recommendedProduct)),
        date: new Date(),
        status: Status.new
      });
      this.addRecommendation(recommendation);
      return recommendation;
    } else {
      return undefined;
    }
  }
}
