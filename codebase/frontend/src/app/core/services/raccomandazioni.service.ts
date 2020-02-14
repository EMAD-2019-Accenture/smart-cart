import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Product } from '../../shared/model/product';
import { Recommendation, Status } from '../../shared/model/recommendation';
import { HttpCommonService } from './http-common.service';

@Injectable({
  providedIn: 'root',
})
export class RaccomandazioniService {
  private static readonly THRESHOLD = 0.4;
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

  public getNewRecommendation(productsInCart: Product[]): Recommendation {
    // TODO: Do not recommend if a similar product has recently been scanned
    if (Math.random() >= RaccomandazioniService.THRESHOLD) {
      // TODO: Need a service from AuthService to get logged customer ID instead of username?? Ask Manuel
      // TODO: Post request that sends these data and get a new recommendation and remove this DEBUG oject
      const recommendation = new Recommendation({
        id: Math.floor(Math.random() * 1000) + 1,
        product: JSON.parse(JSON.stringify(productsInCart[1])),
        date: new Date('01-01-2020'),
        status: Status.new
      });
      this.addRecommendation(recommendation);
      return recommendation;
    } else {
      return undefined;
    }
  }
}
