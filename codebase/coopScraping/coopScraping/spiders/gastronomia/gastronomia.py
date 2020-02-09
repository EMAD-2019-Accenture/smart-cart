import scrapy, random
from scrapy import Request as Request
from scrapy_splash import SplashRequest

class AlimentaricoopSpider(scrapy.Spider):
    name = 'gastronomia'
    alim_dict = dict()
    id = 170

    def start_requests(self):

        gastronomia = ['http://www.catalogoprodotti.coop.it/pam/it/Categorie-PAM/Alimentari-freschi/Gastronomia/c/110203?q=%3Arelevance&page={}&pagesize=36'.format(i) for i in range(2)]        
        
        for url in gastronomia:
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

                name = ', '.join(response.css('.manufacturer span ::text').getall()).replace('\"','')
                name = self.remove_double_quotes(name)

                brand = response.css('.manufacturer h1 ::text').get()
                try:
                    brand = self.remove_tabs(brand)
                    brand = self.remove_new_lines(brand)
                except:
                    brand = ' '

                source = ', '.join(response.css('.origini p.descrizione::text').getall())
                source = self.remove_tabs(source)
                source = self.remove_new_lines(source)
                source = self.remove_double_quotes(source)
                

                description = ''.join(response.css('div.description div.descrizione ::text').getall()) + ', '.join(response.css('div.description div.descrizione2 ::text').getall())
                description = self.remove_tabs(description)
                description = self.remove_new_lines(description)
                description = self.remove_double_quotes(description)

                price = round(self.randrange_float(0.3, 5, 0.05),2)

                ingredients = ''.join(response.css('#div_descrizione_id').getall())

                textIngredients = ''
                for i in ingredients:
                    textIngredients = textIngredients + i
                ingredients = self.parse_ingredients(textIngredients)

                image_url = response.css('#primary_image_id::attr(src)')
                try:
                    image_url = "http://www.catalogoprodotti.coop.it"+image_url.get()
                except:
                    image_url = ' '

                conservation = ', '.join(response.css('.conservazione div div::text').getall())

                preparation = ', '.join(response.css('.preparazione div div::text').getall())
                preparation = self.remove_double_quotes(preparation)

                nutrients = [i +': '+ j for i, j in zip(response.css('.valori_nutrizionali td.c1::text').getall(),response.css('.valori_nutrizionali td.c2::text').getall())]
                try:
                    del nutrients[1]
                    nutrients = ', '.join(nutrients)
                except:
                    nutrients = ' '
                
                allergens = response.css('#allergeni_table td::text').getall()
                allergens = [self.remove_tabs(x) for x in allergens]
                allergens = [self.remove_new_lines(x) for x in allergens]

                self.id += 1
                
                yield {
                    'id': self.id,
                    'barcode': barcode,
                    'name': name,
                    'description': description,
                    'price': price,
                    'brand': brand,
                    'amount': ' ',
                    'image_url': image_url,
                    'source': source,
                    'ingredients': ingredients,
                    'conservation': conservation,
                    'preparation': preparation,
                    'nutrients': nutrients,
                    'discount_id': ' ',
                    'category_id': 4,
                }

                print(name)       

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

    def randrange_float(self, start, stop, step):
        return random.randint(0, int((stop - start) / step)) * step + start