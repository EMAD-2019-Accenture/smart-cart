import { Component, OnInit } from '@angular/core';
import { RaccomandazioniService } from '../raccomandazioni.service';

@Component({
  selector: 'app-raccomandazioni-page',
  templateUrl: './raccomandazioni-page.component.html',
  styleUrls: ['./raccomandazioni-page.component.scss'],
})
export class RaccomandazioniPageComponent implements OnInit {

  constructor(private raccomandazioniService: RaccomandazioniService) { }

  ngOnInit() { }



}
