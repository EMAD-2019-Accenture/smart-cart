import scrapy
from scrapy import Request as Request
from scrapy_splash import SplashRequest


class ProdotticurapersonaleSpider(scrapy.Spider):
    name = 'prodottiCuraPersonale'
    
    def start_requests(self):

        igienePersonale = ['http://www.catalogoprodotti.coop.it/pam/it/Categorie-PAM/Cura-della-persona/Igiene-persona/c/110401?q=%3Arelevance&page={}&pagesize=36'.format(i) for i in range(6)]
        salute = ['http://www.catalogoprodotti.coop.it/pam/it/Categorie-PAM/Cura-della-persona/Salute/c/110403?q=%3Arelevance&page=0&pagesize=36']

        urls = igienePersonale + salute

        for url in urls:
            yield SplashRequest(url=url, callback=self.parse_page_products)

    def parse_page_products(self, response):
        urls = ["{}{}".format('http://www.catalogoprodotti.coop.it',i) for i in response.css('div.productGridItem .thumb a::attr(href)').getall()]

        for url in urls:
            yield SplashRequest(url=url, callback=self.get_info,args={'wait':4})


    def get_info(self, response):
        preparazione = response.css('.preparazione div div::text')

        if not preparazione:
            preparazione = []
        else:
            try:
                preparazione = preparazione.getall()
            except:
                preparazione = []


        immagine = response.css('#primary_image_id::attr(src)')

        try:
            immagine = "http://www.catalogoprodotti.coop.it"+immagine.get()
        except:
            immagine = []
        
        yield {
            'ean': response.css('.ean div.descrizione::text').get(),
            'nome': response.css('.manufacturer span ::text').get(),
            'marchio': response.css('.manufacturer h1 ::text').get(),
            'descrizione': response.css('div.description div.descrizione ::text').getall() + response.css('div.description div.descrizione2 ::text').getall(),
            'immagine': immagine,
            'preparazione': preparazione,
            'attributi': [i +': '+ j for i, j in zip( response.css('.productFeatureClasses .attrib::text').getall(), response.css('.productFeatureClasses .borderLeftDashed::text').getall())],
        }

        print(response.css('.manufacturer span ::text').get())

