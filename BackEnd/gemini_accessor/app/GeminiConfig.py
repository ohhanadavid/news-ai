


import logging
from dotenv import load_dotenv
import os

from google import genai
from google.genai import types

logging.basicConfig(level=logging.DEBUG, format='%(asctime)s %(levelname)s %(message)s')

def geminiRequest(request)->str:
    load_dotenv()
    client = genai.Client(
        api_key=os.environ.get("GEMINI_apiKEY"),
    )

    model = "gemini-2.0-flash"
    contents = [
        types.Content(
            role="user",
            parts=[
                types.Part.from_text(text=request),
            ],
        ),
    ]
    generate_content_config = types.GenerateContentConfig(
        temperature=1,
        top_p=0.95,
        top_k=40,
        max_output_tokens=8192,
        response_mime_type="text/plain",
    )

    ans=""
    for chunk in client.models.generate_content_stream(
        model=model,
        contents=contents,
        config=generate_content_config,
    ):
        ans = ans+ chunk.text+ '/n'

    return ans




