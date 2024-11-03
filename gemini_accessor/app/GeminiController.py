from flask import Blueprint, jsonify, render_template,request,abort,current_app
from GeminiConfig import geminiRequest as gemini
import logging
from dapr.clients import DaprClient
import json

main_blueprint = Blueprint('main', __name__)

logging.basicConfig(level=logging.DEBUG, format='%(asctime)s %(levelname)s %(message)s')

@main_blueprint.route('/api.getMyArtical', methods=['POST'])
def myArtical():
    try:
        
        current_app.logger.info("api.getMyArtical")
        data = request.get_json()
        current_app.logger.info("i have data")
        articl=data.get("artical")
        current_app.logger.info("i get artical")
        numberOfArtical=data.get("numberOfArtical")
        current_app.logger.info("i get numberOfArtical")
        preferencec=data.get("preferencec")
        current_app.logger.info("i get preferencec")
        user=data.get("to")
        current_app.logger.info(f"i get to {user}")
        chat=gemini()
        current_app.logger.info("connect to google")
        
        while True:
            response = chat.send_message(geminiRequstTemplet(articl,numberOfArtical,preferencec))
            current_app.logger.info("i have response")
            #current_app.logger.info(response.text)
            
            text=response.text
            start=text.find('[')
            end=text.find(']')
            text=text[start:end+1]

            current_app.logger.info(text)
            responseData={"articals":text,"to":user}
            # current_app.logger.info(str(responseData))
            dataToSend=json.dumps(responseData)
            current_app.logger.info("response data")
            if is_json_object(dataToSend):
                break
        with DaprClient() as d:
            d.invoke_binding("gimeniAnswer","create",dataToSend)
            current_app.logger.info("invoke")
        return jsonify({"status": "success", "message": "Article processed successfully"}), 200
    except Exception as e:
        current_app.logger.error(f"error {e}")
        abort(500,description= "somting gone wrong!") 
    

@main_blueprint.route('/', methods=['GET'])
def test():
    current_app.logger.info("test")
    return "im lsining"


def geminiRequstTemplet(artical,numberOfArtical,preferencec):
    data = (
    f"from this json {artical} "
    f"i want you to pick {numberOfArtical} artical "
    f"they need to be to most intresting and rlevant for me base on {preferencec}"
    "if its empty or null, choce randomly."
    "i want you return me the answer in this type:"
    "url: the artical url here as short url as possible, dont give me diffrence url! "
    "titel: the artical title here"
    "summary: base on url,title and description put here the short artical summery maximum 50 words"
    "videoUrl: short url from video url if exists"
    "pubDate: the date from pubDate in format yyyy-MM-dd HH:mm:ss"
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