# -*- coding: utf-8 -*-
import scrapy
from scrapy_splash import SplashRequest

class AlimentaricoopSpider(scrapy.Spider):
    name = 'alimentariCoop'
    allowed_domains = ['example.com']
    start_urls = ['http://example.com/']

    def start_requests(self):
        urls = [
            'http://www.catalogoprodotti.coop.it/pam/it/Categorie-PAM/Alimentari-confezionati/Alimenti-preparati-e-altro/Alimenti-preparati/Vellutata-ai-Porcini-e-Patate-Istantanea-35-g/p/7051552'
        ]
        for url in urls:
            yield SplashRequest(url=url, callback=self.parse,args={'wait':0.2})

    def parse(self, response):
        for descr in response.css('#div_descrizione_id'):
            yield {
                'ingredienti': response.css('#div_descrizione_id::text').getall()
            }

        print('Risposta sksk : %s' % response.css('#div_descrizione_id::text').getall())

