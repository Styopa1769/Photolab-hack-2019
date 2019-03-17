from flask import Flask, render_template, request, session, send_file
import json
import example as ex
import base64
from Final import *
import tensorflow as tf

app = Flask(__name__)

regressions, words_dict, text_emotion_model = load_memes_model()

graph = tf.get_default_graph()




@app.route('/get_pictures/', methods = ['POST'])
def get_pictures():
    global graph
    with graph.as_default():
        text = request.get_json()
        base64_array = []
        result = predict_memes(text.get("text"), text_emotion_model, words_dict, regressions)
        for pict in result:
            with open('./memes/'+pict+'.jpg', "rb") as image_file:
                encoded = base64.b64encode(image_file.read())
                base64_array.append(encoded.decode('utf-8'))
        json_data = json.dumps(base64_array)
        return json_data


if __name__ == '__main__':
    app.run()
