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

  public addRecommendation(recommendation: Recommendation): boolean {
    const isAlreadyRecommended: boolean = this.recommendations
      .map(value => value.getProduct().getId())
      .includes(recommendation.getProduct().getId(), 0);
    if (isAlreadyRecommended) {
      return false;
    } else {
      this.recommendations.push(recommendation);
      this.recommendationSubject.next(this.recommendations);
      return true;
    }
  }

  public deleteRecommendation(id: number) {
    const recommToDelete = this.recommendations.find(r => r.getId() === id);
    if (recommToDelete) {
      recommToDelete.setStatus(Status.deleted);
      this.recommendationSubject.next(this.recommendations);
    }
  }

  public acceptRecommendation(id: number) {
    const recommToAccept: Recommendation = this.recommendations.find(r => r.getId() === id);
    if (recommToAccept) {
      recommToAccept.setStatus(Status.accepted);
      this.recommendationSubject.next(this.recommendations);
    }
  }

  public async getNewRecommendation(productsInCart: Product[]): Promise<Recommendation> {
    /*
    if (Math.random() < RaccomandazioniService.THRESHOLD) {
      console.log('Probability fail');
      return null;
    }
    */

    const productsId: string = JSON.stringify({
      productsId: productsInCart.map(prod => prod.getId())
    });
    return await this.http.postRequest(this.recommendationsPath, productsId)
      .then((recomm: IProduct) => {
        const recommendation = new Recommendation({
          id: Math.floor(Math.random() * 10000) + 1,
          product: JSON.parse(JSON.stringify(recomm)),
          date: new Date(),
          status: Status.new
        });
        return recommendation;
      })
      .catch(reason => {
        if (reason.status === 404) {
          console.log('There is no recommendation for this product');
        } else {
          console.log('Network error');
        }
        return null;
      });
  }

  public purgeRecommendations() {
    this.recommendations = [];
  }
}
