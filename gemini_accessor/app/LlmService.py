from dataclasses import asdict

from GeminiConfig import geminiRequest as LLM
import json
import ArticalsToFilter
import logging
from typing import List
from ArticalToReturn import  ArticleReturn
from ArticalsToFilter import ArticleToGetFilter


def my_article(json_data):
    try:
        logging.basicConfig(
            level=logging.INFO,
            format='%(asctime)s - %(levelname)s - %(message)s'
        )
        

        data=ArticalsToFilter.ArticlesToFilter.from_json(json_data)
        logging.info(f'filter news for {data.to}')

        article=data.article_return_list.copy()

        number_of_article=data.number_of_article
        preference=data.preference.copy()
        user=data.to
        option=data.option


        while True:
            try:
                text = LLM((llm_reqeust_templet(article, number_of_article, preference)))
                logging.info(f'answer return from llm for {data.to}')


                start=text.find('[')
                end=text.find(']')
                text=text[start:end+1]
                text=is_json_object(text)
                if not text:
                    logging.warning(f'Invalid JSON format received for user: {data.to}')
                    continue
                data_to_send = checking_url(text, article)
                response_data={"articles":data_to_send,"to":user,"option":option}


                logging.info(f'Successfully filtered news for user: {data.to}')
                return json.dumps(response_data)

            except Exception as e:
                logging.error(f'Error in LLM processing loop for user {data.to}: {str(e)}')
                continue
    except Exception as e:
        print(e)

        

def llm_reqeust_templet(article, number_of_article, preference):
    data = (
    f"from this json {article} "
    f"i want you to pick exactly {number_of_article} articles "
    f"they need to be to most interesting and relevant for me base on {preference}"
    "if its empty or null, choice randomly."
    "Make sure the articles are different and not repetitive. "
    "i want you return me the answer in this type:"
    "url: the article url here give me the exact same url "
    "title: the article title here"
    "summary: base on url,title and description put here the short article summery maximum 50 words"
    "videoUrl: short url from video url if exists"
    "pubDate: the date from pubDate "
    "give me this in json format ready to insert into object"
    "make shore its json format without any error"
    )
    return data

def is_json_object(result):
    return json.loads(result)



def checking_url(result: List, original: List[ArticleToGetFilter]) -> str:

    original_urls = {article.url: article for article in original}

    data = [ArticleReturn.from_json(article) for article in result]

    for article in data:
        matching_url = next(
            (orig_url for orig_url in original_urls.keys()
             if article.url in orig_url and article.url != orig_url),
            None
        )
        if matching_url:
            article.url = matching_url

    return json.dumps([asdict(article) for article in data], indent=4)