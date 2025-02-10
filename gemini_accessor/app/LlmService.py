#from flask import current_app
from GeminiConfig import geminiRequest as gemini
import json
import ArticalsToFilter


def myArtical(jsonData):
    try:
        
        

        data=ArticalsToFilter.ArticlesToFilter.from_json(jsonData)
        article=data.article_return_list.copy()


        numberOfArtical=data.number_of_article


        preference=data.preference.copy()


        user=data.to


        chat=gemini()

        
        while True:
            response = chat.send_message(geminiRequstTemplet(article,numberOfArtical,preference))

            
            text=response.text
            start=text.find('[')
            end=text.find(']')
            text=text[start:end+1]


            responseData={"articles":text,"to":user}

            dataToSend=json.dumps(responseData)

            if is_json_object(dataToSend):
                return dataToSend
    except Exception as e:
        print(e)

        

def geminiRequstTemplet(article, numberOfArtical, preference):
    data = (
    f"from this json {article} "
    f"i want you to pick {numberOfArtical} articles "
    f"they need to be to most interesting and relevant for me base on {preference}"
    "if its empty or null, choice randomly."
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
    try:
        result = json.loads(result)
        return isinstance(result, dict)
    except json.JSONDecodeError:
        return False