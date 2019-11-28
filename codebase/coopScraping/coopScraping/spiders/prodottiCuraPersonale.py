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
            yield SplashRequest(url=url, callback=self.get_info,args={'wait':2})


    def get_info(self, response):

        ean = response.css('.ean div.descrizione::text').get()

        nome = ', '.join(response.css('.manufacturer span ::text').getall()).replace('\"','')
        nome = self.remove_double_quotes(nome)

        marchio = response.css('.manufacturer h1 ::text').get()
        marchio = self.remove_tabs(marchio)
        marchio = self.remove_new_lines(marchio)

        descrizione = ''.join(response.css('div.description div.descrizione ::text').getall()) + ', '.join(response.css('div.description div.descrizione2 ::text').getall())
        descrizione = self.remove_tabs(descrizione)
        descrizione = self.remove_new_lines(descrizione)
        descrizione = self.remove_double_quotes(descrizione)

        immagine = response.css('#primary_image_id::attr(src)')

        try:
            immagine = "http://www.catalogoprodotti.coop.it"+immagine.get()
        except:
            immagine = []

        preparazione = ', '.join(response.css('.preparazione div div::text').getall())
        preparazione = self.remove_double_quotes(preparazione)

        attributi = [i +': '+ j for i, j in zip( response.css('.productFeatureClasses .attrib::text').getall(), response.css('.productFeatureClasses .borderLeftDashed::text').getall())]

        attributi = [self.remove_tabs(x) for x in attributi]
        attributi = [self.remove_new_lines(x) for x in attributi]
        attributi = ', '.join(attributi)

        yield {
            'ean': ean,
            'nome': nome,
            'marchio': marchio,
            'descrizione': descrizione,
            'immagine': immagine,
            'preparazione': preparazione,
            'attributi': attributi,
        }

        print(marchio + ' - '+nome)

    def remove_tabs(self, string):
        return string.replace('\t','')

    def remove_new_lines(self, string):
        return string.replace('\n','')

    def remove_double_quotes(self, string):
        return string.replace('\"',"")