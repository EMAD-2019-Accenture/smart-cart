import { Component, OnInit } from '@angular/core';
import { RaccomandazioniService } from '../raccomandazioni.service';
import { Recommendation } from 'src/app/shared/model/recommendation';

@Component({
  selector: 'app-raccomandazioni-page',
  templateUrl: './raccomandazioni-page.component.html',
  styleUrls: ['./raccomandazioni-page.component.scss'],
})
export class RaccomandazioniPageComponent implements OnInit {
  recommendations: Recommendation[];

  constructor(private raccomandazioniService: RaccomandazioniService) {
    this.recommendations = this.raccomandazioniService.makeEmptyRecommendations();
    // TODO: Go to template and show recommendations
  }

  ngOnInit() {

  }
}
