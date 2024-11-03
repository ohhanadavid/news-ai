from flask import Flask, render_template,request
from GeminiController import main_blueprint
import debugpy
from time import sleep as sleep

app = Flask(__name__)
app.register_blueprint(main_blueprint)

if __name__ == '__main__':

    app.run(host='0.0.0.0',port=7004   )
