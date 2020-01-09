import { Component, OnInit } from '@angular/core';
import { Recommendation } from 'src/app/shared/model/recommendation';
import { RaccomandazioniService } from '../raccomandazioni.service';

@Component({
  selector: 'app-raccomandazioni-page',
  templateUrl: './raccomandazioni-page.component.html',
  styleUrls: ['./raccomandazioni-page.component.scss'],
})
export class RaccomandazioniPageComponent implements OnInit {
  recommendations: Recommendation[];

  constructor(private raccomandazioniService: RaccomandazioniService) {
    this.recommendations = this.raccomandazioniService.getRecomendations()
      .map((value) =>
        new Recommendation(value)
      );
  }

  ngOnInit() { }

  public getNumberSequence(): number[] {
    return Array.from(this.recommendations.keys());
  }
}
