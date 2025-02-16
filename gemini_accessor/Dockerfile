FROM python:3.12

WORKDIR /app

COPY ./app/requirements.txt .
#COPY ./app/requirements.txt .

RUN pip install --no-cache-dir -r requirements.txt

COPY ./app .
#COPY ./app .
RUN chmod 755 /app

EXPOSE 7004


CMD ["python", "main.py"]




