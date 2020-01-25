import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Recommendation } from 'src/app/shared/model/recommendation';
import { RaccomandazioniService } from '../raccomandazioni.service';

@Component({
  selector: 'app-raccomandazioni-page',
  templateUrl: './raccomandazioni-page.component.html',
  styleUrls: ['./raccomandazioni-page.component.scss'],
})
// tslint:disable: align
export class RaccomandazioniPageComponent implements OnInit, OnDestroy {
  recommendations: Recommendation[];
  subscription: Subscription;

  constructor(private raccomandazioniService: RaccomandazioniService,
    private router: Router) { }

  ngOnInit() {
    this.subscription = this.raccomandazioniService.getRecommendationSubject()
      .subscribe(value => this.recommendations = value);
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

  /*
  * After scan the product must be added instant... this requires
  * a change into scan service that start the scan plugin but wait for the correct barcode!!
  */
  public navigateToDetail(index: number) {
    const recommendation: Recommendation = this.recommendations[index];
    this.router.navigateByUrl('/articolo/' + recommendation.getProduct().getBarcode(), { state: { recommendation } });
  }
}
