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

        barcode = response.css('.ean div.descrizione::text').get()

        name = ' '.join(response.css('.manufacturer span ::text').getall()).replace('\"','')
        name = self.remove_double_quotes(name)

        brand = response.css('.manufacturer h1 ::text').get()
        brand = self.remove_tabs(brand)
        brand = self.remove_new_lines(brand)

        description = ''.join(response.css('div.description div.descrizione ::text').getall()) + ' '.join(response.css('div.description div.descrizione2 ::text').getall())
        description = self.remove_tabs(description)
        description = self.remove_new_lines(description)
        description = self.remove_double_quotes(description)

        image_url = response.css('#primary_image_id::attr(src)')
        try:
            image_url = "http://www.catalogoprodotti.coop.it"+immagine.get()
        except:
            image_url = []

        preparation = ', '.join(response.css('.preparazione div div::text').getall())
        preparation = self.remove_double_quotes(preparation)

        attributes = [i +': '+ j for i, j in zip( response.css('.productFeatureClasses .attrib::text').getall(), response.css('.productFeatureClasses .borderLeftDashed::text').getall())]

        attributes = [self.remove_tabs(x) for x in attributes]
        attributes = [self.remove_new_lines(x) for x in attributes]
        attributes = ', '.join(attributes)

        yield {
            'barcode': barcode,
            'name': name,
            'brand': brand,
            'description': description,
            'image_url': image_url,
            'preparation': preparation,
            'attributi': attributes,
        }

        print(brand + ' - '+name +' - '+barcode)

    def remove_tabs(self, string):
        return string.replace('\t','')

    def remove_new_lines(self, string):
        return string.replace('\n','')

    def remove_double_quotes(self, string):
        return string.replace('\"',"")