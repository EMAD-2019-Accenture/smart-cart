import scrapy
from scrapy import Request as Request
from scrapy_splash import SplashRequest
import csv

class AlimentaricoopSpider(scrapy.Spider):
    name = 'preparatiAllergeni'
    alim_dict = dict()
    filename = 'preparati_allergeni.csv' 
    id = 0
    allergens_dict = {
        "Anidride": 1,
        "Arachidi": 2,
        "Cereali": 3,
        "Crostacei": 4,
        "Frutta": 5,
        "Latte": 6,
        "Lupino": 7,
        "Molluschi": 8,
        "Pesce": 9,
        "Sedano": 10,
        "Semi": 11,
        "Senape": 12,
        "Soia": 13,
        "Uova": 14,
        }

    def start_requests(self):
        
        with open(self.filename, 'w', newline='') as file:
            writer = csv.writer(file)
            writer.writerow(["allergen_id", "product_id"])

        alimenti_preparati = ['http://www.catalogoprodotti.coop.it/pam/it/Categorie-PAM/Alimentari-confezionati/Alimenti-preparati-e-altro/c/110102?q=%3Arelevance&page=0&pagesize=36']
        
        for url in alimenti_preparati:
            yield SplashRequest(url=url, callback=self.parse_page_products)


    def parse_page_products(self, response):
        urls = ["{}{}".format('http://www.catalogoprodotti.coop.it',i) for i in response.css('div.productGridItem .thumb a::attr(href)').getall()]

        for url in urls:
            yield SplashRequest(url=url, callback=self.get_info,args={'wait':2})

    def get_info(self, response):
        barcode = response.css('.ean div.descrizione::text').get()
        if barcode:
            if not barcode in self.alim_dict:     
                self.alim_dict[barcode] = 1
                self.id += 1

                allergens = response.css('#allergeni_table td::text').getall()
                allergens = [self.remove_tabs(x) for x in allergens]
                allergens = [self.remove_new_lines(x) for x in allergens]

                if allergens:

                    allergen_list = []

                    for allergen in allergens:
                        keyword = allergen.split(' ', 1)[0]

                        newAllergen = [self.allergens_dict[keyword], self.id]
                        allergen_list.append(newAllergen) 
                    
                    with open(self.filename, 'a', newline='') as file:
                        writer = csv.writer(file)
                        writer.writerows(allergen_list)

                    print(allergen_list)       

    def get_page_product(self, response):
        url = response.url.split("/")[-3]
        index = response.url.find('page')
        field = url + '_Page_' + response.url[index+5]

        yield {
            field: ["{}{}".format('http://www.catalogoprodotti.coop.it',i) for i in response.css('div.productGridItem .thumb a::attr(href)').getall()]
        }
        print('Parsata:{}'.format(response.url))

    def remove_tabs(self, string):
        return string.replace('\t','')

    def remove_new_lines(self, string):
        return string.replace('\n','')

    def remove_double_quotes(self, string):
        return string.replace('\"','')

    def remove_bold(self, string):
        newString =  string.replace('<b>','')        
        return newString.replace('</b>','')

    def remove_br(self, string):
        return string.replace('<br>','')

    def parse_ingredients(self, textIngredients):
        textIngredients = self.remove_double_quotes(textIngredients)
        textIngredients = self.remove_bold(textIngredients)
        textIngredients = textIngredients.replace('<div class=descrizione id=div_descrizione_id>','')
        textIngredients = textIngredients.replace('</div>','')
        
        return textIngredients