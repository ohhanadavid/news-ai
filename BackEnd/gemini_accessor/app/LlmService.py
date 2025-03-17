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
                text =text.replace('/n',"")
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



    "i want you return me the answer in this type:"
    "url: the article url here give me the exact same url "
    "title: the article title here"
    "summary: base on url,title and description put here the short article summery maximum 50 words"
    "videoUrl: the exact same url from video url if exists"
    "pubDate: the date from pubDate "
    "give me this in json format ready to insert into object"
    "make shore its json format without any error"
    )
    data = (
        f"Given the following JSON data {article}, "
        f"I need you to select exactly {number_of_article} articles. "
        f"The articles should be the most interesting and relevant based on {preference}. "
        "If the preference is empty or null, please select the articles randomly. "
        "Ensure that exactly the requested number of articles are returned, with no repetitions. "
        "Return the result in the following JSON format, without any additional characters or errors:"
        "\n"
        "{"
        "\n  \"articles\": ["
        "\n    {"
        "\n      \"url\": \"<article_url>\","
        "\n      \"title\": \"<article_title>\","
        "\n      \"summary\": \"<base on url,title and description put here the short article summery maximum 50 words>\","
        "\n      \"videoUrl\": \"<the exact same url from video url if exists if not exists return here null>\","
        "\n      \"pubDate\": \"<publication_date>\""
        "\n    }"
        "\n  ]"
        "\n}"
        "\nMake sure the JSON format is correct with no extra commas or characters. "
        "The JSON should be ready to be inserted into an object."
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