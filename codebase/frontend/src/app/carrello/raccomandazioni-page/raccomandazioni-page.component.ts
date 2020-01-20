import { Component, OnInit } from '@angular/core';
import { Recommendation } from 'src/app/shared/model/recommendation';
import { RaccomandazioniService } from '../raccomandazioni.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-raccomandazioni-page',
  templateUrl: './raccomandazioni-page.component.html',
  styleUrls: ['./raccomandazioni-page.component.scss'],
})
export class RaccomandazioniPageComponent implements OnInit {
  recommendations: Recommendation[];

  constructor(private raccomandazioniService: RaccomandazioniService,
    private router: Router) { }

  ngOnInit() {
    this.updateRecommendations();
  }

  public getNumberSequence(): number[] {
    return Array.from(this.recommendations.keys());
  }

  public deleteRecommendation(index: number) {
    this.raccomandazioniService.deleteRecommendation(index);
    this.updateRecommendations();
  }

  private updateRecommendations() {
    this.recommendations = this.raccomandazioniService.getRecomendations()
      .map((value) =>
        new Recommendation(value)
      );
  }

  /* Wait to decide if after scan the product must be added instant... this requires
  a change into scan service that start the scan plugin but wait for the correct barcode!!
  public navigateToDetail(index: number) {
    const item: Recommendation = this.recommendations[index];
    this.router.navigateByUrl('/articolo' + item.getProduct().getBarcode(), { state: { recommendation: true } });
  }
  */
}
