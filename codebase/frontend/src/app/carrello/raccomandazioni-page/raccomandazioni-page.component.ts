import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Recommendation, Status } from 'src/app/core/model/recommendation';
import { RaccomandazioniService } from '../../core/services/raccomandazioni.service';

@Component({
  selector: 'app-raccomandazioni-page',
  templateUrl: './raccomandazioni-page.component.html',
  styleUrls: ['./raccomandazioni-page.component.scss'],
})
// tslint:disable: align
export class RaccomandazioniPageComponent implements OnInit, OnDestroy {
  recommendations: Recommendation[];
  newRecommendations: Recommendation[];
  subscription: Subscription;

  constructor(private raccomandazioniService: RaccomandazioniService,
    private router: Router) { }

  ngOnInit() {
    this.subscription = this.raccomandazioniService.getRecommendationSubject()
      .subscribe(value =>
        this.recommendations = value.filter(r => r.getStatus() === Status.new));
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  public getNumberSequence(): number[] {
    return Array.from(this.recommendations.keys());
  }

  public deleteRecommendation(index: number) {
    this.raccomandazioniService.deleteRecommendation(index);
  }

  public navigateToDetail(index: number) {
    const recommendation: Recommendation = this.recommendations[index];
    this.router.navigateByUrl('/articolo/' + recommendation.getProduct().getBarcode(), {
      state: {
        recommendationId: recommendation.getId()
      }
    });
  }
}
