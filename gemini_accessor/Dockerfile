FROM python:3.11

WORKDIR /app

COPY ./gemini_accessor/app/requirements.txt .
#COPY ./app/requirements.txt .

RUN pip install --no-cache-dir -r requirements.txt

COPY ./gemini_accessor/app .
#COPY ./app .
RUN chmod 755 /app

EXPOSE 7004

ENV FLASK_APP=main.py
ENV FLASK_RUN_HOST=0.0.0.0

CMD ["python", "main.py"]




