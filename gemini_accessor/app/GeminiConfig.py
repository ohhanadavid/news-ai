

import os
import logging 
import google.generativeai as genai
from dotenv import load_dotenv

logging.basicConfig(level=logging.DEBUG, format='%(asctime)s %(levelname)s %(message)s')

def geminiRequest():
  load_dotenv()

  
  genai.configure(api_key=os.getenv("GEMINI_apiKEY"))


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

  )

  chat_session = model.start_chat(
    history=[
    ]
  )
  return chat_session
