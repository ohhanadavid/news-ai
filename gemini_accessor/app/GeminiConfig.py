

"""
Install the Google AI Python SDK

$ pip install google-generativeai

See the getting started guide for more information:
https://ai.google.dev/gemini-api/docs/get-started/python
"""

import os
import logging 
import google.generativeai as genai
import Datafile
from flask import current_app

logging.basicConfig(level=logging.DEBUG, format='%(asctime)s %(levelname)s %(message)s')

def geminiRequest():
  key=os.environ.get("GEMINI_apiKEY")
  current_app.logger.info(f"config: {key}")
  
  genai.configure(api_key=os.getenv("GEMINI_apiKEY"))
  # Create the model
  # See https://ai.google.dev/api/python/google/generativeai/GenerativeModel
  generation_config = {
    "temperature": 1,
    "top_p": 0.95,
    "top_k": 64,
    "max_output_tokens": 8192,
    "response_mime_type": "text/plain",
  }

  model = genai.GenerativeModel(
    model_name="gemini-1.5-flash",
    generation_config=generation_config,
    # safety_settings = Adjust safety settings
    # See https://ai.google.dev/gemini-api/docs/safety-settings
  )

  chat_session = model.start_chat(
    history=[
    ]
  )
  return chat_session

# data = (
#     f"from this json {Datafile.data} "
#     f"i want you to pick {Datafile.number} artical "
#     f"they need to be to most intresting an rlevant for me base on {Datafile.preferencec}"
#     "i want you return me the answer in this type:"
#     "url: the artical url here "
#     "titel: the artical title here"
#     "summery: base on url,title and description put here the artical summery"
#     "videoUrl: if video url field is currect url if not dont"
#     "give me this in json format ready to insert into object"
# )
# response = chat_session.send_message(data)

# print(response.text)