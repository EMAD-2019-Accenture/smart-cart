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
            yield SplashRequest(url=url, callback=self.parse,args={'wait':1})

    def parse(self, response):
        for descr in response.css('#div_descrizione_id'):
            yield {
                'ean': response.css('.ean div.descrizione::text').get(),
                'nome': response.css('.manufacturer span ::text').get(),
                'marchio': response.css('.manufacturer h1 ::text').get(),
                'preparazione': response.css('.preparazione div::text')[1].get(),
                'origine': response.css('.origini p.descrizione::text').getall(),
                'descrizione': response.css('div.description div.descrizione ::text').get(),
                'ingredienti': "".join(response.css('#div_descrizione_id::text').getall()),
                'immagine': "http://www.catalogoprodotti.coop.it"+response.css('#primary_image_id::attr(src)').get(),
                'conservazione': response.css('.conservazione div div::text').get(),
                'preparazione': response.css('.preparazione div div::text')[1].get(),
                'nutrienti': [i +': '+ j for i, j in zip(response.css('.valori_nutrizionali td.c1::text').getall(),response.css('.valori_nutrizionali td.c2::text').getall())],
                'allergeni': response.css('#allergeni_table td::text').getall(),
            }

        print("End")

